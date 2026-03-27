package cn.com.sinosoft.dms.webservice.facade;

import java.util.Map;

import cn.com.sinosoft.inf.dict.server.common.DictPage;

public interface DictionaryNewService {
	
	//mantis：CAR0027，處理人員：DP0706，需求單編號：mantis：CAR0027 :因DMS 正式機上的CODE與開發版本有所差異故將DNS查詢方法抽出並另外改寫
	public DictPage savePrpDcustomerUnitNew(String systemCode, Map values) throws Exception;

}
