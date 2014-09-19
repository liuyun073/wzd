package com.liuyun.site.dao;

import com.liuyun.site.domain.BorrowFlow;
import com.liuyun.site.model.borrow.BorrowModel;
import java.util.List;

public abstract interface BorrowFlowDao {
	public abstract void add(BorrowFlow paramBorrowFlow);

	public abstract List<BorrowModel> getBorrowFlowByUserId(long paramLong);
}