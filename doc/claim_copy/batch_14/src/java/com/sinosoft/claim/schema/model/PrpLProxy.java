package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLProxy理赔调派处理记录表
 */
@Entity
@Table(name = "PRPLPROXY")
public class PrpLProxy implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLProxyId id;
	/** 操作状态 1。未处理 2。正在处理 3。已完成 4。已提交 5。 撤消 */
	private String status = "";

	/**
	 * 类PrpLProxy的默认构造方法
	 */
	public PrpLProxy() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "flowId", column = @Column(name = "FLOWID")), @AttributeOverride(name = "logNo", column = @Column(name = "LOGNO")), @AttributeOverride(name = "nodeName", column = @Column(name = "NODENAME")),
			@AttributeOverride(name = "nodestatus", column = @Column(name = "NODESTATUS")), @AttributeOverride(name = "flowStatus", column = @Column(name = "FLOWSTATUS")),
			@AttributeOverride(name = "fromUserCode", column = @Column(name = "FROMUSERCODE")), @AttributeOverride(name = "fromUserName", column = @Column(name = "FROMUSERNAME")),
			@AttributeOverride(name = "fromComCode", column = @Column(name = "FROMCOMCODE")), @AttributeOverride(name = "fromComName", column = @Column(name = "FROMCOMNAME")),
			@AttributeOverride(name = "toUserCode", column = @Column(name = "TOUSERCODE")), @AttributeOverride(name = "toUserName", column = @Column(name = "TOUSERNAME")), @AttributeOverride(name = "toComCode", column = @Column(name = "TOCOMCODE")),
			@AttributeOverride(name = "toComName", column = @Column(name = "TOCOMNAME")), @AttributeOverride(name = "operatorCode", column = @Column(name = "OPERATORCODE")), @AttributeOverride(name = "comCode", column = @Column(name = "COMCODE")),
			@AttributeOverride(name = "operateTime", column = @Column(name = "OPERATETIME")) })
	public PrpLProxyId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLProxyId id) {
		this.id = id;
	}

	/**
	 * 设置属性操作状态
	 * @param status 待设置的属性操作状态
	 */
	public void setStatus(String status) {
		this.status = StringUtils.rightTrim(status);
	}

	/**
	 * 获取属性操作状态
	 * @return 属性操作状态
	 */
	@Transient
	public String getStatus() {
		return status;
	}

}
