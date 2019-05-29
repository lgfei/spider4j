package com.lgfei.tool.spider.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

/**
 * 配置项读取工具类
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2018年3月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PropertiesUtil
{
    public static Map<String, String> propertiesMap = new HashMap<>();
    
    /**
     * 加载配置文件的所有配置项
     * <功能详细描述>
     * @param propertyFileName 配置文件名
     * @see [类、类#方法、类#成员]
     */
    public static void loadAllProperties(String... propertyFileName)
    {
        if (null == propertyFileName || propertyFileName.length == 0)
        {
            return;
        }
        try
        {
            for (String fileName : propertyFileName)
            {
                if (!StringUtils.isEmpty(fileName))
                {
                    Properties properties = PropertiesLoaderUtils.loadAllProperties(fileName);
                    processProperties(properties);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private static void processProperties(Properties props)
        throws BeansException
    {
        for (Object key : props.keySet())
        {
            String keyStr = key.toString();
            try
            {
                // PropertiesLoaderUtils的默认编码是ISO-8859-1,在这里转码一下
                propertiesMap.put(keyStr, new String(props.getProperty(keyStr).getBytes("ISO-8859-1"), "utf-8"));
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            catch (java.lang.Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public static String getProperty(String key)
    {
        String value = propertiesMap.get(key);
        return null == value ? "" : value;
    }
    
    public static Map<String, String> getAllProperty()
    {
        return propertiesMap;
    }
}
