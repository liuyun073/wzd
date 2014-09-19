package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.MessageDao;
import com.liuyun.site.dao.jdbc.mapper.MessageMapper;
import com.liuyun.site.domain.Message;
import com.liuyun.site.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class MessageDaoImpl extends BaseDaoImpl implements MessageDao {
	private Logger logger = Logger.getLogger(MessageDaoImpl.class);

	public int getReceiveMessgeCount(long receive_user) {
		String sql = "select count(t1.id) from dw_message t1 left join dw_user t2 on t2.user_id=t1.receive_user left join dw_user t3 on t3.user_id=t1.sent_user where t1.deltype=0 and t1.receive_user=? ";

		int total = 0;
		total = count(sql, new Object[] { Long.valueOf(receive_user) });
		return total;
	}

	public List<Message> getReceiveMessgeByUserid(long receive_user, int start,
			int pernum) {
		String sql = "select t1.*,t2.username as receive_username,t3.username as sent_username from dw_message t1 left join dw_user t2 on t2.user_id=t1.receive_user left join dw_user t3 on t3.user_id=t1.sent_user where t1.deltype=0 and t1.receive_user=? ";

		String orderSql = " order by t1.addtime desc";
		String limitSql = " limit ?,?";
		sql = sql + orderSql + limitSql;
		List<Message> list = new ArrayList<Message>();
		list = getJdbcTemplate().query(
				sql,
				new Object[] { Long.valueOf(receive_user), Integer.valueOf(start), Integer.valueOf(pernum) },
				new MessageMapper());
		return list;
	}

	public int getSentMessgeCount(long sent_user) {
		String sql = "select count(t1.id) from dw_message t1 left join dw_user t2 on t2.user_id=t1.receive_user left join dw_user t3 on t3.user_id=t1.sent_user where t1.sented=? and t1.sent_user=? ";

		int total = 0;
		total = count(sql, new Object[] { "1", Long.valueOf(sent_user) });
		return total;
	}

	public List<Message> getSentMessgeByUserid(long sent_user, int start, int pernum) {
		String sql = "select t1.*,t2.username as receive_username ,t3.username as sent_username from dw_message t1 left join dw_user t2 on t2.user_id=t1.receive_user left join dw_user t3 on t3.user_id=t1.sent_user where t1.sented=? and t1.sent_user=? ";

		String orderSql = " order by t1.addtime desc";
		String limitSql = " limit ?,?";
		sql = sql + orderSql + limitSql;
		List<Message> list = new ArrayList<Message>();
		list = getJdbcTemplate().query(
				sql,
				new Object[] { "1", Long.valueOf(sent_user),
						Integer.valueOf(start), Integer.valueOf(pernum) },
				new MessageMapper());
		return list;
	}

	public List<Message> getMessgeByUserid(long sent_user, long receive_user, int start, int pernum) {
		String sql = "select t1.*,t2.username as sent_username,t3.username as receive_username from dw_message t1 left join dw_user t2 on t2.user_id=t1.receive_user left join dw_user t3 on t3.user_id=t1.sent_user where t1.sent_user=? and t1.receive_user=? order by t1.addtime desc";

		String orderSql = " order by t1.addtime desc";
		String limitSql = " limit ?,?";
		sql = sql + orderSql + limitSql;
		List<Message> list = new ArrayList<Message>();
		list = getJdbcTemplate().query(
				sql,
				new Object[] { Long.valueOf(sent_user),
						Long.valueOf(receive_user) }, new MessageMapper());
		return list;
	}

	public Message getMessageById(long id) {
		String sql = "select t1.*,t2.username as sent_username,t3.username as receive_username from dw_message t1 left join dw_user t2 on t2.user_id=t1.receive_user left join dw_user t3 on t3.user_id=t1.sent_user where id=?";

		Message msg = null;
		try {
			msg = (Message) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, new MessageMapper());
		} catch (DataAccessException e) {
			this.logger.info("getMessageById" + e.getMessage());
		}
		return msg;
	}

	public Message addMessage(Message msg) {
		String sql = "";
		if (msg.getIs_Authenticate() != "1") {
			sql = "insert into dw_message(sent_user,receive_user,name,status,type,sented,deltype,content,addtime,addip) values(?,?,?,?,?,?,?,?,?,?)";

			getJdbcTemplate().update(
							sql,
							new Object[] { Long.valueOf(msg.getSent_user()),
									Long.valueOf(msg.getReceive_user()),
									msg.getName(),
									Integer.valueOf(msg.getStatus()),
									msg.getType(), msg.getSented(),
									Integer.valueOf(msg.getDeltype()),
									msg.getContent(), msg.getAddtime(),
									msg.getAddip() });
		} else {
			sql = "insert into dw_message(sent_user,receive_user,name,status,type,sented,deltype,content,addtime,addip,is_Authenticate) values(?,?,?,?,?,?,?,?,?,?,?)";

			getJdbcTemplate().update(
					sql,
					new Object[] { Long.valueOf(msg.getSent_user()),
							Long.valueOf(msg.getReceive_user()), msg.getName(),
							Integer.valueOf(msg.getStatus()), msg.getType(),
							msg.getSented(), Integer.valueOf(msg.getDeltype()),
							msg.getContent(), msg.getAddtime(), msg.getAddip(),
							msg.getIs_Authenticate() });
		}
		return msg;
	}

	public Message modifyMessage(Message msg) {
		String sql = "update dw_message set sent_user=?,receive_user=?,name=?,status=?,type=?,sented=?,deltype=?,content=?,addtime=?,addip=? where id=?";
		int ret = getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(msg.getSent_user()),
						Long.valueOf(msg.getReceive_user()), msg.getName(),
						Integer.valueOf(msg.getStatus()), msg.getType(),
						msg.getSented(), Integer.valueOf(msg.getDeltype()),
						msg.getContent(), msg.getAddtime(), msg.getAddip(),
						Long.valueOf(msg.getId()) });
		if (ret < 1)
			return null;
		return msg;
	}

	public void modifyBatchMessage(List<Message> list) {
		String sql = "update dw_message set sent_user=?,receive_user=?,name=?,status=?,type=?,sented=?,deltype=?,content=?,addtime=?,addip=? where id=?";
		List<Object[]> msgList = new ArrayList<Object[]>();
		for (int i = 0; i < list.size(); ++i) {
			Message msg = (Message) list.get(i);
			Object[] args = { Long.valueOf(msg.getSent_user()),
					Long.valueOf(msg.getReceive_user()), msg.getName(),
					Integer.valueOf(msg.getStatus()), msg.getType(),
					msg.getSented(), Integer.valueOf(msg.getDeltype()),
					msg.getContent(), msg.getAddtime(), msg.getAddip(),
					Long.valueOf(msg.getId()) };
			msgList.add(args);
		}
		getJdbcTemplate().batchUpdate(sql, msgList);
	}

	public List<Message> getMessageList(Long[] ids) {
		String sql = "select t1.*,t2.username as sent_username,t3.username as receive_username from dw_message t1 left join dw_user t2 on t2.user_id=t1.receive_user left join dw_user t3 on t3.user_id=t1.sent_user where t1.id in (";

		sql = sql + StringUtils.contact(ids) + ")";
		List<Message> list = new ArrayList<Message>();
		list = getJdbcTemplate().query(sql, new MessageMapper());
		return list;
	}

	public int getMessageCount(long user_id, int status) {
		String sql = "select count(1) from dw_message t1 left join dw_user t2 on t2.user_id=t1.receive_user left join dw_user t3 on t3.user_id=t1.sent_user where t1.deltype=0 and t1.receive_user=? and t1.status=? ";

		int total = 0;
		total = count(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) });
		return total;
	}

	public int getMessageByName(long user_id, String name) {
		String sql = "select count(*) from dw_message where receive_user=? and name=? ";
		int total = 0;
		total = count(sql, new Object[] { Long.valueOf(user_id), name });
		this.logger.info("SQL:" + sql);
		return total;
	}

	public void sendMessage(String status, String subject, String type,
			String to, long user_id, String content, String addtime,
			String extra) {
		String sql = "insert into dw_message_tosend(status,subject,type,`to`,user_id,content,addtime,extra) values(?,?,?,?,?,?,?,?)";

		this.logger.info("添加发送短信或邮件 SQL ： " + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { status, subject, type, to,
						Long.valueOf(user_id), content, addtime, extra });
	}

	public int getSendMessageByContent(String content) {
		String sql = "select count(*) from dw_message_tosend where content=? ";
		int total = 0;
		total = count(sql, new Object[] { content });
		return total;
	}
}