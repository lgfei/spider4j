package com.lgfei.tool.spider.operate.mvcrawler.model.vo;

import com.alibaba.fastjson.JSON;
import com.lgfei.tool.spider.common.vo.BaseVO;

/**
 * 影片源对象
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MvSourceVO extends BaseVO
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4066384156129972971L;
    
    private String mvId;
    
    private String websiteId;
    
    private String websiteName;
    
    private String sourceId;
    
    private String sourceUrl;
    
    public String getMvId()
    {
        return mvId;
    }
    
    public void setMvId(String mvId)
    {
        this.mvId = mvId;
    }
    
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
    
    public String getSourceId()
    {
        return sourceId;
    }
    
    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }
    
    public String getSourceUrl()
    {
        return sourceUrl;
    }
    
    public void setSourceUrl(String sourceUrl)
    {
        this.sourceUrl = sourceUrl;
    }
    
    @Override
    public String toString()
    {
        return JSON.toJSONString(this);
    }
    
}
