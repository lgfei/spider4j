package com.lgfei.tool.spider.common.constant;

/**
 * 公用正则表达式
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年10月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ParamRegex
{
    /**
     * 数字正则
     */
    String DIGIT = "^[0-9]*$";
    
    /**
     * 字符统一正则
     */
    String VARCHAR_REGEX = "^[a-zA-Z0-9-_]*$";
    
    /**
     * 64位id
     */
    String ID_REGEX = "^[a-zA-Z0-9-_=]{1,64}$";
    
    /**
     * 页面当前语言正则
     */
    String PAGE_LANGUAGE_REGEX = "zh_CN|en_US";
}
