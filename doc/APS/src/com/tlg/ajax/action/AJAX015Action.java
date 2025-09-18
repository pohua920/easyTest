package com.tlg.ajax.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.Aps046ResultVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.service.FetclaimmainService;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;

/** mantis：MOB0019，處理人員：BJ085，需求單編號：MOB0019 理賠審核確認作業 */
@SuppressWarnings("serial")
public class AJAX015Action extends BaseAction implements Serializable {
	private String result;
	private String wda00;
	private FetclaimmainService fetclaimmainService;
	
	/** 理賠審核確認作業，頁面上輸入資料日期，自動帶出賠付總金額、賠付總案件數*/
	public String countForReviewData() throws SystemException, Exception {
		try {
			Map<String, String> params = new HashMap<>();
			params.put("wda00", wda00);
			params.put("wda61", "4");
			params.put("nclaim", "N");

			Result searchResult = fetclaimmainService.findForReviewNumByParams(params);
			Aps046ResultVo resultvo = (Aps046ResultVo) searchResult.getResObject();
			
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("count", resultvo.getCount());
			map.put("wde22", resultvo.getWde22());

			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	/** 按下「審核通過」時查詢輸入的資料年月是否已審核*/
	public String countNclaimData() throws Exception{
		Map<String, String> params = new HashMap<>();
		params.put("wda00",wda00);
		params.put("nclaimNotNull","Y");
		int nclaimCount = fetclaimmainService.countFetclaimmain(params);
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("nclaimCount", nclaimCount);
		
		result = JsonUtil.getJSONString(map);
		
		return Action.SUCCESS;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getWda00() {
		return wda00;
	}

	public void setWda00(String wda00) {
		this.wda00 = wda00;
	}

	public FetclaimmainService getFetclaimmainService() {
		return fetclaimmainService;
	}

	public void setFetclaimmainService(FetclaimmainService fetclaimmainService) {
		this.fetclaimmainService = fetclaimmainService;
	}
}