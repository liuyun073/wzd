package com.liuyun.site.quartz.notice;

import com.liuyun.site.domain.Notice;
import com.liuyun.site.util.DateUtils;

public class Sms extends Notice {
	public Sms() {
		setName("sms");
		setAddtime(DateUtils.getNowTimeStr());
	}

	public String warpContent() {
		return getContent();
	}
}