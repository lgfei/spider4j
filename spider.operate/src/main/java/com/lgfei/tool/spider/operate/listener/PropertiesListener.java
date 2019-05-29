package com.lgfei.tool.spider.operate.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

import com.lgfei.tool.spider.common.util.PropertiesUtil;

/**
 * 配置文件监听器，用来加载自定义配置文件
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2018年3月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PropertiesListener implements ApplicationListener<ApplicationEvent>
{
    private String propertyFileNames;
    
    public PropertiesListener(String propertyFileNames)
    {
        this.propertyFileNames = propertyFileNames;
    }
    
    @Override
    public void onApplicationEvent(ApplicationEvent event)
    {
        if (StringUtils.isEmpty(propertyFileNames))
        {
            return;
        }
        String[] arr = propertyFileNames.split(",");
        PropertiesUtil.loadAllProperties(arr);
    }
    
}
