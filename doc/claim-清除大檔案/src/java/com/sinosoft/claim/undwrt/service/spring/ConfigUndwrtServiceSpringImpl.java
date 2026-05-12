/*
 * @(#)BLConfigAction.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.undwrt.service.facade.BusinessDataService;
import com.sinosoft.claim.undwrt.service.facade.ConfigUndwrtService;
import com.sinosoft.claim.undwrt.service.facade.StandardCheckService;
import com.sinosoft.claim.undwrt.service.facade.StandardService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class ConfigUndwrtServiceSpringImpl extends GenericDaoHibernate implements ConfigUndwrtService {

	private StandardService standardService;
	private StandardCheckService standardCheckService;
	private PrpLprepayService prpLprepayService;
	private BusinessDataService businessDataService;
	private PrpLcompensateService prpLcompensateService;

	/**
	 *执行工作流系统发出的sql语句(针对简单描述和SQL描述)
	 *@param iBusinessNo 业务号码
	 *@param iStrSQL 路径条件拼成的SQL语句
	 *@return 执行结果(TRUE:成功/FALSE:失败)
	 *@throws UserException,Exception
	 */
	public boolean executeSql(String iBusinessNo, String iStrSQL) throws Exception {
		boolean blnReturn = false;
		List<?> list = super.getSession().createSQLQuery(iStrSQL).list();
		if (Integer.valueOf(String.valueOf(list.get(0))) > 0) {
			blnReturn = true;
		}
		return blnReturn;
	}

	/**
	 *执行工作流系统发出的高级条件消息语句(针对高级条件) 为了简化，目前的高级条件设置没有弄成反射的方式，而是沿袭了以前的方式，采用直接写方法名
	 *@param iBusinessNo 业务号码
	 *@param iFuncName 高级条件接口名称
	 *@return 执行结果(TRUE:成功/FALSE:失败)
	 *@throws UserException,Exception
	 */
	public boolean executeFunc(String iBusinessNo, int iModelNo, int iNodeNo, String iFuncNameAndBusinessType, String userCode) throws Exception {
		boolean blnReturn = false;
		String riskCode = null;
		String comCode = null;
		int startNode = iFuncNameAndBusinessType.indexOf("(");
		int endNode = iFuncNameAndBusinessType.indexOf(")");
		String iFuncName = iFuncNameAndBusinessType.substring(0, startNode);
		String iBusinessType = iFuncNameAndBusinessType.substring(startNode + 1, endNode);
		try {
			// **********************************************核赔模快条件判断>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			if (iFuncName.equals("Hepei")) {
				// 判断是预赔还是实赔业务，再分别初始化业务数据送入Map中。
				Map<String, Object> businessDataMap = new HashMap<String, Object>();
				if ("precompensate".equals(iBusinessType)) {
					PrpLprepay prpLprepay = this.getPrpLprepayService().findPrpLprepay(iBusinessNo);
					if (prpLprepay == null) {
						// 如果是实赔，第一条边实赔找不到相应数据返回查找第二条边
						return false;
					}
					riskCode = prpLprepay.getRiskCode();
					comCode = prpLprepay.getComCode();
					businessDataMap = this.getBusinessDataService().getBusinessDataMap(prpLprepay);
				} else if (iBusinessType.equals("compensate")) {// 实赔业务
					PrpLcompensate prpLcompensate = this.getPrpLcompensateService().findPrpLcompensate(iBusinessNo);
					if (prpLcompensate == null) {
						// 如果是预赔，第一条边实赔找不到相应数据返回查找第二条边
						return false;
					}
					riskCode = prpLcompensate.getRiskCode();
					comCode = prpLcompensate.getComCode();
					// 得到业务数据，送入Map中
					businessDataMap = this.getBusinessDataService().getBusinessDataMap(prpLcompensate);
				}
				// 从utiUwCondition表得到标准数据
				Collection<?> standardDataList = new ArrayList();
				if (businessDataMap == null) {
					throw new UserException(2005, 829, "讀取的業務數據對象是空值，請聯系系統管理員", "");
				} else {
					// 险别支持
					Object businessValueObject = businessDataMap.get("KindSumRealPay");
					if (businessValueObject != null) {
						if (businessValueObject instanceof Map) {
							Object[] kindList = ((Map<?, ?>) businessValueObject).keySet().toArray();
							standardDataList = standardService.getStandardList(iBusinessType, riskCode, iModelNo, iNodeNo, userCode, comCode, kindList);
							if (standardDataList == null || standardDataList.size() == 0) {
								standardService.getStandardList(iBusinessType, riskCode, iModelNo, iNodeNo, userCode, comCode);
							}
						} else {
							standardDataList = standardService.getStandardList(iBusinessType, riskCode, iModelNo, iNodeNo, userCode, comCode);
						}
					} else
					// 组合产品支持
					if ((businessValueObject = businessDataMap.get("RiskSumRealPay")) != null) {
						String productCode = ((Map<?, ?>) businessValueObject).get("productCode").toString();
						((Map<?, ?>) businessValueObject).remove("productCode");// 产品代码的使命已完成，该删掉了
						if (businessValueObject instanceof Map) {
							Object[] kindList = ((Map<?, ?>) businessValueObject).keySet().toArray();
							standardDataList = standardService.getStandardList(iBusinessType, productCode, iModelNo, iNodeNo, userCode, comCode, kindList);
							if (standardDataList == null || standardDataList.size() == 0) {
								throw new UserException(2005, 829, "該組合險種未配置標准權限，請聯系系統管理員", "");
							}
						} else {
							standardDataList = standardService.getStandardList(iBusinessType, riskCode, iModelNo, iNodeNo, userCode, comCode);
						}
					} else {
						standardDataList = standardService.getStandardList(iBusinessType, riskCode, iModelNo, iNodeNo, userCode, comCode);
					}
				}
				// 标准和业务数据比较，不满足不通过，满足通过
				blnReturn = standardCheckService.checkHepei(standardDataList, businessDataMap);

			} else {// 如果有新的高级条件，在这里增加分支
				// 传入的高级条件有误，没有找到相应的支持方法
				blnReturn = false;
			}
		} catch (Exception e) {
			throw e;
		}
		return blnReturn;
	}

	public StandardService getStandardService() {
		return standardService;
	}

	public void setStandardService(StandardService standardService) {
		this.standardService = standardService;
	}

	public StandardCheckService getStandardCheckService() {
		return standardCheckService;
	}

	public void setStandardCheckService(StandardCheckService standardCheckService) {
		this.standardCheckService = standardCheckService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public BusinessDataService getBusinessDataService() {
		return businessDataService;
	}

	public void setBusinessDataService(BusinessDataService businessDataService) {
		this.businessDataService = businessDataService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}
}
