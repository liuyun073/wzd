package com.liuyun.site.dao;

import com.liuyun.site.domain.QuickenLoans;
import com.liuyun.site.model.SearchParam;
import java.util.List;

public abstract interface QuickenLoansDao {
	public abstract List<QuickenLoans> getList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getSearchCard(SearchParam paramSearchParam);

	public abstract void addQuickenLoans(QuickenLoans paramQuickenLoans);

	public abstract QuickenLoans getLoansById(int paramInt);

	public abstract void delQuickenLoans(int paramInt);
}