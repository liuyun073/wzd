package com.rd.dao.jdbc;

import com.rd.dao.LateBorrowLogDao;
import java.util.List;
import java.util.Map;

public class LateBorrowLogDaoImpl extends BaseDaoImpl implements
		LateBorrowLogDao {
	public boolean addLateBorrowLogDetail(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into dw_late_borrow_log (borrow_id, phone_type, phone_num, phone_status, relation_type, relation_name, repay_time, memo, ts)  ");
		sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, now()) ");
		Object[] args = { params.get("borrow_id"), params.get("phone_type"),
				params.get("phone_num"), params.get("phone_status"),
				params.get("relation_type"), params.get("relation_name"),
				params.get("repay_time"), params.get("memo") };
		int a = getJdbcTemplate().update(sql.toString(), args);
		return a != 0;
	}

	public List<Map<String, Object>> queryLateBorrowDetails(String borrow_id) {
		String sql = " SELECT id , phone_type, phone_num, phone_status, relation_type, relation_name, repay_time, memo,ts  FROM dw_late_borrow_log WHERE borrow_id = ? order by id desc ";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, new Object[] { borrow_id });
		return list;
	}
}