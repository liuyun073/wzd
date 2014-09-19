package com.liuyun.site.web.action.admin;

import com.opensymphony.xwork2.ModelDriven;
import com.liuyun.site.common.enums.EnumIntegralTypeName;
import com.liuyun.site.context.Constant;
import com.liuyun.site.domain.Attestation;
import com.liuyun.site.domain.Message;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserCredit;
import com.liuyun.site.domain.UserTrack;
import com.liuyun.site.domain.UserType;
import com.liuyun.site.model.AttestationModel;
import com.liuyun.site.model.DetailUser;
import com.liuyun.site.model.IdentifySearchParam;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserinfoModel;
import com.liuyun.site.service.AccountService;
import com.liuyun.site.service.MessageService;
import com.liuyun.site.service.UserCreditService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.service.UserinfoService;
import com.liuyun.site.tool.jxl.ExcelHelper;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： UserInfoAction   
 * 类描述： 后台管理--客户管理模块         
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Nov 4, 2013 12:21:45 AM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Nov 4, 2013 12:21:45 AM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class UserInfoAction extends BaseAction implements
		ModelDriven<UserinfoModel> {
	private static final Logger logger = Logger.getLogger(UserInfoAction.class);
	
	private UserService userService;
	private UserinfoService userInfoService;
	private UserinfoModel UserinfoModel = new UserinfoModel();
	private AttestationModel AttestationModel = new AttestationModel();
	private String updatetype;
	private List<UserType> typeList;
	private UserType usertypes;
	private Message message = new Message();
	private MessageService messageService;
	private AccountService accountService;
	private UserCreditService userCreditService;

	public void setUserCreditService(UserCreditService userCreditService) {
		this.userCreditService = userCreditService;
	}

	public AccountService getAccountServiceu() {
		return this.accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
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

	public AttestationModel getAttestationModel() {
		return this.AttestationModel;
	}

	public void setAttestationModel(AttestationModel AttestationModel) {
		this.AttestationModel = AttestationModel;
	}

	public UserType getUsertypes() {
		return this.usertypes;
	}

	public void setUsertypes(UserType usertypes) {
		this.usertypes = usertypes;
	}

	public String getUpdatetype() {
		return this.updatetype;
	}

	public List<UserType> getTypeList() {
		return this.typeList;
	}

	public void setTypeList(List<UserType> typeList) {
		this.typeList = typeList;
	}

	public void setUpdatetype(String updatetype) {
		this.updatetype = updatetype;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserinfoService getUserInfoService() {
		return this.userInfoService;
	}

	public void setUserInfoService(UserinfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public String userinfolist() {
		int i = NumberUtils.getInt((this.request.getParameter("page") == null) ? "1" : this.request.getParameter("page"));
		SearchParam searchParam = new SearchParam();
		String username = StringUtils.isNull(this.request.getParameter("username"));
		searchParam.setUsername(username);
		PageDataList pageDataList = this.userInfoService.getUserInfoListByPageNumber(i, searchParam);
		List<UserinfoModel> list = (List<UserinfoModel>) pageDataList.getList();
		for (Iterator<UserinfoModel> localIterator = list.iterator(); localIterator.hasNext();) {
			UserinfoModel u = localIterator.next();
			u.getAllStatus();
		}

		this.request.setAttribute("userInfoList", list);
		this.request.setAttribute("page", pageDataList.getPage());
		this.request.setAttribute("params", searchParam.toMap());
		return SUCCESS;
	}

	public String index() {
		return SUCCESS;
	}

	public String search() {
		int page = NumberUtils.getInt(this.request.getParameter("page"));

		String temp = (this.request.getParameter("types") == null) ? SUCCESS : this.request.getParameter("types");
		SearchParam searchParam = new SearchParam();

		searchParam.setUsername(this.request.getParameter("username"));
		PageDataList pageDataList;
		if ((temp != null) && (temp.indexOf("user") != -1)) {
			searchParam.setRealname(this.request.getParameter("realname"));
			searchParam.setEmail(this.request.getParameter("email"));
			pageDataList = this.userInfoService.getSearchUserInfo(page, searchParam);
			this.request.setAttribute("detailuserlist", pageDataList.getList());
		} else {
			pageDataList = this.userInfoService.getSearchUserInfo(searchParam, page);
			for (Iterator<UserinfoModel> localIterator = (Iterator<UserinfoModel>) pageDataList.getList().iterator(); localIterator.hasNext();) {
				UserinfoModel u = localIterator.next();
				u.getAllStatus();
			}

			this.request.setAttribute("userInfoList", pageDataList.getList());
		}
		searchParam.addMap("types", temp);
		this.request.setAttribute("page", pageDataList.getPage());
		this.request.setAttribute("params", searchParam.toMap());
		return temp;
	}

	public String userAttestation() {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam searchParam = new SearchParam();
		searchParam.setUsername(this.request.getParameter("username"));
		searchParam.setRealname(this.request.getParameter("realname"));
		String vip_status = StringUtils.isNull(this.request.getParameter("vip_status"));
		searchParam.setVip_status(vip_status);
		String audit_user = this.request.getParameter("audit_user");
		String type = StringUtils.isNull(this.request.getParameter("type"));
		logger.debug("audit=====" + audit_user);
		if (!StringUtils.isBlank(audit_user)) {
			User user = this.userService.getUserByName(this.request.getParameter("audit_user"));
			int userid = (int) user.getUser_id();
			searchParam.setAudit_user(String.valueOf(userid));
			logger.debug("audit=====" + user.getUser_id());
		}
		PageDataList pageDataList = this.userInfoService.getSearchUserInfo(page, searchParam);
		List<User> list = this.userService.getKfList();
		this.request.setAttribute("kflist", list);
		this.request.setAttribute("detailuserlist", pageDataList.getList());
		this.request.setAttribute("page", pageDataList.getPage());
		this.request.setAttribute("params", searchParam.toMap());

		if (type.isEmpty()) {
			return SUCCESS;
		}

		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "attestation_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		List<DetailUser> userList = this.userService.getUserList(searchParam);
		String[] names = { "user_id", "username", "realname", "real_status",
				"email_status", "phone_status", "vip_status", "addtime",
				"vip_verify_time" };
		String[] titles = { "用户ID", "用户名", "真实姓名", "实名认证", "邮箱认证", "手机认证",
				"是否vip", "注册时间", "VIP审核时间" };
		try {
			ExcelHelper.writeExcel(infile, userList, DetailUser.class, Arrays.asList(names), Arrays.asList(titles));
			export(infile, downloadFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String user() {
		List<User> kflist = this.userService.getKfList();
		this.request.setAttribute("kflist", kflist);

		List<UserType> userTypeList = this.userService.getAllUserType();
		this.request.setAttribute("userTypeList", userTypeList);
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		String email = StringUtils.isNull(this.request.getParameter("email"));
		String realname = StringUtils.isNull(this.request.getParameter("realname"));
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String kefu_username = StringUtils.isNull(this.request.getParameter("kefu_username"));
		String card_id = StringUtils.isNull(this.request.getParameter("card_id"));
		String userPhone = StringUtils.isNull(this.request.getParameter("userphone"));
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		String qq = StringUtils.isNull(this.request.getParameter("qq"));
		String phone = StringUtils.isNull(this.request.getParameter("phone"));
		String user_type = StringUtils.isNull(this.request.getParameter("user_type"));
		SearchParam param = new IdentifySearchParam();
		param.setTypename(user_type);
		param.setPhone(phone);
		param.setUsername(username);
		param.setCard_id(card_id);
		param.setEmail(email);
		param.setRealname(realname);
		param.setKefu_username(kefu_username);
		param.setUserPhone(userPhone);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.setQq(qq);

		PageDataList pageDataList = this.userService.getUserList(page, param);
		setPageAttribute(pageDataList, param);
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		logger.debug("path===" + contextPath);
		String downloadFile = "user_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "user_id", "username", "realname", "sex", "email",
				"qq", "phone", "provincetext", "citytext", "areatext",
				"card_id", "addtime", "status", "type_id", "kefu_username" };
		String[] titles = { "ID", "用户名称", "真实姓名", "性别\t", "邮箱", "qq", "手机",
				"省", "市", "县", "身份证号", "添加时间", "状态", "用户类型", "所属客服" };
		List<DetailUser> list = this.userService.getUserList(param);
		try {
			ExcelHelper.writeExcel(infile, list, DetailUser.class, Arrays.asList(names), Arrays.asList(titles));
			export(infile, downloadFile);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public String viewuserAttestation() {
		UserinfoModel userinfoModel = this.userInfoService.getUserALLInfoModelByUserid(this.UserinfoModel.getUser_id());
		userinfoModel.getAllStatus();
		UserTrack t = this.userService.getLastUserTrack(this.UserinfoModel.getUser_id());
		int count = this.userService.userTrackCount(this.UserinfoModel.getUser_id());
		this.request.setAttribute("user", userinfoModel);
		this.request.setAttribute("userLoginCount", Integer.valueOf(count));
		this.request.setAttribute("track", t);
		return SUCCESS;
	}

	public String userCertify() {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam searchParam = new SearchParam();
		searchParam.setUsername(this.request.getParameter("username"));
		searchParam.setRealname(this.request.getParameter("realname"));
		PageDataList pageDataList = this.userInfoService.getSearchUserCertify(page, searchParam);

		this.request.setAttribute("userCertifyList", pageDataList.getList());
		this.request.setAttribute("page", pageDataList.getPage());
		this.request.setAttribute("params", searchParam.toMap());
		return SUCCESS;
	}

	public String verifyUserCertify() {
		int pid = NumberUtils.getInt(this.request.getParameter("pid"));
		long user_id = NumberUtils.getLong(this.request.getParameter("user_id"));
		int status = NumberUtils.getInt(this.request.getParameter("status"));
		int value = NumberUtils.getInt(this.request.getParameter("jifen"));
		String type_name = this.request.getParameter("type_name");
		String verifyRemark = this.request.getParameter("verify_remark");
		String actionType = this.request.getParameter("actionType");
		String msg = "审核成功！";
		UserCredit userCredit = this.userInfoService.getUserCreditByUserId(user_id);
		userCredit.setValue(userCredit.getValue() + value);
		Attestation userAttestation = new Attestation();

		User auth_user = (User) this.session.get(Constant.AUTH_USER);
		if (!StringUtils.isBlank(actionType)) {
			String validcode = this.request.getParameter("validcode");
			if (validcode.isEmpty()) {
				message("你输入的校验码为空！", "/admin/attestation/verifyScene.html");
				return ADMINMSG;
			}
			if (!checkValidImg(validcode)) {
				message("你输入的校验码错误！", "/admin/attestation/verifyScene.html");
				return ADMINMSG;
			}
			if (status == 1) {
				this.userCreditService.updateUserCredit(user_id, value,
						new Byte("1").byteValue(),
						EnumIntegralTypeName.INTEGRAL_CERTIFICATE.getValue());
				userAttestation.setId(pid);
				userAttestation.setStatus(1);
				userAttestation.setVerify_remark(verifyRemark);
				userAttestation.setVerify_time(getTimeStr());
				this.userInfoService.modifyUserCertifyStatus(userAttestation);

				message(msg, "/admin/attestation/userCertify.html");
				this.message = getSiteMessage(auth_user.getUser_id(),
						"客户证明资料认证审核成功！", "客户证明资料-" + type_name + ",认证审核成功！",
						user_id);
				this.messageService.addMessage(this.message);
				return ADMINMSG;
			}

			userAttestation.setId(pid);
			userAttestation.setStatus(3);
			userAttestation.setVerify_remark(verifyRemark);
			userAttestation.setVerify_time(getTimeStr());
			this.userInfoService.modifyUserCertifyStatus(userAttestation);

			message("审核没有通过！", "/admin/attestation/userCertify.html");
			this.message = getSiteMessage(auth_user.getUser_id(),
					"客户证明资料认证审核失败！", "客户证明资料-" + type_name + ",认证审核失败！</br>"
							+ "审核信息：" + verifyRemark, user_id);
			this.messageService.addMessage(this.message);
			return ADMINMSG;
		}

		return SUCCESS;
	}

	public String userCredit() {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam searchParam = new SearchParam();
		searchParam.setUsername(this.request.getParameter("username"));
		searchParam.setRealname(this.request.getParameter("realname"));
		PageDataList pageDataList = this.userInfoService.getUserCreditList(page, searchParam);

		this.request.setAttribute("userCreditList", pageDataList.getList());
		this.request.setAttribute("page", pageDataList.getPage());
		this.request.setAttribute("params", searchParam.toMap());
		return SUCCESS;
	}

	public String viewUserCreditDetail() {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam searchParam = new SearchParam();
		PageDataList pageDataList = this.userInfoService.getCreditLogList(page, searchParam);

		this.request.setAttribute("ucLog", pageDataList.getList());
		this.request.setAttribute("page", pageDataList.getPage());
		this.request.setAttribute("params", searchParam.toMap());

		return SUCCESS;
	}

	public String editUserCredit() {
		return SUCCESS;
	}

	public String edituserinfo() {
		UserinfoModel userinfoModel = this.userInfoService.getUserALLInfoModelByUserid(this.UserinfoModel.getUser_id());
		userinfoModel.getAllStatus();
		System.out.println(userinfoModel.getAddress());
		this.request.setAttribute("user", userinfoModel);
		return SUCCESS;
	}

	public String updateuser() {
		int i = NumberUtils.getInt(this.updatetype);
		switch (i) {
		case 0:
			break;
		case 1:
			this.userInfoService.updateUserinfo(this.UserinfoModel);

			break;
		case 2:
			this.userInfoService.updateBuilding(this.UserinfoModel);

			break;
		case 3:
			this.userInfoService.updateCompany(this.UserinfoModel);

			break;
		case 4:
			this.userInfoService.updateFirm(this.UserinfoModel);

			break;
		case 5:
			this.userInfoService.updateFinance(this.UserinfoModel);

			break;
		case 6:
			this.userInfoService.updateContact(this.UserinfoModel);

			break;
		case 7:
			this.userInfoService.updateMate(this.UserinfoModel);

			break;
		case 8:
			this.userInfoService.updateEducation(this.UserinfoModel);

			break;
		case 9:
			this.userInfoService.updateOtherinfo(this.UserinfoModel);
		}

		UserinfoModel userinfoModel = this.userInfoService.getUserALLInfoModelByUserid(this.UserinfoModel.getUser_id());
		userinfoModel.getAllStatus();
		this.request.setAttribute("user", userinfoModel);

		return SUCCESS;
	}

	public String adduser() {
		this.request.setAttribute("kf", this.userService.getKfList());
		return SUCCESS;
	}

	public String edituser() {
		this.request.setAttribute("user", this.userService.getDetailUser(this.UserinfoModel.getUser_id()));
		this.request.setAttribute("kefu", this.userService.getUserCacheByUserid(this.UserinfoModel.getUser_id()));
		List kfList = this.userService.getKfList();
		this.request.setAttribute("kfList", kfList);
		return SUCCESS;
	}

	public String typelist() {
		this.request.setAttribute("usertype", this.userService.getAllUserType());
		return SUCCESS;
	}

	public String typeyupdateoradd() {
		String temp = this.request.getServletPath();
		if (temp.indexOf("typeupdate") != -1) {
			this.userService.updateUserTypeByList(this.typeList);
		} else if (temp.indexOf("typeadd") != -1) {
			this.usertypes.setAddip(getRequestIp());
			this.usertypes.setAddtime(getTimeStr());
			this.userService.addUserType(this.usertypes);
		} else if (temp.indexOf("typedelete") != -1) {
			this.userService.deleteUserTypeById(NumberUtils.getLong(this.request.getParameter("type_id").toString()));
		}

		return SUCCESS;
	}

	public String viewTenderValue() {
		long user_id = paramLong("user_id");
		UserCredit credit = this.userInfoService.getCreditModelById(user_id);
		this.request.setAttribute("credit", credit);
		return SUCCESS;
	}

	public String editTenderValue() {
		if (checkValidImg(paramString("valid"))) {
			message("验证码不对！", "");
			return ADMINMSG;
		}
		int opvalue = paramInt("opvalue");
		long user_id = paramLong("user_id");
		UserCredit uc = this.userInfoService.getUserCreditByUserId(user_id);
		if (uc == null) {
			message("ID：" + user_id + "用户不存在！", "");
			return ADMINMSG;
		}
		uc.setTender_value(uc.getTender_value() + opvalue);

		message("操作成功！", "");
		return ADMINMSG;
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

	public UserinfoModel getModel() {
		return this.UserinfoModel;
	}
}