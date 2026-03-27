package com.sinosoft.sys.platform.power.service.facade;

import ins.framework.common.Page;

import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaGrade;
import com.sinosoft.sys.platform.power.model.SaaSystem;



/**
 * @author 中科软 
 *
 */
public interface SaaPowerService {
	
	
	
	public SaaSystem findSystemBySystemCode(String code);
	/**
	 */
	public boolean checkPower(String userCode, String taskCode,int powerType,String gradesIdString);
	/**
	 * 
	 * @param strings[0] userCode
	 * @param strings[1] userCodeFields
	 * @param strings[2] companyCodeFields
	 * @param strings[3] riskCodeFields
	 * @param strings[4] adminFlag
	 * @return String 
	 */
	public String addAuthPower(String[] strings);
	/**
	 * @param userCode 
	 * @param taskcode 
	 * @param userCodeFields 
	 * @param companyCodeFields 
	 * @param riskCodeFields 
	 * @param gradesIdString 
	 * @return String 
	 */
	public String addPower(String userCode, String taskCode,String userCodeFields, 
			String companyCodeFields,String riskCodeFields,String gradesIdString, String businssComCode);
	/**
	 */
	public List<String> getInsteadUserList(String userCode);
	/**
	 */
	public String getInsteadGradesIdString(String userCode,String insteadUserCode);
	/**
	 */
	public void login(String userCode, String password);
	
	
	public SaaGrade getLowUndwrtGrade(String taskCode, String comCode,
			String taskType);
	/**
	 */
	public List<String> getPowerUser(String taskCode, String comCode ,
			String riskCode);
	/**
	 */
	public List<String> getPowerUserCodes(String taskCode, String comCode ,
			String riskCode);
	/**
	 */
	public List<String> getPowerComList(String userCode, String taskCode);
	
	
	/**
	 * 
	 * @param userCode
	 * @param note
	 * @return
	 * 		taskCodeList<String>
	 */
	public List<String> findUserTaskCodeByNote(String userCode, String note);
	
	/**
	 */
	public void clearAllCacheManager();
	
	/**
	 * @param comCode
	 * @param companyCodeFields
	 * @return
	 */
	public String addBusinessPower(String comCode, String companyCodeFields);
	
	/**
	 * 判断当前操作员是否有某种操作权限
	 * @param taskCode
	 * @param userCode
	 * @return
	 */
	public boolean checkTask(String taskCode, String userCode);
	
	public List<SaaSystem> findSystem();
	
	public Page findPowerUserList(String comCode,String fieldValue,String taskCode,int pageNo, int pageSize);
	/**
	 * 查找该成员memberCode底下拥有该权限的用户
	 * @param comCode
	 * @param taskCode
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findPowerUserUnderComCode(String comCode,String fieldValue,String taskCode,int pageNo, int pageSize);
}
