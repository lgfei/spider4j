package com.lgfei.tool.spider.common.cache.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lgfei.tool.spider.common.cache.service.RedisService;
import com.lgfei.tool.spider.common.constant.NumberKeys;
import com.lgfei.tool.spider.common.util.StringUtil;

/**
 * redis操作实现类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class RedisServiceImpl implements RedisService
{
    @Autowired
    private StringRedisTemplate template;
    
    @Override
    public <T> T get(String key, Class<T> clazz)
    {
        if (null == key)
        {
            return null;
        }
        if (template.hasKey(key))
        {
            String value = template.opsForValue().get(key);
            // 将json串转为相应的对象返回
            return JSON.parseObject(value, clazz);
        }
        return null;
    }
    
    @Override
    public <T> List<T> mget(String key, Class<T> clazz)
    {
        if (null == key)
        {
            return null;
        }
        if (template.hasKey(key))
        {
            String value = template.opsForValue().get(key);
            return JSONArray.parseArray(value, clazz);
        }
        return null;
    }
    
    @Override
    public <T> List<T> mget(Collection<String> keys, Class<T> clazz)
    {
        if (CollectionUtils.isEmpty(keys))
        {
            return null;
        }
        
        List<String> listStr = template.opsForValue().multiGet(keys);
        if (CollectionUtils.isEmpty(listStr))
        {
            return null;
        }
        
        List<T> list = new ArrayList<T>();
        for (String str : listStr)
        {
            if (!StringUtils.isEmpty(str))
            {
                T item = JSON.parseObject(str, clazz);
                list.add(item);
            }
        }
        return CollectionUtils.isEmpty(list) ? null : list;
    }
    
    @Override
    public boolean set(String key, Object obj)
    {
        if (null == key)
        {
            return false;
        }
        
        String value = JSON.toJSONString(obj);
        // 将对象转为json串放入缓存
        template.opsForValue().set(key, value);
        return true;
    }
    
    @Override
    public boolean set(String key, Object obj, int timeout)
    {
        if (null == key)
        {
            return false;
        }
        if (timeout > NumberKeys.NUM_0)
        {
            String value = JSON.toJSONString(obj);
            template.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        }
        return true;
    }
    
    @Override
    public <T> void mset(Map<String, T> map)
    {
        if (CollectionUtils.isEmpty(map))
        {
            return;
        }
        
        Map<String, String> cacheMap = new HashMap<String, String>();
        for (Map.Entry<String, T> entry : map.entrySet())
        {
            String key = entry.getKey();
            String value = JSON.toJSONString(entry.getValue());
            cacheMap.put(key, value);
        }
        if (CollectionUtils.isEmpty(cacheMap))
        {
            return;
        }
        template.opsForValue().multiSet(cacheMap);
    }
    
    @Override
    public <T> void mset(Map<String, T> map, int timeout)
    {
        mset(map);
        if (timeout < NumberKeys.NUM_0)
        {
            return;
        }
        for (Map.Entry<String, T> entry : map.entrySet())
        {
            String key = entry.getKey();
            if (template.hasKey(key))
            {
                template.expire(key, timeout, TimeUnit.SECONDS);
            }
        }
    }
    
    @Override
    public void del(String... keys)
    {
        if (null == keys || keys.length == 0)
        {
            return;
        }
        if (keys.length == NumberKeys.NUM_1)
        {
            // 单个删除
            template.delete(keys[0]);
            return;
        }
        @SuppressWarnings("unchecked")
        List<String> keyList = CollectionUtils.arrayToList(keys);
        // 批量删除
        template.delete(keyList);
    }
    
    @Override
    public void mdel(Collection<String> keys)
    {
        if (CollectionUtils.isEmpty(keys))
        {
            return;
        }
        // 批量删除
        template.delete(keys);
    }
    
    @Override
    public void rename(String oldKey, String newKey)
    {
        // 老key必须存在，且老key与新key不相同才执行rename
        if (null != oldKey && null != newKey && !StringUtil.isEqual(oldKey, newKey) && template.hasKey(oldKey))
        {
            template.rename(oldKey, newKey);
        }
    }
    
    @Override
    public Set<String> keys(String pattern)
    {
        // 不支持空值匹配
        if (StringUtils.isEmpty(pattern))
        {
            return null;
        }
        return template.keys(pattern);
    }
    
}
