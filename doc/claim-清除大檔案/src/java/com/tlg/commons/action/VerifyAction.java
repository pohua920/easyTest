package com.tlg.commons.action;

import net.sf.json.JSONObject;

import com.tlg.commons.util.VerifyUtil;

import ins.framework.web.Struts2Action;

/**
 * 驗證輸入資料用 
 * 如:身分證字號、車號、地址 等...
 * @author bk007
 * @category mantis： CLM0040，處理人員：BK007 蘇哲，需求單編號：CLM0040 外來人口統一證號格式修正
 */
public class VerifyAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 驗證身分證字號、居留證、法人
	 * @see P:\01.需求變更\理賠\CLM0040.外來人口統一證號格式修正\證號檢核web Service.docx
	 * @param checkId
	 * @category mantis： CLM0040，處理人員：BK007 蘇哲，需求單編號：CLM0040 外來人口統一證號格式修正
	 */
	public String verifyIdentifyNumber() throws Exception {
		String checkId = this.getRequest().getParameter("checkId");
		JSONObject result = VerifyUtil.verifyIdentifyNumber(checkId);
		super.getResponse().setContentType("text/html; charset=UTF-8");
		super.getResponse().getWriter().write(result.toString());
		return NONE;
	}

}
