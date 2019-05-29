package com.lgfei.tool.spider.operate.mvcrawler.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.lgfei.tool.spider.common.cache.service.RedisService;
import com.lgfei.tool.spider.common.constant.NumberKeys;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskConfigVO;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskRuleVO;
import com.lgfei.tool.spider.webmagic.Crawler;

import us.codecraft.webmagic.Spider;

/**
 * WebMagic框架爬虫数据处理实现类
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2017年8月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class MvCrawler implements Crawler<TaskConfigVO, TaskRuleVO>
{
    private static final Logger LOG = LoggerFactory.getLogger(MvCrawler.class);
    
    /**
     * 每个爬虫启动的线程数
     */
    private static final int THREAD_NUM = 1;
    
    @Autowired
    private RedisService redisService;
    
    /**
     * 正在运行的爬虫的容器
     */
    private Map<String, Spider> runningSpiders = new HashMap<>();
    
    @Override
    public void execRun(String... seeds)
    {
        LOG.info("MvCrawler.execRun begin.");
        if (ArrayUtils.isEmpty(seeds))
        {
            LOG.info("没有指定种子页面");
            return;
        }
        Spider spider = Spider.create(new MvPageProcessor()).addUrl(seeds).thread(THREAD_NUM);
        // run(),启动，会阻塞当前线程执行
        // spider.run();
        // start()/runAsync(),异步启动，当前线程继续执行
        spider.runAsync();
        // 记录正在运行的爬虫
        runningSpiders.put(spider.getUUID(), spider);
        LOG.info("MvCrawler.execRun end.");
    }
    
    @Override
    public void execRun(TaskConfigVO task, List<TaskRuleVO> rules)
    {
        LOG.info("MvCrawler.execRun begin.");
        if (null == task || CollectionUtils.isEmpty(rules))
        {
            LOG.info("爬取规则配置错误");
            return;
        }
        
        int start = null == task.getMinPagenum() ? NumberKeys.NUM_1 : task.getMinPagenum();
        int end = null == task.getMaxPagenum() ? NumberKeys.NUM_1 : task.getMaxPagenum();
        Integer pageSize = task.getPageSize();
        int offset = NumberKeys.NUM_1;
        // 如果是通过内容数实现分页的，则需要以下的计算
        if (null != pageSize)
        {
            start = (start - 1) * pageSize;
            end = (end - 1) * pageSize;
            offset = pageSize;
        }
        
        List<String> targetUrls = new ArrayList<>();
        for (int i = start; i <= end; i = i + offset)
        {
            targetUrls.add(String.format(task.getTarget(), i));
        }
        
        // 添加特例
        String specials = task.getSpecials();
        if (!StringUtils.isEmpty(specials))
        {
            String[] specialUrls = specials.split(",");
            for (String specialUrl : specialUrls)
            {
                targetUrls.add(specialUrl);
            }
        }
        
        Spider spider = Spider.create(new MvPageProcessor(task, rules, redisService))
            .addUrl(targetUrls.toArray(new String[targetUrls.size()]))
            .thread(THREAD_NUM)
            .setUUID(task.getWebsiteId());
        // 添加管道
        spider.addPipeline(new MvPipeline());
        // run(),启动，会阻塞当前线程执行
        // spider.run();
        // start()/runAsync(),异步启动，当前线程继续执行
        spider.runAsync();
        // 记录正在运行的爬虫
        runningSpiders.put(task.getTaskId(), spider);
        LOG.info("MvCrawler.execRun end.");
    }
    
    @Override
    public void execStop(String uuid)
    {
        Spider spider = runningSpiders.get(uuid);
        if (null == spider)
        {
            return;
        }
        // 停止爬虫
        spider.stop();
        // 移除记录
        runningSpiders.remove(uuid);
    }
    
}
