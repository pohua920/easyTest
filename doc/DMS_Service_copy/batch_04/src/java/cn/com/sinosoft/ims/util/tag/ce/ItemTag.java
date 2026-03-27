package cn.com.sinosoft.ims.util.tag.ce;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

public abstract class ItemTag extends TagSupport {

	protected String accesskey;

	protected String cssClass;

	protected String cssStyle;

	protected String disabled;

	protected String name;

	protected String onblur;

	protected String onchange;

	protected String onclick;

	protected String ondblclick;

	protected String onfocus;

	protected String onkeydown;

	protected String onkeypress;

	protected String onkeyup;

	protected String onmousedown;

	protected String onmousemove;

	protected String onmouseout;

	protected String onmouseover;

	protected String onmouseup;

	protected String onselect;

	protected String required;

	protected String size;

	protected String tabindex;

	protected String title;

	protected String tooltip;

	protected String value;

	public void setId(String id) {
		this.id = id;
		if (id != null && id.startsWith("${") && id.endsWith("}")) {
			try {
				this.id = (String) ExpressionEvaluatorManager.evaluate("id",
						id, Object.class, this, pageContext);
			} catch (JspException e) {
				e.printStackTrace();
				throw new RuntimeException("setId fail.");
			}
		}
	};

	public void setAccesskey(String accesskey) throws JspException {
		this.accesskey = accesskey;
		if (accesskey != null && accesskey.startsWith("${")
				&& accesskey.endsWith("}")) {
			this.accesskey = (String) ExpressionEvaluatorManager.evaluate(
					"accesskey", accesskey, Object.class, this, pageContext);
		}
	};

	public void setCssClass(String cssClass) throws JspException {
		this.cssClass = cssClass;
		if (cssClass != null && cssClass.startsWith("${")
				&& cssClass.endsWith("}")) {
			this.cssClass = (String) ExpressionEvaluatorManager.evaluate(
					"cssClass", cssClass, Object.class, this, pageContext);
		}
	};

	public void setCssStyle(String cssStyle) throws JspException {
		this.cssStyle = cssStyle;
		if (cssStyle != null && cssStyle.startsWith("${")
				&& cssStyle.endsWith("}")) {
			this.cssStyle = (String) ExpressionEvaluatorManager.evaluate(
					"cssStyle", cssStyle, Object.class, this, pageContext);
		}
	};

	public void setDisabled(String disabled) throws JspException {
		this.disabled = disabled;
		if (disabled != null && disabled.startsWith("${")
				&& disabled.endsWith("}")) {
			this.disabled = (String) ExpressionEvaluatorManager.evaluate(
					"disabled", disabled, Object.class, this, pageContext);
		}
	};

	public void setName(String name) throws JspException {
		this.name = name;
		if (name != null && name.startsWith("${") && name.endsWith("}")) {
			this.name = (String) ExpressionEvaluatorManager.evaluate("name",
					name, Object.class, this, pageContext);
		}
	};

	public void setOnblur(String onblur) throws JspException {
		this.onblur = onblur;
		if (onblur != null && onblur.startsWith("${") && onblur.endsWith("}")) {
			this.onblur = (String) ExpressionEvaluatorManager.evaluate(
					"onblur", onblur, Object.class, this, pageContext);
		}
	};

	public void setOnchange(String onchange) throws JspException {
		this.onchange = onchange;
		if (onchange != null && onchange.startsWith("${")
				&& onchange.endsWith("}")) {
			this.onchange = (String) ExpressionEvaluatorManager.evaluate(
					"onchange", onchange, Object.class, this, pageContext);
		}
	};

	public void setOnclick(String onclick) throws JspException {
		this.onclick = onclick;
		if (onclick != null && onclick.startsWith("${")
				&& onclick.endsWith("}")) {
			this.onclick = (String) ExpressionEvaluatorManager.evaluate(
					"onclick", onclick, Object.class, this, pageContext);
		}
	};

	public void setOndblclick(String ondblclick) throws JspException {
		this.ondblclick = ondblclick;
		if (ondblclick != null && ondblclick.startsWith("${")
				&& ondblclick.endsWith("}")) {
			this.ondblclick = (String) ExpressionEvaluatorManager.evaluate(
					"ondblclick", ondblclick, Object.class, this, pageContext);
		}
	};

