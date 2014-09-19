package com.liuyun.site.web.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;
import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.Areainfo;
import com.liuyun.site.domain.AutoTenderOrder;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserAmount;
import com.liuyun.site.domain.UserCache;
import com.liuyun.site.domain.UserCredit;
import com.liuyun.site.domain.UserTrack;
import com.liuyun.site.domain.Userinfo;
import com.liuyun.site.exception.BorrowException;
import com.liuyun.site.model.DetailUser;
import com.liuyun.site.model.UserAccountSummary;
import com.liuyun.site.model.UserCacheModel;
import com.liuyun.site.model.UserinfoModel;
import com.liuyun.site.service.AccountService;
import com.liuyun.site.service.ArticleService;
import com.liuyun.site.service.AutoBorrowService;
import com.liuyun.site.service.BorrowService;
import com.liuyun.site.service.FriendService;
import com.liuyun.site.service.MessageService;
import com.liuyun.site.service.UserCreditService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.service.UserinfoService;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * 会员action
 * @author liuyun073
 *
 */
public class MemberAction extends BaseAction implements
		ModelDriven<UserinfoModel> {
	private static Logger logger = Logger.getLogger(MemberAction.class);
	private UserService userService;
	private UserinfoService userinfoService;
	private AccountService accountService;
	private ArticleService articleService;
	private FriendService friendService;
	private MessageService messageService;
	private BorrowService borrowService;
	private AutoBorrowService autoBorrowService;
	private UserCreditService userCreditService;
	private String valicode;
	private UserinfoModel userinfo = new UserinfoModel();

	private String sep = File.separator;

	public String getValicode() {
		return this.valicode;
	}

	public void setValicode(String valicode) {
		this.valicode = valicode;
	}

	public AutoBorrowService getAutoBorrowService() {
		return this.autoBorrowService;
	}

	public void setAutoBorrowService(AutoBorrowService autoBorrowService) {
		this.autoBorrowService = autoBorrowService;
	}

	public UserinfoModel getModel() {
		return this.userinfo;
	}

	public String index() throws Exception {
		logger.info("**********************************");
		try {
			User user = (User) this.session.get(Constant.SESSION_USER);
			if (user == null) {
				return "login";
			}
			long user_id = user.getUser_id();

			UserTrack userTrack = this.userService.getLastUserTrack(user_id);
			this.request.setAttribute("userTrack", userTrack);
			DetailUser detailUser = this.userService.getDetailUser(user_id);
			UserCacheModel cache = this.userService
					.getUserCacheByUserid(user_id);
			if ((Global.getWebid().equals("wzdai"))
					|| (Global.getWebid().equals("hndai"))) {
				try {
					if ((cache.getVip_verify_time() != null)
							&& (cache.getVip_status() != 2)) {
						int vipEndTime = Integer.parseInt(cache
								.getVip_verify_time()) + 31536000;
						Date date = Calendar.getInstance().getTime();
						int today = Integer
								.parseInt(DateUtils.getTimeStr(date));
						if (vipEndTime - today < 0)
							cache = this.userService.validUserVip(user_id);
					}
				} catch (Exception localException1) {
				}
			}
			UserAccountSummary summary = this.accountService
					.getUserAccountSummary(user_id);
			UserAmount amount = this.accountService.getUserAmount(user_id);
			Account account = this.accountService.getAccount(user_id);
			User kf = this.accountService.getKf(user_id);
			double sum = this.accountService.getAwardSum(user_id);
			UserCredit uc = this.userinfoService.getCreditModelById(user_id);
			Userinfo userinfo = this.userinfoService
					.getUserinfoByUserid(user_id);
			int unreadmsg = this.messageService.getUnreadMesage(user_id);
			int friendrequests = this.friendService
					.countFriendsRequest(user_id);

			if (userinfo != null) {
				userinfo.getAllStatus();
			}

			AutoTenderOrder autoTenderOrder = this.autoBorrowService
					.getAutoTenderOrderByUserid(user.getUser_id());
			if (autoTenderOrder != null) {
				this.request.setAttribute("autoTenderOrder", autoTenderOrder);
			}
			List logList = this.accountService.getAccountLogList(user_id);
			UserCredit userCredit = this.userCreditService
					.getUserCreditByUserId(user_id);
			this.request.setAttribute("userCredit", userCredit);
			this.request.setAttribute("logList", logList);
			this.request.setAttribute("credit", uc);
			this.request.setAttribute("detailuser", detailUser);
			this.request.setAttribute("cache", cache);
			this.request.setAttribute("summary", summary);
			this.request.setAttribute("amount", amount);
			this.request.setAttribute("account", account);
			this.request.setAttribute("kf", kf);
			this.request.setAttribute("userinfo", userinfo);
			this.request.setAttribute("sum", Double.valueOf(sum));
			this.request.setAttribute("unreadmsg", Integer.valueOf(unreadmsg));
			this.request.setAttribute("friendrequests", Integer
					.valueOf(friendrequests));

			this.request.setAttribute("nid", "member");

			List ggList = this.articleService.getList("22", 0, 5);
			this.request.setAttribute("ggList", ggList);

			List bdList = this.articleService.getList("59", 0, 5);
			this.request.setAttribute("bdList", bdList);

			List cjList = this.articleService.getList("98", 0, 5);
			this.request.setAttribute("cjList", cjList);

			List investList = this.borrowService.getInvestList(user_id);
			this.request.setAttribute("investList", investList);
			int page = NumberUtils.getInt(Global.getValue("index_other_num"));
			page = (page > 0) ? page : 5;

			List tsrzxm = this.articleService.getList("122", 0, page);
			this.request.setAttribute("tsrzxm", tsrzxm);

			List jrzx = this.articleService.getList("126", 0, page);
			this.request.setAttribute("jrzx", jrzx);

			List rzxm = this.articleService.getList("128", 0, page);
			this.request.setAttribute("rzxm", rzxm);

			List borrowList = this.borrowService.getBorrowList(user_id);
			this.request.setAttribute("borrowList", borrowList);

			List wzgz = this.articleService.getList("11", 0, page);
			this.request.setAttribute("wzgz", wzgz);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		logger.info("2**********************************");
		return SUCCESS;
	}

	public String userinfo() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();

		Userinfo info = null;

		String type = this.userinfo.getType();
		if ((type == null) || (type.equals(""))) {
			info = this.userinfoService.getUserinfoByUserid(user_id);
		} else {
			this.userinfo.setUser_id(user_id);
			this.userinfoService.updateUserinfo(this.userinfo);

			info = this.userinfoService.getUserinfoByUserid(user_id);
			User updateuser = this.userService
					.getUserByName(user.getUsername());
			if (updateuser != null) {
				updateuser.hideChar();
			}
			this.session.put(Constant.SESSION_USER, updateuser);

			logger.debug("userinfo:" + info);
			String msg = "修改个人信息成功！";
			String errormsg = checkUserinfo();
			if (errormsg.equals(""))
				this.request.setAttribute(MSG, msg);
			else {
				this.request.setAttribute("errormsg", errormsg);
			}
		}
		this.request.setAttribute("info", info);

		return SUCCESS;
	}

	public String showarea() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		String pid = req.getParameter("pid");
		List<Areainfo> areas = this.userinfoService.getAreainfoByPid(pid);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
//		JSONArray jo = JSONArray.fromObject(areas);
		
		String jo = JSON.toJSONString(areas);
		out.print(jo);
		out.close();
		return null;
	}

	public String building() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		Userinfo info = null;

		String type = this.userinfo.getType();
		if ((type == null) || (type.equals(""))) {
			info = this.userinfoService.getUserinfoByUserid(user_id);
		} else {
			this.userinfo.setUser_id(user_id);
			this.userinfoService.updateBuilding(this.userinfo);
			info = this.userinfoService.getUserinfoByUserid(user_id);
			String msg = "修改房产信息成功！";
			this.request.setAttribute(MSG, msg);
		}

		this.request.setAttribute("info", info);
		return SUCCESS;
	}

	public String company() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		Userinfo info = null;

		String type = this.userinfo.getType();
		if ((type == null) || (type.equals(""))) {
			info = this.userinfoService.getUserinfoByUserid(user_id);
		} else {
			this.userinfo.setUser_id(user_id);
			this.userinfoService.updateCompany(this.userinfo);
			info = this.userinfoService.getUserinfoByUserid(user_id);
			String msg = "修改单位信息成功！";
			this.request.setAttribute(MSG, msg);
		}

		this.request.setAttribute("info", info);
		return SUCCESS;
	}

	public String firm() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		Userinfo info = null;

		String type = this.userinfo.getType();
		if ((type == null) || (type.equals(""))) {
			info = this.userinfoService.getUserinfoByUserid(user_id);
		} else {
			this.userinfo.setUser_id(user_id);
			this.userinfoService.updateFirm(this.userinfo);
			info = this.userinfoService.getUserinfoByUserid(user_id);
			String msg = "修改私营业主信息成功！";
			this.request.setAttribute(MSG, msg);
		}

		this.request.setAttribute("info", info);
		return SUCCESS;
	}

	public String finance() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		Userinfo info = null;

		String type = this.userinfo.getType();
		if ((type == null) || (type.equals(""))) {
			info = this.userinfoService.getUserinfoByUserid(user_id);
		} else {
			this.userinfo.setUser_id(user_id);
			this.userinfoService.updateFinance(this.userinfo);
			info = this.userinfoService.getUserinfoByUserid(user_id);
			String msg = "修改财务状况信息成功！";
			this.request.setAttribute(MSG, msg);
		}

		this.request.setAttribute("info", info);
		return SUCCESS;
	}

	public String contact() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		Userinfo info = null;

		String type = this.userinfo.getType();
		if ((type == null) || (type.equals(""))) {
			info = this.userinfoService.getUserinfoByUserid(user_id);
		} else {
			this.userinfo.setUser_id(user_id);
			this.userinfoService.updateContact(this.userinfo);
			info = this.userinfoService.getUserinfoByUserid(user_id);
			User newuser = this.userService.getUserById(user_id);
			newuser.setQq(info.getQq());
			newuser.setTel(info.getTel());
			this.userService.updateuser(newuser);
			String msg = "修改联系方式成功！";
			this.request.setAttribute(MSG, msg);
		}
		this.request.setAttribute("info", info);
		return SUCCESS;
	}

	public String mate() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		Userinfo info = null;

		String type = this.userinfo.getType();
		if ((type == null) || (type.equals(""))) {
			info = this.userinfoService.getUserinfoByUserid(user_id);
		} else {
			this.userinfo.setUser_id(user_id);
			this.userinfoService.updateMate(this.userinfo);
			info = this.userinfoService.getUserinfoByUserid(user_id);
			String msg = "修改联系方式成功！";
			this.request.setAttribute(MSG, msg);
		}

		this.request.setAttribute("info", info);
		return SUCCESS;
	}

	public String education() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		Userinfo info = null;

		String type = this.userinfo.getType();
		if ((type == null) || (type.equals(""))) {
			info = this.userinfoService.getUserinfoByUserid(user_id);
		} else {
			this.userinfo.setUser_id(user_id);
			this.userinfoService.updateEducation(this.userinfo);
			info = this.userinfoService.getUserinfoByUserid(user_id);
			String msg = "修改教育背景成功！";
			this.request.setAttribute(MSG, msg);
		}

		this.request.setAttribute("info", info);
		return SUCCESS;
	}

	public String other() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		Userinfo info = null;

		String type = this.userinfo.getType();
		if ((type == null) || (type.equals(""))) {
			info = this.userinfoService.getUserinfoByUserid(user_id);
		} else {
			this.userinfo.setUser_id(user_id);
			this.userinfoService.updateOtherinfo(this.userinfo);
			info = this.userinfoService.getUserinfoByUserid(user_id);
			String msg = "修改信息成功！";
			this.request.setAttribute(MSG, msg);
		}

		this.request.setAttribute("info", info);
		return SUCCESS;
	}

	public String realname() {
		User sessionuser = (User) this.session.get(Constant.SESSION_USER);
		long user_id = sessionuser.getUser_id();
		User newUser = null;

		String type = this.userinfo.getType();
		if ((type == null) || (type.equals(""))) {
			newUser = this.userService.getDetailUser(user_id);
		} else {
			String realStatus = StringUtils
					.isNull(sessionuser.getReal_status());
			if ((realStatus.equals("1")) || (realStatus.equals("2"))) {
				message("实名认证已经审核成功或正在审核中！", "");
				return MSG;
			}
			String errormsg = checkRealname();
			if (!errormsg.equals("")) {
				newUser = this.userService.getDetailUser(user_id);
				this.request.setAttribute("errormsg", errormsg);
			}
			if (!checkValidImg(StringUtils.isNull(this.valicode))) {
				message("验证码不正确！", "");
				return MSG;
			}

			String contextPath = ServletActionContext.getServletContext()
					.getRealPath("/");
			String upfiesDir = contextPath + "data/upfiles/images";
			String destfilename1 = upfiesDir
					+ getUploadRealnameFileName(user_id, "_1");
			String card_pic1_path = destfilename1.replace(this.sep, "/");
			String destfilename2 = upfiesDir
					+ getUploadRealnameFileName(user_id, "_2");
			String card_pic2_path = destfilename2.replace(this.sep, "/");
			card_pic1_path = truncatUrl(card_pic1_path, contextPath);
			card_pic2_path = truncatUrl(card_pic2_path, contextPath);
			logger.info(destfilename1);
			logger.info(destfilename2);
			File imageFile1 = null;
			File imageFile2 = null;
			try {
				imageFile1 = new File(destfilename1);
				imageFile2 = new File(destfilename2);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			try {
				FileUtils.copyFile(this.userinfo.getCard_pic1(), imageFile1);
				FileUtils.copyFile(this.userinfo.getCard_pic2(), imageFile2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.userinfo.setUser_id(user_id);
			this.userinfo.setReal_status("2");
			this.userinfo.setCard_pic1_path(card_pic1_path);
			this.userinfo.setCard_pic2_path(card_pic2_path);
			System.out.println(this.userinfo.getRealname()
					+ "=========================");
			this.userService.realnameIdentify(this.userinfo);
			newUser = this.userService.getDetailUser(user_id);
			String msg = "实名认证添加成功，请等待管理员审核 ";
			this.request.setAttribute(MSG, msg);
		}

		if (newUser != null) {
			newUser.hideChar();
		}
		this.request.setAttribute("user", newUser);
		return SUCCESS;
	}

	private String getUploadRealnameFileName(long userid, String flag) {
		Date d = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append("/").append(DateUtils.dateStr2(d)).append("/").append(
				d.getTime()).append("_").append(userid).append(flag).append(
				".jpg");
		return sb.toString();
	}

	public String privacy() throws Exception {
		return SUCCESS;
	}

	public String vip() throws Exception {
		User sessionUser = getSessionUser();
		DetailUser detailUser = this.userService.getDetailUser(sessionUser
				.getUser_id());
		UserCacheModel userCache = null;
		String type = this.request.getParameter("type");
		if (StringUtils.isNull(type).equals("")) {
			List list = this.userService.getKfList();
			Account act = this.accountService.getAccount(sessionUser
					.getUser_id());
			userCache = this.userService.getUserCacheByUserid(sessionUser
					.getUser_id());
			this.request.setAttribute("act", act);
			this.request.setAttribute("kflist", list);
		} else {
			UserCache uc = this.userService.getUserCacheByUserid(sessionUser
					.getUser_id());
			if (uc == null) {
				uc = new UserCache();
			}
			if ((uc.getVip_status() == 2) || (uc.getVip_status() == 1)) {
				message("不允许重复申请！", "/member/vip.html");
				return FAIL;
			}
			String valicode = StringUtils.isNull(this.request
					.getParameter("valicode"));

			boolean valid = checkValidImg(valicode);
			if (!valid) {
				message("验证码错误", "/member/vip.html");
				return MSG;
			}
			String user_id_str = this.request.getParameter("kefu_userid");
			int kf_user_id = NumberUtils.getInt(user_id_str);

			if (Global.getWebid().equals("zsdai")) {
				kf_user_id = 1;
			} else if (kf_user_id <= 0) {
				message("请选择客服人员！", "/member/vip.html");
				return FAIL;
			}

			User newuser = this.userService.getUserById(kf_user_id);
			String vip_remark = this.request.getParameter("vip_remark");
			uc.setVip_remark(vip_remark);
			uc.setKefu_userid(kf_user_id);
			uc.setKefu_username(newuser.getUsername());
			uc.setVip_status(2);
			uc.setKefu_addtime(getTimeStr());
			uc.setUser_id(sessionUser.getUser_id());

			AccountLog accountLog = new AccountLog(sessionUser.getUser_id(),
					Constant.VIP_FEE, 1L, getTimeStr(), getRequestIp());
			boolean isOk = true;
			String checkMsg = "";
			try {
				userCache = this.userService.applyVip(uc, accountLog);
			} catch (BorrowException e) {
				isOk = false;
				checkMsg = e.getMessage();
			} catch (Exception e) {
				isOk = false;
				checkMsg = "系统出错，联系管理员！";
				logger.error(e.getMessage());
			}
			if (!isOk) {
				message(checkMsg, "");
				return MSG;
			}

		}

		this.request.setAttribute("detailUser", detailUser);
		this.request.setAttribute("userCache", userCache);
		return SUCCESS;
	}

	private String checkUserinfo() {
		String msg = "";

		if ((StringUtils.isNull(this.userinfo.getShebaoid()).equals(""))
				&& (StringUtils.isNull(this.userinfo.getShebao()).equals(""))) {
			msg = "社保号不能为空！";
		}
		return msg;
	}

	private String checkRealname() {
		String msg = "";

		if (NumberUtils.getInt(this.userinfo.getNation()) == 0) {
			msg = "民族不能为空,请填写！";
			return msg;
		}
		if (StringUtils.isEmpty(this.userinfo.getBirthday())) {
			msg = "出生日期不能为空,请填写！";
			return msg;
		}
		if (StringUtils.isEmpty(this.userinfo.getCard_id())) {
			msg = "证件号码不能为空,请填写！";
			return msg;
		}
		if (StringUtils.isEmpty(this.userinfo.getRealname())) {
			msg = "真实姓名不能为空,请填写！";
			return msg;
		}
		if (NumberUtils.getInt(this.userinfo.getNation()) == 0) {
			msg = "民族不能为空,请填写！";
			return msg;
		}
		if (StringUtils.isEmpty(this.userinfo.getBirthday())) {
			msg = "出生日期不能为空,请填写！";
			return msg;
		}
		if (StringUtils.isEmpty(this.userinfo.getCard_id())) {
			msg = "证件号码不能为空,请填写！";
			return msg;
		}
		if ((StringUtils.isNull(this.userinfo.getCard_type()).equals("385"))
				&& (!StringUtils.isCard(this.userinfo.getCard_id()))) {
			msg = "证件号码格式不正确,请重新填写！";
			return msg;
		}

		User u = this.userService.getUserByCardNO(this.userinfo.getCard_id());
		if (u != null) {
			msg = "该证件号码已被他人使用！";
			return msg;
		}
		if ((this.userinfo.getProvince() == null)
				|| (this.userinfo.getCity() == null)
				|| (this.userinfo.getArea() == null)
				|| ("0".equals(this.userinfo.getProvince()))
				|| ("0".equals(this.userinfo.getArea()))
				|| ("0".equals(this.userinfo.getCity()))) {
			msg = "籍贯不能为空,请填写！";
			return msg;
		}

		if (this.userinfo.getCard_pic1() == null) {
			msg = "您上传的身份证正面上传文件不能为空！";
			return msg;
		}
		if (this.userinfo.getCard_pic2() == null) {
			msg = "您上传的身份证反面上传文件不能为空！";
			return msg;
		}
		if (this.userinfo.getCard_pic1().length() > 1048576L) {
			msg = "您上传的身份证正面上传文件大小必须小于1MB.";
			return msg;
		}
		if (this.userinfo.getCard_pic2().length() > 1048576L) {
			msg = "您上传的身份证反面上传文件大小必须小于1MB.";
			return msg;
		}
		if (!this.userinfo.getCard_pic1FileName().matches(
				".*(jpg|png|gif|JPG|PNG|GIF)")) {
			msg = "您上传的身份证正面上传文件类型必须为.jpg, .gif或者.png类型";
			return msg;
		}
		if (!this.userinfo.getCard_pic2FileName().matches(
				".*(jpg|png|gif|JPG|PNG|GIF)")) {
			msg = "您上传的身份证反面上传文件类型必须为.jpg, .gif或者.png类型";
		}
		return msg;
	}

	private String truncatUrl(String old, String truncat) {
		old = old.replace(this.sep, "/");
		truncat = truncat.replace(this.sep, "/");
		String url = "";
		url = old.replace(truncat, "");
		return url;
	}

	public BorrowService getBorrowService() {
		return this.borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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

	public MessageService getMessageService() {
		return this.messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public UserCreditService getUserCreditService() {
		return this.userCreditService;
	}

	public void setUserCreditService(UserCreditService userCreditService) {
		this.userCreditService = userCreditService;
	}
}