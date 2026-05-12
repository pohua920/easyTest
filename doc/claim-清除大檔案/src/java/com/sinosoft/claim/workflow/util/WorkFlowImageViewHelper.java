package com.sinosoft.claim.workflow.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpLgeneralClaimTask;
import com.sinosoft.claim.schema.model.PrpLgeneralClaimTaskLog;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfLogStore;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.schema.model.SwfPathLogStore;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.specailCase.service.facade.GeneralClaimService;
import com.sinosoft.claim.util.BusinessRuleUtil;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * <p>
 * Title: WorkFlowImageViewHelper
 * </p>
 * c
 * <p>
 * Description:工作流图象展现ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */
public class WorkFlowImageViewHelper {
	/** 工作流服务 */
	private WorkFlowService workFlowService;
	/** 代码服务 */
	private CodeService codeService;
	/** 通赔服务 */
	private GeneralClaimService generalClaimService;

	private WfLogService wfLogService;
	private static final SimpleDateFormat formatter18 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat formatter10 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 默认构造方法
	 */
	public WorkFlowImageViewHelper() {
	}

	/**
	 * 设置工作流流程到界面
	 * @param httpServletRequest HttpServletRequest
	 * @param businessNo String
	 * @throws Exception
	 */
	public void setFlowDtoToView(HttpServletRequest httpServletRequest, String flowID) throws Exception {
		// 查询流程主表，通过流程主表中的flowStatus的状态，如果是1，表示没有完成，从swfLog表里查询
		// 如果是flowStatus=0 ，则表示 需要从SwflogStore和SwfpathlogStore表里取数据的。
		SwfFlowMain swfFlowMain = this.workFlowService.findFlowMainByPrimaryKey(flowID);
		if (swfFlowMain == null) {
			new UserException(0, 0, "工作流", "工作號 " + flowID + " 流數據不存在!");
		}
		// 根据业务号得到多行的wfLog列表
		List<SwfLog> swfLogList = new ArrayList<SwfLog>();
		List<SwfPathLog> swfPathLogList = new ArrayList<SwfPathLog>();
		// storeFlag转储标记，为2时代表数据已经转储
		if ("2".equals(swfFlowMain.getStoreFlag())) {
			// 计算案件处理时间
			String stopTimes = this.setStopTime(swfFlowMain.getCreatDate(), swfFlowMain.getCloseDate());
			swfFlowMain.setSetStopTime(stopTimes);
			// 从存储表里查询，並复制到swfLogList，和swfpathLogList
			List<SwfLogStore> swfLogStoreList = this.workFlowService.findStoreNodesByFlowID(flowID);
			if (swfLogStoreList != null && !swfLogStoreList.isEmpty()) {
				for (SwfLogStore swfLogStore : swfLogStoreList) {
					swfLogList.add(swfLogStore.toSwfLog());
				}
			}
			List<SwfPathLogStore> swfPathLogStoreList = this.workFlowService.findStoreFlowPathLogByFlowID(flowID);
			if (swfPathLogStoreList != null && !swfPathLogStoreList.isEmpty()) {
				for (SwfPathLogStore swfPathLogStore : swfPathLogStoreList) {
					swfPathLogList.add(swfPathLogStore.toSwfPathLog());
				}
			}
		} else {
			String stopTimes = this.setStopTime(swfFlowMain.getCreatDate(), new Date());
			swfFlowMain.setSetStopTime(stopTimes);
			swfLogList = this.workFlowService.findNodesByFlowID(flowID);
			swfPathLogList = this.workFlowService.findFlowPathLogByFlowID(flowID);
		}
		// 状态中文名称转换
		String registNo = "";
		for (SwfLog swfLog : swfLogList) {
			registNo = swfLog.getRegistNo();
			swfLog.setNodeStatusName(this.codeService.translateCodeCode("ClaimStatus", swfLog.getNodeStatus(), true));
		}
		// 取出通赔信息
		PrpLgeneralClaimTask prpLgeneralClaimTask = null;
		List<PrpLgeneralClaimTask> prpLgeneralClaimTaskList = this.generalClaimService.queryByRegistNo(registNo);
		List<PrpLgeneralClaimTaskLog> prpLgeneralClaimTaskLogList = this.generalClaimService.queryHistoryByRegistNo(registNo);
		if (prpLgeneralClaimTaskList != null && prpLgeneralClaimTaskList.size() > 0) {
			prpLgeneralClaimTask = prpLgeneralClaimTaskList.get(0);
		}
		httpServletRequest.setAttribute("prpLgeneralClaimTask", prpLgeneralClaimTask);
		httpServletRequest.setAttribute("prpLgeneralClaimTaskLogList", prpLgeneralClaimTaskLogList);

		SwfLog firstSwfLog = swfLogList.get(0); // 第一个节点
		SwfLog swfLog = new SwfLog();
		swfLog.setSwfLogList(swfLogList);
		httpServletRequest.setAttribute("swfLog", swfLog);
		// 根据流程号查询流程路径信息
		SwfPathLog swfPathLog = new SwfPathLog();
		swfPathLog.setPathLogList(swfPathLogList);
		httpServletRequest.setAttribute("swfPathLog", swfPathLog);

		int treeLayerDeep = 0;// 节点层深度
		int treeStartNodeNo = 1;// 缺省开始节点为1
		List<SwfLog> swfLogTreeList = new ArrayList<SwfLog>();
		// 加入第一个报案节点
		if (treeLayerDeep == 0) {
			for (SwfLog tempSwfLog : swfLogList) {
				if (tempSwfLog.getNodeNo() == 0 || tempSwfLog.getNodeNo() == 1) {
					treeLayerDeep = 1;
					tempSwfLog.setTreeLayer(treeLayerDeep);
					// 将得到层与节点信息装入节点树
					tempSwfLog.setStartNodeNo(0);
					tempSwfLog.setEndNodeNo(1);
					swfLogList.add(tempSwfLog);
					break;
				}
			}
		}
		this.createNodeTree(swfLogTreeList, swfLogList, swfPathLogList, treeStartNodeNo, treeLayerDeep);

		swfLogTreeList.add(firstSwfLog);
		// 以下为测试代码
		List<SwfLog> treeList = this.orderFlowList(swfLogTreeList);
		// 处理核赔节点，流程查询中核赔节点需要核赔工作流的数据
		for (SwfLog temp : treeList) {
			if ("veric".equals(temp.getNodeType())) {
				String wflogSql = " relateflowid = '" + temp.getId().getFlowID() + "' and relatelogno = " + temp.getId().getLogNo();// 查询核赔的
				wflogSql += " and nodeNo not in (1,11) ";// 过滤出单员与审核通过节点
				wflogSql += " order by logno desc";
				List<WfLog> wflogList = this.getWfLogService().findByConditions(wflogSql);
				WfLog wfLog = null;
				if (wflogList != null && !wflogList.isEmpty()) {
					wfLog = wflogList.get(0);
					temp.setiFlowID(wfLog.getId().getFlowId());
					temp.setiLogNo(wfLog.getId().getLogNo());
					temp.setiModelNo(wfLog.getModelNo());
					temp.setiNodeNo(wfLog.getId().getLogNo());
					temp.setiBusinessNo(wfLog.getBusinessNo());
					temp.setBusinessType(wfLog.getBusinessType());
				}
			}
		}
		SwfLog swfLogTree = new SwfLog();
		swfLogTree.setSwfLogList(treeList);
		httpServletRequest.setAttribute("swfLog", swfLogTree);
		httpServletRequest.setAttribute("swfPathLogList", swfPathLogList);
		// 得到状态代码
		List<PrpDcode> claimStatusList = this.codeService.getCodeType("ClaimStatus", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("claimStatus", claimStatusList);
		// 查询当前工作流主要信息
		httpServletRequest.setAttribute("swfFlowMain", swfFlowMain);
	}

	/**
	 * 产生节点树的方法
	 * @param wfLogTreeDto Collection 节点树
	 * @param swfLogList Collection wfLog列表
	 * @param swfPathLogList Collection wfPathLog列表
	 * @param treeStartNodeNo int 树的开始节点
	 * @param treeLayerDeep int 树的层深
	 * @throws Exception
	 */
	private void createNodeTree(List<SwfLog> swfLogTreeList, List<SwfLog> swfLogList, List<SwfPathLog> swfPathLogList, int treeStartNodeNo, int treeLayerDeep) throws Exception {
		/**
		 * 程序设计思路: 根据第一个节点，做为开始节点，得到startNode节点的endNode,以此endNode节点的做为
		 * startNode再查询它的EndNode,以此方法递归得到一个树状结构
		 */
		int treeEndNodeNo = 0; // 树或边的终止节点
		List<SwfPathLog> layerPathList = new ArrayList<SwfPathLog>(); // 存放本层的节点
		for (int i = 0; i < swfPathLogList.size(); i++) {
			SwfPathLog swfPathLogNode = swfPathLogList.get(i);
			if (swfPathLogNode.getStartNodeNo() == treeStartNodeNo) {
				layerPathList.add(swfPathLogNode);
			}
		}
		// 从层里查询到有哪些结束节点，判断结束节点是否有後续节点，如果有则取後续节点，没有则结束
		for (int k = 0; k < layerPathList.size(); k++) {
			SwfPathLog swfPathLogLayer = layerPathList.get(k);
			treeEndNodeNo = swfPathLogLayer.getEndNodeNo();
			// 是否有後续节点
			if (isExistNextNode(swfPathLogList, treeEndNodeNo)) {
				// 查找与条件中的结束节点
				for (int j = 0; j < swfLogList.size(); j++) {
					SwfLog swfLogNode = swfLogList.get(j);
					if (swfLogNode.getId().getLogNo() == treeEndNodeNo) {
						// 设置层
						if (k == 0) {
							treeLayerDeep = treeLayerDeep + 1;
						}
						swfLogNode.setTreeLayer(treeLayerDeep);
						// 将得到层与节点信息装入节点树
						swfLogNode.setStartNodeNo(swfPathLogLayer.getStartNodeNo());
						swfLogNode.setEndNodeNo(swfPathLogLayer.getEndNodeNo());
						swfLogTreeList.add(swfLogNode);
						// 递归查找下一节点
						createNodeTree(swfLogTreeList, swfLogList, swfPathLogList, treeEndNodeNo, treeLayerDeep);
						break; // 取得下一节点後不再循环
					}
				}// end for
			} else {
				// 查找与条件中的结束节点
				for (int j = 0; j < swfLogList.size(); j++) {
					SwfLog swfLogNode = swfLogList.get(j);
					if (swfLogNode.getId().getLogNo() == treeEndNodeNo) {
						// 设置层
						if (k == 0) {
							treeLayerDeep = treeLayerDeep + 1;
						}
						swfLogNode.setTreeLayer(treeLayerDeep);
						// 将得到层与节点信息装入节点树
						swfLogNode.setStartNodeNo(swfPathLogLayer.getStartNodeNo());
						swfLogNode.setEndNodeNo(swfPathLogLayer.getEndNodeNo());
						swfLogTreeList.add(swfLogNode);
						break; // 取得下一节点後不再循环
					}
				}
			}
		}
		treeLayerDeep = treeLayerDeep - 1;
	}

	/**
	 * 判断是否有後续节点
	 * @param sourceList ArrayList
	 * @param startNode int
	 * @throws Exception
	 * @return boolean
	 */
	private static boolean isExistNextNode(List<SwfPathLog> sourceList, int startNode) throws Exception {
		boolean isExist = false; // 是否有後续节点
		for (int i = 0; i < sourceList.size(); i++) {
			SwfPathLog swfPathLogNodeDto = sourceList.get(i);
			if (swfPathLogNodeDto.getStartNodeNo() == startNode) {
				isExist = true;
			}
		}
		return isExist;
	}

	/**
	 * 整理成要输出的顺序列表
	 * @param sourceList ArrayList
	 * @throws Exception
	 * @return ArrayList
	 */
	private List<SwfLog> orderFlowList(List<SwfLog> sourceList) throws Exception {
		List<SwfLog> sameLayerList = new ArrayList<SwfLog>(); // 存放同层
		List<SwfLog> noSameNodeList = new ArrayList<SwfLog>(); // 同层没有相同节点列表
		List<SwfLog> orderList = new ArrayList<SwfLog>(); // 已排好次序的列表
		int startNodeNo = 0; // 开始节点
		int endNodeNo = 0; // 结束节点
		int treeLayerDeep = 0; // 层中节点数
		int countLayerSameNode = 0;// 同层中相同节点的个数

		// 去掉startNodeNo，endNodeNo相同的同层节点,
		for (int i = 0; i < sourceList.size(); i++) {
			SwfLog swfLogSource = sourceList.get(i);
			startNodeNo = swfLogSource.getStartNodeNo();
			endNodeNo = swfLogSource.getEndNodeNo();
			treeLayerDeep = swfLogSource.getTreeLayer();
			for (int j = 0; j < noSameNodeList.size(); j++) {
				SwfLog swfLogDiff = noSameNodeList.get(j); // 用於比较的Dto
				if (swfLogDiff.getStartNodeNo() == startNodeNo && swfLogDiff.getEndNodeNo() == endNodeNo && swfLogDiff.getTreeLayer() == treeLayerDeep) {
					countLayerSameNode = 1;
					break;
				}
			}
			if (countLayerSameNode == 0)
				noSameNodeList.add(swfLogSource);
			countLayerSameNode = 0;
		}
		// 将同层按顺序排列
		for (int k = 0; k < noSameNodeList.size(); k++) {
			for (int n = 0; n < noSameNodeList.size(); n++) {
				SwfLog swfLogList = noSameNodeList.get(n);
				if (swfLogList.getTreeLayer() == k + 1) {
					sameLayerList.add(swfLogList);
				}
			}
		}
		// 对同层节点计数，並列出位置次序，从高到低的次序
		int nodeCount = 0;// 节点数
		int nodePosOrder = 0;// 节点在层的位置
		int oldLayer = 0;// 旧的层
		int currLayer = 0;// 当前层
		for (int k = 0; k < sameLayerList.size(); k++) {
			SwfLog swfLogOrder = sameLayerList.get(k);
			// 得到前一节点的层数,根据前一节点的层数判断是否放在一个层
			if (k == 0) {
				oldLayer = 1;
			} else {
				oldLayer = currLayer;
			}
			currLayer = swfLogOrder.getTreeLayer();
			for (int n = 0; n < sameLayerList.size(); n++) {
				SwfLog swfLogList = sameLayerList.get(n);
				if (swfLogList.getTreeLayer() == currLayer) {
					nodeCount = nodeCount + 1;
				}
			}
			currLayer = swfLogOrder.getTreeLayer();
			swfLogOrder.setCountNode(nodeCount);
			// 设置次序
			// 第一个节点nodePosOrder为1
			if (oldLayer == currLayer) {
				if (nodePosOrder > 0) {
					nodePosOrder = nodePosOrder - 1;
				} else {
					nodePosOrder = 1;
				}
				swfLogOrder.setNodePosLayer(nodePosOrder);
			} else {
				nodePosOrder = 1;
				swfLogOrder.setNodePosLayer(nodePosOrder);
				nodePosOrder = nodeCount + 1;
			}
			orderList.add(swfLogOrder);
			nodeCount = 0;
		}
		// 计算流入和流出时间差
		// 计算停留时间差
		int size = orderList.size();
		Date flowInTime = null; // 流入时间
		Date submitTime = null; // 提交时间
		for (int i = 0; i < size; i++) {
			SwfLog swfLogListStopTime = orderList.get(i);
			if ("4".equals(swfLogListStopTime.getNodeStatus()) && CommonUtils.isEmpty(swfLogListStopTime.getSubmitTime())) {
				continue;
			}
			flowInTime = this.getTimeToDate(swfLogListStopTime.getFlowInTime());
			submitTime = this.getTimeToDate(swfLogListStopTime.getSubmitTime());
			// 对於没有时间的案件 设置stopTime = 0 ;
			swfLogListStopTime.setStopTimeDesc(this.setStopTime(flowInTime, submitTime));
		}
		return orderList;
	}

	/**
	 * 判断日期源串是否是日期
	 * @param sourceDateTime String
	 * @throws Exception
	 * @return boolean
	 */
	private Date getTimeToDate(String sourceDateTime) throws Exception {
		try {
			return ((DateFormat)formatter18.clone()).parse(sourceDateTime);
		} catch (Exception e) {
			try {
				return ((DateFormat)formatter10.clone()).parse(sourceDateTime);
			} catch (Exception ex) {
				return new Date();
			}
		}
	}

	/**
	 * 计算案件停留时间
	 * @author 中科软
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	private String setStopTime(Date startTime, Date endTime) {
		if (startTime == null || endTime == null) {
			return "0";
		}
		long l = endTime.getTime() - startTime.getTime();
		if (l > 0) {
			int day = new Float(l / (24 * 60 * 60 * 1000)).intValue();
			int hour = new Float((l / (60 * 60 * 1000) - day * 24)).intValue();
			int min = new Float(((l / (60 * 1000)) - day * 24 * 60 - hour * 60)).intValue();
			if (day == 0 && hour == 0 && min == 0) {
				return "小於1分鐘";
			}
			return day + "天" + hour + "小時" + min + "分鐘";// hours就是两者的时间差
		} else {
			return "小於1分鐘";
		}
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public GeneralClaimService getGeneralClaimService() {
		return generalClaimService;
	}

	public void setGeneralClaimService(GeneralClaimService generalClaimService) {
		this.generalClaimService = generalClaimService;
	}

	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}
}
