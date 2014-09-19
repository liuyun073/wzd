package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.model.RewardStatisticsModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RewardStatisticsModelMapper implements
		RowMapper<RewardStatisticsModel> {
	public RewardStatisticsModel mapRow(ResultSet rs, int num)
			throws SQLException {
		RewardStatisticsModel model = new RewardStatisticsModel();
		model.setId(rs.getLong("id"));
		model.setType(Byte.valueOf(rs.getByte("type")));
		model.setReward_user_id(rs.getLong("reward_user_id"));
		model.setPassive_user_id(rs.getLong("passive_user_id"));
		model.setReceive_time(rs.getString("receive_time"));
		model.setReceive_yestime(rs.getString("receive_yestime"));
		model.setReceive_account(rs.getDouble("receive_account"));
		model.setReceive_yesaccount(rs.getDouble("receive_yesaccount"));
		model.setReceive_status(Byte.valueOf(rs.getByte("receive_status")));
		model.setAddtime(rs.getString("addtime"));
		model.setEndtime(rs.getString("endtime"));
		model.setRule_id(rs.getLong("rule_id"));
		model.setBack_type(Byte.valueOf(rs.getByte("back_type")));
		model.setUsername(rs.getString("username"));
		model.setPassive_username(rs.getString("passive_username"));
		model.setType_pk_id(rs.getLong("type_fk_id"));
		return model;
	}
}