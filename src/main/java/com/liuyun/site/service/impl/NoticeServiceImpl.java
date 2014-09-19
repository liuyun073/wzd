package com.liuyun.site.service.impl;

import com.liuyun.site.context.Global;
import com.liuyun.site.dao.MessageDao;
import com.liuyun.site.dao.NoticeDao;
import com.liuyun.site.dao.UserDao;
import com.liuyun.site.domain.Message;
import com.liuyun.site.domain.Notice;
import com.liuyun.site.domain.User;
import com.liuyun.site.service.NoticeService;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.log4j.Logger;

public class NoticeServiceImpl implements NoticeService {
	private Logger logger = Logger.getLogger(NoticeServiceImpl.class);
	private UserDao userDao;
	private NoticeDao noticeDao;
	private MessageDao messageDao;

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public NoticeDao getNoticeDao() {
		return this.noticeDao;
	}

	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	public MessageDao getMessageDao() {
		return this.messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public void sendSms(Notice s) {
		smsModel(s);
	}

	public int sms(String mobile, String content)
			throws UnsupportedEncodingException {
		content = URLEncoder.encode(content, "UTF-8");
		String username = StringUtils.isNull(Global.getValue("sms_username"));
		String password = StringUtils.isNull(Global.getValue("sms_password"));
		String url = "http://smsopen.erongdu.com/sendsms.php?username="
				+ username + "&password=" + password + "&mobile=" + mobile
				+ "&content=" + content;
		this.logger.debug("url:" + url);
		String s = GetPageContent(url);
		this.logger.debug("发送短信结果:" + new String(s.getBytes("UTF-8")));
		this.logger.debug("发送短信结果:" + new String(s.getBytes("GBK")));
		int result = NumberUtils.getInt(StringUtils.isNull(s));
		System.out.println(result);
		return result;
	}

	public String GetPageContent(String pageURL) {
		String pageContent = "";
		BufferedReader in = null;
		InputStreamReader isr = null;
		InputStream is = null;
		HttpURLConnection huc = null;
		try {
			URL url = new URL(pageURL);
			huc = (HttpURLConnection) url.openConnection();
			is = huc.getInputStream();
			isr = new InputStreamReader(is);
			in = new BufferedReader(isr);
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.length() == 0)
					continue;
				pageContent = pageContent + line;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		} finally {
			try {
				is.close();
				isr.close();
				in.close();
				huc.disconnect();
			} catch (Exception localException2) {
			}
		}
		return pageContent;
	}

	public void smsModel(Notice s) {
		User user = this.userDao.getUserById(s.getReceive_userid());
		String mobile = "";
		if ((user == null) || (StringUtils.isBlank(user.getPhone())))
			mobile = StringUtils.isNull(s.getMobile());
		else {
			mobile = user.getPhone();
		}
		String content = StringUtils.isNull(s.getContent());

		this.logger.debug("contentString=========" + content);
		this.logger.debug("mobile=========" + mobile);
		if ((!"".equals(content)) && (!"".equals(content))) {
			int result = -1;
			try {
				result = sms(mobile, content);
			} catch (UnsupportedEncodingException e) {
				this.logger.error(e.getMessage());
			}
			if (result == 1) {
				Notice notice = new Notice();
				notice.setContent(content + "订单短信提醒成功");
				notice.setStatus(1);
				notice.setReceive_userid(s.getReceive_userid());
				notice.setSend_userid(s.getSend_userid());
				notice.setResult("" + result);
				this.noticeDao.add(notice);
			} else {
				Notice notice = new Notice();
				notice.setContent(content + "订单短信提醒失败");
				notice.setStatus(2);
				notice.setReceive_userid(s.getReceive_userid());
				notice.setSend_userid(s.getSend_userid());
				if (result == -1)
					notice.setResult("发送短信结果：" + result + ",即设置手机账号欠费，短信发送失败");
				else {
					notice.setResult("发送短信结果：" + result + ",即参数有误，短信发送失败");
				}

				this.noticeDao.add(notice);
			}
		}
	}

	public void sendMessage(Notice s) {
		Message m = new Message();
		m.setName(s.getTitle());
		m.setReceive_user(s.getReceive_userid());
		m.setSent_user(s.getSend_userid());
		m.setContent(s.warpContent());
		this.messageDao.addMessage(m);
		this.noticeDao.add(s);
	}
}