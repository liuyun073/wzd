package com.liuyun.site.dao;

import com.liuyun.site.domain.Collection;
import com.liuyun.site.model.BorrowTender;
import com.liuyun.site.model.DetailCollection;
import com.liuyun.site.model.SearchParam;
import java.util.List;

public abstract interface CollectionDao {
	public abstract Collection addCollection(Collection paramCollection);

	public abstract void addBatchCollection(List<Collection> paramList);

	public abstract List<DetailCollection> getDetailCollectionList(long paramLong, int paramInt);

	public abstract List<DetailCollection> getCollectionList(long paramLong, int paramInt1,
			int paramInt2, int paramInt3, SearchParam paramSearchParam);

	public abstract int getCollectionListCount(long paramLong, int paramInt,
			SearchParam paramSearchParam);

	public abstract DetailCollection getCollection(long paramLong);

	public abstract void modifyCollectionBonus(int paramInt,
			double paramDouble, List<BorrowTender> paramList);

	public abstract List<DetailCollection> getCollectionLlistByBorrow(long paramLong,
			int paramInt1, int paramInt2, SearchParam paramSearchParam);

	public abstract List<DetailCollection> getCollectionLlistByBorrow(long paramLong);

	public abstract int getCollectionCountByBorrow(long paramLong,
			SearchParam paramSearchParam);

	public abstract double getCollectionInterestSum(long paramLong, int paramInt);

	public abstract int getUnFinishFlowCount(SearchParam paramSearchParam,
			String paramString);

	public abstract List<Collection> getUnFinishFlowList(SearchParam paramSearchParam,
			String paramString, int paramInt1, int paramInt2);

	public abstract List<Collection> getUnFinishFlowList(SearchParam paramSearchParam);

	public abstract List<Collection> getCollectionListByTender(long paramLong);

	public abstract void modifyBatchCollection(List<Collection> paramList);

	public abstract List<DetailCollection> getCollectionLlistByBorrow(long paramLong, int paramInt);

	public abstract void modifyCollection(Collection paramCollection);

	public abstract List<DetailCollection> getAllFlowCollectList(int paramInt);

	public abstract List<DetailCollection> getAllFlowCollectList(int paramInt1, int paramInt2);

	public abstract double getRepaymentAccount(int paramInt);
}