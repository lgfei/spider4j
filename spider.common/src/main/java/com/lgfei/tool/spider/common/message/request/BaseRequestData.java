package com.lgfei.tool.spider.common.message.request;

import com.lgfei.tool.spider.common.constant.ParamRegex;
import com.lgfei.tool.spider.common.validation.annotations.Param;

/**
 * 业务参数对象抽象类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年10月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseRequestData implements IRequestData
{
    /**
     * 语言
     */
    private String language;
    
    @Param(canBlank = true, regex = ParamRegex.PAGE_LANGUAGE_REGEX, defValue = "zh_CN")
    public String getLanguage()
    {
        return language;
    }
    
    public void setLanguage(String language)
    {
        this.language = language;
    }
}
