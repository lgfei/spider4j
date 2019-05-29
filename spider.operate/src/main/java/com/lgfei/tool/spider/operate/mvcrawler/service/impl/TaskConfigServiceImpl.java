package com.lgfei.tool.spider.operate.mvcrawler.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.lgfei.tool.spider.common.enums.ResultCode;
import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.ListResultResponse;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.common.util.CreateIDUtil;
import com.lgfei.tool.spider.common.vo.PageVO;
import com.lgfei.tool.spider.operate.mvcrawler.dao.TaskConfigDao;
import com.lgfei.tool.spider.operate.mvcrawler.enums.RunStatusEnum;
import com.lgfei.tool.spider.operate.mvcrawler.enums.TablesEnum;
import com.lgfei.tool.spider.operate.mvcrawler.enums.YesOrNoEnum;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.CloneTaskReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.CreateTaskConfigReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteTaskConfigReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryTaskConfigListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.StartTaskReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.StopTaskReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateTaskConfigReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskConfigVO;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskRuleVO;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.WebsiteVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskConfigService;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskRuleService;
import com.lgfei.tool.spider.operate.mvcrawler.service.WebsiteService;
import com.lgfei.tool.spider.webmagic.Crawler;

@Service
public class TaskConfigServiceImpl implements TaskConfigService
{
    private static final Logger LOG = LoggerFactory.getLogger(TaskConfigServiceImpl.class);
    
    @Autowired
    private TaskConfigDao taskConfigDao;
    
    @Autowired
    private TaskRuleService taskRuleService;
    
    @Autowired
    private WebsiteService websiteService;
    
    @Autowired
    private Crawler<TaskConfigVO, TaskRuleVO> crawler;
    
    @Override
    public void init()
        throws SystemException
    {
        taskConfigDao.initRunStatus();
    }
    
    @Override
    public TaskConfigVO findById(String taskId)
        throws SystemException
    {
        TaskConfigVO vo = new TaskConfigVO();
        vo.setTaskId(taskId);
        return taskConfigDao.query(vo);
    }
    
    @Override
    public List<TaskConfigVO> findList(TaskConfigVO vo)
        throws SystemException
    {
        if (null == vo)
        {
            return null;
        }
        return taskConfigDao.queryList(vo);
    }
    
    @Override
    public List<TaskConfigVO> findListByWebsitIds(Collection<String> websiteIds)
    {
        if (CollectionUtils.isEmpty(websiteIds))
        {
            return null;
        }
        return taskConfigDao.queryListByWebsitIds(websiteIds);
    }
    
    @Override
    public void findPageList(QueryTaskConfigListReqData data, PageResultResponse<TaskConfigVO> resp)
        throws SystemException
    {
        // 构造查询参数
        TaskConfigVO paramVO = new TaskConfigVO();
        paramVO.setTaskName(data.getTaskName());
        PageVO pageVO = new PageVO();
        pageVO.setPageNum(data.getPageNum());
        pageVO.setPageSize(data.getPageSize());
        // 执行查询
        List<TaskConfigVO> list = taskConfigDao.queryPageList(paramVO, pageVO);
        // 补充网站名称
        if (!CollectionUtils.isEmpty(list))
        {
            for (TaskConfigVO taskConfigVO : list)
            {
                WebsiteVO website = websiteService.findById(taskConfigVO.getWebsiteId());
                if (null != website)
                {
                    taskConfigVO.setWebsiteName(website.getName());
                }
            }
        }
        
        resp.setTotal(pageVO.getTotal());
        resp.setRows(list);
    }
    
    @Override
    public void create(CreateTaskConfigReqData data, BaseResponse resp)
        throws SystemException
    {
        TaskConfigVO vo = data.getTaskConfig();
        if (null == vo)
        {
            return;
        }
        
        // 新增
        vo.setTaskId(CreateIDUtil.businessID(TablesEnum.T_TASK_CONFIG.getModule()));
        taskConfigDao.insert(vo);
    }
    
    @Override
    public void update(UpdateTaskConfigReqData data, BaseResponse resp)
        throws SystemException
    {
        TaskConfigVO vo = data.getTaskConfig();
        if (null == vo)
        {
            return;
        }
        
        // 修改
        taskConfigDao.update(vo);
    }
    
    @Override
    public void batchDelete(DeleteTaskConfigReqData data, BaseResponse resp)
        throws SystemException
    {
        batchDelete(data.getTaskConfigs());
    }
    
