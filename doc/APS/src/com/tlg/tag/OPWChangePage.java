package com.tlg.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Open Window 所使用的分頁
 * 
 * @author Kelvin.Liu
 * 
 */
public class OPWChangePage extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int rowCount = 0;

	private int currentPage = 1;

	private int pageSize = 10;

	private String method = "";

	public int doStartTag() {

		try {
			JspWriter out = pageContext.getOut();
			// 總頁數
			int pageCount = this.rowCount / this.pageSize;
			String result = "";
			// 頁數
			if (pageCount <= 0) {
				pageCount = 1;
			} else if (rowCount % pageSize > 0) {
				pageCount = pageCount + 1;
			}

			int next = currentPage + 1;
			int pre = currentPage - 1;

			if (pre > 0) {
				result += "<a href=\"javascript:__doPostBack('" + this.method
						+ "','" + pre + "','')\">上一頁</a> ";
			} else {
				result += "上一頁 ";
			}

			if (next <= pageCount) {
				result += "<a href=\"javascript:__doPostBack('" + this.method
						+ "','" + next + "','')\">下一頁</a>";
			} else {
				result += "下一頁 ";
			}
			out.print(result);

		} catch (Exception e) {
			System.out.print(e);
		}
		return SKIP_BODY;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            The currentPage to set.
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return Returns the rowCount.
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            The rowCount to set.
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
