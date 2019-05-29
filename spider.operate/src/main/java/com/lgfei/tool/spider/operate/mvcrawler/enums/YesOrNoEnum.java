package com.lgfei.tool.spider.operate.mvcrawler.enums;

/**
 * 是否枚举类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum YesOrNoEnum
{
    YES(1, "是"), NO(0, "否");
    
    private YesOrNoEnum(Integer value, String name)
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
