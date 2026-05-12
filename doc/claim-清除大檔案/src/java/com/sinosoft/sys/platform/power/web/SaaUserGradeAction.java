package com.sinosoft.sys.platform.power.web;

import ins.framework.exception.BusinessException;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.app.common.model.PerfCode;
import com.sinosoft.app.common.service.facade.PerfCodeService;
import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaBusinessline;
import com.sinosoft.sys.platform.power.model.SaaClass;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaGrade;
import com.sinosoft.sys.platform.power.model.SaaRisk;
import com.sinosoft.sys.platform.power.model.SaaSystem;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.service.facade.SaaGradeService;
import com.sinosoft.sys.platform.power.service.facade.SaaInsuranceCategoryService;
import com.sinosoft.sys.platform.power.service.facade.SaaInsuranceService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;
import com.sinosoft.sys.platform.power.service.facade.SaaProductLineService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserGradeService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserService;
import com.sinosoft.sys.platform.power.util.IConstants;
import com.sinosoft.sys.platform.power.vo.SaaGradeTaskVO;
import com.sinosoft.sys.platform.power.vo.SaaRiskObjectVO;
import com.sinosoft.sys.platform.power.vo.SaaUserGradeVO;

@SuppressWarnings("serial")
public class SaaUserGradeAction extends Struts2Action {
	private SaaUserGradeService saaUserGradeService;
	private SaaGradeService saaGradeService;
	private SaaPowerService saaPowerService;
	private SaaUser saaUser;
	private String userCode;
	private String sessionUserCode;
	private SaaGrade saaGrade;
	private String saaGradeID;
	private String rootTaskCode;
	private String[] treeCheckBox;
	private List<SaaUserGradeVO> userGrades = new ArrayList<SaaUserGradeVO>(0);
	private List<SaaGradeTaskVO> gradeTasks = new ArrayList<SaaGradeTaskVO>(0);
	private List<SaaGradeTaskVO> systemTasks = new ArrayList<SaaGradeTaskVO>(0);
	private List<SaaRiskObjectVO> saaRiskObjectVOList = new ArrayList<SaaRiskObjectVO>(0);
	private SaaProductLineService saaProductLineService;
	private SaaInsuranceService saaInsuranceService;
	private SaaInsuranceCategoryService saaInsuranceCategoryService;
	private List<SaaCompany> saaSpareCompanys = new ArrayList<SaaCompany>(0);
	private List<SaaCompany> saaPermitCompanys = new ArrayList<SaaCompany>(0);
	private List<SaaCompany> saaExceptCompanys = new ArrayList<SaaCompany>(0);
	private List<SaaBusinessline> saaProductLines = new ArrayList<SaaBusinessline>(0);
	private List<SaaRisk> saaInsurances = new ArrayList<SaaRisk>(0);
	private List<SaaClass> saaInsuranceCategories = new ArrayList<SaaClass>(0);
	private String[] forbidSelect;
	private String[] allowSelect;
	private SaaUserService saaUserService;
	private List<SaaUser> saaUserSameComs = new ArrayList<SaaUser>(0);

	private List<SaaSystem> systemList = new ArrayList<SaaSystem>(0);
	private Map<SaaSystem, List<SaaUserGradeVO>> systemMap;
	/** add by linsiming 20120510 增加机构类型显示 perfCodeTypeLists/perfCodeService **/
	private List<PerfCode> perfCodeTypeLists;
	private PerfCodeService perfCodeService;
	private CompanyService companyService;

	public List<SaaSystem> getSystemList() {
		return systemList;
	}

	public void setSystemList(List<SaaSystem> systemList) {
		this.systemList = systemList;
	}

	public String prepareQueryUser() {
		return SUCCESS;
	}

	public String prepareQueryAgentUser() {
		return SUCCESS;
	}

