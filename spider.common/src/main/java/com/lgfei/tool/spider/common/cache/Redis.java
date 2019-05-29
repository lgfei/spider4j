package com.lgfei.tool.spider.common.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * redis连接配置信息
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年10月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component
@ConfigurationProperties(prefix = "redis")
@PropertySource("classpath:redis-config.properties")
public class Redis
{
    /**
     * 服务器地址
     */
    private String host;
    
    /**
     * 连接密码
     */
    private String password;
    
    /**
     * 连接端口
     */
    private int port;
    
    /**
     * 数据库索引
     */
    private int database;
    
    /**
     * 连接超时时间
     */
    private int timeout;
    
    /**
     * 默认缓存有效时间
     */
    private long effectiveTime;
    
    public String getHost()
    {
        return host;
    }
    
    public void setHost(String host)
    {
        this.host = host;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public void setPort(int port)
    {
        this.port = port;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public int getDatabase()
    {
        return database;
    }
    
    public void setDatabase(int database)
    {
        this.database = database;
    }
    
    public int getTimeout()
    {
        return timeout;
    }
    
    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }
    
    public long getEffectiveTime()
    {
        return effectiveTime;
    }
    
    public void setEffectiveTime(long effectiveTime)
    {
        this.effectiveTime = effectiveTime;
    }
    
}
