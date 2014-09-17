package com.rd.util;

import java.lang.reflect.Method;

public class ReflectUtils {
	public static Object invokeGetMethod(Class claszz, Object o, String name) {
		Object ret = null;
		try {
			Method method = claszz.getMethod("get"
					+ StringUtils.firstCharUpperCase(name), new Class[0]);
			ret = method.invoke(o, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static Object invokeSetMethod(Class claszz, Object o, String name,
			Class[] argTypes, Object[] args) {
		Object ret = null;
		try {
			Method method = claszz.getMethod("set"
					+ StringUtils.firstCharUpperCase(name), argTypes);
			ret = method.invoke(o, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static Object invokeSetMethod(Class claszz, Object o, String name,
			Class argType, Object args) {
		Object ret = null;
		try {
			Method method = claszz.getMethod("set"
					+ StringUtils.firstCharUpperCase(name),
					new Class[] { argType });
			ret = method.invoke(o, new Object[] { argType });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}