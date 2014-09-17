package com.rd.dao.jdbc.mapper.base;

import com.rd.domain.UserCache;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractUserCacheMapper {
	protected void setProperty(ResultSet rs, UserCache u) throws SQLException {
		u.setUser_id(rs.getLong("user_id"));
		u.setKefu_userid(rs.getLong("kefu_userid"));
		u.setKefu_username(rs.getString("kefu_username"));
		u.setKefu_addtime(rs.getString("kefu_addtime"));
		u.setVip_status(rs.getInt("vip_status"));
		u.setVip_remark(rs.getString("vip_remark"));
		u.setVip_money(rs.getString("vip_money"));
		u.setVip_verify_remark(rs.getString("vip_verify_remark"));
		u.setVip_verify_time(rs.getString("vip_verify_time"));
		u.setBbs_topics_num(rs.getInt("bbs_topics_num"));
		u.setBbs_posts_num(rs.getInt("bbs_posts_num"));
		u.setCredit(rs.getInt("credit"));
		u.setAccount(rs.getInt("account"));
		u.setAccount_use(rs.getInt("account_use"));
		u.setAccount_nouse(rs.getInt("account_nouse"));
		u.setAccount_waitin(rs.getInt("account_waitin"));
		u.setAccount_waitintrest(rs.getInt("account_waitintrest"));
		u.setAccount_intrest(rs.getInt("account_intrest"));
		u.setAccount_award(rs.getInt("account_award"));
		u.setAccount_payment(rs.getInt("account_payment"));
		u.setAccount_expired(rs.getInt("account_expired"));
		u.setAccount_waitvip(rs.getInt("account_waitvip"));
		u.setBorrow_amount(rs.getInt("borrow_amount"));
		u.setVouch_amount(rs.getInt("vouch_amount"));
		u.setBorrow_loan(rs.getInt("borrow_loan"));
		u.setBorrow_success(rs.getInt("borrow_success"));
		u.setBorrow_wait(rs.getInt("borrow_wait"));
		u.setBorrow_paymeng(rs.getInt("borrow_paymeng"));
		u.setFriends_apply(rs.getInt("friends_apply"));
	}
}