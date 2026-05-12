package com.sinosoft.claim.workflow.util;

import ins.framework.common.DateTime;
import ins.framework.common.ServiceFactory;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * <p>
 * Title: WorkFlowSpecialFlowViewHelper
 * </p>
 * <p>
 * Description:工作流指定节点关系的特别流程ViewHelper类，在该类中完成流转数据的的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c)
 * </p>
 * @author 中科软
 */
public class WorkFlowSpecialFlowViewHelper {
	/**
	 * 默认构造方法
	 */
	public WorkFlowSpecialFlowViewHelper() {// COM.IIDIDispatch
	}

	/* ========================第一部分：工作引擎正向/逆向操作======================== */
	/**
	 * 操作工作流的数据整理,此函数就是所有工作流流转的引擎入口， 只有简易赔案才使用该代码
	 * @param user UserDto 用户dto
	 * @param swfLogFunctionIn 传参数
	 * @throws Exception
	 * @return WorkFlowDto 工作流程数据传输数据结构
	 */
	public WorkFlowDto viewToDto(UserDto user, SwfLog swfLogFunctionIn) throws Exception {
		// 取得当前用户信息，写操作员信息到Dto中
		WorkFlowDto workFlowDto = new WorkFlowDto();
		WorkFlowDto workFlowDtoTemp = new WorkFlowDto();
		SwfLog swfLog = null;
		List<SwfLog> submitLogList = new ArrayList<SwfLog>();// 生成新节点後，所要增加的节点
		List<SwfPathLog> submitPathLogList = new ArrayList<SwfPathLog>();// 生成新节点後，所要增加的关系
		List<SwfLog> deleteLogList = new ArrayList<SwfLog>(); // 生成新节点後，删除数据库已存在节点
		List<SwfPathLog> deletePathLogList = new ArrayList<SwfPathLog>(); // 生成新关系後，删除数据库已存在关系

		String swfLogFlowID = swfLogFunctionIn.getId().getFlowID(); // 所进入的流程号码
		int swfLogLogNo = swfLogFunctionIn.getId().getLogNo();// 所进入流程中的节点号码
		String claimTypeFlag = swfLogFunctionIn.getClaimTypeFlag();// 简易赔案条件判断
		// 1。查询出当前工作流流转数据/查找当前节点
		int logMaxNo = 0;
		int pathMaxNo = 0;
		if (DataUtils.emptyToNull(swfLogFlowID) != null && swfLogLogNo > 0) {
			// 利用主键flowID,LogNo查工作节点
			swfLog = this.getWorkFlowService().findNodeByPrimaryKey(swfLogFlowID, swfLogLogNo);
			// 並取得工作流上点和边的最大号码
			logMaxNo = this.getWorkFlowService().getSwfLogMaxLogNo(swfLog.getId().getFlowID());
			pathMaxNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(swfLog.getId().getFlowID());
		}
		// 2。判断工作流是否可以操作
		if (swfLog == null) { // 有工作流程查询出来
			throw new UserException(1, 3, "工作流", "沒有發現當前工作流節點");
		}
		// 3。要判断工作流程是否结束，如果结束了，下面的都不需要操作的
		if (this.getWorkFlowService().checkFlowClose(swfLog.getId().getFlowID())) {
			// 工作流已经关闭了
			throw new UserException(1, 3, "工作流", "當前工作流已經流轉結束!");
		}
		// 4.判断是哪种简易赔案状态的操作
		// 01-简易赔案入口操作||02 简易赔案暂存操作||03 简易赔案提交操作
		// 首先查询出工作流主表状态数据
		if ("01".equals(claimTypeFlag) || "02".equals(claimTypeFlag) || "03".equals(claimTypeFlag)) { // 首先查询出工作流主表状态数据

			SwfFlowMain swfFlowMain = this.getWorkFlowService().findFlowMainByPrimaryKey(swfLog.getId().getFlowID());
			// 修改工作流主表状态标记位
			swfFlowMain.setClaimTypeFlag(claimTypeFlag);
			workFlowDto.setUpdateMainFlow(true);
			workFlowDto.setSwfFlowMain(swfFlowMain);
			swfLog.setClaimTypeFlag(claimTypeFlag);
			swfLog.setHandlerCode(user.getUserCode());
			swfLog.setHandlerName(user.getUserName());
			swfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
			if ("03".equals(claimTypeFlag)) {
				swfLog.setNodeStatus("4");// 先给放在正在处理的任务里面吧。
				swfLog.setKeyOut(swfLog.getBusinessNo());
			} else {
				swfLog.setNodeStatus("2");// 先给放在正在处理的任务里面吧。
			}
			// 更新当前操作节点的人员和时间信息，这样流程留了一个痕迹。
			workFlowDto.setUpdate(true);
		}

		if ("03".equals(claimTypeFlag)) { // "简易赔案提交操作"
			swfLog.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
			// 做简易赔案提交的数据整理
			workFlowDtoTemp = this.submitWorkFlow(user, swfLog, swfLogFunctionIn, logMaxNo, pathMaxNo);
			submitLogList = workFlowDtoTemp.getSubmitSwfLogList();
			if (submitLogList == null) {
				submitLogList = new ArrayList<SwfLog>(); // 允许新增加的节点没数据
			}
			submitPathLogList = workFlowDtoTemp.getSubmitSwfPathLogList();
			if (submitPathLogList == null) {
				submitPathLogList = new ArrayList<SwfPathLog>(); // 允许为空
			}
			deleteLogList = workFlowDtoTemp.getDeleteSwfLogList();
			if (deleteLogList == null) {
				deleteLogList = new ArrayList<SwfLog>(); // 允许为空
			}
			deletePathLogList = workFlowDtoTemp.getDeleteSwfPathLogList();
			if (deletePathLogList == null) {
				deletePathLogList = new ArrayList<SwfPathLog>(); // 允许为空
			}
			workFlowDto.setSubmit(workFlowDtoTemp.getSubmit());
			workFlowDto.setSubmitSwfLogList(submitLogList);
			workFlowDto.setSubmitSwfPathLogList(submitPathLogList);
			workFlowDto.setDelete(workFlowDtoTemp.getDelete());
			workFlowDto.setDeleteSwfLogList(deleteLogList);
			workFlowDto.setDeleteSwfPathLogList(deletePathLogList);
		} // 判断是提交操作的结束
		return workFlowDto;
	}

