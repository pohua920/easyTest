package com.sinosoft.undwrt.common.service.facade;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 * 平臺規則配置服務
 * @author Sinosoft
 */
public interface PlatConfigRuleService {
	/**
	 * 查詢平臺規則配置
	 * @param key 鍵值
	 * @param serialNo 序列號
	 * @return String 平臺規則配置
	 */
   public String getPlatConfigRule(String key,String serialNo);
   
	/**
	 * mantis： EGN0055 ，處理人員： DP0706 ，需求單編號： EGN0055 全險「證件號碼」檢核
	 * 查詢平臺規則配置(承保or全系統)
	 * @param key 鍵值
	 * @param serialNo 序列號
	 * @return String 平臺規則配置
	 */
  public String getPlatConfigRuleAll(String key,String serialNo);
}
