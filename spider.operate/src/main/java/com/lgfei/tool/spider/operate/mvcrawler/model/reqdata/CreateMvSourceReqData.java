package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvSourceVO;

public class CreateMvSourceReqData extends BaseRequestData
{
    private MvSourceVO mvSource;
    
    public MvSourceVO getMvSource()
    {
        return mvSource;
    }
    
    public void setMvSource(MvSourceVO mvSource)
    {
        this.mvSource = mvSource;
    }
    
}
