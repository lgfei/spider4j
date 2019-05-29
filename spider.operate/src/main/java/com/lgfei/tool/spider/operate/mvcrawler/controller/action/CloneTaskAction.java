package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.CloneTaskReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskConfigService;

public class CloneTaskAction extends AbstractMvCrawlerAction<BaseRequest, CloneTaskReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(CloneTaskAction.class);
    
    public CloneTaskAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, CloneTaskReqData data, BaseResponse resp)
    {
        LOG.info("begin CloneTask");
        TaskConfigService service = SpringContextUtil.getBean(TaskConfigService.class);
        try
        {
            // 复制数据
            service.clone(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end CloneTask");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        CloneTaskReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
