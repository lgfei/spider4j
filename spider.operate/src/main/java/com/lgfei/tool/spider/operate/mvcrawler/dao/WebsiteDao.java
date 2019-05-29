package com.lgfei.tool.spider.operate.mvcrawler.dao;

import org.apache.ibatis.annotations.Mapper;

import com.lgfei.tool.spider.common.dao.BaseDao;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.WebsiteVO;

@Mapper
public interface WebsiteDao extends BaseDao<WebsiteVO>
{
}
