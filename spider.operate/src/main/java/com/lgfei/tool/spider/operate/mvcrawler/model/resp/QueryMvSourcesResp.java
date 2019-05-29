package com.lgfei.tool.spider.operate.mvcrawler.model.resp;

import java.util.List;

import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvSourceVO;

public class QueryMvSourcesResp extends BaseResponse
{
    /**
     * 片源
     */
    private List<MvSourceVO> rows;
    
    public List<MvSourceVO> getRows()
    {
        return rows;
    }
    
    public void setRows(List<MvSourceVO> rows)
    {
        this.rows = rows;
    }
    
}
