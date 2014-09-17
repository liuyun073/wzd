package com.rd.tool.itext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.log4j.Logger;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.rd.context.Global;
import com.rd.util.StringUtils;

public class PdfHelper {
	private Logger logger = Logger.getLogger(PdfHelper.class);
	private Document document;
	private BaseFont bfChinese;
	private Font font;
	private String webid = Global.getValue("webid");

	public String getWebid() {
		return this.webid;
	}

	public void setWebid(String webid) {
		this.webid = webid;
	}

	public PdfHelper(String path) {
		this.document = new Document();
		try {
			PdfWriter.getInstance(this.document, new FileOutputStream(path));
			this.document.open();
			this.bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", false);
			this.font = new Font(this.bfChinese, 10.0F, 0);
		} catch (DocumentException localDocumentException) {
		} catch (IOException localIOException) {
		}
	}

	public int getPageNumber() {
		if (this.document == null) {
			return 0;
		}
		return this.document.getPageNumber();
	}

	public static PdfHelper instance(String path) {
		return new PdfHelper(path);
	}

	public void addTable(List<String>[] data, float cellWidth, int num)
			throws DocumentException {
		float[] widths = { cellWidth, cellWidth, cellWidth, cellWidth,
				cellWidth, cellWidth, cellWidth, cellWidth };
		PdfPTable table = new PdfPTable(widths);
		table.setSpacingBefore(10.0F);
		table.setSpacingBefore(10.0F);
		table.setTotalWidth(cellWidth * num);
		table.setLockedWidth(true);
		PdfPCell cell = null;
		for (int i = 0; i < data.length; ++i) {
			for (String s : data[i]) {
				cell = new PdfPCell(new Paragraph(s, this.font));
				table.addCell(cell);
			}
		}
		this.document.add(table);
	}

	public void addTitle(String text) throws DocumentException {
		this.font = new Font(this.bfChinese, 12.0F, 1);
		Paragraph p = new Paragraph(text, this.font);
		String webid = Global.getValue("webid");
		if ((webid != null) && (webid.equals("mdw")))
			p.setAlignment(2);
		else {
			p.setAlignment(1);
		}
		p.setSpacingAfter(20.0F);
		this.document.add(p);
	}

	public void addText(String text) throws DocumentException {
		Paragraph p = new Paragraph(text, this.font);
		p.setSpacingAfter(10.0F);
		this.document.add(p);
	}

	public void addHtml(String html) throws Exception {
		StyleSheet st = new StyleSheet();
		List list = HTMLWorker.parseToList(new StringReader(html), st);
		for (int k = 0; k < list.size(); ++k)
			this.document.add((Element) list.get(k));
	}

	public void exportPdf() {
		this.document.close();
	}

	public void freemarker() {
	}

	public static void addPdfMark(String InPdfFile, String outPdfFile,
			String markImagePath, int size) throws Exception {
		PdfReader reader = new PdfReader(InPdfFile);
		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
				outPdfFile));
		Image img = Image.getInstance(markImagePath);
		img.setAbsolutePosition(15.0F, 10.0F);
		for (int i = 1; i <= size; ++i) {
			PdfContentByte under = stamp.getUnderContent(i);
			under.addImage(img);
		}
		stamp.close();
		File tempfile = new File(InPdfFile);
		if (tempfile.exists())
			tempfile.delete();
	}

	public void addImage(Image image) {
		try {
			image.setAlignment(0);
			image.setAlignment(8);
			if (StringUtils.isNull(this.webid).equals("xdcf")) {
				image.setAbsolutePosition(10.0F, 60.0F);
				image.scaleAbsolute(360.0F, 310.0F);
			} else if (StringUtils.isNull(this.webid).equals("jsy")) {
				image.setAbsolutePosition(40.0F, 10.0F);
				image.scaleAbsolute(500.0F, 115.0F);
			} else {
				image.setAbsolutePosition(50.0F, 550.0F);
				image.scaleAbsolute(210.0F, 210.0F);
			}

			this.document.add(image);
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public void addHtmlList(List<Element> list) throws DocumentException {
		for (Element e : list)
			this.document.add(e);
	}
}