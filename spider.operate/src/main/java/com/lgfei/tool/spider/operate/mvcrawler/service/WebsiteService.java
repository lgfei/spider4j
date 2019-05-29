package com.lgfei.tool.spider.operate.mvcrawler.service;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BatchGridJson;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.ListResultResponse;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteWebsiteReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryWebsiteListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryWebsitePageListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.WebsiteVO;

public interface WebsiteService
{
    WebsiteVO findById(String websiteId)
        throws SystemException;
    
    void findList(QueryWebsiteListReqData data, ListResultResponse<WebsiteVO> resp)
        throws SystemException;
    
    void findPageList(QueryWebsitePageListReqData data, PageResultResponse<WebsiteVO> resp)
        throws SystemException;
    
    void batchSave(BatchGridJson json, BaseResponse resp)
        throws SystemException;
    
    void batchDelete(DeleteWebsiteReqData data, BaseResponse resp)
        throws SystemException;
}
