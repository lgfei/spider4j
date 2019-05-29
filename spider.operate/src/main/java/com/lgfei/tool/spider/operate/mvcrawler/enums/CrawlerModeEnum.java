package com.lgfei.tool.spider.operate.mvcrawler.enums;

/**
 * 爬取模式枚举类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum CrawlerModeEnum
{
    DETAIL(1, "详情"), LIST(2, "列表");
    
    private CrawlerModeEnum(Integer value, String name)
    {
        this.value = value;
        this.name = name;
    }
    
    /**
     * 值
     */
    private Integer value;
    
    /**
     * 名称
     */
    private String name;
    
    public Integer getValue()
    {
        return value;
    }
    
    public void setValue(Integer value)
    {
        this.value = value;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
}
