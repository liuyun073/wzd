package com.rd.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.rd.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： BaseInterceptor   
 * 类描述： 一个拦截器的抽象类   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 8:02:40 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 8:02:40 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public abstract class BaseInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -2239644443711524657L;
	private static final Logger logger = Logger.getLogger(BaseInterceptor.class);

	public void init() {
		super.init();
	}

	public abstract String intercept(ActionInvocation paramActionInvocation)
			throws Exception;

	protected String getServletPath() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//servlet 路径
		String servletPath = request.getServletPath();
		//请求命名空间（路径）
		String namespace = ServletActionContext.getActionMapping().getNamespace();
		//请求后缀名
		String extension = ServletActionContext.getActionMapping().getExtension();
		servletPath = servletPath.replaceFirst(namespace, "").replace("." + extension, ".html");
		return servletPath;
	}

	protected String getNamespce() {
		return ServletActionContext.getActionMapping().getNamespace();
	}

	protected void message(String msg, String url, String text) {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("rsmsg", msg);
		String urltext = "";
		urltext = "<a href=" + request.getContextPath() + url + " >" + text + "></a>";
		request.setAttribute("backurl", urltext);
	}

	protected void message(String msg, String url) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String urltext = "";
		if (!StringUtils.isBlank(url)) {
			urltext = "<a href=" + request.getContextPath() + url + " >返回上一页</a>";
			request.setAttribute("backurl", urltext);
		} else {
			urltext = "<a href='javascript:history.go(-1)'>返回上一页</a>";
		}
		message(msg, url, urltext);
	}
}