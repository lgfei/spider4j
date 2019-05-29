package com.lgfei.tool.spider.operate.mvcrawler.service;

import java.util.Collection;
import java.util.List;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.CreateMvSourceReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteMvSourceReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryMvSourcesReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateMvSourceReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.resp.CreateMvSourceResp;
import com.lgfei.tool.spider.operate.mvcrawler.model.resp.QueryMvSourcesResp;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvSourceVO;

public interface MvSourceService
{
    void batchInsert(List<MvSourceVO> voList)
        throws SystemException;
    
    List<MvSourceVO> findList(MvSourceVO vo)
        throws SystemException;
    
    void findByMvId(QueryMvSourcesReqData data, QueryMvSourcesResp resp)
        throws SystemException;
    
    void deleteByMvIds(Collection<String> mvIds)
        throws SystemException;
    
    void deleteById(DeleteMvSourceReqData data, BaseResponse resp)
        throws SystemException;
    
    void updateMvSource(UpdateMvSourceReqData data, BaseResponse resp)
        throws SystemException;
    
    void createMvSource(CreateMvSourceReqData data, CreateMvSourceResp resp)
        throws SystemException;
}
