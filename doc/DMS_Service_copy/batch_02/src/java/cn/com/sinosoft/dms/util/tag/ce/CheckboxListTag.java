package cn.com.sinosoft.dms.util.tag.ce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.util.ValueStack;

public class CheckboxListTag extends ItemTag {

	protected String list;

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		ValueStack valueStack = ServletActionContext.getContext()
		.getValueStack();
		StringBuffer commonHTML = generateHTML();
		ArrayList valueList = new ArrayList();
		StringBuffer buffer = new StringBuffer();
		if (list == null) {
			throw new JspException("CheckboxListTag.list is null");
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
					buffer.append("<input type=\"checkbox\"");
					buffer.append(commonHTML);
					if (id != null) {
						buffer.append(" id=\"").append(id).append(item[0])
								.append("\"");
					}
					if (item[0] != null) {
						buffer.append(" value=\"").append(item[0]).append("\"");
					}
					if(valueList.contains(item[0])){
						buffer.append(" checked");
					}
					
					buffer.append(">");
					if (id != null) {
						buffer.append("<label for=\"").append(id).append(
								item[0]).append("\">").append(item[1]).append(
								"</label>");
					} else {
						buffer.append(item[1]);
					}
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
					buffer.append("<input type=\"checkbox\"");
					buffer.append(commonHTML);
					if (id != null) {
						buffer.append(" id=\"").append(id).append(keyText)
								.append("\"");
					}
					if (keyText != null) {
						buffer.append(" value=\"").append(keyText).append("\"");
					}
					if(valueList.contains(keyText)){
						buffer.append(" checked");
					}					
					if (id != null) {
						buffer.append("><label for=\"").append(id).append(
								keyText).append("\">").append(valueText)
								.append("</label>");
					} else {
						buffer.append(valueText);
					}
				}
			}
		}

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
		return EVAL_PAGE;
	}

	public void setList(String list) {
		this.list = list;
	}
}
