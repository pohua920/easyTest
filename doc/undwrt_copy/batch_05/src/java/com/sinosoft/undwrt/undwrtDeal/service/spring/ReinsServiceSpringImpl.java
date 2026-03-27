package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.reins.interf.web.ReinsUndrtInterfAction;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.undwrt.common.model.PrpDcode;
import com.sinosoft.undwrt.common.model.PrpDcodeId;
import com.sinosoft.undwrt.common.vo.PrpTreinstrialViewInfoVo;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtDeal.service.facade.ReinsService;

/**
 * 再保服務實現類.
 */
public class ReinsServiceSpringImpl extends GenericDaoHibernate implements
		ReinsService {

	/** 屬性核保調用再保接口. */
	private ReinsUndrtInterfAction reinsUndrtInterfAction;

	/**
	 * 是否強制分保試算計算.
	 * 
	 * @param RiskCode
	 *            險種代碼
	 * @param UwYear
	 *            業務年度
	 * @param BusinessNo
	 *            業務號
	 * @param BusinessType
	 *            業務類型
	 * @return 離線計算返回true, 否則返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean ifOffLineCal(String RiskCode, String UwYear,
			String BusinessNo, String BusinessType) throws Exception {
		boolean ifOffLineCal = false;
		try {
			ifOffLineCal = reinsUndrtInterfAction.ifOffLineCal(RiskCode,
					UwYear, BusinessNo, BusinessType);
		} catch (UserException usee) {
			usee.printStackTrace();
			throw usee;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ifOffLineCal;
	}

	/**
	 * 獲取分保試算信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @param businessType
	 *            業務類型
	 * @return 分保試算信息類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.ReinsService#getReinsTrialInfo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public Collection getReinsTrialInfo(String businessNo, String dangerNo,
			String businessType) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String statementStr = null;
		PrpTreinstrialViewInfoVo prpTreinstrialViewInfoDto = null; // 分保试算显示公共Dto
		Collection collection = new ArrayList();
		List list = null;
		if (businessType.equals("T")) // 取投保单的分保试算信息
		{

			statementStr = "select b.refno,a.reinsMode,a.sharerate,a.amount,a.premium,a.commission,a.currency,c.exchratecny "
					+ "from prptreinstrial a,fhtreaty b,prptdangerunit c "
					+ "where a.treatyno=b.treatyno "
					+ "and a.proposalno=c.proposalno "
					+ "and a.dangerno = c.dangerno "
					+ "and a.proposalno='"
					+ businessNo
					+ "' and a.dangerno='"
					+ dangerNo
					+ "' order by a.serialno";
			list = super.getSession().createSQLQuery(statementStr).list();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				try {
					Object[] obj = (Object[]) it.next();
					prpTreinstrialViewInfoDto = new PrpTreinstrialViewInfoVo();
					prpTreinstrialViewInfoDto.setBusinessNo(businessNo);
					prpTreinstrialViewInfoDto.setDangerNo(Integer
							.parseInt(dangerNo));
					prpTreinstrialViewInfoDto.setRefNo((String) obj[0]);
					if (obj[1] != null) {
						prpTreinstrialViewInfoDto.setReinsMode((String) obj[1]);
					}
					if (obj[2] != null) {
						prpTreinstrialViewInfoDto
								.setShareRate(((BigDecimal) obj[2])
										.doubleValue());
					}
					if (obj[3] != null) {
						prpTreinstrialViewInfoDto
								.setAmount(((BigDecimal) obj[3]).doubleValue());
					}
					if (obj[4] != null) {
						prpTreinstrialViewInfoDto
								.setPremium(((BigDecimal) obj[4]).doubleValue());
					}
					if (obj[5] != null) {
						prpTreinstrialViewInfoDto
								.setCommission(((BigDecimal) obj[5])
										.doubleValue());
					}
					if (obj[6] != null) {
						prpTreinstrialViewInfoDto.setCurrency((String) obj[6]);
					}
					if (obj[7] != null) {
						prpTreinstrialViewInfoDto
								.setExchratecny(((BigDecimal) obj[7])
										.doubleValue());
					}
					collection.add(prpTreinstrialViewInfoDto);

				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(
							internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
				}
			}
		} else if (businessType.equals("P")) // 取保单的分保试算信息
		{
			statementStr = "select b.refno,a.reinsMode,a.sharerate,a.amount,a.premium,a.commission,a.currency,c.exchratecny  "
					+ "from prpcreinstrial a,fhtreaty b,prpcdangerunit c "
					+ "where a.treatyno=b.treatyno "
					+ "and a.policyno=c.policyno "
					+ "and a.dangerno=c.dangerno "
					+ "and a.policyno='"
					+ businessNo
					+ "' and a.dangerno='"
					+ dangerNo
					+ "' order by a.serialno";
			list = super.getSession().createSQLQuery(statementStr).list();
			Iterator it = list.iterator();
			while (it.hasNext())
				try {
					Object[] obj = (Object[]) it.next();
					prpTreinstrialViewInfoDto = new PrpTreinstrialViewInfoVo();
					prpTreinstrialViewInfoDto.setBusinessNo(businessNo);
					prpTreinstrialViewInfoDto.setDangerNo(Integer
							.parseInt(dangerNo));
					prpTreinstrialViewInfoDto.setRefNo((String) obj[0]);
					if (obj[1] != null) {
						prpTreinstrialViewInfoDto.setReinsMode((String) obj[1]);
					}
					if (obj[2] != null) {
						prpTreinstrialViewInfoDto
								.setShareRate(((BigDecimal) obj[2])
										.doubleValue());
					}
					if (obj[3] != null) {
						prpTreinstrialViewInfoDto
								.setAmount(((BigDecimal) obj[3]).doubleValue());
					}
					if (obj[4] != null) {
						prpTreinstrialViewInfoDto
								.setPremium(((BigDecimal) obj[4]).doubleValue());
					}
					if (obj[5] != null) {
						prpTreinstrialViewInfoDto
								.setCommission(((BigDecimal) obj[5])
										.doubleValue());
					}
					if (obj[6] != null) {
						prpTreinstrialViewInfoDto.setCurrency((String) obj[6]);
					}
					if (obj[7] != null) {
						prpTreinstrialViewInfoDto
								.setExchratecny(((BigDecimal) obj[7])
										.doubleValue());
					}
					collection.add(prpTreinstrialViewInfoDto);

				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(
							internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
				}
		} else if (businessType.equals("E")) // 取批单的分保试算信息
		{
			statementStr = "select b.refno,a.reinsMode,a.sharerate,a.amount,a.premium,a.commission,a.chgAmount,"
					+ "a.chgPremium,a.chgCommission from prpPreinstrial a,fhtreaty b "
					+ "where a.treatyno=b.treatyno "
					+ "and endorseno='"
					+ businessNo
					+ "' and dangerno='"
					+ dangerNo
					+ "' order by a.serialno";
			list = super.getSession().createSQLQuery(statementStr).list();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				try {
					Object[] obj = (Object[]) it.next();
					prpTreinstrialViewInfoDto = new PrpTreinstrialViewInfoVo();
					prpTreinstrialViewInfoDto.setBusinessNo(businessNo);
					prpTreinstrialViewInfoDto.setDangerNo(Integer
							.parseInt(dangerNo));
					prpTreinstrialViewInfoDto.setRefNo((String) obj[0]);
					if (obj[1] != null) {
						prpTreinstrialViewInfoDto.setReinsMode((String) obj[1]);
					}
					if (obj[2] != null) {
						prpTreinstrialViewInfoDto
								.setShareRate(((BigDecimal) obj[2])
										.doubleValue());
					}
					if (obj[3] != null) {
						prpTreinstrialViewInfoDto
								.setAmount(((BigDecimal) obj[3]).doubleValue());
					}
					if (obj[4] != null) {
						prpTreinstrialViewInfoDto
								.setPremium(((BigDecimal) obj[4]).doubleValue());
					}
					if (obj[5] != null) {
						prpTreinstrialViewInfoDto
								.setCommission(((BigDecimal) obj[5])
										.doubleValue());
					}
					if (obj[6] != null) {
						prpTreinstrialViewInfoDto
								.setChgAmount(((BigDecimal) obj[6])
										.doubleValue());
					}
					if (obj[7] != null) {
						prpTreinstrialViewInfoDto
								.setChgPremium(((BigDecimal) obj[7])
										.doubleValue());
					}
					if (obj[8] != null) {
						prpTreinstrialViewInfoDto
								.setChgCommission(((BigDecimal) obj[8])
										.doubleValue());
					}
					collection.add(prpTreinstrialViewInfoDto);

				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(
							internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
				}
			}
		}
		return collection;
	}

	/**
	 * 獲取屬性核保調用再保接口.
	 * 
	 * @return 屬性核保調用再保接口的值
	 */
	public ReinsUndrtInterfAction getReinsUndrtInterfAction() {
		return reinsUndrtInterfAction;
	}

	/**
	 * 設置屬性核保調用再保接口.
	 * 
	 * @param reinsUndrtInterfAction
	 *            待設置的核保調用再保接口的值
	 */
	public void setReinsUndrtInterfAction(
			ReinsUndrtInterfAction reinsUndrtInterfAction) {
		this.reinsUndrtInterfAction = reinsUndrtInterfAction;
	}

}
