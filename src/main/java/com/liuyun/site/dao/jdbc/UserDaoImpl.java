package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.UserDao;
import com.liuyun.site.dao.jdbc.mapper.DetailUserMapper;
import com.liuyun.site.dao.jdbc.mapper.UserMapper;
import com.liuyun.site.dao.jdbc.mapper.UserTypeMapper;
import com.liuyun.site.dao.jdbc.mapper.VipUserMapper;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserType;
import com.liuyun.site.model.DetailUser;
import com.liuyun.site.model.SearchParam;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	String queryUserSql = " from dw_user as p1 left join dw_credit as p2 on p1.user_id=p2.user_id left join dw_account as p9 on p1.user_id=p9.user_id left join dw_credit_rank as p3 on p2.value<=p3.point2  and p2.value>=p3.point1 left join dw_user_cache as p4 on p1.user_id=p4.user_id left join dw_area as p5 on p5.id=p1.province left join dw_area as p6 on p6.id=p1.city left join dw_area as p7 on p7.id=p1.area left join dw_user_type p8 on p8.type_id=p1.type_id where 1=1 ";

	String selectSql = "select p1.*,p2.value as credit_jifen,p9.use_money ,p3.pic as credit_pic,p4.vip_status,p4.vip_verify_time,p4.kefu_addtime,p4.kefu_username,p5.name as provincetext,p6.name as citytext,p7.name as areatext,p8.name as typename";

	String inviteUserSql = " from dw_user p2 left join dw_user invite on invite.user_id= p2.invite_userid left join dw_user_cache c on c.user_id=p2.user_id where 1=1 and invite.username is not null ";

	String queryCountSql = " from dw_user as p1 left join dw_user_cache p4 on p1.user_id=p4.user_id left join dw_user_type as p8 on p1.type_id=p8.type_id where 1=1 ";

	public void addUser(User u) {
		String sql = "insert into  dw_user(username,password,email,realname,invite_userid,status,type_id,qq,addtime,addip,email_status,real_status) values (?,?, ?, ?, ?,?,?,?,?,?,?,'0')";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { u.getUsername(), u.getPassword(), u.getEmail(),
						u.getRealname(), u.getInvite_userid(),
						Integer.valueOf(u.getStatus()),
						Integer.valueOf(u.getType_id()), u.getQq(),
						u.getAddtime(), u.getAddip(), u.getEmail_status() });
	}

	public User getUserByUsername(String username) {
		String sql = "select * from dw_user where username = ?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + username);
		User user = null;
		try {
			user = (User) getJdbcTemplate().queryForObject(sql,
					new Object[] { username }, new UserMapper());
		} catch (DataAccessException e) {
			logger.debug("getUserByUsername() " + e.getMessage());
		}
		return user;
	}

	public User getUserByEmail(String email) {
		String sql = "select user_id, email from dw_user where email = ?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + email);
		User user = null;
		try {
			user = (User) getJdbcTemplate().queryForObject(sql,
					new Object[] { email }, new RowMapper<User>() {
						public User mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							User user = new User();
							user.setUser_id(rs.getLong("user_id"));
							user.setEmail(rs.getString("email"));
							return user;
						}
					});
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return user;
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		String sql = "select * from dw_user where username = ? and password=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + username + "," + password);
		User user = null;
		try {
			user = (User) getJdbcTemplate().queryForObject(sql, new Object[] { username, password }, new UserMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return user;
	}

	public User getUserByUserid(long userid) {
		String sql = "select * from dw_user where user_id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + userid);
		User user = null;
		try {
			user = (User) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(userid) }, new UserMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return user;
	}

	public DetailUser getDetailUserByUserid(long userid) {
		StringBuffer sb = new StringBuffer(this.selectSql);
		sb.append(this.queryUserSql).append(" and p1.user_id=?");
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + userid);
		DetailUser user = null;
		try {
			user = (DetailUser) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(userid) },
					new DetailUserMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return user;
	}

	public void updateUser(User user) {
		String sql = "update dw_user  set realname=?,sex=?,phone=?,province=?,city=?,area=?,invite_money=? where user_id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user.getRealname());
		logger.debug("SQL:" + user.getPhone());
		getJdbcTemplate().update(
				sql,
				new Object[] { user.getRealname(), user.getSex(),
						user.getPhone(), user.getProvince(), user.getCity(),
						user.getArea(), user.getInvite_money(),
						Long.valueOf(user.getUser_id()) });
	}

	public User realnameIdentify(User user) {
		String sql = "update dw_user  set real_status=?,realname=?,sex=?,nation=?,birthday=?,card_type=?,card_id=?,province=?,city=?,area=?,card_pic1=?,card_pic2=? where user_id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user.getRealname());
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { user.getReal_status(), user.getRealname(),
						user.getSex(), user.getNation(), user.getBirthday(),
						user.getCard_type(), user.getCard_id(),
						user.getProvince(), user.getCity(), user.getArea(),
						user.getCard_pic1(), user.getCard_pic2(),
						Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public User modifyUserPwd(User user) {
		String sql = "update dw_user set password=? where user_id=?";
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { user.getPassword(),
						Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public User modifyPayPwd(User user) {
		String sql = "update dw_user set paypassword=? where user_id=?";
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { user.getPaypassword(),
						Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public User modifyAnswer(User user) {
		String sql = "update dw_user set question=?,answer=? where user_id=?";
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { user.getQuestion(), user.getAnswer(),
						Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public List<User> getAllKfList() {
		String sql = "select * from dw_user where type_id=3";
		logger.debug("SQL:" + sql);
		List<User> list = new ArrayList<User>();
		try {
			list = getJdbcTemplate().query(sql, new UserMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return list;
	}

	public List<User> getAllVerifyUser() {
		String sql = "select * from dw_user where type_id=9";
		logger.debug("SQL" + sql);
		List<User> list = new ArrayList<User>();
		try {
			list = getJdbcTemplate().query(sql, new UserMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return list;
	}

	public List<DetailUser> getInvitedUserByUserid(long user_id) {
		StringBuffer sb = new StringBuffer(
				"select p2.*,invite.username as invite_username,c.* ");
		sb.append(this.inviteUserSql).append(" and p2.invite_userid=?");
		String sql = sb.toString();
		List<DetailUser> list = new ArrayList<DetailUser>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) }, new VipUserMapper());
		return list;
	}

	public User getUserById(long user_id) {
		String sql = "select * from dw_user where user_id = ?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		User user = null;
		try {
			user = (User) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(user_id) }, new UserMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return user;
	}

	public List<DetailUser> getUserList(int start, int pernum, SearchParam p) {
		String searchSql = p.getSearchParamSql();
		String limitSql = " limit ?,? ";
		StringBuffer sb = new StringBuffer(this.selectSql).append(this.queryUserSql).append(searchSql).append(limitSql);
		String sql = sb.toString();
		logger.debug("getUserList():" + sql);
		List<DetailUser> list = getJdbcTemplate()
				.query(
						sql,
						new Object[] { Integer.valueOf(start),
								Integer.valueOf(pernum) },
						new DetailUserMapper());
		return list;
	}

	public List<DetailUser> getUserList(SearchParam p) {
		String searchSql = p.getSearchParamSql();
		StringBuffer sb = new StringBuffer(this.selectSql).append(
				this.queryUserSql).append(searchSql);
		String sql = sb.toString();
		logger.debug("getUserList():" + sql);
		List<DetailUser> list = getJdbcTemplate().query(sql, new DetailUserMapper());
		return list;
	}

	public int getUserCount(SearchParam p) {
		StringBuffer sb = new StringBuffer("select count(*) ");
		sb.append(this.queryCountSql).append(p.getSearchParamSql());
		String sql = sb.toString();
		logger.debug("getUserCount():" + sql);
		int total = count(sb.toString());
		return total;
	}

	public List<UserType> getAllUserType() {
		String sql = "select * from dw_user_type";
		return getJdbcTemplate().query(sql, new UserTypeMapper());
	}

	public void updateUser(User user, boolean modify) {
		String sql = (modify) ? "update dw_user  set password=?,realname=?,tel=?,type_id=?,status=?,invite_userid=?,invite_money=?,qq=?,wangwang=?,address=?,serial_id=?,islock=?,sex=?,nation=?,birthday=?,card_type=?,card_id=?,phone=?,province=?,city=?,area=?,upip=?,uptime=?,email=?,logintime=?  where user_id=?"
				: "update dw_user  set realname=?,tel=?,type_id=?,status=?,invite_userid=?,invite_money=?,qq=?,wangwang=?,address=?,serial_id=?,islock=?,sex=?,nation=?,birthday=?,card_type=?,card_id=?,phone=?,province=?,city=?,area=? ,upip=?,uptime=?,email=?,logintime=? where user_id=?";
		if (modify)
			getJdbcTemplate().update(
					sql,
					new Object[] { user.getPassword(), user.getRealname(),
							user.getTel(), Integer.valueOf(user.getType_id()),
							Integer.valueOf(user.getStatus()),
							user.getInvite_userid(), user.getInvite_money(),
							user.getQq(), user.getWangwang(),
							user.getAddress(), user.getSerial_id(),
							Integer.valueOf(user.getIslock()), user.getSex(),
							user.getNation(), user.getBirthday(),
							user.getCard_type(), user.getCard_id(),
							user.getPhone(), user.getProvince(),
							user.getCity(), user.getArea(), user.getUpip(),
							user.getUptime(), user.getEmail(),
							Long.valueOf(user.getLogintime()),
							Long.valueOf(user.getUser_id()) });
		else
			getJdbcTemplate().update(
					sql,
					new Object[] { user.getRealname(), user.getTel(),
							Integer.valueOf(user.getType_id()),
							Integer.valueOf(user.getStatus()),
							user.getInvite_userid(), user.getInvite_money(),
							user.getQq(), user.getWangwang(),
							user.getAddress(), user.getSerial_id(),
							Integer.valueOf(user.getIslock()), user.getSex(),
							user.getNation(), user.getBirthday(),
							user.getCard_type(), user.getCard_id(),
							user.getPhone(), user.getProvince(),
							user.getCity(), user.getArea(), user.getUpip(),
							user.getUptime(), user.getEmail(),
							Long.valueOf(user.getLogintime()),
							Long.valueOf(user.getUser_id()) });
	}

	public List<DetailUser> getSearchUserOrRealName(int page, SearchParam p) {
		String sql = "SELECT p.user_id,p.username,p.realname,p1.vip_status,p.email_status,p.phone_status,p1.vip_status,p1.vip_verify_time from dw_user as p LEFT JOIN dw_user_cache as p1 on p.user_id=p1.user_id  where ";

		logger.debug(sql);
		return getJdbcTemplate().query(sql,
				new Object[] { p.getSearchParamSql() }, new RowMapper<DetailUser>() {
					public DetailUser mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						DetailUser user = new DetailUser();
						user.setUser_id(rs.getLong("user_id"));
						user.setUsername(rs.getString("username"));
						user.setRealname(rs.getString("realname"));
						user.setVip_status(rs.getInt("vip_status"));
						user.setEmail_status(rs.getString("email_status"));
						user.setPhone(rs.getString("phone_status"));
						user.setVip_verify_time(rs.getLong("vip_verify_time"));
						return user;
					}
				});
	}

	public int getSearchUserOrRealName(SearchParam p) {
		String sql = "SELECT p.user_id,p.username,p.realname,p1.vip_status,p.email_status,p.phone_status,p1.vip_status,p1.vip_verify_time from dw_user as p LEFT JOIN dw_user_cache as p1 on p.user_id=p1.user_id  where ";
		return getJdbcTemplate().queryForInt(sql,
				new Object[] { p.getSearchParamSql() });
	}

	public String getUserTypeByid(String s) {
		String sql = "SELECT `name` from dw_user_type where type_id=?";
		return (String) getJdbcTemplate().queryForObject(sql,
				new Object[] { s }, String.class);
	}

	public void updateUserType(List<UserType> list) {
		String sql = "update dw_user_type set name=?,purview=?,`order`=?,status=?,type=?,summary=?,remark=?,addtime=? where type_id=? ";
		for (UserType u : list) {
			if (u == null)
				continue;
			getJdbcTemplate().update(
					sql,
					new Object[] { u.getName(), u.getPurview(), u.getOrder(),
							Integer.valueOf(u.getStatus()),
							Integer.valueOf(u.getType()), u.getSummary(),
							u.getRemark(), u.getAddtime(),
							Long.valueOf(u.getType_id()) });
		}
	}

	public void addUserType(UserType userType) {
		String sql = "insert into dw_user_type(`name`,purview,`order`,`status`,type,summary,remark,addtime,addip) VALUES(?,?,?,?,?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { userType.getName(), userType.getPurview(),
						userType.getOrder(),
						Integer.valueOf(userType.getStatus()),
						Integer.valueOf(userType.getType()),
						userType.getSummary(), userType.getRemark(),
						userType.getAddtime(), userType.getAddip() });
	}

	public void deleteUserTypeById(long id) {
		String sql = "delete from dw_user_type where type_id=?";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(id) });
	}

	public int getInviteUserCount(SearchParam param) {
		StringBuffer sb = new StringBuffer("select count(p2.user_id) ");
		sb.append(this.inviteUserSql).append(param.getSearchParamSql());
		String sql = sb.toString();
		int total = count(sql);
		return total;
	}

	public List<DetailUser> getInviteUserList(int start, int pernum, SearchParam param) {
		StringBuffer sb = new StringBuffer(
				"select p2.*,invite.username as invite_username,c.*  ");
		String orderSql = " order by p2.addtime desc ";
		String limitSql = " limit ?,?";
		sb.append(this.inviteUserSql).append(param.getSearchParamSql()).append(
				orderSql).append(limitSql);
		String sql = sb.toString();
		List<DetailUser> list = new ArrayList<DetailUser>();
		list = getJdbcTemplate()
				.query(
						sql,
						new Object[] { Integer.valueOf(start),
								Integer.valueOf(pernum) }, new VipUserMapper());
		return list;
	}

	public UserType getUserTypeById(long id) {
		String sql = "SELECT * from dw_user_type where type_id=?";
		UserType userType = null;
		try {
			userType = (UserType) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(id) }, new UserTypeMapper());
		} catch (DataAccessException e) {
			logger.debug("getUserTypeById():not data found!");
		}
		return userType;
	}

	public boolean isRoleHasPurview(long id) {
		boolean isRoleHasPurview = false;
		String sql = "select * from dw_user where type_id=? ";
		SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(id) });
		if (rowSet.next()) {
			isRoleHasPurview = true;
		}
		return isRoleHasPurview;
	}

	public User modifyEmail_status(User user) {
		String sql = "update dw_user set email_status=? where user_id=?";
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { user.getEmail_status(),
						Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public User modifyRealname(User user) {
		String sql = "update dw_user set realname=? where user_id=?";
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { user.getRealname(),
						Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public User modifyEmail(User user) {
		String sql = "update dw_user set email=? where user_id=?";
		int result = getJdbcTemplate()
				.update(
						sql,
						new Object[] { user.getEmail(),
								Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public User modifyPhone(User user) {
		String sql = "update dw_user set phone=?,phone_status=? where user_id=?";
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { user.getPhone(), user.getPhone_status(),
						Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public User modifyPhone_status(User user) {
		String sql = "update dw_user set phone_status=? where user_id=?";
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { user.getPhone_status(),
						Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public User modifyVideo_status(User user) {
		String sql = "update dw_user set video_status=? where user_id=?";
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(user.getVideo_status()),
						Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public User modifyScene_status(User user) {
		String sql = "update dw_user set scene_status=? where user_id=?";
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(user.getScene_status()),
						Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public User modifyReal_status(User user) {
		String sql = "update dw_user set real_status=? where user_id=?";
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { user.getReal_status(),
						Long.valueOf(user.getUser_id()) });
		if (result < 1) {
			user = null;
		}
		return user;
	}

	public int getUserCount(SearchParam p, int type) {
		String searchSql = p.getSearchParamSql();
		String csql = "select count(*) from dw_user as p1 left join dw_user_cache c on p1.user_id=c.user_id where p1.type_id =? ";
		StringBuffer sb = new StringBuffer(csql).append(searchSql);
		String sql = sb.toString();
		logger.debug("getUserCount():" + sql);
		return getJdbcTemplate().queryForInt(sql,
				new Object[] { Integer.valueOf(type) });
	}

	public List<DetailUser> getUserList(int start, int pernum, SearchParam p, int type) {
		String searchSql = p.getSearchParamSql();
		String limitSql = " limit ?,? ";
		StringBuffer sb = new StringBuffer(this.selectSql)
				.append(" from dw_user as p1 left join dw_credit as p2 on p1.user_id=p2.user_id left join dw_account as p9 on p1.user_id=p9.user_id left join dw_credit_rank as p3 on p2.value<=p3.point2  and p2.value>=p3.point1 left join dw_user_cache as p4 on p1.user_id=p4.user_id left join dw_area as p5 on p5.id=p1.province left join dw_area as p6 on p6.id=p1.city left join dw_area as p7 on p7.id=p1.area left join dw_user_type p8 on p8.type_id=p1.type_id where p1.type_id=? ")
				.append(searchSql).append(limitSql);
		String sql = sb.toString();
		logger.debug("getUserList():" + sql);
		List<DetailUser> list = getJdbcTemplate().query(
				sql,
				new Object[] { Integer.valueOf(type), Integer.valueOf(start),
						Integer.valueOf(pernum) }, new DetailUserMapper());
		return list;
	}

	public DetailUser getDetailUser(long userid, int type) {
		StringBuffer sb = new StringBuffer(this.selectSql);
		sb.append(this.queryUserSql).append(
				" and p1.user_id =? and p1.type_id =?");
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + userid);
		DetailUser user = null;
		try {
			user = (DetailUser) getJdbcTemplate()
					.queryForObject(
							sql,
							new Object[] { Long.valueOf(userid),
									Integer.valueOf(type) },
							new DetailUserMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return user;
	}

	public void updateUserLastInfo(User user) {
		String sql = "update dw_user  set lasttime =?,logintime=? where user_id =?";
		getJdbcTemplate().update(
				sql,
				new Object[] { user.getLasttime(),
						Long.valueOf(user.getLogintime()),
						Long.valueOf(user.getUser_id()) });
	}

	public User getUserByCard(String card) {
		String sql = "select * from dw_user where real_status=1 and card_id=?";
		List<User> list = getJdbcTemplate().query(sql, new Object[] { card },
				new UserMapper());
		if ((list != null) && (list.size() > 0)) {
			return (User) list.get(0);
		}
		return null;
	}

	public List<User> getAllUser(int type) {
		String sql = "select * from dw_user where type_id=?";
		logger.debug("SQL:" + sql);
		List<User> list = new ArrayList<User>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Integer.valueOf(type) }, new UserMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return list;
	}

	public int getUserCount() {
		String sql = "select count(*) from dw_user";
		logger.debug("getUserCount():" + sql);
		return getJdbcTemplate().queryForInt(sql);
	}

	public List<DetailUser> getInviteUserList(SearchParam param) {
		StringBuffer sb = new StringBuffer(
				"select p2.*,invite.username as invite_username,c.*  ");
		String orderSql = " order by p2.addtime desc ";
		sb.append(this.inviteUserSql).append(param.getSearchParamSql()).append(
				orderSql);
		String sql = sb.toString();
		List<DetailUser> list = new ArrayList<DetailUser>();
		list = getJdbcTemplate().query(sql, new VipUserMapper());
		return list;
	}

	public void updateUserIsLock(long userid) {
		String sql = "update dw_user set islock=1 where user_id=?";
		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(userid) });
	}

	public List<User> getKfListWithLimit(int start, int end) {
		String sql = "select * from dw_user where type_id=3 limit " + start
				+ "," + end;
		logger.debug("SQL:" + sql);
		List<User> list = new ArrayList<User>();
		try {
			list = getJdbcTemplate().query(sql, new UserMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return list;
	}

	public List<User> getVipUser() {
		String sql = "select * from dw_user as u left join dw_user_cache as uc on uc.user_id = u.user_id where uc.vip_status = 1;";
		List<User> list = new ArrayList<User>();
		try {
			list = getJdbcTemplate().query(sql, new UserMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;
	}
}