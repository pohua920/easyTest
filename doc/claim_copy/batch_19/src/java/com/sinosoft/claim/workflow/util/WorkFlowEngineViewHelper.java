package com.sinosoft.claim.workflow.util;

import ins.framework.common.DateTime;
import ins.framework.common.ServiceFactory;
import ins.framework.utils.DataUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;

import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfLogId;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * <p>
 * Title: WorkFlowEngineViewHelper
 * </p>
 * <p>
 * Description:工作流引擎ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c)
 * </p>
 * @author 中科软
 */
public class WorkFlowEngineViewHelper {
	/** 单号取号服务 */
	private BillService billService;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 默认构造方法
	 */
	public WorkFlowEngineViewHelper() {
	}

	/* ========================第一部分：工作引擎正向/逆向操作======================== */

	/**
	 * 操作工作流的数据整理,此函数就是所有工作流流转的引擎入口， 想知道工作流是如何运转的，从这个函数看起吧。
	 * @param user UserDto 用户dto
	 * @param swfLogFunctionIn 传参数
	 * @throws Exception
	 * @return WorkFlowDto 工作流程数据传输数据结构
	 */
	public WorkFlowDto viewToDto(UserDto user, SwfLog swfLogFunctionIn) throws Exception {
		// 取得当前用户信息，写操作员信息到DTO中
		WorkFlowDto workFlowDto = new WorkFlowDto();
		WorkFlowDto workFlowDtoTemp = new WorkFlowDto();
		SwfLog swfLog = null;
		List<SwfLog> swfFlowNodeList = new ArrayList<SwfLog>();
		List<SwfLog> submitLogList = new ArrayList<SwfLog>();
		List<SwfPathLog> submitPathLogList = new ArrayList<SwfPathLog>();
		String swfLogFlowID = swfLogFunctionIn.getId().getFlowID(); // 流程号码
		int swfLogLogNo = swfLogFunctionIn.getId().getLogNo(); // 流程中的节点号码
		// 一般以上两个是必须的
		String nodeStatus = swfLogFunctionIn.getNodeStatus(); // 操作是哪种
																// 2,4,5,3目前只有这几种
		String nextBusinessNo = swfLogFunctionIn.getNextBusinessNo();
		String keyIn = swfLogFunctionIn.getKeyIn();
		String keyOut = swfLogFunctionIn.getKeyOut();
		// --------------------创建工作流用的参数--------------------//
		boolean createWorkFlow = swfLogFunctionIn.getCreateFlow(); // True
																	// 创建工作流功能
		String riskCode = swfLogFunctionIn.getRiskCode();
		String comCode = swfLogFunctionIn.getComCode();
		String policyNo = swfLogFunctionIn.getPolicyNo();
		// --------------------创建工作流用的参数--------------------//
		String businessNo = swfLogFunctionIn.getBusinessNo();// 只有当flowId没有时起作用
		String nodeType = swfLogFunctionIn.getNodeType(); // 如果为T类型或者没有flowId时有用
		String conditionBusinessNo = swfLogFunctionIn.getConditionBusinessNo();
		String typeFlag = swfLogFunctionIn.getTypeFlag();
		String claimTypeFlag = swfLogFunctionIn.getClaimTypeFlag();// 简易赔案条件判断
		// 注意此处，通过判断去做简易赔案数据整理
		if ("01".equals(claimTypeFlag) || "02".equals(claimTypeFlag) || "03".equals(claimTypeFlag)) {
			return this.getWorkFlowSpecialFlowViewHelper().viewToDto(user, swfLogFunctionIn);
		}
		// 如果是利用的如上方法，则只需要查询工作流节点中的内容就可以了
		int logMaxNo = 0; // 解决取LogNo号的问题
		int pathMaxNo = 0; // 解决取pathNo号的问题
		// 1.创建工作流程/查找流程信息
		if (createWorkFlow) {
			workFlowDto = this.createFlowInfo(user, businessNo, comCode, riskCode, policyNo, swfLogFunctionIn.getInsuredName(), swfLogFunctionIn.getLossItemName());
			if (workFlowDto.getOperateResult() < 0) {
				// 没有取得模板的号码
				return workFlowDto;
			}
			// 设置当前节点的内容
			swfLog = workFlowDto.getCreateSwfLog();
			logMaxNo = 2;
			pathMaxNo = 1;
		} else { // 查询出工作流数据/查找当前节点
			if (DataUtils.emptyToNull(swfLogFlowID) != null && swfLogLogNo > 0) { // 利用主键flowId,LogNo查工作节点
				swfFlowNodeList = this.getWorkFlowService().findCurrentNode(swfLogFlowID, swfLogLogNo);
			} else {// 利用主键businessNo, nodeType查工作节点
				swfFlowNodeList = this.getWorkFlowService().findCurrentNode(businessNo, nodeType);
			}
			Iterator<?> it = swfFlowNodeList.iterator();
			if (it.hasNext()) {
				// 获得当前工作流程的信息
				swfLog = (SwfLog)it.next();
				// 並取得工作流上点和边的最大号码
				logMaxNo = this.getWorkFlowService().getSwfLogMaxLogNo(swfLog.getId().getFlowID());
				pathMaxNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(swfLog.getId().getFlowID());
			}
		}
		// 工作流判断是否可以操作
		if (swfLog != null) { // 有工作流程查询出来
			// 要判断工作流程是否结束，如果结束了，下面的都不需要操作的
			if (this.getWorkFlowService().checkFlowClose(swfLog.getId().getFlowID())) {
				// 工作流已经关闭了
				workFlowDto.setCheckClose(true);
				return workFlowDto;
			}
			// 判断是不是要创建创建子任务，以後都相同的M类型任务的处理 M创建後，和正常的流程是一样的
			if ("M".equals(swfLog.getTaskType()) && !"5".equals(nodeStatus)) {
				// 创建子任务过程
				swfLog.setBusinessNo(nextBusinessNo);
				swfLog.setKeyIn(businessNo);
				workFlowDtoTemp = this.getSubmitFlowInfo(user, swfLog, keyIn, logMaxNo, pathMaxNo);
				logMaxNo = logMaxNo + 1;
				pathMaxNo = pathMaxNo + 1;
				workFlowDto.setSubmit(true);
				workFlowDto.setSubmitSwfLogList(workFlowDtoTemp.getSubmitSwfLogList());
				workFlowDto.setSubmitSwfPathLogList(workFlowDtoTemp.getSubmitSwfPathLogList());
				// 设置创建的子任务为当前的需要处理的任务节点
				swfFlowNodeList.clear();
				swfFlowNodeList = workFlowDto.getSubmitSwfLogList();
				Iterator<?> it = swfFlowNodeList.iterator();
				if (it.hasNext()) {
					swfLog =(SwfLog) it.next();
					swfLog.setHandlerCode(user.getUserCode());
					swfLog.setHandlerName(user.getUserName());
					// M类型出来的子节点直接设置keyOut,不管是否提交
					swfLog.setKeyOut(keyOut);
				}
			}
			// 传入的参数类型为T特殊类型的节点
			if ("T".equals(swfLogFunctionIn.getTaskType())) {
				// 创建新节点
				// 为T类型的节点可以正常的走普通路径，所以做标志判断的时候该用其他内部标志，表示T类型，比如AddNewNode等同於T
				swfLog.setTaskType("AddNewNode");
				workFlowDtoTemp = this.getSubmitFlowInfo(user, swfLog, keyIn, logMaxNo, pathMaxNo);
				if (workFlowDtoTemp.getSubmitSwfLogList() == null) {// 查出後续是没有节点的。。。直接抛出
					return workFlowDto;
				}
				// 因为节点产生了，所以增加了
				logMaxNo = logMaxNo + 1;
				pathMaxNo = pathMaxNo + 1;
				workFlowDto.setSubmit(true);
				workFlowDto.setSubmitSwfLogList(workFlowDtoTemp.getSubmitSwfLogList());
				workFlowDto.setSubmitSwfPathLogList(workFlowDtoTemp.getSubmitSwfPathLogList());
				// 设置创建的子任务为当前的需要处理的任务节点
				swfFlowNodeList.clear();
				swfFlowNodeList = workFlowDto.getSubmitSwfLogList();
				Iterator<?> it = swfFlowNodeList.iterator();
				if (it.hasNext()) {
					swfLog = (SwfLog) it.next();
					swfLog.setHandlerCode(user.getUserCode());
					swfLog.setHandlerName(user.getUserName());
					swfLog.setTypeFlag(typeFlag);// 因为回勘的要求
					// T类型出来的子节点直接设置keyOut,不管是否提交
					swfLog.setKeyOut(keyOut);
				}
			}
			// 做判断提交，如果不可以提交，那就改为保存操作
			// 2.修改工作流0/1/2/3都是修改工作流
			if ("0".equals(nodeStatus) || "1".equals(nodeStatus) || "2".equals(nodeStatus) || "3".equals(nodeStatus)) {
				// 修改工作流
				// 由於团意险在报案环节可以修改被保险人，所以这里加上被保险人，不加的话在修改被保险人时，工作流表中的被保险人不变
				if ("regis".equals(nodeType)) {
					swfLog.setInsuredName(swfLogFunctionIn.getInsuredName());
				}
				swfLog.setNodeStatus(nodeStatus);
				swfLog.setHandlerCode(user.getUserCode());
				swfLog.setHandlerName(user.getUserName());
				swfLog.setKeyOut(keyOut);
				// 日期改成时分秒後，存入数据也是YEAR_TO_SECOND
				swfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				if (typeFlag != null && typeFlag.length() > 0) {
					swfLog.setTypeFlag(typeFlag);
				}
				// 如果是M类型的接点，则不需要处理update情况//优化工作流
				if (workFlowDto.getSubmit()) {
					List<SwfLog> submitLogMList = new ArrayList<SwfLog>();
					submitLogMList.add(swfLog);
					workFlowDto.setSubmitSwfLogList(submitLogMList);
				} else {
					// 判断当前信息中是否有工作流的存在。。
					if (swfLog.getId().getLogNo() > 0) {
						workFlowDto.setUpdate(true);
						workFlowDto.setUpdateSwfLog(swfLog);
					}
				}
			}
			// 3。提交工作流
			if ("4".equals(nodeStatus)) {
				// 判断是否需要人为的结束,人为结束只要设置传入的参数为endFlag=1就行了。
				if ("1".equals(swfLogFunctionIn.getEndFlag())) {
					swfLog.setEndFlag("1");
				}
				// 判断是否允许节点能提交？？这个在页面上已经进行判断了
				// 由於团意险在报案环节可以修改被保险人，所以这里加上被保险人，不加的话在修改被保险人时，工作流表中的被保险人不变
				if ("regis".equals(nodeType)) {
					swfLog.setInsuredName(swfLogFunctionIn.getInsuredName());
				}
				// 修改工作流nodeStatus=4
				swfLog.setNodeStatus(nodeStatus);
				swfLog.setKeyOut(keyOut); //
				// 关联保单定损只有一个，定损时只存商业保单，当：
				// 从定损处入口申请垫支付时，保单号需要取对应於强制立案号的强制保单，所以不能用定损时的保单。
				if (DataUtils.emptyToNull(swfLogFunctionIn.getPolicyNo()) != null) {
					swfLog.setPolicyNo(swfLogFunctionIn.getPolicyNo());
				}
				// 设置提交的时间
				// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
				swfLog.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				// 还需要设置条件
				swfLog.setConditionBusinessNo(conditionBusinessNo);
				// 设置是否有typeFlag
				if ((typeFlag != null) && (typeFlag.length() > 0)) {
					swfLog.setTypeFlag(typeFlag);
				}
				// 正是做提交的操作
				workFlowDtoTemp = this.submitWorkFlow(user, swfLog, swfLogFunctionIn, logMaxNo, pathMaxNo);
				workFlowDto.setUpdate(workFlowDtoTemp.getUpdate());
				workFlowDto.setUpdateSwfLog(workFlowDtoTemp.getUpdateSwfLog());
				workFlowDto.setUpdateSwfLog2(workFlowDtoTemp.getUpdateSwfLog2());
				submitLogList = workFlowDtoTemp.getSubmitSwfLogList();
				if (submitLogList == null) {
					submitLogList = new ArrayList<SwfLog>(); // 考虑到有时候提交後面是空的,允许的
				}
				submitPathLogList = workFlowDtoTemp.getSubmitSwfPathLogList();
				if (submitPathLogList == null) {
					submitPathLogList = new ArrayList<SwfPathLog>(); // 考虑到有时候提交後面是空的,允许的
				}
				// 原来有新增加的数据，需要合並的节点和路径的这里是优化，前面进行M和T操作形成的新节点。
				if (workFlowDto.getSubmit()) {
					Iterator<SwfLog> it = workFlowDto.getSubmitSwfLogList().iterator();
					while (it.hasNext()) {
						submitLogList.add(it.next());
					}
					Iterator<SwfPathLog> it1 = workFlowDto.getSubmitSwfPathLogList().iterator();
					while (it1.hasNext()) {
						submitPathLogList.add(it1.next());
					}
					// 如果已经有提交的数据了
				}
				workFlowDto.setSubmit(workFlowDtoTemp.getSubmit());
				workFlowDto.setSubmitSwfLogList(submitLogList);
				workFlowDto.setSubmitSwfPathLogList(submitPathLogList);
				workFlowDto.setClose(workFlowDtoTemp.getClose());
				workFlowDto.setCloseSwfFlowMain(workFlowDtoTemp.getCloseSwfFlowMain());
				workFlowDto.setStatus(workFlowDtoTemp.getStatus());
			} // 判断是提交操作的结束
				// 优化工作流
			if (workFlowDto.getCreate() && workFlowDto.getUpdate()) {
				// 新创建的工作流直接创建，就不用在update一把了
				workFlowDto.setCreateSwfLog(workFlowDto.getUpdateSwfLog());
				workFlowDto.setUpdate(false);
			}
			// 优化工作流，如果报案的时候同时进行了关闭操作，则关闭的主表当时一定是空的，所以
			if (workFlowDto.getCreate() && workFlowDto.getClose()) {
				// 新创建的工作流直接创建，关闭也要进行修整的
				SwfFlowMain swfFlowMain = workFlowDto.getCreateSwfFlowMain();
				swfFlowMain.setFlowStatus("0");
				workFlowDto.setCloseSwfFlowMain(swfFlowMain);
			}
			// 4。回退工作流
			if ("5".equals(nodeStatus)) {
				// 目前只有核损、定损才用得到，无条件到达定损，並且人员是上个节点上的人员
				// 首先查找定损的节点，从回退的节点上查询属性为定损的节点，处理类型为typeFlag相同的节点
				// 由後向前进行查询。（目前只有核损、核价和人伤核损三个）
				// 新增一条判断swfLogDto.getNodeType().equals("certi")可以从理算退回到单证
				String tempNodeType = swfLog.getNodeType();
				if ("verip".equals(tempNodeType) || "verif".equals(tempNodeType) || "veriw".equals(tempNodeType) || "propv".equals(tempNodeType) || "compe".equals(tempNodeType) || "certi".equals(tempNodeType)) {
					// 核损的退回,理算的退回
					workFlowDto = this.backWorkFlow(user, swfLog.getId().getFlowID(), swfLog.getId().getLogNo(), swfLogFunctionIn);
				} else {
					throw new UserException(1, 3, "工作流", "沒有發現可以回退的工作流節點");
				}
			}
		}
		return workFlowDto;
	}

