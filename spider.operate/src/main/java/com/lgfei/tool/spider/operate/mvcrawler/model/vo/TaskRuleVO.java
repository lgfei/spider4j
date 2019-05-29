package com.lgfei.tool.spider.operate.mvcrawler.model.vo;

import com.alibaba.fastjson.JSON;
import com.lgfei.tool.spider.common.vo.BaseVO;

/**
 * 爬取内容的规则配置
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskRuleVO extends BaseVO
{
    /**
     * 序列
     */
    private static final long serialVersionUID = 789545348409983071L;
    
    /**
     * 任务id
     */
    private String taskId;
    
    /**
     * 字段名称
     */
    private String fieldName;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 爬取规则
     */
    private String rule;
    
    /**
     * 数据类型（1-单文本，2-List文本）
     */
    private Integer type;
    
    /**
     * 对于List文本类型，单个值的规则
     */
    private String itemRule;
    
    /**
     * 是否允许为空，0-不允许，1-允许
     */
    private Integer allowBlank;
    
    /**
     * 匹配条件，1-包含于，2-不包含于，3-大于，4-小于，5-相似
     */
    private Integer condition;
    
    /**
     * 匹配的值
     */
    private String matchValue;
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getFieldName()
    {
        return fieldName;
    }
    
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getRule()
    {
        return rule;
    }
    
    public void setRule(String rule)
    {
        this.rule = rule;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public String getItemRule()
    {
        return itemRule;
    }
    
    public void setItemRule(String itemRule)
    {
        this.itemRule = itemRule;
    }
    
    public Integer getAllowBlank()
    {
        return allowBlank;
    }
    
    public void setAllowBlank(Integer allowBlank)
    {
        this.allowBlank = allowBlank;
    }
    
    public Integer getCondition()
    {
        return condition;
    }
    
    public void setCondition(Integer condition)
    {
        this.condition = condition;
    }
    
    public String getMatchValue()
    {
        return matchValue;
    }
    
    public void setMatchValue(String matchValue)
    {
        this.matchValue = matchValue;
    }
    
    @Override
    public String toString()
    {
        return JSON.toJSONString(this);
    }
}
