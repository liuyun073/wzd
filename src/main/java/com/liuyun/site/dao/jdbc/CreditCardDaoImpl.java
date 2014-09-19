package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.CreditCardDao;
import com.liuyun.site.dao.jdbc.mapper.CreditCardMapper;
import com.liuyun.site.domain.CreditCard;
import com.liuyun.site.model.SearchParam;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class CreditCardDaoImpl extends BaseDaoImpl implements CreditCardDao {
	private static Logger logger = Logger.getLogger(CreditCardDaoImpl.class);

	public List<CreditCard> getList(int page, int max, SearchParam p) {
		String sql = "select * from dw_credit_card";
		sql = sql + p.getSearchParamSql();
		sql = sql + "  LIMIT ?,?";
		List<CreditCard> list = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(page), Integer.valueOf(max) }, new CreditCardMapper());
		return list;
	}

	public int getSearchCard(SearchParam param) {
		String selectSql = "select count(p2.card_id) from dw_credit_card p2 where 1=1 ";
		String searchSql = param.getSearchParamSql();
		searchSql = searchSql.replace("p1", "p2");
		String querySql = selectSql + searchSql;
		logger.debug("getSearchCard():" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public CreditCard addCreditCard(CreditCard creditCard) {
		String sql = "insert into dw_credit_card(name,issuing_bank,issuing_nstitution,issuing_status,check_style,interest,tel,currency,grade,borrowing_limit,interest_free,integral_policy,integral_indate,credit_rules,scoring_rules,installment,amount,fee_payment,applicable_fee,prepayment,open_card,repea_card,app_condition,app_way,submit_info,supplement_num,supplement_app,report_loss,loss_protection,loss_tel,lowest_refund,allopatry_back_fee,rmb_repayment,foreign_repayment,special_repayment,card_features,add_service,joint_discount,main_card_fee,supplement_card_fee,year_cut_rules,fee_date,local_fee,local_interbank_fee,offsite_fee,offsite_interbank_fee,overseas_pay_fee,overseas_unpay_fee,overseas_meet_fee,enchashment_limit,localback_fee,local_interbank_back_fee,offsite_overflow_back_fee,offsite_interbank_back_fee,overseas_pay_back_fee,overseas_unpay_back_fee,overflow_back_rules,message_money,overseas_fee,change_card,ahead_change_card,express_fee,statement_fee,statement_free_clause,loss_fee,reset_password_fee,selfdom_card_fee,foreign_convert_fee,slip_fee,slip_fee_copy,slip_fee_foreign,slip_fee_copy_foreign,overdue_fine,transfinite_fee,type_value,litpic) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { creditCard.getName(),
						creditCard.getIssuing_bank(),
						creditCard.getIssuing_nstitution(),
						creditCard.getIssuing_status(),
						creditCard.getCheck_style(), creditCard.getInterest(),
						creditCard.getTel(), creditCard.getCurrency(),
						creditCard.getGrade(), creditCard.getBorrowing_limit(),
						creditCard.getInterest_free(),
						creditCard.getIntegral_policy(),
						creditCard.getIntegral_indate(),
						creditCard.getCredit_rules(),
						creditCard.getScoring_rules(),
						creditCard.getInstallment(), creditCard.getAmount(),
						creditCard.getFee_payment(),
						creditCard.getApplicable_fee(),
						creditCard.getPrepayment(), creditCard.getOpen_card(),
						creditCard.getRepea_card(),
						creditCard.getApp_condition(), creditCard.getApp_way(),
						creditCard.getSubmit_info(),
						creditCard.getSupplement_num(),
						creditCard.getSupplement_app(),
						creditCard.getReport_loss(),
						creditCard.getLoss_protection(),
						creditCard.getLoss_tel(),
						creditCard.getLowest_refund(),
						creditCard.getAllopatry_back_fee(),
						creditCard.getRmb_repayment(),
						creditCard.getForeign_repayment(),
						creditCard.getSpecial_repayment(),
						creditCard.getCard_features(),
						creditCard.getAdd_service(),
						creditCard.getJoint_discount(),
						creditCard.getMain_card_fee(),
						creditCard.getSupplement_card_fee(),
						creditCard.getYear_cut_rules(),
						creditCard.getFee_date(), creditCard.getLocal_fee(),
						creditCard.getLocal_interbank_fee(),
						creditCard.getOffsite_fee(),
						creditCard.getOffsite_interbank_fee(),
						creditCard.getOverseas_pay_fee(),
						creditCard.getOverseas_unpay_fee(),
						creditCard.getOverseas_meet_fee(),
						creditCard.getEnchashment_limit(),
						creditCard.getLocalback_fee(),
						creditCard.getLocal_interbank_back_fee(),
						creditCard.getOffsite_overflow_back_fee(),
						creditCard.getOffsite_interbank_back_fee(),
						creditCard.getOverseas_pay_back_fee(),
						creditCard.getOverseas_unpay_back_fee(),
						creditCard.getOverflow_back_rules(),
						creditCard.getMessage_money(),
						creditCard.getOverseas_fee(),
						creditCard.getChange_card(),
						creditCard.getAhead_change_card(),
						creditCard.getExpress_fee(),
						creditCard.getStatement_fee(),
						creditCard.getStatement_free_clause(),
						creditCard.getLoss_fee(),
						creditCard.getReset_password_fee(),
						creditCard.getSelfdom_card_fee(),
						creditCard.getForeign_convert_fee(),
						creditCard.getSlip_fee(),
						creditCard.getSlip_fee_copy(),
						creditCard.getSlip_fee_foreign(),
						creditCard.getSlip_fee_copy_foreign(),
						creditCard.getOverdue_fine(),
						creditCard.getTransfinite_fee(),
						Integer.valueOf(creditCard.getType_value()),
						creditCard.getLitpic() });
		return creditCard;
	}

	public CreditCard getCardById(int cardId) {
		String sql = "select * from dw_credit_card where card_id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + cardId);
		CreditCard creditCard = null;
		try {
			creditCard = (CreditCard) getJdbcTemplate().queryForObject(sql,
					new Object[] { Integer.valueOf(cardId) },
					new CreditCardMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return creditCard;
	}

	public void updateCreditCard(CreditCard creditCard) {
		String sql = "update dw_credit_card set name=?,issuing_bank=?,issuing_nstitution=?,issuing_status=?,check_style=?,interest=?,tel=?,currency=?,grade=?,borrowing_limit=?,interest_free=?,integral_policy=?,integral_indate=?,credit_rules=?,scoring_rules=?,installment=?,amount=?,fee_payment=?,applicable_fee=?,prepayment=?,open_card=?,repea_card=?,app_condition=?,app_way=?,submit_info=?,supplement_num=?,supplement_app=?,report_loss=?,loss_protection=?,loss_tel=?,lowest_refund=?,allopatry_back_fee=?,rmb_repayment=?,foreign_repayment=?,special_repayment=?,card_features=?,add_service=?,joint_discount=?,main_card_fee=?,supplement_card_fee=?,year_cut_rules=?,fee_date=?,local_fee=?,local_interbank_fee=?,offsite_fee=?,offsite_interbank_fee=?,overseas_pay_fee=?,overseas_unpay_fee=?,overseas_meet_fee=?,enchashment_limit=?,localback_fee=?,local_interbank_back_fee=?,offsite_overflow_back_fee=?,offsite_interbank_back_fee=?,overseas_pay_back_fee=?,overseas_unpay_back_fee=?,overflow_back_rules=?,message_money=?,overseas_fee=?,change_card=?,ahead_change_card=?,express_fee=?,statement_fee=?,statement_free_clause=?,loss_fee=?,reset_password_fee=?,selfdom_card_fee=?,foreign_convert_fee=?,slip_fee=?,slip_fee_copy=?,slip_fee_foreign=?,slip_fee_copy_foreign=?,overdue_fine=?,transfinite_fee=?,type_value=?,litpic=? where card_id=?";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { creditCard.getName(),
						creditCard.getIssuing_bank(),
						creditCard.getIssuing_nstitution(),
						creditCard.getIssuing_status(),
						creditCard.getCheck_style(), creditCard.getInterest(),
						creditCard.getTel(), creditCard.getCurrency(),
						creditCard.getGrade(), creditCard.getBorrowing_limit(),
						creditCard.getInterest_free(),
						creditCard.getIntegral_policy(),
						creditCard.getIntegral_indate(),
						creditCard.getCredit_rules(),
						creditCard.getScoring_rules(),
						creditCard.getInstallment(), creditCard.getAmount(),
						creditCard.getFee_payment(),
						creditCard.getApplicable_fee(),
						creditCard.getPrepayment(), creditCard.getOpen_card(),
						creditCard.getRepea_card(),
						creditCard.getApp_condition(), creditCard.getApp_way(),
						creditCard.getSubmit_info(),
						creditCard.getSupplement_num(),
						creditCard.getSupplement_app(),
						creditCard.getReport_loss(),
						creditCard.getLoss_protection(),
						creditCard.getLoss_tel(),
						creditCard.getLowest_refund(),
						creditCard.getAllopatry_back_fee(),
						creditCard.getRmb_repayment(),
						creditCard.getForeign_repayment(),
						creditCard.getSpecial_repayment(),
						creditCard.getCard_features(),
						creditCard.getAdd_service(),
						creditCard.getJoint_discount(),
						creditCard.getMain_card_fee(),
						creditCard.getSupplement_card_fee(),
						creditCard.getYear_cut_rules(),
						creditCard.getFee_date(), creditCard.getLocal_fee(),
						creditCard.getLocal_interbank_fee(),
						creditCard.getOffsite_fee(),
						creditCard.getOffsite_interbank_fee(),
						creditCard.getOverseas_pay_fee(),
						creditCard.getOverseas_unpay_fee(),
						creditCard.getOverseas_meet_fee(),
						creditCard.getEnchashment_limit(),
						creditCard.getLocalback_fee(),
						creditCard.getLocal_interbank_back_fee(),
						creditCard.getOffsite_overflow_back_fee(),
						creditCard.getOffsite_interbank_back_fee(),
						creditCard.getOverseas_pay_back_fee(),
						creditCard.getOverseas_unpay_back_fee(),
						creditCard.getOverflow_back_rules(),
						creditCard.getMessage_money(),
						creditCard.getOverseas_fee(),
						creditCard.getChange_card(),
						creditCard.getAhead_change_card(),
						creditCard.getExpress_fee(),
						creditCard.getStatement_fee(),
						creditCard.getStatement_free_clause(),
						creditCard.getLoss_fee(),
						creditCard.getReset_password_fee(),
						creditCard.getSelfdom_card_fee(),
						creditCard.getForeign_convert_fee(),
						creditCard.getSlip_fee(),
						creditCard.getSlip_fee_copy(),
						creditCard.getSlip_fee_foreign(),
						creditCard.getSlip_fee_copy_foreign(),
						creditCard.getOverdue_fine(),
						creditCard.getTransfinite_fee(),
						Integer.valueOf(creditCard.getType_value()),
						creditCard.getLitpic(),
						Integer.valueOf(creditCard.getCard_id()) });
	}

	public void updateLitpic(String litpic, int cardId) {
		String sql = "update dw_credit_card set litpic=? where card_id=?";
		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql,
				new Object[] { litpic, Integer.valueOf(cardId) });
	}

	public List<CreditCard> getCardByType(int type) {
		String sql = "select * from dw_credit_card where type_value=? limit 0,4";
		logger.debug("SQL:" + sql);
		List<CreditCard> list = new ArrayList<CreditCard>();
		try {
			list = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(type) }, new CreditCardMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return list;
	}

	public void delCreditCard(int cardId) {
		String sql = "delete from dw_credit_card where card_id=?";
		getJdbcTemplate().update(sql, new Object[] { Integer.valueOf(cardId) });
	}
}