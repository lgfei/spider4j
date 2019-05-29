package com.lgfei.tool.spider.operate.mvcrawler.service;

import java.util.Collection;
import java.util.List;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.CloneTaskReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.CreateTaskConfigReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteTaskConfigReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryTaskConfigListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.StartTaskReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.StopTaskReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateTaskConfigReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskConfigVO;

public interface TaskConfigService
{
    void init()
        throws SystemException;
    
    TaskConfigVO findById(String taskId)
        throws SystemException;
    
    List<TaskConfigVO> findList(TaskConfigVO vo)
        throws SystemException;
    
    List<TaskConfigVO> findListByWebsitIds(Collection<String> websiteIds);
    
    void findPageList(QueryTaskConfigListReqData data, PageResultResponse<TaskConfigVO> resp)
        throws SystemException;
    
    void create(CreateTaskConfigReqData data, BaseResponse resp)
        throws SystemException;
    
    void update(UpdateTaskConfigReqData data, BaseResponse resp)
        throws SystemException;
    
    void batchDelete(DeleteTaskConfigReqData data, BaseResponse resp)
        throws SystemException;
    
    void batchDelete(List<TaskConfigVO> voList)
        throws SystemException;
    
    void start(StartTaskReqData data, BaseResponse resp)
        throws SystemException;
    
    void stop(StopTaskReqData data, BaseResponse resp)
        throws SystemException;
    
    void clone(CloneTaskReqData data, BaseResponse resp)
        throws SystemException;
}
