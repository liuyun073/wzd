package com.rd.service;

import com.rd.domain.RewardStatistics;
import com.rd.domain.Rule;
import com.rd.domain.User;
import com.rd.domain.UserCache;
import com.rd.model.PageDataList;
import com.rd.model.RewardStatisticsModel;
import com.rd.model.RewardStatisticsSumModel;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface RewardStatisticsService {
	public abstract void addRewardStatistics(
			RewardStatistics paramRewardStatistics);

	public abstract List<RewardStatisticsModel> getRewardStatistics(
			SearchParam paramSearchParam);

	public abstract PageDataList getRewardStatisticsList(
			SearchParam paramSearchParam, int paramInt);

	public abstract void verifyReward(RewardStatistics paramRewardStatistics,
			String paramString1, User paramUser, String paramString2);

	public abstract RewardStatistics getRewardStatisticsById(long paramLong);

	public abstract List<RewardStatisticsModel> getRewardList();

	public abstract void addRewardStatistics(long paramLong, String paramString);

	public abstract void updateReward(String paramString,
			UserCache paramUserCache);

	public abstract RewardStatisticsSumModel getSumAccount(long paramLong,
			String paramString1, String paramString2, String paramString3);

	public abstract String getRule(Rule paramRule, String paramString);

	public abstract void updateAccount(double paramDouble, long paramLong);
}