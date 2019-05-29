package com.lgfei.tool.spider.operate.mvcrawler.service;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteMvCacheReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryMvPageListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.SyncMvCacheReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvInfoVO;

public interface MvCacheService
{
    void findPageList(QueryMvPageListReqData data, PageResultResponse<MvInfoVO> resp)
        throws SystemException;
    
    void deleteMvCache(DeleteMvCacheReqData data, BaseResponse respss)
        throws SystemException;
    
    void syncMvCache(SyncMvCacheReqData data, BaseResponse resp)
        throws SystemException;
}
