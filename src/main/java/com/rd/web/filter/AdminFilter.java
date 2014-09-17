package com.rd.web.filter;

import com.rd.context.Global;
import com.rd.util.StringUtils;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： AdminFilter   
 * 类描述： 后台请求拦截器  
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 8:47:58 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 8:47:58 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class AdminFilter implements Filter {
	public void init(FilterConfig arg0) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession();
		String servletPath = getServletPath(httpServletRequest);
		String adminUrl = Global.getString("admin_url");
		if ((!StringUtils.isBlank(adminUrl)) && (servletPath.endsWith(adminUrl))) {
			session.setAttribute("adminUrlCheck", "true");
			request.setAttribute("source", "fliter");
			httpServletRequest.getRequestDispatcher("/admin/auth.html").forward(request, response);
		} else {
			boolean isAdminCheck = StringUtils.isBlank(StringUtils.isNull(session.getAttribute("adminUrlCheck")));
			if (isAdminCheck)
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/notfound.html");
			else
				chain.doFilter(request, response);
		}
	}

	protected String getServletPath(HttpServletRequest request) {
		String servletPath = request.getRequestURI();
		return servletPath;
	}
}