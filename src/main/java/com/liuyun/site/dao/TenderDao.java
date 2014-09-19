package com.liuyun.site.dao;

import com.liuyun.site.domain.Tender;
import com.liuyun.site.domain.TenderAwardYesAndNo;
import com.liuyun.site.model.BorrowNTender;
import com.liuyun.site.model.BorrowTender;
import com.liuyun.site.model.DetailTender;
import com.liuyun.site.model.RankModel;
import com.liuyun.site.model.SearchParam;
import java.util.List;

public abstract interface TenderDao {
	public abstract List<BorrowTender> getTenderListByBorrowid(long paramLong);

	public abstract List<BorrowTender> getTenderListByBorrowid(long paramLong, int paramInt);

	public abstract List<BorrowTender> getTenderListByBorrow(long paramLong);

	public abstract List<BorrowTender> getTenderListByBorrow(long paramLong1, long paramLong2);

	public abstract List<BorrowTender> getTenderListByBorrowid(long paramLong, int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract int getTenderCountByBorrowid(long paramLong,
			SearchParam paramSearchParam);

	public abstract Tender addTender(Tender paramTender);

	public abstract Tender modifyTender(Tender paramTender);

	public abstract List<DetailTender> getInvestTenderListByUserid(long paramLong);

	public abstract List<DetailTender> getInvestTenderingListByUserid(long paramLong);

	public abstract List<DetailTender> getInvestTenderListByUserid(long paramLong,
			int paramInt1, int paramInt2, SearchParam paramSearchParam);

	public abstract int getInvestTenderCountByUserid(long paramLong,
			SearchParam paramSearchParam);

	public abstract List<DetailTender> getInvestTenderingListByUserid(long paramLong,
			int paramInt1, int paramInt2, SearchParam paramSearchParam);

	public abstract int getInvestTenderingCountByUserid(long paramLong,
			SearchParam paramSearchParam);

	public abstract List<DetailTender> getSuccessTenderList(long paramLong);

	public abstract List<DetailTender> getSuccessTenderList(long paramLong, int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract double getTotalTenderByUserid(long paramLong);

	public abstract int getTotalTenderTimesByUserid(long paramLong);

	public abstract List<RankModel> getRankListByTime(String paramString1,
			String paramString2);

	public abstract List<RankModel> getMoreRankListByTime(String paramString1,
			String paramString2, int paramInt);

	public abstract List<RankModel> getRankList();

	public abstract List<RankModel> getAllRankList();

	public abstract List<BorrowTender> getTenderList();

	public abstract List<BorrowTender> getNewTenderList();

	public abstract List<BorrowNTender> getAllTenderList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getAllTenderCount(SearchParam paramSearchParam);

	public abstract List<BorrowNTender> getAllTenderList(SearchParam paramSearchParam);

	public abstract List<BorrowTender> allTenderListBybid(long paramLong);

	public abstract Tender getTenderById(long paramLong);

	public abstract void modifyRepayTender(double paramDouble1,
			double paramDouble2, long paramLong);

	public abstract double getTenderListByUserid(long paramLong,
			String paramString1, String paramString2);

	public abstract void updateTenderAwardYesAndNo(
			TenderAwardYesAndNo paramTenderAwardYesAndNo);

	public abstract TenderAwardYesAndNo tenderAwardYesAndNo(int paramInt);

	public abstract int getAutoTenderByUserid(long paramLong1, long paramLong2,
			long paramLong3, long paramLong4);
}