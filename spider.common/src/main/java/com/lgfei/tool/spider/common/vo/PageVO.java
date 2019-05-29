package com.lgfei.tool.spider.common.vo;

/**
 * 分页查询实体类
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2017年6月26日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PageVO
{
    /**
     * 页码数
     */
    private Integer pageNum;
    
    /**
     * 每页条数
     */
    private Integer pageSize;
    
    /**
     * 当前查询的总条数
     */
    private int total;
    
    public Integer getPageNum()
    {
        return pageNum;
    }
    
    public void setPageNum(Integer pageNum)
    {
        this.pageNum = pageNum;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getTotal()
    {
        return total;
    }
    
    public void setTotal(int total)
    {
        this.total = total;
    }
    
}
