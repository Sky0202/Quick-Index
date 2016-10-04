package com.sky.quickindex;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


public class PinyinUtil {

	private static HanyuPinyinOutputFormat format;

	public static String chineseWordToPinyin(String chineseWord) {
		if (format == null) {
			format = new HanyuPinyinOutputFormat();
		}
		//设置去除声调
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		//将汉语字符串转化为字符数据
		char[] chars = chineseWord.toCharArray();
		StringBuffer sb=new StringBuffer();
		for (char c : chars) {
			//跨过空格
			if(Character.isWhitespace(c)){
				continue;
			}else{
				//拼配汉字的正则表达式
				if(Character.toString(c).matches("[\\u4E00-\\u9FA5]")) {
					try {
						String[] results = PinyinHelper.toHanyuPinyinStringArray(c, format);
						String result = results[0];
						sb.append(result);
					} catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
						badHanyuPinyinOutputFormatCombination.printStackTrace();
					}
				}else{
					//如果是字母
					if(Character.isLetter(c)){
						//直接变为字母
						sb.append(c);
					}else{
						//如果是"火星文",以#代替
						sb.append("#");
					}
				}
			}
		}
		return sb.toString();

	}
}
