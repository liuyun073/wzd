package com.rd.quartz.notice;

import com.rd.service.NoticeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class NoticeQuartzJob {
	private Logger logger = Logger.getLogger(NoticeQuartzJob.class);

	@Autowired
	private NoticeService noticeService;

	public NoticeService getNoticeService() {
		return this.noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
}