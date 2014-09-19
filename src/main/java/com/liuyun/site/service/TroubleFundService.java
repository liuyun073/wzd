package com.liuyun.site.service;

import com.liuyun.site.domain.TroubleAwardRecord;
import com.liuyun.site.domain.TroubleDonateRecord;
import com.liuyun.site.domain.TroubleFundDonateRecord;
import com.liuyun.site.model.PageDataList;

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