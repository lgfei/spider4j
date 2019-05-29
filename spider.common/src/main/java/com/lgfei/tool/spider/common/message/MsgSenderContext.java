package com.lgfei.tool.spider.common.message;

import com.lgfei.tool.spider.common.message.annotations.Location;
import com.lgfei.tool.spider.common.message.response.BaseResponse;

/**
 * 请求消息体
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年10月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MsgSenderContext
{
    /**
     * 请求参数配置项
     */
    private Location location;
    
    /**
     * 请求参数
     */
    private String msgBody;
    
    /**
     * 返回类型
     */
    private Class<? extends BaseResponse> respClazz;
    
    /**
     * 远程服务地址
     */
    private String remoteUrl;
    
    /**
     * <默认构造函数>
      * @param location uri
      * @param msgBody 消息体
      * @param respClazz 响应类
      * @param remoteUrl 远程url
     */
    public MsgSenderContext(Location location, String msgBody, Class<? extends BaseResponse> respClazz,
        String remoteUrl)
    {
        this.location = location;
        this.msgBody = msgBody;
        this.respClazz = respClazz;
        this.remoteUrl = remoteUrl;
    }
    
    public Location getLocation()
    {
        return location;
    }
    
    public void setLocation(Location location)
    {
        this.location = location;
    }
    
    public String getMsgBody()
    {
        return msgBody;
    }
    
    public void setMsgBody(String msgBody)
    {
        this.msgBody = msgBody;
    }
    
    public Class<? extends BaseResponse> getRespClazz()
    {
        return respClazz;
    }
    
    public void setRespClazz(Class<? extends BaseResponse> respClazz)
    {
        this.respClazz = respClazz;
    }
    
    public String getRemoteUrl()
    {
        return remoteUrl;
    }
    
    public void setRemoteUrl(String remoteUrl)
    {
        this.remoteUrl = remoteUrl;
    }
    
}
