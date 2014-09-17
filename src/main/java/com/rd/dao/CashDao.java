package com.rd.dao;

import com.rd.domain.AccountCash;
import com.rd.domain.Advanced;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface CashDao {
	public abstract List<AccountCash> getAccountCashList(long paramLong);

	public abstract int getAccountCashCount(long paramLong,
			SearchParam paramSearchParam);

	public abstract List<AccountCash> getAccountCashList(long paramLong, int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract AccountCash addCash(AccountCash paramAccountCash);

	public abstract int getAllCashCount(SearchParam paramSearchParam);

	public abstract List<AccountCash> getAllCashList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<AccountCash> getAllCashList(SearchParam paramSearchParam);

	public abstract AccountCash getAccountCash(long paramLong);

	public abstract void updateCash(AccountCash paramAccountCash);

	public abstract int getAccountCashNum(long paramLong, int paramInt);

	public abstract List<AccountCash> getAccountCashList(long paramLong, int paramInt);

	public abstract List<AccountCash> getAccountCashList(long paramLong1, int paramInt,
			long paramLong2);

	public abstract double getAccountCashSum(long paramLong, int paramInt);

	public abstract double getAccountCashSum(long paramLong1, int paramInt,
			long paramLong2);

	public abstract double getAccountCashSum(long paramLong1, int paramInt,
			long paramLong2, long paramLong3);

	public abstract double getSumTotal();

	public abstract double getSumUseMoney();

	public abstract double getSumNoUseMoney();

	public abstract double getSumCollection();

	public abstract void getAdvance_insert(Advanced paramAdvanced);

	public abstract List<Advanced> getAdvanceList();

	public abstract void getAdvance_update(Advanced paramAdvanced);

	public abstract double getAccountNoCashSum(long paramLong1, int paramInt,
			long paramLong2);

	public abstract AccountCash addFreeCash(AccountCash paramAccountCash);
}