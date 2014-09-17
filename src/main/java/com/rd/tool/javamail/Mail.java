package com.rd.tool.javamail;

import com.rd.context.Global;
import com.rd.domain.User;
import com.rd.model.SystemInfo;
import com.rd.tool.coder.BASE64Encoder;
import com.rd.tool.coder.MD5;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import org.apache.log4j.Logger;

public class Mail {
	private static Logger logger = Logger.getLogger(Mail.class);
	private String host;
	private String from;
	private String to;
	private String subject;
	private String body;
	private EmailAutherticator auth;
	private static Mail mail = new Mail();

	private Mail() {
		init();
	}

	private void init() {
		SystemInfo info = Global.SYSTEMINFO;
		EmailAutherticator auth = new EmailAutherticator(info
				.getValue("email_email"), info.getValue("email_pwd"));
		this.host = info.getValue("email_host");
		this.from = auth.getUsername();
		logger.info("From：" + this.from);
		this.subject = info.getValue("email_from_name");
		logger.info("数据库读取：" + this.subject);
		setAuth(auth);
		setHost(this.host);
		setSubject(this.subject);
		setFrom(this.from);
	}

	public static Mail getInstance() {
		Mail mail = new Mail();
		SystemInfo info = Global.SYSTEMINFO;
		EmailAutherticator auth = new EmailAutherticator(info
				.getValue("email_email"), info.getValue("email_pwd"));
		String host = info.getValue("email_host");
		String from = auth.getUsername();
		String subject = info.getValue("email_from_name");
		mail.setAuth(auth);
		mail.setHost(host);
		mail.setSubject(subject);
		mail.setFrom(from);
		return mail;
	}

