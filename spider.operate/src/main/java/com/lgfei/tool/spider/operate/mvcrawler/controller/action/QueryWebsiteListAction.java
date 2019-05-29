package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.ListResultResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryWebsiteListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.WebsiteVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.WebsiteService;

public class QueryWebsiteListAction
    extends AbstractMvCrawlerAction<BaseRequest, QueryWebsiteListReqData, ListResultResponse<WebsiteVO>>
{
    private static final Logger LOG = LoggerFactory.getLogger(QueryWebsiteListAction.class);
    
    public QueryWebsiteListAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, QueryWebsiteListReqData data, ListResultResponse<WebsiteVO> resp)
    {
        LOG.info("begin QueryWebsiteList");
        WebsiteService service = SpringContextUtil.getBean(WebsiteService.class);
        try
        {
            service.findList(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end QueryWebsiteList");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        QueryWebsiteListReqData data, ListResultResponse<WebsiteVO> resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
