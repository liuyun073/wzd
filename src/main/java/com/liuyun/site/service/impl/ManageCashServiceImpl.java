package com.liuyun.site.service.impl;

import com.liuyun.site.common.enums.EnumTroubleFund;
import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.dao.AccountDao;
import com.liuyun.site.dao.AccountLogDao;
import com.liuyun.site.dao.CashDao;
import com.liuyun.site.dao.HongBaoDao;
import com.liuyun.site.dao.OperationLogDao;
import com.liuyun.site.dao.ProtocolDao;
import com.liuyun.site.dao.UserDao;
import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountBank;
import com.liuyun.site.domain.AccountCash;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.Advanced;
import com.liuyun.site.domain.HongBao;
import com.liuyun.site.domain.Huikuan;
import com.liuyun.site.domain.OnlineBank;
import com.liuyun.site.domain.OperationLog;
import com.liuyun.site.domain.PaymentInterface;
import com.liuyun.site.domain.Protocol;
import com.liuyun.site.domain.User;
import com.liuyun.site.exception.AccountException;
import com.liuyun.site.model.DetailUser;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.account.AccountSumModel;
import com.liuyun.site.service.ManageCashService;
import com.liuyun.site.tool.Page;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import java.util.List;

public class ManageCashServiceImpl implements ManageCashService {
	AccountDao accountDao;
	AccountLogDao accountLogDao;
	CashDao cashDao;
	HongBaoDao hongBaoDao;
	UserDao userDao;
	ProtocolDao protocolDao;
	OperationLogDao operationLogDao;

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

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public HongBaoDao getHongBaoDao() {
		return this.hongBaoDao;
	}

	public void setHongBaoDao(HongBaoDao hongBaoDao) {
		this.hongBaoDao = hongBaoDao;
	}

