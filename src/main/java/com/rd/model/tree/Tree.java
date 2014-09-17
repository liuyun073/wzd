package com.rd.model.tree;

import java.util.List;

public abstract interface Tree<T> {
	public abstract boolean hasChild();

	public abstract List<Tree<T>> getChild();

	public abstract Tree<T> getParent();

	public abstract T getModel();

	public abstract void addChild(Tree<T> paramTree);
}