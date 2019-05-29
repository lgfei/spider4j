package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateMvSourceReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvSourceService;

public class UpdateMvSourceAction extends AbstractMvCrawlerAction<BaseRequest, UpdateMvSourceReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(UpdateMvSourceAction.class);
    
    public UpdateMvSourceAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, UpdateMvSourceReqData data, BaseResponse resp)
    {
        LOG.info("begin UpdateMvSource");
        MvSourceService service = SpringContextUtil.getBean(MvSourceService.class);
        try
        {
            // 更新数据
            service.updateMvSource(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end UpdateMvSource");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        UpdateMvSourceReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
