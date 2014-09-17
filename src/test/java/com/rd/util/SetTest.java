package com.rd.util;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class SetTest {
	public static void main(String[] args) {
		Set set = new HashSet();
		boolean s1 = set.add("1");
		System.out.println(s1);
		boolean s2 = set.add("1");
		System.out.println(s2);
		set.remove(null);
	}
}