package com.liuyun.site.web.filter;

import com.liuyun.site.context.Constant;
import com.liuyun.site.domain.User;
import com.liuyun.site.util.StringUtils;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {
	protected String encoding = null;

	protected FilterConfig filterConfig = null;

	protected boolean ignore = false;

	protected String forwardPath = null;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		HttpSession session = httpServletRequest.getSession();
		User sessionUser = (User) session.getAttribute(Constant.SESSION_USER);

		String servletPath = httpServletRequest.getServletPath();
		String queryString = httpServletRequest.getQueryString();
		List<String> pathList = notNeedSessionCheck();
		if ((!pathList.contains(servletPath)) && (sessionUser == null)) {
			String redirectURL = servletPath;
			if (!StringUtils.isBlank(queryString)) {
				redirectURL = httpServletRequest.getContextPath() + servletPath + StringUtils.isNull(queryString);
			}
			redirectURL = URLEncoder.encode(redirectURL, "UTF-8");
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+ "/user/login.html?redirectURL=" + httpServletRequest.getContextPath() + redirectURL);
			return;
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		this.forwardPath = filterConfig.getInitParameter("forwardpath");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true"))
			this.ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			this.ignore = true;
		else
			this.ignore = false;
	}

	protected String selectEncoding(ServletRequest request) {
		return this.encoding;
	}

	private List<String> notNeedSessionCheck() {
		String[] paths = { "/member/identify/active.html",
				"/member/pay/gopay.html", "/member/identify/active.action",
				"/member/pay/gopay.action" };

		return Arrays.asList(paths);
	}
}