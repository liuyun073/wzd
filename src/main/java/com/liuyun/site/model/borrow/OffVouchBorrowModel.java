package com.liuyun.site.model.borrow;

import com.liuyun.site.context.Constant;

public class OffVouchBorrowModel extends BaseBorrowModel {
	private static final long serialVersionUID = -965497211520156565L;
	private BorrowModel model;

	public OffVouchBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.setBorrowType(Constant.TYPE_OFFVOUCH);
		this.model.setIs_offvouch(1);
		init();
	}
}