package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractCollectionMapper;
import com.liuyun.site.domain.Collection;
import com.liuyun.site.model.DetailCollection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CollectionUnionMapper extends AbstractCollectionMapper implements
		RowMapper<Collection> {
	public Collection mapRow(ResultSet rs, int num) throws SQLException {
		DetailCollection c = new DetailCollection();
		setProperty(rs, c);
		c.setBorrow_name(rs.getString("borrow_name"));
		c.setPhone(rs.getString("phone"));
		c.setTime_limit(rs.getString("time_limit"));
		c.setUsername(rs.getString("username"));
		c.setBorrow_id(rs.getLong("borrow_id"));
		return c;
	}
}