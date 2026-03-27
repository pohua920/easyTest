package com.sinosoft.undwrt.common.service.facade;

import java.util.ArrayList;
import java.util.Collection;

import com.sinosoft.undwrt.common.model.PrpDExpenseBalance;
import com.sinosoft.undwrt.common.model.PrpDExpenseDetail;

// TODO: Auto-generated Javadoc
/**
 * 費用聯動接口类.
 */
public interface PrpDExpenseBalanceService {

    
    /**
	 * 根據機構代碼,險種代碼,産品代碼從PrpDExpenseBalance表中獲取費用聯動核定費用數據.
	 * 
	 * @param iComCode
	 *            機構代碼
	 * @param iRiskCode
	 *            險種
	 * @param iProductCode
	 *            産品代碼
	 * @return 符合條件的記錄
	 * @throws Exception
	 *             异常
	 */
    public PrpDExpenseBalance getPrpDExpenseBalance(String iComCode, String iRiskCode, String iProductCode) throws Exception;
    
    /**
     * 根據機構代碼,險種代碼,産品代碼從PrpDExpenseBalance表中獲取費用聯動核定費用數據.
     *
     * @param iComCode 機構代碼
     * @param iRiskCode 險種
     * @param iProductCode 産品代碼
     * @param iValidDate 有效起始日期
     * @param iInValidDate 有效終止日期
     * @return 符合條件的記錄
     * @throws Exception 異常
     */
    public PrpDExpenseBalance getPrpDExpenseBalance(String iComCode,String iRiskCode,String iProductCode,
    		String iValidDate,String iInValidDate)throws Exception;
    
    /**
     *  根據機構代碼,險種代碼,産品代碼從PrpDExpenseBalance表中獲取費用聯動核定費用數據..
     *
     * @param iComCode 機構代碼
     * @param iRiskCode 險種
     * @param iProductCode 産品代碼
     * @return 符合條件的記錄
     * @throws Exception 異常
     */
    public PrpDExpenseBalance getPrpDExpenseBalanceTwo(String iComCode,String iRiskCode,String iProductCode)throws Exception;
    
    /**
     * 更新費用聯動表.
     *
     * @param prpDExpenseBalance 費用聯動類
     * @param prpDExpenseDetail 費用聯動明細類
     * @throws Exception 異常
     */
    public void update(PrpDExpenseBalance prpDExpenseBalance,PrpDExpenseDetail prpDExpenseDetail)throws Exception;
    
    /**
     * 插入一條數據.
     *
     * @param prpDExpenseBalance 費用聯動類
     * @throws Exception 異常
     */
    public void insert(PrpDExpenseBalance prpDExpenseBalance) throws Exception;

    /**
     * 按主鍵刪除一條數據.
     *
     * @param comCode 機構代碼
     * @throws Exception 異常
     */
    public void delete(String comCode) throws Exception;

    /**
     * 按條件刪除數據.
     *
     * @param conditions 刪除條件
     * @throws Exception 異常
     */
    public void deleteByConditions(String conditions) throws Exception;

    /**
     * 按主鍵更新一條數據(主鍵本身無法變更).
     *
     * @param prpDExpenseBalance 費用聯動類
     * @throws Exception 異常
     */
    public void update(PrpDExpenseBalance prpDExpenseBalance) throws Exception;

    /**
     * 按主鍵查找一條數據.
     *
     * @param comCode 機構代碼
     * @return prpDExpenseBalance 費用聯動類
     * @throws Exception 異常
     */
    public PrpDExpenseBalance findByPrimaryKey(String comCode) throws Exception;

    /**
     * 按條件查詢多條數據.
     *
     * @param conditions 查詢條件
     * @return Collection 符合條件的集合
     * @throws Exception 異常
     */
    public Collection findByConditions(String conditions) throws Exception;

    /**
     * 查詢滿足模糊查詢條件的記錄數.
     *
     * @param conditions 模糊查詢條件
     * @return 滿足條件的記錄數
     * @throws Exception 異常
     */
    public int getCount(String conditions) throws Exception;
    
    /**
     * 費用調整.
     *
     * @param iComCode 機構代碼
     * @param iRiskCode 險種
     * @param iProductCode 產品代碼
     * @param iExpenseType 費用模式:計劃費用(0')/實際費用模式(0;)
     * @param iExpenseFee 費用
     * @throws Exception 異常
     */
    public void expenseAdjust(String iComCode,String iRiskCode,String iProductCode,String iExpenseType,
    		double iExpenseFee)throws Exception;
    
    /**
     * 費用劃撥.
     *
     * @param iComCode 機構代碼
     * @param iRiskCode 險種
     * @param iProductCode 產品代碼
     * @param iValidDate 有效起始日期
     * @param iInValidDate 有效終止日期
     * @param iTransferFee 劃撥額度
     * @throws Exception 異常
     */
    public void expenseTransfer(String iComCode,String iRiskCode,String iProductCode,String iValidDate,String iInValidDate,
    		double iTransferFee)throws Exception;
}
