package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractCollectionMapper;
import com.liuyun.site.domain.Collection;
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