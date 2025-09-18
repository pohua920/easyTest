package com.tlg.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class JsoupUtil {
	/**
	 * 使用自帶的basicWithImages 白名單
	 * 允許的便簽有a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
	 * strike,strong,sub,sup,u,ul,img
	 * 以及a標籤的href,img標籤的src,align,alt,height,width,title屬性
	 */
	private static final Whitelist whitelist = Whitelist.basicWithImages();
	/** 配置過濾化參數,不對代碼進行格式化 */
	private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
	static {
		// 富文本編輯時一些樣式是使用style來進行實現的
		// 比如紅色字體 style="color:red;"
		// 所以需要給所有標籤添加style屬性
		whitelist.addAttributes(":all", "style");
	}

	public static String clean(String content) {
		return Jsoup.clean(content, "", whitelist, outputSettings);
	}

}
