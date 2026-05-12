package com.sinosoft.claim.print.web;

import ins.framework.web.Struts2Action;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 * 水險列印之前的數據準備和校驗
 * @author 中科軟
 *
 */
public class ShipPrintBeforeEditAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private String printType;
	private String bizNoType;
	
	 /**
	  * 水險列印之前的數據準備
	  * @return
	  */
	public String shipPrintBeforeEdit(){
		String forward = "shipPrint";
		HttpServletRequest httpServletRequest = super.getRequest();
		String titleName = ConstantsCollection.SHIPPRINTTYPEINFO.get(this.printType);
		Map<String,String> bizNoTypes = new LinkedHashMap<String,String>();
		if(!CommonUtils.isEmpty(this.bizNoType)){
			for(String s : this.bizNoType.split(",")){
				bizNoTypes.put(s, ConstantsCollection.BIZNOTYPE.get(s));
			}
		}
		httpServletRequest.setAttribute("titleName", titleName);
		httpServletRequest.setAttribute("bizNoTypes", bizNoTypes);
		return forward;
	}
	/**
	 * 水險列印之前的數據校驗
	 * @return
	 */
	public String checkBizNo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/HTML");
//		String data = "";
		String printType = request.getParameter("printType");
		String bizNoType = request.getParameter("bizNoType");
		String bizNo = request.getParameter("bizNo");
		if("PropReplevyReport".equals(printType) && "1".equals(bizNoType) && !"".equals(DataUtils.dbNullToEmpty(bizNo))){
			
		}
		return NONE;
	}

	public String getBizNoType() {
		return bizNoType;
	}

	public void setBizNoType(String bizNoType) {
		this.bizNoType = bizNoType;
	}
	public String getPrintType() {
		return printType;
	}
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	
}
