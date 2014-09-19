package com.liuyun.site.model.tree;

import com.liuyun.site.domain.Site;
import java.util.List;

public class SiteTree implements Tree<Site> {
	private Site model;
	private Tree<Site> parent;
	private List<Tree<Site>> child;

	public SiteTree() {
	}

	public SiteTree(Site model, List<Tree<Site>> child) {
		this.model = model;
		this.child = child;
	}

	public boolean hasChild() {
		return (this.child != null) && (this.child.size() > 0);
	}

	public List<Tree<Site>> getChild() {
		return this.child;
	}

	public Tree<Site> getParent() {
		return this.parent;
	}

	public Site getModel() {
		return this.model;
	}

	public void addChild(Tree<Site> t) {
		this.child.add(t);
	}
}