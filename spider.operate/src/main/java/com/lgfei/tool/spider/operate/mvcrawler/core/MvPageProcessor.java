package com.lgfei.tool.spider.operate.mvcrawler.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.lgfei.tool.spider.common.cache.CacheKey;
import com.lgfei.tool.spider.common.cache.service.RedisService;
import com.lgfei.tool.spider.common.util.CreateIDUtil;
import com.lgfei.tool.spider.common.util.ReflectUtil;
import com.lgfei.tool.spider.common.util.StringUtil;
import com.lgfei.tool.spider.operate.mvcrawler.enums.CrawlerModeEnum;
import com.lgfei.tool.spider.operate.mvcrawler.enums.MatchConditionEnum;
import com.lgfei.tool.spider.operate.mvcrawler.enums.RuleTypeEnum;
import com.lgfei.tool.spider.operate.mvcrawler.enums.TablesEnum;
import com.lgfei.tool.spider.operate.mvcrawler.enums.YesOrNoEnum;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvInfoVO;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskConfigVO;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.TaskRuleVO;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * WebMagic框架爬虫类
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2017年8月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MvPageProcessor implements PageProcessor
{
    private static final Logger LOG = LoggerFactory.getLogger(MvPageProcessor.class);
    
    private Site site = Site.me().setRetryTimes(3).setSleepTime(500);
    
    @Override
    public Site getSite()
    {
        return site;
    }
    
    public MvPageProcessor()
    {
        super();
    }
    
    public MvPageProcessor(TaskConfigVO task, List<TaskRuleVO> rules, RedisService redisService)
    {
        super();
        this.task = task;
        this.rules = rules;
        this.redisService = redisService;
    }
    
    @Override
    public void process(Page page)
    {
        LOG.info("=====开始提取内容=====");
        List<MvInfoVO> mvList = new ArrayList<>();
        // 详情模式
        if (CrawlerModeEnum.DETAIL.getValue().equals(task.getMode()))
        {
            if (page.getUrl().regex(task.getRegex()).match())
            {
                List<String> links = page.getHtml().links().regex(task.getTargetArea()).all();
                page.addTargetRequests(links);
            }
            else if (page.getUrl().regex(task.getTargetArea()).match())
            {
                MvInfoVO mv = getMvInfo(page.getHtml());
                if (null != mv)
                {
                    mvList.add(mv);
                }
            }
            else
            {
                LOG.info("非目标网页:{}", page.getUrl());
            }
        }
        // 列表模式
        else
        {
            if (page.getUrl().regex(task.getRegex()).match())
            {
                List<Selectable> targetAreas = page.getHtml().xpath(task.getTargetArea()).nodes();
                for (Selectable selectable : targetAreas)
                {
                    MvInfoVO mv = getMvInfo(selectable);
                    if (null != mv)
                    {
                        mvList.add(mv);
                    }
                }
            }
            else
            {
                LOG.info("非目标网页：{}", page.getUrl());
            }
        }
        // 缓存至Redis
        if (!CollectionUtils.isEmpty(mvList))
        {
            Map<String, MvInfoVO> maps = new HashMap<>();
            for (MvInfoVO mv : mvList)
            {
                String cacheKey = CacheKey.KvMapping.mv.format(mv.getMvId());
                maps.put(cacheKey, mv);
            }
            redisService.mset(maps);
        }
        LOG.info("=====提取内容结束=====");
    }
    
    /**
     * 根据规则获取影片信息
     * <功能详细描述>
     * @param selectable
     * @return
     * @see [类、类#方法、类#成员]
     */
    private MvInfoVO getMvInfo(Selectable selectable)
    {
        MvInfoVO mvInfo = new MvInfoVO();
        mvInfo.setMvId(CreateIDUtil.businessID(TablesEnum.T_MV_INFO.getModule()));
        mvInfo.setWebsiteId(task.getWebsiteId());
        if (!CollectionUtils.isEmpty(rules))
        {
            for (TaskRuleVO rule : rules)
            {
                String fieldName = rule.getFieldName();
                String value = null;
                if (RuleTypeEnum.TEXT.getValue().equals(rule.getType()))
                {
                    // 文本类型
                    value = selectable.xpath(rule.getRule()).get();
                }
                else
                {
                    // 集合类型
                    List<Selectable> allNode = selectable.xpath(rule.getRule()).nodes();
                    if (CollectionUtils.isEmpty(allNode))
                    {
                        continue;
                    }
                    List<String> values = new ArrayList<>();
                    for (Selectable node : allNode)
                    {
                        String tmpStr = node.xpath(rule.getItemRule()).get();
                        if (!StringUtils.isEmpty(tmpStr))
                        {
                            values.add(node.xpath(rule.getItemRule()).get());
                        }
                    }
                    value = StringUtil.list2String(values);
                }
                // 去前后空格
                value = null == value ? null : value.trim();
                // 判断是否符合入库条件
                boolean isMatch = match(value, rule.getAllowBlank(), rule.getCondition(), rule.getMatchValue());
                if (!isMatch)
                {
                    LOG.info("{}:{},匹配失败", fieldName, value);
                    return null;
                }
                // 反射赋值
                ReflectUtil.setValue(mvInfo, fieldName, value);
            }
        }
        return mvInfo;
    }
    
    /**
     * 根据取值范围确定是否录入
     * <功能详细描述>
     * @param value
     * @param allowBlank
     * @param condition
     * @param matchValue
     * @return
     * @see [类、类#方法、类#成员]
     */
    private boolean match(String value, Integer allowBlank, Integer condition, String matchValue)
    {
        // 是否为空匹配
        if (YesOrNoEnum.YES.getValue().equals(allowBlank))
        {
            if (StringUtils.isEmpty(value))
            {
                // 允许为空且正好为空，匹配成功，不再往下校验
                return true;
            }
        }
        else if (YesOrNoEnum.NO.getValue().equals(allowBlank))
        {
            if (StringUtils.isEmpty(value))
            {
                // 不允许为空却正好为空，匹配失败，不再往下校验
                return false;
            }
        }
        // 取值范围匹配
        if (null == condition || null == matchValue)
        {
            return true;
        }
        if (MatchConditionEnum.EQUAL.getValue().equals(condition))
        {
            return StringUtil.in(value, matchValue.split(","));
        }
        else if (MatchConditionEnum.NOT_EQUAL.getValue().equals(condition))
        {
            return !StringUtil.in(value, matchValue.split(","));
        }
        else if (MatchConditionEnum.GREATER.getValue().equals(condition))
        {
            return StringUtil.isGreater(value, matchValue);
        }
        else if (MatchConditionEnum.LESS.getValue().equals(condition))
        {
            return StringUtil.isLess(value, matchValue);
        }
        else if (MatchConditionEnum.LIKE.getValue().equals(condition))
        {
            return StringUtil.isLike(value, matchValue);
        }
        else
        {
            return true;
        }
    }
    
    private TaskConfigVO task;
    
    private List<TaskRuleVO> rules;
    
    private RedisService redisService;
    
    public TaskConfigVO getTask()
    {
        return task;
    }
    
    public void setTask(TaskConfigVO task)
    {
        this.task = task;
    }
    
    public List<TaskRuleVO> getRules()
    {
        return rules;
    }
    
    public void setRules(List<TaskRuleVO> rules)
    {
        this.rules = rules;
    }
    
    public RedisService getRedisService()
    {
        return redisService;
    }
    
    public void setRedisService(RedisService redisService)
    {
        this.redisService = redisService;
    }
    
}