	/** =================================================================* */
	/**
	 * 根据当前节点的内容，提交工作流的下面的节点
	 * @param user 当前操作用户
	 * @param swfLog 当前节点信息
	 * @param swfLogFunctionIn 工作流传入参数
	 * @param logNo swfLog中的LogNo的最大号
	 * @param pathNo swfPathLog中的PathNo的最大号
	 * @return workFlowDto 整理好的流转数据
	 * @throws Exception
	 */
	private WorkFlowDto submitWorkFlow(UserDto user, SwfLog swfLog, SwfLog swfLogFunctionIn, int logNo, int pathNo) throws Exception {
		WorkFlowDto workFlowDto = new WorkFlowDto();
		WorkFlowDto workFlowDtoTemp = new WorkFlowDto();
		// 可以在这个函数中同时扩展其他特别流程的扩展
		workFlowDtoTemp = this.getSubmitFlowInfo(user, swfLog, swfLogFunctionIn, logNo, pathNo);
		workFlowDto.setSubmit(workFlowDtoTemp.getSubmit());
		workFlowDto.setSubmitSwfLogList(workFlowDtoTemp.getSubmitSwfLogList());
		workFlowDto.setSubmitSwfPathLogList(workFlowDtoTemp.getSubmitSwfPathLogList());
		workFlowDto.setDelete(workFlowDtoTemp.getDelete());
		workFlowDto.setDeleteSwfLogList(workFlowDtoTemp.getDeleteSwfLogList());
		workFlowDto.setDeleteSwfPathLogList(workFlowDtoTemp.getDeleteSwfPathLogList());
		return workFlowDto;
	}

	/** =================================================================* */
	/**
	 * 整理新生成的SwfLogList数据和SwfPathLogList数据
	 * @param user UserDto
	 * @param swfLogCurrentNode SwfLog //当前节点的信息
	 * @param swfLogFunctionInOld SwfLog //函数传如的参数
	 * @param logNo int
	 * @param pathNo int
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	private WorkFlowDto getSubmitFlowInfo(UserDto user, SwfLog swfLogCurrentNode, SwfLog swfLogFunctionInOld, int logNo, int pathNo) throws Exception {
		// 思路：写定流程数据的内容，並删除原来已经存在的查勘，定损和立案相关的数据
		// （1）利用传入的swfLogCurrentNode,作为Node赋值数据的来源
		// （2）除了报案和调度保留外，其他的节点都有更新，並设置新数据到节点上。
		// （3）若数据库已经有查勘和定损和立案节点，那么考虑处理方式。
		// 1。 取得当前用户信息，写操作员信息到Dto中
		WorkFlowDto workFlowDto = new WorkFlowDto();
		// 2.取工作流号码
		String flowID = swfLogCurrentNode.getId().getFlowID();
		/*---3.取工作流的模板号*/
		int modelNo = swfLogCurrentNode.getModelNo();
		/*
		 * 4.---------------------查询节点表swfNode的定义--------------------------------
		 * ----
		 */
		List<SwfLog> submitSwfLogList = new ArrayList<SwfLog>(); // 整理需要新插入的工作流程节点
		List<SwfPathLog> submitSwfPathLogList = new ArrayList<SwfPathLog>();// 整理需要新插入的工作流程边
		List<SwfLog> deleteSwfLogList = new ArrayList<SwfLog>(); // 整理需要删除的工作流程节点
		List<SwfPathLog> deleteSwfPathLogList = new ArrayList<SwfPathLog>();// 整理需要删除的工作流程边
		List<SwfLog> swfNodeNextList = new ArrayList<SwfLog>();// 存放从界面传来需要送的多个节点

