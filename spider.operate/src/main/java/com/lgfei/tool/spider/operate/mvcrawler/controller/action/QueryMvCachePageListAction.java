package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.common.util.SpringContextUtil;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryMvPageListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvInfoVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvCacheService;

public class QueryMvCachePageListAction
    extends AbstractMvCrawlerAction<BaseRequest, QueryMvPageListReqData, PageResultResponse<MvInfoVO>>
{
    private static final Logger LOG = LoggerFactory.getLogger(QueryMvCachePageListAction.class);
    
    public QueryMvCachePageListAction(String actionName)
    {
        super(actionName);
    }
    
    @Override
    protected void action(BaseRequest req, QueryMvPageListReqData data, PageResultResponse<MvInfoVO> resp)
    {
        LOG.info("begin QueryMvCacheList");
        MvCacheService service = SpringContextUtil.getBean(MvCacheService.class);
        try
        {
            service.findPageList(data, resp);
        }
        catch (SystemException e)
        {
            resp.setRetCode(e.getErrCode());
            resp.setRetMsg(e.getErrMsg());
        }
        LOG.info("end QueryMvCacheList");
    }
    
    @Override
    public void doOthers(HttpServletRequest request, HttpServletResponse response, BaseRequest req,
        QueryMvPageListReqData data, PageResultResponse<MvInfoVO> resp)
    {
        // TODO Auto-generated method stub
        
    }
    
}
