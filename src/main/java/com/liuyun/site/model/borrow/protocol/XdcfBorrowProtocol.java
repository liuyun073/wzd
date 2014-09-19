package com.liuyun.site.model.borrow.protocol;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.liuyun.site.context.Global;
import com.liuyun.site.domain.Borrow;
import com.liuyun.site.domain.Site;
import com.liuyun.site.domain.User;
import com.liuyun.site.service.ArticleService;
import com.liuyun.site.tool.itext.PdfHelper;
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