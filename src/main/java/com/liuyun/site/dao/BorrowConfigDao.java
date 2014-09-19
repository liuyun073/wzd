package com.liuyun.site.dao;

import java.util.List;

import com.liuyun.site.domain.BorrowConfig;

public abstract interface BorrowConfigDao {
	public abstract List<BorrowConfig> getList();
}