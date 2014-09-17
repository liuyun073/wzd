package com.rd.quartz.notice;

import com.rd.domain.Notice;
import com.rd.util.DateUtils;

public class Sms extends Notice {
	public Sms() {
		setName("sms");
		setAddtime(DateUtils.getNowTimeStr());
	}

	public String warpContent() {
		return getContent();
	}
}