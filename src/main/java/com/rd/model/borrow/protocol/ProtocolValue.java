package com.rd.model.borrow.protocol;

import com.itextpdf.text.DocumentException;
import com.rd.util.ReflectUtils;
import com.rd.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

public class ProtocolValue {
	private Map<String, Object> data = new HashMap();
	private BorrowProtocol protocol;
	private final String NULL = "";

	public Object printProtocol(String var) {
		Object ret = "";
		String[] tVars = var.split("\\.");
		if (tVars == null)
			return ret;
		if (tVars.length == 1) {
			ret = this.data.get(tVars[0]);
		} else if (tVars.length == 2) {
			ret = this.data.get(tVars[0]);
			if (ret == null)
				return "";
			if (tVars[0].equals("tenderTable"))
				try {
					this.protocol.addPdfTable(this.protocol.getPdf(),
							this.protocol.getBorrow());
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			else {
				ret = ReflectUtils.invokeGetMethod(ret.getClass(), ret,
						tVars[1]);
			}
		}
		return StringUtils.isNull(ret);
	}

	public Map<String, Object> getData() {
		return this.data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}