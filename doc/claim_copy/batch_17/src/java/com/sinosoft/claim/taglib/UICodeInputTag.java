package com.sinosoft.claim.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * com.sinosoft.platform.ui.view.taglib.UICodeInputTag 平台相关的双击域
 * @Description 
 * @author 中科软
 */
public class UICodeInputTag extends TagSupport {

	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	private static String value = "<iframe id=\"CodeFrame\" name=\"CodeFrame\" src=\"/claim/pages/platform/common/QueryCodeInputOverview.jsp\" style=\"DISPLAY:none;Z-INDEX:100;POSITION:absolute\" marginwidth=\"0\" marginheight=\"0\" hspace=\"0\" vspace=\"0\" frameborder=\"0\" scrolling=\"no\"></iframe>";

	public UICodeInputTag() {
	}

	public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(value);
		} catch (IOException e) {
			throw new JspException(e.toString());
		}
		return (EVAL_BODY_INCLUDE);
	}

	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}

	public void release() {
		super.release();
	}

}
