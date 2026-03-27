package com.sinosoft.claim.common.util;

public class PageQueryUtil {
	public static String pageQuery(String sql, int pageNo, int pageSize) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM ( SELECT row_.*, rownum rownum_ FROM (");
		buffer.append(sql);
		buffer.append(") row_ WHERE rownum <= " + pageSize * pageNo + ") WHERE rownum_ > " + pageSize * (pageNo - 1));
		return buffer.toString();
	}
}
