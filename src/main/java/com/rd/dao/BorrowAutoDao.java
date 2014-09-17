package com.rd.dao;

import com.rd.domain.AutoTenderOrder;
import com.rd.domain.BorrowAuto;
import java.util.List;

public abstract interface BorrowAutoDao {
	public abstract List<BorrowAuto> getBorrowAutoList(long paramLong);

	public abstract void addBorrowAuto(BorrowAuto paramBorrowAuto);

	public abstract void updateBorrowAuto(BorrowAuto paramBorrowAuto);

	public abstract void deleteBorrowAuto(long paramLong);

	public abstract void insertAutoTender();

	public abstract List<BorrowAuto> getBorrowAutoListByStatus(long paramLong);

	public abstract void updateAutoTenderOrder(
			AutoTenderOrder paramAutoTenderOrder);

	public abstract AutoTenderOrder getAutoTenderOrder(long paramLong);

	public abstract List<AutoTenderOrder> getAutoTenderOrder();

	public abstract int getAutoTenderOrderCount();
}