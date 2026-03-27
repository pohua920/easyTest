package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.function.insutil.dto.domain.PrpDcompanyDto;
import com.sinosoft.function.insutil.resource.dtofactory.domain.DBPrpDcompany;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.undwrt.common.model.PrpDcompany;
import com.sinosoft.undwrt.common.service.facade.PrpDcompanyService;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.SwfCondition;
import com.sinosoft.undwrt.undwrtBase.model.SwfNode;
import com.sinosoft.undwrt.undwrtBase.model.SwfPath;
import com.sinosoft.undwrt.undwrtBase.model.SwfPathId;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService;

/**
 * 工作流路徑實現類.
 */
public class SwfPathServiceSpringImpl extends GenericDaoHibernate<SwfPath, SwfPathId> implements SwfPathService {

	/** 屬性路徑條件的條數. */
	private int conditionCount = 0;

	/** 屬性權限標誌位. */
	public String batchFlag = "false";

	/** 屬性工作流節點定義接口. */
	private SwfNodeService swfNodeService;

	/** 屬性工作流條件描述接口. */
	private SwfConditionService swfConditionService;

	/** 屬性機構接口. */
	private PrpDcompanyService prpDcompanyService;

	/**
	 * 取得以某節點爲起始節點的所有滿足條件且優先級最高的路徑.
	 * 
	 * @param iModelNo
	 *            模板號
	 * @param iStartNodeNo
	 *            起始節點號
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iDefaultFlag
	 *            是否缺省值--*0:否 ':是
	 * @param iComCode
	 *            機構代碼
	 * @return 滿足條件的路徑類集合
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             the 異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService#getPathes(int,
	 *      int, java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<SwfPath> getPathes(int modelNo, int startNodeNo, String certiType, String businessNo, String defaultFlag, String comCode) throws UserException,
			Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		int intCount = 0;
		int j = 0;
		boolean flag = false;
		String strWherePart = "";
		SwfPath wfPathDto = new SwfPath();
		SwfCondition wfConditionDto = new SwfCondition();
		SwfNode swfNodeDto = null;
		Collection conditionList = new ArrayList();
		Collection wfPathDtoList = new ArrayList();
		Collection wfPathDtoListNew = new ArrayList();
		try {
			String strSQL = "select * from swfpath where ModelNo=" + modelNo + " AND StartNodeNo=" + startNodeNo + " ORDER BY EndNodeNo";
			// 查找符合该业务的路径
			// wfPathDtoList = dbWfPath.findByConditions(strSQL, 0, 0);
			wfPathDtoList = super.getSession().createSQLQuery(strSQL).addEntity(SwfPath.class).list();
			// wfPathDtoList = this.findBySql(strSQL);
			intCount = wfPathDtoList.size();
			if (intCount == 0) {
				throw new UserException(-98, -1007, this.getClass().getName());
			}
			Iterator itwfpath = wfPathDtoList.iterator();
			while (itwfpath.hasNext()) {
				j++;
				wfPathDto = (SwfPath) itwfpath.next();
				// -----过滤掉审核通过节点。双核界面调整需求。徐明杰2005-8-12
				// swfNodeDto =
				// dbSwfNode.findByPrimaryKey(wfPathDto.getId().getModelNo(),
				// wfPathDto.getSwfNodeByfkPathNode2().getId().getNodeNo());
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.modelNo", wfPathDto.getId().getModelNo());
				queryRule.addEqual("id.nodeNo", wfPathDto.getSwfNodeByfkPathNode2().getId().getNodeNo());
				swfNodeDto = swfNodeService.findByPrimaryKey(queryRule);
				// 车队提交节点列表需要审核通过节点 modify by luyang 2005-11-11
				// 出单员可以直接到审核通过节点
				if (this.batchFlag.equals("false") && startNodeNo != 1) {
					if (swfNodeDto == null || StringUtils.trimToEmpty(swfNodeDto.getEndFlag()).equals("1")) {
						continue;
					}
				}
				wfPathDtoListNew.add(wfPathDto);
				this.conditionCount = 0;
				// 查找符合该业务的路径条件,并计算出路径条件的条数conditionCount
				strWherePart = this.getCondition(comCode, wfPathDto.getId().getModelNo(), wfPathDto.getId().getPathNo());
				// 如果没有路径条件则认为此路径是满足条件的
				if (this.conditionCount == 0) {
					// 张颖需求：如果路径上没有设置条件，则不让通过。2005-4-8
					throw new Exception(internal.getText("undwrt.service.swfPath.setPathCondition"));
				}
				if (this.conditionCount > 0) {
					flag = false;
					strSQL = strWherePart + " Order by ModelNo,PathNo,ConditionNo";
					// conditionList = dbWfCondition.findByConditions(strSQL);
					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addSql(strWherePart);
					conditionList = swfConditionService.findByConditions(queryRule);
					Iterator itcondition = conditionList.iterator();
					while (itcondition.hasNext()) {
						wfConditionDto = (SwfCondition) itcondition.next();
						// 简单配置条件拼写
						if (wfConditionDto.getConfigType().equals("0")) {
							wfConditionDto.setConfigText(wfConditionDto.getColumnName() + wfConditionDto.getOperator() + wfConditionDto.getValue());
						}
						// flag =
						// blWfConditionAction.execute(businessNo,comCode,modelNo,startNodeNo,
						flag = swfConditionService.execute(businessNo, comCode, modelNo, startNodeNo, wfConditionDto);
						if (flag) {
							break;
						}
					}
					if (!flag) {
						wfPathDtoListNew.remove(wfPathDto);
					}
				}
			}
			intCount = conditionList.size();
			int maxPriority = 100;
			itwfpath = wfPathDtoListNew.iterator();
			// 找出所有路径中最大的优先级
			while (itwfpath.hasNext()) {
				wfPathDto = (SwfPath) itwfpath.next();
				if (wfPathDto.getPriority() < maxPriority) {
					maxPriority = wfPathDto.getPriority();
				}
			}
			itwfpath = wfPathDtoListNew.iterator();
			// if (defaultFlag == "1")
			if ("1".equals(defaultFlag)) {
				while (itwfpath.hasNext()) {
					wfPathDto = (SwfPath) itwfpath.next();
					if (wfPathDto.getDefaultFlag() == "1" && wfPathDto.getPriority() == maxPriority) {
						wfPathDtoListNew.add(wfPathDto);
						break;
					}
				}
			} else {
				while (itwfpath.hasNext()) {
					wfPathDto = (SwfPath) itwfpath.next();
					if (wfPathDto.getPriority() == maxPriority) {
						wfPathDtoListNew.add(wfPathDto);
						break;
					}
				}
			}
		} catch (UserException ue) {
			ue.printStackTrace();
			throw ue;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return (List) wfPathDtoListNew;
	}

	/**
	 * 
	 * @param iModelNo
	 * @param iStartNodeNo
	 * @param endNodeNo
	 * @param iBusinessType
	 * @param iBusinessNo
	 * @param iDefaultFlag
	 * @param iComCode
	 * @return
	 * @throws UserException
	 * @throws Exception
	 * 
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService#getPathes(int,
	 *      int, int, java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<SwfPath> getPathes(int iModelNo, int iStartNodeNo, int endNodeNo, String iBusinessType, String iBusinessNo, String iDefaultFlag, String iComCode)
			throws UserException, Exception {
		// TODO Auto-generated method stub
		InternationalizationUtil internal = new InternationalizationUtil();
		int intCount = 0;
		int j = 0;
		boolean flag = false;
		String strWherePart = "";
		SwfPath wfPathDto = new SwfPath();
		SwfCondition wfConditionDto = new SwfCondition();
		SwfNode swfNodeDto = null;
		Collection conditionList = new ArrayList();
		Collection wfPathDtoList = new ArrayList();
		Collection wfPathDtoListNew = new ArrayList();
		try {
			String strSQL = "select * from swfpath where ModelNo=" + iModelNo + " AND StartNodeNo=" + iStartNodeNo + " AND endNodeNo = " + endNodeNo
					+ " ORDER BY EndNodeNo";
			// 查找符合该业务的路径
			// wfPathDtoList = dbWfPath.findByConditions(strSQL, 0, 0);
			wfPathDtoList = super.getSession().createSQLQuery(strSQL).addEntity(SwfPath.class).list();
			// wfPathDtoList = this.findBySql(strSQL);
			intCount = wfPathDtoList.size();
			if (intCount == 0) {
				throw new UserException(-98, -1007, this.getClass().getName());
			}
			Iterator itwfpath = wfPathDtoList.iterator();
			while (itwfpath.hasNext()) {
				j++;
				wfPathDto = (SwfPath) itwfpath.next();
				// -----过滤掉审核通过节点。双核界面调整需求。徐明杰2005-8-12
				// swfNodeDto =
				// dbSwfNode.findByPrimaryKey(wfPathDto.getId().getModelNo(),
				// wfPathDto.getSwfNodeByfkPathNode2().getId().getNodeNo());
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.modelNo", wfPathDto.getId().getModelNo());
				queryRule.addEqual("id.nodeNo", wfPathDto.getSwfNodeByfkPathNode2().getId().getNodeNo());
				swfNodeDto = swfNodeService.findByPrimaryKey(queryRule);
				// 车队提交节点列表需要审核通过节点 modify by luyang 2005-11-11
				// 出单员可以直接到审核通过节点
				if (this.batchFlag.equals("false") && iStartNodeNo != 1) {
					if (swfNodeDto == null || StringUtils.trimToEmpty(swfNodeDto.getEndFlag()).equals("1")) {
						continue;
					}
				}
				wfPathDtoListNew.add(wfPathDto);
				this.conditionCount = 0;
				// 查找符合该业务的路径条件,并计算出路径条件的条数conditionCount
				strWherePart = this.getCondition(iComCode, wfPathDto.getId().getModelNo(), wfPathDto.getId().getPathNo());
				// 如果没有路径条件则认为此路径是满足条件的
				if (this.conditionCount == 0) {
					// 张颖需求：如果路径上没有设置条件，则不让通过。2005-4-8
					throw new Exception(internal.getText("undwrt.service.swfPath.setPathCondition"));
				}
				if (this.conditionCount > 0) {
					flag = false;
					strSQL = strWherePart + " Order by ModelNo,PathNo,ConditionNo";
					// conditionList = dbWfCondition.findByConditions(strSQL);
					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addSql(strWherePart);
					conditionList = swfConditionService.findByConditions(queryRule);
					Iterator itcondition = conditionList.iterator();
					while (itcondition.hasNext()) {
						wfConditionDto = (SwfCondition) itcondition.next();
						// 简单配置条件拼写
						if (wfConditionDto.getConfigType().equals("0")) {
							wfConditionDto.setConfigText(wfConditionDto.getColumnName() + wfConditionDto.getOperator() + wfConditionDto.getValue());
						}
						// flag =
						// blWfConditionAction.execute(businessNo,comCode,modelNo,startNodeNo,
						flag = swfConditionService.execute(iBusinessNo, iComCode, iModelNo, iStartNodeNo, wfConditionDto);
						if (flag) {
							break;
						}
					}
					if (!flag) {
						wfPathDtoListNew.remove(wfPathDto);
					}
				}
			}
			intCount = conditionList.size();
			int maxPriority = 100;
			itwfpath = wfPathDtoListNew.iterator();
			// 找出所有路径中最大的优先级
			while (itwfpath.hasNext()) {
				wfPathDto = (SwfPath) itwfpath.next();
				if (wfPathDto.getPriority() < maxPriority) {
					maxPriority = wfPathDto.getPriority();
				}
			}
			itwfpath = wfPathDtoListNew.iterator();
			// if (defaultFlag == "1")
			if ("1".equals(iDefaultFlag)) {
				while (itwfpath.hasNext()) {
					wfPathDto = (SwfPath) itwfpath.next();
					if (wfPathDto.getDefaultFlag() == "1" && wfPathDto.getPriority() == maxPriority) {
						wfPathDtoListNew.add(wfPathDto);
						break;
					}
				}
			} else {
				while (itwfpath.hasNext()) {
					wfPathDto = (SwfPath) itwfpath.next();
					if (wfPathDto.getPriority() == maxPriority) {
						wfPathDtoListNew.add(wfPathDto);
						break;
					}
				}
			}
		} catch (UserException ue) {
			ue.printStackTrace();
			throw ue;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return (List) wfPathDtoListNew;
	}

	/**
	 * 遞歸向上獲取路徑條件.
	 * 
	 * @param iComCode
	 *            機構代碼
	 * @param iModelNo
	 *            模板號
	 * @param iPathNo
	 *            路徑號
	 * @return 路徑條件
	 * @throws Exception
	 *             異常
	 */
	private String getCondition(String iComCode, int iModelNo, int iPathNo) throws Exception {
		int level = 8;
		int intCount = 0;
		int conditionCount = 0;
		String strWherePart = "";
		PrpDcompany prpDcompanyDto = null;
		String upperComCode = "";
		String calComCode = "0000000000";
		QueryRule queryRule = QueryRule.getInstance();
		try {

			/* 如果传入机构为空 */
			if ((iComCode == null) || (iComCode.length() == 0)) {
				strWherePart = " ModelNo=" + iModelNo + " AND PathNo=" + iPathNo + " AND (ComCode IS NULL OR ComCode='') AND SerialNo=1 AND ValidStatus='1'";
				queryRule.addSql(strWherePart);
				intCount = swfConditionService.getCount(queryRule);
				this.conditionCount = intCount;
				return strWherePart;
			}
			// modify by xuning gpic 20071109 针对于的现状，不用递归查询。直接查00000000的数据就行了
			// calComCode = iComCode;
			while (1 == 1) {
				// 获取当前机构的路径条件
				strWherePart = " ModelNo=" + iModelNo + " AND PathNo=" + iPathNo + " AND ComCode='" + calComCode.trim()
						+ "' AND SerialNo=1 AND ValidStatus='1'";
				// intCount = dbWfCondition.getCount(strWherePart);
				queryRule.getQueryRuleList().clear();
				queryRule.getRuleList().clear();
				queryRule.addSql(strWherePart);
				intCount = swfConditionService.getCount(queryRule);
				if (intCount > 0) {
					this.conditionCount = intCount;
					return strWherePart;
				}
				// 获取不到当前机构的路径条件则查找上级机构代码
				prpDcompanyDto = new PrpDcompany();
				prpDcompanyDto = prpDcompanyService.findByPrimaryKey(calComCode);
				upperComCode = prpDcompanyDto.getUpperClaimComCode();
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

	/**
	 * 取得以某節點爲起始節點的所有滿足條件的路徑.
	 * 
	 * @param iModelNo
	 *            模板號
	 * @param iStartNodeNo
	 *            起始節點號
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iDefaultFlag
	 *            是否缺省值--*0:否 ':是
	 * @param iComCode
	 *            機構代碼
	 * @param batchFlag
	 *            標志
	 * @return 滿足條件的路徑類集合
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             the 異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService#getPathes(int,
	 *      int, java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public List<SwfPath> getPathes(int iModelNo, int iStartNodeNo, String iBusinessType, String iBusinessNo, String iDefaultFlag, String iComCode,
			String batchFlag) throws UserException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 獲取屬性工作流路徑.
	 * 
	 * @param wfLog
	 *            工作流日誌
	 * @return 屬性工作流路徑的值
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService#getPassPath(com.sinosoft.undwrt.undwrtBase.model.WfLog)
	 */
	@Override
	public SwfPath getPassPath(WfLog wfLog) throws Exception {
		SwfPath swfPath = null;
		List swfPathList = null;
		SwfNode swfNode = null;
		int modelno;
		int endNodeNo;

		QueryRule queryRule = QueryRule.getInstance();
		String conditions = "ModelNo=" + wfLog.getModelNo() + " AND StartNodeNo=" + wfLog.getNodeNo();
		queryRule.addSql(conditions);

		swfPathList = super.find(queryRule);
		for (int i = 0; i < swfPathList.size(); i++) {
			SwfPath tmpSwfPath = (SwfPath) swfPathList.get(i);
			modelno = tmpSwfPath.getId().getModelNo();
			endNodeNo = tmpSwfPath.getSwfNodeByfkPathNode2().getId().getNodeNo();
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.modelNo", modelno);
			queryRule.addEqual("id.nodeNo", endNodeNo);
			swfNode = swfNodeService.findByPrimaryKey(queryRule);

			if (swfNode != null && StringUtils.trimToEmpty(swfNode.getEndFlag()).equals("1")) {
				swfPath = tmpSwfPath;
				break;
			}
		}

		return swfPath;
	}

	/**
	 * 取得以某節點爲起始節點的滿足條件的路徑.
	 * 
	 * @param wfLog
	 *            日誌工作流類
	 * @return 滿足條件的路徑
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService#getPathes(com.sinosoft.undwrt.undwrtBase.model.WfLog)
	 */
	@Override
	public List<SwfPath> getPathes(WfLog wfLog) throws UserException, Exception {
		List list = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.flowId", wfLog.getId().getFlowId());
		queryRule.addEqual("id.logNo", wfLog.getId().getLogNo());

		list = super.find(queryRule);

		return list;
	}

	/**
	 * 取得以某節點爲起始節點的所有滿足條件的路徑.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的路徑的集合
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService#getSwfPathList(ins.framework.common.QueryRule)
	 */
	@Override
	public List<SwfPath> getSwfPathList(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

	/**
	 * 取得以某節點爲起始節點的所有滿足條件的路徑.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的路徑的集合
	 * @throws UserException
	 *             用戶自定義異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService#getPathesByQueryRule(ins.framework.common.QueryRule)
	 */
	@Override
	public List<SwfPath> getPathesByQueryRule(QueryRule queryRule) throws UserException, Exception {
		List list = super.find(queryRule);
		return list;
	}

	/**
	 * 獲取屬性工作流節點定義接口.
	 * 
	 * @return 屬性工作流節點定義接口的值
	 */
	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	/**
	 * 設置屬性工作流節點定義接口.
	 * 
	 * @param swfNodeService
	 *            待設置的工作流節點定義接口的值
	 */
	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}

	/**
	 * 獲取屬性工作流條件描述接口.
	 * 
	 * @return 屬性工作流條件描述接口的值
	 */
	public SwfConditionService getSwfConditionService() {
		return swfConditionService;
	}

	/**
	 * 設置屬性工作流條件描述接口.
	 * 
	 * @param swfConditionService
	 *            待設置的工作流條件描述接口的值
	 */
	public void setSwfConditionService(SwfConditionService swfConditionService) {
		this.swfConditionService = swfConditionService;
	}

	/**
	 * 獲取屬性機構接口.
	 * 
	 * @return 屬性機構接口的值
	 */
	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	/**
	 * 設置屬性機構接口.
	 * 
	 * @param prpDcompanyService
	 *            待設置的機構接口的值
	 */
	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

}
