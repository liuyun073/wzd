package com.liuyun.site.web.action.admin;

import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.UserCreditService;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import java.util.List;

public class ManageUserCreditAction extends BaseAction {
	private UserCreditService userCreditService;

	public UserCreditService getUserCreditService() {
		return this.userCreditService;
	}

	public void setUserCreditService(UserCreditService userCreditService) {
		this.userCreditService = userCreditService;
	}

	public String showUserCredit() {
		int pageNo = NumberUtils.getInt(this.request.getParameter("page"));
		int pageSize = NumberUtils
				.getInt(this.request.getParameter("pageSize"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		PageDataList pageDataList = this.userCreditService.getUserCreditPage(
				pageNo, pageSize, param);
		setPageAttribute(pageDataList, param);
		return SUCCESS;
	}

	public String showUserCreditLog() {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		long type_id = NumberUtils.getInt(this.request.getParameter("type_id"));
		long user_id = NumberUtils.getInt(this.request.getParameter("user_id"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		String dotime1 = StringUtils.isNull(this.request
				.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request
				.getParameter("dotime2"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		PageDataList pageDataList = this.userCreditService.getCreditLogPage(
				page, param, type_id, user_id);
		List typeList = this.userCreditService.getCreditTypeAll();
		this.request.setAttribute("typeList", typeList);
		this.request.setAttribute("type_id", Long.valueOf(type_id));
		setPageAttribute(pageDataList, param);
		return SUCCESS;
	}
}