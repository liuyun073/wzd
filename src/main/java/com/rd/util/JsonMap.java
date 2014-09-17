package com.rd.util;

import java.util.HashMap;
import java.util.Map;

public class JsonMap {
	Map<String, Object> map = new HashMap<String, Object>();

	public Map<String, Object> getMap() {
		return this.map;
	}

	public void add(String key, Object o) {
		this.map.put(key, o);
	}
}