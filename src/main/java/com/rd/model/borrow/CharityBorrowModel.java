package com.rd.model.borrow;

import com.rd.context.Constant;

public class CharityBorrowModel extends MortgageBorrowModel {
	private static final long serialVersionUID = -965497211520156565L;
	private BorrowModel model;

	public CharityBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.setBorrowType(Constant.TYPE_CHARITY);
		this.model.setIs_charity(1);
		init();
	}
}