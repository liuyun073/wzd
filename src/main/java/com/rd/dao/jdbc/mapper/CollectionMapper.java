package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractCollectionMapper;
import com.rd.domain.Collection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CollectionMapper extends AbstractCollectionMapper implements
		RowMapper<Collection> {
	public Collection mapRow(ResultSet rs, int num) throws SQLException {
		Collection c = new Collection();
		setProperty(rs, c);
		return c;
	}
}