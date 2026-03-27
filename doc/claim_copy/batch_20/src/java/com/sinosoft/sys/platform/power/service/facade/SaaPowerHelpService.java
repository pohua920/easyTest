package com.sinosoft.sys.platform.power.service.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SaaPowerHelpService {
	public boolean checkTaskByGrade(String taskCode, List<Long> gradeList);

	public String getComPerRange(String permitCom);

	public String getComExcRange(String exceCom);

	public Collection<String> getPermitCom(long id, String comLevelStr);

	public Collection<String> getPermitComWithOut(long id, String comLevelStr);

	public Collection<String> getAuthPermitCom(String userCode, String comLevelStr);

	public Collection<String> getAuthPermitAgentCom(String userCode, String comLevelStr);

	@SuppressWarnings("unchecked")
	public String removeDuplicateWithOrder(List list);

	public String getExceCom(long id, String comLevelStr);

	public Collection<String> getAuthExceCom(String userCode, String comLevelStr);

	public Collection<String> getRisksByProductCode(long id);

	public List<String> getExceComList(long id, String comLevelStr);

	@SuppressWarnings("unchecked")
	public Collection<String> getAuthRisksByProductCode(String userCode);

	/**
	 * 
	 * @param comCodes
	 * @param comLevelStr
	 * @return
	 */
	public List<String> getSubCompanyCodeList(List<String> comCodes, String comLevelStr);

	@SuppressWarnings("unchecked")
	public Map builderGradeMap(String taskCode, String taskType);

	public String getUpperComcode(String comcode);

	public void clearAllServerCacheManager();

	/**
	 * 
	 * @param comCode
	 * @param comLevelStr
	 * @return
	 */
	public List<String> getSubAllCompanyCode(String comCode, String comLevelStr);

	public List<String> getMixed(List<String> listA, List<String> listB);
}
