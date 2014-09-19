package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.NoticeConfigDao;
import com.liuyun.site.domain.NoticeConfig;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class NoticeConfigDaoImpl extends BaseDaoImpl implements NoticeConfigDao {
	private static Logger logger = Logger.getLogger(NoticeConfigDaoImpl.class);

	RowMapper<NoticeConfig> mapper = new RowMapper<NoticeConfig>() {
		public NoticeConfig mapRow(ResultSet rs, int num) throws SQLException {
			NoticeConfig c = new NoticeConfig();
			c.setId(rs.getLong("id"));
			c.setType(rs.getString("type"));
			c.setEmail(rs.getLong("email"));
			c.setLetters(rs.getLong("letters"));
			c.setSms(rs.getLong("sms"));
			return c;
		}
	};

	public List<NoticeConfig> getList() {
		String sql = "select * from dw_notice_config";
		List<NoticeConfig> list = new ArrayList<NoticeConfig>();
		list = getJdbcTemplate().query(sql, this.mapper);
		return list;
	}

	public int getListCount() {
		int total = 0;
		String sql = "select count(1) from dw_notice_config";
		logger.debug("SQL:" + sql);
		try {
			total = getJdbcTemplate().queryForInt(sql, new Object[0]);
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return total;
	}

	public void add(NoticeConfig n) {
		String sql = "insert into dw_notice_config(type,sms,email,letters) values(?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { n.getType(), Long.valueOf(n.getSms()),
						Long.valueOf(n.getEmail()),
						Long.valueOf(n.getLetters()) });
	}
}