package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractRewardStatisticsMapper;
import com.liuyun.site.model.RewardStatisticsSumModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RewardStatisticsSumMapper extends AbstractRewardStatisticsMapper
		implements RowMapper<RewardStatisticsSumModel> {
	public RewardStatisticsSumModel mapRow(ResultSet rs, int num)
			throws SQLException {
		RewardStatisticsSumModel rss = new RewardStatisticsSumModel();

		rss.setUsername(rs.getString("username"));
		rss.setAccount(rs.getDouble("account"));
		return rss;
	}
}