/*
 * @(#)GeneralClaimSpringService.java	Mar 5, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.sendUndwrt.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.Iterator;

import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.UtiUwCondition;
import com.sinosoft.claim.schema.model.UtiUwLevel;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.UtiUwConditionService;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.claim.sendUndwrt.service.facade.SendUndwrtService;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class SendUndwrtServiceSpringImpl extends GenericDaoHibernate<String,String> implements SendUndwrtService {
	private UtiUwConditionService utiUwConditionService;
	private PrpDriskService prpDriskService;
	private UtiUwLevelService utiUwLevelService;
	private PrpDuserService prpDuserService;
	private SwfLogService swfLogService;

	/**
	 * 获得核赔权限对应的金额
	 */
	public String findFactorValue(String comCode, int nodeNo, String riskCode) throws Exception {
		int factorValue = 0;
		UtiUwCondition utiUwCondition = utiUwConditionService.findByPrimaryKey(comCode, 40, nodeNo, riskCode, "C", "SumPaid", 1);
		if (utiUwCondition == null){
			factorValue = 999999999;
		}else{
			factorValue = Integer.valueOf(DataUtils.nullToZero(utiUwCondition.getFactorValue())) / 2;
		}
		return String.valueOf(factorValue);
	}

	/**
	 * 获得向上第一个核赔人员
	 */
	public UtiUwLevel findUpUwLevel(String comCode, int nodeNo, String riskCode) throws Exception {
		UtiUwLevel utiUwLevel = new UtiUwLevel();
		String classCode = prpDriskService.findPrpDrisk(riskCode).getClassCode();
		String conditions = "comcode in ('" + comCode + "','00') and modelno = 0 and nodeno > " + nodeNo + " and (classcode like '%" + classCode + "%' or classcode = '*' or riskcode like '%" + riskCode
				+ "%') and uwtype = 'C' and validstatus = '1' order by nodeno";
		try {
			Iterator<UtiUwLevel> iterator = utiUwLevelService.findByConditions(conditions).iterator();
			if (iterator.hasNext()) {
				utiUwLevel = iterator.next();
				utiUwLevel.setUserName(prpDuserService.findPrpDuser(utiUwLevel.getId().getUserCode()).getUserName());
			}
		} catch (Exception exception) {
			throw exception;
		} finally {
		}
		return utiUwLevel;
	}

	/**
	 * 送审初复核调派
	 */
	public void proxy(String flowID, int logNo, String toUserCode) throws Exception {
		SwfLog swfLog = swfLogService.findSwfLog(flowID, logNo);
		swfLog.setHandlerCode(toUserCode);
		swfLog.setHandlerName(prpDuserService.findPrpDuser(toUserCode).getUserName());
		swfLogService.update(swfLog);
	}

	public UtiUwConditionService getUtiUwConditionService() {
		return utiUwConditionService;
	}

	public void setUtiUwConditionService(UtiUwConditionService utiUwConditionService) {
		this.utiUwConditionService = utiUwConditionService;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

}
