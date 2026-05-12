/*
 * @(#)BLSWfConditionAction.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.SwfCondition;
import com.sinosoft.claim.schema.service.facade.SwfConditionService;
import com.sinosoft.claim.undwrt.service.facade.ConfigUndwrtService;
import com.sinosoft.claim.undwrt.service.facade.SwfConditionUndwrtService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class SwfConditionUndwrtServiceSpringImpl extends GenericDaoHibernate implements SwfConditionUndwrtService{

//	private static Logger logger = Logger.getLogger(SwfConditionUndwrtServiceSpringImpl.class);
	private ConfigUndwrtService configUndwrtService;
	private SwfConditionService swfConditionService;

	/**
	 * 执行工作流系统发出的sql语句(针对简单描述和SQL描述)
	 * @param businessNo 业务号码
	 * @param SwfCondition swfCondition
	 * @throws UserException
	 * @throws Exception
	 * @return boolean
	 */
	public boolean execute(String businessNo, int modelno, int nodeNo, SwfCondition swfCondition, String userCode) throws Exception {
		String strWhere = "";
		String strConfig = "";
		boolean blnResult = false;
		try {
			if (swfCondition.getConfigType().equals("0") || swfCondition.getConfigType().equals("1")) {
				strWhere = swfCondition.getBusinessKey().trim() + "='" + businessNo.trim() + "' AND " + swfCondition.getConfigText().trim();
				strConfig = "SELECT COUNT(*) FROM " + swfCondition.getTableName().trim() + " WHERE " + strWhere.trim();
				blnResult = configUndwrtService.executeSql(businessNo, strConfig);
			}
			if (swfCondition.getConfigType().equals("2")) {
				strConfig = swfCondition.getConfigText().trim();
				// linjf0703 add
				if (userCode == null) {
					blnResult = false;
				} else {
					blnResult = configUndwrtService.executeFunc(businessNo, modelno, nodeNo, strConfig, userCode);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return blnResult;
	}

	/**
	 * 删除路径下的所有条件
	 * @param modelNo int
	 * @param pathNo int
	 * @throws SQLException
	 * @throws Exception
	 * @author 中科软
	 */
	public void deleteAllCondition(int modelNo, int pathNo) throws Exception {
		String statement = "DELETE FROM SWfCondition where modelNo =" + modelNo + " AND pathNo = " + pathNo;
		HibernateUtils.executeSql(super.getSession(), statement);
	}

	/**
	 * 保存路径条件
	 * @param dbManager DBManager
	 * @param SwfCondition swfCondition
	 * @throws Exception
	 */
	public void saveWfCondition(SwfCondition swfCondition) throws Exception {
		List<SwfCondition> conditionListNew = new ArrayList<SwfCondition>();
		SwfCondition tempSwfCondition = null;
		String statement = "DELETE FROM SWfCondition where modelNo =" + swfCondition.getId().getModelNo() + " AND pathNo =" + swfCondition.getId().getPathNo();
		String strWherePart = "";
		int i = 0;
		if (swfCondition.getFlag().equals("delete")) {
			// 删除改路径下所有条件
			HibernateUtils.executeSql(super.getSession(), statement);
		} else {
			List<SwfCondition> conditionList = swfCondition.getConditionList();
			for (SwfCondition temp : conditionList) {
				tempSwfCondition = new SwfCondition();
				PropertyUtils.copyProperties(tempSwfCondition, temp);
				// 简单描述
				if (tempSwfCondition.getConfigType().equals("0")) {
					i++;
					if (i > 1) {
						strWherePart = strWherePart + " AND ";
					}
					strWherePart += tempSwfCondition.getColumnName() + " " + tempSwfCondition.getOperator() + " " + tempSwfCondition.getValue();
					tempSwfCondition.setConfigText(strWherePart);
					tempSwfCondition.getId().setSerialNo(i);
				}
				conditionListNew.add(tempSwfCondition);
			}
			// 删除改路径下所有条件
			HibernateUtils.executeSql(super.getSession(), statement);
			// 批量插入新的路径条件
			this.getSwfConditionService().save(conditionListNew);
		}
	}

	public ConfigUndwrtService getConfigUndwrtService() {
		return configUndwrtService;
	}

	public void setConfigUndwrtService(ConfigUndwrtService configUndwrtService) {
		this.configUndwrtService = configUndwrtService;
	}

	public SwfConditionService getSwfConditionService() {
		return swfConditionService;
	}

	public void setSwfConditionService(SwfConditionService swfConditionService) {
		this.swfConditionService = swfConditionService;
	}

	@Override
	public void convertDto(SwfCondition swfCondition, String mode) throws Exception {

	}

}
