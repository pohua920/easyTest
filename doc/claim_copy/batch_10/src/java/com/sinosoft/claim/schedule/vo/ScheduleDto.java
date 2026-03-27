package com.sinosoft.claim.schedule.vo;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcheckItem;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * 新案件提示数据传输对象
 * <p>
 * Title: 新案件提示
 * </p>
 * <p>
 * Description: 车险理赔理赔节点
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ScheduleDto implements Serializable {
	/** 调度主表信息 */
	private PrpLscheduleMainWF prpLscheduleMainWF;

	/** 改派的定损调度 */
	private PrpLscheduleItem prpLscheduleItem;

	/** 三者车辆信息 */
	private List<PrpLthirdParty> prpLthirdPartyList;
	/** 调度表的信息 */
	private List<PrpLscheduleItem> prpLscheduleItemList;
	/** 查勘表标的信息 */
	private List<PrpLcheckItem> prpLcheckItemList;
	/** 报案信息补充说明 */
	private List<PrpLregistExt> prpLregistExtList;

	private List<?> smcComCodeInfoList;
	private List<?> smSendSMListList;

	public List<?> getSmcComCodeInfoList() {
		return smcComCodeInfoList;
	}

	public void setSmcComCodeInfoList(List<?> smcComCodeInfoList) {
		this.smcComCodeInfoList = smcComCodeInfoList;
	}

	public List<?> getSmSendSMListList() {
		return smSendSMListList;
	}

	public void setSmSendSMListList(List<?> smSendSMListList) {
		this.smSendSMListList = smSendSMListList;
	}

	/**
	 * @return Returns the prpLregistExtList.
	 */
	public List<PrpLregistExt> getPrpLregistExtList() {
		return prpLregistExtList;
	}

	/**
	 * @param prpLregistExtList The prpLregistExtList to set.
	 */
	public void setPrpLregistExtList(List<PrpLregistExt> prpLregistExtList) {
		this.prpLregistExtList = prpLregistExtList;
	}

	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;

	/** 属性调度处理标志 */
	private String scheduleType = "";

	public ScheduleDto() {
	}

	/**
	 * 得到调度主表信息
	 * @return 调度主表信息
	 */
	public PrpLscheduleMainWF getPrpLscheduleMainWF() {
		return prpLscheduleMainWF;
	}

	/**
	 * 设置调度主表信息
	 * @param prpLscheduleMainWF 调度主表信息
	 */
	public void setPrpLscheduleMainWF(PrpLscheduleMainWF prpLscheduleMainWF) {
		this.prpLscheduleMainWF = prpLscheduleMainWF;
	}

	/**
	 * 得到调度标的信息
	 * @return 调度标的信息
	 */
	public List<PrpLscheduleItem> getPrpLscheduleItemList() {
		return prpLscheduleItemList;
	}

	/**
	 * 设置调度标的信息
	 * @param PrpLscheduleItem调度标的信息
	 */
	public void setPrpLscheduleItemList(List<PrpLscheduleItem> prpLscheduleItemList) {
		this.prpLscheduleItemList = prpLscheduleItemList;
	}

	/**
	 * 得到调度操作状态信息
	 * @return 调度操作状态信息
	 */
	public PrpLclaimStatus getPrpLclaimStatus() {
		return prpLclaimStatus;
	}

	/**
	 * 设置调度操作状态信息
	 * @param prpLclaimStuats 调度操作状态信息
	 */
	public void setPrpLclaimStatus(PrpLclaimStatus prpLclaimStatus) {
		this.prpLclaimStatus = prpLclaimStatus;
	}

	/**
	 * 得到三者车辆信息
	 * @return 三者车辆信息
	 */
	public List<PrpLthirdParty> getPrpLthirdPartyList() {
		return prpLthirdPartyList;
	}

	/**
	 * 设置三者车辆信息
	 * @param PrpLthirdPartyList 三者车辆表信息
	 */
	public void setPrpLthirdPartyList(List<PrpLthirdParty> prpLthirdPartyList) {
		this.prpLthirdPartyList = prpLthirdPartyList;
	}

	/**
	 * 得到查勘标的信息
	 * @return 查勘标的信息
	 */
	public List<PrpLcheckItem> getPrpLcheckItemList() {
		return prpLcheckItemList;
	}

	/**
	 * 设置查勘标的信息
	 * @param PrpLcheckItem查勘标的信息
	 */
	public void setPrpLcheckItemList(List<PrpLcheckItem> prpLcheckItemList) {
		this.prpLcheckItemList = prpLcheckItemList;
	}

	/**
	 * 设置属性调度处理标志
	 * @param scheduleType 待设置的属性调度处理标志的值
	 */
	public void setScheduleType(String scheduleType) {
		this.scheduleType = StringUtils.rightTrim(scheduleType);
	}

	/**
	 * 获取属性调度处理标志
	 * @return 属性调度处理标志的值
	 */
	public String getScheduleType() {
		return scheduleType;
	}

	/**
	 * 得到调度改派状态信息
	 * @return 调度改派状态信息
	 */
	public PrpLscheduleItem getPrpLscheduleItem() {
		return prpLscheduleItem;
	}

	/**
	 * 设置调度改派状态信息
	 * @param prpLclaimStuats 调度改派状态信息
	 */
	public void setPrpLscheduleItem(PrpLscheduleItem prpLscheduleItem) {
		this.prpLscheduleItem = prpLscheduleItem;
	}
}
