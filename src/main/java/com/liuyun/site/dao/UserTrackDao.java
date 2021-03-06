package com.liuyun.site.dao;

import com.liuyun.site.domain.UserTrack;

public abstract interface UserTrackDao {
	public abstract void addUserTrack(UserTrack paramUserTrack);

	public abstract UserTrack getLastUserTrack(long paramLong);

	public abstract int getUserTrackCount(long paramLong);
}