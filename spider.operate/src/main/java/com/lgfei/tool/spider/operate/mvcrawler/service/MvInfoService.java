package com.lgfei.tool.spider.operate.mvcrawler.service;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteMvReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryMvPageListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateMvReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvInfoVO;

public interface MvInfoService
{
    void insert(MvInfoVO vo)
        throws SystemException;
    
    void findPageList(QueryMvPageListReqData data, PageResultResponse<MvInfoVO> resp)
        throws SystemException;
    
    void batchDelete(DeleteMvReqData data, BaseResponse resp)
        throws SystemException;
    
    void batchUpdate(UpdateMvReqData data, BaseResponse resp)
        throws SystemException;
}
