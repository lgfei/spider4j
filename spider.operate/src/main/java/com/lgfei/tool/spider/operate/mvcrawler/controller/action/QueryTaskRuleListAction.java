package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.ListResultResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryTaskRuleReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskRuleVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskRuleService;

public class QueryTaskRuleListAction
    extends AbstractMvCrawlerAction<BaseRequest, QueryTaskRuleReqData, ListResultResponse<TaskRuleVO>>
{
    private static final Logger LOG = LoggerFactory.getLogger(QueryTaskRuleListAction.class);
    
    public QueryTaskRuleListAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, QueryTaskRuleReqData data, ListResultResponse<TaskRuleVO> resp)
    {
        LOG.info("begin QueryTaskRuleList");
        TaskRuleService service = SpringContextUtil.getBean(TaskRuleService.class);
        try
        {
            service.findList(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end QueryTaskRuleList");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        QueryTaskRuleReqData data, ListResultResponse<TaskRuleVO> resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
