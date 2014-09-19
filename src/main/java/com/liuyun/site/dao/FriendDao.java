package com.liuyun.site.dao;

import com.liuyun.site.domain.Friend;
import java.util.List;

public abstract interface FriendDao {
	public abstract List<Friend> getFriendsRequest(long paramLong);

	public abstract int countFriendsRequest(long paramLong);

	public abstract List<Friend> getFriendsByUserId(long paramLong, int paramInt);

	public abstract int countFriendsByUserId(long paramLong);

	public abstract void addFriend(Friend paramFriend);

	public abstract void addFriendRequest(Friend paramFriend);

	public abstract void modifyFriendRequest(Friend paramFriend);

	public abstract void modifyFriend(Friend paramFriend);

	public abstract void modifyFriendStatus(int paramInt, long paramLong1,
			long paramLong2);

	public abstract void deleteFriend(long paramLong1, long paramLong2);

	public abstract void deleteFriendRequest(long paramLong1, long paramLong2);
}