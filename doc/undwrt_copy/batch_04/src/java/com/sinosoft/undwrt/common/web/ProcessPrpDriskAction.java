package com.sinosoft.undwrt.common.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sinosoft.undwrt.common.model.PrpDrisk;
import com.sinosoft.undwrt.common.service.facade.PrpDriskService;

//import ins.common.service.facade.CodeService;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

/**
 * 獲取險種處理類.
 */
public class ProcessPrpDriskAction extends Struts2Action {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 屬性險種接口. */
	private PrpDriskService prpDriskService;

	/** 屬性險種訊息實體類. */
	private PrpDrisk prpdrisk;

	/** 屬性險種訊息列表. */
	private List<PrpDrisk> prpdriskList;

	/** 屬性commonMap. */
	private Map<String, String> commonMap;

	// private CodeService codeService;

	/**
	 * 獲取屬性commonMap.
	 * 
	 * @return 屬性commonMap的值
	 */
	public Map<String, String> getCommonMap() {
		return commonMap;
	}

	/**
	 * 設置commonMap.
	 * 
	 * @param commonMap
	 *            commonMap
	 */
	public void setCommonMap(Map<String, String> commonMap) {
		this.commonMap = commonMap;
	}

	/**
	 * 獲取屬性險種接口.
	 * 
	 * @return 屬性險種接口的值
	 */
	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	/**
	 * 設置屬性險種接口.
	 * 
	 * @param prpDriskService
	 *            待設置的險種接口的值
	 */
	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	/**
	 * 獲取屬性險種訊息實體類.
	 * 
	 * @return 屬性險種訊息實體類的值
	 */
	public PrpDrisk getPrpdrisk() {
		return prpdrisk;
	}

	/**
	 * 設置屬性險種訊息實體類.
	 * 
	 * @param prpdrisk
	 *            待設置的險種訊息實體類的值
	 */
	public void setPrpdrisk(PrpDrisk prpdrisk) {
		this.prpdrisk = prpdrisk;
	}

	/**
	 * 獲取屬性險種訊息列表.
	 * 
	 * @return 屬性險種訊息列表的值
	 */
	public List<PrpDrisk> getPrpdriskList() {
		return prpdriskList;
	}

	/**
	 * 設置屬性險種訊息列表.
	 * 
	 * @param prpdriskList
	 *            待設置的險種訊息列表的值
	 */
	public void setPrpdriskList(List<PrpDrisk> prpdriskList) {
		this.prpdriskList = prpdriskList;
	}

	// public String delete() {
	// prpDriskService.deleteByPK(Long.parseLong(taskId));
	// return SUCCESS;
	// }

	/**
	 * 查詢險種訊息列表.
	 * 
	 * @param condition
	 *            the condition
	 * @return String 查询数据结果字符串
	 * @decription 根据输入条件查询任务列表
	 * @author gaohaifeng 20110320
	 */
	public String prpDriskListQuery(String condition) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 20;
		}
		QueryRule queryRule = QueryRule.getInstance();// 获取QueryRule对象的Instance

		queryRule.addSql("select * from prpdrisk where ");
		if (condition != null && !"".equals(condition)) {
			queryRule.addSql(condition);
		}

		try {
			// Page page = prpDriskService.queryRulePrpDriskList(queryRule,
			// pageNo, pageSize);

		} catch (Exception e) {
			this.writeJSONMsg(e.getMessage());
		}
		return NONE;

	}

	// public String view() {
	// opreateType = "view";
	// oaTask = taskService.getTask(Long.parseLong(taskId));
	// return SUCCESS;
	// }
	//
	// public String prepareUpdate() {
	// opreateType = "edit";
	// commonMap = codeService.getCheckBoxList("TaskEdit");
	// oaTask = taskService.getTask(Long.parseLong(taskId));
	// return SUCCESS;
	// }
	//
	// public String prepareDivideResource() {
	// oaTask = taskService.getTask(Long.parseLong(taskId));
	// return SUCCESS;
	// }
	//
	// public String divideResource() {
	// oaTask.setOaUserTasks(oaUserTaskList);
	// taskService.divideResource(oaTask);
	// return SUCCESS;
	// }
	// public String update(){
	// taskService.update(oaTask);
	// return SUCCESS;
	// }
}
