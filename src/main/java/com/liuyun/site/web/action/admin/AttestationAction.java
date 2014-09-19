package com.liuyun.site.web.action.admin;

import com.opensymphony.xwork2.ModelDriven;
import com.liuyun.site.common.enums.EnumIntegralTypeName;
import com.liuyun.site.context.AuditType;
import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.Attestation;
import com.liuyun.site.domain.AttestationType;
import com.liuyun.site.domain.CreditType;
import com.liuyun.site.domain.Message;
import com.liuyun.site.domain.OperationLog;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserCache;
import com.liuyun.site.model.DetailUser;
import com.liuyun.site.model.IdentifySearchParam;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserCacheModel;
import com.liuyun.site.service.AccountService;
import com.liuyun.site.service.AttestationService;
import com.liuyun.site.service.MessageService;
import com.liuyun.site.service.RewardStatisticsService;
import com.liuyun.site.service.UserCreditService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.service.UserinfoService;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import java.util.List;


/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： AttestationAction   
 * 类描述： 后台管理--认证管理模块   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 28, 2013 11:50:38 PM  
 * ------------------------------------------------------ 
 * 修改人：  
 * 修改时间：Oct 28, 2013 11:50:38 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class AttestationAction extends BaseAction implements
		ModelDriven<AttestationType> {
	private UserService userService;
	private UserinfoService userinfoService;
	private AttestationService attestationService;
	private List<AttestationType> attestationTypeslist;
	private AttestationType attestationType = new AttestationType();
	private Message message = new Message();
	private MessageService messageService;
	private AccountService accountService;
	private RewardStatisticsService rewardStatisticsService;
	private UserCreditService userCreditService;
	private String username;
	private String password;

	public UserCreditService getUserCreditService() {
		return this.userCreditService;
	}

	public void setUserCreditService(UserCreditService userCreditService) {
		this.userCreditService = userCreditService;
	}

	public AccountService getAccountService() {
		return this.accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public MessageService getMessageService() {
		return this.messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public List<AttestationType> getAttestationTypeslist() {
		return this.attestationTypeslist;
	}

	public void setAttestationTypeslist(
			List<AttestationType> attestationTypeslist) {
		this.attestationTypeslist = attestationTypeslist;
	}

	public AttestationService getAttestationService() {
		return this.attestationService;
	}

	public void setAttestationService(AttestationService attestationService) {
		this.attestationService = attestationService;
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

	public RewardStatisticsService getRewardStatisticsService() {
		return this.rewardStatisticsService;
	}

	public void setRewardStatisticsService(
			RewardStatisticsService rewardStatisticsService) {
		this.rewardStatisticsService = rewardStatisticsService;
	}

	public String index() {
		return SUCCESS;
	}

	public String verifyRealname() {
		CreditType emailType = this.userCreditService
				.getCreditTypeByNid(EnumIntegralTypeName.INTEGRAL_REAL_NAME
						.getValue());
		long creditValue = emailType.getValue();
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		String status = StringUtils.isNull(this.request.getParameter("status"));
		IdentifySearchParam param = new IdentifySearchParam();
		if (status.isEmpty())
			param.setReal_status(2);
		else {
			param.setReal_status(NumberUtils.getInt(status));
		}
		param.setUsername(username);
		PageDataList plist = null;
		plist = this.userService.getUserList(page, param);
		setPageAttribute(plist, param);
		this.request.setAttribute("verifyType", EnumIntegralTypeName.INTEGRAL_REAL_NAME.getValue());
		this.request.setAttribute("creditValue", Long.valueOf(creditValue));
		return SUCCESS;
	}

	public String verifyPhone() {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		String status = StringUtils.isNull(this.request.getParameter("status"));
		IdentifySearchParam param = new IdentifySearchParam();
		if (status.isEmpty())
			param.setPhone_status(2);
		else {
			param.setPhone_status(NumberUtils.getInt(status));
		}
		param.setUsername(username);
		PageDataList plist = null;
		plist = this.userService.getUserList(page, param);
		setPageAttribute(plist, param);
		this.request.setAttribute("verifyType",
				EnumIntegralTypeName.INTEGRAL_PHONE.getValue());
		return SUCCESS;
	}

	public String verifyVideo() {
		CreditType emailType = this.userCreditService
				.getCreditTypeByNid(EnumIntegralTypeName.INTEGRAL_VIDEO
						.getValue());
		long creditValue = emailType.getValue();
		this.request.setAttribute("creditValue", Long.valueOf(creditValue));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		String status = StringUtils.isNull(this.request.getParameter("status"));
		IdentifySearchParam param = new IdentifySearchParam();
		if (status.isEmpty())
			param.setVideo_status(2);
		else {
			param.setVideo_status(NumberUtils.getInt(status));
		}
		param.setUsername(username);
		PageDataList plist = null;
		plist = this.userService.getUserList(page, param);
		setPageAttribute(plist, param);
		this.request.setAttribute("verifyType",
				EnumIntegralTypeName.INTEGRAL_VIDEO.getValue());
		return SUCCESS;
	}

	public String verifyScene() {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		String status = StringUtils.isNull(this.request.getParameter("status"));
		IdentifySearchParam param = new IdentifySearchParam();
		if (status.isEmpty())
			param.setScene_status(2);
		else {
			param.setScene_status(NumberUtils.getInt(status));
		}
		param.setUsername(username);
		PageDataList plist = null;
		plist = this.userService.getUserList(page, param);
		setPageAttribute(plist, param);
		this.request.setAttribute("verifyType",
				EnumIntegralTypeName.INTEGRAL_SCENE.getValue());
		return SUCCESS;
	}

	public String vip() {
		int pages = NumberUtils.getInt(this.request.getParameter("page"));
		int status = NumberUtils.getInt(this.request.getParameter("status"));
		SearchParam searchParam = new SearchParam();
		searchParam.setUsername(this.request.getParameter("username"));
		searchParam.setKefu_username(this.request.getParameter("kefu_name"));

		PageDataList pageDataList = this.userinfoService.getUserVipinfo(pages,
				10, status, searchParam);
		this.request.setAttribute("vipinfo", pageDataList.getList());
		this.request.setAttribute("page", pageDataList.getPage());
		this.request.setAttribute("params", searchParam.toMap());

		return SUCCESS;
	}

	public String viewvip() {
		int vipStatus = NumberUtils.getInt(this.request
				.getParameter("vip_status"));
		long user_id = NumberUtils
				.getLong(this.request.getParameter("user_id"));
		String vipVerifyRemark = this.request.getParameter("vip_verify_remark");
		String validcode = StringUtils.isNull(this.request
				.getParameter("validcode"));
		UserCacheModel vipinfo = this.userService.getUserCacheByUserid(user_id);
		User auth_user = (User) this.session.get(Constant.AUTH_USER);
		if (!StringUtils.isBlank(this.actionType)) {
			if ("".equals(validcode)) {
				message("验证码不能为空！", "/admin/attestation/viewvip.html?user_id="
						+ user_id);
				return ADMINMSG;
			}
			if (!checkValidImg(validcode)) {
				message("你输入的校验码错误！",
						"/admin/attestation/viewvip.html?user_id=" + user_id);
				return ADMINMSG;
			}

			DetailUser detailUser = this.userService.getDetailUser(auth_user
					.getUser_id());
			DetailUser newdetailUser = this.userService.getDetailUser(user_id);
			OperationLog operationLog = new OperationLog(user_id, auth_user
					.getUser_id(), "", getTimeStr(), getRequestIp(), "");

			if (vipStatus == 1) {
				long userId = user_id;
				UserCache userCache = this.userService.getUserCacheByUserid(userId);
				if (userCache.getVip_status() == 1) {
					message("该用户vip申请已经审核通过！",
							"/admin/attestation/vip.html?status=2");
					return MSG;
				}
				userCache.setVip_status(1);
				userCache.setVip_verify_remark(vipVerifyRemark);
				userCache.setVip_verify_time(getTimeStr());

				AccountLog accountLog = new AccountLog(userId, Constant.VIP_FEE, userId, getTimeStr(), getRequestIp());
				AccountLog inviteLog = new AccountLog(userId, Constant.INVITE_MONEY, userId, getTimeStr(), getRequestIp());
				//this.userinfoService.VerifyVipSuccess(userCache, accountLog, inviteLog);
				this.userinfoService.VerifyVipSuccess(userCache, accountLog);
				this.userinfoService.VerifyVipSuccess(userCache, inviteLog);
				
				operationLog.setType(Constant.VIP_SUCCESS);
				operationLog.setOperationResult(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核vip用户"
						+ newdetailUser.getUsername() + "成功通过，扣除用户名为"
						+ newdetailUser.getUsername() + "的用户vip费用"
						+ Global.getValue("vip_fee"));
				this.accountService.addOperationLog(operationLog);
				message("审核成功！", "/admin/attestation/vip.html?status=2");
				userCache = this.userService.getUserCacheByUserid(userId);

				this.message = getSiteMessage(auth_user.getUser_id(),
						"申请VIP成功！", "申请VIP成功！", user_id);
				this.messageService.addMessage(this.message);
				return ADMINMSG;
			}

			long userId = user_id;
			UserCache userCache = this.userService.getUserCacheByUserid(userId);
			userCache.setVip_status(-1);
			userCache.setVip_verify_remark(vipVerifyRemark);
			userCache.setVip_verify_time(getTimeStr());

			AccountLog accountLog = new AccountLog(userId, Constant.VIP_FEE,
					userId, getTimeStr(), getRequestIp());
			this.userinfoService.VerifyVipFail(userCache, accountLog);
			message("审核没有通过！", "/admin/attestation/vip.html");

			operationLog.setType(Constant.VIP_FAIL);
			operationLog.setOperationResult(detailUser.getTypename() + "（"
					+ operationLog.getAddip() + "）用户名为"
					+ detailUser.getUsername() + "的操作员审核vip用户"
					+ newdetailUser.getUsername() + "失败未通过");
			this.accountService.addOperationLog(operationLog);
			this.message = getSiteMessage(auth_user.getUser_id(), "申请VIP失败！",
					"申请VIP失败！</br>审核信息：" + vipVerifyRemark, user_id);
			this.messageService.addMessage(this.message);

			return ADMINMSG;
		}

		this.request.setAttribute("vipinfo", vipinfo);
		return SUCCESS;
	}

	public String typelist() {
		this.request.setAttribute("type", this.attestationService
				.getAttestationType());
		return SUCCESS;
	}

	public String typeaddoredit() {
		String type = this.request.getParameter("type");
		if (type.equals("add")) {
			this.attestationService.addAttestationType(this.attestationType);
		} else if (type.equals("update"))
			this.attestationService
					.updateAttestationTypeByList(this.attestationTypeslist);
		else {
			return ERROR;
		}

		return SUCCESS;
	}

	public String typeadd() {
		return SUCCESS;
	}

	public String viewUserInfo() {
		long user_id = 0L;
		String username = this.request.getParameter("username");
		String viewType = this.request.getParameter("viewType");
		User userInfo = this.userinfoService.getUserinfoByUsername(username);
		if ((userInfo != null) && (userInfo.getUser_id() != 0L)) {
			user_id = userInfo.getUser_id();
		}
		this.request.setAttribute("userCreditInfo", this.userService
				.getDetailUser(user_id));
		this.request.setAttribute("userUseMoneyInfo", this.userService
				.getDetailUser(user_id));
		this.request.setAttribute("userInfo", userInfo);
		if ("viewCard".equals(viewType)) {
			String viewcard = this.request.getParameter("viewcard");
			this.request.setAttribute("viewcard", viewcard);
			return "viewType";
		}
		return SUCCESS;
	}

	public String viewAudit() {
		long user_id = NumberUtils
				.getLong(this.request.getParameter("user_id"));
		String type = StringUtils.isNull(this.request.getParameter("type"));
		CreditType emailType = this.userCreditService.getCreditTypeByNid(type);
		long creditValue = emailType.getValue();
		this.request.setAttribute("creditValue", Long.valueOf(creditValue));
		this.request.setAttribute("type", type);
		this.request.setAttribute("user_id", Long.valueOf(user_id));
		return SUCCESS;
	}

	public String userCertifyAudit() {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String pid = StringUtils.isNull(this.request.getParameter("pid"));
		Attestation attestation = this.userinfoService.getAttestationById(pid);

		this.request.setAttribute("type_name", attestation.getType_name());
		this.request.setAttribute("user_id", Long.valueOf(attestation
				.getUser_id()));
		this.request.setAttribute("type", type);
		this.request.setAttribute("pid", pid);
		return SUCCESS;
	}

	public String AuditUserInfo() {
		long user_id = NumberUtils
				.getLong(this.request.getParameter("user_id"));
		int status = NumberUtils.getInt(this.request.getParameter("status"));
		int value = NumberUtils.getInt(this.request.getParameter("jifen"));
		String auditContent = this.request.getParameter("content");
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String actionType = this.request.getParameter("actionType");
		String msg = "审核成功！";

		User existsUser = this.userService.getUserById(user_id);

		User auth_user = (User) this.session.get(Constant.AUTH_USER);

		DetailUser detailUser = this.userService.getDetailUser(auth_user
				.getUser_id());
		DetailUser newdetailUser = this.userService.getDetailUser(user_id);
		OperationLog operationLog = new OperationLog(user_id, auth_user
				.getUser_id(), "", getTimeStr(), getRequestIp(), "");

		if (!StringUtils.isBlank(actionType)) {
			String validcode = this.request.getParameter("validcode");
			if (validcode.isEmpty()) {
				AuditType uType = AuditType.getAuditType(type);
				switch (uType) {
				case realnameAudit:
					message("你输入的校验码为空！", "/admin/attestation/verifyPhone.html");
					break;
				case emailAudit:
					message("你输入的校验码为空！",
							"/admin/attestation/verifyRealname.html");
					break;
				case phoneAudit:
				default:
					message("你输入的校验码为空！", "/admin/attestation/verifyScene.html");
				}
				return ADMINMSG;
			}
			if (!checkValidImg(validcode)) {
				AuditType uType = AuditType.getAuditType(type);
				switch (uType) {
				case realnameAudit:
					message("你输入的校验码错误！", "/admin/attestation/verifyPhone.html");
					break;
				case emailAudit:
					message("你输入的校验码错误！",
							"/admin/attestation/verifyRealname.html");
					break;
				case phoneAudit:
				default:
					message("你输入的校验码错误！", "/admin/attestation/verifyScene.html");
				}

				return ADMINMSG;
			}
			if (status == 1) {
				User user = new User();

				AuditType auditType = AuditType.getAuditType(type);

				switch (auditType) {
				case realnameAudit:
					message("实名认证成功！", "/admin/attestation/verifyRealname.html");
					break;
				case phoneAudit:
					message("手机认证成功！", "/admin/attestation/verifyPhone.html");
					break;
				case emailAudit:
					message("邮件认证成功！", "/admin/attestation/verifyRealname.html");
					break;
				case videoAudit:
					message("视频认证成功！", "/admin/attestation/verifyVideo.html");
					break;
				case sceneAudit:
				default:
					message("现场认证成功！", "/admin/attestation/verifyScene.html");
				}
				switch (auditType) {
				case emailAudit:
					user.setUser_id(user_id);
					user.setEmail_status("1");
					this.userService.modifyEmail_status(user);

					this.userCreditService.updateUserCredit(user_id, value,
							new Byte("1").byteValue(),
							EnumIntegralTypeName.INTEGRAL_EMAIL.getValue());

					this.message = getSiteMessage(auth_user.getUser_id(),
							"邮件认证审核成功！", "邮件认证审核成功！", user_id);
					this.message.setIs_Authenticate("1");
					this.messageService.addMessage(this.message);

					break;
				case phoneAudit:
					user.setUser_id(user_id);
					user.setPhone_status("1");
					this.userService.modifyPhone_status(user);

					this.userCreditService.updateUserCredit(user_id, value,
							new Byte("1").byteValue(),
							EnumIntegralTypeName.INTEGRAL_PHONE.getValue());

					this.message = getSiteMessage(auth_user.getUser_id(),
							"手机认证审核成功！", "手机认证审核成功！", user_id);
					this.message.setIs_Authenticate("1");
					this.messageService.addMessage(this.message);
					message(msg, "/admin/attestation/verifyPhone.html");

					operationLog.setAddtime(getTimeStr());
					operationLog.setType(Constant.PHONE_SUCCESS);
					operationLog.setOperationResult(detailUser.getTypename()
							+ "（" + operationLog.getAddip() + "）用户名为"
							+ detailUser.getUsername() + "的操作员审核用户名为"
							+ newdetailUser.getUsername() + "的用户手机认证成功");
					this.accountService.addOperationLog(operationLog);
					break;
				case videoAudit:
					user.setUser_id(user_id);
					user.setVideo_status(1);
					this.userService.modifyVideo_status(user);

					this.userCreditService.updateUserCredit(user_id, value,
							new Byte("1").byteValue(),
							EnumIntegralTypeName.INTEGRAL_VIDEO.getValue());
					this.message = getSiteMessage(auth_user.getUser_id(),
							"视频认证审核成功！", "视频认证审核成功！", user_id);
					this.message.setIs_Authenticate("1");
					this.messageService.addMessage(this.message);
					break;
				case sceneAudit:
					user.setUser_id(user_id);
					user.setScene_status(1);
					this.userService.modifyScene_status(user);

					this.userCreditService.updateUserCredit(user_id, value,
							new Byte("1").byteValue(),
							EnumIntegralTypeName.INTEGRAL_SCENE.getValue());

					this.message = getSiteMessage(auth_user.getUser_id(),
							"现场认证审核成功！", "现场认证审核成功！", user_id);
					this.message.setIs_Authenticate("1");
					this.messageService.addMessage(this.message);
					break;
				case realnameAudit:
					User cardUser = this.userService.getUserByCardNO(existsUser
							.getCard_id());
					if ((cardUser != null)
							&& (cardUser.getUser_id() != existsUser
									.getUser_id())) {
						msg = "该证件号码已被他人使用,不能被实名认证！";
						message(msg, "");
						return ADMINMSG;
					}
					user.setUser_id(user_id);
					user.setReal_status("1");
					this.userService.modifyReal_status(user);

					this.userCreditService.updateUserCredit(user_id, value,
							new Byte("1").byteValue(),
							EnumIntegralTypeName.INTEGRAL_REAL_NAME.getValue());

					this.message = getSiteMessage(auth_user.getUser_id(),
							"实名认证审核成功！", "实名认证审核成功！", user_id);
					this.message.setIs_Authenticate("1");
					this.messageService.addMessage(this.message);
					System.out.println(this.message.getName());

					operationLog.setAddtime(getTimeStr());
					operationLog.setType(Constant.REALNAME_SUCCESS);
					operationLog.setOperationResult(detailUser.getTypename()
							+ "（" + operationLog.getAddip() + "）用户名为"
							+ detailUser.getUsername() + "的操作员审核用户名为"
							+ newdetailUser.getUsername() + "的用户实名认证成功");
					this.accountService.addOperationLog(operationLog);
				}

				return ADMINMSG;
			}

			User user = new User();
			message(msg, "/admin/attestation/verifyScene.html");
			AuditType auditType = AuditType.getAuditType(type);

			switch (auditType) {
			case phoneAudit:
				user.setUser_id(user_id);
				user.setEmail_status("-1");
				this.userService.modifyEmail_status(user);
				this.message = getSiteMessage(auth_user.getUser_id(),
						"邮箱认证审核失败！", "邮箱认证审核失败！</br>审核信息：" + auditContent,
						user_id);
				this.message.setIs_Authenticate("1");
				this.messageService.addMessage(this.message);
				break;
			case realnameAudit:
				user.setUser_id(user_id);
				user.setPhone_status("-1");
				this.userService.modifyPhone_status(user);
				this.message = getSiteMessage(auth_user.getUser_id(),
						"手机认证审核失败！", "手机认证审核失败！</br>审核信息：" + auditContent,
						user_id);
				this.message.setIs_Authenticate("1");
				this.messageService.addMessage(this.message);

				operationLog.setAddtime(getTimeStr());
				operationLog.setType(Constant.PHONE_FAIL);
				operationLog.setOperationResult(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核用户名为"
						+ newdetailUser.getUsername() + "的用户手机认证失败");
				this.accountService.addOperationLog(operationLog);
				break;
			case sceneAudit:
				user.setUser_id(user_id);
				user.setVideo_status(-1);
				this.userService.modifyVideo_status(user);
				this.message = getSiteMessage(auth_user.getUser_id(),
						"视频认证审核失败！", "视频认证审核失败！</br>审核信息：" + auditContent,
						user_id);
				this.message.setIs_Authenticate("1");
				this.messageService.addMessage(this.message);
				break;
			case videoAudit:
				user.setUser_id(user_id);
				user.setScene_status(-1);
				this.userService.modifyScene_status(user);
				this.message = getSiteMessage(auth_user.getUser_id(),
						"现场认证审核失败！", "现场认证审核失败！</br>审核信息：" + auditContent,
						user_id);
				this.message.setIs_Authenticate("1");
				this.messageService.addMessage(this.message);
				break;
			case emailAudit:
				user.setUser_id(user_id);
				user.setReal_status("-1");
				this.userService.modifyReal_status(user);
				this.message = getSiteMessage(auth_user.getUser_id(),
						"实名认证审核失败！", "实名认证审核失败！</br>审核信息：" + auditContent,
						user_id);
				this.message.setIs_Authenticate("1");
				this.messageService.addMessage(this.message);

				operationLog.setAddtime(getTimeStr());
				operationLog.setType(Constant.REALNAME_FAIL);
				operationLog.setOperationResult(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核用户名为"
						+ newdetailUser.getUsername() + "的用户实名认证失败");
				this.accountService.addOperationLog(operationLog);
			}

			message("审核没有通过！", "/admin/attestation/verifyScene.html");
			return ADMINMSG;
		}

		this.request
				.setAttribute("user", this.userService.getUserById(user_id));
		return SUCCESS;
	}

	private Message getSiteMessage(long receive_user, String title,
			String content, long sent_user) {
		this.message.setSent_user(receive_user);
		this.message.setReceive_user(sent_user);
		this.message.setStatus(0);
		this.message.setType(Constant.SYSTEM);
		this.message.setName(title);
		this.message.setContent(content);
		this.message.setAddip(getRequestIp());
		this.message.setAddtime(getTimeStr());
		return this.message;
	}

	public AttestationType getModel() {
		return this.attestationType;
	}
}