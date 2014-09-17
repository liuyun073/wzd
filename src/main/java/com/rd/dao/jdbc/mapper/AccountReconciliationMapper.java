package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractAccountMapper;
import com.rd.model.account.AccountReconciliationModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AccountReconciliationMapper extends AbstractAccountMapper
		implements RowMapper<AccountReconciliationModel> {
	public AccountReconciliationModel mapRow(ResultSet rs, int num)
			throws SQLException {
		AccountReconciliationModel s = new AccountReconciliationModel();
		setProperty(rs, s);
		s.setRecharge_money(rs.getDouble("recharge_money"));
		s.setLog_recharge_money(rs.getDouble("log_recharge_money"));
		s.setUp_recharge_money(rs.getDouble("up_recharge_money"));
		s.setDown_recharge_money(rs.getDouble("down_recharge_money"));
		s.setHoutai_recharge_money(rs.getDouble("houtai_recharge_money"));
		s.setCash_money(rs.getDouble("cash_money"));
		s.setAllcollection(rs.getDouble("allcollection"));
		s.setBorrow_award(rs.getDouble("borrow_award"));
		s.setBorrow_fee(rs.getDouble("borrow_fee"));
		s.setCollection(rs.getDouble("collection"));
		s.setInvest_award(rs.getDouble("invest_award"));
		s.setInvest_yeswait_interest(rs.getDouble("invest_yeswait_interest"));
		s.setInvite_money(rs.getDouble("invite_money"));
		s.setManage_fee(rs.getDouble("manage_fee"));
		s.setVip_money(rs.getDouble("vip_money"));
		s.setWait_interest(rs.getDouble("wait_interest"));
		s.setUsername(rs.getString("username"));
		s.setRepayment_interest(rs.getDouble("repayment_interest"));
		s.setRepayment_principal(rs.getDouble("repayment_principal"));
		s.setSystem_fee(rs.getDouble("system_fee"));
		s.setYes_repayment_interest(rs.getDouble("yes_repayment_interest"));
		s.setFlow_repayment_interest(rs.getDouble("flow_repayment_interest"));
		s.setFlow_yes_repayment_interest(rs.getDouble("flow_yes_repayment_interest"));
		return s;
	}
}