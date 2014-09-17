package com.rd.model.borrow;

import com.rd.context.Constant;

public class StudentBorrowModel extends BaseBorrowModel {
	private static final long serialVersionUID = -965497211520156565L;
	private BorrowModel model;

	public StudentBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.setBorrowType(Constant.TYPE_STUDENT);
		this.model.setIs_student(1);
		this.model.setIs_xin(1);
		init();
	}
}