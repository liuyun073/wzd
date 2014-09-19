package com.liuyun.site.model;

public class UserAccountSummary {
	private long user_id;
	private AccountLogSummary logSummary;
	private double accountTotal;
	private double accountUseMoney;
	private double accountNoUseMoney;
	private double accountOwnMoney;
	private double borrowNoUseTotal;
	private double investTotal;
	private double investInterest;
	private double lendTotal;
	private double awardTotal;
	private double yes_interest;
	private double max_cash;
	private double hongbao;
	private double collect;
	private double huikuanSum;
	private double free_cash;
	private double collectTotal;
	private double collectInterest;
	private double lateTotal;
	private double lateInterest;
	private double overdueInterest;
	private double advanceTotal;
	private double lossInterest;
	private String newestCollectDate;
	private double newestCollectMoney;
	private double borrowTotal;
	private double borrowInterest;
	private int borrowTimes;
	private double repayTotal;
	private double repayInterest;
	private double repaidTotal;
	private double notRepayTotal;
	private int investTimes;
	private int repayTimes;
	private int waitRepayTimes;
	private String newestRepayDate;
	private double newestRepayMoney;
	private double rechargeTotal;
	private double cashTotal;
	private double onlineRechargeTotal;
	private double todayOnlineRechargeTotal;
	private double offlineRechargeTotal;
	private double manualRechargeTotal;
	private double feeTotal;
	private double rechargeFee;
	private double cashFee;
	private double cashCredited;
	private double mostAmount;
	private double leastAmount;
	private double useAmount;
	private int waitRepayCount;
	private int hadRepayCount;
	private int hadDueRepayCount;
	private int waitDueRepayCount;

	public double getCollect() {
		return this.collect;
	}

	public void setCollect(double collect) {
		this.collect = collect;
	}

	public double getHuikuanSum() {
		return this.huikuanSum;
	}

	public void setHuikuanSum(double huikuanSum) {
		this.huikuanSum = huikuanSum;
	}

	public double getHongbao() {
		return this.hongbao;
	}

	public void setHongbao(double hongbao) {
		this.hongbao = hongbao;
	}

	public double getFree_cash() {
		return this.free_cash;
	}

	public void setFree_cash(double free_cash) {
		this.free_cash = free_cash;
	}

	public double getMax_cash() {
		return this.max_cash;
	}

	public void setMax_cash(double max_cash) {
		this.max_cash = max_cash;
	}

	public double getYes_interest() {
		return this.yes_interest;
	}

	public void setYes_interest(double yes_interest) {
		this.yes_interest = yes_interest;
	}

	public double getTodayOnlineRechargeTotal() {
		return this.todayOnlineRechargeTotal;
	}

	public void setTodayOnlineRechargeTotal(double todayOnlineRechargeTotal) {
		this.todayOnlineRechargeTotal = todayOnlineRechargeTotal;
	}

	public double getCashCredited() {
		return this.cashCredited;
	}

	public void setCashCredited(double cashCredited) {
		this.cashCredited = cashCredited;
	}

	public UserAccountSummary() {
	}

