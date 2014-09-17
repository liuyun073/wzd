package com.rd.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.rd.common.enums.EnumIntegralTypeName;
import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.domain.CreditType;
import com.rd.domain.PasswordToken;
import com.rd.domain.User;
import com.rd.domain.UserCache;
import com.rd.domain.UserCreditLog;
import com.rd.domain.UserTrack;
import com.rd.domain.Userinfo;
import com.rd.model.DetailUser;
import com.rd.model.UserAccountSummary;
import com.rd.service.AccountService;
import com.rd.service.BorrowService;
import com.rd.service.CooperationLoginService;
import com.rd.service.MemberBorrowService;
import com.rd.service.MessageService;
import com.rd.service.PasswordTokenService;
import com.rd.service.RewardStatisticsService;
import com.rd.service.UserCreditService;
import com.rd.service.UserService;
import com.rd.service.UserinfoService;
import com.rd.tool.coder.BASE64Decoder;
import com.rd.tool.coder.MD5;
import com.rd.tool.iphelper.IPUtils;
import com.rd.tool.javamail.Mail;
import com.rd.tool.ucenter.UCenterHelper;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;


/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： UserAction   
 * 类描述： TODO(这里用一句话描述这个类的作用)   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Nov 4, 2013 7:06:18 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Nov 4, 2013 7:06:18 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class UserAction extends BaseAction implements ModelDriven<User> {
	private static Logger logger = Logger.getLogger(UserAction.class);
	private static final long serialVersionUID = -7819826246025932070L;
	private UserService userService;
	private UserinfoService userinfoService;
	private AccountService accountService;
	private BorrowService borrowService;
	private MemberBorrowService memberBorrowService;
	private PasswordTokenService passwordTokenService;
	private CooperationLoginService cooperationLoginService;
	private RewardStatisticsService rewardStatisticsService;
	private UserCreditService userCreditService;
	private User user = new User();
	private UserCache userCache = new UserCache();
	private String validatecode;
	private StringBuffer msg = new StringBuffer();

	private String backUrl = "";
	private MessageService messageService;

	public void setMemberBorrowService(MemberBorrowService memberBorrowService) {
		this.memberBorrowService = memberBorrowService;
	}

	public MessageService getMessageService() {
		return this.messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	
	/**
	 * **************************************************************************************
	 * 方法名: login 
	 * 功能: 前台登录
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String login() throws Exception {
		User openUser = (User) this.request.getAttribute("openUser");
		if ((openUser != null) && (openUser.getUsername() != null)) {
			this.user = openUser;
		}

		String openLoginId = this.request.getParameter("openLoginId");

		String redirectURL = paramString("redirectURL");
		this.request.setAttribute("redirectURL", redirectURL);
		int loginFailMaxTimes = Global.getInt("login_fail_maxtimes");
		loginFailMaxTimes = (loginFailMaxTimes < 1) ? 3 : loginFailMaxTimes;
		String returnUrl = "/member/index.html";
		String source = StringUtils.isNull(this.request.getParameter("source"));
		if ((!hasCookieValue()) && (openUser == null)) {
			if (isSession()) {
				return "member";
			}
			String actionType = getActionType();
			if ("".equals(actionType)) {
				if ((openLoginId != null) && (openLoginId.length() > 0) && (Long.parseLong(openLoginId) > 0L)) {
					this.request.setAttribute("openLoginId", openLoginId);
				}
				return "login";
			}
			this.backUrl = ("/user/login.html?redirectURL=" + URLEncoder.encode(redirectURL, "UTF-8"));
			String flag = loginValidate();

			if (flag.equals(FAIL)) {
				logger.info("存在非法数据");
				return flag;
			}

		}

		User u = null;
		if ((openUser == null) || (openUser.getUsername() == null))
			try {
				u = this.userService.login(this.user.getUsername(), this.user.getPassword());
			} catch (Exception e) {
				e.printStackTrace();
			}
		else {
			u = openUser;
		}
		if (u == null) {
			String errorMsg = "用户不存在或密码错误！";
			logger.info(errorMsg);
			this.msg.append(errorMsg);
			message(this.msg.toString(), this.backUrl);

			if (Global.getInt("login_fail_limit") == 1) {
				User failUser = this.userService.getUserByName(this.user.getUsername());
				if (failUser != null) {
					if (failUser.getIslock() == 1) {
						errorMsg = "该账户" + failUser.getUsername() + "已经被锁定！";
						logger.info(errorMsg);
						this.msg.append(errorMsg);
						message(this.msg.toString(), this.backUrl);
						return FAIL;
					}

					int loginFailTimes = this.userService.getLoginFailTimes(failUser.getUser_id());

					this.userService.updateLoginFailTimes(failUser.getUser_id());
					if (loginFailTimes >= loginFailMaxTimes) {
						errorMsg = "该账户" + failUser.getUsername() + "已经被锁定！";
						logger.info(errorMsg);
						this.msg.append(errorMsg);
						message(this.msg.toString(), this.backUrl);

						this.userService.updateUserIsLock(failUser.getUser_id());

						this.userService.cleanLoginFailTimes(failUser.getUser_id());
						return FAIL;
					}
				}
			}

			if (Global.getWebid().equals("mszb")) {
				return "login";
			}
			return FAIL;
		}

		int loginFailTimes = this.userService.getLoginFailTimes(u.getUser_id());
		if (u.getIslock() == 1) {
			String errorMsg = "该账户" + u.getUsername() + "已经被锁定！";
			logger.info(errorMsg);
			this.msg.append(errorMsg);
			message(this.msg.toString(), this.backUrl);
			if (Global.getWebid().equals("mszb")) {
				return "login";
			}
			return FAIL;
		}
		if (u.getStatus() == 0) {
			String errorMsg = "该账户" + u.getUsername() + "已经被关闭！";
			logger.info(errorMsg);
			this.msg.append(errorMsg);
			message(this.msg.toString(), this.backUrl);

			if (Global.getWebid().equals("mszb")) {
				return "login";
			}
			return FAIL;
		}

		if (loginFailTimes >= loginFailMaxTimes) {
			String errorMsg = "该账户" + u.getUsername() + "已经被锁定！";
			logger.info(errorMsg);
			this.msg.append(errorMsg);
			message(this.msg.toString(), this.backUrl);
			logger.info("用户ID：" + u.getUser_id());
			this.userService.updateUserIsLock(u.getUser_id());
			this.userService.cleanLoginFailTimes(u.getUser_id());
			return FAIL;
		}
		long logintime = NumberUtils.getLong(DateUtils.getNowTimeStr());
		u.setLogintime(logintime);
		this.userService.updateuser(u);
		u.hideChar();

		this.session.put(Constant.SESSION_USER, u);

		String username = "";
		try {
			username = URLEncoder.encode(u.getUsername(), "UTF-8");
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}

		Cookie usernameCookie = new Cookie("username", username);
		Cookie logintimeCookie = new Cookie(Constant.LOGINTIME, String.valueOf(System.currentTimeMillis()));

		usernameCookie.setMaxAge(3600);
		logintimeCookie.setMaxAge(3600);

		usernameCookie.setPath("/");
		logintimeCookie.setPath("/");

		this.response.addCookie(usernameCookie);
		this.response.addCookie(logintimeCookie);

		long user_id = u.getUser_id();

		this.userService.cleanLoginFailTimes(user_id);
		int unreadmsg = this.messageService.getUnreadMesage(user_id);
		this.request.setAttribute("unreadmsg", Integer.valueOf(unreadmsg));

		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
		String nowMsg = "本次：" + getAreaByIp() + " " + sdf.format(nowDate);
		UserTrack track = this.userService.getLastUserTrack(u.getUser_id());
		String lastMsg = "";
		if (track == null) {
			lastMsg = "第一次登陆！";
		} else {
			lastMsg = "上次：" + getAreaByIp(track.getLogin_ip()) + " " + sdf.format(DateUtils.getDate(track.getLogin_time()));
			User lastUser = new User();
			lastUser.setLasttime(sdf.format(DateUtils.getDate(track.getLogin_time())));

			this.userService.updateUserLastInfo(lastUser);
		}
		String ipmsg = lastMsg + "<br/>" + nowMsg;

		UserTrack newTrack = new UserTrack();
		newTrack.setUser_id("" + u.getUser_id());
		newTrack.setLogin_ip(getRequestIp());
		newTrack.setLogin_time("" + nowDate.getTime() / 1000L);
		this.userService.addUserTrack(newTrack);

		String errorMsg = "登录成功<br/><font style=color:red>请确认您的上一次登录时间</font><br/><font style=color:red>" + ipmsg + "</font>";
		logger.info(errorMsg);
		this.msg.append(errorMsg);
		message(this.msg.toString(), returnUrl, "进入用户中心");

		if ("1".equals(Global.getValue(Constant.LOGIN_TIME))) {
			return MSG;
		}
		if (!redirectURL.isEmpty()) {
			returnUrl = redirectURL;
			this.response.sendRedirect(redirectURL);
			return null;
		}
		if ((openLoginId != null) && (openLoginId.length() > 0) && (Long.parseLong(openLoginId) > 0L)) {
			this.cooperationLoginService.updateCooperationUserIdById(u.getUser_id(), Long.parseLong(openLoginId));
		}

		if (source.equals("index")) {
			return "index";
		}
		return "member";
	}

	/**
	 * **************************************************************************************
	 * 方法名: logout 
	 * 功能: 前台登出 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String logout() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put(Constant.SESSION_USER, null);
		return SUCCESS;
	}

	
	/**
	 * **************************************************************************************
	 * 方法名: register 
	 * 功能: 前台注册 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String register() throws Exception {
		if (isSession()) {
			return "member";
		}
		String actionType = getActionType();
		boolean open = true;
		if ("".equals(actionType)) {
			return "register";
		}
		if (actionType.equals("adminadduser")) {
			open = false;
		}

		this.backUrl = ((open) ? "/user/register.html" : "/admin/userinfo/adduser.html");

		String flag = regValidate();

		if (flag.equals(FAIL)) {
			logger.debug("存在非法数据");
			return flag;
		}
		String pwd = this.user.getPassword();

		MD5 md5 = new MD5();
		this.user.setPassword(md5.getMD5ofStr(this.user.getPassword()));
		this.user.setAddtime(getTimeStr());
		this.user.setAddip(getRequestIp());
		User newUser = this.userService.register(this.user);
		User u = this.userService.getUserByName(newUser.getUsername());
		HttpServletRequest request = ServletActionContext.getRequest();
		String realip = IPUtils.getRemortIP(request);

		UserCache cache = new UserCache();
		cache.setUser_id(u.getUser_id());
		this.userService.saveUserCache(cache);
		
		/**/
		//在 Discuz! Ucenter 中注册
		try {
			String retMsg = UCenterHelper.ucenter_register(this.user.getUsername(), pwd, this.user.getEmail());
			logger.info(this.user.getUsername());
			logger.info(retMsg);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			sendActiveMail(newUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (open) {
			this.msg.append("注册成功！");
			if (newUser.getInvite_userid() != null) {
				this.rewardStatisticsService.addRewardStatistics(newUser.getUser_id(), getRequestIp());
			}

			String openLoginId = request.getParameter("openLoginId");
			if ((openLoginId != null) && (openLoginId.length() > 0) && (Long.parseLong(openLoginId) > 0L)) {
				this.cooperationLoginService.updateCooperationUserIdById(u.getUser_id(), Long.parseLong(openLoginId));
			}
			message(this.msg.toString(), "/user/login.html", "激活邮件已发送，请登录邮箱激活！");
		} else {
			this.msg.append("添加成功");
			message(this.msg.toString(), "/admin/userinfo/user.html", "点击返回！");
		}
		
		return SUCCESS;
	}

	
	public String checkUsername() throws Exception {
		logger.debug(this.user);
		boolean result = this.userService.checkUsername(this.user.getUsername());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (result)
			out.println("1");
		else {
			out.println("");
		}
		out.flush();
		out.close();
		return null;
	}

	public String checkEmail() throws Exception {
		boolean result = this.userService.checkEmail(this.user.getEmail());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (result)
			out.println("1");
		else {
			out.println("");
		}
		out.flush();
		out.close();
		return null;
	}

	public String show() throws Exception {
		DetailUser showUser = this.userService.getDetailUser(this.user
				.getUser_id());
		Userinfo info = this.userinfoService.getUserinfoByUserid(this.user
				.getUser_id());
		UserAccountSummary uas = this.accountService
				.getUserAccountSummary(this.user.getUser_id());
		List borrowList = this.borrowService.getBorrowList(this.user
				.getUser_id());
		List investList = this.borrowService.getSuccessListByUserid(this.user
				.getUser_id(), "1");

		List repament_scuess = this.memberBorrowService.getRepayMentList(
				"repaymentyes", this.user.getUser_id());
		List repament_failure = this.memberBorrowService.getRepayMentList(
				"repayment", this.user.getUser_id());
		List borrowFlowList = this.borrowService
				.getBorrowFlowListByuserId(this.user.getUser_id());
		List earlyRepaymentList = this.borrowService.getRepaymentList(null,
				this.user.getUser_id());
		List lateRepaymentList = this.borrowService.getRepaymentList(
				"lateRepayment", this.user.getUser_id());
		List overdueRepaymentList = this.borrowService.getRepaymentList(
				"overdueRepayment", this.user.getUser_id());
		List overdueRepaymentsList = this.borrowService.getRepaymentList(
				"overdueRepayments", this.user.getUser_id());
		List overdueNoRepaymentsList = this.borrowService.getRepaymentList(
				"overdueNoRepayments", this.user.getUser_id());

		this.request.setAttribute("repament_scuess", Integer
				.valueOf(repament_scuess.size()));
		this.request.setAttribute("repament_failure", Integer
				.valueOf(repament_failure.size()));
		this.request.setAttribute("borrowFlowList", Integer
				.valueOf(borrowFlowList.size()));
		this.request.setAttribute("earlyRepaymentList", Integer
				.valueOf(earlyRepaymentList.size()));
		this.request.setAttribute("lateRepaymentList", Integer
				.valueOf(lateRepaymentList.size()));
		this.request.setAttribute("overdueRepaymentList", Integer
				.valueOf(overdueRepaymentList.size()));
		this.request.setAttribute("overdueRepaymentsList", Integer
				.valueOf(overdueRepaymentsList.size()));
		this.request.setAttribute("overdueNoRepaymentsList", Integer
				.valueOf(overdueNoRepaymentsList.size()));

		this.request.setAttribute("u", showUser);
		this.request.setAttribute("info", info);
		this.request.setAttribute("summary", uas);
		this.request.setAttribute("borrowList", borrowList);
		this.request.setAttribute("investList", investList);
		logger.info(showUser);
		return SUCCESS;
	}

	public String getpwd() throws Exception {
		if (isBlank()) {
			this.request.setAttribute("getpwdtype", "pwd");
			return SUCCESS;
		}

		String username = this.request.getParameter("username");
		String validcode = this.request.getParameter("validcode");
		this.request.setAttribute("getpwdtype", "pwd");
		User u = null;
		if (StringUtils.isNull(validcode).equals("")) {
			message("验证码不能为空！");
			return MSG;
		}
		if (StringUtils.isNull(username).equals("")) {
			message("用户名不能为空！");
			return MSG;
		}
		if (!checkValidImg(validcode)) {
			message("验证码不正确！");
			return MSG;
		}
		u = this.userService.getUserByName(username);
		if (u == null) {
			message("用户名未注册！");
			return MSG;
		}
		List tokenList = this.passwordTokenService
				.getPasswordTokenByUsername(username);
		if (tokenList.size() == 0) {
			return getPwdMail(u);
		}
		this.request.setAttribute("tokenList", tokenList);
		this.request.getRequestDispatcher("/user/getpwdByEmail.html").forward(
				this.request, this.response);

		return "pwd";
	}

	private String getPwdMail(User u) throws Exception {
		sendGetPwdMail(u);
		String email = u.getEmail();
		email = email.substring(0, 2).concat("*").concat(
				email.substring(email.indexOf('@')));
		message("找回密码邮件发送至" + email + "！", "/user/login.html");
		return MSG;
	}

	private String getPayPwdMail(User u) throws Exception {
		sendGetPayPwdMail(u);
		String email = u.getEmail();
		email = email.substring(0, 2).concat("*").concat(
				email.substring(email.indexOf('@')));
		message("找回密码邮件发送至" + email + "！", "/user/login.html");
		return MSG;
	}

	public String getpwdByEmail() throws Exception {
		this.request.getAttribute("tokenList");
		String htmlType = this.request.getParameter("htmlType");
		if (StringUtils.isBlank(htmlType)) {
			this.request.getAttribute("tokenList");
			this.request.setAttribute("getEmail", "pwdByEmail");
			this.request.setAttribute("pwdComUrl", "/user/getpwdByEmail.html");
			return SUCCESS;
		}
		String username = this.request.getParameter("username");
		String[] answer = this.request.getParameterValues("answer");
		List tokenList = this.passwordTokenService
				.getPasswordTokenByUsername(username);
		this.request.setAttribute("getEmail", "pwdByEmail");
		this.request.setAttribute("tokenList", tokenList);
		User u = this.userService.getUserByName(username);
		if (username == null) {
			message("用户名不能为空");
			return MSG;
		}
		if (StringUtils.isNull(answer).equals("")) {
			message("密保答案不能为空！");
			return MSG;
		}
		int size = tokenList.size();
		PasswordToken passwordToken = null;
		for (int i = 0; i < size; ++i) {
			passwordToken = (PasswordToken) tokenList.get(i);
			for (int j = 0; j < answer.length; ++j) {
				if ((!answer[j].equals(""))
						&& (answer[j].equals(passwordToken.getAnswer()))) {
					return getPwdMail(u);
				}
			}

		}

		message("密保问题不对，请重试！");
		return MSG;
	}

	public String getpaypwdByEmail() throws Exception {
		this.request.getAttribute("tokenList");
		String htmlType = this.request.getParameter("htmlType");
		if (StringUtils.isBlank(htmlType)) {
			this.request.setAttribute("getEmail", "paypwdByEmail");
			this.request.setAttribute("pwdComUrl",
					"/user/getpaypwdByEmail.html");
			return SUCCESS;
		}
		String username = this.request.getParameter("username");
		String[] answer = this.request.getParameterValues("answer");
		List tokenList = this.passwordTokenService
				.getPasswordTokenByUsername(username);
		this.request.setAttribute("getEmail", "paypwdByEmail");
		this.request.setAttribute("tokenList", tokenList);
		User u = this.userService.getUserByName(username);
		if (username == null) {
			message("用户名不能为空");
			return MSG;
		}
		if (StringUtils.isNull(answer).equals("")) {
			message("密保答案不能为空！");
			return MSG;
		}
		int size = tokenList.size();
		PasswordToken passwordToken = null;
		for (int i = 0; i < size; ++i) {
			passwordToken = (PasswordToken) tokenList.get(i);
			for (int j = 0; j < answer.length; ++j) {
				if ((!answer[j].equals(""))
						&& (answer[j].equals(passwordToken.getAnswer()))) {
					return getPayPwdMail(u);
				}
			}

		}

		message("密保问题不对，请重试！");
		return MSG;
	}

	public String getpaypwd() throws Exception {
		if (isBlank()) {
			this.request.setAttribute("getpwdtype", "paypwd");
			return SUCCESS;
		}
		String username = this.request.getParameter("username");
		String validcode = this.request.getParameter("validcode");
		this.request.setAttribute("getpwdtype", "paypwd");
		User u = null;
		if (StringUtils.isNull(validcode).equals("")) {
			message("验证码不能为空！");
			return MSG;
		}
		if (StringUtils.isNull(username).equals("")) {
			message("用户名不能为空！");
			return MSG;
		}
		if (!checkValidImg(validcode)) {
			message("验证码不正确！");
			return MSG;
		}
		u = this.userService.getUserByName(username);
		if (u == null) {
			message("用户名未注册！");
			return MSG;
		}
		List tokenList = this.passwordTokenService
				.getPasswordTokenByUsername(username);
		if (tokenList.size() == 0) {
			return getPayPwdMail(u);
		}
		this.request.setAttribute("tokenList", tokenList);
		this.request.getRequestDispatcher("/user/getpaypwdByEmail.html")
				.forward(this.request, this.response);

		return "paypwd";
	}

	public String setpwd() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String idstr = StringUtils.isNull(this.request.getParameter("id"));
		String errormsg = "";
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] idstrBytes = decoder.decodeBuffer(idstr);
		String decodeidstr = new String(idstrBytes);

		String[] idstrArr = decodeidstr.split(",");

		if (idstrArr.length < 4) {
			errormsg = "链接无效，请<a href='" + this.request.getContextPath()
					+ "/getpwd.html'>点此</a>重新获取邮件！";
			message(errormsg, "/getpwd.html");
			return FAIL;
		}
		long setpwdTime = NumberUtils.getLong(idstrArr[2]);
		if (System.currentTimeMillis() - setpwdTime * 1000L > 86400000L) {
			errormsg = "链接有效时间1天，已经失效，请<a href='"
					+ this.request.getContextPath()
					+ "/getpwd.html'>点此</a>重新获取邮件!";
			message(errormsg, "/getpwd.html");
			return FAIL;
		}
		User u = this.userService.getUserById(NumberUtils.getLong(idstrArr[0]));
		if (u == null) {
			errormsg = "用户不存在，请联系客服!";
			message(errormsg, "/getpwd.html");
			return FAIL;
		}

		String userIDAddtime = u.getUser_id() + u.getAddtime();
		MD5 md5 = new MD5();
		userIDAddtime = md5.getMD5ofStr(userIDAddtime);
		if (!StringUtils.isNull(idstrArr[1]).equals(userIDAddtime)) {
			errormsg = "找回密码失败，请联系客服!";
			message(errormsg, "/getpwd.html");
			return FAIL;
		}

		if (type.equals("")) {
			this.request.setAttribute("u", u);
			return SUCCESS;
		}
		String password = StringUtils.isNull(this.request
				.getParameter("password"));
		String confirm_password = StringUtils.isNull(this.request
				.getParameter("confirm_password"));

		User user = u;
		if ((password.equals("")) || (confirm_password.equals(""))) {
			errormsg = "新密码不能为空！";
		} else if (!password.equals(confirm_password)) {
			errormsg = "两次密码不一致！";
		} else {
			user.setPassword(md5.getMD5ofStr(password));
			this.userService.modifyUserPwd(user);
			message("重置密码成功！", "/user/login.html", "点此登录");
			return OK;
		}
		this.request.setAttribute("u", user);
		this.request.setAttribute("errormsg", errormsg);
		return SUCCESS;
	}

	public String setpaypwd() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String idstr = StringUtils.isNull(this.request.getParameter("id"));
		String errormsg = "";
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] idstrBytes = decoder.decodeBuffer(idstr);
		String decodeidstr = new String(idstrBytes);

		String[] idstrArr = decodeidstr.split(",");

		if (idstrArr.length < 4) {
			errormsg = "链接无效，请<a href='" + this.request.getContextPath()
					+ "/getpwd.html'>点此</a>重新获取邮件！";
			message(errormsg, "/getpwd.html");
			return FAIL;
		}
		long setpwdTime = NumberUtils.getLong(idstrArr[2]);
		if (System.currentTimeMillis() - setpwdTime * 1000L > 86400000L) {
			errormsg = "链接有效时间1天，已经失效，请<a href='"
					+ this.request.getContextPath()
					+ "/getpwd.html'>点此</a>重新获取邮件!";
			message(errormsg, "/getpwd.html");
			return FAIL;
		}
		User u = this.userService.getUserById(NumberUtils.getLong(idstrArr[0]));
		if (u == null) {
			errormsg = "用户不存在，请联系客服!";
			message(errormsg, "/getpwd.html");
			return FAIL;
		}

		String userIDAddtime = u.getUser_id() + u.getAddtime();
		MD5 md5 = new MD5();
		userIDAddtime = md5.getMD5ofStr(userIDAddtime);
		if (!StringUtils.isNull(idstrArr[1]).equals(userIDAddtime)) {
			errormsg = "招回密码失败，请联系客服!";
			message(errormsg, "/getpwd.html");
			return FAIL;
		}

		if (type.equals("")) {
			this.request.setAttribute("u", u);
			return SUCCESS;
		}
		String password = StringUtils.isNull(this.request
				.getParameter("password"));
		String confirm_password = StringUtils.isNull(this.request
				.getParameter("confirm_password"));

		User user = u;
		if ((password.equals("")) || (confirm_password.equals(""))) {
			errormsg = "新密码不能为空！";
		} else if (!password.equals(confirm_password)) {
			errormsg = "两次密码不一致！";
		} else {
			user.setPaypassword(md5.getMD5ofStr(password));
			this.userService.modifyPayPwd(user);
			message("重置密码成功！", "/user/login.html", "点此登录");
			return OK;
		}
		this.request.setAttribute("u", user);
		this.request.setAttribute("errormsg", errormsg);
		return SUCCESS;
	}

	public String regInvite() throws Exception {
		String u = StringUtils.isNull(this.request.getParameter("u"));
		BASE64Decoder decoder = new BASE64Decoder();
		String user_id = decoder.decodeStr(u);
		long userid = NumberUtils.getLong(user_id);
		User inviteUser = this.userService.getUserById(userid);
		this.request.setAttribute("inviteUser", inviteUser);
		return SUCCESS;
	}

	private String loginValidate() {
		if (StringUtils.isNull(this.user.getUsername()).equals("")) {
			String errorMsg = "用户名不能为空！";
			logger.info(errorMsg);
			this.msg.append(errorMsg);
			message(this.msg.toString(), this.backUrl);
			return FAIL;
		}
		if (StringUtils.isNull(this.user.getPassword()).equals("")) {
			String errorMsg = "密码不能为空！";
			logger.info(errorMsg);
			this.msg.append(errorMsg);
			message(this.msg.toString(), this.backUrl);
			return FAIL;
		}
		return SUCCESS;
	}

	private void setCookie() {
		String cookieValue = StringUtils.isNull(this.request
				.getParameter("login_cookie"));
		if (!cookieValue.isEmpty()) {
			int cookieDate = 0;
			if ((cookieValue != null) && (cookieValue != "")) {
				cookieDate = Integer.parseInt(cookieValue);
			}
			Cookie loginNameCookie = new Cookie("cookie_username", this.user
					.getUsername());
			Cookie loginPassCookie = new Cookie("cookie_password", this.user
					.getPassword());
			loginNameCookie.setMaxAge(cookieDate * 60);
			loginPassCookie.setMaxAge(cookieDate * 60);
			this.response.addCookie(loginNameCookie);
			this.response.addCookie(loginPassCookie);
		}
	}

	private boolean hasCookieValue() {
		String username = "";
		String password = "";
		Cookie[] cookies = this.request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; ++i) {
				Cookie cookie = cookies[i];
				if ("cookie_username".equals(StringUtils.isNull(cookie
						.getName()))) {
					username = cookie.getValue();
				}
				if (!"cookie_password".equals(StringUtils.isNull(cookie
						.getName())))
					continue;
				password = cookie.getValue();
			}

			if ((StringUtils.isBlank(username))
					|| (StringUtils.isBlank(password))) {
				return false;
			}
			this.user.setUsername(username);
			this.user.setPassword(password);
			return true;
		}

		return false;
	}

	private String regValidate() {
		String flag = loginValidate();
		if (flag.equals(FAIL)) {
			return FAIL;
		}
		User existUser = this.userService
				.getUserByName(this.user.getUsername());
		if (existUser != null) {
			String errorMsg = "用户名已经存在！";
			logger.info(errorMsg);
			this.msg.append(errorMsg);
			message(this.msg.toString(), this.backUrl);
			return FAIL;
		}
		if (StringUtils.isNull(this.user.getRealname()).equals("")) {
			String errorMsg = "真实姓名不能为空！";
			logger.info(errorMsg);
			this.msg.append(errorMsg);
			message(this.msg.toString(), this.backUrl);
			return FAIL;
		}
		if (!StringUtils.isEmail(this.user.getEmail())) {
			String errorMsg = "邮箱格式不对！";
			logger.info(errorMsg);
			this.msg.append(errorMsg);
			message(this.msg.toString(), this.backUrl);
			return FAIL;
		}
		boolean isHasEmail = this.userService.checkEmail(this.user.getEmail());
		if (!isHasEmail) {
			String errorMsg = "邮箱已经存在！";
			logger.info(errorMsg);
			this.msg.append(errorMsg);
			message(this.msg.toString(), this.backUrl);
			return FAIL;
		}
		if ((StringUtils.isNull(Global.getWebid()).equals("xdcf"))
				|| (StringUtils.isNull(Global.getWebid()).equals("zrzb"))
				|| (StringUtils.isNull(Global.getWebid()).equals("yydai"))) {
			String validcode = StringUtils.isNull(this.request
					.getParameter("validcode"));
			if (!checkValidImg(validcode)) {
				String errorMsg = "验证码不正确！";
				logger.info(errorMsg);
				this.msg.append(errorMsg);
				message(this.msg.toString(), this.backUrl);
				return FAIL;
			}
		}
		return SUCCESS;
	}

	private void sendActiveMail(User user) throws Exception {
		String to = user.getEmail();
		Mail m = Mail.getInstance();
		m.setTo(to);
		m.readActiveMailMsg();
		m.replace(user.getUsername(), to, "/user/active.html?id="
				+ m.getdecodeIdStr(user));
		logger.debug("Email_msg:" + m.getBody());
		m.sendMail();
	}

	/**
	 * **************************************************************************************
	 * 方法名: active 
	 * 功能: 用户邮件激活 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String active() throws Exception {
		User user = null;
		String msg = "";
		String errormsg = "";
		String idstr = this.request.getParameter("id");
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] idstrBytes = decoder.decodeBuffer(idstr);
		String decodeidstr = new String(idstrBytes);
		String[] idstrArr = decodeidstr.split(",");

		if (idstrArr.length < 4) {
			errormsg = "链接无效，请<a href='" + this.request.getContextPath()
					+ "/user/login.html'>点此登录</a>，重新获取邮件！";
			message(errormsg, "/user/login.html");
			return MSG;
		}
		long activeTime = NumberUtils.getLong(idstrArr[2]);

		if (System.currentTimeMillis() - activeTime * 1000L > 86400000L) {
			errormsg = "链接有效时间1天，已经失效，请<a href='"
					+ this.request.getContextPath()
					+ "/user/login.html'>点此登录</a>，重新获取邮件!";
			message(errormsg, "/user/login.html");
			return MSG;
		}
		if (idstrArr.length < 1) {
			errormsg = "解析激活码失败，请联系管理员！";
			message(errormsg, "/member/index.html");
			return MSG;
		}
		String useridStr = idstrArr[0];
		long user_id = 0L;
		try {
			user_id = Long.parseLong(useridStr);
		} catch (Exception e) {
			user_id = 0L;
		}
		if (user_id > 0L) {
			user = this.userService.getUserById(user_id);
			if (user == null) {
				errormsg = "当前用户不存在，请重新注册！";
				message(errormsg, "/user/register.html");
				return MSG;
			}
		} else {
			errormsg = "解析激活码失败，请联系管理员！";
			message(errormsg, "/member/index.html");
			return MSG;
		}
		String addtime = user.getAddtime();
		MD5 md5 = new MD5();
		String m = md5.getMD5ofStr(user_id + addtime);
		if ((useridStr.equals("" + user_id))
				&& (StringUtils.isNull(idstrArr[1]).toLowerCase().equals(m
						.toLowerCase()))) {
			user.setEmail_status("1");

			CreditType emailType = this.userCreditService
					.getCreditTypeByNid(EnumIntegralTypeName.INTEGRAL_EMAIL
							.getValue());
			if (emailType != null) {
				List<UserCreditLog> logList = this.userCreditService.getCreditLogList(user_id,
						emailType.getId());
				if ((logList == null) || (logList.size() <= 0)) {
					this.userCreditService.updateUserCredit(user_id,
							(int) emailType.getValue(), new Byte("1")
									.byteValue(),
							EnumIntegralTypeName.INTEGRAL_EMAIL.getValue());
				}
			}

			User newUser = this.userService.modifyEmail_status(user);
			msg = "邮箱激活成功！";
		} else {
			errormsg = "激活失败，请重新激活！";
		}

		if (errormsg.equals(""))
			message(msg, "/member/index.html");
		else {
			message(errormsg, "/member/index.html");
		}
		return MSG;
	}

	private void sendGetPwdMail(User user) throws Exception {
		String to = user.getEmail();
		Mail m = Mail.getInstance();
		m.setTo(to);
		m.readGetpwdMailMsg();
		m.replace(user.getUsername(), to, "/user/setpwd.html?id="
				+ m.getdecodeIdStr(user));
		logger.debug("Email_msg:" + m.getBody());
		m.sendMail();
	}

	private void sendGetPayPwdMail(User user) throws Exception {
		String to = user.getEmail();
		Mail m = Mail.getInstance();
		m.setTo(to);
		m.readGetpwdMailMsg();
		m.replace(user.getUsername(), to, "/user/setpaypwd.html?id="
				+ m.getdecodeIdStr(user));
		logger.debug("Email_msg:" + m.getBody());
		m.sendMail();
	}

	public String updateuser() {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));

		if ((this.user != null) && (this.user.getPassword().length() > 0)) {
			MD5 md5 = new MD5();
			this.user.setPassword(md5.getMD5ofStr(this.user.getPassword()));
			this.user.setUptime(getTimeStr());
			this.user.setUpip(getRequestIp());
		}
		this.userService.updateuser(this.user);

		UserCache userCache = this.userService.getUserCacheByUserid(this.user
				.getUser_id());
		long kefu_userid = NumberUtils.getLong(StringUtils.isNull(this.request
				.getParameter("kefu_userid")));
		String kefu_username = null;
		if (kefu_userid > 0L) {
			kefu_username = this.userService.getUserById(kefu_userid)
					.getUsername();
		}
		userCache.setKefu_userid(kefu_userid);
		userCache.setKefu_username(kefu_username);
		this.userinfoService.updateUserCache(userCache);

		if (actionType.isEmpty()) {
			this.msg.append("更新成功");
			message(this.msg.toString(), "");
			return SUCCESS;
		}
		message("更新成功", "/admin/userinfo/user.html");
		return ADMINMSG;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserCache getUserCache() {
		return this.userCache;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserinfoService getUserinfoService() {
		return this.userinfoService;
	}

	public void setUserinfoService(UserinfoService userinfoService) {
		this.userinfoService = userinfoService;
	}

	public AccountService getAccountService() {
		return this.accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public BorrowService getBorrowService() {
		return this.borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public PasswordTokenService getPasswordTokenService() {
		return this.passwordTokenService;
	}

	public void setPasswordTokenService(
			PasswordTokenService passwordTokenService) {
		this.passwordTokenService = passwordTokenService;
	}

	public String getValidatecode() {
		return this.validatecode;
	}

	public void setValidatecode(String validatecode) {
		this.validatecode = validatecode;
	}

	public StringBuffer getMsg() {
		return this.msg;
	}

	public void setMsg(StringBuffer msg) {
		this.msg = msg;
	}

	public User getModel() {
		return this.user;
	}

	public CooperationLoginService getCooperationLoginService() {
		return this.cooperationLoginService;
	}

	public void setCooperationLoginService(
			CooperationLoginService cooperationLoginService) {
		this.cooperationLoginService = cooperationLoginService;
	}

	public RewardStatisticsService getRewardStatisticsService() {
		return this.rewardStatisticsService;
	}

	public void setRewardStatisticsService(
			RewardStatisticsService rewardStatisticsService) {
		this.rewardStatisticsService = rewardStatisticsService;
	}

	public UserCreditService getUserCreditService() {
		return this.userCreditService;
	}

	public void setUserCreditService(UserCreditService userCreditService) {
		this.userCreditService = userCreditService;
	}

	public MemberBorrowService getMemberBorrowService() {
		return this.memberBorrowService;
	}
	
	public static void main(String[] args) {
		String pw = "123456";
		MD5 md5 = new MD5();
		
		System.out.println(md5.getMD5ofStr(pw));
	}
}