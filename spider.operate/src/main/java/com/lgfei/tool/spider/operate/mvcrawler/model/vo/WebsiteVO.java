package com.lgfei.tool.spider.operate.mvcrawler.model.vo;

import com.alibaba.fastjson.JSON;
import com.lgfei.tool.spider.common.vo.BaseVO;

public class WebsiteVO extends BaseVO
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4455411452084841971L;
    
    /**
     * 业务ID
     */
    private String websiteId;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 网站地址
     */
    private String address;
    
    public String getWebsiteId()
    {
        return websiteId;
    }
    
    public void setWebsiteId(String websiteId)
    {
        this.websiteId = websiteId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    @Override
    public String toString()
    {
        return JSON.toJSONString(this);
    }
}
