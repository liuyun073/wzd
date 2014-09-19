package com.liuyun.site.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.liuyun.site.service.UserLogService;
import com.liuyun.site.util.StringUtils;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： UserLogInterceptor   
 * 类描述： 登录日志拦截器  
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 8:15:58 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 8:15:58 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class UserLogInterceptor extends BaseInterceptor {
	private static final long serialVersionUID = -6325242223825713099L;
	private static final Logger logger = Logger.getLogger(UserLogInterceptor.class);

	public void init() {
		super.init();
	}

	public String intercept(ActionInvocation ai) throws Exception {
		ServletContext context = ServletActionContext.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		UserLogService userLogService = (UserLogService) ctx.getBean("userLogService");

		String namespace = getNamespce();
		String url = getServletPath();
		String retMsg = getRetMsg();
		String result = ai.invoke();

		return result;
	}

	private String getRetMsg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String retMsg = StringUtils.isNull(request.getAttribute("errorMsg"));
		if (retMsg.trim().isEmpty()) {
			retMsg = StringUtils.isNull(request.getAttribute("msg"));
		}
		if (retMsg.trim().isEmpty()) {
			return "1";
		}
		return retMsg;
	}
}