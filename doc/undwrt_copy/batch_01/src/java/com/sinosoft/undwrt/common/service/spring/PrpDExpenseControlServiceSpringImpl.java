package com.sinosoft.undwrt.common.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.Collection;
import java.util.Iterator;

import com.sinosoft.platform.bl.facade.BLPrpDcompanyFacade;
import com.sinosoft.platform.dto.domain.PrpDcompanyDto;
import com.sinosoft.undwrt.common.model.PrpDExpenseControl;
import com.sinosoft.undwrt.common.model.PrpDExpenseControlId;
import com.sinosoft.undwrt.common.service.facade.PrpDExpenseControlService;
import com.sinosoft.utility.string.ChgDate;

/**
 * 費用聯動控制策略實現類.
 */
public class PrpDExpenseControlServiceSpringImpl extends
		GenericDaoHibernate<PrpDExpenseControl, PrpDExpenseControlId> implements
		PrpDExpenseControlService {

	/**
	 * 根據業務歸屬機構從費用聯動控制策略表PRPDEXPENSECONTROL中遞歸向上查找控制策略數據.
	 * 
	 * @param iComCode
	 *            業務歸屬機構
	 * @return 符合條件的記錄
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDExpenseControlService#getExpenseControl(java.lang.String)
	 */
	@Override
	public PrpDExpenseControl getExpenseControl(String iComCode)
			throws Exception {

		PrpDExpenseControl prpDExpenseControlDto = null;
		BLPrpDcompanyFacade blPrpDcompanyFacade = new BLPrpDcompanyFacade();
		PrpDcompanyDto prpDcompanyDto = null;
		Collection col = null;
		Iterator iterator = null;
		ChgDate nowDate = new ChgDate();

		String strComCode = iComCode;
		String strNowDate = "";
		boolean blnStatus = true;

		strNowDate = nowDate.getCurrentTime("yyyy-MM-dd");
		QueryRule queryRule = QueryRule.getInstance();
		while (blnStatus) {
			prpDcompanyDto = blPrpDcompanyFacade.findByPrimaryKey(strComCode);
			if (prpDcompanyDto.getComLevel().equals("1")
					|| prpDcompanyDto.getComCode().equals("0000000000")) {
				break;
			}
			queryRule.addEqual("id.comCode", prpDcompanyDto.getComCode());
			queryRule.addLessEqual("id.validDate", strNowDate);
			queryRule.addGreaterEqual("id.invalidDate", strNowDate);
			col = this.findByQueryRule(queryRule);
			iterator = col.iterator();
			while (iterator.hasNext()) {
				// 取该机构下的第一条数据,如果配置多条也只按其中一条处理
				prpDExpenseControlDto = (PrpDExpenseControl) iterator.next();
				break;
			}

			if (prpDExpenseControlDto != null) {
				break;
			}
			strComCode = prpDcompanyDto.getUpperComCode();
		}
		return prpDExpenseControlDto;

	}

	/**
	 * 根據條件獲取費用聯動控制策略實現訊息.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的費用聯動控制策略實集合
	 */
	public Collection<PrpDExpenseControl> findByQueryRule(QueryRule queryRule) {
		return super.find(queryRule);
	}
}
