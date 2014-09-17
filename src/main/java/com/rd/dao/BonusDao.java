package com.rd.dao;

import com.rd.domain.BonusApr;
import com.rd.domain.Borrow;
import java.util.List;

public abstract interface BonusDao {
	public abstract void addBonusApr(Borrow paramBorrow);

	public abstract void modifyBonusAprById(long paramLong, double paramDouble);

	public abstract List<BonusApr> getBonusAprList(long paramLong);

	public abstract BonusApr getBonus(long paramLong);
}