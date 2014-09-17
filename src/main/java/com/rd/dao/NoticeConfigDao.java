package com.rd.dao;

import com.rd.domain.NoticeConfig;
import java.util.List;

public abstract interface NoticeConfigDao {
	public abstract List<NoticeConfig> getList();

	public abstract int getListCount();

	public abstract void add(NoticeConfig paramNoticeConfig);
}