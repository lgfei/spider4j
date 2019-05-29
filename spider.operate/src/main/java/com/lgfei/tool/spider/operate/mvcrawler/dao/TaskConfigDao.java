package com.lgfei.tool.spider.operate.mvcrawler.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lgfei.tool.spider.common.dao.BaseDao;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskConfigVO;

@Mapper
public interface TaskConfigDao extends BaseDao<TaskConfigVO>
{
    void updateRunStatus(TaskConfigVO vo);
    
    void initRunStatus();
    
    List<TaskConfigVO> queryListByWebsitIds(Collection<String> ids);
}
