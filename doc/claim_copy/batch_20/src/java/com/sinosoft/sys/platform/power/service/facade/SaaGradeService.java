package com.sinosoft.sys.platform.power.service.facade;

import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaGrade;
import com.sinosoft.sys.platform.power.model.SaaGradeTask;
import com.sinosoft.sys.platform.power.model.SaaTask;
import com.sinosoft.sys.platform.power.vo.SaaGradeTaskVO;


public interface SaaGradeService {

	public List<SaaGrade> initSaaGradeList(String userCode);
	
	public List<SaaGrade> initSaaGradeListSysCode(String userCode, String systemTypename);
	
	public List<SaaGrade> initSaaGradeListForGrade(String userCode,String systemTypename);

	public List<SaaGradeTask> findSaaGradeTaskList(String saaGradeID);

	public List<SaaGradeTask> findSaaGradeTasks(String saaGradeID);

	public List<SaaGradeTaskVO> findSaaGradeTaskVOList(String saaGradeID,
			String userCode);

	public List<SaaGradeTaskVO> findSaaGradeTaskVOListByRootCode(
			String saaGradeID, String userCode, String rootTaskCode);

	public List<SaaGradeTaskVO> findRootSaaGradeTaskVO(String userCode);

	public List<SaaTask> findSaaTaskList();

	public SaaGrade findSaaGradeByGradeID(String saaGradeID);

	public List<SaaTask> findSaaTaskListByRootTask(String rootTaskCode);

	public void updateSaaGrade(String[] taskCodes, String[] intranetCheckBox,
			String[] internetCheckBox, SaaGrade saaGrade, String userCodeOper);

	public void addSaaGrade(String[] taskCodes, String[] intranetCheckBox,
			String[] internetCheckBox, SaaGrade saaGrade, String userCodeOper);

	public String getSaaGradeTaskId(Long saaGradeId);

	public List<Long> getSaaGradeTaskIdList(Long saaGradeId);

	public void deleteSaaGradeByGradeID(String saaGradeId);

}
