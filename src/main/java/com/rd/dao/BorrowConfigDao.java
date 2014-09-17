package com.rd.dao;

import java.util.List;

import com.rd.domain.BorrowConfig;

public abstract interface BorrowConfigDao {
	public abstract List<BorrowConfig> getList();
}