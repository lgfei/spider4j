package com.lgfei.tool.spider.common.message.response;

import java.util.List;

public class ListResultResponse<VO> extends BaseResponse
{
    /**
     * 结果集
     */
    private List<VO> data;
    
    public List<VO> getData()
    {
        return data;
    }
    
    public void setData(List<VO> data)
    {
        this.data = data;
    }
    
}
