package com.sinosoft.undwrt.undwrtInterface.service.facade;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.common.schema.model.PrpQmain;
import com.sinosoft.platform.dto.domain.PrpDuserCADto;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.DBManager;

/**
 * 提交核保的業務接口類.
 */
public interface TaskService {

	/**
	 * 提交核保.
	 * 
	 * @param modelType
	 *            模板類型
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param classCode
	 *            險類代碼
	 * @param comCode
	 *            機構代碼
	 * @param makecom
	 *            出單機構
	 * @param userCode
	 *            用戶代碼
	 * @param handlerCode
	 *            經辦人代碼
	 * @param handler1Code
	 *            歸屬業務員代碼
	 * @param contractNo
	 *            合約號
	 * @param singleCode
	 *            出單員代碼
	 * @return 工作流號
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public String start(DBManager dbManager,String modelType, String certiType, String businessNo, String riskCode, String classCode, String comCode, String makecom,
			String userCode, String handlerCode, String handler1Code, String contractNo, String singleCode) 
					throws UserException, SQLException, Exception;
	/**
	 * 
	 * 报价单提交核保
	 *	
	 * @param businessNo 业务号
	 * @return
	 */
	public void startQta(String businessNo,String businessType,String strModelType) throws Exception;
	
	/**
	 * 
	 * 准备提交核保（为 提交核保整理数据）
	 *	
	 * @param businessNo	业务号
	 * @param businessType	业务类型
	 * @throws Exception	异常
	 */
	public String startPrepare(DBManager dbManager,String businessNo, String businessType) throws Exception;
	
	/*
	mantis： CAR0175，處理人員：Sam，需求單編號：CAR0175--- start
	車險報價單核保時，若任意險自動核保失敗，強制險也跟著狀態走
	*/
	/**
	 * 任意險報價單提交核保失敗，強制險要跟著失敗用的method
	 */
	public String startPrepareByA01Error(DBManager dbManager,String businessNo, String businessType) throws Exception;
	/* mantis： CAR0175，處理人員：Sam，需求單編號：CAR0175 --- end */
	
	public String checkData(String businessNo, String businessType) throws Exception;
	
	public void checkMainSubQatSubmit(String businessType, String businessNo );
	
	/**
	 * 查詢提交核保前的核保狀態
	 */
	public String queryUnderWriteFlag(String businessNo,String bussineeType);
	/**
	 * 異常時更新核保狀態為初始值
	 */
	public void updateUnderWriteFlag(String businessNo,String bussineeType,String underWriteFlag);
	
	  //add by songzhewen 20170301 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin
		public PrpDuserCADto findByPrimaryKey(String userCode)throws Exception;
	    public void update(PrpDuserCADto prpDuserCADto)throws Exception;
	 //add by songzhewen 20170301 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改end
    /**
	 * 續保專用的批次提交核保方法
	 * add by dongfan 
	 */
	public void checkDataForRenewal(List<PrpQmain> list) throws Exception;
	/**
	 * 續保多线程提交核保
	 * add by dongfan 
	 */
	public void checkDateByThread(String businessNo, String businessType) throws Exception;
	
	/*
	mantis： LIA0092，處理人員：Sam，需求單編號：LIA0092--- start
	普通批改-核保&保費試算
	*/
	/**
	 * 寵物險重覆投保檢核
	 * @param proposalno 要保書號
	 * @return true 代表重覆投保了 false 代表沒有重覆投保狀況(或檢核不了)
	 * @throws Exception
	 */
	public boolean checkDoubleInsuranceByPE(String proposalno) throws Exception ;
	/* mantis： LIA0092，處理人員：Sam，需求單編號：LIA0092 --- end */
}