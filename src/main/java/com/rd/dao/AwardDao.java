package com.rd.dao;

import com.rd.domain.ObjAward;
import com.rd.domain.RuleAward;
import com.rd.domain.UserAward;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface AwardDao {
	public abstract RuleAward getRuleAwardById(long paramLong);

	public abstract long getRuleIdByAwardType(int paramInt);

	public abstract RuleAward getRuleAwardByAwardType(int paramInt);

	public abstract List<RuleAward> getRuleAwardList();

	public abstract void updateRuleAwardById(RuleAward paramRuleAward);

	public abstract void updateBestowMoney(long paramLong, double paramDouble);

	public abstract void updateTotalMoney(long paramLong, double paramDouble);

	public abstract void addRuleAward(RuleAward paramRuleAward);

	public abstract List<ObjAward> getObjectAwardListByRuleId(long paramLong);

	public abstract ObjAward getObjectAwardById(long paramLong);

	public abstract void updateObjAward(ObjAward paramObjAward);

	public abstract void updateBestow(long paramLong1, long paramLong2);

	public abstract void addObjAward(ObjAward paramObjAward);

	public abstract List<UserAward> getAwardeeList(long paramLong,
			int paramInt, boolean paramBoolean);

	public abstract List<UserAward> getMyAwardList(long paramLong1,
			long paramLong2);

	public abstract int getUserAwardDayCnt(long paramLong1, long paramLong2);

	public abstract int getUserAwardTotalCnt(long paramLong1, long paramLong2);

	public abstract void addUserAward(UserAward paramUserAward);

	public abstract List<UserAward> getUserAwardList(int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract int getUserAwardCount(SearchParam paramSearchParam);

	public abstract List<UserAward> getAllUserAwardList(
			SearchParam paramSearchParam);
}