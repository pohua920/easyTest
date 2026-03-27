package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;

import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtDeal.vo.WfGradeVo;

/**
 * 核保審核處理接口類.
 */
public interface CommonDealSubmitService {

	/**
	 * 報價單任務提交.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param userCode
	 *            用戶代碼
	 * @param iTaskCode
	 *            任務代碼
	 * @throws Exception
	 *             異常
	 * @throws UserException
	 *             自定義異常
	 */
	public void submitTaskQta(String businessNo, String userCode, String iTaskCode) throws Exception, UserException;

	/**
	 * 保存核保意見.
	 * 
	 * @param uwNotion
	 *            the uw notion
	 * @throws Exception
	 *             異常
	 */
	public void saveNotion(UwNotion uwNotion) throws Exception;

	/**
	 * 提交核保任務.
	 * 
	 * @param flowID
	 *            工作流號
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param flowStatus
	 *            工作流狀態
	 * @param flag
	 *            核保標誌
	 * @param userCode
	 *            用戶代碼
	 * @param opertorCode
	 *            操作員代碼
	 * @param currendNodeNo
	 *            當前節點號
	 * @param wfGradeDto
	 *            定級信息類
	 * @throws Exception
	 *             異常
	 */
	public void submitTask(String flowID, int modelNo, int nodeNo, String businessType, String businessNo, String flowStatus, String flag, String userCode,
			String opertorCode, int currendNodeNo, WfGradeVo wfGradeDto) throws Exception;

	/**
	 * 對複核後的任務進行處理.
	 * 
	 * @param iModelNo
	 *            模板號
	 * @param iCertiType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iFlag
	 *            是修改還是新啓動標志
	 * @param iNodeNo
	 *            節點號
	 * @param iOption
	 *            是出單員提交還是雙核內部提交
	 * @param iRiskCode
	 *            險種代碼
	 * @param iClassCode
	 *            險類代碼
	 * @param iComCode
	 *            機構代碼
	 * @param iMakeCom
	 *            出單機構
	 * @param iHandlerCode
	 *            經辦人代碼
	 * @param iHandler1Code
	 *            歸屬業務員代碼
	 * @param iUserCode
	 *            用戶代碼
	 * @param iContractNo
	 *            合約號
	 * @param iSingleCode
	 *            出單員代碼
	 * @param dbManager
	 *            數據管理對象
	 * @return 工作流日誌對象的工作流號
	 * @throws SQLException
	 *             sql異常
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String dealFirstTrans(int iModelNo, String iCertiType, String iBusinessNo, String iFlag, int iNodeNo, String iOption, String iRiskCode,
			String iClassCode, String iComCode, String iMakeCom, String iHandlerCode, String iHandler1Code, String iUserCode, String iContractNo,
			String iSingleCode, DBManager dbManager) throws SQLException, UserException, Exception;

	/**
	 * 任務撤回.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param prpDuserDto
	 *            用戶信息類
	 * @return 成功返回true，失敗返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean retract(String businessNo, PrpDuserDto prpDuserDto) throws Exception;

	/**
	 * 批量下發修改.
	 * 
	 * @param wfLogList
	 *            工作流日誌 list
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void submitBatchTask(Collection wfLogList) throws UserException, Exception;

	/**
	 * 生成虛擬編碼
	 * 
	 * @param bizNo1
	 *            業務號1
	 * @param bizNo2
	 *            業務號2
	 * @return Map<String,Object> 返回訊息
	 */
	public Map<String, Object> genDummyCode(String businessNo, String businessType) throws Exception;
	/**
	 * 校驗是否需要實收
	 * 收費出單的批單中，如果險種信息中有批增，則需要實收
	 * @param prpPhead
	 * @return true-需要實收  false-无需實收
	 */
	public boolean checkIsNeadPaid(PrpPhead prpPhead);
	
	//mantis： OTH0139，處理人員：DP0713，需求單編號：OTH0139 保單內容批改規則異動 Start
	//判斷是否修改過業務員
	public boolean endorChangeBusiness(String businessNo, String businessType);
}
