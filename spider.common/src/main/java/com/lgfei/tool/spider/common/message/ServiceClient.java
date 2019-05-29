package com.lgfei.tool.spider.common.message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgfei.tool.spider.common.constant.NumberKeys;
import com.lgfei.tool.spider.common.enums.ResultCode;
import com.lgfei.tool.spider.common.exception.InnerException;
import com.lgfei.tool.spider.common.message.annotations.Location;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.response.IResponse;
import com.lgfei.tool.spider.common.util.PropertiesUtil;
import com.lgfei.tool.spider.common.util.ReflectUtil;

/**
 * 请求发送器
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年10月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class ServiceClient
{
    /**
     * 日志记录
     */
    private static final Logger LOG = LoggerFactory.getLogger(ServiceClient.class);
    
    /**
     * 初始化
     */
    private static final ServiceClient INSTANCE = new ServiceClient();
    
    /**
     * <私有构造函数>
     */
    private ServiceClient()
    {
    }
    
    /**
     * 实例化方法
     * <功能详细描述>
     * @return ServiceClient
     * @see [类、类#方法、类#成员]
     */
    public static ServiceClient newInstance()
    {
        return INSTANCE;
    }
    
    /**
     * 外部调用入口
     * <功能详细描述>
     * @param context 请求体
     * @param <T> 响应类
     * @return 响应体
     * @throws InnerException 内部异常
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public <T extends IResponse> T call(MsgSenderContext context)
        throws InnerException
    {
        Location location = context.getLocation();
        String uri = location.uri();
        
        if (location.module().equals(PropertiesUtil.getProperty("project.serviceName")))
        {
            return call(context.getMsgBody(), context.getRemoteUrl(), uri, (Class<T>)context.getRespClazz());
        }
        throw new InnerException(ResultCode.INNER_ERROR.getRetCode());
    }
    
    /**
     * 利用httpclient调用远程的服务
     * <功能详细描述>
     * @param params 请求参数
     * @param baseUrl 服务器地址
     * @param uri 接口路径
     * @param clazz 响应对象
     * @return 响应消息
     * @throws InnerException 内部异常
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("all")
    private static <T> T call(String params, String baseUrl, String uri, Class<T> clazz)
        throws InnerException
    {
        RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(NumberKeys.NUM_60000)
            .setConnectTimeout(NumberKeys.NUM_60000)
            .build();
        
        CloseableHttpClient client = HttpClients.createDefault();
        try
        {
            StringBuilder url = new StringBuilder();
            url.append(baseUrl);
            url.append(uri);
            
            HttpPost post = new HttpPost(url.toString());
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            post.setConfig(requestConfig);
            
            BaseRequest baseRequest = JSONObject.parseObject(params, BaseRequest.class);
            Field[] fields = baseRequest.getClass().getDeclaredFields();
            
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Field field : fields)
            {
                String paramKey = field.getName();
                Object paramObj = ReflectUtil.getValue(baseRequest, paramKey);
                String paramValue = null == paramObj ? null : String.valueOf(paramObj);
                list.add(new BasicNameValuePair(paramKey, paramValue));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded;charset=utf-8");
            post.setEntity(formEntity);
            
            HttpResponse res = client.execute(post);
            
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity entity = res.getEntity();
                
                String result = EntityUtils.toString(entity);
                
                T t = JSONObject.toJavaObject(JSON.parseObject(result), clazz);
                
                return t;
            }
        }
        catch (UnsupportedEncodingException e)
        {
            LOG.error("Service request failed...", e);
            throw new InnerException(ResultCode.INNER_ERROR.getRetCode(), ResultCode.INNER_ERROR.getRetMsg());
        }
        catch (ClientProtocolException e)
        {
            LOG.error("Service request failed...", e);
            throw new InnerException(ResultCode.REQUEST_EXPIRED.getRetCode(), ResultCode.REQUEST_EXPIRED.getRetMsg());
        }
        catch (IOException e)
        {
            LOG.error("Service request failed...", e);
            throw new InnerException(ResultCode.INNER_ERROR.getRetCode(), ResultCode.INNER_ERROR.getRetMsg());
        }
        finally
        {
            try
            {
                client.close();
            }
            catch (IOException e)
            {
                LOG.error("Service close IO failed...");
            }
        }
        throw new InnerException(ResultCode.INNER_ERROR.getRetCode());
    }
}
