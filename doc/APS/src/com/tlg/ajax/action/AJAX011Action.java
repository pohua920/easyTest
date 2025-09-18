package com.tlg.ajax.action;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.formatAddressService.FormatAddressCheckService;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;

/** mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 */
@SuppressWarnings("serial")
public class AJAX011Action extends BaseAction implements Serializable {
	
	private FormatAddressCheckService formatAddressCheckService;
	private String result;
	
	
	@SuppressWarnings("unchecked")
	public String formatAddressCheck() {
		Map<String,Object> map = new LinkedHashMap<>();
		try {
			
			String address = getRequest().getParameter("address");
			map = formatAddressCheckService.formatAddressCheck(address);
			result = JsonUtil.getJSONString(map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}


	



	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}






	public FormatAddressCheckService getFormatAddressCheckService() {
		return formatAddressCheckService;
	}






	public void setFormatAddressCheckService(FormatAddressCheckService formatAddressCheckService) {
		this.formatAddressCheckService = formatAddressCheckService;
	}
	
}