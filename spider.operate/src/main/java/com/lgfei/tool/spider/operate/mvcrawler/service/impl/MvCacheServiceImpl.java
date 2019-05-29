package com.lgfei.tool.spider.operate.mvcrawler.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.lgfei.tool.spider.common.cache.service.RedisService;
import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteMvCacheReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryMvPageListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.SyncMvCacheReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvInfoVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvCacheService;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvInfoService;

@Service
public class MvCacheServiceImpl implements MvCacheService
{
    private static final Logger LOG = LoggerFactory.getLogger(MvCacheServiceImpl.class);
    
    @Autowired
    private RedisService redisService;
    
    @Autowired
    private MvInfoService mvInfoService;
    
    @Override
    public void findPageList(QueryMvPageListReqData data, PageResultResponse<MvInfoVO> resp)
        throws SystemException
    {
        Set<String> keys = null;
        String mvId = data.getMvId();
        if (StringUtils.isEmpty(mvId))
        {
            keys = redisService.keys("mv:*");
        }
        else
        {
            keys = new HashSet<>();
            keys.add("mv:" + mvId);
        }
        List<MvInfoVO> mvCacheList = redisService.mget(keys, MvInfoVO.class);
        if (CollectionUtils.isEmpty(mvCacheList))
        {
            LOG.info("根据影片id没有找对应的缓存");
            return;
        }
        String name = data.getName();
        int offset = data.getPageSize();
        int start = offset * (data.getPageNum() - 1);
        if (!StringUtils.isEmpty(name))
        {
            mvCacheList = like(mvCacheList, name);
        }
        resp.setRows(splitList(mvCacheList, start, offset));
        resp.setTotal(mvCacheList.size());
    }
    
    private List<MvInfoVO> like(List<MvInfoVO> list, String name)
    {
        List<MvInfoVO> results = new ArrayList<>();
        for (MvInfoVO mvInfoVO : list)
        {
            String curName = mvInfoVO.getName();
            if (match(curName, name))
            {
                results.add(mvInfoVO);
            }
        }
        return results;
    }
    
    private boolean match(String origStr, String paramStr)
    {
        String regex = ".*(" + paramStr + ").*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(origStr);
        return matcher.find();
    }
    
    private List<MvInfoVO> splitList(List<MvInfoVO> list, int start, int offset)
    {
        int len = list.size();
        List<MvInfoVO> rows = new ArrayList<>();
        for (int i = 0; i < offset; i++)
        {
            int index = start + i;
            if (index >= len)
            {
                break;
            }
            rows.add(list.get(index));
        }
        return rows;
    }
    
    @Override
    public void deleteMvCache(DeleteMvCacheReqData data, BaseResponse respss)
        throws SystemException
    {
        Set<String> keys = getKeys(data.getMvIds());
        redisService.mdel(keys);
    }
    
    private Set<String> getKeys(List<String> mvIds)
    {
        if (CollectionUtils.isEmpty(mvIds))
        {
            // 全部key
            return redisService.keys("mv:*");
        }
        // 传入的key
        Set<String> keys = new HashSet<>();
        for (String mvId : mvIds)
        {
            keys.add("mv:" + mvId);
        }
        return keys;
    }
    
    @Override
    public void syncMvCache(SyncMvCacheReqData data, BaseResponse resp)
        throws SystemException
    {
        Set<String> failedKeys = new HashSet<>();
        Set<String> keys = getKeys(data.getMvIds());
        try
        {
            // 逐条将redis的数据同步至DB
            for (String cacheKey : keys)
            {
                MvInfoVO mvInfo = redisService.get(cacheKey, MvInfoVO.class);
                try
                {
                    mvInfoService.insert(mvInfo);
                }
                catch (Exception e)
                {
                    LOG.error("redis to db failed:{}", e.getMessage());
                    LOG.info("redis影片：{},同步至数据库失败", mvInfo.getName());
                    // 记录同步失败的影片，标记为不清除
                    failedKeys.add(cacheKey);
                }
            }
        }
        catch (Exception e)
        {
            LOG.error("redis缓存操作异常：{}", e.getMessage());
        }
        finally
        {
            if (!CollectionUtils.isEmpty(failedKeys))
            {
                // 移除同步失败的key
                keys.removeAll(failedKeys);
            }
            if (!CollectionUtils.isEmpty(keys))
            {
                redisService.mdel(keys);
            }
        }
    }
}
