package com.liuyun.site.tool.coder;

import java.io.IOException;
import java.io.OutputStream;

public class BASE64Encoder extends CharacterEncoder {
	
	protected int bytesPerAtom() {
		return 3;
	}

	protected int bytesPerLine() {
		return 57;
	}

	private static final char[] pem_array = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '+', '/' };


	protected void encodeAtom(OutputStream outStream, byte[] data, int offset,
			int len) throws IOException {
		
		if (len == 1) {
			byte byte0 = data[offset];
			int k = 0;
			outStream.write(pem_array[(byte0 >>> 2 & 0x3F)]);
			outStream.write(pem_array[((byte0 << 4 & 0x30) + (k >>> 4 & 0xF))]);
			outStream.write(61);
			outStream.write(61);
		} else if (len == 2) {
			byte byte1 = data[offset];
			byte byte3 = data[(offset + 1)];
			int l = 0;
			outStream.write(pem_array[(byte1 >>> 2 & 0x3F)]);
			outStream.write(pem_array[((byte1 << 4 & 0x30) + (byte3 >>> 4 & 0xF))]);
			outStream.write(pem_array[((byte3 << 2 & 0x3C) + (l >>> 6 & 0x3))]);
			outStream.write(61);
		} else {
			byte byte2 = data[offset];
			byte byte4 = data[(offset + 1)];
			byte byte5 = data[(offset + 2)];
			outStream.write(pem_array[(byte2 >>> 2 & 0x3F)]);
			outStream.write(pem_array[((byte2 << 4 & 0x30) + (byte4 >>> 4 & 0xF))]);
			outStream.write(pem_array[((byte4 << 2 & 0x3C) + (byte5 >>> 6 & 0x3))]);
			outStream.write(pem_array[(byte5 & 0x3F)]);
		}
	}
}