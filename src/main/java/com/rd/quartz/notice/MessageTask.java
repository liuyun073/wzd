package com.rd.quartz.notice;

import com.rd.domain.Notice;
import com.rd.service.NoticeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageTask extends AbstractLoanTask {
	private Logger logger = Logger.getLogger(MessageTask.class);

	@Autowired
	private NoticeService noticeService;

	public NoticeService getNoticeService() {
		return this.noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public MessageTask(NoticeService noticeService) {
		this.task.setName("MessageTask");
		this.noticeService = noticeService;
	}

	public void doLoan() {
		this.logger.debug("MessageTask start");
		while (NoticeJobQueue.MESSAGE.size() > 0) {
			Notice s = (Notice) NoticeJobQueue.MESSAGE.peek();
			if (s != null) {
				Notice sms = s;
				try {
					this.noticeService.sendMessage(sms);
				} catch (Exception e) {
					this.logger.error(e.getMessage());
					e.printStackTrace();
				} finally {
					NoticeJobQueue.MESSAGE.remove(s);
				}
			}
		}
	}

	public Object getLock() {
		return "false";
	}
}