package com.sinosoft.sys.platform.company.web;

import com.sinosoft.sys.platform.company.vo.UserDetailInfo;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.service.facade.SaaUserService;

import ins.framework.web.Struts2Action;

public class UserAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	
	private SaaUserService saaUserService;

	private SaaUser prpDuser;

	private UserDetailInfo userDetailInfo;

	private String comCode;

	private String isNullFlag;

	private String userCode;

	private String type;

	private String[] checkbox;

	public SaaUser getPrpDuser() {
		return prpDuser;
	}

	public void setPrpDuser(SaaUser prpDuser) {
		this.prpDuser = prpDuser;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}


	public SaaUserService getSaaUserService() {
		return saaUserService;
	}

	public void setSaaUserService(SaaUserService saaUserService) {
		this.saaUserService = saaUserService;
	}

	public String[] getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String[] checkbox) {
		this.checkbox = checkbox;
	}

	public String getIsNullFlag() {
		return isNullFlag;
	}

	public void setIsNullFlag(String isNullFlag) {
		this.isNullFlag = isNullFlag;
	}

	public UserDetailInfo getUserDetailInfo() {
		return userDetailInfo;
	}

	public void setUserDetailInfo(UserDetailInfo userDetailInfo) {
		this.userDetailInfo = userDetailInfo;
	}

	/** *****************Action 开始 ********************************** */
	public String user() {
		return SUCCESS;
	}

//	public String edit() {
//		if ("edit".equals(type)) {
//			prpDuser = saaUserService.getUserByUserCode(userCode);
//		}
//		return SUCCESS;
//	}
//
//	// 添加
//	public String add() {
//		saaUserService.addUser(prpDuser);
//		return SUCCESS;
//	}
//
//	// 删除
//	public String delete() {
//		if (checkbox != null) {
//			for (int i = 0; i < checkbox.length; i++) {
//				prpDuser = saaUserService.getUserByUserCode(checkbox[i]);
//				prpDuser.setValidStatus("0");
//				saaUserService.updateUser(prpDuser);
//			}
//
//		} else {
//			throw new NullPointerException("未选定删除项！");
//		}
//		return SUCCESS;
//	}
//
//	// 修改
//	public String update() {
//		saaUserService.updateUser(prpDuser);
//		prpDuser = saaUserService.getUserByUserCode(userCode);
//		setType("edit");
//		return SUCCESS;
//	}

//	// 结果列表
//	public String query() {
//		logger.debug("query");
//		if (pageNo == 0) {
//			pageNo = 1;
//		}
//		if (pageSize == 0) {
//			pageSize = INITPAGESIZE;
//		}
//		try {
//			Page page = saaUserService.findByUser(prpDuser, pageNo, pageSize);
//			writeJSONData(page, "userCode", "userName", "prpDcompany.comCode",
//					"newUserCode");
//		} catch (Exception e) {
//			this.writeJSONMsg(e.getMessage());}
//		return null;
//	}
//
//	public String userDetailInfo() {
//		userDetailInfo = saaUserService.getUserDetailInfoByUserCode(userCode);
//		return SUCCESS;
//	}

}
