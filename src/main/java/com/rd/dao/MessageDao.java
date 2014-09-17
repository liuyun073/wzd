package com.rd.dao;

import com.rd.domain.Message;
import java.util.List;

public abstract interface MessageDao {
	public abstract int getReceiveMessgeCount(long paramLong);

	public abstract List<Message> getReceiveMessgeByUserid(long paramLong,
			int paramInt1, int paramInt2);

	public abstract int getSentMessgeCount(long paramLong);

	public abstract List<Message> getSentMessgeByUserid(long paramLong, int paramInt1,
			int paramInt2);

	public abstract List<Message> getMessgeByUserid(long paramLong1, long paramLong2,
			int paramInt1, int paramInt2);

	public abstract Message getMessageById(long paramLong);

	public abstract Message addMessage(Message paramMessage);

	public abstract Message modifyMessage(Message paramMessage);

	public abstract void modifyBatchMessage(List<Message> paramList);

	public abstract List<Message> getMessageList(Long[] paramArrayOfLong);

	public abstract int getMessageCount(long paramLong, int paramInt);

	public abstract int getMessageByName(long paramLong, String paramString);

	public abstract void sendMessage(String paramString1, String paramString2,
			String paramString3, String paramString4, long paramLong,
			String paramString5, String paramString6, String paramString7);

	public abstract int getSendMessageByContent(String paramString);
}