package com.lgfei.tool.spider.crawler4j.service.impl;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lgfei.tool.spider.crawler4j.MyCrawler;
import com.lgfei.tool.spider.crawler4j.service.MyCrawlerSerivce;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * crawler4j框架爬虫数据处理实现类
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2017年8月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class MyCrawlerSerivceImpl implements MyCrawlerSerivce
{
    private static final Logger LOG = LoggerFactory.getLogger(MyCrawlerSerivceImpl.class);
    
    /**
     * 定义7个爬虫，也就是7个线程
     */
    private static final int NUMBER_OF_CRAWLERS = 7;
    
    /**
     * 抓取最大深度
     */
    private static final int MAXDEPTH = 0;
    
    @Override
    public void execute(String[] seeds)
        throws Exception
    {
        LOG.info("MyCrawlerSerivceImpl.execute begin.");
        if (ArrayUtils.isEmpty(seeds))
        {
            throw new Exception("没有指定种子页面");
        }
        // 定义爬虫数据存储位置
        String crawlStorageFolder = "f:/temp/crawl";
        
        // 定义爬虫配置
        CrawlConfig config = new CrawlConfig();
        // 设置爬虫文件存储位置
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(MAXDEPTH);
        
        // 实例化页面获取器
        PageFetcher pageFetcher = new PageFetcher(config);
        // 实例化爬虫机器人配置 比如可以设置 user-agent
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        
        // 实例化爬虫机器人对目标服务器的配置，每个网站都有一个robots.txt文件 规定了该网站哪些页面可以爬，哪些页面禁止爬，该类是对robots.txt规范的实现
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        // 实例化爬虫控制器
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        
        // 配置爬虫种子页面，就是规定的从哪里开始爬，可以配置多个种子页面
        for (String pageUrl : seeds)
        {
            controller.addSeed(pageUrl);
        }
        
        // 启动爬虫，爬虫从此刻开始执行爬虫任务，根据以上配置
        controller.start(MyCrawler.class, NUMBER_OF_CRAWLERS);
        LOG.info("MyCrawlerSerivceImpl.execute end.");
    }
    
}
