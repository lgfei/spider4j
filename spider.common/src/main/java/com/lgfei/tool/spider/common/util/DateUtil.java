package com.lgfei.tool.spider.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil
{
    public static String dateToString(String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }
    
    public static String dateToString(Date date, String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
    public static String dateToString(long date, String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
