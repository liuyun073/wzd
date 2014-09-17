package com.rd.dao;

import com.rd.domain.Advanced;
import com.rd.domain.Borrow;
import com.rd.domain.Repayment;
import com.rd.domain.Reservation;
import com.rd.domain.RunBorrow;
import com.rd.model.SearchParam;
import com.rd.model.UserBorrowModel;
import com.rd.model.borrow.BorrowModel;
import com.rd.model.borrow.ReservationModel;
import com.rd.model.invest.InvestBorrowModel;

import java.util.List;

public abstract interface BorrowDao {
	public abstract List<UserBorrowModel> getList(BorrowModel paramBorrowModel);

	public abstract List<BorrowModel> getBorrowList(long paramLong, int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract int count(BorrowModel paramBorrowModel);

	public abstract BorrowModel getBorrowById(long paramLong);

	public abstract UserBorrowModel getUserBorrowById(long paramLong);

	public abstract void addBorrow(Borrow paramBorrow);

	public abstract void updateBorrow(Borrow paramBorrow);

	public abstract int updateBorrow(double paramDouble, int paramInt,
			long paramLong);

	public abstract void updateRecommendBorrow(Borrow paramBorrow);

	public abstract List<BorrowModel> getBorrowList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getBorrowCount(SearchParam paramSearchParam);

	public abstract List<InvestBorrowModel> getSuccessListByUserid(long paramLong,
			String paramString);

	public abstract List<InvestBorrowModel> getSuccessBorrowList(long paramLong,
			String paramString, int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<InvestBorrowModel> getSuccessBorrowList(String paramString,
			int paramInt1, int paramInt2, SearchParam paramSearchParam);

	public abstract int getSuccessBorrowCount(long paramLong,
			String paramString, SearchParam paramSearchParam);

	public abstract List<UserBorrowModel> getListByUserid(long paramLong);

	public abstract void deleteBorrow(Borrow paramBorrow);

	public abstract List<BorrowModel> getBorrowList(long paramLong);

	public abstract int getBorrowCount(long paramLong);

	public abstract List<UserBorrowModel> getAllBorrowList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getAllBorrowCount(SearchParam paramSearchParam);

	public abstract List<UserBorrowModel> getFullBorrowList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getFullBorrowCount(SearchParam paramSearchParam);

	public abstract List<BorrowModel> unfinshBorrowList(long paramLong);

	public abstract void updateBorrowFlow(Borrow paramBorrow);

	public abstract double hasTenderTotalPerBorrowByUserid(long paramLong1,
			long paramLong2);

	public abstract void addjk(RunBorrow paramRunBorrow);

	public abstract List<RunBorrow> jklist(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getjkCount(SearchParam paramSearchParam);

	public abstract double getRepayTotalWithJin(long paramLong);

	public abstract void updateJinBorrow(Borrow paramBorrow);

	public abstract int updateBorrowStatus(int paramInt, long paramLong);

	public abstract List<BorrowModel> getBorrowListByStatus(int paramInt);

	public abstract List<Advanced> advancedList();

	public abstract double getBorrowAccountSum();

	public abstract double getWaitAccountSum();

	public abstract double getDayBorrowAccountSum(String paramString1,
			String paramString2);

	public abstract List<Repayment> getBorrowList(String paramString, int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract int getBorrowCount(String paramString,
			SearchParam paramSearchParam);

	public abstract void reservation_add(Reservation paramReservation);

	public abstract int getReservation_list_count(SearchParam paramSearchParam);

	public abstract List<ReservationModel> getReservation_list(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract double getBorrowSum();

	public abstract double getTotalRepayAccountAndInterest(String paramString1,
			String paramString2);

	public abstract double getSumBorrowAccount(SearchParam paramSearchParam);

	public abstract int hasBorrowCountByUserid(long paramLong1, long paramLong2);

	public abstract List<Repayment> getFlowBorrowList(String paramString, int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract int releaseFlowBorrow(double paramDouble, long paramLong);

	public abstract int getBorrowCountForSuccess();

	public abstract int getApplyBorrowCount();

	public abstract double getApplyBorrowTotal();
}