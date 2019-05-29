package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateTaskRuleEnableFlagReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskRuleService;

public class UpdateTaskRuleEnableFlagAction
    extends AbstractMvCrawlerAction<BaseRequest, UpdateTaskRuleEnableFlagReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(UpdateTaskRuleEnableFlagAction.class);
    
    public UpdateTaskRuleEnableFlagAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, UpdateTaskRuleEnableFlagReqData data, BaseResponse resp)
    {
        LOG.info("begin UpdateTaskRuleEnableFlag");
        TaskRuleService service = SpringContextUtil.getBean(TaskRuleService.class);
        try
        {
            // 更新数据
            service.updateEnableFlag(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end UpdateTaskRuleEnableFlag");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        UpdateTaskRuleEnableFlagReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
