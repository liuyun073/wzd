package com.rd.web.action.admin;

import com.rd.context.Global;
import com.rd.domain.SystemConfig;
import com.rd.model.SystemInfo;
import com.rd.service.SystemService;
import com.rd.util.StringUtils;
import com.rd.web.action.BaseAction;
import java.util.List;
import java.util.Properties;


/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： SystemConfigAction   
 * 类描述： 后台管理--系统设置管理模块       
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Nov 4, 2013 12:21:13 AM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Nov 4, 2013 12:21:13 AM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class SystemConfigAction extends BaseAction {
	private SystemService systemService;
	private List<SystemConfig> sysInfo;

	public List<SystemConfig> getSysInfo() {
		return this.sysInfo;
	}

	public void setSysInfo(List<SystemConfig> sysInfo) {
		this.sysInfo = sysInfo;
	}

	public SystemService getSystemService() {
		return this.systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public String index() {
		this.sysInfo = this.systemService.getSystemInfoForListBysytle(1);
		return SUCCESS;
	}

	public String update() {
		if (this.sysInfo != null)
			this.systemService.updateSystemInfo(this.sysInfo, this.context);
		this.sysInfo = this.systemService.getSystemInfoForListBysytle(1);
		SystemInfo info = this.systemService.getSystemInfo();
		String[] webinfo = Global.SYSTEMNAME;
		for (String s : webinfo) {
			this.context.setAttribute(s, info.getValue(s));
		}
		return SUCCESS;
	}

	public String clean() {
		System.out.println(this.context.getRealPath(""));
		return SUCCESS;
	}

	public String welcome() {
		Properties props = System.getProperties();
		Runtime runtime = Runtime.getRuntime();
		long freeMemoery = runtime.freeMemory();
		long totalMemory = runtime.totalMemory();
		long usedMemory = totalMemory - freeMemoery;
		long maxMemory = runtime.maxMemory();
		long useableMemory = maxMemory - totalMemory + freeMemoery;
		this.request.setAttribute("props", props);
		this.request.setAttribute("freeMemoery", Long.valueOf(freeMemoery));
		this.request.setAttribute("totalMemory", Long.valueOf(totalMemory));
		this.request.setAttribute("usedMemory", Long.valueOf(usedMemory));
		this.request.setAttribute("maxMemory", Long.valueOf(maxMemory));
		this.request.setAttribute("useableMemory", Long.valueOf(useableMemory));
		return SUCCESS;
	}

	public String add() {
		String actionType = this.request.getParameter("actionType");
		String name = this.request.getParameter("name");
		String nid = this.request.getParameter("nid");
		String value = this.request.getParameter("value");
		String msg = "增加成功";
		SystemConfig sysconfig = new SystemConfig();
		sysconfig.setName(name);
		sysconfig.setNid(nid);
		sysconfig.setValue(value);
		sysconfig.setStatus("1");

		if (!StringUtils.isBlank(actionType)) {
			if (name == null) {
				message("参数名不能为空", "/admin/system/index.html");
				return ADMINMSG;
			}
			if (value == null) {
				message("参数值不能为空", "/admin/system/index.html");
				return ADMINMSG;
			}
			if (nid == null) {
				message("变量名不能为空", "/admin/system/index.html");
				return ADMINMSG;
			}
			this.systemService.addSystemConfig(sysconfig);
			message(msg, "/admin/system/index.html");
			return ADMINMSG;
		}

		return SUCCESS;
	}
}