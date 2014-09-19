package com.liuyun.site.dao;

import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountBank;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.DownLineBank;
import com.liuyun.site.domain.Huikuan;
import com.liuyun.site.domain.OnlineBank;
import com.liuyun.site.domain.PaymentInterface;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserAmount;
import com.liuyun.site.model.HongBaoModel;
import com.liuyun.site.model.HuikuanModel;
import com.liuyun.site.model.Interest;
import com.liuyun.site.model.NewCollection;
import com.liuyun.site.model.Newpay;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserAccountSummary;
import com.liuyun.site.model.account.AccountModel;
import com.liuyun.site.model.account.AccountOneDayModel;
import com.liuyun.site.model.account.AccountReconciliationModel;
import com.liuyun.site.model.account.AccountSumModel;
import com.liuyun.site.model.account.BorrowSummary;
import com.liuyun.site.model.account.CollectSummary;
import com.liuyun.site.model.account.InvestSummary;
import com.liuyun.site.model.account.OnlineBankModel;
import com.liuyun.site.model.account.RepaySummary;
import com.liuyun.site.model.account.TiChengModel;

import java.util.List;

public abstract interface AccountDao {
	public abstract List<AccountLog> getAccountLogSummary(long paramLong);

	public abstract UserAccountSummary getUserAccountSummary(long paramLong);

	public abstract double getBorrowSum(long paramLong);

	public abstract int getBorrowTimes(long paramLong);

	public abstract double getBorrowAmount(long paramLong);

	public abstract UserAmount getUserAmount(long paramLong);

	public abstract AccountModel getAccount(long paramLong);

	public abstract AccountModel getAccount_hongbao(long paramLong);

	public abstract AccountModel getAccountByBankAccount(long paramLong,
			String paramString);

	public abstract List<Interest> getInterest(long paramLong);

	public abstract Newpay getNewpay(long paramLong);

	public abstract NewCollection getNewCollection(long paramLong);

	public abstract List getWaitpayment(long paramLong);

	public abstract User getKf(long paramLong);

	public abstract Account addAccount(Account paramAccount);

	public abstract AccountBank addBank(AccountBank paramAccountBank);

	public abstract int getAccountBankCount(long paramLong);

	public abstract AccountBank updateBank(AccountBank paramAccountBank);

	public abstract void updateAccount(Account paramAccount);

	public abstract void updateAccount(double paramDouble1,
			double paramDouble2, double paramDouble3, long paramLong);

	public abstract void updateAccount(double paramDouble1,
			double paramDouble2, double paramDouble3, long paramLong,
			double paramDouble4, int paramInt);

	public abstract int updateAccountNotZero(double paramDouble1,
			double paramDouble2, double paramDouble3, long paramLong);

	public abstract void updateAccount(double paramDouble1,
			double paramDouble2, double paramDouble3, double paramDouble4,
			long paramLong);

