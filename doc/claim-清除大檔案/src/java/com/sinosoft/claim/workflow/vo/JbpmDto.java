package com.sinosoft.claim.workflow.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.task.Task;



public class JbpmDto {
	//流程模板Id
	private String processId;
	//流程节点ID
	private String actorId;
	// 流程业务号码
	private String businessId = "";
	//流程实例参数
	
	private boolean close = false;
	//任务Id
	private Long taskId = -1L;
	//流程实例ID
	private Long processInstanceId = -1L;
	
	//下一集节点
//	private Set<String> nextActorIds = null;
	//当前任务
	private Task currentTask = null;
	//下一集节点任务
	private List<Task> nextTaskList = null;
	/**
	 * bpm执行成功标志
	 */
	private boolean bpmSuccess = false;
	
	private Map<String,Object> paramsMap = new HashMap<String,Object>(1);
	/**
	 * 定损多个的处理
	 */
	private Map<String,List<Object>>certainLossNodeMap = new HashMap<String,List<Object>>(3);
	
//	public static void main(String[] args) {
//		JbpmDto jbpmDto = new JbpmDto();
//		jbpmDto.putParamsMap("cancelFlag", "ss");
//		jbpmDto.getBusinessId();
//	}
	
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}


	public Task getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}

	public List<Task> getNextTaskList() {
		return nextTaskList;
	}

	public void setNextTaskList(List<Task> nextTaskList) {
		this.nextTaskList = nextTaskList;
	}
	public void addNextTaskList(Task task) {
		if(this.nextTaskList==null){
			this.nextTaskList = new ArrayList<Task>(5);
		}
		this.nextTaskList.add(task);
	}

	public boolean getClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}

	public boolean getBpmSuccess() {
		return bpmSuccess;
	}
	public void setBpmSuccess(boolean bpmSuccess) {
		this.bpmSuccess = bpmSuccess;
	}
	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}
	

	public void putParamsMap(String key,Object value){
		if(paramsMap==null){
			paramsMap = new HashMap<String,Object>(5);
		}
		paramsMap.put(key, value);
	}
	public Object getParamsMap(String key){
		if(paramsMap==null){
			paramsMap = new HashMap<String,Object>(5);
		}
		return paramsMap.get(key);
	}
	
	public Map<String, List<Object>> getCertainLossNodeMap() {
		return certainLossNodeMap;
	}

	public void setCertainLossNodeMap(Map<String, List<Object>> certainLossNodeMap) {
		this.certainLossNodeMap = certainLossNodeMap;
	}
	
	public Object getCertainLossNodeMap(String key,int index){
		List<Object> certaNodeList = this.certainLossNodeMap.get(key);
		if(certaNodeList!=null&&certaNodeList.size()>index){
			return certaNodeList.get(index);
		}
		return null;
	}
	/**
	 * 添加定损信息,车辆为certa，人伤为wound，财产为propc
	 */
	public void addCertainLossNodeMap(String key,Object value){
		List<Object> certaNodeList = this.certainLossNodeMap.get(key);
		if(certaNodeList==null){
			certaNodeList = new ArrayList<Object>(3);
		}
		certaNodeList.add(value);
		this.certainLossNodeMap.put(key, certaNodeList);
	}
	
	public List<Object> getPropcNodeList() {
		List<Object> propcNodeList = this.certainLossNodeMap.get("propc");
		return propcNodeList==null?new ArrayList<Object>():propcNodeList;
	}

	public void setPropcNodeList(List<Object> propcNodeList) {
		this.certainLossNodeMap.put("propc",propcNodeList);
	}

	public List<Object> getCertaNodeList() {
		List<Object> certaNodeList = this.certainLossNodeMap.get("certa");
		return certaNodeList==null?new ArrayList<Object>():certaNodeList;
	}

	public void setCertaNodeList(List<Object> certaNodeList) {
		this.certainLossNodeMap.put("certa",certaNodeList);
	}
	
	public List<Object> getWoundNodeList() {
		List<Object> woundNodeList = this.certainLossNodeMap.get("wound");
		return woundNodeList==null?new ArrayList<Object>():woundNodeList;
	}

	public void setWoundNodeList(List<Object> woundNodeList) {
		this.certainLossNodeMap.put("wound",woundNodeList);
	}

}
