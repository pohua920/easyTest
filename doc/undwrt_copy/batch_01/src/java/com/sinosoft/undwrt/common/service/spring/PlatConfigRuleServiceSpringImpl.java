package com.sinosoft.undwrt.common.service.spring;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.bpsdriver.util.SystemCode;
import com.sinosoft.common.schema.model.UtiPlatConfigRule;
import com.sinosoft.undwrt.common.service.facade.PlatConfigRuleService;
/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
public class PlatConfigRuleServiceSpringImpl extends
GenericDaoHibernate<UtiPlatConfigRule, String> implements PlatConfigRuleService {
    public static final String SYSTEM_CODE = SystemCode.PRPINS;
    //private static HashMap props = new HashMap();
    /**初始化緩存實例 lijianming  modify 2010-1-11  增加緩存的處理*/
	private static CacheService cacheManager = CacheManager.getInstance("PlatConfigRuleServiceSpringImpl");
	/**
	 * 查詢平臺規則配置
	 * @param key 鍵值
	 * @param serialNo 序列號
	 * @return String 平臺規則配置
	 */
    public String getPlatConfigRule(String key,String serialNo) { 	  
        String value = null;
        //增加緩存的處理
        String cacheManagerKey = cacheManager.generateCacheKey("getPlatConfigRule",key,serialNo);
        Object cacheObj = cacheManager.getCache(cacheManagerKey);
        if(cacheObj!=null){
        	value = (String)cacheObj;
        	return value;
		}
        
//        String  hql="from UtiPlatConfigRule where SYSTEMCODE = '" + SYSTEM_CODE + "' and PARAMCODE=? and SERIALNO=?";
        String  hql=("from UtiPlatConfigRule where SYSTEMCODE = ? and PARAMCODE=? and SERIALNO=?");
        List<UtiPlatConfigRule> utiPlatConfigRuleList = super.findByHql(hql,SYSTEM_CODE,key,serialNo); 
        	 
        if (utiPlatConfigRuleList != null && utiPlatConfigRuleList.size() > 0) {
            value = utiPlatConfigRuleList.get(0).getRule();
            cacheManager.putCache(cacheManagerKey, value);
        }
        return value;
    }
    
    /**
     * mantis： EGN0055 ，處理人員： DP0706 ，需求單編號： EGN0055 全險「證件號碼」檢核
	 * 查詢平臺規則配置(承保or全系統)
	 * @param key 鍵值
	 * @param serialNo 序列號
	 * @return String 平臺規則配置
	 */
    public String getPlatConfigRuleAll(String key,String serialNo) { 	  
        String value = null;
        //增加緩存的處理
        String cacheManagerKey = cacheManager.generateCacheKey("getPlatConfigRule",key,serialNo);
        Object cacheObj = cacheManager.getCache(cacheManagerKey);
        if(cacheObj!=null){
        	value = (String)cacheObj;
        	return value;
		}
        
        String  hql=("from UtiPlatConfigRule where SYSTEMCODE in ('prpins','prpall') and PARAMCODE=? and SERIALNO=? order by systemcode desc");
      
        List<UtiPlatConfigRule> utiPlatConfigRuleList = super.findByHql(hql,key,serialNo); 
        	 
        if (utiPlatConfigRuleList != null && utiPlatConfigRuleList.size() > 0) {
            value = utiPlatConfigRuleList.get(0).getRule();
            cacheManager.putCache(cacheManagerKey, value);
        }
        return value;
    }
}
