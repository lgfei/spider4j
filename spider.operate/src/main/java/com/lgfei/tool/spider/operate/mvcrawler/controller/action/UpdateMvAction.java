package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateMvReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvInfoService;

public class UpdateMvAction extends AbstractMvCrawlerAction<BaseRequest, UpdateMvReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(UpdateMvAction.class);
    
    public UpdateMvAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, UpdateMvReqData data, BaseResponse resp)
    {
        LOG.info("begin UpdateMv");
        MvInfoService service = SpringContextUtil.getBean(MvInfoService.class);
        try
        {
            // 更新数据
            service.batchUpdate(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end UpdateMv");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        UpdateMvReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
