package com.rd.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.rd.context.Global;
import com.rd.exception.BussinessException;
import com.rd.exception.ManageBussinessException;
import com.rd.tool.iphelper.IPUtils;
import com.rd.web.action.BaseAction;

import freemarker.core.ParseException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： ActionInterceptor   
 * 类描述： action 拦截器  
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 8:00:19 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 8:00:19 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class ActionInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1008901298342362080L;
	private static final Logger log = Logger.getLogger(ActionInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip = IPUtils.getRemortIP(request);
		Global.ipThreadLocal.set(ip);
		log.debug("Set Ip:" + ip);
		String actionName = invocation.getInvocationContext().getName();
		try {
			String result = invocation.invoke();
			return result;
		} catch (BussinessException e) {
			log.error(e);
			String urlback = "<a href='javascript:history.go(-1)'>返回上一页</a>";
			request.setAttribute("backurl", urlback);
			request.setAttribute("rsmsg", e.getMessage());
			return BaseAction.MSG;
		} catch (ManageBussinessException e) {
			log.error(actionName, e);
			String urlback = "<a href='javascript:history.go(-1)'>返回上一页</a>";
			request.setAttribute("backurl", urlback);
			request.setAttribute("rsmsg", e.getMessage());
			return BaseAction.ADMINMSG;
		} catch (ParseException e) {
			log.error(actionName, e);
			String urlback = "<a href='javascript:history.go(-1)'>返回上一页</a>";
			request.setAttribute("backurl", urlback);
			request.setAttribute("rsmsg", "页面显示异常，联系管理员！");
			return BaseAction.MSG;
		} catch (Exception e) {
			log.error(actionName, e);
			e.printStackTrace();
			String urlback = "<a href='javascript:history.go(-1)'>返回上一页</a>";
			request.setAttribute("backurl", urlback);
			request.setAttribute("rsmsg", "系统异常联系管理员！");
		}
		return BaseAction.MSG;
	}
}