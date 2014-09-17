package com.rd.tool.uchon;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UchonHelper {
	
	public static int checkOtp(String uchonSnDb, String uchonOtp)
			throws IOException {
		
		int result = 0;
		DatagramSocket client = new DatagramSocket();
		String sendStr = "code=0&id=username&sn=" + uchonSnDb + "&otp=" + uchonOtp + "&svrC=&";

		byte[] sendBuf = sendStr.getBytes();
		InetAddress addr = InetAddress.getByName("42.120.11.17");
		int port = 3344;
		DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);

		byte[] recvBuf = new byte[100];
		DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);

		int count = 0;
		String recvStr = "";

		while (count++ < 10) {
			client.send(sendPacket);
			client.receive(recvPacket);
			recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
			if (recvStr == null)
				continue;
			result = Integer.valueOf(recvStr.split("&")[1].split(" ")[0]).intValue();
			break;
		}

		return result;
	}
}