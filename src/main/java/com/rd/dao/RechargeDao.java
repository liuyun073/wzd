package com.rd.dao;

import com.rd.domain.AccountRecharge;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface RechargeDao {
	public abstract List<AccountRecharge> getList(long paramLong);

	public abstract int getCount(long paramLong, SearchParam paramSearchParam);

	public abstract List<AccountRecharge> getList(long paramLong, int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<AccountRecharge> getAllList(SearchParam paramSearchParam);

	public abstract void addRecharge(AccountRecharge paramAccountRecharge);

	public abstract AccountRecharge getRechargeByTradeno(String paramString);

	public abstract int updateRecharge(int paramInt, String paramString1,
			String paramString2);

	public abstract int updateRechargeByStatus(int paramInt,
			String paramString1, String paramString2);

	public abstract void updateRechargeFee(double paramDouble, long paramLong);

	public abstract int getAllCount(SearchParam paramSearchParam);

	public abstract List<AccountRecharge> getAllList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract AccountRecharge getRecharge(long paramLong);

	public abstract void updateRecharge(AccountRecharge paramAccountRecharge);

	public abstract double getLastRechargeSum(long paramLong);

	public abstract List<AccountRecharge> getLastOfflineRechargeList(long paramLong);

	public abstract double getLastRechargeSum(long paramLong, int paramInt);

	public abstract double getRechargeSumWithNoAdmin(long paramLong,
			int paramInt);

	public abstract double getLastRechargeSum(long paramLong1, int paramInt,
			long paramLong2);

	public abstract double getAccount_sum(SearchParam paramSearchParam,
			int paramInt);

	public abstract AccountRecharge getMinRecharge(long paramLong,
			String paramString);

	public abstract void updateAccountRechargeYes_no(
			AccountRecharge paramAccountRecharge);

	public abstract double getTodayOnlineRechargeTotal(long paramLong,
			int paramInt);

	public abstract double getTodayRechargeTotal(long paramLong, int paramInt);
}