package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractTenderMapper;
import com.liuyun.site.model.BorrowTender;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class BorrowUnionTenderMapper extends AbstractTenderMapper implements
		RowMapper<BorrowTender> {
	private static final long serialVersionUID = 6792669476626617378L;

	public BorrowTender mapRow(ResultSet rs, int rowNum) throws SQLException {
		BorrowTender t = new BorrowTender();
		setProperty(rs, t);
		t.setUsername(rs.getString("username"));
		return t;
	}
}