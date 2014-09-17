package com.rd.dao;

import com.rd.domain.Notice;
import com.rd.domain.NoticeConfig;

import java.util.List;

public abstract interface NoticeDao {
	public abstract List<NoticeConfig> getList();

	public abstract int getListCount();

	public abstract void add(Notice paramNotice);
}