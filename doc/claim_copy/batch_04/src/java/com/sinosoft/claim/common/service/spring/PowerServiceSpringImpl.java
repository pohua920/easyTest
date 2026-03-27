package com.sinosoft.claim.common.service.spring;

import java.util.ArrayList;
import java.util.List;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.common.service.facade.PowerService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.UtiUserGradePower;
import com.sinosoft.claim.schema.model.UtiUserGradePowerId;
import com.sinosoft.claim.schema.service.facade.UtiUserGradePowerService;
import com.sinosoft.sysframework.common.util.SqlUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 用户权限
 * @author 中科软
 */
public class PowerServiceSpringImpl extends GenericDaoHibernate<UtiUserGradePower, UtiUserGradePowerId> implements PowerService{
	private static CacheService cacheManager = CacheManager.getInstance("PowerServiceSpringImpl");
	private UtiUserGradePowerService utiUserGradePowerService;
	/**
	 * 添加用户的查询险种的权限。
	 * @param user 用户
	 * @param tableName 表名称
	 * @return
	 * @throws Exception
	 */
	public String addRiskPower(UserDto user,String tableName,String systemCode) throws Exception {
		return addRiskPower(user.getUserCode(),user.getComCode(),"",tableName,systemCode);
	}
	/**
	 * 添加用户的查询险种的权限。
	 * @param userCode 用户名称
	 * @param comCode 登录机构
	 * @param gradeCodes 角色
	 * @param tableName 表名称
	 * @return
	 * @throws Exception
	 */
	public String addRiskPower(String userCode, String comCode, String gradeCodes, String tableName,String systemCode) throws Exception {
		String value = "";
		if (CommonUtils.isEmpty(userCode)) {
			throw new UserException(-1, 0, "系統權限校驗", "參數\"員工代碼\"沒有值");
		}
		if (CommonUtils.isEmpty(tableName)){
			throw new UserException(-1, 0, "系統權限校驗", "參數\"表名\"沒有值");
		}
		if (CommonUtils.isEmpty(comCode) && CommonUtils.isEmpty(gradeCodes)){
			throw new UserException(-1, 0, "系統權限校驗", "參數\"登錄機構\"和\"登錄崗位列表\"必須有一個有值");
		}
		if(CommonUtils.isEmpty(systemCode)){
			systemCode = "claim";
		}
		//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 START
//		String key = cacheManager.generateCacheKey("addRiskPower",userCode,comCode,gradeCodes,tableName,systemCode);
//		value = (String) cacheManager.getCache(key);
//		if (CommonUtils.isEmpty(value)) {
			value = this.addRiskPowerImpl(userCode, comCode, gradeCodes, tableName,systemCode);
//			cacheManager.putCache(key, value);
//		}
		//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 END
		return value;
	}
	/**
	 * 添加用户的查询险种的权限。
	 * @param userCode 用户名称
	 * @param comCode 登录机构
	 * @param gradeCodes 角色
	 * @param tableName 表名称
	 * @return
	 * @throws Exception
	 */
	private String addRiskPowerImpl(String userCode, String comCode, String gradeCodes, String tableName,String systemCode) throws Exception {
		StringBuffer conditions = new StringBuffer("UserCode = '").append(userCode).append("'");
		conditions.append(SqlUtils.convertString("ComCode", comCode));
		if(!CommonUtils.isEmpty(gradeCodes)){
			conditions.append(SqlUtils.convertString("GradeCode", gradeCodes));
		}else{
			conditions.append(" and gradeCode in(Select distinct(GradeCode) From UtiGradeTask Where TaskCode = '").append(systemCode).append("')");
		}
		StringBuffer buffer = new StringBuffer();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions.toString());
		List<UtiUserGradePower> list = utiUserGradePowerService.findUtiUserGradePower(queryRule);
		List<String> riskCodeList = new ArrayList<String>(list.size());
		for (UtiUserGradePower utiUserGradePower : list) {
			if (!CommonUtils.isEmpty(utiUserGradePower.getPermitRiskCode())&&!riskCodeList.contains(utiUserGradePower.getPermitRiskCode())) {
				if (utiUserGradePower.getPermitRiskCode().equals("*")){
					return " AND (1 = 1) ";
				}
				buffer.append(",");
				buffer.append(utiUserGradePower.getPermitRiskCode());
				riskCodeList.add(utiUserGradePower.getPermitRiskCode());
			}
		}
		if(buffer.length()==0){
			return " AND (1 = 0) ";
		}
		String[] riskCodeArray = StringUtils.split(buffer.substring(1), ",");
		buffer.setLength(0);
		riskCodeList.clear();
		buffer.append(" AND ").append(tableName).append(".RiskCode IN (");
		for (int i = 0; i < riskCodeArray.length; i++) {
			if(!riskCodeList.contains(riskCodeArray[i])){
				buffer.append("'").append(riskCodeArray[i]).append("',");
				riskCodeList.add(riskCodeArray[i]);
			}
		}
		String sql = buffer.substring(0,buffer.length()-1)+") ";
		return sql;
	}
	public UtiUserGradePowerService getUtiUserGradePowerService() {
		return utiUserGradePowerService;
	}
	public void setUtiUserGradePowerService(UtiUserGradePowerService utiUserGradePowerService) {
		this.utiUserGradePowerService = utiUserGradePowerService;
	}
	
}
