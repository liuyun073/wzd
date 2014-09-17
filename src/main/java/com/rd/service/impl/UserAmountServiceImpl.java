package com.rd.service.impl;

import com.rd.dao.UserAmountDao;
import com.rd.domain.UserAmount;
import com.rd.domain.UserAmountApply;
import com.rd.domain.UserAmountLog;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.service.UserAmountService;
import com.rd.tool.Page;
import java.util.List;

public class UserAmountServiceImpl implements UserAmountService {
	UserAmountDao userAmountDao;

	public UserAmountDao getUserAmountDao() {
		return this.userAmountDao;
	}

	public void setUserAmountDao(UserAmountDao userAmountDao) {
		this.userAmountDao = userAmountDao;
	}

	public List<UserAmountApply> getUserAmountApply(long user_id) {
		return this.userAmountDao.getUserAmountApply(user_id);
	}

	public void add(UserAmountApply amount) {
		this.userAmountDao.add(amount);
	}

	public PageDataList getAmountApplyByUserid(long user_id, int page,
			SearchParam param) {
		int total = this.userAmountDao.getAmountApplyCountByUserid(user_id,
				param);
		Page p = new Page(total, page);
		List list = this.userAmountDao.getAmountApplyListByUserid(user_id, p.getStart(), p.getEnd(), param);
		return new PageDataList(p, list);
	}

	public PageDataList getUserAmountApply(int page, SearchParam param) {
		int total = this.userAmountDao.getUserMountApplyCount(param);
		Page p = new Page(total, page);
		List list = this.userAmountDao.getUserMountApply(p.getStart(), p
				.getEnd(), param);
		return new PageDataList(p, list);
	}

	public UserAmount getUserAmount(long user_id) {
		return this.userAmountDao.getUserAmount(user_id);
	}

	public UserAmountApply getUserAmountApplyById(long id) {
		return this.userAmountDao.getUserAmountApplyById(id);
	}

	public void verifyAmountApply(UserAmountApply apply, UserAmountLog log) {
		UserAmount amount = this.userAmountDao
				.getUserAmount(apply.getUser_id());
		if (apply.getStatus() == 1) {
			this.userAmountDao.updateCreditAmount(apply.getAccount(), apply.getAccount(), 0.0D, amount);
			log.setAccount("" + apply.getAccount());
			log.setAccount_all("" + (amount.getCredit() + apply.getAccount()));
			log.setAccount_use("" + (amount.getCredit_use() + apply.getAccount()));
			log.setAccount_nouse("" + amount.getCredit_nouse());
			log.setAmount_type("credit");
			log.setType("apply_add");
			log.setRemark("申请额度审核通过");
		} else {
			log.setAccount("" + apply.getAccount());
			log.setAccount_all("" + amount.getCredit());
			log.setAccount_use("" + amount.getCredit_use());
			log.setAccount_nouse("" + amount.getCredit_nouse());
			log.setAmount_type("credit");
			log.setType("apply_add");
			log.setRemark("申请额度审核不通过");
		}
		this.userAmountDao.updateApply(apply);
		this.userAmountDao.addAmountLog(log);
	}

	public PageDataList getUserAmount(int page, SearchParam param) {
		int total = this.userAmountDao.getUserAmountCount(param);
		Page p = new Page(total, page);
		List list = this.userAmountDao.getUserAmount(p.getStart(), p.getEnd(),
				param);
		return new PageDataList(p, list);
	}
}