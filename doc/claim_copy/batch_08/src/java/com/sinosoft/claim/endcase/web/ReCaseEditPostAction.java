package com.sinosoft.claim.endcase.web;

import java.util.ArrayList;
import java.util.List;

import ins.framework.common.DateTime;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.endcase.service.facade.RecaseService;
import com.sinosoft.claim.endcase.util.ReCaseViewHelper;
import com.sinosoft.claim.endcase.vo.ReCaseDto;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfLogStore;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 分发HTTP Post 车险理赔报案编辑界面
 * <p>
 * Title: 车险理赔报案编辑界面信息
 * </p>
 * <p>
 * Description: 车险理赔报案编辑界面信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: sinosoft.com.cn
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class ReCaseEditPostAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/**重开赔案数据收集*/
	private ReCaseViewHelper reCaseViewHelper;
	/**重开赔案服务*/
	private RecaseService recaseService;
	/**重开赔案主表服务*/
	private PrpLrecaseService prpLrecaseService;
	/**工作流数据收集*/
	private WorkFlowViewHelper workFlowViewHelper;
	/**工作流处理服务*/
	private WorkFlowService workFlowService;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private BusinessViewHelper businessViewHelper;
	/**
	 * 重开赔案保存
	 * @return
	 * @throws Exception
	 */
	public String reCaseEditPost() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		String forward = ""; // 向前流转
		/*
		 * * 程序思路：========================================================
		 * (1)查此案件重开赔案表（prplrecase）中有没有记录，没有则把serialNo置为1，有则累加serialNo
		 * (2)增加一条记录到重开赔案表 (3)删除立案表中的结案日期 (4)打开流转轨迹 (5)产生一条待处理的理算任务
		 * ========================================================
		 */
		String claimNo = httpServletRequest.getParameter("ClaimNo");
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		// 从界面整理数据到reCaseDto,增加一条重开赔案记录
		ReCaseDto reCaseDto = reCaseViewHelper.viewToDto(httpServletRequest);
		// 用来操作工作流的传输对象
		WorkFlowDto workFlowDto = new WorkFlowDto();
		if (!WorkFlowDto.isWorkflowswitch()) {
			workFlowDto = this.getWorkFlowDto();
		} else {
			// 1.寻找工作流上的编码
			// //获取flowId
			String flowId = this.getWorkFlowService().findViewFlowIDBybusinessNo(claimNo); // 获取flowId
			// reason :检查工作流是否已经关闭,如关闭，则需要新生成理算任务，若没关闭，则只要将寻找的到结案号的一个结案变成0状态。
			// 考虑漏洞。。重开赔案目前没有任何的的业务操作，则以後会不会有漏洞。。。
			if (flowId.equals("")) {
				throw new Exception("案件未查詢到工作流信息，不能重開賠案！");
			}
			// 2.检查流程现在的状态
			SwfFlowMain swfFlowMainDto = this.getWorkFlowService().findFlowMainByPrimaryKey(flowId);// 将flowstatus置为1
			int maxEndcaseLogNo = 0;
			// 以下部分在转到swflogstore表的数据後，会发现问题，所以需要进行解决
			if ("2".equals(swfFlowMainDto.getStoreFlag())) { // 到store表里面去了。
				maxEndcaseLogNo = this.getWorkFlowService().getSwfLogStoreMaxNodeLogNo(flowId, "endca", claimNo);
			} else {
				maxEndcaseLogNo = this.getWorkFlowService().getSwfLogMaxNodeLogNo(flowId, "endca", claimNo);
			}
			// 生成一条待处理理算任务
			SwfLog swfLogjaDto = new SwfLog();
			swfLogjaDto = this.getWorkFlowService().findNodeByPrimaryKey(flowId, maxEndcaseLogNo); // swfLogjaDto为结案结点的swflogDto
			if (swfLogjaDto == null) {
				SwfLogStore tempSwfLogStore = this.getWorkFlowService().findSwfLogStoreDtoByPrimaryKey(flowId, maxEndcaseLogNo);
				if (tempSwfLogStore != null) {
					swfLogjaDto = tempSwfLogStore.toSwfLog();
				}
			}
			if (swfLogjaDto == null) {
				throw new UserException(1, 10, "重開賠案", "案件未查詢到結案的工作流訊息，不能重開賠案！");
			}
			// 检查是否现在这个立案已经重开过赔案了，不能再重新开赔案了
			String compeStr = "flowid='" + flowId + "' and businessno='" + claimNo + "' and nodeType='compe' and flowstatus>0 and nodestatus<4";
			int compeCount = this.getWorkFlowService().findFlowNodeCountByConditon(compeStr);
			if (compeCount > 0) {
				throw new UserException(1, 10, "重開賠案", "此案件的立案已經重開過賠案並未處理完畢，請不要再重開賠案！");
			}
			PrpLrecase prpLrecase = reCaseDto.getPrpLrecase();
			String businessId = prpLrecase.getId().getClaimNo() + "_" + prpLrecase.getId().getSerialNo();
			workFlowDto = this.jbpmBusinessViewHelper.getJbpmWorkFlowReCase(user, swfLogjaDto, claimNo, claimNo);
			workFlowDto.getJbpmDto().setBusinessId(businessId);
		}
		recaseService.save(reCaseDto, workFlowDto);
