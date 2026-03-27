package com.sinosoft.undwrt.undwrtBase.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The Class SwfPathNew.
 */
@Entity(name = "SWFPATHNEW_UNDWRT")
@Table(name = "SWFPATHNEW")
public class SwfPathNew implements java.io.Serializable{
	
   /** The Constant serialVersionUID. */
   private static final long serialVersionUID = 1L;
	
   /** 屬性The sinosoft id. */
   private SwfPathNewId id;

   /** 屬性險種名稱. */
   private String riskCName;

   /** 屬性The sinosoft riske name. */
   private String riskeName;


   /** 屬性機構中文名稱. */
   private String comCName;

   /** 屬性The sinosoft com e name. */
   private String comEName;

   /** 屬性The sinosoft path. */
   private String path;

   /** 屬性The sinosoft path desc. */
   private String pathDesc;

   /** 屬性標志. */
   private String flag;

   /** 工作流狀態接口. */
   private String status;
   
	/**
	 * Instantiates a new swf path new.
	 */
	public SwfPathNew() {
}

	/**
	 * 獲取屬性the sinosoft id.
	 * 
	 * @return 屬性the sinosoft id的值
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")),
			@AttributeOverride(name = "comCode", column = @Column(name = "COMCODE")) })
public SwfPathNewId getId() {
	return id;
}

/**
 * 設置屬性the sinosoft id.
 * 
 * @param id
 *            待設置的the sinosoft id的值
 */
public void setId(SwfPathNewId id) {
	this.id = id;
}

/**
 * 獲取屬性險種名稱.
 * 
 * @return 屬性險種名稱的值
 */
@Column(name = "RISKCNAME")
public String getRiskCName() {
	return riskCName;
}

/**
 * 設置屬性險種名稱.
 * 
 * @param riskCName
 *            待設置的險種名稱的值
 */
public void setRiskCName(String riskCName) {
	this.riskCName = riskCName;
}

/**
 * 獲取屬性the sinosoft riske name.
 * 
 * @return 屬性the sinosoft riske name的值
 */
@Column(name = "RISKENAME")
public String getRiskeName() {
	return riskeName;
}

/**
 * 設置屬性the sinosoft riske name.
 * 
 * @param riskeName
 *            待設置的the sinosoft riske name的值
 */
public void setRiskeName(String riskeName) {
	this.riskeName = riskeName;
}

/**
 * 獲取屬性機構中文名稱.
 * 
 * @return 屬性機構中文名稱的值
 */
@Column(name = "COMCNAME")
public String getComCName() {
	return comCName;
}

/**
 * 設置屬性機構中文名稱.
 * 
 * @param comCName
 *            待設置的機構中文名稱的值
 */
public void setComCName(String comCName) {
	this.comCName = comCName;
}

/**
 * 獲取屬性the sinosoft com e name.
 * 
 * @return 屬性the sinosoft com e name的值
 */
@Column(name = "COMENAME")
public String getComEName() {
	return comEName;
}

/**
 * 設置屬性the sinosoft com e name.
 * 
 * @param comEName
 *            待設置的the sinosoft com e name的值
 */
public void setComEName(String comEName) {
	this.comEName = comEName;
}

/**
 * 獲取屬性the sinosoft path.
 * 
 * @return 屬性the sinosoft path的值
 */
@Column(name = "PATH")
public String getPath() {
	return path;
}

/**
 * 設置屬性the sinosoft path.
 * 
 * @param path
 *            待設置的the sinosoft path的值
 */
public void setPath(String path) {
	this.path = path;
}

/**
 * 獲取屬性the sinosoft path desc.
 * 
 * @return 屬性the sinosoft path desc的值
 */
@Column(name = "PATHDESC")
public String getPathDesc() {
	return pathDesc;
}

/**
 * 設置屬性the sinosoft path desc.
 * 
 * @param pathDesc
 *            待設置的the sinosoft path desc的值
 */
public void setPathDesc(String pathDesc) {
	this.pathDesc = pathDesc;
}

/**
 * 獲取屬性標志.
 * 
 * @return 屬性標志的值
 */
@Column(name = "FLAG")
public String getFlag() {
	return flag;
}

/**
 * 設置屬性標志.
 * 
 * @param flag
 *            待設置的標志的值
 */
public void setFlag(String flag) {
	this.flag = flag;
}

/**
 * 獲取工作流狀態接口.
 * 
 * @return 工作流狀態接口的值
 */
@Column(name = "STATUS")
public String getStatus() {
	return status;
}

/**
 * 設置工作流狀態接口.
 * 
 * @param status
 *            待設置的工作流狀態的值
 */
public void setStatus(String status) {
	this.status = status;
}
   

}