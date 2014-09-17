package com.rd.model.borrow;

import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.util.NumberUtils;

public class MortgageBorrowModel extends BaseBorrowModel {
	private static final long serialVersionUID = -965497211520156565L;
	private BorrowModel model;

	public MortgageBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.setBorrowType(Constant.TYPE_MORTGAGE);
		this.model.setIs_fast(1);
		init();
	}

	public double getTransactionFee() {
		return NumberUtils.getDouble(this.model.getAccount()) * Global.getDouble(Constant.TRANSACTION_FEE);
	}
}