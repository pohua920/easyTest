package com.sinosoft.undwrt.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.undwrt.common.model.PrpDcode;
import com.sinosoft.undwrt.common.model.PrpDcodeId;
import com.sinosoft.undwrt.common.service.facade.PrpDcodeService;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtDeal.vo.DangerRiskKindVo;

/**
 * 基礎代碼實現類.
 */
public class PrpDcodeServiceSpringImpl extends
		GenericDaoHibernate<PrpDcode, PrpDcodeId> implements PrpDcodeService {

	/**
	 * 根據條件查詢基礎代碼.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @param pageNo
	 *            頁碼
	 * @param pageSize
	 *            每頁顯示的記錄條數
	 * @return page對象
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDcodeService#findUserTaskList(ins.framework.common.QueryRule,
	 *      int, int)
	 */
	@Override
	public Page findUserTaskList(QueryRule queryRule, int pageNo, int pageSize) {
		Page page = super.find(queryRule, pageNo, pageSize);
		return page;
	}

	/**
	 * 根據業務代碼得到一條基礎代碼記錄.
	 * 
	 * @param codecode
	 *            業務代碼
	 * @return 符合條件的記錄
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDcodeService#get(java.lang.String)
	 */
	@Override
	public PrpDcode get(String codecode) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根據條件查詢基礎代碼.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @param pageNo
	 *            頁碼
	 * @param pageSize
	 *            每頁顯示的記錄條數
	 * @return 符合條件的集合
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDcodeService#findByHslList(ins.framework.common.QueryRule,
	 *      int, int)
	 */
	@Override
	public List<PrpDcode> findByHslList(QueryRule queryRule, int pageNo,
			int pageSize) {
		List<PrpDcode> list = null;
		Page page = super.find(queryRule, pageNo, pageSize);
		list = page.getResult();

		return list;
	}

	/**
	 * 根據sql查詢基礎代碼.
	 * 
	 * @param sql
	 *            查詢條件
	 * @return 符合條件的集合
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDcodeService#findBySqlList(java.lang.String)
	 */
	@Override
	public List findBySqlList(String sql) {
		List<PrpDcode> list = null;
		list = super.getSession().createSQLQuery(sql).addEntity(PrpDcode.class)
				.list();

		return list;
	}

	/**
	 * 根據條件查詢基礎代碼.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 符合條件的記錄集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDcodeService#findByConditions(ins.framework.common.QueryRule)
	 */
	public List findByConditions(QueryRule queryRule) throws Exception {
		List<PrpDcode> list = super.find(queryRule);
		return list;
	}

	/**
	 * 根據條件查詢基礎代碼.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 符合條件的集合
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDcodeService#findPrpDcodeList(ins.framework.common.QueryRule)
	 */
	@Override
	public List<PrpDcode> findPrpDcodeList(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

	/**
	 * 獲得風險類別.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @return 風險類別
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDcodeService#getDangerRiskKind(java.lang.String)
	 */
	public Collection getDangerRiskKind(String riskCode) throws Exception {

		String statementStr = null;
		InternationalizationUtil internal = new InternationalizationUtil();
		List<PrpDcode> list = null;
		DangerRiskKindVo dangerRiskKindDto = null;
		statementStr = "select codecode, codeCName from PrpDcode "
				+ "where  codeCode in "
				+ "(select codeCode from prpdcoderisk where codetype='RiskClass' "
				+ "and riskcode='" + riskCode + "') and codeType='RiskClass'";

		list = super.getSession().createSQLQuery(statementStr).list();
		Iterator it = list.iterator();
		Collection collection = new ArrayList();
		while (it.hasNext()) {
			try {
				Object[] obj = (Object[]) it.next();
				dangerRiskKindDto = new DangerRiskKindVo();
				dangerRiskKindDto.setRiskKindCode((String) obj[0]);
				dangerRiskKindDto.setRiskKindName((String) obj[1]);
				collection.add(dangerRiskKindDto);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(
						internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
			}
		}
		if (collection.size() == 0) {
			throw new Exception(
					internal.getText("undwrt.action.commonDangerRisk.codeTableNoRiskType"));
		}

		return collection;
	}
}
