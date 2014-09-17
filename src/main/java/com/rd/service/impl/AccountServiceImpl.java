package com.rd.service.impl;

import com.rd.common.enums.EnumTroubleFund;
import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.dao.AccountDao;
import com.rd.dao.AccountLogDao;
import com.rd.dao.CashDao;
import com.rd.dao.CollectionDao;
import com.rd.dao.HongBaoDao;
import com.rd.dao.OperationLogDao;
import com.rd.dao.ProtocolDao;
import com.rd.dao.RechargeDao;
import com.rd.dao.RepaymentDao;
import com.rd.dao.RewardStatisticsDao;
import com.rd.dao.RuleDao;
import com.rd.dao.TenderDao;
import com.rd.dao.UserDao;
import com.rd.domain.Account;
import com.rd.domain.AccountBank;
import com.rd.domain.AccountCash;
import com.rd.domain.AccountLog;
import com.rd.domain.AccountRecharge;
import com.rd.domain.HongBao;
import com.rd.domain.Huikuan;
import com.rd.domain.Notice;
import com.rd.domain.NoticeConfig;
import com.rd.domain.OperationLog;
import com.rd.domain.PaymentInterface;
import com.rd.domain.Protocol;
import com.rd.domain.User;
import com.rd.domain.UserAmount;
import com.rd.exception.AccountException;
import com.rd.model.DetailTender;
import com.rd.model.DetailUser;
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
import com.rd.model.account.AccountReconciliationModel;
import com.rd.model.account.BorrowSummary;
import com.rd.model.account.CollectSummary;
import com.rd.model.account.InvestSummary;
import com.rd.model.account.OnlineBankModel;
import com.rd.model.account.OperationLogModel;
import com.rd.model.account.RepaySummary;
import com.rd.quartz.notice.NoticeJobQueue;
import com.rd.quartz.notice.Sms;
import com.rd.service.AccountService;
import com.rd.tool.Page;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

public class AccountServiceImpl implements AccountService {
	private Logger logger = Logger.getLogger(AccountServiceImpl.class);
	private AccountDao accountDao;
	private AccountLogDao accountLogDao;
	private RechargeDao rechargeDao;
	private TenderDao tenderDao;
	private CashDao cashDao;
	private CollectionDao collectionDao;
	private UserDao userDao;
	private ProtocolDao protocolDao;
	private RepaymentDao repaymentDao;
	private RewardStatisticsDao rewardStatisticsDao;
	private RuleDao ruleDao;
	private HongBaoDao hongBaoDao;
	private OperationLogDao operationLogDao;

	public RepaymentDao getRepaymentDao() {
		return this.repaymentDao;
	}

	public void setRepaymentDao(RepaymentDao repaymentDao) {
		this.repaymentDao = repaymentDao;
	}

	public ProtocolDao getProtocolDao() {
		return this.protocolDao;
	}

	public void setProtocolDao(ProtocolDao protocolDao) {
		this.protocolDao = protocolDao;
	}

