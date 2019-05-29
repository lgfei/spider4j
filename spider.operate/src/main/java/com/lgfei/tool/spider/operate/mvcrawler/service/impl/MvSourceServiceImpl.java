package com.lgfei.tool.spider.operate.mvcrawler.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.CreateIDUtil;
import com.lgfei.tool.spider.operate.mvcrawler.dao.MvSourceDao;
import com.lgfei.tool.spider.operate.mvcrawler.enums.TablesEnum;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.CreateMvSourceReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteMvSourceReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryMvSourcesReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateMvSourceReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.resp.CreateMvSourceResp;
import com.lgfei.tool.spider.operate.mvcrawler.model.resp.QueryMvSourcesResp;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvSourceVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvSourceService;

@Service
public class MvSourceServiceImpl implements MvSourceService
{
    @Autowired
    private MvSourceDao mvSourceDao;
    
    @Override
    public void batchInsert(List<MvSourceVO> voList)
        throws SystemException
    {
        if (CollectionUtils.isEmpty(voList))
        {
            return;
        }
        mvSourceDao.batchInsert(voList);
    }
    
    @Override
    public List<MvSourceVO> findList(MvSourceVO vo)
        throws SystemException
    {
        return mvSourceDao.queryList(vo);
    }
    
    @Override
    public void findByMvId(QueryMvSourcesReqData data, QueryMvSourcesResp resp)
        throws SystemException
    {
        MvSourceVO vo = new MvSourceVO();
        vo.setMvId(data.getMvId());
        
        resp.setRows(this.findList(vo));
    }
    
    @Override
    public void deleteByMvIds(Collection<String> mvIds)
    {
        if (CollectionUtils.isEmpty(mvIds))
        {
            return;
        }
        mvSourceDao.deleteByMvIds(mvIds);
    }
    
    @Override
    public void deleteById(DeleteMvSourceReqData data, BaseResponse resp)
        throws SystemException
    {
        String sourceId = data.getSourceId();
        if (StringUtils.isEmpty(sourceId))
        {
            return;
        }
        MvSourceVO vo = new MvSourceVO();
        vo.setSourceId(sourceId);
        mvSourceDao.delete(vo);
    }
    
    @Override
    public void updateMvSource(UpdateMvSourceReqData data, BaseResponse resp)
    {
        MvSourceVO vo = data.getMvSource();
        if (null == vo)
        {
            return;
        }
        mvSourceDao.update(vo);
    }
    
    @Override
    public void createMvSource(CreateMvSourceReqData data, CreateMvSourceResp resp)
        throws SystemException
    {
        MvSourceVO vo = data.getMvSource();
        if (null == vo)
        {
            return;
        }
        String newSourceId = CreateIDUtil.businessID(TablesEnum.T_MV_SOURCE.getModule());
        vo.setSourceId(newSourceId);
        mvSourceDao.insert(vo);
        resp.setSourceId(newSourceId);
    }
    
}
