package com.tlg.aps.webService.claimBlockChainService.client.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.util.AppContext;
import com.tlg.util.Result;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public class ConfigUtil {

	
	
	@SuppressWarnings("unchecked")
	public static String getIp() throws Exception{
		
		PrpdNewCodeService prpdNewCodeService = (PrpdNewCodeService)AppContext.getBean("prpdNewCodeService");
		Map<String, String> params = new HashMap<String, String>();
		params.put("codetype", "CLBlockChain");
		params.put("codecode", "IP");
		Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
		if(result.getResObject() == null){
			throw new Exception("無法取得區塊鍊網址");
		}
		List<PrpdNewCode> prpdNewCodeList = (List<PrpdNewCode>)result.getResObject();
		if(prpdNewCodeList == null || prpdNewCodeList.size() == 0){
			throw new Exception("無法取得區塊鍊網址");
		}
		return prpdNewCodeList.get(0).getNewcodecode();
	}
	
	@SuppressWarnings("unchecked")
	public static String getApiKey() throws Exception{
	
		PrpdNewCodeService prpdNewCodeService = (PrpdNewCodeService)AppContext.getBean("prpdNewCodeService");
		Map<String, String> params = new HashMap<String, String>();
		params.put("codetype", "CLBlockChain");
		params.put("codecode", "api_key");
		Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
		if(result.getResObject() == null){
			throw new Exception("無法取得區塊鍊API_KEY");
		}
		List<PrpdNewCode> prpdNewCodeList = (List<PrpdNewCode>)result.getResObject();
		if(prpdNewCodeList == null || prpdNewCodeList.size() == 0){
			throw new Exception("無法取得區塊鍊API_KEY");
		}
		return prpdNewCodeList.get(0).getNewcodecode();
		
	}
}
