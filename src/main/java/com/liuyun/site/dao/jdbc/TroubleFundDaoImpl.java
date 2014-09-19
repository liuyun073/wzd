package com.liuyun.site.dao.jdbc;

import com.liuyun.site.common.enums.EnumTroubleFund;
import com.liuyun.site.dao.TroubleFundDao;
import com.liuyun.site.domain.TroubleAwardRecord;
import com.liuyun.site.domain.TroubleDonateRecord;
import com.liuyun.site.domain.TroubleFundDonateRecord;
import com.liuyun.site.model.TroubleAwardModel;
import com.liuyun.site.model.TroubleDonateModel;
import com.liuyun.site.model.TroubleFundModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class TroubleFundDaoImpl extends BaseDaoImpl implements TroubleFundDao {
	private static Logger logger = Logger.getLogger(TroubleFundDaoImpl.class);

	public List<TroubleFundModel> getTroubleFund(TroubleFundDonateRecord t, double trouble_apr,
			int start, int end) {
		String sql = "select a.*,b.username,b.realname,(a.money*"
				+ trouble_apr
				+ ") as into_funds from dw_donate_record a left join dw_user b on a.user_id=b.user_id where a.giving_way=? ";
		sql = sql + " order by a.id desc";
		String limitSql = " limit ?,?";
		sql = sql + limitSql;
		List<TroubleFundModel> list = new ArrayList<TroubleFundModel>();
		list = getJdbcTemplate().query(
				sql,
				new Object[] { Long.valueOf(t.getGiving_way()),
						Integer.valueOf(start), Integer.valueOf(end) },
				new RowMapper<TroubleFundModel>() {
					public TroubleFundModel mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						TroubleFundModel i = new TroubleFundModel();
						i.setId(rs.getLong("id"));
						i.setUser_id(rs.getLong("user_id"));
						i.setAddtime(rs.getString("addtime"));
						i.setDisplay_way(rs.getLong("display_way"));
						i.setGiving_way(rs.getLong("giving_way"));
						i.setMoney(rs.getDouble("money"));
						i.setRealname(rs.getString("realname"));
						i.setUsername(rs.getString("username"));
						i.setRemark(rs.getString("remark"));
						i.setAward_money(rs.getDouble("award_money"));
						i.setInto_funds(rs.getDouble("into_funds"));
						return i;
					}
				});
		return list;
	}

	public TroubleFundDonateRecord addTroubleFund(TroubleFundDonateRecord t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_donate_record(user_id,money,giving_way,display_way,remark,addtime,award_money) values(?,?,?,?,?,?,?)";
		final TroubleFundDonateRecord a = t;
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setLong(1, a.getUser_id());
				ps.setDouble(2, a.getMoney());
				ps.setLong(3, a.getGiving_way());
				ps.setLong(4, a.getDisplay_way());
				ps.setString(5, a.getRemark());
				ps.setString(6, a.getAddtime());
				ps.setDouble(7, a.getAward_money());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		t.setId(key);
		return t;
	}

	public TroubleAwardRecord addTroubleAward(TroubleAwardRecord t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_award_payments(user_id,money,status,addtime) values(?,?,?,?)";
		final TroubleAwardRecord a = t;
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setLong(1, a.getUser_id());
				ps.setDouble(2, a.getMoney());
				ps.setLong(3, a.getStatus());
				ps.setString(4, a.getAddtime());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		t.setId(key);
		return t;
	}

	public TroubleDonateRecord addTroubleDonate(TroubleDonateRecord t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_donate_payments(user_id,money,borrow_time,borrow_use,repayment_time,borrow_content,remark,status,addtime) values(?,?,?,?,?,?,?,?,?)";
		final TroubleDonateRecord a = t;
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setLong(1, a.getUser_id());
				ps.setDouble(2, a.getMoney());
				ps.setString(3, a.getBorrow_time());
				ps.setString(4, a.getBorrow_use());
				ps.setString(5, a.getRepayment_time());
				ps.setString(6, a.getBorrow_content());
				ps.setString(7, a.getRemark());
				ps.setLong(8, a.getStatus());
				ps.setString(9, a.getAddtime());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		t.setId(key);
		return t;
	}

	public List<TroubleAwardModel> getTroubleAwardList(long status, int start, int end) {
		String sql = "select a.*,b.username,b.realname from dw_award_payments a left join dw_user b on a.user_id=b.user_id where a.status=? ";
		sql = sql + " order by a.id desc";
		String limitSql = " limit ?,?";
		sql = sql + limitSql;
		List<TroubleAwardModel> list = new ArrayList<TroubleAwardModel>();
		list = getJdbcTemplate().query(
				sql,
				new Object[] { Long.valueOf(status), Integer.valueOf(start),
						Integer.valueOf(end) }, new RowMapper<TroubleAwardModel>() {
					public TroubleAwardModel mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						TroubleAwardModel i = new TroubleAwardModel();
						i.setId(rs.getLong("id"));
						i.setUser_id(rs.getLong("user_id"));
						i.setAddtime(rs.getString("addtime"));
						i.setStatus(rs.getLong("status"));
						i.setMoney(rs.getDouble("money"));
						i.setRealname(rs.getString("realname"));
						i.setUsername(rs.getString("username"));
						return i;
					}
				});
		return list;
	}

	public List<TroubleDonateModel> getTroubleDonateList(long status, int start, int end) {
		String sql = "select a.*,b.username,b.realname from dw_donate_payments a left join dw_user b on a.user_id=b.user_id where 1=1 ";
		String ordersql = " order by a.id desc";

		List<TroubleDonateModel> list = new ArrayList<TroubleDonateModel>();
		if ((status != EnumTroubleFund.FIRST.getValue())
				&& (status != EnumTroubleFund.ZERO.getValue())) {
			sql = sql + ordersql;
			String limitSql = " limit ?,?";
			sql = sql + limitSql;
			list = getJdbcTemplate()
					.query(
							sql,
							new Object[] { Integer.valueOf(start),
									Integer.valueOf(end) }, new RowMapper<TroubleDonateModel>() {
								public TroubleDonateModel mapRow(ResultSet rs,
										int rowNum) throws SQLException {
									TroubleDonateModel i = new TroubleDonateModel();
									i.setId(rs.getLong("id"));
									i.setAddtime(rs.getString("addtime"));
									i.setStatus(rs.getLong("status"));
									i.setMoney(rs.getDouble("money"));
									i.setRealname(rs.getString("realname"));
									i.setUsername(rs.getString("username"));
									i.setUser_id(rs.getLong("user_id"));
									i.setBorrow_content(rs
											.getString("borrow_content"));
									i.setBorrow_time(rs
											.getString("borrow_time"));
									i.setBorrow_use(rs.getString("borrow_use"));
									i.setRepayment_time(rs
											.getString("repayment_time"));
									i.setRemark(rs.getString("remark"));
									return i;
								}
							});
			return list;
		}
		sql = sql + " and a.status=? ";
		sql = sql + ordersql;
		String limitSql = " limit ?,?";
		sql = sql + limitSql;
		list = getJdbcTemplate().query(
				sql,
				new Object[] { Long.valueOf(status), Integer.valueOf(start),
						Integer.valueOf(end) }, new RowMapper<TroubleDonateModel>() {
					public TroubleDonateModel mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						TroubleDonateModel i = new TroubleDonateModel();
						i.setId(rs.getLong("id"));
						i.setAddtime(rs.getString("addtime"));
						i.setStatus(rs.getLong("status"));
						i.setMoney(rs.getDouble("money"));
						i.setRealname(rs.getString("realname"));
						i.setUsername(rs.getString("username"));
						i.setUser_id(rs.getLong("user_id"));
						i.setBorrow_content(rs.getString("borrow_content"));
						i.setBorrow_time(rs.getString("borrow_time"));
						i.setBorrow_use(rs.getString("borrow_use"));
						i.setRepayment_time(rs.getString("repayment_time"));
						i.setRemark(rs.getString("remark"));
						return i;
					}
				});
		return list;
	}

	public int getTroubleDonateCount(long status) {
		String sql = "select count(1) from dw_donate_payments a left join dw_user b on a.user_id=b.user_id where 1=1 ";
		int count = 0;
		if ((status != EnumTroubleFund.FIRST.getValue())
				&& (status != EnumTroubleFund.ZERO.getValue())) {
			logger.debug("SQL:" + sql);
			count = count(sql, new Object[0]);
		} else {
			sql = sql + " and a.status=? ";
			logger.debug("SQL:" + sql);
			count = count(sql, new Object[] { Long.valueOf(status) });
		}
		return count;
	}

	public double getTroubleAwardSum(long status) {
		String sql = "select sum(money) as num from dw_award_payments where status=? ";

		logger.info("SQL:" + sql);
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(status) });
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public double getTroubleDonateSum(long status) {
		String sql = "select sum(money) as num from dw_donate_payments where status=? ";
		logger.info("SQL:" + sql);
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(status) });
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public int getTroubleAwardCount(long status) {
		String sql = "select count(1) from dw_award_payments a left join dw_user b on a.user_id=b.user_id where a.status=? ";
		logger.debug("SQL:" + sql);
		int count = 0;
		count = count(sql, new Object[] { Long.valueOf(status) });
		return count;
	}

	public int getTroubleFundCount(TroubleFundDonateRecord t) {
		String sql = "select count(1) from dw_donate_record a left join dw_user b on a.user_id=b.user_id where a.giving_way=? ";
		logger.debug("SQL:" + sql);
		int count = 0;
		count = count(sql, new Object[] { Long.valueOf(t.getGiving_way()) });
		return count;
	}

	public TroubleDonateRecord getTroubleDonateById(int id) {
		TroubleDonateRecord t = new TroubleDonateRecord();
		String sql = "select a.*,b.username,b.realname from dw_donate_payments a left join dw_user b on a.user_id=b.user_id where a.id=?";
		t = (TroubleDonateRecord) getJdbcTemplate().queryForObject(sql,
				new Object[] { Integer.valueOf(id) }, new RowMapper<TroubleDonateModel>() {
					public TroubleDonateModel mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						TroubleDonateModel i = new TroubleDonateModel();
						i.setId(rs.getLong("id"));
						i.setAddtime(rs.getString("addtime"));
						i.setStatus(rs.getLong("status"));
						i.setMoney(rs.getDouble("money"));
						i.setRealname(rs.getString("realname"));
						i.setUsername(rs.getString("username"));
						i.setUser_id(rs.getLong("user_id"));
						i.setBorrow_content(rs.getString("borrow_content"));
						i.setBorrow_time(rs.getString("borrow_time"));
						i.setBorrow_use(rs.getString("borrow_use"));
						i.setRepayment_time(rs.getString("repayment_time"));
						i.setRemark(rs.getString("remark"));
						return i;
					}
				});
		return t;
	}

	public TroubleDonateRecord updateTroubleDonate(TroubleDonateRecord t) {
		//KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "update dw_donate_payments set user_id=?,money=?,borrow_time=?,borrow_use=?,repayment_time=?,borrow_content=?,remark=?,status=?,addtime=? where id=?";
		//TroubleDonateRecord a = t;
		getJdbcTemplate().update(sql,
						new Object[] { Long.valueOf(t.getUser_id()),
								Double.valueOf(t.getMoney()),
								t.getBorrow_time(), t.getBorrow_use(),
								t.getRepayment_time(), t.getBorrow_content(),
								t.getRemark(), Long.valueOf(t.getStatus()),
								t.getAddtime(), Long.valueOf(t.getId()) });
		return t;
	}
}