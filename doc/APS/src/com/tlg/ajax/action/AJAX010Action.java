package com.tlg.ajax.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.sales.entity.Prpdagent;
import com.tlg.sales.service.PrpdagentService;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;

/** mantis：SALES0008，處理人員：BJ085，需求單編號：SALES0008 保經代分支機構維護程式需求*/
@SuppressWarnings("serial")
public class AJAX010Action extends BaseAction implements Serializable {
	private PrpdagentService prpdagentService;
	private String result;
	private String agentcode;
	private String orgicode;
	
	@SuppressWarnings("unchecked")
	public String findAgentnameByParams() {
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("orgicode", orgicode);
			//mantis：SALES0008，處理人員：BJ085，需求單編號：SALES0008 保經代分支機構維護程式需求-修正錯誤查詢條件
			String[] agenttypeList = {"2","3"};
			params.put("agenttypeList", agenttypeList);
			params.put("validstatus", "1");
			Result searchResult = prpdagentService.findPrpdagentByParams(params);
			Map<String,Object> map = new LinkedHashMap<>();
			
			boolean isExist = false;
			if (null != searchResult.getResObject()) {
				List<Prpdagent> resultList =  (List<Prpdagent>) searchResult.getResObject();
				if(!resultList.isEmpty()) {
					isExist = true;
					Prpdagent prpdagent = resultList.get(0);
					map.put("agentname", prpdagent.getAgentname());
					map.put("agentcode", prpdagent.getAgentcode());
				}
			}
			map.put("isExist", isExist);
			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}


	public PrpdagentService getPrpdagentService() {
		return prpdagentService;
	}

	public void setPrpdagentService(PrpdagentService prpdagentService) {
		this.prpdagentService = prpdagentService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getAgentcode() {
		return agentcode;
	}

	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
	}

	public String getOrgicode() {
		return orgicode;
	}

	public void setOrgicode(String orgicode) {
		this.orgicode = orgicode;
	}

}