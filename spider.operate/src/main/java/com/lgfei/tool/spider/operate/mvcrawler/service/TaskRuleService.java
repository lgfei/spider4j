package com.lgfei.tool.spider.operate.mvcrawler.service;

import java.util.Collection;
import java.util.List;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BatchGridJson;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.ListResultResponse;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteTaskRuleReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryTaskRuleReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateTaskRuleEnableFlagReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskRuleVO;

public interface TaskRuleService
{
    void findList(QueryTaskRuleReqData data, ListResultResponse<TaskRuleVO> resp)
        throws SystemException;
    
    ListResultResponse<TaskRuleVO> findList(TaskRuleVO vo)
        throws SystemException;
    
    void batchInsert(List<TaskRuleVO> voList)
        throws SystemException;
    
    void batchSave(BatchGridJson json, BaseResponse resp)
        throws SystemException;
    
    void batchDelete(DeleteTaskRuleReqData data, BaseResponse resp)
        throws SystemException;
    
    void updateEnableFlag(UpdateTaskRuleEnableFlagReqData data, BaseResponse resp)
        throws SystemException;
    
    void deleteByTaskIds(Collection<String> taskIds)
        throws SystemException;
}
