package com.rd.dao;

import com.rd.domain.UserAmount;
import com.rd.domain.UserAmountApply;
import com.rd.domain.UserAmountLog;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface UserAmountDao {
	public abstract void add(UserAmountApply paramUserAmountApply);

	public abstract List<UserAmountApply> getUserAmountApply(long paramLong);

	public abstract List<UserAmountApply> getAmountApplyListByUserid(long paramLong,
			int paramInt1, int paramInt2, SearchParam paramSearchParam);

	public abstract int getAmountApplyCountByUserid(long paramLong,
			SearchParam paramSearchParam);

	public abstract List<UserAmountApply> getUserMountApply(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getUserMountApplyCount(SearchParam paramSearchParam);

	public abstract UserAmount getUserAmount(long paramLong);

	public abstract UserAmountApply getUserAmountApplyById(long paramLong);

	public abstract void updateCreditAmount(double paramDouble1,
			double paramDouble2, double paramDouble3, UserAmount paramUserAmount);

	public abstract void updateApply(UserAmountApply paramUserAmountApply);

	public abstract void addAmountLog(UserAmountLog paramUserAmountLog);

	public abstract UserAmount getUserAmountById(long paramLong);

	public abstract void addAmount(UserAmount paramUserAmount);

	public abstract int getUserAmountCount(SearchParam paramSearchParam);

	public abstract List<UserAmount> getUserAmount(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);
}