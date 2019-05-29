package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import java.util.List;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;

public class SyncMvCacheReqData extends BaseRequestData
{
    private List<String> mvIds;
    
    public List<String> getMvIds()
    {
        return mvIds;
    }
    
    public void setMvIds(List<String> mvIds)
    {
        this.mvIds = mvIds;
    }
    
}
