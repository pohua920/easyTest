package cn.com.sinosoft.ims.util.tag.ce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.struts2.ServletActionContext;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.opensymphony.xwork2.util.ValueStack;

public class SelectTag extends ItemTag {

	protected String list;
	protected String multiple;
	protected String headValue;
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		ValueStack valueStack = ServletActionContext.getContext()
		.getValueStack();
		StringBuffer commonHTML = generateHTML();
		ArrayList valueList = new ArrayList();
		StringBuffer buffer = new StringBuffer();
		if (list == null) {
			throw new JspException("SelectTag.list is null");
		}
		if (id == null) {
			id = name;
		}
		if (value != null) {
			if (value.startsWith("{") && value.endsWith("}")) {
				value = value.substring(1, value.length() - 1);
				String[] values = value.split(",");
				for(int i=0;i<values.length;i++){
					if (values[i].startsWith("'") && values[i].endsWith("'")) {
						values[i] = values[i].substring(1, values[i].length()-1);
					}
					valueList.add(values[i].trim());
				}
			} else {
				Object valueObject = valueStack.findValue(value);
				if(valueObject instanceof java.util.List){
					java.util.List objList = (java.util.List)valueObject;
					for(Object obj : objList){
						valueList.add(obj.toString().trim());
					}
				}else{
					valueList.add(value.trim());
				}
			}
		}
		
		buffer.append("<select");
		buffer.append(commonHTML);
		if (id != null) {
			buffer.append(" id=\"").append(id).append("\"");
		}
		buffer.append(">");
		
		if(headValue!=null&&!"".equals(headValue)){
			buffer.append("<option value=\"").append("\"");
			buffer.append(">").append(headValue).append("</option>");
		}
		if (list.startsWith("#{") && list.endsWith("}")) {
			String[] items = list.substring(0, list.length() - 1).substring(2)
					.split(",");
			if (items != null) {
				for (int i = 0; i < items.length; i++) {
					String[] item = items[i].split(":");
					if (item == null || item.length != 2) {
						continue;
					}
					if (item[0].startsWith("'") && item[0].endsWith("'")) {
						item[0] = item[0].substring(1, item[0].length() - 1);
					}
					if (item[1].startsWith("'") && item[1].endsWith("'")) {
						item[1] = item[1].substring(1, item[1].length() - 1);
					}
					buffer.append("<option value=\"").append(item[0]).append("\"");

					if (valueList.contains(item[0])) {
						buffer.append(" selected");
					}
					buffer.append(">").append(item[1]).append("</option>");
				}
			}
		} else {
			Object obj = valueStack.findValue(list);
			if (obj instanceof Map) {
				Map map = (Map) obj;
				Iterator iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					Object itemKey = iterator.next();
					Object itemValue = map.get(itemKey);
					String keyText = itemKey.toString();
					String valueText = itemValue.toString();
					buffer.append("<option value=\"").append(keyText).append("\"");

					if (valueList.contains(keyText)) {
						buffer.append(" selected");
					}
					buffer.append(">").append(valueText).append("</option>");
				}
			}
		}
		buffer.append("</select>");

		JspWriter writer = pageContext.getOut();
		try {
			writer.print(buffer.toString());
		} catch (IOException e) {
			throw new JspException(e.toString());
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		clearValue();
		list = null;
		multiple = null;
		return EVAL_PAGE;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void setMultiple(String multiple) throws JspException {
		this.multiple = multiple;
		if (multiple != null && multiple.startsWith("${")
				&& multiple.endsWith("}")) {
			this.multiple = (String) ExpressionEvaluatorManager.evaluate(
					"multiple", multiple, Object.class, this, pageContext);
		}		
	}

	public void setHeadValue(String headValue) {
		this.headValue = headValue;
	}
	
}
