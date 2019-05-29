package com.lgfei.tool.spider.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础业务实体类
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2017年6月26日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseVO implements Serializable
{
    /**
     * 序列
     */
    private static final long serialVersionUID = 7826765341295199154L;
    
    /**
     * 主键id
     */
    private Long id;
    
    /**
     * 创建时间
     */
    private Date createdTime;
    
    /**
     * 最后修改时间
     */
    private Date updatedTime;
    
    /**
     * 可用标识，0-不可用，1-可用
     */
    private Integer enableFlag;
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public Date getCreatedTime()
    {
        return createdTime;
    }
    
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }
    
    public Date getUpdatedTime()
    {
        return updatedTime;
    }
    
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }
    
    public Integer getEnableFlag()
    {
        return enableFlag;
    }
    
    public void setEnableFlag(Integer enableFlag)
    {
        this.enableFlag = enableFlag;
    }
    
}
