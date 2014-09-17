package com.rd.dao;

import com.rd.domain.Rule;
import java.util.List;

public abstract interface RuleDao {
	public abstract Rule getRuleById(Long paramLong);

	public abstract Rule getRuleByNid(String paramString);

	public abstract List<Rule> getRuleList(int paramInt);
}