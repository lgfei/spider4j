package com.lgfei.tool.spider.operate.dispatcher;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.lgfei.tool.spider.common.constant.Constant;
import com.lgfei.tool.spider.common.dispatcher.BaseRequestAcceptor;
import com.lgfei.tool.spider.common.util.PropertiesUtil;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.CloneTaskAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.CreateMvSourceAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.CreateTaskConfigAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.DeleteMvAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.DeleteMvCacheAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.DeleteMvSourceAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.DeleteTaskConfigAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.DeleteTaskRuleAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.DeleteWebsiteAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.QueryMvCachePageListAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.QueryMvCrawlerConfigAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.QueryMvPageListAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.QueryMvSourceListAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.QueryTaskConfigListAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.QueryTaskRuleListAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.QueryWebsiteListAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.QueryWebsitePageListAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.SaveTaskRuleAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.SaveWebsiteAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.StartTaskAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.StopTaskAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.SyncMvCacheAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.UpdateMvAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.UpdateMvSourceAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.UpdateTaskConfigAction;
import com.lgfei.tool.spider.operate.mvcrawler.controller.action.UpdateTaskRuleEnableFlagAction;

/**
 * 后台服务请求分发器
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@ConfigurationProperties
@Component("operateRequsetAcceptor")
public class OperateRequsetAcceptor extends BaseRequestAcceptor
{
    private static final String SERVICE_NAME_SPIDEROPERATE = PropertiesUtil.getProperty("project.serviceName");
    
    private static final String APP_NAME_MVCRAWLER = PropertiesUtil.getProperty("mvcrawler.appName");
    
    /**
     * 默认构造函数
     */
    public OperateRequsetAcceptor()
    {
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "querymvcrawlerconfig.do"),
            new QueryMvCrawlerConfigAction("查询影片爬虫配置数据"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "querytaskconfiglist.do"),
            new QueryTaskConfigListAction("查询影片爬虫任务列表"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "createtaskconfig.do"),
            new CreateTaskConfigAction("创建任务配置"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "updatetaskconfig.do"),
            new UpdateTaskConfigAction("修改任务配置"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "deletetaskconfig.do"),
            new DeleteTaskConfigAction("删除任务配置"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "starttask.do"), new StartTaskAction("启动爬虫任务"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "stoptask.do"), new StopTaskAction("停止爬虫任务"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "clonetask.do"), new CloneTaskAction("克隆任务配置"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "querytaskrulelist.do"),
            new QueryTaskRuleListAction("查询任务规则列表"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "savetaskrule.do"), new SaveTaskRuleAction("保存任务规则"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "deletetaskrule.do"), new DeleteTaskRuleAction("删除任务规则"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "updatetaskruleenableflag.do"),
            new UpdateTaskRuleEnableFlagAction("更新任务规则可用状态"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "querywebsitelist.do"),
            new QueryWebsiteListAction("查询网站列表"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "querywebsitepagelist.do"),
            new QueryWebsitePageListAction("分页查询网站列表"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "savewebsite.do"), new SaveWebsiteAction("保存网站信息"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "deletewebsite.do"), new DeleteWebsiteAction("删除网站信息"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "querymvpagelist.do"),
            new QueryMvPageListAction("分页查询影片列表"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "querymvsourcelist.do"),
            new QueryMvSourceListAction("查询影片的片源列表"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "deletemv.do"), new DeleteMvAction("删除影片"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "updatemv.do"), new UpdateMvAction("更新影片"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "updatemvsource.do"), new UpdateMvSourceAction("更新片源"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "deletemvsource.do"), new DeleteMvSourceAction("删除片源"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "createmvsource.do"), new CreateMvSourceAction("新增片源"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "querymvcachepagelist.do"),
            new QueryMvCachePageListAction("分页查询影片列表"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "deletemvcache.do"), new DeleteMvCacheAction("删除影片缓存"));
        
        super.registerAction(generateUri(APP_NAME_MVCRAWLER, "syncmvcache.do"), new SyncMvCacheAction("同步影片"));
    }
    
    private String generateUri(String appName, String businessName)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.SLASH)
            .append(SERVICE_NAME_SPIDEROPERATE)
            .append(Constant.SLASH)
            .append(appName)
            .append(Constant.SLASH)
            .append(businessName);
        return sb.toString();
    }
}
