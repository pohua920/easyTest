package com.sinosoft.sys.platform.power.web;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.exception.BusinessException;
import ins.framework.web.Struts2Action;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.sys.platform.power.model.SaaGrade;
import com.sinosoft.sys.platform.power.model.SaaSystem;
import com.sinosoft.sys.platform.power.model.SaaTask;
import com.sinosoft.sys.platform.power.service.facade.SaaGradeService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerHelpService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;
import com.sinosoft.sys.platform.power.util.IConstants;
import com.sinosoft.sys.platform.power.vo.SaaGradeTaskVO;

@SuppressWarnings("serial")
public class SaaGradeAction extends Struts2Action {
	private SaaGradeService saaGradeService;
	private SaaPowerService saaPowerService;
	private SaaPowerHelpService saaPowerHelpService;

	private String gradeID;
	private SaaGrade grade;
	private SaaGrade oldGrade;
	private String userComCode;
	private String editType;

	private Map<SaaSystem, List<SaaGrade>> systemMap;

	private String rootTaskCode;

	private String systemTypeName;
	private List<SaaGrade> grades = new ArrayList<SaaGrade>(0);

	private List<SaaSystem> systemList = new ArrayList<SaaSystem>(0);

	private List<SaaGradeTaskVO> gradeTasks = new ArrayList<SaaGradeTaskVO>(0);

	private List<SaaGradeTaskVO> systemTasks = new ArrayList<SaaGradeTaskVO>(0);

	private List<SaaTask> tasks = new ArrayList<SaaTask>(0);

	private String uppercomcode;
	private String[] treeCheckBox;
	private String[] intranetCheckBox;
	private String[] internetCheckBox;

	private static CacheService cacheManager = CacheManager.getInstance("GradeTask");

	public List<SaaGrade> getGrades() {
		return grades;
	}

	public void setGrades(List<SaaGrade> grades) {
		this.grades = grades;
	}

	public SaaGradeService getSaaGradeService() {
		return saaGradeService;
	}

	public void setSaaGradeService(SaaGradeService gradeService) {
		this.saaGradeService = gradeService;
	}

	public String getGradeID() {
		return gradeID;
	}

	public void setGradeID(String gradeID) {
		this.gradeID = gradeID;
	}

	public SaaGrade getGrade() {
		return grade;
	}

	public void setGrade(SaaGrade grade) {
		this.grade = grade;
	}

	public String[] getTreeCheckBox() {
		return treeCheckBox;
	}

	public void setTreeCheckBox(String[] treeCheckBox) {
		this.treeCheckBox = treeCheckBox;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public List<SaaTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<SaaTask> tasks) {
		this.tasks = tasks;
	}

	public List<SaaGradeTaskVO> getGradeTasks() {
		return gradeTasks;
	}

	public void setGradeTasks(List<SaaGradeTaskVO> gradeTasks) {
		this.gradeTasks = gradeTasks;
	}

	public String initGradeList() {
		String userCode = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode, IConstants.SAA_GRADE, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		systemList = saaPowerService.findSystem();
		systemMap = new LinkedHashMap<SaaSystem, List<SaaGrade>>();
		List<SaaGrade> gradeList = null;
		for (SaaSystem saaSystem : systemList) {
			gradeList = new ArrayList<SaaGrade>(0);
			gradeList = saaGradeService.initSaaGradeListForGrade((String) getSession().getAttribute("UserCode"), saaSystem.getSystemCode());
			systemMap.put(saaSystem, gradeList);
			grades.addAll(gradeList);
		}
		return SUCCESS;
	}

	public String config() {
		// String userCode=(String)getSession().getAttribute("UserCode");
		// boolean hasPower=saaPowerService.checkPower(userCode,
		// IConstants.SAA_GRADE, 1, "");
		// if(!hasPower){
		// throw new BusinessException("很抱歉，你没有做此操作的权限",false);
		// }
		return SUCCESS;
	}

	public String prepareCopyGrade() {
		userComCode = (String) getSession().getAttribute("ComCode");
		oldGrade = saaGradeService.findSaaGradeByGradeID(gradeID);
		gradeTasks = saaGradeService.findSaaGradeTaskVOList(gradeID,(String)getSession().getAttribute("UserCode"));
		systemTasks = saaGradeService.findRootSaaGradeTaskVO((String) getSession().getAttribute("UserCode"));
		return SUCCESS;
	}

	public String preGradeTaskCodeBySys() {
		gradeTasks = saaGradeService
				.findSaaGradeTaskVOListByRootCode(gradeID, (String) getSession().getAttribute("UserCode"), rootTaskCode);
		return SUCCESS;
	}

