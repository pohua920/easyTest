/*
 * @(#)WorkFlowDto.java	Mar 16, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.workflow.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.model.SwfPackage;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 工作流流程数据传输对象
 * <p>
 * Title: 工作流流程DTO
 * </p>
 * <p>
 * Description: 车险理赔理赔节点 工作流部分
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class WorkFlowDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 工作流流程节点信息 */
	private List<SwfLog> swfLogList = new ArrayList<SwfLog>(0);

	/** 工作流流程边信息 */
	private List<SwfPathLog> swfPathLogList = new ArrayList<SwfPathLog>(0);

	/** 工作流日志业务信息 */
	private List<SwfPackage> swfPackageList = new ArrayList<SwfPackage>(0);

	/** 工作流流程主表信息 */
	private SwfFlowMain swfFlowMain;

	/** 工作流当前处理节点的业务编码信息 */
	private String bessinessNo = "";

	/** 工作流当前状态 0 正常， 9异常 只做运算时候用的。 */
	private String status = "0";

	/** 工作流创建操作 */
	private boolean create = false;

	/** 创建时，创建节点的工作流流程节点信息 */
	private SwfLog createSwfLog;

	/** 创建时，工作流流程主表信息 */
	private SwfFlowMain createSwfFlowMain;

	/** 工作流更新操作 */
	private boolean update = false;

	/** 更新时，更新节点的工作流流程节点信息 */
	private SwfLog updateSwfLog;

	/** 更新时，批量更新节点的信息 */
	private List<SwfLog> updateSwfLogList = new ArrayList<SwfLog>(0);

	/** 工作流提交操作 */
	private boolean submit = false;

	/** 提交时，新增加节点的工作流流程节点信息 */
	private List<SwfLog> submitSwfLogList = new ArrayList<SwfLog>(0);

	/** 提交时，新增加节点路径的工作流流程节点路径信息 */
	private List<SwfPathLog> submitSwfPathLogList = new ArrayList<SwfPathLog>(0);

	/** 节点的办理信息 */
	private List<SwfNotion> swfNotionList = new ArrayList<SwfNotion>(0);

	/** 工作流关闭操作 */
	private boolean close = false;

	/** 关闭时，工作流流程主表信息 */
	private SwfFlowMain closeSwfFlowMain;

	/** 数据整理的结果 */
	private int operateResult = 0;

	/** 工作流重开操作 */
	private boolean reOpen = false;

	/** 重开时，工作流流程主表信息 */
	private SwfFlowMain reOpenSwfFlowMain;

	/** 工作流是否已经被关闭操作 */
	private boolean checkClose = false;

	/** 是否是需要进行占用节点操作 */
	boolean freeHoldNode = false;

	/** 工作流回收操作 */
	boolean recycle = false;

	/** 更新时，可以更新节点的另外一个，就是更新的时候，可以更新两个节点。工作流流程节点信息 */
	private SwfLog updateSwfLog2;

	/** 工作流删除操作 */
	private boolean delete = false;

	/** 提交时，新增加节点的工作流流程节点信息 */
	private List<SwfLog> deleteSwfLogList = new ArrayList<SwfLog>(0);

	/** 提交时，新增加节点路径的工作流流程节点路径信息 */
	private List<SwfPathLog> deleteSwfPathLogList = new ArrayList<SwfPathLog>(0);

	/** 工作流主表单独更新操作 */
	private boolean updateMainFlow = false;

	private JbpmDto jbpmDto;
	/** 工作流流转参数 */
	private Map<String, Object> paramMap = new HashMap<String, Object>();
	private Map<String, List<Object>> paramCertainLossNodeMap = new HashMap<String, List<Object>>(3);
	private Map<String, Object> flowParamMap = new HashMap<String, Object>();
	/** 当前处理工作流任务 */
	private SwfLog currSwfLog;
	/** 根据actorId判断当前工作流任务调用新旧工作流引擎处理 */
	private boolean isNewWorkFlow = false;
	/** 工作流开启开关 */
	private static boolean workFlowSwitch = false;
	/** 是否從配置讀取工作流開啟狀態配置 */
	private static boolean reloadSwitch = true;
	/** 工作流回退 */
	private boolean back = false;
	/** 回退到的节点 */
	private List<SwfLog> backSwfLogList = new ArrayList<SwfLog>(0);
	/** 注销拒赔 */
	private boolean claimCancel = false;
	/** 自动结案 */
	private boolean autoClose = false;
	/** 下環節節點 */
	private String nextActorId = "";

	private int maxLogNo = 0;
	private int maxPathLogNo = 0;
	/**   */
	private boolean cancel = false;

	public WorkFlowDto() {
	}

	/**
	 * 数据整理的结果
	 * @return 工作流流程主表信息
	 */
	public int getOperateResult() {
		return operateResult;
	}

	/**
	 * 设置工作流流程主表信息
	 * @param prpLscheduleMainWFDto 工作流流程主表信息
	 */
	public void setOperateResult(int operateResult) {
		this.operateResult = operateResult;
	}

	/**
	 * 得到工作流流程主表信息
	 * @return 工作流流程主表信息
	 */
	public SwfFlowMain getSwfFlowMain() {
		return swfFlowMain;
	}

	/**
	 * 设置工作流流程主表信息
	 * @param prpLscheduleMainWFDto 工作流流程主表信息
	 */
	public void setSwfFlowMain(SwfFlowMain swfFlowMain) {
		this.swfFlowMain = swfFlowMain;
	}

	/**
	 * 得到工作流流程节点信息
	 * @return 工作流流程节点信息
	 */
	public List<SwfLog> getSwfLogList() {
		return swfLogList;
	}

	/**
	 * 设置工作流流程节点信息
	 * @param PrpLscheduleItem工作流流程节点信息
	 */
	public void setSwfLogList(List<SwfLog> swfLogList) {
		this.swfLogList = swfLogList;
	}

	/**
	 * 得到工作流流程边信息
	 * @return 工作流流程边信息
	 */
	public List<SwfPathLog> getSwfPathLogList() {
		return swfPathLogList;
	}

	/**
	 * 设置工作流流程边信息
	 * @param prpLclaimStuats 工作流流程边信息
	 */
	public void setSwfPathLogList(List<SwfPathLog> swfPathLogList) {
		this.swfPathLogList = swfPathLogList;
	}

	/**
	 * 得到工作流日志业务信息
	 * @return 工作流日志业务信息
	 */
	public List<SwfPackage> getSwfPackageList() {
		return swfPackageList;
	}

	/**
	 * 设置工作流日志业务信息
	 * @param PrpLthirdPartyList 工作流日志业务表信息
	 */
	public void setSwfPackageList(List<SwfPackage> swfPackageList) {
		this.swfPackageList = swfPackageList;
	}

	/**
	 * 得到工作流当前处理节点的业务编码信息
	 * @return 工作流当前处理节点的业务编码信息
	 */
	public String getBessinessNo() {
		return bessinessNo;
	}

	/**
	 * 设置工作流当前处理节点的业务编码信息
	 * @param prpLscheduleMainWF 工作流当前处理节点的业务编码信息
	 */
	public void setBessinessNo(String bessinessNo) {
		this.bessinessNo = bessinessNo;
	}

	/**
	 * 得到工作流当前状态
	 * @return 工作流当前状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置工作流当前状态
	 * @param status 工作流当前状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 得到工作流创建操作
	 * @return 工作流创建操作
	 */
	public boolean getCreate() {
		return create;
	}

	/**
	 * 设置工作流创建操作
	 * @param boolean 工作流创建操作
	 */
	public void setCreate(boolean create) {
		this.create = create;
	}

	/**
	 * 得到工作流更新操作
	 * @return 工作流更新操作
	 */
	public boolean getUpdate() {
		return update;
	}

	/**
	 * 设置工作流更新操作
	 * @param boolean 工作流更新操作
	 */
	public void setUpdate(boolean update) {
		this.update = update;
	}

	/**
	 * 得到工作流提交操作
	 * @return 工作流提交操作
	 */
	public boolean getSubmit() {
		return submit;
	}

	/**
	 * 设置工作流提交操作
	 * @param boolean 工作流提交操作
	 */
	public void setSubmit(boolean submit) {
		this.submit = submit;
	}

	/**
	 * 得到工作流关闭操作
	 * @return 工作流关闭操作
	 */
	public boolean getClose() {
		return close;
	}

	/**
	 * 设置工作流关闭操作
	 * @param boolean 工作流关闭操作
	 */
	public void setClose(boolean close) {
		this.close = close;
	}

	/**
	 * 得到提交时，新增加节点的工作流流程节点信息
	 * @return 工作流流程节点信息
	 */
	public List<SwfLog> getSubmitSwfLogList() {
		return submitSwfLogList;
	}

	/**
	 * 设置提交时，新增加节点的工作流流程节点信息
	 * @param PrpLscheduleItem工作流流程节点信息
	 */
	public void setSubmitSwfLogList(List<SwfLog> submitSwfLogList) {
		this.submitSwfLogList = submitSwfLogList;
	}

	/**
	 * 得到更新时，更新节点的工作流流程节点信息
	 * @return 工作流流程节点信息
	 */
	public SwfLog getUpdateSwfLog() {
		return updateSwfLog;
	}

	/**
	 * 设置更新时，更新节点的工作流流程节点信息
	 * @param 工作流流程节点信息
	 */
	public void setUpdateSwfLog(SwfLog updateSwfLog) {
		this.updateSwfLog = updateSwfLog;
	}

	/**
	 * 得到创建时，创建节点的工作流流程节点信息
	 * @return 工作流流程节点信息
	 */
	public SwfLog getCreateSwfLog() {
		return createSwfLog;
	}

	/**
	 * 设置创建时，创建节点的工作流流程节点信息
	 * @param 工作流流程节点信息
	 */
	public void setCreateSwfLog(SwfLog createSwfLog) {
		this.createSwfLog = createSwfLog;
	}

	/**
	 * 得到提交时，新增加节点路径的工作流流程节点路径信息
	 * @return 工作流流程节点路径信息
	 */
	public List<SwfPathLog> getSubmitSwfPathLogList() {
		return submitSwfPathLogList;
	}

	/**
	 * 设置提交时，新增加节点路径的工作流流程节点路径信息
	 * @param PrpLscheduleItem工作流流程节点路径信息
	 */
	public void setSubmitSwfPathLogList(List<SwfPathLog> submitSwfPathLogList) {
		this.submitSwfPathLogList = submitSwfPathLogList;
	}

	/**
	 * 得到更新时，批量更新的工作流流程节点信息
	 * @return 工作流流程节点信息
	 */
	public List<SwfLog> getUpdateSwfLogList() {
		return updateSwfLogList;
	}

	/**
	 * 设置更新时，批量更新的工作流流程节点信息
	 * @param PrpLscheduleItem工作流流程节点信息
	 */
	public void setUpdateSwfLogList(List<SwfLog> updateSwfLogList) {
		this.updateSwfLogList = updateSwfLogList;
	}

	/**
	 * 得到节点批办信息节点信息
	 * @return 工作流流程节点信息
	 */
	public List<SwfNotion> getSwfNotionList() {
		return swfNotionList;
	}

	/**
	 * 设置节点批办信息节点信息
	 * @param PrpLscheduleItem工作流流程节点信息
	 */
	public void setSwfNotionList(List<SwfNotion> swfNotionList) {
		this.swfNotionList = swfNotionList;
	}

	/**
	 * 得到创建工作流流程主表信息
	 * @return 创建工作流流程主表信息
	 */
	public SwfFlowMain getCreateSwfFlowMain() {
		return createSwfFlowMain;
	}

	/**
	 * 设置创建工作流流程主表信息
	 * @param prpLscheduleMainWF 创建工作流流程主表信息
	 */
	public void setCreateSwfFlowMain(SwfFlowMain createSwfFlowMain) {
		this.createSwfFlowMain = createSwfFlowMain;
	}

	/**
	 * 得到关闭工作流流程主表信息
	 * @return 关闭工作流流程主表信息
	 */
	public SwfFlowMain getCloseSwfFlowMain() {
		return closeSwfFlowMain;
	}

	/**
	 * 设置关闭工作流流程主表信息
	 * @param prpLscheduleMainWF 关闭工作流流程主表信息
	 */
	public void setCloseSwfFlowMain(SwfFlowMain closeSwfFlowMain) {
		this.closeSwfFlowMain = closeSwfFlowMain;
	}

	/**
	 * 得到工作流是否已经被关闭操作
	 * @return 工作流是否已经被关闭操作
	 */
	public boolean getCheckClose() {
		return checkClose;
	}

	/**
	 * 设置工作流是否已经被关闭操作
	 * @param boolean 工作流是否已经被关闭操作
	 */
	public void setCheckClose(boolean checkClose) {
		this.checkClose = checkClose;
	}

	/**
	 * 是否是正在独自占用该节点
	 * @return 正在独自占用该节点
	 */
	public boolean getFreeHoldNode() {
		return this.freeHoldNode;
	}

	/**
	 * 设置正在独自占用该节点
	 * @param 正在独自占用该节点
	 */
	public void setFreeHoldNode(boolean freeHoldNode) {
		this.freeHoldNode = freeHoldNode;
	}

	public boolean getRecycle() {
		return recycle;
	}

	public void setRecycle(boolean recycle) {
		this.recycle = recycle;
	}

	// reasion:重开
	public void setReOpen(boolean reOpen) {
		this.reOpen = reOpen;
	}

	public boolean getReOpen() {
		return reOpen;
	}

	/**
	 * 得到重新打开工作流流程主表信息
	 * @return 重新打开工作流流程主表信息
	 */
	public SwfFlowMain getReOpenSwfFlowMain() {
		return reOpenSwfFlowMain;
	}

	/**
	 * 设置重新打开工作流流程主表信息
	 * @param prpLscheduleMainWF 重新打开工作流流程主表信息
	 */
	public void setReOpenSwfFlowMain(SwfFlowMain reOpenSwfFlowMain) {
		this.reOpenSwfFlowMain = reOpenSwfFlowMain;
	}

	/**
	 * 得到更新时，2更新节点的工作流流程节点信息
	 * @return 工作流流程节点信息
	 */
	public SwfLog getUpdateSwfLog2() {
		return updateSwfLog2;
	}

	/**
	 * 设置更新时，2更新节点的工作流流程节点信息
	 * @param 工作流流程节点信息
	 */
	public void setUpdateSwfLog2(SwfLog updateSwfLog2) {
		this.updateSwfLog2 = updateSwfLog2;
	}

	/**
	 * 得到工作流删除操作
	 * @return 工作流删除操作
	 */
	public boolean getDelete() {
		return delete;
	}

	/**
	 * 设置工作流删除操作
	 * @param boolean 工作流删除操作
	 */
	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	/**
	 * 得到删除时，节点的工作流流程节点信息
	 * @return 工作流流程节点信息
	 */
	public List<SwfLog> getDeleteSwfLogList() {
		return deleteSwfLogList;
	}

	/**
	 * 设置删除时，节点的工作流流程节点信息
	 * @param PrpLscheduleItem工作流流程节点信息
	 */
	public void setDeleteSwfLogList(List<SwfLog> deleteSwfLogList) {
		this.deleteSwfLogList = deleteSwfLogList;
	}

	/**
	 * 得到删除时，节点路径的工作流流程节点路径信息
	 * @return 工作流流程节点路径信息
	 */
	public List<SwfPathLog> getDeleteSwfPathLogList() {
		return deleteSwfPathLogList;
	}

	/**
	 * 设置删除时，节点路径的工作流流程节点路径信息
	 * @param PrpLscheduleItem工作流流程节点路径信息
	 */
	public void setDeleteSwfPathLogList(List<SwfPathLog> deleteSwfPathLogList) {
		this.deleteSwfPathLogList = deleteSwfPathLogList;
	}

	/**
	 * 得到工作流更新主表操作
	 * @return 工作流更新主表操作
	 */
	public boolean getUpdateMainFlow() {
		return updateMainFlow;
	}

	/**
	 * 设置工作流更新主表操作
	 * @param boolean 工作流更新主表操作
	 */
	public void setUpdateMainFlow(boolean updateMainFlow) {
		this.updateMainFlow = updateMainFlow;
	}

	public JbpmDto getJbpmDto() {
		return jbpmDto;
	}

	public void setJbpmDto(JbpmDto jbpmDto) {
		this.jbpmDto = jbpmDto;
	}

	public SwfLog getCurrSwfLog() {
		return currSwfLog;
	}

	public void setCurrSwfLog(SwfLog currSwfLog) {
		this.currSwfLog = currSwfLog;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public boolean isNewWorkFlow() {
		return isNewWorkFlow;
	}

	public void setNewWorkFlow(boolean isNewWorkFlow) {
		this.isNewWorkFlow = isNewWorkFlow;
	}

	public boolean getBack() {
		return back;
	}

	public void setBack(boolean back) {
		this.back = back;
	}

	public List<SwfLog> getBackSwfLogList() {
		return backSwfLogList;
	}

	public void setBackSwfLogList(List<SwfLog> backSwfLogList) {
		this.backSwfLogList = backSwfLogList;
	}

	public boolean getClaimCancel() {
		return claimCancel;
	}

	public void setClaimCancel(boolean claimCancel) {
		this.claimCancel = claimCancel;
	}

	public boolean getAutoClose() {
		return autoClose;
	}

	public void setAutoClose(boolean autoClose) {
		this.autoClose = autoClose;
	}

	public static boolean isWorkflowswitch() {
		if(reloadSwitch){
			try {
				String workflowswitch = AppConfig.get("sysconst.WORKFLOWSWITCH");
				workFlowSwitch = "1".equals(workflowswitch);
			} catch (Exception e) {
				workFlowSwitch = false;
			}
			reloadSwitch = false;
		}
		return workFlowSwitch;
	}

	public String getNextActorId() {
		return nextActorId;
	}

	public void setNextActorId(String nextActorId) {
		this.nextActorId = nextActorId;
	}

	public Map<String, List<Object>> getParamCertainLossNodeMap() {
		return paramCertainLossNodeMap;
	}

	public void setParamCertainLossNodeMap(Map<String, List<Object>> paramCertainLossNodeMap) {
		this.paramCertainLossNodeMap = paramCertainLossNodeMap;
	}

	public Map<String, Object> getFlowParamMap() {
		return flowParamMap;
	}

	public void setFlowParamMap(Map<String, Object> flowParamMap) {
		this.flowParamMap = flowParamMap;
	}

	public int getMaxLogNo() {
		return maxLogNo++;
	}

	public void setMaxLogNo(int maxLogNo) {
		this.maxLogNo = maxLogNo;
	}

	public int getMaxPathLogNo() {
		return maxPathLogNo++;
	}

	public void setMaxPathLogNo(int maxPathLogNo) {
		this.maxPathLogNo = maxPathLogNo;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	
	public static void setWorkFlowSwitch(boolean workFlowSwitch) {
		WorkFlowDto.workFlowSwitch = workFlowSwitch;
	}

	public static void setReloadSwitch(boolean reloadSwitch) {
		WorkFlowDto.reloadSwitch = reloadSwitch;
	}

	public static boolean isReloadSwitch() {
		return reloadSwitch;
	}

	/**
	 * 添加定损信息,车辆为certa，人伤为wound，财产为propc
	 */
	public void addParamCertainLossNodeMap(String key, Object value) {
		List<Object> certaNodeList = this.paramCertainLossNodeMap.get(key);
		if (certaNodeList == null) {
			certaNodeList = new ArrayList<Object>(3);
		}
		certaNodeList.add(value);
		this.paramCertainLossNodeMap.put(key, certaNodeList);
	}
}
