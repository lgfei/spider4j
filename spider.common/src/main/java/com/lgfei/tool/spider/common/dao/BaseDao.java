package com.lgfei.tool.spider.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lgfei.tool.spider.common.vo.PageVO;

public interface BaseDao<VO>
{
    VO query(VO vo);
    
    List<VO> queryList(VO vo);
    
    List<VO> queryPageList(@Param("param") VO vo, @Param("page") PageVO pageVO);
    
    int insert(VO vo);
    
    int batchInsert(List<VO> voList);
    
    int update(VO vo);
    
    int batchUpdate(List<VO> voList);
    
    int delete(VO vo);
    
    int batchDelete(List<VO> voList);
    
    void updateEnableFlag(VO vo);
}
