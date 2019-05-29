package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryMvSourcesReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.resp.QueryMvSourcesResp;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvSourceService;

public class QueryMvSourceListAction
    extends AbstractMvCrawlerAction<BaseRequest, QueryMvSourcesReqData, QueryMvSourcesResp>
{
    private static final Logger LOG = LoggerFactory.getLogger(QueryMvSourceListAction.class);
    
    public QueryMvSourceListAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, QueryMvSourcesReqData data, QueryMvSourcesResp resp)
    {
        LOG.info("begin QueryMvSourceList");
        MvSourceService service = SpringContextUtil.getBean(MvSourceService.class);
        try
        {
            service.findByMvId(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end QueryMvSourceList");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        QueryMvSourcesReqData data, QueryMvSourcesResp resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
