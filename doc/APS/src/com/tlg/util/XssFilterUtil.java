package com.tlg.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class XssFilterUtil {
	private static final Logger log = Logger.getLogger(XssFilterUtil.class);

	private static List<Pattern> patterns = null;

	/**
	 * @Title: XSS常見攻擊
	 * @methodName: getXssPatternList
	 * @Description: Pattern.MULTILINE(?
	 *               m)：在這種模式下，'^'和'$'分別匹配一行的開始和結束。此外，'^'仍然匹配字串的開始，'$'也匹配字串的結束。
	 *               預設情況下，這兩個表示式僅僅匹配字串的開始和結束。
	 *               <p>
	 *               Pattern.DOTALL(?s) ：在這種模式下，表示式'.'可以匹配任意字元，包括表示一行的結束符。
	 *               預設情況下，表示式'.'不匹配行的結束符。
	 */
	private static List<Object[]> getXssPatternList() {

		List<Object[]> ret = new ArrayList<Object[]>();

		ret.add(new Object[] { "<(no)?script[^>]*>.*?</(no)?script>", Pattern.CASE_INSENSITIVE });
		ret.add(new Object[] { "</script>", Pattern.CASE_INSENSITIVE });
		ret.add(new Object[] { "<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
		ret.add(new Object[] { "eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
		ret.add(new Object[] { "expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
		ret.add(new Object[] { "(javascript:|vbscript:|view-source:)*", Pattern.CASE_INSENSITIVE });
		ret.add(new Object[] { "<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
		ret.add(new Object[] { "(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
		ret.add(new Object[] {"<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
		return ret;
	}

	// XSS常見攻擊-正則表示式
	private static List<Pattern> getPatterns() {

		if (patterns == null) {

			List<Pattern> list = new ArrayList<Pattern>();

			String regex = null;
			Integer flag = null;
			int arrLength = 0;

			for (Object[] arr : getXssPatternList()) {
				arrLength = arr.length;
				for (int i = 0; i < arrLength; i++) {
					regex = (String) arr[0];
					flag = (Integer) arr[1];
					list.add(Pattern.compile(regex, flag));
				}
			}

			patterns = list;
		}

		return patterns;
	}

	// 處理特殊字元
	public static String stripXss(String value) {

		if (StringUtils.isNotBlank(value)) {

//			log.info("【XSS攻擊防禦】，接收字元是：" + value);
			Matcher matcher = null;

			for (Pattern pattern : getPatterns()) {
				matcher = pattern.matcher(value);
				// 匹配
				if (matcher.find()) {
					// 刪除相關字串
					value = matcher.replaceAll("");
				}
			}

//			log.info("【XSS攻擊防禦】，匹配正則是：" + matcher + "，處理後是：" + value);

			/**
			 * 替換為轉移字元，類似HtmlUtils.htmlEscape
			 */
			// value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			// 刪除特殊符號
			// String specialStr =
			// "%20|=|!=|-|--|;|'|\"|%|#|+|//|/| |\\|<|>|(|)|{|}";

			if (StringUtils.isNotBlank(value)) {
				String specialStr = "%20|=|!=|-|--|'|\"|%|#|[+]|//|/| |<|>|{|}";
				for (String str : specialStr.split("\\|")) {
					if (value.indexOf(str) > -1) {
						value = value.replaceAll(str, "");

					}
				}
//				log.info("【XSS攻擊防禦】，特殊符號處理後是：" + value);
			}
		}

		return value;
	}
}