//		this.jbpmBusinessViewHelper.saveWorkFlow(this.recaseService, "save", workFlowDto, reCaseDto);
		this.clearErrorsAndMessages();
		this.addActionMessage("申請重開賠案成功");
		// 默认不需要自动跳转
		forward = "success";
		return forward;
	}
	
	/***
	 * 旧工作流处理重开赔案任务
	 * @return
	 * @throws Exception
	 */
//	private WorkFlowDto getWorkFlowDto() throws Exception {
//		HttpServletRequest request = super.getRequest();
//		String claimNo = request.getParameter("ClaimNo");
//		String PolicyNo = request.getParameter("PolicyNo");
//		String riskCode = request.getParameter("riskCode");
//		UserDto user = (UserDto) request.getSession().getAttribute("user");
//		// 从界面整理数据到reCaseDto,增加一条重开赔案记录
//		// 用来操作工作流的传输对象
//		WorkFlowDto workFlowDto = new WorkFlowDto();
//		// 1.寻找工作流上的编码
//		// //获取flowId
//		String flowId = this.getWorkFlowService().findViewFlowIDBybusinessNo(claimNo); // 获取flowId
//		// reason :检查工作流是否已经关闭,如关闭，则需要新生成理算任务，若没关闭，则只要将寻找的到结案号的一个结案变成0状态。
//		// 考虑漏洞。。重开赔案目前没有任何的的业务操作，则以後会不会有漏洞。。。
//		if (flowId.equals("")) {
//			throw new Exception("案件未查詢到工作流信息，不能重開賠案！");
//		}
//		// 2.检查流程现在的状态
//		SwfFlowMain swfFlowMainDto = this.getWorkFlowService().findFlowMainByPrimaryKey(flowId);// 将flowstatus置为1
//		int maxLogNo = 0;
//		int maxEndcaseLogNo = 0;
//		int maxPathNo = 0;
//		// 以下部分在转到swflogstore表的数据後，会发现问题，所以需要进行解决
//		if ("2".equals(swfFlowMainDto.getStoreFlag())) { // 到store表里面去了。
//			// 说明被转出去了。
//			maxLogNo = this.getWorkFlowService().getSwfLogStoreMaxLogNo(flowId); // db层maxLogNo是取的最大LogNo+1
//			maxEndcaseLogNo = this.getWorkFlowService().getSwfLogStoreMaxNodeLogNo(flowId, "endca", claimNo);
//			maxPathNo = this.getWorkFlowService().getSwfPathLogStoreMaxPathNo(flowId);
//
//		} else {
//			maxLogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowId); // db层maxLogNo是取的最大LogNo+1
//			maxEndcaseLogNo = this.getWorkFlowService().getSwfLogMaxNodeLogNo(flowId, "endca", claimNo);
//			maxPathNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowId);
//		}
//		// 生成一条待处理理算任务
//		SwfLog swfLogjaDto = new SwfLog();
//		swfLogjaDto = this.getWorkFlowService().findNodeByPrimaryKey(flowId, maxEndcaseLogNo); // swfLogjaDto为结案结点的swflogDto
//		if (swfLogjaDto == null) {
//			SwfLogStore tempSwfLogStore = this.getWorkFlowService().findSwfLogStoreDtoByPrimaryKey(flowId, maxEndcaseLogNo);
//			if (tempSwfLogStore != null) {
//				swfLogjaDto = tempSwfLogStore.toSwfLog();
//			}
//		}
//		int modelNo = 0; // 默认
//		SwfLog swfLoglsDto = new SwfLog(); // swfLoglsDto为生成新理算节点的swfLogDto
//		List<SwfLog> swfLogList = new ArrayList<SwfLog>(); // 保存新增加的理算节点
//		List<SwfPathLog> swfPathLogList = new ArrayList<SwfPathLog>(); // 保存新增加的结案到理算的线信息
//		if (swfLogjaDto == null) {
//			throw new UserException(1, 10, "重開賠案", "案件未查詢到結案的工作流訊息，不能重開賠案！");
//		}
//		// 检查是否现在这个立案已经重开过赔案了，不能再重新开赔案了
//		String compeStr = "flowid='" + flowId + "' and businessno='" + claimNo + "' and nodeType='compe' and flowstatus>0 and nodestatus<4";
//		int compeCount = this.getWorkFlowService().findFlowNodeCountByConditon(compeStr);
//		if (compeCount > 0) {
//			throw new UserException(1, 10, "重開賠案", "此案件的立案已經重開過賠案並未處理完畢，請不要再重開賠案！");
//		}
//		// 3.关闭的流程的处理
//		if (swfLogjaDto != null && swfFlowMainDto.getFlowStatus().equals("0")) {
//			// 3.1开启工作流主表数据，打开工作流的节点所有数据
//			swfFlowMainDto.setFlowStatus("1");
//			workFlowDto.setReOpen(true);
//			workFlowDto.setReOpenSwfFlowMain(swfFlowMainDto);
//		}
//		// 4.形成新的理赔节点数据
//		modelNo = swfLogjaDto.getModelNo();
//		SwfNode swfNodeDto = this.getWorkFlowService().findModelNodeByNodeType(modelNo, "compe");
//		int nodeNo = swfNodeDto.getId().getNodeNo();
//		String nodeName = swfNodeDto.getNodeName();
//		if (nodeName == null || nodeName.trim().equals(""))
//			nodeName = "nodename";
//		String beforeHandlerCode = swfLogjaDto.getHandlerCode();
//		String beforeHandleName = swfLogjaDto.getHandlerName();
//		swfLoglsDto.setBeforeHandlerCode(beforeHandlerCode);
//		swfLoglsDto.setBeforeHandlerName(beforeHandleName);
//		swfLoglsDto.setBusinessNo(claimNo);
//		// 对於车险谁结案，谁处理 非车险谁重开，谁处理
//		if (!"D".equals(ConstantCodes.carClassMap.get(riskCode))) {
//			swfLoglsDto.setComCode(user.getComCode());
//		} else {
//			swfLoglsDto.setComCode(swfLogjaDto.getComCode());
//		}
//		swfLoglsDto.getId().setFlowID(flowId);
//		swfLoglsDto.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
//		swfLoglsDto.setFlowStatus("1");
//		swfLoglsDto.setKeyIn(claimNo);
//		swfLoglsDto.getId().setLogNo(maxLogNo);
//		swfLoglsDto.setNodeName(nodeName);
//		swfLoglsDto.setNodeNo(nodeNo);
//		swfLoglsDto.setNodeStatus("0");
//		swfLoglsDto.setNodeType("compe");
//		swfLoglsDto.setPackageID("0");
//		swfLoglsDto.setMainFlowID("0");
//		swfLoglsDto.setSubFlowID("0");
//		swfLoglsDto.setRegistNo(swfLogjaDto.getRegistNo());
//		swfLoglsDto.setInsuredName(swfLogjaDto.getInsuredName());
//		swfLoglsDto.setPolicyNo(PolicyNo);
//		swfLoglsDto.setRiskCode(riskCode);
//		swfLoglsDto.setModelNo(modelNo);
//		if (!"D".equals(ConstantCodes.carClassMap.get(riskCode))) {
//			swfLoglsDto.setHandleDept(user.getComCode());
//		} else {
//			swfLoglsDto.setHandleDept(swfLogjaDto.getHandleDept());
//		}
//		swfLoglsDto.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
//		swfLoglsDto.setTaskNo(swfNodeDto.getTaskNo()); // 看文档
//		swfLoglsDto.setTaskType(swfNodeDto.getTaskType()); // 看文档
//		swfLoglsDto.setTitleStr("重開賠案"); // 看文档
//		swfLoglsDto.setDeptName(user.getComName());
//		swfLoglsDto.setPosX(0); // 看文档
//		swfLoglsDto.setPosY(0); // 看文档
//		swfLoglsDto.setTypeFlag(swfNodeDto.getTypeFlag());
//		if ("D".equals(ConstantCodes.carClassMap.get(riskCode))) {
//			swfLoglsDto.setLossItemCode(swfLogjaDto.getLossItemCode());
//			swfLoglsDto.setLossItemName(swfLogjaDto.getLossItemName());
//		}
//		swfLoglsDto.setEndFlag(swfNodeDto.getEndFlag()); // 看文档
//		swfLogList.add(swfLoglsDto);
//		// 5.形成新的理赔线数据
//		String pathName = "從 結案 到 " + nodeName;
//		SwfPathLog swfPathLogDtoTemp = new SwfPathLog();
//		swfPathLogDtoTemp.getId().setPathNo(maxPathNo);
//		swfPathLogDtoTemp.getId().setFlowID(flowId);
//		swfPathLogDtoTemp.setStartNodeNo(maxEndcaseLogNo);
//		swfPathLogDtoTemp.setStartNodeName("結案");
//		swfPathLogDtoTemp.setEndNodeNo(maxLogNo);
//		swfPathLogDtoTemp.setEndNodeName(nodeName);
//		swfPathLogDtoTemp.setModelNo(modelNo);
//		swfPathLogDtoTemp.setPathName(pathName);
//		swfPathLogList.add(swfPathLogDtoTemp);
//		// 6.设置新增加方式，做为後台数据保存的来源。
//		workFlowDto.setSubmit(true);
//		workFlowDto.setSubmitSwfPathLogList(swfPathLogList);
//		workFlowDto.setSubmitSwfLogList(swfLogList);
//		return workFlowDto;
//	}
	private WorkFlowDto getWorkFlowDto() throws Exception {
        HttpServletRequest request = super.getRequest();
        String claimNo = request.getParameter("ClaimNo");
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        String flowID = this.getWorkFlowService().findViewFlowIDBybusinessNo(claimNo); // 获取flowId
        if (CommonUtils.isEmpty(flowID)) {
            throw new UserException(-1, 0, "重開賠案", "未找到本案相關工作流資料！");
        }
        // 2.检查流程现在的状态
        SwfFlowMain swfFlowMainDto = this.getWorkFlowService().findFlowMainByPrimaryKey(flowID);// 将flowstatus置为1
        int maxEndcaseLogNo = 0;
        // 以下部分在转到swflogstore表的数据後，会发现问题，所以需要进行解决
        SwfLog endcaSwfLog = null;
        if ("2".equals(swfFlowMainDto.getStoreFlag())) { // 到store表里面去了。
            maxEndcaseLogNo = this.getWorkFlowService().getSwfLogStoreMaxNodeLogNo(flowID, "endca", claimNo);
            SwfLogStore tempSwfLogStore = this.getWorkFlowService().findSwfLogStoreDtoByPrimaryKey(flowID, maxEndcaseLogNo);
            if (tempSwfLogStore != null) {
                endcaSwfLog = tempSwfLogStore.toSwfLog();
            }
        } else {
            maxEndcaseLogNo = this.getWorkFlowService().getSwfLogMaxNodeLogNo(flowID, "endca", claimNo);
            endcaSwfLog = this.getWorkFlowService().findNodeByPrimaryKey(flowID, maxEndcaseLogNo);
        }
        if (endcaSwfLog == null) {
            throw new UserException(1, 10, "重開賠案", "案件未查詢到結案的工作流訊息，不能重開賠案！");
        }
        // 检查是否现在这个立案已经重开过赔案了，不能再重新开赔案了
        String compeStr = "flowid='" + flowID + "' and businessno='" + claimNo + "' and nodeType='compe' and flowstatus > 0 and nodestatus<4";
        int compeCount = this.getWorkFlowService().findFlowNodeCountByConditon(compeStr);
        if (compeCount > 0) {
            throw new UserException(1, 10, "重開賠案", "此案件的立案已經重開過賠案並未處理完畢，請不要再重開賠案！");
        }
        return this.businessViewHelper.getWorkFlowReCase(user, endcaSwfLog, claimNo, claimNo);
    }

	public ReCaseViewHelper getReCaseViewHelper() {
		return reCaseViewHelper;
	}

	public void setReCaseViewHelper(ReCaseViewHelper reCaseViewHelper) {
		this.reCaseViewHelper = reCaseViewHelper;
	}

	public RecaseService getRecaseService() {
		return recaseService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public void setRecaseService(RecaseService recaseService) {
		this.recaseService = recaseService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}

    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }

    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }
	
}
