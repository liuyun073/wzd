package com.liuyun.site.dao;

import java.util.List;
import java.util.Map;

public abstract interface LateBorrowLogDao {
	public abstract boolean addLateBorrowLogDetail(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> queryLateBorrowDetails(
			String paramString);
}