		String nodeType = "";
		String policyNo = "";
		// 从业务那传过来的指定节点。
		swfNodeNextList = swfLogFunctionInOld.getSwfLogList();
		// 1。首先从检查立案开始做起：
		String strSql = "flowId ='" + flowID + "' and nodeType='claim'";
		List<SwfLog> swfLogClaimNodeList = this.getWorkFlowService().findByConditions(strSql);
		// 2.检查已有的立案的被处理的情况並处理。
		for (int j = 0; j < swfLogClaimNodeList.size(); j++) {
			SwfLog swfLogClaimNode = swfLogClaimNodeList.get(j); // 获得已有的立案节点
			// 若查找到业务穿过来有此立案的立案等信息，则可以进行处理数据。
			for (int i = 0; i < swfNodeNextList.size(); i++) {
				SwfLog nowNode = swfNodeNextList.get(i);
				nodeType = nowNode.getNodeType();
				policyNo = nowNode.getPolicyNo();
				if ("claim".equals(nodeType) && policyNo.equals(swfLogClaimNode.getPolicyNo())) {
					// 查询到是立案信息，並且保单号相等，则需要整理的数据
					this.getSubmitSwfLogDtoInfoBySwfLogDto(swfLogClaimNode, swfLogCurrentNode, user);
					swfLogClaimNode.setKeyOut(nowNode.getKeyOut());
					swfLogClaimNode.setBusinessNo(nowNode.getKeyOut());
					// 需要增加到更新的SwfLogList列表中。
					submitSwfLogList.add(swfLogClaimNode);
					// 需要增加到删除的SwfLogList列表中，删除已经在数据库中有的数据。
					deleteSwfLogList.add(swfLogClaimNode);
				}
			}
		}
		// 3.查询出调度任务来进行处理。
		strSql = "flowId ='" + flowID + "' and nodeType='sched'";
		List<SwfLog> swfLogSchedNodeList = this.getWorkFlowService().findByConditions(strSql);
		SwfLog swfLogDtoSched = new SwfLog();
		if(swfLogSchedNodeList.size()>0){
			swfLogDtoSched = swfLogSchedNodeList.get(0); // 获得已有的调度节点
		}
		// 设置调度为已经完成,並更新调度信息
		swfLogDtoSched = this.getSubmitSwfLogDtoInfoBySwfLogDto(swfLogDtoSched, swfLogCurrentNode, user);
		submitSwfLogList.add(swfLogDtoSched);
		deleteSwfLogList.add(swfLogDtoSched);

