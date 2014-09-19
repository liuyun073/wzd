package com.liuyun.site.dao;

import com.liuyun.site.domain.Protocol;
import com.liuyun.site.model.ProtocolModel;
import com.liuyun.site.model.SearchParam;
import java.util.List;

public abstract interface ProtocolDao {
	public abstract void addProtocol(Protocol paramProtocol);

	public abstract int getProtocolCount(SearchParam paramSearchParam);

	public abstract List<ProtocolModel> getProtocolList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<ProtocolModel> getProtocolList(SearchParam paramSearchParam);

	public abstract ProtocolModel getProtocolById(long paramLong);
}