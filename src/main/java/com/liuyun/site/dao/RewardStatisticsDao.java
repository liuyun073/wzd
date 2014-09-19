package com.liuyun.site.dao;

import com.liuyun.site.domain.RewardStatistics;
import com.liuyun.site.domain.User;
import com.liuyun.site.model.RewardStatisticsModel;
import com.liuyun.site.model.RewardStatisticsSumModel;
import com.liuyun.site.model.SearchParam;
import java.util.List;

public abstract interface RewardStatisticsDao {
	public abstract void addRewardStatistics(
			RewardStatistics paramRewardStatistics);

	public abstract List<RewardStatisticsModel> getRewardStatistics(
			SearchParam paramSearchParam);

	public abstract List<RewardStatisticsModel> getRewardStatisticsList(SearchParam paramSearchParam,
			int paramInt1, int paramInt2);

	public abstract int getCount(SearchParam paramSearchParam);

	public abstract RewardStatisticsSumModel getSumAccount(long paramLong,
			String paramString1, String paramString2, String paramString3);

	public abstract void updateReward(RewardStatistics paramRewardStatistics);

	public abstract RewardStatistics getRewardStatisticsById(long paramLong);

	public abstract List<RewardStatisticsModel> getRewardList();

	public abstract User getUserByInviteId(long paramLong, String paramString);

	public abstract RewardStatistics getByTypePkId(long paramLong);

	public abstract RewardStatistics getRewardByPassiveId(long paramLong1,
			long paramLong2);

	public abstract RewardStatistics getRewardByTypeFkId(byte paramByte,
			long paramLong);

	public abstract void updateAccount(double paramDouble, long paramLong);
}