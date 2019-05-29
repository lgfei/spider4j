package com.lgfei.tool.spider.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lgfei.tool.spider.crawler4j.service.MyCrawlerSerivce;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.lgfei.tool.spider.crawler4j.service.impl.MyCrawlerSerivceImpl.class)
public class MyCrawlerServiceTest
{
    @Autowired
    private MyCrawlerSerivce service;
    
    @Test
    public void testExecute()
    {
        String[] seeds = {"https://lgfei.github.io/"};
        try
        {
            service.execute(seeds);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
