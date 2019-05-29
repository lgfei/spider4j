package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskConfigVO;

public class UpdateTaskConfigReqData extends BaseRequestData
{
    /**
     * 任务实体
     */
    private TaskConfigVO taskConfig;
    
    public TaskConfigVO getTaskConfig()
    {
        return taskConfig;
    }
    
    public void setTaskConfig(TaskConfigVO taskConfig)
    {
        this.taskConfig = taskConfig;
    }
    
}
