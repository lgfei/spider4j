package com.lgfei.tool.spider.operate.mvcrawler.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.lgfei.tool.spider.common.constant.Constant;
import com.lgfei.tool.spider.common.constant.NumberKeys;
import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.common.util.CreateIDUtil;
import com.lgfei.tool.spider.common.vo.PageVO;
import com.lgfei.tool.spider.operate.mvcrawler.dao.MvInfoDao;
import com.lgfei.tool.spider.operate.mvcrawler.enums.TablesEnum;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteMvReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryMvPageListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateMvReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvInfoVO;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvSourceVO;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.WebsiteVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvInfoService;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvSourceService;
import com.lgfei.tool.spider.operate.mvcrawler.service.WebsiteService;

@Service
public class MvInfoServiceImpl implements MvInfoService
{
    private static final Logger LOG = LoggerFactory.getLogger(MvInfoServiceImpl.class);
    
    @Autowired
    private MvInfoDao mvInfoDao;
    
    @Autowired
    private MvSourceService mvSourceService;
    
    @Autowired
    private WebsiteService websiteService;
    
    @Override
    public void insert(MvInfoVO vo)
        throws SystemException
    {
        if (null == vo)
        {
            return;
        }
        try
        {
            mvInfoDao.insert(vo);
            addMvSource(vo.getMvId(), vo.getWebsiteId(), vo.getSourceUrls());
        }
        catch (Exception e)
        {
            LOG.info("影片：{}入库失败,{}", vo.getName(), e.getMessage());
            if (e instanceof DuplicateKeyException)
            {
                MvInfoVO dbVo = mvInfoDao.query(vo);
                if (null == dbVo)
                {
                    LOG.info("影片名冲突却在数据库中没查到!");
                    return;
                }
                // 更新数据
                dbVo = refreshMvInfo(dbVo, vo);
                mvInfoDao.update(dbVo);
                addMvSource(dbVo.getMvId(), vo.getWebsiteId(), vo.getSourceUrls());
            }
        }
    }
    
    private MvInfoVO refreshMvInfo(MvInfoVO dbVo, MvInfoVO paramVo)
    {
        if (!StringUtils.isEmpty(paramVo.getBrief()))
        {
            dbVo.setBrief(paramVo.getBrief());
        }
        if (!StringUtils.isEmpty(paramVo.getDetails()))
        {
            dbVo.setDetails(paramVo.getDetails());
        }
        if (!StringUtils.isEmpty(paramVo.getDirector()))
        {
            dbVo.setDirector(paramVo.getDirector());
        }
        if (!StringUtils.isEmpty(paramVo.getPlayer()))
        {
            dbVo.setPlayer(paramVo.getPlayer());
        }
        if (!StringUtils.isEmpty(paramVo.getPoster()))
        {
            dbVo.setPoster(paramVo.getPoster());
        }
        if (!StringUtils.isEmpty(paramVo.getShowDate()))
        {
            dbVo.setShowDate(paramVo.getShowDate());
        }
        return dbVo;
    }
    
    private void addMvSource(String mvId, String websiteId, String sourceUrls)
        throws SystemException
    {
        if (StringUtils.isEmpty(sourceUrls))
        {
            return;
        }
        
        List<String> exixtsUrls = new ArrayList<>();
        
        MvSourceVO paramVO = new MvSourceVO();
        paramVO.setMvId(mvId);
        List<MvSourceVO> exixtsSources = mvSourceService.findList(paramVO);
        for (MvSourceVO mvSourceVO : exixtsSources)
        {
            exixtsUrls.add(mvSourceVO.getSourceUrl());
        }
        
        String[] urls = sourceUrls.split(",");
        List<MvSourceVO> sourceList = new ArrayList<>();
        for (String url : urls)
        {
            if (exixtsUrls.contains(url))
            {
                continue;
            }
            MvSourceVO sourceVO = new MvSourceVO();
            sourceVO.setSourceId(CreateIDUtil.businessID(TablesEnum.T_MV_SOURCE.getModule()));
            sourceVO.setMvId(mvId);
            sourceVO.setWebsiteId(websiteId);
            if (!url.startsWith(Constant.HTTP))
            {
                url = correctSoureUrl(websiteId, url);
            }
            sourceVO.setSourceUrl(url);
            
            sourceList.add(sourceVO);
        }
        // 插入播放源
        mvSourceService.batchInsert(sourceList);
    }
    
    /**
     * 纠正不是以http开头的播放地址
     * <功能详细描述>
     * @param websiteId 网站id
     * @param url 播放源地址
     * @return 正确的url
     * @throws SystemException 系统异常
     * @see [类、类#方法、类#成员]
     */
    private String correctSoureUrl(String websiteId, String url)
        throws SystemException
    {
        WebsiteVO website = websiteService.findById(websiteId);
        String websiteAddr = website.getAddress();
        if (websiteAddr.endsWith(Constant.SLASH))
        {
            websiteAddr = websiteAddr.substring(NumberKeys.NUM_0, websiteAddr.length() - NumberKeys.NUM_1);
        }
        if (!url.startsWith(Constant.SLASH))
        {
            url = Constant.SLASH.concat(url);
        }
        url = websiteAddr.concat(url);
        return url;
    }
    
    @Override
    public void findPageList(QueryMvPageListReqData data, PageResultResponse<MvInfoVO> resp)
        throws SystemException
    {
        MvInfoVO vo = new MvInfoVO();
        vo.setMvId(data.getMvId());
        vo.setName(data.getName());
        PageVO pageVO = new PageVO();
        pageVO.setPageNum(data.getPageNum());
        pageVO.setPageSize(data.getPageSize());
        
        List<MvInfoVO> list = mvInfoDao.queryMvPageList(vo, pageVO);
        
        resp.setTotal(pageVO.getTotal());
        resp.setRows(list);
    }
    
    @Override
    public void batchDelete(DeleteMvReqData data, BaseResponse resp)
        throws SystemException
    {
        List<MvInfoVO> voList = data.getMvInfos();
        if (CollectionUtils.isEmpty(voList))
        {
            return;
        }
        Set<String> mvIds = new HashSet<>();
        for (MvInfoVO mvInfoVO : voList)
        {
            mvIds.add(mvInfoVO.getMvId());
        }
        // 删除片源
        mvSourceService.deleteByMvIds(mvIds);
        // 删除影片
        mvInfoDao.batchDelete(voList);
    }
    
    @Override
    public void batchUpdate(UpdateMvReqData data, BaseResponse resp)
        throws SystemException
    {
        List<MvInfoVO> voList = data.getMvInfos();
        if (CollectionUtils.isEmpty(voList))
        {
            return;
        }
        mvInfoDao.batchUpdate(voList);
    }
    
}
