package com.liuyun.site.service;

import java.util.List;

import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.Collection;
import com.liuyun.site.domain.Repayment;
import com.liuyun.site.model.DetailCollection;
import com.liuyun.site.model.DetailTender;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserBorrowModel;

public abstract interface MemberBorrowService {
	public abstract PageDataList getList(String paramString, long paramLong,
			int paramInt, SearchParam paramSearchParam);

	public abstract List<Repayment> getRepaymentList(long paramLong);

	public abstract List<Repayment> getRepaymentList(long paramLong1, long paramLong2);

	public abstract Repayment getRepayment(long paramLong);

	public abstract List<DetailTender> getBorrowTenderListByUserid(long paramLong);

	public abstract List getBorrowTenderListByUserid(long paramLong1,
			long paramLong2);

	public abstract DetailCollection getCollection(long paramLong);

	public abstract void doRepay(Repayment paramRepayment, Account paramAccount);

	public abstract void doRepay(Repayment paramRepayment,
			Account paramAccount, AccountLog paramAccountLog);

	public abstract PageDataList getRepaymentList(SearchParam paramSearchParam,
			int paramInt);

	public abstract List<Repayment> getRepaymentList(SearchParam paramSearchParam);

	public abstract PageDataList getLateList(SearchParam paramSearchParam,
			int paramInt);

	public abstract List<UserBorrowModel> getRepayMentList(String paramString, long paramLong);

	public abstract PageDataList getUnFinishFlowList(
			SearchParam paramSearchParam, int paramInt);

	public abstract PageDataList getOverdueList(SearchParam paramSearchParam,
			int paramInt);

	public abstract List<Repayment> getOverdueList(SearchParam paramSearchParam);

	public abstract double getRepaymentSum(SearchParam paramSearchParam);

	public abstract List<Collection> getUnFinishFlowList(SearchParam paramSearchParam);

	public abstract List<Repayment> getLateRepaymentByBorrowid(long paramLong);
}