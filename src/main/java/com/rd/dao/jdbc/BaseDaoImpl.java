package com.rd.dao.jdbc;

import com.rd.model.borrow.BorrowModel;
import javax.sql.DataSource;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class BaseDaoImpl {
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public String connectSql(BorrowModel model) {
		String typeSql = model.getTypeSql();
		String statusSql = model.getStatusSql();
		String searchSql = model.getSearchParamSql();
		String orderSql = model.getOrderSql();
		String LimiteSql = model.getLimitSql();
		return typeSql + statusSql + searchSql + orderSql + LimiteSql;
	}

	public String connectCountSql(BorrowModel model) {
		String typeSql = model.getTypeSql();
		String statusSql = model.getStatusSql();
		String searchSql = model.getSearchParamSql();
		return typeSql + statusSql + searchSql;
	}

	protected int count(String sql, Object[] args) {
		int count = 0;
		count = getJdbcTemplate().queryForInt(sql, args);
		return count;
	}

	protected int count(String sql) {
		int count = 0;
		count = getJdbcTemplate().queryForInt(sql);
		return count;
	}

	protected double sum(String sql, Object[] args) {
		double sum = 0.0D;
		SqlRowSet rs = null;
		try {
			rs = getJdbcTemplate().queryForRowSet(sql, args);
			if (rs.next())
				sum = rs.getDouble("num");
		} catch (InvalidResultSetAccessException e) {
			e.printStackTrace();
		}
		return sum;
	}

	protected double sum(String sql, String name, Object[] args) {
		double sum = 0.0D;
		SqlRowSet rs = null;
		try {
			rs = getJdbcTemplate().queryForRowSet(sql, args);
			if (rs.next())
				sum = rs.getDouble(name);
		} catch (InvalidResultSetAccessException e) {
			e.printStackTrace();
		}
		return sum;
	}
}