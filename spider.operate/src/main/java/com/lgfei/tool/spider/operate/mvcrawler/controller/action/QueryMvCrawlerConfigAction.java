package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.request.BaseRequestData;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.resp.QueryMvCrawlerConfigResp;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvCrawlerConfigService;

public class QueryMvCrawlerConfigAction
    extends AbstractMvCrawlerAction<BaseRequest, BaseRequestData, QueryMvCrawlerConfigResp>
{
    private static final Logger LOG = LoggerFactory.getLogger(QueryMvCrawlerConfigAction.class);
    
    public QueryMvCrawlerConfigAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, BaseRequestData data, QueryMvCrawlerConfigResp resp)
    {
        LOG.info("begin QueryMvCrawlerConfig");
        MvCrawlerConfigService service = SpringContextUtil.getBean(MvCrawlerConfigService.class);
        service.queryAll(resp);
        LOG.info("end QueryMvCrawlerConfig");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        BaseRequestData data, QueryMvCrawlerConfigResp resp)
    {
        // TODO Auto-generated method stub
        
    }
}
