package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.SwfModelUse;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfModelUseService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.UndwrtService;
import com.sinosoft.utiall.dbsvr.DBPrpDcompany;

/**
 * 核保系統實現類.
 */
public class UndwrtServiceSpringImpl implements UndwrtService {

	/** 屬性模板使用設定接口. */
	private SwfModelUseService swfModelUseService;

	/**
	 * 獲取模板號.
	 * 
	 * @param modelType
	 *            模板類型
	 * @param riskCode
	 *            險種代碼
	 * @param comCode
	 *            機構代碼
	 * @return 模板號
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.UndwrtService#getModelNo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public int getModelNo(String modelType, String classCode,String riskCode, String comCode)
			throws SQLException, Exception {
		boolean blnIfExist = false;
		int modelNo = 0;
		String strComCodeTemp = comCode;
		int intResult = 0;
		DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
		List<SwfModelUse> swfModelUseList = null;

		QueryRule queryRule = QueryRule.getInstance();
		while (!blnIfExist) {
			String sql="(riskCode='"+riskCode+"' or +classCode='"+classCode+"')";
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.modelType", modelType);
			queryRule.addEqual("id.comCode", strComCodeTemp);
			queryRule.addEqual("modelStatus", "1");
			queryRule.addSql(sql);
			swfModelUseList = swfModelUseService.getSwfModelUseList(queryRule);
			if (swfModelUseList.size() > 0)
			{
				if (swfModelUseList.size() > 1)
				{
					for(SwfModelUse SwfModelUse : swfModelUseList)
					{
						if(riskCode.equals(SwfModelUse.getId().getRiskCode()))
						{
							modelNo = SwfModelUse.getId().getModelNo();
							break;
						}
					}
				}
				modelNo = swfModelUseList.get(0).getId().getModelNo();
				break;
			} else {
				intResult = dbPrpDcompany.getInfo(strComCodeTemp);
				if (intResult == 100
						|| dbPrpDcompany.getComCode().equals(
								dbPrpDcompany.getUpperComCode()))
					blnIfExist = true;
				else
					strComCodeTemp = dbPrpDcompany.getUpperComCode();
			}
		}
		return modelNo;
	}

	/**
	 * 獲取屬性模板使用設定接口.
	 * 
	 * @return 屬性模板使用設定接口的值
	 */
	public SwfModelUseService getSwfModelUseService() {
		return swfModelUseService;
	}

	/**
	 * 設置屬性模板使用設定接口.
	 * 
	 * @param swfModelUseService
	 *            待設置的模板使用設定接口的值
	 */
	public void setSwfModelUseService(SwfModelUseService swfModelUseService) {
		this.swfModelUseService = swfModelUseService;
	}
}
