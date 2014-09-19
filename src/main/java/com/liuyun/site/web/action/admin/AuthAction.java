package com.liuyun.site.web.action.admin;

import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.domain.Purview;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserTrack;
import com.liuyun.site.model.purview.PurviewSet;
import com.liuyun.site.service.ManageAuthService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.tool.coder.MD5;
import com.liuyun.site.tool.uchon.UchonHelper;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import java.util.List;
import org.apache.log4j.Logger;

public class AuthAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(AuthAction.class);
	
	private UserService userService;
	private ManageAuthService manageAuthService;
	private String username;
	private String password;
	private String valicode;
	private String uchoncode;
	private StringBuffer sb = new StringBuffer();

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

	public String getValicode() {
		return this.valicode;
	}

	public void setValicode(String valicode) {
		this.valicode = valicode;
	}

	public ManageAuthService getManageAuthService() {
		return this.manageAuthService;
	}

	public void setManageAuthService(ManageAuthService manageAuthService) {
		this.manageAuthService = manageAuthService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String index() throws Exception {
		return SUCCESS;
	}

	public String auth() throws Exception {
		String source = StringUtils.isNull(this.request.getAttribute("source"));
		if (source.equals("fliter")) {
			return ERROR;
		}
		String msg = "";//checkAuth();
		if (!StringUtils.isBlank(msg)) {
			this.request.setAttribute(MSG, this.sb.toString());
			return ERROR;
		}
		User u = this.userService.login(this.username, this.password);
		if (u == null) {
			msg = "用户名密码不正确！";
			this.sb.append(msg);
			this.request.setAttribute(MSG, this.sb.toString());
			return ERROR;
		}
		
		/**/
		if (!StringUtils.isBlank(u.getSerial_id())) {
			msg = "";
			if (StringUtils.isBlank(this.uchoncode)) {
				msg = "请输入动态口令!";
				this.sb.append(msg);
				this.request.setAttribute(MSG, this.sb.toString());
				return ERROR;
			}
			int result = UchonHelper.checkOtp(u.getSerial_id(), this.uchoncode);
			if (result == 421)
				msg = "动态口令有误！";
			else if (result == 0)
				msg = "动态口令服务器出现错误!";
			else if (result != 200) {
				msg = "动态口令出现错误!";
			}
			if (!StringUtils.isBlank(msg)) {
				this.sb.append(msg);
				this.request.setAttribute(MSG, this.sb.toString());
				return ERROR;
			}
		} else {
			MD5 md5 = new MD5();
			String uckey = md5.getMD5ofStr(Global.getString("weburl"));
			if (!uckey.equals(this.uchoncode)) {
				this.sb.append("口令卡不对！");
				this.request.setAttribute(MSG, this.sb.toString());
				return ERROR;
			}
		}
		
		
		List<Purview> list = this.manageAuthService.getPurviewByUserid(u.getUser_id());
		if ((list == null) || (list.size() < 1)) {
			msg = "没有权限，非法访问！";
			this.sb.append(msg);
			this.request.setAttribute(MSG, this.sb.toString());
			return ERROR;
		}
		PurviewSet ps = new PurviewSet(list);
		ps.toSet();
		this.session.put(Constant.AUTH_PURVIEW, ps);
		this.session.put(Constant.AUTH_USER, u);

		UserTrack newTrack = new UserTrack();
		newTrack.setUser_id("" + u.getUser_id());
		newTrack.setLogin_ip(getRequestIp());
		newTrack.setLogin_time(DateUtils.getNowTimeStr());
		this.userService.addUserTrack(newTrack);

		return SUCCESS;
	}

	private String checkAuth() {
		if (StringUtils.isBlank(this.valicode)) {
			this.sb.append("验证码不能为空！");
			return ERROR;
		}
		if (!checkValidImg(this.valicode)) {
			this.sb.append("验证码不正确！");
			return ERROR;
		}
		if (StringUtils.isBlank(this.username)) {
			this.sb.append("用户名不能为空！");
			return ERROR;
		}
		if (StringUtils.isBlank(this.password)) {
			this.sb.append("密码不能为空！");
			return ERROR;
		}
		return "";
	}

	public String logout() throws Exception {
		this.session.put(Constant.AUTH_USER, null);
		this.session.put(Constant.SESSION_USER, null);
		this.session.put(Constant.AUTH_PURVIEW, null);
		this.session.put("adminUrlCheck", null);
		message("退出成功！", Global.getString("admin_url"));
		return ADMINMSG;
	}

	public String getUchoncode() {
		return this.uchoncode;
	}

	public void setUchoncode(String uchoncode) {
		this.uchoncode = uchoncode;
	}
	
	public static void main(String[] args) {
		MD5 md5 = new MD5();
		String uckey = md5.getMD5ofStr("http://www.sfdai.com");

		System.out.println(uckey);

	}
}