	public abstract List<AccountSumModel> getUserAcount(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<AccountOneDayModel> getUserOneDayAcount(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getUserAccountCount(SearchParam paramSearchParam);

	public abstract int getUserOneDayAccountCount(SearchParam paramSearchParam);

	public abstract int getTiChengAccountCount(SearchParam paramSearchParam);

	public abstract List<TiChengModel> getTiChengAccountList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<TiChengModel> getFriendTiChengAccountList(int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract int getFriendTiChengAccountCount(
			SearchParam paramSearchParam);

	public abstract List<TiChengModel> getFriendTiChengAcount(SearchParam paramSearchParam);

	public abstract List<AccountSumModel> getUserAcount(SearchParam paramSearchParam);

	public abstract double getSum(String paramString, long paramLong);

	public abstract int getCount(String paramString, long paramLong);

	public abstract List<AccountModel> getAccountList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<AccountModel> getAccountList(SearchParam paramSearchParam);

	public abstract int getAccountCount(SearchParam paramSearchParam);

	public abstract void addHuikuanMoney(Huikuan paramHuikuan);

	public abstract List<HuikuanModel> huikuanlist(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<HuikuanModel> huikuanlist(SearchParam paramSearchParam);

	public abstract int gethuikuanCount(SearchParam paramSearchParam);

	public abstract HuikuanModel viewhuikuan(int paramInt);

	public abstract void verifyhuikuan(Huikuan paramHuikuan);

	public abstract double gethuikuanSum(long paramLong, int paramInt);

	public abstract double gethuikuanSum(long paramLong1, int paramInt,
			long paramLong2);

	public abstract HuikuanModel getHuikuanByCashid(long paramLong);

	public abstract double gethuikuanSum(long paramLong);

	public abstract double getFlowMonthTenderCollection(long paramLong);

	public abstract double getFlowDayTenderCollection(long paramLong);

	public abstract RepaySummary getRepaySummary(long paramLong);

	public abstract BorrowSummary getBorrowSummary(long paramLong);

	public abstract InvestSummary getInvestSummary(long paramLong);

	public abstract CollectSummary getCollectSummary(long paramLong);

	public abstract double getCollectionSum(long paramLong, int paramInt);

	public abstract double getCollectionSum(long paramLong1, int paramInt,
			long paramLong2);

	public abstract double getCollectionSumNoJinAndSecond(long paramLong1,
			int paramInt, long paramLong2);

	public abstract double getCollectionSumNoJinAndSecond(long paramLong1,
			int paramInt1, int paramInt2, long paramLong2);

	public abstract List<AccountOneDayModel> getUserOneDayAcount(SearchParam paramSearchParam);

	public abstract List<TiChengModel> getTiChengAcount(SearchParam paramSearchParam);

	public abstract AccountBank updateBankByAccount(
			AccountBank paramAccountBank, String paramString);

	public abstract List<HongBaoModel> getHongBaoList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getHongBaoListCount(SearchParam paramSearchParam);

	public abstract List<HongBaoModel> getHongBaoList(SearchParam paramSearchParam);

	public abstract int getAccountReconciliationListCount(
			SearchParam paramSearchParam);

	public abstract List<AccountReconciliationModel> getAccountReconciliationList(int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract void updateAccount(double paramDouble1,
			double paramDouble2, double paramDouble3, double paramDouble4,
			long paramLong, int paramInt);

	public abstract void addFreeCash(double paramDouble, long paramLong);

	public abstract double getFreeCash(long paramLong);

	public abstract double getFlowDayTenderCollection(long paramLong,
			int paramInt);

	public abstract double getFreeCashSum(long paramLong);

	public abstract DownLineBank addDownRechargeBank(
			DownLineBank paramDownLineBank);

	public abstract DownLineBank updateDownRechargeBank(
			DownLineBank paramDownLineBank);

	public abstract List<AccountBank> getDownRechargeBankList(int paramInt1, int paramInt2);

	public abstract int getDownRechargeBankCount();

	public abstract AccountBank getDownRechargeBank(int paramInt);

	public abstract PaymentInterface addPayInterface(
			PaymentInterface paramPaymentInterface);

	public abstract List<PaymentInterface> getPayInterfaceList(int paramInt);

	public abstract int getPayInterfaceCount();

	public abstract PaymentInterface updatePayInterface(
			PaymentInterface paramPaymentInterface);

	public abstract OnlineBank addOnlineBank(OnlineBank paramOnlineBank);

	public abstract List<OnlineBankModel> getOnlineBankList(long paramLong);

	public abstract int getOnlineBankCount();

	public abstract OnlineBank updateOnlineBank(OnlineBank paramOnlineBank);

	public abstract AccountBank updateBank(AccountBank paramAccountBank,
			int paramInt);

	public abstract OnlineBank getOnlineBank(int paramInt);

	public abstract PaymentInterface getPaymentInterface(int paramInt);
}