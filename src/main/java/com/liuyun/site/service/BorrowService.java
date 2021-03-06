package com.liuyun.site.service;

import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.Advanced;
import com.liuyun.site.domain.BonusApr;
import com.liuyun.site.domain.Borrow;
import com.liuyun.site.domain.BorrowAuto;
import com.liuyun.site.domain.BorrowFlow;
import com.liuyun.site.domain.OperationLog;
import com.liuyun.site.domain.Protocol;
import com.liuyun.site.domain.Repayment;
import com.liuyun.site.domain.Reservation;
import com.liuyun.site.domain.RunBorrow;
import com.liuyun.site.domain.Tender;
import com.liuyun.site.domain.TenderAwardYesAndNo;
import com.liuyun.site.model.BorrowNTender;
import com.liuyun.site.model.BorrowTender;
import com.liuyun.site.model.DetailCollection;
import com.liuyun.site.model.DetailTender;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.ProtocolModel;
import com.liuyun.site.model.RankModel;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserBorrowModel;
import com.liuyun.site.model.borrow.BorrowModel;
import com.liuyun.site.model.borrow.FastExpireModel;
import com.liuyun.site.model.invest.CollectionList;
import com.liuyun.site.model.invest.InvestBorrowList;
import com.liuyun.site.model.invest.InvestBorrowModel;

import java.util.List;
import java.util.Map;

public abstract interface BorrowService {
	public abstract List<UserBorrowModel> getList();

	public abstract List<UserBorrowModel> getList(int paramInt);

	public abstract List<UserBorrowModel> getList(int paramInt1, int paramInt2);

	public abstract PageDataList getList(BorrowModel paramBorrowModel,
			int paramInt);

	public abstract PageDataList getList(BorrowModel paramBorrowModel);

	public abstract List<UserBorrowModel> getRecommendList();

	public abstract void updateRecommendBorrow(Borrow paramBorrow);

	public abstract BorrowModel getBorrow(long paramLong);

	public abstract Tender getTenderById(long paramLong);

	public abstract UserBorrowModel getUserBorrow(long paramLong);

	public abstract List<BorrowTender> getTenderList(long paramLong);

	public abstract List<BorrowTender> getTenderList(long paramLong1, long paramLong2);

	public abstract PageDataList getTenderList(long paramLong, int paramInt,
			SearchParam paramSearchParam);

	public abstract void addBorrow(BorrowModel paramBorrowModel,
			AccountLog paramAccountLog);

	public abstract void updateBorrow(Borrow paramBorrow);

	public abstract void verifyBorrow(BorrowModel paramBorrowModel,
			AccountLog paramAccountLog, OperationLog paramOperationLog);

	public abstract void deleteBorrow(BorrowModel paramBorrowModel,
			AccountLog paramAccountLog);

	public abstract List<InvestBorrowModel> getSuccessListByUserid(long paramLong,
			String paramString);

	public abstract InvestBorrowList getSuccessListByUserid(long paramLong,
			String paramString, int paramInt, SearchParam paramSearchParam);

	public abstract List<UserBorrowModel> getSuccessListForIndex(String paramString, int paramInt);

	public abstract Tender addTender(Tender paramTender,
			BorrowModel paramBorrowModel, Account paramAccount,
			AccountLog paramAccountLog, Protocol paramProtocol);

	public abstract BorrowFlow addFlow(BorrowFlow paramBorrowFlow,
			BorrowModel paramBorrowModel, Account paramAccount,
			AccountLog paramAccountLog);

	public abstract List<DetailCollection> getDetailCollectionList(long paramLong, int paramInt);

	public abstract CollectionList getCollectionList(long paramLong,
			int paramInt1, int paramInt2, SearchParam paramSearchParam);

	public abstract PageDataList getCollectionListByBorrow(long paramLong,
			int paramInt, SearchParam paramSearchParam);

	public abstract List<DetailCollection> getCollectionListByBorrow(long paramLong);

	public abstract List<DetailTender> getInvestTenderListByUserid(long paramLong);

	public abstract PageDataList getInvestTenderListByUserid(long paramLong,
			int paramInt, SearchParam paramSearchParam);

	public abstract List<BorrowAuto> getBorrowAutoList(long paramLong);

	public abstract void addBorrowAuto(BorrowAuto paramBorrowAuto);

	public abstract void modifyBorrowAuto(BorrowAuto paramBorrowAuto);

	public abstract void deleteBorrowAuto(long paramLong);

