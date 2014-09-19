package com.liuyun.site.web.action;

import com.liuyun.site.context.Constant;
import com.liuyun.site.domain.User;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.UserCreditService;
import com.liuyun.site.util.NumberUtils;
import java.util.List;

public class UserCreditAction extends BaseAction {
	private UserCreditService userCreditService;

	public String creditLog() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		if (user == null)
			return ERROR;
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String dotime1 = this.request.getParameter("dotime1");
		String dotime2 = this.request.getParameter("dotime2");
		long type_id = NumberUtils.getInt(this.request.getParameter("type_id"));
		long user_id = user.getUser_id();
		SearchParam param = new SearchParam();
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);

		PageDataList pageDataList = this.userCreditService.getCreditLogPage(
				page, param, type_id, user_id);

		List typeList = this.userCreditService.getCreditTypeAll();
		List list = pageDataList.getList();
		this.request.setAttribute("typeList", typeList);
		this.request.setAttribute("type_id", Long.valueOf(type_id));
		this.request.setAttribute("creditList", list);
		this.request.setAttribute("page", pageDataList.getPage());
		this.request.setAttribute("params", param.toMap());
		return SUCCESS;
	}

	public UserCreditService getUserCreditService() {
		return this.userCreditService;
	}

	public void setUserCreditService(UserCreditService userCreditService) {
		this.userCreditService = userCreditService;
	}
}