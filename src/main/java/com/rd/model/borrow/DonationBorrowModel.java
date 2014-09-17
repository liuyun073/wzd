package com.rd.model.borrow;

import com.rd.context.Constant;

public class DonationBorrowModel extends BaseBorrowModel {
	private static final long serialVersionUID = -8364474289482249293L;
	private BorrowModel model;

	public DonationBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.setBorrowType(Constant.TYPE_DONATION);
		this.model.setIs_donation(1);
		init();
	}
}