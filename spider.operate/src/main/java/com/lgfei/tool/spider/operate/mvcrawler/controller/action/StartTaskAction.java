package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.StartTaskReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskConfigService;

public class StartTaskAction extends AbstractMvCrawlerAction<BaseRequest, StartTaskReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(StartTaskAction.class);
    
    public StartTaskAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, StartTaskReqData data, BaseResponse resp)
    {
        LOG.info("begin StartTask");
        TaskConfigService service = SpringContextUtil.getBean(TaskConfigService.class);
        try
        {
            // 启动爬虫
            service.start(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end StartTask");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        StartTaskReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
