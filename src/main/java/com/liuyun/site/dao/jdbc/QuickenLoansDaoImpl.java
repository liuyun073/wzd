package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.QuickenLoansDao;
import com.liuyun.site.dao.jdbc.mapper.QuickenLoansMapper;
import com.liuyun.site.domain.QuickenLoans;
import com.liuyun.site.model.SearchParam;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class QuickenLoansDaoImpl extends BaseDaoImpl implements QuickenLoansDao {
	private static Logger logger = Logger.getLogger(QuickenLoansDaoImpl.class);

	public List<QuickenLoans> getList(int page, int max, SearchParam p) {
		String sql = "select * from dw_quicken_loans";
		sql = sql + p.getSearchParamSql();
		sql = sql + "  LIMIT ?,?";
		List<QuickenLoans> list = getJdbcTemplate().query(sql,
				new Object[] { Integer.valueOf(page), Integer.valueOf(max) },
				new QuickenLoansMapper());
		return list;
	}

	public int getSearchCard(SearchParam param) {
		String selectSql = "select count(p2.loans_id) from dw_quicken_loans p2 where 1=1 ";
		String searchSql = param.getSearchParamSql();
		searchSql = searchSql.replace("p1", "p2");
		String querySql = selectSql + searchSql;
		logger.debug("getSearchCard():" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public void addQuickenLoans(QuickenLoans quickenLoans) {
		String sql = "insert into dw_quicken_loans(name,phone,area,remark,create_time) values (?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { quickenLoans.getName(), quickenLoans.getPhone(),
						quickenLoans.getArea(), quickenLoans.getRemark(),
						quickenLoans.getCreateTime() });
	}

	public QuickenLoans getLoansById(int loansId) {
		String sql = "select * from dw_quicken_loans where loans_id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + loansId);
		QuickenLoans quickenLoans = null;
		try {
			quickenLoans = (QuickenLoans) getJdbcTemplate().queryForObject(sql,
					new Object[] { Integer.valueOf(loansId) },
					new QuickenLoansMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return quickenLoans;
	}

	public void delQuickenLoans(int loansId) {
		String sql = "delete from dw_quicken_Loans where loans_id=?";
		getJdbcTemplate()
				.update(sql, new Object[] { Integer.valueOf(loansId) });
	}
}