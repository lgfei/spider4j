package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryTaskConfigListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskConfigVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskConfigService;

public class QueryTaskConfigListAction
    extends AbstractMvCrawlerAction<BaseRequest, QueryTaskConfigListReqData, PageResultResponse<TaskConfigVO>>
{
    private static final Logger LOG = LoggerFactory.getLogger(QueryTaskConfigListAction.class);
    
    public QueryTaskConfigListAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, QueryTaskConfigListReqData data, PageResultResponse<TaskConfigVO> resp)
    {
        LOG.info("begin QueryTaskConfigList");
        TaskConfigService service = SpringContextUtil.getBean(TaskConfigService.class);
        try
        {
            service.findPageList(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end QueryTaskConfigList");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        QueryTaskConfigListReqData data, PageResultResponse<TaskConfigVO> resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
