package com.sinosoft.sys.platform.power.service.facade;


import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaUserGrade;
import com.sinosoft.sys.platform.power.vo.SaaRiskObjectVO;
import com.sinosoft.sys.platform.power.vo.SaaUserGradeVO;



public interface SaaUserGradeService {
	public void updateUserGrade(List<SaaUserGradeVO> userGrades, String userCode,String operUserCode);
	//代岗授权获取员工岗位
	public List<SaaUserGradeVO> getInseadUserGradeVOList(String userCode,
			String userCodeOperate);
	public List<SaaUserGradeVO> getUserGradeVOListSysCode(String userCode,
			String userCodeOperate ,String sysCode);
	public List<SaaUserGradeVO> getUserGradeVOList(String userCode,String userCodeOperate);	
	// 员工业务权限->
	public List<SaaCompany> findSaaCompanyList();
	public List<SaaCompany> findSpareCompanyList(String userCode);
	
	public List<SaaCompany> findAgentSpareCompanyList(String userCode);
	public List<SaaCompany> findSaaPermitCompanyList(String userCode,String saaGradeID);	
	public List<SaaCompany> findSaaExceptCompanyList(String userCode,String saaGradeID);
	public String getUserGradeID(String userCode,String saaGradeID);
	public void updateUserServicePower(String[] allowSelect,String[] forbidSelect,String userCode,String saaGradeID,String[] treeCheckBox);
	public List<SaaRiskObjectVO> findSaaRiskObjectVOList(String userCode,String saaGradeID,String userCodeOperate);
	//员工业务权限<-
	public List<SaaUserGrade> getUserGradeList(String userCode);
	public List<SaaUserGrade> getUserGradeListSysCode(String userCode, String systemCode);
	public void saveAllUserGrade(List<SaaUserGrade> list);
	public void deleteAllUserGrade(List<SaaUserGrade> list);
	
	/*判断是否存在该岗位*/
	public boolean isExist(String userCode, Long gradeId);
	/**判断是否是管理员**/
	public boolean isSuperManager(String userCode);
	
}
