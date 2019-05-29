package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;

public class QueryTaskConfigListReqData extends BaseRequestData
{
    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * 页码数
     */
    private Integer pageNum;
    
    /**
     * 每页条数
     */
    private Integer pageSize;
    
    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }
    
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
    
}
