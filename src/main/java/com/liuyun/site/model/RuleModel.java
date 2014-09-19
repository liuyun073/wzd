package com.liuyun.site.model;

import com.alibaba.fastjson.JSON;
import com.liuyun.site.domain.Rule;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class RuleModel extends Rule implements Serializable {
	private static final long serialVersionUID = 8971537069152829541L;
	private Logger logger = Logger.getLogger(RuleModel.class);

	public RuleModel(Rule rule) {
		if (rule != null) {
			setId(rule.getId());
			setName(rule.getName());
			setStatus(rule.getStatus());
			setAddtime(rule.getAddtime());
			setNid(rule.getNid());
			setRemark(rule.getRemark());
			setRule_check(rule.getRule_check());
		}
	}

	public RuleModel() {
	}

	public Object getValueByKey(String key) {
		if (key == null)
			return null;
		Map<String, Object> map = (Map<String, Object>) JSON.parse(getRule_check());
		Object obj = map.get(key);
		return obj;
	}

	public List<Integer> getValueListByKey(String key) {
		Object obj = getValueByKey(key);
		List<Integer> list = null;
		try {
			list = (List<Integer>) obj;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return list;
	}

	public Double getValueDoubleByKey(String key) {
		return Double.valueOf(NumberUtils.getDouble(StringUtils.isNull(getValueByKey(key))));
	}

	public Integer getValueIntByKey(String key) {
		return Integer.valueOf(NumberUtils.getInt(StringUtils
				.isNull(getValueByKey(key))));
	}

	public String getValueStrByKey(String key) {
		return StringUtils.isNull(getValueByKey(key));
	}

	public static void main(String[] args) {
		RuleCheckModel t = new RuleCheckModel();
		List<Integer> list = new ArrayList<Integer>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));
		list.add(Integer.valueOf(1));
		t.setBorrow_type(list);

		String str = JSON.toJSONString(t);
		RuleModel r = new RuleModel();
		r.setRule_check(str);
		System.out.println(str);

		r.getValueListByKey("borrowTypeList");
	}
}