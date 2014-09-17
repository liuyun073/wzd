package com.rd.dao.jdbc.mapper.base;

import com.rd.domain.AttestationType;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractAttestationTypeMapper {
	
	protected void setProperty(ResultSet rs, AttestationType attestationType)
			throws SQLException {
		attestationType.setType_id(rs.getInt("type_id"));
		attestationType.setName(rs.getString("name"));
		attestationType.setStatus(rs.getInt("status"));
		attestationType.setOrder(rs.getString("order"));
		attestationType.setJifen(rs.getInt("jifen"));
		attestationType.setSummary(rs.getString("summary"));
		attestationType.setRemark(rs.getString("remark"));
		attestationType.setAddtime(rs.getString("addtime"));
		attestationType.setAddip(rs.getString("addip"));
	}
}