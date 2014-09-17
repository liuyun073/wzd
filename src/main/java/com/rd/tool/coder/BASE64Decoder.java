package com.rd.tool.coder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BASE64Decoder extends CharacterDecoder {

	/** This class has 4 bytes per atom */
	protected int bytesPerAtom() {
		return 4;
	}

	/** Any multiple of 4 will do, 72 might be common */
	protected int bytesPerLine() {
		return 72;
	}

	/**
	 * This character array provides the character to value map
	 * based on RFC1521.
	 */	
	private static final char[] pem_array = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '+', '/' };

	private static final byte[] pem_convert_array = new byte[256];

	static {
		for (int i = 0; i < 255; ++i) {
			pem_convert_array[i] = -1;
		}
		for (int j = 0; j < pem_array.length; ++j)
			pem_convert_array[pem_array[j]] = (byte) j;
	}
	
	byte decode_buffer[] = new byte[4];


	protected void decodeAtom(InputStream inStream, OutputStream outStream, int rem) throws IOException {
		
		int     i;
		byte a = -1, b = -1, c = -1, d = -1;
		
		if (rem < 2) {
			throw new IOException("BASE64Decoder: Not enough bytes for an atom.");
		}
		do {
			i = inStream.read();
			if (i == -1)
				throw new IOException("StreamExhausted");
		} while ((i == 10) || (i == 13));
		this.decode_buffer[0] = (byte) i;
		
		int j = readFully(inStream, this.decode_buffer, 1, rem - 1);
		if (j == -1)
			throw new IOException("StreamExhausted");
		if ((rem > 3) && (this.decode_buffer[3] == 61))
			rem = 3;
		if ((rem > 2) && (this.decode_buffer[2] == 61))
			rem = 2;
		switch (rem) {
		case 4:
			d = pem_convert_array[(this.decode_buffer[3] & 0xFF)];
		case 3:
			c = pem_convert_array[(this.decode_buffer[2] & 0xFF)];
		case 2:
			b = pem_convert_array[(this.decode_buffer[1] & 0xFF)];
			a = pem_convert_array[(this.decode_buffer[0] & 0xFF)];
		}

		switch (rem) {
		case 2:
			outStream.write((byte) (a << 2 & 0xFC | b >>> 4 & 0x3));
			break;
		case 3:
			outStream.write((byte) (a << 2 & 0xFC | b >>> 4 & 0x3));
			outStream.write((byte) (b << 4 & 0xF0 | c >>> 2 & 0xF));
			break;
		case 4:
			outStream.write((byte) (a << 2 & 0xFC | b >>> 4 & 0x3));
			outStream.write((byte) (b << 4 & 0xF0 | c >>> 2 & 0xF));
			outStream.write((byte) (c << 6 & 0xC0 | d & 0x3F));
		}
	}
}