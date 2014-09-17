package com.rd.dao;

import com.rd.domain.TroubleAwardRecord;
import com.rd.domain.TroubleDonateRecord;
import com.rd.domain.TroubleFundDonateRecord;
import com.rd.model.TroubleAwardModel;
import com.rd.model.TroubleDonateModel;
import com.rd.model.TroubleFundModel;

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