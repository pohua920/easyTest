package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLthirdProp财产损失部位
 */
@Entity
@Table(name = "PRPLTHIRDPROP")
public class PrpLthirdProp implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLthirdPropId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性车牌号 */
	private String licenseNo;

	/** 属性财产损失部位代码 */
	private String lossItemCode;

	/** 属性财产损失部位名称 */
	private String lossItemName;

	/** 属性损失程序描述 */
	private String lossItemDesc;

	/** 属性状态字段 */
	private String flag;

	/** 属性显示列表 */
	private Collection<PrpLthirdProp> thirdPropList;

	/** 属性是险别 */
	private String prpLthirdPropKindCode = "";

	/** 属性调度处理标志 */
	private String scheduleType = "";

	/** 属性是否选择发送 */
	private String selectSend = "";
	/** 属性是哪个节点的调用 */
	private String nodeType = "";
	/** 财车车牌号码*/
	private String goodsCarLicenseNo = "";

	@Column(name="GOODSCARLICENSENO")
	public String getGoodsCarLicenseNo() {
		return goodsCarLicenseNo;
	}

	public void setGoodsCarLicenseNo(String goodsCarLicenseNo) {
		this.goodsCarLicenseNo = goodsCarLicenseNo;
	}

	/**
	 * 类PrpLthirdProp的默认构造方法
	 */
	public PrpLthirdProp() {
		id = new PrpLthirdPropId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLthirdPropId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLthirdPropId id) {
		this.id = id;
	}

	/**
	 * 属性险种代码的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性车牌号的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 属性财产损失部位代码的getter方法
	 */

	@Column(name = "LOSSITEMCODE")
	public String getLossItemCode() {
		return this.lossItemCode;
	}

	/**
	 * 属性财产损失部位代码的setter方法
	 */
	public void setLossItemCode(String lossItemCode) {
		this.lossItemCode = lossItemCode;
	}

	/**
	 * 属性财产损失部位名称的getter方法
	 */

	@Column(name = "LOSSITEMNAME")
	public String getLossItemName() {
		return this.lossItemName;
	}

	/**
	 * 属性财产损失部位名称的setter方法
	 */
	public void setLossItemName(String lossItemName) {
		this.lossItemName = lossItemName;
	}

	/**
	 * 属性损失程序描述的getter方法
	 */

	@Column(name = "LOSSITEMDESC")
	public String getLossItemDesc() {
		return this.lossItemDesc;
	}

	/**
	 * 属性损失程序描述的setter方法
	 */
	public void setLossItemDesc(String lossItemDesc) {
		this.lossItemDesc = lossItemDesc;
	}

	/**
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setThirdPropList(Collection<PrpLthirdProp> thirdPropList) {
		this.thirdPropList = thirdPropList;
	}

	@Transient
	public Collection<PrpLthirdProp> getThirdPropList() {
		return thirdPropList;
	}

	/**
	 * 设置属性险别类型
	 * @param prpLthirdPropKindCode 待设置的属性险别类型的值
	 */
	public void setPrpLthirdPropKindCode(String prpLthirdPropKindCode) {
		this.prpLthirdPropKindCode = StringUtils.rightTrim(prpLthirdPropKindCode);
	}

	/**
	 * 获取属性险别类型
	 * @return 属性险别类型的值
	 */
	@Transient
	public String getPrpLthirdPropKindCode() {
		return prpLthirdPropKindCode;
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
	@Transient
	public String getScheduleType() {
		return scheduleType;
	}

	/**
	 * 设置属性是否选择发送
	 * @param selectSend 待设置的属性是否选择发送的值
	 */
	public void setSelectSend(String selectSend) {
		this.selectSend = StringUtils.rightTrim(selectSend);
	}

	/**
	 * 获取属性是否选择发送
	 * @return 属性是否选择发送的值
	 */
	@Transient
	public String getSelectSend() {
		return selectSend;
	}

	/**
	 * 设置属性节点类型
	 * @param nodeType 待设置的属性节点类型的值
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = StringUtils.rightTrim(nodeType);
	}

	/**
	 * 获取属性节点类型
	 * @return 属性节点类型的值
	 */
	@Transient
	public String getNodeType() {
		return nodeType;
	}

}
