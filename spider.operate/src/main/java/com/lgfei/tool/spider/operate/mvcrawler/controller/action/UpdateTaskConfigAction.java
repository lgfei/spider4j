package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateTaskConfigReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskConfigService;

public class UpdateTaskConfigAction extends AbstractMvCrawlerAction<BaseRequest, UpdateTaskConfigReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(UpdateTaskConfigAction.class);
    
    public UpdateTaskConfigAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, UpdateTaskConfigReqData data, BaseResponse resp)
    {
        LOG.info("begin UpdateTaskConfig");
        TaskConfigService service = SpringContextUtil.getBean(TaskConfigService.class);
        try
        {
            // 保存数据
            service.update(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end UpdateTaskConfig");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        UpdateTaskConfigReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
