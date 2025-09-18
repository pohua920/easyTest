package com.tlg.ajax.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.Ajax005PrpduserVo;
import com.tlg.prpins.service.PrpduserService;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class AJAX005Action extends BaseAction implements Serializable {
	private PrpduserService prpduserService;
	private String handler1code;
	private String result;

	/*mantis：FIR0310，處理人員：BJ016，需求單編號：FIR0310 住火保經代分行服務人員對照表*/
	@SuppressWarnings("unchecked")
	public String findHandler1codeData() {
		try {
			String handler1codeTemp = getRequest().getParameter("handler1code");
			Map<String,Object> params = new HashMap<>(); 
			params.put("usercode", handler1codeTemp);
			params.put("validstatus", "1");
			
			Map<String,Object> map = new LinkedHashMap<>();
			Result resultSet = prpduserService.selectForAjax005(params);
			if(resultSet != null && resultSet.getResObject() != null) {
				List<Ajax005PrpduserVo> searchResult = (List<Ajax005PrpduserVo>)resultSet.getResObject();
				Ajax005PrpduserVo ajax005PrpduserVo = searchResult.get(0);
				map.put("ajax005PrpduserVo", ajax005PrpduserVo);
			}
				
			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public PrpduserService getPrpduserService() {
		return prpduserService;
	}

	public void setPrpduserService(PrpduserService prpduserService) {
		this.prpduserService = prpduserService;
	}

	public String getHandler1code() {
		return handler1code;
	}

	public void setHandler1code(String handler1code) {
		this.handler1code = handler1code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}