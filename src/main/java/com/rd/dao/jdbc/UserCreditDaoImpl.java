package com.rd.dao.jdbc;

import com.rd.dao.UserCreditDao;
import com.rd.dao.jdbc.mapper.CreditLogMapper;
import com.rd.dao.jdbc.mapper.CreditTypeMapper;
import com.rd.dao.jdbc.mapper.UserCreditLogMapper;
import com.rd.dao.jdbc.mapper.UserCreditMapper;
import com.rd.dao.jdbc.mapper.UserCreditModelMapper;
import com.rd.domain.CreditType;
import com.rd.domain.UserCredit;
import com.rd.domain.UserCreditLog;
import com.rd.model.SearchParam;
import com.rd.model.UserCreditLogModel;
import com.rd.model.UserCreditModel;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserCreditDaoImpl extends BaseDaoImpl implements UserCreditDao {
	private static Logger logger = Logger.getLogger(UserCreditDaoImpl.class);

	public void addUserCredit(UserCredit uc) {
		String sql = "insert into  dw_credit(user_id, value,op_user, addtime, addip,updatetime, updateip, tender_value, borrow_value, gift_value, expense_value ,valid_value) values (?,?,?,?,?,?,?,?,?,?,?,?)";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(uc.getUser_id()),
						Integer.valueOf(uc.getValue()),
						Integer.valueOf(uc.getOp_user()),
						Long.valueOf(uc.getAddtime()), uc.getAddip(),
						uc.getUpdatetime(), uc.getUpdateip(),
						Integer.valueOf(uc.getTender_value()),
						Integer.valueOf(uc.getBorrow_value()),
						Integer.valueOf(uc.getGift_value()),
						Integer.valueOf(uc.getExpense_value()),
						Integer.valueOf(uc.getValid_value()) });
	}

	public List<UserCreditModel> getUserCreditByPageNumber(int Page, int Max) {
		String sql = "SELECT p.*,`user`.username,`user`.realname,type.name as creditTypeName,log.remark as creditLogRemark ,log.addtime as creditLogAddTime from dw_credit AS p LEFT JOIN  dw_credit_log as log on p.user_id=log.user_id LEFT JOIN dw_credit_type as type on log.type_id=type.id LEFT JOIN dw_user as user on p.user_id=`user`.user_id LIMIT ?,?";
		List<UserCreditModel> list = getJdbcTemplate().query(
				sql,
				new Object[] { Integer.valueOf(Page * Max),
						Integer.valueOf(Max) }, new UserCreditModelMapper());
		return list;
	}

	public void updateUserCredit(UserCredit userCredit) {
		String sql = "update dw_credit set value = ? where user_id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + userCredit.getUser_id());
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(userCredit.getValue()),
						Long.valueOf(userCredit.getUser_id()) });
	}

	public void updateCreditTenderValue(UserCredit userCredit) {
		String sql = "update dw_credit set tender_value = ? where user_id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + userCredit.getUser_id());
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(userCredit.getTender_value()),
						Long.valueOf(userCredit.getUser_id()) });
	}

	public UserCredit getUserCreditByUserId(long user_id) {
		String sql = "select * from dw_credit where user_id = ?";
		UserCredit userCredit = null;
		try {
			userCredit = (UserCredit) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(user_id) },
					new UserCreditMapper());
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		return userCredit;
	}

	public int getCreditValueByUserId(long user_id) {
		String sql = "select value from dw_credit where user_id=?";
		return getJdbcTemplate().queryForInt(sql,
				new Object[] { Long.valueOf(user_id) });
	}

	public int getUserCreditCount(SearchParam p) {
		String sql = "select count(1) from dw_user as p2 left join dw_credit as p1 on p1.user_id=p2.user_id  where 1=1";
		sql = sql + p.getSearchParamSql();
		return getJdbcTemplate().queryForInt(sql);
	}

	public List<UserCreditModel> getUserCredit(int page, int max, SearchParam p) {
		String sql = "select p1.*, p2.username, p2.realname,p3.pic as credit_pic from dw_user as p2 left join dw_credit as p1 on p1.user_id = p2.user_id left join dw_credit_rank as p3 on p1.value <= p3.point2 and p1.value >= p3.point1 where 1 = 1 ";

		sql = sql + p.getSearchParamSql();
		sql = sql + "  LIMIT ?,?";
		return getJdbcTemplate().query(sql,
				new Object[] { Integer.valueOf(page), Integer.valueOf(max) },
				new UserCreditModelMapper());
	}

	public void addUserCreditLog(UserCreditLog ucLog) {
		String sql = "insert into dw_credit_log(id,user_id,type_id,op,value,remark,op_user,addtime,addip) values(?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(ucLog.getId()),
						Long.valueOf(ucLog.getUser_id()),
						Integer.valueOf(ucLog.getType_id()),
						Long.valueOf(ucLog.getOp()),
						Integer.valueOf(ucLog.getValue()), ucLog.getRemark(),
						Integer.valueOf(ucLog.getOp_user()),
						Long.valueOf(ucLog.getAddtime()), ucLog.getAddip() });
	}

	public UserCreditModel getCreditModelById(long user_id) {
		String sql = "select p1.*, p2.username, p2.realname,p3.pic as credit_pic from dw_user as p2 left join dw_credit as p1 on p1.user_id = p2.user_id left join dw_credit_rank as p3 on p1.value <= p3.point2 and p1.value >= p3.point1 where 1 = 1 and p2.user_id=?";

		UserCreditModel credit = null;
		try {
			credit = (UserCreditModel) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(user_id) },
					new UserCreditModelMapper());
		} catch (DataAccessException e) {
			logger.info("查询结果为空！");
		}
		return credit;
	}

	public UserCreditLogModel getCreditLogByUserId(long user_id) {
		String sql = "select p1.*,p2.username,p2.realname from dw_credit_log as p1 left join dw_user as p2 on p1.user_id = p2.user_id where 1=1 and p1.user_id =?";
		return (UserCreditLogModel) getJdbcTemplate().queryForObject(sql,
				new Object[] { Long.valueOf(user_id) }, new CreditLogMapper());
	}

	public int getCreditLogCount(SearchParam p) {
		String sql = "select count(1) from dw_user as p2 left join dw_credit_log as p1 on p1.user_id=p2.user_id  where 1=1";
		sql = sql + p.getSearchParamSql();
		return getJdbcTemplate().queryForInt(sql);
	}

	public List<UserCreditLogModel> getCreditLog(int page, int max, SearchParam p) {
		String sql = "SELECT p1.*,p2.username,p2.realname,p3.name as typeName from dw_user as p2 left join dw_credit_log as p1 on p1.user_id=p2.user_id left join dw_credit_type as p3 on p1.type_id = p3.id where 1=1";

		sql = sql + p.getSearchParamSql();
		sql = sql + "  LIMIT ?,?";
		return getJdbcTemplate().query(sql,
				new Object[] { Integer.valueOf(page), Integer.valueOf(max) },
				new CreditLogMapper());
	}

	public CreditType getCreditTypeByNid(String nid) {
		String sql = "select dct.id, dct.name, dct.nid, dct.value, dct.cycle, dct.award_times, dct.interval, dct.remark, dct.op_user, dct.addtime, dct.addip, dct.updatetime, dct.updateip, dct.rule_nid, dct.credit_category from dw_credit_type dct where dct.nid = ? ";
		try {
			CreditType type = (CreditType) getJdbcTemplate().queryForObject(
					sql, new Object[] { nid }, new CreditTypeMapper());
			return type;
		} catch (Exception e) {
			logger.info("查询结果为空！");
		}
		return null;
	}

	public List<CreditType> getCreditTypeList(String credit_category) {
		String sql = "select dct.id, dct.name, dct.nid, dct.value, dct.cycle, dct.award_times, dct.interval, dct.remark, dct.op_user, dct.addtime, dct.addip, dct.updatetime, dct.updateip, dct.rule_nid, dct.credit_category from dw_credit_type dct where dct.credit_category = ? ";
		return getJdbcTemplate().query(sql, new Object[] { credit_category },
				new CreditTypeMapper());
	}

	public void editCreditValue(long user_id, int value) {
		String sql = "";
		if (value > 0)
			sql = "update dw_credit dc set  dc.value = ifnull(dc.value,0) + ? , dc.valid_value = ifnull(dc.valid_value,0) + ? where user_id = ? ";
		else if (value < 0) {
			sql = "update dw_credit dc set dc.valid_value = ifnull(dc.valid_value,0) + ? , dc.expense_value = ifnull(dc.expense_value ,0) - ? where user_id = ? ";
		}
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(value), Integer.valueOf(value),
						Long.valueOf(user_id) });
	}

	public void editTenderValue(long user_id, int value) {
		String sql = "";
		if (value > 0)
			sql = "update dw_credit dc set dc.value = ifnull(dc.value,0) + ? , dc.tender_value = ifnull(dc.tender_value,0) + ? , dc.valid_value = ifnull(dc.valid_value,0) + ? where user_id = ? ";
		else if (value < 0) {
			sql = "update dw_credit dc set dc.tender_value = ifnull(dc.tender_value,0) + ? , dc.valid_value = ifnull(dc.valid_value,0) + ? , dc.expense_value = ifnull(dc.expense_value ,0) - ? where user_id = ? ";
		}
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(value), Integer.valueOf(value),
						Integer.valueOf(value), Long.valueOf(user_id) });
	}

	public void editBorrowValue(long user_id, int value) {
		String sql = "";
		if (value > 0) {
			sql = "update dw_credit dc set dc.value = ifnull(dc.value,0) + ? , dc.borrow_value = ifnull(dc.borrow_value,0) + ? , dc.valid_value = ifnull(dc.valid_value,0) + ? where user_id = ? ";
			getJdbcTemplate().update(
					sql,
					new Object[] { Integer.valueOf(value),
							Integer.valueOf(value), Integer.valueOf(value),
							Long.valueOf(user_id) });
		} else if (value < 0) {
			sql = "update dw_credit dc set dc.borrow_value = ifnull(dc.borrow_value,0) + ? , dc.valid_value = ifnull(dc.valid_value,0) + ? , dc.expense_value = ifnull(dc.expense_value ,0) - ? where user_id = ? ";
		}
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(value), Integer.valueOf(value),
						Integer.valueOf(value), Long.valueOf(user_id) });
	}

	public void editGiftValue(long user_id, int value) {
		String sql = "";
		if (value > 0)
			sql = "update dw_credit dc set dc.value = ifnull(dc.value,0) + ? , dc.gift_value = ifnull(dc.gift_value,0)+ ? , dc.valid_value = ifnull(dc.valid_value,0) + ? where user_id = ? ";
		else if (value < 0) {
			sql = "update dw_credit dc set dc.gift_value = ifnull(dc.gift_value,0)+ ? , dc.valid_value = ifnull(dc.valid_value,0) + ? , dc.expense_value = ifnull(dc.expense_value ,0) - ? where user_id = ? ";
		}
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(value), Integer.valueOf(value),
						Integer.valueOf(value), Long.valueOf(user_id) });
	}

	public void editExpenseValue(long user_id, int value) {
		if (value > 0) {
			String sql = "update dw_credit dc set dc.valid_value = ifnull(dc.valid_value,0) - ? , dc.expense_value = ifnull(dc.expense_value ,0)+ ? where user_id = ? ";
			getJdbcTemplate().update(
					sql,
					new Object[] { Integer.valueOf(value),
							Integer.valueOf(value), Long.valueOf(user_id) });
		}
	}

	public List<CreditType> getCreditTypeByUserId(long user_id) {
		String sql = "select dct.* from dw_credit_log dcl left join dw_credit_type dct on dcl.type_id = dct.id  where dcl.user_id = ? group by dct.id ";
		return getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) }, new CreditTypeMapper());
	}

	public int getUserCreditLogCount(SearchParam p, long type_id, long user_id) {
		String sql = "select count(1) from dw_user as p2 left join dw_credit_log as p1 on p1.user_id=p2.user_id  where 1 = 1";

		List<Long> list = new ArrayList<Long>();

		if (type_id > 0L) {
			sql = sql + " and p1.type_id = ?";
			list.add(Long.valueOf(type_id));
		}

		if (user_id > 0L) {
			sql = sql + " and p1.user_id = ?";
			list.add(Long.valueOf(user_id));
		}
		sql = sql + p.getSearchParamSql();

		Object[] obj = new Object[list.size()];

		for (int i = 0; i < list.size(); ++i) {
			obj[i] = list.get(i);
		}

		return getJdbcTemplate().queryForInt(sql, obj);
	}

	public List<UserCreditLogModel> getCreditLogPage(int page, int max, SearchParam p,
			long type_id, long user_id) {
		String sql = "SELECT p1.*,p2.username,p2.realname,p3.name as typeName from dw_user as p2 left join dw_credit_log as p1 on p1.user_id=p2.user_id left join dw_credit_type as p3 on p1.type_id = p3.id where 1=1";

		List<Object> list = new ArrayList<Object>();

		if (type_id > 0L) {
			sql = sql + " and p1.type_id = ?";
			list.add(Long.valueOf(type_id));
		}

		if (user_id > 0L) {
			sql = sql + " and p1.user_id = ?";
			list.add(Long.valueOf(user_id));
		}

		list.add(Integer.valueOf(page));
		list.add(Integer.valueOf(max));

		Object[] obj = new Object[list.size()];

		for (int i = 0; i < list.size(); ++i) {
			obj[i] = list.get(i);
		}

		sql = sql + p.getSearchParamSql();
		sql = sql + " order by p1.id desc LIMIT ?,?";
		return getJdbcTemplate().query(sql, obj, new CreditLogMapper());
	}

	public List<CreditType> getCreditTypeAll() {
		String sql = "select * from dw_credit_type ";
		return getJdbcTemplate().query(sql, new CreditTypeMapper());
	}

	public List<UserCreditLog> getCreditLogList(long user_id, long type_id) {
		String sql = "select * from dw_credit_log where user_id = ? and type_id = ? ";
		return getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id), Long.valueOf(type_id) },
				new UserCreditLogMapper());
	}

	public int getValidValueByUserId(long user_id) {
		String sql = "select valid_value from dw_credit where user_id=?";
		return getJdbcTemplate().queryForInt(sql,
				new Object[] { Long.valueOf(user_id) });
	}
}