	public String transferChinese(String strText) {
		try {
			strText = MimeUtility.encodeText(new String(strText.getBytes(),
					"utf-8"), "utf-8", "B");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}

	public void sendMail() throws Exception {
		Properties props = new Properties();

		props.put("mail.smtp.host", this.host);
		props.put("mail.smtp.auth", "true");
		System.out.println(props);
		Session session = Session.getDefaultInstance(props, this.auth);

		MimeMessage message = new MimeMessage(session);
		message.setContent("Hello", "text/plain");
		logger.info(this.subject);

		message.setSubject(this.subject, "utf-8");
		message.setSentDate(new Date());
		Address address = new InternetAddress(this.from, this.subject, "utf-8");

		message.setFrom(address);
		Address toaddress = new InternetAddress(this.to);
		message.addRecipient(Message.RecipientType.TO, toaddress);

		Multipart mainPart = new MimeMultipart();
		BodyPart html = new MimeBodyPart();
		html.setContent(this.body, "text/html; charset=utf-8");
		mainPart.addBodyPart(html);

		message.setContent(mainPart);
		logger.debug(message);
		logger.debug("TO:" + this.to);
		try {
			Transport.send(message);
		} catch (Exception e) {
			logger.error("Send Email founds error!");
			e.printStackTrace();
		}
		logger.debug("Send Mail Ok!");
	}

	public void sendMail(String to, String subject, String content)
			throws Exception {
		setTo(to);
		setSubject(subject);
		setBody(content);
		sendMail();
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public EmailAutherticator getAuth() {
		return this.auth;
	}

	public void setAuth(EmailAutherticator auth) {
		this.auth = auth;
	}

	public void readActiveMailMsg() {
		readMsg("mail.msg");
	}

	public void readGetpwdMailMsg() {
		readMsg("getpwd.msg");
	}

	public void readPayBorrowForBorrowerMsg() {
		readMsg("emailpayborrowforborrower.msg");
	}

	public void readOverdueReceivablesMsg() {
		readMsg("EmailOverdueReceivables.msg");
	}

	private void readMsg(String filename) {
		StringBuffer sb = new StringBuffer();
		InputStream fis = Mail.class.getResourceAsStream(filename);
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(fis, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("读取文件遇见不正确的文件编码！");
		}
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		try {
			while ((line = br.readLine()) != null)
				sb.append(line);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		String msg = sb.toString();
		logger.info(msg);
		setBody(msg);
	}

	public String getdecodeIdStr(User user) {
		String chars = "0123456789qwertyuiopasdfghjklmnbvcxz-=~!@#$%^*+-._/*<>|";
		int length = chars.length();
		StringBuffer url = new StringBuffer();
		StringBuffer rancode = new StringBuffer();
		String timeStr = "" + System.currentTimeMillis() / 1000L;
		String userIDAddtime = user.getUser_id() + user.getAddtime();
		MD5 md5 = new MD5();
		userIDAddtime = md5.getMD5ofStr(userIDAddtime);
		url.append(user.getUser_id()).append(",").append(userIDAddtime).append(
				",").append(timeStr).append(",");
		for (int i = 0; i < 10; ++i) {
			int num = (int) (Math.random() * (length - 2)) + 1;
			rancode.append(chars.charAt(num));
		}
		url.append(rancode);
		String idurl = url.toString();
		BASE64Encoder encoder = new BASE64Encoder();
		String s = encoder.encode(idurl.getBytes());
		return s;
	}

	public void replace(String webname, String host, String username,
			String email, String url) {
		String msg = getBody();
		msg = msg.replace("$webname$", webname).replace("$email$", email)
				.replace("$host$", host).replace("$username$", username)
				.replace("$url$", host + url);
		setBody(msg);
	}

	public void replace(String username, String email, String url) {
		SystemInfo info = Global.SYSTEMINFO;
		String weburl = info.getValue("weburl");
		String webname = info.getValue("webname");
		replace(webname, weburl, username, email, url);
	}

	public String bodyReplace(Map<String, Object> map) {
		SystemInfo info = Global.SYSTEMINFO;
		String weburl = info.getValue("weburl");
		String webname = info.getValue("webname");
		String fuwutel = info.getValue("fuwutel");

		String username = (map.get("username") != null) ? (String) map.get("username") : "";
		String no = (map.get("no") != null) ? (String) map.get("no") : "";
		if (no != "") {
			no = no.substring(0, no.length() - 4) + "****";
		}

		String status = (map.get("status") != null) ? (String) map.get("status") : "";
		String type = (map.get("type") != null) ? (String) map.get("type") : "";

		String money = (map.get("money") != null) ? (String) map.get("money") : "";
		String time = (map.get("time") != null) ? (String) map.get("time") : "";
		String m = (map.get("msg") != null) ? (String) map.get("msg") : "";
		String fee = (map.get("fee") != null) ? (String) map.get("fee") : "";

		String borrowname = (map.get("borrowname") != null) ? (String) map.get("borrowname") : "";
		String account = (map.get("account") != null) ? (String) map.get("account") : "";
		String apr = (map.get("apr") != null) ? (String) map.get("apr") : "";

		String timelimit = (map.get("timelimit") != null) ? (String) map.get("timelimit") : "";

		String order = (map.get("order") != null) ? (String) map.get("order") : "";

		String latedays = (map.get("latedays") != null) ? (String) map.get("latedays") : "";

		String lateInterest = (map.get("lateInterest") != null) ? (String) map.get("lateInterest") : "";
		String msg = getBody();
		msg = msg.replace("$webname$", webname).replace("$host$", weburl)
				.replace("$username$", username).replace("$NO$", no).replace(
						"$status$", status).replace("$type$", type).replace(
						"$money$", money).replace("$time$", time).replace(
						"$msg$", m).replace("$fee$", fee).replace("$fuwutel$",
						fuwutel).replace("$borrowname$", borrowname).replace(
						"$account$", account).replace("$apr$", apr).replace(
						"$timelimit$", timelimit).replace("$order$", order)
				.replace("$latedays$", latedays).replace("$lateInterest$",
						lateInterest);

		return msg;
	}

	public static void main(String[] args) {
		Mail m = getInstance();
		User u = new User();
		u.setUser_id(2675L);
		u.setEmail("welkinrook@163.com");
		m.readActiveMailMsg();
		m.replace("温州贷", "http://localhost", "fuljia", u.getEmail(),
				"http://localhost?id=");
		try {
			m.sendMail();
		} catch (Exception localException) {
		}
	}
}