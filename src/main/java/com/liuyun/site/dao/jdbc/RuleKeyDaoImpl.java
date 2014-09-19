package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.RuleKeyDao;
import com.liuyun.site.dao.jdbc.mapper.RuleKeyMapper;
import com.liuyun.site.domain.RuleKey;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class RuleKeyDaoImpl extends BaseDaoImpl implements RuleKeyDao {
	private static Logger logger = Logger.getLogger(RuleKeyDaoImpl.class);

	public RuleKey getRuleKeyById(Long id) {
		String sql = "select * from dw_rule_key where id = ?";
		RuleKey ruleKey = null;
		try {
			ruleKey = (RuleKey) getJdbcTemplate().queryForObject(sql,
					new Object[] { id }, new RuleKeyMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return ruleKey;
	}
}