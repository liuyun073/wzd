package com.rd.service;

import com.rd.domain.TroubleAwardRecord;
import com.rd.domain.TroubleDonateRecord;
import com.rd.domain.TroubleFundDonateRecord;
import com.rd.model.PageDataList;

public abstract interface TroubleFundService {
	public abstract TroubleFundDonateRecord troubleFund(
			TroubleFundDonateRecord paramTroubleFundDonateRecord,
			String paramString, double paramDouble);

	public abstract PageDataList getTroubleFundList(
			TroubleFundDonateRecord paramTroubleFundDonateRecord,
			double paramDouble, int paramInt);

	public abstract TroubleDonateRecord troubleDonate(
			TroubleFundDonateRecord paramTroubleFundDonateRecord,
			double paramDouble);

	public abstract double getTroubleSum(long paramLong);

	public abstract TroubleDonateRecord addTroubleDonate(
			TroubleDonateRecord paramTroubleDonateRecord);

	public abstract TroubleAwardRecord troubleAward(
			TroubleFundDonateRecord paramTroubleFundDonateRecord,
			double paramDouble1, long paramLong, double paramDouble2);

	public abstract PageDataList getTroubleDonateList(long paramLong,
			int paramInt);

	public abstract TroubleDonateRecord getTroubleDonateById(int paramInt);

	public abstract TroubleDonateRecord updateTroubleDonate(
			TroubleDonateRecord paramTroubleDonateRecord);
}