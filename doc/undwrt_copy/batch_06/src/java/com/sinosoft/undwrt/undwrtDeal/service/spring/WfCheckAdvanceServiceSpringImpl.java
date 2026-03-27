package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.common.QueryRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.sinosoft.platform.dto.domain.SwfNodeDto;
import com.sinosoft.platform.resource.dtofactory.domain.DBSwfNode;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.log.Logger;
import com.sinosoft.undwrt.common.model.PrpDcompany;
import com.sinosoft.undwrt.common.service.facade.PrpDcompanyService;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.SwfCondition;
import com.sinosoft.undwrt.undwrtBase.model.SwfPath;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.WfCheckAdvanceService;

/**
 * 權限校驗
 */

public class WfCheckAdvanceServiceSpringImpl implements WfCheckAdvanceService {

	/** 屬性日誌處理類. */
	private static Logger logger = Logger
			.getLogger(WfCheckAdvanceServiceSpringImpl.class);

	/** 屬性工作流條件描述接口. */
	private SwfConditionService swfConditionService;

	/** 屬性工作流路徑定義接口. */
	private SwfPathService swfPathService;

	/** 屬性機構接口. */
	private PrpDcompanyService prpDcompanyService;

	/**
	 * 權限校驗.
	 * 
	 * @param ModelNo
	 *            模板號
	 * @param StartNodeNo
	 *            開始節點號
	 * @param BusinessType
	 *            業務類型
	 * @param BusinessNo
	 *            業務號
	 * @param DefaultFlag
	 *            默認標誌位
	 * @return 有權限返回true，沒有權限返回false
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.WfCheckAdvanceService#checkAdvanceCondition(int,
	 *      int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean checkAdvanceCondition(int ModelNo, int StartNodeNo,
			String BusinessType, String BusinessNo, String DefaultFlag)
			throws Exception {
		try {
			boolean hasPath = this.getAdvancePathes(ModelNo, StartNodeNo,
					BusinessType, BusinessNo, DefaultFlag);
			return hasPath;
		} catch (UserException usee) {
			usee.printStackTrace();
			throw usee;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/** 屬性路徑條件的條數. */
	private int conditionCount = 0;

