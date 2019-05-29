package com.lgfei.tool.spider.operate.mvcrawler.model.vo;

import com.alibaba.fastjson.JSON;
import com.lgfei.tool.spider.common.vo.BaseVO;

/**
 * 影片信息对象
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MvInfoVO extends BaseVO
{
    /**
     * 序列
     */
    private static final long serialVersionUID = 6176206288335500226L;
    
    /**
     * 影片Id
     */
    private String mvId;
    
    /**
     * 影片名
     */
    private String name;
    
    /**
     * 简介
     */
    private String brief;
    
    /**
     * 上映日期
     */
    private String showDate;
    
    /**
     * 详细剧情
     */
    private String details;
    
    /**
     * 主演
     */
    private String player;
    
    /**
     * 导演
     */
    private String director;
    
    /**
     * 海报
     */
    private String poster;
    
    /**
     * 网站Id
     */
    private String websiteId;
    
    /**
     * 播放源地址（多个用','隔开）
     */
    private String sourceUrls;
    
    public String getMvId()
    {
        return mvId;
    }
    
    public void setMvId(String mvId)
    {
        this.mvId = mvId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getBrief()
    {
        return brief;
    }
    
    public void setBrief(String brief)
    {
        this.brief = brief;
    }
    
    public String getShowDate()
    {
        return showDate;
    }
    
    public void setShowDate(String showDate)
    {
        this.showDate = showDate;
    }
    
    public String getDetails()
    {
        return details;
    }
    
    public void setDetails(String details)
    {
        this.details = details;
    }
    
    public String getPlayer()
    {
        return player;
    }
    
    public void setPlayer(String player)
    {
        this.player = player;
    }
    
    public String getDirector()
    {
        return director;
    }
    
    public void setDirector(String director)
    {
        this.director = director;
    }
    
    public String getPoster()
    {
        return poster;
    }
    
    public void setPoster(String poster)
    {
        this.poster = poster;
    }
    
    public String getWebsiteId()
    {
        return websiteId;
    }
    
    public void setWebsiteId(String websiteId)
    {
        this.websiteId = websiteId;
    }
    
    public String getSourceUrls()
    {
        return sourceUrls;
    }
    
    public void setSourceUrls(String sourceUrls)
    {
        this.sourceUrls = sourceUrls;
    }
    
    @Override
    public String toString()
    {
        return JSON.toJSONString(this);
    }
    
}
