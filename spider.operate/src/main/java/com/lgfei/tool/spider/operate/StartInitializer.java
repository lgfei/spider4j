package com.lgfei.tool.spider.operate;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.lgfei.tool.spider.operate.listener.MyApplicationEventListener;
import com.lgfei.tool.spider.operate.listener.PropertiesListener;

/**
 * 注册启动类
 * <用war包方式部署时用到>
 * 
 * @author  lgfei
 * @version  [版本号, 2018年4月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StartInitializer extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
    {
        // 添加监听器
        builder.application().addListeners(new MyApplicationEventListener());
        builder.application()
            .addListeners(new PropertiesListener("project-config.properties,mvcrawler-config.properties"));
        // 加载启动类
        return builder.sources(Application.class);
    }
}
