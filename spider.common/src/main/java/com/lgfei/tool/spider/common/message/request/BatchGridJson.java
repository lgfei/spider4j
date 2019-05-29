package com.lgfei.tool.spider.common.message.request;

import com.alibaba.fastjson.JSONArray;

public class BatchGridJson extends BaseRequestData
{
    private JSONArray inserted;
    
    private JSONArray updated;
    
    public JSONArray getInserted()
    {
        return inserted;
    }
    
    public void setInserted(JSONArray inserted)
    {
        this.inserted = inserted;
    }
    
    public JSONArray getUpdated()
    {
        return updated;
    }
    
    public void setUpdated(JSONArray updated)
    {
        this.updated = updated;
    }
    
}
