package com.liuyun.site.util.tenpayUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： RSAPublicKeyReader   
 * 类描述： RSA 公钥读取工具  
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 9:14:03 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 9:14:03 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class RSAPublicKeyReader {
	public static PublicKey getPublicKeyFromFile(InputStream is, String charset)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		byte[] bytes = loadPublicKeyStringFromFile(is, charset);

		KeyFactory kf = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
		PublicKey key = kf.generatePublic(keySpec);

		return key;
	}

	private static byte[] loadPublicKeyStringFromFile(InputStream is,
			String charset) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(is));
		String line;
		do
			line = bufferedReader.readLine();
		while ((line != null) && (line.charAt(0) != '-'));
		do {
			line = bufferedReader.readLine();
			if (line == null)
				break;
		} while (line.charAt(0) == '-');

		while ((line != null) && (line.charAt(0) != '-')) {
			stringBuffer.append(line);
			line = bufferedReader.readLine();
		}
		String base64String = stringBuffer.toString();
		return Base64.decode(base64String.getBytes());
	}
}