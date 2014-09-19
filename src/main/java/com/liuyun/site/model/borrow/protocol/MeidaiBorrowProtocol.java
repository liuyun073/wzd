package com.liuyun.site.model.borrow.protocol;

import com.liuyun.site.domain.User;

public class MeidaiBorrowProtocol extends BorrowProtocol {
	public MeidaiBorrowProtocol(User user, long borrow_id, long tender_id) {
		super(user, borrow_id, tender_id);
	}
}