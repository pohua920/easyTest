package com.sinosoft.claim.workflow.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * 自定义统计数据传输对象
 * <p>
 * Title: 车险理赔统计DTO
 * </p>
 * <p>
 * Description: 车险理赔统计样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 */
public class StatStatusDto implements Serializable {
	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/** 属性节点种类 */
	private String nodeType = "";
	/** 属性节点种类名称 */
	private String nodeTypeName = "";
	/** 属性操作状态位 */
	private String status = "";
	/** 属性操作状态名称 */
	private String statusName = "";
	/** 属性统计数量 */
	int count = 0;
	/** 属性标志位 */
	private String flag = "";
	/** 属性备注 */
	private String remark = "";
	/** 统计集合 */
	private List<StatStatusDto> statStatusList = new ArrayList<StatStatusDto>();
	/** 用户代码 */
	private String userCode;
	/** 用户名称 */
	private String userName;
	/** 编辑类型 */
	private String editType;

	/** 统计开始日期 */
	private String startDate;

	/** 统计结束日期 */
	private String endDate;

	/** 状态数量值 */
	private int waitStatusCount = 0;
	/** 状态数量值 */
	private int procStatusCount = 0;
	/** 状态数量值 */
	private int noSubmitStatusCount = 0;
	/** 状态数量值 */
	private int submitStatusCount = 0;
	/** 状态数量值 */
	private int backStatusCount = 0;

	/**
	 * 得到属性节点种类名称
	 * @return 属性节点种类名称
	 */
	public String getNodeTypeName() {
		return nodeTypeName;
	}

	/**
	 * 得到属性统计数量
	 * @return 属性统计数量
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 得到属性备注
	 * @return 属性备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 得到属性操作状态位
	 * @return 属性操作状态位
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 得到统计集合
	 * @return 统计集合
	 */
	public List<StatStatusDto> getStatStatusList() {
		return statStatusList;
	}

	/**
	 * 得到属性操作状态名称
	 * @return 属性操作状态名称
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * 得到属性节点种类
	 * @return 属性节点种类
	 */
	public String getNodeType() {
		return nodeType;
	}

	/**
	 * 设置属性标志位
	 * @param flag 属性标志位
	 */
	public void setFlag(String flag) {
		this.flag = StringUtils.rightTrim(flag);
	}

	/**
	 * 设置属性节点种类名称
	 * @param nodeTypeName 属性节点种类名称
	 */
	public void setNodeTypeName(String nodeTypeName) {
		this.nodeTypeName = StringUtils.rightTrim(nodeTypeName);
	}

	/**
	 * 设置属性统计数量
	 * @param count 属性统计数量
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 设置属性备注
	 * @param remark 属性备注
	 */
	public void setRemark(String remark) {
		this.remark = StringUtils.rightTrim(remark);
	}

	/**
	 * 设置属性操作状态位
	 * @param status 属性操作状态位
	 */
	public void setStatus(String status) {
		this.status = StringUtils.rightTrim(status);
	}

	/**
	 * 设置统计集合
	 * @param statStatusList 统计集合
	 */
	public void setStatStatusList(List<StatStatusDto> statStatusList) {
		this.statStatusList = statStatusList;
	}

	/**
	 * 设置属性操作状态名称
	 * @param statusName 属性操作状态名称
	 */
	public void setStatusName(String statusName) {
		this.statusName = StringUtils.rightTrim(statusName);
	}

	/**
	 * 设置属性节点种类
	 * @param nodeType 属性节点种类
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = StringUtils.rightTrim(nodeType);
	}

	/**
	 * 设置用户代码
	 * @param userCode 用户代码
	 */
	public void setUserCode(String userCode) {
		this.userCode = StringUtils.rightTrim(userCode);
	}

	/**
	 * 设置编辑类型
	 * @param editType 编辑类型
	 */
	public void setEditType(String editType) {
		this.editType = StringUtils.rightTrim(editType);
	}

	/**
	 * 设置用户名称
	 * @param userName 用户名称
	 */
	public void setUserName(String userName) {
		this.userName = StringUtils.rightTrim(userName);
	}

	/**
	 * 得到属性标志位
	 * @return 属性标志位
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * 得到用户代码
	 * @return 用户代码
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * 得到编辑类型
	 * @return 编辑类型
	 */
	public String getEditType() {
		return editType;
	}

	/**
	 * 得到用户名称
	 * @return 用户名称
	 */
	public String getUserName() {
		return userName;
	}

	public StatStatusDto() {
	}

	/**
	 * 得到属性统计开始日期
	 * @return 属性统计开始日期
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * 设置属性统计开始日期
	 * @param startDate 属性统计开始日期
	 */
	public void setStartDate(String startDate) {
		this.startDate = StringUtils.rightTrim(startDate);
	}

	/**
	 * 得到属性统计结束日期
	 * @return 属性统计结束日期
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 设置属性统计结束日期
	 * @param endDate 属性统计结束日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = StringUtils.rightTrim(endDate);
	}

	/**
	 * 设置属性状态数量
	 * @param waitstatuscount 状态数量值
	 */
	public void setWaitStatusCount(int waitStatusCount) {
		this.waitStatusCount = waitStatusCount;
	}

	/**
	 * 获取属性状态数量
	 * @return 状态数量
	 */
	public int getWaitStatusCount() {
		return waitStatusCount;
	}

	/**
	 * 设置属性状态数量
	 * @param procstatuscount 状态数量值
	 */
	public void setProcStatusCount(int procStatusCount) {
		this.procStatusCount = procStatusCount;
	}

	/**
	 * 获取属性状态数量
	 * @return 状态数量
	 */
	public int getProcStatusCount() {
		return procStatusCount;
	}

	/**
	 * 设置属性状态数量
	 * @param nosubmitstatuscount 状态数量值
	 */
	public void setNoSubmitStatusCount(int noSubmitStatusCount) {
		this.noSubmitStatusCount = noSubmitStatusCount;
	}

	/**
	 * 获取属性状态数量
	 * @return 状态数量
	 */
	public int getNoSubmitStatusCount() {
		return noSubmitStatusCount;
	}

	/**
	 * 设置属性状态数量
	 * @param submitstatuscount 状态数量值
	 */
	public void setSubmitStatusCount(int submitStatusCount) {
		this.submitStatusCount = submitStatusCount;
	}

	/**
	 * 获取属性状态数量
	 * @return 状态数量
	 */
	public int getSubmitStatusCount() {
		return submitStatusCount;
	}

	/**
	 * 设置属性状态数量
	 * @param backstatuscount 状态数量值
	 */
	public void setBackStatusCount(int backStatusCount) {
		this.backStatusCount = backStatusCount;
	}

	/**
	 * 获取属性状态数量
	 * @return 状态数量
	 */
	public int getBackStatusCount() {
		return backStatusCount;
	}

}
