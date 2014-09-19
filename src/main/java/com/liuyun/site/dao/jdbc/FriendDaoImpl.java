package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.FriendDao;
import com.liuyun.site.dao.jdbc.mapper.FriendsMapper;
import com.liuyun.site.domain.Friend;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class FriendDaoImpl extends BaseDaoImpl implements FriendDao {
	private static Logger logger = Logger.getLogger(BorrowDaoImpl.class);

	public List<Friend> getFriendsRequest(long user_id) {
		String sql = "select a.*,b.username as username from dw_friends_request a,dw_user b where a.user_id=b.user_id and a.friends_userid=? and a.status=0 order by a.addtime desc";

		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		List<Friend> list = new ArrayList<Friend>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new FriendsMapper());
		return list;
	}

	public int countFriendsRequest(long user_id) {
		int total = 0;
		String sql = "select count(1) from dw_friends_request a,dw_user b where a.friends_userid=b.user_id and a.user_id=? and a.status=0";

		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		try {
			total = getJdbcTemplate().queryForInt(sql, new Object[] { Long.valueOf(user_id) });
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return total;
	}

	public List<Friend> getFriendsByUserId(long user_id, int status) {
		String sql = "select a.*,b.username as username from dw_friends a,dw_user b where a.friends_userid=b.user_id and a.user_id=? and a.status=? order by a.addtime desc";

		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		List<Friend> list = new ArrayList<Friend>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) }, new FriendsMapper());
		return list;
	}

	public int countFriendsByUserId(long user_id) {
		int total = 0;
		String sql = "select count(1) from dw_friends a where a.user_id=?";

		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		try {
			total = getJdbcTemplate().queryForInt(sql, new Object[] { Long.valueOf(user_id) });
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return total;
	}

	public void addFriend(Friend friend) {
		String sql = "insert into dw_friends(user_id,friends_userid,status,type,content,addtime,addip) values(?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(friend.getUser_id()),
						Long.valueOf(friend.getFriends_userid()),
						Integer.valueOf(friend.getStatus()),
						Integer.valueOf(friend.getType()), friend.getContent(),
						friend.getAddtime(), friend.getAddip() });
	}

	public void addFriendRequest(Friend friend) {
		String sql = "insert into dw_friends_request(user_id,friends_userid,status,type,content,addtime,addip) values(?,?,?,?,?,?,?)";

		logger.info("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(friend.getUser_id()),
						Long.valueOf(friend.getFriends_userid()),
						Integer.valueOf(friend.getStatus()),
						Integer.valueOf(friend.getType()), friend.getContent(),
						friend.getAddtime(), friend.getAddip() });
	}

	public void modifyFriendRequest(Friend friend) {
		String sql = "update dw_friends_request set status=?,type=?,content=? where (user_id=? and friends_userid=?) or (friends_userid=? and user_id=?)";

		logger.info(sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(friend.getStatus()),
						Integer.valueOf(friend.getType()), friend.getContent(),
						Long.valueOf(friend.getUser_id()),
						Long.valueOf(friend.getFriends_userid()),
						Long.valueOf(friend.getUser_id()),
						Long.valueOf(friend.getFriends_userid()) });
	}

	public void modifyFriend(Friend friend) {
		String sql = "update dw_friends set status=?,type=?,content=? where (user_id=? and friends_userid=?) or (friends_userid=? and user_id=?)";

		logger.info(sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(friend.getStatus()),
						Integer.valueOf(friend.getType()), friend.getContent(),
						Long.valueOf(friend.getUser_id()),
						Long.valueOf(friend.getFriends_userid()),
						Long.valueOf(friend.getUser_id()),
						Long.valueOf(friend.getFriends_userid()) });
	}

	public void modifyFriendStatus(int status, long user_id, long frienduser_id) {
		String sql = "update dw_friends set status=? where (user_id=? and friends_userid=?) ";

		logger.info(sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(status), Long.valueOf(user_id),
						Long.valueOf(frienduser_id) });
	}

	public void deleteFriend(long user_id, long frienduser_id) {
		String sql = "delete from dw_friends where (user_id=? and friends_userid=?) ";
		logger.info(sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(user_id),
						Long.valueOf(frienduser_id) });
	}

	public void deleteFriendRequest(long user_id, long frienduser_id) {
		String sql = "delete from dw_friends_request where (user_id=? and friends_userid=?) ";
		logger.info(sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(user_id),
						Long.valueOf(frienduser_id) });
	}
}