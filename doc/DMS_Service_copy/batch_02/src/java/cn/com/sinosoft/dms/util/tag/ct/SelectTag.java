package cn.com.sinosoft.dms.util.tag.ct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.struts2.ServletActionContext;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import cn.com.sinosoft.ims.util.tag.ce.ItemTag;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinosoft.dmsdriver.model.PrpDclass;
import com.sinosoft.dmsdriver.model.PrpDcode;
import com.sinosoft.dmsdriver.model.PrpDcurrency;
import com.sinosoft.dmsdriver.service.common.DictPage;
import com.sinosoft.dmsdriver.service.server.DictAPIService;
import com.sinosoft.dmsdriver.service.server.PageService;
import com.sinosoft.dmsdriver.util.SystemCode;

/**
 * @author hua
 * 数据字典，代码下拉框
 */
public class SelectTag extends ItemTag {
	private static final long serialVersionUID = 1L;
	protected String codeType;//代码类型
	protected String sysCode;//系统代码
	protected String headValue;//第一个显示的值
	protected String multiple;// 是否为多选
	

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

	public void setCodeType(String codeType) throws JspException {
		this.codeType = codeType;
		if (codeType != null && codeType.startsWith("${")
				&& codeType.endsWith("}")) {
			this.codeType = (String) ExpressionEvaluatorManager.evaluate(
					"codeType", codeType, Object.class, this, pageContext);
		}
	}

	public void setSysCode(String sysCode) throws JspException {
		this.sysCode = sysCode;
		if (sysCode != null && sysCode.startsWith("${")
				&& sysCode.endsWith("}")) {
			this.sysCode = (String) ExpressionEvaluatorManager.evaluate(
					"sysCode", sysCode, Object.class, this, pageContext);
		}
	}

	public int doStartTag() throws JspException {
		ValueStack valueStack = ServletActionContext.getContext()
				.getValueStack();
		StringBuffer commonHTML = generateHTML();// 生成样式等html字符串
		List<PrpDcode> list = new ArrayList();
		ArrayList valueList = new ArrayList();
	    DictPage dictPage;
	    List<PrpDclass> listClass = new ArrayList();
	    List<PrpDcurrency> currencyList = new ArrayList();
		
		StringBuffer result = new StringBuffer();// result：输出的html
		
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
		result.append("<select");
		result.append(commonHTML);
		if (id != null) {
			result.append(" id=\"").append(id).append("\"");
		}
		result.append(">");

		/** 第一个显示的值 */
		if (headValue != null && !"".equals(headValue)) {
			result.append("<option value=\"").append("\"");
			result.append(">").append(headValue).append("</option>");
		}

		try {
			/** 调用接口方法翻译代码 .....................................................................*/
			if("PrpDclass".equals(codeType)){
				dictPage =PageService.getclass(sysCode,"", 0, 0);
				listClass = dictPage.getData();
			} else if("Currency".equals(codeType)){
				currencyList = PageService.getPrpDcurrency(SystemCode.DMS, "", "", "1", 0, 0).getData();
			}
			else{
				list = DictAPIService.getPrpDcodeList(sysCode, codeType);
			}
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new JspException(e1.toString());
		}

		/**************************************
		 * 通过接口获得的值转换成html
		 * *************************************/
		if("PrpDclass".equals(codeType)){
			if(listClass.size()>0){
				for(int i=0;i<listClass.size();i++){
					String classCode = listClass.get(i).getClassCode();
					String classCName = listClass.get(i).getClassCName();
					result.append("<option value=\"").append(classCode).append("\"");
					if(valueList.contains(classCode)){
						result.append(" selected");
					}
					result.append(">").append(classCName).append("</option>");
				}
			}
		}else if("Currency".equals(codeType)){
			for(int i=0;i<currencyList.size();i++){
				String currencyCode = currencyList.get(i).getCurrencyCode();
				String currencyCName = currencyList.get(i).getCurrencyCName();
				result.append("<option value=\"").append(currencyCode).append("\"");
				if(valueList.contains(currencyCode)){
					result.append(" selected");
				}
				result.append(">").append(currencyCName).append("</option>");
			}
		}else{
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					String code = list.get(i).getId().getCodeCode();
					String codeCName = list.get(i).getCodeCName();
					result.append("<option value=\"").append(code).append("\"");
					if (valueList.contains(code)) {
						result.append(" selected");
					}
					result.append(">").append(codeCName).append("</option>");
				}
			}
			
		}
		 result.append("</select>");
		/******************************************
		 *  将结果写回页面
		 * ********************************************/
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(result);
		} catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e.toString());
		}
		return EVAL_BODY_INCLUDE;
	}
	public int doEndTag() throws JspException {
		clearValue();
		codeType = null;
		sysCode = null;
		return EVAL_PAGE;
	}
}
