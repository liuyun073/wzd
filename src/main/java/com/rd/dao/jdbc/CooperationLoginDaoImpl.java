package com.rd.dao.jdbc;

import com.rd.dao.CooperationLoginDao;
import com.rd.dao.jdbc.mapper.CooperationLoginMapper;
import com.rd.domain.CooperationLogin;
import org.apache.log4j.Logger;

public class CooperationLoginDaoImpl extends BaseDaoImpl implements
		CooperationLoginDao {
	private static Logger logger = Logger.getLogger(CooperationLoginDaoImpl.class);

	public void addCooperationLogin(CooperationLogin c) {
		String sql = "insert into dw_cooperation_login(type,user_id,open_id,open_key,gmt_create) values(?,?,?,?,now())";
		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql, new Object[] { c.getType(), Long.valueOf(c.getUser_id()), c.getOpen_id(), c.getOpen_key() });
	}

	public CooperationLogin getCooperationLogin(String open_id,
			String opend_key, Byte type) {
		String sql = "select * from dw_cooperation_login where open_id = ? and open_key = ? and type = ?";

		CooperationLogin cooperationLogin = null;
		try {
			cooperationLogin = (CooperationLogin) getJdbcTemplate().queryForObject(sql, new Object[] { open_id, opend_key, type }, new CooperationLoginMapper());
		} catch (Exception e) {
			logger.info("查询结果为空！");
		}
		return cooperationLogin;
	}

	public CooperationLogin getCooperationLoginByUserId(long user_id, Byte type) {
		String sql = "select * from dw_cooperation_login where user_id = ? and type = ?";

		CooperationLogin cooperationLogin = null;
		try {
			cooperationLogin = (CooperationLogin) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(user_id), type }, new CooperationLoginMapper());
		} catch (Exception e) {
			logger.info("查询结果为空！");
		}
		return cooperationLogin;
	}

	public void updateCooperationUserId(long user_id, String open_id,
			String open_key, Byte type) {
		String sql = "update dw_cooperation_login set user_id = ? where open_id = ? and open_key = ? and type = ?";

		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(user_id), open_id, open_key, type });
	}

	public void updateCooperationUserIdById(long user_id, long id) {
		String sql = "update dw_cooperation_login set user_id = ? where id = ? ";

		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(user_id), Long.valueOf(id) });
	}
}