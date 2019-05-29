package com.lgfei.tool.spider.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 获取spring容器，以访问容器中定义的其他bean
 * <功能详细描述>
 * 
 * @author  longguofei
 * @version  [版本号, 2017年11月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class SpringContextUtil implements ApplicationContextAware
{
    
    /**
     * 单列
     */
    private static SpringContextUtil instance = new SpringContextUtil();
    
    /**
     * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
     */
    private ApplicationContext applicationContext; //Spring应用上下文环境
    
    public static SpringContextUtil getInstance()
    {
        return instance;
    }
    
    public static void setInstance(SpringContextUtil instance)
    {
        SpringContextUtil.instance = instance;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        instance.applicationContext = applicationContext;
    }
    
    /**
     * 获取spring上下文
     * @return ApplicationContext
     * @see [类、类#方法、类#成员]
     */
    public static ApplicationContext getApplicationContext()
    {
        return instance.applicationContext;
    }
    
    /**   
     * 获取对象      
     * @param name   
     * @return Object 一个以所给名字注册的bean的实例   
     * @throws BeansException   
     */
    public static Object getBean(String name)
        throws BeansException
    {
        return instance.applicationContext.getBean(name);
    }
    
    /**   
     * 获取对象      
     * @param requiredType bean的类型   
     * @param <T> 一个以所给名字注册的bean的实例
     * @return T 一个以所给名字注册的bean的实例
     * @throws BeansException bean不存在异常
     */
    public static <T> T getBean(Class<T> requiredType)
        throws BeansException
    {
        return (T)instance.applicationContext.getBean(requiredType);
    }
    
    /**   
     * 获取类型为requiredType的对象   
     * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）   
     * @param name       bean注册名   
     * @param requiredType 返回对象类型   
     * @return Object 返回requiredType类型对象   
     * @throws BeansException   
     */
    public static Object getBean(String name, Class<?> requiredType)
        throws BeansException
    {
        return instance.applicationContext.getBean(name, requiredType);
    }
    
    /**   
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true    
     * @param name   
     * @return boolean   
     */
    public static boolean containsBean(String name)
    {
        return instance.applicationContext.containsBean(name);
    }
    
    /**   
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。   
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）      
     * @param name   
     * @return boolean   
     * @throws NoSuchBeanDefinitionException   
     */
    public static boolean isSingleton(String name)
        throws NoSuchBeanDefinitionException
    {
        return instance.applicationContext.isSingleton(name);
    }
    
    /**   
     * 获取spring注册对象类型
     * @param name 服务名
     * @return Class 注册对象的类型   
     * @throws NoSuchBeanDefinitionException   
     */
    public static Class<?> getType(String name)
        throws NoSuchBeanDefinitionException
    {
        return instance.applicationContext.getType(name);
    }
    
    /**   
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名      
     * @param name 服务名
     * @return 返回这些别名
     * @throws NoSuchBeanDefinitionException 未搜索到异常
     */
    public static String[] getAliases(String name)
        throws NoSuchBeanDefinitionException
    {
        return instance.applicationContext.getAliases(name);
    }
}
