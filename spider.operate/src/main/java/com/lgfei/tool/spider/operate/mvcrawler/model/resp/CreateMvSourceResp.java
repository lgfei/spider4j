package com.lgfei.tool.spider.operate.mvcrawler.model.resp;

import com.lgfei.tool.spider.common.message.response.BaseResponse;

public class CreateMvSourceResp extends BaseResponse
{
    /**
     * 新生成的片源id
     */
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
