package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractRewardStatisticsMapper;
import com.rd.domain.RewardStatistics;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RewardStatisticsMapper extends AbstractRewardStatisticsMapper
		implements RowMapper<RewardStatistics> {
	public RewardStatistics mapRow(ResultSet rs, int num) throws SQLException {
		RewardStatistics reward = new RewardStatistics();
		setProperty(rs, reward);
		reward.setId(rs.getLong("id"));
		reward.setType(Byte.valueOf(rs.getByte("type")));
		reward.setReward_user_id(rs.getLong("reward_user_id"));
		reward.setPassive_user_id(rs.getLong("passive_user_id"));
		reward.setReceive_time(rs.getString("receive_time"));
		reward.setReceive_yestime(rs.getString("receive_yestime"));
		reward.setReceive_account(rs.getDouble("receive_account"));
		reward.setReceive_yesaccount(rs.getDouble("receive_yesaccount"));
		reward.setReceive_status(Byte.valueOf(rs.getByte("receive_status")));
		reward.setAddtime(rs.getString("addtime"));
		reward.setEndtime(rs.getString("endtime"));
		reward.setRule_id(rs.getLong("rule_id"));
		reward.setBack_type(Byte.valueOf(rs.getByte("back_type")));
		reward.setType_fk_id(rs.getLong("type_fk_id"));
		return reward;
	}
}