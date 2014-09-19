package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.BorrowConfigDao;
import com.liuyun.site.domain.BorrowConfig;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

public class BorrowConfigDaoImpl extends BaseDaoImpl implements BorrowConfigDao {
	RowMapper<BorrowConfig> mapper = new RowMapper<BorrowConfig>() {
		public BorrowConfig mapRow(ResultSet rs, int num) throws SQLException {
			BorrowConfig c = new BorrowConfig();
			c.setId(rs.getLong("id"));
			c.setName(rs.getString("name"));
			c.setMost_account(rs.getDouble("most_account"));
			c.setLowest_account(rs.getDouble("lowest_account"));
			c.setMost_apr(rs.getDouble("most_apr"));
			c.setLowest_apr(rs.getDouble("lowest_apr"));
			c.setMost_award_apr(rs.getDouble("most_award_apr"));
			c.setLowest_award_apr(rs.getDouble("lowest_award_apr"));
			c.setMost_award_funds(rs.getDouble("most_award_funds"));
			c.setLowest_award_funds(rs.getDouble("lowest_award_funds"));
			c.setIs_trail(rs.getInt("is_trail"));
			c.setIs_review(rs.getInt("is_review"));
			c.setRemark(rs.getString("remark"));
			c.setIdentify(rs.getString("identify"));
			c.setDaymanagefee(rs.getDouble("daymanagefee"));
			c.setManagefee(rs.getDouble("managefee"));
			return c;
		}
	};

	public List<BorrowConfig> getList() {
		String sql = "select * from dw_borrow_config";
		List<BorrowConfig> list = new ArrayList<BorrowConfig>();
		list = getJdbcTemplate().query(sql, this.mapper);
		return list;
	}
}