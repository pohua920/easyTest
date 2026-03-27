package com.sinosoft.undwrt.undwrtDeal.service.facade;

import com.sinosoft.undwrt.common.model.PrpDExpenseBalance;
import com.sinosoft.undwrt.undwrtBase.model.WfGrade;

// TODO: Auto-generated Javadoc
/**
 * 核定費用結余服務接口類.
 */
public interface ExpenseControlDealService {

	/**
	 * 獲取核定費用結余.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iExpenseType
	 *            費用模式
	 * @param prpDExpenseBalanceDto
	 *            費用聯動核定費用類
	 * @param wfGradeDto
	 *            定級訊息類
	 * @return 核定費用結余
	 * @throws Exception
	 *             異常
	 */
	public double getExpenseBalance(String iBusinessType, String iBusinessNo,
			String iExpenseType, PrpDExpenseBalance prpDExpenseBalanceDto,
			WfGrade wfGradeDto) throws Exception;

	/**
	 * 在批單核批通過後獲取最新的核定費用結余.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @return 批單核批通過後獲取最新的核定費用結余
	 * @throws Exception
	 *             異常
	 */
	public double getExpenseBalance(String iBusinessType, String iBusinessNo)
			throws Exception;

	/**
	 * 回寫費用聯動控制處理.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iPolicyNo
	 *            保單號
	 * @param iComCode
	 *            機構代碼
	 * @param iRiskCode
	 *            險種代碼
	 * @param iProductCode
	 *            產品代碼
	 * @param iUserCode
	 *            用戶代碼
	 * @throws Exception
	 *             異常
	 */
	public void echoExpenseControl(String iBusinessType, String iBusinessNo,
			String iPolicyNo, String iComCode, String iRiskCode,
			String iProductCode, String iUserCode) throws Exception;

	/**
	 * 費用聯動控制處理.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iComCode
	 *            機構代碼
	 * @param iRiskCode
	 *            險種代碼
	 * @param iProductCode
	 *            產品代碼
	 * @param wfGradeDto
	 *            頂級信息類
	 * @return 成功返回true,失敗返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean dealExpenseControl(String iBusinessType, String iBusinessNo,
			String iComCode, String iRiskCode, String iProductCode,
			WfGrade wfGradeDto) throws Exception;
}
