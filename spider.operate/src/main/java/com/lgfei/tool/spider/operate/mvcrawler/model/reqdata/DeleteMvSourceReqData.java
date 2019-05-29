package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;

public class DeleteMvSourceReqData extends BaseRequestData
{
    private String sourceId;
    
    public String getSourceId()
    {
        return sourceId;
    }
    
    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }
    
}
