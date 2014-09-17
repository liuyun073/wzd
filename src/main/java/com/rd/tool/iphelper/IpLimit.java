package com.rd.tool.iphelper;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class IpLimit {
	public static BigInteger StringToBigInt(String ipInString) {
		ipInString = ipInString.replace(" ", "");
		byte[] bytes;
		if (ipInString.contains(":")) {
			bytes = ipv6ToBytes(ipInString);
		} else {
			bytes = ipv4ToBytes(ipInString);
		}
		return new BigInteger(bytes);
	}

	public static String BigIntToString(BigInteger ipInBigInt) {
		byte[] bytes = ipInBigInt.toByteArray();

		byte[] unsignedBytes = bytes;
		try {
			String ip = InetAddress.getByAddress(unsignedBytes).toString();

			return ip.substring(ip.indexOf('/') + 1).trim();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	private static byte[] ipv6ToBytes(String ipv6) {
		byte[] ret = new byte[17];

		ret[0] = 0;

		int ib = 16;

		boolean comFlag = false;

		if (ipv6.startsWith(":")) {
			ipv6 = ipv6.substring(1);
		}
		String[] groups = ipv6.split(":");

		for (int ig = groups.length - 1; ig > -1; --ig) {
			if (groups[ig].contains(".")) {
				byte[] temp = ipv4ToBytes(groups[ig]);

				ret[(ib--)] = temp[4];

				ret[(ib--)] = temp[3];

				ret[(ib--)] = temp[2];

				ret[(ib--)] = temp[1];

				comFlag = true;
			} else if ("".equals(groups[ig])) {
				int zlg = 9 - (groups.length + ((comFlag) ? 1 : 0));

				while (zlg-- > 0) {
					ret[(ib--)] = 0;

					ret[(ib--)] = 0;
				}

			} else {
				int temp = Integer.parseInt(groups[ig], 16);

				ret[(ib--)] = (byte) temp;

				ret[(ib--)] = (byte) (temp >> 8);
			}

		}

		return ret;
	}

	private static byte[] ipv4ToBytes(String ipv4) {
		byte[] ret = new byte[5];

		ret[0] = 0;

		int position1 = ipv4.indexOf(".");

		int position2 = ipv4.indexOf(".", position1 + 1);

		int position3 = ipv4.indexOf(".", position2 + 1);

		ret[1] = (byte) Integer.parseInt(ipv4.substring(0, position1));

		ret[2] = (byte) Integer.parseInt(ipv4.substring(position1 + 1,
				position2));

		ret[3] = (byte) Integer.parseInt(ipv4.substring(position2 + 1,
				position3));

		ret[4] = (byte) Integer.parseInt(ipv4.substring(position3 + 1));

		return ret;
	}

	public static boolean IsIp(String tip, String[][] myRange) {
		boolean flag = false;

		BigInteger tbig = StringToBigInt(tip);

		int rangeLength = myRange.length;

		for (int i = 0; i < rangeLength; ++i) {
			for (int j = 0; j < myRange[i].length; ++j) {
				BigInteger sbig = StringToBigInt(myRange[i][j]);

				++j;

				BigInteger ebig = StringToBigInt(myRange[i][j]);

				if (tbig.compareTo(sbig) == 0) {
					flag = true;

					break;
				}

				if ((tbig.compareTo(sbig) != 1) || (tbig.compareTo(ebig) != -1))
					continue;
				flag = true;

				break;
			}

		}

		return flag;
	}

	public static void main(String[] args) {
		String[][] iplimit1 = { { "192.168.0.1", "192.168.0.255" },
				{ "10.210.71.0", "10.210.71.255" },
				{ "202.106.182.158", "202.106.182.158" } };

		boolean flag = false;

		String ip = "202.106.182.158";

		flag = IsIp(ip, iplimit1);

		System.out.println("The first test =" + flag);

		String ip1 = "201.101.102.21";

		flag = IsIp(ip1, iplimit1);

		System.out.println("The other test =" + flag);
	}

	class IpRange {
		private String[][] ipRange;

		public IpRange(String[][] ip) {
			this.ipRange = ip;
		}

		public String getIpAt(int row, int column) {
			return this.ipRange[row][column];
		}
	}
}