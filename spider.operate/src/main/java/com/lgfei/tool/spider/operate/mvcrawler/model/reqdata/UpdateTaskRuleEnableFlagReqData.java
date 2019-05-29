package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;

public class UpdateTaskRuleEnableFlagReqData extends BaseRequestData
{
    /**
     * 主键
     */
    private Long id;
    
    /**
     * 可用标识，0-不可用，1-可用
     */
    private Integer enableFlag;
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
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
