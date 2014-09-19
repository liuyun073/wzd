package com.liuyun.site.web.action.member;

import com.opensymphony.xwork2.ModelDriven;
import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.domain.Notice;
import com.liuyun.site.domain.NoticeConfig;
import com.liuyun.site.domain.User;
import com.liuyun.site.model.UserinfoModel;
import com.liuyun.site.quartz.notice.NoticeJobQueue;
import com.liuyun.site.service.AccountService;
import com.liuyun.site.service.ArticleService;
import com.liuyun.site.service.FriendService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.service.UserinfoService;
import com.liuyun.site.tool.coder.MD5;
import com.liuyun.site.tool.ucenter.UCenterHelper;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class SecurityAction extends BaseAction implements
		ModelDriven<UserinfoModel> {
	private UserService userService;
	private UserinfoService userinfoService;
	private AccountService accountService;
	private ArticleService articleService;
	private FriendService friendService;
	private UserinfoModel userinfo = new UserinfoModel();

	public UserinfoModel getModel() {
		return this.userinfo;
	}

	public String userpwd() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		String errormsg = "";
		String oldpassword = this.request.getParameter("oldpassword");
		String newpassword = this.request.getParameter("newpassword");
		String chgpwdType = this.request.getParameter("actionType");
		this.request.setAttribute("query_type", "userpwd");
		if (!StringUtils.isNull(chgpwdType).equals("")) {
			if (newpassword != null) {
				errormsg = checkUserpwd(oldpassword, newpassword);
				if (!errormsg.equals("")) {
					this.request.setAttribute("errormsg", errormsg);
					return SUCCESS;
				}
				user.setPassword(new MD5().getMD5ofStr(newpassword));
				try {
					UCenterHelper.ucenter_edit(user.getUsername(), oldpassword,
							newpassword, user.getEmail());
				} catch (Exception e) {
					e.printStackTrace();
				}
				String msg = "修改密码成功！";
				this.request.setAttribute(MSG, msg);
				this.userService.modifyUserPwd(user);
				if (user != null) {
					NoticeConfig noticeConfig = Global.getNoticeConfig("password_update");
					if (noticeConfig.getSms() == 1L) {
						Notice notice = new Notice();
						notice.setContent("尊敬的" + Global.getValue("webname")
								+ "用户[" + user.getUsername() + "],您于"
								+ DateUtils.dateStr5(DateUtils.getNowTimeStr())
								+ "[" + getRequestIp() + "]修改登录密码成功，你的新密码为"
								+ newpassword);
						notice.setReceive_userid(user.getUser_id());
						notice.setSend_userid(1L);
						NoticeJobQueue.NOTICE.offer(notice);
					}
				}
			} else {
				errormsg = "修改密码失败！";
				this.request.setAttribute("errormsg", errormsg);
				return SUCCESS;
			}
		}

		return SUCCESS;
	}

	private String checkUserpwd(String oldpassword, String newpassword) {
		User user = (User) this.session.get(Constant.SESSION_USER);
		if (this.userService.login(user.getUsername(), oldpassword) == null)
			return "密码不正确，请输入您的旧密码 ";
		if ((newpassword.length() < 6) || (newpassword.length() > 15)) {
			return "新密码长度在6到15之间";
		}
		return "";
	}

	public String paypwd() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		String oldpassword = this.request.getParameter("oldpassword");
		String newpassword = this.request.getParameter("newpassword");
		String valicode = this.request.getParameter("valicode");
		if (newpassword != null) {
			String msg = "修改交易密码成功！";
			String errormsg = checkPaypwd(oldpassword, newpassword, valicode);
			if ("".equals(errormsg)) {
				user.setPaypassword(new MD5().getMD5ofStr(newpassword));
				this.userService.modifyPayPwd(user);
				this.request.setAttribute(MSG, msg);
				if (user != null) {
					NoticeConfig noticeConfig = Global.getNoticeConfig("paypwd_update");
					if (noticeConfig!=null && noticeConfig.getSms() == 1L) {
						Notice notice = new Notice();
						notice.setContent("尊敬的" + Global.getValue("webname")
								+ "用户[" + user.getUsername() + "],您于"
								+ DateUtils.dateStr5(DateUtils.getNowTimeStr())
								+ "[" + getRequestIp() + "]修改交易密码成功，你的新密码为"
								+ newpassword);
						notice.setReceive_userid(user.getUser_id());
						notice.setSend_userid(1L);
						NoticeJobQueue.NOTICE.offer(notice);
					}
				}
			} else {
				this.request.setAttribute("errormsg", errormsg);
			}
		}
		this.request.setAttribute("query_type", "paypwd");

		return SUCCESS;
	}

	private String checkPaypwd(String oldpassword, String newpassword,
			String valicode) {
		User user = (User) this.session.get(Constant.SESSION_USER);

		MD5 md5 = new MD5();
		String oldpwdmd5 = md5.getMD5ofStr(oldpassword);
		String userpaypwd = StringUtils.isNull(user.getPaypassword());
		if (userpaypwd.isEmpty())
			userpaypwd = user.getPassword();
		String userpwd = StringUtils.isNull(user.getPassword());
		if ((newpassword.length() < 6) || (newpassword.length() > 15))
			return "新密码长度在6到15之间";
		if (StringUtils.isNull(oldpassword).equals(""))
			return "原始交易密码不能为空！ ";
		if (!oldpwdmd5.equals(userpaypwd))
			return "原始交易密码不正确，请输入您的原始交易密码 ";
		if ((userpaypwd.equals("")) && (!oldpwdmd5.equals(userpwd)))
			return "还未设定交易密码，原始密码请输入您的登录密码！ ";
		if ((newpassword.length() < 6) || (newpassword.length() > 15))
			return "新密码长度在6到15之间";
		if (!checkValidImg(valicode)) {
			return "验证码不正确！";
		}
		return "";
	}

	public String protection() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);

		user = this.userService.getUserById(user.getUser_id());

		String question = StringUtils.isNull(this.request
				.getParameter("question"));
		String answer = StringUtils.isNull(this.request.getParameter("answer"));
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String mType = StringUtils.isNull(this.request.getParameter("mType"));
		if (answer != null) {
			if ("1".equals(type)) {
				if (user.getAnswer().equals(answer)) {
					this.request.setAttribute("mType", "2");
					this.request.setAttribute(MSG, "请重新设置密码保护！");
				} else {
					this.request.setAttribute("errormsg", "请输入正确的旧密码保护！");
				}
			} else if ("2".equals(mType)) {
				String errormsg = checkAnswer(answer);
				String msg = "密码保护设置成功！";
				user.setQuestion(question);
				user.setAnswer(answer);
				this.userService.modifyAnswer(user);
				if (errormsg.equals(""))
					this.request.setAttribute(MSG, msg);
				else {
					this.request.setAttribute("errormsg", errormsg);
				}
			}
		} else if (StringUtils.isBlank(user.getAnswer())) {
			this.request.setAttribute("mType", "2");
		}

		this.request.setAttribute("query_type", "protection");
		this.request.setAttribute("user", user);
		return SUCCESS;
	}

	private String checkAnswer(String answer) {
		if (answer.length() > 15) {
			return "答案长度小于50";
		}
		if (answer.isEmpty()) {
			return "答案不能为空!";
		}
		return "";
	}

	public String serialStatusSet() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);

		String oldpassword = this.request.getParameter("oldpassword");
		String newpassword = this.request.getParameter("newpassword");
		if (newpassword != null) {
			String msg = "修改密码成功！";
			String errormsg = checkUserpwd(oldpassword, newpassword);

			user.setPassword(newpassword);
			this.userService.modifyUserPwd(user);
			if (errormsg.equals(""))
				this.request.setAttribute(MSG, msg);
			else {
				this.request.setAttribute("errormsg", errormsg);
			}
		}
		this.request.setAttribute("query_type", "serialStatusSet");

		return SUCCESS;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public AccountService getAccountService() {
		return this.accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public UserinfoService getUserinfoService() {
		return this.userinfoService;
	}

	public void setUserinfoService(UserinfoService userinfoService) {
		this.userinfoService = userinfoService;
	}

	public ArticleService getArticleService() {
		return this.articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public FriendService getFriendService() {
		return this.friendService;
	}

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}
}