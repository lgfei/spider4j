package com.lgfei.tool.spider.operate.mvcrawler.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BatchGridJson;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.ListResultResponse;
import com.lgfei.tool.spider.operate.mvcrawler.dao.TaskRuleDao;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteTaskRuleReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryTaskRuleReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateTaskRuleEnableFlagReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskRuleVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskRuleService;

@Service
public class TaskRuleServiceImpl implements TaskRuleService
{
    @Autowired
    private TaskRuleDao taskRuleDao;
    
    @Override
    public void findList(QueryTaskRuleReqData data, ListResultResponse<TaskRuleVO> resp)
        throws SystemException
    {
        TaskRuleVO vo = new TaskRuleVO();
        vo.setTaskId(data.getTaskId());
        vo.setEnableFlag(data.getEnableFlag());
        ListResultResponse<TaskRuleVO> result = findList(vo);
        
        resp.setData(result.getData());
    }
    
    @Override
    public ListResultResponse<TaskRuleVO> findList(TaskRuleVO vo)
        throws SystemException
    {
        ListResultResponse<TaskRuleVO> result = new ListResultResponse<>();
        result.setData(taskRuleDao.queryList(vo));
        return result;
    }
    
    @Override
    public void batchInsert(List<TaskRuleVO> voList)
        throws SystemException
    {
        if (CollectionUtils.isEmpty(voList))
        {
            return;
        }
        taskRuleDao.batchInsert(voList);
    }
    
    @Override
    public void batchSave(BatchGridJson json, BaseResponse resp)
        throws SystemException
    {
        if (null == json)
        {
            return;
        }
        // 新增
        List<TaskRuleVO> inserted = JSON.parseArray(json.getInserted().toJSONString(), TaskRuleVO.class);
        if (!CollectionUtils.isEmpty(inserted))
        {
            taskRuleDao.batchInsert(inserted);
        }
        // 修改
        List<TaskRuleVO> updated = JSON.parseArray(json.getUpdated().toJSONString(), TaskRuleVO.class);
        if (!CollectionUtils.isEmpty(updated))
        {
            taskRuleDao.batchUpdate(updated);
        }
    }
    
    @Override
    public void batchDelete(DeleteTaskRuleReqData data, BaseResponse resp)
        throws SystemException
    {
        List<TaskRuleVO> voList = data.getTaskRules();
        if (CollectionUtils.isEmpty(voList))
        {
            return;
        }
        taskRuleDao.batchDelete(voList);
    }
    
    @Override
    public void updateEnableFlag(UpdateTaskRuleEnableFlagReqData data, BaseResponse resp)
        throws SystemException
    {
        TaskRuleVO vo = new TaskRuleVO();
        vo.setId(data.getId());
        vo.setEnableFlag(data.getEnableFlag());
        taskRuleDao.updateEnableFlag(vo);
    }
    
    @Override
    public void deleteByTaskIds(Collection<String> taskIds)
        throws SystemException
    {
        if (CollectionUtils.isEmpty(taskIds))
        {
            return;
        }
        taskRuleDao.deleteByTaskIds(taskIds);
    }
}
