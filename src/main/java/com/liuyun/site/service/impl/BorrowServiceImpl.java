package com.liuyun.site.service.impl;

import com.liuyun.site.common.enums.EnumIntegralTypeName;
import com.liuyun.site.common.enums.EnumRuleBorrowCategory;
import com.liuyun.site.common.enums.EnumRuleNid;
import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.dao.AccountDao;
import com.liuyun.site.dao.AccountLogDao;
import com.liuyun.site.dao.BonusDao;
import com.liuyun.site.dao.BorrowAutoDao;
import com.liuyun.site.dao.BorrowDao;
import com.liuyun.site.dao.BorrowFlowDao;
import com.liuyun.site.dao.CashDao;
import com.liuyun.site.dao.CollectionDao;
import com.liuyun.site.dao.LateBorrowLogDao;
import com.liuyun.site.dao.LotteryDao;
import com.liuyun.site.dao.MessageDao;
import com.liuyun.site.dao.OperationLogDao;
import com.liuyun.site.dao.ProtocolDao;
import com.liuyun.site.dao.RepaymentDao;
import com.liuyun.site.dao.RuleDao;
import com.liuyun.site.dao.TenderDao;
import com.liuyun.site.dao.UserAmountDao;
import com.liuyun.site.dao.UserCreditDao;
import com.liuyun.site.dao.UserDao;
import com.liuyun.site.dao.UserLogDao;
import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.Advanced;
import com.liuyun.site.domain.BonusApr;
import com.liuyun.site.domain.Borrow;
import com.liuyun.site.domain.BorrowAuto;
import com.liuyun.site.domain.BorrowFlow;
import com.liuyun.site.domain.Collection;
import com.liuyun.site.domain.CreditType;
import com.liuyun.site.domain.Message;
import com.liuyun.site.domain.OperationLog;
import com.liuyun.site.domain.Protocol;
import com.liuyun.site.domain.Repayment;
import com.liuyun.site.domain.Reservation;
import com.liuyun.site.domain.RunBorrow;
import com.liuyun.site.domain.Tender;
import com.liuyun.site.domain.TenderAddLotteryTimes;
import com.liuyun.site.domain.TenderAwardYesAndNo;
import com.liuyun.site.domain.UserAmount;
import com.liuyun.site.domain.UserAmountLog;
import com.liuyun.site.domain.UserCredit;
import com.liuyun.site.domain.UserCreditLog;
import com.liuyun.site.exception.BorrowException;
import com.liuyun.site.exception.NoEnoughInterestBorrowException;
import com.liuyun.site.model.BorrowNTender;
import com.liuyun.site.model.BorrowTender;
import com.liuyun.site.model.DetailCollection;
import com.liuyun.site.model.DetailTender;
import com.liuyun.site.model.DetailUser;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.ProtocolModel;
import com.liuyun.site.model.RankModel;
import com.liuyun.site.model.RuleModel;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserBorrowModel;
import com.liuyun.site.model.UserCreditModel;
import com.liuyun.site.model.account.AccountModel;
import com.liuyun.site.model.account.RepaySummary;
import com.liuyun.site.model.borrow.BorrowHelper;
import com.liuyun.site.model.borrow.BorrowModel;
import com.liuyun.site.model.borrow.FastExpireModel;
import com.liuyun.site.model.borrow.ReservationModel;
import com.liuyun.site.model.invest.CollectionList;
import com.liuyun.site.model.invest.InvestBorrowList;
import com.liuyun.site.model.invest.InvestBorrowModel;
import com.liuyun.site.service.BorrowService;
import com.liuyun.site.tool.Page;
import com.liuyun.site.tool.credit.CreditCount;
import com.liuyun.site.tool.interest.InterestCalculator;
import com.liuyun.site.tool.interest.MonthInterest;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class BorrowServiceImpl implements BorrowService {
	private Logger logger = Logger.getLogger(BorrowServiceImpl.class);

	BorrowDao borrowDao;
	TenderDao tenderDao;
	CollectionDao collectionDao;
	BorrowAutoDao borrowAutoDao;
	AccountDao accountDao;
	AccountLogDao accountLogDao;
	UserAmountDao userAmountDao;
	BonusDao bonusDao;
	RepaymentDao repaymentDao;
	BorrowFlowDao borrowFlowDao;
	MessageDao messageDao;
	UserLogDao userLogDao;
	CashDao cashDao;
	OperationLogDao operationLogDao;
	UserDao userDao;
	ProtocolDao protocolDao;
	LotteryDao lotteryDao;
	UserCreditDao userCreditDao;
	RuleDao ruleDao;
	LateBorrowLogDao lateBorrowLogDao;
	

	public List<UserBorrowModel> getList() {
		String site_id = StringUtils.isNull(Global.getValue("webid"));
		BorrowModel model = new BorrowModel();

		model.setStatus(Constant.STATUS_INDEX);

		SearchParam param = new SearchParam();
		param.setOrder(Constant.ORDER_INDEX);
		model.setParam(param);
		List<UserBorrowModel> indexList = new ArrayList<UserBorrowModel>();
		if (StringUtils.isNull(site_id).equals("jsy")) {
			List<UserBorrowModel> newbList = new ArrayList<UserBorrowModel>();
			try {
				model.setStatus(Constant.STATUS_TENDER_INDEX);
				newbList = this.borrowDao.getList(model);
			} catch (Exception e) {
				this.logger.error(e.getMessage());
			}

			indexList.addAll(newbList);
		}
		String index_borrow = Global.getValue("index_borrow");
		String[] borrows = new String[0];

		if (index_borrow != null) {
			borrows = index_borrow.split(",");
		}
		if ((!index_borrow.isEmpty()) && (borrows.length > 0)) {
			for (int i = 0; i < borrows.length; ++i) {
				long start = System.currentTimeMillis();
				List<UserBorrowModel> bList = new ArrayList<UserBorrowModel>();
				try {
					String webid = Global.getValue("webid");
					if ((webid.equals("zrzb")) || (webid.equals("xdcf"))) {
						model.setStatus(Constant.STATUS_ZR_INDEX);
						if (NumberUtils.getInt(borrows[i]) == Constant.TYPE_FLOW) {
							Page p = new Page();
							p.setStart(0);
							int flownum = NumberUtils.getInt(Global.getValue("index_flownum"));
							flownum = (flownum < 1) ? 10 : flownum;
							p.setPernum(flownum);
							model.setPager(p);
						} else {
							model.setPager(null);
						}
					} else if (webid.equals("huidai")) {
						model.setStatus(Constant.STATUS_INDEX);
						if (NumberUtils.getInt(borrows[i]) == Constant.TYPE_FLOW) {
							Page p = new Page();
							p.setStart(0);
							int flownum = NumberUtils.getInt(Global.getValue("index_flownum"));
							flownum = (flownum < 1) ? 10 : flownum;
							p.setPernum(flownum);
							model.setPager(p);
						} else {
							model.setPager(null);
						}
					} else if (webid.equals("mdw")) {
						model.setStatus(Constant.STATUS_md_INDEX);
						if (NumberUtils.getInt(borrows[i]) == Constant.TYPE_FLOW) {
							Page p = new Page();
							p.setStart(0);
							int flownum = NumberUtils.getInt(Global.getValue("index_flownum"));
							flownum = (flownum < 1) ? 10 : flownum;
							p.setPernum(flownum);
							model.setPager(p);
						} else {
							model.setPager(null);
						}
					} else if (webid.equals("jsy")) {
						model.setStatus(Constant.STATUS_JSY_INDEX);
					}
					bList = this.borrowDao.getList(BorrowHelper.getHelper(NumberUtils.getInt(borrows[i]), model));
				} catch (Exception e) {
					this.logger.error(e.getMessage());
				}
				indexList.addAll(bList);
				long end = System.currentTimeMillis();
				this.logger.info("GetList Cost Time:" + (end - start));
			}
		} else {
			List<UserBorrowModel> bList = new ArrayList<UserBorrowModel>();
			model.setStatus(Constant.STATUS_INDEX);
			param.setOrder(Constant.ORDER_BORROW_VERIFY_TIME_DOWN);
			model.setParam(param);
			bList = this.borrowDao.getList(BorrowHelper.getHelper(100, model));
			indexList = bList;
		}

		return indexList;
	}

	public List<UserBorrowModel> getList(int type) {
		BorrowModel model = new BorrowModel();
		model.setStatus(1);
		List<UserBorrowModel> list = this.borrowDao.getList(BorrowHelper.getHelper(type, model));
		return list;
	}

	public List<UserBorrowModel> getList(int type, int status) {
		BorrowModel model = new BorrowModel();
		model.setStatus(status);
		List<UserBorrowModel> list = this.borrowDao.getList(BorrowHelper.getHelper(type, model));
		return list;
	}

	public PageDataList getList(BorrowModel wrapModel, int perNum) {
		BorrowModel model = wrapModel.getModel();
		PageDataList borrowList = new PageDataList();
		int total = this.borrowDao.count(model);
		Page p = new Page(total, model.getPageStart(), model.getPageNum());
		model.setPager(p);
		List<UserBorrowModel> list = this.borrowDao.getList(model);
		borrowList.setList(list);
		borrowList.setPage(p);
		return borrowList;
	}

	public PageDataList getList(BorrowModel wrapModel) {
		BorrowModel model = wrapModel.getModel();
		PageDataList borrowList = new PageDataList();
		int total = this.borrowDao.count(model);
		Page p = new Page(total, model.getPageStart(), model.getPageNum());
		model.setPager(p);
		List<UserBorrowModel> list = this.borrowDao.getList(model);
		borrowList.setList(list);
		borrowList.setPage(p);
		return borrowList;
	}

	public PageDataList getBorrowList(String type, int startPage, SearchParam param) {
		PageDataList borrowList = new PageDataList();
		int total = this.borrowDao.getBorrowCount(type, param);
		Page p = new Page(total, startPage);
		List<Repayment> list = new ArrayList<Repayment>();
		List<Repayment> newlist = this.borrowDao.getBorrowList(type, p.getStart(), p.getPernum(), param);
		list.addAll(newlist);
		if ((newlist.size() > 0) && (newlist.size() < 10)) {
			List<Repayment> flowlist = this.borrowDao.getFlowBorrowList(type, 0, p.getPernum() - newlist.size(), param);
			list.addAll(flowlist);
		} else if (newlist.size() == 0) {
			int newTotal = this.borrowDao.getBorrowCount(null, param);
			List<Repayment> flowlist = this.borrowDao.getFlowBorrowList(type, p.getStart() - newTotal, p.getPernum(), param);
			list.addAll(flowlist);
		}
		borrowList.setList(list);
		borrowList.setPage(p);
		return borrowList;
	}

	public BorrowModel getBorrow(long id) {
		BorrowModel b = this.borrowDao.getBorrowById(id);
		return b;
	}

	public Tender getTenderById(long tid) {
		Tender t = this.tenderDao.getTenderById(tid);
		return t;
	}

	public UserBorrowModel getUserBorrow(long id) {
		return this.borrowDao.getUserBorrowById(id);
	}

	public List<BorrowTender> getTenderList(long borrow_id) {
		String webid = StringUtils.isNull(Global.getValue("webid"));
		if ((webid.equals(Constant.ZRZB)) || (webid.equals(Constant.MDW))) {
			return this.tenderDao.getTenderListByBorrowid(borrow_id, 15);
		}
		return this.tenderDao.getTenderListByBorrowid(borrow_id);
	}

	public List<BorrowTender> getTenderList(long borrow_id, long user_id) {
		return this.tenderDao.getTenderListByBorrow(borrow_id, user_id);
	}

	public List<BorrowTender> getSuccessTenderList() {
		return this.tenderDao.getTenderList();
	}

	public List<BorrowTender> getNewTenderList() {
		return this.tenderDao.getNewTenderList();
	}

	public PageDataList getTenderList(long borrow_id, int page, SearchParam param) {
		int total = this.tenderDao.getTenderCountByBorrowid(borrow_id, param);
		int pagenum = Global.getInt("borrow_ajaxTenderListPagenum");
		String webid = Global.getValue("webid");
		if (StringUtils.isNull(webid).equals("ssjb")) {
			pagenum = (int) (Math.pow(2.0D, 15.0D) - 1.0D);
		} else if (pagenum < 10) {
			pagenum = 10;
		}
		Page p = new Page(total, page, pagenum);
		List<BorrowTender> list = this.tenderDao.getTenderListByBorrowid(borrow_id, p.getStart(), p.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public void addBorrow(BorrowModel borrow, AccountLog log) {
		borrow.skipTrial();
		BorrowModel model = borrow.getModel();

		if (model.getBorrowType() == Constant.TYPE_SECOND) {
			double interest = borrow.calculateInterest();
			double fee = borrow.calculateBorrowFee();
			double award = borrow.calculateBorrowAward();
			double freezeVal = interest + fee + award;

			AccountModel act = this.accountDao.getAccount(model.getUser_id());
			if (act.getUse_money() < freezeVal) {
				throw new NoEnoughInterestBorrowException();
			}
			act.freeze(freezeVal);
			this.accountDao.updateAccount(act);
			log.setMoney(freezeVal);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			log.setRemark("发布标秒标，冻结资金" + NumberUtils.format2(freezeVal) + "元");
		}

		if ((model.getBorrowType() == Constant.TYPE_CREDIT) || (model.getBorrowType() == Constant.TYPE_STUDENT)) {
			double amount = NumberUtils.getDouble(model.getAccount());
			UserAmount ua = this.userAmountDao.getUserAmount(model.getUser_id());
			this.userAmountDao.updateCreditAmount(0.0D, -amount, amount, ua);
			UserAmountLog amountLog = new UserAmountLog();
			amountLog.setUser_id(ua.getUser_id());
			amountLog.setAccount(Double.toString(amount));
			amountLog.setAccount_all(Double.toString(ua.getCredit()));
			amountLog.setAccount_use(Double.toString(ua.getCredit_use() - amount));
			amountLog.setAccount_nouse(Double.toString(ua.getCredit_nouse() + amount));
			amountLog.setType("");
			amountLog.setAmount_type("");
			amountLog.setRemark("扣除");
			amountLog.setAddip("");
			amountLog.setRemark("");
			amountLog.setAddtime("");
			this.userAmountDao.addAmountLog(amountLog);
		}
		if (model.getBorrowType() == Constant.TYPE_PROPERTY) {
			RepaySummary repay = this.accountDao.getRepaySummary(model.getUser_id());
			Account act = this.accountDao.getAccount(model.getUser_id());
			double ownMoney = act.getTotal() - repay.getRepayTotal();

			String sql = "SELECT SUM(account) FROM dw_borrow WHERE user_id=? AND STATUS=1 AND is_jin=1";
			double jinAccount = this.accountDao.getCount(sql, model.getUser_id());

			if (ownMoney - jinAccount < NumberUtils.getDouble(model
					.getAccount())) {
				throw new BorrowException("对不起，超出您可用的净值额度，发标失败！！");
			}
		}
		if (model.getBorrowType() == Constant.TYPE_FLOW) {
			int flow_money = model.getFlow_money();
			long account = NumberUtils.getLong(model.getAccount());
			if (account <= 0L) {
				throw new BorrowException("借款金额必须是大于0的整数！");
			}
			if (flow_money <= 0) {
				throw new BorrowException("每份金额必须是大于0的整数！");
			}
			if (account % flow_money != 0L) {
				throw new BorrowException("借款金额必须是每份金额的整数倍！");
			}
			model.setFlow_money(flow_money);
			model.setFlow_count((int) (account / flow_money));
			model.setFlow_yescount(0);
		}

		this.borrowDao.addBorrow(model);
		if ((log != null) && (log.getMoney() > 0.0D))
			this.accountLogDao.addAccountLog(log);
	}

	public PageDataList getAllBorrowTenderList(int page, SearchParam param) {
		int total = this.tenderDao.getAllTenderCount(param);
		int pagenum = Global.getInt("borrow_ajaxTenderListPagenum");
		if (pagenum < 10)
			pagenum = 10;
		Page p = new Page(total, page, pagenum);
		System.out.println(p.getStart() + "++++++++++++++++++++++" + p.getPernum());
		List<BorrowNTender> list = this.tenderDao.getAllTenderList(p.getStart(), p.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public List<BorrowNTender> getAllBorrowTenderList(SearchParam param) {
		List<BorrowNTender> list = this.tenderDao.getAllTenderList(param);
		return list;
	}

	public void updateBorrow(Borrow borrow) {
		this.borrowDao.updateBorrow(borrow);
	}

	public void verifyBorrow(BorrowModel borrow, AccountLog log,
			OperationLog operationLog) {
		
		this.borrowDao.updateBorrow(borrow);
		String sendMsg = "";
		BorrowModel model = BorrowHelper.getHelper(borrow);
		DetailUser detailUser = this.userDao.getDetailUserByUserid(operationLog.getVerify_user());
		DetailUser newdetailUser = this.userDao.getDetailUserByUserid(operationLog.getUser_id());
		if ((borrow.getStatus() != 1) && (borrow.getStatus() != 19)) {
			if (model.getBorrowType() == Constant.TYPE_SECOND) {
				double interest = model.calculateInterest();
				double fee = model.calculateBorrowFee();
				double award = model.calculateBorrowAward();
				double freezeVal = interest + fee + award;
				AccountModel act = this.accountDao.getAccount(model.getModel().getUser_id());
				this.accountDao.updateAccount(0.0D, freezeVal, -freezeVal, model.getModel().getUser_id());
				log.setUser_id(borrow.getUser_id());
				log.setTo_user(1L);
				log.setMoney(freezeVal);
				log.setTotal(act.getTotal());
				log.setUse_money(act.getUse_money() + freezeVal);
				log.setNo_use_money(act.getNo_use_money() - freezeVal);
				log.setCollection(act.getCollection());
				sendMsg = "借款标初审不通过，解冻资金" + NumberUtils.format2(freezeVal) + "元";
				log.setRemark(sendMsg);
				this.accountLogDao.addAccountLog(log);
			} else if ((model.getBorrowType() == Constant.TYPE_CREDIT) || (model.getBorrowType() == Constant.TYPE_STUDENT)) {
				double amount = NumberUtils.getDouble(model.getModel().getAccount());
				UserAmount ua = this.userAmountDao.getUserAmount(model.getModel().getUser_id());
				if (ua == null) {
					this.logger.error("用户" + model.getModel().getUser_id() + "的信用账户不存在.");
					throw new BorrowException("用户" + model.getModel().getUser_id() + "的信用账户不存在.");
				}
				this.userAmountDao.updateCreditAmount(0.0D, amount, -amount, ua);
				UserAmountLog amountLog = new UserAmountLog();
				amountLog.setType("borrow_cancel");
				amountLog.setAmount_type("credit");
				amountLog.setAccount("" + amount);
				amountLog.setAccount_all(Double.toString(ua.getCredit_use()));
				amountLog.setAccount_use(Double.toString(ua.getCredit_use() + amount));
				amountLog.setAccount_nouse(Double.toString(ua.getCredit_nouse() - amount));
				amountLog.setRemark("借款标初审不通过");
				amountLog.setAddtime(DateUtils.getNowTimeStr());
				amountLog.setAddip("");
				sendMsg = "借款标初审不通过";
				this.userAmountDao.addAmountLog(amountLog);
			} else {
				sendMsg = "借款标初审不通过";
			}
			Message message = getSiteMessage(1L, sendMsg, sendMsg, borrow.getUser_id(), new Message());
			message.setAddtime(DateUtils.getNowTimeStr());
			this.messageDao.addMessage(message);

			operationLog.setType(Constant.BORROW_FIRST_VERIFY_FAIL);
			operationLog.setOperationResult(detailUser.getTypename() + "（"
					+ operationLog.getAddip() + "）用户名为"
					+ detailUser.getUsername() + "的操作员审核发标人用户名为"
					+ newdetailUser.getUsername() + "的借款标（标名为"
					+ borrow.getName() + "）初审失败");
			this.operationLogDao.addOperationLog(operationLog);
		} else {
			operationLog.setType(Constant.BORROW_FIRST_VERIFY_SUCCESS);
			operationLog.setOperationResult(detailUser.getTypename() + "（"
					+ operationLog.getAddip() + "）用户名为"
					+ detailUser.getUsername() + "的操作员审核发标人用户名为"
					+ newdetailUser.getUsername() + "的借款标（标名为"
					+ borrow.getName() + "）初审成功");
			this.operationLogDao.addOperationLog(operationLog);
		}
	}

	public void deleteBorrow(BorrowModel borrow, AccountLog log) {
		Borrow db = this.borrowDao.getBorrowById(borrow.getId());
		if (db == null) {
			throw new BorrowException("借款标的状态异常！");
		}
		this.borrowDao.updateBorrow(borrow);
		String sendMsg = "";
		BorrowModel model = BorrowHelper.getHelper(borrow);
		if (model.getBorrowType() == Constant.TYPE_SECOND) {
			double interest = model.calculateInterest();
			double fee = model.calculateBorrowFee();
			double award = model.calculateBorrowAward();
			double freezeVal = interest + fee + award;
			AccountModel act = this.accountDao.getAccount(model.getModel().getUser_id());
			this.accountDao.updateAccount(0.0D, freezeVal, -freezeVal, model.getModel().getUser_id());
			log.setUser_id(borrow.getUser_id());
			log.setTo_user(1L);
			log.setMoney(freezeVal);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money() + freezeVal);
			log.setNo_use_money(act.getNo_use_money() - freezeVal);
			log.setCollection(act.getCollection());
			sendMsg = "撤回秒标，解冻资金" + NumberUtils.format2(freezeVal) + "元";
			log.setRemark(sendMsg);
			this.accountLogDao.addAccountLog(log);
			Message message = getSiteMessage(1L, sendMsg, sendMsg, borrow.getUser_id(), new Message());
			message.setAddtime(DateUtils.getNowTimeStr());
			this.messageDao.addMessage(message);
		} else {
			if ((model.getBorrowType() != Constant.TYPE_CREDIT) && (model.getBorrowType() != Constant.TYPE_STUDENT))
				return;
			double amount = NumberUtils.getDouble(model.getModel().getAccount());
			UserAmount ua = this.userAmountDao.getUserAmount(model.getModel().getUser_id());
			if (ua == null) {
				this.logger.error("用户" + model.getModel().getUser_id() + "的信用账户不存在.");
				throw new BorrowException("用户" + model.getModel().getUser_id() + "的信用账户不存在.");
			}
			this.userAmountDao.updateCreditAmount(0.0D, amount, -amount, ua);
			UserAmountLog amountLog = new UserAmountLog();
			amountLog.setType("borrow_cancel");
			amountLog.setAmount_type("credit");
			amountLog.setAccount("" + amount);
			amountLog.setAccount_all(Double.toString(ua.getCredit_use()));
			amountLog.setAccount_use(Double.toString(ua.getCredit_use() + amount));
			amountLog.setAccount_nouse(Double.toString(ua.getCredit_nouse() - amount));
			amountLog.setRemark("用户撤回借款标.");
			amountLog.setAddtime(DateUtils.getNowTimeStr());
			amountLog.setAddip("");
			this.userAmountDao.addAmountLog(amountLog);
		}
	}

	public List<InvestBorrowModel> getSuccessListByUserid(long user_id, String type) {
		return this.borrowDao.getSuccessListByUserid(user_id, type);
	}

	public InvestBorrowList getSuccessListByUserid(long user_id, String type, int startPage, SearchParam param) {
		InvestBorrowList investList = new InvestBorrowList();
		int total = this.borrowDao.getSuccessBorrowCount(user_id, type, param);
		Page p = new Page(total, startPage);
		List<InvestBorrowModel> list = this.borrowDao.getSuccessBorrowList(user_id, type, p.getStart(), p.getPernum(), param);
		investList.setList(list);
		investList.setPage(p);
		return investList;
	}

	public List<UserBorrowModel> getSuccessListForIndex(String type, int pernum) {
		long start = System.currentTimeMillis();
		BorrowModel model = new BorrowModel();
		model.setStatus(Constant.STATUS_complete);
		Page p = new Page();
		p.setStart(0);
		p.setPernum(pernum);
		model.setPager(p);
		List<UserBorrowModel> list = this.borrowDao.getList(BorrowHelper.getHelper(Constant.TYPE_ALL, model));
		long end = System.currentTimeMillis();
		this.logger.info("GetSuccessListForIndex Cost Time:" + (end - start));
		return list;
	}

	public List<UserBorrowModel> getListByUserid(long user_id) {
		return this.borrowDao.getListByUserid(user_id);
	}

	public Tender addTender(Tender tender, BorrowModel borrow, Account act, AccountLog log, Protocol protocol) {
		BorrowModel model = borrow.getModel();
		String logRemark = log.getRemark();

		tenderLog("Begin tender service!", tender.getUser_id(), model.getId(), tender.getMoney(), tender.getAccount());
		Tender t = getValidTender(tender, borrow, log);
		double validAccount = NumberUtils.getDouble(t.getAccount());

		if (model.getBorrowType() != Constant.TYPE_DONATION) {
			InterestCalculator ic = borrow.interestCalculator(validAccount);
			List<MonthInterest> monthList = ic.getMonthList();

			List<Collection> collectList = new ArrayList<Collection>();
			int i = 0;
			for (MonthInterest mi : monthList) {
				Collection c = fillCollection(mi, t, ic);
				c.setOrder(i++);
				if (borrow.getBorrowType() == Constant.TYPE_FLOW) {
					String repayTime = "";
					if (model.getIsday() == 1)
						repayTime = DateUtils.rollDay(tender.getAddtime(), "" + model.getTime_limit_day());
					else {
						repayTime = DateUtils.rollMonth(tender.getAddtime(), model.getTime_limit());
					}
					c.setRepay_time(repayTime);
				}
				collectList.add(c);
			}
			this.collectionDao.addBatchCollection(collectList);

			double repayment_account = ic.getTotalAccount();
			double repayment_interest = repayment_account - NumberUtils.getDouble(t.getAccount());
			t.setRepayment_account("" + NumberUtils.format6(repayment_account));
			t.setInterest("" + NumberUtils.format6(repayment_interest));
			if (model.getBorrowType() == Constant.TYPE_FLOW) {
				t.setWait_account("" + NumberUtils.format6(repayment_account));
				t.setWait_interest("" + NumberUtils.format6(repayment_interest));
			}

			t = this.tenderDao.modifyTender(t);
		}

		int row = 0;
		try {
			row = this.accountDao.updateAccountNotZero(0.0D, -validAccount, validAccount, act.getUser_id());
		} catch (Exception e) {
			tenderLog("freeze account fail!", tender.getUser_id(), model.getId(), tender.getMoney(), tender.getAccount());
			this.logger.error(e.getMessage());

			if (row >= 1){
				tenderLog("freeze account success!", tender.getUser_id(), model.getId(), tender.getMoney(), tender.getAccount());
			}
			else {
				throw new BorrowException("投资人冻结投资款失败！请注意您的可用余额。");
			}
		} finally {
			if (row < 1)
				throw new BorrowException("投资人冻结投资款失败！请注意您的可用余额。");
		}
		
		act = this.accountDao.getAccount(act.getUser_id());
		log.setMoney(validAccount);
		log.setTotal(act.getTotal());
		log.setUse_money(act.getUse_money());
		log.setUser_id(act.getUser_id());
		log.setType(Constant.TENDER);
		log.setNo_use_money(act.getNo_use_money());
		log.setCollection(act.getCollection());
		log.setRemark(logRemark + "投资成功，冻结投资者的投标资金" + NumberUtils.format4(validAccount));
		this.accountLogDao.addAccountLog(log);

		if (model.getBorrowType() == Constant.TYPE_FLOW) {
			this.accountDao.updateAccount(0.0D, 0.0D, -validAccount, validAccount, act.getUser_id());
			act = this.accountDao.getAccount(act.getUser_id());
			log.setType(Constant.INVEST);
			log.setMoney(validAccount);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			log.setRemark(logRemark + "扣除流转标投资冻结款" + NumberUtils.format4(validAccount));
			this.accountLogDao.addAccountLog(log);

			double interest = borrow.calculateInterest(validAccount);
			this.accountDao.updateAccount(interest, 0.0D, 0.0D, interest, act.getUser_id());
			act = this.accountDao.getAccount(act.getUser_id());
			log.setType(Constant.WAIT_INTEREST);
			log.setMoney(interest);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			log.setRemark(logRemark + "生产待收利息" + NumberUtils.format4(interest));
			this.accountLogDao.addAccountLog(log);

			this.accountDao.updateAccount(validAccount, validAccount, 0.0D, 0.0D, model.getUser_id());
			act = this.accountDao.getAccount(model.getUser_id());
			log.setUser_id(model.getUser_id());
			log.setTo_user(tender.getUser_id());
			log.setType(Constant.BORROW_SUCCESS);
			log.setMoney(validAccount);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			log.setRemark(logRemark + "借款入账" + NumberUtils.format4(validAccount));
			this.accountLogDao.addAccountLog(log);

			String remark = "收到 标[" + logRemarkHtml(model) + "]投资奖励!!";
			double awardValue = 0.0D;
			if (model.getAward() == 1)
				awardValue = NumberUtils.getDouble(tender.getAccount()) * model.getPart_account() / 100.0D;
			else if (model.getAward() == 2)
				awardValue = model.getFunds() / NumberUtils.getDouble(model.getAccount()) * NumberUtils.getDouble(tender.getAccount());
			else {
				awardValue = 0.0D;
			}
			if (awardValue > 0.0D) {
				this.accountDao.updateAccount(awardValue, awardValue, 0.0D, 0.0D, tender.getUser_id());
				act = this.accountDao.getAccount(tender.getUser_id());
				log.setUser_id(tender.getUser_id());
				log.setTo_user(model.getUser_id());
				log.setType(Constant.AWARD_ADD);
				log.setMoney(awardValue);
				log.setTotal(act.getTotal());
				log.setUse_money(act.getUse_money());
				log.setNo_use_money(act.getNo_use_money());
				log.setCollection(act.getCollection());
				log.setRemark(remark);
				this.accountLogDao.addAccountLog(log);

				if (Global.getWebid().equals("jsy")) {
					DetailUser user = this.userDao.getDetailUserByUserid(act.getUser_id());
					protocol.setRemark("用户名为" + user.getUsername()
							+ "的用户投标（标名为" + model.getName() + "的借款标）奖励金额"
							+ awardValue + "元");
					protocol.setUser_id(tender.getUser_id());
					protocol.setMoney(awardValue);
					protocol.setProtocol_type(Constant.AWARD_PROTOCOL);
					protocol.setBorrow_id(tender.getBorrow_id());
					protocol.setPid(tender.getId());
					this.protocolDao.addProtocol(protocol);
				}
			}

			if (Global.getWebid().equals("jsy")) {
				DetailUser user = this.userDao.getDetailUserByUserid(act.getUser_id());
				protocol.setRemark("用户名为" + user.getUsername() + "的用户投标（标名为"
						+ model.getName() + "的借款标）金额" + tender.getAccount()
						+ "元");
				protocol.setProtocol_type(Constant.TENDER_PROTOCOL);
				protocol.setUser_id(tender.getUser_id());
				protocol.setBorrow_id(tender.getBorrow_id());
				protocol.setPid(tender.getId());
				protocol.setMoney(NumberUtils.getDouble(tender.getAccount()));
				this.protocolDao.addProtocol(protocol);
			}

			if (awardValue > 0.0D) {
				this.accountDao.updateAccount(-awardValue, -awardValue, 0.0D, 0.0D, model.getUser_id());
				act = this.accountDao.getAccount(model.getUser_id());
				log.setUser_id(model.getUser_id());
				log.setTo_user(tender.getUser_id());
				log.setType(Constant.AWARD_DEDUCT);
				log.setTotal(act.getTotal());
				log.setUse_money(act.getUse_money());
				log.setNo_use_money(act.getNo_use_money());
				log.setCollection(act.getCollection());
				log.setRemark("扣除标[" + logRemarkHtml(model) + "]投资奖励!!");
				this.accountLogDao.addAccountLog(log);
			}

			byte isDate = (byte) model.getIsday();
			int time = 0;
			if (EnumRuleBorrowCategory.BORROW_DAY.getValue() == isDate)
				time = model.getTime_limit_day();
			else if (EnumRuleBorrowCategory.BORROW_MONTH.getValue() == isDate) {
				time = Integer.parseInt(model.getTime_limit());
			}
			RuleModel rule = new RuleModel(this.ruleDao.getRuleByNid(EnumRuleNid.INTEGRAL_TENDER.getValue()));
			double check_money = rule.getValueDoubleByKey("integral_base_money").doubleValue();

			int value = CreditCount.getTenderValueMethodOne(isDate, time,
					Double.parseDouble(t.getAccount()), check_money);
			if (value > 0) {
				CreditType creditType = this.userCreditDao.getCreditTypeByNid(EnumIntegralTypeName.INTEGRAL_INVEST.getValue());

				UserCreditModel credit = this.userCreditDao.getCreditModelById(t.getUser_id());

				if ((credit != null) && (credit.getUser_id() > 0L)) {
					this.userCreditDao.editTenderValue(t.getUser_id(), value);
				} else if ((credit == null) || (credit.getUser_id() <= 0L)) {
					UserCredit uc = new UserCredit(t.getUser_id(), 10, new Date().getTime() / 1000L, "");
					uc.setValue(value);
					uc.setTender_value(value);
					this.userCreditDao.addUserCredit(uc);
				}

				UserCreditLog creditLog = new UserCreditLog();
				creditLog.setAddtime(NumberUtils.getLong(DateUtils.getNowTimeStr()));
				creditLog.setOp(Long.parseLong("1"));
				creditLog.setOp_user((int) t.getUser_id());
				creditLog.setType_id((int) creditType.getId());
				creditLog.setUser_id(t.getUser_id());
				creditLog.setValue(value);
				this.userCreditDao.addUserCreditLog(creditLog);
			}
		}

		tenderLog("Tender service end!", tender.getUser_id(), model.getId(), tender.getMoney(), tender.getAccount());
		return t;
	}

	public void getTenderAddLotteryTimes(Tender t) {
		double tender_money = NumberUtils.getDouble(StringUtils.isNull(Global.getValue("tender_money")));
		double tenderAccount = NumberUtils.getDouble(StringUtils.isNull(t.getAccount()));
		if (tenderAccount >= tender_money) {
			TenderAddLotteryTimes tenderAddLotteryTimes = new TenderAddLotteryTimes();
			tenderAddLotteryTimes.setTender_id(t.getId());
			tenderAddLotteryTimes.setLottery_times(1L);
			tenderAddLotteryTimes.setUser_id(t.getUser_id());
			tenderAddLotteryTimes.setAddtime(DateUtils.getNowTimeStr());
			this.lotteryDao.addTenderAddLotteryTimes(tenderAddLotteryTimes);
		}
	}

	private Tender getValidTender(Tender tender, BorrowModel borrow,
			AccountLog log) {
		BorrowModel model = this.borrowDao.getBorrowById(tender.getBorrow_id());
		borrow = BorrowHelper.getHelper(model);
		double validAccount = 0.0D;
		double tenderAccount = NumberUtils.getDouble(tender.getMoney());
		double account_val = NumberUtils.getDouble(model.getAccount());
		double account_yes_val = NumberUtils.getDouble(model.getAccount_yes());
		long tender_times_val = NumberUtils.getLong(model.getTender_times());

		if (account_yes_val >= account_val) {
			tenderLog("Borrow is full!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
			throw new BorrowException("此标已满！");
		}
		if (tenderAccount + account_yes_val >= account_val) {
			validAccount = account_val - account_yes_val;
			borrow.skipReview();
			if (model.getStatus() == 3) {
				if (borrow.getBorrowType() == Constant.TYPE_SECOND) {
					double interest = borrow.calculateInterest();
					double award = borrow.calculateBorrowAward();
					double freeze = interest + award;
					this.accountDao.updateAccount(0.0D, freeze, -freeze, model.getUser_id());
					Account borrowAct = this.accountDao.getAccount(model.getUser_id());
					log.setUser_id(model.getUser_id());
					log.setTo_user(1L);
					log.setType(Constant.UNFREEZE);
					log.setMoney(freeze);
					log.setTotal(borrowAct.getTotal());
					log.setUse_money(borrowAct.getUse_money());
					log.setNo_use_money(borrowAct.getNo_use_money());
					log.setCollection(borrowAct.getCollection());
					log.setAddtime(DateUtils.getNowTimeStr());
					log.setRemark(log.getRemark() + "解冻资金" + NumberUtils.format4(freeze));
					this.accountLogDao.addAccountLog(log);
				}
				model.setVerify_time(DateUtils.getNowTimeStr());
				this.borrowDao.updateJinBorrow(model);
			}
		} else {
			validAccount = tenderAccount;
		}
		tenderLog("Tender service,get validAccount!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
		model.setAccount_yes("" + (account_yes_val + validAccount));
		model.setTender_times("" + (tender_times_val + 1L));
		Account newAccount = this.accountDao.getAccount(tender.getUser_id());
		this.logger.info("Tender service,New account use_money! uid="
				+ tender.getUser_id() + ",bid=" + model.getId() + ",money="
				+ tender.getMoney() + ",account=" + newAccount.getUse_money());
		if (validAccount > newAccount.getUse_money()) {
			tenderLog("Tender fail,not enough money!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
			throw new BorrowException("投资金额大于您的可用金额，投标失败！");
		}

		if ((((Global.getWebid().equals("xdcf")) || (Global.getWebid().equals("zrzb"))))
				&& (model.getIs_flow() == 1)
				&& (model.getIsday() == 1)
				&& (model.getTime_limit_day() == 7)) {
			double flow_month_tender_collection = this.accountDao.getFlowMonthTenderCollection(tender.getUser_id());
			double flow_day_tender_collection = this.accountDao.getFlowDayTenderCollection(tender.getUser_id(), 7);
			if (flow_day_tender_collection + validAccount > flow_month_tender_collection) {
				tenderLog("Tender fail!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
				throw new BorrowException("您的待收金额小于" + validAccount + ",不能进行投流转标，投标失败！");
			}

		}

		if ((((Global.getWebid().equals("xdcf")) || (Global.getWebid().equals("zrzb"))))
				&& (model.getIs_flow() == 1)
				&& (model.getIsday() == 1)
				&& (model.getTime_limit_day() == 28)) {
			double flow_month_tender_collection = this.accountDao.getFlowMonthTenderCollection(tender.getUser_id());
			double flow_day_collection = this.accountDao.getFlowDayTenderCollection(tender.getUser_id(), 28);
			if (flow_day_collection + validAccount > flow_month_tender_collection) {
				tenderLog("Tender fail!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
				throw new BorrowException("您的待收金额小于" + validAccount + ",不能进行投流转标，投标失败！");
			}

		}

		double mbTenderAccount = NumberUtils.getDouble(Global.getValue("mb_tender_account"));
		if ((mbTenderAccount > 0.0D) && (NumberUtils.format2(newAccount.getCollection()) < mbTenderAccount) && (model.getIs_mb() == 1)) {
			tenderLog("Tender fail!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
			throw new BorrowException("您的待收金额小于" + mbTenderAccount + ",不能进行投秒标，投标失败！");
		}

		double collectionLimit = NumberUtils.getDouble(model.getCollection_limit());
		if ((collectionLimit > 0.0D) && (NumberUtils.format2(newAccount.getCollection()) < NumberUtils.format2(collectionLimit))) {
			throw new BorrowException("您的待收金额小于该借款标最小待收限制" + collectionLimit + ",不能进行投标，投标失败！");
		}
		tenderLog("Tender success!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
		int count = this.borrowDao.updateBorrow(validAccount,
				model.getStatus(), model.getId());
		if (count < 1) {
			tenderLog("Update borrow fail!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
			throw new BorrowException("投标失败！失败原因有可能是：此标已满！");
		}
		if (model.getBorrowType() == Constant.TYPE_FLOW) {
			this.borrowDao.updateBorrowFlow(borrow);
		}

		tender.setMoney("" + NumberUtils.format6(tenderAccount));
		tender.setAccount("" + NumberUtils.format6(validAccount));
		double most_account_num = NumberUtils.getDouble(model.getMost_account());
		double hasTender = 0.0D;
		try {
			hasTender = this.borrowDao.hasTenderTotalPerBorrowByUserid(model.getId(), tender.getUser_id());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BorrowException("系统繁忙，投标失败,请稍后再试！");
		}
		if ((validAccount + hasTender > most_account_num) && (most_account_num > 0.0D)) {
			throw new BorrowException("投资金额不能大于最大限制金额额度! ");
		}
		tender = this.tenderDao.addTender(tender);
		return tender;
	}

	private void tenderLog(String type, long user_id, long bid, String money, String account) {
		this.logger.info(type + " uid=" + user_id + ",bid=" + bid + ",money=" + money + ",account=" + account);
	}

	public BorrowFlow addFlow(BorrowFlow flow, BorrowModel borrow, Account act,
			AccountLog log) {
		BorrowModel model = borrow.getModel();
		int validCount = flow.getFlow_count();

		if (model.getFlow_yescount() + flow.getFlow_count() > model.getFlow_count()) {
			validCount = model.getFlow_count() - model.getFlow_yescount();
		}
		double validMoney = model.getFlow_money() * validCount;
		double interest = borrow.calculateInterest(validMoney);
		double repayment = interest + validMoney;
		flow.setRepayment_account("" + repayment);
		flow.setInterest("" + interest);
		flow.setValid_count(validCount);
		this.borrowFlowDao.add(flow);
		model.setFlow_yescount(model.getFlow_yescount() + validCount);
		this.borrowDao.updateBorrowFlow(model);

		double buyMoney = borrow.getModel().getFlow_count() * validCount;
		this.accountDao.updateAccount(-buyMoney, -buyMoney, 0.0D, flow.getUser_id());

		log.setMoney(buyMoney);
		log.setTotal(act.getTotal() - buyMoney);
		log.setUse_money(act.getUse_money() - buyMoney);
		log.setNo_use_money(act.getNo_use_money());
		log.setCollection(act.getCollection());
		log.setRemark("扣除投资者的投标资金");
		this.accountLogDao.addAccountLog(log);

		return flow;
	}

	private Collection fillCollection(MonthInterest mi, Tender t,
			InterestCalculator ic) {
		Collection c = new Collection();
		c.setTender_id(t.getId());

		c.setInterest("" + mi.getInterest());
		c.setCapital("" + mi.getAccountPerMon());
		c.setRepay_account("" + (mi.getInterest() + mi.getAccountPerMon()));
		c.setAddtime("" + new Date().getTime() / 1000L);

		c.setSite_id(0L);
		c.setStatus(0);
		c.setRepay_yesaccount("0");
		c.setLate_days(0L);
		c.setLate_interest("0");
		c.setUser_id(t.getUser_id());
		return c;
	}

	public List<DetailCollection> getDetailCollectionList(long user_id, int status) {
		return this.collectionDao.getDetailCollectionList(user_id, status);
	}

	public CollectionList getCollectionList(long user_id, int status,
			int startPage, SearchParam param) {
		CollectionList cList = new CollectionList();
		int total = this.collectionDao.getCollectionListCount(user_id, status, param);
		Page page = new Page(total, startPage);
		List<DetailCollection> list = this.collectionDao.getCollectionList(user_id, status, page.getStart(), page.getPernum(), param);
		cList.setList(list);
		cList.setPage(page);
		return cList;
	}

	public PageDataList getCollectionListByBorrow(long borrow_id, int page,
			SearchParam param) {
		PageDataList pList = new PageDataList();
		int total = this.collectionDao.getCollectionCountByBorrow(borrow_id, param);
		Page p = new Page(total, page);
		List<DetailCollection> list = this.collectionDao.getCollectionLlistByBorrow(borrow_id, p.getStart(), p.getPernum(), param);
		pList.setList(list);
		pList.setPage(p);
		return pList;
	}

	public List<DetailCollection> getCollectionListByBorrow(long borrow_id) {
		return this.collectionDao.getCollectionLlistByBorrow(borrow_id);
	}

	public List<DetailTender> getInvestTenderListByUserid(long user_id) {
		List<DetailTender> list = this.tenderDao.getInvestTenderListByUserid(user_id);
		return list;
	}

	public List<DetailTender> getInvestTenderingListByUserid(long user_id) {
		List<DetailTender> list = this.tenderDao.getInvestTenderingListByUserid(user_id);
		return list;
	}

	public PageDataList getInvestTenderingListByUserid(long user_id, int page, SearchParam param) {
		PageDataList pList = new PageDataList();
		int total = this.tenderDao.getInvestTenderingCountByUserid(user_id, param);
		Page p = new Page(total, page);
		List<DetailTender> list = this.tenderDao.getInvestTenderingListByUserid(user_id, p.getStart(), p.getPernum(), param);
		pList.setList(list);
		pList.setPage(p);
		return pList;
	}

	public PageDataList getInvestTenderListByUserid(long user_id, int startPage, SearchParam param) {
		PageDataList pList = new PageDataList();
		int total = this.tenderDao.getInvestTenderCountByUserid(user_id, param);
		Page p = new Page(total, startPage);
		List<DetailTender> list = this.tenderDao.getInvestTenderListByUserid(user_id, p.getStart(), p.getPernum(), param);
		pList.setList(list);
		pList.setPage(p);
		return pList;
	}

	public List<BorrowAuto> getBorrowAutoList(long user_id) {
		return this.borrowAutoDao.getBorrowAutoList(user_id);
	}

	public void addBorrowAuto(BorrowAuto auto) {
		this.borrowAutoDao.addBorrowAuto(auto);
	}

	public void modifyBorrowAuto(BorrowAuto auto) {
		this.borrowAutoDao.updateBorrowAuto(auto);
	}

	public void deleteBorrowAuto(long id) {
		this.borrowAutoDao.deleteBorrowAuto(id);
	}

	public Account getAccount(long user_id) {
		return this.accountDao.getAccount(user_id);
	}

	public List<BorrowModel> getBorrowList(long user_id) {
		return this.borrowDao.getBorrowList(user_id, 0, 5, new SearchParam());
	}

	public int getBorrowCount(long user_id) {
		return this.borrowDao.getBorrowCount(user_id);
	}

	public PageDataList getAllBorrowList(int startPage, SearchParam param) {
		int total = this.borrowDao.getAllBorrowCount(param);
		Page p = new Page(total, startPage);
		List<UserBorrowModel> list = this.borrowDao.getAllBorrowList(p.getStart(), p.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public PageDataList getFullBorrowList(int startPage, SearchParam param) {
		int total = this.borrowDao.getFullBorrowCount(param);
		Page p = new Page(total, startPage);
		List<UserBorrowModel> list = this.borrowDao.getFullBorrowList(p.getStart(), p.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public void verifyFullBorrow(BorrowModel model, OperationLog operationLog) {
		this.borrowDao.updateBorrow(model.getModel());
		Account act = this.accountDao.getAccount(model.getModel().getUser_id());
		if ((model.getModel().getStatus() == 3) && (model.getBorrowType() == Constant.TYPE_PROJECT)) {
			this.bonusDao.addBonusApr(model.getModel());
		}

		if (model.getBorrowType() == Constant.TYPE_SECOND) {
			AccountLog log = new AccountLog();
			double interest = model.calculateInterest();
			double award = model.calculateBorrowAward();
			double freeze = interest + award;
			this.accountDao.updateAccount(0.0D, freeze, -freeze, model.getModel().getUser_id());
			act = this.accountDao.getAccount(model.getModel().getUser_id());
			log.setType(Constant.UNFREEZE);
			log.setUser_id(model.getModel().getUser_id());
			log.setTo_user(1L);
			log.setMoney(freeze);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			log.setAddtime(DateUtils.getNowTimeStr());
			log.setRemark("解冻资金" + NumberUtils.format4(freeze));
			this.accountLogDao.addAccountLog(log);
		}
		if (model.getModel().getStatus() == 3) {
			DetailUser detailUser = this.userDao.getDetailUserByUserid(operationLog.getVerify_user());
			DetailUser newdetailUser = this.userDao.getDetailUserByUserid(model.getModel().getUser_id());
			operationLog.setType(Constant.BORROW_FULL_VERIFY_SUCCESS);
			operationLog.setOperationResult(detailUser.getTypename() + "（"
					+ operationLog.getAddip() + "）用户名为"
					+ detailUser.getUsername() + "的操作员审核发标人用户名为"
					+ newdetailUser.getUsername() + "的借款标（标名为"
					+ model.getModel().getName() + "）满标复审成功");
			this.operationLogDao.addOperationLog(operationLog);
		} else {
			DetailUser detailUser = this.userDao.getDetailUserByUserid(operationLog.getVerify_user());
			DetailUser newdetailUser = this.userDao.getDetailUserByUserid(model.getModel().getUser_id());
			operationLog.setType(Constant.BORROW_FULL_VERIFY_FAIL);
			operationLog.setOperationResult(detailUser.getTypename() + "（"
					+ operationLog.getAddip() + "）用户名为"
					+ detailUser.getUsername() + "的操作员审核发标人用户名为"
					+ newdetailUser.getUsername() + "的借款标（标名为"
					+ model.getModel().getName() + "）满标复审失败");
			this.operationLogDao.addOperationLog(operationLog);
		}
	}

	public void verifyFullBorrow(BorrowModel model, OperationLog operationLog,
			Protocol protocol) {
		this.borrowDao.updateBorrow(model.getModel());

		if (Global.getWebid().equals("jsy")) {
			List<BorrowTender> list = this.tenderDao.getTenderListByBorrow(model.getModel().getBorrowid());
			for (BorrowTender tender : list) {
				protocol.setPid(tender.getId());
				protocol.setRemark("用户名为" + tender.getUsername() + "的用户投标（标名为"
						+ tender.getName() + "的借款标）金额" + tender.getAccount()
						+ "元，生成投资人投标资金划转委托书");
				protocol.setMoney(NumberUtils.getDouble(tender.getAccount()));
				protocol.setUser_id(tender.getUser_id());
				this.protocolDao.addProtocol(protocol);
			}
		}
		Account act = this.accountDao.getAccount(model.getModel().getUser_id());
		if ((model.getModel().getStatus() == 3)
				&& (model.getBorrowType() == Constant.TYPE_PROJECT)) {
			this.bonusDao.addBonusApr(model.getModel());
		}

		if (model.getBorrowType() == Constant.TYPE_SECOND) {
			AccountLog log = new AccountLog();
			double interest = model.calculateInterest();
			double award = model.calculateBorrowAward();
			double freeze = interest + award;
			this.accountDao.updateAccount(0.0D, freeze, -freeze, model.getModel().getUser_id());
			act = this.accountDao.getAccount(model.getModel().getUser_id());
			log.setType(Constant.UNFREEZE);
			log.setUser_id(model.getModel().getUser_id());
			log.setTo_user(1L);
			log.setMoney(freeze);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			log.setAddtime(DateUtils.getNowTimeStr());
			log.setRemark("解冻资金" + NumberUtils.format4(freeze));
			this.accountLogDao.addAccountLog(log);
		}
		if (model.getModel().getStatus() == 3) {
			DetailUser detailUser = this.userDao.getDetailUserByUserid(operationLog.getVerify_user());
			DetailUser newdetailUser = this.userDao.getDetailUserByUserid(model.getModel().getUser_id());
			operationLog.setType(Constant.BORROW_FULL_VERIFY_SUCCESS);
			operationLog.setOperationResult(detailUser.getTypename() + "（"
					+ operationLog.getAddip() + "）用户名为"
					+ detailUser.getUsername() + "的操作员审核发标人用户名为"
					+ newdetailUser.getUsername() + "的借款标（标名为"
					+ model.getModel().getName() + "）满标复审成功");
			this.operationLogDao.addOperationLog(operationLog);
		} else {
			DetailUser detailUser = this.userDao.getDetailUserByUserid(operationLog.getVerify_user());
			DetailUser newdetailUser = this.userDao.getDetailUserByUserid(model.getModel().getUser_id());
			operationLog.setType(Constant.BORROW_FULL_VERIFY_FAIL);
			operationLog.setOperationResult(detailUser.getTypename() + "（"
					+ operationLog.getAddip() + "）用户名为"
					+ detailUser.getUsername() + "的操作员审核发标人用户名为"
					+ newdetailUser.getUsername() + "的借款标（标名为"
					+ model.getModel().getName() + "）满标复审失败");
			this.operationLogDao.addOperationLog(operationLog);
		}
	}

	public void verifyFullBorrow(BorrowModel model, AccountLog log) {
		Account act = this.accountDao.getAccount(model.getModel().getUser_id());
		if (model.getModel().getStatus() == 3) {
			if (model.getBorrowType() == Constant.TYPE_PROJECT) {
				this.bonusDao.addBonusApr(model.getModel());
			}
			if (model.getBorrowType() == Constant.TYPE_SECOND) {
				double interest = model.calculateInterest();
				double award = model.calculateBorrowAward();
				double freeze = interest + award;
				this.accountDao.updateAccount(0.0D, freeze, -freeze, model.getModel().getUser_id());
				act = this.accountDao.getAccount(model.getModel().getUser_id());
				log.setType(Constant.UNFREEZE);
				log.setMoney(freeze);
				log.setTotal(act.getTotal());
				log.setUse_money(act.getUse_money());
				log.setNo_use_money(act.getNo_use_money());
				log.setCollection(act.getCollection());
				log.setRemark(log.getRemark() + "解冻资金" + NumberUtils.format4(freeze));
				this.accountLogDao.addAccountLog(log);
			}
		}
	}

	public List<BorrowModel> unfinshBorrowList(long user_id) {
		return this.borrowDao.unfinshBorrowList(user_id);
	}

	public List<DetailTender> getInvestList(long user_id) {
		List<DetailTender> list = this.tenderDao.getSuccessTenderList(user_id);
		return list;
	}

	public List<BonusApr> getBonusAprList(long borrow_id) {
		return this.bonusDao.getBonusAprList(borrow_id);
	}

	public void addBonusApr(Borrow borrow) {
		this.bonusDao.addBonusApr(borrow);
	}

	public void modifyBonusAprById(long id, double apr) {
		this.bonusDao.modifyBonusAprById(id, apr);
		BonusApr b = this.bonusDao.getBonus(id);
		Repayment repay = this.repaymentDao.getRepayment(b.getOrder(), b.getBorrow_id());
		double cap = NumberUtils.getDouble(repay.getCapital());
		double bonus = cap * apr;
		double repayAccount = NumberUtils.getDouble(repay.getRepayment_account());
		repayAccount += bonus;
		repay.setBonus(Double.toString(bonus));
		repay.setRepayment_account(Double.toString(repayAccount));
		this.repaymentDao.modifyRepaymentBonus(repay);
		List<BorrowTender> list = this.tenderDao.getTenderListByBorrowid(b.getBorrow_id());
		this.collectionDao.modifyCollectionBonus(b.getOrder(), apr, list);
	}

	private Message getSiteMessage(long receive_user, String title,
			String content, long sent_user, Message message) {
		message.setSent_user(receive_user);
		message.setReceive_user(sent_user);
		message.setStatus(0);
		message.setType(Constant.SYSTEM);
		message.setName(title);
		message.setContent(content);
		return message;
	}

	public double hasTenderTotalPerBorrowByUserid(long borrow_id, long user_id) {
		double total = 0.0D;
		total = this.borrowDao.hasTenderTotalPerBorrowByUserid(borrow_id, user_id);
		return total;
	}

	public double hasTenderListByUserid(long user_id, String starttime, String endtime) {
		double total = 0.0D;
		total = this.tenderDao.getTenderListByUserid(user_id, starttime, endtime);
		return total;
	}

	public List<RankModel> getRankListByTime(String startTime, String endTime) {
		return this.tenderDao.getRankListByTime(startTime, endTime);
	}

	public List<RankModel> getMoreRankListByTime(String startTime, String endTime, int num) {
		return this.tenderDao.getMoreRankListByTime(startTime, endTime, num);
	}

	public void addJk(RunBorrow runBorrow) {
		this.borrowDao.addjk(runBorrow);
	}

	public PageDataList jk(int page, SearchParam param) {
		PageDataList plist = new PageDataList();
		int total = this.borrowDao.getjkCount(param);
		Page p = new Page(total, page);
		plist.setPage(p);
		List<RunBorrow> list = this.borrowDao.jklist(p.getStart(), p.getPernum(), param);
		plist.setList(list);
		return plist;
	}

	public List<Advanced> advancedList() {
		List<Advanced> list = this.borrowDao.advancedList();
		return list;
	}

	public Advanced borrowAccountSum(String startTime, String endTime) {
		Advanced advanced = new Advanced();
		List<Advanced> list = this.borrowDao.advancedList();
		if (list.size() > 0) {
			advanced = (Advanced) list.get(0);
			double borrowTotal = this.borrowDao.getBorrowAccountSum();
			double waitTotal = this.borrowDao.getWaitAccountSum();
			double borrowDayTotal = this.borrowDao.getDayBorrowAccountSum(startTime, endTime);
			advanced.setBorrow_total(borrowTotal);
			advanced.setWait_total(waitTotal);
			advanced.setBorrow_day_total(borrowDayTotal);
			this.cashDao.getAdvance_update(advanced);
		}
		return advanced;
	}

	public List<BorrowTender> getAllTenderList(long borrow_id) {
		return this.tenderDao.getTenderListByBorrowid(borrow_id);
	}

	public double getRepayTotalWithJin(long user_id) {
		return this.borrowDao.getRepayTotalWithJin(user_id);
	}

	public List<BorrowModel> getBorrowFlowListByuserId(long user_id) {
		return this.borrowFlowDao.getBorrowFlowByUserId(user_id);
	}

	public List<Repayment> getRepaymentList(String search, long user_id) {
		return this.repaymentDao.getRepaymentList(search, user_id);
	}

	public void updateJinBorrow(Borrow borrow) {
		this.borrowDao.updateJinBorrow(borrow);
	}

	public List<Repayment> getAllRepaymentList(int webstatus) {
		return this.repaymentDao.getAllRepaymentList(webstatus);
	}

	public List<DetailCollection> getAllFlowCollectList(int status) {
		int aheadTime = Global.getInt("flow_aheadtime");
		return this.collectionDao.getAllFlowCollectList(status, aheadTime);
	}

	public List<BorrowModel> getBorrowListByStatus(int status) {
		return this.borrowDao.getBorrowListByStatus(status);
	}

	public void reservation_add(Reservation reservation) {
		this.borrowDao.reservation_add(reservation);
	}

	public PageDataList reservation_list(SearchParam param, int page) {
		PageDataList plist = new PageDataList();
		int total = this.borrowDao.getReservation_list_count(param);
		Page p = new Page(total, page);
		plist.setPage(p);
		List<ReservationModel> list = this.borrowDao.getReservation_list(p.getStart(), p.getPernum(), param);
		plist.setList(list);
		return plist;
	}

	public double getBorrowTotal() {
		return this.borrowDao.getBorrowSum();
	}

	public double getTotalRepayAccountAndInterest(String todayStartTime, String todayEndTime) {
		return this.borrowDao.getTotalRepayAccountAndInterest(todayStartTime,  todayEndTime);
	}

	public double getSumBorrowAccount(SearchParam param) {
		double total = 0.0D;
		total = this.borrowDao.getSumBorrowAccount(param);
		return total;
	}

	public int hasBorrowCountByUserid(long borrow_id, long user_id) {
		int count = 0;
		count = this.borrowDao.hasBorrowCountByUserid(borrow_id, user_id);
		return count;
	}

	public void updateTenderAward(TenderAwardYesAndNo tenderAwardYesAndNo) {
		this.tenderDao.updateTenderAwardYesAndNo(tenderAwardYesAndNo);
	}

	public TenderAwardYesAndNo TenderAward(int id) {
		TenderAwardYesAndNo tenderAwardYesAndNo = this.tenderDao.tenderAwardYesAndNo(id);
		return tenderAwardYesAndNo;
	}

	public PageDataList protocolList(SearchParam param, int page) {
		PageDataList plist = new PageDataList();
		int total = this.protocolDao.getProtocolCount(param);
		Page p = new Page(total, page);
		plist.setPage(p);
		List<ProtocolModel> list = this.protocolDao.getProtocolList(p.getStart(), p.getPernum(), param);
		plist.setList(list);
		return plist;
	}

	public ProtocolModel getProtocolByid(long id) {
		return this.protocolDao.getProtocolById(id);
	}

	public void addLateLog(Map<String, Object> params) {
		this.lateBorrowLogDao.addLateBorrowLogDetail(params);
	}

	public LateBorrowLogDao getLateBorrowLogDao() {
		return this.lateBorrowLogDao;
	}

	public void setLateBorrowLogDao(LateBorrowLogDao lateBorrowLogDao) {
		this.lateBorrowLogDao = lateBorrowLogDao;
	}

	public List<Map<String, Object>> getLateLog(String borrowid) {
		return this.lateBorrowLogDao.queryLateBorrowDetails(borrowid);
	}

	public int getBorrowCountForSuccess() {
		return this.borrowDao.getBorrowCountForSuccess();
	}

	public List<UserBorrowModel> getRecommendList() {
		BorrowModel model = new BorrowModel();
		model.setStatus(Constant.STATUS_INDEX_ZA);
		SearchParam param = new SearchParam();
		param.setOrder(Constant.ORDER_INDEX);
		model.setParam(param);
		String index_borrow = Global.getValue("index_borrow");
		List<UserBorrowModel> indexList = new ArrayList<UserBorrowModel>();
		String[] borrows = new String[0];
		if (index_borrow != null) {
			borrows = index_borrow.split(",");
		}
		if ((borrows != null) && (borrows.length > 0)) {
			for (int i = 0; i < borrows.length; ++i) {
				long start = System.currentTimeMillis();
				List<UserBorrowModel> bList = new ArrayList<UserBorrowModel>();
				try {
					bList = this.borrowDao.getList(BorrowHelper.getHelper(NumberUtils.getInt(borrows[i]), model));
				} catch (Exception e) {
					this.logger.error(e.getMessage());
				}
				indexList.addAll(bList);
				long end = System.currentTimeMillis();
				this.logger.info("GetList Cost Time:" + (end - start));
			}
		}
		return indexList;
	}

	public void updateRecommendBorrow(Borrow borrow) {
		this.borrowDao.updateRecommendBorrow(borrow);
	}

	public int getTotalTenderTimesByUserid(long userid) {
		return this.tenderDao.getTotalTenderTimesByUserid(userid);
	}

	public PageDataList getFastExpireList(SearchParam param, int page) {
		PageDataList plist = new PageDataList();
		int total = this.repaymentDao.getFastExpireCount(param);
		Page p = new Page(total, page);
		plist.setPage(p);
		List<Map<String, Object>> list = this.repaymentDao.getFastExpireList(param, p.getStart(), p.getPernum());
		plist.setList(list);
		return plist;
	}

	private String logRemarkHtml(BorrowModel model) {
		String s = "<a href='" + Global.getString("weburl")
				+ "/invest/detail.html?borrowid=" + model.getId()
				+ "' target=_blank>" + model.getName() + "</a>";
		return s;
	}

	public List<FastExpireModel> getFastExpireList(SearchParam param) {
		return this.repaymentDao.getFastExpireList(param);
	}

	public List<RankModel> getRankList() {
		return this.tenderDao.getRankList();
	}

	public List<RankModel> getAllRankList() {
		return this.tenderDao.getAllRankList();
	}

	public RuleDao getRuleDao() {
		return this.ruleDao;
	}

	public void setRuleDao(RuleDao ruleDao) {
		this.ruleDao = ruleDao;
	}

	public UserCreditDao getUserCreditDao() {
		return this.userCreditDao;
	}

	public void setUserCreditDao(UserCreditDao userCreditDao) {
		this.userCreditDao = userCreditDao;
	}

	public LotteryDao getLotteryDao() {
		return this.lotteryDao;
	}

	public void setLotteryDao(LotteryDao lotteryDao) {
		this.lotteryDao = lotteryDao;
	}

	public ProtocolDao getProtocolDao() {
		return this.protocolDao;
	}

	public void setProtocolDao(ProtocolDao protocolDao) {
		this.protocolDao = protocolDao;
	}

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public OperationLogDao getOperationLogDao() {
		return this.operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public CashDao getCashDao() {
		return this.cashDao;
	}

	public void setCashDao(CashDao cashDao) {
		this.cashDao = cashDao;
	}

	public MessageDao getMessageDao() {
		return this.messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public BorrowDao getBorrowDao() {
		return this.borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public TenderDao getTenderDao() {
		return this.tenderDao;
	}

	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}

	public CollectionDao getCollectionDao() {
		return this.collectionDao;
	}

	public void setCollectionDao(CollectionDao collectionDao) {
		this.collectionDao = collectionDao;
	}

	public BorrowAutoDao getBorrowAutoDao() {
		return this.borrowAutoDao;
	}

	public void setBorrowAutoDao(BorrowAutoDao borrowAutoDao) {
		this.borrowAutoDao = borrowAutoDao;
	}

	public AccountLogDao getAccountLogDao() {
		return this.accountLogDao;
	}

	public void setAccountLogDao(AccountLogDao accountLogDao) {
		this.accountLogDao = accountLogDao;
	}

	public AccountDao getAccountDao() {
		return this.accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public UserAmountDao getUserAmountDao() {
		return this.userAmountDao;
	}

	public void setUserAmountDao(UserAmountDao userAmountDao) {
		this.userAmountDao = userAmountDao;
	}

	public BonusDao getBonusDao() {
		return this.bonusDao;
	}

	public void setBonusDao(BonusDao bonusDao) {
		this.bonusDao = bonusDao;
	}

	public RepaymentDao getRepaymentDao() {
		return this.repaymentDao;
	}

	public void setRepaymentDao(RepaymentDao repaymentDao) {
		this.repaymentDao = repaymentDao;
	}

	public BorrowFlowDao getBorrowFlowDao() {
		return this.borrowFlowDao;
	}

	public void setBorrowFlowDao(BorrowFlowDao borrowFlowDao) {
		this.borrowFlowDao = borrowFlowDao;
	}

	public UserLogDao getUserLogDao() {
		return this.userLogDao;
	}

	public void setUserLogDao(UserLogDao userLogDao) {
		this.userLogDao = userLogDao;
	}

	public int getApplyBorrowCount() {
		return this.borrowDao.getApplyBorrowCount();
	}

	public double getApplyBorrowTotal() {
		return this.borrowDao.getApplyBorrowTotal();
	}
}