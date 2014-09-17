package com.rd.dao.jdbc;

import com.rd.dao.RuleDao;
import com.rd.dao.jdbc.mapper.RuleMapper;
import com.rd.domain.Rule;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class RuleDaoImpl extends BaseDaoImpl implements RuleDao {
	private static Logger logger = Logger.getLogger(RuleDaoImpl.class);

	public Rule getRuleById(Long id) {
		String sql = "select * from dw_rule where id = ?";
		Rule rule = null;
		try {
			rule = (Rule) getJdbcTemplate().queryForObject(sql,
					new Object[] { id }, new RuleMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rule;
	}

	public Rule getRuleByNid(String nid) {
		String sql = "select * from dw_rule where nid = ?";
		Rule rule = null;
		try {
			rule = (Rule) getJdbcTemplate().queryForObject(sql,
					new Object[] { nid }, new RuleMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rule;
	}

	public List<Rule> getRuleList(int status) {
		String sql = "select * from dw_rule where status=?";
		List<Rule> list = new ArrayList<Rule>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Integer.valueOf(status) }, new RuleMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;
	}
}