	/**
	 * 创建一个新的工作流流程
	 * @param user UserDto 登录用户信息
	 * @param businessNo String 业务号码，主要是如果是报案节点开始的，那么是报案号码
	 * @param comCode String 当前的归属机构的代码
	 * @param riskCode String 当前的险种
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	protected WorkFlowDto createFlowInfo(UserDto user, String businessNo, String comCode, String riskCode, String policyNo, String insuredName, String lossItemName) throws Exception {
		// 1。 取得当前用户信息，写操作员信息到DTO中
		WorkFlowDto workFlowDto = new WorkFlowDto();
		int year = DateTime.current().getYear();
		String strTitle = "創建工作流程";
		String tableName = "swfflowmain";
		// 默认创建的险种是车险类DAA的
		if (DataUtils.emptyToNull(riskCode) == null) {
			riskCode = this.getCodeService().translateProductCode("RISKCODE_DAA");
		}
		/*---2.取工作流的流号-*/
		String flowID = this.getBillService().getNo(tableName, riskCode, comCode, year);
		/*---3.取工作流的模板号,从swfModelUse中取得相映的模板设置的号码*/
		int modelNo = this.getWorkFlowService().getModelNo(riskCode, comCode);
		// 如果没有取得工作流号码，那么则返回一个空的数据集合，通知调用该函数的程序
		// 查找不到分配的模板时候，立刻提示出错误，要不然会有问题
		if (modelNo < 0) {
			throw new UserException(1, 3, "工作流", "沒有發現險種爲'" + riskCode + "'，機構爲'" + comCode + "'所配置的理賠模板，請和管理員聯系！");
		}
		/*
		 * 4.写---------------------工作流主表wfFlowMain--------------------------------
		 * ----
		 */
		SwfFlowMain swfFlowMainDto = new SwfFlowMain();
		swfFlowMainDto.setFlowID(flowID);
		swfFlowMainDto.setFlowName(businessNo);
		swfFlowMainDto.setFlowStatus("1");
		swfFlowMainDto.setPolicyNo(policyNo);
		swfFlowMainDto.setCreatDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
		swfFlowMainDto.setModelNo(modelNo);
		swfFlowMainDto.setFlag("");

