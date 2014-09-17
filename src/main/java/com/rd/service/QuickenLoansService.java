package com.rd.service;

import com.rd.domain.QuickenLoans;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;

public abstract interface QuickenLoansService {
	public abstract PageDataList getList(int paramInt,
			SearchParam paramSearchParam);

	public abstract void addQuickenLoans(QuickenLoans paramQuickenLoans);

	public abstract QuickenLoans getLoansById(int paramInt);

	public abstract void delQuickenLoans(int paramInt);
}