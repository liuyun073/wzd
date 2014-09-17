package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbsractCreditCardMapper;
import com.rd.domain.CreditCard;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CreditCardMapper extends AbsractCreditCardMapper implements
		RowMapper<CreditCard> {
	public CreditCard mapRow(ResultSet rs, int rowNum) throws SQLException {
		CreditCard b = new CreditCard();
		setProperty(rs, b);
		return b;
	}
}