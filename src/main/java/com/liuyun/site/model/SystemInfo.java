package com.liuyun.site.model;

import com.liuyun.site.domain.SystemConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SystemInfo {
	private Map<String, SystemConfig> map;

	public SystemInfo() {
		this.map = Collections.synchronizedMap(new HashMap<String, SystemConfig>());
	}

	public void addConfig(SystemConfig sys) {
		this.map.put(sys.getNid().replace("con_", ""), sys);
	}

	private SystemConfig getConfig(String key) {
		SystemConfig sys = this.map.get(key);
		return sys;
	}

	public String getValue(String key) {
		SystemConfig c = getConfig(key);
		if (c == null)
			return null;
		return c.getValue();
	}

	public String getStatus(String key) {
		SystemConfig c = getConfig(key);
		if (c == null)
			return null;
		return getConfig(key).getStatus();
	}
}