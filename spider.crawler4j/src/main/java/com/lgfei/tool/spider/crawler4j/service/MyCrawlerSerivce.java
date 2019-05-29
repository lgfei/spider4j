package com.lgfei.tool.spider.crawler4j.service;

/**
 * crawler4j框架爬虫数据处理接口
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2017年8月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface MyCrawlerSerivce
{
    /**
     * 执行网站数据爬取
     * <功能详细描述>
     * @param seeds 种子页面url
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    void execute(String[] seeds)
        throws Exception;
}
