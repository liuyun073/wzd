package com.liuyun.site.service.impl;

import com.liuyun.site.dao.QuickenLoansDao;
import com.liuyun.site.domain.QuickenLoans;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.QuickenLoansService;
import com.liuyun.site.tool.Page;

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