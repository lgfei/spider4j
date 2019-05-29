package com.lgfei.tool.spider.crawler4j;

import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * crawler4j框架爬虫类
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2017年8月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MyCrawler extends WebCrawler
{
    /**
     * 正则匹配指定的后缀文件
     */
    private static final Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))$");
    //private final static Pattern FILTERS = Pattern.compile(".*(\\.(jpg|png))$");
    
    /**
     * 这个方法主要是决定哪些url我们需要抓取，返回true表示是我们需要的，返回false表示不是我们需要的Url
     * 第一个参数referringPage封装了当前爬取的页面信息
     * 第二个参数url封装了当前爬取的页面url信息
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url)
    {
        String href = url.getURL().toLowerCase();
        // 正则匹配，过滤掉我们不需要的后缀文件
        return !FILTERS.matcher(href).matches();
    }
    
    /**
     * 当我们爬到我们需要的页面，这个方法会被调用，我们可以尽情的处理这个页面
     * page参数封装了所有页面信息
     */
    @Override
    public void visit(Page page)
    {
        // 获取url
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);
        
        if (page.getParseData() instanceof HtmlParseData)
        {
            // 强制类型转换，获取html数据对象
            HtmlParseData htmlParseData = (HtmlParseData)page.getParseData();
            // 获取页面纯文本（无html标签）
            String text = htmlParseData.getText();
            // 获取页面Html
            String html = htmlParseData.getHtml();
            // 获取页面输出链接
            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            
            System.out.println("纯文本长度: " + text.length());
            System.out.println("html长度: " + html.length());
            System.out.println("输出链接个数: " + links.size());
        }
    }
}
