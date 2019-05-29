package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.StopTaskReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskConfigService;

public class StopTaskAction extends AbstractMvCrawlerAction<BaseRequest, StopTaskReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(StopTaskAction.class);
    
    public StopTaskAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, StopTaskReqData data, BaseResponse resp)
    {
        LOG.info("begin StopTask");
        TaskConfigService service = SpringContextUtil.getBean(TaskConfigService.class);
        try
        {
            // 停止爬虫
            service.stop(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end StopTask");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        StopTaskReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
