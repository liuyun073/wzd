package com.rd.service.impl;

import com.rd.context.Global;
import com.rd.dao.AccountDao;
import com.rd.dao.AccountLogDao;
import com.rd.dao.BorrowDao;
import com.rd.dao.CollectionDao;
import com.rd.dao.MemberBorrowDao;
import com.rd.dao.RepaymentDao;
import com.rd.dao.TenderDao;
import com.rd.domain.Account;
import com.rd.domain.AccountLog;
import com.rd.domain.Collection;
import com.rd.domain.Repayment;
import com.rd.exception.RepaymentException;
import com.rd.model.DetailCollection;
import com.rd.model.DetailTender;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.model.UserBorrowModel;
import com.rd.model.borrow.BorrowModel;
import com.rd.service.MemberBorrowService;
import com.rd.tool.Page;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import java.util.List;

public class MemberBorrowServiceImpl implements MemberBorrowService {
	MemberBorrowDao memberBorrowDao;
	RepaymentDao repaymentDao;
	TenderDao tenderDao;
	CollectionDao collectionDao;
	AccountDao accountDao;
	AccountLogDao accountLogDao;
	BorrowDao borrowDao;

	public MemberBorrowDao getMemberBorrowDao() {
		return this.memberBorrowDao;
	}

	public void setMemberBorrowDao(MemberBorrowDao memberBorrowDao) {
		this.memberBorrowDao = memberBorrowDao;
	}

	public RepaymentDao getRepaymentDao() {
		return this.repaymentDao;
	}

