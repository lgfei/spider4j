package com.lgfei.tool.spider.common.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * redis连接池配置信息
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年10月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component
@ConfigurationProperties(prefix = "redis.pool")
@PropertySource("classpath:redis-config.properties")
public class RedisPool
{
    /**
     * 连接池中的最大空闲连接数
     */
    private int maxIdle;
    
    /**
     * 连接池中的最小空闲连接数
     */
    private int minIdle;
    
    /**
     * 连接池中的最大等待时间，单位（毫秒）
     */
    private long maxWaitMillis;
    
    public int getMaxIdle()
    {
        return maxIdle;
    }
    
    public void setMaxIdle(int maxIdle)
    {
        this.maxIdle = maxIdle;
    }
    
    public int getMinIdle()
    {
        return minIdle;
    }
    
    public void setMinIdle(int minIdle)
    {
        this.minIdle = minIdle;
    }
    
    public long getMaxWaitMillis()
    {
        return maxWaitMillis;
    }
    
    public void setMaxWaitMillis(long maxWaitMillis)
    {
        this.maxWaitMillis = maxWaitMillis;
    }
}
