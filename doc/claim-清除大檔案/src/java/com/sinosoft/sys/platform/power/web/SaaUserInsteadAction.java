package com.sinosoft.sys.platform.power.web;

import ins.framework.exception.BusinessException;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.model.SaaUserInstead;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserGradeService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserInsteadService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserService;
import com.sinosoft.sys.platform.power.util.IConstants;
import com.sinosoft.sys.platform.power.vo.SaaUserGradeVO;


@SuppressWarnings("serial")
public class SaaUserInsteadAction extends Struts2Action{
	private SaaUserInsteadService saaUserInsteadService;
	private SaaUserGradeService saaUserGradeService;
	private SaaPowerService saaPowerService;
	private SaaUserService saaUserService;
	private String userCode;
	private SaaUser saaUser;
	private SaaUserInstead saaUserInstead;
	private List<SaaUserGradeVO> saaUserGradeVOs = new ArrayList<SaaUserGradeVO>(0);
	private List<SaaUser> saaUserList = new ArrayList<SaaUser>(0);
	public String prepareQueryUser(){
		String userCode1=(String)getSession().getAttribute("UserCode");
		boolean hasPower=saaPowerService.checkPower(userCode1, IConstants.SAA_INSTEAD_QUERY, (Integer)getSession().getAttribute("PowerType"), "");
		if(!hasPower){
			throw new BusinessException("您沒有代崗授權查詢權限！",false);
		}
		return SUCCESS;
	}
//	public String queryUserList(){
//		try {
//			Page page = saaUserInsteadService.getUserList(saaUser, this.pageNo,
//					this.pageSize);
//			this.writeJSONData(page, "userCode", "userName", "comCode",
//					"validStatus");
//		} catch (Exception e) {
//			this.writeJSONMsg(e.getMessage());}
//		return null;
//	}

	public String prepareInsteadUser(){
		String userCode1=(String)getSession().getAttribute("UserCode");
		boolean hasPower=saaPowerService.checkPower(userCode1, IConstants.SAA_INSTEAD_QUERY, (Integer)getSession().getAttribute("PowerType"), "");
		if(!hasPower){
			throw new BusinessException("您沒有代崗授權查詢權限！",false);
		}
		saaUserInstead = saaUserInsteadService.getInstance();
		saaUser = saaUserService.findSaaUserByUserCode(userCode,(String)getSession().getAttribute("UserCode"));
		if (null==saaUser) {
			throw new BusinessException("對不起您沒有員工"+userCode+" 的管理權限！",false);
		}
		saaUserGradeVOs = saaUserGradeService.getInseadUserGradeVOList(userCode,(String)getSession().getAttribute("UserCode"));
		return SUCCESS;
	}
	public String insteadUser(){
		String userCode1=(String)getSession().getAttribute("UserCode");
		boolean hasPower=saaPowerService.checkPower(userCode1, IConstants.SAA_INSTEAD_BACK, (Integer)getSession().getAttribute("PowerType"), "");
		if(!hasPower){
			throw new BusinessException("您沒有代崗授權回收權限！",false);
		}
		saaUserInsteadService.editUserInstead(saaUserInstead);
		return SUCCESS;
	}
	
	public String checkUserInstead(){
		String checkResult = saaUserInsteadService.checkUserInstead(userCode);
		if("editUserInstead".equals(checkResult)){
			saaUserInstead = saaUserInsteadService.getInstance();
			saaUser = saaUserService.findSaaUserByUserCode(userCode,(String)getSession().getAttribute("UserCode"));
			if (null==saaUser) {
				throw new BusinessException("對不起您沒有員工"+userCode+" 的管理權限！",false);
			}
			saaUserGradeVOs = saaUserGradeService.getInseadUserGradeVOList(userCode,(String)getSession().getAttribute("UserCode"));
			return "editUserInstead";
		}else{
			saaUserInstead = saaUserInsteadService.getUserInsteadByUserCode(userCode);
			return "viewUserInstead";
		}
	}
	
	public String updateUserInstead(){
		saaUserInsteadService.updateUserInstead(saaUserInstead);
		return SUCCESS;
	}
	public SaaUserInsteadService getSaaUserInsteadService() {
		return saaUserInsteadService;
	}
	public void setSaaUserInsteadService(SaaUserInsteadService saaUserInsteadService) {
		this.saaUserInsteadService = saaUserInsteadService;
	}
	public SaaUser getSaaUser() {
		return saaUser;
	}
	public void setSaaUser(SaaUser saaUser) {
		this.saaUser = saaUser;
	}
	public SaaUserGradeService getSaaUserGradeService() {
		return saaUserGradeService;
	}
	public void setSaaUserGradeService(SaaUserGradeService saaUserGradeService) {
		this.saaUserGradeService = saaUserGradeService;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public List<SaaUser> getSaaUserList() {
		return saaUserList;
	}
	public void setSaaUserList(List<SaaUser> saaUserList) {
		this.saaUserList = saaUserList;
	}
	public SaaUserInstead getSaaUserInstead() {
		return saaUserInstead;
	}
	public void setSaaUserInstead(SaaUserInstead saaUserInstead) {
		this.saaUserInstead = saaUserInstead;
	}
	public List<SaaUserGradeVO> getSaaUserGradeVOs() {
		return saaUserGradeVOs;
	}
	public void setSaaUserGradeVOs(List<SaaUserGradeVO> saaUserGradeVOs) {
		this.saaUserGradeVOs = saaUserGradeVOs;
	}
	public SaaUserService getSaaUserService() {
		return saaUserService;
	}
	public void setSaaUserService(SaaUserService saaUserService) {
		this.saaUserService = saaUserService;
	}

	public SaaPowerService getSaaPowerService() {
		return saaPowerService;
	}

	public void setSaaPowerService(SaaPowerService saaPowerService) {
		this.saaPowerService = saaPowerService;
	}
}
