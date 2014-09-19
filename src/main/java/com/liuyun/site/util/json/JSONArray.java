package com.liuyun.site.util.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class JSONArray {
	private ArrayList<Object> myArrayList;

	public JSONArray() {
		this.myArrayList = new ArrayList<Object>();
	}

	public JSONArray(JSONTokener x) throws JSONException {
		if (x.nextClean() != '[') {
			throw x.syntaxError("A JSONArray text must start with '['");
		}
		if (x.nextClean() != ']') {
			x.back();
			while (true) {
				if (x.nextClean() == ',') {
					x.back();
					this.myArrayList.add(JSONObject.NULL);
				} else {
					x.back();
					this.myArrayList.add(x.nextValue());
				}
				switch (x.nextClean()) {
				case ',':
				case ';':
					if (x.nextClean() == ']') {
						return;
					}
					x.back();
				case ']':
					
				}
			}
			// return;

			//throw x.syntaxError("Expected a ',' or ']'");
		}
	}

	public JSONArray(String source) throws JSONException {
		this(new JSONTokener(source));
	}

	public JSONArray(Collection<Object> collection) {
		this.myArrayList = new ArrayList<Object>();
		if (collection != null) {
			Iterator<Object> iter = collection.iterator();
			while (iter.hasNext())
				this.myArrayList.add(JSONObject.wrap(iter.next()));
		}
	}

	public JSONArray(Object array) throws JSONException {
		if (array.getClass().isArray()) {
			int length = Array.getLength(array);
			for (int i = 0; i < length; ++i)
				put(JSONObject.wrap(Array.get(array, i)));
		} else {
			throw new JSONException(
					"JSONArray initial value should be a string or collection or array.");
		}
	}

	public Object get(int index) throws JSONException {
		Object object = opt(index);
		if (object == null) {
			throw new JSONException("JSONArray[" + index + "] not found.");
		}
		return object;
	}

	public boolean getBoolean(int index) throws JSONException {
		Object object = get(index);
		if ((object.equals(Boolean.FALSE))
				|| ((object instanceof String) && (((String) object)
						.equalsIgnoreCase("false"))))
			return false;
		if ((object.equals(Boolean.TRUE))
				|| ((object instanceof String) && (((String) object)
						.equalsIgnoreCase("true")))) {
			return true;
		}
		throw new JSONException("JSONArray[" + index + "] is not a boolean.");
	}

	public double getDouble(int index) throws JSONException {
		Object object = get(index);
		try {
			return (object instanceof Number) ? ((Number) object).doubleValue()
					: Double.parseDouble((String) object);
		} catch (Exception e) {
			throw new JSONException("JSONArray[" + index + "] is not a number.");
		}
	}

	public int getInt(int index) throws JSONException {
		Object object = get(index);
		try {
			return (object instanceof Number) ? ((Number) object).intValue()
					: Integer.parseInt((String) object);
		} catch (Exception e) {
			throw new JSONException("JSONArray[" + index + "] is not a number.");
		}
	}

	public JSONArray getJSONArray(int index) throws JSONException {
		Object object = get(index);
		if (object instanceof JSONArray) {
			return (JSONArray) object;
		}
		throw new JSONException("JSONArray[" + index + "] is not a JSONArray.");
	}

	public JSONObject getJSONObject(int index) throws JSONException {
		Object object = get(index);
		if (object instanceof JSONObject) {
			return (JSONObject) object;
		}
		throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
	}

	public long getLong(int index) throws JSONException {
		Object object = get(index);
		try {
			return (object instanceof Number) ? ((Number) object).longValue()
					: Long.parseLong((String) object);
		} catch (Exception e) {
			throw new JSONException("JSONArray[" + index + "] is not a number.");
		}
	}

	public String getString(int index) throws JSONException {
		Object object = get(index);
		if (object instanceof String) {
			return (String) object;
		}
		throw new JSONException("JSONArray[" + index + "] not a string.");
	}

	public boolean isNull(int index) {
		return JSONObject.NULL.equals(opt(index));
	}

	public String join(String separator) throws JSONException {
		int len = length();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < len; ++i) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(JSONObject.valueToString(this.myArrayList.get(i)));
		}
		return sb.toString();
	}

	public int length() {
		return this.myArrayList.size();
	}

	public Object opt(int index) {
		return ((index < 0) || (index >= length())) ? null : this.myArrayList
				.get(index);
	}

	public boolean optBoolean(int index) {
		return optBoolean(index, false);
	}

	public boolean optBoolean(int index, boolean defaultValue) {
		try {
			return getBoolean(index);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public double optDouble(int index) {
		return optDouble(index, (0.0D / 0.0D));
	}

	public double optDouble(int index, double defaultValue) {
		try {
			return getDouble(index);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public int optInt(int index) {
		return optInt(index, 0);
	}

	public int optInt(int index, int defaultValue) {
		try {
			return getInt(index);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public JSONArray optJSONArray(int index) {
		Object o = opt(index);
		return (o instanceof JSONArray) ? (JSONArray) o : null;
	}

	public JSONObject optJSONObject(int index) {
		Object o = opt(index);
		return (o instanceof JSONObject) ? (JSONObject) o : null;
	}

	public long optLong(int index) {
		return optLong(index, 0L);
	}

	public long optLong(int index, long defaultValue) {
		try {
			return getLong(index);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public String optString(int index) {
		return optString(index, "");
	}

	public String optString(int index, String defaultValue) {
		Object object = opt(index);
		return (JSONObject.NULL.equals(object)) ? defaultValue : object
				.toString();
	}

	public JSONArray put(boolean value) {
		put((value) ? Boolean.TRUE : Boolean.FALSE);
		return this;
	}

	public JSONArray put(Collection<Object> value) {
		put(new JSONArray(value));
		return this;
	}

	public JSONArray put(double value) throws JSONException {
		Double d = new Double(value);
		JSONObject.testValidity(d);
		put(d);
		return this;
	}

	public JSONArray put(int value) {
		put(new Integer(value));
		return this;
	}

	public JSONArray put(long value) {
		put(new Long(value));
		return this;
	}

	public JSONArray put(Map value) {
		put(new JSONObject(value));
		return this;
	}

	public JSONArray put(Object value) {
		this.myArrayList.add(value);
		return this;
	}

	public JSONArray put(int index, boolean value) throws JSONException {
		put(index, (value) ? Boolean.TRUE : Boolean.FALSE);
		return this;
	}

	public JSONArray put(int index, Collection value) throws JSONException {
		put(index, new JSONArray(value));
		return this;
	}

	public JSONArray put(int index, double value) throws JSONException {
		put(index, new Double(value));
		return this;
	}

	public JSONArray put(int index, int value) throws JSONException {
		put(index, new Integer(value));
		return this;
	}

	public JSONArray put(int index, long value) throws JSONException {
		put(index, new Long(value));
		return this;
	}

	public JSONArray put(int index, Map value) throws JSONException {
		put(index, new JSONObject(value));
		return this;
	}

	public JSONArray put(int index, Object value) throws JSONException {
		JSONObject.testValidity(value);
		if (index < 0) {
			throw new JSONException("JSONArray[" + index + "] not found.");
		}
		if (index < length()) {
			this.myArrayList.set(index, value);
		} else {
			do
				put(JSONObject.NULL);
			while (index != length());

			put(value);
		}
		return this;
	}

	public Object remove(int index) {
		Object o = opt(index);
		this.myArrayList.remove(index);
		return o;
	}

	public JSONObject toJSONObject(JSONArray names) throws JSONException {
		if ((names == null) || (names.length() == 0) || (length() == 0)) {
			return null;
		}
		JSONObject jo = new JSONObject();
		for (int i = 0; i < names.length(); ++i) {
			jo.put(names.getString(i), opt(i));
		}
		return jo;
	}

	public String toString() {
		try {
			return '[' + join(",") + ']';
		} catch (Exception e) {
		}
		return null;
	}

	public String toString(int indentFactor) throws JSONException {
		return toString(indentFactor, 0);
	}

	String toString(int indentFactor, int indent) throws JSONException {
		int len = length();
		if (len == 0) {
			return "[]";
		}

		StringBuffer sb = new StringBuffer("[");
		if (len == 1) {
			sb.append(JSONObject.valueToString(this.myArrayList.get(0),
					indentFactor, indent));
		} else {
			int newindent = indent + indentFactor;
			sb.append('\n');
			for (int i = 0; i < len; ++i) {
				if (i > 0) {
					sb.append(",\n");
				}
				for (int j = 0; j < newindent; ++j) {
					sb.append(' ');
				}
				sb.append(JSONObject.valueToString(this.myArrayList.get(i),
						indentFactor, newindent));
			}
			sb.append('\n');
			for (int i = 0; i < indent; ++i) {
				sb.append(' ');
			}
		}
		sb.append(']');
		return sb.toString();
	}

	public Writer write(Writer writer) throws JSONException {
		try {
			boolean b = false;
			int len = length();

			writer.write(91);

			for (int i = 0; i < len; ++i) {
				if (b) {
					writer.write(44);
				}
				Object v = this.myArrayList.get(i);
				if (v instanceof JSONObject)
					((JSONObject) v).write(writer);
				else if (v instanceof JSONArray)
					((JSONArray) v).write(writer);
				else {
					writer.write(JSONObject.valueToString(v));
				}
				b = true;
			}
			writer.write(93);
			return writer;
		} catch (IOException e) {
			throw new JSONException(e);
		}
	}
}