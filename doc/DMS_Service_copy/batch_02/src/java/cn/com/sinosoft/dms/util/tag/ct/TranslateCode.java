package cn.com.sinosoft.dms.util.tag.ct;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.sinosoft.dmsdriver.service.server.DictAPIService;

import cn.com.sinosoft.dms.util.tag.ce.ItemTag;

public class TranslateCode extends ItemTag {
	protected String codeType;
	protected String codeCode;
	protected String language;
	protected String sysCode;

	public void setCodeType(String codeType) throws JspException {
		this.codeType = codeType;
		if (codeType != null && codeType.startsWith("${")
				&& codeType.endsWith("}")) {
			this.codeType = (String) ExpressionEvaluatorManager.evaluate(
					"codeType", codeType, Object.class, this, pageContext);
		}
	}

	public void setCodeCode(String codeCode) throws JspException {
		this.codeCode = codeCode;
		if (codeCode != null && codeCode.startsWith("${")
				&& codeCode.endsWith("}")) {
			this.codeCode = (String) ExpressionEvaluatorManager.evaluate(
					"codeCode", codeCode, Object.class, this, pageContext);
		}	
	}

	public void setLanguage(String language) throws JspException {
		this.language = language;
		if (language != null && language.startsWith("${")
				&& language.endsWith("}")) {
			this.language = (String) ExpressionEvaluatorManager.evaluate(
					"language", language, Object.class, this, pageContext);
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

	public int doStartTag() throws JspException{
		String result = "";//翻译结果
		try {
			/**调用接口方法翻译代码*/
			result = DictAPIService.translateCode(sysCode, codeType, codeCode, language);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new JspException(e1.toString());
		}
		JspWriter writer = pageContext.getOut();
			try {
				/**将结果写回页面*/
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
		codeCode = null;
		language = null;
		sysCode = null;
		return EVAL_PAGE;
	}

}
