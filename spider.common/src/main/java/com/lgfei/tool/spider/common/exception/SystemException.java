package com.lgfei.tool.spider.common.exception;

/**
 * 系统异常类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年10月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SystemException extends Exception
{
    /**
     * 序列
     */
    private static final long serialVersionUID = 8886099279458402449L;
    
    private int errCode;
    
    private String errMsg;
    
    public SystemException(int retCode, String retMsg)
    {
        super(retMsg);
        this.errCode = retCode;
        this.errMsg = retMsg;
    }
    
    public SystemException(String retMsg)
    {
        super(retMsg);
        this.errMsg = retMsg;
    }
    
    public SystemException(int retCode, String retMsg, Throwable e)
    {
        super(retMsg, e);
        this.errCode = retCode;
        this.errMsg = retMsg;
    }
    
    public int getErrCode()
    {
        return errCode;
    }
    
    public void setErrCode(int errCode)
    {
        this.errCode = errCode;
    }
    
    public String getErrMsg()
    {
        return errMsg;
    }
    
    public void setErrMsg(String errMsg)
    {
        this.errMsg = errMsg;
    }
    
}
