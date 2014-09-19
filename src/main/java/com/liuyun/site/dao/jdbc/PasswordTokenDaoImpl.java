package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.PasswordTokenDao;
import com.liuyun.site.dao.jdbc.mapper.PasswordTokenMapper;
import com.liuyun.site.domain.PasswordToken;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class PasswordTokenDaoImpl extends BaseDaoImpl implements
		PasswordTokenDao {
	private static Logger logger = Logger.getLogger(PasswordTokenDaoImpl.class);

	public List<PasswordToken> getAllList() {
		String sql = "select * from dw_password_token order by user_id desc";
		//List<PasswordToken> list = new ArrayList<PasswordToken>();
		return getJdbcTemplate().query(sql, new PasswordTokenMapper());
	}

	public void addPasswordToken(List<PasswordToken> tokenList, long userId) {
		int listSize = tokenList.size();
		PasswordToken passwordToken = null;
		if (listSize > 0)
			for (int i = 0; i < listSize; ++i) {
				passwordToken = (PasswordToken) tokenList.get(i);
				int result = 0;
				String sql = "insert into dw_password_token(user_id,question,answer) values (?,?,?)";
				logger.debug("Sql:" + sql);
				result = getJdbcTemplate().update(
						sql,
						new Object[] { Long.valueOf(userId),
								passwordToken.getQuestion(),
								passwordToken.getAnswer() });
				logger.debug("result:" + result);
			}
	}

	public void deletePasswordTokenById(long id) {
		String sql = "delete from dw_password_token where id=?";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(id) });
	}

	public void updatePasswordTokenByList(List<PasswordToken> list) {
		String sql = "update dw_password_token set user_id=?,question=?,answer=? where id=?";
		for (PasswordToken pt : list) {
			if (pt == null)
				continue;
			getJdbcTemplate().update(
					sql,
					new Object[] { Long.valueOf(pt.getUser_id()),
							pt.getQuestion(), pt.getAnswer(),
							Long.valueOf(pt.getId()) });
		}
	}

	public List<PasswordToken> getPasswordTokenByUserId(long userId) {
		String sql = "select * from dw_password_token where user_id = ?";
		logger.info("SQL:" + sql);
		logger.info("SQL:" + userId);
		List<PasswordToken> list = new ArrayList<PasswordToken>();
		list = getJdbcTemplate().query(sql, new PasswordTokenMapper(),
				new Object[] { Long.valueOf(userId) });
		return list;
	}

	public List<PasswordToken> getPasswordTokenByUsername(String username) {
		String sql = "select * from dw_password_token as p left join dw_user as u on p.user_id = u.user_id where username = ?;";
		List<PasswordToken> list = new ArrayList<PasswordToken>();
		list = getJdbcTemplate().query(sql, new PasswordTokenMapper(),
				new Object[] { username });
		return list;
	}

	public String getAnswerByQuestion(String question, long userId) {
		String sql = "select answer from dw_password_token where user_id = ?";
		logger.info("SQL:" + sql);
		logger.info("question:" + question + "userId:" + userId);
		return (String) getJdbcTemplate().queryForObject(sql,
				new Object[] { question, Long.valueOf(userId) }, String.class);
	}
}