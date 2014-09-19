package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.RewardStatistics;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractRewardStatisticsMapper {
	protected void setProperty(ResultSet rs, RewardStatistics rewardStatistics)
			throws SQLException {
		rewardStatistics.setId(rs.getLong("id"));
		rewardStatistics.setType(Byte.valueOf(rs.getByte("type")));
		rewardStatistics.setReward_user_id(rs.getLong("reward_user_id"));
		rewardStatistics.setPassive_user_id(rs.getLong("passive_user_id"));
		rewardStatistics.setReceive_time(rs.getString("receive_time"));
		rewardStatistics.setReceive_yestime(rs.getString("receive_yestime"));
		rewardStatistics.setReceive_account(rs.getDouble("receive_account"));
		rewardStatistics.setReceive_yesaccount(rs
				.getDouble("receive_yesaccount"));
		rewardStatistics.setReceive_status(Byte.valueOf(rs
				.getByte("receive_status")));
		rewardStatistics.setAddtime(rs.getString("addtime"));
		rewardStatistics.setEndtime(rs.getString("endtime"));
		rewardStatistics.setRule_id(rs.getLong("rule_id"));
		rewardStatistics.setBack_type(Byte.valueOf(rs.getByte("back_type")));
	}
}