package com.lgfei.tool.spider.common.message.response;

/**
 * 响应结果返回接口
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface IResponse
{
    /**
     * 获取返回码
     * <功能详细描述>
     * @return 错误码
     * @see [类、类#方法、类#成员]
     */
    int getRetCode();
    
    /**
     * 设置返回码
     * <功能详细描述>
     * @param retCode 错误码
     * @see [类、类#方法、类#成员]
     */
    void setRetCode(int retCode);
    
    /**
     * 获取返回信息
     * <功能详细描述>
     * @return 错误消息
     * @see [类、类#方法、类#成员]
     */
    String getRetMsg();
    
    /**
     * 设置返回信息
     * <功能详细描述>
     * @param retMsg 错误消息
     * @see [类、类#方法、类#成员]
     */
    void setRetMsg(String retMsg);
}
