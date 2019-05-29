package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteTaskRuleReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskRuleService;

public class DeleteTaskRuleAction extends AbstractMvCrawlerAction<BaseRequest, DeleteTaskRuleReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(DeleteTaskRuleAction.class);
    
    public DeleteTaskRuleAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, DeleteTaskRuleReqData data, BaseResponse resp)
    {
        LOG.info("begin DeleteTaskRule");
        TaskRuleService service = SpringContextUtil.getBean(TaskRuleService.class);
        try
        {
            // 删除数据
            service.batchDelete(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end DeleteTaskRule");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        DeleteTaskRuleReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
