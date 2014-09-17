package com.rd.service.impl;

import com.rd.dao.CreditCardDao;
import com.rd.domain.CreditCard;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.service.CreditCardService;
import com.rd.tool.Page;
import java.util.List;

public class CreditCardServiceImpl implements CreditCardService {
	CreditCardDao creditCardDao;

	public CreditCardDao getCreditCardDao() {
		return this.creditCardDao;
	}

	public void setCreditCardDao(CreditCardDao creditCardDao) {
		this.creditCardDao = creditCardDao;
	}

	public PageDataList getList(int page, SearchParam p) {
		PageDataList pageDataList = new PageDataList();
		Page pages = new Page(this.creditCardDao.getSearchCard(p), page, 10);
		pageDataList.setList(this.creditCardDao.getList(pages.getStart(), pages
				.getPernum(), p));
		pageDataList.setPage(pages);
		return pageDataList;
	}

	public CreditCard addCreditCard(CreditCard c) {
		return this.creditCardDao.addCreditCard(c);
	}

	public CreditCard getCardById(int cardId) {
		CreditCard b = this.creditCardDao.getCardById(cardId);
		return b;
	}

	public void updateCreditCard(CreditCard c) {
		this.creditCardDao.updateCreditCard(c);
	}

	public List<CreditCard> getListByType(int type) {
		return this.creditCardDao.getCardByType(type);
	}

	public void delCreditCard(int cardId) {
		this.creditCardDao.delCreditCard(cardId);
	}

	public void updateLitpic(String litpic, int cardId) {
		this.creditCardDao.updateLitpic(litpic, cardId);
	}
}