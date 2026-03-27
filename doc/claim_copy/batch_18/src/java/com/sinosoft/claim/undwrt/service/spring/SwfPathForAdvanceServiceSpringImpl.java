/*
 * @(#)BLSWFPathForAdvanceAction.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.SwfCondition;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.service.facade.SwfConditionService;
import com.sinosoft.claim.schema.service.facade.SwfPathService;
import com.sinosoft.claim.undwrt.service.facade.SwfConditionUndwrtService;
import com.sinosoft.claim.undwrt.service.facade.SwfPathForAdvanceService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class SwfPathForAdvanceServiceSpringImpl extends GenericDaoHibernate implements SwfPathForAdvanceService {

	private PrpDcompanyService prpDcompanyService;
	private SwfConditionUndwrtService swfConditionUndwrtService;
	private SwfPathService swfPathService;
	private SwfConditionService swfConditionService;

	private int conditionCount = 0;

	/**
	 * 取得以某节点为起始节点的所有满足条件且优先级最高的路径以及路径的个数
	 * @param modelNo 模板号
	 * @param startNodeNo 起始节点号
	 * @param certiType 单证类型
	 * @param businessNo 业务号
	 * @param defaultFlag 是否缺省值--*0:否 1:是
	 * @param comCode 机构代码
	 * @throws UserException
	 * @throws Exception
	 * @return Collection
	 */
	public boolean getAdvancePathes(int modelNo, int startNodeNo, String certiType, String businessNo, String defaultFlag, String userCode) throws UserException, Exception {
		int intCount = 0;
		boolean flag = false;
		String strWherePart = "";
		SwfPath swfPath = new SwfPath();
		SwfCondition wfCondition = new SwfCondition();
		List<SwfCondition> conditionList = new ArrayList<SwfCondition>();
		List<SwfPath> wfPathList = new ArrayList<SwfPath>();
		List<SwfPath> wfPathListNew = new ArrayList<SwfPath>();
		String strSQL = " ModelNo=" + modelNo + " AND StartNodeNo=" + startNodeNo + " ORDER BY EndNodeNo";
		// 查找符合该业务的路径
		wfPathList = swfPathService.findByConditions(strSQL);
		intCount = wfPathList.size();
		if (intCount == 0) {
			throw new UserException(-98, -1007, "沒有找到路徑!", "抱歉,系統沒有找到當前節點到審核通過的路徑!");
		}
		Iterator<SwfPath> itwfpath = wfPathList.iterator();
		while (itwfpath.hasNext()) {
			swfPath = itwfpath.next();
			wfPathListNew.add(swfPath);
			this.conditionCount = 0;
			// 查找符合该业务的路径条件,並计算出路径条件的条数conditionCount
			// 目前边条件中不区分机构，因此暂时传comCode=null.已方便实现通用
			strWherePart = this.getCondition(null, swfPath.getId().getModelNo(), swfPath.getId().getPathNo());
			// 如果没有路径条件则认为此路径是满足条件的
			if (this.conditionCount == 0) {
				throw new UserException(-98, -1007, this.getClass().getName(), "抱歉,系統沒有找到當前節點到審核通過的路徑條件!");
			}
			if (this.conditionCount > 0) {
				flag = false;
				strSQL = strWherePart + " Order by ModelNo,PathNo,ConditionNo";
				conditionList = swfConditionService.findByConditions(strSQL);
				Iterator<SwfCondition> itcondition = conditionList.iterator();
				while (itcondition.hasNext()) {
					wfCondition = itcondition.next();
					// 简单配置条件拼写
					if (wfCondition.getConfigType().equals("0")) {
						wfCondition.setConfigText(wfCondition.getColumnName() + wfCondition.getOperator() + wfCondition.getValue());
					}
					flag = swfConditionUndwrtService.execute(businessNo, swfPath.getId().getModelNo(), swfPath.getStartNodeNo(), wfCondition, userCode);
					if (flag) {
						break;
					}
				}
				if (!flag) {
					wfPathListNew.remove(swfPath);
				}
			}
		}
		intCount = conditionList.size();
		int maxPriority = 100;
		itwfpath = wfPathListNew.iterator();
		// 找出所有路径中最大的优先级
		while (itwfpath.hasNext()) {
			swfPath = (SwfPath) itwfpath.next();
			if (swfPath.getPriority() < maxPriority) {
				maxPriority = swfPath.getPriority();
			}
		}
		itwfpath = wfPathListNew.iterator();
		if ("1".equals(defaultFlag)) {
			while (itwfpath.hasNext()) {
				swfPath = (SwfPath) itwfpath.next();
				if (swfPath.getDefaultFlag() == "1" && swfPath.getPriority() == maxPriority) {
					wfPathListNew.add(swfPath);
					break;
				}
			}
		} else {
			while (itwfpath.hasNext()) {
				swfPath = (SwfPath) itwfpath.next();
				if (swfPath.getPriority() == maxPriority) {
					wfPathListNew.add(swfPath);
					break;
				}
			}
		}
		if (wfPathListNew.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 递归向上获取路径条件
	 * @param iComCode 机构代码
	 * @param iModelNo 工作流模板号码
	 * @param iPathNo 路径号码
	 * @return 
	 */
	public String getCondition(String iComCode, int iModelNo, int iPathNo) throws Exception {
		int intCount = 0;
		String strWherePart = "";
		PrpDcompany prpDcompany = null;
		String upperComCode = "";
		String calComCode = "";
		try {
			/* 如果传入机构为空 */
			if ((iComCode == null) || (iComCode.length() == 0)) {
				strWherePart = " ModelNo=" + iModelNo + " AND PathNo=" + iPathNo + " AND SerialNo=1 AND ValidStatus='1'";
				intCount = this.getSwfConditionService().getCount(strWherePart);
				this.conditionCount = intCount;
				return strWherePart;
			}
			calComCode = iComCode;
			while (true) {
				// 获取当前机构的路径条件
				strWherePart = " ModelNo=" + iModelNo + " AND PathNo=" + iPathNo + " AND ComCode='" + calComCode.trim() + "' AND SerialNo=1 AND ValidStatus='1'";
				intCount = this.getSwfConditionService().getCount(strWherePart);
				if (intCount > 0) {
					this.conditionCount = intCount;
					return strWherePart;
				}
				// 获取不到当前机构的路径条件则查找上级机构代码
				prpDcompany = prpDcompanyService.findByPrimaryKey(calComCode);
				upperComCode = prpDcompany.getPrpDcompany().getComCode();
				// 如果上级机构代码和本机构代码相等
				if (calComCode.equalsIgnoreCase(upperComCode)) {
					intCount = 0;
					break;
				}
				calComCode = upperComCode;
			}
			this.conditionCount = intCount;
		} catch (Exception e) {
			throw e;
		}
		return strWherePart;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public int getConditionCount() {
		return conditionCount;
	}

	public void setConditionCount(int conditionCount) {
		this.conditionCount = conditionCount;
	}

	public SwfConditionUndwrtService getSwfConditionUndwrtService() {
		return swfConditionUndwrtService;
	}

	public void setSwfConditionUndwrtService(SwfConditionUndwrtService swfConditionUndwrtService) {
		this.swfConditionUndwrtService = swfConditionUndwrtService;
	}

	public SwfPathService getSwfPathService() {
		return swfPathService;
	}

	public void setSwfPathService(SwfPathService swfPathService) {
		this.swfPathService = swfPathService;
	}

	public SwfConditionService getSwfConditionService() {
		return swfConditionService;
	}

	public void setSwfConditionService(SwfConditionService swfConditionService) {
		this.swfConditionService = swfConditionService;
	}

}
