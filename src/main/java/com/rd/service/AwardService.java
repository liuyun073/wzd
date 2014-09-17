package com.rd.service;

import com.rd.domain.ObjAward;
import com.rd.domain.RuleAward;
import com.rd.domain.User;
import com.rd.domain.UserAward;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.model.award.AwardResult;
import java.util.List;

public abstract interface AwardService {
	public abstract AwardResult award(long paramLong, User paramUser,
			double paramDouble);

	public abstract RuleAward getRuleAwardByRuleId(long paramLong);

	public abstract List<RuleAward> getRuleAwardList();

	public abstract void updateRuleAward(RuleAward paramRuleAward);

	public abstract void addRuleAward(RuleAward paramRuleAward);

	public abstract List<UserAward> getAwardeeList(long paramLong,
			int paramInt, boolean paramBoolean);

	public abstract void updateTotalMoney(long paramLong, double paramDouble);

	public abstract PageDataList getUserAwardList(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<ObjAward> getObjectAwardListByRuleId(long paramLong);

	public abstract void addObjAward(ObjAward paramObjAward);

	public abstract void updateObjAward(ObjAward paramObjAward);

	public abstract ObjAward getObjectAwardById(long paramLong);

	public abstract long getRuleIdByAwardType(int paramInt);

	public abstract RuleAward getRuleAwardByAwardType(int paramInt);

	public abstract List<UserAward> getAllUserAwardList(
			SearchParam paramSearchParam);

	public abstract List<UserAward> getMyAwardList(long paramLong1,
			long paramLong2);
}