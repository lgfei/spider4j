package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import java.util.List;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskConfigVO;

public class DeleteTaskConfigReqData extends BaseRequestData
{
    private List<TaskConfigVO> taskConfigs;
    
    public List<TaskConfigVO> getTaskConfigs()
    {
        return taskConfigs;
    }
    
    public void setTaskConfigs(List<TaskConfigVO> taskConfigs)
    {
        this.taskConfigs = taskConfigs;
    }
    
}
