package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteTaskConfigReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskConfigService;

public class DeleteTaskConfigAction extends AbstractMvCrawlerAction<BaseRequest, DeleteTaskConfigReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(DeleteTaskConfigAction.class);
    
    public DeleteTaskConfigAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, DeleteTaskConfigReqData data, BaseResponse resp)
    {
        LOG.info("begin DeleteTaskConfig");
        TaskConfigService service = SpringContextUtil.getBean(TaskConfigService.class);
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
        LOG.info("end DeleteTaskConfig");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        DeleteTaskConfigReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
