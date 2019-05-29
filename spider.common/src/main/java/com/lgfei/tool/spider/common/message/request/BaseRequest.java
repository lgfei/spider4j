package com.lgfei.tool.spider.common.message.request;

import com.lgfei.tool.spider.common.constant.NumberKeys;
import com.lgfei.tool.spider.common.validation.annotations.Param;

/**
 * 请求参数结构体
 * <规定了请求体结构>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseRequest implements IRequest
{
    /**
     * 客户端的标识，由服务端分配
     */
    private String nodeId;
    
    /**
     * 服务器之间的接口版本号
     */
    private Integer version;
    
    /**
     * 消息跟踪号
     */
    private String traceId;
    
    /**
     * 最早的消息跟踪号
     */
    private String firstTraceId;
    
    /**
     * 发起请求的时间戳，格式为Yyyymmddhhmmss, UTC时间
     */
    private String timestamp;
    
    /**
     * 接口鉴权方式，1：hmac-sha256
     */
    private Integer signType;
    
    /**
     * 参数签名
     */
    private String sign;
    
    /**
     *  业务请求参数
     */
    private String data;
    
    @Param(canBlank = false, maxLength = NumberKeys.NUM_3)
    public String getNodeId()
    {
        return nodeId;
    }
    
    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }
    
    @Param(canBlank = false)
    public Integer getVersion()
    {
        return version;
    }
    
    public void setVersion(Integer version)
    {
        this.version = version;
    }
    
    @Param(canBlank = false, minLength = NumberKeys.NUM_29, maxLength = NumberKeys.NUM_32)
    public String getTraceId()
    {
        return traceId;
    }
    
    public void setTraceId(String traceId)
    {
        this.traceId = traceId;
    }
    
    @Param(canBlank = true, minLength = NumberKeys.NUM_29, maxLength = NumberKeys.NUM_32)
    public String getFirstTraceId()
    {
        return firstTraceId;
    }
    
    public void setFirstTraceId(String firstTraceId)
    {
        this.firstTraceId = firstTraceId;
    }
    
    @Param(canBlank = false, maxLength = NumberKeys.NUM_14)
    public String getTimestamp()
    {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }
    
    @Param(canBlank = false)
    public Integer getSignType()
    {
        return signType;
    }
    
    public void setSignType(Integer signType)
    {
        this.signType = signType;
    }
    
    @Param(canBlank = false, minLength = NumberKeys.NUM_1, maxLength = NumberKeys.NUM_512)
    public String getSign()
    {
        return sign;
    }
    
    public void setSign(String sign)
    {
        this.sign = sign;
    }
    
    @Param(canBlank = false)
    public String getData()
    {
        return data;
    }
    
    public void setData(String data)
    {
        this.data = data;
    }
    
}
