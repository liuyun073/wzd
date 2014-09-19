package com.liuyun.site.model.borrow;

import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.util.NumberUtils;

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