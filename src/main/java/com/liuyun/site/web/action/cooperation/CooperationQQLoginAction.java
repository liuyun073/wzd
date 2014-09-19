package com.liuyun.site.web.action.cooperation;

import com.liuyun.site.common.enums.EnumCooperationLoginType;
import com.liuyun.site.domain.CooperationLogin;
import com.liuyun.site.domain.QqGetUserInfoParamBean;
import com.liuyun.site.domain.QqGetUserInfoResultBean;
import com.liuyun.site.domain.User;
import com.liuyun.site.service.CooperationLoginService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.util.cooperation.OpenQqUtils;
import com.liuyun.site.web.action.BaseAction;
import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;

public class CooperationQQLoginAction extends BaseAction {
	private CooperationLoginService cooperationLoginService;
	private UserService userService;
	protected final Logger log = Logger.getLogger(super.getClass());

	public void qqLogin() {
		OpenQqUtils oqu = new OpenQqUtils();
		try {
			this.request.setAttribute("qqLoginUrl", oqu.getQqLoginUrl());
			this.response.sendRedirect(oqu.getQqLoginUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String loginSuccess() throws IOException {
		OpenQqUtils oqu = new OpenQqUtils();

		String accessTokenUrl = oqu.getAccessTokenUrl(this.request
				.getParameter("code"));

		String accessToken = oqu.getAccessToken(accessTokenUrl);

		String openId = oqu.getOpenId(accessToken);

		if ((openId == null) || (openId.length() <= 0) || (accessToken == null)
				|| (accessToken.length() <= 0)) {
			return "loginError";
		}
		QqGetUserInfoParamBean paramBean = new QqGetUserInfoParamBean();
		paramBean.setAccessToken(accessToken);
		paramBean.setOpenId(openId);
		QqGetUserInfoResultBean userInfoResultBean = oqu.getUserInfo(paramBean);

		if (userInfoResultBean.getErrorFlg()) {
			this.log.error("QQ合作登录_QQ登录信息验证失败 ");
			return "loginError";
		}
		String getNickname = userInfoResultBean.getNickName();
		if ((getNickname != null) && (getNickname.length() > 0)) {
			this.request.setAttribute("nickname", getNickname);
		}

		CooperationLogin cooperation = new CooperationLogin();
		cooperation.setOpen_id(openId);
		cooperation.setOpen_key(accessToken);
		cooperation.setType(Byte
				.valueOf(EnumCooperationLoginType.COOPERATION_QQ.getValue()));
		this.cooperationLoginService.addCooperationLogin(cooperation);

		CooperationLogin qqLogin = this.cooperationLoginService
				.getCooperationLogin(openId, accessToken, Byte
						.valueOf(EnumCooperationLoginType.COOPERATION_QQ
								.getValue()));

		if ((qqLogin != null) && (qqLogin.getUser_id() > 0L)) {
			User user = this.userService.getUserById(qqLogin.getUser_id());
			this.request.setAttribute("openUser", user);
			try {
				this.request.getRequestDispatcher("/user/login.html").forward(
						this.request, this.response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} else if ((qqLogin != null) && (qqLogin.getId() > 0L)
				&& (qqLogin.getUser_id() <= 0L)) {
			this.request.setAttribute("openLoginId", Long.valueOf(qqLogin
					.getId()));
			return "loginRegister";
		}
		return "loginError";
	}

	public CooperationLoginService getCooperationLoginService() {
		return this.cooperationLoginService;
	}

	public void setCooperationLoginService(
			CooperationLoginService cooperationLoginService) {
		this.cooperationLoginService = cooperationLoginService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}