package com.liuyun.site.tool.jcaptcha;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

public class GMailEngine extends ListImageCaptchaEngine {
	
	protected void buildInitialFactories() {
		int minWordLength = 5;
		int maxWordLength = 5;
		int fontSize = 20;
		int imageWidth = 180;
		int imageHeight = 30;

		WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));

		TextPaster randomPaster = new DecoratedRandomTextPaster(Integer.valueOf(minWordLength), Integer.valueOf(maxWordLength),
				new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11), new Color(23, 67, 172) }), new TextDecorator[0]);
		BackgroundGenerator background = new UniColorBackgroundGenerator(Integer.valueOf(imageWidth), Integer.valueOf(imageHeight), Color.white);
		FontGenerator font = new RandomFontGenerator(Integer.valueOf(fontSize),
				Integer.valueOf(fontSize), new Font[] {
						new Font("nyala", 1, fontSize),
						new Font("Consolas", 1, fontSize),
						new Font("Credit valley", 1, fontSize) });

		ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[0]);
		ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[0]);
		ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[0]);

		WordToImage word2image = new DeformedComposedWordToImage(font, background, randomPaster, backDef, textDef, postDef);
		addFactory(new GimpyFactory(dictionnaryWords, word2image));
	}
}