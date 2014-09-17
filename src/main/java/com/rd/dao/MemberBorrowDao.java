package com.rd.dao;

import com.rd.model.SearchParam;
import com.rd.model.UserBorrowModel;

import java.util.List;

public abstract interface MemberBorrowDao {
	public abstract List<UserBorrowModel> getBorrowList(String paramString, long paramLong,
			int paramInt1, int paramInt2, SearchParam paramSearchParam);

	public abstract int getBorrowCount(String paramString, long paramLong,
			SearchParam paramSearchParam);

	public abstract List<UserBorrowModel> getRepamentList(String paramString, long paramLong);
}