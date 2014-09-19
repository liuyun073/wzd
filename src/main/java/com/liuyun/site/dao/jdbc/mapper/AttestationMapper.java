package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.domain.Attestation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AttestationMapper implements RowMapper<Attestation> {
	public Attestation mapRow(ResultSet rs, int rowNum) throws SQLException {
		Attestation a = new Attestation();
		a.setId(rs.getLong("id"));
		a.setUser_id(rs.getLong("user_id"));
		a.setType_id(rs.getInt("type_id"));
		a.setName(rs.getString("name"));
		a.setStatus(rs.getInt("status"));
		a.setLitpic(rs.getString("litpic"));
		a.setContent(rs.getString("content"));
		a.setJifen(rs.getInt("jifen_val"));
		a.setPic(rs.getString("pic"));
		a.setPic2(rs.getString("pic2"));
		a.setPic3(rs.getString("pic3"));
		a.setVerify_time(rs.getString("verify_time"));
		a.setVerify_user(rs.getLong("verify_user"));
		a.setVerify_remark(rs.getString("verify_remark"));
		a.setAddtime(rs.getString("addtime"));
		a.setAddip(rs.getString("addip"));
		a.setType_name(rs.getString("type_name"));
		return a;
	}
}