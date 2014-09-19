package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.model.borrow.ReservationModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ReservationMapper implements RowMapper<ReservationModel> {
	
	public ReservationModel mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ReservationModel s = new ReservationModel();
		s.setId(rs.getLong("id"));
		s.setReservation_user(rs.getLong("reservation_user"));
		s.setBorrow_apr(rs.getString("borrow_apr"));
		s.setAddtime(rs.getString("addtime"));
		s.setAddip(rs.getString("addip"));
		s.setTender_account(rs.getDouble("tender_account"));
		s.setUsername(rs.getString("username"));
		return s;
	}
}