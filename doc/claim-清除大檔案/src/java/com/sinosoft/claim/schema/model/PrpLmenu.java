package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpLmenu
 */
@Entity
@Table(name = "PRPLMENU")
public class PrpLmenu implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性菜单功能代码 */
	private String funcID;

	/** 属性菜单级别 */
	private String funcLevel;

	/** 属性上一级菜单 */
	private String parentID;

	/** 属性中文名称 */
	private String funcCName;

	/** 属性英文名称 */
	private String funcEName;

	/** 属性功能类型 */
	private String funcType;

	/** 属性使用标志 */
	private String useFlag;

	/** 属性菜单标题图片 */
	private String image;

	/** 属性展开菜单的图片 */
	private String imageExpand;

	/** 属性合上菜单的图片 */
	private String imageCollapse;

	/** 属性任务代码 */
	private String taskCode;

	/** 属性权限检验代码 */
	private String checkCode;

	/** 属性展开菜单的图标 */
	private String iconExpand;

	/** 属性合上菜单的图标 */
	private String iconCollapse;

	/** 属性参数 */
	private String param;

	/** 属性模块名称 */
	private String modelName;

	/** 属性排序 */
	private BigDecimal sortNo;

	/** 属性标志 */
	private String flag;
	Collection<?> menuFuncList;

	// 将菜单分为三级存放 modify by qinyongli 2006-02-27
	Collection<?> menuLevel1 = new ArrayList<Object>(); // 一级菜单
	Collection<?> menuLevel2 = new ArrayList<Object>(); // 二级菜单
	Collection<?> menuLevel3 = new ArrayList<Object>(); // 三级菜单

	/**
	 * 类PrpLmenu的默认构造方法
	 */
	public PrpLmenu() {
	}

	/**
	 * 属性菜单功能代码的getter方法
	 */
	@Id
	@Column(name = "FUNCID")
	public String getFuncID() {
		return this.funcID;
	}

	/**
	 * 属性菜单功能代码的setter方法
	 */
	public void setFuncID(String funcID) {
		this.funcID = funcID;
	}

	/**
	 * 属性菜单级别的getter方法
	 */

	@Column(name = "FUNCLEVEL")
	public String getFuncLevel() {
		return this.funcLevel;
	}

	/**
	 * 属性菜单级别的setter方法
	 */
	public void setFuncLevel(String funcLevel) {
		this.funcLevel = funcLevel;
	}

	/**
	 * 属性上一级菜单的getter方法
	 */

	@Column(name = "PARENTID")
	public String getParentID() {
		return this.parentID;
	}

	/**
	 * 属性上一级菜单的setter方法
	 */
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	/**
	 * 属性中文名称的getter方法
	 */

	@Column(name = "FUNCCNAME")
	public String getFuncCName() {
		return this.funcCName;
	}

	/**
	 * 属性中文名称的setter方法
	 */
	public void setFuncCName(String funcCName) {
		this.funcCName = funcCName;
	}

	/**
	 * 属性英文名称的getter方法
	 */

	@Column(name = "FUNCENAME")
	public String getFuncEName() {
		return this.funcEName;
	}

	/**
	 * 属性英文名称的setter方法
	 */
	public void setFuncEName(String funcEName) {
		this.funcEName = funcEName;
	}

	/**
	 * 属性功能类型的getter方法
	 */

	@Column(name = "FUNCTYPE")
	public String getFuncType() {
		return this.funcType;
	}

	/**
	 * 属性功能类型的setter方法
	 */
	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}

	/**
	 * 属性使用标志的getter方法
	 */

	@Column(name = "USEFLAG")
	public String getUseFlag() {
		return this.useFlag;
	}

	/**
	 * 属性使用标志的setter方法
	 */
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	/**
	 * 属性菜单标题图片的getter方法
	 */

	@Column(name = "IMAGE")
	public String getImage() {
		return this.image;
	}

	/**
	 * 属性菜单标题图片的setter方法
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 属性展开菜单的图片的getter方法
	 */

	@Column(name = "IMAGEEXPAND")
	public String getImageExpand() {
		return this.imageExpand;
	}

	/**
	 * 属性展开菜单的图片的setter方法
	 */
	public void setImageExpand(String imageExpand) {
		this.imageExpand = imageExpand;
	}

	/**
	 * 属性合上菜单的图片的getter方法
	 */

	@Column(name = "IMAGECOLLAPSE")
	public String getImageCollapse() {
		return this.imageCollapse;
	}

	/**
	 * 属性合上菜单的图片的setter方法
	 */
	public void setImageCollapse(String imageCollapse) {
		this.imageCollapse = imageCollapse;
	}

	/**
	 * 属性任务代码的getter方法
	 */

	@Column(name = "TASKCODE")
	public String getTaskCode() {
		return this.taskCode;
	}

	/**
	 * 属性任务代码的setter方法
	 */
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	/**
	 * 属性权限检验代码的getter方法
	 */

	@Column(name = "CHECKCODE")
	public String getCheckCode() {
		return this.checkCode;
	}

	/**
	 * 属性权限检验代码的setter方法
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	/**
	 * 属性展开菜单的图标的getter方法
	 */

	@Column(name = "ICONEXPAND")
	public String getIconExpand() {
		return this.iconExpand;
	}

	/**
	 * 属性展开菜单的图标的setter方法
	 */
	public void setIconExpand(String iconExpand) {
		this.iconExpand = iconExpand;
	}

	/**
	 * 属性合上菜单的图标的getter方法
	 */

	@Column(name = "ICONCOLLAPSE")
	public String getIconCollapse() {
		return this.iconCollapse;
	}

	/**
	 * 属性合上菜单的图标的setter方法
	 */
	public void setIconCollapse(String iconCollapse) {
		this.iconCollapse = iconCollapse;
	}

	/**
	 * 属性参数的getter方法
	 */

	@Column(name = "PARAM")
	public String getParam() {
		return this.param;
	}

	/**
	 * 属性参数的setter方法
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * 属性模块名称的getter方法
	 */

	@Column(name = "MODELNAME")
	public String getModelName() {
		return this.modelName;
	}

	/**
	 * 属性模块名称的setter方法
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * 属性排序的getter方法
	 */

	@Column(name = "SORTNO")
	public BigDecimal getSortNo() {
		return this.sortNo;
	}

	/**
	 * 属性排序的setter方法
	 */
	public void setSortNo(BigDecimal sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Transient
	public Collection<?> getMenuLevel1() {
		return menuLevel1;
	}

	public void setMenuLevel1(Collection<?> menuLevel1) {
		this.menuLevel1 = menuLevel1;
	}

	@Transient
	public Collection<?> getMenuLevel2() {
		return menuLevel2;
	}

	public void setMenuLevel2(Collection<?> menuLevel2) {
		this.menuLevel2 = menuLevel2;
	}

	@Transient
	public Collection<?> getMenuLevel3() {
		return menuLevel3;
	}

	public void setMenuLevel3(Collection<?> menuLevel3) {
		this.menuLevel3 = menuLevel3;
	}

	/**
	 * @return Returns the menuFuncList.
	 */
	@Transient
	public Collection<?> getMenuFuncList() {
		return menuFuncList;
	}

	/**
	 * @param menuFuncList The menuFuncList to set.
	 */
	public void setMenuFuncList(Collection<?> menuFuncList) {
		this.menuFuncList = menuFuncList;
	}

}
