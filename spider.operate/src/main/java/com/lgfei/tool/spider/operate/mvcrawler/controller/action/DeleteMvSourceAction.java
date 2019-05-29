package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteMvSourceReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvSourceService;

public class DeleteMvSourceAction extends AbstractMvCrawlerAction<BaseRequest, DeleteMvSourceReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(DeleteMvSourceAction.class);
    
    public DeleteMvSourceAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, DeleteMvSourceReqData data, BaseResponse resp)
    {
        LOG.info("begin DeleteMvSource");
        MvSourceService service = SpringContextUtil.getBean(MvSourceService.class);
        try
        {
            // 删除数据
            service.deleteById(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end DeleteMvSource");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        DeleteMvSourceReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
