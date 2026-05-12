package com.sinosoft.app.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.sinosoft.app.common.service.facade.PlatConfigRuleService;
import com.sinosoft.common.schema.model.UtiPlatConfigRule;
/**
 * 平臺規則配置服務實現
 * @author bk007
 */
public class PlatConfigRuleServiceSpringImpl extends GenericDaoHibernate<UtiPlatConfigRule, String> implements PlatConfigRuleService {
	/**
	 * 查詢平臺規則配置
	 * @param key 鍵值
	 * @param serialNo 序列號
	 * @return String 平臺規則配置
	 * @category mantis： CLM0040，處理人員：BK007 蘇哲，需求單編號：CLM0040 外來人口統一證號格式修正
	 */
    public String getPlatConfigRule(String key,String serialNo) { 	  
        String value = null;
        String hql=("from UtiPlatConfigRule where (SYSTEMCODE =? OR SYSTEMCODE =?) and PARAMCODE=? and SERIALNO=? ORDER BY SYSTEMCODE DESC");
        List<UtiPlatConfigRule> utiPlatConfigRuleList = super.findByHql(hql,"prpall","claim",key,serialNo); 
        if (CollectionUtils.isNotEmpty(utiPlatConfigRuleList)) {
        	return utiPlatConfigRuleList.get(0).getRule();
        }
        return value;
    }
}
