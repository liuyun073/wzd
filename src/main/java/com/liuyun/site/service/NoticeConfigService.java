package com.liuyun.site.service;

import com.liuyun.site.domain.NoticeConfig;
import com.liuyun.site.model.PageDataList;

public abstract interface NoticeConfigService {
	public abstract PageDataList noticeConfigList(int paramInt);

	public abstract void add(NoticeConfig paramNoticeConfig);
}