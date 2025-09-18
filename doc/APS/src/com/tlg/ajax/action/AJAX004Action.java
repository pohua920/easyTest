package com.tlg.ajax.action;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;
import com.tlg.xchg.entity.CwpRcvAnnounce;
import com.tlg.xchg.entity.CwpUndwrtAnnounce;
import com.tlg.xchg.service.CwpRcvAnnounceService;
import com.tlg.xchg.service.CwpUndwrtAnnounceService;

@SuppressWarnings("serial")
public class AJAX004Action extends BaseAction implements Serializable {
	/* mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面 start */

	private CwpUndwrtAnnounceService cwpUndwrtAnnounceService;
	private CwpRcvAnnounceService cwpRcvAnnounceService;
	private BigDecimal oid;
	private String announceCase;
	private String result;
	private Map<String,String> policyStatusMap = new HashMap<>();

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws Exception {
		
	}
	
	public String findCwpAnnounceData() {
		try {
			formLoad("execute");
			Map<String,Object> params = new HashMap<>(); 
			params.put("oid", oid);
			Result resultSet = null;
			Map<String,Object> map = new LinkedHashMap<>();
			if(announceCase.equals("RCV")) {
				resultSet = cwpRcvAnnounceService.findCwpRcvAnnounceByOid(oid);
				if (null != resultSet.getResObject()) {
					CwpRcvAnnounce resultValue = (CwpRcvAnnounce) resultSet.getResObject();
					map.put("cwpAnnounceData", resultValue);
				}
			}else if(announceCase.equals("UNDWRT")){
				resultSet = cwpUndwrtAnnounceService.findCwpUndwrtAnnounceByOid(oid);
				if (null != resultSet.getResObject()) {
					CwpUndwrtAnnounce resultValue = (CwpUndwrtAnnounce) resultSet.getResObject();
					map.put("cwpAnnounceData", resultValue);
				}
			}		
			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public CwpUndwrtAnnounceService getCwpUndwrtAnnounceService() {
		return cwpUndwrtAnnounceService;
	}

	public void setCwpUndwrtAnnounceService(CwpUndwrtAnnounceService cwpUndwrtAnnounceService) {
		this.cwpUndwrtAnnounceService = cwpUndwrtAnnounceService;
	}

	public CwpRcvAnnounceService getCwpRcvAnnounceService() {
		return cwpRcvAnnounceService;
	}

	public void setCwpRcvAnnounceService(CwpRcvAnnounceService cwpRcvAnnounceService) {
		this.cwpRcvAnnounceService = cwpRcvAnnounceService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public BigDecimal getOid() {
		return oid;
	}

	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}

	public String getAnnounceCase() {
		return announceCase;
	}

	public void setAnnounceCase(String announceCase) {
		this.announceCase = announceCase;
	}

	public Map<String, String> getPolicyStatusMap() {
		return policyStatusMap;
	}

	public void setPolicyStatusMap(Map<String, String> policyStatusMap) {
		this.policyStatusMap = policyStatusMap;
	}	

}