package com.rd.service;

import com.rd.domain.AutoTenderOrder;
import com.rd.domain.Repayment;
import com.rd.model.DetailCollection;
import com.rd.model.borrow.BorrowModel;
import java.util.List;

public abstract interface AutoBorrowService {
	public abstract void autoVerifyFullSuccess(BorrowModel paramBorrowModel);

	public abstract void autoVerifyFullFail(BorrowModel paramBorrowModel);

	public abstract void autoCancel(BorrowModel paramBorrowModel);

	public abstract void autoDoRepayForSecond(BorrowModel paramBorrowModel);

	public abstract void autoRepay(Repayment paramRepayment);

	public abstract void autoFlowRepay(DetailCollection paramDetailCollection);

	public abstract void batchRepayTimer();

	public abstract void autoDealTender(BorrowModel paramBorrowModel);

	public abstract void autoVerifyFullSuccessForDonation(BorrowModel paramBorrowModel);

	public abstract List<AutoTenderOrder> getAutoTenderOrderList();

	public abstract AutoTenderOrder getAutoTenderOrderByUserid(long paramLong);
}