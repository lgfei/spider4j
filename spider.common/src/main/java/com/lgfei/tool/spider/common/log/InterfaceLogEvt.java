package com.lgfei.tool.spider.common.log;

import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 接口日志实体类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class InterfaceLogEvt
{
    private String moduleName;
    
    private String responseType;
    
    private String interfaceType;
    
    private String interfaceName;
    
    private String source;
    
    private String dest;
    
    private String traceId;
    
    private String transactionId;
    
    private int resultCode;
    
    private String exceptionMsg;
    
    private long startTime;
    
    private String requestTime;
    
    private String logLevel;
    
    private Map<String, Object> dataMap;
    
    /**
     * <默认构造函数>
      *
     */
    public InterfaceLogEvt()
    {
    }
    
    /**
     * <默认构造函数>
      * @param transactionId 流水号
      * @param resultCode 错误码
      * @param startTime 启动时间
      * @param dataMap 数据参数
     */
    public InterfaceLogEvt(String transactionId, int resultCode, long startTime, Map<String, Object> dataMap)
    {
        super();
        this.transactionId = transactionId;
        this.resultCode = resultCode;
        this.startTime = startTime;
        this.dataMap = dataMap;
    }
    
    public String getModuleName()
    {
        return moduleName;
    }
    
    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }
    
    public String getResponseType()
    {
        return responseType;
    }
    
    public void setResponseType(String responseType)
    {
        this.responseType = responseType;
    }
    
    public String getInterfaceType()
    {
        return interfaceType;
    }
    
    public void setInterfaceType(String interfaceType)
    {
        this.interfaceType = interfaceType;
    }
    
    public String getInterfaceName()
    {
        return interfaceName;
    }
    
    public void setInterfaceName(String interfaceName)
    {
        this.interfaceName = interfaceName;
    }
    
    public String getSource()
    {
        return source;
    }
    
    public void setSource(String source)
    {
        this.source = source;
    }
    
    public String getDest()
    {
        return dest;
    }
    
    public void setDest(String dest)
    {
        this.dest = dest;
    }
    
    public String getTraceId()
    {
        return traceId;
    }
    
    public void setTraceId(String traceId)
    {
        this.traceId = traceId;
    }
    
    public String getTransactionId()
    {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }
    
    public String getExceptionMsg()
    {
        return exceptionMsg;
    }
    
    public void setExceptionMsg(String exceptionMsg)
    {
        this.exceptionMsg = exceptionMsg;
    }
    
    public int getResultCode()
    {
        return resultCode;
    }
    
    public void setResultCode(int resultCode)
    {
        this.resultCode = resultCode;
    }
    
    public long getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }
    
    public String getRequestTime()
    {
        return requestTime;
    }
    
    public void setRequestTime(String requestTime)
    {
        this.requestTime = requestTime;
    }
    
    public String getLogLevel()
    {
        return logLevel;
    }
    
    public void setLogLevel(String logLevel)
    {
        this.logLevel = logLevel;
    }
    
    public Map<String, Object> getDataMap()
    {
        return dataMap;
    }
    
    public void setDataMap(Map<String, Object> dataMap)
    {
        this.dataMap = dataMap;
    }
    
    public long getSpendTime()
    {
        return System.currentTimeMillis() - startTime;
    }
    
    @Override
    public String toString()
    {
        return JSON.toJSONString(this);
    }
    
}