	public abstract Account getAccount(long paramLong);

	public abstract List<BorrowModel> getBorrowList(long paramLong);

	public abstract List<DetailTender> getInvestList(long paramLong);

	public abstract int getBorrowCount(long paramLong);

	public abstract PageDataList getAllBorrowList(int paramInt,
			SearchParam paramSearchParam);

	public abstract PageDataList getFullBorrowList(int paramInt,
			SearchParam paramSearchParam);

	public abstract void verifyFullBorrow(BorrowModel paramBorrowModel,
			OperationLog paramOperationLog);

	public abstract void verifyFullBorrow(BorrowModel paramBorrowModel,
			AccountLog paramAccountLog);

	public abstract List<BorrowModel> unfinshBorrowList(long paramLong);

	public abstract List<BonusApr> getBonusAprList(long paramLong);

	public abstract void addBonusApr(Borrow paramBorrow);

	public abstract void modifyBonusAprById(long paramLong, double paramDouble);

	public abstract List<DetailTender> getInvestTenderingListByUserid(long paramLong);

	public abstract List<RankModel> getRankListByTime(String paramString1,
			String paramString2);

	public abstract List<RankModel> getRankList();

	public abstract List<RankModel> getAllRankList();

	public abstract List<RankModel> getMoreRankListByTime(String paramString1,
			String paramString2, int paramInt);

	public abstract PageDataList getInvestTenderingListByUserid(long paramLong,
			int paramInt, SearchParam paramSearchParam);

	public abstract double hasTenderTotalPerBorrowByUserid(long paramLong1,
			long paramLong2);

	public abstract List<BorrowTender> getNewTenderList();

	public abstract List<BorrowTender> getSuccessTenderList();

	public abstract void addJk(RunBorrow paramRunBorrow);

	public abstract PageDataList jk(int paramInt, SearchParam paramSearchParam);

	public abstract List<BorrowTender> getAllTenderList(long paramLong);

	public abstract double getRepayTotalWithJin(long paramLong);

	public abstract List<BorrowModel> getBorrowFlowListByuserId(long paramLong);

	public abstract List<Repayment> getRepaymentList(String paramString, long paramLong);

	public abstract PageDataList getAllBorrowTenderList(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<BorrowNTender> getAllBorrowTenderList(SearchParam paramSearchParam);

	public abstract void updateJinBorrow(Borrow paramBorrow);

	public abstract List<Advanced> advancedList();

	public abstract Advanced borrowAccountSum(String paramString1,
			String paramString2);

	public abstract PageDataList getBorrowList(String paramString,
			int paramInt, SearchParam paramSearchParam);

	public abstract double getSumBorrowAccount(SearchParam paramSearchParam);

	public abstract double getBorrowTotal();

	public abstract double getTotalRepayAccountAndInterest(String paramString1,
			String paramString2);

	public abstract int hasBorrowCountByUserid(long paramLong1, long paramLong2);

	public abstract double hasTenderListByUserid(long paramLong,
			String paramString1, String paramString2);

	public abstract void updateTenderAward(
			TenderAwardYesAndNo paramTenderAwardYesAndNo);

	public abstract TenderAwardYesAndNo TenderAward(int paramInt);

	public abstract List<Repayment> getAllRepaymentList(int paramInt);

	public abstract List<DetailCollection> getAllFlowCollectList(int paramInt);

	public abstract List<BorrowModel> getBorrowListByStatus(int paramInt);

	public abstract void reservation_add(Reservation paramReservation);

	public abstract PageDataList reservation_list(SearchParam paramSearchParam,
			int paramInt);

	public abstract void verifyFullBorrow(BorrowModel paramBorrowModel,
			OperationLog paramOperationLog, Protocol paramProtocol);

	public abstract PageDataList protocolList(SearchParam paramSearchParam,
			int paramInt);

	public abstract ProtocolModel getProtocolByid(long paramLong);

	public abstract void addLateLog(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> getLateLog(String paramString);

	public abstract int getBorrowCountForSuccess();

	public abstract int getTotalTenderTimesByUserid(long paramLong);

	public abstract void getTenderAddLotteryTimes(Tender paramTender);

	public abstract PageDataList getFastExpireList(
			SearchParam paramSearchParam, int paramInt);

	public abstract List<FastExpireModel> getFastExpireList(SearchParam paramSearchParam);

	public abstract int getApplyBorrowCount();

	public abstract double getApplyBorrowTotal();
}