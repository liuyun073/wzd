package com.liuyun.site.dao;

import com.liuyun.site.domain.Notice;
import com.liuyun.site.domain.NoticeConfig;

import java.util.List;

public abstract interface NoticeDao {
	public abstract List<NoticeConfig> getList();

	public abstract int getListCount();

	public abstract void add(Notice paramNotice);
}