package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractTenderMapper;
import com.rd.model.BorrowTender;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class BorrowTenderMapper extends AbstractTenderMapper implements
		RowMapper<BorrowTender> {
	private static final long serialVersionUID = 6792669476626617378L;

	public BorrowTender mapRow(ResultSet rs, int rowNum) throws SQLException {
		BorrowTender t = new BorrowTender();
		setProperty(rs, t);
		try {
			t.setUsername(rs.getString("username"));
			t.setRepay_time(rs.getString("repay_time"));
			t.setRepay_account(rs.getString("repay_account"));
		} catch (Exception localException) {
		}
		return t;
	}
}