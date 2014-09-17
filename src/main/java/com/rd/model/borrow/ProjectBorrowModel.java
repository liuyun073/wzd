package com.rd.model.borrow;

import com.rd.context.Constant;

public class ProjectBorrowModel extends BaseBorrowModel {
	private static final long serialVersionUID = -965497211520156565L;
	private BorrowModel model;

	public ProjectBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.setBorrowType(Constant.TYPE_PROJECT);
		this.model.setIs_project(1);
		init();
	}
}