	public OperationLogDao getOperationLogDao() {
		return this.operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public HongBaoDao getHongBaoDao() {
		return this.hongBaoDao;
	}

	public void setHongBaoDao(HongBaoDao hongBaoDao) {
		this.hongBaoDao = hongBaoDao;
	}

	public RechargeDao getRechargeDao() {
		return this.rechargeDao;
	}

	public void setRechargeDao(RechargeDao rechargeDao) {
		this.rechargeDao = rechargeDao;
	}

	public AccountDao getAccountDao() {
		return this.accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public TenderDao getTenderDao() {
		return this.tenderDao;
	}

	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}

	public AccountLogDao getAccountLogDao() {
		return this.accountLogDao;
	}

	public void setAccountLogDao(AccountLogDao accountLogDao) {
		this.accountLogDao = accountLogDao;
	}

	public CashDao getCashDao() {
		return this.cashDao;
	}

	public void setCashDao(CashDao cashDao) {
		this.cashDao = cashDao;
	}

	public CollectionDao getCollectionDao() {
		return this.collectionDao;
	}

	public void setCollectionDao(CollectionDao collectionDao) {
		this.collectionDao = collectionDao;
	}

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public RewardStatisticsDao getRewardStatisticsDao() {
		return this.rewardStatisticsDao;
	}

	public void setRewardStatisticsDao(RewardStatisticsDao rewardStatisticsDao) {
		this.rewardStatisticsDao = rewardStatisticsDao;
	}

	public RuleDao getRuleDao() {
		return this.ruleDao;
	}

	public void setRuleDao(RuleDao ruleDao) {
		this.ruleDao = ruleDao;
	}

	public UserAccountSummary getUserAccountSummary(long user_id) {
		String webid = Global.getValue("webid");
		UserAccountSummary uas = new UserAccountSummary(user_id);
		long start = System.currentTimeMillis();

		long end = System.currentTimeMillis();
		this.logger.info("UserAccountSummary Cost Time:" + (end - start));

		start = System.currentTimeMillis();
		Account act = this.accountDao.getAccount(user_id);
		uas.setAccountTotal(act.getTotal());
		uas.setAccountUseMoney(act.getUse_money());
		uas.setAccountNoUseMoney(act.getNo_use_money());
		uas.setCollectTotal(act.getCollection());
		uas.setHongbao(act.getHongbao());
		end = System.currentTimeMillis();
		long starts = NumberUtils.getLong(Global
				.getString("huikuan_start_time"));
		double collects = this.accountDao.getCollectionSumNoJinAndSecond(
				user_id, 1, starts);
		double huikuansum = this.accountDao.gethuikuanSum(user_id, 1);
		uas.setCollect(collects);
		uas.setHuikuanSum(huikuansum);

		this.logger.info("Account Cost Time:" + (end - start));

		start = System.currentTimeMillis();
		RepaySummary repay = this.accountDao.getRepaySummary(user_id);
		uas.setRepayTotal(repay.getRepayTotal());
		uas.setRepayInterest(repay.getRepayInterest());
		uas.setNewestRepayDate(repay.getRepayTime());
		uas.setNewestRepayMoney(repay.getRepayAccount());
		end = System.currentTimeMillis();
		this.logger.info("RepaySummary Cost Time:" + (end - start));

		start = System.currentTimeMillis();
		BorrowSummary borrow = this.accountDao.getBorrowSummary(user_id);
		uas.setBorrowTotal(borrow.getBorrowTotal());
		uas.setBorrowInterest(borrow.getBorrowInterest());
		uas.setBorrowTimes(borrow.getBorrowTimes());
		end = System.currentTimeMillis();
		this.logger.info("BorrowSummary Cost Time:" + (end - start));

		start = System.currentTimeMillis();
		InvestSummary i = this.accountDao.getInvestSummary(user_id);
		uas.setInvestTotal(i.getInvestTotal());
		uas.setInvestInterest(i.getInvestInterest());
		uas.setInvestTimes(i.getInvestTimes());
		end = System.currentTimeMillis();
		this.logger.info("InvestSummary Cost Time:" + (end - start));

		start = System.currentTimeMillis();
		CollectSummary collect = this.accountDao.getCollectSummary(user_id);
		uas.setCollectInterest(collect.getCollectInterest());
		uas.setNewestCollectDate(collect.getCollectTime());
		uas.setNewestCollectMoney(collect.getCollectMoney());
		end = System.currentTimeMillis();
		this.logger.info("CollectSummary Cost Time:" + (end - start));

		start = System.currentTimeMillis();

		String rechargeTotalSql = "select sum(money) as num from dw_account_recharge where status=1 and money>0 and user_id=?";
		double rechargeTotal = this.accountDao
				.getSum(rechargeTotalSql, user_id);
		uas.setRechargeTotal(rechargeTotal);

		String cashTotalSql = "select sum(total) as num from dw_account_cash where status=1 and user_id=?";
		double cashTotal = this.accountDao.getSum(cashTotalSql, user_id);
		uas.setCashTotal(cashTotal);

		String onlineRechargeTotalSql = "select sum(money) as num from dw_account_recharge where status=1 and type=1 and user_id=?";
		double onlineRechargeTotal = this.accountDao.getSum(
				onlineRechargeTotalSql, user_id);
		uas.setOnlineRechargeTotal(onlineRechargeTotal);

		String todayOnlineRechargeTotalSql = "select sum(money) as num from dw_account_recharge where status=1 and type=1 and user_id=? and addtime>"
				+ DateUtils.getIntegralTime().getTime()
				/ 1000L
				+ " and addtime<"
				+ DateUtils.getLastIntegralTime().getTime()
				/ 1000L;
		double todayOnlineRechargeTotal = this.accountDao.getSum(
				todayOnlineRechargeTotalSql, user_id);
		uas.setTodayOnlineRechargeTotal(todayOnlineRechargeTotal);

		String offlineRechargeTotalSql = "select sum(money) as num from dw_account_recharge where status=1 and type=2 and user_id=?";
		double offlineRechargeTotal = this.accountDao.getSum(
				offlineRechargeTotalSql, user_id);
		uas.setOfflineRechargeTotal(offlineRechargeTotal);

		String cashCreditedSql = "select sum(credited) as num from dw_account_cash where status=1 and user_id=?";
		double cashCredited = this.accountDao.getSum(cashCreditedSql, user_id);
		uas.setCashCredited(cashCredited);

		String cashFeeSql = "select sum(fee) as num from dw_account_cash where status=1 and user_id=?";
		double cashFee = this.accountDao.getSum(cashFeeSql, user_id);
		uas.setCashFee(cashFee);

		if ((StringUtils.isNull(webid).equals("zdvci"))
				|| (StringUtils.isNull(webid).equals("lhcf"))
				|| (StringUtils.isNull(webid).equals("bfct"))
				|| (StringUtils.isNull(webid).equals("jrexc"))) {
			String startTimeStr = Global.getString("newcash_starttime");
			long startTime = DateUtils.getTime(startTimeStr);

			double allcash = this.cashDao.getAccountCashSum(act.getUser_id(),
					1, startTime);

			double nocash = this.cashDao.getAccountNoCashSum(act.getUser_id(),
					0, startTime);

			double outstandingAmount = this.accountDao
					.getCollectionSumNoJinAndSecond(act.getUser_id(), 1,
							startTime);

			double award = this.accountLogDao.getAwardSum(act.getUser_id(),
					startTime);

			double free_cash = this.accountDao.getFreeCashSum(act.getUser_id());
			double x = outstandingAmount - allcash - nocash + award + free_cash;
			uas.setFree_cash(x);
		}

		uas.setAccountOwnMoney(uas.getAccountTotal() - uas.getRepayTotal());
		int days = NumberUtils.getInt(Global.getValue("free_cash_day"));
		days = (days > 0) ? days : 15;
		double lastSum = this.rechargeDao.getLastRechargeSum(user_id, days);
		double cashFreeze = this.cashDao.getAccountCashSum(user_id, 0);

		double y = 0.0D;
		if (Global.getString("webid").equals("wzdai")) {
			y = getFreeCashAmount(user_id, uas);
		} else if (Global.getString("webid").equals("huidai")) {
			double lastSumWithNoAdmin = this.rechargeDao
					.getRechargeSumWithNoAdmin(user_id, days);
			y = uas.getAccountOwnMoney() - lastSumWithNoAdmin - cashFreeze;
		} else {
			y = uas.getAccountOwnMoney() - lastSum - cashFreeze;
		}
		uas.setMax_cash(y);

		double awardTotal = this.accountLogDao.getAwardSum(user_id);
		uas.setAwardTotal(NumberUtils.format2(awardTotal));

		double yes_interest = this.collectionDao.getCollectionInterestSum(
				user_id, 1);
		uas.setYes_interest(NumberUtils.format2(yes_interest));
		end = System.currentTimeMillis();
		this.logger.info("Other Cost Time:" + (end - start));
		return uas;
	}

	public UserAmount getUserAmount(long user_id) {
		return this.accountDao.getUserAmount(user_id);
	}

	public AccountModel getAccount(long user_id) {
		return this.accountDao.getAccount(user_id);
	}

	public User getKf(long user_id) {
		return this.accountDao.getKf(user_id);
	}

	public List<AccountLogModel> getAccountLogList(long user_id) {
		return this.accountLogDao.getAccountLogList(user_id);
	}

	public AccountLogList getAccountLogList(long user_id, int startPage,
			SearchParam param) {
		AccountLogList accountLogList = new AccountLogList();
		int total = this.accountLogDao.getAccountLogCount(user_id, param);
		Page p = new Page(total, startPage);
		List<AccountLogModel> list = this.accountLogDao.getAccountLogList(user_id, p.getStart(),
				p.getPernum(), param);
		accountLogList.setPage(p);
		accountLogList.setList(list);
		return accountLogList;
	}

	public double getAccountLogTotalMoney(long user_id) {
		return this.accountLogDao.getAccountLogTotalMoney(user_id);
	}

	public List<AccountCash> getAccountCashList(long user_id) {
		return this.cashDao.getAccountCashList(user_id);
	}

	public int getAccountCashList(long user_id, int status) {
		return this.cashDao.getAccountCashNum(user_id, status);
	}

	public AccountCashList getAccountCashList(long user_id, int startPage,
			SearchParam param) {
		AccountCashList accountCashList = new AccountCashList();
		int total = this.cashDao.getAccountCashCount(user_id, param);
		Page p = new Page(total, startPage);
		List<AccountCash> list = this.cashDao.getAccountCashList(user_id, p.getStart(), p.getPernum(), param);
		accountCashList.setPage(p);
		accountCashList.setList(list);
		return accountCashList;
	}

	public AccountModel getAccountByBankAccount(long user_id, String bankaccount) {
		return this.accountDao.getAccountByBankAccount(user_id, bankaccount);
	}

	public void cancelCash(long id, AccountLog log) {
		AccountCash cash = this.cashDao.getAccountCash(id);
		if ((cash == null)
				|| ((cash.getStatus() != 0) && (cash.getStatus() != 2))) {
			throw new AccountException("该提现已经审核或者已经取消,请勿重复操作！");
		}
		if (cash != null) {
			cash.setStatus(4);
			this.cashDao.updateCash(cash);
			double total = NumberUtils.getDouble(cash.getTotal());
			this.accountDao.updateAccount(0.0D, total, -total, cash
					.getUser_id());
			Account act = this.accountDao.getAccount(cash.getUser_id());
			log.setMoney(total);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			log.setRemark("取消提现，提现金额" + total + "元");
			this.accountLogDao.addAccountLog(log);
			try {
				Huikuan h = this.accountDao.getHuikuanByCashid(cash.getId());
				h.setStatus("" + cash.getStatus());
				h.setRemark("提现取消,额度返还！");
				this.accountDao.verifyhuikuan(h);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void newCash(AccountCash cash, Account act, double money,
			boolean isCash) {
		String webid = Global.getValue("webid");
		double y = 0.0D;
		double freeline = 0.0D;
		double outstandingAmount = 0.0D;

		double cashFee = NumberUtils.getDouble(Global.getValue("cash_fee"));
		double maxCash = NumberUtils.getDouble(Global.getValue("most_cash"));
		if (money > maxCash) {
			throw new AccountException("提现金额不能大于" + maxCash);
		}
		if ((StringUtils.isNull(webid).equals("zrzb"))
				|| (StringUtils.isNull(webid).equals("xdcf"))) {
			double allcash = this.cashDao
					.getAccountCashSum(act.getUser_id(), 1);
			double nocash = this.cashDao
					.getAccountCashSum(cash.getUser_id(), 0);
			double collection = this.accountDao.getCollectionSum(cash
					.getUser_id(), 1);
			double huikuansum = this.accountDao.gethuikuanSum(
					cash.getUser_id(), 1);
			double x = collection - allcash - nocash - huikuansum;
			double fee = Global.getCash(x, cashFee, money, maxCash);
			double credited = money - fee;
			cash.setFee("" + fee);
			cash.setCredited("" + credited);
		} else if ((StringUtils.isNull(webid).equals("zdvci"))
				|| (StringUtils.isNull(webid).equals("lhcf"))
				|| (StringUtils.isNull(webid).equals("jrexc"))
				|| (StringUtils.isNull(webid).equals("bfct"))) {
			String startTimeStr = Global.getString("newcash_starttime");
			long startTime = DateUtils.getTime(startTimeStr);

			double allcash = this.cashDao.getAccountCashSum(act.getUser_id(),
					1, startTime);

			double nocash = this.cashDao.getAccountNoCashSum(cash.getUser_id(),
					0, startTime);

			outstandingAmount = this.accountDao.getCollectionSumNoJinAndSecond(
					cash.getUser_id(), 1, startTime);

			double award = this.accountLogDao.getAwardSum(cash.getUser_id(),
					startTime);

			double free_cash = this.accountDao
					.getFreeCashSum(cash.getUser_id());
			double x = outstandingAmount - allcash - nocash + award + free_cash;
			double fee = Global.getCash(x, cashFee, money, maxCash);
			fee = NumberUtils.format6(fee);

			if (fee > 0.0D) {
				freeline = fee;
				cash.setFreecash(freeline / cashFee);
			}
			double credited = money - fee;
			cash.setFee("" + fee);
			cash.setCredited("" + credited);
		} else if ((StringUtils.isNull(webid).equals("wzdai"))
				|| (StringUtils.isNull(webid).equals("jsy"))
				|| (StringUtils.isNull(webid).equals("zsdai"))
				|| (StringUtils.isNull(webid).equals("aidai"))
				|| (StringUtils.isNull(webid).equals("jsdai"))) {
			UserAccountSummary uas = getUserAccountSummary(cash.getUser_id());
			double z = getFreeCashAmount(cash.getUser_id(), uas);
			double largeCashlow = 200000.0D;
			if ((uas.getAccountUseMoney() >= largeCashlow)
					&& (uas.getAccountOwnMoney() >= largeCashlow)
					&& (money > 50000.0D) && (money < 200000.0D)) {
				throw new AccountException("您好，大额提现金额必须是20万至50万之间");
			}
			if (StringUtils.isNull(webid).equals("jsdai")) {
				double accountOwnMoney = uas.getAccountOwnMoney();
				this.logger.info(Double.valueOf(accountOwnMoney));
				if (accountOwnMoney < money) {
					throw new AccountException("提现金额不能大于净资产！");
				}
			}
			double fee = Global.getCashForWzdai(uas.getAccountUseMoney(), uas
					.getAccountOwnMoney(), money, z, cashFee, maxCash);
			Account account = this.accountDao.getAccount_hongbao(act
					.getUser_id());
			double hongbao = account.getHongbao();
			if (hongbao - fee >= 0.0D) {
				cash.setHongbao(fee);
				fee = 0.0D;
			} else {
				cash.setHongbao(hongbao);
				fee -= hongbao;
			}

			double credited = money - fee;
			cash.setFee("" + fee);
			cash.setCredited("" + credited);
		} else if (StringUtils.isNull(webid).equals("mdw")) {
			double fee = 0.0D;
			double credited = money - fee;
			cash.setFee("" + fee);
			cash.setCredited("" + credited);
		} else if (StringUtils.isNull(webid).equals("rydai")) {
			double lastSum = this.rechargeDao.getLastRechargeSum(cash
					.getUser_id(), 15);
			Date d = Calendar.getInstance().getTime();
			d = DateUtils.rollDay(d, -15);
			String tenderTotalSql = "select count(*) from dw_borrow_tender where status=1 and user_id=? and addtime>  "
					+ d.getTime() / 1000L;
			int countTenderTotal = this.accountDao.getCount(tenderTotalSql,
					cash.getUser_id());
			double fee = 0.0D;
			if (countTenderTotal >= 1) {
				fee = 3.0D;
			} else {
				String cash_fee_string = Global.getValue("cash_fee");
				double cash_fee = NumberUtils.getDouble(cash_fee_string);
				fee = lastSum * cash_fee;
			}
			double credited = money - fee;
			cash.setFee("" + fee);
			cash.setCredited("" + credited);
		} else if (StringUtils.isNull(webid).equals("ssjb")) {
			int days = NumberUtils.getInt(Global.getValue("free_cash_day"));
			days = (days > 0) ? days : 15;
			double lastSum = this.rechargeDao.getLastRechargeSum(cash
					.getUser_id(), days);
			UserAccountSummary uas = getUserAccountSummary(cash.getUser_id());
			double cashFreeze = this.cashDao.getAccountCashSum(cash
					.getUser_id(), 0);

			if (StringUtils.isNull(webid).equals("huidai")) {
				double lastSumWithNoAdmin = this.rechargeDao
						.getRechargeSumWithNoAdmin(cash.getUser_id(), days);
				y = uas.getAccountOwnMoney() - lastSumWithNoAdmin - cashFreeze;
			} else {
				y = uas.getAccountOwnMoney() - lastSum - cashFreeze;
			}
			String nowtimestring = DateUtils.getNowTimeStr();
			long nowtime = NumberUtils.getLong(nowtimestring);
			long award_start_time = NumberUtils.getLong(Global
					.getValue("award_start_time"));
			long award_end_time = NumberUtils.getLong(Global
					.getValue("award_end_time"));
			double tender_award = 0.0D;
			if ((nowtime > award_start_time) && (nowtime < award_end_time)) {
				Account user_account = this.accountDao.getAccount(act
						.getUser_id());
				tender_award = user_account.getTotal_tender_award();
			}
			double fee = Global.getCash(money, cashFee, y, maxCash,
					tender_award);
			double credited = money - fee;
			cash.setFee("" + fee);
			cash.setCredited("" + credited);
		} else {
			int days = NumberUtils.getInt(Global.getValue("free_cash_day"));
			days = (days > 0) ? days : 15;
			double lastSum = this.rechargeDao.getLastRechargeSum(cash
					.getUser_id(), days);
			UserAccountSummary uas = getUserAccountSummary(cash.getUser_id());
			double cashFreeze = this.cashDao.getAccountCashSum(cash
					.getUser_id(), 0);
			double todayCashFee = 0.0D;
			double todayRechargeTotal = 0.0D;
			double todayCashTotal = 0.0D;

			if (StringUtils.isNull(webid).equals("huidai")) {
				double lastSumWithNoAdmin = this.rechargeDao
						.getRechargeSumWithNoAdmin(cash.getUser_id(), days);
				y = uas.getAccountOwnMoney() - lastSumWithNoAdmin - cashFreeze;
			} else if (StringUtils.isNull(webid).equals("hndai")) {
				todayCashFee = NumberUtils.getDouble(Global
						.getValue("todayCash_fee"));

				todayRechargeTotal = this.rechargeDao.getTodayRechargeTotal(
						cash.getUser_id(), days);
				SearchParam param = new SearchParam();
				String dotime = DateUtils.dateStr2("" + DateUtils.getIntegralTime()
						.getTime() / 1000L);
				param.setDotime1(dotime);
				param.setDotime2(dotime);
				param.setStatus("1");
				param.setUsername(this.userDao.getUserById(cash.getUser_id())
						.getUsername());
				List<AccountRecharge> todayRechargeList = this.rechargeDao.getAllList(param);
				int todayRechargeCount = this.rechargeDao.getAllCount(param);

				if (todayRechargeCount > 0) {
					String todayRechargeTimeDate = ((AccountRecharge) todayRechargeList
							.get(todayRechargeCount - 1)).getAddtime();

					String todayCashTotalSql = "select sum(total) as num from dw_account_cash where status=1 and user_id=? and addtime>"
							+ todayRechargeTimeDate
							+ " and addtime<"
							+ DateUtils.getLastIntegralTime().getTime() / 1000L;
					todayCashTotal = this.accountDao.getSum(todayCashTotalSql,
							cash.getUser_id());
				}
				y = uas.getAccountOwnMoney() - lastSum - cashFreeze;
			} else {
				y = uas.getAccountOwnMoney() - lastSum - cashFreeze;
			}
			double fee = 0.0D;
			if (StringUtils.isNull(webid).equals("hndai"))
				fee = Global.getCashForHndai(money, cashFee, y, maxCash,
						todayRechargeTotal, todayCashTotal, todayCashFee);
			else {
				fee = Global.getCash(money, cashFee, y, maxCash);
			}
			double credited = money - fee;
			cash.setFee("" + fee);
			cash.setCredited("" + credited);
		}
		if (!isCash) {
			return;
		}

		if ((((StringUtils.isNull(webid).equals("zdvci"))
				|| (StringUtils.isNull(webid).equals("jrexc"))
				|| (StringUtils.isNull(webid).equals("lhcf")) || (StringUtils
				.isNull(webid).equals("bfct"))))
				&& (freeline > 0.0D))
			this.cashDao.addFreeCash(cash);
		else {
			this.cashDao.addCash(cash);
		}
		this.accountDao.updateAccount(0.0D, -money, money, cash.getUser_id());
	}

	private double getFreeCashAmount(long userId, UserAccountSummary uas) {
		double z = 0.0D;
		int days = NumberUtils.getInt(Global.getValue("free_cash_day"));
		days = (days > 0) ? days : 15;
		Date now = new Date();
		Date d = DateUtils.rollDay(new Date(), -days);
		double cash15 = this.cashDao.getAccountCashSum(userId, 1,
				d.getTime() / 1000L);
		double recharge15 = this.rechargeDao.getLastRechargeSum(userId, 15);

		double y = uas.getAccountOwnMoney() - recharge15 + cash15;

		List<AccountRecharge> rechargeList = this.rechargeDao.getLastOfflineRechargeList(userId);

		List<String> cashTime = new ArrayList<String>();
		cashTime.add("" + now.getTime() / 1000L);
		for (int i = rechargeList.size() - 1; i >= 0; --i) {
			AccountRecharge r = (AccountRecharge) rechargeList.get(i);
			cashTime.add(r.getAddtime());
		}
		cashTime.add("" + d.getTime() / 1000L);

		List<Double> cashSumList = new ArrayList<Double>();
		for (int i = 1; i < cashTime.size(); ++i) {
			long end = NumberUtils.getLong((String) cashTime.get(i - 1));
			long start = NumberUtils.getLong((String) cashTime.get(i));
			double t = this.cashDao.getAccountCashSum(userId, 1, start, end);
			cashSumList.add(Double.valueOf(t));
		}

		List<Double> wList = new ArrayList<Double>();
		int cashSumListSize = cashSumList.size();
		wList.add(Double.valueOf(y - ((Double) cashSumList.get(cashSumListSize - 1)).doubleValue()));
		for (int i = 0; i < rechargeList.size(); ++i) {
			double r = ((AccountRecharge) rechargeList.get(i)).getMoney();
			double c = ((Double) cashSumList.get(cashSumListSize - 2 - i)).doubleValue();
			wList.add(Double.valueOf(r - c));
		}

		double t = 0.0D;
		for (int i = 0; i < wList.size(); ++i) {
			t += ((Double) wList.get(i)).doubleValue();
			if (t < 0.0D) {
				t = 0.0D;
			}
		}
		double cashFreeze = this.cashDao.getAccountCashSum(userId, 0);
		z = t - cashFreeze;
		this.logger.info("得出的免费额度:" + z);
		return z;
	}

	public void newCash(AccountCash cash, Account act, double money,
			AccountLog log) {
		newCash(cash, act, money, true);
		log.setMoney(money);
		log.setTotal(act.getTotal());
		log.setUse_money(act.getUse_money() - money);
		log.setNo_use_money(act.getNo_use_money() + money);
		log.setCollection(act.getCollection());
		this.accountLogDao.addAccountLog(log);
		int enableHuikuan = Global.getInt("huikuan_enable");
		if (enableHuikuan == 1)
			autoHuikuanForCash(cash.getUser_id(), NumberUtils.getDouble(cash
					.getTotal()), cash.getId(), null);
	}

	private double autoHuikuanForCash(long user_id, double account,
			long cash_id, AccountLog log) {
		long start = NumberUtils
				.getLong(Global.getString("huikuan_start_time"));
		int isMonth = Global.getInt("huikuan_month");
		int isday = (isMonth == 1) ? 0 : 1;
		double collection = this.accountDao.getCollectionSumNoJinAndSecond(
				user_id, 1, isday, start);
		double huikuansum = this.accountDao.gethuikuanSum(user_id);

		if (collection > huikuansum) {
			double huikuan_money = 0.0D;
			if (collection - huikuansum > account)
				huikuan_money = account;
			else {
				huikuan_money = collection - huikuansum;
			}
			if (huikuan_money >= 0.01D) {
				Huikuan huikuan = new Huikuan();
				huikuan.setUser_id(user_id);
				huikuan.setHuikuan_money(NumberUtils.format2Str(huikuan_money));
				huikuan.setHuikuan_award("0");
				huikuan.setRemark("回款提现,扣除回款额度"
						+ NumberUtils.format2Str(huikuan_money));
				huikuan.setStatus("1");
				huikuan.setAddtime(DateUtils.getNowTimeStr());
				huikuan.setCash_id(cash_id);
				this.accountDao.addHuikuanMoney(huikuan);
			}
		}
		return account;
	}

	public Account addAccount(Account act) {
		return this.accountDao.addAccount(act);
	}

	public AccountBank addBank(AccountBank bank) {
		return this.accountDao.addBank(bank);
	}

	public AccountBank modifyBank(AccountBank bank) {
		return this.accountDao.updateBank(bank);
	}

	public List<AccountRecharge> getRechargeList(long user_id) {
		return this.rechargeDao.getList(user_id);
	}

	public AccountRechargeList getRechargeList(long user_id, int startPage,
			SearchParam param) {
		AccountRechargeList accountRechargeList = new AccountRechargeList();
		int total = this.rechargeDao.getCount(user_id, param);
		Page p = new Page(total, startPage);
		accountRechargeList.setPage(p);
		List<AccountRecharge> list = this.rechargeDao.getList(user_id, p.getStart(), p.getPernum(), param);
		accountRechargeList.setList(list);
		return accountRechargeList;
	}

	public void addAccountLog(AccountLog log) {
		this.accountLogDao.addAccountLog(log);
	}

	public void modifyAccount(Account act) {
		this.accountDao.updateAccount(act);
	}

	public synchronized void newRecharge(String orderId, String returnText,
			AccountLog log) {
		AccountRecharge existRecharge = this.rechargeDao
				.getRechargeByTradeno(orderId);
		if (existRecharge == null) {
			return;
		}
		this.logger.debug("===============");
		if ((existRecharge.getStatus() == 0)
				|| (existRecharge.getStatus() == 2)) {
			this.logger.debug("newRecharege");

			Account act = this.accountDao
					.getAccount(existRecharge.getUser_id());
			this.accountDao.updateAccount(existRecharge.getMoney(),
					existRecharge.getMoney(), 0.0D, existRecharge.getUser_id());

			int count = this.rechargeDao.updateRechargeByStatus(1, returnText,
					existRecharge.getTrade_no());
			if (count < 1) {
				throw new AccountException("充值异常，联系管理员!");
			}

			log.setUser_id(existRecharge.getUser_id());
			log.setTo_user(1L);
			log.setMoney(existRecharge.getMoney());
			log.setTotal(act.getTotal() + existRecharge.getMoney());
			log.setUse_money(act.getUse_money() + existRecharge.getMoney());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			this.accountLogDao.addAccountLog(log);
			validRecharge(existRecharge, act, log);
			this.logger.debug("*****");
			if (Global.getWebid().equals("jsy")) {
				DetailUser detailUser = this.userDao
						.getDetailUserByUserid(existRecharge.getUser_id());
				Protocol protocol = new Protocol(0L,
						Constant.RECHARGE_PROTOCOL, log.getAddtime(), log
								.getAddip(), "");
				protocol.setMoney(existRecharge.getMoney());
				protocol.setUser_id(existRecharge.getUser_id());
				protocol.setRemark(detailUser.getTypename() + "（"
						+ log.getAddip() + "）用户名为" + detailUser.getUsername()
						+ "的操作员审核网上充值" + existRecharge.getUsername() + "用户"
						+ existRecharge.getMoney() + "元成功，生成投资人投标保证金托管委托书");
			}

			existRecharge = this.rechargeDao.getRechargeByTradeno(orderId);
			NoticeConfig noticeConfig = Global.getNoticeConfig("up_recharge");
			Notice s = new Sms();
			if (noticeConfig.getSms() == 1L) {
				s.setAddtime(existRecharge.getAddtime());
				s.setContent("尊敬的" + Global.getValue("webname") + "用户，您于"
						+ DateUtils.dateStr5(s.getAddtime()) + "进行网上充值"
						+ existRecharge.getMoney() + "元成功，请登录网站查看详情！");
				s.setReceive_userid(existRecharge.getUser_id());
				s.setSend_userid(1L);
				NoticeJobQueue.NOTICE.offer(s);
			}
		}

		this.logger.debug("===============");
	}

	public void validRecharge(AccountRecharge existRecharge, Account act,
			AccountLog log) {
		if ((existRecharge != null)
				&& (StringUtils.isNull(existRecharge.getPayment()).equals("11"))) {
			try {
				double xs_rechargefee = NumberUtils.getDouble(Global
						.getValue("xs_rechargefee"));
				double fee = existRecharge.getMoney() * xs_rechargefee;
				if (fee >= 0.01D) {
					existRecharge.setFee("" + NumberUtils.format2(fee));
					this.rechargeDao.updateRechargeFee(
							NumberUtils.format2(fee), existRecharge.getId());
					this.accountDao.updateAccount(-fee, -fee, 0.0D,
							existRecharge.getUser_id());
					act = this.accountDao
							.getAccount(existRecharge.getUser_id());
					log.setType(Constant.FEE);
					log.setTo_user(1L);
					log.setMoney(fee);
					log.setTotal(act.getTotal());
					log.setUse_money(act.getUse_money());
					log.setNo_use_money(act.getNo_use_money());
					log.setCollection(act.getCollection());
					this.accountLogDao.addAccountLog(log);
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				this.logger.error("扣除手续费失败!" + existRecharge.getTrade_no());
			}
		} else if ((existRecharge != null)
				&& (StringUtils.isNull(existRecharge.getPayment()).equals("12"))) {
			try {
				double yb_rechargefee = NumberUtils.getDouble(Global.getValue("yb_rechargefee"));
				double fee = existRecharge.getMoney() * yb_rechargefee;
				if (fee >= 0.01D) {
					existRecharge.setFee("" + NumberUtils.format2(fee));
					this.rechargeDao.updateRechargeFee(NumberUtils.format2(fee), existRecharge.getId());
					this.accountDao.updateAccount(-fee, -fee, 0.0D, existRecharge.getUser_id());
					act = this.accountDao.getAccount(existRecharge.getUser_id());
					log.setType(Constant.FEE);
					log.setTo_user(1L);
					log.setMoney(fee);
					log.setTotal(act.getTotal());
					log.setUse_money(act.getUse_money());
					log.setNo_use_money(act.getNo_use_money());
					log.setCollection(act.getCollection());
					this.accountLogDao.addAccountLog(log);
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				this.logger.error("扣除手续费失败!" + existRecharge.getTrade_no());
			}
		} else if ((existRecharge != null)
				&& (StringUtils.isNull(existRecharge.getPayment()).equals("51"))) {
			try {
				double tl_rechargefee = NumberUtils.getDouble(Global
						.getValue("tl_rechargefee"));
				double fee = existRecharge.getMoney() * tl_rechargefee;
				if (fee >= 0.0001D) {
					existRecharge.setFee("" + NumberUtils.format2(fee));
					this.rechargeDao.updateRechargeFee(NumberUtils.format2(fee), existRecharge.getId());
					this.accountDao.updateAccount(-fee, -fee, 0.0D, existRecharge.getUser_id());
					act = this.accountDao.getAccount(existRecharge.getUser_id());
					log.setType(Constant.FEE);
					log.setTo_user(1L);
					log.setMoney(fee);
					log.setTotal(act.getTotal());
					log.setUse_money(act.getUse_money());
					log.setNo_use_money(act.getNo_use_money());
					log.setCollection(act.getCollection());
					this.accountLogDao.addAccountLog(log);
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				this.logger.error("扣除手续费失败!" + existRecharge.getTrade_no());
			}
		} else if ((existRecharge != null)
				&& (StringUtils.isNull(existRecharge.getPayment()).equals("13"))) {
			try {
				double bf_rechargefee = NumberUtils.getDouble(Global.getValue("bf_rechargefee"));
				double fee = existRecharge.getMoney() * bf_rechargefee;
				if (fee >= 0.01D) {
					existRecharge.setFee("" + NumberUtils.format2(fee));
					this.rechargeDao.updateRechargeFee(NumberUtils.format2(fee), existRecharge.getId());
					this.accountDao.updateAccount(-fee, -fee, 0.0D, existRecharge.getUser_id());
					act = this.accountDao.getAccount(existRecharge.getUser_id());
					log.setType(Constant.FEE);
					log.setTo_user(1L);
					log.setMoney(fee);
					log.setTotal(act.getTotal());
					log.setUse_money(act.getUse_money());
					log.setNo_use_money(act.getNo_use_money());
					log.setCollection(act.getCollection());
					this.accountLogDao.addAccountLog(log);
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				this.logger.error("扣除手续费失败!" + existRecharge.getTrade_no());
			}
		} else if ((existRecharge != null)
				&& (StringUtils.isNull(existRecharge.getPayment()).equals("55"))) {
			try {
				double hc_rechargefee = NumberUtils.getDouble(Global.getValue("dinpay_recharge_fee"));
				double fee = existRecharge.getMoney() * hc_rechargefee;
				if (fee >= 0.01D) {
					existRecharge.setFee("" + NumberUtils.format2(fee));
					this.rechargeDao.updateRechargeFee(NumberUtils.format2(fee), existRecharge.getId());
					this.accountDao.updateAccount(-fee, -fee, 0.0D, existRecharge.getUser_id());
					act = this.accountDao.getAccount(existRecharge.getUser_id());
					log.setType(Constant.FEE);
					log.setTo_user(1L);
					log.setMoney(fee);
					log.setTotal(act.getTotal());
					log.setUse_money(act.getCollection());
					log.setNo_use_money(act.getNo_use_money());
					log.setCollection(act.getCollection());
					this.accountLogDao.addAccountLog(log);
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				this.logger.error("扣除手续费失败！" + existRecharge.getTrade_no());
			}
		} else if ((existRecharge != null)
				&& (StringUtils.isNull(existRecharge.getPayment()).equals("54"))) {
			try {
				double hc_rechargefee = NumberUtils.getDouble(Global.getValue("ecpsspay_recharge_fee"));
				double fee = existRecharge.getMoney() * hc_rechargefee;
				this.logger.info("计算手续费开始");
				if (fee >= 0.01D) {
					this.logger.info("充值手续费：" + fee);
					existRecharge.setFee("" + NumberUtils.format2(fee));
					this.rechargeDao.updateRechargeFee(NumberUtils.format2(fee), existRecharge.getId());
					this.accountDao.updateAccount(-fee, -fee, 0.0D, existRecharge.getUser_id());
					act = this.accountDao.getAccount(existRecharge.getUser_id());
					log.setType(Constant.FEE);
					log.setTo_user(1L);
					log.setMoney(fee);
					log.setTotal(act.getTotal());
					log.setUse_money(act.getCollection());
					log.setNo_use_money(act.getNo_use_money());
					log.setCollection(act.getCollection());
					log.setRemark("汇潮支付，扣除充值手续费:" + fee);
					this.accountLogDao.addAccountLog(log);
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				this.logger.error("扣除手续费失败！" + existRecharge.getTrade_no());
			}
		} else if ((existRecharge != null) && (StringUtils.isNull(existRecharge.getPayment()).equals("9"))) {
			try {
				double tenpay_rechargefee = NumberUtils.getDouble(Global.getValue("tenpay_recharge_fee"));
				double fee = existRecharge.getMoney() * tenpay_rechargefee;
				this.logger.info("计算手续费开始");
				if (fee >= 0.01D) {
					this.logger.info("充值手续费：" + fee);
					existRecharge.setFee("" + NumberUtils.format2(fee));
					this.rechargeDao.updateRechargeFee(NumberUtils.format2(fee), existRecharge.getId());
					this.accountDao.updateAccount(-fee, -fee, 0.0D, existRecharge.getUser_id());
					act = this.accountDao.getAccount(existRecharge.getUser_id());
					log.setType(Constant.FEE);
					log.setTo_user(1L);
					log.setMoney(fee);
					log.setTotal(act.getTotal());
					log.setUse_money(act.getCollection());
					log.setNo_use_money(act.getNo_use_money());
					log.setCollection(act.getCollection());
					log.setRemark("财付通支付，扣除充值手续费:" + fee);
					this.accountLogDao.addAccountLog(log);
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				this.logger.error("扣除手续费失败！" + existRecharge.getTrade_no());
			}
		} else if ((existRecharge != null) && (StringUtils.isNull(existRecharge.getPayment()).equals("56"))) {
			try {
				double shengpay_rechargefee = NumberUtils.getDouble(Global
						.getValue("shengpay_recharge_fee"));
				double fee = existRecharge.getMoney() * shengpay_rechargefee;
				if (fee >= 0.01D) {
					existRecharge.setFee("" + NumberUtils.format2(fee));
					this.rechargeDao.updateRechargeFee(NumberUtils.format2(fee), existRecharge.getId());
					this.accountDao.updateAccount(-fee, -fee, 0.0D, existRecharge.getUser_id());
					act = this.accountDao.getAccount(existRecharge.getUser_id());
					log.setType(Constant.FEE);
					log.setTo_user(1L);
					log.setMoney(fee);
					log.setTotal(act.getTotal());
					log.setUse_money(act.getUse_money());
					log.setNo_use_money(act.getNo_use_money());
					log.setCollection(act.getCollection());
					this.accountLogDao.addAccountLog(log);
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				this.logger.error("扣除手续费失败!" + existRecharge.getTrade_no());
			}
		} else {
			if ((existRecharge == null) || (!StringUtils.isNull(existRecharge.getPayment()).equals("58")))
				return;
			try {
				double hc_rechargefee = NumberUtils.getDouble(Global.getValue("ecpsspay_fast_recharge_fee"));
				double fee = existRecharge.getMoney() * hc_rechargefee;
				this.logger.info("计算手续费开始");
				if (fee >= 0.01D) {
					this.logger.info("充值手续费：" + fee);
					existRecharge.setFee("" + NumberUtils.format2(fee));
					this.rechargeDao.updateRechargeFee(NumberUtils.format2(fee), existRecharge.getId());
					this.accountDao.updateAccount(-fee, -fee, 0.0D, existRecharge.getUser_id());
					act = this.accountDao.getAccount(existRecharge.getUser_id());
					log.setType(Constant.FEE);
					log.setTo_user(1L);
					log.setMoney(fee);
					log.setTotal(act.getTotal());
					log.setUse_money(act.getCollection());
					log.setNo_use_money(act.getNo_use_money());
					log.setCollection(act.getCollection());
					log.setRemark("汇潮支付，扣除充值手续费:" + fee);
					this.accountLogDao.addAccountLog(log);
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				this.logger.error("扣除手续费失败！" + existRecharge.getTrade_no());
			}
		}
	}

	public synchronized void recharge(String orderId, String returnText,
			AccountLog log, AccountLog newlog) {
		String recharge_apr = Global.getValue(Constant.RECHARGE_APR);
		double apr = NumberUtils.getDouble(recharge_apr);
		AccountRecharge existRecharge = this.rechargeDao.getRechargeByTradeno(orderId);
		if (existRecharge == null) {
			return;
		}
		this.logger.debug("===============");
		if ((existRecharge.getStatus() == 0) || (existRecharge.getStatus() == 2)) {
			this.logger.debug("newRecharege");

			Account act = this.accountDao.getAccount(existRecharge.getUser_id());
			this.accountDao.updateAccount(existRecharge.getMoney()
					* (1.0D - apr), existRecharge.getMoney() * (1.0D - apr),
					0.0D, existRecharge.getUser_id());

			this.rechargeDao.updateRecharge(1, returnText, existRecharge.getTrade_no());

			log.setUser_id(existRecharge.getUser_id());
			log.setTo_user(1L);
			log.setMoney(existRecharge.getMoney() * (1.0D - apr));
			log.setTotal(act.getTotal() + existRecharge.getMoney() * (1.0D - apr));
			log.setUse_money(act.getUse_money() + existRecharge.getMoney() * (1.0D - apr));
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			this.accountLogDao.addAccountLog(log);

			newlog.setUser_id(existRecharge.getUser_id());
			newlog.setTo_user(1L);
			newlog.setMoney(existRecharge.getMoney() * apr);
			newlog.setTotal(act.getTotal() + existRecharge.getMoney() * apr);
			newlog.setUse_money(act.getUse_money() + existRecharge.getMoney() * apr);
			newlog.setNo_use_money(act.getNo_use_money());
			newlog.setCollection(act.getCollection());
			this.accountLogDao.addAccountLog(newlog);
			this.logger.debug("*****");
		}
		this.logger.debug("===============");
	}

	public void failRecharge(String orderId, String returnText, AccountLog log) {
		AccountRecharge existRecharge = this.rechargeDao.getRechargeByTradeno(orderId);
		if (existRecharge == null) {
			return;
		}
		if (existRecharge.getStatus() == 0)
			this.rechargeDao.updateRecharge(2, returnText, existRecharge.getTrade_no());
	}

	public void addRecharge(AccountRecharge r) {
		this.rechargeDao.addRecharge(r);
	}

	public PageDataList getRechargeList(int page, SearchParam param) {
		PageDataList plist = new PageDataList();
		int total = this.rechargeDao.getAllCount(param);
		Page p = new Page(total, page);
		plist.setPage(p);
		List<AccountRecharge> list = this.rechargeDao.getAllList(p.getStart(), p.getPernum(), param);
		plist.setList(list);
		return plist;
	}

	public List<AccountRecharge> getRechargeList(SearchParam param) {
		List<AccountRecharge> list = this.rechargeDao.getAllList(param);
		return list;
	}

	public AccountRecharge getRecharge(long id) {
		return this.rechargeDao.getRecharge(id);
	}

	public void verifyRecharge(AccountRecharge r, AccountLog log,
			OperationLog operationLog) {
		this.rechargeDao.updateRecharge(r);
		double recharge = r.getMoney();
		Account act = this.accountDao.getAccount(r.getUser_id());

		DetailUser detailUser = this.userDao.getDetailUserByUserid(operationLog.getVerify_user());
		if (r.getStatus() == 1) {
			if ("50".equals(r.getPayment())) {
				this.accountDao.updateAccount(recharge, 0.0D, recharge, r.getUser_id());

				act = this.accountDao.getAccount(act.getUser_id());
				log.setType(Constant.ACCOUNT_BACK);
				log.setMoney(Math.abs(recharge));
				log.setTotal(act.getTotal());
				log.setUse_money(act.getUse_money());
				log.setNo_use_money(act.getNo_use_money());
				log.setCollection(act.getCollection());
				this.accountLogDao.addAccountLog(log);

				operationLog.setType(Constant.BACKSTAGE_BACK_SUCCESS);
				operationLog.setOperationResult(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核后台扣款"
						+ r.getUsername() + recharge + "元成功");
				this.operationLogDao.addOperationLog(operationLog);
			} else {
				this.accountDao.updateAccount(recharge, recharge, 0.0D, r.getUser_id());

				act = this.accountDao.getAccount(act.getUser_id());
				log.setType(Constant.RECHARGE_SUCCESS);
				log.setMoney(recharge);
				log.setTotal(act.getTotal());
				log.setUse_money(act.getUse_money());
				log.setNo_use_money(act.getNo_use_money());
				log.setCollection(act.getCollection());
				this.accountLogDao.addAccountLog(log);

				double offrecharge_award = Global.getDouble(Constant.OFFRECHARGE_AWARD);
				int type = NumberUtils.getInt(r.getType());
				int payment = NumberUtils.getInt(r.getPayment());
				double offrecharge_award_limit = Global.getDouble("offrecharge_award_limit");
				if ((offrecharge_award > 0.0D) && (type == 2)
						&& (payment != 48)
						&& (recharge >= offrecharge_award_limit)) {
					double offrecharge_award_value = recharge * offrecharge_award;
					this.accountDao.updateAccount(offrecharge_award_value, offrecharge_award_value, 0.0D, r.getUser_id());

					act = this.accountDao.getAccount(act.getUser_id());
					log.setType(Constant.OFFRECHARGE_AWARD);
					log.setMoney(offrecharge_award_value);
					log.setTotal(act.getTotal());
					log.setUse_money(act.getUse_money());
					log.setNo_use_money(act.getNo_use_money());
					log.setCollection(act.getCollection());
					log.setRemark("线下充值奖励" + NumberUtils.format2(offrecharge_award_value) + "元");
					this.accountLogDao.addAccountLog(log);
				}

				if (StringUtils.isNull(r.getType()).equals("2")) {
					operationLog.setType(Constant.LINE_RECHARGE_SUCCESS);
					operationLog.setOperationResult(detailUser.getTypename()
							+ "（" + operationLog.getAddip() + "）用户名为"
							+ detailUser.getUsername() + "的操作员审核后台线下充值"
							+ r.getUsername() + recharge + "元成功");
					if ("48".equals(r.getPayment())) {
						operationLog.setType(Constant.BACKSTAGE_RECHARGE_SUCCESS);
						operationLog.setOperationResult(detailUser.getTypename()
								+ "（"
								+ operationLog.getAddip()
								+ "）用户名为"
								+ detailUser.getUsername()
								+ "的操作员审核线下充值"
								+ r.getUsername() + recharge + "元成功");
					}
				} else {
					operationLog.setType(Constant.ONLINE_RECHARGE_SUCCESS);
					operationLog.setOperationResult(detailUser.getTypename()
							+ "（" + operationLog.getAddip() + "）用户名为"
							+ detailUser.getUsername() + "的操作员审核网上充值"
							+ r.getUsername() + recharge + "元成功");
				}
				this.operationLogDao.addOperationLog(operationLog);
			}
		} else if ("50".equals(r.getPayment())) {
			this.accountDao.updateAccount(0.0D, -recharge, recharge, r.getUser_id());

			log.setType(Constant.UNFREEZE);
			log.setMoney(Math.abs(recharge));
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			this.accountLogDao.addAccountLog(log);

			operationLog.setType(Constant.BACKSTAGE_BACK_FAIL);
			operationLog.setOperationResult(detailUser.getTypename() + "（"
					+ operationLog.getAddip() + "）用户名为"
					+ detailUser.getUsername() + "的操作员在"
					+ DateUtils.getTime(operationLog.getAddtime()) + "后台审核扣款"
					+ r.getUsername() + recharge + "元失败");
			this.operationLogDao.addOperationLog(operationLog);
		} else {
			if (StringUtils.isNull(r.getType()).equals("2")) {
				operationLog.setType(Constant.LINE_RECHARGE_FAIL);
				operationLog.setOperationResult(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核后台线下充值"
						+ r.getUsername() + recharge + "元失败");
				if ("48".equals(r.getPayment())) {
					operationLog.setType(Constant.BACKSTAGE_RECHARGE_FAIL);
					operationLog.setOperationResult(detailUser.getTypename()
							+ "（" + operationLog.getAddip() + "）用户名为"
							+ detailUser.getUsername() + "的操作员审核线下充值"
							+ r.getUsername() + recharge + "元失败");
				}
			} else {
				operationLog.setType(Constant.ONLINE_RECHARGE_FAIL);
				operationLog.setOperationResult(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核网上充值"
						+ r.getUsername() + recharge + "元失败");
			}
			this.operationLogDao.addOperationLog(operationLog);
		}
	}

	public void verifyRecharge(AccountRecharge r, AccountLog log,
			OperationLog operationLog, Protocol protocol) {
		this.rechargeDao.updateRecharge(r);
		double recharge = r.getMoney();
		Account act = this.accountDao.getAccount(r.getUser_id());

		DetailUser detailUser = this.userDao.getDetailUserByUserid(operationLog.getVerify_user());

		protocol.setPid(r.getId());
		protocol.setMoney(r.getMoney());
		protocol.setUser_id(r.getUser_id());
		if (r.getStatus() == 1) {
			if (r.getPayment().equals("50")) {
				this.accountDao.updateAccount(recharge, 0.0D, recharge, r.getUser_id());

				act = this.accountDao.getAccount(act.getUser_id());
				log.setType(Constant.ACCOUNT_BACK);
				log.setMoney(Math.abs(recharge));
				log.setTotal(act.getTotal());
				log.setUse_money(act.getUse_money());
				log.setNo_use_money(act.getNo_use_money());
				log.setCollection(act.getCollection());
				this.accountLogDao.addAccountLog(log);

				operationLog.setType(Constant.BACKSTAGE_BACK_SUCCESS);
				operationLog.setOperationResult(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核后台扣款"
						+ r.getUsername() + recharge + "元成功");
				this.operationLogDao.addOperationLog(operationLog);
			} else {
				this.accountDao.updateAccount(recharge, recharge, 0.0D, r.getUser_id());

				act = this.accountDao.getAccount(act.getUser_id());
				log.setType(Constant.RECHARGE_SUCCESS);
				log.setMoney(recharge);
				log.setTotal(act.getTotal());
				log.setUse_money(act.getUse_money());
				log.setNo_use_money(act.getNo_use_money());
				log.setCollection(act.getCollection());
				this.accountLogDao.addAccountLog(log);

				if (StringUtils.isNull(r.getType()).equals("2")) {
					operationLog.setType(Constant.LINE_RECHARGE_SUCCESS);
					operationLog.setOperationResult(detailUser.getTypename()
							+ "（" + operationLog.getAddip() + "）用户名为"
							+ detailUser.getUsername() + "的操作员审核后台线下充值"
							+ r.getUsername() + recharge + "元成功");
					if (r.getPayment().equals("48")) {
						operationLog
								.setType(Constant.BACKSTAGE_RECHARGE_SUCCESS);
						operationLog.setOperationResult(detailUser
								.getTypename()
								+ "（"
								+ operationLog.getAddip()
								+ "）用户名为"
								+ detailUser.getUsername()
								+ "的操作员审核线下充值"
								+ r.getUsername() + recharge + "元成功");

						protocol.setRemark(detailUser.getTypename() + "（"
								+ operationLog.getAddip() + "）用户名为"
								+ detailUser.getUsername() + "的操作员审核线下充值"
								+ r.getUsername() + recharge
								+ "元成功，生成投资人投标保证金托管委托书");
					}
				} else {
					operationLog.setType(Constant.ONLINE_RECHARGE_SUCCESS);
					operationLog.setOperationResult(detailUser.getTypename()
							+ "（" + operationLog.getAddip() + "）用户名为"
							+ detailUser.getUsername() + "的操作员审核网上充值"
							+ r.getUsername() + recharge + "元成功");

					protocol.setRemark(detailUser.getTypename() + "（"
							+ operationLog.getAddip() + "）用户名为"
							+ detailUser.getUsername() + "的操作员审核网上充值"
							+ r.getUsername() + "用户" + recharge
							+ "元成功，生成投资人投标保证金托管委托书");
				}

				this.operationLogDao.addOperationLog(operationLog);
				this.protocolDao.addProtocol(protocol);
			}
		} else if (r.getPayment().equals("50")) {
			this.accountDao.updateAccount(0.0D, -recharge, recharge, r.getUser_id());

			log.setType(Constant.UNFREEZE);
			log.setMoney(Math.abs(recharge));
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			this.accountLogDao.addAccountLog(log);

			operationLog.setType(Constant.BACKSTAGE_BACK_FAIL);
			operationLog.setOperationResult(detailUser.getTypename() + "（"
					+ operationLog.getAddip() + "）用户名为"
					+ detailUser.getUsername() + "的操作员在"
					+ DateUtils.getTime(operationLog.getAddtime()) + "后台审核扣款"
					+ r.getUsername() + recharge + "元失败");
			this.operationLogDao.addOperationLog(operationLog);
		} else {
			if (StringUtils.isNull(r.getType()).equals("2")) {
				operationLog.setType(Constant.LINE_RECHARGE_FAIL);
				operationLog.setOperationResult(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核后台线下充值"
						+ r.getUsername() + recharge + "元失败");
				if (r.getPayment().equals("48")) {
					operationLog.setType(Constant.BACKSTAGE_RECHARGE_FAIL);
					operationLog.setOperationResult(detailUser.getTypename()
							+ "（" + operationLog.getAddip() + "）用户名为"
							+ detailUser.getUsername() + "的操作员审核线下充值"
							+ r.getUsername() + recharge + "元失败");
				}
			} else {
				operationLog.setType(Constant.ONLINE_RECHARGE_FAIL);
				operationLog.setOperationResult(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核网上充值"
						+ r.getUsername() + recharge + "元失败");
			}
			this.operationLogDao.addOperationLog(operationLog);
		}
	}

	public void verifyRecharge(AccountRecharge r, AccountLog log,
			HongBao hongbao, OperationLog operationLog) {
		this.rechargeDao.updateRecharge(r);
		double recharge = r.getMoney();
		Account act = this.accountDao.getAccount(r.getUser_id());
		if (r.getStatus() == 1) {
			if (r.getPayment().equals("50")) {
				this.accountDao.updateAccount(recharge, 0.0D, recharge, r.getUser_id());

				act = this.accountDao.getAccount(act.getUser_id());
				log.setType(Constant.ACCOUNT_BACK);
				log.setMoney(Math.abs(recharge));
				log.setTotal(act.getTotal());
				log.setUse_money(act.getUse_money());
				log.setNo_use_money(act.getNo_use_money());
				log.setCollection(act.getCollection());
				this.accountLogDao.addAccountLog(log);
			} else {
				this.accountDao.updateAccount(recharge, recharge, 0.0D, r.getUser_id());

				act = this.accountDao.getAccount(act.getUser_id());
				log.setType(Constant.RECHARGE_SUCCESS);
				log.setMoney(recharge);
				log.setTotal(act.getTotal());
				log.setUse_money(act.getUse_money());
				log.setNo_use_money(act.getNo_use_money());
				log.setCollection(act.getCollection());
				this.accountLogDao.addAccountLog(log);

				if (!StringUtils.isNull(r.getType()).equals("2")) {
					return;
				}

				this.accountDao.updateAccount(0.0D, 0.0D, 0.0D, r.getUser_id(), NumberUtils.format2(hongbao.getHongbao_money()), 1);
				hongbao.setRemark("用户"
								+ r.getUsername()
								+ "线下充值"
								+ NumberUtils.format2(r.getMoney())
								+ "成功,订单号："
								+ r.getTrade_no()
								+ ",添加红包"
								+ NumberUtils.format2(hongbao
										.getHongbao_money()) + "元");
				this.hongBaoDao.addHongbao(hongbao);
			}
		} else {
			if (!r.getPayment().equals("50"))
				return;
			this.accountDao.updateAccount(0.0D, -recharge, recharge, r.getUser_id());

			log.setType(Constant.UNFREEZE);
			log.setMoney(Math.abs(recharge));
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			this.accountLogDao.addAccountLog(log);
		}
	}

	public PageDataList getAccountLogList(int page, SearchParam param) {
		int total = this.accountLogDao.getAccountLogCount(param);
		Page p = new Page(total, page);
		List<AccountLogModel> list = this.accountLogDao.getAccountLogList(p.getStart(), p.getPernum(), param);
		return new PageDataList(p, list);
	}

	public List<AccountLogModel> getAccountLogList(SearchParam param) {
		List<AccountLogModel> list = this.accountLogDao.getAccountLogList(param);
		return list;
	}

	public void addRecharge(AccountRecharge r, AccountLog accountLog) {
		this.rechargeDao.addRecharge(r);
	}

	public void updateAccount(double totalVar, double useVar, double nouseVar, long user_id) {
		this.accountDao.updateAccount(totalVar, useVar, nouseVar, user_id);
	}

	public void updateAccount(double totalVar, double useVar, double nouseVar,
			double tender_award, long user_id, int a) {
		this.accountDao.updateAccount(totalVar, useVar, nouseVar, tender_award, user_id, a);
	}

	public void updateAccount(double totalVar, double useVar, double nouseVar,
			double collectVar, long user_id) {
		this.accountDao.updateAccount(totalVar, useVar, nouseVar, collectVar, user_id);
	}

	public PageDataList getUserAccountModel(int page, SearchParam param) {
		PageDataList plist = new PageDataList();
		int total = this.accountDao.getAccountCount(param);
		Page p = new Page(total, page);
		plist.setPage(p);
		List<AccountModel> list = this.accountDao.getAccountList(p.getStart(), p.getPernum(), param);
		plist.setList(list);
		return plist;
	}

	public List<AccountModel> getAccountList(SearchParam param) {
		List<AccountModel> list = this.accountDao.getAccountList(param);
		return list;
	}

	public double getRechargesum(SearchParam param, int ids) {
		double sum = this.rechargeDao.getAccount_sum(param, ids);
		return sum;
	}

	public double getAwardSum(long user_id) {
		double sum = this.accountLogDao.getAwardSum(user_id);
		return sum;
	}

	public double getInvestInterestSum(long user_id) {
		double sum = this.accountLogDao.getInvestInterestSum(user_id);
		return sum;
	}

	public void huikuan(Huikuan huikuan) {
		this.accountDao.addHuikuanMoney(huikuan);
	}

	public PageDataList huikuanlist(int page, SearchParam param) {
		PageDataList plist = new PageDataList();
		int total = this.accountDao.gethuikuanCount(param);
		Page p = new Page(total, page);
		plist.setPage(p);
		List<HuikuanModel> list = this.accountDao.huikuanlist(p.getStart(), p.getPernum(), param);
		plist.setList(list);
		return plist;
	}

	public List<HuikuanModel> huikuanlist(SearchParam param) {
		List<HuikuanModel> list = this.accountDao.huikuanlist(param);
		return list;
	}

	public HuikuanModel viewHuikuan(int id) {
		HuikuanModel huikuan = this.accountDao.viewhuikuan(id);
		return huikuan;
	}

	public HuikuanModel verifyHuikuan(HuikuanModel huikuan, AccountLog log,
			AccountRecharge recharge) {
		//double account = Double.parseDouble(huikuan.getHuikuan_money());
		double award = Double.parseDouble(huikuan.getHuikuan_award());
		double money = award;
		if (Global.getWebid().equals("jsy")) {
			this.accountDao.verifyhuikuan(huikuan);
		} else {
			if (huikuan.getStatus().equals("1")) {
				this.accountDao.updateAccount(NumberUtils.format2(money),
						NumberUtils.format2(money), 0.0D, huikuan.getUser_id());
				log.setRemark("申请回款续投成功,生成回款续投奖励,账户资金增加"
						+ NumberUtils.format2(money) + "元");
				recharge.setStatus(1);
				recharge.setRemark("申请回款续投成功,生成回款续投奖励,账户资金增加"
						+ NumberUtils.format2(money) + "元");
			} else {
				log.setRemark("申请回款续投失败");
				recharge.setStatus(2);
				recharge.setRemark("申请回款续投失败");
			}
			Account act = this.accountDao.getAccount(huikuan.getUser_id());
			log.setMoney(NumberUtils.format2(money));
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			recharge.setMoney(NumberUtils.format2(money));
			this.accountLogDao.addAccountLog(log);
			this.rechargeDao.addRecharge(recharge);
			this.accountDao.verifyhuikuan(huikuan);
		}
		return huikuan;
	}

	public List[] addBatchRecharge(List[] data) {
		List[] retData = new List[data.length];
		for (int i = 0; i < data.length; ++i) {
			List<String> row = data[i];
			if (row == null)
				continue;
			if (row.size() < 2) {
				continue;
			}
			String username = StringUtils.isNull(row.get(0));
			double money = NumberUtils
					.getDouble(StringUtils.isNull(row.get(1)));
			User u = this.userDao.getUserByUsername(username);
			if (u == null) {
				row.add("用户未找到！");
				retData[i] = row;
			} else {
				AccountRecharge r = new AccountRecharge();
				r.setUser_id(u.getUser_id());
				r.setMoney(money);
				r.setTotal(money);
				r.setFee("0");
				r.setType("2");
				r.setAddtime(DateUtils.getNowTimeStr());
				r.setStatus(1);
				r.setTrade_no(StringUtils.generateTradeNO(u.getUser_id(), "E"));
				r.setPayment("48");
				this.rechargeDao.addRecharge(r);

				this.accountDao.updateAccount(money, money, 0.0D, u.getUser_id());

				AccountLog accountLog = new AccountLog(u.getUser_id(), Constant.RECHARGE, 1L, DateUtils.getNowTimeStr(), "");
				Account act = this.accountDao.getAccount(u.getUser_id());
				accountLog.setMoney(r.getMoney());
				accountLog.setTotal(act.getTotal());
				accountLog.setUse_money(act.getUse_money());
				accountLog.setNo_use_money(act.getNo_use_money());
				accountLog.setRemark("投标奖励金额发放" + r.getMoney() + "元");
				this.accountLogDao.addAccountLog(accountLog);
				row.add("导入成功！");
				retData[i] = row;
			}
		}
		return data;
	}

	public PageDataList getTenderLogList(int page, SearchParam param) {
		int total = this.accountLogDao.getTenderLogCount(param);
		Page p = new Page(total, page);
		List<DetailTender> list = this.accountLogDao.getTenderLogList(p.getStart(), p.getPernum(), param);
		return new PageDataList(p, list);
	}

	public PageDataList getHongBaoList(int pages, SearchParam param) {
		int total = this.accountDao.getHongBaoListCount(param);
		Page page = new Page(total, pages);
		List<HongBaoModel> list = this.accountDao.getHongBaoList(page.getStart(), page.getPernum(), param);
		return new PageDataList(page, list);
	}

	public List<HongBaoModel> getHongBaoList(SearchParam param) {
		List<HongBaoModel> list = this.accountDao.getHongBaoList(param);
		return list;
	}

	public List<DetailTender> getTenderLogList(SearchParam param) {
		List<DetailTender> list = this.accountLogDao.getTenderLogList(param);
		return list;
	}

	public AccountBank modifyBankByAccount(AccountBank bank, String bankaccount) {
		return this.accountDao.updateBankByAccount(bank, bankaccount);
	}

	public PageDataList getAccountReconciliationList(int pages,
			SearchParam param) {
		int total = this.accountDao.getAccountReconciliationListCount(param);
		Page page = new Page(total, pages);
		List<AccountReconciliationModel> list = this.accountDao.getAccountReconciliationList(page.getStart(), page.getPernum(), param);
		PageDataList pageDataList = new PageDataList(page, list);
		return pageDataList;
	}

	public AccountRecharge getMinRechargeMoney(long user_id) {
		AccountRecharge accountRecharge = new AccountRecharge();
		accountRecharge = this.rechargeDao.getMinRecharge(user_id, "1");
		return accountRecharge;
	}

	public void updateAccountRechargeYes_no(AccountRecharge accountRecharge) {
		this.rechargeDao.updateAccountRechargeYes_no(accountRecharge);
	}

	public PageDataList operationLog(int pages, SearchParam param) {
		int total = this.operationLogDao.getOperationLogCount(param);
		Page page = new Page(total, pages);
		List<OperationLogModel> list = this.operationLogDao.getOperationLogList(page.getStart(), page.getPernum(), param);
		PageDataList pageDataList = new PageDataList(page, list);
		return pageDataList;
	}

	public List<OperationLogModel> operationLog(SearchParam param) {
		List<OperationLogModel> list = this.operationLogDao.getOperationLogList(param);
		return list;
	}

	public void addOperationLog(OperationLog log) {
		this.operationLogDao.addOperationLog(log);
	}

	public List<AccountLogModel> getAccountLogList(long user_id, SearchParam param) {
		List<AccountLogModel> list = this.accountLogDao.getAccountLogList(user_id, param);
		return list;
	}

	public double getAccountLogSum(String type) {
		return this.accountLogDao.getAccountLogSum(type);
	}

	public double getAllSumLateMoney(int status) {
		return this.repaymentDao.getAllSumLateMoney(status);
	}

	public double getRepaymentAccount(int status) {
		return this.collectionDao.getRepaymentAccount(status);
	}

	public List<AccountLogSumModel> getAccountLogSumWithMonth(SearchParam param) {
		return this.accountLogDao.getAccountLogSumWithMonth(param);
	}

	public int getAccountBankCount(long user_id) {
		return this.accountDao.getAccountBankCount(user_id);
	}

	public double getAllLateSumWithNoRepaid() {
		return this.repaymentDao.getAllLateSumWithNoRepaid();
	}

	public double getAllLateSumWithYesRepaid() {
		return this.repaymentDao.getAllLateSumWithYesRepaid();
	}

	public List<OnlineBankModel> onlineBank(long paymentInterfaceId) {
		return this.accountDao.getOnlineBankList(paymentInterfaceId);
	}

	public List<PaymentInterface> paymentInterface(int init) {
		return this.accountDao.getPayInterfaceList(init);
	}

	public List<AccountBank> downBank() {
		return this.accountDao.getDownRechargeBankList(EnumTroubleFund.ZERO.getValue(), EnumTroubleFund.ZERO.getValue());
	}
}