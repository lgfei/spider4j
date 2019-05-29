package com.lgfei.tool.spider.common.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgfei.tool.spider.common.dispatcher.BaseRequestAcceptor;

/**
 * 分发servlet
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tianqiuming
 * @version  [版本号, 2017年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RequestAcceptorServlet extends HttpServlet
{
    private static final long serialVersionUID = 2600143224322327782L;
    
    @Autowired
    private transient BaseRequestAcceptor acceptor;
    
    /**
     * <默认构造函数>
      *
     */
    public RequestAcceptorServlet()
    {
    }
    
    /**
     * <默认构造函数>
      * @param acceptor 消息分发器
     */
    public RequestAcceptorServlet(BaseRequestAcceptor acceptor)
    {
        this.acceptor = acceptor;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        // Action调用入口
        acceptor.doAction(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        this.doGet(req, resp);
    }
}
