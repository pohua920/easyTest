package com.sinosoft.claim.check.vo;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLext;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLthirdCarLoss;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckExt;
import com.sinosoft.claim.schema.model.PrpLcheckLoss;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * 自定义查勘数据传输对象
 * <p>
 * Title: 车险理赔查勘DTO
 * </p>
 * <p>
 * Description: 车险理赔查勘样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CheckDto implements Serializable {
	/** 查勘主信息 */
	private PrpLcheck prpLcheck;
	/** 查勘扩展信息 */
	private List<PrpLcheckExt> prpLcheckExtList;
	/** 事故估损金额 */
	private List<PrpLcheckLoss> prpLcheckLossList;
	/** 新增加了定损的标的 */
	private boolean newScheduleItem = false;
	/** 调度标的的详细内容 */
	private String scheduleItemNote = "";
	/** 三者车辆信息 */
	private List<PrpLthirdParty> prpLthirdPartyList;
	/** 人员伤亡跟踪信息 */
	private List<PrpLpersonTrace> prpLpersonTraceList;
	/** 驾驶员信息 */
	private List<PrpLdriver> prpLdriverList;
	/** 报案信息补充说明 */
	private List<PrpLregistExt> prpLregistExtList;
	/** 查勘报告 */
	private List<PrpLregistText> prpLregistTextList;
	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;
	/** 调度表的信息 */
	private List<PrpLscheduleItem> prpLscheduleItemList;
	/** 特别约定信息 */
	private List<PrpCengage> prpCengageList;
	/** 损失部位信息 */
	private List<PrpLthirdCarLoss> prpLthirdCarLossList;
	/** 车辆外财产损失部位信息 */
	private List<PrpLthirdProp> prpLthirdPropList;
	/** 车上财产损失部位信息 */
	// private List prpLthirdPropCarList;
	/** 调查信息 */
	private AcciCheckDto acciChcekDto;
	/** 货运险扩展信息 */
	private PrpLext prpLext;
	/** 为了存储危巨宰代码信息 */
	private PrpLregist prpLregist;
	private List<PrpLclaimLoss> prpLclaimLossList;

	/**
	 * 设置货运险扩展信息
	 * @param PrpLext 货运险扩展信息
	 */
	public void setPrpLext(PrpLext prpLext) {
		this.prpLext = prpLext;
	}

	/**
	 * 获得货运险扩展信息
	 * @return 货运险扩展信息
	 */
	public PrpLext getPrpLext() {
		return this.prpLext;
	}

	/**
	 * 设置调查信息
	 * @param acciChcek 调查信息
	 */
	public void setAcciCheckDto(AcciCheckDto acciCheckDto) {
		this.acciChcekDto = acciCheckDto;
	}

	/**
	 * 获得调查信息
	 * @return 调查信息
	 */
	public AcciCheckDto getAcciCheckDto() {
		return this.acciChcekDto;
	}

	/**
	 * @return Returns the prpCengageList.
	 */
	public List<PrpCengage> getPrpCengageList() {
		return prpCengageList;
	}

	/**
	 * @param prpCengageList The prpCengageList to set.
	 */
	public void setPrpCengageList(List<PrpCengage> prpCengageList) {
		this.prpCengageList = prpCengageList;
	}

	/**
	 * 得到查勘主信息
	 * @return 查勘主信息
	 */
	public PrpLcheck getPrpLcheck() {
		return prpLcheck;
	}

	/**
	 * 得到操作状态信息
	 * @return 操作状态信息
	 */
	public PrpLclaimStatus getPrpLclaimStatus() {
		return prpLclaimStatus;
	}

	/**
	 * 设置查勘报告
	 * @param prpLregistTextList 查勘报告
	 */
	public void setPrpLregistTextList(List<PrpLregistText> prpLregistTextList) {
		this.prpLregistTextList = prpLregistTextList;
	}

	/**
	 * 设置查勘主信息
	 * @param prpLcheck 查勘主信息
	 */
	public void setPrpLcheck(PrpLcheck prpLcheck) {
		this.prpLcheck = prpLcheck;
	}

	/**
	 * 设置查勘报告
	 * @param prpLregistTextList 查勘报告
	 */
	public List<PrpLregistText> getPrpLregistTextList() {
		return prpLregistTextList;
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
	 * 得到驾驭员信息
	 * @return 驾驭员 信息
	 */
	public List<PrpLdriver> getPrpLdriverList() {
		return prpLdriverList;
	}

	/**
	 * 设置驾驭员信息
	 * @param PrpLdriverList 驾驭员表信息
	 */
	public void setPrpLdriverList(List<PrpLdriver> prpLdriverList) {
		this.prpLdriverList = prpLdriverList;
	}

	/**
	 * 得到查勘扩展信息
	 * @return 查勘扩展信息
	 */
	public List<PrpLcheckExt> getPrpLcheckExtList() {
		return prpLcheckExtList;
	}

	/**
	 * 设置查勘扩展信息
	 * @param PrpLcheckExtList 查勘扩展信息
	 */
	public void setPrpLcheckExtList(List<PrpLcheckExt> prpLcheckExtList) {
		this.prpLcheckExtList = prpLcheckExtList;
	}

	/**
	 * 得到事故估损金额信息
	 * @return 事故估损金额信息
	 */
	public List<PrpLcheckLoss> getPrpLcheckLossList() {
		return prpLcheckLossList;
	}

	/**
	 * 设置事故估损金额信息
	 * @param prpLcheckLossList 事故估损金额信息
	 */
	public void setPrpLcheckLossList(List<PrpLcheckLoss> prpLcheckLossList) {
		this.prpLcheckLossList = prpLcheckLossList;
	}

	/**
	 * 设置操作状态信息
	 * @param prpLclaimStatus 操作状态信息
	 */
	public void setPrpLclaimStatus(PrpLclaimStatus prpLclaimStatus) {
		this.prpLclaimStatus = prpLclaimStatus;
	}

	/**
	 * 得到人伤跟踪信息
	 * @return 人伤跟踪 信息
	 */
	public List<PrpLpersonTrace> getPrpLpersonTraceList() {
		return prpLpersonTraceList;
	}

	/**
	 * 设置人伤跟踪信息
	 * @param PrpLpersonTraceList 人伤跟踪表信息
	 */
	public void setPrpLpersonTraceList(List<PrpLpersonTrace> prpLpersonTraceList) {
		this.prpLpersonTraceList = prpLpersonTraceList;
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
	 * 得到损失部位信息
	 * @return 损失部位信息
	 */
	public List<PrpLthirdCarLoss> getPrpLthirdCarLossList() {
		return prpLthirdCarLossList;
	}

	/**
	 * 设置调度标的信息
	 * @param prpLthirdCarLossList调度标的信息
	 */
	public void setPrpLthirdCarLossList(List<PrpLthirdCarLoss> prpLthirdCarLossList) {
		this.prpLthirdCarLossList = prpLthirdCarLossList;
	}

	/**
	 * 设置其它损失信息
	 * @param prpLthirdPropList 其它损失信息
	 */

	public void setPrpLthirdPropList(List<PrpLthirdProp> prpLthirdPropList) {
		this.prpLthirdPropList = prpLthirdPropList;
	}

	/**
	 * 得到其它损失信息
	 * @return 其它损失信息
	 */

	public List<PrpLthirdProp> getPrpLthirdPropList() {
		return prpLthirdPropList;
	}

	/**
	 * 得到新增定损标的
	 * @return 新增定损标的
	 */
	public boolean getNewScheduleItem() {
		return newScheduleItem;
	}

	/**
	 * 设置新增定损标的
	 * @param boolean 新增定损标的
	 */
	public void setNewScheduleItem(boolean newScheduleItem) {
		this.newScheduleItem = newScheduleItem;
	}

	/**
	 * 设置属性调度标的的详细内容
	 * @param scheduleItemNote 待设置的属性调度标的的详细内容的值
	 */
	public void setScheduleItemNote(String scheduleItemNote) {
		this.scheduleItemNote = StringUtils.rightTrim(scheduleItemNote);
	}

	/**
	 * 获取属性调度标的的详细内容
	 * @return 属性调度标的的详细内容
	 */
	public String getScheduleItemNote() {
		return scheduleItemNote;
	}

	public List<PrpLregistExt> getPrpLregistExtList() {
		return prpLregistExtList;
	}

	public void setPrpLregistExtList(List<PrpLregistExt> prpLregistExtList) {
		this.prpLregistExtList = prpLregistExtList;
	}

	public AcciCheckDto getAcciChcekDto() {
		return acciChcekDto;
	}

	public void setAcciChcekDto(AcciCheckDto acciChcekDto) {
		this.acciChcekDto = acciChcekDto;
	}

	public PrpLregist getPrpLregist() {
		return prpLregist;
	}

	public void setPrpLregist(PrpLregist prpLregist) {
		this.prpLregist = prpLregist;
	}

	public List<PrpLclaimLoss> getPrpLclaimLossList() {
		return prpLclaimLossList;
	}

	public void setPrpLclaimLossList(List<PrpLclaimLoss> prpLclaimLossList) {
		this.prpLclaimLossList = prpLclaimLossList;
	}
}
