package com.sinosoft.claim.claim.web;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.bl.action.domain.BLSwfLogAction;
import com.sinosoft.claim.bl.action.domain.BLSwfPathLogAction;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.dto.custom.WorkFlowDto;
import com.sinosoft.claim.dto.domain.SwfFlowMainDto;
import com.sinosoft.claim.dto.domain.SwfLogDto;
import com.sinosoft.claim.dto.domain.SwfNodeDto;
import com.sinosoft.claim.dto.domain.SwfPathLogDto;
import com.sinosoft.claim.ui.control.action.UIWorkFlowAction;
import com.sinosoft.claim.ui.control.viewHelper.WorkFlowSpecialFlowViewHelper;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.DBManager;

/**
 * <p>
 * Title: ScheduleViewHelper
 * </p>
 * c
 * <p>
 * Description:工作流引擎ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2004
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */

public class WorkFlowEngineViewHelper {
	/** 单号取号服务 */
	private BillService billService;
	/** 代码服务 */
	private CodeService codeService;

	/**
	 * 默认构造方法
	 */
	public WorkFlowEngineViewHelper() {// COM.IIDIDispatch
	}

	/* ========================第一部分：工作引擎正向/逆向操作======================== */

	/**
	 * 操作工作流的数据整理,此函数就是所有工作流流转的引擎入口， 想知道工作流是如何运转的，从这个函数看起吧。
	 * @param user UserDto 用户dto
	 * @param swfLogFunctionInDto 传参数
	 * @throws Exception
	 * @return WorkFlowDto 工作流程数据传输数据结构
	 */
	public WorkFlowDto viewToDto(UserDto user, SwfLogDto swfLogFunctionInDto) throws Exception {
		// 取得当前用户信息，写操作员信息到Dto中
		WorkFlowDto workFlowDto = new WorkFlowDto();
		WorkFlowDto workFlowDtoTemp = new WorkFlowDto();
		SwfLogDto swfLogDto = null;
		Collection<?> swfFlowNodeList = new ArrayList<SwfLogDto>();
		ArrayList<Object> submitLogList = new ArrayList<Object>();
		ArrayList<Object> submitPathLogList = new ArrayList<Object>();
		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();

		String swfLogFlowID = swfLogFunctionInDto.getFlowID(); // 流程号码
		int swfLogLogNo = swfLogFunctionInDto.getLogNo(); // 流程中的节点号码
		// 一般以上两个是必须的
		String nodeStatus = swfLogFunctionInDto.getNodeStatus(); // 操作是哪种
																	// 2,4,5,3目前只有这几种
		String nextBusinessNo = swfLogFunctionInDto.getNextBusinessNo();
		String keyIn = swfLogFunctionInDto.getKeyIn();

		String keyOut = swfLogFunctionInDto.getKeyOut();

		// --------------------创建工作流用的参数--------------------//
		boolean createWorkFlow = swfLogFunctionInDto.getCreateFlow(); // True
																		// 创建工作流功能
		String riskCode = swfLogFunctionInDto.getRiskCode();
		String comCode = swfLogFunctionInDto.getComCode();
		String policyNo = swfLogFunctionInDto.getPolicyNo();
		// --------------------创建工作流用的参数--------------------//

		String businessNo = swfLogFunctionInDto.getBusinessNo();// 只有当flowid没有时起作用
		String nodeType = swfLogFunctionInDto.getNodeType(); // 如果为T类型或者没有flowid时有用

		String conditionBusinessNo = swfLogFunctionInDto.getConditionBusinessNo();
		String typeFlag = swfLogFunctionInDto.getTypeFlag();
		String claimTypeFlag = swfLogFunctionInDto.getClaimTypeFlag();// 简易赔案条件判断
		// 注意此处，通过判断去做简易赔案数据整理
		if ("01".equals(claimTypeFlag) || "02".equals(claimTypeFlag) || "03".equals(claimTypeFlag)) {
			WorkFlowSpecialFlowViewHelper workFlowSpecailFlowViewHelper = new WorkFlowSpecialFlowViewHelper();
			return workFlowSpecailFlowViewHelper.viewToDto(user, swfLogFunctionInDto);
		}

		// 如果是利用的如上方法，则只需要查询工作流节点中的内容就可以了
		int logMaxNo = 0; // 解决取LogNo号的问题
		int pathMaxNo = 0; // 解决取pathNo号的问题

		// 1.创建工作流程/查找流程信息
		if (createWorkFlow) {
			workFlowDto = createFlowInfo(user, businessNo, comCode, riskCode, policyNo, swfLogFunctionInDto.getInsuredName(), swfLogFunctionInDto.getLossItemName());
			if (workFlowDto.getOperateResult() < 0) {
				// 没有取得模板的号码
				return workFlowDto;
			}

			// 设置当前节点的内容
			swfLogDto = workFlowDto.getCreateSwfLogDto();
			logMaxNo = 2;
			pathMaxNo = 1;
		} else { // 查询出工作流数据/查找当前节点
			if (!swfLogFlowID.equals("") && swfLogLogNo > 0) { // 利用主键flowid,LogNo查工作节点
				swfFlowNodeList = uiWorkFlowAction.findCurrentNode(swfLogFlowID, swfLogLogNo);
			} else {// 利用主键businessNo, nodeType查工作节点
				swfFlowNodeList = uiWorkFlowAction.findCurrentNode(businessNo, nodeType);
			}
			Iterator<?> it = swfFlowNodeList.iterator();
			if (it.hasNext()) {
				// 获得当前工作流程的信息
				swfLogDto = (SwfLogDto)it.next();
				// 並取得工作流上点和边的最大号码
				logMaxNo = uiWorkFlowAction.getSwfLogMaxLogNo(swfLogDto.getFlowID());
				pathMaxNo = uiWorkFlowAction.getSwfPathLogMaxPathNo(swfLogDto.getFlowID());

			}
		}
		// 工作流判断是否可以操作
		if (swfLogDto != null) { // 有工作流程查询出来
			// 要判断工作流程是否结束，如果结束了，下面的都不需要操作的
			if (uiWorkFlowAction.checkFlowClose(swfLogDto.getFlowID())) {
				// 工作流已经关闭了
				workFlowDto.setCheckClose(true);
				return workFlowDto;
			}
			// 判断是不是要创建创建子任务，以後都相同的M类型任务的处理 M创建後，和正常的流程是一样的
			if (swfLogDto.getTaskType().equals("M") && !nodeStatus.equals("5")) {
				// 创建子任务过程
				swfLogDto.setBusinessNo(nextBusinessNo);

				swfLogDto.setKeyIn(businessNo);
				workFlowDtoTemp = getSubmitFlowInfo(user, swfLogDto, keyIn, logMaxNo, pathMaxNo);
				logMaxNo = logMaxNo + 1;
				pathMaxNo = pathMaxNo + 1;

				workFlowDto.setSubmit(true);
				workFlowDto.setSubmitSwfLogDtoList(workFlowDtoTemp.getSubmitSwfLogDtoList());
				workFlowDto.setSubmitSwfPathLogDtoList(workFlowDtoTemp.getSubmitSwfPathLogDtoList());
				// 设置创建的子任务为当前的需要处理的任务节点
				swfFlowNodeList.clear();
				swfFlowNodeList = workFlowDto.getSubmitSwfLogDtoList();
				Iterator<?> it = swfFlowNodeList.iterator();
				if (it.hasNext()) {
					swfLogDto = (SwfLogDto)it.next();
					swfLogDto.setHandlerCode(user.getUserCode());
					swfLogDto.setHandlerName(user.getUserName());
					// M类型出来的子节点直接设置keyOut,不管是否提交
					swfLogDto.setKeyOut(keyOut);
				}
			}
			// 传入的参数类型为T特殊类型的节点
			if (swfLogFunctionInDto.getTaskType().equals("T")) {
				// 创建新节点
				// 为T类型的节点可以正常的走普通路径，所以做标志判断的时候该用其他内部标志，表示T类型，比如AddNewNode等同於T
				swfLogDto.setTaskType("AddNewNode");
				workFlowDtoTemp = getSubmitFlowInfo(user, swfLogDto, keyIn, logMaxNo, pathMaxNo);

				if (workFlowDtoTemp.getSubmitSwfLogDtoList() == null) {// 查出後续是没有节点的。。。直接抛出
					return workFlowDto;
				}

				// 因为节点产生了，所以增加了
				logMaxNo = logMaxNo + 1;
				pathMaxNo = pathMaxNo + 1;
				workFlowDto.setSubmit(true);
				workFlowDto.setSubmitSwfLogDtoList(workFlowDtoTemp.getSubmitSwfLogDtoList());
				workFlowDto.setSubmitSwfPathLogDtoList(workFlowDtoTemp.getSubmitSwfPathLogDtoList());
				// 设置创建的子任务为当前的需要处理的任务节点
				swfFlowNodeList.clear();
				swfFlowNodeList = workFlowDto.getSubmitSwfLogDtoList();
				Iterator<?> it = swfFlowNodeList.iterator();
				if (it.hasNext()) {
					swfLogDto = (SwfLogDto)it.next();
					swfLogDto.setHandlerCode(user.getUserCode());
					swfLogDto.setHandlerName(user.getUserName());

					swfLogDto.setTypeFlag(typeFlag);// 因为回勘的要求
					// T类型出来的子节点直接设置keyOut,不管是否提交
					swfLogDto.setKeyOut(keyOut);
				}
			}
			// 做判断提交，如果不可以提交，那就改为保存操作

			// 2.修改工作流0/1/2/3都是修改工作流
			if (nodeStatus.equals("0") || nodeStatus.equals("1") || nodeStatus.equals("2") || nodeStatus.equals("3")) {
				// 修改工作流
				// 由於团意险在报案环节可以修改被保险人，所以这里加上被保险人，不加的话在修改被保险人时，工作流表中的被保险人不变
				if ("regis".equals(nodeType)) {
					swfLogDto.setInsuredName(swfLogFunctionInDto.getInsuredName());
				}

				swfLogDto.setNodeStatus(nodeStatus);
				swfLogDto.setHandlerCode(user.getUserCode());
				swfLogDto.setHandlerName(user.getUserName());
				swfLogDto.setKeyOut(keyOut);
				// modify by weishixin modify 20050227 start
				// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
				swfLogDto.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				// modify by weishixin modify 20050227 end
				if (typeFlag != null && typeFlag.length() > 0) {
					swfLogDto.setTypeFlag(typeFlag);
				}
				// 如果是M类型的接点，则不需要处理update情况//优化工作流
				if (workFlowDto.getSubmit()) {
					ArrayList<SwfLogDto> submitLogMList = new ArrayList<SwfLogDto>();
					submitLogMList.add(swfLogDto);
					workFlowDto.setSubmitSwfLogDtoList(submitLogMList);
				} else {
					// 判断当前信息中是否有工作流的存在。。
					if (swfLogDto.getLogNo() > 0) {
						workFlowDto.setUpdate(true);
						workFlowDto.setUpdateSwfLogDto(swfLogDto);
					}

				}
			}

			// 3。提交工作流
			if (nodeStatus.equals("4")) {

				// 判断是否需要人为的结束,人为结束只要设置传入的参数为endflag=1就行了。
				if (swfLogFunctionInDto.getEndFlag().equals("1")) {
					swfLogDto.setEndFlag("1");
				}
				// 判断是否允许节点能提交？？这个在页面上已经进行判断了
				// 由於团意险在报案环节可以修改被保险人，所以这里加上被保险人，不加的话在修改被保险人时，工作流表中的被保险人不变
				if ("regis".equals(nodeType)) {
					swfLogDto.setInsuredName(swfLogFunctionInDto.getInsuredName());
				}
				// 修改工作流nodeStatus=4
				swfLogDto.setNodeStatus(nodeStatus);
				swfLogDto.setKeyOut(keyOut); //
				// add by lym for :关联保单定损只有一个，定损时只存商业保单，当：
				// 从定损处入口申请垫支付时，保单号需要取对应於强制立案号的强制保单，所以不能用定损时的保单。
				if (swfLogFunctionInDto != null && swfLogFunctionInDto.getPolicyNo() != null && !swfLogFunctionInDto.getPolicyNo().equals("")) {
					swfLogDto.setPolicyNo(swfLogFunctionInDto.getPolicyNo());
				}
				// add by lym for
				// end---------------------------------------------
				// 设置提交的时间
				// modify by weishixin modify 20050227 start
				// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
				swfLogDto.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				// modify by weishixin modify 20050227 end
				// 还需要设置条件
				swfLogDto.setConditionBusinessNo(conditionBusinessNo);
				// 设置是否有typeFlag
				if ((typeFlag != null) && (typeFlag.length() > 0)) {
					swfLogDto.setTypeFlag(typeFlag);
				}

				// 正是做提交的操作
				workFlowDtoTemp = submitWorkFlow(user, swfLogDto, swfLogFunctionInDto, logMaxNo, pathMaxNo);
				workFlowDto.setUpdate(workFlowDtoTemp.getUpdate());
				workFlowDto.setUpdateSwfLogDto(workFlowDtoTemp.getUpdateSwfLogDto());
				workFlowDto.setUpdateSwfLog2Dto(workFlowDtoTemp.getUpdateSwfLog2Dto());

				submitLogList = workFlowDtoTemp.getSubmitSwfLogDtoList();
				if (submitLogList == null)
					submitLogList = new ArrayList<Object>(); // 考虑到有时候提交後面是空的,允许的
				submitPathLogList = workFlowDtoTemp.getSubmitSwfPathLogDtoList();
				if (submitPathLogList == null)
					submitPathLogList = new ArrayList<Object>(); // 考虑到有时候提交後面是空的,允许的
				// 原来有新增加的数据，需要合並的节点和路径的这里是优化，前面进行M和T操作形成的新节点。
				if (workFlowDto.getSubmit()) {
					Iterator<?> it = workFlowDto.getSubmitSwfLogDtoList().iterator();
					Iterator<?> it2 =workFlowDto.getSubmitSwfPathLogDtoList().iterator();
					while (it.hasNext()) {
						submitLogList.add(it.next());
					}

					while (it2.hasNext()) {
						submitPathLogList.add(it2.next());
					}

					// 如果已经有提交的数据了
				}

				workFlowDto.setSubmit(workFlowDtoTemp.getSubmit());
				workFlowDto.setSubmitSwfLogDtoList(submitLogList);
				workFlowDto.setSubmitSwfPathLogDtoList(submitPathLogList);
				workFlowDto.setClose(workFlowDtoTemp.getClose());
				workFlowDto.setCloseSwfFlowMainDto(workFlowDtoTemp.getCloseSwfFlowMainDto());

				workFlowDto.setStatus(workFlowDtoTemp.getStatus());
			} // 判断是提交操作的结束
				// 优化工作流
			if (workFlowDto.getCreate() && workFlowDto.getUpdate()) {
				// 新创建的工作流直接创建，就不用在udpate一把了
				workFlowDto.setCreateSwfLogDto(workFlowDto.getUpdateSwfLogDto());
				workFlowDto.setUpdate(false);
			}

			// 优化工作流，如果报案的时候同时进行了关闭操作，则关闭的主表当时一定是空的，所以
			if (workFlowDto.getCreate() && workFlowDto.getClose()) {
				// 新创建的工作流直接创建，关闭也要进行修整的
				SwfFlowMainDto swfFlowMainDto = workFlowDto.getCreateSwfFlowMainDto();
				swfFlowMainDto.setFlowStatus("0");
				workFlowDto.setCloseSwfFlowMainDto(swfFlowMainDto);

			}

			// 4。回退工作流
			if (nodeStatus.equals("5")) {
				// 目前只有核损、定损才用得到，无条件到达定损，並且人员是上个节点上的人员
				// 首先查找定损的节点，从回退的节点上查询属性为定损的节点，处理类型为typeFlag相同的节点
				// 由後向前进行查询。（目前只有核损、核价和人伤核损三个）
				// lijiyuan add 2005-11-4
				// modify by liuwei at 2011-03-03
				// 新增一条判断swfLogDto.getNodeType().equals("certi")可以从理算退回到单证
				if (swfLogDto.getNodeType().equals("verip") || swfLogDto.getNodeType().equals("verif") || swfLogDto.getNodeType().equals("veriw") || swfLogDto.getNodeType().equals("propv") || swfLogDto.getNodeType().equals("compe")
						|| swfLogDto.getNodeType().equals("certi")) // 核损的退回,理算的退回
				{
					workFlowDto = backWorkFlow(user, swfLogDto.getFlowID(), swfLogDto.getLogNo(), swfLogFunctionInDto);
				} else {
					throw new UserException(1, 3, "工作流", "沒有發現可以回退的工作流節點");
				}
			}
		}
		return workFlowDto;
	}

