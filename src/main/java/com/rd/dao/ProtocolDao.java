package com.rd.dao;

import com.rd.domain.Protocol;
import com.rd.model.ProtocolModel;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface ProtocolDao {
	public abstract void addProtocol(Protocol paramProtocol);

	public abstract int getProtocolCount(SearchParam paramSearchParam);

	public abstract List<ProtocolModel> getProtocolList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<ProtocolModel> getProtocolList(SearchParam paramSearchParam);

	public abstract ProtocolModel getProtocolById(long paramLong);
}