package com.sinosoft.sys.platform.power.service.facade;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;


import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.model.SaaUserGrade;
import com.sinosoft.sys.platform.power.vo.SaaAuthTaskVO;
import com.sinosoft.sys.platform.power.vo.SaaRiskObjectVO;

public interface SaaUserPowerService {
	public List<SaaUser> findSaaUserList(String userCode);

	public void copyUserPower(String userCodeFrom, String userCodeTo, String operUserCode, Date date);

	// public Page findSaaUserList(SaaUser saaUser,int pageNO,intpageSize,String userCodeOperate);
	
	public List<SaaAuthTaskVO> findSaaAuthTaskVOListByUserCode(String userCodeOperate, String userCode);

	public List<SaaRiskObjectVO> findSaaAuthProductVOListByUserCode(String userCodeOperate, String userCode);

	public List<SaaAuthTaskVO> findSaaAuthTaskVOListByUserCodeRootTask(String userCodeOperate, String userCode, String rootTaskCode);

	public void updateTaskPower(String[] taskCodes, String userCode);

	public void updateProductPower(String[] productCodes, String userCode);

	public void updateComPower(String authComCode, String authExceptComCode, String userCode);

	public String findAuthCompanySql(String userCode);

	public String findAuthExceptCompanySql(String userCode);

	public String findSaaUserAuthComCode(String userCode);

	public String findSaaUserAuthExceptComCode(String userCode);

	public String findSaaUserAuthComName(String useCode);

	public String findSaaUserAuthExceptComName(String useCode);

	public String findSubCompanySql(String userCode);

	public void updateUserPowerByExcel(FileInputStream file, String userCode);

	public void exportUserPowerToExcel(String comCodes);

	public List<SaaAuthTaskVO> findRootSaaAuthTaskVOList(String userCodeOperate);

	public String getUserOperateComCodeStr(String userCode, String comLevels);
	
	public List<SaaUserGrade> findUserByPermitCompany(String comCode,String saaGradeID);
}
