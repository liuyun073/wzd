package com.liuyun.site.model.borrow;

import com.liuyun.site.context.Constant;

public class PropertyBorrowModel extends BaseBorrowModel {
	private BorrowModel model;
	private static final long serialVersionUID = -1490035608742973452L;

	public PropertyBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.borrowType = Constant.TYPE_PROPERTY;
		this.model.setIs_jin(1);
		init();
	}
}