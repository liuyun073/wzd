package com.liuyun.site.service;

import com.liuyun.site.domain.Rule;
import java.util.List;

public abstract interface RuleService {
	public abstract Rule getRuleById(Long paramLong);

	public abstract Rule getRuleByNid(String paramString);

	public abstract List<Rule> getRuleList(int paramInt);
}