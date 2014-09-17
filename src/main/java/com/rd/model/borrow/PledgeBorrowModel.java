package com.rd.model.borrow;

import com.rd.context.Constant;

public class PledgeBorrowModel extends BaseBorrowModel {
	private static final long serialVersionUID = -965497211520156565L;
	private BorrowModel model;

	public PledgeBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.setBorrowType(Constant.TYPE_MORTGAGE);
		this.model.setIs_fast(1);
		this.model.setIs_pledge(1);
		init();
	}
}