	public void setOnfocus(String onfocus) throws JspException {
		this.onfocus = onfocus;
		if (onfocus != null && onfocus.startsWith("${")
				&& onfocus.endsWith("}")) {
			this.onfocus = (String) ExpressionEvaluatorManager.evaluate(
					"onfocus", onfocus, Object.class, this, pageContext);
		}
	};

	public void setOnkeydown(String onkeydown) throws JspException {
		this.onkeydown = onkeydown;
		if (onkeydown != null && onkeydown.startsWith("${")
				&& onkeydown.endsWith("}")) {
			this.onkeydown = (String) ExpressionEvaluatorManager.evaluate(
					"onkeydown", onkeydown, Object.class, this, pageContext);
		}
	};

	public void setOnkeypress(String onkeypress) throws JspException {
		this.onkeypress = onkeypress;
		if (onkeypress != null && onkeypress.startsWith("${")
				&& onkeypress.endsWith("}")) {
			this.onkeypress = (String) ExpressionEvaluatorManager.evaluate(
					"onkeypress", onkeypress, Object.class, this, pageContext);
		}
	};

	public void setOnkeyup(String onkeyup) throws JspException {
		this.onkeyup = onkeyup;
		if (onkeyup != null && onkeyup.startsWith("${")
				&& onkeyup.endsWith("}")) {
			this.onkeyup = (String) ExpressionEvaluatorManager.evaluate(
					"onkeyup", onkeyup, Object.class, this, pageContext);
		}
	};

	public void setOnmousedown(String onmousedown) throws JspException {
		this.onmousedown = onmousedown;
		if (onmousedown != null && onmousedown.startsWith("${")
				&& onmousedown.endsWith("}")) {
			this.onmousedown = (String) ExpressionEvaluatorManager
					.evaluate("onmousedown", onmousedown, Object.class, this,
							pageContext);
		}
	};

	public void setOnmousemove(String onmousemove) throws JspException {
		this.onmousemove = onmousemove;
		if (onmousemove != null && onmousemove.startsWith("${")
				&& onmousemove.endsWith("}")) {
			this.onmousemove = (String) ExpressionEvaluatorManager
					.evaluate("onmousemove", onmousemove, Object.class, this,
							pageContext);
		}
	};

	public void setOnmouseout(String onmouseout) throws JspException {
		this.onmouseout = onmouseout;
		if (onmouseout != null && onmouseout.startsWith("${")
				&& onmouseout.endsWith("}")) {
			this.onmouseout = (String) ExpressionEvaluatorManager.evaluate(
					"onmouseout", onmouseout, Object.class, this, pageContext);
		}
	};

	public void setOnmouseover(String onmouseover) throws JspException {
		this.onmouseover = onmouseover;
		if (onmouseover != null && onmouseover.startsWith("${")
				&& onmouseover.endsWith("}")) {
			this.onmouseover = (String) ExpressionEvaluatorManager
					.evaluate("onmouseover", onmouseover, Object.class, this,
							pageContext);
		}
	};

	public void setOnmouseup(String onmouseup) throws JspException {
		this.onmouseup = onmouseup;
		if (onmouseup != null && onmouseup.startsWith("${")
				&& onmouseup.endsWith("}")) {
			this.onmouseup = (String) ExpressionEvaluatorManager.evaluate(
					"onmouseup", onmouseup, Object.class, this, pageContext);
		}
	};

	public void setOnselect(String onselect) throws JspException {
		this.onselect = onselect;
		if (onselect != null && onselect.startsWith("${")
				&& onselect.endsWith("}")) {
			this.onselect = (String) ExpressionEvaluatorManager.evaluate(
					"onselect", onselect, Object.class, this, pageContext);
		}
	};

	public void setRequired(String required) throws JspException {
		this.required = required;
		if (required != null && required.startsWith("${")
				&& required.endsWith("}")) {
			this.required = (String) ExpressionEvaluatorManager.evaluate(
					"required", required, Object.class, this, pageContext);
		}
	};

	public void setSize(String size) throws JspException {
		this.size = size;
		if (size != null && size.startsWith("${") && size.endsWith("}")) {
			this.size = (String) ExpressionEvaluatorManager.evaluate("size",
					size, Object.class, this, pageContext);
		}
	};

