package com.lgfei.tool.spider.common.message.response;

import java.util.List;

/**
 * 管理页面分页查询的结果对象
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年12月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PageResultResponse<VO> extends BaseResponse
{
    /**
     * 总条数
     */
    private int total;
    
    /**
     * 结果集
     */
    private List<VO> rows;
    
    public int getTotal()
    {
        return total;
    }
    
    public void setTotal(int total)
    {
        this.total = total;
    }
    
    public List<VO> getRows()
    {
        return rows;
    }
    
    public void setRows(List<VO> rows)
    {
        this.rows = rows;
    }
    
}
