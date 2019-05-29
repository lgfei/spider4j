package com.lgfei.tool.spider.common.cache;

/**
 * CacheKey定义类
 * <功能详细描述>
 * @author  Lgfei
 * @version  [版本号, 2017年11月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CacheKey
{
    /**
     * 定义缓存key
     * <功能详细描述>
     * @author  Lgfei
     * @version  [版本号, 2017年11月2日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public enum KvMapping
    {
        /**
         * 影片缓存
         */
        mv("mv:%s");
        
        private String mapping;
        
        public String getMapping()
        {
            return mapping;
        }
        
        private KvMapping(String mapping)
        {
            this.mapping = mapping;
        }
        
        public String format(Object... args)
        {
            return String.format(mapping, args);
        }
    }
}
