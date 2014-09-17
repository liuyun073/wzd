package com.rd.web.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rd.common.enums.EnumRuleStatus;
import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.dao.BorrowConfigDao;
import com.rd.dao.NoticeConfigDao;
import com.rd.domain.BorrowConfig;
import com.rd.domain.NoticeConfig;
import com.rd.domain.Rule;
import com.rd.domain.Site;
import com.rd.domain.User;
import com.rd.model.SystemInfo;
import com.rd.model.tree.Tree;
import com.rd.quartz.QuartzJob;
import com.rd.quartz.notice.NoticeJobQueue;
import com.rd.service.ArticleService;
import com.rd.service.AutoBorrowService;
import com.rd.service.BorrowService;
import com.rd.service.NoticeService;
import com.rd.service.RuleService;
import com.rd.service.SystemService;
import com.rd.util.StringUtils;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： WebConfigContextListener   
 * 类描述： 
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Feb 6, 2014 11:21:39 AM  
 * ------------------------------------------------------ 
 * 修改人： 系统监听器
 * 修改时间：Feb 6, 2014 11:21:39 AM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class WebConfigContextListener implements ServletContextListener,
		HttpSessionAttributeListener {
	private static Logger logger = Logger.getLogger(WebConfigContextListener.class);
	private Object lock = new Object();

	public void contextDestroyed(ServletContextEvent event) {
	}

	public void contextInitialized(ServletContextEvent event) {
		
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		SystemService systemService = (SystemService) ctx.getBean("systemService");
		BorrowService borrowService = (BorrowService) ctx.getBean("borrowService");
		AutoBorrowService autoBorrowService = (AutoBorrowService) ctx.getBean("autoBorrowService");
		NoticeService noticeService = (NoticeService) ctx.getBean("noticeService");
		RuleService ruleService = (RuleService) ctx.getBean("ruleService");
		
		
		SystemInfo info = systemService.getSystemInfo();
		Global.SYSTEMINFO = info;
		setWebConfig(context, info);

		
		checkVersion();

		
		ArticleService articleService = (ArticleService) ctx.getBean("articleService");
		Tree<Site> tree = articleService.getSiteTree();
		context.setAttribute("tree", tree);

		
		BorrowConfigDao borrowConfigDao = (BorrowConfigDao) ctx.getBean("borrowConfigDao");
		List<BorrowConfig> list = borrowConfigDao.getList();
		Map<Long, BorrowConfig> map = new HashMap<Long, BorrowConfig>();
		for (int i = 0; i < list.size(); ++i) {
			BorrowConfig config = (BorrowConfig) list.get(i);
			map.put(Long.valueOf(config.getId()), config);
		}
		Global.BORROWCONFIG = map;
		

		NoticeConfigDao noticeConfigDao = (NoticeConfigDao) ctx.getBean("noticeConfigDao");
		List<NoticeConfig> noticelist = noticeConfigDao.getList();
		Map<String, NoticeConfig> noticemap = new HashMap<String, NoticeConfig>();
		for (int i = 0; i < noticelist.size(); ++i) {
			NoticeConfig config = (NoticeConfig) noticelist.get(i);
			noticemap.put(config.getType(), config);
		}
		Global.NOTICECONFIG = noticemap;

		
		Map<String, Rule> ruleMap = new HashMap<String, Rule>();
		ruleService = (RuleService) ctx.getBean("ruleService");
		List<Rule> ruleList = ruleService.getRuleList(EnumRuleStatus.RULE_STATUS_YES.getValue());
		for (int i = 0; i < ruleList.size(); ++i) {
			Rule r = (Rule) ruleList.get(i);
			if (r != null) {
				ruleMap.put(r.getNid(), r);
			}
		}
		Global.RULES = ruleMap;

		
		QuartzJob job = new QuartzJob();
		job.setBorrowService(borrowService);
		job.setAutoBorrowService(autoBorrowService);
		job.doTask();

		
		logger.debug(ctx.getBean("messageDao"));
		NoticeJobQueue.init(noticeService);
	}

	private void setWebConfig(ServletContext context, SystemInfo info) {
		String[] webinfo = Global.SYSTEMNAME;
		for (String s : webinfo) {
			logger.debug(s + ":" + info.getValue(s));
			context.setAttribute(s, info.getValue(s));
			if ((s.equals("theme_dir")) && (StringUtils.isBlank(info.getValue(s)))) {
				context.setAttribute(s, "/themes/soonmes_default");
			}
		}
		context.setAttribute("webroot", context.getContextPath());
	}

	public void attributeAdded(HttpSessionBindingEvent event) {
		User user = getSessionUser(event);
		if (user != null)
			synchronized (this.lock) {
				Global.SESSION_MAP.put(user.getUsername(), Long.valueOf(System.currentTimeMillis()));
			}
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		User user = getSessionUser(event);
		if (user != null)
			synchronized (this.lock) {
				if (Global.SESSION_MAP.containsKey(user.getUsername()))
					Global.SESSION_MAP.remove(user.getUsername());
			}
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		User user = getSessionUser(event);
		if (user == null)
			synchronized (this.lock) {
				if ((Global.SESSION_MAP.containsKey(event.getName())) && (event.getValue() == null))
					Global.SESSION_MAP.remove(event.getName());
			}
	}

	public User getSessionUser(HttpSessionBindingEvent event) {
		if (StringUtils.isNull(event.getName()).equals(Constant.SESSION_USER)) {
			Object obj = event.getValue();
			if (obj != null) {
				return (User) obj;
			}
		}
		return null;
	}

	public void checkVersion() {
		String dbVersion = Global.getVersion();
		String sysVersion = Global.VERSION;
		logger.info("数据库版本：" + dbVersion);
		logger.info("系统版本:" + sysVersion);
		if (!Global.getVersion().equals(Global.VERSION))
			throw new RuntimeException("数据库版本与系统版本不一致，请更新数据库！");
	}
}