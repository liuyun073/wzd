package com.liuyun.site.freemarker;

import com.liuyun.site.dao.ArticleDao;
import com.liuyun.site.dao.AttestationDao;
import com.liuyun.site.dao.LinkageDao;
import com.liuyun.site.dao.UserDao;
import com.liuyun.site.freemarker.directive.AreaDirectiveModel;
import com.liuyun.site.freemarker.directive.AttestationDirectiveModel;
import com.liuyun.site.freemarker.directive.LinkageDirectiveModel;
import com.liuyun.site.freemarker.directive.SiteDirectiveModel;
import com.liuyun.site.freemarker.method.AttestationTypeNameModel;
import com.liuyun.site.freemarker.method.DateMethodModel;
import com.liuyun.site.freemarker.method.DateRollMethodModel;
import com.liuyun.site.freemarker.method.InterestMethodModel;
import com.liuyun.site.freemarker.method.LinkageMethodModel;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import javax.servlet.ServletContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： CustomFreemarkerManager   
 * 类描述： freemarker 视图管理器   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 27, 2013 3:37:01 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 27, 2013 3:37:01 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class CustomFreemarkerManager extends FreemarkerManager {
	
	/*
	 * (非 Javadoc) 
	 * <p>Title: createConfiguration</p> 
	 * <p>Description: 重载createConfiguration方法， 可在此注册自定义函数</p> 
	 * @param servletContext
	 * @return
	 * @throws TemplateException 
	 * @see org.apache.struts2.views.freemarker.FreemarkerManager#createConfiguration(javax.servlet.ServletContext)
	 */
	protected Configuration createConfiguration(ServletContext servletContext)
			throws TemplateException {
		
		Configuration cfg = super.createConfiguration(servletContext);
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		LinkageDao linkageDao = (LinkageDao) ctx.getBean("linkageDao");
		AttestationDao attestationDao = (AttestationDao) ctx.getBean("attestationDao");
		UserDao userDao = (UserDao) ctx.getBean("userDao");
		ArticleDao articleDao = (ArticleDao) ctx.getBean("articleDao");

		cfg.setSharedVariable("dateformat", new DateMethodModel());
		cfg.setSharedVariable("dateroll", new DateRollMethodModel());
		cfg.setSharedVariable("interest", new InterestMethodModel());

		cfg.setSharedVariable("linkage", new LinkageDirectiveModel(linkageDao));

		cfg.setSharedVariable("region", new AreaDirectiveModel(linkageDao));

		cfg.setSharedVariable("attestation", new AttestationDirectiveModel(attestationDao));
		cfg.setSharedVariable("Type", new AttestationTypeNameModel(linkageDao, userDao));

		cfg.setSharedVariable("siteDirect", new SiteDirectiveModel(articleDao));

		cfg.setSharedVariable("getLinkage", new LinkageMethodModel(linkageDao));

		return cfg;
	}
}