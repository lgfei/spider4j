package com.lgfei.tool.spider.test;

import com.lgfei.tool.spider.common.util.DesUtil;

public class OtherTest
{
    public static void main(String[] args)
    {
        String s = "12345";
        System.out.println(s.substring(0, s.length() - 1));
        
        try
        {
            String password = DesUtil.decrypt("Hmtkf7LUn6s=", "cGVmJl4xMjM=");
            System.out.println(password);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
