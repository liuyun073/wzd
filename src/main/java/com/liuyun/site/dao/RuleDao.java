package com.liuyun.site.dao;

import com.liuyun.site.domain.Rule;
import java.util.List;

public abstract interface RuleDao {
	public abstract Rule getRuleById(Long paramLong);

	public abstract Rule getRuleByNid(String paramString);

	public abstract List<Rule> getRuleList(int paramInt);
}