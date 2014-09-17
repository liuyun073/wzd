package com.rd.web.action.member;

import com.alibaba.fastjson.JSON;
import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.domain.Attestation;
import com.rd.domain.Notice;
import com.rd.domain.NoticeConfig;
import com.rd.domain.User;
import com.rd.domain.UserCache;
import com.rd.quartz.notice.NoticeJobQueue;
import com.rd.quartz.notice.Sms;
import com.rd.service.UserService;
import com.rd.service.UserinfoService;
import com.rd.tool.coder.BASE64Decoder;
import com.rd.tool.javamail.Mail;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import com.rd.web.action.BaseAction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class IdentifyAction extends BaseAction {
	private static Logger logger = Logger.getLogger(IdentifyAction.class);
	private UserinfoService userinfoService;
	private UserService userService;
	private File litpic;
	private String name;
	private int type_id;
	private String type;
	private String filePath;
	private String sep = File.separator;
	private String email;
	private String phone;
	private String verify_remark;
	private String verify_time;

	public UserinfoService getUserinfoService() {
		return this.userinfoService;
	}

	public void setUserinfoService(UserinfoService userinfoService) {
		this.userinfoService = userinfoService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public File getLitpic() {
		return this.litpic;
	}

	public void setLitpic(File litpic) {
		this.litpic = litpic;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType_id() {
		return this.type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getVerify_remark() {
		return this.verify_remark;
	}

	public void setVerify_remark(String verify_remark) {
		this.verify_remark = verify_remark;
	}

	public String getVerify_time() {
		return this.verify_time;
	}

	public void setVerify_time(String verify_time) {
		this.verify_time = verify_time;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean validPicture(File imageFile2) throws Exception {
		String retMsg = "";
		try {
			BufferedImage bi = ImageIO.read(imageFile2);
			if (bi == null) {
				logger.info("你输入的文件为无效图片，即图片文件不合法");
				return false;
			}
			logger.info("你输入的文件为有效图片");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public String list() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		String actionType = this.request.getParameter("actionType");
		if (!StringUtils.isBlank(actionType)) {
			String validcode = this.request.getParameter("validcode");

			if (this.litpic == null) {
				message("你上传的图片为空", "/member/identify/attestation.html");
				return MSG;
			}

			if (validcode.isEmpty()) {
				message("你输入的校验码为空！", "/member/identify/attestation.html");
				return MSG;
			}

			if (!checkValidImg(validcode)) {
				message("你输入的校验码错误！", "/member/identify/attestation.html");
				return MSG;
			}
			boolean litpicValid = validPicture(this.litpic);
			if (!litpicValid) {
				message("你上传的图片不合法！", "/member/identify/attestation.html");
				return MSG;
			}
		}
		List list = new ArrayList();
		Attestation att = new Attestation();
		if ((this.type != null) && (!this.type.equals(""))) {
			moveFile(user);
			att.setUser_id(user.getUser_id());
			att.setName(this.name);
			att.setType_id(this.type_id);
			att.setAddtime("" + new Date().getTime() / 1000L);
			att.setLitpic(this.filePath);
			att.setJifen(this.userinfoService.getAttestationTypeByTypeId(
					this.type_id).getJifen());
			att.setVerify_remark(this.verify_remark);
			att.setVerify_time(this.verify_time);
			this.userinfoService.addAttestation(att);
		}
		list = this.userinfoService.getAttestationListByUserid(user
				.getUser_id());
		this.request.setAttribute("attestations", list);
		return SUCCESS;
	}

	private void moveFile(User user) {
		String dataPath = ServletActionContext.getServletContext().getRealPath(
				"/data");
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		Date d1 = new Date();
		String upfiesDir = dataPath + this.sep + "upfiles" + this.sep
				+ "images" + this.sep;
		String destfilename1 = upfiesDir + DateUtils.dateStr2(d1) + this.sep
				+ user.getUser_id() + "_attestation" + "_" + d1.getTime()
				+ ".jpg";
		this.filePath = destfilename1;
		this.filePath = truncatUrl(this.filePath, contextPath);
		logger.info(destfilename1);
		File imageFile1 = null;
		try {
			imageFile1 = new File(destfilename1);
			FileUtils.copyFile(this.litpic, imageFile1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	public String email() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		String emailUrl = "/member/identify/email.html";
		user = this.userService.getDetailUser(user.getUser_id());
		this.request.setAttribute("user", user);
		if ("".equals(getActionType())) {
			return SUCCESS;
		}
		String emailStatus = StringUtils.isNull(user.getEmail_status());
		if ((emailStatus.equals("1")) || (emailStatus.equals("2"))) {
			message("邮箱认证已经审核成功或正在审核中！", "");
			return MSG;
		}

		String errormsg = "";
		if ((this.email == null) || (this.email.equals(""))) {
			message("email不能为空！", emailUrl);
			return MSG;
		}
		if ((user.getEmail() == null) || (!user.getEmail().equals(this.email))) {
			user.setEmail(this.email);
			User newUser = this.userService.modifyEmail(user);
			if (newUser != null) {
				this.session.put(Constant.SESSION_USER, newUser);
				this.request.setAttribute("user", newUser);
			}
		}
		try {
			sendMail(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			errormsg = "发送激活邮件成功！";
			message(errormsg, emailUrl);
			return MSG;
		}

		errormsg = "发送激活邮件成功！";
		message(errormsg, emailUrl);
		return MSG;
	}

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
			errormsg = "链接无效，请重新获取邮件！";
			this.request.setAttribute("errormsg", errormsg);
			return SUCCESS;
		}
		long activeTime = NumberUtils.getLong(idstrArr[2]);

		if (System.currentTimeMillis() - activeTime * 1000L > 86400000L) {
			errormsg = "链接有效时间1天，已经失效，请重新获取邮件!";
			this.request.setAttribute("errormsg", errormsg);
			return SUCCESS;
		}
		if (idstrArr.length < 1) {
			errormsg = "解析激活码失败，请联系管理员！";
			this.request.setAttribute("errormsg", errormsg);
			return SUCCESS;
		}
		String useridStr = idstrArr[0];
		user = (User) this.session.get(Constant.SESSION_USER);
		if (user == null) {
			long user_id = 0L;
			try {
				user_id = Long.parseLong(useridStr);
			} catch (Exception e) {
				user_id = 0L;
			}
			if (user_id > 0L) {
				user = this.userService.getUserById(user_id);
			} else {
				errormsg = "解析激活码失败，请联系管理员！";
				this.request.setAttribute("errormsg", errormsg);
				return SUCCESS;
			}
		}
		long user_id = user.getUser_id();
		if (useridStr.equals(user_id)) {
			user.setEmail_status("1");
			User newUser = this.userService.modifyEmail_status(user);

			msg = "邮箱激活成功！";
		} else {
			errormsg = "激活失败，请重新激活！";
		}

		if (errormsg.equals("")) {
			this.request.setAttribute(MSG, msg);
		} else
			this.request.setAttribute("errormsg", errormsg);

		return SUCCESS;
	}

	public String phoneSms() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		String valicode = StringUtils.isNull(this.request
				.getParameter("valicode"));
		String code_number = (String) this.session.get("code_number");
		String errormsg = "";
		String phoneUrl = "/member/identify/phone.html";
		String phone = StringUtils.isNull(this.request.getParameter("mobile"));
		if ((phone == null) || (phone.equals(""))) {
			errormsg = "手机号码不能为空！";
			json(errormsg);
			return null;
		}
		long date = DateUtils.getTime(new Date());
		long preDate = 0L;
		Object object = this.session.get("nowdate");
		if (object == null) {
			object = "";
		}
		if (!"".equals(object)) {
			preDate = Long.parseLong(object.toString());
		}

		if (!StringUtils.isBlank(code_number)) {
			if (!code_number.equals(valicode)) {
				errormsg = "短信验证码不正确！,请查看是否过期或者输入错误";
				json(errormsg);
				return null;
			}
			user.setPhone(phone);
			user.setPhone_status("1");
			this.userService.modifyPhone(user);
			this.session.put(Constant.SESSION_USER, user);
			errormsg = "短信验证成功";
			json(errormsg);
			return null;
		}

		User newuser = this.userService.getUserById(user.getUser_id());
		if ((!StringUtils.isNull(newuser.getReal_status()).equals(""))
				&& (StringUtils.isNull(newuser.getReal_status()).equals("1"))) {
			if (newuser != null)
				this.session.put(Constant.SESSION_USER, newuser);
		} else {
			errormsg = "请先进行实名认证！";
			json(errormsg);
			return null;
		}

		return null;
	}

	public String phone() throws Exception {
		User newUser = null;
		String phoneUrl = "/member/identify/phone.html";
		User user = getSessionUser();
		String errormsg = "";
		if (isBlank()) {
			newUser = this.userService.getUserById(user.getUser_id());
			newUser.hideChar();
			this.request.setAttribute("user", newUser);
			return SUCCESS;
		}
		newUser = this.userService.getUserById(user.getUser_id());
		String phonelStatus = StringUtils.isNull(newUser.getPhone_status());
		if ((phonelStatus.equals("1")) || (phonelStatus.equals("2"))) {
			message("手机认证已经审核成功或正在审核中！", "");
			return MSG;
		}
		if ((this.phone == null) || (this.phone.equals(""))) {
			errormsg = "手机号码不能为空！";
			message(errormsg, phoneUrl);
			return MSG;
		}

		User newuser = this.userService.getUserById(user.getUser_id());
		if ((!StringUtils.isNull(newuser.getReal_status()).equals(""))
				&& (StringUtils.isNull(newuser.getReal_status()).equals("1"))) {
			user.setPhone(this.phone);
			user.setPhone_status("2");
			newuser = this.userService.modifyPhone(user);
			if (newuser != null)
				this.session.put(Constant.SESSION_USER, newuser);
		} else {
			message("请先进行实名认证！", "/member/identify/realname.html");
			return MSG;
		}
		errormsg = "手机认证申请提交成功，等待管理员审核！";
		message(errormsg, phoneUrl);

		return MSG;
	}

	public void json(String returnmessage) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("data", returnmessage);
		try {
			printJson(JSON.toJSONString(jsonMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String mobileaccess() {
		String code_number = code();

		long date = DateUtils.getTime(new Date());
		String returnmessage = "";
		if (this.session.get("nowdate") != null) {
			logger.info("当前系统时间" + date + "上一次获取时间"
					+ this.session.get("nowdate"));
			long preDate = Long.parseLong(this.session.get("nowdate")
					.toString());
			if ((this.session.get("nowdate") != null) && (date - preDate < 60L)) {
				returnmessage = "本次短信验证码已发出，请60秒后重试。如果超过60秒还没有输该验证码，请重新获取";
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("data", returnmessage);
				try {
					printJson(JSON.toJSONString(jsonMap));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

		}

		User user = (User) this.session.get(Constant.SESSION_USER);

		this.session.put("code_number", code_number);
		date = DateUtils.getTime(new Date());
		this.session.put("nowdate", Long.valueOf(date));
		NoticeConfig noticeConfig = Global.getNoticeConfig("phone_code");
		String mobile = this.request.getParameter("mobile");
		Notice s = new Sms();
		if (noticeConfig.getSms() == 1L) {
			s.setReceive_userid(user.getUser_id());
			s.setSend_userid(1L);
			s.setContent("尊敬的" + Global.getValue("webname") + "用户["
					+ user.getUsername() + "]，您于"
					+ DateUtils.dateStr5(s.getAddtime()) + "进行手机认证，所获取验证码为"
					+ code_number);
			s.setMobile(mobile);
			NoticeJobQueue.NOTICE.offer(s);
		}
		return null;
	}

	public static String code() {
		Set set = GetRandomNumber();

		Iterator iterator = set.iterator();

		String temp = "";
		while (iterator.hasNext()) {
			temp = temp + iterator.next();
		}

		return temp;
	}

	public static Set<Integer> GetRandomNumber() {
		Set set = new HashSet();

		Random random = new Random();

		while (set.size() < 4) {
			set.add(Integer.valueOf(random.nextInt(10)));
		}
		return set;
	}

	private String truncatUrl(String old, String truncat) {
		String url = "";
		url = old.replace(truncat, "");
		url = url.replace(this.sep, "/");
		return url;
	}

	private void sendMail(User user) throws Exception {
		String to = user.getEmail();
		Mail m = Mail.getInstance();
		m.setTo(to);
		m.readActiveMailMsg();
		m.replace(user.getUsername(), to, "/member/identify/active.html?id="
				+ m.getdecodeIdStr(user));
		logger.debug("Email_msg:" + m.getBody());
		m.sendMail();
	}

	public String scene() throws Exception {
		User user = getSessionUser();
		user = this.userService.getDetailUser(user.getUser_id());
		this.request.setAttribute("user", user);
		if (isBlank()) {
			return SUCCESS;
		}

		user.setScene_status(2);
		User newUser = this.userService.modifyScene_status(user);
		if (newUser != null) {
			this.session.put(Constant.SESSION_USER, newUser);
			this.request.setAttribute("user", newUser);
		}
		String msg = "现场认证申请提交成功，等待管理员审核！";
		message(msg, "/member/identify/scene.html");
		return MSG;
	}

	public String video() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		user = this.userService.getDetailUser(user.getUser_id());
		this.request.setAttribute("user", user);
		if (isBlank()) {
			return SUCCESS;
		}
		user.setVideo_status(2);
		User newUser = this.userService.modifyVideo_status(user);
		if (newUser != null) {
			UserCache userCache = this.userService.getUserCacheByUserid(user
					.getUser_id());
			this.request.setAttribute("user", newUser);
			this.request.setAttribute("cache", userCache);
		}
		String msg = "视频认证申请提交成功，等待管理员审核！";
		message(msg, "/member/identify/video.html");
		return MSG;
	}
}