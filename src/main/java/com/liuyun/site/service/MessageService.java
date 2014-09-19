package com.liuyun.site.service;

import com.liuyun.site.domain.Message;
import com.liuyun.site.model.PageDataList;

public abstract interface MessageService {
	public abstract PageDataList getReceiveMessgeByUserid(long paramLong,
			int paramInt);

	public abstract PageDataList getSentMessgeByUserid(long paramLong,
			int paramInt);

	public abstract Message getMessageById(long paramLong);

	public abstract Message addMessage(Message paramMessage);

	public abstract Message modifyMessge(Message paramMessage);

	public abstract void deleteReceiveMessage(Long[] paramArrayOfLong);

	public abstract void deleteSentMessage(Long[] paramArrayOfLong);

	public abstract void setReadMessage(Long[] paramArrayOfLong);

	public abstract void setUnreadMessage(Long[] paramArrayOfLong);

	public abstract int getUnreadMesage(long paramLong);
}