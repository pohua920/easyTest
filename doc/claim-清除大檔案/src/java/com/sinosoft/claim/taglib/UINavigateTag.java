package com.sinosoft.claim.taglib;

import ins.framework.common.Page;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * 生成分页导航控制条
 * @Description
 * @author 中科软
 */
public class UINavigateTag extends TagSupport {
	private static final long serialVersionUID = UINavigateTag.class.hashCode();
	/** The context-relative URI. */
	protected String page = null;
	private String objectName = "";
	private boolean display = true;// 是否必须显示分页条。true：必须显示；false：页数大於1时才显示。
	private String LINE_SEPARATOR = System.getProperty("line.separator");
	private StringBuffer results = new StringBuffer(64000);

	public int doStartTag() throws JspException {
		// Generate the URL to be encoded
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String objectName = getObjectName();
		Page page = (Page) request.getAttribute(objectName);
		if (page != null) {
			results.setLength(0);
			long currentPage = page.getCurrentPageNo();
			long pagesCount = page.getTotalPageCount();
			if (pagesCount > 1 || isDisplay()) {// 2页以上才显示分页栏
				writeLine("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
				writeLine("<tr><td align=\"center\">");
				writeLine(" <input type=\"hidden\" name=\"pagesCount\" value=\"" + pagesCount + "\">");// 要隐藏总页数
				if (page.getTotalCount() > 0) {
					// 页
					writeLine("[" + getI18N("navigator.page") + page.getCurrentPageNo() + "/" + page.getTotalPageCount() + " ]");// 页
					writeLine("[ " + page.getTotalCount() + " " + getI18N("navigator.records") + "]");// 条记录
				}
				if (currentPage > 1) {
					writeLine("<a href=# alt='" + getI18N("navigator.first") + "' onclick=\"return locate(1);\">[" + getI18N("navigator.first") + "]</a>");
					writeLine("<a href=# alt='" + getI18N("navigator.prev") + "' onclick=\"return locate(" + (page.getCurrentPageNo() - 1) + ")\">[" + getI18N("navigator.prev") + "]</a>");// 上页
				} else if (isDisplay() && page.getTotalCount() > 0) {// 配置显示且记录大於1
					writeLine("[" + getI18N("navigator.first") + "]");// 首页
					writeLine("[" + getI18N("navigator.prev") + "]");// 上页
				}
				if (currentPage < pagesCount) {// 配置显示且记录大於1
					writeLine("<a href=# alt='" + getI18N("navigator.next") + "' onclick=\"return locate(" + (currentPage + 1) + ")\">[" + getI18N("navigator.next") + "]</a>");// 下页
					writeLine("<a href=# alt='" + getI18N("navigator.last") + "' onclick=\"return locate(" + pagesCount + ")\">[" + getI18N("navigator.last") + "]</a>");// 末页
				} else if (isDisplay() && page.getTotalCount() > 0) {
					writeLine("[" + getI18N("navigator.next") + "]");// 下页
					writeLine("[" + getI18N("navigator.last") + "]");// 末页
				}
				if (pagesCount > 1) {// 页数大於1才显示可跳转
					writeLine(" " + getI18N("navigator.to") + "<input type=\"text\" name=\"newPageNo\" class=\"small\" style=\"width:25px;\" onchange=\"setSameElementValue(this)\">" + getI18N("navigator.page") + "");// 页
					writeLine("<img src='"+request.getContextPath()+"/images/btnGo.gif' align='middle' style='cursor:hand'  border='0' alt='" + getI18N("navigator.goTo") + "' onclick=\"return goPage()\">");// 转到
				}
				writeLine("</td></tr></table>");
			}
			JspWriter writer = pageContext.getOut();
			try {
				writer.print(results.toString());
			} catch (IOException e) {
				throw new JspException(e.toString());
			}
		}

		return (EVAL_BODY_INCLUDE);
	}

	private String getI18N(String name) {
		Locale locale = ActionContext.getContext().getLocale();
		return LocalizedTextUtil.findDefaultText(name, locale);
	}

	/**
	 * Render the end of the hyperlink.
	 * @return int
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}

	/** Release any acquired resources. */
	public void release() {
		super.release();
		this.page = null;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	private void writeLine(String value) {
		results.append(value);
		results.append(LINE_SEPARATOR);
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}
}
