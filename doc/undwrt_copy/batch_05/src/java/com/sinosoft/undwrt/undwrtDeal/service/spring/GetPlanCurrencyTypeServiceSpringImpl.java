package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.undwrt.common.vo.PrpCurrencyTypeVo;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtDeal.service.facade.GetPlanCurrencyTypeService;

/**
 * 交費計劃中的幣種信息實現類.
 */
public class GetPlanCurrencyTypeServiceSpringImpl extends GenericDaoHibernate
		implements GetPlanCurrencyTypeService {

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/**
	 * 交費計劃中的幣種信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 幣種信息
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPlanCurrencyType(String businessNo, String businessType)
			throws SQLException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		Collection currencyType = null;
		String statementStr = null;
		PrpCurrencyTypeVo prpCurrencyType = null;
		if (businessType.equals("T")) {
			statementStr = "SELECT DISTINCT currency1 FROM prptfee a where proposalno = '"
					+ businessNo + "'";
		} else if (businessType.equals("P")) {
			statementStr = "SELECT DISTINCT currency1 FROM prpcfee a where policyno = '"
					+ businessNo + "'";
		}

		else if (businessType.equals("E")) {
			PrpPmain prpPmain = new PrpPmain();
			prpPmain = endorseService.getPrpPheadByEndorseNo(businessNo)
					.getPrpPmains().get(0);
			statementStr = "SELECT DISTINCT currency1 FROM prpcpfee a where policyNo = '"
					+ prpPmain.getPolicyNo() + "'";
		}

		List list = super.getSession().createSQLQuery(statementStr).list();
		String tempType = "";
		Iterator it = list.iterator();
		while (it.hasNext()) {
			try {
				if (list.size() == 1) {
					Object obj = it.next();
					currencyType = new ArrayList();
					tempType = (String) obj;
					prpCurrencyType = new PrpCurrencyTypeVo();
					prpCurrencyType.setCurrencyType(tempType);
					currencyType.add(prpCurrencyType);
				} else {
					Object[] obj = (Object[]) it.next();
					currencyType = new ArrayList();
					tempType = (String) obj[0];
					prpCurrencyType = new PrpCurrencyTypeVo();
					prpCurrencyType.setCurrencyType(tempType);
					currencyType.add(prpCurrencyType);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (currencyType == null) {
			throw new Exception(
					internal.getText("undwrt.service.getPlanCurrencyType.feePlanInfor"));
		}
		return currencyType;
	}

	/**
	 * 獲取屬性批單處理接口.
	 * 
	 * @return 屬性批單處理接口的值
	 */
	public EndorseService getEndorseService() {
		return endorseService;
	}

	/**
	 * 設置屬性批單處理接口.
	 * 
	 * @param endorseService
	 *            待設置的批單處理接口的值
	 */
	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

}
