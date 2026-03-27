package com.tlg.commons.util.api.rest.blockChain.util;

import ins.framework.common.ServiceFactory;

import java.util.List;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.schema.model.PrpDcode;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public class ConfigUtil {

	public static String getIp() throws Exception{
		
		CodeService codeService = ((CodeService)ServiceFactory.getService("codeService"));
		List<PrpDcode> prpDcodeList = codeService.getNewCodeCode("CLBlockChain", "IP");
		if(prpDcodeList == null || prpDcodeList.size() == 0){
			throw new Exception("無法取得區塊鍊網址");
		}
		return prpDcodeList.get(0).getNewCodeCode();
	}
	
	public static String getApiKey() throws Exception{
		
		CodeService codeService = ((CodeService)ServiceFactory.getService("codeService"));
		List<PrpDcode> prpDcodeList = codeService.getNewCodeCode("CLBlockChain", "api_key");
		if(prpDcodeList == null || prpDcodeList.size() == 0){
			throw new Exception("無法取得區塊鍊API_KEY");
		}
		return prpDcodeList.get(0).getNewCodeCode();
	}
}
