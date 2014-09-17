package com.rd.model.purview;

import java.util.Comparator;

public class PurviewLevelCompare implements Comparator<PurviewSet> {
	public int compare(PurviewSet o1, PurviewSet o2) {
		int result = (o1.getModel().getId() == o2.getModel().getId()) ? 0 : (o1
				.getModel().getId() > o2.getModel().getId()) ? 1 : -1;
		return result;
	}
}