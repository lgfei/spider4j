package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.request.BatchGridJson;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.service.WebsiteService;

public class SaveWebsiteAction extends AbstractMvCrawlerAction<BaseRequest, BatchGridJson, BaseResponse>
{
    private static final Logger LOG = LoggerFactory.getLogger(SaveWebsiteAction.class);
    
    public SaveWebsiteAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, BatchGridJson data, BaseResponse resp)
    {
        LOG.info("begin SaveWebsite");
        WebsiteService service = SpringContextUtil.getBean(WebsiteService.class);
        try
        {
            // 保存数据
            service.batchSave(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end SaveWebsite");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req, BatchGridJson data,
        BaseResponse resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
