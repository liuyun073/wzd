package com.rd.service.impl;

import com.rd.dao.MessageDao;
import com.rd.domain.Message;
import com.rd.model.PageDataList;
import com.rd.service.MessageService;
import com.rd.tool.Page;
import java.util.List;

public class MessageServiceImpl implements MessageService {
	private MessageDao messageDao;

	public MessageDao getMessageDao() {
		return this.messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public PageDataList getReceiveMessgeByUserid(long userid, int startPage) {
		PageDataList pList = new PageDataList();
		int total = this.messageDao.getReceiveMessgeCount(userid);
		Page p = new Page(total, startPage);
		List list = this.messageDao.getReceiveMessgeByUserid(userid, p
				.getStart(), p.getPernum());
		pList.setPage(p);
		pList.setList(list);
		return pList;
	}

	public PageDataList getSentMessgeByUserid(long userid, int startPage) {
		PageDataList pList = new PageDataList();
		int total = this.messageDao.getSentMessgeCount(userid);
		Page p = new Page(total, startPage);
		List list = this.messageDao.getSentMessgeByUserid(userid, p.getStart(),
				p.getPernum());
		pList.setPage(p);
		pList.setList(list);
		return pList;
	}

	public Message getMessageById(long id) {
		return this.messageDao.getMessageById(id);
	}

	public Message addMessage(Message msg) {
		return this.messageDao.addMessage(msg);
	}

	public Message modifyMessge(Message msg) {
		return this.messageDao.modifyMessage(msg);
	}

	public void deleteReceiveMessage(Long[] ids) {
		List<Message> list = this.messageDao.getMessageList(ids);
		for (Message msg : list) {
			msg.setDeltype(1);
		}
		this.messageDao.modifyBatchMessage(list);
	}

	public void deleteSentMessage(Long[] ids) {
		List<Message> list = this.messageDao.getMessageList(ids);
		for (Message msg : list) {
			msg.setSented("0");
		}
		this.messageDao.modifyBatchMessage(list);
	}

	public void setReadMessage(Long[] ids) {
		List<Message> list = this.messageDao.getMessageList(ids);
		for (Message msg : list) {
			msg.setStatus(1);
		}
		this.messageDao.modifyBatchMessage(list);
	}

	public void setUnreadMessage(Long[] ids) {
		List<Message> list = this.messageDao.getMessageList(ids);
		for (Message msg : list) {
			msg.setStatus(0);
		}
		this.messageDao.modifyBatchMessage(list);
	}

	public int getUnreadMesage(long userid) {
		return this.messageDao.getMessageCount(userid, 0);
	}
}