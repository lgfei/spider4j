package com.lgfei.tool.spider.operate.mvcrawler.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.request.BatchGridJson;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.ListResultResponse;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.common.util.CreateIDUtil;
import com.lgfei.tool.spider.common.vo.PageVO;
import com.lgfei.tool.spider.operate.mvcrawler.dao.WebsiteDao;
import com.lgfei.tool.spider.operate.mvcrawler.enums.TablesEnum;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteWebsiteReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryWebsiteListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryWebsitePageListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskConfigVO;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.WebsiteVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskConfigService;
import com.lgfei.tool.spider.operate.mvcrawler.service.TaskRuleService;
import com.lgfei.tool.spider.operate.mvcrawler.service.WebsiteService;

@Service
public class WebsiteServiceImpl implements WebsiteService
{
    //private static final Logger LOG = LoggerFactory.getLogger(WebsiteServiceImpl.class);
    
    @Autowired
    private WebsiteDao websiteDao;
    
    @Autowired
    private TaskConfigService taskConfigService;
    
    @Autowired
    private TaskRuleService taskRuleService;
    
    @Override
    public WebsiteVO findById(String websiteId)
        throws SystemException
    {
        WebsiteVO vo = new WebsiteVO();
        vo.setWebsiteId(websiteId);
        return websiteDao.query(vo);
    }
    
    @Override
    public void findList(QueryWebsiteListReqData data, ListResultResponse<WebsiteVO> resp)
        throws SystemException
    {
        WebsiteVO vo = data.getWebsite();
        resp.setData(websiteDao.queryList(vo));
    }
    
    @Override
    public void findPageList(QueryWebsitePageListReqData data, PageResultResponse<WebsiteVO> resp)
        throws SystemException
    {
        WebsiteVO vo = new WebsiteVO();
        vo.setName(data.getName());
        PageVO pageVO = new PageVO();
        pageVO.setPageNum(data.getPageNum());
        pageVO.setPageSize(data.getPageSize());
        
        List<WebsiteVO> list = websiteDao.queryPageList(vo, pageVO);
        
        resp.setTotal(pageVO.getTotal());
        resp.setRows(list);
    }
    
    @Override
    public void batchDelete(DeleteWebsiteReqData data, BaseResponse resp)
        throws SystemException
    {
        List<WebsiteVO> voList = data.getWebsites();
        if (CollectionUtils.isEmpty(voList))
        {
            return;
        }
        
        Set<String> websiteIds = new HashSet<>();
        for (WebsiteVO website : voList)
        {
            websiteIds.add(website.getWebsiteId());
        }
        
        List<TaskConfigVO> taskList = taskConfigService.findListByWebsitIds(websiteIds);
        Set<String> taskIds = new HashSet<>();
        for (TaskConfigVO task : taskList)
        {
            taskIds.add(task.getTaskId());
        }
        // 删规则表
        taskRuleService.deleteByTaskIds(taskIds);
        // 删任务表
        taskConfigService.batchDelete(taskList);
        // 删网站信息表
        websiteDao.batchDelete(voList);
    }
    
    @Override
    public void batchSave(BatchGridJson json, BaseResponse resp)
        throws SystemException
    {
        if (null == json)
        {
            return;
        }
        // 新增
        List<WebsiteVO> inserted = JSON.parseArray(json.getInserted().toJSONString(), WebsiteVO.class);
        if (!CollectionUtils.isEmpty(inserted))
        {
            for (WebsiteVO websiteVO : inserted)
            {
                websiteVO.setWebsiteId(CreateIDUtil.businessID(TablesEnum.T_WEBSITE_INFO.getModule()));
            }
            websiteDao.batchInsert(inserted);
        }
        // 修改
        List<WebsiteVO> updated = JSON.parseArray(json.getUpdated().toJSONString(), WebsiteVO.class);
        if (!CollectionUtils.isEmpty(updated))
        {
            websiteDao.batchUpdate(updated);
        }
    }
}
