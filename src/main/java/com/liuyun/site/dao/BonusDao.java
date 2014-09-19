package com.liuyun.site.dao;

import com.liuyun.site.domain.BonusApr;
import com.liuyun.site.domain.Borrow;
import java.util.List;

public abstract interface BonusDao {
	public abstract void addBonusApr(Borrow paramBorrow);

	public abstract void modifyBonusAprById(long paramLong, double paramDouble);

	public abstract List<BonusApr> getBonusAprList(long paramLong);

	public abstract BonusApr getBonus(long paramLong);
}