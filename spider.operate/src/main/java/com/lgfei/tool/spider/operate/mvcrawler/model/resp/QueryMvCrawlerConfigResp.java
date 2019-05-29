package com.lgfei.tool.spider.operate.mvcrawler.model.resp;

import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.operate.mvcrawler.model.MvCrawlerConfig;

public class QueryMvCrawlerConfigResp extends BaseResponse
{
    private MvCrawlerConfig mvCrawlerConfig;
    
    public MvCrawlerConfig getMvCrawlerConfig()
    {
        return mvCrawlerConfig;
    }
    
    public void setMvCrawlerConfig(MvCrawlerConfig mvCrawlerConfig)
    {
        this.mvCrawlerConfig = mvCrawlerConfig;
    }
}
