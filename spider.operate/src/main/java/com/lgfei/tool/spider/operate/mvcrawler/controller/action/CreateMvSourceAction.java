package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.CreateMvSourceReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.resp.CreateMvSourceResp;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvSourceService;

public class CreateMvSourceAction
    extends AbstractMvCrawlerAction<BaseRequest, CreateMvSourceReqData, CreateMvSourceResp>
{
    private static final Logger LOG = LoggerFactory.getLogger(CreateMvSourceAction.class);
    
    public CreateMvSourceAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, CreateMvSourceReqData data, CreateMvSourceResp resp)
    {
        LOG.info("begin CreateMvSource");
        MvSourceService service = SpringContextUtil.getBean(MvSourceService.class);
        try
        {
            // 插入数据
            service.createMvSource(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end CreateMvSource");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        CreateMvSourceReqData data, CreateMvSourceResp resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
