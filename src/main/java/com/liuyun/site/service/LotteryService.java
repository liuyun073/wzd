package com.liuyun.site.service;

import com.liuyun.site.domain.LotteryRule;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.WinningInformation;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import java.util.List;

public abstract interface LotteryService {
	public abstract List<LotteryRule> lotteryList();

	public abstract void modify_lottery(LotteryRule paramLotteryRule);

	public abstract void add_lottery(LotteryRule paramLotteryRule);

	public abstract LotteryRule getLotteryById(long paramLong);

	public abstract void winning_information_add(
			WinningInformation paramWinningInformation);

	public abstract List winningInformationList();

	public abstract int getWinningInformationByUseridCount(long paramLong1,
			long paramLong2);

	public abstract double getWinningInformationByAwardSum(long paramLong);

	public abstract String initLottery(String paramString1,
			String paramString2, User paramUser, int paramInt);

	public abstract PageDataList getWinningInfortionList(int paramInt,
			SearchParam paramSearchParam);
}