package com.liuyun.site.service;

import com.liuyun.site.domain.AccountBank;
import com.liuyun.site.domain.AccountCash;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.Advanced;
import com.liuyun.site.domain.OnlineBank;
import com.liuyun.site.domain.OperationLog;
import com.liuyun.site.domain.PaymentInterface;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.account.AccountSumModel;

import java.util.List;

public abstract interface ManageCashService {
	public abstract PageDataList getUserAccount(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<AccountSumModel> getUserAccount(SearchParam paramSearchParam);

	public abstract PageDataList getAllCash(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<AccountCash> getAllCash(SearchParam paramSearchParam);

	public abstract AccountCash getAccountCash(long paramLong);

	public abstract void verifyCash(AccountCash paramAccountCash,
			AccountLog paramAccountLog, OperationLog paramOperationLog);

	public abstract PageDataList getUserOneDayAcount(int paramInt,
			SearchParam paramSearchParam);

	public abstract List getUserOneDayAcount(SearchParam paramSearchParam);

	public abstract PageDataList getTiChengAcount(int paramInt,
			SearchParam paramSearchParam);

	public abstract void bankLog(OperationLog paramOperationLog);

	public abstract PageDataList getFriendTiChengAcount(int paramInt,
			SearchParam paramSearchParam);

	public abstract List getFriendTiChengAcount(SearchParam paramSearchParam);

	public abstract List getTiChengAcount(SearchParam paramSearchParam);

	public abstract double getSumTotal();

	public abstract double getSumUseMoney();

	public abstract double getSumNoUseMoney();

	public abstract double getSumCollection();

	public abstract void getAdvanced_insert(Advanced paramAdvanced);

	public abstract void getAdvanced_update(Advanced paramAdvanced);

	public abstract List getAdvancedList();

	public abstract void rechargeDownLineBank(AccountBank paramAccountBank,
			String paramString);

	public abstract PageDataList getList(int paramInt);

	public abstract AccountBank getDownLineBank(int paramInt);

	public abstract void addPayInterface(PaymentInterface paramPaymentInterface);

	public abstract List getPayInterfaceList(int paramInt);

	public abstract PaymentInterface updatePayInterface(
			PaymentInterface paramPaymentInterface);

	public abstract OnlineBank addOnlineBank(OnlineBank paramOnlineBank);

	public abstract PageDataList getOnlineBankList(int paramInt);

	public abstract OnlineBank updateOnlineBank(OnlineBank paramOnlineBank);

	public abstract OnlineBank getOnlineBank(int paramInt);

	public abstract void OnLineBank(OnlineBank paramOnlineBank,
			String paramString);

	public abstract PaymentInterface getPayInterface(int paramInt);

	public abstract void PaymemtInterface(
			PaymentInterface paramPaymentInterface, String paramString);
}