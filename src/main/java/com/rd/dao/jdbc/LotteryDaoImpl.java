package com.rd.dao.jdbc;

import com.rd.dao.LotteryDao;
import com.rd.domain.LotteryRule;
import com.rd.domain.TenderAddLotteryTimes;
import com.rd.domain.WinningInformation;
import com.rd.model.SearchParam;
import com.rd.util.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class LotteryDaoImpl extends BaseDaoImpl implements LotteryDao {
	private static Logger logger = Logger.getLogger(LotteryDaoImpl.class);

	RowMapper<LotteryRule> mapper = new RowMapper<LotteryRule>() {
		public LotteryRule mapRow(ResultSet rs, int num) throws SQLException {
			LotteryRule c = new LotteryRule();
			c.setId(rs.getLong("id"));
			c.setName(rs.getString("name"));
			c.setStart_date(rs.getString("start_date"));
			c.setEnd_date(rs.getString("end_date"));
			c.setIs_accordingtoaward(rs.getLong("is_accordingtoaward"));
			c.setIs_countpoint(rs.getLong("is_countpoint"));
			c.setPoint_limit(rs.getLong("point_limit"));
			c.setUser_limit(rs.getLong("user_limit"));
			c.setDescription(rs.getString("description"));
			c.setGmt_create(rs.getString("gmt_create"));
			c.setGmt_modfied(rs.getString("gmt_modfied"));
			c.setMsg_type(rs.getLong("msg_type"));
			c.setContext(rs.getString("context"));
			c.setSubject(rs.getString("subject"));
			c.setSingle_point_limit(rs.getLong("single_point_limit"));
			c.setMax_point(rs.getLong("max_point"));
			c.setMax_award_num(rs.getLong("max_award_num"));
			c.setMax_day_times(rs.getLong("max_day_times"));
			c.setMax_times(rs.getLong("max_times"));
			c.setPlayer_type(rs.getLong("player_type"));
			c.setAttribute(rs.getString("attribute"));
			c.setSingle_money_limit(rs.getLong("single_money_limit"));
			c.setSingle_max_point_limit(rs.getLong("single_max_point_limit"));
			return c;
		}
	};

	RowMapper<WinningInformation> winningmapper = new RowMapper<WinningInformation>() {
		public WinningInformation mapRow(ResultSet rs, int num) throws SQLException {
			WinningInformation c = new WinningInformation();
			c.setId(rs.getLong("id"));
			c.setUser_id(rs.getLong("user_id"));
			c.setUsername(rs.getString("username"));
			c.setGmt_create(rs.getString("gmt_create"));
			c.setGmt_modified(rs.getString("gmt_modified"));
			c.setLevel(rs.getLong("level"));
			c.setAward_id(rs.getLong("award_id"));
			c.setPoint_reduce(rs.getLong("point_reduce"));
			c.setRule_id(rs.getLong("rule_id"));
			c.setAward_name(rs.getString("award_name"));
			c.setStatus(rs.getLong("status"));
			c.setAttributes(rs.getString("attributes"));
			return c;
		}
	};

	public List<LotteryRule> getList() {
		String sql = "select * from dw_lottery_rule";
		List<LotteryRule> list = getJdbcTemplate().query(sql, new Object[0], this.mapper);
		return list;
	}

	public LotteryRule getLotteryById(long id) {
		String sql = "select * from dw_lottery_rule where 1=1 ";
		if (id >= 0L) {
			sql = sql + " and id=? ";
		}
		LotteryRule lotteryRule = (LotteryRule) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, this.mapper);
		return lotteryRule;
	}

	public int getListCount() {
		int total = 0;
		String sql = "select count(1) from dw_lottery_rule";
		logger.debug("SQL:" + sql);
		try {
			total = getJdbcTemplate().queryForInt(sql, new Object[0]);
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return total;
	}

	public void add(LotteryRule n) {
		String sql = "insert into dw_lottery_rule(name,start_date,end_date,is_accordingtoaward,is_countpoint,point_limit,user_limit,description,gmt_create,gmt_modfied,msg_type,context,subject,single_point_limit,max_point,max_award_num) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { n.getName(), n.getStart_date(), n.getEnd_date(),
						Long.valueOf(n.getIs_accordingtoaward()),
						Long.valueOf(n.getIs_countpoint()),
						Long.valueOf(n.getPoint_limit()),
						Long.valueOf(n.getUser_limit()), n.getDescription(),
						n.getGmt_create(), n.getGmt_modfied(),
						Long.valueOf(n.getMsg_type()), n.getContext(),
						n.getSubject(),
						Long.valueOf(n.getSingle_point_limit()),
						Long.valueOf(n.getMax_point()),
						Long.valueOf(n.getMax_award_num()) });
	}

	public void update(LotteryRule n) {
		String sql = "update dw_lottery_rule set name=?,start_date=?,end_date=?,is_accordingtoaward=?,is_countpoint=?,point_limit=?,user_limit=?,description=?,gmt_create=?,gmt_modfied=?,msg_type=?,context=?,subject=?,single_point_limit=?,max_point=?,max_award_num=? where id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] { n.getName(), n.getStart_date(), n.getEnd_date(),
						Long.valueOf(n.getIs_accordingtoaward()),
						Long.valueOf(n.getIs_countpoint()),
						Long.valueOf(n.getPoint_limit()),
						Long.valueOf(n.getUser_limit()), n.getDescription(),
						n.getGmt_create(), n.getGmt_modfied(),
						Long.valueOf(n.getMsg_type()), n.getContext(),
						n.getSubject(),
						Long.valueOf(n.getSingle_point_limit()),
						Long.valueOf(n.getMax_point()),
						Long.valueOf(n.getMax_award_num()),
						Long.valueOf(n.getId()) });
	}

	public void winning_information_add(WinningInformation n) {
		String sql = "insert into dw_winning_information(user_id,username,level,award_id,point_reduce,rule_id,award_name,status,gmt_create,gmt_modified,attributes) values(?,?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(n.getUser_id()), n.getUsername(),
						Long.valueOf(n.getLevel()),
						Long.valueOf(n.getAward_id()),
						Long.valueOf(n.getPoint_reduce()),
						Long.valueOf(n.getRule_id()), n.getAward_name(),
						Long.valueOf(n.getStatus()), n.getGmt_create(),
						n.getGmt_modified(), n.getAttributes() });
	}

	public List<WinningInformation> getWinningInformationList() {
		String sql = "select * from dw_winning_information ";
		List<WinningInformation> list = getJdbcTemplate().query(sql, new Object[0], this.winningmapper);
		return list;
	}

	public int getWinningInformationByUseridCount(long userid, long status) {
		int times = 0;
		String sql = "select count(1) from dw_winning_information where  gmt_create>"
				+ DateUtils.getIntegralTime().getTime() / 1000L
				+ " and gmt_create<"
				+ DateUtils.getLastIntegralTime().getTime() / 1000L
				+ " and 1=1 ";
		if (userid > 0L) {
			sql = sql + " and user_id=? ";
			times = getJdbcTemplate().queryForInt(sql, new Object[] { Long.valueOf(userid) });
		}
		if (status != 0L) {
			sql = sql + " and status=? ";
			times = getJdbcTemplate().queryForInt(sql, new Object[] { Long.valueOf(userid), Long.valueOf(status) });
		}

		return times;
	}

	public double getWinningInformationByAwardSum(long status) {
		int times = 0;
		String sql = "select sum(winning_money) from dw_winning_information where  gmt_create>"
				+ DateUtils.getIntegralTime().getTime() / 1000L
				+ " and gmt_create<"
				+ DateUtils.getLastIntegralTime().getTime() / 1000L
				+ " and status=?";
		times = getJdbcTemplate().queryForInt(sql, new Object[] { Long.valueOf(status) });
		return times;
	}

	public List<WinningInformation> getWinningInformationList(int start, int end, SearchParam param) {
		String sql = "select * from dw_winning_information p1 where 1=1 ";
		String orderSql = " order by p1.gmt_create desc ";
		String limitSql = "limit ?,?";
		String searchSql = param.getSearchParamSql();
		sql = sql + searchSql + orderSql + limitSql;
		logger.debug("winningInformationList:" + sql);
		List<WinningInformation> list = new ArrayList<WinningInformation>();
		list = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(start), Integer.valueOf(end) }, this.winningmapper);
		return list;
	}

	public int getWinningInformationCount(SearchParam param) {
		String sql = "select count(1) from dw_winning_information p1 where 1=1 ";
		sql = sql + param.getSearchParamSql();
		logger.debug("SQL:" + sql);
		int count = 0;
		count = count(sql, new Object[0]);
		return count;
	}

	public void addTenderAddLotteryTimes(TenderAddLotteryTimes n) {
		String sql = "insert into dw_tender_add_lottery_times(user_id,tender_id,lottery_times,addtime) values(?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(n.getUser_id()),
						Long.valueOf(n.getTender_id()),
						Long.valueOf(n.getLottery_times()), n.getAddtime() });
	}

	public int getTenderAddLotteryTimes(long userid) {
		int times = 0;
		String sql = "select sum(lottery_times) from dw_tender_add_lottery_times where  addtime>"
				+ DateUtils.getIntegralTime().getTime() / 1000L
				+ " and addtime<"
				+ DateUtils.getLastIntegralTime().getTime() / 1000L + " and user_id=?";
		times = getJdbcTemplate().queryForInt(sql, new Object[] { Long.valueOf(userid) });
		return times;
	}
}