package com.rd.service.impl;

import com.rd.dao.QuickenLoansDao;
import com.rd.domain.QuickenLoans;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.service.QuickenLoansService;
import com.rd.tool.Page;

public class QuickenLoansServiceImpl implements QuickenLoansService {
	QuickenLoansDao quickenLoansDao;

	public QuickenLoansDao getQuickenLoansDao() {
		return this.quickenLoansDao;
	}

	public void setQuickenLoansDao(QuickenLoansDao quickenLoansDao) {
		this.quickenLoansDao = quickenLoansDao;
	}

	public PageDataList getList(int page, SearchParam p) {
		PageDataList pageDataList = new PageDataList();
		Page pages = new Page(this.quickenLoansDao.getSearchCard(p), page, 10);
		pageDataList.setList(this.quickenLoansDao.getList(pages.getStart(),
				pages.getPernum(), p));
		pageDataList.setPage(pages);
		return pageDataList;
	}

	public void addQuickenLoans(QuickenLoans quickenLoans) {
		this.quickenLoansDao.addQuickenLoans(quickenLoans);
	}

	public QuickenLoans getLoansById(int loansId) {
		QuickenLoans quickenLoans = this.quickenLoansDao.getLoansById(loansId);
		return quickenLoans;
	}

	public void delQuickenLoans(int loansId) {
		this.quickenLoansDao.delQuickenLoans(loansId);
	}
}