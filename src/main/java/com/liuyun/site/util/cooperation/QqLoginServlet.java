package com.liuyun.site.util.cooperation;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QqLoginServlet extends HttpServlet {
	private static final long serialVersionUID = -1527777072775436166L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("<script>");
		out.println("window.location.href=\"" + getQqLoginUrl() + "\"");
		out.println("</script>");
		out.flush();
	}

	private String getQqLoginUrl() {
		OpenQqUtils oqu = new OpenQqUtils();

		StringBuilder qqLoginUrl = new StringBuilder();

		qqLoginUrl.append("https://graph.qq.com/oauth2.0/authorize");

		qqLoginUrl.append("?response_type=code");

		qqLoginUrl.append("&client_id=" + oqu.getConfigValue("qq.appid"));

		qqLoginUrl.append("&redirect_uri=" + oqu.getConfigValue("qq.callback"));

		qqLoginUrl.append("&scope=" + oqu.getConfigValue("qq.scope"));

		qqLoginUrl.append("&state=javaSdk-code");

		qqLoginUrl.append("&display=");

		return qqLoginUrl.toString();
	}
}