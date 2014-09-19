package com.liuyun.site.service;

import com.liuyun.site.domain.CreditType;
import com.liuyun.site.domain.UserCredit;
import com.liuyun.site.domain.UserCreditLog;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
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