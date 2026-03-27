package com.sinosoft.sys.platform.power.web;

import ins.framework.exception.BusinessException;
import ins.framework.web.Struts2Action;

import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaTask;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.service.facade.SaaAuthTaskService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;
import com.sinosoft.sys.platform.power.service.facade.SaaTaskService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserService;
import com.sinosoft.sys.platform.power.util.IConstants;



@SuppressWarnings("serial")
public class SaaAuthTaskAction extends Struts2Action{
	private SaaPowerService saaPowerService;
	private SaaUser saaUser;
	private String userCode;
	private SaaUserService saaUserService;
	private SaaTaskService saaTaskService;
	private SaaAuthTaskService saaAuthTaskService;
	
	/** 授权功能初始�? */
	public String initAuthTask() {
		String userCodeTmp=(String)getSession().getAttribute("UserCode");
		boolean hasPower=saaPowerService.checkPower(userCodeTmp, IConstants.SAA_AUTHTASK, (Integer)getSession().getAttribute("PowerType"), "");
		if(!hasPower){
			throw new BusinessException("您没有该功能的权限！",false);
		}
		List<SaaTask> authTaskList = saaTaskService.getAllDate();
		saaAuthTaskService.saveSaaAuthTask(authTaskList, userCodeTmp);
		return SUCCESS;
	}
	
	
/***************************************************************************/
	public SaaPowerService getSaaPowerService() {
		return saaPowerService;
	}

	public void setSaaPowerService(SaaPowerService saaPowerService) {
		this.saaPowerService = saaPowerService;
	}

	public SaaUser getSaaUser() {
		return saaUser;
	}

	public void setSaaUser(SaaUser saaUser) {
		this.saaUser = saaUser;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public SaaUserService getSaaUserService() {
		return saaUserService;
	}

	public void setSaaUserService(SaaUserService saaUserService) {
		this.saaUserService = saaUserService;
	}


	public SaaTaskService getSaaTaskService() {
		return saaTaskService;
	}


	public void setSaaTaskService(SaaTaskService saaTaskService) {
		this.saaTaskService = saaTaskService;
	}


	public SaaAuthTaskService getSaaAuthTaskService() {
		return saaAuthTaskService;
	}


	public void setSaaAuthTaskService(SaaAuthTaskService saaAuthTaskService) {
		this.saaAuthTaskService = saaAuthTaskService;
	}
	
}
