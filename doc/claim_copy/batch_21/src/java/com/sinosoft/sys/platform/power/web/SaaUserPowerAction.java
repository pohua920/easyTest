package com.sinosoft.sys.platform.power.web;

import ins.framework.exception.BusinessException;
import ins.framework.web.Struts2Action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerHelpService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserPowerService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserService;
import com.sinosoft.sys.platform.power.util.IConstants;
import com.sinosoft.sys.platform.power.vo.SaaAuthTaskVO;
import com.sinosoft.sys.platform.power.vo.SaaRiskObjectVO;

public class SaaUserPowerAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	private List<SaaUser> saaUserList;
	private SaaUserPowerService saaUserPowerService;
	private SaaPowerService saaPowerService;
	private SaaPowerHelpService saaPowerHelpService;
	private SaaUserService saaUserService;
	private CompanyService companyService;
	private String userCodeTo;
	private String userCodeFrom;
	private String comName;
	private String flag;
	private SaaUser saaUser;
	private String userCode;
	private String password;
	private String authComCode;
	private String authExceptComCode;
	private String authComName;
	private String authExceptComName;
	private String[] treeCheckBox;
	private String comCodes;
	private List<SaaAuthTaskVO> saaAuthTaskVOList = new ArrayList<SaaAuthTaskVO>(
			0);
	private List<SaaRiskObjectVO> saaAuthProductVOList = new ArrayList<SaaRiskObjectVO>(
			0);
	private File powerImportExcel;
	private List<SaaAuthTaskVO> systemTasks = new ArrayList<SaaAuthTaskVO>(0);

	private String rootTaskCode;

	// public String queryUser(){
	// try {
	// Page page = saaUserPowerService.findSaaUserList(saaUser, this.pageNo,
	// this.pageSize,(String)getSession().getAttribute("UserCode"));
	// this.writeJSONData(page, "userCode", "userName",
	// "comCode","validStatus");
	// } catch (Exception e) {
	// this.writeJSONMsg(e.getMessage());
	// }
	// return null;
	// }
	public String login() {
		// String userCode=request.getParameter("userCode");
		// String password=request.getParameter("password");
		// int powerTpye = Integer.parseInt(IpSelectAction.getEnvironment()
		// .getNetType());
		// if (powerTpye>1) {
		// throw new BusinessException("很抱歉，你没有做此操作的权限",false);
		// }
		System.out.println("xxxxxxx");
		saaPowerService.login(userCode, password);
		List<String> preUserCom = (List<String>) saaPowerHelpService
				.getAuthPermitCom(userCode, null);
		if (preUserCom.size() > 0 || userCode.equals("00000000")) {
			// HttpSession session = getSession(true);
			getSession().setAttribute("UserCode", userCode);
			return SUCCESS;
		} else
			throw new BusinessException("很抱歉，你沒有做此操作的權限!", false);
	}

	public String prepareUserPowerCopy() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_POWERFULLCOPY, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaUserList = saaUserPowerService.findSaaUserList((String) getSession()
				.getAttribute("UserCode"));
		return SUCCESS;
	}

	public String userPowerCopy() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_POWERFULLCOPY, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaUserPowerService.copyUserPower(userCodeFrom, userCodeTo,
				(String) getSession().getAttribute("UserCode"), new Date());
		return SUCCESS;
	}

	public String prepareGrantUserPower(){		
		
		saaUser = saaUserService.findSaaUserByUserCode(userCode,(String)getSession().getAttribute("UserCode"));		
		if (null==saaUser) {
			throw new BusinessException("很抱歉，沒有找到人員代碼為"+userCode+" 的系統人員",false);
		}
		SaaCompany prpdCompany = companyService.findPrpDcompanyByComCode(saaUser.getComCode());
		if (null==prpdCompany) {
			throw new BusinessException("很抱歉，該人員公司代碼"+saaUser.getComCode()+" 錯誤，沒有找到相關公司名稱",false);
		}
		comName = prpdCompany.getComCName();
		
		return SUCCESS;
	}

	public String showUserInfo() {
		saaUser = saaUserService.findSaaUserByUserCode(userCode,
				(String) getSession().getAttribute("UserCode"));
		if (null == saaUser) {
			throw new BusinessException("很抱歉，沒有找到人員代碼為" + userCode + " 的系統人員",
					false);
		}
		return SUCCESS;
	}

	public String userPowerAllConfig() {
		return SUCCESS;
	}

	public String prepareGrantUserPowerSelect() {

		String userCode1=(String)getSession().getAttribute("UserCode");
		System.out.println("----------------"+userCode1);
		//String userCode1 = "00000000";
		HttpSession session = getSession();
		session.setAttribute("PowerType", 1);// 添加權限,內網外網類型，1
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		return SUCCESS;
	}
	

	public String taskPowerConfig() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		systemTasks = saaUserPowerService.findRootSaaAuthTaskVOList(userCode1);

		return SUCCESS;
	}

	public String taskPowerConfigByRootCode() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaAuthTaskVOList = saaUserPowerService
				.findSaaAuthTaskVOListByUserCodeRootTask((String) getSession()
						.getAttribute("UserCode"), userCode, rootTaskCode);
		return SUCCESS;
	}

	public String productPowerConfig() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaAuthProductVOList = saaUserPowerService
				.findSaaAuthProductVOListByUserCode((String) getSession()
						.getAttribute("UserCode"), userCode);
		return SUCCESS;
	}

	public String comPowerConfig() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		authComCode = saaUserPowerService.findSaaUserAuthComCode(userCode);
		authExceptComCode = saaUserPowerService
				.findSaaUserAuthExceptComCode(userCode);
		authComName = saaUserPowerService.findSaaUserAuthComName(userCode);
		authExceptComName = saaUserPowerService
				.findSaaUserAuthExceptComName(userCode);
		return SUCCESS;
	}
	
	public String taskPowerGrant() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaUserPowerService.updateTaskPower(treeCheckBox, userCode);
		return SUCCESS;
	}

	public String comPowerGrant() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaUserPowerService.updateComPower(authComCode, authExceptComCode,
				userCode);
		return SUCCESS;
	}

	public String productPowerGrant() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaUserPowerService.updateProductPower(treeCheckBox, userCode);
		return SUCCESS;
	}

	public String prepareImportUserPower() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_POWERDATAIMP, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		return SUCCESS;
	}

	public String userPowerImport() {
		try {
			saaUserPowerService.updateUserPowerByExcel(new FileInputStream(
					powerImportExcel),
					(String) getSession().getAttribute("UserCode"));
		} catch (FileNotFoundException e) {
			throw new BusinessException("----", false);
		}
		return SUCCESS;
	}

	public String prepareExportUserPower() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_POWERDATAEXPL, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		return SUCCESS;
	}

	public String userPowerExport() {
		saaUserPowerService.exportUserPowerToExcel(comCodes);
		try {
			getResponse().sendRedirect("/sales/downloadFiles/UserPowers.xls");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String generateRiskCodes() {
		return SUCCESS;
	}

	public List<SaaUser> getSaaUserList() {
		return saaUserList;
	}

	public void setSaaUserList(List<SaaUser> saaUserList) {
		this.saaUserList = saaUserList;
	}

	public SaaUserPowerService getSaaUserPowerService() {
		return saaUserPowerService;
	}

	public void setSaaUserPowerService(SaaUserPowerService saaUserPowerService) {
		this.saaUserPowerService = saaUserPowerService;
	}

	public String getUserCodeTo() {
		return userCodeTo;
	}

	public void setUserCodeTo(String userCodeTo) {
		this.userCodeTo = userCodeTo;
	}

	public String getUserCodeFrom() {
		return userCodeFrom;
	}

	public void setUserCodeFrom(String userCodeFrom) {
		this.userCodeFrom = userCodeFrom;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public SaaUser getSaaUser() {
		return saaUser;
	}

	public void setSaaUser(SaaUser saaUser) {
		this.saaUser = saaUser;
	}

	public SaaUserService getSaaUserService() {
		return saaUserService;
	}

	public void setSaaUserService(SaaUserService saaUserService) {
		this.saaUserService = saaUserService;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public List<SaaAuthTaskVO> getSaaAuthTaskVOList() {
		return saaAuthTaskVOList;
	}

	public void setSaaAuthTaskVOList(List<SaaAuthTaskVO> saaAuthTaskVOList) {
		this.saaAuthTaskVOList = saaAuthTaskVOList;
	}

	public List<SaaRiskObjectVO> getSaaAuthProductVOList() {
		return saaAuthProductVOList;
	}

	public void setSaaAuthProductVOList(
			List<SaaRiskObjectVO> saaAuthProductVOList) {
		this.saaAuthProductVOList = saaAuthProductVOList;
	}

	public String[] getTreeCheckBox() {
		return treeCheckBox;
	}

	public void setTreeCheckBox(String[] treeCheckBox) {
		this.treeCheckBox = treeCheckBox;
	}

	public String getAuthComCode() {
		return authComCode;
	}

	public void setAuthComCode(String authComCode) {
		this.authComCode = authComCode;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getComName() {
		return comName;
	}

	public String getAuthExceptComCode() {
		return authExceptComCode;
	}

	public void setAuthExceptComCode(String authExceptComCode) {
		this.authExceptComCode = authExceptComCode;
	}

	public String getAuthComName() {
		return authComName;
	}

	public void setAuthComName(String authComName) {
		this.authComName = authComName;
	}

	public String getAuthExceptComName() {
		return authExceptComName;
	}

	public void setAuthExceptComName(String authExceptComName) {
		this.authExceptComName = authExceptComName;
	}

	public File getPowerImportExcel() {
		return powerImportExcel;
	}

	public void setPowerImportExcel(File powerImportExcel) {
		this.powerImportExcel = powerImportExcel;
	}

	public String getComCodes() {
		return comCodes;
	}

	public void setComCodes(String comCodes) {
		this.comCodes = comCodes;
	}

	public SaaPowerService getSaaPowerService() {
		return saaPowerService;
	}

	public void setSaaPowerService(SaaPowerService saaPowerService) {
		this.saaPowerService = saaPowerService;
	}

	public SaaPowerHelpService getSaaPowerHelpService() {
		return saaPowerHelpService;
	}

	public void setSaaPowerHelpService(SaaPowerHelpService saaPowerHelpService) {
		this.saaPowerHelpService = saaPowerHelpService;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<SaaAuthTaskVO> getSystemTasks() {
		return systemTasks;
	}

	public void setSystemTasks(List<SaaAuthTaskVO> systemTasks) {
		this.systemTasks = systemTasks;
	}

	public String getRootTaskCode() {
		return rootTaskCode;
	}

	public void setRootTaskCode(String rootTaskCode) {
		this.rootTaskCode = rootTaskCode;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
}
