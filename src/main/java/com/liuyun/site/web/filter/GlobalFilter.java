package com.liuyun.site.web.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class GlobalFilter implements Filter {
	public void destroy() {
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String url = request.getRequestURI();
		Pattern p = Pattern.compile(".(jsp|jspx|php|asp|aspx)");
		Matcher m = p.matcher(url);
		boolean rs = m.find();
		if (url.equals("/imageUp.jsp"))
			chain.doFilter(req, res);
		else if (rs)
			request.getRequestDispatcher("/404.html").forward(req, res);
		else
			chain.doFilter(req, res);
	}
}