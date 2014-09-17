package com.rd.test.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class PdfTest {
	private String mPdfPath;

	public void setPdfPath(String pdfPath) {
		this.mPdfPath = pdfPath;
	}

	public boolean createPDF() {
		Document document = new Document();
		document.setPageSize(PageSize.A4);
		try {
			PdfWriter.getInstance(document, new FileOutputStream(this.mPdfPath
					+ "taony125-test.pdf"));
			document.open();
			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", false);
			Font headFont = new Font(bfChinese, 10.0F, 1);
			Font headFont1 = new Font(bfChinese, 8.0F, 1);
			Font headFont2 = new Font(bfChinese, 10.0F, 0);
			float[] widths = { 80.0F, 80.0F, 80.0F, 80.0F, 80.0F, 80.0F, 80.0F,
					80.0F };
			PdfPTable table = new PdfPTable(widths);
			table.setSpacingBefore(130.0F);
			table.setTotalWidth(560.0F);
			table.setLockedWidth(true);

			PdfPCell cell = new PdfPCell(new Paragraph("Taony125 testPdf 中文字体",
					headFont));
			for (int i = 0; i < 20; ++i) {
				cell.setColspan(8);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("test" + i, headFont));
				cell.setFixedHeight(20.0F);
				cell.setColspan(8);
				cell.setHorizontalAlignment(1);
				cell.setVerticalAlignment(5);
				table.addCell(cell);
			}

			document.add(table);
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
			return false;
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
			return false;
		}
		document.close();
		return true;
	}

	public static void main(String[] args) {
		PdfTest mCreatPDF = new PdfTest();
		mCreatPDF.setPdfPath("D:/");
		mCreatPDF.createPDF();
	}
}