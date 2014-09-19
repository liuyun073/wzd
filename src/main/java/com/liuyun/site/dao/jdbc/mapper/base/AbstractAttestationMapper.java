package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.Attestation;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractAttestationMapper {
	
	protected void setProperty(ResultSet rs, Attestation attestation)
			throws SQLException {
		attestation.setId(rs.getLong("id"));
		attestation.setUser_id(rs.getLong("user_id"));
		attestation.setType_id(rs.getInt("type_id"));
		attestation.setName(rs.getString("name"));
		attestation.setStatus(rs.getInt("status"));
		attestation.setLitpic(rs.getString("litpic"));
		attestation.setContent(rs.getString("content"));
		attestation.setJifen(rs.getInt("jifen"));
		attestation.setPic(rs.getString("pic"));
		attestation.setPic2(rs.getString("pic2"));
		attestation.setPic3(rs.getString("pic3"));
		attestation.setVerify_time(rs.getString("verify_time"));
		attestation.setVerify_user(rs.getLong("verify_user"));
		attestation.setVerify_remark(rs.getString("verify_remark"));
		attestation.setAddtime(rs.getString("addtime"));
		attestation.setAddip(rs.getString("addip"));
	}
}