package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.BonusDao;
import com.liuyun.site.domain.BonusApr;
import com.liuyun.site.domain.Borrow;
import com.liuyun.site.util.NumberUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

public class BonusDaoImpl extends BaseDaoImpl implements BonusDao {
	RowMapper<BonusApr> mapper = new RowMapper<BonusApr>() {
		public BonusApr mapRow(ResultSet rs, int num) throws SQLException {
			BonusApr ba = new BonusApr();
			ba.setId(rs.getLong("id"));
			ba.setBorrow_id(rs.getLong("borrow_id"));
			ba.setOrder(rs.getInt("order"));
			ba.setApr(rs.getDouble("apr"));
			return ba;
		}
	};

	public void addBonusApr(Borrow borrow) {
		String sql = "insert into dw_bonus_apr(borrow_id,`order`,apr) values(?,?,?)";
		int order = NumberUtils.getInt(borrow.getTime_limit());
		List<Object[]> argsList = new ArrayList<Object[]>();
		for (int i = 0; i < order; ++i) {
			Object[] arg = { Long.valueOf(borrow.getId()), Integer.valueOf(i), Double.valueOf(0.0D) };
			argsList.add(arg);
		}
		getJdbcTemplate().batchUpdate(sql, argsList);
	}

	public void modifyBonusAprById(long id, double apr) {
		String sql = "update dw_bonus_apr set apr=? where id=?";
		getJdbcTemplate().update(sql, new Object[] { Double.valueOf(apr), Long.valueOf(id) });
	}

	public List<BonusApr> getBonusAprList(long borrow_id) {
		String sql = "select * from dw_bonus_apr where borrow_id=?";
		List<BonusApr> list = new ArrayList<BonusApr>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(borrow_id) }, this.mapper);
		return list;
	}

	public BonusApr getBonus(long id) {
		String sql = "select * from dw_bonus_apr where id=?";
		BonusApr ba = new BonusApr();
		ba = (BonusApr) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, this.mapper);
		return ba;
	}
}