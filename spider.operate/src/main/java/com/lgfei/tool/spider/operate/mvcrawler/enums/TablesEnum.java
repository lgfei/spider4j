package com.lgfei.tool.spider.operate.mvcrawler.enums;

public enum TablesEnum
{
    /**
     * 
     */
    T_MV_INFO("t_mv_info", "mv", "影片基本信息表"),
    /**
     * 
     */
    T_MV_SOURCE("t_mv_source", "mvsource", "影片播放源信息表"),
    /**
     * 
     */
    T_TASK_CONFIG("t_task_config", "task", "爬虫任务配置表"),
    /**
     * 
     */
    T_TASK_RULE("t_task_rule", "rule", "任务抓取规则表"),
    /**
     * 
     */
    T_WEBSITE_INFO("t_website_info", "website", "网站信息表");
    
    private TablesEnum(String name, String module, String desc)
    {
        this.name = name;
        this.module = module;
        this.desc = desc;
    }
    
    /**
     * 表名
     */
    private String name;
    
    /**
     * 模块名
     */
    private String module;
    
    /**
     * 描述
     */
    private String desc;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getModule()
    {
        return module;
    }
    
    public void setModule(String module)
    {
        this.module = module;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
}
