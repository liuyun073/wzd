package com.rd.model.borrow.protocol;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.rd.context.Global;
import com.rd.domain.Borrow;
import com.rd.domain.Site;
import com.rd.domain.User;
import com.rd.service.ArticleService;
import com.rd.tool.itext.PdfHelper;
import java.io.IOException;
import java.net.MalformedURLException;

public class XdcfBorrowProtocol extends BorrowProtocol {
	public XdcfBorrowProtocol(User user, long borrow_id, long tender_id) {
		super(user, borrow_id, tender_id);
	}

	protected int addPdfContent(PdfHelper pdf, Borrow b)
			throws DocumentException {
		int size = 0;
		Site site = getArticleService().getSiteById(32L);
		String content = site.getContent();
		pdf.addText(content);
		try {
			Image image = Image.getInstance(Global.getValue("weburl")
					+ Global.getValue("theme_dir") + "/images/zhang.jpg");
			pdf.addImage(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		size = pdf.getPageNumber();
		return size;
	}
}