package com.sinosoft.claim.taglib;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.sinosoft.claim.common.ConstantCodes;

public class UIRCDateTag extends TagSupport {

	private static final long serialVersionUID = 1L;


	private String name;

	private String format;

	private Object value;

	private boolean wdatePicker = true;

	private String rcFormat;// 民国年的时间格式

	private String defaultValue = "";// 控件是否添加默认值，0当前年月日，-1，向前延一年，1向後延一年

	private String title;

	private String disabled;// 是否disabled
	private String readonly;// 是否只读
	private String className, style;// 样式
	private String onclick, onblur, ondblclick, onfocus, onkeyup, onkeydown, onchange, onkeypress;// 添加的事件
	private Object maxDate;//最大时间
	private Object minDate;//最小时间
	
	private String text;

	/**
	 * <rc:rcDate format="yy-MM-dd" value="${t1}" name="data"/> <input
	 * type="text" name="name" disabled="disabled" readonly="readonly"
	 * class="Wdate" onClick="WdatePicker({dateFmt:'yyy-MM-dd',vel:'name1'})"
	 * style="" onclick="" onblur="" ondblclick="" onfocus=""> <input
	 * type="text" name="name_show" value="2012-04-07" />
	 */

	public int doStartTag() throws JspException {
		try {
			if (value == null || "".equals(value.toString())) {
				value = this.pageContext.findAttribute(name);
			}
			print(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	/**
	 * <rc:rcDate format="yy-MM-dd" value="${t1}" name="data"/> <input
	 * type="text" name="name" disabled="disabled" readonly="readonly"
	 * class="Wdate" onClick="WdatePicker({dateFmt:'yyy-MM-dd',vel:'name1'})"
	 * style="" onclick="" onblur="" ondblclick="" onfocus=""> <input
	 * type="text" name="name_show" value="2012-04-07" />
	 */
	private void print(Object date) throws Exception {
		String dataPrint = "";
		String rcData = "";
		String rcMaxDate = "";
		String rcMinDate = "";
		try {
			dataPrint = changeDateToString(date);
			String rcyear = getRCYear(dataPrint);
			rcData = format(rcyear, dataPrint);
		} catch (Exception e) {
		}
		try {
			if (maxDate != null && isNotEmpty(maxDate.toString())) {
				rcMaxDate = changeDateToString(maxDate);
			}
			if (minDate != null && isNotEmpty(minDate.toString())) {
				rcMinDate = changeDateToString(minDate);
			}
		} catch (Exception e) {
		}
		StringBuffer input = new StringBuffer();
		StringBuffer hidden = new StringBuffer();
		input.append("<input type=\"text\" name=\"" + name + "_show_format_rcDate\" ");
		input.append("value=\"" + rcData + "\" ");
		input.append("realValue=\"" + dataPrint + "\" ");
		if (isNotEmpty(readonly)&&("true".equals(readonly)||"readonly".equals(readonly))) {
			wdatePicker = false;
		}
		if (isNotEmpty(onclick) || wdatePicker) {
			input.append("onclick=\"");
			if(wdatePicker){
				input.append("WdatePicker({dateFmt:'" + getRcFormat()+"'");
				if(isNotEmpty(rcMaxDate)){
					input.append(",maxDate:'"+rcMaxDate+"'");
				}
				if(isNotEmpty(rcMinDate)){
					input.append(",minDate:'"+rcMinDate+"'");
				}
				input.append( "});");
			}
			if(isNotEmpty(onclick)){
				input.append(onclick+";");
			}
			input.append( "\" ");
		}
		if (isNotEmpty(onblur)&&wdatePicker) {
			input.append("onblur=\"showRcDateTime(this,'" + getFormat() + "');" + onblur + "\" ");
		}else if(isNotEmpty(onblur)){
			input.append("onblur=\"" +onblur + "\" ");
		}else if(wdatePicker){
			input.append("onblur=\"showRcDateTime(this,'" + getFormat() + "');\" ");
		}
		if (isNotEmpty(ondblclick)) {
			input.append("ondblclick=\"" + ondblclick + "\" ");
		}
		if (isNotEmpty(onfocus)) {
			input.append("onfocus=\"" + onfocus + "\" ");
		}
		if (isNotEmpty(onkeyup)) {
			input.append("onkeyup=\"" + onkeyup + "\" ");
		}
		if (isNotEmpty(onkeydown)) {
			input.append("onkeydown=\"" + onkeydown + "\" ");
		}
		if (isNotEmpty(onchange)) {
			input.append("onchange=\"" + onchange + "\" ");
		}
		if (isNotEmpty(onkeypress)) {
			input.append("onkeypress=\"" + onkeypress + "\" ");
		}
		if (isNotEmpty(disabled)) {
			input.append("disabled=\"" + disabled + "\" ");
		}
		if (isNotEmpty(readonly)) {
			input.append("readonly=\"" + readonly + "\" ");
		}
		if (isNotEmpty(className) && wdatePicker) {
			input.append("class=\"Wdate " + className + "\" ");
		} else if (isNotEmpty(className)) {
			input.append("class=\"" + className + "\" ");
		} else if (wdatePicker) {
			input.append("class=\"Wdate\" ");
		}
		if (isNotEmpty(style)) {
			input.append("style=\"" + style + "\" ");
		}
		if (isNotEmpty(title)) {
			input.append("title=\"" + title + "\" ");
		}
		if(isNotEmpty(text)){
			input.append(text+" ");
		}
		input.append("/>");
		hidden.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + dataPrint + "\" />");
		input.append(hidden);
		pageContext.getOut().print(input.toString());
	}

	private String format(String rcyear, String dataPrint) {
		if (isNotEmpty(dataPrint)) {
			int yearPlace = getFormat().lastIndexOf("y") + 1;
			return rcyear + dataPrint.substring(yearPlace);
		} else {
			return "";
		}
	}

	private String changeDateToString(Object date) {
		if (date != null && isNotEmpty(date.toString())) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getFormat());
				if("nowDate".equals(date.toString())){
					date = new Date();
				}
				return simpleDateFormat.format(date);
			} catch (Exception e) {
				return date.toString();
			}
		} else {
			if (isNotEmpty(defaultValue)) {
				Calendar calendar = Calendar.getInstance();
				int year = Integer.parseInt(defaultValue);
				calendar.add(Calendar.YEAR, year);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getFormat());
				return simpleDateFormat.format(calendar.getTime());
			}
			return "";
		}
	}

	private String getRCYear(String date) throws Exception {
		if (isNotEmpty(date)) {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getFormat());
			calendar.setTime(simpleDateFormat.parse(date));
			int rcyear = calendar.get(Calendar.YEAR) - ConstantCodes.YEAROFFSET;
			if (rcyear < 100) {
				if(rcyear<10){
					return "00" + rcyear;
				}else{
					return "0" + rcyear;
				}
			}else {
				return String.valueOf(rcyear);
			}
		} else {
			return "";
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRcFormat() {
		if (rcFormat == null) {
			if (getFormat().lastIndexOf("y") == 3) {
				rcFormat = getFormat().substring(1);
			} else if (getFormat().lastIndexOf("y") == 2) {
				rcFormat = getFormat();
			} else {
				rcFormat = "yyy-MM-dd";
			}
		}
		return rcFormat;
	}

	public String getFormat() {
		if (!isNotEmpty(format)) {
			format = "yyyy-MM-dd";
		}
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setValue(Object value_) {
		this.value = value_;
	}

	private boolean isNotEmpty(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return true;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getClassName() {
		return className;
	}

	public void setClass(String className) {
		this.className = className;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnkeypress() {
		return onkeypress;
	}

	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOnkeyup() {
		return onkeyup;
	}

	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	public String getOnkeydown() {
		return onkeydown;
	}

	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	public Object getValue() {
		return value;
	}

	public boolean isWdatePicker() {
		return wdatePicker;
	}

	public void setWdatePicker(boolean wdatePicker) {
		this.wdatePicker = wdatePicker;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Object maxDate) {
		this.maxDate = maxDate;
	}

	public Object getMinDate() {
		return minDate;
	}

	public void setMinDate(Object minDate) {
		this.minDate = minDate;
	}

}
