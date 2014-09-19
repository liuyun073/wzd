package com.liuyun.site.dao;

import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.model.DetailTender;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.account.AccountLogModel;
import com.liuyun.site.model.account.AccountLogSumModel;

import java.util.List;

public abstract interface AccountLogDao {
	public abstract void addAccountLog(AccountLog paramAccountLog);

	public abstract double getAccountLogTotalMoney(long paramLong);

	public abstract double getAccountLogInterestTotalMoney(long paramLong);

	public abstract int getAccountLogCount(long paramLong,
			SearchParam paramSearchParam);

	public abstract List<AccountLogModel> getAccountLogList(long paramLong, int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract List<AccountLogModel> getAccountLogList(long paramLong);

	public abstract List<AccountLogModel> getAccountLogList(long paramLong,
			SearchParam paramSearchParam);

	public abstract int getAccountLogCount(SearchParam paramSearchParam);

	public abstract List<AccountLogModel> getAccountLogList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<AccountLogModel> getAccountLogList(SearchParam paramSearchParam);

	public abstract int getTenderLogCount(SearchParam paramSearchParam);

	public abstract List<DetailTender> getTenderLogList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<DetailTender> getTenderLogList(SearchParam paramSearchParam);

	public abstract double getAwardSum(long paramLong);

	public abstract double getAwardSum(long paramLong1, long paramLong2);

	public abstract double getInvestInterestSum(long paramLong);

	public abstract double getrepayInterestSum(long paramLong);

	public abstract double getAccountLogSum(String paramString);

	public abstract List<AccountLogSumModel> getAccountLogSumWithMonth(SearchParam paramSearchParam);

	public abstract int getAccountLogSumCount();
}