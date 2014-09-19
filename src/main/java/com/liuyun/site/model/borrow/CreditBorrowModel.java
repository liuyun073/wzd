package com.liuyun.site.model.borrow;

import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.util.NumberUtils;

public class CreditBorrowModel extends BaseBorrowModel {
	private static final long serialVersionUID = 6478298326297026207L;
	private BorrowModel model;

	public CreditBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.setBorrowType(Constant.TYPE_CREDIT);
		init();
	}

	public double getTransactionFee() {
		return NumberUtils.getDouble(this.model.getAccount()) * Global.getDouble(Constant.TRANSACTION_FEE);
	}
}