package com.rd.service;

import com.rd.domain.Account;
import com.rd.domain.AccountBank;
import com.rd.domain.AccountCash;
import com.rd.domain.AccountLog;
import com.rd.domain.AccountRecharge;
import com.rd.domain.HongBao;
import com.rd.domain.Huikuan;
import com.rd.domain.OperationLog;
import com.rd.domain.PaymentInterface;
import com.rd.domain.Protocol;
import com.rd.domain.User;
import com.rd.domain.UserAmount;
import com.rd.model.DetailTender;
import com.rd.model.HongBaoModel;
import com.rd.model.HuikuanModel;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.model.UserAccountSummary;
import com.rd.model.account.AccountCashList;
import com.rd.model.account.AccountLogList;
import com.rd.model.account.AccountLogModel;
import com.rd.model.account.AccountLogSumModel;
import com.rd.model.account.AccountModel;
import com.rd.model.account.AccountRechargeList;
import com.rd.model.account.OnlineBankModel;
import com.rd.model.account.OperationLogModel;

import java.util.List;

public abstract interface AccountService {
	public abstract UserAccountSummary getUserAccountSummary(long paramLong);

	public abstract UserAmount getUserAmount(long paramLong);

	public abstract AccountModel getAccount(long paramLong);

	public abstract User getKf(long paramLong);

	public abstract List<AccountLogModel> getAccountLogList(long paramLong);

	public abstract AccountLogList getAccountLogList(long paramLong,
			int paramInt, SearchParam paramSearchParam);

	public abstract List<AccountLogModel> getAccountLogList(SearchParam paramSearchParam);

	public abstract List<AccountLogModel> getAccountLogList(long paramLong,
			SearchParam paramSearchParam);

	public abstract double getAccountLogTotalMoney(long paramLong);

	public abstract List<AccountCash> getAccountCashList(long paramLong);

	public abstract int getAccountCashList(long paramLong, int paramInt);

	public abstract AccountCashList getAccountCashList(long paramLong,
			int paramInt, SearchParam paramSearchParam);

	public abstract void cancelCash(long paramLong, AccountLog paramAccountLog);

	public abstract void newCash(AccountCash paramAccountCash,
			Account paramAccount, double paramDouble, boolean paramBoolean);

	public abstract Account addAccount(Account paramAccount);

	public abstract AccountBank addBank(AccountBank paramAccountBank);

	public abstract int getAccountBankCount(long paramLong);

	public abstract AccountBank modifyBank(AccountBank paramAccountBank);

	public abstract List<AccountRecharge> getRechargeList(long paramLong);

	public abstract AccountRechargeList getRechargeList(long paramLong,
			int paramInt, SearchParam paramSearchParam);

	public abstract void addAccountLog(AccountLog paramAccountLog);

	public abstract void modifyAccount(Account paramAccount);

	public abstract void newRecharge(String paramString1, String paramString2,
			AccountLog paramAccountLog);

	public abstract void recharge(String paramString1, String paramString2,
			AccountLog paramAccountLog1, AccountLog paramAccountLog2);

	public abstract void failRecharge(String paramString1, String paramString2,
			AccountLog paramAccountLog);

	public abstract void addRecharge(AccountRecharge paramAccountRecharge);

	public abstract PageDataList getRechargeList(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<AccountRecharge> getRechargeList(SearchParam paramSearchParam);

	public abstract AccountRecharge getRecharge(long paramLong);

	public abstract void verifyRecharge(AccountRecharge paramAccountRecharge,
			AccountLog paramAccountLog, OperationLog paramOperationLog);

	public abstract void verifyRecharge(AccountRecharge paramAccountRecharge,
			AccountLog paramAccountLog, HongBao paramHongBao,
			OperationLog paramOperationLog);

	public abstract PageDataList getAccountLogList(int paramInt,
			SearchParam paramSearchParam);

	public abstract void addRecharge(AccountRecharge paramAccountRecharge,
			AccountLog paramAccountLog);

	public abstract void updateAccount(double paramDouble1,
			double paramDouble2, double paramDouble3, long paramLong);

	public abstract void updateAccount(double paramDouble1,
			double paramDouble2, double paramDouble3, double paramDouble4,
			long paramLong);

	public abstract void newCash(AccountCash paramAccountCash,
			Account paramAccount, double paramDouble, AccountLog paramAccountLog);

	public abstract PageDataList getUserAccountModel(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<AccountModel> getAccountList(SearchParam paramSearchParam);

	public abstract double getRechargesum(SearchParam paramSearchParam,
			int paramInt);

	public abstract double getAwardSum(long paramLong);

	public abstract double getInvestInterestSum(long paramLong);

	public abstract PageDataList getTenderLogList(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<DetailTender> getTenderLogList(SearchParam paramSearchParam);

	public abstract void huikuan(Huikuan paramHuikuan);

	public abstract PageDataList huikuanlist(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<HuikuanModel> huikuanlist(SearchParam paramSearchParam);

	public abstract HuikuanModel viewHuikuan(int paramInt);

	public abstract HuikuanModel verifyHuikuan(HuikuanModel paramHuikuanModel,
			AccountLog paramAccountLog, AccountRecharge paramAccountRecharge);

	public abstract List[] addBatchRecharge(List[] paramArrayOfList);

	public abstract AccountModel getAccountByBankAccount(long paramLong,
			String paramString);

	public abstract AccountBank modifyBankByAccount(
			AccountBank paramAccountBank, String paramString);

	public abstract PageDataList getHongBaoList(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<HongBaoModel> getHongBaoList(SearchParam paramSearchParam);

	public abstract PageDataList getAccountReconciliationList(int paramInt,
			SearchParam paramSearchParam);

	public abstract AccountRecharge getMinRechargeMoney(long paramLong);

	public abstract void updateAccountRechargeYes_no(
			AccountRecharge paramAccountRecharge);

	public abstract void updateAccount(double paramDouble1,
			double paramDouble2, double paramDouble3, double paramDouble4,
			long paramLong, int paramInt);

	public abstract PageDataList operationLog(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<OperationLogModel> operationLog(SearchParam paramSearchParam);

	public abstract void addOperationLog(OperationLog paramOperationLog);

	public abstract void verifyRecharge(AccountRecharge paramAccountRecharge,
			AccountLog paramAccountLog, OperationLog paramOperationLog,
			Protocol paramProtocol);

	public abstract double getAccountLogSum(String paramString);

	public abstract double getAllSumLateMoney(int paramInt);

	public abstract double getRepaymentAccount(int paramInt);

	public abstract List<AccountLogSumModel> getAccountLogSumWithMonth(SearchParam paramSearchParam);

	public abstract double getAllLateSumWithNoRepaid();

	public abstract double getAllLateSumWithYesRepaid();

	public abstract List<OnlineBankModel> onlineBank(long paramLong);

	public abstract List<PaymentInterface> paymentInterface(int paramInt);

	public abstract List<AccountBank> downBank();
}