		// 4.增加一个单证节点，为了之後的定损或者核损连接来用的。
		SwfNode swfNode = new SwfNode();
		// 设置单证节点，从模板上取单证的节点的设置信息
		// 初始化下一个节点的各项信息
		// 如果已经有单正了，那么需要删除原来的单正信息
		strSql = "flowId ='" + flowID + "' and nodeType='certi' and nodestatus<4";
		List<SwfLog> swfLogCertiNodeList = this.getWorkFlowService().findByConditions(strSql);
		SwfLog swfLogDtoCertiold = new SwfLog();
		if(swfLogCertiNodeList.size()>0){
			swfLogDtoCertiold = swfLogCertiNodeList.get(0); // 获得已有的单正节点
			deleteSwfLogList.add(swfLogDtoCertiold);
		}
		/*---------------------写流程节点表swfLog------------------------------------*/
		SwfLog swfLogCerti = new SwfLog();
		// 同时设置单证节点已经提交
		for (int i = 0; i < swfNodeNextList.size(); i++) {
			SwfLog nowNode = swfNodeNextList.get(i);
			nodeType = nowNode.getNodeType();
			policyNo = nowNode.getPolicyNo();
			if ("certi".equals(nodeType)) {
				swfNode = new SwfNode();
				swfNode = this.getFirstNodeTypeNode(modelNo, "certi");
				swfLogCerti = new SwfLog();
				swfNode = this.setSwfNodeInfo(swfNode, nowNode); // 从nowNode上面收集数据信息
				this.getSwfLogDtoInfoBySwfNode(swfLogCerti, swfNode, user, flowID, modelNo, logNo, "4", swfLogFunctionInOld.getKeyIn(), swfLogCurrentNode);
				submitSwfLogList.add(swfLogCerti);// 需要增加到更新的SwfLogList列表中
				logNo++; // 最大号+1
			}
		}
		// 6 增加查勘，定损(车辆和财产)，核损，计算书和核赔的信息
		strSql = "flowId ='" + flowID + "' and nodeType in ('check','certa','propc')";
		List<SwfLog> swfLogCheckCertaNodeList = this.getWorkFlowService().findByConditions(strSql);
		// 2.先从业务中传过来的查勘，定损，计算书信息。
		SwfPathLog swfPathLog = new SwfPathLog();
		SwfLog swfLogCompe = new SwfLog();
		for (int i = 0; i < swfNodeNextList.size(); i++) {
			SwfLog nowNode = new SwfLog();
			nowNode = swfNodeNextList.get(i);
			nodeType = nowNode.getNodeType();
			policyNo = nowNode.getPolicyNo();
			// 新增计算书，和理算关系，新增核赔和计算书的关系
			if ("compp".equals(nodeType)) {
				swfNode = new SwfNode();
				// 设置理算节点，从模板上取理算的节点的设置信息
				swfNode = this.getFirstNodeTypeNode(modelNo, "compe"); // 产生理算包
				swfLogCompe = new SwfLog();
				swfNode = this.setSwfNodeInfo(swfNode, nowNode); // 从nowNode上面收集数据信息
				// 5.增加一个理算节点，为了单证连接用的。
				// 初始化下一个节点的各项信息
				// ---------------------写流程节点表swfLog------------------------------------
				this.getSwfLogDtoInfoBySwfNode(swfLogCompe, swfNode, user, flowID, modelNo, logNo, "0", swfLogFunctionInOld.getKeyIn(), swfLogCurrentNode);
				submitSwfLogList.add(swfLogCompe);// 需要增加到更新的SwfLogList列表中
				logNo++; // 最大号+1
				// .---------------------写流程线表swfPathLog---------------------------------
				swfPathLog = new SwfPathLog(); // 单证-》理算
				this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLogCerti, swfLogCompe, flowID, modelNo, pathNo);
				submitSwfPathLogList.add(swfPathLog);
				pathNo++;

				swfNode = new SwfNode();
				// 设置计算书节点，从模板上取理算的节点的设置信息
				swfNode = this.getFirstNodeTypeNode(modelNo, nodeType);
				SwfLog swfLogCompp = new SwfLog(); // 理算节点
				swfNode = setSwfNodeInfo(swfNode, nowNode); // 从nowNode上面收集数据信息
				// 初始化下一个节点的各项信息
				/*---------------------写流程节点表swfLog------------------------------------*/
				this.getSwfLogDtoInfoBySwfNode(swfLogCompp, swfNode, user, flowID, modelNo, logNo, "4", swfLogFunctionInOld.getKeyIn(), swfLogCurrentNode);
				// 需要增加到更新的SwfLogList列表中
				submitSwfLogList.add(swfLogCompp);
				logNo++; // 最大号+1
				/*
				 * .---------------------写流程线表swfPathLog--------------------------
				 * -------
				 */
				swfPathLog = new SwfPathLog(); // 理算-》计算书
				this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLogCompe, swfLogCompp, flowID, modelNo, pathNo);
				submitSwfPathLogList.add(swfPathLog);
				pathNo++;

