package com.lgfei.tool.spider.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lgfei.tool.spider.common.enums.ResultCode;
import com.lgfei.tool.spider.common.exception.InnerException;

/**
 * 反射操作工具类
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2017年6月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class ReflectUtil
{
    private static final Logger LOG = LoggerFactory.getLogger(ReflectUtil.class);
    
    /**
     * 反射出某一个类的所有getter方法（包括所有父类）
     * <功能详细描述>
     * @param clazz 类
     * @return 方法列表
     * @see [类、类#方法、类#成员]
     */
    public static List<Method> getMethods(Class<?> clazz)
    {
        List<Method> methodList = new ArrayList<Method>();
        while (null != clazz)
        {
            Method[] methods = clazz.getDeclaredMethods();
            if (null == methods || methods.length == 0)
            {
                clazz = clazz.getSuperclass();
                continue;
            }
            for (Method method : methods)
            {
                if (method.getName().startsWith("get"))
                {
                    methodList.add(method);
                }
            }
            clazz = clazz.getSuperclass();
        }
        
        return methodList;
    }
    
    /**
     * 获取对象中的属性
     * <包括父类属性>
     * @param obj 对象
     * @param fieldName 属性名
     * @return 属性
     * @see [类、类#方法、类#成员]
     */
    public static Field getField(Object obj, String fieldName)
    {
        if (null == obj)
        {
            return null;
        }
        Class<?> currentClass = obj.getClass();
        while (null != currentClass)
        {
            try
            {
                return currentClass.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException e)
            {
                // 如果有继承则继续校验父类
                currentClass = currentClass.getSuperclass();
            }
        }
        return null;
    }
    
    /**
     * 利用反射根据属性名称获取属性值
     * <一句话功能简述>
     * <功能详细描述>
     * @param obj 对象
     * @param fieldName 字段名称
     * @return 对象
     * @throws InnerException 自定义异常
     * @see [类、类#方法、类#成员]
     */
    public static Object getValue(Object obj, String fieldName)
        throws InnerException
    {
        if (null == obj)
        {
            return null;
        }
        try
        {
            return getValueIncludeSupper(obj, fieldName);
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            LOG.error("反射取值异常,obj:{},field:{}", obj.getClass().getName(), fieldName);
            throw new InnerException(ResultCode.INNER_ERROR.getRetCode(), ResultCode.INNER_ERROR.getRetMsg(), e);
        }
    }
    
    /**
     * 利用反射根据属性名称获取属性值
     * 会遍历所有父类的属性，直到找到fieldName对应的属性，如果最后没找到则返回空
     * @param obj 实体对象
     * @param fieldName 属性名
     * @return Object
     * @throws NoSuchMethodException 未找到方法异常
     * @throws IllegalAccessException 方法调用异常
     * @throws InvocationTargetException 非法访问异常
     * @see [类、类#方法、类#成员]
     */
    private static Object getValueIncludeSupper(Object obj, String fieldName)
        throws IllegalAccessException, InvocationTargetException
    {
        Class<?> currentClass = obj.getClass();
        while (null != currentClass)
        {
            try
            {
                currentClass.getDeclaredField(fieldName);
                return reflect(obj, fieldName);
            }
            catch (NoSuchFieldException e)
            {
                // 如果有继承则继续校验父类
                currentClass = currentClass.getSuperclass();
            }
            catch (NoSuchMethodException e)
            {
                try
                {
                    Field field = currentClass.getDeclaredField(fieldName);
                    if (null != field)
                    {
                        field.setAccessible(true);
                        return field.get(obj);
                    }
                }
                catch (NoSuchFieldException e1)
                {
                    // 如果有继承则继续校验父类
                    currentClass = currentClass.getSuperclass();
                }
            }
        }
        return null;
    }
    
    /**
     * 反射取值
     * 根据属性名反射getter方法，取出属性值
     * @param obj 实体对象
     * @param fieldName 属性名
     * @return Object
     * @throws NoSuchMethodException 未找到方法异常
     * @throws IllegalAccessException 方法调用异常
     * @throws InvocationTargetException 非法访问异常
     * @see [类、类#方法、类#成员]
     */
    private static Object reflect(Object obj, String fieldName)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        // 获取属性名称首字母，大写
        // 修改fingbug,指定locale（使用locale.us避免在不同环境的机器上出现问题）
        String firstLetter = fieldName.substring(0, 1).toUpperCase(Locale.US);
        // 拼接getter方法名
        String getMethodName = "get" + firstLetter + fieldName.substring(1);
        // 获取getter方法
        Method getMethod = obj.getClass().getMethod(getMethodName);
        // 取值
        Object fieldValue = getMethod.invoke(obj);
        
        return fieldValue;
    }
    
    /**
     * 反射赋值
     * <功能详细描述>
     * @param obj 对象
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @throws InnerException 异常
     * @see [类、类#方法、类#成员]
     */
    public static void setValue(Object obj, String fieldName, Object fieldValue)
        throws InnerException
    {
        if (null == obj)
        {
            return;
        }
        Class<?> currentClass = obj.getClass();
        while (null != currentClass)
        {
            try
            {
                currentClass.getDeclaredField(fieldName);
                // 获取属性名称首字母，大写
                String firstLetter = fieldName.substring(0, 1).toUpperCase(Locale.US);
                // 拼接setter方法名
                String setMethodName = "set" + firstLetter + fieldName.substring(1);
                try
                {
                    // 根据字段名称和类型赋值字段值
                    Class<?> fieldClazz = obj.getClass().getDeclaredField(fieldName).getType();
                    if (String.class.isAssignableFrom(fieldClazz))
                    {
                        Method setMethod = currentClass.getMethod(setMethodName, String.class);
                        setMethod.invoke(obj, null == fieldValue ? null : fieldValue.toString());
                    }
                    else if (Integer.class.isAssignableFrom(fieldClazz))
                    {
                        Method setMethod = currentClass.getMethod(setMethodName, Integer.class);
                        setMethod.invoke(obj, null == fieldValue ? null : Integer.parseInt(fieldValue.toString()));
                    }
                    else
                    {
                        LOG.info("暂不支持除String和Integer之外的类型反射赋值");
                    }
                    return;
                }
                catch (NoSuchMethodException e)
                {
                    setFieldValueWhenNoGetter(obj, fieldName, fieldValue, currentClass);
                    return;
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                {
                    LOG.error("反射赋值异常,obj:{},field:{},fieldValue:{}",
                        obj.getClass().getName(),
                        fieldName,
                        fieldValue);
                    throw new InnerException(ResultCode.INNER_ERROR.getRetCode(), ResultCode.INNER_ERROR.getRetMsg(),
                        e);
                }
                
            }
            catch (NoSuchFieldException e)
            {
                // 如果有继承则继续校验父类
                currentClass = currentClass.getSuperclass();
            }
        }
    }
    
    /**
     * 当属性没有实现getter方法时给属赋值
     * <功能详细描述>
     * @param obj 对象
     * @param fieldName 属性名
     * @param fieldValue 属性值
     * @param currentClass 当前class
     * @throws NoSuchFieldException 没有属性异常
     * @see [类、类#方法、类#成员]
     */
    private static void setFieldValueWhenNoGetter(Object obj, String fieldName, Object fieldValue,
        Class<?> currentClass)
        throws NoSuchFieldException
    {
        Field field = currentClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        try
        {
            field.set(obj, fieldValue);
            return;
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            LOG.error("反射赋值异常,obj:{},field:{},fieldValue:{}", obj.getClass().getName(), fieldName, fieldValue);
            throw new InnerException(ResultCode.INNER_ERROR.getRetCode(), ResultCode.INNER_ERROR.getRetMsg(), e);
        }
    }
}
