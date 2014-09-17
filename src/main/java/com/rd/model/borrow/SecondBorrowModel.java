package com.rd.model.borrow;

import com.rd.context.Constant;

public class SecondBorrowModel extends BaseBorrowModel {
	private static final long serialVersionUID = 7375703874958748525L;
	private BorrowModel model;

	public SecondBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.setIs_mb(1);
		this.model.setBorrowType(Constant.TYPE_SECOND);
		this.model.setNeedBorrowFee(false);
		this.model.setBorrow_fee(0.005D);
		init();
	}
}