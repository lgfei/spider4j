package com.lgfei.tool.spider.common.message.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求对象请求路径配置
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Location
{
    /**
     * 模块名
     * <功能详细描述>
     * @return 模块名
     * @see [类、类#方法、类#成员]
     */
    String module();
    
    /**
     * 请求路径
     * <功能详细描述>
     * @return 请求uri
     * @see [类、类#方法、类#成员]
     */
    String uri();
}
