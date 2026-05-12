/*
 * @(#)BLStandardAction.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.UtiUwCondition;
import com.sinosoft.claim.schema.model.UtiUwLevel;
import com.sinosoft.claim.schema.model.UtiUwUserCondition;
import com.sinosoft.claim.schema.service.facade.UtiUwConditionService;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.claim.schema.service.facade.UtiUwUserConditionService;
import com.sinosoft.claim.undwrt.service.facade.StandardService;
import com.sinosoft.undwrt.dto.custom.UwFactorConstants;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 
 */
public class StandardServiceSpringImpl extends GenericDaoHibernate implements StandardService{
	private PrpDcompanyService prpDcompanyService;
	private CodeService codeService;
	private UtiUwLevelService utiUwLevelService;
	private UtiUwUserConditionService utiUwUserConditionService;
	private UtiUwConditionService utiUwConditionService;
	
	/***
	 * 获取人员的核赔权限 
	 * @param businessType 业务类型
	 * @param riskCode 险种代码
	 * @param modelNo 流程模板好
	 * @param nodeNo 流程节点号
	 * @param userCode 人员代码
	 * @param comCode 机构代码
	 * @return 人员的核赔权限
	 */
	public List<UtiUwCondition> getStandardList(String businessType, String riskCode, int modelNo, int nodeNo, String userCode, String comCode) throws Exception {
		String uwType = "";
		if ("precompensate".equalsIgnoreCase(businessType)) {
			uwType = "Y";
		} else if ("compensate".equalsIgnoreCase(businessType)) {
			uwType = "C";
		} else {
			throw new Exception("无此业务类型");
		}
		String calComCode = comCode;
		// 先判断该人员是否有自己的权限。通过comCode,userCode riskCode modelNo nodeNo uwType 去
		// UtiUwUserCondition表里查询
		List<UtiUwUserCondition> conditionList = this.getUtiUwLevetDto(userCode, calComCode, riskCode, modelNo, nodeNo, uwType);
		List<UtiUwCondition> list = null;
		// 如果conditionList为空则表示该人员无权限，取机构的权限
		if (conditionList == null || conditionList.isEmpty()) {
			list = this.getUtiUwConditionStandarList(calComCode, modelNo, nodeNo, riskCode, uwType);
		}
		// 对标准数据进行排序
		return this.sortUtiUwConditionStandarListByOtherValue(list);
	}
	/***
	 * 根据险别条件取得核赔条件
	 * @param businessType 业务类型
	 * @param riskCode 险种代码
	 * @param modelNo 流程模板好
	 * @param nodeNo 流程节点号
	 * @param userCode 人员代码
	 * @param comCode 机构代码
	 * @param kindList 险别集合
	 * @return 核赔权限
	 */
	public List<UtiUwCondition> getStandardList(String businessType, String riskCode, int modelNo, int nodeNo, String userCode, String comCode, Object[] kindList) throws Exception {
		String uwType;
		if ("precompensate".equalsIgnoreCase(businessType)) {
			uwType = "Y";
		}else if("compensate".equalsIgnoreCase(businessType)) {
			uwType = "C";
		}else{
			throw new Exception("无此业务类型");
		}
		String  calComCode = comCode;
		//取得各险别所对应的factorValueNo
		Map<String, String> factorValueNoMap = this.getFactorValueNoMap(riskCode, kindList);
		// 先判断该人员是否有自己的权限。通过comCode,userCode riskCode modelNo nodeNo uwType 去
		// UtiUwUserCondition表里查询
		Object [] factorValueNos = factorValueNoMap.keySet().toArray();
		String conditiontemp = "('1','";//将险种级别的因子也包含进去
		if(factorValueNos!=null&&factorValueNos.length>0){
			for(int i = 0;i<factorValueNos.length;i++){
				conditiontemp += factorValueNos[i].toString();
				conditiontemp += "','";
			}
		}
		conditiontemp = conditiontemp.substring(0, conditiontemp.length()-2);
		conditiontemp +=")";
		StringBuffer statement = new StringBuffer(200);
        
        statement.append(" RISKCODE = '");
        statement.append(riskCode);
        statement.append("' AND MODELNO = '");
        statement.append(modelNo);
        statement.append("' AND NODENO = '");
        statement.append(nodeNo);
        statement.append("' AND UWTYPE = '");
        statement.append(uwType);
        statement.append("' AND VALIDSTATUS = '1'");
        if(factorValueNos!=null&&factorValueNos.length>0){
        	statement.append(" AND FACTORVALUENO IN ");
        	statement.append(conditiontemp);
        }
        String conditions = statement.toString();
		List<UtiUwUserCondition> tempList = this.getUtiUwUserConditionService().findByConditions(" usercode = '" + userCode + "' and comcode = '" + calComCode + "' and " + conditions);
		List<UtiUwCondition> list = null;
		// 如果conditionList为空则表示该人员无权限，取机构的权限
		if (tempList == null || tempList.size() == 0) {
			PrpDcompany prpDcompany = null;
			boolean isFind = false;
			while (true) {
				if (this.getUtiUwConditionService().getCount(" comcode = '" + calComCode + "' and " + conditions) > 0) {
					isFind = true;
					break;
				}
				// 获取不到当前机构的路径条件则查找上级机构代码
				prpDcompany = prpDcompanyService.findByPrimaryKey(calComCode);
				if ("1".equals(prpDcompany.getComLevel())) {
					break;
				} else {
					calComCode = prpDcompany.getPrpDcompany().getComCode();
				}
			}
			if (isFind) {
				list = this.getUtiUwConditionService().findByConditions(" comcode = '" + calComCode + "' and " + conditions);
			}
		}
		//对标准数据进行排序
		
		list = this.sortUtiUwConditionStandarListByOtherValue(list);
		String riskType = codeService.translateRiskCodetoRiskType(riskCode);
		if (!"D".equalsIgnoreCase(riskType)) {// 非车险需要把险别代码的值写入CodeTypeValue
			list = this.dealUtiUwConditionStandarList(list, factorValueNoMap);
		}
		return list;
	}
	
