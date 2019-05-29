package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;

public class StartTaskReqData extends BaseRequestData
{
    /**
     * 任务Id
     */
    private String taskId;
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
}
