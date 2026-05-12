package com.sinosoft.one.bpm.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jbpm.task.Task;

public class BusinessTaskData {
	private String actorId;
	
	private Long processInstanceId = -1L;
	
	private Long taskId = -1L;
	
	private Task task = null;
	
	private Map<Long,String> nextActorIds = new HashMap<Long,String>();
	
	private List<Task> nextTasks = new ArrayList<Task>();
	
	private Set<String>	nextActorIdSet = new HashSet<String>();
	
	private boolean close = false;

	private static final ThreadLocal<BusinessTaskData> threadLocal = new ThreadLocal<BusinessTaskData>();
	
	public BusinessTaskData() {
		super();
	}
	
	public BusinessTaskData(String actorId, Long taskId) {
		super();
		this.actorId = actorId;
		this.taskId = taskId;
	}

	public BusinessTaskData(String actorId, Long processInstanceId, Long taskId, Task task, Map<Long, String> nextActorIds, List<Task> nextTasks, Set<String> nextActorIdSet) {
		super();
		this.actorId = actorId;
		this.processInstanceId = processInstanceId;
		this.taskId = taskId;
		this.task = task;
		this.nextActorIds = nextActorIds;
		this.nextTasks = nextTasks;
		this.nextActorIdSet = nextActorIdSet;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Map<Long, String> getNextActorIds() {
		return nextActorIds;
	}

	public void setNextActorIds(Map<Long, String> nextActorIds) {
		this.nextActorIds = nextActorIds;
	}

	public List<Task> getNextTasks() {
		return nextTasks;
	}

	public void setNextTasks(List<Task> nextTasks) {
		this.nextTasks = nextTasks;
	}

	public Set<String> getNextActorIdSet() {
		return nextActorIdSet;
	}

	public void setNextActorIdSet(Set<String> nextActorIdSet) {
		this.nextActorIdSet = nextActorIdSet;
	}

	public static ThreadLocal<BusinessTaskData> getThreadlocal() {
		return threadLocal;
	}
	
	public void addNextActorIds(Long taskId,String actorId){
		if(this.nextActorIds==null){
			this.nextActorIds = new HashMap<Long,String>();
		}
		this.addNextActorIdSet(actorId);
		this.nextActorIds.put(taskId, actorId);
	}
	public void addNextActorIdSet(String actorId){
		if(this.nextActorIdSet==null){
			this.nextActorIdSet = new HashSet<String>();
		}
		this.nextActorIdSet.add(actorId);
	}
	public void addNextTasks(Task task){
		if(this.nextTasks==null){
			this.nextTasks = new ArrayList<Task>();
		}
		this.nextTasks.add(task);
	}
	
	public boolean getClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}
}
