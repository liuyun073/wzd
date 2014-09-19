package com.liuyun.site.model.borrow.protocol;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class TemplateReader extends FilterReader {
	private ArrayList<String> lines = new ArrayList<String>();
	StringBuffer lineBuf = new StringBuffer();
	StringBuffer variables = new StringBuffer();

	public TemplateReader(Reader r) {
		super(r);
	}

	public int read() throws IOException {
		int c = this.in.read();
		handleChar(c);
		return c;
	}

	public int read(char[] cbuf, int off, int len) throws IOException {
		int numchars = this.in.read(cbuf, off, len);
		for (int i = off; i < off + numchars; ++i) {
			char c = cbuf[i];
			handleChar(c);
		}
		return numchars;
	}

	private void handleChar(int c) {
		if (c == 36) {
			this.variables.setLength(0);
			this.variables.append((char) c);
			this.lines.add(this.lineBuf.toString());
			this.lineBuf.setLength(0);
			this.lineBuf.append((char) c);
		} else if (c == 123) {
			if ((this.variables.length() > 0)
					&& (this.variables.toString().equals("$"))) {
				this.variables.append((char) c);
			} else {
				this.variables.setLength(0);
				this.variables.append((char) c);
			}
			this.lineBuf.append((char) c);
		} else if (c == 125) {
			if ((this.variables.length() > 0)
					&& (this.variables.toString().equals("${"))) {
				this.lineBuf.append((char) c);
				this.lines.add(this.lineBuf.toString());
				this.lineBuf.setLength(0);
				this.variables.setLength(0);
			} else {
				this.lineBuf.append((char) c);
			}
		} else {
			this.lineBuf.append((char) c);
		}
	}

	public void close() throws IOException {
		super.close();
		this.in.close();
	}

	public ArrayList<String> getLines() {
		this.lines.add(this.lineBuf.toString());
		return this.lines;
	}

	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}
}