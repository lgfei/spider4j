package com.lgfei.tool.spider.common.cache.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 针对封装实体类型的redis操作接口
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RedisService
{
    /**
     * 读取单个key，单个对象缓存数据
     * <功能详细描述>
     * @param key 缓存key
     * @param clazz 类型
     * @return T
     * @see [类、类#方法、类#成员]
     */
    <T> T get(String key, Class<T> clazz);
    
    /**
     * 读取单个key，list对象缓存数据
     * <功能详细描述>
     * @param key 缓存key
     * @param clazz 类型
     * @return List<T>
     * @see [类、类#方法、类#成员]
     */
    <T> List<T> mget(String key, Class<T> clazz);
    
    /**
     * 读取多个key，list对象缓存数据
     * <功能详细描述>
     * @param keys 多个缓存key
     * @param clazz 类型
     * @return List<T>
     * @see [类、类#方法、类#成员]
     */
    <T> List<T> mget(Collection<String> keys, Class<T> clazz);
    
    /**
     * 缓存单个对象
     * <功能详细描述>
     * @param key 缓存key
     * @param obj 缓存数据
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean set(String key, Object obj);
    
    /**
     * 缓存单个对象（自定义缓存时间）
     * <功能详细描述>
     * @param key 缓存key
     * @param obj 缓存数据
     * @param timeout 失效时间
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean set(String key, Object obj, int timeout);
    
    /**
     * Map批量缓存
     * <功能详细描述>
     * @param map 缓存数据
     * @see [类、类#方法、类#成员]
     */
    <T> void mset(Map<String, T> map);
    
    /**
     * Map批量缓存（自定义缓存时间）
     * <功能详细描述>
     * @param map 缓存数据
     * @param timeout 失效时间
     * @see [类、类#方法、类#成员]
     */
    <T> void mset(Map<String, T> map, int timeout);
    
    /**
     * 删除缓存
     * <功能详细描述>
     * @param keys 一个或多个键
     * @see [类、类#方法、类#成员]
     */
    void del(String... keys);
    
    /**
     * 批量删除缓存
     * <功能详细描述>
     * @param keys 一个或多个键
     * @see [类、类#方法、类#成员]
     */
    void mdel(Collection<String> keys);
    
    /**
     * 缓存key重命名
     * <功能详细描述>
     * @param oldKey 原来的key
     * @param newKey 新key
     * @return String
     * @see [类、类#方法、类#成员]
     */
    void rename(String oldKey, String newKey);
    
    /**
     * 模糊匹配key
     * <功能详细描述>
     * @param pattern 正则 
     * @return Set<String>
     * @see [类、类#方法、类#成员]
     */
    Set<String> keys(String pattern);
}
