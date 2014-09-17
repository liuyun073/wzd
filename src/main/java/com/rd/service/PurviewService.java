package com.rd.service;

import java.util.List;

public abstract interface PurviewService {
	public abstract List getPurvidewById(long paramLong);

	public abstract List getPurviewByUserId(long paramLong);
}