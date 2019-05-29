package com.lgfei.tool.spider.operate.mvcrawler.model.reqdata;

import java.util.List;

import com.lgfei.tool.spider.common.message.request.BaseRequestData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvInfoVO;

public class UpdateMvReqData extends BaseRequestData
{
    private List<MvInfoVO> mvInfos;
    
    public List<MvInfoVO> getMvInfos()
    {
        return mvInfos;
    }
    
    public void setMvInfos(List<MvInfoVO> mvInfos)
    {
        this.mvInfos = mvInfos;
    }
    
}
