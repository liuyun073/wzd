package com.rd.model.borrow.protocol;

import com.rd.domain.User;

public class MeidaiBorrowProtocol extends BorrowProtocol {
	public MeidaiBorrowProtocol(User user, long borrow_id, long tender_id) {
		super(user, borrow_id, tender_id);
	}
}