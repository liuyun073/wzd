package com.liuyun.site.dao;

import com.liuyun.site.domain.TroubleAwardRecord;
import com.liuyun.site.domain.TroubleDonateRecord;
import com.liuyun.site.domain.TroubleFundDonateRecord;
import com.liuyun.site.model.TroubleAwardModel;
import com.liuyun.site.model.TroubleDonateModel;
import com.liuyun.site.model.TroubleFundModel;

import java.util.List;

public abstract interface TroubleFundDao {
	public abstract TroubleFundDonateRecord addTroubleFund(
			TroubleFundDonateRecord paramTroubleFundDonateRecord);

	public abstract List<TroubleFundModel> getTroubleFund(
			TroubleFundDonateRecord paramTroubleFundDonateRecord,
			double paramDouble, int paramInt1, int paramInt2);

	public abstract TroubleAwardRecord addTroubleAward(
			TroubleAwardRecord paramTroubleAwardRecord);

	public abstract TroubleDonateRecord addTroubleDonate(
			TroubleDonateRecord paramTroubleDonateRecord);

	public abstract List<TroubleAwardModel> getTroubleAwardList(long paramLong, int paramInt1,
			int paramInt2);

	public abstract List<TroubleDonateModel> getTroubleDonateList(long paramLong, int paramInt1,
			int paramInt2);

	public abstract double getTroubleAwardSum(long paramLong);

	public abstract double getTroubleDonateSum(long paramLong);

	public abstract int getTroubleAwardCount(long paramLong);

	public abstract int getTroubleDonateCount(long paramLong);

	public abstract int getTroubleFundCount(
			TroubleFundDonateRecord paramTroubleFundDonateRecord);

	public abstract TroubleDonateRecord getTroubleDonateById(int paramInt);

	public abstract TroubleDonateRecord updateTroubleDonate(
			TroubleDonateRecord paramTroubleDonateRecord);
}