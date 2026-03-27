package com.sinosoft.undwrt.common.service.spring;

import java.util.Collection;
import java.util.Iterator;

import com.sinosoft.undwrt.common.model.PrpDExpenseBalance;
import com.sinosoft.undwrt.common.model.PrpDExpenseDetail;
import com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService;
import com.sinosoft.utility.string.ChgDate;
import com.sinosoft.utility.string.Str;

import ins.framework.dao.GenericDaoHibernate;

/**
 * 費用聯動實現類.
 */
public class PrpDExpenseBalanceServiceSpringImpl extends GenericDaoHibernate
		implements PrpDExpenseBalanceService {

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
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#getPrpDExpenseBalance(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public PrpDExpenseBalance getPrpDExpenseBalance(String iComCode,
			String iRiskCode, String iProductCode) throws Exception {

		PrpDExpenseBalance prpDExpenseBalanceDto = null;
		Collection col = null;
		Iterator iterator = null;
		ChgDate nowDate = new ChgDate();

		String strSQL = " 1=1 ";
		String strNowDate = "";

		strNowDate = nowDate.getCurrentTime("yyyy-MM-dd");

		if (!iProductCode.equals("")) {
			strSQL += Str.convertString("ComCode", iComCode, "=");
			strSQL += Str.convertString("RiskCode", iRiskCode, "=");
			strSQL += Str.convertString("ProductCode", iProductCode, "=");
		} else {
			strSQL += Str.convertString("ComCode", iComCode, "=");
			strSQL += Str.convertString("RiskCode", iRiskCode, "=");
		}

		strSQL += " And ValidDate<='" + strNowDate + "' And InValidDate>='"
				+ strNowDate + "'";

		col = this.findByConditions(strSQL);
		iterator = col.iterator();
		while (iterator.hasNext()) {
			// 取第一条数据,如果配置多条也只按其中一条处理
			prpDExpenseBalanceDto = (PrpDExpenseBalance) iterator.next();
			break;
		}

		return prpDExpenseBalanceDto;

	}

	/**
	 * 按條件查詢多條數據.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @return Collection 符合條件的集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#findByConditions(java.lang.String)
	 */
	public Collection findByConditions(String conditions) throws Exception {
		// TODO Auto-generated method stub
		return super.findBySql(conditions);
	}

	/**
	 * 根據機構代碼,險種代碼,産品代碼從PrpDExpenseBalance表中獲取費用聯動核定費用數據.
	 * 
	 * @param iComCode
	 *            機構代碼
	 * @param iRiskCode
	 *            險種
	 * @param iProductCode
	 *            産品代碼
	 * @param iValidDate
	 *            有效起始日期
	 * @param iInValidDate
	 *            有效終止日期
	 * @return 符合條件的記錄
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#getPrpDExpenseBalance(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public PrpDExpenseBalance getPrpDExpenseBalance(String iComCode,
			String iRiskCode, String iProductCode, String iValidDate,
			String iInValidDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 更新費用聯動表.
	 * 
	 * @param prpDExpenseBalance
	 *            費用聯動類
	 * @param prpDExpenseDetail
	 *            費用聯動明細類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#getPrpDExpenseBalanceTwo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public PrpDExpenseBalance getPrpDExpenseBalanceTwo(String iComCode,
			String iRiskCode, String iProductCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 插入一條數據.
	 * 
	 * @param prpDExpenseBalance
	 *            費用聯動類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#insert(com.sinosoft.undwrt.common.model.PrpDExpenseBalance)
	 */
	@Override
	public void insert(PrpDExpenseBalance prpDExpenseBalance) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 按主鍵刪除一條數據.
	 * 
	 * @param comCode
	 *            機構代碼
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#delete(java.lang.String)
	 */
	@Override
	public void delete(String comCode) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 按條件刪除數據.
	 * 
	 * @param conditions
	 *            刪除條件
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#deleteByConditions(java.lang.String)
	 */
	@Override
	public void deleteByConditions(String conditions) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 按主鍵更新一條數據(主鍵本身無法變更).
	 * 
	 * @param prpDExpenseBalance
	 *            費用聯動類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#update(com.sinosoft.undwrt.common.model.PrpDExpenseBalance)
	 */
	@Override
	public void update(PrpDExpenseBalance prpDExpenseBalance) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 按主鍵查找一條數據.
	 * 
	 * @param comCode
	 *            機構代碼
	 * @return prpDExpenseBalance 費用聯動類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#findByPrimaryKey(java.lang.String)
	 */
	@Override
	public PrpDExpenseBalance findByPrimaryKey(String comCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查詢滿足模糊查詢條件的記錄數.
	 * 
	 * @param conditions
	 *            模糊查詢條件
	 * @return 滿足條件的記錄數
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#getCount(java.lang.String)
	 */
	@Override
	public int getCount(String conditions) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 更新費用聯動表.
	 * 
	 * @param prpDExpenseBalance
	 *            費用聯動類
	 * @param prpDExpenseDetail
	 *            費用聯動明細類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#update(com.sinosoft.undwrt.common.model.PrpDExpenseBalance,
	 *      com.sinosoft.undwrt.common.model.PrpDExpenseDetail)
	 */
	@Override
	public void update(PrpDExpenseBalance prpDExpenseBalance,
			PrpDExpenseDetail prpDExpenseDetail) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 費用調整.
	 * 
	 * @param iComCode
	 *            機構代碼
	 * @param iRiskCode
	 *            險種
	 * @param iProductCode
	 *            產品代碼
	 * @param iExpenseType
	 *            費用模式:計劃費用(0')/實際費用模式(0;)
	 * @param iExpenseFee
	 *            費用
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#expenseAdjust(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, double)
	 */
	@Override
	public void expenseAdjust(String iComCode, String iRiskCode,
			String iProductCode, String iExpenseType, double iExpenseFee)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 費用劃撥.
	 * 
	 * @param iComCode
	 *            機構代碼
	 * @param iRiskCode
	 *            險種
	 * @param iProductCode
	 *            產品代碼
	 * @param iValidDate
	 *            有效起始日期
	 * @param iInValidDate
	 *            有效終止日期
	 * @param iTransferFee
	 *            劃撥額度
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService#expenseTransfer(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, double)
	 */
	@Override
	public void expenseTransfer(String iComCode, String iRiskCode,
			String iProductCode, String iValidDate, String iInValidDate,
			double iTransferFee) throws Exception {
		// TODO Auto-generated method stub

	}

}
