package com.liuyun.site.dao;

import com.liuyun.site.domain.CreditType;
import com.liuyun.site.domain.UserCredit;
import com.liuyun.site.domain.UserCreditLog;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserCreditLogModel;
import com.liuyun.site.model.UserCreditModel;
import java.util.List;

public abstract interface UserCreditDao {
	public abstract void addUserCredit(UserCredit paramUserCredit);

	public abstract List<UserCreditModel> getUserCreditByPageNumber(int paramInt1, int paramInt2);

	public abstract void updateUserCredit(UserCredit paramUserCredit);

	public abstract void updateCreditTenderValue(UserCredit paramUserCredit);

	public abstract UserCredit getUserCreditByUserId(long paramLong);

	public abstract int getCreditValueByUserId(long paramLong);

	public abstract int getUserCreditCount(SearchParam paramSearchParam);

	public abstract List<UserCreditModel> getUserCredit(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract void addUserCreditLog(UserCreditLog paramUserCreditLog);

	public abstract int getCreditLogCount(SearchParam paramSearchParam);

	public abstract List<UserCreditLogModel> getCreditLog(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract UserCreditLogModel getCreditLogByUserId(long paramLong);

	public abstract UserCreditModel getCreditModelById(long paramLong);

	public abstract CreditType getCreditTypeByNid(String paramString);

	public abstract List<CreditType> getCreditTypeList(String paramString);

	public abstract void editCreditValue(long paramLong, int paramInt);

	public abstract void editTenderValue(long paramLong, int paramInt);

	public abstract void editBorrowValue(long paramLong, int paramInt);

	public abstract void editGiftValue(long paramLong, int paramInt);

	public abstract void editExpenseValue(long paramLong, int paramInt);

	public abstract List<CreditType> getCreditTypeByUserId(long paramLong);

	public abstract int getUserCreditLogCount(SearchParam paramSearchParam,
			long paramLong1, long paramLong2);

	public abstract List<UserCreditLogModel> getCreditLogPage(int paramInt1, int paramInt2,
			SearchParam paramSearchParam, long paramLong1, long paramLong2);

	public abstract List<CreditType> getCreditTypeAll();

	public abstract List<UserCreditLog> getCreditLogList(long paramLong1, long paramLong2);

	public abstract int getValidValueByUserId(long paramLong);
}