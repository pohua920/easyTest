package com.tlg.ajax.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.dms.service.PrpdexchService;
import com.tlg.prpins.service.PrpdhighareaMcService;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class AJAX007Action extends BaseAction implements Serializable {

	private PrpdexchService prpdexchService;
	private PrpdhighareaMcService prpdhighareaMcService;
	private String exchdate;
	private String basecurrency;
	private String countycode;
	private String shortcode;
	private String result;

	/* mantis：MAR0029，處理人員：BJ085，需求單編號：MAR0029 水險兌換率幣別  */
	public String findExchdateByUK() {
		try {
			Map<String,String> params = new HashMap<>();
			params.put("exchdate", exchdate);
			params.put("basecurrency", basecurrency);
			params.put("exchcurrency", "NTD");
			Result searchResult = prpdexchService.findPrpdexchByUK(params);
			Map<String,Object> map = new LinkedHashMap<>();
			if (null != searchResult.getResObject()) {
				map.put("isExist", true);
			}else{
				map.put("isExist", false);
			}
			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	/* mantis：MAR0037，處理人員：BJ085，需求單編號：MAR0037 高風險地區新增維護檔  */
	public String findPrpdhighareaMcByUK() {
		try {
			Map<String,String> params = new HashMap<>();
			params.put("countycode", countycode);
			params.put("shortcode", shortcode);
			Result searchResult = prpdhighareaMcService.findPrpdhighareaMcByUK(params);
			Map<String,Object> map = new LinkedHashMap<>();
			if (null != searchResult.getResObject()) {
				map.put("isExist", true);
			}else{
				map.put("isExist", false);
			}
			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public PrpdexchService getPrpdexchService() {
		return prpdexchService;
	}
	public void setPrpdexchService(PrpdexchService prpdexchService) {
		this.prpdexchService = prpdexchService;
	}
	public String getExchdate() {
		return exchdate;
	}
	public void setExchdate(String exchdate) {
		this.exchdate = exchdate;
	}
	public String getBasecurrency() {
		return basecurrency;
	}
	public void setBasecurrency(String basecurrency) {
		this.basecurrency = basecurrency;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public PrpdhighareaMcService getPrpdhighareaMcService() {
		return prpdhighareaMcService;
	}
	public void setPrpdhighareaMcService(PrpdhighareaMcService prpdhighareaMcService) {
		this.prpdhighareaMcService = prpdhighareaMcService;
	}
	public String getCountycode() {
		return countycode;
	}
	public void setCountycode(String countycode) {
		this.countycode = countycode;
	}
	public String getShortcode() {
		return shortcode;
	}
	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}
}