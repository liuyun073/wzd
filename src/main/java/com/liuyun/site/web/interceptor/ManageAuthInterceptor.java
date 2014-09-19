package com.liuyun.site.web.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.dao.SystemDao;
import com.liuyun.site.domain.User;
import com.liuyun.site.model.purview.PurviewSet;
import com.liuyun.site.tool.iphelper.IPUtils;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.liuyun.site.web.action.BaseAction;;


/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： ManageAuthInterceptor   
 * 类描述： 权限拦截器   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 8:22:28 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 8:22:28 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class ManageAuthInterceptor extends BaseInterceptor {
	private static final long serialVersionUID = -2239644443711524657L;
	private static final Logger logger = Logger.getLogger(ManageAuthInterceptor.class);

	public String intercept(ActionInvocation ai) throws Exception {
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		User user = (User) session.get(Constant.AUTH_USER);
		String servletPath = getServletPath();

		String isAllowIp_enable = Global.getValue("isAllowIp_enable");
		if ((isAllowIp_enable != null) && (isAllowIp_enable.equals("1"))) {
			ServletContext context = ServletActionContext.getServletContext();
			ApplicationContext appctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
			SystemDao systemDao = (SystemDao) appctx.getBean("systemDao");
			List<String> ipList = systemDao.getAllowIp();
			String realip = IPUtils.getRemortIP(ServletActionContext.getRequest());
			if (!ipList.contains(realip)) {
				logger.debug("未授权IP：" + realip + "没有权限访问后台！");
				message("未授权IP：" + realip + "没有权限访问后台！", "");
				return BaseAction.ADMINMSG;
			}
		}

		if ((servletPath.startsWith("/admin/auth.html")) || (servletPath.startsWith("/admin/index.html")) || (servletPath.startsWith("/admin/logout.html"))) {
			return ai.invoke();
		}
		if (user == null) {
			message("请先登录！", "/admin/index.html");
			return BaseAction.ADMINMSG;
		}
		PurviewSet session_purviewset = (PurviewSet) session.get(Constant.AUTH_PURVIEW);
		if (!session_purviewset.hasList()) {
			logger.debug(user.getUsername() + "没有权限访问后台！");
			message(user.getUsername() + "没有权限访问后台！", "");
			return BaseAction.ADMINMSG;
		}

		if (session_purviewset.contains(servletPath)) {
			
			return ai.invoke();
		}

		message(user.getUsername() + "没有权限访问后台！", "");
		return BaseAction.ADMINMSG;
	}

	protected String getServletPath() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String servletPath = request.getServletPath();
		String extension = ServletActionContext.getActionMapping().getExtension();
		servletPath = servletPath.replace("." + extension, ".html");
		return servletPath;
	}
}