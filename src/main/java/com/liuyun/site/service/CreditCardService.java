package com.liuyun.site.service;

import com.liuyun.site.domain.CreditCard;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import java.util.List;

public abstract interface CreditCardService {
	public abstract PageDataList getList(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<CreditCard> getListByType(int paramInt);

	public abstract CreditCard addCreditCard(CreditCard paramCreditCard);

	public abstract CreditCard getCardById(int paramInt);

	public abstract void updateCreditCard(CreditCard paramCreditCard);

	public abstract void updateLitpic(String paramString, int paramInt);

	public abstract void delCreditCard(int paramInt);
}