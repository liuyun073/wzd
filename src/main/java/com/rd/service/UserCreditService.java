package com.rd.service;

import com.rd.domain.CreditType;
import com.rd.domain.UserCredit;
import com.rd.domain.UserCreditLog;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface UserCreditService {
	public abstract PageDataList getUserCreditPage(int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract Boolean updateUserCredit(long paramLong, int paramInt,
			byte paramByte, String paramString);

	public abstract UserCredit getUserCreditByUserId(long paramLong);

	public abstract int getCreditValueByUserId(long paramLong, Byte paramByte);

	public abstract PageDataList getCreditLogPage(int paramInt,
			SearchParam paramSearchParam, long paramLong1, long paramLong2);

	public abstract List<CreditType> getCreditTypeByUserId(long paramLong);

	public abstract List<CreditType> getCreditTypeAll();

	public abstract CreditType getCreditTypeByNid(String paramString);

	public abstract List<UserCreditLog> getCreditLogList(long paramLong1, long paramLong2);
}