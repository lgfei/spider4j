package com.lgfei.tool.spider.operate.mvcrawler.dao;

import java.util.Collection;

import org.apache.ibatis.annotations.Mapper;

import com.lgfei.tool.spider.common.dao.BaseDao;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskRuleVO;

@Mapper
public interface TaskRuleDao extends BaseDao<TaskRuleVO>
{
    int deleteByTaskIds(Collection<String> ids);
}
