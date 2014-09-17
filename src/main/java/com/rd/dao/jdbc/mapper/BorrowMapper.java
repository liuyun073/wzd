package com.rd.dao.jdbc.mapper;

import com.rd.context.Global;
import com.rd.dao.jdbc.mapper.base.AbstractBorrowMapper;
import com.rd.model.borrow.BorrowModel;
import com.rd.util.StringUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class BorrowMapper extends AbstractBorrowMapper implements
		RowMapper<BorrowModel> {
	
	public BorrowModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		BorrowModel b = new BorrowModel();
		setProperty(rs, b);
		String webid = Global.getValue("webid");
		if (StringUtils.isNull(webid).equals("mdw")) {
			b.setBorrow_time(rs.getString("borrow_time"));
			b.setBorrow_account(rs.getString("borrow_account"));
			b.setBorrow_time_limit(rs.getString("borrow_time_limit"));
			b.setCollection_limit(rs.getString("collection_limit"));
		} else if (StringUtils.isNull(webid).equals("jsy")) {
			b.setIs_pledge(rs.getInt("is_pledge"));
		}
		b.setLate_award(rs.getDouble("late_award"));
		return b;
	}
}