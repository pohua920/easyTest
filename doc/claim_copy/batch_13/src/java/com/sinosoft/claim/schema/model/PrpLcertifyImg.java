package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类PrpLcertifyImg
 */
@Entity
@Table(name = "PRPLCERTIFYIMG")
public class PrpLcertifyImg implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcertifyImgId id;

	/** 属性标的名称 */
	private String lossItemName;

	/** 属性单证类型代码 */
	private String typeCode;

	/** 属性图片名称 */
	private String picName;

	/** 属性签收日期 */
	private Date signInDate;

	/** 属性第三方传送图片代码（如易保代码） */
	private String thirdPartyCode;

	/** 属性上传时文件名 */
	private String uploadFileName;

	/** 属性影象文件名 */
	private String imgFileName;

	/** 属性图片存放路径 */
	private String picPath;

	/** 属性收集者 */
	private String collectorName;

	/** 属性接收状态(0:未接收，1：接收到) */
	private String receiveStatus;

	/** 属性标志字段 */
	private String flag;

	/** 属性上传图片字节数大小 */
	private Long imgSize;

	/** 属性上传图片的节点位置 */
	private String uploadNodeFlag;

	/** 属性显示名称 */
	private String displayName;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性有效标志 */
	private String validStatus;

	/** 属性注销日期 */
	private Date cancelDate;

	/** 属性UPLOADFILEPATH */
	private String uploadfilepath;

	/** 关联对象,不存数据库，展示用 */
	private List<PrpLcertifyImg> certifyImgList = new ArrayList<PrpLcertifyImg>(0);
	/** 编辑类型 */
	private String editType = "";

	/**
	 * 属性ediType的getter方法
	 */
	@Transient
	public String getEditType() {
		return editType;
	}

	/**
	 * 属性editType的setter方法
	 */
	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * 类PrpLcertifyImg的默认构造方法
	 */
	public PrpLcertifyImg() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "businessNo", column = @Column(name = "BUSINESSNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")),
			@AttributeOverride(name = "lossItemCode", column = @Column(name = "LOSSITEMCODE")) })
	public PrpLcertifyImgId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcertifyImgId id) {
		this.id = id;
	}

	/**
	 * 属性标的名称的getter方法
	 */

	@Column(name = "LOSSITEMNAME")
	public String getLossItemName() {
		return this.lossItemName;
	}

	/**
	 * 属性标的名称的setter方法
	 */
	public void setLossItemName(String lossItemName) {
		this.lossItemName = lossItemName;
	}

	/**
	 * 属性单证类型代码的getter方法
	 */

	@Column(name = "TYPECODE")
	public String getTypeCode() {
		return this.typeCode;
	}

	/**
	 * 属性单证类型代码的setter方法
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * 属性图片名称的getter方法
	 */

	@Column(name = "PICNAME")
	public String getPicName() {
		return this.picName;
	}

	/**
	 * 属性图片名称的setter方法
	 */
	public void setPicName(String picName) {
		this.picName = picName;
	}

	/**
	 * 属性签收日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SIGNINDATE")
	public Date getSignInDate() {
		return this.signInDate;
	}

	/**
	 * 属性签收日期的setter方法
	 */
	public void setSignInDate(Date signInDate) {
		this.signInDate = signInDate;
	}

	/**
	 * 属性第三方传送图片代码（如易保代码）的getter方法
	 */

	@Column(name = "THIRDPARTYCODE")
	public String getThirdPartyCode() {
		return this.thirdPartyCode;
	}

	/**
	 * 属性第三方传送图片代码（如易保代码）的setter方法
	 */
	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}

	/**
	 * 属性上传时文件名的getter方法
	 */

	@Column(name = "UPLOADFILENAME")
	public String getUploadFileName() {
		return this.uploadFileName;
	}

	/**
	 * 属性上传时文件名的setter方法
	 */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * 属性影象文件名的getter方法
	 */

	@Column(name = "IMGFILENAME")
	public String getImgFileName() {
		return this.imgFileName;
	}

	/**
	 * 属性影象文件名的setter方法
	 */
	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	/**
	 * 属性图片存放路径的getter方法
	 */

	@Column(name = "PICPATH")
	public String getPicPath() {
		return this.picPath;
	}

	/**
	 * 属性图片存放路径的setter方法
	 */
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	/**
	 * 属性收集者的getter方法
	 */

	@Column(name = "COLLECTORNAME")
	public String getCollectorName() {
		return this.collectorName;
	}

	/**
	 * 属性收集者的setter方法
	 */
	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}

	/**
	 * 属性接收状态(0:未接收，1：接收到)的getter方法
	 */

	@Column(name = "RECEIVESTATUS")
	public String getReceiveStatus() {
		return this.receiveStatus;
	}

	/**
	 * 属性接收状态(0:未接收，1：接收到)的setter方法
	 */
	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	/**
	 * 属性标志字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性上传图片字节数大小的getter方法
	 */

	@Column(name = "IMGSIZE")
	public Long getImgSize() {
		return this.imgSize;
	}

	/**
	 * 属性上传图片字节数大小的setter方法
	 */
	public void setImgSize(Long imgSize) {
		this.imgSize = imgSize;
	}

	/**
	 * 属性上传图片的节点位置的getter方法
	 */

	@Column(name = "UPLOADNODEFLAG")
	public String getUploadNodeFlag() {
		return this.uploadNodeFlag;
	}

	/**
	 * 属性上传图片的节点位置的setter方法
	 */
	public void setUploadNodeFlag(String uploadNodeFlag) {
		this.uploadNodeFlag = uploadNodeFlag;
	}

	/**
	 * 属性显示名称的getter方法
	 */

	@Column(name = "DISPLAYNAME")
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * 属性显示名称的setter方法
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性有效标志的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性有效标志的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性注销日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CANCELDATE")
	public Date getCancelDate() {
		return this.cancelDate;
	}

	/**
	 * 属性注销日期的setter方法
	 */
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	/**
	 * 属性UPLOADFILEPATH的getter方法
	 */

	@Column(name = "UPLOADFILEPATH")
	public String getUploadfilepath() {
		return this.uploadfilepath;
	}

	/**
	 * 属性UPLOADFILEPATH的setter方法
	 */
	public void setUploadfilepath(String uploadfilepath) {
		this.uploadfilepath = uploadfilepath;
	}

	@Transient
	public List<PrpLcertifyImg> getCertifyImgList() {
		return certifyImgList;
	}

	public void setCertifyImgList(List<PrpLcertifyImg> certifyImgList) {
		this.certifyImgList = certifyImgList;
	}

}