	public UserAccountSummary(long user_id) {
		this.user_id = user_id;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public AccountLogSummary getLogSummary() {
		return this.logSummary;
	}

	public void setLogSummary(AccountLogSummary logSummary) {
		this.logSummary = logSummary;
	}

	public double getAccountTotal() {
		return this.accountTotal;
	}

	public void setAccountTotal(double accountTotal) {
		this.accountTotal = accountTotal;
	}

	public double getAccountUseMoney() {
		return this.accountUseMoney;
	}

	public void setAccountUseMoney(double accountUseMoney) {
		this.accountUseMoney = accountUseMoney;
	}

	public double getAccountNoUseMoney() {
		return this.accountNoUseMoney;
	}

	public void setAccountNoUseMoney(double accountNoUseMoney) {
		this.accountNoUseMoney = accountNoUseMoney;
	}

	public double getBorrowNoUseTotal() {
		return this.borrowNoUseTotal;
	}

	public void setBorrowNoUseTotal(double borrowNoUseTotal) {
		this.borrowNoUseTotal = borrowNoUseTotal;
	}

	public double getInvestTotal() {
		return this.investTotal;
	}

	public void setInvestTotal(double investTotal) {
		this.investTotal = investTotal;
	}

	public double getLendTotal() {
		return this.lendTotal;
	}

	public void setLendTotal(double lendTotal) {
		this.lendTotal = lendTotal;
	}

	public double getAwardTotal() {
		return this.awardTotal;
	}

	public void setAwardTotal(double awardTotal) {
		this.awardTotal = awardTotal;
	}

	public double getCollectTotal() {
		return this.collectTotal;
	}

	public void setCollectTotal(double collectTotal) {
		this.collectTotal = collectTotal;
	}

	public double getCollectInterest() {
		return this.collectInterest;
	}

	public void setCollectInterest(double collectInterest) {
		this.collectInterest = collectInterest;
	}

	public double getLateTotal() {
		return this.lateTotal;
	}

	public void setLateTotal(double lateTotal) {
		this.lateTotal = lateTotal;
	}

	public double getLateInterest() {
		return this.lateInterest;
	}

	public void setLateInterest(double lateInterest) {
		this.lateInterest = lateInterest;
	}

	public double getOverdueInterest() {
		return this.overdueInterest;
	}

	public void setOverdueInterest(double overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public double getAdvanceTotal() {
		return this.advanceTotal;
	}

	public void setAdvanceTotal(double advanceTotal) {
		this.advanceTotal = advanceTotal;
	}

	public double getLossInterest() {
		return this.lossInterest;
	}

	public void setLossInterest(double lossInterest) {
		this.lossInterest = lossInterest;
	}

	public String getNewestCollectDate() {
		return this.newestCollectDate;
	}

	public void setNewestCollectDate(String newestCollectDate) {
		this.newestCollectDate = newestCollectDate;
	}

	public double getBorrowTotal() {
		return this.borrowTotal;
	}

	public void setBorrowTotal(double borrowTotal) {
		this.borrowTotal = borrowTotal;
	}

	public int getBorrowTimes() {
		return this.borrowTimes;
	}

	public void setBorrowTimes(int borrowTimes) {
		this.borrowTimes = borrowTimes;
	}

	public double getRepaidTotal() {
		return this.repaidTotal;
	}

	public void setRepaidTotal(double repaidTotal) {
		this.repaidTotal = repaidTotal;
	}

	public double getNotRepayTotal() {
		return this.notRepayTotal;
	}

	public void setNotRepayTotal(double notRepayTotal) {
		this.notRepayTotal = notRepayTotal;
	}

	public int getInvestTimes() {
		return this.investTimes;
	}

	public void setInvestTimes(int investTimes) {
		this.investTimes = investTimes;
	}

	public int getRepayTimes() {
		return this.repayTimes;
	}

	public void setRepayTimes(int repayTimes) {
		this.repayTimes = repayTimes;
	}

	public String getNewestRepayDate() {
		return this.newestRepayDate;
	}

	public void setNewestRepayDate(String newestRepayDate) {
		this.newestRepayDate = newestRepayDate;
	}

	public double getNewestCollectMoney() {
		return this.newestCollectMoney;
	}

	public void setNewestCollectMoney(double newestCollectMoney) {
		this.newestCollectMoney = newestCollectMoney;
	}

	public double getNewestRepayMoney() {
		return this.newestRepayMoney;
	}

	public void setNewestRepayMoney(double newestRepayMoney) {
		this.newestRepayMoney = newestRepayMoney;
	}

	public double getRechargeTotal() {
		return this.rechargeTotal;
	}

	public void setRechargeTotal(double rechargeTotal) {
		this.rechargeTotal = rechargeTotal;
	}

	public double getCashTotal() {
		return this.cashTotal;
	}

	public void setCashTotal(double cashTotal) {
		this.cashTotal = cashTotal;
	}

	public double getOnlineRechargeTotal() {
		return this.onlineRechargeTotal;
	}

	public void setOnlineRechargeTotal(double onlineRechargeTotal) {
		this.onlineRechargeTotal = onlineRechargeTotal;
	}

	public double getOfflineRechargeTotal() {
		return this.offlineRechargeTotal;
	}

	public void setOfflineRechargeTotal(double offlineRechargeTotal) {
		this.offlineRechargeTotal = offlineRechargeTotal;
	}

	public double getManualRechargeTotal() {
		return this.manualRechargeTotal;
	}

	public void setManualRechargeTotal(double manualRechargeTotal) {
		this.manualRechargeTotal = manualRechargeTotal;
	}

	public double getFeeTotal() {
		return this.feeTotal;
	}

	public void setFeeTotal(double feeTotal) {
		this.feeTotal = feeTotal;
	}

	public double getRechargeFee() {
		return this.rechargeFee;
	}

	public void setRechargeFee(double rechargeFee) {
		this.rechargeFee = rechargeFee;
	}

	public double getCashFee() {
		return this.cashFee;
	}

	public void setCashFee(double cashFee) {
		this.cashFee = cashFee;
	}

	public double getMostAmount() {
		return this.mostAmount;
	}

	public void setMostAmount(double mostAmount) {
		this.mostAmount = mostAmount;
	}

	public double getLeastAmount() {
		return this.leastAmount;
	}

	public void setLeastAmount(double leastAmount) {
		this.leastAmount = leastAmount;
	}

	public double getUseAmount() {
		return this.useAmount;
	}

	public void setUseAmount(double useAmount) {
		this.useAmount = useAmount;
	}

	public double getInvestInterest() {
		return this.investInterest;
	}

	public void setInvestInterest(double investInterest) {
		this.investInterest = investInterest;
	}

	public double getBorrowInterest() {
		return this.borrowInterest;
	}

	public void setBorrowInterest(double borrowInterest) {
		this.borrowInterest = borrowInterest;
	}

	public double getAccountOwnMoney() {
		return this.accountOwnMoney;
	}

	public void setAccountOwnMoney(double accountOwnMoney) {
		this.accountOwnMoney = accountOwnMoney;
	}

	public double getRepayTotal() {
		return this.repayTotal;
	}

	public void setRepayTotal(double repayTotal) {
		this.repayTotal = repayTotal;
	}

	public double getRepayInterest() {
		return this.repayInterest;
	}

	public void setRepayInterest(double repayInterest) {
		this.repayInterest = repayInterest;
	}

	public int getWaitRepayTimes() {
		return this.waitRepayTimes;
	}

	public void setWaitRepayTimes(int waitRepayTimes) {
		this.waitRepayTimes = waitRepayTimes;
	}

	public int getWaitRepayCount() {
		return this.waitRepayCount;
	}

	public void setWaitRepayCount(int waitRepayCount) {
		this.waitRepayCount = waitRepayCount;
	}

	public int getHadRepayCount() {
		return this.hadRepayCount;
	}

	public void setHadRepayCount(int hadRepayCount) {
		this.hadRepayCount = hadRepayCount;
	}

	public int getHadDueRepayCount() {
		return this.hadDueRepayCount;
	}

	public void setHadDueRepayCount(int hadDueRepayCount) {
		this.hadDueRepayCount = hadDueRepayCount;
	}

	public int getWaitDueRepayCount() {
		return this.waitDueRepayCount;
	}

	public void setWaitDueRepayCount(int waitDueRepayCount) {
		this.waitDueRepayCount = waitDueRepayCount;
	}
}