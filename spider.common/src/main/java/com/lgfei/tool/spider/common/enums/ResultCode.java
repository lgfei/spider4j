package com.lgfei.tool.spider.common.enums;

public enum ResultCode
{
    SUCCESS(0, "Success"), INVALID_PARAM(1, "invalid param"), REQUEST_INVALID(2, "request invalid"), INNER_ERROR(3,
        "server is busy"), REQUEST_EXPIRED(4, "request timeout"), INCOMPLETE_DATA(5, "incomplete data");
    
    private Integer retCode;
    
    private String retMsg;
    
    private ResultCode(Integer retCode, String retMsg)
    {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }
    
    public Integer getRetCode()
    {
        return retCode;
    }
    
    public void setRetCode(Integer retCode)
    {
        this.retCode = retCode;
    }
    
    public String getRetMsg()
    {
        return retMsg;
    }
    
    public void setRetMsg(String retMsg)
    {
        this.retMsg = retMsg;
    }
    
}
