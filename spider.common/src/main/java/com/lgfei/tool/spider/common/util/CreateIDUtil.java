package com.lgfei.tool.spider.common.util;

import com.lgfei.tool.spider.common.constant.NumberKeys;

public final class CreateIDUtil
{
    private static final char[] CHAR_32 = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
        'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};
    
    private static final int REMAIN_SIZE = 15;
    
    /**
     * 随机字符数字组合
     * <功能详细描述>
     * @param size
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getRandom(int size)
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; i++)
        {
            s.append(CHAR_32[((int)(Math.random() * 1000000)) % CHAR_32.length]);
        }
        return s.toString();
    }
    
    /**
     * 生成业务ID
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String businessID(String moudel)
    {
        moudel = null == moudel ? "" : moudel;
        int randomSize = REMAIN_SIZE - moudel.length();
        if (randomSize < 0)
        {
            moudel = moudel.substring(NumberKeys.NUM_0, REMAIN_SIZE);
        }
        String timeStr = DateUtil.dateToString("yyyyMMddhhmmssSS");
        String randomStr = "";
        if (randomSize > 0)
        {
            randomStr = getRandom(randomSize);
        }
        
        StringBuffer id = new StringBuffer(moudel).append(timeStr).append(randomStr);
        
        return id.toString();
    }
}
