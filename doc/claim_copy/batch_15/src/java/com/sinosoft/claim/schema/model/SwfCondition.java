package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类SwfCondition工作流条件描述表
 */
@Entity
@Table(name = "SWFCONDITION")
public class SwfCondition implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private SwfConditionId id;

	/** 属性工作流路径定义表 */
	private SwfPath swfPath;

	/** 属性部门代码 */
	private String comCode;

	/** 属性是否有效标 */
	private String validStatus;

	/** 属性配置类型标志 */
	private String configType;

	/** 属性配置描述 */
	private String configText;

	/** 属性业务键值 */
	private String businessKey;

	/** 属性数据库名称 */
	private String dbName;

	/** 属性表名 */
	private String tableName;

	/** 属性字段数据类型 */
	private String dataType;

	/** 属性字段名 */
	private String columnName;

	/** 属性字段描述 */
	private String columnDesc;

	/** 属性运算符 */
	private String operator;

	/** 属性比较值 */
	private String value;

	/** 属性标志字段 */
	private String flag;

	/*
	 * 数据库中没有的字段，在页面上展示用
	 */
	/** 编辑类型 */
	private String editType = "";
	/** 路径名 */
	private String pathName = "";
	/** 属性显示列表 */
	private List<SwfCondition> conditionList = new ArrayList<SwfCondition>(0);

	/** HQL對應實體類 */
	private String origClass;

	/** 需要回寫屬性值來源的目標字段HQL */
	private String origProperty;

	/** 需要回寫到SWFLOG實體的目標資源HQL */
	private String destProperty;

	
	/**
	 * 类SwfCondition的默认构造方法
	 */
	public SwfCondition() {
		id = new SwfConditionId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "modelNo", column = @Column(name = "MODELNO")), @AttributeOverride(name = "pathNo", column = @Column(name = "PATHNO")),
			@AttributeOverride(name = "conditionNo", column = @Column(name = "CONDITIONNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public SwfConditionId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(SwfConditionId id) {
		this.id = id;
	}

	/**
	 * 属性工作流路径定义表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MODELNO", referencedColumnName = "MODELNO", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "PATHNO", referencedColumnName = "PATHNO", nullable = false, insertable = false, updatable = false) })
	public SwfPath getSwfPath() {
		return this.swfPath;
	}

	/**
	 * 属性工作流路径定义表的setter方法
	 */
	public void setSwfPath(SwfPath swfPath) {
		this.swfPath = swfPath;
	}

	/**
	 * 属性部门代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性部门代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性是否有效标的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性是否有效标的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性配置类型标志的getter方法
	 */

	@Column(name = "CONFIGTYPE")
	public String getConfigType() {
		return this.configType;
	}

	/**
	 * 属性配置类型标志的setter方法
	 */
	public void setConfigType(String configType) {
		this.configType = configType;
	}

	/**
	 * 属性配置描述的getter方法
	 */

	@Column(name = "CONFIGTEXT")
	public String getConfigText() {
		return this.configText;
	}

	/**
	 * 属性配置描述的setter方法
	 */
	public void setConfigText(String configText) {
		this.configText = configText;
	}

	/**
	 * 属性业务键值的getter方法
	 */

	@Column(name = "BUSINESSKEY")
	public String getBusinessKey() {
		return this.businessKey;
	}

	/**
	 * 属性业务键值的setter方法
	 */
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * 属性数据库名称的getter方法
	 */

	@Column(name = "DBNAME")
	public String getDbName() {
		return this.dbName;
	}

	/**
	 * 属性数据库名称的setter方法
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * 属性表名的getter方法
	 */

	@Column(name = "TABLENAME")
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * 属性表名的setter方法
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 属性字段数据类型的getter方法
	 */

	@Column(name = "DATATYPE")
	public String getDataType() {
		return this.dataType;
	}

	/**
	 * 属性字段数据类型的setter方法
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 属性字段名的getter方法
	 */

	@Column(name = "COLUMNNAME")
	public String getColumnName() {
		return this.columnName;
	}

	/**
	 * 属性字段名的setter方法
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * 属性字段描述的getter方法
	 */

	@Column(name = "COLUMNDESC")
	public String getColumnDesc() {
		return this.columnDesc;
	}

	/**
	 * 属性字段描述的setter方法
	 */
	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}

	/**
	 * 属性运算符的getter方法
	 */

	@Column(name = "OPERATOR")
	public String getOperator() {
		return this.operator;
	}

	/**
	 * 属性运算符的setter方法
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 属性比较值的getter方法
	 */

	@Column(name = "VALUE")
	public String getValue() {
		return this.value;
	}

	/**
	 * 属性比较值的setter方法
	 */
	public void setValue(String value) {
		this.value = value;
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

	@Transient
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	@Transient
	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	@Transient
	public List<SwfCondition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<SwfCondition> conditionList) {
		this.conditionList = conditionList;
	}

	@Column(name = "origClass")
	public String getOrigClass() {
		return origClass;
	}

	public void setOrigClass(String origClass) {
		this.origClass = origClass;
	}

	@Column(name = "origProperty")
	public String getOrigProperty() {
		return origProperty;
	}

	public void setOrigProperty(String origProperty) {
		this.origProperty = origProperty;
	}

	@Column(name = "destProperty")
	public String getDestProperty() {
		return destProperty;
	}

	public void setDestProperty(String destProperty) {
		this.destProperty = destProperty;
	}

}