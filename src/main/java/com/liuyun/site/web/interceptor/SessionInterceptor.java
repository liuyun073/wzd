package com.liuyun.site.web.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.dao.UserDao;
import com.liuyun.site.domain.User;
import com.liuyun.site.util.StringUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： SessionInterceptor   
 * 类描述： 会话拦截器   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 8:15:33 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 8:15:33 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class SessionInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -2239644443711524657L;
	
	private UserDao userDao;

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String, Object> session = ctx.getSession();
		User user = (User) session.get(Constant.SESSION_USER);

		if (user != null) {
			Cookie[] cookies = request.getCookies();
			String username = null;
			long logintime = 0L;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase("username"))
					try {
						username = URLDecoder.decode(cookie.getValue(), "UTF-8");
					} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
						
					}
				if (cookie.getName().equalsIgnoreCase(Constant.LOGINTIME)) {
					logintime = Long.parseLong(cookie.getValue());
				}

			}

			long lastlogintime = 0L;
			if (Global.SESSION_MAP.containsKey(username)) {
				lastlogintime = Long.parseLong(Global.SESSION_MAP.get(username).toString());
			}

			if (logintime < lastlogintime) {
				user = null;

				session.remove(Constant.SESSION_USER);
				Cookie[] overdueCookies = request.getCookies();
				try {
					for (int k = 0; k < overdueCookies.length; ++k) {
						Cookie cookie = new Cookie(cookies[k].getName(), null);
						cookie.setMaxAge(0);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				} catch (Exception ex) {
					System.out.println("清空Cookies发生异常！");
				}
			}

		}

		if (user == null) {
			String servletPath = request.getServletPath();

			servletPath = servletPath.replace(".action", ".html");
			String queryString = request.getQueryString();
			String redirectURL = servletPath;
			if (!StringUtils.isBlank(queryString)) {
				redirectURL = request.getContextPath() + servletPath + "?" + StringUtils.isNull(queryString);
			}
			redirectURL = URLEncoder.encode(redirectURL, "UTF-8");
			response.sendRedirect(request.getContextPath() + "/user/login.html?redirectURL=" + request.getContextPath() + redirectURL);
			return null;
		}
		
		return actionInvocation.invoke();
	}
}