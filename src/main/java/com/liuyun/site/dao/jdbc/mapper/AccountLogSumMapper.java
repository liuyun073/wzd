package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractAccountLogMapper;
import com.liuyun.site.model.account.AccountLogSumModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AccountLogSumMapper extends AbstractAccountLogMapper implements
		RowMapper<AccountLogSumModel> {
	public AccountLogSumModel mapRow(ResultSet rs, int num) throws SQLException {
		AccountLogSumModel log = new AccountLogSumModel();

		log.setType(rs.getString("type"));
		log.setTypename(rs.getString("typename"));
		log.setSum(rs.getDouble("sum"));
		return log;
	}
}