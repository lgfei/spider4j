package com.lgfei.tool.spider.webmagic;

import java.util.List;

public interface Crawler<TASK, RULE>
{
    void execRun(String... seeds);
    
    void execRun(TASK task, List<RULE> rules);
    
    void execStop(String uuid);
}
