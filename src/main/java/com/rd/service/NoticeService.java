package com.rd.service;

import com.rd.domain.Notice;

public abstract interface NoticeService {
	public abstract void sendSms(Notice paramNotice);

	public abstract void sendMessage(Notice paramNotice);
}