package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;

public class QueryMvSourcesReqData extends BaseRequestData
{
    /**
     * 影片id
     */
    private String mvId;
    
    public String getMvId()
    {
        return mvId;
    }
    
    public void setMvId(String mvId)
    {
        this.mvId = mvId;
    }
    
}