	private Map<String, String> getFactorValueNoMap(String riskCode, Object[] kindList) {
		Map<String, String> factorValueNoMap = new HashMap<String, String>();
		String conditiontemp = "('";
		if (kindList != null && kindList.length > 0) {
			for (int i = 0; i < kindList.length; i++) {
				conditiontemp += kindList[i];
				conditiontemp += "','";
			}
		} else {
			conditiontemp += "','";
		}
		conditiontemp = conditiontemp.substring(0, conditiontemp.length() - 2);
		conditiontemp += ")";
		StringBuffer statement = new StringBuffer(200);
		statement.append("SELECT ");
		statement.append("FactorValueNo,KindCode ");
		statement.append("FROM utiuwkindcondition WHERE ");
		statement.append("riskcode = '");
		statement.append(riskCode);
		statement.append("' and ");
		statement.append("kindCode in ");
		statement.append(conditiontemp);
		List<Object[]> resultList = super.getSession().createSQLQuery(statement.toString()).list();
		if (resultList != null && !resultList.isEmpty()) {
			for (Object[] object : resultList) {
				factorValueNoMap.put(String.valueOf(object[0]), String.valueOf(object[1]));
			}
		}
		return factorValueNoMap;
	}
	
	/***
	 * 获取机构的核赔权限
	 * @param calComCode 机构代码
	 * @param modelNo 核赔工作流模板号码
	 * @param nodeNo 核赔工作流节点 
	 * @param riskCode 险种代码
	 * @param uwType 核赔类型
	 * @return 
	 */
	public List<UtiUwCondition> getUtiUwConditionStandarList(String calComCode, int modelNo, int nodeNo, String riskCode, String uwType) throws Exception {
		PrpDcompany prpDcompany = null;
		boolean isFind = false;
		while (true) {
			if (this.getUtiUwConditionService().findCountByConditions(calComCode, modelNo, nodeNo, riskCode, uwType)) {
				isFind = true;
				break;
			}
			// 获取不到当前机构的路径条件则查找上级机构代码
			prpDcompany = prpDcompanyService.findByPrimaryKey(calComCode);
			if ("1".equals(prpDcompany.getComLevel())) {
				break;
			} else {
				calComCode = prpDcompany.getPrpDcompany().getComCode();
			}
		}
		List<UtiUwCondition> utiUwConditionList = new ArrayList<UtiUwCondition>();
		if (isFind) {
			String str = "COMCODE = '" + calComCode + "' AND MODELNO = " + modelNo + " AND NODENO = " + nodeNo + " AND RISKCODE = '" + riskCode + "' AND UWTYPE = '" + uwType + "' AND ValidStatus = '1'";
			utiUwConditionList = this.getUtiUwConditionService().findByConditions(str);
		}
		return utiUwConditionList;
	}
	
