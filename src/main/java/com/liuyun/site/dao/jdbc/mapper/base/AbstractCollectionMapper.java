package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.Collection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCollectionMapper {
	
	protected void setProperty(ResultSet rs, Collection c) throws SQLException {
		c.setId(rs.getLong("Id"));
		c.setSite_id(rs.getLong("site_id"));
		c.setStatus(rs.getInt("Status"));
		c.setOrder(rs.getInt("Order"));
		c.setTender_id(rs.getLong("tender_id"));
		c.setRepay_time(rs.getString("repay_time"));
		c.setRepay_yestime(rs.getString("repay_yestime"));
		c.setRepay_account(rs.getString("repay_account"));
		c.setRepay_yesaccount(rs.getString("repay_yesaccount"));
		c.setInterest(rs.getString("interest"));
		c.setCapital(rs.getString("capital"));
		c.setBonus(rs.getString("bonus"));
		c.setLate_days(rs.getLong("late_days"));
		c.setLate_interest(rs.getString("late_interest"));
		c.setAddtime(rs.getString("addtime"));
		c.setAddip(rs.getString("addip"));
	}
}