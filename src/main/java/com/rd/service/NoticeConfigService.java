package com.rd.service;

import com.rd.domain.NoticeConfig;
import com.rd.model.PageDataList;

public abstract interface NoticeConfigService {
	public abstract PageDataList noticeConfigList(int paramInt);

	public abstract void add(NoticeConfig paramNoticeConfig);
}