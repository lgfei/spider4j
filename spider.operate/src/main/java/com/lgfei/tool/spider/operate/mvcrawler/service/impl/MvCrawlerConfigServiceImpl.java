package com.lgfei.tool.spider.operate.mvcrawler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgfei.tool.spider.operate.mvcrawler.model.MvCrawlerConfig;
import com.lgfei.tool.spider.operate.mvcrawler.model.resp.QueryMvCrawlerConfigResp;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvCrawlerConfigService;

@Service
public class MvCrawlerConfigServiceImpl implements MvCrawlerConfigService
{
    @Autowired
    private MvCrawlerConfig config;
    
    @Override
    public void queryAll(QueryMvCrawlerConfigResp resp)
    {
        if (null != this.config)
        {
            resp.setMvCrawlerConfig(this.config);
        }
    }
    
}
