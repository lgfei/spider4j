package com.lgfei.tool.spider.operate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgfei.tool.spider.common.exception.InnerException;
import com.lgfei.tool.spider.common.util.DesUtil;
import com.lgfei.tool.spider.common.web.servlet.RequestAcceptorServlet;
import com.lgfei.tool.spider.operate.dispatcher.OperateRequsetAcceptor;
import com.lgfei.tool.spider.operate.listener.MyApplicationEventListener;
import com.lgfei.tool.spider.operate.listener.PropertiesListener;

/**
 * SpringBoot应用启动类
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2017年8月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@EnableConfigurationProperties
@EnableScheduling
@SpringBootApplication
@ComponentScan("com.lgfei.tool.spider")
public class Application
{
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    
    /**
     *  事务超时时间, 120秒
     */
    private static final int TX_METHOD_TIMEOUT = 120;
    
    /**
     * 事务切面
     */
    private static final String AOP_POINTCUT_EXPRESSION =
        "execution (* com.lgfei.tool.spider.operate.*.service.impl.*.*(..))";
    
    @Value("${encrypt.key}")
    private String decryptKey;
    
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public Properties getDBProperies()
    {
        return new Properties();
    }
    
    /** 
     * DataSource配置
     * @return DataSource
     * @see [类、类#方法、类#成员]
     */
    @Bean
    public DataSource dataSource()
    {
        Properties dbPros = getDBProperies();
        try
        {
            // 解密
            dbPros.setProperty("password", DesUtil.decrypt(dbPros.getProperty("password"), decryptKey));
        }
        catch (Exception e)
        {
            LOG.error("解密数据库密码失败:{}", e.getMessage());
            throw new InnerException("解密数据库密码失败");
        }
        PoolProperties pool = JSON.parseObject(JSONObject.toJSONString(dbPros), PoolProperties.class);
        pool.setDbProperties(dbPros);
        return new org.apache.tomcat.jdbc.pool.DataSource(pool);
        
    }
    
    /**
     * 请求分发器
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Bean
    public ServletRegistrationBean servletRegistration()
    {
        ServletRegistrationBean servlet =
            new ServletRegistrationBean(new RequestAcceptorServlet(new OperateRequsetAcceptor()), "*.do");
        servlet.setLoadOnStartup(1);
        return servlet;
    }
    
    /**
     * 事务配置
     * <功能详细描述>
     * @return 事务
     * @see [类、类#方法、类#成员]
     */
    @Bean
    public TransactionInterceptor txAdvice()
    {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(TX_METHOD_TIMEOUT);
        
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("batch*", requiredTx);
        txMap.put("refresh*", requiredTx);
        /*txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("find*", readOnlyTx);*/
        source.setNameMap(txMap);
        TransactionInterceptor txAdvice =
            new TransactionInterceptor(new DataSourceTransactionManager(dataSource()), source);
        return txAdvice;
    }
    
    /**
     * 注册事务切面
     * <功能详细描述>
     * @return 事务点
     * @see [类、类#方法、类#成员]
     */
    @Bean
    public Advisor txAdviceAdvisor()
    {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
    
    /**
     * Spring内置tomcat服务启动入口
     * <功能详细描述>
     * @param args 参数
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args)
    {
        LOG.info("开始启动");
        SpringApplication app = new SpringApplication(Application.class);
        // 添加监听器
        app.addListeners(new MyApplicationEventListener());
        app.addListeners(new PropertiesListener("project-config.properties,mvcrawler-config.properties"));
        // 启动应用
        app.run(args);
        LOG.info("启动成功");
    }
    
}
