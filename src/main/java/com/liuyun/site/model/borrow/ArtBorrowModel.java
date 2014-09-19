package com.liuyun.site.model.borrow;

import com.liuyun.site.context.Constant;

public class ArtBorrowModel extends MortgageBorrowModel {
	private static final long serialVersionUID = -965497211520156565L;
	private BorrowModel model;

	public ArtBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.setBorrowType(Constant.TYPE_ART);
		this.model.setIs_art(1);
		init();
	}
}