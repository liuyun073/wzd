package com.rd.service;

import com.rd.domain.UserAmount;
import com.rd.domain.UserAmountApply;
import com.rd.domain.UserAmountLog;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface UserAmountService {
	public abstract List<UserAmountApply> getUserAmountApply(long paramLong);

	public abstract void add(UserAmountApply paramUserAmountApply);

	public abstract PageDataList getUserAmountApply(int paramInt,
			SearchParam paramSearchParam);

	public abstract PageDataList getAmountApplyByUserid(long paramLong,
			int paramInt, SearchParam paramSearchParam);

	public abstract UserAmount getUserAmount(long paramLong);

	public abstract UserAmountApply getUserAmountApplyById(long paramLong);

	public abstract void verifyAmountApply(
			UserAmountApply paramUserAmountApply,
			UserAmountLog paramUserAmountLog);

	public abstract PageDataList getUserAmount(int paramInt,
			SearchParam paramSearchParam);
}