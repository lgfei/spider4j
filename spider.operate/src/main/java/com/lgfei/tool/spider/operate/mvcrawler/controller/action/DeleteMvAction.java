package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteMvReqData;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvInfoService;

public class DeleteMvAction extends AbstractMvCrawlerAction<BaseRequest, DeleteMvReqData, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(DeleteMvAction.class);
    
    public DeleteMvAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, DeleteMvReqData data, BaseResponse resp)
    {
        LOG.info("begin DeleteMv");
        MvInfoService service = SpringContextUtil.getBean(MvInfoService.class);
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
        LOG.info("end DeleteMv");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        DeleteMvReqData data, BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
