package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.WebsiteVO;

public class QueryWebsiteListReqData extends BaseRequestData
{
    /**
     * 网站信息
     */
    private WebsiteVO website;
    
    public WebsiteVO getWebsite()
    {
        return website;
    }
    
    public void setWebsite(WebsiteVO website)
    {
        this.website = website;
    }
    
}