		workFlowDto.setCreateSwfFlowMain(swfFlowMainDto);
		/*
		 * 5.---------------------查询节点表wfNode的定义----------------------------------
		 * --
		 */
		// 假设所有的模板第一个节点的号码就是1
		int nodeNo = 1;
		SwfNode swfNode = this.getWorkFlowService().findModelNodeByPrimaryKey(modelNo, nodeNo);
		/*
		 * 6.---------------------写流程节点表wfLog------------------------------------
		 */
		SwfLog swfLog = new SwfLog();
		swfLog.getId().setFlowID(flowID);
		swfLog.getId().setLogNo(1);
		swfLog.setModelNo(modelNo);
		swfLog.setNodeNo(nodeNo);
		swfLog.setNodeName(swfNode.getNodeName());
		swfLog.setBusinessNo(businessNo);
		swfLog.setHandleDept(user.getComCode());
		swfLog.setHandlerCode(user.getUserCode());
		swfLog.setHandlerName(user.getUserName());
		// 待处理的查询条件，增加报案号，被保险人，车牌号(工作流需要添加)
		swfLog.setInsuredName(insuredName);
		swfLog.setLossItemName(lossItemName);
		swfLog.setRegistNo(businessNo);
		swfLog.setTimeLimit(swfNode.getTimeLimit());
		swfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		swfLog.setNodeStatus("1");
		swfLog.setFlowStatus("1");
		swfLog.setPackageID("0");
		swfLog.setFlag(swfNode.getFlag());
		swfLog.setTaskNo(swfNode.getTaskNo());
		swfLog.setTaskType(swfNode.getTaskType());
		swfLog.setNodeType(swfNode.getNodeType());
		swfLog.setTitleStr(strTitle);
		swfLog.setRiskCode(riskCode);
		swfLog.setKeyIn(businessNo);
		swfLog.setKeyOut("");
		swfLog.setDeptName(user.getComName());
		swfLog.setSubFlowID("0");
		swfLog.setMainFlowID("0");
		swfLog.setPosX(0);
		swfLog.setPosY(0);
		swfLog.setEndFlag(swfNode.getEndFlag());
		swfLog.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		swfLog.setPolicyNo(policyNo);
		swfLog.setComCode(comCode);

