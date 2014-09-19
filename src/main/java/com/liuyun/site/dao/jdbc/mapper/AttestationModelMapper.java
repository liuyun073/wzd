package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractAttestationMapper;
import com.liuyun.site.model.AttestationModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AttestationModelMapper extends AbstractAttestationMapper implements
		RowMapper<AttestationModel> {
	public AttestationModel mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		AttestationModel attestationModel = new AttestationModel();
		setProperty(rs, attestationModel);
		attestationModel.setType_name(rs.getString("type_name"));
		attestationModel.setUsername(rs.getString("username"));
		attestationModel.setRealname(rs.getString("realname"));

		return attestationModel;
	}
}