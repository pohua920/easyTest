package com.sinosoft.undwrt.undwrtDeal.vo;

import java.io.Serializable;
/**
 * 改派任务 SendTaskVo类.
 * 
 * @author gss created on 2014-03-24
 */
public class SendTaskVo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	//核保人员代码
	private String userCode;
	//核保人员名称
	private String userName;
	//权限机构代码
	private String comCode;
	//权限机构名称
	private String comName;
	//核保级别
	private String nodeNo;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getNodeNo() {
		return nodeNo;
	}

	public void setNodeNo(String nodeNo) {
		this.nodeNo = nodeNo;
	}
	
	
}