	/***
	 * 获取人员的核赔权限 
	 * @param userCode 人员代码
	 * @param calComCode 机构代码
	 * @param riskCode 险种代码
	 * @param modelNo 核赔工作流模板号
	 * @param nodeNo 核赔节点号
	 * @param uwType 核赔类型
	 * @return
	 */
	public List<UtiUwUserCondition> getUtiUwLevetDto(String userCode, String calComCode, String riskCode, int modelNo, int nodeNo, String uwType) throws Exception {
		List<UtiUwUserCondition> conditionList = null;
		PrpDcompany prpDcompany = null;
		UtiUwLevel utiUwLevel = null;
		prpDcompany = new PrpDcompany();
		// 如果该人员有权限，并且有效，则取人员权限,如果未找到该人员权限、该人员权限无孝，则取机构代码
		while (true) {
			utiUwLevel = utiUwLevelService.findByPrimaryKeyAndValidStatus(userCode, calComCode, riskCode, modelNo, nodeNo, uwType);
			if (utiUwLevel != null) {
				break;
			}
			// 获取不到当前机构的路径条件则查找上级机构代码
			prpDcompany = prpDcompanyService.findByPrimaryKey(calComCode);
			if ("1".equals(prpDcompany.getComLevel())) {
				break;
			} else {
				calComCode = prpDcompany.getPrpDcompany().getComCode();
			}
		}
		if (utiUwLevel != null && "1".equals(utiUwLevel.getFlag())) {
			String conditions = "ComCode= '" + calComCode + "' AND ModelNo= " + modelNo + "AND riskCode= '" + riskCode + "' AND NodeNo =" + nodeNo + " AND userCode= '" + userCode + "'";
			conditionList = utiUwUserConditionService.findGroupByConditions(conditions);
		}
		return conditionList;
	}
	
	/***
	 * 对权限讯息进行排序（使CodeTypeValue字断=OtherValue的在最下面）
	 * @param utiUwConditionStandarList 
	 * @return 
	 */
	public List<UtiUwCondition> sortUtiUwConditionStandarListByOtherValue(List<UtiUwCondition> utiUwConditionStandarList) {
		if (utiUwConditionStandarList == null) {
			return utiUwConditionStandarList;
		}
		List<UtiUwCondition> utiUwConditionNonOther = new ArrayList<UtiUwCondition>();
		List<UtiUwCondition> utiUwConditionOther = new ArrayList<UtiUwCondition>();
		List<UtiUwCondition> utiUwConditionSort = new ArrayList<UtiUwCondition>();
		for (Iterator<UtiUwCondition> iter = utiUwConditionStandarList.iterator(); iter.hasNext();) {
			UtiUwCondition utiUwConditionDto = (UtiUwCondition) iter.next();
			if (UwFactorConstants.Sign.OTHER.equals(utiUwConditionDto.getFactorValue())) {
				utiUwConditionOther.add(utiUwConditionDto);
			} else {
				utiUwConditionNonOther.add(utiUwConditionDto);
			}
		}
		utiUwConditionSort.addAll(utiUwConditionNonOther);
		utiUwConditionSort.addAll(utiUwConditionOther);
		return utiUwConditionSort;
	}

	/***
	 * 非车核赔权限讯息处理（把险别代码的值写入CodeTypeValue）
	 * @param utiUwConditionStandarList 
	 * @param factorValueNoMap  
	 * @return
	 */
	public List<UtiUwCondition> dealUtiUwConditionStandarList(List<UtiUwCondition> utiUwConditionStandarList, Map<String, String> factorValueNoMap) {
		if (utiUwConditionStandarList == null) {
			return utiUwConditionStandarList;
		}
		for (Iterator<UtiUwCondition> iter = utiUwConditionStandarList.iterator(); iter.hasNext();) {
			UtiUwCondition utiUwCondition = iter.next();
			if (factorValueNoMap.containsKey(new Integer(utiUwCondition.getId().getFactorValueNo()))) {
				utiUwCondition.setFactorValue(factorValueNoMap.get(utiUwCondition.getId().getFactorValueNo().toString()));
			}
		}
		return utiUwConditionStandarList;
	}
	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}
	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}
	public CodeService getCodeService() {
		return codeService;
	}
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}
	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}
	public UtiUwUserConditionService getUtiUwUserConditionService() {
		return utiUwUserConditionService;
	}
	public void setUtiUwUserConditionService(UtiUwUserConditionService utiUwUserConditionService) {
		this.utiUwUserConditionService = utiUwUserConditionService;
	}
	
	public UtiUwConditionService getUtiUwConditionService() {
		return utiUwConditionService;
	}

	public void setUtiUwConditionService(UtiUwConditionService utiUwConditionService) {
		this.utiUwConditionService = utiUwConditionService;
	}
}
