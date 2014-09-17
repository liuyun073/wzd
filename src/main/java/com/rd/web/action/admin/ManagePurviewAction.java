package com.rd.web.action.admin;

import com.opensymphony.xwork2.ModelDriven;
import com.rd.domain.Purview;
import com.rd.domain.UserType;
import com.rd.exception.ManageAuthException;
import com.rd.exception.UserServiceException;
import com.rd.model.AdminInfoModel;
import com.rd.model.DetailUser;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.model.UserinfoModel;
import com.rd.model.purview.PurviewSet;
import com.rd.service.ManageAuthService;
import com.rd.service.UserService;
import com.rd.service.UserinfoService;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import com.rd.web.action.BaseAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： ManagePurviewAction   
 * 类描述： 后台管理--权限管理模块      
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Nov 4, 2013 12:20:38 AM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Nov 4, 2013 12:20:38 AM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class ManagePurviewAction extends BaseAction implements
		ModelDriven<Purview> {
	private static final Logger logger = Logger
			.getLogger(ManagePurviewAction.class);

	Purview model = new Purview();
	List<Integer> purviewid;
	ManageAuthService manageAuthService;
	UserService userService;
	UserinfoService userinfoService;
	private UserinfoModel UserinfoModel = new UserinfoModel();

	public UserinfoModel getUserinfoModel() {
		return this.UserinfoModel;
	}

	public void setUserinfoModel(UserinfoModel userinfoModel) {
		this.UserinfoModel = userinfoModel;
	}

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

	public static Logger getLogger() {
		return logger;
	}

	public List<Integer> getPurviewid() {
		return this.purviewid;
	}

	public void setPurviewid(List<Integer> purviewid) {
		this.purviewid = purviewid;
	}

	public void setModel(Purview model) {
		this.model = model;
	}

	public Purview getModel() {
		return this.model;
	}

	public ManageAuthService getManageAuthService() {
		return this.manageAuthService;
	}

	public void setManageAuthService(ManageAuthService manageAuthService) {
		this.manageAuthService = manageAuthService;
	}

	public String showAllRole() throws Exception {
		List userTypeList = this.userService.getAllUserType();
		this.request.setAttribute("list", userTypeList);
		return SUCCESS;
	}

	public String modifyRole() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		int id = NumberUtils.getInt(this.request.getParameter("id"));
		String name = this.request.getParameter("name");
		String purview = this.request.getParameter("purview");
		int type = NumberUtils.getInt(this.request.getParameter("type"));
		String order = this.request.getParameter("order");
		String remark = this.request.getParameter("remark");
		UserType userType = new UserType();
		userType.setType_id(id);
		userType.setName(name);
		userType.setPurview(purview);
		userType.setType(type);
		userType.setOrder(order);
		userType.setRemark(remark);

		List userTypeList = new ArrayList();
		userTypeList.add(userType);
		String msg = "修改成功！";
		if (!StringUtils.isBlank(actionType)) {
			this.userService.updateUserTypeByList(userTypeList);
			message(msg, "/admin/purview/showAllRole.html");
			return ADMINMSG;
		}

		this.request.setAttribute("id", Integer.valueOf(id));
		this.request.setAttribute("userType", this.userService
				.getUserTypeById(id));
		return SUCCESS;
	}

	public String delRole() throws Exception {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		String msg = "删除成功！";
		try {
			this.userService.deleteUserTypeById(id);
		} catch (UserServiceException e) {
			logger.debug("delRole:" + e.getMessage());
			msg = e.getMessage();
		}
		message(msg, "/admin/purview/showAllRole.html");
		return ADMINMSG;
	}

	public String addRole() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		int id = NumberUtils.getInt(this.request.getParameter("id"));
		String msg = "增加成功！";
		String name = this.request.getParameter("name");
		String purview = this.request.getParameter("purview");
		int type = NumberUtils.getInt(this.request.getParameter("type"));
		String order = this.request.getParameter("order");
		String remark = this.request.getParameter("remark");
		UserType userType = new UserType();
		userType.setName(name);
		userType.setPurview(purview);
		userType.setType(type);
		userType.setOrder(order);
		userType.setRemark(remark);
		if (!StringUtils.isBlank(actionType)) {
			this.userService.addUserType(userType);
			message(msg, "/admin/purview/showAllRole.html");
			return ADMINMSG;
		}
		this.request.setAttribute("id", Integer.valueOf(id));
		return SUCCESS;
	}

	public String setPurview() throws Exception {
		long user_typeid = NumberUtils.getLong(this.request
				.getParameter("user_typeid"));
		UserType userType = this.userService.getUserTypeById(user_typeid);
		if (userType == null) {
			message("该角色不存在", "/admin/purview/showAllRole.html");
			return ADMINMSG;
		}
		if (!getActionType().isEmpty()) {
			logger.debug(this.purviewid);
			this.manageAuthService.addUserTypePurviews(this.purviewid,
					user_typeid);
		}
		UserType role = this.userService.getUserTypeById(user_typeid);
		List list = this.manageAuthService.getAllCheckedPurview(user_typeid);

		PurviewSet ps = new PurviewSet(list);
		Set set = ps.toSet();
		this.request.setAttribute("purviews", set);
		this.request.setAttribute("role", role);
		return SUCCESS;
	}

	public String allPurview() throws Exception {
		int pid = NumberUtils.getInt(this.request.getParameter("pid"));
		List list = this.manageAuthService.getPurviewByPid(pid);
		setMsgUrl("/admin/purview/allPurview.html");
		this.request.setAttribute("list", list);
		this.request.setAttribute("pid", Integer.valueOf(pid));
		return SUCCESS;
	}

	public String addPurview() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		int pid = NumberUtils.getInt(this.request.getParameter("pid"));
		if (!StringUtils.isBlank(actionType)) {
			Purview p = this.manageAuthService.getPurview(pid);
			if (p != null)
				this.model.setLevel(p.getLevel() + 1);
			else {
				this.model.setLevel(1);
			}
			this.manageAuthService.addPurview(this.model);
			message("新增权限成功！");
			return ADMINMSG;
		}
		this.request.setAttribute("pid", Integer.valueOf(pid));
		return SUCCESS;
	}

	public String delPurview() throws Exception {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		String msg = "删除成功！";
		try {
			this.manageAuthService.delPurview(id);
		} catch (ManageAuthException e) {
			logger.debug("delPurview:" + e.getMessage());
			msg = e.getMessage();
		}
		message(msg);
		return ADMINMSG;
	}

	public String modifyPurview() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		int id = NumberUtils.getInt(this.request.getParameter("id"));
		if (!StringUtils.isBlank(actionType)) {
			this.manageAuthService.modifyPurview(this.model);
			message("修改权限成功");
			return ADMINMSG;
		}
		Purview p = this.manageAuthService.getPurview(id);
		this.request.setAttribute("p", p);
		return SUCCESS;
	}

	public String allAdmin() throws Exception {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		int type = NumberUtils.getInt(this.request.getParameter("type"));
		SearchParam searchParam = new SearchParam();
		PageDataList pageDataList = this.userService.getUserList(page,
				searchParam, type);
		this.request.setAttribute("adminList", pageDataList.getList());
		this.request.setAttribute("page", pageDataList.getPage());
		this.request.setAttribute("params", searchParam.toMap());
		return SUCCESS;
	}

	public String editAdmin() throws Exception {
		long userid = NumberUtils.getLong(this.request.getParameter("user_id"));
		int type = NumberUtils.getInt(this.request.getParameter("type"));
		if (!StringUtils.isBlank(this.actionType)) {
			AdminInfoModel model = this.userinfoService
					.getAllAdminInfoModelByUserid(userid);
			this.userinfoService.updateAdmininfo(model);
			message("修改信息成功", "/admin/purview/allAdmin.html?type=1");
			return ADMINMSG;
		}
		DetailUser detailUser = this.userService.getDetailUser(userid, type);
		this.request.setAttribute("user", detailUser);
		return SUCCESS;
	}
}