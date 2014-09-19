package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.ScrollPic;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractScrollPicMapper {
	protected void setProperty(ResultSet rs, ScrollPic sp) throws SQLException {
		sp.setId(rs.getLong("id"));
		sp.setSite_id(rs.getLong("site_id"));
		sp.setStatus(rs.getInt("status"));
		sp.setSort(rs.getInt("sort"));
		sp.setFlag(rs.getString("flag"));
		sp.setType_id(rs.getLong("type_id"));
		sp.setUrl(rs.getString("url"));
		sp.setName(rs.getString("name"));
		sp.setPic(rs.getString("pic"));
		sp.setSummary(rs.getString("summary"));
		sp.setHits(rs.getInt("hits"));
		sp.setAddtime(rs.getString("addtime"));
		sp.setAddip(rs.getString("addip"));
	}
}