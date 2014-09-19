package com.liuyun.site.model.purview;

import com.liuyun.site.domain.Purview;
import com.liuyun.site.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PurviewSet {
	private List<Purview> purviewList;
	private Purview model;
	private Set<PurviewSet> childs;

	public PurviewSet(List<Purview> purviewList) {
		this.purviewList = purviewList;
	}

	public PurviewSet(Purview model, Set<PurviewSet> childs) {
		this.model = model;
		this.childs = childs;
	}

	public PurviewSet() {
	}

	public Purview getModel() {
		return this.model;
	}

	public void setModel(Purview model) {
		this.model = model;
	}

	public Set<PurviewSet> getChilds() {
		return this.childs;
	}

	public void setChilds(Set<PurviewSet> childs) {
		this.childs = childs;
	}

	public boolean hasList() {
		return (this.purviewList != null) && (this.purviewList.size() >= 1);
	}

	public boolean contains(String url) {
		if ((!hasList()) || (StringUtils.isBlank(url))) {
			return false;
		}
		for (Purview p : this.purviewList) {
			if (StringUtils.isNull(p.getUrl()).equals(url)) {
				return true;
			}
		}
		return false;
	}

	public Set<PurviewSet> toSet() {
		this.childs = new TreeSet<PurviewSet>(new PurviewLevelCompare());
		List<Purview> firstList = new ArrayList<Purview>();
		List<Purview> secList = new ArrayList<Purview>();
		List<Purview> thirdList = new ArrayList<Purview>();

		if (this.purviewList == null)
			return this.childs;

		for (Purview p : this.purviewList) {
			switch (p.getLevel()) {
			case 1:
				firstList.add(p);
				break;
			case 2:
				secList.add(p);
				break;
			case 3:
				thirdList.add(p);
			}
		}

		PurviewSet ps = null;

		for (Purview p1 : firstList) {
			Set<PurviewSet> set1 = new TreeSet<PurviewSet>(new PurviewLevelCompare());
			for (Purview p2 : secList) {
				if (p2.getPid() == p1.getId()) {
					Set<PurviewSet> set2 = new TreeSet<PurviewSet>(new PurviewLevelCompare());
					for (Purview p3 : thirdList) {
						if (p3.getPid() == p2.getId()) {
							PurviewSet ps3 = new PurviewSet(p3, null);
							set2.add(ps3);
						}
					}
					PurviewSet ps2 = new PurviewSet(p2, set2);
					set1.add(ps2);
				}
			}
			ps = new PurviewSet(p1, set1);
			this.childs.add(ps);
		}

		return this.childs;
	}
}