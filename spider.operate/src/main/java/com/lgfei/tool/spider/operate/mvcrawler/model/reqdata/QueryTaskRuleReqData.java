package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;

public class QueryTaskRuleReqData extends BaseRequestData
{
    /**
     * 任务id
     */
    private String taskId;
    
    /**
     * 可用状态
     */
    private Integer enableFlag;
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public Integer getEnableFlag()
    {
        return enableFlag;
    }
    
    public void setEnableFlag(Integer enableFlag)
    {
        this.enableFlag = enableFlag;
    }
    
}
