package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.domain.BorrowAuto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class BorrowAutoMapper implements RowMapper<BorrowAuto> {
	public BorrowAuto mapRow(ResultSet rs, int num) throws SQLException {
		BorrowAuto ba = new BorrowAuto();
		ba.setId(rs.getLong("id"));
		ba.setStatus(rs.getInt("status"));
		ba.setTender_type(rs.getInt("tender_type"));
		ba.setTender_account(rs.getInt("tender_account"));
		ba.setTender_scale(rs.getInt("tender_scale"));
		ba.setVideo_status(rs.getInt("video_status"));
		ba.setScene_status(rs.getInt("scene_status"));
		ba.setMy_friend(rs.getInt("my_friend"));
		ba.setNot_black(rs.getInt("not_black"));
		ba.setLate_status(rs.getInt("late_status"));
		ba.setDianfu_status(rs.getInt("dianfu_status"));
		ba.setBlack_status(rs.getInt("black_status"));
		ba.setLate_times(rs.getInt("late_times"));
		ba.setDianfu_times(rs.getInt("dianfu_times"));
		ba.setBlack_user(rs.getInt("black_user"));
		ba.setBlack_times(rs.getInt("black_times"));
		ba.setNot_late_black(rs.getInt("not_late_black"));
		ba.setBorrow_credit_status(rs.getInt("borrow_credit_status"));
		ba.setBorrow_credit_first(rs.getInt("borrow_credit_first"));
		ba.setBorrow_credit_last(rs.getInt("borrow_credit_last"));
		ba.setBorrow_style_status(rs.getInt("borrow_style_status"));
		ba.setBorrow_style(rs.getInt("borrow_style"));
		ba.setTimelimit_status(rs.getInt("timelimit_status"));
		ba.setTimelimit_month_first(rs.getInt("timelimit_month_first"));
		ba.setTimelimit_month_last(rs.getInt("timelimit_month_last"));
		ba.setTimelimit_day_first(rs.getInt("timelimit_day_first"));
		ba.setTimelimit_day_last(rs.getInt("timelimit_day_last"));
		ba.setApr_status(rs.getInt("apr_status"));
		ba.setApr_first(rs.getInt("apr_first"));
		ba.setApr_last(rs.getInt("apr_last"));
		ba.setAward_status(rs.getInt("award_status"));
		ba.setAward_first(rs.getDouble("award_first"));
		ba.setAward_last(rs.getDouble("award_last"));
		ba.setVouch_status(rs.getInt("vouch_status"));
		ba.setTuijian_status(rs.getInt("tuijian_status"));
		ba.setUser_id(rs.getInt("user_id"));
		ba.setAddtime(rs.getInt("addtime"));
		ba.setFast_status(rs.getInt("fast_status"));
		ba.setXin_status(rs.getInt("xin_status"));
		ba.setJin_status(rs.getInt("jin_status"));
		return ba;
	}
}