	public void setRepaymentDao(RepaymentDao repaymentDao) {
		this.repaymentDao = repaymentDao;
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

	public AccountDao getAccountDao() {
		return this.accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public AccountLogDao getAccountLogDao() {
		return this.accountLogDao;
	}

	public void setAccountLogDao(AccountLogDao accountLogDao) {
		this.accountLogDao = accountLogDao;
	}

	public BorrowDao getBorrowDao() {
		return this.borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public PageDataList getList(String type, long user_id, int startPage, SearchParam param) {
		PageDataList bList = new PageDataList();
		int total = this.memberBorrowDao.getBorrowCount(type, user_id, param);
		Page p = new Page(total, startPage);
		List<UserBorrowModel> list = this.memberBorrowDao.getBorrowList(type, user_id, p.getStart(), p.getPernum(), param);
		bList.setPage(p);
		bList.setList(list);
		return bList;
	}

	public List<Repayment> getRepaymentList(long user_id) {
		return this.repaymentDao.getRepaymentList(user_id);
	}

	public List<Repayment> getRepaymentList(long user_id, long borrowId) {
		return this.repaymentDao.getRepaymentList(user_id, borrowId);
	}

	public Repayment getRepayment(long repay_id) {
		return this.repaymentDao.getRepayment(repay_id);
	}

	public List<DetailTender> getBorrowTenderListByUserid(long user_id) {
		return this.tenderDao.getInvestTenderListByUserid(user_id);
	}

	public List getBorrowTenderListByUserid(long user_id, long borrowId) {
		return null;
	}

	public DetailCollection getCollection(long cid) {
		return this.collectionDao.getCollection(cid);
	}

	public synchronized void doRepay(Repayment repay, Account act) {
		BorrowModel borrow = this.borrowDao.getBorrowById(repay.getBorrow_id());
		if ((borrow.getStatus() != 6) && (borrow.getStatus() != 7)) {
			throw new RepaymentException("借款标的状态不允许进行还款！");
		}
		Repayment dbRepay = this.repaymentDao.getRepayment(repay.getId());
		if ((dbRepay == null) || (dbRepay.getStatus() == 1) || (dbRepay.getWebstatus() == 1)) {
			throw new RepaymentException("该期借款已经还款,请不要重复操作！");
		}
		boolean hasAhead = this.repaymentDao.hasRepaymentAhead(repay.getOrder(), repay.getBorrow_id());
		if (hasAhead) {
			throw new RepaymentException("还有尚未还款的借款！");
		}
		repay.setWebstatus(1);
		double repayMoney = NumberUtils.getDouble(repay.getRepayment_account());
		double lateMoney = NumberUtils.getDouble(repay.getLate_interest());
		double freezeVal = repayMoney + lateMoney;
		this.accountDao.updateAccount(0.0D, -freezeVal, freezeVal, act.getUser_id());
		if (this.repaymentDao.modifyRepaymentStatusWithCheck(repay.getId(), repay.getStatus(), repay.getWebstatus()) < 1)
			throw new RepaymentException("该期借款已经还款,请不要重复操作！");
	}

	public void doRepay(Repayment repay, Account act, AccountLog log) {
		doRepay(repay, act);
		BorrowModel borrow = this.borrowDao.getBorrowById(repay.getBorrow_id());
		act = this.accountDao.getAccount(act.getUser_id());
		double repayMoney = NumberUtils.getDouble(repay.getRepayment_account());
		double lateMoney = NumberUtils.getDouble(repay.getLate_interest());
		double freezeVal = repayMoney + lateMoney;
		log.setMoney(freezeVal);
		log.setTotal(act.getTotal());
		log.setUse_money(act.getUse_money());
		log.setNo_use_money(act.getNo_use_money());
		log.setCollection(act.getCollection());
		log.setRemark(logRemarkHtml(borrow) + "冻结进行还款的本息");
		this.accountLogDao.addAccountLog(log);
	}

	public PageDataList getRepaymentList(SearchParam param, int page) {
		int total = this.repaymentDao.getRepaymentCount(param);
		Page p = new Page(total, page);
		List<Repayment> list = this.repaymentDao.getRepaymentList(param, p.getStart(), p.getPernum());
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public List<Repayment> getRepaymentList(SearchParam param) {
		List<Repayment> list = this.repaymentDao.getRepaymentList(param);
		return list;
	}

	public PageDataList getLateList(SearchParam param, int page) {
		String nowTime = DateUtils.getNowTimeStr();
		int total = this.repaymentDao.getLateCount(param, nowTime);
		Page p = new Page(total, page);
		List<Repayment> list = this.repaymentDao.getLateList(param, nowTime, p.getStart(), p.getPernum());
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public PageDataList getUnFinishFlowList(SearchParam param, int page) {
		String nowTime = DateUtils.getNowTimeStr();
		int total = this.collectionDao.getUnFinishFlowCount(param, nowTime);
		Page p = new Page(total, page);
		List<Collection> list = this.collectionDao.getUnFinishFlowList(param, nowTime, p.getStart(), p.getPernum());
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public List<Collection> getUnFinishFlowList(SearchParam param) {
		List<Collection> list = this.collectionDao.getUnFinishFlowList(param);
		return list;
	}

	public List<UserBorrowModel> getRepayMentList(String type, long user_id) {
		List<UserBorrowModel> list = this.memberBorrowDao.getRepamentList(type, user_id);
		return list;
	}

	public PageDataList getOverdueList(SearchParam param, int page) {
		String nowTime = DateUtils.getNowTimeStr();
		int total = this.repaymentDao.getOverdueCount(param, nowTime);
		Page p = new Page(total, page);
		List<Repayment> list = this.repaymentDao.getOverdueList(param, nowTime, p.getStart(), p.getPernum());
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public List<Repayment> getOverdueList(SearchParam param) {
		String nowTime = DateUtils.getNowTimeStr();
		List<Repayment> list = this.repaymentDao.getOverdueList(param, nowTime);
		return list;
	}

	public double getRepaymentSum(SearchParam param) {
		double sum = this.repaymentDao.getRepaymentSum(param);
		return sum;
	}

	private String logRemarkHtml(BorrowModel model) {
		String s = "[<a href='" + Global.getString("weburl")
				+ "/invest/detail.html?borrowid=" + model.getId()
				+ "' target=_blank>" + model.getName() + "</a>]";
		return s;
	}

	public List<Repayment> getLateRepaymentByBorrowid(long borrow_id) {
		List<Repayment> list = this.repaymentDao.getLateRepaymentByBorrowid(borrow_id);
		return list;
	}
}