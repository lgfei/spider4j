package com.lgfei.tool.spider.operate.mvcrawler.controller.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;

import com.alibaba.fastjson.JSONObject;
import com.lgfei.tool.spider.common.dispatcher.action.BaseAction;
import com.lgfei.tool.spider.common.exception.InnerException;
import com.lgfei.tool.spider.common.message.request.BaseRequest;
import com.lgfei.tool.spider.common.message.request.BaseRequestData;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.util.ReflectUtil;

/**
 * Action的抽象父类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2018年3月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractMvCrawlerAction<REQ extends BaseRequest, DATA extends BaseRequestData, RSP extends BaseResponse>
    extends BaseAction<REQ, DATA, RSP>
{
    private static final Logger LOG = LoggerFactory.getLogger(AbstractMvCrawlerAction.class);
    
    /**
     * 接口名称
     */
    private String actionName;
    
    public AbstractMvCrawlerAction(String actionName)
    {
        this.actionName = actionName;
    }
    
    @Override
    protected abstract void action(REQ req, DATA data, RSP resp);
    
    @SuppressWarnings("unchecked")
    @Override
    protected Map<String, Object> getDataMap(DATA data, RSP resp)
    {
        return BeanMap.create(data);
    }
    
    @Override
    public void beforeExecute(REQ req, DATA data, RSP resp, JSONObject json)
        throws InnerException
    {
        if (null == data)
        {
            return;
        }
        // 追加分页参数
        try
        {
            data.getClass().getDeclaredField("pageNum");
            data.getClass().getDeclaredField("pageSize");
            if (json.containsKey("page") && json.containsKey("rows"))
            {
                int page = json.getIntValue("page");
                int rows = json.getIntValue("rows");
                ReflectUtil.setValue(data, "pageNum", page);
                ReflectUtil.setValue(data, "pageSize", rows);
            }
        }
        catch (NoSuchFieldException | SecurityException e)
        {
            // 不需要分页参数
            LOG.info("该接口不需要分页");
        }
    }
    
    @Override
    public abstract void doOthers(HttpServletRequest request, HttpServletResponse response, REQ req, DATA data,
        RSP resp);
    
    public String getActionName()
    {
        return actionName;
    }
    
    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }
}
