package com.liuyun.site.service;

import com.liuyun.site.domain.Notice;

public abstract interface NoticeService {
	public abstract void sendSms(Notice paramNotice);

	public abstract void sendMessage(Notice paramNotice);
}