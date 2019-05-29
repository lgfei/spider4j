package com.lgfei.tool.spider.common.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.constant.NumberKeys;
import com.lgfei.tool.spider.common.util.ReflectUtil;
import com.lgfei.tool.spider.common.vo.PageVO;

/**
 * mybatis分页查询拦截器
 * <p>
 * 分页拦截器，用于拦截需要进行分页查询的操作，然后对其进行分页处理。 <br/>
 * 利用拦截器实现Mybatis分页的原理：  <br/>
 *  要利用JDBC对数据库进行操作就必须要有一个对应的Statement对象，
 *  Mybatis在执行Sql语句前就会产生一个包含Sql语句的Statement对象，而且对应的Sql语句是在Statement之前产生的，
 *  所以我们就可以在它生成Statement之前对用来生成Statement的Sql语句下手。 
 *  <br/>
 *  在Mybatis中Statement语句是通过RoutingStatementHandler对象的prepare方法生成的。
 *  所以利用拦截器实现Mybatis分页的一个思路就是拦截StatementHandler接口的prepare方法，
 *  然后在拦截器方法中把Sql语句改成对应的分页查询Sql语句， 之后再调用StatementHandler对象的prepare方法，即调用invocation.proceed()。
 *  <br/>
 *  对于分页而言，在拦截器里面我们还需要做的一个操作就是统计满足当前条件的记录一共有多少， 
 *  这是通过获取到了原始的Sql语句后，把它改为对应的统计语句再利用Mybatis封装好的参数和设置参数的功能把Sql语句中的参数进行替换，之后再执行查询记录数的Sql语句进行总记录数的统计。 
 * </p>
 * 
 * @author  lgfei
 * @version  [版本号, 2017年6月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor
{
    private static final Logger LOG = LoggerFactory.getLogger(PageInterceptor.class);
    
    private static final String SELECT = "select";
    
    private static final String FROM = "from";
    
    /**
     * 数据库类型，不同的数据库有不同的分页方法
     * (目前仅支持Mysql)
     */
    private String dbType;
    
    /**
     * 拦截后要执行的方法
     */
    @Override
    public Object intercept(Invocation invocation)
        throws Throwable
    {
        LOG.info("进入mybatis拦截器");
        Connection connection = null;
        try
        {
            RoutingStatementHandler handler = (RoutingStatementHandler)invocation.getTarget();
            StatementHandler delegate = (StatementHandler)ReflectUtil.getValue(handler, "delegate");
            BoundSql boundSql = delegate.getBoundSql();
            
            // 分页参数
            PageVO page = null;
            Object paramObj = boundSql.getParameterObject();
            if (paramObj instanceof ParamMap)
            {
                ParamMap<?> paramMap = (ParamMap<?>)paramObj;
                Object pageParam = paramMap.containsKey("page") ? paramMap.get("page") : null;
                if (null != pageParam)
                {
                    page = (PageVO)pageParam;
                }
            }
            
            if (null != page)
            {
                MappedStatement mappedStatement = (MappedStatement)ReflectUtil.getValue(delegate, "mappedStatement");
                connection = (Connection)invocation.getArgs()[0];
                
                // 组装查询总数的sql，获取总条数
                int total = this.getTotal(boundSql, paramObj, mappedStatement, connection);
                page.setTotal(total);
                
                // 组装分页查询的sql
                String sql = boundSql.getSql();
                String pageSql = this.getPageSql(page, sql);
                ReflectUtil.setValue(boundSql, "sql", pageSql);
            }
            
            // 执行原有查询或者拼接了分页的查询
            return invocation.proceed();
        }
        finally
        {
            if (null != connection)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                    LOG.error("数据库链接关闭失败:{}", e);
                }
            }
        }
    }
    
    /**
     * 拦截器对应的封装原始对象的方法
     */
    @Override
    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }
    
    /**
     * 设置注册拦截器时设定的属性
     */
    @Override
    public void setProperties(Properties properties)
    {
        this.dbType = properties.getProperty("dbType");
    }
    
    /**
     * 根据page对象获取对应的分页查询Sql语句
     *
     * @param page 分页对象
     * @param sql 原sql语句
     * @return 分页的sql语句
     */
    private String getPageSql(PageVO page, String sql)
    {
        StringBuffer sqlBuffer = new StringBuffer(sql);
        
        if ("mysql".equalsIgnoreCase(dbType))
        {
            return getMysqlPageSql(page, sqlBuffer);
        }
        else
        {
            LOG.info("unknow dbType:{}", dbType);
        }
        
        return sqlBuffer.toString();
    }
    
    /**
     * 获取Mysql数据库的分页查询语句
     * @param page 分页对象
     * @param sqlBuffer 包含原sql语句的StringBuffer对象
     * @return Mysql数据库分页语句
     */
    private String getMysqlPageSql(PageVO page, StringBuffer sqlBuffer)
    {
        // 查询多少条（默认10）
        int pageSize = null == page.getPageSize() ? NumberKeys.NUM_10 : page.getPageSize();
        // 起始位置（默认0）
        int offset = null == page.getPageNum() ? NumberKeys.NUM_0 : ((page.getPageNum() - NumberKeys.NUM_1) * pageSize);
        
        sqlBuffer.append(" limit ").append(offset).append(',').append(pageSize);
        
        return sqlBuffer.toString();
    }
    
    /**
     * 获取当前的参数对象查询的总记录数
     * <功能详细描述>
     * @param boundSql 原sql语句
     * @param paramObj 查询条件参数
     * @param mappedStatement Mapper映射语句
     * @param connection 当前的数据库连接
     * @return int 当前条件下的总数
     * @see [类、类#方法、类#成员]
     */
    private int getTotal(BoundSql boundSql, Object paramObj, MappedStatement mappedStatement, Connection connection)
    {
        // 原本sql语句
        String sql = boundSql.getSql();
        // 解析后得到查询总数的sql语句
        String countSql = getCountSql(sql);
        
        // 获取参数列表
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql =
            new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, paramObj);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, paramObj, countBoundSql);
        PreparedStatement pstmt = null;
        // 结果集
        ResultSet rs = null;
        // 总条数
        int total = 0;
        try
        {
            pstmt = connection.prepareStatement(countSql);
            // 赋值参数
            parameterHandler.setParameters(pstmt);
            // 执行查询
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                total = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            LOG.error(e.getMessage());
        }
        finally
        {
            closeResources(pstmt, rs);
        }
        return total;
    }
    
    /**
     * 解析sql对应的count计数sql
     * <功能详细描述>
     * @param sql sql语句
     * @return count计数sql
     * @see [类、类#方法、类#成员]
     */
    private String getCountSql(String sql)
    {
        try
        {
            long start = System.currentTimeMillis();
            // 退出循环标识
            boolean timeoutFlag = true;
            String tmpSql = sql;
            while (timeoutFlag)
            {
                // 设置循环计算不能超过3s
                if (System.currentTimeMillis() - start > NumberKeys.NUM_3000)
                {
                    timeoutFlag = false;
                }
                int fromIndex = tmpSql.toLowerCase().lastIndexOf(FROM);
                tmpSql = tmpSql.substring(0, fromIndex);
                
                // 左括号计数
                int leftNum = 0;
                // 右括号计数
                int rightNum = 0;
                char[] arr = tmpSql.toCharArray();
                for (char c : arr)
                {
                    if (c == '(')
                    {
                        leftNum++;
                    }
                    if (c == ')')
                    {
                        rightNum++;
                    }
                }
                if (leftNum == rightNum)
                {
                    break;
                }
            }
            
            if (timeoutFlag)
            {
                int selectIndex = tmpSql.toLowerCase().indexOf(SELECT) + SELECT.length();
                tmpSql = tmpSql.substring(selectIndex);
                
                String countSql = sql.replace(tmpSql, " 1 ");
                
                return "select count(1) from (" + countSql + ") tmp";
            }
            else
            {
                // 计算超时
                return "select count(1) from (" + sql + ") tmp";
            }
        }
        catch (Exception e)
        {
            return "select count(1) from (" + sql + ") tmp";
        }
    }
    
    /**
     * 关闭流 + GC
     * <功能详细描述>
     * @param pstmt 预处理
     * @param rs 结果集
     * @see [类、类#方法、类#成员]
     */
    private void closeResources(PreparedStatement pstmt, ResultSet rs)
    {
        try
        {
            if (null != rs)
            {
                rs.close();
            }
            if (null != pstmt)
            {
                pstmt.close();
            }
        }
        catch (SQLException e)
        {
            LOG.error(e.getMessage());
        }
        finally
        {
            rs = null;
            pstmt = null;
        }
    }
}
