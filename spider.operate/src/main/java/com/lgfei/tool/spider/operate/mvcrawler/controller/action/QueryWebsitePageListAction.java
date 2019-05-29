package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryWebsitePageListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.WebsiteVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.WebsiteService;

public class QueryWebsitePageListAction
    extends AbstractMvCrawlerAction<BaseRequest, QueryWebsitePageListReqData, PageResultResponse<WebsiteVO>>
{
    private static final Logger LOG = LoggerFactory.getLogger(QueryWebsitePageListAction.class);
    
    public QueryWebsitePageListAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, QueryWebsitePageListReqData data, PageResultResponse<WebsiteVO> resp)
    {
        LOG.info("begin QueryWebsitePageList");
        WebsiteService service = SpringContextUtil.getBean(WebsiteService.class);
        try
        {
            service.findPageList(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end QueryWebsitePageList");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        QueryWebsitePageListReqData data, PageResultResponse<WebsiteVO> resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
