package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import java.util.List;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskRuleVO;

public class DeleteTaskRuleReqData extends BaseRequestData
{
    private List<TaskRuleVO> taskRules;
    
    public List<TaskRuleVO> getTaskRules()
    {
        return taskRules;
    }
    
    public void setTaskRules(List<TaskRuleVO> taskRules)
    {
        this.taskRules = taskRules;
    }
    
}
