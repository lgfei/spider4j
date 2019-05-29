package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.SyncMvCacheReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvCacheService;

public class SyncMvCacheAction extends AbstractMvCrawlerAction<BaseRequest, SyncMvCacheReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(SyncMvCacheAction.class);
    
    public SyncMvCacheAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, SyncMvCacheReqData data, BaseResponse resp)
    {
        LOG.info("begin SyncMvCache");
        MvCacheService service = SpringContextUtil.getBean(MvCacheService.class);
        try
        {
            service.syncMvCache(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end SyncMvCache");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        SyncMvCacheReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
