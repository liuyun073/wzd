package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractTenderMapper;
import com.liuyun.site.model.BorrowNTender;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class BorrowNTenderMapper extends AbstractTenderMapper implements
		RowMapper<BorrowNTender> {
	private static final long serialVersionUID = 6792669476626617378L;

	public BorrowNTender mapRow(ResultSet rs, int rowNum) throws SQLException {
		BorrowNTender t = new BorrowNTender();
		setProperty(rs, t);
		t.setUsername(rs.getString("username"));
		t.setBorrowname(rs.getString("borrowname"));

		return t;
	}
}