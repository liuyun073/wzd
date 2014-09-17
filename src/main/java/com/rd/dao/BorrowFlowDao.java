package com.rd.dao;

import com.rd.domain.BorrowFlow;
import com.rd.model.borrow.BorrowModel;
import java.util.List;

public abstract interface BorrowFlowDao {
	public abstract void add(BorrowFlow paramBorrowFlow);

	public abstract List<BorrowModel> getBorrowFlowByUserId(long paramLong);
}