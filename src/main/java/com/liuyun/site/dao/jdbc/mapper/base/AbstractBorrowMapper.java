package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.context.Global;
import com.liuyun.site.domain.Borrow;
import com.liuyun.site.util.StringUtils;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractBorrowMapper {
	
	protected void setProperty(ResultSet rs, Borrow b) throws SQLException {
		String webid = Global.getValue("webid");
		b.setId(rs.getLong("id"));
		b.setSite_id(rs.getLong("site_id"));
		b.setUser_id(rs.getLong("user_id"));
		b.setName(rs.getString("name"));
		b.setStatus(rs.getInt("status"));
		b.setHits(rs.getInt("hits"));
		b.setLitpic(rs.getString("litpic"));
		b.setFlag(rs.getString("flag"));
		b.setIs_vouch(rs.getInt("is_vouch"));
		b.setType(rs.getString("view_type"));
		b.setView_type(rs.getInt("vouch_award"));
		b.setVouch_award(rs.getString("vouch_award"));
		b.setVouch_user(rs.getString("vouch_user"));
		b.setVouch_account(rs.getString("vouch_account"));
		b.setVouch_times(rs.getInt("vouch_times"));
		b.setSource(rs.getString("source"));
		b.setPublish(rs.getString("publish"));
		b.setCustomer(rs.getString("customer"));
		b.setNumber_id(rs.getString("number_id"));
		b.setVerify_user(rs.getString("verify_user"));
		b.setVerify_time(rs.getString("verify_time"));
		b.setVerify_remark(rs.getString("verify_remark"));
		b.setRepayment_user(rs.getInt("repayment_user"));
		b.setForst_account(rs.getString("forst_account"));
		b.setRepayment_account(rs.getString("repayment_account"));
		b.setMonthly_repayment(rs.getString("monthly_repayment"));
		b.setRepayment_yesaccount(rs.getString("repayment_yesaccount"));
		b.setRepayment_yesinterest(rs.getInt("repayment_yesinterest"));
		b.setRepayment_time(rs.getString("repayment_time"));
		b.setRepayment_remark(rs.getString("repayment_remark"));
		b.setSuccess_time(rs.getString("success_time"));
		b.setEnd_time(rs.getString("end_time"));
		b.setPayment_account(rs.getString("payment_account"));
		b.setEach_time(rs.getString("each_time"));
		b.setUse(rs.getString("use"));
		b.setTime_limit(rs.getString("time_limit"));
		b.setStyle(rs.getString("style"));
		b.setAccount(rs.getString("account"));
		b.setAccount_yes(rs.getString("account_yes"));
		b.setTender_times(rs.getString("tender_times"));
		b.setApr(rs.getDouble("apr"));
		b.setLowest_account(rs.getString("lowest_account"));
		b.setMost_account(rs.getString("most_account"));
		b.setValid_time(rs.getString("valid_time"));
		b.setAward(rs.getInt("award"));
		b.setPart_account(rs.getDouble("part_account"));
		b.setFunds(rs.getDouble("funds"));
		b.setIs_false(rs.getString("is_false"));
		b.setOpen_account(rs.getString("open_account"));
		b.setOpen_borrow(rs.getString("open_borrow"));
		b.setOpen_tender(rs.getString("open_tender"));
		b.setOpen_credit(rs.getString("open_credit"));
		b.setContent(rs.getString("content"));
		b.setAddtime(rs.getString("addtime"));
		b.setAddip(rs.getString("addip"));
		b.setIs_mb(rs.getInt("is_mb"));
		b.setIs_fast(rs.getInt("is_fast"));
		b.setIs_jin(rs.getInt("is_jin"));
		b.setPwd(rs.getString("pwd"));
		b.setIsday(rs.getInt("isday"));
		b.setTime_limit_day(rs.getInt("time_limit_day"));
		b.setIs_art(rs.getInt("is_art"));
		b.setIs_charity(rs.getInt("is_charity"));
		b.setIs_xin(rs.getInt("is_xin"));
		b.setIs_project(rs.getInt("is_project"));
		b.setIs_flow(rs.getInt("is_flow"));
		b.setFlow_count(rs.getInt("flow_count"));
		b.setFlow_yescount(rs.getInt("flow_yescount"));
		b.setFlow_money(rs.getInt("flow_money"));
		b.setFlow_status(rs.getInt("flow_status"));
		b.setIs_student(rs.getInt("is_student"));
		b.setIs_offvouch(rs.getInt("is_offvouch"));
		if ((StringUtils.isNull(webid).equals("jlct"))
				|| (StringUtils.isNull(webid).equals("aidai"))) {
			b.setIs_donation(rs.getInt("is_donation"));
		}
		if (StringUtils.isNull(webid).equals("jsy")) {
			b.setIs_pledge(rs.getInt("is_pledge"));
		}
		if (StringUtils.isNull(webid).equals("zxjr")) {
			b.setIs_recommend(rs.getInt("is_recommend"));
		}
		b.setLate_award(rs.getDouble("late_award"));
	}
}