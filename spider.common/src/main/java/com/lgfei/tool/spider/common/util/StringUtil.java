package com.lgfei.tool.spider.common.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lgfei.tool.spider.common.constant.NumberKeys;

/**
 * 字符串处理工具类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class StringUtil
{
    /**
     * 比较两个字符是否相等
     * <功能详细描述>
     * @param str1 字符1
     * @param str2 字符2
     * @return true相等 ，false不相等
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEqual(String str1, String str2)
    {
        if (null == str1 && null == str2)
        {
            return true;
        }
        
        if (null != str1 && null != str2)
        {
            return str1.equals(str2);
        }
        return false;
    }
    
    /**
     * 比较mainStr是否是depeStrs的一个
     * <功能详细描述>
     * @param mainStr
     * @param depeStrs
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean in(String mainStr, String[] depeStrs)
    {
        if (null == depeStrs || depeStrs.length == 0)
        {
            return false;
        }
        for (String str : depeStrs)
        {
            if (isEqual(mainStr, str))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 大于比较
     * <功能详细描述>
     * @param mainStr
     * @param depeStr
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isGreater(String mainStr, String depeStr)
    {
        try
        {
            Integer int1 = Integer.parseInt(mainStr);
            Integer int2 = Integer.parseInt(depeStr);
            
            return Integer.compare(int1, int2) == NumberKeys.NUM_1;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    /**
     * 小于比较
     * <功能详细描述>
     * @param mainStr
     * @param depeStr
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLess(String mainStr, String depeStr)
    {
        try
        {
            Integer int1 = Integer.parseInt(mainStr);
            Integer int2 = Integer.parseInt(depeStr);
            
            return Integer.compare(int1, int2) == NumberKeys.MNUM_1;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    /**
     * 模糊匹配
     * <功能详细描述>
     * @param mainStr
     * @param regex
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLike(String mainStr, String regex)
    {
        if (null == mainStr || null == regex)
        {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mainStr);
        return matcher.matches();
        
    }
    
    /**
     * string集合拼接成逗号分隔的字符串
     * <功能详细描述>
     * @param strList 字符串集合
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String list2String(Collection<String> strList)
    {
        StringBuffer strings = new StringBuffer();
        for (String str : strList)
        {
            strings.append(str).append(',');
        }
        return strings.toString().substring(NumberKeys.NUM_0, strings.length() - 1);
    }
    
    /** 
     * 判断字符是否为空null ''
     * <功能详细描述>
     * @param strings 字符
     * @return true为空 。false不为空
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(String... strs)
    {
        for (String str : strs)
        {
            if (str == null || "".equals(str))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 判断是否整数字符
     * <功能详细描述>
     * @param str 字符
     * @return true为整数 ，false不为整数
     * @see [类、类#方法、类#成员]
     */
    public static boolean isInteger(String str)
    {
        if (isEmpty(str))
        {
            return false;
        }
        try
        {
            Integer.parseInt(str);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * 判断是否长整型字符
     * <功能详细描述>
     * @param str 字符
     * @return true为长整数 ，false不为长整数
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLong(String str)
    {
        if (isEmpty(str))
        {
            return false;
        }
        try
        {
            Long.parseLong(str);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        
        return true;
    }
    
}
