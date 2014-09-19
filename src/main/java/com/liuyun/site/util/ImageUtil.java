package com.liuyun.site.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ImageUtil {
	public static Boolean fileIsImage(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);

			BufferedImage image = ImageIO.read(is);
			if (image == null){
				return Boolean.valueOf(false);
			}
			return Boolean.valueOf(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Boolean.valueOf(false);
	}
}