	public void setTabindex(String tabindex) throws JspException {
		this.tabindex = tabindex;
		if (tabindex != null && tabindex.startsWith("${")
				&& tabindex.endsWith("}")) {
			this.tabindex = (String) ExpressionEvaluatorManager.evaluate(
					"tabindex", tabindex, Object.class, this, pageContext);
		}
	};

	public void setTitle(String title) throws JspException {
		this.title = title;
		if (title != null && title.startsWith("${") && title.endsWith("}")) {
			this.title = (String) ExpressionEvaluatorManager.evaluate("title",
					title, Object.class, this, pageContext);
		}
	};

	public void setTooltip(String tooltip) throws JspException {
		this.tooltip = tooltip;
		if (tooltip != null && tooltip.startsWith("${")
				&& tooltip.endsWith("}")) {
			this.tooltip = (String) ExpressionEvaluatorManager.evaluate(
					"tooltip", tooltip, Object.class, this, pageContext);
		}
	};

	public void setValue(String value) throws JspException {
		this.value = value;
		if (value != null && value.startsWith("${") && value.endsWith("}")) {
			this.value = (String) ExpressionEvaluatorManager.evaluate("value",
					value, Object.class, this, pageContext);
		}
	};

	public StringBuffer generateHTML() {
		StringBuffer buffer = new StringBuffer();

		if (accesskey != null) {
			buffer.append(" accesskey=\"").append(accesskey).append("\"");
		}
		if (cssClass != null) {
			buffer.append(" class=\"").append(cssClass).append("\"");
		}
		if (cssStyle != null) {
			buffer.append(" style=\"").append(cssStyle).append("\"");
		}
		if (disabled != null) {
			buffer.append(" disabled=\"").append(disabled).append("\"");
		}
		if (name != null) {
			buffer.append(" name=\"").append(name).append("\"");
		}
		if (onblur != null) {
			buffer.append(" onblur=\"").append(onblur).append("\"");
		}
		if (onchange != null) {
			buffer.append(" onchange=\"").append(onchange).append("\"");
		}
		if (onclick != null) {
			buffer.append(" onclick=\"").append(onclick).append("\"");
		}
		if (ondblclick != null) {
			buffer.append(" ondblclick=\"").append(ondblclick).append("\"");
		}
		if (onfocus != null) {
			buffer.append(" onfocus=\"").append(onfocus).append("\"");
		}
		if (onkeydown != null) {
			buffer.append(" onkeydown=\"").append(onkeydown).append("\"");
		}
		if (onkeypress != null) {
			buffer.append(" onkeypress=\"").append(onkeypress).append("\"");
		}
		if (onkeyup != null) {
			buffer.append(" onkeyup=\"").append(onkeyup).append("\"");
		}
		if (onmousedown != null) {
			buffer.append(" onmousedown=\"").append(onmousedown).append("\"");
		}
		if (onmousemove != null) {
			buffer.append(" onmousemove=\"").append(onmousemove).append("\"");
		}
		if (onmouseout != null) {
			buffer.append(" onmouseout=\"").append(onmouseout).append("\"");
		}
		if (onmouseover != null) {
			buffer.append(" onmouseover=\"").append(onmouseover).append("\"");
		}
		if (onmouseup != null) {
			buffer.append(" onmouseup=\"").append(onmouseup).append("\"");
		}
		if (onselect != null) {
			buffer.append(" onselect=\"").append(onselect).append("\"");
		}
		if (required != null) {
			buffer.append(" required=\"").append(required).append("\"");
		}
		if (size != null) {
			buffer.append(" size=\"").append(size).append("\"");
		}
		if (tabindex != null) {
			buffer.append(" tabindex=\"").append(tabindex).append("\"");
		}
		if (title != null) {
			buffer.append(" title=\"").append(title).append("\"");
		}
		if (tooltip != null) {
			buffer.append(" tooltip=\"").append(tooltip).append("\"");
		}

		return buffer;
	}

	public void clearValue() {

		accesskey = null;
		cssClass = null;
		cssStyle = null;
		disabled = null;
		name = null;
		onblur = null;
		onchange = null;
		onclick = null;
		ondblclick = null;
		onfocus = null;
		onkeydown = null;
		onkeypress = null;
		onkeyup = null;
		onmousedown = null;
		onmousemove = null;
		onmouseout = null;
		onmouseover = null;
		onmouseup = null;
		onselect = null;
		required = null;
		size = null;
		tabindex = null;
		title = null;
		tooltip = null;
		value = null;
		id = null;
	}
}
