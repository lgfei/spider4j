package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import java.util.List;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.WebsiteVO;

public class DeleteWebsiteReqData extends BaseRequestData
{
    /**
     * 待删除的网站
     */
    private List<WebsiteVO> websites;
    
    public List<WebsiteVO> getWebsites()
    {
        return websites;
    }
    
    public void setWebsites(List<WebsiteVO> websites)
    {
        this.websites = websites;
    }
    
}
