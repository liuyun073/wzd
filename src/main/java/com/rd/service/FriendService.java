package com.rd.service;

import com.rd.domain.Friend;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface FriendService {
	public abstract List<Friend> getFriendsRequest(long paramLong);

	public abstract int countFriendsRequest(long paramLong);

	public abstract List<Friend> getFriendsByUserId(long paramLong);

	public abstract int countFriendsByUserId(long paramLong);

	public abstract void addFriend(Friend paramFriend);

	public abstract void addFriendRequest(Friend paramFriend);

	public abstract void addBlackFriend(long paramLong, String paramString);

	public abstract void addBlackFriend(Friend paramFriend);

	public abstract void delFriend(long paramLong, String paramString);

	public abstract void readdFriend(long paramLong, String paramString);

	public abstract List<Friend> getBlackList(long paramLong);

	public abstract PageDataList getTiChengAcount(int paramInt,
			SearchParam paramSearchParam);

	public abstract PageDataList getFriendTiChengAcount(int paramInt,
			SearchParam paramSearchParam);
}