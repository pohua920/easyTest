package com.tlg.ajax.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.sales.service.HandlercodeTmpService;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：SALES0007，處理人員：BJ085，需求單編號：SALES0007 新增業務人員所屬服務人員維護*/
@SuppressWarnings("serial")
public class AJAX009Action extends BaseAction implements Serializable {
	private HandlercodeTmpService handlercodeTmpService;
	private String result;
	private String orgicode;
	private String cmem00;
	
	public String findHandlercodeVaildByParams() {
		try {
			Map<String,String> params = new HashMap<>();
			params.put("orgicode", orgicode);
			if(!StringUtil.isSpace(cmem00)) {
				params.put("cmem00", cmem00);
			}
			params.put("validstatus", "1");
			Result searchResult = handlercodeTmpService.findHandlercodeIsValidByParams(params);
			Map<String,Object> map = new LinkedHashMap<>();
			
			//查詢結果為空或cmem18有值都為無效
			boolean isExist = false;
			if (null != searchResult.getResObject()) {
				Map resultMap = (Map) searchResult.getResObject();
				if(StringUtil.isSpace((String) resultMap.get("cmem18"))) {
					isExist = true;
				}
			}
			map.put("isExist", isExist);
			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public HandlercodeTmpService getHandlercodeTmpService() {
		return handlercodeTmpService;
	}

	public void setHandlercodeTmpService(HandlercodeTmpService handlercodeTmpService) {
		this.handlercodeTmpService = handlercodeTmpService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOrgicode() {
		return orgicode;
	}

	public void setOrgicode(String orgicode) {
		this.orgicode = orgicode;
	}

	public String getCmem00() {
		return cmem00;
	}

	public void setCmem00(String cmem00) {
		this.cmem00 = cmem00;
	}
}