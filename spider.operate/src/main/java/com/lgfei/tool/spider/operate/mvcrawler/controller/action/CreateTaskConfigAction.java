package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.CreateTaskConfigReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskConfigService;

public class CreateTaskConfigAction extends AbstractMvCrawlerAction<BaseRequest, CreateTaskConfigReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(CreateTaskConfigAction.class);
    
    public CreateTaskConfigAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, CreateTaskConfigReqData data, BaseResponse resp)
    {
        LOG.info("begin CreateTaskConfig");
        TaskConfigService service = SpringContextUtil.getBean(TaskConfigService.class);
        try
        {
            // 保存数据
            service.create(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end CreateTaskConfig");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        CreateTaskConfigReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
