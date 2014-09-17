package com.rd.dao;

import com.rd.domain.ScrollPic;
import java.util.List;

public abstract interface ScrollPicDao {
	public abstract List<ScrollPic> getList(int paramInt1, int paramInt2);

	public abstract ScrollPic getScrollPicById(long paramLong);

	public abstract void delScrollPic(long paramLong);

	public abstract void modifyScrollPic(ScrollPic paramScrollPic);

	public abstract ScrollPic addScrollPic(ScrollPic paramScrollPic);
}