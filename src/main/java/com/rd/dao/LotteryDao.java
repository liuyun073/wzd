package com.rd.dao;

import com.rd.domain.LotteryRule;
import com.rd.domain.TenderAddLotteryTimes;
import com.rd.domain.WinningInformation;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface LotteryDao {
	public abstract List<LotteryRule> getList();

	public abstract int getListCount();

	public abstract void add(LotteryRule paramLotteryRule);

	public abstract void update(LotteryRule paramLotteryRule);

	public abstract LotteryRule getLotteryById(long paramLong);

	public abstract void winning_information_add(
			WinningInformation paramWinningInformation);

	public abstract List<WinningInformation> getWinningInformationList();

	public abstract int getWinningInformationByUseridCount(long paramLong1,
			long paramLong2);

	public abstract double getWinningInformationByAwardSum(long paramLong);

	public abstract List<WinningInformation> getWinningInformationList(int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract int getWinningInformationCount(SearchParam paramSearchParam);

	public abstract void addTenderAddLotteryTimes(
			TenderAddLotteryTimes paramTenderAddLotteryTimes);

	public abstract int getTenderAddLotteryTimes(long paramLong);
}