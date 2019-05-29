package com.lgfei.tool.spider.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lgfei.tool.spider.operate.Application;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskConfigVO;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskRuleVO;
import com.lgfei.tool.spider.webmagic.Crawler;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MvCrawlerTest
{
    @Autowired
    private Crawler<TaskConfigVO, TaskRuleVO> crawler;
    
    @Test
    public void testExecute()
    {
        String[] seeds = {"http://piaohuatv.co/dianying/index.html"};
        try
        {
            crawler.execRun(seeds);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