    @Override
    public void batchDelete(List<TaskConfigVO> voList)
        throws SystemException
    {
        if (CollectionUtils.isEmpty(voList))
        {
            return;
        }
        Set<String> taskIds = new HashSet<>();
        for (TaskConfigVO task : voList)
        {
            taskIds.add(task.getTaskId());
        }
        // 删规则表
        taskRuleService.deleteByTaskIds(taskIds);
        // 删任务表
        taskConfigDao.batchDelete(voList);
    }
    
    @Override
    public void start(StartTaskReqData data, BaseResponse resp)
        throws SystemException
    {
        String taskId = data.getTaskId();
        if (StringUtils.isEmpty(taskId))
        {
            return;
        }
        
        TaskConfigVO task = findById(taskId);
        if (null == task)
        {
            return;
        }
        // 获取规则配置
        TaskRuleVO ruleParm = new TaskRuleVO();
        ruleParm.setTaskId(task.getTaskId());
        ruleParm.setEnableFlag(YesOrNoEnum.YES.getValue());
        ListResultResponse<TaskRuleVO> result = taskRuleService.findList(ruleParm);
        List<TaskRuleVO> tempList = result.getData();
        if (CollectionUtils.isEmpty(tempList))
        {
            LOG.info("缺少字段规则");
            throw new SystemException(ResultCode.INCOMPLETE_DATA.getRetCode(), ResultCode.INCOMPLETE_DATA.getRetMsg());
        }
        // 更改状态
        task.setRunStatus(RunStatusEnum.STARTED.getValue());
        taskConfigDao.updateRunStatus(task);
        // 过滤掉被禁用的规则
        List<TaskRuleVO> rules = new ArrayList<>();
        for (TaskRuleVO taskRule : tempList)
        {
            if (YesOrNoEnum.YES.getValue().equals(taskRule.getEnableFlag()))
            {
                rules.add(taskRule);
            }
        }
        // 运行爬虫
        crawler.execRun(task, rules);
        LOG.info(task.getTaskName() + "正在运行");
        // 修改状态为正在运行
        task.setRunStatus(RunStatusEnum.RUNNING.getValue());
        taskConfigDao.updateRunStatus(task);
    }
    
    @Override
    public void stop(StopTaskReqData data, BaseResponse resp)
        throws SystemException
    {
        String taskId = data.getTaskId();
        if (StringUtils.isEmpty(taskId))
        {
            return;
        }
        TaskConfigVO task = findById(taskId);
        if (null == task)
        {
            return;
        }
        // 更改状态
        task.setRunStatus(RunStatusEnum.STOPPING.getValue());
        taskConfigDao.updateRunStatus(task);
        // 停止爬虫
        crawler.execStop(task.getTaskId());
        LOG.info(task.getTaskName() + "正在停止");
        // 修改状态为已停止
        task.setRunStatus(RunStatusEnum.STOPED.getValue());
        taskConfigDao.updateRunStatus(task);
    }
    
    @Override
    public void clone(CloneTaskReqData data, BaseResponse resp)
        throws SystemException
    {
        String taskId = data.getTaskId();
        if (StringUtils.isEmpty(taskId))
        {
            LOG.info("taskId为空");
            return;
        }
        
        // 获取原taskconfig数据
        TaskConfigVO taskConfigVo = new TaskConfigVO();
        taskConfigVo.setTaskId(taskId);
        taskConfigVo = taskConfigDao.query(taskConfigVo);
        
        if (null == taskConfigVo)
        {
            LOG.info("找不到对应的任务，taskId:{}", taskId);
            return;
        }
        
        // 生成新的taskconfig
        String cloneTaskId = CreateIDUtil.businessID(TablesEnum.T_TASK_CONFIG.getModule());
        String cloneTaskName = taskConfigVo.getTaskName() + "-copy";
        
        taskConfigVo.setTaskId(cloneTaskId);
        taskConfigVo.setTaskName(cloneTaskName);
        
        taskConfigDao.insert(taskConfigVo);
        
        // 获取原taskrule数据
        TaskRuleVO paramTaskRuleVo = new TaskRuleVO();
        paramTaskRuleVo.setTaskId(taskId);
        ListResultResponse<TaskRuleVO> rulesResp = taskRuleService.findList(paramTaskRuleVo);
        List<TaskRuleVO> rules = rulesResp.getData();
        
        // 生成新的taskrule
        if (CollectionUtils.isEmpty(rules))
        {
            LOG.info("没有找到原任务规则");
            return;
        }
        for (TaskRuleVO taskRuleVO : rules)
        {
            taskRuleVO.setTaskId(cloneTaskId);
        }
        taskRuleService.batchInsert(rules);
    }
}
