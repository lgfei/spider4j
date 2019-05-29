package com.lgfei.tool.spider.operate.mvcrawler.enums;

/**
 * 启动状态枚举类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum RunStatusEnum
{
    READY(1, "待启动"), STARTED(2, "已启动"), RUNNING(3, "正在运行"), STOPPING(4, "正在停止"), STOPED(5, "已停止");
    
    private RunStatusEnum(Integer value, String name)
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
