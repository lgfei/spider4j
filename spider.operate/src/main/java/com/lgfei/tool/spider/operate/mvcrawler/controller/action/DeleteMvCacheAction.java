package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteMvCacheReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvCacheService;

public class DeleteMvCacheAction extends AbstractMvCrawlerAction<BaseRequest, DeleteMvCacheReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(DeleteMvCacheAction.class);
    
    public DeleteMvCacheAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, DeleteMvCacheReqData data, BaseResponse resp)
    {
        LOG.info("begin DeleteMvCache");
        MvCacheService service = SpringContextUtil.getBean(MvCacheService.class);
        try
        {
            service.deleteMvCache(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end DeleteMvCache");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        DeleteMvCacheReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