	public AccountDao getAccountDao() {
		return this.accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public CashDao getCashDao() {
		return this.cashDao;
	}

	public void setCashDao(CashDao cashDao) {
		this.cashDao = cashDao;
	}

	public AccountLogDao getAccountLogDao() {
		return this.accountLogDao;
	}

	public void setAccountLogDao(AccountLogDao accountLogDao) {
		this.accountLogDao = accountLogDao;
	}

	public PageDataList getUserAccount(int page, SearchParam param) {
		int total = this.accountDao.getUserAccountCount(param);
		Page p = new Page(total, page);
		List<AccountSumModel> list = this.accountDao.getUserAcount(p.getStart(), p.getPernum(),
				param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public List<AccountSumModel> getUserAccount(SearchParam param) {
		List<AccountSumModel> list = this.accountDao.getUserAcount(param);
		return list;
	}

	public PageDataList getAllCash(int page, SearchParam param) {
		int total = this.cashDao.getAllCashCount(param);
		Page p = new Page(total, page);
		List<AccountCash> list = this.cashDao.getAllCashList(p.getStart(), p.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public List<AccountCash> getAllCash(SearchParam param) {
		List<AccountCash> list = this.cashDao.getAllCashList(param);
		return list;
	}

	public AccountCash getAccountCash(long id) {
		return this.cashDao.getAccountCash(id);
	}

	public void verifyCash(AccountCash cash, AccountLog log,
			OperationLog operationLog) {
		double money = NumberUtils.getDouble(cash.getTotal());
		DetailUser verifydetailUser = this.userDao
				.getDetailUserByUserid(operationLog.getVerify_user());
		DetailUser detailUser = this.userDao.getDetailUserByUserid(cash
				.getUser_id());
		if (Global.getWebid().equals("wzdai")) {
			AccountLog newlog = new AccountLog();
			if (cash.getStatus() == 1) {
				this.accountDao.updateAccount(-money, 0.0D, -money, cash.getUser_id());
				log.setRemark("提现成功，扣除资金" + cash.getTotal() + "元");
				log.setType(Constant.CASH_SUCCESS);
				log.setAddtime(DateUtils.getNowTimeStr());
			} else {
				this.accountDao.updateAccount(0.0D, money, -money, cash.getUser_id());
				log.setRemark("提现提现审核失败，返回资金" + cash.getTotal() + "元");
				log.setType(Constant.CASH_FAIL);
				log.setAddtime(DateUtils.getNowTimeStr());
			}
			this.cashDao.updateCash(cash);
			Account act = this.accountDao.getAccount(cash.getUser_id());
			log.setMoney(money);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			this.accountLogDao.addAccountLog(log);
			if (cash.getStatus() == 1) {
				this.accountDao.updateAccount(0.0D, 0.0D, 0.0D, cash.getUser_id(), -cash.getHongbao(), 1);
			}
			Account accout = this.accountDao.getAccount(cash.getUser_id());
			User user = this.userDao.getUserById(cash.getUser_id());
			HongBao hongbao = new HongBao(Constant.HONGBAO_LESS, log.getAddtime(), log.getAddip(), "审核提现成功,ID为" + cash.getId()
					+ "的用户" + user.getUsername() + "提现账号为" + cash.getAccount()
					+ ",提现成功金额为" + cash.getCredited() + ",扣除提现红包"
					+ cash.getHongbao() + "元", cash.getUser_id(), cash
					.getHongbao());
			this.hongBaoDao.addHongbao(hongbao);
		} else if (Global.getWebid().equals("jsy")) {
			if (cash.getStatus() == 1) {
				this.accountDao.updateAccount(-money, 0.0D, -money, cash
						.getUser_id());
				log.setRemark("提现成功，扣除资金" + cash.getTotal() + "元");
				log.setType(Constant.CASH_SUCCESS);
				log.setAddtime(DateUtils.getNowTimeStr());
				Account account = this.accountDao.getAccount(cash.getUser_id());
				double tender_award = account.getTotal_tender_award();
				if (tender_award >= money) {
					account.setTotal_tender_award(tender_award - money);
					this.accountDao.updateAccount(account);
				} else {
					account.setTotal_tender_award(0.0D);
					this.accountDao.updateAccount(account);
				}

				operationLog.setType(Constant.CASH_SUCCESS);
				operationLog.setOperationResult(verifydetailUser.getTypename()
						+ "（" + operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核后台提现" + money
						+ "元成功");

				Protocol protocol = new Protocol(0L, Constant.CASH_PROTOCOL,
						log.getAddtime(), log.getAddip(), "");
				protocol.setRemark(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核提现"
						+ detailUser.getUsername() + "用户" + money
						+ "元成功，生成提现委托书");
				protocol.setMoney(money);
				protocol.setUser_id(detailUser.getUser_id());
				protocol.setBank_account(cash.getAccount());
				protocol.setBank_branch(cash.getBranch());
				this.protocolDao.addProtocol(protocol);
			} else {
				this.accountDao.updateAccount(0.0D, money, -money, cash
						.getUser_id());
				log.setRemark("提现提现审核失败，返回资金" + cash.getTotal() + "元");
				log.setType(Constant.CASH_FAIL);
				log.setAddtime(DateUtils.getNowTimeStr());

				operationLog.setType(Constant.CASH_FAIL);
				operationLog.setOperationResult(verifydetailUser.getTypename()
						+ "（" + operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核后台提现" + money
						+ "元失败");
			}

			this.cashDao.updateCash(cash);
			Account act = this.accountDao.getAccount(cash.getUser_id());
			log.setMoney(money);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			this.accountLogDao.addAccountLog(log);

			this.operationLogDao.addOperationLog(operationLog);
		} else {
			if (cash.getStatus() == 1) {
				this.accountDao.updateAccount(-money, 0.0D, -money, cash
						.getUser_id());
				log.setRemark("提现成功，扣除资金" + cash.getTotal() + "元");
				log.setType(Constant.CASH_SUCCESS);
				log.setAddtime(DateUtils.getNowTimeStr());
				Account account = this.accountDao.getAccount(cash.getUser_id());
				double tender_award = account.getTotal_tender_award();
				if (tender_award >= money) {
					account.setTotal_tender_award(tender_award - money);
					this.accountDao.updateAccount(account);
				} else {
					account.setTotal_tender_award(0.0D);
					this.accountDao.updateAccount(account);
				}

				operationLog.setType(Constant.CASH_SUCCESS);
				operationLog.setOperationResult(verifydetailUser.getTypename()
						+ "（" + operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核后台提现" + money
						+ "元成功");
			} else {
				this.accountDao.updateAccount(0.0D, money, -money, cash
						.getUser_id());
				log.setRemark("提现提现审核失败，返回资金" + cash.getTotal() + "元");
				log.setType(Constant.CASH_FAIL);
				log.setAddtime(DateUtils.getNowTimeStr());

				operationLog.setType(Constant.CASH_FAIL);
				operationLog.setOperationResult(verifydetailUser.getTypename()
						+ "（" + operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核后台提现" + money
						+ "元失败");
			}

			this.cashDao.updateCash(cash);
			Account act = this.accountDao.getAccount(cash.getUser_id());
			log.setMoney(money);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			this.accountLogDao.addAccountLog(log);

			this.operationLogDao.addOperationLog(operationLog);
			try {
				Huikuan h = this.accountDao.getHuikuanByCashid(cash.getId());
				if (h != null) {
					h.setStatus("" + cash.getStatus());
					if (cash.getStatus() == 1)
						h.setRemark("回款提现审核成功,额度扣除！");
					else {
						h.setRemark("回款提现审核失败,额度返回！");
					}
					this.accountDao.verifyhuikuan(h);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void bankLog(OperationLog operationLog) {
		this.operationLogDao.addOperationLog(operationLog);
	}

	public PageDataList getUserOneDayAcount(int page, SearchParam param) {
		int total = this.accountDao.getUserOneDayAccountCount(param);
		Page p = new Page(total, page);
		List list = this.accountDao.getUserOneDayAcount(p.getStart(), p
				.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public List getUserOneDayAcount(SearchParam param) {
		List list = this.accountDao.getUserOneDayAcount(param);
		return list;
	}

	public PageDataList getTiChengAcount(int page, SearchParam param) {
		int total = this.accountDao.getTiChengAccountCount(param);
		Page p = new Page(total, page);
		List list = this.accountDao.getTiChengAccountList(p.getStart(), p
				.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public PageDataList getFriendTiChengAcount(int page, SearchParam param) {
		int total = this.accountDao.getFriendTiChengAccountCount(param);
		Page p = new Page(total, page);
		List list = this.accountDao.getFriendTiChengAccountList(p.getStart(), p
				.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public List getFriendTiChengAcount(SearchParam param) {
		List list = this.accountDao.getFriendTiChengAcount(param);
		return list;
	}

	public List getTiChengAcount(SearchParam param) {
		List list = this.accountDao.getTiChengAcount(param);
		return list;
	}

	public void rechargeDownLineBank(AccountBank bank, String ids) {
		if (StringUtils.isBlank(bank.getBank())) {
			throw new AccountException("银行开户银行不能为空！！！");
		}
		if (StringUtils.isBlank(bank.getAccount())) {
			throw new AccountException("银行账号不能为空！！！");
		}
		if (StringUtils.isBlank(bank.getBank_realname())) {
			throw new AccountException("银行法人不能为空！！！");
		}
		if (StringUtils.isBlank(bank.getBranch())) {
			throw new AccountException("银行开户行名称不能为空！！！");
		}
		if ("".equals(ids))
			this.accountDao.addBank(bank);
		else
			this.accountDao.updateBank(bank, EnumTroubleFund.FIRST.getValue());
	}

	public void OnLineBank(OnlineBank bank, String ids) {
		if ("".equals(ids))
			this.accountDao.addOnlineBank(bank);
		else
			this.accountDao.updateOnlineBank(bank);
	}

	public void PaymemtInterface(PaymentInterface paymentInterface, String ids) {
		if ("".equals(ids))
			this.accountDao.addPayInterface(paymentInterface);
		else
			this.accountDao.updatePayInterface(paymentInterface);
	}

	public AccountBank getDownLineBank(int id) {
		AccountBank bank = this.accountDao.getDownRechargeBank(id);
		return bank;
	}

	public PageDataList getList(int page) {
		int total = this.accountDao.getDownRechargeBankCount();
		Page p = new Page(total, page);
		List list = this.accountDao.getDownRechargeBankList(p.getStart(), p
				.getPernum());
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public double getSumTotal() {
		return this.cashDao.getSumTotal();
	}

	public double getSumUseMoney() {
		return this.cashDao.getSumUseMoney();
	}

	public double getSumNoUseMoney() {
		return this.cashDao.getSumNoUseMoney();
	}

	public double getSumCollection() {
		return this.cashDao.getSumCollection();
	}

	public void getAdvanced_insert(Advanced advanced) {
		this.cashDao.getAdvance_insert(advanced);
	}

	public void getAdvanced_update(Advanced advanced) {
		this.cashDao.getAdvance_update(advanced);
	}

	public List getAdvancedList() {
		List list = this.cashDao.getAdvanceList();
		return list;
	}

	public void addPayInterface(PaymentInterface paymentInterface) {
		this.accountDao.addPayInterface(paymentInterface);
	}

	public List getPayInterfaceList(int init) {
		List list = this.accountDao.getPayInterfaceList(init);

		return list;
	}

	public PageDataList getOnlineBankList(int page) {
		int total = this.accountDao.getOnlineBankCount();
		Page p = new Page(total, page);
		List list = this.accountDao.getOnlineBankList(EnumTroubleFund.ZERO
				.getValue());
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public OnlineBank getOnlineBank(int id) {
		OnlineBank onlineBank = new OnlineBank();
		onlineBank = this.accountDao.getOnlineBank(id);
		return onlineBank;
	}

	public PaymentInterface updatePayInterface(PaymentInterface p) {
		return this.accountDao.updatePayInterface(p);
	}

	public OnlineBank addOnlineBank(OnlineBank onlineBank) {
		return this.accountDao.addOnlineBank(onlineBank);
	}

	public OnlineBank updateOnlineBank(OnlineBank p) {
		return this.accountDao.updateOnlineBank(p);
	}

	public PaymentInterface getPayInterface(int id) {
		PaymentInterface paymentInterface = new PaymentInterface();
		paymentInterface = this.accountDao.getPaymentInterface(id);
		return paymentInterface;
	}
}