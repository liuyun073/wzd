package com.liuyun.site.web.action.member;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

public class PaymentAction implements ServletRequestAware {
	private static final Logger logger = Logger.getLogger(PaymentAction.class);
	private HttpServletRequest request;

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String gopay() {
		String param = this.request.getQueryString();
		logger.info(param);
		return "success";
	}
}