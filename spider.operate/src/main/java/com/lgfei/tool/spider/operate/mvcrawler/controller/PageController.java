package com.lgfei.tool.spider.operate.mvcrawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/mvcrawler")
public class PageController
{
    private static final Logger LOG = LoggerFactory.getLogger(PageController.class);
    
    @RequestMapping(value = "/mainpage", method = RequestMethod.GET)
    public String mainPage()
    {
        LOG.info("enter PageController.mainPage");
        return "mvcrawler/main";
    }
    
    @RequestMapping(value = "/westpage", method = RequestMethod.GET)
    public String westPage()
    {
        LOG.info("enter PageController.westPage");
        return "mvcrawler/west";
    }
    
    @RequestMapping(value = "/northpage", method = RequestMethod.GET)
    public String northPage()
    {
        LOG.info("enter PageController.northPage");
        return "mvcrawler/north";
    }
    
    @RequestMapping(value = "/southpage", method = RequestMethod.GET)
    public String southPage()
    {
        LOG.info("enter PageController.southPage");
        return "mvcrawler/south";
    }
    
    @RequestMapping(value = "/task/listpage", method = RequestMethod.GET)
    public String taskListPage()
    {
        LOG.info("enter PageController.taskListPage");
        return "mvcrawler/tasklist";
    }
    
    @RequestMapping(value = "/website/listpage", method = RequestMethod.GET)
    public String websiteListPage()
    {
        LOG.info("enter PageController.websiteListPage");
        return "mvcrawler/websitelist";
    }
    
    @RequestMapping(value = "/mv/listpage", method = RequestMethod.GET)
    public String mvListPage()
    {
        LOG.info("enter PageController.mvListPage");
        return "mvcrawler/mvlist";
    }
    
    @RequestMapping(value = "/cache/listpage", method = RequestMethod.GET)
    public String cacheListPage()
    {
        LOG.info("enter PageController.cacheListPage");
        return "mvcrawler/cachelist";
    }
}
