package com.tlg.ajax.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.tlg.dms.entity.PrpdRisk;
import com.tlg.dms.service.PrpdRiskService;
import com.tlg.exception.SystemException;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class AJAX003Action extends ActionSupport implements Serializable {
	/* mantis：OTH0087，處理人員：BJ085，需求單編號：OTH0087 AML手動登錄 start */

	private PrpdRiskService prpdRiskService;
	private String classcode;
	private String result;
	
	@SuppressWarnings("unchecked")
	public String findRiskCode() throws SystemException, Exception {
		String riskcode = "";
		Map<String,String> riskcodeMap = new LinkedHashMap<>();
		Map<String,Object> map = new HashMap<>();
		if(classcode.equals("AB")) {
			riskcode = "A01B01";
			riskcodeMap.put("A01B01", "A01B01-強制車險+任意車險(特例)");
		}else {
			Map<String,String> params = new HashMap<>();
			params.put("classcode", classcode);
			Result resultSet = prpdRiskService.findPrpdRiskByParams(params);
			List<PrpdRisk> riskcodeList = new ArrayList<>();
			if(resultSet.getResObject()!=null) {
				riskcodeList = (List<PrpdRisk>) resultSet.getResObject();
				for(PrpdRisk prpdRisk:riskcodeList) {
					riskcodeMap.put(prpdRisk.getRiskcode(), prpdRisk.getRiskcode()+"-"+prpdRisk.getRiskcname());
				}
				Set set = riskcodeMap.entrySet();
				Iterator ir = set.iterator();
				
				if (ir.hasNext()) {
					Map.Entry mapentry = (Map.Entry) ir.next();
					riskcode = (String) mapentry.getKey();
				}			
			}
		}
		map.put("riskcode", riskcode);
		map.put("riskcodes", riskcodeMap);
		result = JsonUtil.getJSONString(map);
		
		return Action.SUCCESS;
	}

	public PrpdRiskService getPrpdRiskService() {
		return prpdRiskService;
	}

	public void setPrpdRiskService(PrpdRiskService prpdRiskService) {
		this.prpdRiskService = prpdRiskService;
	}

	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}