package com.liuyun.site.service;

import com.liuyun.site.domain.RewardStatistics;
import com.liuyun.site.domain.Rule;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserCache;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.RewardStatisticsModel;
import com.liuyun.site.model.RewardStatisticsSumModel;
import com.liuyun.site.model.SearchParam;
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