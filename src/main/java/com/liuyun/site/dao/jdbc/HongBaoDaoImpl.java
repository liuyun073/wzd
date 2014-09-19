package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.HongBaoDao;
import com.liuyun.site.domain.HongBao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class HongBaoDaoImpl extends BaseDaoImpl implements HongBaoDao {
	private static Logger logger = Logger.getLogger(BorrowDaoImpl.class);

	public HongBao addHongbao(HongBao hongbao) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_hongbao(user_id,hongbao_money,type,remark,addtime,addip) values(?,?,?,?,?,?)";
		final HongBao a = hongbao;
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setLong(1, a.getUser_id());
				ps.setDouble(2, a.getHongbao_money());
				ps.setString(3, a.getType());
				ps.setString(4, a.getRemark());
				ps.setString(5, a.getAddtime());
				ps.setString(6, a.getAddip());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		hongbao.setId(key);
		return hongbao;
	}
}