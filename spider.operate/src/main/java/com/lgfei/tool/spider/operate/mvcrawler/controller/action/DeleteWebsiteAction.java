package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteWebsiteReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.WebsiteService;

public class DeleteWebsiteAction extends AbstractMvCrawlerAction<BaseRequest, DeleteWebsiteReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(DeleteWebsiteAction.class);
    
    public DeleteWebsiteAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, DeleteWebsiteReqData data, BaseResponse resp)
    {
        LOG.info("begin DeleteWebsite");
        WebsiteService service = SpringContextUtil.getBean(WebsiteService.class);
        try
        {
            // 删除数据
            service.batchDelete(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end DeleteWebsite");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        DeleteWebsiteReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