	/**
	 * 操作工作流的数据整理,此函数就是所有工作流流转的引擎入口， 简易赔案工作流处理
	 * @param user UserDto 用户dto
	 * @param swfLogFunctionInDto 传参数
	 * @throws Exception
	 * @return WorkFlowDto 工作流程数据传输数据结构 add by liping 80728
	 *         简易赔案提交数据查询也必须在一个事物控制中做
	 */
	public WorkFlowDto viewToDto(DBManager dbManager, UserDto user, SwfLogDto swfLogFunctionInDto) throws Exception {
		// 取得当前用户信息，写操作员信息到Dto中
		WorkFlowDto workFlowDto = new WorkFlowDto();
		WorkFlowDto workFlowDtoTemp = new WorkFlowDto();
		SwfLogDto swfLogDto = null;
		Collection<?> swfFlowNodeList = new ArrayList<SwfLogDto>();
		ArrayList<SwfLogDto> submitLogList = new ArrayList<SwfLogDto>();
		ArrayList<SwfPathLogDto> submitPathLogList = new ArrayList<SwfPathLogDto>();
		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();

		String swfLogFlowID = swfLogFunctionInDto.getFlowID(); // 流程号码
		int swfLogLogNo = swfLogFunctionInDto.getLogNo(); // 流程中的节点号码
		// 一般以上两个是必须的
		String nodeStatus = swfLogFunctionInDto.getNodeStatus(); // 操作是哪种
																	// 2,4,5,3目前只有这几种
		String nextBusinessNo = swfLogFunctionInDto.getNextBusinessNo();
		String keyIn = swfLogFunctionInDto.getKeyIn();

		String keyOut = swfLogFunctionInDto.getKeyOut();

		// --------------------创建工作流用的参数--------------------//
		boolean createWorkFlow = swfLogFunctionInDto.getCreateFlow(); // True
																		// 创建工作流功能
		String riskCode = swfLogFunctionInDto.getRiskCode();
		String comCode = swfLogFunctionInDto.getComCode();
		String policyNo = swfLogFunctionInDto.getPolicyNo();
		// --------------------创建工作流用的参数--------------------//

		String businessNo = swfLogFunctionInDto.getBusinessNo();// 只有当flowid没有时起作用
		String nodeType = swfLogFunctionInDto.getNodeType(); // 如果为T类型或者没有flowid时有用

		String conditionBusinessNo = swfLogFunctionInDto.getConditionBusinessNo();
		String typeFlag = swfLogFunctionInDto.getTypeFlag();
		String claimTypeFlag = swfLogFunctionInDto.getClaimTypeFlag();// 简易赔案条件判断
		// 注意此处，通过判断去做简易赔案数据整理
		if ("01".equals(claimTypeFlag) || "02".equals(claimTypeFlag) || "03".equals(claimTypeFlag)) {
			WorkFlowSpecialFlowViewHelper workFlowSpecailFlowViewHelper = new WorkFlowSpecialFlowViewHelper();
			return workFlowSpecailFlowViewHelper.viewToDto(dbManager, user, swfLogFunctionInDto);
		}

		// 如果是利用的如上方法，则只需要查询工作流节点中的内容就可以了
		int logMaxNo = 0; // 解决取LogNo号的问题
		int pathMaxNo = 0; // 解决取pathNo号的问题

		// 1.创建工作流程/查找流程信息
		if (createWorkFlow) {
			workFlowDto = createFlowInfo(user, businessNo, comCode, riskCode, policyNo, swfLogFunctionInDto.getInsuredName(), swfLogFunctionInDto.getLossItemName());
			if (workFlowDto.getOperateResult() < 0) {
				// 没有取得模板的号码
				return workFlowDto;
			}

			// 设置当前节点的内容
			swfLogDto = workFlowDto.getCreateSwfLogDto();
			logMaxNo = 2;
			pathMaxNo = 1;
		} else { // 查询出工作流数据/查找当前节点
			if (!swfLogFlowID.equals("") && swfLogLogNo > 0) { // 利用主键flowid,LogNo查工作节点
				// modify by liping 080728 简易赔案特殊处理
				String condition = " flowid='" + swfLogFlowID + "' And " + " LogNo=" + swfLogLogNo;
				BLSwfLogAction blSwfLogAction = new BLSwfLogAction();
				swfFlowNodeList = blSwfLogAction.findViewSwfLogAll(dbManager, condition);
			} else {// 利用主键businessNo, nodeType查工作节点
					// modify by liping 080728 简易赔案特殊处理
				String condition = " BUSINESSNO='" + businessNo + "' And " + " NodeType ='" + nodeType + "' And (NodeStatus<'4' )";
				BLSwfLogAction blSwfLogAction = new BLSwfLogAction();
				swfFlowNodeList = blSwfLogAction.findViewSwfLogAll(dbManager, condition);
			}
			Iterator<?> it = swfFlowNodeList.iterator();
			if (it.hasNext()) {
				// 获得当前工作流程的信息
				swfLogDto = (SwfLogDto) it.next();
				// 並取得工作流上点和边的最大号码
				// modify by liping 080728 简易赔案特殊处理
				logMaxNo = new BLSwfLogAction().getMaxLogNo(dbManager, swfLogDto.getFlowID());
				pathMaxNo = new BLSwfPathLogAction().getMaxPathNo(dbManager, swfLogDto.getFlowID());

			}
		}
		// 工作流判断是否可以操作
		if (swfLogDto != null) { // 有工作流程查询出来
			// 要判断工作流程是否结束，如果结束了，下面的都不需要操作的
			if (uiWorkFlowAction.checkFlowClose(swfLogDto.getFlowID())) {
				// 工作流已经关闭了
				workFlowDto.setCheckClose(true);
				return workFlowDto;
			}
			// 判断是不是要创建创建子任务，以後都相同的M类型任务的处理 M创建後，和正常的流程是一样的
			if (swfLogDto.getTaskType().equals("M") && !nodeStatus.equals("5")) {
				// 创建子任务过程
				swfLogDto.setBusinessNo(nextBusinessNo);

				swfLogDto.setKeyIn(businessNo);
				workFlowDtoTemp = getSubmitFlowInfo(user, swfLogDto, keyIn, logMaxNo, pathMaxNo);
				logMaxNo = logMaxNo + 1;
				pathMaxNo = pathMaxNo + 1;

				workFlowDto.setSubmit(true);
				workFlowDto.setSubmitSwfLogDtoList(workFlowDtoTemp.getSubmitSwfLogDtoList());
				workFlowDto.setSubmitSwfPathLogDtoList(workFlowDtoTemp.getSubmitSwfPathLogDtoList());
				// 设置创建的子任务为当前的需要处理的任务节点
				swfFlowNodeList.clear();
				swfFlowNodeList = workFlowDto.getSubmitSwfLogDtoList();
				Iterator<?> it = swfFlowNodeList.iterator();
				if (it.hasNext()) {
					swfLogDto = (SwfLogDto) it.next();
					swfLogDto.setHandlerCode(user.getUserCode());
					swfLogDto.setHandlerName(user.getUserName());
					// M类型出来的子节点直接设置keyOut,不管是否提交
					swfLogDto.setKeyOut(keyOut);

				}
			}
			// 传入的参数类型为T特殊类型的节点
			if (swfLogFunctionInDto.getTaskType().equals("T")) {
				// 创建新节点
				// 为T类型的节点可以正常的走普通路径，所以做标志判断的时候该用其他内部标志，表示T类型，比如AddNewNode等同於T
				swfLogDto.setTaskType("AddNewNode");
				workFlowDtoTemp = getSubmitFlowInfo(user, swfLogDto, keyIn, logMaxNo, pathMaxNo);

				if (workFlowDtoTemp.getSubmitSwfLogDtoList() == null) {// 查出後续是没有节点的。。。直接抛出
					return workFlowDto;
				}

				// 因为节点产生了，所以增加了
				logMaxNo = logMaxNo + 1;
				pathMaxNo = pathMaxNo + 1;
				workFlowDto.setSubmit(true);
				workFlowDto.setSubmitSwfLogDtoList(workFlowDtoTemp.getSubmitSwfLogDtoList());
				workFlowDto.setSubmitSwfPathLogDtoList(workFlowDtoTemp.getSubmitSwfPathLogDtoList());
				// 设置创建的子任务为当前的需要处理的任务节点
				swfFlowNodeList.clear();
				swfFlowNodeList = workFlowDto.getSubmitSwfLogDtoList();
				Iterator<?> it = swfFlowNodeList.iterator();
				if (it.hasNext()) {
					swfLogDto = (SwfLogDto) it.next();
					swfLogDto.setHandlerCode(user.getUserCode());
					swfLogDto.setHandlerName(user.getUserName());

					swfLogDto.setTypeFlag(typeFlag);// 因为回勘的要求
					// T类型出来的子节点直接设置keyOut,不管是否提交
					swfLogDto.setKeyOut(keyOut);
				}
			}
			// 做判断提交，如果不可以提交，那就改为保存操作

			// 2.修改工作流0/1/2/3都是修改工作流
			if (nodeStatus.equals("0") || nodeStatus.equals("1") || nodeStatus.equals("2") || nodeStatus.equals("3")) {
				// 修改工作流
				swfLogDto.setNodeStatus(nodeStatus);
				swfLogDto.setHandlerCode(user.getUserCode());
				swfLogDto.setHandlerName(user.getUserName());
				swfLogDto.setKeyOut(keyOut);
				// modify by weishixin modify 20050227 start
				// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
				swfLogDto.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				// modify by weishixin modify 20050227 end
				if (typeFlag != null && typeFlag.length() > 0) {
					swfLogDto.setTypeFlag(typeFlag);
				}
				// 如果是M类型的接点，则不需要处理update情况//优化工作流
				if (workFlowDto.getSubmit()) {
					ArrayList<SwfLogDto> submitLogMList = new ArrayList<SwfLogDto>();
					submitLogMList.add(swfLogDto);
					workFlowDto.setSubmitSwfLogDtoList(submitLogMList);
				} else {
					// 判断当前信息中是否有工作流的存在。。
					if (swfLogDto.getLogNo() > 0) {
						workFlowDto.setUpdate(true);
						workFlowDto.setUpdateSwfLogDto(swfLogDto);
					}

				}
			}

			// 3。提交工作流
			if (nodeStatus.equals("4")) {

				// 判断是否需要人为的结束,人为结束只要设置传入的参数为endflag=1就行了。
				if (swfLogFunctionInDto.getEndFlag().equals("1")) {
					swfLogDto.setEndFlag("1");
				}
				// 判断是否允许节点能提交？？这个在页面上已经进行判断了

				// 修改工作流nodeStatus=4
				swfLogDto.setNodeStatus(nodeStatus);
				swfLogDto.setKeyOut(keyOut); //
				// add by lym for :关联保单定损只有一个，定损时只存商业保单，当：
				// 从定损处入口申请垫支付时，保单号需要取对应於强制立案号的强制保单，所以不能用定损时的保单。
				if (swfLogFunctionInDto != null && swfLogFunctionInDto.getPolicyNo() != null && !swfLogFunctionInDto.getPolicyNo().equals("")) {
					swfLogDto.setPolicyNo(swfLogFunctionInDto.getPolicyNo());
				}
				// add by lym for
				// end---------------------------------------------
				// 设置提交的时间
				// modify by weishixin modify 20050227 start
				// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
				swfLogDto.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				// modify by weishixin modify 20050227 end
				// 还需要设置条件
				swfLogDto.setConditionBusinessNo(conditionBusinessNo);
				// 设置是否有typeFlag
				if ((typeFlag != null) && (typeFlag.length() > 0)) {
					swfLogDto.setTypeFlag(typeFlag);
				}

				// 正是做提交的操作
				workFlowDtoTemp = submitWorkFlow(user, swfLogDto, swfLogFunctionInDto, logMaxNo, pathMaxNo);
				workFlowDto.setUpdate(workFlowDtoTemp.getUpdate());
				workFlowDto.setUpdateSwfLogDto(workFlowDtoTemp.getUpdateSwfLogDto());
				workFlowDto.setUpdateSwfLog2Dto(workFlowDtoTemp.getUpdateSwfLog2Dto());

				submitLogList = workFlowDtoTemp.getSubmitSwfLogDtoList();
				if (submitLogList == null)
					submitLogList = new ArrayList<SwfLogDto>(); // 考虑到有时候提交後面是空的,允许的
				submitPathLogList = workFlowDtoTemp.getSubmitSwfPathLogDtoList();
				if (submitPathLogList == null)
					submitPathLogList = new ArrayList<SwfPathLogDto>(); // 考虑到有时候提交後面是空的,允许的
				// 原来有新增加的数据，需要合並的节点和路径的这里是优化，前面进行M和T操作形成的新节点。
				if (workFlowDto.getSubmit()) {
					Iterator<?> it = workFlowDto.getSubmitSwfLogDtoList().iterator();
					Iterator<?> it2 = workFlowDto.getSubmitSwfPathLogDtoList().iterator();
					while (it.hasNext()) {
						submitLogList.add((SwfLogDto) it.next());
					}

					while (it2.hasNext()) {
						submitPathLogList.add((SwfPathLogDto) it2.next());
					}

					// 如果已经有提交的数据了
				}

				workFlowDto.setSubmit(workFlowDtoTemp.getSubmit());
				workFlowDto.setSubmitSwfLogDtoList(submitLogList);
				workFlowDto.setSubmitSwfPathLogDtoList(submitPathLogList);
				workFlowDto.setClose(workFlowDtoTemp.getClose());
				workFlowDto.setCloseSwfFlowMainDto(workFlowDtoTemp.getCloseSwfFlowMainDto());

				workFlowDto.setStatus(workFlowDtoTemp.getStatus());
			} // 判断是提交操作的结束
				// 优化工作流
			if (workFlowDto.getCreate() && workFlowDto.getUpdate()) {
				// 新创建的工作流直接创建，就不用在udpate一把了
				workFlowDto.setCreateSwfLogDto(workFlowDto.getUpdateSwfLogDto());
				workFlowDto.setUpdate(false);
			}

			// 优化工作流，如果报案的时候同时进行了关闭操作，则关闭的主表当时一定是空的，所以
			if (workFlowDto.getCreate() && workFlowDto.getClose()) {
				// 新创建的工作流直接创建，关闭也要进行修整的
				SwfFlowMainDto swfFlowMainDto = workFlowDto.getCreateSwfFlowMainDto();
				swfFlowMainDto.setFlowStatus("0");
				workFlowDto.setCloseSwfFlowMainDto(swfFlowMainDto);

			}

			// 4。回退工作流
			if (nodeStatus.equals("5")) {
				// 目前只有核损、定损才用得到，无条件到达定损，並且人员是上个节点上的人员
				// 首先查找定损的节点，从回退的节点上查询属性为定损的节点，处理类型为typeFlag相同的节点
				// 由後向前进行查询。（目前只有核损、核价和人伤核损三个）
				// lijiyuan add 2005-11-4
				if (swfLogDto.getNodeType().equals("verip") || swfLogDto.getNodeType().equals("verif") || swfLogDto.getNodeType().equals("veriw") || swfLogDto.getNodeType().equals("propv") || swfLogDto.getNodeType().equals("compe")) // 核损的退回,理算的退回
				{
					workFlowDto = backWorkFlow(user, swfLogDto.getFlowID(), swfLogDto.getLogNo(), swfLogFunctionInDto);
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
	 * @param BusinessNo String 业务号码，主要是如果是报案节点开始的，那么是报案号码
	 * @param comCode String 当前的归属机构的代码
	 * @param RiskCode String 当前的险种
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	protected WorkFlowDto createFlowInfo(UserDto user, String BusinessNo, String comCode, String RiskCode, String policyNo, String insuredName, String lossItemName) throws Exception {
		// 1。 取得当前用户信息，写操作员信息到Dto中
		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		WorkFlowDto workFlowDto = new WorkFlowDto();
		int year = DateTime.current().getYear();
		String strTitle = "創建工作流程";
		String tableName = "swfflowmain";
		// 默认创建的险种是车险类DAA的
		if (RiskCode.equals("")) {
			RiskCode = codeService.translateProductCode("RISKCODE_DAA");
		}
		/*---2.取工作流的流号-*/
		String flowID = this.getBillService().getNo(tableName, RiskCode, comCode, year);
		// String flowID="200000000010305000015";
		/*---3.取工作流的模板号,从swfmodeluse中取得相映的模板设置的号码*/
		int modelNo = uiWorkFlowAction.getModelNo(RiskCode, comCode);
		// 如果没有取得工作流号码，那么则返回一个空的数据集合，通知调用该函数的程序
		// add by lixiang start at 2006-1-20
		// reason:查找不到分配的模板时候，立刻提示出错误，要不然会有问题

		if (modelNo < 0) {
			throw new UserException(1, 3, "工作流", "沒有發現險種爲'" + RiskCode + "'，機構爲'" + comCode + "'所配置的理賠模板，請和管理員聯系！");
		}
		/*
		 * 4.写---------------------工作流主表wfFlowMain--------------------------------
		 * ----
		 */
		SwfFlowMainDto swfFlowMainDto = new SwfFlowMainDto();
		swfFlowMainDto.setFlowID(flowID);
		swfFlowMainDto.setFlowName(BusinessNo);
		swfFlowMainDto.setFlowStatus("1");
		swfFlowMainDto.setPolicyNo(policyNo);
		swfFlowMainDto.setCreatDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
		swfFlowMainDto.setModelNo(modelNo);
		swfFlowMainDto.setFlag("");

		workFlowDto.setCreateSwfFlowMainDto(swfFlowMainDto);

		/*
		 * 5.---------------------查询节点表wfNode的定义----------------------------------
		 * --
		 */
		// 假设所有的模板第一个节点的号码就是1
		int nodeNo = 1;
		SwfNodeDto swfNodeDto = new SwfNodeDto();
		swfNodeDto = uiWorkFlowAction.findModelNodeByPrimaryKey(modelNo, nodeNo);

		/*
		 * 6.---------------------写流程节点表wfLog------------------------------------
		 */
		SwfLogDto swfLogDto = new SwfLogDto();
		swfLogDto.setFlowID(flowID);
		swfLogDto.setLogNo(1);
		swfLogDto.setModelNo(modelNo);
		swfLogDto.setNodeNo(nodeNo);
		swfLogDto.setNodeName(swfNodeDto.getNodeName());
		swfLogDto.setBusinessNo(BusinessNo);
		swfLogDto.setHandleDept(user.getComCode());
		swfLogDto.setHandlerCode(user.getUserCode());
		swfLogDto.setHandlerName(user.getUserName());

		// 待处理的查询条件，增加报案号，被保险人，车牌号(工作流需要添加)
		swfLogDto.setInsuredName(insuredName);
		swfLogDto.setLossItemName(lossItemName);
		swfLogDto.setRegistNo(BusinessNo);
		// modify by weishixin modify 20050227 start
		// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
		swfLogDto.setTimeLimit(swfNodeDto.getTimeLimit());
		swfLogDto.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		// modify by weishixin modify 20050227 end
		swfLogDto.setNodeStatus("1");
		swfLogDto.setFlowStatus("1");
		swfLogDto.setPackageID("0");
		swfLogDto.setFlag(swfNodeDto.getFlag());
		swfLogDto.setTaskNo(swfNodeDto.getTaskNo());
		swfLogDto.setTaskType(swfNodeDto.getTaskType());
		swfLogDto.setNodeType(swfNodeDto.getNodeType());
		swfLogDto.setTitleStr(strTitle);
		swfLogDto.setRiskCode(RiskCode);
		swfLogDto.setKeyIn(BusinessNo);
		swfLogDto.setKeyOut("");
		swfLogDto.setDeptName(user.getComName());
		swfLogDto.setSubFlowID("0");
		swfLogDto.setMainFlowID("0");
		swfLogDto.setPosX(0);
		swfLogDto.setPosY(0);
		swfLogDto.setEndFlag(swfNodeDto.getEndFlag());
		// modify by weishixin modify 20050227 start
		// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
		swfLogDto.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		// modify by weishixin modify 20050227 end
		swfLogDto.setPolicyNo(policyNo);
		swfLogDto.setComCode(comCode);
		// 加到Dto中
		workFlowDto.setCreateSwfLogDto(swfLogDto);
		workFlowDto.setCreate(true);
		// 创建工作流的过程 不操作数据，只写数据到workFlowDto中
		return workFlowDto;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	/**
	 * 提交工作流
	 * @param httpServletRequest HttpServletRequest
	 * @param SwfLogDto 当前任务节点
	 * @param KeyIN 输入的业务号码
	 * @param KeyOut 输出的业务号码
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	protected WorkFlowDto getSubmitFlowInfo(UserDto user, SwfLogDto swfLogFunctionInDto, String KeyIn, int logNo, int pathNo) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		// 1。 取得当前用户信息，写操作员信息到Dto中
		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		WorkFlowDto workFlowDto = new WorkFlowDto();
		// 2.取工作流号码
		String flowID = swfLogFunctionInDto.getFlowID();
		/*---3.取工作流的模板号*/
		int modelNo = swfLogFunctionInDto.getModelNo();
		int nodeNo = swfLogFunctionInDto.getNodeNo();
		// 程序上认为回归的时候程序置为标志B,但是判断後，直接把值恢复成"S"
		if (swfLogFunctionInDto.getTaskType().equals("B")) {
			nodeNo = swfLogFunctionInDto.getTaskNo();
			swfLogFunctionInDto.setTaskType("S");
		}
		/*
		 * 4.---------------------查询节点表wfNode的定义----------------------------------
		 * --
		 */

		Collection<SwfNodeDto> wfNodeDtoList = new ArrayList<SwfNodeDto>(); // 模板的节点
		ArrayList<SwfLogDto> wfLogDtoList = new ArrayList<SwfLogDto>(); // 整理後需要新插入的工作流程节点
		ArrayList<SwfPathLogDto> wfPathLogDtoList = new ArrayList<SwfPathLogDto>();// 整理後需要新插入的工作流程边

		// 如果是多任务的节点，只查关系，不查後面的线
		if (swfLogFunctionInDto.getTaskType().equals("M")) {
			int nextNodeNo = swfLogFunctionInDto.getTaskNo();
			SwfNodeDto wfNodeDto = new SwfNodeDto();
			wfNodeDto = uiWorkFlowAction.findModelNodeByPrimaryKey(modelNo, nextNodeNo);
			if (wfNodeDto != null)
				wfNodeDtoList.add(wfNodeDto);

			// 如果M类型派生出的节点继续提交了呢？？这怎办呢？
		}// AddNewNode=T,C是T类型节点的特殊内部表示
		else if (swfLogFunctionInDto.getTaskType().equals("AddNewNode")) {
			wfNodeDtoList = uiWorkFlowAction.findModelNextTNodes(modelNo, nodeNo);
		} else {
			// 查询此节点後的所有的节点信息（正常节点taskType =S 单任务节点）
			// 目前没有确定执行边条件的业务号是什么？？？swfLogFunctionInDto.getConditionBusinessNo(),从节点上传过来
			wfNodeDtoList = uiWorkFlowAction.findModelNextNodes(modelNo, nodeNo, swfLogFunctionInDto.getConditionBusinessNo());
		}
		/* 5.-----------------根据下个工作流节点写工作流程的点和线的数据---------------- */

		if (wfNodeDtoList.iterator().hasNext()) {
			Iterator<SwfNodeDto> it = wfNodeDtoList.iterator();
			// 获取wflog表中flowID的最大的logno的开始值

			while (it.hasNext()) {

				SwfNodeDto wfNodeDto = new SwfNodeDto();
				wfNodeDto = (SwfNodeDto) it.next();
				// 判断有没有传入的指定的typeflag,需要入swflog的。
				if (swfLogFunctionInDto.getTypeFlag().length() > 0)
					wfNodeDto.setTypeFlag(swfLogFunctionInDto.getTypeFlag());

				/*
				 * 6.---------------------写流程节点表wfLog----------------------------
				 * --------
				 */
				SwfLogDto wfLogDtoTemp = new SwfLogDto();
				// 设置wfLogDtoTemp

				// 判断是否在当前的流程中，已经有被定义成该节点的存在，並且状态为0,没有处理，（特殊节点，如果是核损，实赔呢？因为允许多次提交）
				// 如果有，就不用再插入数据库了
				Collection<SwfLogDto> wfLogHasSaveList = new ArrayList<SwfLogDto>();
				// 回访不需要检查是不是有重复的节点
				if (!wfNodeDto.getNodeType().equals("backv") && !wfNodeDto.getNodeType().equals("compp") && !wfNodeDto.getNodeType().equals("veric") && !wfNodeDto.getNodeType().equals("verif")) {
					wfLogHasSaveList = uiWorkFlowAction.findNoDealNodeByModelNodeNo(flowID, wfNodeDto.getNodeNo(), wfNodeDto.getNodeType());
				}

				// add by lixiang start at 2006-6-6
				// 由於强三的加入，需要考虑一下，如果结案是按照险种来结案的，那么需要如何做？
				// 这里初步考虑用riskcode来区分结案，然後用相同的结案来处理
				if (wfNodeDto.getNodeType().equals("endca")) {
					ArrayList<SwfLogDto> wfLogHasSaveListTemp = (ArrayList<SwfLogDto>) wfLogHasSaveList;
					wfLogHasSaveList = new ArrayList<SwfLogDto>();

					// 只要判断已经产生的结案中是有 riskcode=本身的riskCode的数据，则可以产生新的结案
					for (int i = 0; i < wfLogHasSaveListTemp.size(); i++) {
						String riskCode = ((SwfLogDto) wfLogHasSaveListTemp.get(i)).getRiskCode();

						if (swfLogFunctionInDto.getRiskCode().equals(riskCode)) {

							wfLogHasSaveList.add((SwfLogDto) wfLogHasSaveListTemp.get(i));
							break;
						}
					}
				}
				
				if (wfLogHasSaveList.iterator().hasNext()) {

					// 解决多点聚合的时候，产生了大量的多余节点的问题
					wfLogDtoTemp = (SwfLogDto) wfLogHasSaveList.iterator().next();
					// 如果存在这样的数据，则不需要插入wflog，只要在wfPathlog中增加相应的边就可以了
					/*
					 * 7.---------------------写流程线表wfPathLog----------------------
					 * -------
					 */
				} else {
					wfNodeDto.setInsureCarFlag(swfLogFunctionInDto.getInsureCarFlag());
					wfNodeDto.setExigenceGree(swfLogFunctionInDto.getExigenceGree());
					getSwfLogDtoInfoBySwfNode(wfLogDtoTemp, wfNodeDto, user, flowID, modelNo, logNo, "0", KeyIn, swfLogFunctionInDto);
					// 设置默认值
					wfLogDtoTemp.setLossItemCode(swfLogFunctionInDto.getLossItemCode());
					wfLogDtoTemp.setLossItemName(swfLogFunctionInDto.getLossItemName());
					wfLogDtoTemp.setTypeFlag(swfLogFunctionInDto.getTypeFlag());
					// 节点上的附加信息 当前预设置,以後也不做保留的，只用来对下个节点起作用的
					// 默认为本节点数据的附加内容，但是如果外部设置了新的值，则以新的为准

					wfLogDtoList.add(wfLogDtoTemp);
					logNo++;
				}
				/*
				 * 7.---------------------写流程线表wfPathLog--------------------------
				 * -------
				 */

				SwfPathLogDto swfPathLogDto = new SwfPathLogDto();
				getSwfPathLogDtoInfoBySwfLog(swfPathLogDto, swfLogFunctionInDto, wfLogDtoTemp, flowID, modelNo, pathNo);
				wfPathLogDtoList.add(swfPathLogDto);
				pathNo++;
			}
			workFlowDto.setSubmitSwfLogDtoList(wfLogDtoList);
			workFlowDto.setSubmitSwfPathLogDtoList(wfPathLogDtoList);
			workFlowDto.setSubmit(true);
		} else {
			// 如果是单任务节点，但是後面已经没有节点了，查询是不是M的後续节点，如果是（条件是taskNo>0），
			if (swfLogFunctionInDto.getTaskType().equals("S") && swfLogFunctionInDto.getTaskNo() > 1) {
				// 回归主线，为了防止重复递归，设置条件为只可以套一次,並且关系只保留一次,其实不用修改数据中的内容
				// 给再次递归调用本身的时候加个标志位，在次判断的时候，会给置回S
				swfLogFunctionInDto.setTaskType("B");
				swfLogFunctionInDto.setNextBusinessNo(swfLogFunctionInDto.getNextBusinessNo());
				swfLogFunctionInDto.setKeyOut(swfLogFunctionInDto.getKeyOut());
				swfLogFunctionInDto.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				// 回归主线的bussnessno
				workFlowDto = getSubmitFlowInfo(user, swfLogFunctionInDto, KeyIn, logNo, pathNo);
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
	 * @param swfLogFunctionInDto SwfLogDto //本节点的信息
	 * @param swfLogFunctionInOldDto SwfLogDto//函数传如的参数
	 * @param logNo int
	 * @param pathNo int
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	protected WorkFlowDto getSubmitFlowInfo(UserDto user, SwfLogDto swfLogFunctionInDto, SwfLogDto swfLogFunctionInOldDto, int logNo, int pathNo) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		// 1。 取得当前用户信息，写操作员信息到Dto中
		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		WorkFlowDto workFlowDto = new WorkFlowDto();
		// 2.取工作流号码
		String flowID = swfLogFunctionInDto.getFlowID();
		/*---3.取工作流的模板号*/
		int modelNo = swfLogFunctionInDto.getModelNo();
		/*
		 * 4.---------------------查询节点表wfNode的定义----------------------------------
		 * --
		 */

		Collection<SwfNodeDto> wfNodeDtoList = new ArrayList<SwfNodeDto>(); // 模板的节点
		ArrayList<SwfLogDto> wfLogDtoList = new ArrayList<SwfLogDto>(); // 整理後需要新插入的工作流程节点
		ArrayList<SwfPathLogDto> wfPathLogDtoList = new ArrayList<SwfPathLogDto>();// 整理後需要新插入的工作流程边
		Collection<?> swfNodeNextList = new ArrayList<SwfLogDto>();// 存放从界面传过来的需要送的多个节点

		swfNodeNextList = swfLogFunctionInOldDto.getSwfLogList();
		// 查询节点的详细定义
		// 下一个节点定义的内容
		int nextNodeNo = 0;
		String nodeType = "";
		Iterator<?> it = swfNodeNextList.iterator();
		while (it.hasNext()) {
			// swfLogNext 下一个节点从界面传进的定义
			SwfLogDto swfLogNext = (SwfLogDto)it.next();
			nextNodeNo = swfLogNext.getNodeNo();
			nodeType = swfLogNext.getNodeType();
			// 异常判断，如果nextNodeNo=0 说明没找到点,那目前只能是不操作这个下个节点了。以後讨论
			// 目前允许利用nodeType进行传递内容的
			// 在facade只传节点类型进入工作流中就可以的。
			SwfNodeDto swfNodeDto = new SwfNodeDto();
			if ((nextNodeNo == 0) && (!nodeType.equals(""))) {
				swfNodeDto = getFirstNodeTypeNode(modelNo, nodeType);
			} else {
				if (nextNodeNo == 0)
					continue;
				swfNodeDto = uiWorkFlowAction.findModelNodeByPrimaryKey(modelNo, nextNodeNo);
			}
			if (swfNodeDto != null) // 可以正确查询到下一个节点上的信息.
			{
				if (swfLogNext.getHandlerCode().length() > 0) {
					swfNodeDto.setHandlerCode(swfLogNext.getHandlerCode());
					swfNodeDto.setHandlerName(swfLogNext.getHandlerName());
				}
				// 节点上的附加信息 当前预设置,以後也不做保留的，只用来对下个节点起作用的
				// 默认为本节点数据的附加内容，但是如果外部设置了新的值，则以新的为准
				swfNodeDto.setScheduleID(swfLogFunctionInDto.getScheduleID());
				swfNodeDto.setLossItemCode(swfLogFunctionInDto.getLossItemCode());
				swfNodeDto.setLossItemName(swfLogFunctionInDto.getLossItemName());
				swfNodeDto.setInsureCarFlag(swfLogFunctionInDto.getInsureCarFlag());
				swfNodeDto.setTypeFlag(swfLogFunctionInDto.getTypeFlag());
				swfNodeDto.setHandlerRange(swfLogFunctionInDto.getHandlerRange());
				swfNodeDto.setExigenceGree(swfLogFunctionInDto.getExigenceGree());
				swfNodeDto.setHandleDept(swfLogFunctionInDto.getNewHandleDept());
				swfNodeDto.setDeptName(swfLogFunctionInDto.getNewDeptName());

				if (swfLogNext.getScheduleID() > 0)
					swfNodeDto.setScheduleID(swfLogNext.getScheduleID());
				if (!swfLogNext.getLossItemCode().equals(""))
					swfNodeDto.setLossItemCode(swfLogNext.getLossItemCode());
				if (!swfLogNext.getLossItemName().equals(""))
					swfNodeDto.setLossItemName(swfLogNext.getLossItemName());
				if (!swfLogNext.getInsureCarFlag().equals(""))
					swfNodeDto.setInsureCarFlag(swfLogNext.getInsureCarFlag());
				if (!swfLogNext.getTypeFlag().equals(""))
					swfNodeDto.setTypeFlag(swfLogNext.getTypeFlag());
				if (!swfLogNext.getHandlerRange().equals(""))
					swfNodeDto.setHandlerRange(swfLogNext.getHandlerRange());
				if (!swfLogNext.getExigenceGree().equals(""))
					swfNodeDto.setExigenceGree(swfLogNext.getExigenceGree());
				if (!swfLogNext.getNewHandleDept().equals(""))
					swfNodeDto.setHandleDept(swfLogNext.getNewHandleDept());
				if (!swfLogNext.getNewDeptName().equals(""))
					swfNodeDto.setDeptName(swfLogNext.getNewDeptName());
				// add by lixiang start 2006-6-4
				// reason : 由於强三必须支持立案是按照不同的保单来的
				if (swfNodeDto.getNodeType().equals("claim") || swfNodeDto.getNodeType().equals("compe") || swfNodeDto.getNodeType().equals("cance")) {
					if (!swfLogNext.getPolicyNo().equals(""))
						swfNodeDto.setPolicyNo(swfLogNext.getPolicyNo());
					if (!swfLogNext.getRiskCode().equals(""))
						swfNodeDto.setRiskCode(swfLogNext.getRiskCode());
					if (!swfLogNext.getKeyIn().equals(""))
						swfNodeDto.setKeyIn(swfLogNext.getKeyIn());
					if (!swfLogNext.getBusinessNo().equals(""))
						swfNodeDto.setBusinessNo(swfLogNext.getBusinessNo());

				}
				// add by lixiang end 2006-6-4
				wfNodeDtoList.add(swfNodeDto);
			}
		}
		/* 5.-----------------根据下个工作流节点写工作流程的点和线的数据---------------- */

		if (wfNodeDtoList.iterator().hasNext()) {
			Iterator<SwfNodeDto> it1 = wfNodeDtoList.iterator();
			while (it1.hasNext()) {
				SwfNodeDto wfNodeDto = new SwfNodeDto();
				wfNodeDto = (SwfNodeDto) it1.next();

				/*
				 * 6.---------------------写流程节点表wfLog----------------------------
				 * --------
				 */
				SwfLogDto wfLogDtoTemp = new SwfLogDto();
				// 设置wfLogDtoTemp

				// 判断是否在当前的流程中，已经有被定义成该节点的存在，並且状态为0,没有处理，（特殊节点，如果是核损，实赔呢？因为允许多次提交）
				// 如果有，就不用再插入数据库了
				Collection<?> wfLogHasSaveList = new ArrayList<SwfLogDto>();
				// 单证节点不应该有大量的多余节点，是要聚合的。。後来的定损调度也需要合並的
				if (wfNodeDto.getNodeType().equals("certi") || wfNodeDto.getNodeType().equals("compe")) {
					// 由於强三的加入，是否可以考虑，一个保单上的节点是不能重复的。。比如单证，但是理算因为保单不同，允许一个保单一个。
					wfLogHasSaveList = uiWorkFlowAction.findNoDealNodeByModelNodeNoByPerson(flowID, wfNodeDto.getNodeNo(), wfNodeDto.getNodeType(), wfNodeDto.getPolicyNo());
				}
				if (wfLogHasSaveList.iterator().hasNext()) {
					// 解决多点聚合的时候，产生了大量的多余节点的问题
					// 从数据库中查询到已经存在的节点，並把它设置成要去的节点
					wfLogDtoTemp = (SwfLogDto)wfLogHasSaveList.iterator().next();
					// 如果存在这样的数据，则不需要插入wflog，只要在wfPathlog中增加相应的边就可以了
					/*
					 * 7.---------------------写流程线表wfPathLog----------------------
					 * -------
					 */
				} else {
					// 如果当前节点为调度，设置当前节点的附加信息
					// 初始化下一个节点的各项信息
					getSwfLogDtoInfoBySwfNode(wfLogDtoTemp, wfNodeDto, user, flowID, modelNo, logNo, "0", swfLogFunctionInOldDto.getKeyIn(), swfLogFunctionInDto);
					// 删除sched节点上的附加信息

					// 设置wfLogDtoTemp新的节点的操作员

					wfLogDtoList.add(wfLogDtoTemp);
					logNo++;
				}
				/*
				 * 7.---------------------写流程线表wfPathLog--------------------------
				 * -------
				 */
				SwfPathLogDto swfPathLogDto = new SwfPathLogDto();
				getSwfPathLogDtoInfoBySwfLog(swfPathLogDto, swfLogFunctionInDto, wfLogDtoTemp, flowID, modelNo, pathNo);
				wfPathLogDtoList.add(swfPathLogDto);
				pathNo++;
			}
			workFlowDto.setSubmitSwfLogDtoList(wfLogDtoList);
			workFlowDto.setSubmitSwfPathLogDtoList(wfPathLogDtoList);
			workFlowDto.setSubmit(true);
		} else {
			workFlowDto.setStatus("9"); // 设置异常
		}
		return workFlowDto;
	}

	/**
	 * 根据工作流模板上的相同节点定义，写wflog表，工作流程节点的操作处理
	 * @param swfLogDto SwfLogDto
	 * @param swfNodeDto SwfNodeDto
	 * @param user UserDto
	 * @param flowID String
	 * @param modelNo int
	 * @param logNo int
	 * @param nodeStatus String
	 * @param keyIn String
	 * @param swfLogFunctionIn SwfLogDto （当前工作流节点）
	 * @throws Exception
	 * @return SwfLogDto
	 */
	private SwfLogDto getSwfLogDtoInfoBySwfNode(SwfLogDto swfLogDto, SwfNodeDto swfNodeDto, UserDto user, String flowID, int modelNo, int logNo, String nodeStatus, String keyIn, SwfLogDto swfLogFunctionIn) throws Exception {
		swfLogDto.setFlowID(flowID);
		swfLogDto.setLogNo(logNo);
		swfLogDto.setModelNo(modelNo);
		swfLogDto.setNodeNo(swfNodeDto.getNodeNo());
		swfLogDto.setNodeName(swfNodeDto.getNodeName());
		swfLogDto.setBusinessNo(swfLogFunctionIn.getBusinessNo());
		/*--1。如果立案的插入操作，则需要读的是swfLogFunctionIn.的NextBusinessNo*/
		if (swfNodeDto.getNodeType().equals("endca")) {
			swfLogDto.setBusinessNo(swfLogFunctionIn.getNextBusinessNo());
		}
		swfLogDto.setBeforeHandlerCode(user.getUserCode());
		swfLogDto.setBeforeHandlerName(user.getUserName());
		swfLogDto.setTimeLimit(swfNodeDto.getTimeLimit());
		// modify by weishixin modify 20050227 start
		// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
		swfLogDto.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		// modify by weishixin modify 20050227 end
		swfLogDto.setNodeStatus(nodeStatus);
		swfLogDto.setFlowStatus("1");
		swfLogDto.setPackageID("0");
		swfLogDto.setFlag(swfNodeDto.getFlag());
		swfLogDto.setTaskNo(swfNodeDto.getTaskNo());
		swfLogDto.setTaskType(swfNodeDto.getTaskType());
		swfLogDto.setNodeType(swfNodeDto.getNodeType());

		// 待处理的查询条件，增加报案号，被保险人，车牌号(工作流需要添加)
		swfLogDto.setRegistNo(swfLogFunctionIn.getRegistNo());
		swfLogDto.setInsuredName(swfLogFunctionIn.getInsuredName());
		swfLogDto.setLossItemName(swfLogFunctionIn.getLossItemName());
		swfLogDto.setRiskCode(swfLogFunctionIn.getRiskCode());
		swfLogDto.setKeyIn(keyIn);
		/*--等於单证,定损，核损的时候，keyin是registno,其它情况下，等於keyin的值，这个是因为没办法解决keyin不同的，並行环境。*/
		if (swfLogDto.getNodeType().equals("certi") || swfLogDto.getNodeType().equals("certa") || swfLogDto.getNodeType().equals("verip") || swfLogDto.getNodeType().equals("verpo") || swfLogDto.getNodeType().equals("verif")) {
			swfLogDto.setKeyIn(swfLogFunctionIn.getKeyIn());
		}
		/*--等於实赔的情况下，就是claimno,buesssno*/

		if (swfLogDto.getNodeType().equals("compe")) {

			swfLogDto.setKeyIn(swfLogDto.getBusinessNo());
		}
		swfLogDto.setTypeFlag(swfNodeDto.getTypeFlag());
		swfLogDto.setKeyOut("");

		swfLogDto.setSubFlowID("0");
		swfLogDto.setMainFlowID("0");
		swfLogDto.setPosX(0);
		swfLogDto.setPosY(0);
		swfLogDto.setEndFlag(swfNodeDto.getEndFlag());
		// 设置流入时间
		// modify by weishixin modify 20050227 start
		// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
		swfLogDto.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		// modify by weishixin modify 20050227 end
		// 设置节点的名称
		String titleAttr = swfNodeDto.getNodeName() + "節點流入時間：" + swfLogDto.getFlowInTime() + " 上一節點操作人:" + user.getUserName();
		swfLogDto.setTitleStr(titleAttr);

		// 设置保单号码
		swfLogDto.setPolicyNo(swfLogFunctionIn.getPolicyNo());
		// 设置默认节点上的人员
		swfLogDto.setHandlerCode(swfNodeDto.getHandlerCode());
		swfLogDto.setHandlerName(swfNodeDto.getHandlerName());
		swfLogDto.setComCode(swfLogFunctionIn.getComCode());
		// 设置附加数据
		swfLogDto.setScheduleID(swfNodeDto.getScheduleID());
		swfLogDto.setLossItemCode(swfNodeDto.getLossItemCode());
		swfLogDto.setLossItemName(swfNodeDto.getLossItemName());
		swfLogDto.setInsureCarFlag(swfNodeDto.getInsureCarFlag());
		swfLogDto.setHandlerRange(swfNodeDto.getHandlerRange());
		swfLogDto.setExigenceGree(swfNodeDto.getExigenceGree());
		swfLogDto.setHandleDept(swfNodeDto.getHandleDept());
		swfLogDto.setDeptName(swfNodeDto.getDeptName());

		if (swfLogDto.getHandleDept().equals(""))
			swfLogDto.setHandleDept(swfLogFunctionIn.getNewHandleDept());
		if (swfLogDto.getHandleDept().equals(""))
			swfLogDto.setHandleDept(user.getComCode());
		if (swfLogDto.getDeptName().equals(""))
			swfLogDto.setDeptName(swfLogFunctionIn.getNewDeptName());
		if (swfLogDto.getDeptName().equals(""))
			swfLogDto.setDeptName(user.getComName());

		// add by lixiang start at 2006-6-4
		// resason :增加立案中数据的支持

		if (swfLogDto.getNodeType().equals("claim") || swfLogDto.getNodeType().equals("compe") || swfLogDto.getNodeType().equals("cance")) {
			if (!swfNodeDto.getRiskCode().equals(""))
				swfLogDto.setRiskCode(swfNodeDto.getRiskCode());
			if (!swfNodeDto.getPolicyNo().equals(""))
				swfLogDto.setPolicyNo(swfNodeDto.getPolicyNo());
			if (!swfNodeDto.getKeyIn().equals(""))
				swfLogDto.setKeyIn(swfNodeDto.getKeyIn());
			if (!swfNodeDto.getBusinessNo().equals(""))
				swfLogDto.setBusinessNo(swfNodeDto.getBusinessNo());

			// add by lixiang end at 2006-6-4
		}

		return swfLogDto;
	}

	/**
	 * 写wfPathlog表，工作流程线的操作处理
	 * @param swfPathLogDto SwfPathLogDto 新工作流流程路径节点
	 * @param swfLogCurrDto SwfLogDto 当前节点
	 * @param swfLogNextDto SwfLogDto 下一个节点
	 * @param flowID String 工作流流号
	 * @param modelNo int 工作模板号
	 * @param pathNo int 工作流程路径号
	 * @throws Exception
	 * @return SwfPathLogDto
	 */
	protected SwfPathLogDto getSwfPathLogDtoInfoBySwfLog(SwfPathLogDto swfPathLogDto, SwfLogDto swfLogCurrDto, SwfLogDto swfLogNextDto, String flowID, int modelNo, int pathNo) throws Exception {
		String pathName = "";
		swfPathLogDto.setFlowID(flowID);
		swfPathLogDto.setPathNo(pathNo);
		swfPathLogDto.setModelNo(modelNo);
		pathName = "從 " + swfLogCurrDto.getNodeName() + " 到 " + swfLogNextDto.getNodeName();
		swfPathLogDto.setPathName(pathName);
		swfPathLogDto.setStartNodeNo(swfLogCurrDto.getLogNo()); // 只记录序号
		swfPathLogDto.setStartNodeName(swfLogCurrDto.getNodeName());
		swfPathLogDto.setEndNodeNo(swfLogNextDto.getLogNo()); // 这里已经不是模板定义的号码
		swfPathLogDto.setEndNodeName(swfLogNextDto.getNodeName());
		swfPathLogDto.setFlag("");
		return swfPathLogDto;
	}

	/**
	 * 根据当前节点的内容，提交工作流的下面的节点
	 * @param user 当前操作用户
	 * @param swfLogDto 当前节点信息
	 * @param swfLogFunctionIn 工作流传入参数
	 * @param logNo swfLog中的LogNo的最大号
	 * @param pathNo swfPathLog中的PathNo的最大号
	 * @return workFlowDto 整理好的流转数据
	 * @throws Exception
	 */

	private WorkFlowDto submitWorkFlow(UserDto user, SwfLogDto swfLogDto, SwfLogDto swfLogFunctionIn, int logNo, int pathNo) throws Exception {
		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		WorkFlowDto workFlowDto = new WorkFlowDto();
		WorkFlowDto workFlowDtoTemp = new WorkFlowDto();
		// 变更当前节点的状态

		// 暂时保留当前工作节点的信息,作为工作流的提交保存的当前节点内容
		SwfLogDto swfLogDtoTemp = new SwfLogDto();
		swfLogDtoTemp = swfLogDto;
		// 如果有结束标志的点，无论後面有任何的设置，都要立即结束，不用判断下面的节点和关系等等
		if (swfLogDto.getEndFlag().equals("1")) {
			// 结束工作流
			// 首先结束该节点前的失陪节点 ,为了稳妥，先查询此流程的taskType='M'类型的结束吧
			workFlowDto.setUpdate(true);
			swfLogDtoTemp.setHandlerCode(user.getUserCode());
			swfLogDtoTemp.setHandlerName(user.getUserName());
			workFlowDto.setUpdateSwfLogDto(swfLogDtoTemp);

			// add by lixiang start at 2006-6-7
			// reason :由於结案是可能有多个结案的，所以当判断还存在没有关闭的结案，则流程不结束。
			// 等待所有的结案都完毕时，流程才结束。
			if (swfLogDtoTemp.getNodeType().equals("endca")) {
				// 2。查找是否只有一个活动的理算，如果理算超过1个，则不能关闭流程
				String conditonss = "flowId='" + swfLogDtoTemp.getFlowID() + "' and nodeType='compe' and nodestatus<4";
				Collection<?> compeList = uiWorkFlowAction.findNodesByConditions(conditonss);
				// 假设发现还没有结完案子的流程，则不将流程结束，
				// 只是关闭掉目前立案的相关的那个理算，直接返回现有的workFlowDto.
				if (compeList != null && compeList.size() > 1) {
					Iterator<?> it = compeList.iterator();
					while (it.hasNext()) {
						SwfLogDto swfLogDtoCompe = new SwfLogDto();
						swfLogDtoCompe = (SwfLogDto) it.next();
						if (swfLogDtoCompe.getKeyIn().equals(swfLogDtoTemp.getKeyIn())) {
							swfLogDtoCompe.setNodeStatus("4");// 关闭理算
							workFlowDto.setUpdateSwfLog2Dto(swfLogDtoCompe);
							break;
						}
					}

					return workFlowDto;
				}

			}
			// add by lixiang start at 2006-6-7
			workFlowDto.setClose(true);
			SwfFlowMainDto swfFlowMainDto = new SwfFlowMainDto();

			// 如果是第一个节点，现在还没有工作流主表内容呢，所以不需要查询的。
			swfFlowMainDto = uiWorkFlowAction.findFlowMainByPrimaryKey(swfLogDto.getFlowID().trim());
			if (swfFlowMainDto != null) {
				swfFlowMainDto.setCloseDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
				swfFlowMainDto.setFlowStatus("0");
			}
			workFlowDto.setCloseSwfFlowMainDto(swfFlowMainDto);
			return workFlowDto;
		}

		// 一般情况下，NextBusinessNo和BusinessNo都是相同的
		// 除了赔款计算书的节点
		if (!swfLogDto.getNodeType().equals("compp")) {
			swfLogDto.setBusinessNo(swfLogFunctionIn.getNextBusinessNo());
		}
		swfLogDto.setNextBusinessNo(swfLogFunctionIn.getNextBusinessNo());
		// 提交工作流ba
		// 由於有些节点的keyin keyout不是上个节点的流入，所以只得单独加keyin
		// 判断是不是人到人的提交,根据设置的标志nextNodeListType
		if (swfLogFunctionIn.getNextNodeListType().equals("1")) {
			// 支持指定的

			workFlowDtoTemp = getSubmitFlowInfo(user, swfLogDto, swfLogFunctionIn, logNo, pathNo);
		} else { // 支持从模板上取得
			swfLogDto.setNewHandleDept(swfLogFunctionIn.getNewHandleDept());
			workFlowDtoTemp = getSubmitFlowInfo(user, swfLogDto, swfLogFunctionIn.getKeyIn(), logNo, pathNo);
		}

		// 设置处理人员
		swfLogDtoTemp.setHandlerCode(user.getUserCode());
		swfLogDtoTemp.setHandlerName(user.getUserName());
		// 如果有定损的话，设置定损类型
		if (swfLogFunctionIn.getTypeFlag() != null && swfLogFunctionIn.getTypeFlag().length() > 0) {
			swfLogDtoTemp.setTypeFlag(swfLogFunctionIn.getTypeFlag());
		}
		if (workFlowDto.equals("9")) {
			// 设置工作流的这个节点为异常
			swfLogDtoTemp.setNodeStatus("9");
		} else {
			// 正常流转，没有任务异常
			workFlowDto.setUpdate(true);
			// 核损，实赔
			if (workFlowDtoTemp.getUpdate()) // 表示经过提交操作发现以近有後面的节点，只要保存操作即可
			{
				workFlowDto.setUpdateSwfLogDto(workFlowDtoTemp.getUpdateSwfLogDto());
			} else {
				workFlowDto.setUpdateSwfLogDto(swfLogDtoTemp);
				workFlowDto.setSubmit(true);
				workFlowDto.setSubmitSwfLogDtoList(workFlowDtoTemp.getSubmitSwfLogDtoList());
				workFlowDto.setSubmitSwfPathLogDtoList(workFlowDtoTemp.getSubmitSwfPathLogDtoList());
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
	private SwfNodeDto getFirstNodeTypeNode(int modelNo, String nodeType) throws Exception { // 程序思路：
																								// ---------------------------------------------------
																								// 根据模板号码，节点类型查询出swfNodeDto数据
																								// ---------------------------------------------------

		SwfNodeDto swfNodeDto = new SwfNodeDto();
		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		String conditions = "modelNo=" + modelNo + " and nodeType='" + nodeType + "'";
		swfNodeDto = uiWorkFlowAction.findModelFirstNodeByCondition(conditions);
		return swfNodeDto;
	}

	/* ========================（工作流引ad擎正向操作）结束============================ */
	/* ========================第二部分：工作流引擎逆向操作======================== */

	private WorkFlowDto backWorkFlow(UserDto user, String flowID, int logNo, SwfLogDto swfLogFunctionInDto) throws Exception {
		WorkFlowDto workFlowDto = new WorkFlowDto();

		if (swfLogFunctionInDto.getNextNodeListType().equals("1")) {
			if (swfLogFunctionInDto.getSwfLogList() != null && swfLogFunctionInDto.getSwfLogList().size() > 0) {
				// 指定退回节点集合（通用）,lixiang增加一种直接指定flowid,logno序列的退回方式
				// 假设为NextNodeList不为null的\
				workFlowDto = getBackFlowInfoByNextNodeList(user, flowID, logNo, swfLogFunctionInDto);
				// add by liuwei at 2011-03-04 增加退回单证环节判断 start
			} else if ("certi".equals(swfLogFunctionInDto.getNodeType())) {
				workFlowDto = getBackFlowInfoByCompe(user, flowID, logNo, swfLogFunctionInDto);
				// add by liuwei at 2011-03-04 增加退回单证环节判断 end
			} else {
				// 指定退回节点(按当前LogNo逐级递减找到第一个要退回的节点) 核损专用
				workFlowDto = getBackFlowInfoByVerif(user, flowID, logNo, swfLogFunctionInDto);
			}
		} else {
			// 根据工作流日志swflog表进行回退，即退回以当前节点logNo为终点的所有起点logNo对应的节点
			workFlowDto = getBackFlowInfo(user, flowID, logNo);
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
		/*---1。 取得当前用户信息，写操作员信息到Dto中*/
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		WorkFlowDto workFlowDto = new WorkFlowDto();
		/*---2.查询当前节点工作流数据-*/
		SwfLogDto swfLogDto = new SwfLogDto();
		swfLogDto = uiWorkFlowAction.findNodeByPrimaryKey(flowID, logNo);
		/*---3.取工作流的模板号*/
		int modelNo = swfLogDto.getModelNo();
		int nodeNo = swfLogDto.getNodeNo();
		/*
		 * 4.---------------------查询节点表wfNode的定义----------------------------------
		 * --
		 */
		// 查询此节点前的所有的节点信息
		Collection<?> swfNodeDtoList = new ArrayList<SwfNodeDto>();
		swfNodeDtoList = uiWorkFlowAction.findModelPerviousNodes(modelNo, nodeNo);
		// 如果从数据库
		if (swfNodeDtoList != null) {
			// 获取wflog表中flowID的最大的logno的开始值
			int llogNo = uiWorkFlowAction.getSwfLogMaxLogNo(flowID);
			int pathNo = uiWorkFlowAction.getSwfPathLogMaxPathNo(flowID);
			ArrayList<SwfLogDto> swfLogDtoList = new ArrayList<SwfLogDto>();
			ArrayList<SwfPathLogDto> swfPathLogDtoList = new ArrayList<SwfPathLogDto>();
			Iterator<?> it = swfNodeDtoList.iterator();
			while (it.hasNext()) {
				SwfNodeDto swfNodeDto = new SwfNodeDto();
				swfNodeDto = (SwfNodeDto)it.next();
				/*
				 * 6.---------------------写流程节点表wfLog----------------------------
				 * --------
				 */
				SwfLogDto swfLogDtoTemp = new SwfLogDto();
				// 根据定义，和原来的节点内容形成新的节点

				getSwfLogDtoInfoBySwfNode(swfLogDtoTemp, swfNodeDto, user, flowID, modelNo, llogNo, "7", swfLogDto.getBusinessNo(), swfLogDto);
				// 回退操作特有的内容,但是回退操作的那个人是谁呢??
				swfLogDtoTemp.setKeyOut(keyOut);
				swfLogDtoTemp.setHandleDept(swfLogDto.getHandleDept());
				swfLogDtoTemp.setHandlerCode(swfLogDto.getBeforeHandlerCode());
				swfLogDtoTemp.setHandlerName(swfLogDto.getBeforeHandlerName());
				swfLogDtoTemp.setHandleTime(swfLogDto.getHandleTime());

				swfLogDtoList.add(swfLogDtoTemp);
				/*
				 * 7.---------------------写流程线表wfPathLog--------------------------
				 * ----------
				 */
				SwfPathLogDto swfPathLogDto = new SwfPathLogDto();
				getSwfPathLogDtoInfoBySwfLog(swfPathLogDto, swfLogDto, swfLogDtoTemp, flowID, modelNo, pathNo);
				swfPathLogDtoList.add(swfPathLogDto);

				llogNo++;
				pathNo++;
			}
			// 加到Dto中
			workFlowDto.setSubmitSwfLogDtoList(swfLogDtoList);
			workFlowDto.setSubmitSwfPathLogDtoList(swfPathLogDtoList);
			workFlowDto.setSubmit(true);
			swfLogDto.setNodeStatus("5");// 设置为已回退
			workFlowDto.setUpdate(true);
			workFlowDto.setUpdateSwfLogDto(swfLogDto);

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
		/*---1。 取得当前用户信息，写操作员信息到Dto中*/

		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		WorkFlowDto workFlowDto = new WorkFlowDto();
		/*---2.查询当前节点工作流数据-*/
		SwfLogDto swfLogDto = new SwfLogDto();
		swfLogDto = uiWorkFlowAction.findNodeByPrimaryKey(flowID, logNo);

		/*---3.取工作流的模板号*/
		int modelNo = swfLogDto.getModelNo();
		/*
		 * 4.---------------------查询节点表swfPath中所对应的上个节点的定义------------------------
		 * ------------
		 */
		// 查询此节点前的所有的节点信息
		Collection<?> swfLogDtoBackList = new ArrayList<SwfLogDto>();
		swfLogDtoBackList = uiWorkFlowAction.findPerviousNodes(flowID, logNo);
		// 如果从数据库
		if (swfLogDtoBackList != null) {
			// 获取wflog表中flowID的最大的logno的开始值
			int llogNo = uiWorkFlowAction.getSwfLogMaxLogNo(flowID);
			int pathNo = uiWorkFlowAction.getSwfPathLogMaxPathNo(flowID);
			ArrayList<SwfLogDto> swfLogDtoList = new ArrayList<SwfLogDto>();
			ArrayList<SwfPathLogDto> swfPathLogDtoList = new ArrayList<SwfPathLogDto>();
			Iterator<?> it = swfLogDtoBackList.iterator();
			while (it.hasNext()) {
				SwfLogDto swfLogDtoTemp = new SwfLogDto();
				swfLogDtoTemp = (SwfLogDto)it.next();
				/*
				 * 6.---------------------写流程节点表wfLog----------------------------
				 * --------
				 */
				// 根据定义，和原来的节点内容形成新的节点
				swfLogDtoTemp.setLogNo(llogNo);
				// modify by weishixin modify 20050227 start
				// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
				swfLogDtoTemp.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				// modify by weishixin modify 20050227 end
				swfLogDtoTemp.setNodeStatus("3");// 表示退回的
				// 设置节点的名称
				String titleAttr = swfLogDtoTemp.getNodeName() + "節點流入時間：" + swfLogDto.getFlowInTime() + "上一節點操作人:" + user.getUserName();
				swfLogDtoTemp.setTitleStr(titleAttr);
				swfLogDtoTemp.setBeforeHandlerCode(user.getUserCode());
				swfLogDtoTemp.setBeforeHandlerName(user.getUserName());

				swfLogDtoList.add(swfLogDtoTemp);
				// 回退操作特有的内容,但是回退操作的那个人是谁呢??
				/*
				 * 7.---------------------写流程线表wfPathLog--------------------------
				 * ----------
				 */
				SwfPathLogDto swfPathLogDto = new SwfPathLogDto();
				getSwfPathLogDtoInfoBySwfLog(swfPathLogDto, swfLogDto, swfLogDtoTemp, flowID, modelNo, pathNo);
				swfPathLogDtoList.add(swfPathLogDto);

				llogNo++;
				pathNo++;
			}
			// 加到Dto中
			workFlowDto.setSubmitSwfLogDtoList(swfLogDtoList);
			workFlowDto.setSubmitSwfPathLogDtoList(swfPathLogDtoList);
			workFlowDto.setSubmit(true);
			swfLogDto.setNodeStatus("5");// 设置为已回退
			/*
			 * add by zhangyurui 20090211 begin 回退是将核赔操作员保存
			 */
			swfLogDto.setHandlerCode(user.getUserCode());
			swfLogDto.setHandlerName(user.getUserName() + "-回退");
			/*
			 * add by zhangyurui 20090211 end 回退是将核赔操作员保存
			 */
			// modify by weishixin modify 20050227 start
			// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
			swfLogDto.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());// 提交时间
			// modify by weishixin modify 20050227 end

			workFlowDto.setUpdate(true);
			workFlowDto.setUpdateSwfLogDto(swfLogDto);

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
	public WorkFlowDto getBackFlowInfoByNextNodeList(UserDto user, String flowID, int logNo, SwfLogDto swfLogFunctionInDto) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		/*---1。 取得当前用户信息，写操作员信息到Dto中*/

		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		WorkFlowDto workFlowDto = new WorkFlowDto();
		/*---2.查询当前节点工作流数据-*/
		SwfLogDto swfLogDto = new SwfLogDto();
		swfLogDto = uiWorkFlowAction.findNodeByPrimaryKey(flowID, logNo);

		/*---3.取工作流的模板号*/
		int modelNo = swfLogDto.getModelNo();
		/*
		 * 4.---------------------查询节点表swfPath中所对应的上个节点的定义------------------------
		 * ------------
		 */
		// 如果从指定节点开始计算的
		Collection<?> swfNodeNextList = swfLogFunctionInDto.getSwfLogList();
		if (swfNodeNextList != null) {
			// 获取wflog表中flowID的最大的logno的开始值
			int llogNo = uiWorkFlowAction.getSwfLogMaxLogNo(flowID);
			int pathNo = uiWorkFlowAction.getSwfPathLogMaxPathNo(flowID);
			ArrayList<SwfLogDto> swfLogDtoList = new ArrayList<SwfLogDto>();
			ArrayList<SwfPathLogDto> swfPathLogDtoList = new ArrayList<SwfPathLogDto>();
			// 根据指定的节点号进行回退的操作。
			Iterator<?> it = swfNodeNextList.iterator();
			while (it.hasNext()) {
				SwfLogDto swfLogDtoTemp = new SwfLogDto(); // 外部定义
				swfLogDtoTemp = (SwfLogDto)it.next();
				/*
				 * 6.---------------------写流程节点表wfLog----------------------------
				 * --------
				 */
				// 根据定义，和原来的节点内容形成新的节点
				swfLogDtoTemp.setLogNo(llogNo);
				// modify by weishixin modify 20050227 start
				// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
				swfLogDtoTemp.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				swfLogDtoTemp.setHandleTime(swfLogDtoTemp.getFlowInTime());

				swfLogDtoTemp.setSubmitTime("");
				// modify by weishixin modify 20050227 end
				swfLogDtoTemp.setNodeStatus("3");// 表示退回的
				// 设置节点的名称
				String titleAttr = swfLogDtoTemp.getNodeName() + "節點流入時間：" + swfLogDto.getFlowInTime() + " 上一節點操作人:" + user.getUserName();
				swfLogDtoTemp.setTitleStr(titleAttr);
				swfLogDtoTemp.setBeforeHandlerCode(user.getUserCode());
				swfLogDtoTemp.setBeforeHandlerName(user.getUserName());
				// 设置从哪个节点回退的标志
				swfLogDtoTemp.setBusinessType(swfLogDto.getNodeType());// 比如新产生的节点知道是从哪里退回来的
				swfLogDtoList.add(swfLogDtoTemp);
				// 回退操作特有的内容,但是回退操作的那个人是谁呢??
				/*
				 * 7.---------------------写流程线表wfPathLog--------------------------
				 * ----------
				 */
				SwfPathLogDto swfPathLogDto = new SwfPathLogDto();
				getSwfPathLogDtoInfoBySwfLog(swfPathLogDto, swfLogDto, swfLogDtoTemp, flowID, modelNo, pathNo);
				swfPathLogDtoList.add(swfPathLogDto);

				llogNo++;
				pathNo++;
			}
			// 加到Dto中
			workFlowDto.setSubmitSwfLogDtoList(swfLogDtoList);
			workFlowDto.setSubmitSwfPathLogDtoList(swfPathLogDtoList);
			workFlowDto.setSubmit(true);
			swfLogDto.setNodeStatus("5");// 设置为已回退
			// modify by weishixin modify 20050227 start
			// reason:日期改成时分秒後，存入数据也是YEAR_TO_SECOND
			swfLogDto.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());// 提交时间
			// modify by weishixin modify 20050227 end

			workFlowDto.setUpdate(true);
			workFlowDto.setUpdateSwfLogDto(swfLogDto);

		}
		return workFlowDto;
	}

	/**
	 * 回退的工作流(指定退回节点,按当前LogNo逐级递减找到第一个要退回的节点)
	 * @param httpServletRequest HttpServletRequest
	 * @param flowID String 工作流流程编码
	 * @param logNo int 工作流流程顺序号
	 * @param swfLogFunctionInDto SwfLogDto
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	public WorkFlowDto getBackFlowInfoByVerif(UserDto user, String flowID, int logNo, SwfLogDto swfLogFunctionInDto) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		/*---1。 取得当前用户信息，写操作员信息到Dto中*/

		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		WorkFlowDto workFlowDto = new WorkFlowDto();
		/*---2.查询当前节点工作流数据-*/
		SwfLogDto swfLogDto = new SwfLogDto();
		swfLogDto = uiWorkFlowAction.findNodeByPrimaryKey(flowID, logNo);

		/*---3.取工作流的模板号*/
		int modelNo = swfLogDto.getModelNo();

		/*
		 * 4.---------------------查询上一个要回退的节点所对应的swflog--------------------------
		 * ----------
		 */
		// 查询此节点前的所有的节点信息
		SwfLogDto swfLogNextNode = new SwfLogDto();

		// 查询上一个要回退的节点所对应的swflog（按LogNo逐级递减找到第一个要回退的节点）
		for (int i = logNo - 1; i > 0; i--) {
			swfLogNextNode = uiWorkFlowAction.findNodeByPrimaryKey(flowID, i);
			if (swfLogNextNode != null) {
				if (swfLogNextNode.getNodeType().trim().equals(swfLogFunctionInDto.getNodeType()) && swfLogNextNode.getLossItemCode().trim().equals(swfLogFunctionInDto.getLossItemCode().trim())) {
					break;
				}
			}

		}
		if (swfLogNextNode == null) {
			throw new Exception("找不到回退的節點，請聯繫管理員");
		}

		/* 5.---------------------获取wflog表中flowID的最大的logno的开始值----------- */
		int llogNo = uiWorkFlowAction.getSwfLogMaxLogNo(flowID);
		int pathNo = uiWorkFlowAction.getSwfPathLogMaxPathNo(flowID);
		ArrayList<SwfLogDto> swfLogDtoList = new ArrayList<SwfLogDto>();
		ArrayList<SwfPathLogDto> swfPathLogDtoList = new ArrayList<SwfPathLogDto>();

		/*
		 * 6.---------------------写流程节点表wfLog------------------------------------
		 */
		// 根据定义，和原来的节点内容形成新的节点
		swfLogNextNode.setLogNo(llogNo);
		swfLogNextNode.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		swfLogNextNode.setSubmitTime("");
		swfLogNextNode.setHandleTime("");
		swfLogNextNode.setNodeStatus("3");// 表示退回的
		// 设置节点的名称
		String titleAttr = swfLogNextNode.getNodeName() + "節點流入時間：" + swfLogDto.getFlowInTime() + " 上一節點操作人:" + user.getUserName();
		swfLogNextNode.setTitleStr(titleAttr);
		swfLogNextNode.setBeforeHandlerCode(user.getUserCode());
		swfLogNextNode.setBeforeHandlerName(user.getUserName());
		// 定损环节退回的任务写明退回源节点
		if (swfLogFunctionInDto.getBusinessType() != null && !swfLogFunctionInDto.getBusinessType().equals("")) {
			swfLogNextNode.setBusinessType(swfLogFunctionInDto.getBusinessType());
		}

		swfLogDtoList.add(swfLogNextNode);

		/*
		 * 7.---------------------写流程线表wfPathLog----------------------------------
		 * --
		 */
		SwfPathLogDto swfPathLogDto = new SwfPathLogDto();
		getSwfPathLogDtoInfoBySwfLog(swfPathLogDto, swfLogDto, swfLogNextNode, flowID, modelNo, pathNo);
		swfPathLogDtoList.add(swfPathLogDto);

		// 加到Dto中
		workFlowDto.setSubmitSwfLogDtoList(swfLogDtoList);
		workFlowDto.setSubmitSwfPathLogDtoList(swfPathLogDtoList);
		workFlowDto.setSubmit(true);
		swfLogDto.setNodeStatus("5");// 设置为已回退
		swfLogDto.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());// 提交时间

		workFlowDto.setUpdate(true);
		workFlowDto.setUpdateSwfLogDto(swfLogDto);

		return workFlowDto;
	}

	/**
	 * 回退的工作流(指定退回节点,按当前LogNo逐级递减找到第一个要退回的节点)
	 * @param httpServletRequest HttpServletRequest
	 * @param flowID String 工作流流程编码
	 * @param logNo int 工作流流程顺序号
	 * @param swfLogFunctionInDto SwfLogDto
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	public WorkFlowDto getBackFlowInfoByCompe(UserDto user, String flowID, int logNo, SwfLogDto swfLogFunctionInDto) throws Exception {
		// 思路：根据当前节点的信息，查找下面的节点。並形成新的wfLog数据,wfpathLog的数据
		/*---1。 取得当前用户信息，写操作员信息到Dto中*/

		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		WorkFlowDto workFlowDto = new WorkFlowDto();
		/*---2.查询当前节点工作流数据-*/
		SwfLogDto swfLogDto = new SwfLogDto();
		swfLogDto = uiWorkFlowAction.findNodeByPrimaryKey(flowID, logNo);

		/*---3.取工作流的模板号*/
		int modelNo = swfLogDto.getModelNo();

		/* 4.---------------------获取wflog表中flowID的最大的logno的开始值----------- */
		int llogNo = uiWorkFlowAction.getSwfLogMaxLogNo(flowID);
		int pathNo = uiWorkFlowAction.getSwfPathLogMaxPathNo(flowID);

		/*
		 * 5.---------------------查询上一个要回退的节点所对应的swflog--------------------------
		 * ----------
		 */
		// 查询此节点前的所有的节点信息
		SwfLogDto swfLogNextNode = new SwfLogDto();

		// 查询上一个要回退的节点所对应的swflog（按LogNo逐级递减找到第一个要回退的节点）
		boolean flag = false;
		for (int i = llogNo - 1; i > 0; i--) {
			swfLogNextNode = uiWorkFlowAction.findNodeByPrimaryKey(flowID, i);
			if (swfLogNextNode != null) {
				if (swfLogNextNode.getNodeType().trim().equals(swfLogFunctionInDto.getNodeType())) {
					flag = true;
					break;
				}
			}
		}

		if (swfLogNextNode == null) {
			throw new Exception("找不到回退的節點，請聯繫管理員");
		}

		if (!flag) {
			throw new Exception("找不到回退的節點，請聯繫管理員");
		}

		if (!"4".equals(swfLogNextNode.getNodeStatus())) {
			throw new Exception("單證環節還沒有處理，請先進行單證處理");
		}

		ArrayList<SwfLogDto> swfLogDtoList = new ArrayList<SwfLogDto>();
		ArrayList<SwfPathLogDto> swfPathLogDtoList = new ArrayList<SwfPathLogDto>();

		/*
		 * 6.---------------------写流程节点表wfLog------------------------------------
		 */
		// 根据定义，和原来的节点内容形成新的节点
		swfLogNextNode.setLogNo(llogNo);
		swfLogNextNode.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		swfLogNextNode.setSubmitTime("");
		swfLogNextNode.setHandleTime("");
		swfLogNextNode.setNodeStatus("3");// 表示退回的
		// 设置节点的名称
		String titleAttr = swfLogNextNode.getNodeName() + "節點流入時間：" + swfLogDto.getFlowInTime() + " 上一節點操作人:" + user.getUserName();
		swfLogNextNode.setTitleStr(titleAttr);
		swfLogNextNode.setBeforeHandlerCode(user.getUserCode());
		swfLogNextNode.setBeforeHandlerName(user.getUserName());
		// 定损环节退回的任务写明退回源节点
		if (swfLogFunctionInDto.getBusinessType() != null && !swfLogFunctionInDto.getBusinessType().equals("")) {
			swfLogNextNode.setBusinessType(swfLogFunctionInDto.getBusinessType());
		}

		swfLogDtoList.add(swfLogNextNode);

		/*
		 * 7.---------------------写流程线表wfPathLog----------------------------------
		 * --
		 */
		SwfPathLogDto swfPathLogDto = new SwfPathLogDto();
		getSwfPathLogDtoInfoBySwfLog(swfPathLogDto, swfLogDto, swfLogNextNode, flowID, modelNo, pathNo);
		swfPathLogDtoList.add(swfPathLogDto);

		// 加到Dto中
		workFlowDto.setSubmitSwfLogDtoList(swfLogDtoList);
		workFlowDto.setSubmitSwfPathLogDtoList(swfPathLogDtoList);
		workFlowDto.setSubmit(true);
		swfLogDto.setNodeStatus("5");// 设置为已回退
		swfLogDto.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());// 提交时间

		workFlowDto.setUpdate(true);
		workFlowDto.setUpdateSwfLogDto(swfLogDto);

		return workFlowDto;
	}

	public BillService getBillService() {
		if (billService == null) {
			this.billService = (BillService) ServiceFactory.getService("billService");
		}
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	/* ========================（工作流引擎逆向操作）结束============================ */

}
