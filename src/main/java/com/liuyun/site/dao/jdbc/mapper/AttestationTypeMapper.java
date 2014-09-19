package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractAttestationTypeMapper;
import com.liuyun.site.domain.AttestationType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AttestationTypeMapper extends AbstractAttestationTypeMapper
		implements RowMapper<AttestationType> {
	public AttestationType mapRow(ResultSet rs, int rowNum) throws SQLException {
		AttestationType attestationType = new AttestationType();
		setProperty(rs, attestationType);

		return attestationType;
	}
}