	public String prepareUpdateGrade() {
		String userCode = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode, IConstants.SAA_GRADE_UPDATE, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		userComCode = (String) getSession().getAttribute("ComCode");
		grade = saaGradeService.findSaaGradeByGradeID(gradeID);
		gradeTasks = saaGradeService.findSaaGradeTaskVOList(gradeID,(String)getSession().getAttribute("UserCode"));
		systemTasks = saaGradeService.findRootSaaGradeTaskVO((String) getSession().getAttribute("UserCode"));
		return SUCCESS;
	}

	public String viewGrade() {
		String userCode = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode, IConstants.SAA_GRADE_QUERY, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		grade = saaGradeService.findSaaGradeByGradeID(gradeID);
		systemTasks = saaGradeService.findRootSaaGradeTaskVO((String) getSession().getAttribute("UserCode"));
		return SUCCESS;
	}

	public String deleteGrade() {
		String userCode = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode, IConstants.SAA_GRADE_DELETE, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaGradeService.deleteSaaGradeByGradeID(gradeID);
		return SUCCESS;
	}

	public String updateGrade() throws Exception {
		String userCode = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode, IConstants.SAA_GRADE_UPDATE, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaGradeService.updateSaaGrade(treeCheckBox, intranetCheckBox, internetCheckBox, grade, (String) getSession().getAttribute(
				"UserCode"));
		cacheManager.clearCache("checkPower");
		return SUCCESS;
	}

	public String prepareInsertGrade() {
		String userCode = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode, IConstants.SAA_GRADE_INSERT, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		userComCode = (String) getSession().getAttribute("ComCode");
		if (!"9999999998".equals(userComCode)) {
			List<String> perAuthCom = (List<String>) saaPowerHelpService.getAuthPermitCom(userCode, null);
			if (perAuthCom.size() < 1) {
				throw new BusinessException("很抱歉，你沒有任何可操作的機構權限，請聯系權限管理員", false);
			}
			uppercomcode = perAuthCom.get(0);
		} else {
			uppercomcode = userComCode;
		}
		grade = new SaaGrade();
		grade.setSystemTypeName(systemTypeName);
		systemTasks = saaGradeService.findRootSaaGradeTaskVO(userCode);
		return SUCCESS;
	}

	public String insertGrade() {
		String userCode = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode, IConstants.SAA_GRADE_INSERT, 1, "");
		if (!hasPower) {
			throw new BusinessException("很抱歉，你沒有做此操作的權限", false);
		}
		saaGradeService
				.addSaaGrade(treeCheckBox, intranetCheckBox, internetCheckBox, grade, (String) getSession().getAttribute("UserCode"));
		return SUCCESS;
	}

	public String[] getIntranetCheckBox() {
		return intranetCheckBox;
	}

	public void setIntranetCheckBox(String[] intranetCheckBox) {
		this.intranetCheckBox = intranetCheckBox;
	}

	public String[] getInternetCheckBox() {
		return internetCheckBox;
	}

	public void setInternetCheckBox(String[] internetCheckBox) {
		this.internetCheckBox = internetCheckBox;
	}

	public SaaGrade getOldGrade() {
		return oldGrade;
	}

	public void setOldGrade(SaaGrade oldGrade) {
		this.oldGrade = oldGrade;
	}

	public String getUserComCode() {
		return userComCode;
	}

	public void setUserComCode(String userComCode) {
		this.userComCode = userComCode;
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

	public String getUppercomcode() {
		return uppercomcode;
	}

	public List<SaaSystem> getSystemList() {
		return systemList;
	}

	public void setSystemList(List<SaaSystem> systemList) {
		this.systemList = systemList;
	}

	public String getSystemTypeName() {
		return systemTypeName;
	}

	public void setSystemTypeName(String systemTypeName) {
		this.systemTypeName = systemTypeName;
	}

	public void setUppercomcode(String uppercomcode) {
		this.uppercomcode = uppercomcode;
	}

	public SaaPowerHelpService getSaaPowerHelpService() {
		return saaPowerHelpService;
	}

	public void setSaaPowerHelpService(SaaPowerHelpService saaPowerHelpService) {
		this.saaPowerHelpService = saaPowerHelpService;
	}

	public Map<SaaSystem, List<SaaGrade>> getSystemMap() {
		return systemMap;
	}

	public void setSystemMap(Map<SaaSystem, List<SaaGrade>> systemMap) {
		this.systemMap = systemMap;
	}
}
