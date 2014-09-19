package com.liuyun.site.service;

import com.liuyun.site.domain.AutoTenderOrder;
import com.liuyun.site.domain.Repayment;
import com.liuyun.site.model.DetailCollection;
import com.liuyun.site.model.borrow.BorrowModel;
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