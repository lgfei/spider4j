package com.lgfei.tool.spider.operate.mvcrawler.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lgfei.tool.spider.common.dao.BaseDao;
import com.lgfei.tool.spider.common.vo.PageVO;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvInfoVO;

@Mapper
public interface MvInfoDao extends BaseDao<MvInfoVO>
{
    List<MvInfoVO> queryMvPageList(@Param("param") MvInfoVO vo, @Param("page") PageVO pageVO);
}
