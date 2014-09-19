package com.liuyun.site.quartz.notice;

import com.liuyun.site.domain.Notice;
import com.liuyun.site.service.NoticeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class SmsTask extends AbstractLoanTask {
	private Logger logger = Logger.getLogger(SmsTask.class);

	@Autowired
	private NoticeService noticeService;

	public NoticeService getNoticeService() {
		return this.noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public SmsTask(NoticeService noticeService) {
		this.task.setName("SmsTask");
		this.noticeService = noticeService;
	}

	public void doLoan() {
		this.logger.debug("SmsTask start");
		while (NoticeJobQueue.NOTICE.size() > 0) {
			Notice s = (Notice) NoticeJobQueue.NOTICE.peek();
			if (s != null) {
				Notice sms = s;
				try {
					this.noticeService.sendSms(sms);
				} catch (Exception e) {
					this.logger.error(e.getMessage());
					e.printStackTrace();
				} finally {
					NoticeJobQueue.NOTICE.remove(s);
				}
			}
		}
	}

	public Object getLock() {
		return "false";
	}
}