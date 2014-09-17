package com.rd.web.action;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.apache.struts2.util.TokenHelper;

import com.octo.captcha.service.CaptchaServiceException;
import com.opensymphony.xwork2.ActionContext;
import com.rd.context.Constant;
import com.rd.domain.Borrow;
import com.rd.domain.User;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.tool.iphelper.IPSeeker;
import com.rd.tool.iphelper.IPUtils;
import com.rd.tool.javamail.MailWithAttachment;
import com.rd.tool.jcaptcha.CaptchaServiceSingleton;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;

public class BaseAction implements ServletRequestAware, ServletResponseAware,
		SessionAware, ServletContextAware {
	
	
	private static final Logger logger = Logger.getLogger(BaseAction.class);
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String FAIL = "fail";
	public static final String OK = "ok";
	public static final String MSG = "msg";
	public static final String ADMINMSG = "adminmsg";
	public static final String NOTFOUND = "notfound";
	protected Map<String, Object> session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext context;
	protected String actionType;

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	public String getActionType() {
		return StringUtils.isNull(this.actionType);
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	protected User getSessionUser() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		return user;
	}

	protected User getAuthUser() {
		User user = (User) this.session.get(Constant.AUTH_USER);
		return user;
	}

	protected String getRequestIp() {
		String realip = IPUtils.getRemortIP(this.request);
		return realip;
	}

	protected String getAreaByIp() {
		String realip = getRequestIp();
		return getAreaByIp(realip);
	}

	protected String getAreaByIp(String ip) {
		IPSeeker ipSeeker = IPSeeker.getInstance();
		String nowarea = ipSeeker.getArea(ip);
		return nowarea;
	}

	protected String getTimeStr() {
		String str = Long.toString(System.currentTimeMillis() / 1000L);
		return str;
	}

	protected void genernateCaptchaImage() throws IOException {
		this.response.setHeader("Cache-Control", "no-store");
		this.response.setHeader("Pragma", "no-cache");
		this.response.setDateHeader("Expires", 0L);
		this.response.setContentType("image/jpeg");
		ServletOutputStream out = this.response.getOutputStream();
		try {
			String captchaId = this.request.getSession(true).getId();
			BufferedImage challenge = (BufferedImage) CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId, this.request.getLocale());
			ImageIO.write(challenge, "jpg", out);
			out.flush();
		} catch (CaptchaServiceException localCaptchaServiceException) {
		} finally {
			out.close();
		}
	}

	protected boolean checkValidImg(String valid) {
		boolean b = false;
		try {
			b = CaptchaServiceSingleton.getInstance().validateResponseForID(this.request.getSession().getId(), valid.toLowerCase()).booleanValue();
		} catch (CaptchaServiceException e) {
			b = false;
		}
		return b;
	}

	protected void message(String msg, String url) {
		String urltext = "";
		if (!StringUtils.isBlank(url)) {
			urltext = "<a href=" + this.request.getContextPath() + url + " >返回上一页</a>";
			this.request.setAttribute("backurl", urltext);
		} else {
			urltext = "<a href='javascript:history.go(-1)'>返回上一页</a>";
		}
		message(msg, url, urltext);
	}

	protected void message(String msg) {
		message(msg, getMsgUrl());
	}

	protected void message(String msg, String url, String text) {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("rsmsg", msg);
		String urltext = "<a href=" + request.getContextPath() + url + " >" + text + "></a>";
		request.setAttribute("backurl", urltext);
	}

	public String blank() throws Exception {
		return SUCCESS;
	}

	public boolean isBlank() {
		return "".equals(getActionType());
	}

	public void saveParam() {
		this.request.setAttribute("param", new SearchParam().toMap());
	}

	public boolean isSession() {
		User sessionUser = getSessionUser();
		return sessionUser != null;
	}

	protected void setMsgUrl(String url) {
		this.request.setAttribute("currentUrl", url);
		String msgurl = (String) this.session.get("msgurl");
		String query = this.request.getQueryString();
		if (!StringUtils.isBlank(query)) {
			url = url + "?" + query;
		}
		msgurl = url;
		this.session.put("msgurl", msgurl);
	}

	protected String getMsgUrl() {
		String msgurl = "";
		Object o = null;
		if ((o = this.session.get("msgurl")) != null) {
			msgurl = (String) o;
		}
		return msgurl;
	}

	protected void setPageAttribute(PageDataList plist, SearchParam param) {
		this.request.setAttribute("page", plist.getPage());
		this.request.setAttribute("list", plist.getList());
		this.request.setAttribute("param", param.toMap());
	}

	protected String upload(File upload, String fileName, String destDir,
			String destFileName) throws Exception {
		
		if (upload == null)
			return "";
		
		logger.info("文件：" + upload);
		logger.info("文件名：" + fileName);
		String destFileUrl = destDir + "/" + destFileName;
		String destfilename = ServletActionContext.getServletContext().getRealPath(destDir) + "/" + destFileName;
		logger.info(destfilename);
		File imageFile = null;
		imageFile = new File(destfilename);
		FileUtils.copyFile(upload, imageFile);
		return destFileUrl;
	}

	protected String generateUploadFilename() {
		User u = getSessionUser();
		String timeStr = DateUtils.dateStr3(new Date());
		if (u == null)
			return timeStr;
		return u.getUser_id() + timeStr;
	}

	protected String generateUploadFilename(String fileName) {
		String suffix = null;
		if (fileName != null) {
			int last = fileName.lastIndexOf('.');
			suffix = fileName.substring(last);
		}
		return generateUploadFilename() + suffix;
	}

	protected String getLogRemark(Borrow b) {
		String s = "对[<a href='" + this.request.getContextPath()
				+ "/invest/detail.html?borrowid=" + b.getId()
				+ "' target=_blank>" + b.getName() + "</a>]";
		return s;
	}

	protected String getRef() {
		String ref = StringUtils.isNull(this.request.getParameter("ref"));
		return ref;
	}

	protected String getAndSaveRef() {
		String ref = getRef();
		this.request.setAttribute("ref", ref);
		return ref;
	}

	protected void printJson(String json) throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	protected void generateDownloadFile(String inFile, String downloadFile)
			throws IOException {
		
		InputStream ins = new BufferedInputStream(new FileInputStream(inFile));
		byte[] buffer = new byte[ins.available()];
		ins.read(buffer);
		ins.close();
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
		response.reset();
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(downloadFile.getBytes()));
		response.addHeader("Content-Length", "" + new File(inFile).length());
		OutputStream ous = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		ous.write(buffer);
		ous.flush();
		ous.close();
	}

	protected int paramInt(String str) {
		return NumberUtils.getInt(this.request.getParameter(str));
	}

	protected long paramLong(String str) {
		return NumberUtils.getLong(this.request.getParameter(str));
	}

	protected String paramString(String str) {
		return StringUtils.isNull(this.request.getParameter(str));
	}

	protected void export(String infile, String downloadFile) throws Exception {
		File inFile = new File(infile);
		InputStream ins = new BufferedInputStream(new FileInputStream(infile));
		byte[] buffer = new byte[ins.available()];
		ins.read(buffer);
		ins.close();
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
		response.reset();
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(downloadFile.getBytes()));
		response.addHeader("Content-Length", "" + inFile.length());
		OutputStream ous = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		ous.write(buffer);
		ous.flush();
		ous.close();
	}

	protected String checkToken(String name) {
		String paramValue = paramString(name);
		String tokenValue = StringUtils.isNull((String) this.session.get(name));

		if ((paramValue.isEmpty()) && (tokenValue.isEmpty())) {
			return "会话Token未设定！";
		}
		if ((paramValue.isEmpty()) && (!tokenValue.isEmpty())) {
			return "表单Token未设定！";
		}
		if ((paramValue.equals(tokenValue)) && (!tokenValue.isEmpty())) {
			this.session.remove(name);
			return "";
		}

		return "请勿重复提交！";
	}

	protected void saveToken(String name) {
		String token = TokenHelper.generateGUID();
		this.session.put(name, token);
	}

	protected void sendMailWithAttachment(User user, long borrow_id,
			long tender_id) throws Exception {
		String to = user.getEmail();
		String attachfile = ServletActionContext.getServletContext()
				.getRealPath("/")
				+ "data/protocol/" + borrow_id + "_" + tender_id + ".pdf";
		MailWithAttachment m = MailWithAttachment.getInstance();
		m.setTo(to);
		m.readProtocolMsg();
		m.replace(user.getUsername(), to, "/member/identify/active.html?id="
				+ m.getdecodeIdStr(user));
		m.attachfile(attachfile);
		m.sendMail();
	}
}