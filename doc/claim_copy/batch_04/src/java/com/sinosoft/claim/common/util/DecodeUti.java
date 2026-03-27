package com.sinosoft.claim.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecodeUti {

	public static String toDecodeUnicode(String o) {
		String out = o;
		Pattern p = Pattern.compile("(&#\\d+;)");
		Matcher m = p.matcher(o);
		while (m.find()) {
			String s = m.group(1);
			String t = s.replace("&#", "").replace(";", "");
			out = out.replace(s, new String(Character.toChars(Integer.parseInt(t))));
		}
		return out;
	}

}
