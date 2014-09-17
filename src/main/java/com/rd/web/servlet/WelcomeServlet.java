package com.rd.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： WelcomeServlet   
 * 类描述： 暂时没有用到   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 9:07:10 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 9:07:10 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = -7893123013990800964L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/index.action").forward(request, response);
	}
}