				// 同时产生核赔
				swfNode = new SwfNode();
				// 设置核赔节点，从模板上取核赔的节点的设置信息
				swfNode = this.getFirstNodeTypeNode(modelNo, "veric");
				SwfLog swfLogVeric = new SwfLog(); // 核赔节点
				swfNode = this.setSwfNodeInfo(swfNode, nowNode); // 从nowNode上面收集数据信息
				// 初始化下一个节点的各项信息
				/*---------------------写流程节点表swfLog------------------------------------*/
				// 只有在核赔这必须设置此节点的状态为未处理，其他的都默认为4-已提交
				this.getSwfLogDtoInfoBySwfNode(swfLogVeric, swfNode, user, flowID, modelNo, logNo, "0", swfLogFunctionInOld.getKeyIn(), swfLogCurrentNode);
				// 需要增加到更新的SwfLogList列表中
				submitSwfLogList.add(swfLogVeric);
				logNo++; // 最大号+1
				/*
				 * .---------------------写流程线表swfPathLog--------------------------
				 * -------
				 */
				swfPathLog = new SwfPathLog(); // 计算书-》核赔
				this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLogCompp, swfLogVeric, flowID, modelNo, pathNo);
				submitSwfPathLogList.add(swfPathLog);
				pathNo++;

			}
			if ("check".equals(nodeType) || "certa".equals(nodeType) || "propc".equals(nodeType)) {
				// 检查已有的查勘，定损的被处理的情况並处理。
				int findCheck = 0;
				for (int j = 0; j < swfLogCheckCertaNodeList.size(); j++) {
					SwfLog swfLogCheckCertaNode = new SwfLog();
					swfLogCheckCertaNode = swfLogCheckCertaNodeList.get(j); // 获得已有的节点数据
					// 如果发现原来数据中已经有了查勘已完成的数据，
					// 则不会重新写查勘数据
					if (nodeType.equals(swfLogCheckCertaNode.getNodeType()) && nodeType.equals("check")) {
						findCheck = 1;// 发现了查勘节点
						if (!"4".equals(swfLogCheckCertaNode.getNodeStatus())) {
							// 查勘节点未处理，或正在处理
							swfLogCheckCertaNode = this.getSubmitSwfLogDtoInfoBySwfLogDto(swfLogCheckCertaNode, swfLogCurrentNode, user);
							// 需要增加到更新的SwfLogList列表中。
							submitSwfLogList.add(swfLogCheckCertaNode);
							// 需要增加到删除的SwfLogList列表中，删除已经在数据库中有的数据。
							deleteSwfLogList.add(swfLogCheckCertaNode);
						}
					}
					if (nodeType.equals("propc") || nodeType.equals("certa")) { // 若全是定损
						// 设置定损和财产定损的节点和边数据被删除
						if ("certa".equals(swfLogCheckCertaNode.getNodeType()) || "propc".equals(swfLogCheckCertaNode.getNodeType())) {
							deleteSwfLogList.add(swfLogCheckCertaNode);
						}
					}
				}// end for 数据库里的定损查勘结束
				if (findCheck == 0 && nodeType.equals("check")) {
					// 数据库里面根本没有查勘的话，需要单独加入
					swfNode = new SwfNode();
					// 设置查勘节点，从模板上取查勘的节点的设置信息
					swfNode = this.getFirstNodeTypeNode(modelNo, nodeType);
					SwfLog swfLogCheck = new SwfLog();
					swfNode = this.setSwfNodeInfo(swfNode, nowNode); // 从nowNode上面收集数据信息
					// 初始化下一个节点的各项信息
					/*---------------------写流程节点表swfLog------------------------------------*/
					this.getSwfLogDtoInfoBySwfNode(swfLogCheck, swfNode, user, flowID, modelNo, logNo, "4", swfLogFunctionInOld.getKeyIn(), swfLogCurrentNode);
					submitSwfLogList.add(swfLogCheck);// 需要增加到更新的SwfLogList列表中
					logNo++; // 最大号+1

					/*
					 * .---------------------写流程线表swfPathLog----------------------
					 * -----------
					 */
					swfPathLog = new SwfPathLog(); // 调度-》查勘
					this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLogDtoSched, swfLogCheck, flowID, modelNo, pathNo);
					submitSwfPathLogList.add(swfPathLog);
					pathNo++;
				}
				// 新增车辆定损，车辆核损，和单证之间的关系
				if (nodeType.equals("certa")) {
					swfNode = new SwfNode();
					// 设置定损节点，从模板上取定损的节点的设置信息
					swfNode = this.getFirstNodeTypeNode(modelNo, nodeType);
					SwfLog swfLogCerta = new SwfLog(); // 定损节点
					swfNode = this.setSwfNodeInfo(swfNode, nowNode); // 从nowNode上面收集数据信息
					// 初始化下一个节点的各项信息
					// ---------------------写流程节点表swfLog------------------------------------
					this.getSwfLogDtoInfoBySwfNode(swfLogCerta, swfNode, user, flowID, modelNo, logNo, "4", swfLogFunctionInOld.getKeyIn(), swfLogCurrentNode);
					// 需要增加到更新的SwfLogList列表中
					submitSwfLogList.add(swfLogCerta);
					logNo++; // 最大号+1

					// ---------------------写流程线表swfPathLog---------------------------------
					swfPathLog = new SwfPathLog(); // 调度-》定损
					this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLogDtoSched, swfLogCerta, flowID, modelNo, pathNo);
					submitSwfPathLogList.add(swfPathLog);
					pathNo++;

					// 同时产生核损
					swfNode = new SwfNode();
					// 设置核损节点，从模板上取核损的节点的设置信息
					swfNode = this.getFirstNodeTypeNode(modelNo, "verif");
					SwfLog swfLogDtoVerif = new SwfLog(); // 定损节点
					swfNode = this.setSwfNodeInfo(swfNode, nowNode); // 从nowNode上面收集数据信息
					// 初始化下一个节点的各项信息
					// ---------------------写流程节点表swfLog------------------------------------
					getSwfLogDtoInfoBySwfNode(swfLogDtoVerif, swfNode, user, flowID, modelNo, logNo, "4", swfLogFunctionInOld.getKeyIn(), swfLogCurrentNode);
					// 需要增加到更新的SwfLogList列表中
					submitSwfLogList.add(swfLogDtoVerif);
					logNo++; // 最大号+1

					// ---------------------写流程线表swfPathLog---------------------------------
					swfPathLog = new SwfPathLog(); // 定损-》核损
					this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLogCerta, swfLogDtoVerif, flowID, modelNo, pathNo);
					submitSwfPathLogList.add(swfPathLog);
					pathNo++;
					// ---------------------写流程线表swfPathLog---------------------------------
					swfPathLog = new SwfPathLog(); // 核损-》单证
					this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLogDtoVerif, swfLogCerti, flowID, modelNo, pathNo);
					submitSwfPathLogList.add(swfPathLog);
					pathNo++;
				}
				// 新增财产定损，和单证之间的关系
				if (nodeType.equals("propc")) {
					swfNode = new SwfNode();
					// 设置财产定损节点，从模板上取财产定损的节点的设置信息
					swfNode = getFirstNodeTypeNode(modelNo, nodeType);
					SwfLog swfLogPropc = new SwfLog(); // 财产定损节点
					swfLogPropc.setBusinessNo(swfLogCurrentNode.getBusinessNo());
					// 初始化下一个节点的各项信息
					/*---------------------写流程节点表swfLog------------------------------------*/
					this.getSwfLogDtoInfoBySwfNode(swfLogPropc, swfNode, user, flowID, modelNo, logNo, "4", swfLogFunctionInOld.getKeyIn(), swfLogCurrentNode);
					// 需要增加到更新的SwfLogList列表中
					submitSwfLogList.add(swfLogPropc);
					logNo++; // 最大号+1

					/*
					 * .---------------------写流程线表swfPathLog----------------------
					 * -----------
					 */
					swfPathLog = new SwfPathLog(); // 调度-》财产定损
					this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLogDtoSched, swfLogPropc, flowID, modelNo, pathNo);
					submitSwfPathLogList.add(swfPathLog);
					pathNo++;

					/*
					 * .---------------------写流程线表swfPathLog----------------------
					 * -----------
					 */
					swfPathLog = new SwfPathLog(); // 财产定损-》单证
					this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLogPropc, swfLogCerti, flowID, modelNo, pathNo);
					submitSwfPathLogList.add(swfPathLog);
					pathNo++;

				}
			} // end for 从业务传过来的数据的Next的数据
			workFlowDto.setSubmitSwfLogList(submitSwfLogList);
			workFlowDto.setSubmitSwfPathLogList(submitSwfPathLogList);
			workFlowDto.setSubmit(true);
			workFlowDto.setDeleteSwfLogList(deleteSwfLogList);
			workFlowDto.setDeleteSwfPathLogList(deleteSwfPathLogList);
			workFlowDto.setDelete(true);
		}
		// 返回都整理完毕的工作流的流转数据。
		return workFlowDto;
	}

	// **设置数据库中已经有的节点为提交的节点
	private SwfLog getSubmitSwfLogDtoInfoBySwfLogDto(SwfLog swfLog, SwfLog swfLogCurrentNode, UserDto user) throws Exception {
		swfLog.setHandlerCode(user.getUserCode());
		swfLog.setHandlerName(user.getUserName());
		swfLog.setHandleDept(swfLogCurrentNode.getHandleDept());
		swfLog.setDeptName(swfLogCurrentNode.getDeptName());
		swfLog.setNodeStatus("4");
		swfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		swfLog.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		swfLog.setKeyOut(swfLogCurrentNode.getKeyIn());
		return swfLog;
	}

	/**
	 * 查询指定的模板中，有指定类型的第一个节点
	 * @param modelNo String 模板号码
	 * @param nodeType String 节点类型
	 * @throws Exception
	 * @return SwfNode
	 */
	private SwfNode getFirstNodeTypeNode(int modelNo, String nodeType) throws Exception { // 程序思路：
		// ---------------------------------------------------
		// 根据模板号码，节点类型查询出swfNodeDto数据
		// ---------------------------------------------------
		SwfNode swfNode = new SwfNode();
		String conditions = "modelNo=" + modelNo + " and nodeType='" + nodeType + "'";
		swfNode = this.getWorkFlowService().findModelFirstNodeByCondition(conditions);
		return swfNode;
	}

	/**
	 * 根据工作流模板上的相同节点定义，写swfLog表，工作流程节点的操作处理
	 * @param SwfLog swfLog
	 * @param SwfNode swfNode
	 * @param user UserDto
	 * @param flowID String
	 * @param modelNo int
	 * @param logNo int
	 * @param nodeStatus String 注意，这里的节点状态是可以进行设置的
	 * @param keyIn String
	 * @param swfLogFunctionIn SwfLog （当前工作流节点）
	 * @throws Exception
	 * @return SwfLog
	 */
	private SwfLog getSwfLogDtoInfoBySwfNode(SwfLog swfLog, SwfNode swfNode, UserDto user, String flowID, int modelNo, int logNo, String nodeStatus, String keyIn, SwfLog swfLogFunctionIn) throws Exception {
		swfLog.getId().setFlowID(flowID);
		swfLog.getId().setLogNo(logNo);
		swfLog.setModelNo(modelNo);
		swfLog.setNodeNo(swfNode.getId().getNodeNo());
		swfLog.setNodeName(swfNode.getNodeName());
		swfLog.setBusinessNo(swfLogFunctionIn.getRegistNo());
		swfLog.setBeforeHandlerCode(user.getUserCode());
		swfLog.setBeforeHandlerName(user.getUserName());
		swfLog.setTimeLimit(swfNode.getTimeLimit());
		swfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		swfLog.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		swfLog.setNodeStatus(nodeStatus);
		swfLog.setFlowStatus("1");
		swfLog.setPackageID("0");
		swfLog.setFlag(swfNode.getFlag());
		swfLog.setTaskNo(swfNode.getTaskNo());
		swfLog.setTaskType(swfNode.getTaskType());
		swfLog.setNodeType(swfNode.getNodeType());
		swfLog.setRegistNo(swfLogFunctionIn.getRegistNo());
		swfLog.setInsuredName(swfLogFunctionIn.getInsuredName());
		swfLog.setLossItemName(swfLogFunctionIn.getLossItemName());
		swfLog.setKeyIn(swfLogFunctionIn.getKeyIn());
		swfLog.setSubFlowID("0");
		swfLog.setMainFlowID("0");
		swfLog.setPosX(0);
		swfLog.setPosY(0);
		swfLog.setEndFlag(swfNode.getEndFlag());
		swfLog.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		String titleAttr = swfNode.getNodeName() + "節點" + "流入時間：" + swfLog.getFlowInTime() + "上一節點操作人:" + user.getUserName();
		swfLog.setTitleStr(titleAttr);
		// 设置默认节点上的人员
		swfLog.setHandlerCode(user.getUserCode());
		swfLog.setHandlerName(user.getUserName());
		swfLog.setComCode(swfLogFunctionIn.getComCode());
		// 设置附加数据
		swfLog.setHandlerRange(swfNode.getHandlerRange());
		swfLog.setExigenceGree(swfNode.getExigenceGree());
		swfLog.setHandleDept(swfNode.getHandleDept());
		swfLog.setDeptName(swfNode.getDeptName());
		swfLog.setPolicyNo(swfLogFunctionIn.getPolicyNo());
		swfLog.setRiskCode(swfLogFunctionIn.getRiskCode());

		if (swfLog.getHandleDept().equals(""))
			swfLog.setHandleDept(swfLogFunctionIn.getNewNewHandleDept());
		if (swfLog.getHandleDept().equals(""))
			swfLog.setHandleDept(user.getComCode());
		if (swfLog.getDeptName().equals(""))
			swfLog.setDeptName(swfLogFunctionIn.getNewNewDeptName());
		if (swfLog.getDeptName().equals(""))
			swfLog.setDeptName(user.getComName());

		if (!swfNode.getRiskCode().equals(""))
			swfLog.setRiskCode(swfNode.getRiskCode());
		if (!swfNode.getPolicyNo().equals(""))
			swfLog.setPolicyNo(swfNode.getPolicyNo());
		if (!swfNode.getKeyIn().equals(""))
			swfLog.setKeyIn(swfNode.getKeyIn());
		if (!swfNode.getBusinessNo().equals(""))
			swfLog.setBusinessNo(swfNode.getBusinessNo());
		if (!swfNode.getLossItemCode().equals(""))
			swfLog.setLossItemCode(swfNode.getLossItemCode());
		if (!swfNode.getLossItemName().equals(""))
			swfLog.setLossItemName(swfNode.getLossItemName());
		if (!swfNode.getInsureCarFlag().equals(""))
			swfLog.setInsureCarFlag(swfNode.getInsureCarFlag());
		swfLog.setKeyOut(swfLog.getBusinessNo());
		// 增加标的车判断
		if (swfLog.getLossItemCode() != null && "1".equals(swfLog.getLossItemCode())) {
			swfLog.setInsureCarFlag("1");
		}
		return swfLog;
	}

	/**
	 * 写swfPathlog表，工作流程线的操作处理
	 * @param swfPathLog SwfPathLog 新工作流流程路径节点
	 * @param swfLogCurr SwfLog 当前节点
	 * @param swfLogNext SwfLog 下一个节点
	 * @param flowID String 工作流流号
	 * @param modelNo int 工作模板号
	 * @param pathNo int 工作流程路径号
	 * @throws Exception
	 * @return SwfPathLog
	 */
	protected SwfPathLog getSwfPathLogDtoInfoBySwfLog(SwfPathLog swfPathLog, SwfLog swfLogCurr, SwfLog swfLogNext, String flowID, int modelNo, int pathNo) throws Exception {
		String pathName = "";
		swfPathLog.getId().setFlowID(flowID);
		swfPathLog.getId().setPathNo(pathNo);
		swfPathLog.setModelNo(modelNo);
		pathName = "从 " + swfLogCurr.getNodeName() + " 到 " + swfLogNext.getNodeName();
		swfPathLog.setPathName(pathName);
		swfPathLog.setStartNodeNo(swfLogCurr.getId().getLogNo()); // 只记录序号
		swfPathLog.setStartNodeName(swfLogCurr.getNodeName());
		swfPathLog.setEndNodeNo(swfLogNext.getId().getLogNo()); // 这里已经不是模板定义的号码
		swfPathLog.setEndNodeName(swfLogNext.getNodeName());
		swfPathLog.setFlag("");
		return swfPathLog;
	}

	/**
	 * 使用外部业务传入的参数来设置SwfNode上的数据，方便之後进行数据赋值
	 * @param swfNodeDto
	 * @param swfFunctoinInDto
	 * @return
	 * @throws Exception
	 */
	private SwfNode setSwfNodeInfo(SwfNode swfNodeDto, SwfLog nowNode) throws Exception { // 程序思路：
		// ---------------------------------------------------
		// 将根据模板号码，节点类型查询出swfNodeDto数据进行整理,设置如businessNo之类的参数
		// ---------------------------------------------------
		if (!nowNode.getBusinessNo().equals(""))
			swfNodeDto.setBusinessNo(nowNode.getBusinessNo());
		if (!nowNode.getKeyIn().equals(""))
			swfNodeDto.setKeyIn(nowNode.getKeyIn());
		if (!nowNode.getRiskCode().equals(""))
			swfNodeDto.setRiskCode(nowNode.getRiskCode());
		if (!nowNode.getLossItemCode().equals(""))
			swfNodeDto.setLossItemCode(nowNode.getLossItemCode());
		if (!nowNode.getLossItemName().equals(""))
			swfNodeDto.setLossItemName(nowNode.getLossItemName());
		if (!nowNode.getPolicyNo().equals(""))
			swfNodeDto.setPolicyNo(nowNode.getPolicyNo());
		if (!nowNode.getExigenceGree().equals(""))
			swfNodeDto.setExigenceGree(nowNode.getExigenceGree());
		return swfNodeDto;
	}

	/* ========================（工作流正向操作）结束============================ */
	/** 工作流服务 */
	private WorkFlowService workFlowService;

	public WorkFlowService getWorkFlowService() {
		if (workFlowService == null) {
			return (WorkFlowService) ServiceFactory.getService("workFlowService");
		}
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
}