	public String prepareUpdateUserGrade() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1, IConstants.EWPS_SYSTEM_POWERMANAGE_POWER, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaUser = saaUserService.findSaaUserByUserCode(userCode, (String) getSession().getAttribute("UserCode"));
		if (null == saaUser) {
			throw new BusinessException("很抱歉，沒有找到人員代碼為" + userCode + " 的系統人員", false);
		}
		systemList = saaPowerService.findSystem();
		systemMap = new LinkedHashMap<SaaSystem, List<SaaUserGradeVO>>();
		List<SaaUserGradeVO> userGradesVOs = null;
		for (SaaSystem saaSystem : systemList) {
			userGradesVOs = new ArrayList<SaaUserGradeVO>(0);
			// userGradesVOs =
			// saaUserGradeService.getUserGradeVOListSysCode(userCode,(String)getSession().getAttribute("UserCode"),saaSystem.getSystemCode());
			// 全部岗位都是总公司级别的，所以用00000000查询。岗位不控制，用业务权限控制权限
			userGradesVOs = saaUserGradeService.getUserGradeVOListSysCode(userCode, "00000000", saaSystem.getSystemCode());
			systemMap.put(saaSystem, userGradesVOs);
			userGrades.addAll(userGradesVOs);//
		}
		return SUCCESS;
	}

	public String updateUserGrade() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1, IConstants.EWPS_SYSTEM_SAA_GRADE, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaUserGradeService.updateUserGrade(userGrades, userCode, (String) getSession().getAttribute("UserCode"));
		SaaCompany saaCompany = companyService.getPrpDcompanyByComCode(userCode);
		if(saaCompany!=null){
			for (SaaUserGradeVO itemGrade : userGrades) {
				if ("5".equals(itemGrade.getGradeCode()) && itemGrade.isChecked()) {
					System.out.println("含有部門副職");
					saaCompany.setValidStatus("1");
					companyService.updateVirtualCompany(saaCompany);
					break;
				}else if ("5".equals(itemGrade.getGradeCode()) && !itemGrade.isChecked()) {
					System.out.println("不含有部門副職");
					saaCompany.setValidStatus("0");
					companyService.updateVirtualCompany(saaCompany);
					break;
				}
			}
		}
		return SUCCESS;
	}

	public String prepareUpdateUserGradePower() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1, IConstants.EWPS_SYSTEM_SAA_POWER, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		return SUCCESS;
	}

	public String initUserGradeList() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1, IConstants.EWPS_SYSTEM_SAA_POWER, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaUser = saaUserService.findSaaUserByUserCode(userCode, (String) getSession().getAttribute("UserCode"));
		if (null == saaUser) {
			throw new BusinessException("很抱歉，沒有找到人員代碼為" + userCode + " 的系統人員", false);
		}
		// modify by luogang 20111109 reason:岗位不控制权限，由机构控制业务权限
		// userGrades =
		// saaUserGradeService.getUserGradeVOList(userCode,(String)getSession().getAttribute("UserCode"));
		userGrades = saaUserGradeService.getUserGradeVOList(userCode, "00000000");
		
		// modify by linsiming 20120611 去掉总部_员工角色权限,主要因为员工不需要配置业务机构范围 start
		for (SaaUserGradeVO itemGrade : userGrades) {
			if("9".equals(itemGrade.getGradeCode())&& itemGrade.isChecked()){
				itemGrade.setChecked(false);
			}
		}
		// modify by linsiming 20120611 去掉总部_员工角色权限,主要因为员工不需要配置业务机构范围 end
		return SUCCESS;
	}

	public String viewUserGrade() {
		sessionUserCode = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(sessionUserCode, IConstants.EWPS_SYSTEM_SAA_POWER, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaGrade = saaGradeService.findSaaGradeByGradeID(saaGradeID);
		// modify by luogang 20111109 reason:岗位不控制权限，由机构控制业务权限
		// gradeTasks =
		// saaGradeService.findSaaGradeTaskVOList(saaGradeID,(String)getSession().getAttribute("UserCode"));
		// systemTasks=saaGradeService.findRootSaaGradeTaskVO((String)getSession().getAttribute("UserCode"));
		gradeTasks = saaGradeService.findSaaGradeTaskVOList(saaGradeID, "00000000");
		systemTasks = saaGradeService.findRootSaaGradeTaskVO("00000000");
		return SUCCESS;
	}

	public String preGradeTaskCodeBySys() {
		gradeTasks = saaGradeService.findSaaGradeTaskVOListByRootCode(saaGradeID, (String) getSession().getAttribute("UserCode"), rootTaskCode);
		return SUCCESS;
	}

	public String viewUserGradePower() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1, IConstants.EWPS_SYSTEM_SAA_POWER, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		// saaSpareCompanys =
		// saaUserGradeService.findSpareCompanyList(userCode1);
		saaPermitCompanys = saaUserGradeService.findSaaPermitCompanyList(userCode, saaGradeID);
		saaExceptCompanys = saaUserGradeService.findSaaExceptCompanyList(userCode, saaGradeID);
		saaRiskObjectVOList = saaUserGradeService.findSaaRiskObjectVOList(userCode, saaGradeID, userCode1);
		saaUserSameComs = saaUserService.findSaaUserSameComList(userCode, userCode1);

		/**  add by linsiming 20120510 增加机构类型显示 perfCodeTypeLists start **/
		try {
			perfCodeTypeLists = perfCodeService.findPerfCodeList("ComType");
		} catch (Exception e) {
			e.printStackTrace();
		}
		/** add by linsiming 20120510 增加机构类型显示 perfCodeTypeLists end **/
		return SUCCESS;
	}

	public String viewAgentUserGradePower() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1, IConstants.EWPS_SYSTEM_SAA_POWER, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaSpareCompanys = saaUserGradeService.findAgentSpareCompanyList(userCode1);
		saaPermitCompanys = saaUserGradeService.findSaaPermitCompanyList(userCode, saaGradeID);
		saaExceptCompanys = saaUserGradeService.findSaaExceptCompanyList(userCode, saaGradeID);
		saaRiskObjectVOList = saaUserGradeService.findSaaRiskObjectVOList(userCode, saaGradeID, userCode1);
		saaUserSameComs = saaUserService.findSaaUserSameComList(userCode, userCode1);
		return SUCCESS;
	}

	public String viewGrade() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1, IConstants.EWPS_SYSTEM_SAA_POWER, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaGrade = saaGradeService.findSaaGradeByGradeID(saaGradeID);
		gradeTasks = saaGradeService.findSaaGradeTaskVOList(saaGradeID, (String) getSession().getAttribute("UserCode"));
		return SUCCESS;
	}

	public String prepareUpdateUserServicePower() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1, IConstants.EWPS_SYSTEM_SAA_POWER, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaUser = saaUserService.findSaaUserByUserCode(userCode, (String) getSession().getAttribute("UserCode"));
		if (null == saaUser) {
			throw new BusinessException("很抱歉，沒有找到人員代碼為" + userCode + " 的系統人員", false);
		}
		return SUCCESS;
	}

	public String updateUserServicePower() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1, IConstants.EWPS_SYSTEM_SAA_POWER, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaUserGradeService.updateUserServicePower(allowSelect, forbidSelect, userCode, saaGradeID, treeCheckBox);
		if("5".equals(saaGradeID)){
			updateVirtualCompany(userCode,userCode1);
		}
		
		
		return SUCCESS;
	}

	private void updateVirtualCompany(String operateCode,String currentUserCode) {
		SaaCompany saaCompany = companyService.getPrpDcompanyByComCode(operateCode);
		if(saaCompany==null){
			if(allowSelect!=null){
				String officeCode = allowSelect[0];
				SaaUser saaUser = saaUserService.findSaaUserByUserCode(operateCode);//userCode
				SaaCompany saaCompanyTemp = companyService.getPrpDcompanyByComCode(officeCode);
				SaaCompany newSaaCompany = new SaaCompany();
				newSaaCompany.setComCode(saaUser.getUserCode());
				newSaaCompany.setComCName(saaUser.getUserName());
				newSaaCompany.setComType("03");
				newSaaCompany.setValidStatus("1");
				newSaaCompany.setVirtualFlag("3");
				newSaaCompany.setUpperComCode(saaCompanyTemp.getUpperComCode());
				newSaaCompany.setCreateCode(currentUserCode);
				newSaaCompany.setCreateTime(new Date());
				newSaaCompany.setUpdateCode(currentUserCode);
				newSaaCompany.setUpdateDate(new Date());
				companyService.insertVirtualCompany(newSaaCompany);
			}
		}else{
			if (allowSelect != null) {
				String officeCode = allowSelect[0];
				if (officeCode != null) {
					SaaUser saaUser = saaUserService.findSaaUserByUserCode(operateCode);// userCode
					SaaCompany saaCompanyTemp = companyService.getPrpDcompanyByComCode(officeCode);
					saaCompany.setComCode(saaUser.getUserCode());
					saaCompany.setComCName(saaUser.getUserName());
					saaCompany.setComType("03");
					saaCompany.setValidStatus("1");
					saaCompany.setVirtualFlag("3");
					saaCompany.setUpperComCode(saaCompanyTemp.getUpperComCode());
					saaCompany.setCreateTime(new Date());
					saaCompany.setUpdateDate(new Date());
					companyService.updateVirtualCompany(saaCompany);
				}
			}
		}
	}

	/** ************************************************************************ */

	public SaaUserGradeService getSaaUserGradeService() {
		return saaUserGradeService;
	}

	public void setSaaUserGradeService(SaaUserGradeService saaUserGradeService) {
		this.saaUserGradeService = saaUserGradeService;
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

	public List<SaaUserGradeVO> getUserGrades() {
		return userGrades;
	}

	public void setUserGrades(List<SaaUserGradeVO> userGrades) {
		this.userGrades = userGrades;
	}

	public SaaGradeService getSaaGradeService() {
		return saaGradeService;
	}

	public void setSaaGradeService(SaaGradeService saaGradeService) {
		this.saaGradeService = saaGradeService;
	}

	public String getSaaGradeID() {
		return saaGradeID;
	}

	public void setSaaGradeID(String saaGradeID) {
		this.saaGradeID = saaGradeID;
	}

	public SaaGrade getSaaGrade() {
		return saaGrade;
	}

	public void setSaaGrade(SaaGrade saaGrade) {
		this.saaGrade = saaGrade;
	}

	public List<SaaGradeTaskVO> getGradeTasks() {
		return gradeTasks;
	}

	public void setGradeTasks(List<SaaGradeTaskVO> gradeTasks) {
		this.gradeTasks = gradeTasks;
	}

	public SaaProductLineService getSaaProductLineService() {
		return saaProductLineService;
	}

	public void setSaaProductLineService(SaaProductLineService saaProductLineService) {
		this.saaProductLineService = saaProductLineService;
	}

	public SaaInsuranceService getSaaInsuranceService() {
		return saaInsuranceService;
	}

	public void setSaaInsuranceService(SaaInsuranceService saaInsuranceService) {
		this.saaInsuranceService = saaInsuranceService;
	}

	public SaaInsuranceCategoryService getSaaInsuranceCategoryService() {
		return saaInsuranceCategoryService;
	}

	public void setSaaInsuranceCategoryService(SaaInsuranceCategoryService saaInsuranceCategoryService) {
		this.saaInsuranceCategoryService = saaInsuranceCategoryService;
	}

	public List<SaaRisk> getSaaInsurances() {
		return saaInsurances;
	}

	public void setSaaInsurances(List<SaaRisk> saaInsurances) {
		this.saaInsurances = saaInsurances;
	}

	public List<SaaClass> getSaaInsuranceCategories() {
		return saaInsuranceCategories;
	}

	public void setSaaInsuranceCategories(List<SaaClass> saaInsuranceCategories) {
		this.saaInsuranceCategories = saaInsuranceCategories;
	}

	public List<SaaBusinessline> getSaaProductLines() {
		return saaProductLines;
	}

	public void setSaaProductLines(List<SaaBusinessline> saaProductLines) {
		this.saaProductLines = saaProductLines;
	}

	/*
	 * public String getSaaUserGradeID() { return saaUserGradeID; } public void
	 * setSaaUserGradeID(String saaUserGradeID) { this.saaUserGradeID =
	 * saaUserGradeID; }
	 */
	public String[] getForbidSelect() {
		return forbidSelect;
	}

	public void setForbidSelect(String[] forbidSelect) {
		this.forbidSelect = forbidSelect;
	}

	public String[] getAllowSelect() {
		return allowSelect;
	}

	public void setAllowSelect(String[] allowSelect) {
		this.allowSelect = allowSelect;
	}

	public List<SaaRiskObjectVO> getSaaRiskObjectVOList() {
		return saaRiskObjectVOList;
	}

	public void setSaaRiskObjectVOList(List<SaaRiskObjectVO> saaRiskObjectVOList) {
		this.saaRiskObjectVOList = saaRiskObjectVOList;
	}

	public List<SaaUser> getSaaUserSameComs() {
		return saaUserSameComs;
	}

	public void setSaaUserSameComs(List<SaaUser> saaUserSameComs) {
		this.saaUserSameComs = saaUserSameComs;
	}

	public SaaUserService getSaaUserService() {
		return saaUserService;
	}

	public void setSaaUserService(SaaUserService saaUserService) {
		this.saaUserService = saaUserService;
	}

	public String[] getTreeCheckBox() {
		return treeCheckBox;
	}

	public void setTreeCheckBox(String[] treeCheckBox) {
		this.treeCheckBox = treeCheckBox;
	}

	public List<SaaCompany> getSaaSpareCompanys() {
		return saaSpareCompanys;
	}

	public void setSaaSpareCompanys(List<SaaCompany> saaSpareCompanys) {
		this.saaSpareCompanys = saaSpareCompanys;
	}

	public List<SaaCompany> getSaaPermitCompanys() {
		return saaPermitCompanys;
	}

	public void setSaaPermitCompanys(List<SaaCompany> saaPermitCompanys) {
		this.saaPermitCompanys = saaPermitCompanys;
	}

	public List<SaaCompany> getSaaExceptCompanys() {
		return saaExceptCompanys;
	}

	public void setSaaExceptCompanys(List<SaaCompany> saaExceptCompanys) {
		this.saaExceptCompanys = saaExceptCompanys;
	}

	public SaaPowerService getSaaPowerService() {
		return saaPowerService;
	}

	public void setSaaPowerService(SaaPowerService saaPowerService) {
		this.saaPowerService = saaPowerService;
	}

	public List<SaaGradeTaskVO> getSystemTasks() {
		return systemTasks;
	}

	public void setSystemTasks(List<SaaGradeTaskVO> systemTasks) {
		this.systemTasks = systemTasks;
	}

	public String getRootTaskCode() {
		return rootTaskCode;
	}

	public void setRootTaskCode(String rootTaskCode) {
		this.rootTaskCode = rootTaskCode;
	}

	public Map<SaaSystem, List<SaaUserGradeVO>> getSystemMap() {
		return systemMap;
	}

	public void setSystemMap(Map<SaaSystem, List<SaaUserGradeVO>> systemMap) {
		this.systemMap = systemMap;
	}

	public String getSessionUserCode() {
		return sessionUserCode;
	}

	public void setSessionUserCode(String sessionUserCode) {
		this.sessionUserCode = sessionUserCode;
	}

	public void setPerfCodeTypeLists(List<PerfCode> perfCodeTypeLists) {
		this.perfCodeTypeLists = perfCodeTypeLists;
	}

	public List<PerfCode> getPerfCodeTypeLists() {
		return perfCodeTypeLists;
	}

	public void setPerfCodeService(PerfCodeService perfCodeService) {
		this.perfCodeService = perfCodeService;
	}

	public PerfCodeService getPerfCodeService() {
		return perfCodeService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

}
