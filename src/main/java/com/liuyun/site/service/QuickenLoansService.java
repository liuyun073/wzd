package com.liuyun.site.service;

import com.liuyun.site.domain.QuickenLoans;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;

public abstract interface QuickenLoansService {
	public abstract PageDataList getList(int paramInt,
			SearchParam paramSearchParam);

	public abstract void addQuickenLoans(QuickenLoans paramQuickenLoans);

	public abstract QuickenLoans getLoansById(int paramInt);

	public abstract void delQuickenLoans(int paramInt);
}