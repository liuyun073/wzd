package com.liuyun.site.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelHelper {
	public static final String UID = "serialVersionUID";

	public static void writeExcel(String file, List list, Class clazz)
			throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		List flist = new ArrayList();
		for (int i = 0; i < fields.length; ++i) {
			if (fields[i].getName().equals("serialVersionUID")) {
				continue;
			}
			flist.add(fields[i].getName());
		}
		writeExcel(file, list, clazz, flist, null);
	}

	public static void writeExcel(String file, List list, Class clazz,
			List<String> fields, List<String> titles) throws Exception {
		OutputStream os = getOutputStream(file);
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet ws = wwb.createSheet("Sheet1", 0);
		Label label = null;
		int start = 0;
		if ((titles != null) && (titles.size() > 0)) {
			for (int j = 0; j < titles.size(); ++j) {
				label = new Label(j, 0, (String) titles.get(j));
				ws.addCell(label);
			}
			++start;
		}
		for (int i = start; i < list.size() + start; ++i) {
			Object o = list.get(i - start);
			if (o == null) {
				continue;
			}
			for (int j = 0; j < fields.size(); ++j) {
				String value = "";
				if (fields.equals("serialVersionUID"))
					continue;
				try {
					value = ReflectUtils.invokeGetMethod(clazz, o,
							(String) fields.get(j)).toString();
				} catch (Exception localException) {
				}
				if ((fields.get(j) != null)
						&& (((String) fields.get(j)).equals("addtime"))) {
					value = DateUtils.dateStr4(value);
				}
				label = new Label(j, i, value);
				ws.addCell(label);
			}
		}
		wwb.write();
		wwb.close();
	}

	public static OutputStream getOutputStream(String file) throws Exception {
		File f = new File(file);
		f.createNewFile();
		OutputStream os = new FileOutputStream(f);
		return os;
	}
}