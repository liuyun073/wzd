package com.rd.model.borrow.protocol;

import com.itextpdf.text.DocumentException;
import com.rd.context.Global;
import com.rd.domain.Borrow;
import com.rd.domain.User;
import com.rd.model.BorrowTender;
import com.rd.model.borrow.BorrowHelper;
import com.rd.tool.itext.PdfHelper;
import com.rd.util.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseBorrowProtocol extends BorrowProtocol {
	public BaseBorrowProtocol(User user, long borrow_id, long tender_id,
			int borrowType, int templateType) {
		super(user, borrow_id, tender_id, borrowType, templateType);
	}

	public BaseBorrowProtocol(User user, long borrow_id, long tender_id) {
		super(user, borrow_id, tender_id);
	}

	protected void addPdfTable(PdfHelper pdf, Borrow b)
			throws DocumentException {
		List<BorrowTender> list = new ArrayList<BorrowTender>();
		if ((b.getIs_flow() == 1) && (((Global.getWebid().equals("zbzr")) || (Global.getWebid().equals("mdw"))))) {
			User user = getTenderUser();
			list = getBorrowService().getTenderList(getBorrow().getId(), (user == null) ? 0L : user.getUser_id());
		} else {
			list = getBorrowService().getTenderList(getBorrow().getId());
		}
		List<String> cellList = null;
		List[] args = new List[list.size() + 1];

		cellList = new ArrayList<String>();
		cellList.add("出借人(id)");
		cellList.add("借款金额");
		cellList.add("借款期限");
		cellList.add("年利率");
		cellList.add("借款开始日");
		cellList.add("借款到期日");
		cellList.add("截止还款日");
		cellList.add("还款本息");
		args[0] = cellList;
		for (int i = 1; i < list.size() + 1; ++i) {
			BorrowTender t = (BorrowTender) list.get(i - 1);
			cellList = new ArrayList<String>();
			cellList.add(t.getUsername());
			cellList.add(t.getAccount());
			cellList.add(b.getTime_limit());
			cellList.add("" + b.getApr());
			cellList.add(DateUtils.dateStr2(BorrowHelper.getBorrowVerifyTime(b, t)));
			Date d = BorrowHelper.getBorrowRepayTime(b, t);
			cellList.add(DateUtils.dateStr2(d));
			cellList.add("每月截止" + DateUtils.getDay(d) + "日");
			cellList.add(t.getRepayment_account());
			args[i] = cellList;
		}
		pdf.addTable(args, 80.0F, 7);
	}
}