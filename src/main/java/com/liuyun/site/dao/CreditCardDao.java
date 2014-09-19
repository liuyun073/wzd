package com.liuyun.site.dao;

import com.liuyun.site.domain.CreditCard;
import com.liuyun.site.model.SearchParam;
import java.util.List;

public abstract interface CreditCardDao {
	public abstract List<CreditCard> getList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getSearchCard(SearchParam paramSearchParam);

	public abstract CreditCard addCreditCard(CreditCard paramCreditCard);

	public abstract CreditCard getCardById(int paramInt);

	public abstract void updateCreditCard(CreditCard paramCreditCard);

	public abstract List<CreditCard> getCardByType(int paramInt);

	public abstract void delCreditCard(int paramInt);

	public abstract void updateLitpic(String paramString, int paramInt);
}