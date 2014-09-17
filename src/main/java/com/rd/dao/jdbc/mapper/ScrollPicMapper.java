package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractScrollPicMapper;
import com.rd.domain.ScrollPic;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ScrollPicMapper extends AbstractScrollPicMapper implements
		RowMapper<ScrollPic> {
	public ScrollPic mapRow(ResultSet rs, int rowNum) throws SQLException {
		ScrollPic sp = new ScrollPic();
		setProperty(rs, sp);
		return sp;
	}
}