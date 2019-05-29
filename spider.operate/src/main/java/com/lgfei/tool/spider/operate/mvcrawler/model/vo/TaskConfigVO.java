package com.lgfei.tool.spider.operate.mvcrawler.model.vo;

import com.alibaba.fastjson.JSON;
import com.lgfei.tool.spider.common.vo.BaseVO;

public class TaskConfigVO extends BaseVO
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4455411452084841971L;
    
    /**
     * 网站id
     */
    private String websiteId;
    
    /**
     * 网站名称
     */
    private String websiteName;
    
    /**
     * 任务id
     */
    private String taskId;
    
    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 目标页面地址
     */
    private String target;
    
    /**
     * 目标地址正则
     */
    private String regex;
    
    /**
     * 爬取模式
     */
    private Integer mode;
    
    /**
     * 目标区域
     */
    private String targetArea;
    
    /**
     * 最小页码
     */
    private Integer minPagenum;
    
    /**
     * 最大页码
     */
    private Integer maxPagenum;
    
    /**
     * 每页内容数
     */
    private Integer pageSize;
    
    /**
     * 运行状态，1-待启动，2-已启动，3-正在运行，4-正则停止，5-已停止
     */
    private Integer runStatus;
    
    /**
     * 特例地址，多个用逗号分隔
     */
    private String specials;
    
    public String getWebsiteId()
    {
        return websiteId;
    }
    
    public void setWebsiteId(String websiteId)
    {
        this.websiteId = websiteId;
    }
    
    public String getWebsiteName()
    {
        return websiteName;
    }
    
    public void setWebsiteName(String websiteName)
    {
        this.websiteName = websiteName;
    }
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getTarget()
    {
        return target;
    }
    
    public void setTarget(String target)
    {
        this.target = target;
    }
    
    public String getRegex()
    {
        return regex;
    }
    
    public void setRegex(String regex)
    {
        this.regex = regex;
    }
    
    public Integer getMode()
    {
        return mode;
    }
    
    public void setMode(Integer mode)
    {
        this.mode = mode;
    }
    
    public String getTargetArea()
    {
        return targetArea;
    }
    
    public void setTargetArea(String targetArea)
    {
        this.targetArea = targetArea;
    }
    
    public Integer getMinPagenum()
    {
        return minPagenum;
    }
    
    public void setMinPagenum(Integer minPagenum)
    {
        this.minPagenum = minPagenum;
    }
    
    public Integer getMaxPagenum()
    {
        return maxPagenum;
    }
    
    public void setMaxPagenum(Integer maxPagenum)
    {
        this.maxPagenum = maxPagenum;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getRunStatus()
    {
        return runStatus;
    }
    
    public void setRunStatus(Integer runStatus)
    {
        this.runStatus = runStatus;
    }
    
    public String getSpecials()
    {
        return specials;
    }
    
    public void setSpecials(String specials)
    {
        this.specials = specials;
    }
    
    @Override
    public String toString()
    {
        return JSON.toJSONString(this);
    }
}
