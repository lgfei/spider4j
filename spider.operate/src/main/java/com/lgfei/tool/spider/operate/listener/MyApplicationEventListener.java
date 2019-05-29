package com.lgfei.tool.spider.operate.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskConfigService;

/**
 * 上下文创建完成后执行的事件监听器
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MyApplicationEventListener implements ApplicationListener<ApplicationEvent>
{
    private static final Logger LOG = LoggerFactory.getLogger(MyApplicationEventListener.class);
    
    @Override
    public void onApplicationEvent(ApplicationEvent event)
    {
        LOG.info("MyApplicationPreparedEventListener.onApplicationEvent begin...");
        // 在这里可以监听到Spring Boot的生命周期
        if (event instanceof ApplicationEnvironmentPreparedEvent)
        {
            // 初始化环境变量 
        }
        else if (event instanceof ApplicationPreparedEvent)
        {
            // 初始化完成 
        }
        else if (event instanceof ContextRefreshedEvent)
        {
            // 应用刷新 
            ContextRefreshedEvent thisEvent = (ContextRefreshedEvent)event;
            ApplicationContext appContext = thisEvent.getApplicationContext();
            initMvCrawler(appContext);
        }
        else if (event instanceof ApplicationReadyEvent)
        {
            // 应用已启动完成
        }
        else if (event instanceof ContextStartedEvent)
        {
            //应用启动，需要在代码动态添加监听器才可捕获 
        }
        else if (event instanceof ContextStoppedEvent)
        {
            // 应用停止 
        }
        else if (event instanceof ContextClosedEvent)
        {
            // 应用关闭 
        }
        else
        {
            LOG.info("未知的监听");
        }
        LOG.info("MyApplicationPreparedEventListener.onApplicationEvent end...");
    }
    
    /**
     * 影片爬虫的初始化
     * <功能详细描述>
     * @param appContext
     * @see [类、类#方法、类#成员]
     */
    private void initMvCrawler(ApplicationContext appContext)
    {
        TaskConfigService taskConfigService = appContext.getBean(TaskConfigService.class);
        try
        {
            taskConfigService.init();
        }
        catch (SystemException e)
        {
            LOG.error("影片爬虫任务初始化失败");
        }
    }
}