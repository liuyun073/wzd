package com.rd.pdf;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.itextpdf.text.DocumentException;
import com.rd.tool.itext.PdfHelper;

public class Pdf {
	
	@Test
	public void create() {
		PdfHelper pdf = PdfHelper.instance("D:/test.pdf");
		try {
			System.out.println("Start...");
			pdf.addText("的分撒旦法");
			List list = new ArrayList();
			for (int i = 0; i < 5; ++i) {
				list.add(i);
			}
			List[] data = new List[5];
			for (int i = 0; i < 5; ++i) {
				data[i] = list;
			}

			pdf.addTable(data, 50.0F, 8);
			pdf.exportPdf();
			System.out.println("End...");
			//i = null;
		} catch (DocumentException localDocumentException) {
		}
	}
}