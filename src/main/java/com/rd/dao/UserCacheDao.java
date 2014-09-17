package com.rd.dao;

import com.rd.domain.UserCache;
import com.rd.model.SearchParam;
import com.rd.model.UserCacheModel;
import com.rd.model.VIPStatisticModel;

import java.util.List;

public abstract interface UserCacheDao {
	public abstract void addUserCache(UserCache paramUserCache);

	public abstract void updateUserCache(UserCache paramUserCache);

	public abstract UserCacheModel getUserCacheByUserid(long paramLong);

	public abstract UserCacheModel validUserVip(long paramLong);

	public abstract List<UserCacheModel> getUserVipinfo(long paramLong, int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract int getUserVipinfo(int paramInt,
			SearchParam paramSearchParam);

	public abstract int getVipStatistic(SearchParam paramSearchParam);

	public abstract List<VIPStatisticModel> getVipStatisticList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<VIPStatisticModel> getVipStatisticList(SearchParam paramSearchParam);

	public abstract int getLoginFailTimes(long paramLong);

	public abstract void cleanLoginFailTimes(long paramLong);

	public abstract void updateLoginFailTimes(long paramLong);
}