	/**
	 * 取得以某節點爲起始節點的所有滿足條件且優先級最高的路徑.
	 * 
	 * @param modelNo
	 *            模板號
	 * @param startNodeNo
	 *            起始節點號
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param defaultFlag
	 *            默認標誌位
	 * @return 成功返回true，失敗返回false
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public boolean getAdvancePathes(int modelNo, int startNodeNo,
			String certiType, String businessNo, String defaultFlag)
			throws UserException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		int intCount = 0;
		int j = 0;
		String comCode = "0000000000";
		boolean flag = false;
		String strWherePart = "";
		SwfPath swfPath = new SwfPath();
		SwfCondition swfCondition = new SwfCondition();
		SwfNodeDto swfNodeDto = null;
		Collection conditionList = new ArrayList();
		Collection wfPathDtoList = new ArrayList();
		Collection wfPathDtoListNew = new ArrayList();
		try {
			String strSQL = "";
			// 查找符合该业务的路径
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.modelNo", modelNo);
			queryRule.addEqual("swfNodeByfkPathNode1.id.nodeNo", startNodeNo);
			queryRule.addAscOrder("swfNodeByfkPathNode2.id.nodeNo");
			wfPathDtoList = swfPathService.getPathesByQueryRule(queryRule);
			intCount = wfPathDtoList.size();
			if (intCount == 0) {
				throw new UserException(
						-98,
						-1007,
						internal.getText("undwrt.service.wfCheckAdvance.notFindRoute"),
						internal.getText("undwrt.service.wfCheckAdvance.systemNotFindRoute"));
			}
			Iterator itwfpath = wfPathDtoList.iterator();
			while (itwfpath.hasNext()) {
				j++;
				swfPath = (SwfPath) itwfpath.next();
				wfPathDtoListNew.add(swfPath);
				this.conditionCount = 0;
				// 查找符合该业务的路径条件,并计算出路径条件的条数conditionCount
				queryRule = this.getCondition(comCode, swfPath.getId()
						.getModelNo(), swfPath.getId().getPathNo());
				// 如果没有路径条件则认为此路径是满足条件的
				if (this.conditionCount == 0) {
					// continue;
					// 张颖需求：如果路径上没有设置条件，则不让通过。2005-4-8
					// wfPathDtoListNew.clear();
					throw new UserException(
							-98,
							-1007,
							this.getClass().getName(),
							internal.getText("undwrt.service.wfCheckAdvance.systemNotFindRoute"));
					// throw new UserException( -98, -1003,
					// "没有设置路径条件！请与系统管理员联系！");
				}
				if (this.conditionCount > 0) {
					flag = false;
					queryRule.addAscOrder("id.modelNo")
							.addAscOrder("id.pathNo")
							.addAscOrder("id.conditionNo");
					conditionList = swfConditionService
							.findByConditions(queryRule);
					Iterator itcondition = conditionList.iterator();
					while (itcondition.hasNext()) {
						swfCondition = (SwfCondition) itcondition.next();
						// 简单配置条件拼写
						if (swfCondition.getConfigType().equals("0")) {
							swfCondition.setConfigText(swfCondition
									.getColumnName()
									+ swfCondition.getOperator()
									+ swfCondition.getValue());
							// System.out.println("简单条件拼写：" +
							// wfConditionDto.getConfigText());
						}

						int modelno = swfPath.getId().getModelNo();
						int endNodeNo = swfPath.getSwfNodeByfkPathNode2()
								.getId().getNodeNo();
						int startNodeNo1 = swfPath.getSwfNodeByfkPathNode1()
								.getId().getNodeNo();
						flag = swfConditionService.execute(businessNo, comCode,
								modelno, startNodeNo1, swfCondition);
						// 第一次循环flag正常，第二次报异常
						// flag=true;
						if (flag) {
							break;
						}
					}
					if (!flag) {
						wfPathDtoListNew.remove(swfPath);
					}
				}
			}
			intCount = conditionList.size();
			long maxPriority = 100;
			itwfpath = wfPathDtoListNew.iterator();
			// 找出所有路径中最大的优先级
			// getPriority()越小优先级越高
			while (itwfpath.hasNext()) {
				swfPath = (SwfPath) itwfpath.next();
				if (swfPath.getPriority() < maxPriority) {
					maxPriority = swfPath.getPriority();
				}
			}
			itwfpath = wfPathDtoListNew.iterator();
			// wfPathDto.getDefaultFlag()是一个开关，为了使用getPriority()优先级。getDefaultFlag="1",为开
			if (defaultFlag == "1")// 不晓得这个flag是做什么用的，入口的时候直接赋值defaultFlag ==
									// "1"
			{
				while (itwfpath.hasNext()) {
					swfPath = (SwfPath) itwfpath.next();
					if (swfPath.getDefaultFlag() == "1"
							&& swfPath.getPriority() == maxPriority) {
						wfPathDtoListNew.add(swfPath);
						break;
					}
				}
			} else {
				while (itwfpath.hasNext()) {
					swfPath = (SwfPath) itwfpath.next();
					if (swfPath.getPriority() == maxPriority) {
						wfPathDtoListNew.add(swfPath);
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
		if (wfPathDtoListNew.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根據PrpDcompany的上級機構UpperComCode獲取路徑條件.
	 * 
	 * @param iComCode
	 *            機構代碼
	 * @param iModelNo
	 *            模板號
	 * @param iPathNo
	 *            路徑號
	 * @return 路徑條件
	 * @throws Exception
	 *            異常
	 */
	private QueryRule getCondition(String iComCode, int iModelNo, int iPathNo)
			throws Exception {
		int level = 8;
		int intCount = 0;
		int conditionCount = 0;
		String strWherePart = "";
		PrpDcompany prpDcompany = null;
		String upperComCode = "";
		String calComCode = "";
		QueryRule queryRule = QueryRule.getInstance();
		try {
			/* 如果传入机构为空 */
			if ((iComCode == null) || (iComCode.length() == 0)) {
				// strWherePart = " ModelNo=" + iModelNo + " AND PathNo=" +
				// iPathNo
				// +
				// " AND (ComCode IS NULL OR ComCode='') AND SerialNo=1 AND ValidStatus='1'";
				String sql = " and (comCode IS NULL OR comCode='')";
				queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.modelNo", iModelNo);
				queryRule.addEqual("id.pathNo", iPathNo);
				queryRule.addSql(sql);
				queryRule.addEqual("id.serialNo", "1");
				queryRule.addEqual("validStatus", "1");

				intCount = swfConditionService.getCount(queryRule);
				this.conditionCount = intCount;
				return queryRule;
			}

			calComCode = iComCode;

			while (1 == 1) {
				// 获取当前机构的路径条件
				// strWherePart = " id.modelNo=" + iModelNo + " AND id.pathNo="
				// + iPathNo
				// + " and comCode='" + calComCode.trim()
				// + "' and id.serialNo=1 AND validStatus='1'";
				queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.modelNo", iModelNo);
				queryRule.addEqual("id.pathNo", iPathNo);
				queryRule.addEqual("comCode", calComCode.trim());
				queryRule.addEqual("id.serialNo", 1);
				queryRule.addEqual("validStatus", "1");

				intCount = swfConditionService.getCount(queryRule);
				if (intCount > 0) {
					this.conditionCount = intCount;
					return queryRule;
				}
				// 获取不到当前机构的路径条件则查找上级机构代码
				prpDcompany = new PrpDcompany();
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
		return queryRule;
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
	 * 獲取屬性工作流路徑定義接口.
	 * 
	 * @return 屬性工作流路徑定義接口的值
	 */
	public SwfPathService getSwfPathService() {
		return swfPathService;
	}

	/**
	 * 設置屬性工作流路徑定義接口.
	 * 
	 * @param swfPathService
	 *            待設置的工作流路徑定義接口的值
	 */
	public void setSwfPathService(SwfPathService swfPathService) {
		this.swfPathService = swfPathService;
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
