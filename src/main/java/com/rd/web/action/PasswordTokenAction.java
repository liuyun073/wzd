package com.rd.web.action;

import com.opensymphony.xwork2.ModelDriven;
import com.rd.domain.PasswordToken;
import com.rd.domain.User;
import com.rd.service.PasswordTokenService;
import com.rd.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class PasswordTokenAction extends BaseAction implements
		ModelDriven<PasswordToken> {
	private PasswordTokenService passwordTokenService;
	private List<PasswordToken> tokenList;
	private PasswordToken passwordToken = new PasswordToken();

	public String tokenListByUserId() {
		User user = getSessionUser();
		List tokenList = this.passwordTokenService
				.getPasswordTokenByUserId(user.getUser_id());
		this.request.setAttribute("tokenList", tokenList);
		return SUCCESS;
	}

	public String modifyPasswordToken() {
		String type = this.request.getParameter("type");
		User user = getSessionUser();
		if (type.equals("add")) {
			boolean isOk = true;
			try {
				List list = new ArrayList();
				PasswordToken pt1 = new PasswordToken();

				String passwordToken1 = StringUtils.isNull(this.request
						.getParameter("passwordToken1"));
				pt1.setQuestion(passwordToken1);
				String answer1 = StringUtils.isNull(this.request
						.getParameter("answer1"));
				pt1.setAnswer(StringUtils.isNull(answer1));

				PasswordToken pt2 = new PasswordToken();
				String passwordToken2 = StringUtils.isNull(this.request
						.getParameter("passwordToken2"));
				pt2.setQuestion(passwordToken2);
				String answer2 = this.request.getParameter("answer2");
				pt2.setAnswer(StringUtils.isNull(answer2));

				PasswordToken pt3 = new PasswordToken();
				String answer3 = this.request.getParameter("passwordToken3");
				String passwordToken3 = StringUtils.isNull(answer3);
				pt3.setQuestion(passwordToken3);
				pt3.setAnswer(StringUtils.isNull(this.request
						.getParameter("answer3")));
				if (((passwordToken1.equals(passwordToken2)) && (answer2
						.equals("")))
						|| ((passwordToken2.equals(passwordToken3)) && (answer3
								.equals("")))
						|| (passwordToken1.equals(passwordToken3))) {
					message("密保问题重复，请重新设置!");
					return MSG;
				}
				if (!pt1.getAnswer().equals("")) {
					list.add(pt1);
				}
				if (!pt2.getAnswer().equals("")) {
					list.add(pt2);
				}
				if (!pt3.getAnswer().equals("")) {
					list.add(pt3);
				}
				this.passwordTokenService.addPasswordToken(list, user
						.getUser_id());
			} catch (Exception e) {
				isOk = false;
				e.printStackTrace();
			}
			if (isOk) {
				tokenListByUserId();
			}
		} else if (type.equals("update")) {
			this.passwordTokenService.updatePasswordTokenByList(this.tokenList);
		} else {
			return ERROR;
		}

		return SUCCESS;
	}

	public PasswordTokenService getPasswordTokenService() {
		return this.passwordTokenService;
	}

	public void setPasswordTokenService(
			PasswordTokenService passwordTokenService) {
		this.passwordTokenService = passwordTokenService;
	}

	public List<PasswordToken> getTokenList() {
		return this.tokenList;
	}

	public void setTokenList(List<PasswordToken> tokenList) {
		this.tokenList = tokenList;
	}

	public PasswordToken getModel() {
		return this.passwordToken;
	}
}