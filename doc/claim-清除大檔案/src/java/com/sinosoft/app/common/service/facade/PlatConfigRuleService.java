package com.sinosoft.app.common.service.facade;

/**
 * 平臺規則配置服務
 * @author bk007
 * @category mantis： CLM0040，處理人員：BK007 蘇哲，需求單編號：CLM0040 外來人口統一證號格式修正
 */
public interface PlatConfigRuleService {
	/**
	 * 查詢平臺規則配置
	 * @param key 鍵值
	 * @param serialNo 序列號
	 * @return String 平臺規則配置
	 * @category mantis： CLM0040，處理人員：BK007 蘇哲，需求單編號：CLM0040 外來人口統一證號格式修正
	 */
   public String getPlatConfigRule(String key,String serialNo);
}
