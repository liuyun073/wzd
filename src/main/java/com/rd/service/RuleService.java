package com.rd.service;

import com.rd.domain.Rule;
import java.util.List;

public abstract interface RuleService {
	public abstract Rule getRuleById(Long paramLong);

	public abstract Rule getRuleByNid(String paramString);

	public abstract List<Rule> getRuleList(int paramInt);
}