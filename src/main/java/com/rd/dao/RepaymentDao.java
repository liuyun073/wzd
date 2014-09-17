package com.rd.dao;

import com.rd.domain.Repayment;
import com.rd.model.SearchParam;
import com.rd.model.borrow.FastExpireModel;

import java.util.List;
import java.util.Map;

public abstract interface RepaymentDao {
	public abstract List<Repayment> getRepaymentList(long paramLong);

	public abstract List<Repayment> getRepaymentList(long paramLong1, long paramLong2);

	public abstract Repayment getRepayment(long paramLong);

	public abstract Repayment getRepayment(int paramInt, long paramLong);

	public abstract void addBatchRepayment(Repayment[] paramArrayOfRepayment);

	public abstract void modifyRepayment(Repayment paramRepayment);

	public abstract void modifyRepaymentBonus(Repayment paramRepayment);

	public abstract void modifyRepaymentYes(Repayment paramRepayment);

	public abstract List<Repayment> getRepaymentList(SearchParam paramSearchParam,
			int paramInt1, int paramInt2);

	public abstract int getRepaymentCount(SearchParam paramSearchParam);

	public abstract int getCountRepaid(int paramInt);

	public abstract int getCountExprire(int paramInt);

	public abstract List<Repayment> getLateList(SearchParam paramSearchParam,
			String paramString, int paramInt1, int paramInt2);

	public abstract int getLateCount(SearchParam paramSearchParam,
			String paramString);

	public abstract boolean hasRepaymentAhead(int paramInt, long paramLong);

	public abstract List<Repayment> getRepaymentList(String paramString, long paramLong);

	public abstract List<Repayment> getAllRepaymentList(int paramInt);

	public abstract List<Repayment> getAllRepaymentList(int paramInt, long paramLong1,
			long paramLong2);

	public abstract List<Repayment> getOverdueList(SearchParam paramSearchParam,
			String paramString, int paramInt1, int paramInt2);

	public abstract List<Repayment> getOverdueList(SearchParam paramSearchParam,
			String paramString);

	public abstract int getOverdueCount(SearchParam paramSearchParam,
			String paramString);

	public abstract List<Repayment> getRepaymentList(SearchParam paramSearchParam);

	public abstract double getRepaymentSum(SearchParam paramSearchParam);

	public abstract void modifyRepaymentStatus(long paramLong, int paramInt1,
			int paramInt2);

	public abstract int modifyRepaymentStatusWithCheck(long paramLong,
			int paramInt1, int paramInt2);

	public abstract List<Map<String, Object>> getToPayRepayMent();

	public abstract List<Map<String, Object>> getLateList();

	public abstract double getSumLateMoney(long paramLong);

	public abstract void updateLaterepayment(long paramLong1, long paramLong2,
			double paramDouble);

	public abstract List<Repayment> getLateRepaymentByBorrowid(long paramLong);

	public abstract double getAllSumLateMoney(int paramInt);

	public abstract List<Map<String, Object>> getFastExpireList(SearchParam paramSearchParam,
			int paramInt1, int paramInt2);

	public abstract List<FastExpireModel> getFastExpireList(SearchParam paramSearchParam);

	public abstract int getFastExpireCount(SearchParam paramSearchParam);

	public abstract double getAllLateSumWithNoRepaid();

	public abstract double getAllLateSumWithYesRepaid();
}