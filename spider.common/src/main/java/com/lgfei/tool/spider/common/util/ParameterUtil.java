package com.lgfei.tool.spider.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lgfei.tool.spider.common.constant.Constant;
import com.lgfei.tool.spider.common.constant.NumberKeys;
import com.lgfei.tool.spider.common.exception.InnerException;

/**
 * 请求参数处理工具类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class ParameterUtil
{
    
    private static final Logger LOG = LoggerFactory.getLogger(ParameterUtil.class);
    
    /**
     * 初始化对象
     */
    private static final ParameterUtil INSTANCE = new ParameterUtil();
    
    /**
     * POST
     */
    private static final String METHOD_POST = "POST";
    
    /**
     * <默认构造函数>
     */
    private ParameterUtil()
    {
    }
    
    /**
     * 实例化对象方法
     * <功能详细描述>
     * @return ParameterUtil
     * @see [类、类#方法、类#成员]
     */
    public static ParameterUtil newInstance()
    {
        return INSTANCE;
    }
    
    /**
     * 采集参数
     * <将请求参数收集，按Map格式返回>
     * @param request 请求体
     * @return Map<String, String> 结果
     * @throws IOException IO异常
     * @see [类、类#方法、类#成员]
     */
    public Map<String, String> collectParamMap(HttpServletRequest request)
        throws IOException
    {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet())
        {
            String paramVal = entry.getValue()[0];
            String paramKey = entry.getKey();
            map.put(paramKey, paramVal);
        }
        return map;
    }
    
    /**
     * 采集参数
     * <将请求参数收集，按Json格式返回>
     * @param request 请求体
     * @return JSONObject 结果
     * @throws InnerException 内部异常
     * @see [类、类#方法、类#成员]
     */
    public JSONObject collectParamJson(HttpServletRequest request)
        throws InnerException
    {
        JSONObject json = null;
        // 如果是post的请求，要从流中读取数据
        if (METHOD_POST.equals(request.getMethod()))
        {
            json = getPostParam(request);
            if (null != json && !json.isEmpty())
            {
                return json;
            }
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        json = new JSONObject();
        
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet())
        {
            String paramKey = entry.getKey();
            String paramVal = entry.getValue()[NumberKeys.NUM_0];
            json.put(paramKey, paramVal);
        }
        return json;
    }
    
    /**
     * 获取POST请求消息体
     * <功能详细描述>
     * @param request 请求体
     * @return JSONObject
     * @see [类、类#方法、类#成员]
     */
    private JSONObject getPostParam(HttpServletRequest request)
    {
        JSONObject json = new JSONObject();
        try
        {
            BufferedReader reader = request.getReader();
            
            char[] buff = new char[NumberKeys.NUM_1024];
            StringBuilder sb = new StringBuilder();
            int len;
            while ((len = reader.read(buff)) != -1)
            {
                sb.append(buff, 0, len);
            }
            
            // 请求参数体String
            String paramStr = sb.toString();
            // 将参数体转换为Json
            try
            {
                json = JSON.parseObject(paramStr);
            }
            catch (JSONException e)
            {
                // 解析非json的参数
                String[] params = paramStr.split("&");
                for (String str : params)
                {
                    int pos = str.indexOf("=");
                    if (pos > NumberKeys.MNUM_1)
                    {
                        json.put(str.substring(NumberKeys.NUM_0, pos),
                            URLDecoder.decode(str.substring(pos + NumberKeys.NUM_1), Constant.UTF8));
                    }
                    else
                    {
                        json.put(str, null);
                    }
                }
            }
        }
        catch (IOException e)
        {
            LOG.error("读取请求参数异常" + e);
        }
        return json;
    }
}
