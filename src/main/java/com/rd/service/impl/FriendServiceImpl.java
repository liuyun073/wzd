package com.rd.service.impl;

import com.rd.dao.AccountDao;
import com.rd.dao.FriendDao;
import com.rd.dao.UserDao;
import com.rd.domain.Friend;
import com.rd.domain.User;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.service.FriendService;
import com.rd.tool.Page;
import java.util.List;

public class FriendServiceImpl implements FriendService {
	private FriendDao friendDao;
	private UserDao userDao;
	private AccountDao accountDao;

	public AccountDao getAccountDao() {
		return this.accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public FriendDao getFriendDao() {
		return this.friendDao;
	}

	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public List<Friend> getFriendsRequest(long userId) {
		return this.friendDao.getFriendsRequest(userId);
	}

	public List<Friend> getFriendsByUserId(long userId) {
		return this.friendDao.getFriendsByUserId(userId, 1);
	}

	public int countFriendsRequest(long userId) {
		return this.friendDao.countFriendsRequest(userId);
	}

	public int countFriendsByUserId(long userId) {
		return this.friendDao.countFriendsByUserId(userId);
	}

	public void addFriend(Friend friend) {
		this.friendDao.addFriend(friend);
		friend.setStatus(1);
		this.friendDao.modifyFriendRequest(friend);
		this.friendDao.modifyFriend(friend);
	}

	public void addFriendRequest(Friend friend) {
		this.friendDao.addFriend(friend);
		this.friendDao.addFriendRequest(friend);
	}

	public void addBlackFriend(long user_id, String username) {
		User u = this.userDao.getUserByUsername(username);
		if (u == null)
			return;
		this.friendDao.modifyFriendStatus(2, user_id, u.getUser_id());
	}

	public void delFriend(long user_id, String username) {
		User u = this.userDao.getUserByUsername(username);
		if (u == null)
			return;
		long frienduser_id = u.getUser_id();
		this.friendDao.deleteFriend(user_id, frienduser_id);
		this.friendDao.deleteFriendRequest(user_id, frienduser_id);
	}

	public void readdFriend(long user_id, String username) {
		User u = this.userDao.getUserByUsername(username);
		if (u == null)
			return;
		this.friendDao.modifyFriendStatus(1, user_id, u.getUser_id());
	}

	public List<Friend> getBlackList(long userid) {
		return this.friendDao.getFriendsByUserId(userid, 2);
	}

	public void addBlackFriend(Friend friend) {
		User u = this.userDao.getUserByUsername(friend.getFriends_username());
		friend.setFriends_userid(u.getUser_id());

		this.friendDao.deleteFriend(friend.getUser_id(), friend
				.getFriends_userid());
		this.friendDao.deleteFriendRequest(friend.getUser_id(), friend
				.getFriends_userid());
		this.friendDao.addFriend(friend);
	}

	public PageDataList getTiChengAcount(int page, SearchParam param) {
		int total = this.accountDao.getTiChengAccountCount(param);
		Page p = new Page(total, page);
		List list = this.accountDao.getTiChengAccountList(p.getStart(), p
				.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public PageDataList getFriendTiChengAcount(int page, SearchParam param) {
		int total = this.accountDao.getFriendTiChengAccountCount(param);
		Page p = new Page(total, page);
		List list = this.accountDao.getFriendTiChengAccountList(p.getStart(), p
				.getPernum(), param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}
}