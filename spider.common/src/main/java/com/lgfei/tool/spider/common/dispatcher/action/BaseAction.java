package com.lgfei.tool.spider.common.dispatcher.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.lgfei.tool.spider.common.exception.InnerException;
import com.lgfei.tool.spider.common.log.InterfaceLogEvt;
import com.lgfei.tool.spider.common.log.LogUtil;
import com.lgfei.tool.spider.common.message.request.IRequest;
import com.lgfei.tool.spider.common.message.request.IRequestData;
import com.lgfei.tool.spider.common.message.response.IResponse;

/**
 * Action业务逻辑抽象父类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @param <REQ> request请求公共参数
 * @param <DATA> request请求参数
 * @param <RESP> 返回数据
 * @version  [版本号, 2017年10月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseAction<REQ extends IRequest, DATA extends IRequestData, RESP extends IResponse>
{
    /**
     * 通用的接口日志结构体
     */
    private InterfaceLogEvt logEvt;
    
    /**
     * 具体的动作
     * <一句话功能简述>
     * <功能详细描述>
     * @param req request请求公共参数
     * @param data request请求参数
     * @param resp 返回数据
     * @see [类、类#方法、类#成员]
     */
    protected abstract void action(REQ req, DATA data, RESP resp);
    
    /**
     * 执行
     * <一句话功能简述>
     * <功能详细描述>
     * @param req request请求公共参数
     * @param data request请求参数
     * @param resp 返回数据
     * @see [类、类#方法、类#成员]
     */
    public void execute(REQ req, DATA data, RESP resp)
    {
        action(req, data, resp);
    }
    
    /**
     * 获取数据
     * <一句话功能简述>
     * <功能详细描述>
     * @param data request请求参数
     * @param resp 返回数据
     * @return 获取数据
     * @see [类、类#方法、类#成员]
     */
    protected abstract Map<String, Object> getDataMap(DATA data, RESP resp);
    
    /**
     * 执行之前
     * <一句话功能简述>
     * <功能详细描述>
     * @param req request请求公共参数
     * @param data request请求参数
     * @param resp 返回数据]
     * @param json 请求json
     * @throws InnerException 内部异常
     * @see [类、类#方法、类#成员]
     */
    public abstract void beforeExecute(REQ req, DATA data, RESP resp, JSONObject json)
        throws InnerException;
    
    /**
     * 开始记录日志
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public void doStartLog()
    {
        // 初始化日志体
        this.logEvt = new InterfaceLogEvt();
    }
    
    /**
     * 结束记录日志
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public void doEndLog()
    {
        // 记录接口日志
        LogUtil.print(this.getLogEvt());
    }
    
    /** 
     * 其他操作
     * <功能详细描述>
     * @param request request请求对象
     * @param response response响应对象
     * @param req request请求公共参数
     * @param data request请求参数
     * @param resp 返回数据
     * @see [类、类#方法、类#成员]
     */
    public abstract void doOthers(HttpServletRequest request, HttpServletResponse response, REQ req, DATA data,
        RESP resp);
    
    public InterfaceLogEvt getLogEvt()
    {
        return logEvt;
    }
    
    public void setLogEvt(InterfaceLogEvt logEvt)
    {
        this.logEvt = logEvt;
    }
    
}
