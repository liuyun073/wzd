package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.context.Global;
import com.liuyun.site.dao.jdbc.mapper.base.AbstractBorrowMapper;
import com.liuyun.site.model.borrow.BorrowModel;
import com.liuyun.site.util.StringUtils;
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