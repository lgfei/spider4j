package com.lgfei.tool.spider.common.dispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.lgfei.tool.spider.common.dispatcher.action.BaseAction;
import com.lgfei.tool.spider.common.enums.ResultCode;
import com.lgfei.tool.spider.common.exception.InnerException;
import com.lgfei.tool.spider.common.exception.ValidateException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.request.IRequest;
import com.lgfei.tool.spider.common.message.request.IRequestData;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.IResponse;
import com.lgfei.tool.spider.common.message.response.ListResultResponse;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.common.util.ParameterUtil;
import com.lgfei.tool.spider.common.validation.Validator;

/**
 * Http请求接收、校验、分发类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseRequestAcceptor
{
    private final static Logger LOG = LoggerFactory.getLogger(BaseRequestAcceptor.class);
    
    private static Map<String, BaseAction<IRequest, IRequestData, IResponse>> actions =
        new HashMap<String, BaseAction<IRequest, IRequestData, IResponse>>();
    
    /**
     * 注册action
     * <一句话功能简述>
     * <功能详细描述>
     * @param uri 接口
     * @param action 具体执行对象
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void registerAction(String uri, BaseAction action)
    {
        LOG.info("registerAction:[uri:{}]", uri);
        actions.put(uri, action);
    }
    
    /**
     * action分发入口
     * <功能详细描述>
     * @param request 请求体
     * @param response 响应体
     * @throws IOException IO异常
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public void doAction(HttpServletRequest request, HttpServletResponse response)
    {
        IResponse resp = new BaseResponse();
        String uri = request.getRequestURI();
        BaseAction<IRequest, IRequestData, IResponse> action = actions.get(uri);
        if (action == null)
        {
            LOG.error("This action not be register, uri:{}", uri);
            resp = new BaseResponse(ResultCode.REQUEST_INVALID);
            writeOutputStream(response, resp);
            return;
        }
        // 初始化日志体
        action.doStartLog();
        
        //业务逻辑处理开始时间
        long startTime = System.currentTimeMillis();
        // 具体的异常消息
        String exceptionMsg = "";
        //初始化请求参数
        JSONObject json = null;
        try
        {
            ParameterizedType type = (ParameterizedType)action.getClass().getAnnotatedSuperclass().getType();
            Type[] arguments = type.getActualTypeArguments();
            Class<IRequest> reqClass = (Class<IRequest>)arguments[0];
            Class<IRequestData> dataClass = (Class<IRequestData>)arguments[1];
            Class<IResponse> respClass = null;
            String respTypeName = arguments[2].getTypeName();
            if (respTypeName.contains(PageResultResponse.class.getName()))
            {
                respClass = (Class<IResponse>)Class.forName(PageResultResponse.class.getName());
            }
            else if (respTypeName.contains(ListResultResponse.class.getName()))
            {
                respClass = (Class<IResponse>)Class.forName(ListResultResponse.class.getName());
            }
            else
            {
                respClass = (Class<IResponse>)arguments[2];
            }
            // 收集参数
            json = ParameterUtil.newInstance().collectParamJson(request);
            
            // 请求体的所有参数
            IRequest req = JSONObject.parseObject(JSONObject.toJSONString(json), reqClass);
            // 请求参数中的业务相关参数(data)
            BaseRequest baseReq = (BaseRequest)req;
            JSONObject dataJson = JSON.parseObject(baseReq.getData());
            IRequestData reqData = JSONObject.parseObject(JSONObject.toJSONString(dataJson), dataClass);
            if (null == reqData)
            {
                reqData = dataClass.newInstance();
            }
            // 响应结果
            resp = respClass.newInstance();
            
            // 签名校验 + 固定请求参数校验
            action.beforeExecute(req, reqData, resp, json);
            // 执行业务参数校验
            Validator.getInstance().validate(dataClass, reqData);
            // 所有校验完成之后 做trim
            JSONObject trimDataJson = (JSONObject)trimJSON(JSONObject.toJSON(reqData));
            reqData = JSONObject.parseObject(JSONObject.toJSONString(trimDataJson), dataClass);
            
            // 执行业务逻辑
            action.execute(req, reqData, resp);
            //执行其他操作
            action.doOthers(request, response, req, reqData, resp);
        }
        catch (ValidateException e)
        {
            resp = new BaseResponse(e);
            exceptionMsg = e.getContent();
            LOG.error("校验异常,msg:{},content:{}", e.getErrorDesc(), exceptionMsg);
        }
        catch (InnerException e)
        {
            resp = new BaseResponse(e);
            exceptionMsg = e.getMessage();
            LOG.error("内部异常,msg:{},content:{}", e.getErrorDesc(), exceptionMsg);
        }
        catch (Exception e)
        {
            resp = new BaseResponse(ResultCode.INNER_ERROR);
            exceptionMsg = e.getMessage();
            LOG.error("内部异常,content:{}", exceptionMsg);
        }
        finally
        {
            // 构建日志
            String logLevel = resp.getRetCode() == ResultCode.SUCCESS.getRetCode() ? "INFO" : "WARN";
            action.getLogEvt().setLogLevel(logLevel);
            action.getLogEvt().setDest(uri);
            action.getLogEvt().setStartTime(startTime);
            action.getLogEvt().setExceptionMsg(resp.getRetMsg() + ":" + exceptionMsg);
            // 记录接口日志
            action.doEndLog();
            //业务逻辑处理开始时间
            long endTime = System.currentTimeMillis();
            //记录特定接口日志
            LOG.info("|{}|{}|{}", uri, resp.getRetCode(), endTime - startTime);
            
        }
        writeOutputStream(response, resp);
    }
    
    /**
     * 响应结果输出
     * <功能详细描述>
     * @param response 响应对象
     * @param resp 响应结果
     * @throws IOException IO异常
     * @see [类、类#方法、类#成员]
     */
    private void writeOutputStream(HttpServletResponse response, IResponse resp)
    {
        // 文件流无需写返回
        if ("application/octet-stream;charset=utf-8".equalsIgnoreCase(response.getContentType()))
        {
            return;
        }
        
        PrintWriter out = null;
        try
        {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            out = response.getWriter();
            JSONObject jsonObj = JSON.parseObject(JSON.toJSONString(resp));
            out.print(jsonObj.toJSONString());
            out.flush();
        }
        catch (IOException e)
        {
            LOG.error("write msg error.", e);
        }
        finally
        {
            IOUtils.close(out);
        }
    }
    
    /**
     * json 去除前后空格
     * <功能详细描述>
     * @param json jsonObject 或者 jsonArray
     * @return 去空格后对象
     * @see [类、类#方法、类#成员]
     */
    public Object trimJSON(Object json)
    {
        //空时返回空
        if (json == null)
        {
            return null;
        }
        
        if (json instanceof JSONObject)
        {
            //key 类型为jsonobject
            JSONObject returnJson = new JSONObject();
            JSONObject jsonObj = (JSONObject)json;
            for (Entry<String, Object> entry : jsonObj.entrySet())
            {
                String key = entry.getKey();
                Object value = entry.getValue();
                returnJson.put(key, trimJSON(value));
            }
            
            return returnJson;
        }
        else if (json instanceof JSONArray)
        {
            //key 类型为JSONArray
            JSONArray returnArray = new JSONArray();
            JSONArray jsonArray = (JSONArray)json;
            int size = jsonArray.size();
            for (int pos = 0; pos < size; pos++)
            {
                returnArray.add(trimJSON(jsonArray.get(pos)));
            }
            
            return returnArray;
        }
        else if (json instanceof String)
        {
            //key 类型为String
            String str = (String)json;
            return str.trim();
        }
        else
        {
            //原样返回
            return json;
        }
        
    }
}
