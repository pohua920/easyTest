/*
 * @(#)BLStandardAction.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.facade;

import java.util.List;
import java.util.Map;

import com.sinosoft.claim.schema.model.UtiUwCondition;
import com.sinosoft.claim.schema.model.UtiUwUserCondition;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <中科软>
 * @Date    <Feb 21, 2013>
 * @description 
 */
public interface StandardService {
	//车险核赔模板基础数据初始化
	public List<UtiUwCondition> getStandardList(String businessType, String riskCode, int modelNo, int nodeNo, String userCode, String comCode) throws Exception;
	//根据险别条件取得核赔条件
	public List<UtiUwCondition> getStandardList(String businessType, String riskCode, int modelNo, int nodeNo, String userCode, String comCode, Object[] kindList) throws Exception;
	
	/**
	 * 如果从UtiUwCondition表中找不到当前机构则查找上级机构
	 */
	public List<UtiUwCondition> getUtiUwConditionStandarList(String calComCode, int modelNo, int nodeNo, String riskCode, String uwType) throws Exception;
	
	
	/**
	 * 从UtiUwUserCondition表里查找该人员是否有自己的核赔权限
	 */
	public List<UtiUwUserCondition> getUtiUwLevetDto(String userCode, String calComCode, String riskCode, int modelNo, int nodeNo, String uwType) throws Exception;
	
	
	/**
	 * 
	 * @param 重新排序标准List，使CodeTypeValue字断=OtherValue的在最下面
	 */
	public List<UtiUwCondition> sortUtiUwConditionStandarListByOtherValue(List<UtiUwCondition> utiUwConditionStandarList);
	
	/**
	 * 
	 * @param 非车险需要把险别代码的值写入CodeTypeValue
	 */
	public List<UtiUwCondition> dealUtiUwConditionStandarList(List<UtiUwCondition> utiUwConditionStandarList, Map<String, String> factorValueNoMap);
	
}
