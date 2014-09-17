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
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import org.apache.log4j.Logger;

public class MailWithAttachment {
	private static Logger logger = Logger.getLogger(MailWithAttachment.class);

	String to = "";
	String from = "";
	String host = "";
	String username = "";
	String password = "";
	String filename = "";
	String subject = "";
	String content = "";
	Vector<String> file = new Vector<String>();

	public MailWithAttachment() {
	}

	public MailWithAttachment(String to, String from, String smtpServer,
			String username, String password, String subject, String content) {
		this.to = to;
		this.from = from;
		this.host = smtpServer;
		this.username = username;
		this.password = password;
		this.subject = subject;
		this.content = content;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getContent() {
		return this.content;
	}

	public void setPassWord(String pwd) {
		this.password = pwd;
	}

	public void setUserName(String usn) {
		this.username = usn;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
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

	public void attachfile(String fname) {
		this.file.addElement(fname);
	}

	public static MailWithAttachment getInstance() {
		MailWithAttachment mail = new MailWithAttachment();
		String host = Global.getValue("email_host");
		String from = Global.getValue("email_from");
		String username = Global.getValue("email_email");
		String password = Global.getValue("email_pwd");
		String subject = Global.getValue("email_from_name");
		mail.setHost(host);
		mail.setFrom(from);
		mail.setUserName(username);
		mail.setPassWord(password);
		mail.setSubject(subject);
		return mail;
	}

	public void readProtocolMsg() {
		readMsg("protocol.msg");
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
			while ((line = br.readLine()) != null){
				sb.append(line);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		String msg = sb.toString();
		logger.info(msg);
		setContent(msg);
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
		url.append(user.getUser_id()).append(",").append(userIDAddtime).append(",").append(timeStr).append(",");
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
		String msg = getContent();
		msg = msg.replace("$webname$", webname).replace("$email$", email).replace("$host$", host).replace("$username$", username).replace("$url$", host + url);
		setContent(msg);
	}

	public void replace(String username, String email, String url) {
		SystemInfo info = Global.SYSTEMINFO;
		String weburl = info.getValue("weburl");
		String webname = info.getValue("webname");
		replace(webname, weburl, username, email, url);
	}

	public void sendMail(String to, String content) throws Exception {
		setTo(to);
		setContent(content);
		sendMail();
	}

	public boolean sendMail() {
		Properties props = new Properties();
		props.put("mail.smtp.host", this.host);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								MailWithAttachment.this.username,
								MailWithAttachment.this.password);
					}

				});
		try {
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(this.from));

			msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(this.to));
			this.subject = transferChinese(this.subject);
			msg.setSubject(this.subject);

			Multipart mp = new MimeMultipart();

			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setContent(this.content, "text/html;charset=gb2312");

			mp.addBodyPart(mbpContent);

			Enumeration<?> efile = this.file.elements();
			while (efile.hasMoreElements()) {
				MimeBodyPart mbpFile = new MimeBodyPart();
				this.filename = efile.nextElement().toString();
				FileDataSource fds = new FileDataSource(this.filename);
				mbpFile.setDataHandler(new DataHandler(fds));

				String filename = new String(fds.getName().getBytes(), "ISO-8859-1");

				mbpFile.setFileName(filename);

				mp.addBodyPart(mbpFile);
			}

			this.file.removeAllElements();

			msg.setContent(mp);
			msg.setSentDate(new Date());
			msg.saveChanges();

			Transport transport = session.getTransport("smtp");
			transport.connect(this.host, this.username, this.password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (Exception mex) {
			mex.printStackTrace();

			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		MailWithAttachment sendmail = new MailWithAttachment();

		sendmail.setHost("smtp.qq.com");
		sendmail.setUserName("1353216244@qq.com");
		sendmail.setPassWord("kevin@407437254");
		sendmail.setTo("407437254@qq.com");
		sendmail.setFrom("1353216244@qq.com");
		sendmail.setSubject("你好，这是测试！");
		sendmail.setContent("你好这是一个带多附件的测试！");

		sendmail.attachfile("d:\\a.txt");

		System.out.println(sendmail.sendMail());
	}
}