		workFlowDto.setCreateSwfLog(swfLog);
		workFlowDto.setCreate(true);
		// 创建工作流的过程 不操作数据，只写数据到workFlowDto中
		return workFlowDto;
	}

	/**
	 * 提交工作流
	 * @param httpServletRequest HttpServletRequest
	 * @param SwfLog 当前任务节点
	 * @param KeyIN 输入的业务号码
	 * @param KeyOut 输出的业务号码
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	protected WorkFlowDto getSubmitFlowInfo(UserDto user, SwfLog swfLogFunctionIn, String KeyIn, int logNo, int pathNo) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		// 1。 取得当前用户信息，写操作员信息到DTO中
		WorkFlowDto workFlowDto = new WorkFlowDto();
		// 2.取工作流号码
		String flowID = swfLogFunctionIn.getId().getFlowID();
		/*---3.取工作流的模板号*/
		int modelNo = swfLogFunctionIn.getModelNo();
		int nodeNo = swfLogFunctionIn.getNodeNo();
		// 程序上认为回归的时候程序置为标志B,但是判断後，直接把值恢复成"S"
		if ("B".equals(swfLogFunctionIn.getTaskType())) {
			nodeNo = swfLogFunctionIn.getTaskNo();
			swfLogFunctionIn.setTaskType("S");
		}
		/*
		 * 4.---------------------查询节点表wfNode的定义----------------------------------
		 * --
		 */
		List<SwfNode> wfNodeList = new ArrayList<SwfNode>(); // 模板的节点
		List<SwfLog> wfLogList = new ArrayList<SwfLog>(); // 整理後需要新插入的工作流程节点
		List<SwfPathLog> wfPathLogList = new ArrayList<SwfPathLog>();// 整理後需要新插入的工作流程边

		// 如果是多任务的节点，只查关系，不查後面的线
		if ("M".equals(swfLogFunctionIn.getTaskType())) {
			int nextNodeNo = swfLogFunctionIn.getTaskNo();
			SwfNode wfNode = this.getWorkFlowService().findModelNodeByPrimaryKey(modelNo, nextNodeNo);
			if (wfNode != null) {
				wfNodeList.add(wfNode);
			}
			// 如果M类型派生出的节点继续提交了呢？？这怎办呢？
		} else if ("AddNewNode".equals(swfLogFunctionIn.getTaskType())) {// AddNewNode=T,C是T类型节点的特殊内部表示
			wfNodeList = this.getWorkFlowService().findModelNextTNodes(modelNo, nodeNo);
		} else {
			// 查询此节点後的所有的节点信息（正常节点taskType =S 单任务节点）
			// 目前没有确定执行边条件的业务号是什么？？？swfLogFunctionInDto.getConditionBusinessNo(),从节点上传过来
			wfNodeList = this.getWorkFlowService().findModelNextNodes(modelNo, nodeNo, swfLogFunctionIn.getConditionBusinessNo());
		}
		/* 5.-----------------根据下个工作流节点写工作流程的点和线的数据---------------- */
		if (wfNodeList.iterator().hasNext()) {
			Iterator<SwfNode> it = wfNodeList.iterator();
			// 获取wfLog表中flowID的最大的logNo的开始值
			while (it.hasNext()) {
				SwfNode wfNode = new SwfNode();
				PropertyUtils.copyProperties(wfNode, it.next());
				// 判断有没有传入的指定的typeFlag,需要入swfLog的。
				if (DataUtils.emptyToNull(swfLogFunctionIn.getTypeFlag())!=null)
					wfNode.setTypeFlag(swfLogFunctionIn.getTypeFlag());

				/*
				 * 6.---------------------写流程节点表wfLog----------------------------
				 * --------
				 */
				SwfLog wfLogTemp = new SwfLog();
				// 设置wfLogTemp
				// 判断是否在当前的流程中，已经有被定义成该节点的存在，並且状态为0,没有处理，（特殊节点，如果是核损，实赔呢？因为允许多次提交）
				// 如果有，就不用再插入数据库了
				List<SwfLog> wfLogHasSaveList = new ArrayList<SwfLog>();
				// 回访不需要检查是不是有重复的节点
				String tempNodeType = wfNode.getNodeType();
				if (!"backv".equals(tempNodeType) && !"compp".equals(tempNodeType) && !"veric".equals(tempNodeType) && !"verif".equals(tempNodeType)) {
					wfLogHasSaveList = this.getWorkFlowService().findNoDealNodeByModelNodeNo(flowID, wfNode.getId().getNodeNo(), tempNodeType);
				}
				// 由於强三的加入，需要考虑一下，如果结案是按照险种来结案的，那么需要如何做？
				// 这里初步考虑用riskCode来区分结案，然後用相同的结案来处理
				if ("endca".equals(tempNodeType)) {
					List<SwfLog> wfLogHasSaveListTemp = wfLogHasSaveList;
					wfLogHasSaveList = new ArrayList<SwfLog>();
					// 只要判断已经产生的结案中是有 riskCode=本身的riskCode的数据，则可以产生新的结案
					for (int i = 0; i < wfLogHasSaveListTemp.size(); i++) {
						String riskCode = wfLogHasSaveListTemp.get(i).getRiskCode();
						if (swfLogFunctionIn.getRiskCode().equals(riskCode)) {
							wfLogHasSaveList.add(wfLogHasSaveListTemp.get(i));
							break;
						}
					}
				}
				Iterator<?> it1 = wfLogHasSaveList.iterator();
				if (it1.hasNext()) {
					// 解决多点聚合的时候，产生了大量的多余节点的问题
					PropertyUtils.copyProperties(wfLogTemp, it1.next());
					wfLogTemp.setId(new SwfLogId(wfLogTemp.getId().getFlowID(),wfLogTemp.getId().getLogNo()));
					// 如果存在这样的数据，则不需要插入wfLog，只要在wfPathLog中增加相应的边就可以了
					/*
					 * 7.---------------------写流程线表wfPathLog----------------------
					 * -------
					 */
				} else {
					wfNode.setInsureCarFlag(swfLogFunctionIn.getInsureCarFlag());
					wfNode.setExigenceGree(swfLogFunctionIn.getExigenceGree());
					this.getSwfLogDtoInfoBySwfNode(wfLogTemp, wfNode, user, flowID, modelNo, logNo, "0", KeyIn, swfLogFunctionIn);
					// 设置默认值
					wfLogTemp.setLossItemCode(swfLogFunctionIn.getLossItemCode());
					wfLogTemp.setLossItemName(swfLogFunctionIn.getLossItemName());
					wfLogTemp.setTypeFlag(swfLogFunctionIn.getTypeFlag());
					// 节点上的附加信息 当前预设置,以後也不做保留的，只用来对下个节点起作用的
					// 默认为本节点数据的附加内容，但是如果外部设置了新的值，则以新的为准
					wfLogList.add(wfLogTemp);
					logNo++;
				}
				/*
				 * 7.---------------------写流程线表wfPathLog--------------------------
				 * -------
				 */
				SwfPathLog swfPathLog = new SwfPathLog();
				this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLogFunctionIn, wfLogTemp, flowID, modelNo, pathNo);
				wfPathLogList.add(swfPathLog);
				pathNo++;
			}
			workFlowDto.setSubmitSwfLogList(wfLogList);
			workFlowDto.setSubmitSwfPathLogList(wfPathLogList);
			workFlowDto.setSubmit(true);
		} else {
			// 如果是单任务节点，但是後面已经没有节点了，查询是不是M的後续节点，如果是（条件是taskNo>0），
			if ("S".equals(swfLogFunctionIn.getTaskType()) && swfLogFunctionIn.getTaskNo() > 1) {
				// 回归主线，为了防止重复递归，设置条件为只可以套一次,並且关系只保留一次,其实不用修改数据中的内容
				// 给再次递归调用本身的时候加个标志位，在次判断的时候，会给置回S
				swfLogFunctionIn.setTaskType("B");
				swfLogFunctionIn.setNextBusinessNo(swfLogFunctionIn.getNextBusinessNo());
				swfLogFunctionIn.setKeyOut(swfLogFunctionIn.getKeyOut());
				swfLogFunctionIn.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				// 回归主线的bussnessNo
				workFlowDto = this.getSubmitFlowInfo(user, swfLogFunctionIn, KeyIn, logNo, pathNo);
			} else {
				// 如果以上情况都不是那么。。。 没有正常结束，但是也没有任何下一个节点的记录，需要提示操作员，並且无法进行下去
				workFlowDto.setStatus("9"); // 设置异常
			}
		}
		return workFlowDto;
	}

	/**
	 * 提交工作流(人到人的)，
	 * @param user UserDto
	 * @param swfLogFunctionIn SwfLog //本节点的信息
	 * @param swfLogFunctionInOld SwfLog//函数传如的参数
	 * @param logNo int
	 * @param pathNo int
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	protected WorkFlowDto getSubmitFlowInfo(UserDto user, SwfLog swfLogFunctionIn, SwfLog swfLogFunctionInOld, int logNo, int pathNo) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		// 1。 取得当前用户信息，写操作员信息到DTO中
		WorkFlowDto workFlowDto = new WorkFlowDto();
		// 2.取工作流号码
		String flowID = swfLogFunctionIn.getId().getFlowID();
		/*---3.取工作流的模板号*/
		int modelNo = swfLogFunctionIn.getModelNo();
		/*
		 * 4.---------------------查询节点表wfNode的定义----------------------------------
		 * --
		 */
		List<SwfNode> wfNodeList = new ArrayList<SwfNode>(); // 模板的节点
		List<SwfLog> wfLogList = new ArrayList<SwfLog>(); // 整理後需要新插入的工作流程节点
		List<SwfPathLog> wfPathLogList = new ArrayList<SwfPathLog>();// 整理後需要新插入的工作流程边
		List<SwfLog> swfNodeNextList = swfLogFunctionInOld.getSwfLogList();// 存放从界面传过来的需要送的多个节点
		// 查询节点的详细定义 下一个节点定义的内容
		Iterator<SwfLog> itn = swfNodeNextList.iterator();
		int nextNodeNo = 0;
		String nodeType = "";
		while (itn.hasNext()) {
			// swfLogNext 下一个节点从界面传进的定义
			SwfLog swfLogNext = itn.next();
			nextNodeNo = swfLogNext.getNodeNo();
			nodeType = swfLogNext.getNodeType();
			// 异常判断，如果nextNodeNo=0 说明没找到点,那目前只能是不操作这个下个节点了。以後讨论
			// 目前允许利用nodeType进行传递内容的
			// 在facade只传节点类型进入工作流中就可以的。
			SwfNode tempSwfNode = null;
			if ((nextNodeNo == 0) && (!nodeType.equals(""))) {
				tempSwfNode = this.getFirstNodeTypeNode(modelNo, nodeType);
			} else {
				if (nextNodeNo == 0) {
					continue;
				}
				tempSwfNode = this.getWorkFlowService().findModelNodeByPrimaryKey(modelNo, nextNodeNo);
			}
			if (tempSwfNode != null) {// 可以正确查询到下一个节点上的信息.
				SwfNode swfNode = new SwfNode();
				PropertyUtils.copyProperties(swfNode, tempSwfNode);//避免hibernate session缓存导致的swfNode被modelNo\NodeNo相同的覆盖
				if (DataUtils.emptyToNull(swfLogNext.getHandlerCode())!=null) {
					swfNode.setHandlerCode(swfLogNext.getHandlerCode());
					swfNode.setHandlerName(swfLogNext.getHandlerName());
				}
				// 节点上的附加信息 当前预设置,以後也不做保留的，只用来对下个节点起作用的
				// 默认为本节点数据的附加内容，但是如果外部设置了新的值，则以新的为准
				swfNode.setScheduleID(swfLogFunctionIn.getScheduleID());
				swfNode.setLossItemCode(swfLogFunctionIn.getLossItemCode());
				swfNode.setLossItemName(swfLogFunctionIn.getLossItemName());
				swfNode.setInsureCarFlag(swfLogFunctionIn.getInsureCarFlag());
				swfNode.setTypeFlag(swfLogFunctionIn.getTypeFlag());
				swfNode.setHandlerRange(swfLogFunctionIn.getHandlerRange());
				swfNode.setExigenceGree(swfLogFunctionIn.getExigenceGree());
				swfNode.setHandleDept(swfLogFunctionIn.getNewNewHandleDept());
				swfNode.setDeptName(swfLogFunctionIn.getNewNewDeptName());

				if (swfLogNext.getScheduleID() > 0)
					swfNode.setScheduleID(swfLogNext.getScheduleID());
				if (DataUtils.emptyToNull(swfLogNext.getLossItemCode()) != null)
					swfNode.setLossItemCode(swfLogNext.getLossItemCode());
				if (DataUtils.emptyToNull(swfLogNext.getLossItemName()) != null)
					swfNode.setLossItemName(swfLogNext.getLossItemName());
				if (DataUtils.emptyToNull(swfLogNext.getInsureCarFlag()) != null)
					swfNode.setInsureCarFlag(swfLogNext.getInsureCarFlag());
				if (DataUtils.emptyToNull(swfLogNext.getTypeFlag()) != null)
					swfNode.setTypeFlag(swfLogNext.getTypeFlag());
				if (DataUtils.emptyToNull(swfLogNext.getHandlerRange()) != null)
					swfNode.setHandlerRange(swfLogNext.getHandlerRange());
				if (DataUtils.emptyToNull(swfLogNext.getExigenceGree()) != null)
					swfNode.setExigenceGree(swfLogNext.getExigenceGree());
				if (DataUtils.emptyToNull(swfLogNext.getNewNewHandleDept()) != null)
					swfNode.setHandleDept(swfLogNext.getNewNewHandleDept());
				if (DataUtils.emptyToNull(swfLogNext.getNewNewDeptName()) != null)
					swfNode.setDeptName(swfLogNext.getNewNewDeptName());
				// 由於强三必须支持立案是按照不同的保单来的
				if ("claim".equals(swfNode.getNodeType()) || "compe".equals(swfNode.getNodeType()) || "cance".equals(swfNode.getNodeType())) {
					if (DataUtils.emptyToNull(swfLogNext.getPolicyNo()) != null)
						swfNode.setPolicyNo(swfLogNext.getPolicyNo());
					if (DataUtils.emptyToNull(swfLogNext.getRiskCode()) != null)
						swfNode.setRiskCode(swfLogNext.getRiskCode());
					if (DataUtils.emptyToNull(swfLogNext.getKeyIn()) != null)
						swfNode.setKeyIn(swfLogNext.getKeyIn());
					if (DataUtils.emptyToNull(swfLogNext.getBusinessNo()) != null)
						swfNode.setBusinessNo(swfLogNext.getBusinessNo());
				}
				wfNodeList.add(swfNode);
			}
		}
		/* 5.-----------------根据下个工作流节点写工作流程的点和线的数据---------------- */
		if (wfNodeList.iterator().hasNext()) {
			Iterator<SwfNode> it = wfNodeList.iterator();
			while (it.hasNext()) {
				SwfNode wfNodeDto = it.next();
				/*
				 * 6.---------------------写流程节点表wfLog----------------------------
				 * --------
				 */
				SwfLog wfLogTemp = new SwfLog();
				// 判断是否在当前的流程中，已经有被定义成该节点的存在，並且状态为0,没有处理，（特殊节点，如果是核损，实赔呢？因为允许多次提交）
				// 如果有，就不用再插入数据库了
				List<SwfLog> wfLogHasSaveList = new ArrayList<SwfLog>();
				// 单证节点不应该有大量的多余节点，是要聚合的。。後来的定损调度也需要合並的
				if ("certi".equals(wfNodeDto.getNodeType()) || "compe".equals(wfNodeDto.getNodeType())) {
					// 由於强三的加入，是否可以考虑，一个保单上的节点是不能重复的。。比如单证，但是理算因为保单不同，允许一个保单一个。
					wfLogHasSaveList = this.getWorkFlowService().findNoDealNodeByModelNodeNoByPerson(flowID, wfNodeDto.getId().getNodeNo(), wfNodeDto.getNodeType(), wfNodeDto.getPolicyNo());
				}
				Iterator<?> it1 = wfLogHasSaveList.iterator();
				if (it1.hasNext()) {
					// 解决多点聚合的时候，产生了大量的多余节点的问题
					// 从数据库中查询到已经存在的节点，並把它设置成要去的节点
					wfLogTemp = (SwfLog) it1.next();
					// 如果存在这样的数据，则不需要插入wfLog，只要在wfPathLog中增加相应的边就可以了
					/*
					 * 7.---------------------写流程线表wfPathLog----------------------
					 * -------
					 */
				} else {
					// 如果当前节点为调度，设置当前节点的附加信息
					// 初始化下一个节点的各项信息
					this.getSwfLogDtoInfoBySwfNode(wfLogTemp, wfNodeDto, user, flowID, modelNo, logNo, "0", swfLogFunctionInOld.getKeyIn(), swfLogFunctionIn);
					// 删除sched节点上的附加信息
					wfLogList.add(wfLogTemp);
					logNo++;
				}
				/*
				 * 7.---------------------写流程线表wfPathLog--------------------------
				 * -------
				 */
				SwfPathLog swfPathLog = new SwfPathLog();
				this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLogFunctionIn, wfLogTemp, flowID, modelNo, pathNo);
				wfPathLogList.add(swfPathLog);
				pathNo++;
			}
			workFlowDto.setSubmitSwfLogList(wfLogList);
			workFlowDto.setSubmitSwfPathLogList(wfPathLogList);
			workFlowDto.setSubmit(true);
		} else {
			workFlowDto.setStatus("9"); // 设置异常
		}
		return workFlowDto;
	}

	/**
	 * 根据工作流模板上的相同节点定义，写wfLog表，工作流程节点的操作处理
	 * @param SwfLog SwfLog
	 * @param SwfNode SwfNode
	 * @param user UserDto
	 * @param flowID String
	 * @param modelNo int
	 * @param logNo int
	 * @param nodeStatus String
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
		swfLog.setBusinessNo(swfLogFunctionIn.getBusinessNo());
		/*--1。如果立案的插入操作，则需要读的是swfLogFunctionIn.的NextBusinessNo*/
		if ("endca".equals(swfNode.getNodeType())) {
			swfLog.setBusinessNo(swfLogFunctionIn.getNextBusinessNo());
		}
		swfLog.setBeforeHandlerCode(user.getUserCode());
		swfLog.setBeforeHandlerName(user.getUserName());
		swfLog.setTimeLimit(swfNode.getTimeLimit());
		// 日期改成时分秒後，存入数据也是YEAR_TO_SECOND
		swfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		swfLog.setNodeStatus(nodeStatus);
		swfLog.setFlowStatus("1");
		swfLog.setPackageID("0");
		swfLog.setFlag(swfNode.getFlag());
		swfLog.setTaskNo(swfNode.getTaskNo());
		swfLog.setTaskType(swfNode.getTaskType());
		swfLog.setNodeType(swfNode.getNodeType());
		// 待处理的查询条件，增加报案号，被保险人，车牌号(工作流需要添加)
		swfLog.setRegistNo(swfLogFunctionIn.getRegistNo());
		swfLog.setInsuredName(swfLogFunctionIn.getInsuredName());
		swfLog.setLossItemName(swfLogFunctionIn.getLossItemName());

		swfLog.setRiskCode(swfLogFunctionIn.getRiskCode());
		swfLog.setKeyIn(keyIn);
		/*--等於单证,定损，核损的时候，keyIn是regisNo,其它情况下，等於keyIn的值，这个是因为没办法解决keyIn不同的，並行环境。*/
		String nodeType = swfLog.getNodeType();
		if ("certi".equals(nodeType) || "certa".equals(nodeType) || "verip".equals(nodeType) || "verpo".equals(nodeType) || "verif".equals(nodeType)) {
			swfLog.setKeyIn(swfLogFunctionIn.getKeyIn());
		}
		/*--等於实赔的情况下，就是*/
		if ("compe".equals(nodeType)) {
			swfLog.setKeyIn(swfLog.getBusinessNo());
		}
		/*-如果节点是核损节点，那么要将定损的标志位传下去--*/
		swfLog.setTypeFlag(swfNode.getTypeFlag());
		swfLog.setKeyOut("");
		swfLog.setSubFlowID("0");
		swfLog.setMainFlowID("0");
		swfLog.setPosX(0);
		swfLog.setPosY(0);
		swfLog.setEndFlag(swfNode.getEndFlag());
		// 设置流入时间 日期改成时分秒後，存入数据也是YEAR_TO_SECOND
		swfLog.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		// 设置节点的名称
		String titleAttr = swfNode.getNodeName() + "節點流入時間：" + CommonUtils.getMGDateStr(format.parse(swfLog.getFlowInTime()),format) + " 上一節點操作人:" + user.getUserName();
		swfLog.setTitleStr(titleAttr);
		// 设置保单号码
		swfLog.setPolicyNo(swfLogFunctionIn.getPolicyNo());
		// 设置默认节点上的人员
		swfLog.setHandlerCode(swfNode.getHandlerCode());
		swfLog.setHandlerName(swfNode.getHandlerName());
		swfLog.setComCode(swfLogFunctionIn.getComCode());
		// 设置附加数据
		swfLog.setScheduleID(swfNode.getScheduleID());
		swfLog.setLossItemCode(swfNode.getLossItemCode());
		swfLog.setLossItemName(swfNode.getLossItemName());
		swfLog.setInsureCarFlag(swfNode.getInsureCarFlag());
		swfLog.setHandlerRange(swfNode.getHandlerRange());
		swfLog.setExigenceGree(swfNode.getExigenceGree());
		swfLog.setHandleDept(swfNode.getHandleDept());
		swfLog.setDeptName(swfNode.getDeptName());
		if (DataUtils.emptyToNull(swfLog.getHandleDept()) == null)
			swfLog.setHandleDept(swfLogFunctionIn.getNewNewHandleDept());
		if (DataUtils.emptyToNull(swfLog.getHandleDept()) == null)
			swfLog.setHandleDept(user.getComCode());
		if (DataUtils.emptyToNull(swfLog.getDeptName()) == null)
			swfLog.setDeptName(swfLogFunctionIn.getNewNewDeptName());
		if (DataUtils.emptyToNull(swfLog.getDeptName()) == null)
			swfLog.setDeptName(user.getComName());
		// 增加立案中数据的支持
		if ("claim".equals(nodeType) || "compe".equals(nodeType) || "cance".equals(nodeType)) {
			if (DataUtils.emptyToNull(swfNode.getRiskCode()) != null)
				swfLog.setRiskCode(swfNode.getRiskCode());
			if (DataUtils.emptyToNull(swfNode.getPolicyNo()) != null)
				swfLog.setPolicyNo(swfNode.getPolicyNo());
			if (DataUtils.emptyToNull(swfNode.getKeyIn()) != null)
				swfLog.setKeyIn(swfNode.getKeyIn());
			if (DataUtils.emptyToNull(swfNode.getBusinessNo()) != null)
				swfLog.setBusinessNo(swfNode.getBusinessNo());
		}
		return swfLog;
	}

	/**
	 * 写wfPathlog表，工作流程线的操作处理
	 * @param swfPathLog SwfPathLog 新工作流流程路径节点
	 * @param swfLogCurr SwfLog 当前节点
	 * @param swfLogNext SwfLog 下一个节点
	 * @param flowID String 工作流流号
	 * @param modelNo int 工作模板号
	 * @param pathNo int 工作流程路径号
	 * @throws Exception
	 * @return SwfPathLogDto
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
		// 变更当前节点的状态
		// 暂时保留当前工作节点的信息,作为工作流的提交保存的当前节点内容
		SwfLog swfLogTemp = new SwfLog();
		swfLogTemp = swfLog;
		// 如果有结束标志的点，无论後面有任何的设置，都要立即结束，不用判断下面的节点和关系等等
		if ("1".equals(swfLog.getEndFlag())) {
			// 结束工作流
			// 首先结束该节点前的失陪节点 ,为了稳妥，先查询此流程的taskType='M'类型的结束吧
			workFlowDto.setUpdate(true);
			swfLogTemp.setHandlerCode(user.getUserCode());
			swfLogTemp.setHandlerName(user.getUserName());
			workFlowDto.setUpdateSwfLog(swfLogTemp);
			// 由於结案是可能有多个结案的，所以当判断还存在没有关闭的结案，则流程不结束。
			// 等待所有的结案都完毕时，流程才结束。
			if ("endca".equals(swfLogTemp.getNodeType())) {
				// 2。查找是否只有一个活动的理算，如果理算超过1个，则不能关闭流程
				String conditonss = "flowId='" + swfLogTemp.getId().getFlowID() + "' and nodeType='compe' and nodestatus<4";
				List<SwfLog> compeList = this.getWorkFlowService().findNodesByConditions(conditonss);
				// 假设发现还没有结完案子的流程，则不将流程结束，
				// 只是关闭掉目前立案的相关的那个理算，直接返回现有的workFlowDto.
				if (compeList != null && compeList.size() > 1) {
					for (int i = 0; i < compeList.size(); i++) {
						SwfLog swfLogCompe = compeList.get(i);
						if (swfLogCompe.getKeyIn().equals(swfLogTemp.getKeyIn())) {
							swfLogCompe.setNodeStatus("4");// 关闭理算
							workFlowDto.setUpdateSwfLog2(swfLogCompe);
							break;
						}
					}
					return workFlowDto;
				}
			}
			workFlowDto.setClose(true);
			SwfFlowMain swfFlowMainDto = new SwfFlowMain();
			// 如果是第一个节点，现在还没有工作流主表内容呢，所以不需要查询的。
			swfFlowMainDto = this.getWorkFlowService().findFlowMainByPrimaryKey(swfLog.getId().getFlowID().trim());
			if (swfFlowMainDto != null) {
				swfFlowMainDto.setCloseDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
				swfFlowMainDto.setFlowStatus("0");
			}
			workFlowDto.setCloseSwfFlowMain(swfFlowMainDto);
			return workFlowDto;
		}
		// 一般情况下，NextBusinessNo和BusinessNo都是相同的
		// 除了赔款计算书的节点
		if (!"compp".equals(swfLog.getNodeType())) {
			swfLog.setBusinessNo(swfLogFunctionIn.getNextBusinessNo());
		}
		swfLog.setNextBusinessNo(swfLogFunctionIn.getNextBusinessNo());
		// 提交工作流
		// 由於有些节点的keyIn keyOut不是上个节点的流入，所以只得单独加keyIn
		// 判断是不是人到人的提交,根据设置的标志nextNodeListType
		if ("1".equals(swfLogFunctionIn.getNextNodeListType())) {
			// 支持指定的
			workFlowDtoTemp = this.getSubmitFlowInfo(user, swfLog, swfLogFunctionIn, logNo, pathNo);
		} else { // 支持从模板上取得
			swfLog.setNewNewHandleDept(swfLogFunctionIn.getNewNewHandleDept());
			workFlowDtoTemp = this.getSubmitFlowInfo(user, swfLog, swfLogFunctionIn.getKeyIn(), logNo, pathNo);
		}

		// 设置处理人员
		swfLogTemp.setHandlerCode(user.getUserCode());
		swfLogTemp.setHandlerName(user.getUserName());
		// 如果有定损的话，设置定损类型
		if (swfLogFunctionIn.getTypeFlag() != null && swfLogFunctionIn.getTypeFlag().length() > 0) {
			swfLogTemp.setTypeFlag(swfLogFunctionIn.getTypeFlag());
		}
		if (workFlowDto.equals("9")) {// ??坑
			// 设置工作流的这个节点为异常
			swfLogTemp.setNodeStatus("9");
		} else {
			// 正常流转，没有任务异常
			workFlowDto.setUpdate(true);
			// 核损，实赔
			if (workFlowDtoTemp.getUpdate()) {// 表示经过提交操作发现以近有後面的节点，只要保存操作即可
				workFlowDto.setUpdateSwfLog(workFlowDtoTemp.getUpdateSwfLog());
			} else {
				workFlowDto.setUpdateSwfLog(swfLogTemp);
				workFlowDto.setSubmit(true);
				workFlowDto.setSubmitSwfLogList(workFlowDtoTemp.getSubmitSwfLogList());
				workFlowDto.setSubmitSwfPathLogList(workFlowDtoTemp.getSubmitSwfPathLogList());
			}
		}

		return workFlowDto;
	}

	/**
	 * 查询指定的模板中，有指定类型的第一个节点
	 * @param modelNo String 模板号码
	 * @param nodeType String 节点类型
	 * @throws Exception
	 * @return Collection
	 */
	private SwfNode getFirstNodeTypeNode(int modelNo, String nodeType) throws Exception { // 程序思路：
		// ---------------------------------------------------
		// 根据模板号码，节点类型查询出swfNodeDto数据
		// ---------------------------------------------------
		String conditions = "modelNo=" + modelNo + " and nodeType='" + nodeType + "'";
		return this.getWorkFlowService().findModelFirstNodeByCondition(conditions);
	}

	/* ========================（工作流引ad擎正向操作）结束============================ */
	/* ========================第二部分：工作流引擎逆向操作======================== */

	/**
	 * 回退的工作流
	 * @param user 用户信息
	 * @param flowID 工作流流程编码
	 * @param logNo 工作流流程顺序号
	 * @param swfLogFunctionInDto 工作流信息
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	private WorkFlowDto backWorkFlow(UserDto user, String flowID, int logNo, SwfLog swfLogFunctionInDto) throws Exception {
		WorkFlowDto workFlowDto = new WorkFlowDto();
		if ("1".equals(swfLogFunctionInDto.getNextNodeListType())) {
			if (swfLogFunctionInDto.getSwfLogList() != null && swfLogFunctionInDto.getSwfLogList().size() > 0) {
				// 指定退回节点集合（通用）,增加一种直接指定flowId,logNo序列的退回方式
				// 假设为NextNodeList不为null的\
				workFlowDto = this.getBackFlowInfoByNextNodeList(user, flowID, logNo, swfLogFunctionInDto);
				// 增加退回单证环节判断 start
			} else if ("certi".equals(swfLogFunctionInDto.getNodeType())) {
				workFlowDto = this.getBackFlowInfoByCompe(user, flowID, logNo, swfLogFunctionInDto);
				// 增加退回单证环节判断 end
			} else {
				// 指定退回节点(按当前LogNo逐级递减找到第一个要退回的节点) 核损专用
				workFlowDto = this.getBackFlowInfoByVerif(user, flowID, logNo, swfLogFunctionInDto);
			}
		} else {
			// 根据工作流日志swfLog表进行回退，即退回以当前节点logNo为终点的所有起点logNo对应的节点
			workFlowDto = this.getBackFlowInfo(user, flowID, logNo);
		}
		return workFlowDto;
	}

	/**
	 * 回退的工作流(利用模板进行回退)
	 * @param httpServletRequest HttpServletRequest
	 * @param flowID String 工作流流程编码
	 * @param logNo int 工作流流程顺序号
	 * @param keyOut String 记录新节点的KeyOut的值，这样退回的数据就可以直接按业务的号码修改
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	public WorkFlowDto getBackFlowInfo(HttpServletRequest httpServletRequest, String flowID, int logNo, String keyOut) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		/*---1。 取得当前用户信息，写操作员信息到DTO中*/
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		WorkFlowDto workFlowDto = new WorkFlowDto();
		/*---2.查询当前节点工作流数据-*/
		SwfLog swfLog = this.getWorkFlowService().findNodeByPrimaryKey(flowID, logNo);
		/*---3.取工作流的模板号*/
		int modelNo = swfLog.getModelNo();
		int nodeNo = swfLog.getNodeNo();
		/*
		 * 4.---------------------查询节点表wfNode的定义----------------------------------
		 * --
		 */
		// 查询此节点前的所有的节点信息
		List<SwfNode> swfNodeDtoList = this.getWorkFlowService().findModelPerviousNodes(modelNo, nodeNo);
		// 如果从数据库
		if (swfNodeDtoList != null) {
			Iterator<SwfNode> it = swfNodeDtoList.iterator();
			// 获取wfLog表中flowID的最大的logNo的开始值
			int llogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowID);
			int pathNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID);
			List<SwfLog> swfLogDtoList = new ArrayList<SwfLog>();
			List<SwfPathLog> swfPathLogDtoList = new ArrayList<SwfPathLog>();

			while (it.hasNext()) {
				SwfNode swfNode = it.next();
				//6.---写流程节点表wfLog
				SwfLog swfLogTemp = new SwfLog();
				// 根据定义，和原来的节点内容形成新的节点
				this.getSwfLogDtoInfoBySwfNode(swfLogTemp, swfNode, user, flowID, modelNo, llogNo, "7", swfLog.getBusinessNo(), swfLog);
				// 回退操作特有的内容,但是回退操作的那个人是谁呢??
				swfLogTemp.setKeyOut(keyOut);
				swfLogTemp.setHandleDept(swfLog.getHandleDept());
				swfLogTemp.setHandlerCode(swfLog.getBeforeHandlerCode());
				swfLogTemp.setHandlerName(swfLog.getBeforeHandlerName());
				swfLogTemp.setHandleTime(swfLog.getHandleTime());
				swfLogDtoList.add(swfLogTemp);
				//7.---写流程线表wfPathLog
				SwfPathLog swfPathLog = new SwfPathLog();
				this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLog, swfLogTemp, flowID, modelNo, pathNo);
				swfPathLogDtoList.add(swfPathLog);
				llogNo++;
				pathNo++;
			}
			// 加到Dto中
			workFlowDto.setSubmitSwfLogList(swfLogDtoList);
			workFlowDto.setSubmitSwfPathLogList(swfPathLogDtoList);
			workFlowDto.setSubmit(true);
			swfLog.setNodeStatus("5");// 设置为已回退
			workFlowDto.setUpdate(true);
			workFlowDto.setUpdateSwfLog(swfLog);
		}
		return workFlowDto;
	}

	/**
	 * 回退的工作流(根据工作流日志swflog表进行回退，即退回以当前节点logNo为终点的所有起点logNo对应的节点)
	 * @param httpServletRequest HttpServletRequest
	 * @param flowID String 工作流流程编码
	 * @param logNo int 工作流流程顺序号
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	public WorkFlowDto getBackFlowInfo(UserDto user, String flowID, int logNo) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		/*---1。 取得当前用户信息，写操作员信息到DTO中*/
		WorkFlowDto workFlowDto = new WorkFlowDto();
		/*---2.查询当前节点工作流数据-*/
		SwfLog swfLog = this.getWorkFlowService().findNodeByPrimaryKey(flowID, logNo);
		/*---3.取工作流的模板号*/
		int modelNo = swfLog.getModelNo();
		//---4.查询节点表swfPath中所对应的上个节点的定义
		// 查询此节点前的所有的节点信息
		List<SwfLog> swfLogDtoBackList = this.getWorkFlowService().findPerviousNodes(flowID, logNo);
		// 如果从数据库
		if (swfLogDtoBackList != null) {
			Iterator<SwfLog> it = swfLogDtoBackList.iterator();
			// 获取wfLog表中flowID的最大的logNo的开始值
			int llogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowID);
			int pathNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID);
			List<SwfLog> swfLogList = new ArrayList<SwfLog>();
			List<SwfPathLog> swfPathLogList = new ArrayList<SwfPathLog>();
			while (it.hasNext()) {
				SwfLog swfLogemp = new SwfLog();
				//---6.写流程节点表wfLog
				// 根据定义，和原来的节点内容形成新的节点
				PropertyUtils.copyProperties(swfLogemp, it.next());
				swfLogemp.setId(new SwfLogId(flowID,llogNo));
				swfLogemp.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				swfLogemp.setNodeStatus("3");// 表示退回的
				// 设置节点的名称
				String titleAttr = swfLogemp.getNodeName() + "節點流入時間：" + CommonUtils.getMGDateStr(format.parse(swfLogemp.getFlowInTime()),format) + " 上一節點操作人:" + user.getUserName();
				swfLogemp.setTitleStr(titleAttr);
				swfLogemp.setBeforeHandlerCode(user.getUserCode());
				swfLogemp.setBeforeHandlerName(user.getUserName());

				swfLogList.add(swfLogemp);
				// 回退操作特有的内容,但是回退操作的那个人是谁呢??
				//7.写流程线表wfPathLog
				SwfPathLog swfPathLog = new SwfPathLog();
				this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLog, swfLogemp, flowID, modelNo, pathNo);
				swfPathLogList.add(swfPathLog);

				llogNo++;
				pathNo++;
			}
			// 加到Dto中
			workFlowDto.setSubmitSwfLogList(swfLogList);
			workFlowDto.setSubmitSwfPathLogList(swfPathLogList);
			workFlowDto.setSubmit(true);
			swfLog.setNodeStatus("5");// 设置为已回退
			// 回退是将核赔操作员保存
			swfLog.setHandlerCode(user.getUserCode());
			swfLog.setHandlerName(user.getUserName() + "-回退");

			swfLog.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());// 提交时间

			workFlowDto.setUpdate(true);
			workFlowDto.setUpdateSwfLog(swfLog);
		}
		return workFlowDto;
	}

	/**
	 * 回退的工作流(根据指定的节点回退)
	 * @param httpServletRequest HttpServletRequest
	 * @param flowID String 工作流流程编码
	 * @param logNo int 工作流流程顺序号
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	public WorkFlowDto getBackFlowInfoByNextNodeList(UserDto user, String flowID, int logNo, SwfLog swfLogFunctionInDto) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		/*---1。 取得当前用户信息，写操作员信息到DTO中*/
		WorkFlowDto workFlowDto = new WorkFlowDto();
		/*---2.查询当前节点工作流数据-*/
		SwfLog swfLog = this.getWorkFlowService().findNodeByPrimaryKey(flowID, logNo);
		/*---3.取工作流的模板号*/
		int modelNo = swfLog.getModelNo();
		/*
		 * 4.---------------------查询节点表swfPath中所对应的上个节点的定义------------------------
		 * ------------
		 */
		// 如果从指定节点开始计算的
		List<SwfLog> swfNodeNextList = swfLogFunctionInDto.getSwfLogList();
		if (swfNodeNextList != null) {
			Iterator<SwfLog> it = swfNodeNextList.iterator();
			// 获取wfLog表中flowID的最大的logNo的开始值
			int llogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowID);
			int pathNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID);
			List<SwfLog> swfLogList = new ArrayList<SwfLog>();
			List<SwfPathLog> swfPathLogList = new ArrayList<SwfPathLog>();
			// 根据指定的节点号进行回退的操作。
			while (it.hasNext()) {
				SwfLog swfLogTemp = new SwfLog();
				PropertyUtils.copyProperties(swfLogTemp, it.next());
				
				/*
				 * 6.---------------------写流程节点表wfLog----------------------------
				 * --------
				 */
				// 根据定义，和原来的节点内容形成新的节点
				swfLogTemp.setId(new SwfLogId(flowID,llogNo));
				// 日期改成时分秒後，存入数据也是YEAR_TO_SECOND
				swfLogTemp.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				swfLogTemp.setHandleTime(swfLogTemp.getFlowInTime());
				swfLogTemp.setSubmitTime("");
				swfLogTemp.setNodeStatus("3");// 表示退回的
				// 设置节点的名称
				String titleAttr = swfLogTemp.getNodeName() + "節點流入時間：" + CommonUtils.getMGDateStr(format.parse(swfLogTemp.getFlowInTime()),format) + " 上一節點操作人:" + user.getUserName();
				swfLogTemp.setTitleStr(titleAttr);
				swfLogTemp.setBeforeHandlerCode(user.getUserCode());
				swfLogTemp.setBeforeHandlerName(user.getUserName());
				// 设置从哪个节点回退的标志
				swfLogTemp.setBusinessType(swfLog.getNodeType());// 比如新产生的节点知道是从哪里退回来的
				swfLogList.add(swfLogTemp);
				// 回退操作特有的内容,但是回退操作的那个人是谁呢??
				/*
				 * 7.---------------------写流程线表wfPathLog--------------------------
				 * ----------
				 */
				SwfPathLog swfPathLog = new SwfPathLog();
				this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLog, swfLogTemp, flowID, modelNo, pathNo);
				swfPathLogList.add(swfPathLog);
				llogNo++;
				pathNo++;
			}
			workFlowDto.setSubmitSwfLogList(swfLogList);
			workFlowDto.setSubmitSwfPathLogList(swfPathLogList);
			workFlowDto.setSubmit(true);
			swfLog.setNodeStatus("5");// 设置为已回退
			// 日期改成时分秒後，存入数据也是YEAR_TO_SECOND
			swfLog.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());// 提交时间

			workFlowDto.setUpdate(true);
			workFlowDto.setUpdateSwfLog(swfLog);
		}
		return workFlowDto;
	}

	/**
	 * 回退的工作流(指定退回节点,按当前LogNo逐级递减找到第一个要退回的节点)
	 * @param httpServletRequest HttpServletRequest
	 * @param flowID String 工作流流程编码
	 * @param logNo int 工作流流程顺序号
	 * @param swfLogFunctionIn SwfLogDto
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	public WorkFlowDto getBackFlowInfoByVerif(UserDto user, String flowID, int logNo, SwfLog swfLogFunctionIn) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		/*---1。 取得当前用户信息，写操作员信息到DTO中*/
		WorkFlowDto workFlowDto = new WorkFlowDto();
		/*---2.查询当前节点工作流数据-*/
		SwfLog swfLog = this.getWorkFlowService().findNodeByPrimaryKey(flowID, logNo);
		/*---3.取工作流的模板号*/
		int modelNo = swfLog.getModelNo();
		//查询上一个要回退的节点所对应的swflog
		// 查询此节点前的所有的节点信息
		SwfLog backNode = new SwfLog();
		// 查询上一个要回退的节点所对应的swfLog（按LogNo逐级递减找到第一个要回退的节点）
		for (int i = logNo - 1; i > 0; i--) {
			backNode = this.getWorkFlowService().findNodeByPrimaryKey(flowID, i);
			if (backNode != null) {
				if (backNode.getNodeType().trim().equals(swfLogFunctionIn.getNodeType()) && backNode.getLossItemCode().trim().equals(swfLogFunctionIn.getLossItemCode().trim())) {
					break;
				}
			}
		}
		if (backNode == null) {
			throw new UserException(1, 3, "工作流", "找不到回退的節點，請聯系管理員!");
		}
		/* 5.---------------------获取wfLog表中flowID的最大的logNo的开始值----------- */
		int llogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowID);
		int pathNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID);
		List<SwfLog> swfLogList = new ArrayList<SwfLog>();
		List<SwfPathLog> swfPathLogList = new ArrayList<SwfPathLog>();
		SwfLog swfLogNextNode = new SwfLog();
		PropertyUtils.copyProperties(swfLogNextNode, backNode);
		// 根据定义，和原来的节点内容形成新的节点
		swfLogNextNode.setId(new SwfLogId(flowID,llogNo));
		swfLogNextNode.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		swfLogNextNode.setSubmitTime("");
		swfLogNextNode.setHandleTime("");
		swfLogNextNode.setNodeStatus("3");// 表示退回的
		// 设置节点的名称
		String titleAttr = swfLogNextNode.getNodeName() + "節點流入時間：" + CommonUtils.getMGDateStr(format.parse(swfLogNextNode.getFlowInTime()),format) + " 上一節點操作人:" + user.getUserName();
		swfLogNextNode.setTitleStr(titleAttr);
		swfLogNextNode.setBeforeHandlerCode(user.getUserCode());
		swfLogNextNode.setBeforeHandlerName(user.getUserName());
		// 定损环节退回的任务写明退回源节点
		if (swfLogFunctionIn.getBusinessType() != null && !swfLogFunctionIn.getBusinessType().equals("")) {
			swfLogNextNode.setBusinessType(swfLogFunctionIn.getBusinessType());
		}
		swfLogList.add(swfLogNextNode);
		/*
		 * 7.---------------------写流程线表wfPathLog----------------------------------
		 * --
		 */
		SwfPathLog swfPathLog = new SwfPathLog();
		this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLog, swfLogNextNode, flowID, modelNo, pathNo);
		swfPathLogList.add(swfPathLog);

		// 加到Dto中
		workFlowDto.setSubmitSwfLogList(swfLogList);
		workFlowDto.setSubmitSwfPathLogList(swfPathLogList);
		workFlowDto.setSubmit(true);
		swfLog.setNodeStatus("5");// 设置为已回退
		swfLog.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());// 提交时间

		workFlowDto.setUpdate(true);
		workFlowDto.setUpdateSwfLog(swfLog);

		return workFlowDto;
	}

	/**
	 * 回退的工作流(指定退回节点,按当前LogNo逐级递减找到第一个要退回的节点)
	 * @param httpServletRequest HttpServletRequest
	 * @param flowID String 工作流流程编码
	 * @param logNo int 工作流流程顺序号
	 * @param swfLogFunctionInDto SwfLog
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	public WorkFlowDto getBackFlowInfoByCompe(UserDto user, String flowID, int logNo, SwfLog swfLogFunctionInDto) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		/*---1。 取得当前用户信息，写操作员信息到DTO中*/
		WorkFlowDto workFlowDto = new WorkFlowDto();
		/*---2.查询当前节点工作流数据-*/
		SwfLog swfLog = this.getWorkFlowService().findNodeByPrimaryKey(flowID, logNo);
		/*---3.取工作流的模板号*/
		int modelNo = swfLog.getModelNo();
		/* 4.---------------------获取wfLog表中flowID的最大的logNo的开始值----------- */
		int llogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowID);
		int pathNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID);
		/*
		 * 5.---------------------查询上一个要回退的节点所对应的swfLog--------------------------
		 * ----------
		 */
		// 查询此节点前的所有的节点信息
		SwfLog backCertiNode = null;
		// 查询上一个要回退的节点所对应的swfLog（按LogNo逐级递减找到第一个要回退的节点）
		boolean flag = false;
		for (int i = llogNo - 1; i > 0; i--) {
			backCertiNode = this.getWorkFlowService().findNodeByPrimaryKey(flowID, i);
			if (backCertiNode != null) {
				if (backCertiNode.getNodeType().trim().equals(swfLogFunctionInDto.getNodeType())) {
					flag = true;
					break;
				}
			}
		}
		if (backCertiNode == null || !flag) {
			throw new UserException(1, 3, "工作流", "找不到回退的節點，請聯系管理員!");
		}
		if (!"4".equals(backCertiNode.getNodeStatus())) {
			throw new UserException(1, 3, "工作流", "單證環節還沒有處理，請先進行單證處理!");
		}
		List<SwfLog> swfLogList = new ArrayList<SwfLog>();
		List<SwfPathLog> swfPathLogList = new ArrayList<SwfPathLog>();
		//hibernate持久化对象赋值到new对象，脱管
		SwfLog swfLogNextNode = new SwfLog();
		PropertyUtils.copyProperties(swfLogNextNode, backCertiNode);
		/*
		 * 6.---------------------写流程节点表wfLog------------------------------------
		 */
		// 根据定义，和原来的节点内容形成新的节点
		swfLogNextNode.setId(new SwfLogId(flowID,llogNo));
		swfLogNextNode.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		swfLogNextNode.setSubmitTime("");
		swfLogNextNode.setHandleTime("");
		swfLogNextNode.setNodeStatus("3");// 表示退回的
		// 设置节点的名称
		String titleAttr = swfLogNextNode.getNodeName() + "節點流入時間：" + CommonUtils.getMGDateStr(format.parse(swfLogNextNode.getFlowInTime()),format) + " 上一節點操作人:" + user.getUserName();
		swfLogNextNode.setTitleStr(titleAttr);
		swfLogNextNode.setBeforeHandlerCode(user.getUserCode());
		swfLogNextNode.setBeforeHandlerName(user.getUserName());
		// 定损环节退回的任务写明退回源节点
		if (swfLogFunctionInDto.getBusinessType() != null && !swfLogFunctionInDto.getBusinessType().equals("")) {
			swfLogNextNode.setBusinessType(swfLogFunctionInDto.getBusinessType());
		}
		swfLogList.add(swfLogNextNode);
		//---------------------写流程线表wfPathLog----------------------------------
		SwfPathLog swfPathLog = new SwfPathLog();
		this.getSwfPathLogDtoInfoBySwfLog(swfPathLog, swfLog, swfLogNextNode, flowID, modelNo, pathNo);
		swfPathLogList.add(swfPathLog);

		// 加到DTO中
		workFlowDto.setSubmitSwfLogList(swfLogList);
		workFlowDto.setSubmitSwfPathLogList(swfPathLogList);
		workFlowDto.setSubmit(true);
		swfLog.setNodeStatus("5");// 设置为已回退
		swfLog.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());// 提交时间

		workFlowDto.setUpdate(true);
		workFlowDto.setUpdateSwfLog(swfLog);
		return workFlowDto;
	}

	/* ========================（工作流引擎逆向操作）结束============================ */
	/** 工作流服务 */
	private WorkFlowService workFlowService;
	/** 工作流指定节点关系的特别流程ViewHelper */
	private WorkFlowSpecialFlowViewHelper workFlowSpecialFlowViewHelper;
	/** 代码服务 */
	private CodeService codeService;

	public WorkFlowService getWorkFlowService() {
		if (workFlowService == null) {
			return (WorkFlowService) ServiceFactory.getService("workFlowService");
		}
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public WorkFlowSpecialFlowViewHelper getWorkFlowSpecialFlowViewHelper() {
		if (workFlowSpecialFlowViewHelper == null) {
			return (WorkFlowSpecialFlowViewHelper) ServiceFactory.getService("workFlowSpecialFlowViewHelper");
		}
		return workFlowSpecialFlowViewHelper;
	}

	public void setWorkFlowSpecialFlowViewHelper(WorkFlowSpecialFlowViewHelper workFlowSpecialFlowViewHelper) {
		this.workFlowSpecialFlowViewHelper = workFlowSpecialFlowViewHelper;
	}

	public CodeService getCodeService() {
		if (codeService == null) {
			return (CodeService) ServiceFactory.getService("codeService");
		}
		return codeService;
	}

	public BillService getBillService() {
		if (billService == null) {
			billService = (BillService) ServiceFactory.getService("billService");
		}
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
}
