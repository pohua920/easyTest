package com.sinosoft.claim.undwrt.service.spring;

import ins.framework.common.DateTime;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.undwrt.service.facade.BusinessDataService;
import com.sinosoft.undwrt.dto.custom.UwHPFactorCode;

/***
 * @version BLHPBusinessDataServiceImplGpic（98fubon）
 * @author 陈杰
 */
public class BusinessDataServiceSpringImpl extends GenericDaoHibernate  implements BusinessDataService {

	private CodeService codeService;
	private PolicyService policyService;
	private PrpLclaimService prpLclaimService;
	private PrpCmainService prpCmainService;
	private PrpCcoinsService prpCcoinsService;
	private CompensateService compensateService;
	private PrpDriskService prpDriskService;

	/** 实赔 */
	@Override
	public Map<String,Object> getBusinessDataMap(PrpLcompensate prpLcompensate) throws Exception {
		String riskType = this.getCodeService().translateRiskCodetoRiskType(prpLcompensate.getRiskCode());
		if ("D".equalsIgnoreCase(riskType)) {
			return this.getBusinessShiPei_RiskType_D(prpLcompensate);
		} else if ("E".equalsIgnoreCase(riskType)) {
			return this.getBusinessShiPei_RiskType_E(prpLcompensate);
		} else if ("Q".equalsIgnoreCase(riskType) || "G".equals(riskType)) {// 当是 工程险 和 财产险时，从共保业务取我方分摊后的金额
			return this.getBusinessShiPei_RiskType_Q(prpLcompensate);
		} else {
			return this.getBusinessShiPei_RiskType_Other(prpLcompensate);
		}
	}

	/** 预赔 */
	@Override
	public Map<String,Object> getBusinessDataMap(PrpLprepay prpLprepay) throws Exception {
		String riskType = this.getCodeService().translateRiskCodetoRiskType(prpLprepay.getRiskCode());
		Map<String, Object> businessDataMap = new HashMap<String, Object>();
		if ("D".equalsIgnoreCase(riskType)) {
			Double sumPaid = null;
			sumPaid = new Double(prpLprepay.getSumPrePaid());
			// 折算为人民币的金额进行权限控制
			if (prpLprepay.getExchangeRate() != 0 && prpLprepay.getExchangeRate() != 1) {
				sumPaid = new Double(sumPaid.doubleValue() * prpLprepay.getExchangeRate());
			}
			// 总核赔金额
			businessDataMap.put("SumPaid", sumPaid);
			// 是否实收
			businessDataMap.put(UwHPFactorCode.Car.RealPayFlag, checkPay(prpLprepay.getPolicyNo()));
			return businessDataMap;
		} else if (!"D".equals(riskType)) {
			Double sumPaid = null;
			sumPaid = new Double(prpLprepay.getSumPrePaid());
			// 折算为人民币的金额进行权限控制
			if (prpLprepay.getExchangeRate() != 0 && prpLprepay.getExchangeRate() != 1) {
				sumPaid = new Double(sumPaid.doubleValue() * prpLprepay.getExchangeRate());
			}
			// 总核赔金额
			double sumPrepaid = this.getSumPreCoinsPaid(prpLprepay);
			// 折算为人民币的金额进行权限控制
			if (prpLprepay.getExchangeRate() != 0 && prpLprepay.getExchangeRate() != 1) {
				sumPrepaid = sumPrepaid * prpLprepay.getExchangeRate();
			}
			businessDataMap.put("SumPaid", new Double(sumPrepaid));
			// 预赔金额是否超过估损金额的30%
			businessDataMap.put("ThirtyPercent", this.thirtyPercent(prpLprepay));
			return businessDataMap;
		}
		return null;
	}

	/**
	 * 车险业务数据组织
	 * @param dbManager
	 * @param prpLcompensateDto
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> getBusinessShiPei_RiskType_D(PrpLcompensate prpLcompensateDto) throws Exception {
		Map<String, Object> businessDataMap = new HashMap<String, Object>();
		//车险组织根据组织业务Map因子值
		Double sumPaid = null;
		sumPaid = new Double(prpLcompensateDto.getSumThisPaid());
		// 折算为人民币的金额进行权限控制
		if (prpLcompensateDto.getExchangeRate() != 0 && prpLcompensateDto.getExchangeRate() != 1) {
			sumPaid = new Double(sumPaid.doubleValue() * prpLcompensateDto.getExchangeRate());
		}
		// 总核赔金额
		businessDataMap.put("SumPaid", sumPaid);
		// 是否实收
		businessDataMap.put(UwHPFactorCode.Car.RealPayFlag, this.checkPay(prpLcompensateDto.getPolicyNo()));
		// 是否倒签
		businessDataMap.put("BackOperation", this.isBackOperation(prpLcompensateDto));
		// 分险别的核赔金额
		businessDataMap.put("KindSumRealPay", this.getKindLossMap(prpLcompensateDto));

		return businessDataMap;
	}

	/**
	 * 意健险业务数据组织
	 * @param dbManager
	 * @param prpLcompensate
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> getBusinessShiPei_RiskType_E(PrpLcompensate prpLcompensate) throws Exception {
		Map<String,Object> businessDataMap = new HashMap<String,Object>();
		double sumThisPaid = this.getSumCoinsPaid(prpLcompensate);
		String isBackOperationForYuyue = this.isBackOperationForYuyue(prpLcompensate);
		String isDailiOrJingji = this.isDailiOrJingji(prpLcompensate);
		// 折算为人民币的金额进行权限控制
		if (prpLcompensate.getExchangeRate() != 0 && prpLcompensate.getExchangeRate() != 1) {
			sumThisPaid = sumThisPaid * prpLcompensate.getExchangeRate();
		}
		String riskCode = prpLcompensate.getRiskCode();
		if (this.isUnitProduct(riskCode)) {
			businessDataMap.put("RiskSumRealPay", this.getRiskLossMap(prpLcompensate));
		} else {
			businessDataMap.put(UwHPFactorCode.Acci.KindSumRealPay, this.getKindLossMap(prpLcompensate));
		}
		//非车需要根据核批当前时间来判断是否进行了实收
		businessDataMap.put(UwHPFactorCode.Acci.RealPayFlag, this.checkPay(prpLcompensate));
		businessDataMap.put("SumPaid", new Double(sumThisPaid));
		businessDataMap.put("BackOperationYuyue", isBackOperationForYuyue);
		if ("N".equals(isBackOperationForYuyue)) {
			businessDataMap.put("BackOperation", "Y");
		} else {
			businessDataMap.put("BackOperation", this.isBackOperation(prpLcompensate));
		}
		if ("N".equals(isDailiOrJingji)) {
			// 是否倒签单
			businessDataMap.put("AgentBackOperation", this.isBackOperation(prpLcompensate));
		} else {
			businessDataMap.put("AgentBackOperation", "Y");
		}
		return businessDataMap;
	}

	/**
	 * 财产险业务数据组织
	 * @param dbManager
	 * @param prpLcompensate
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> getBusinessShiPei_RiskType_Q(PrpLcompensate prpLcompensate) throws Exception {
		Map<String,Object> businessDataMap = new HashMap<String,Object>();
		// 分险别的核赔金额
		String riskCode = prpLcompensate.getRiskCode();
		String isBackOperationForYuyue = this.isBackOperationForYuyue(prpLcompensate);
		String isDailiOrJingji = this.isDailiOrJingji(prpLcompensate);
		if (this.isUnitProduct(riskCode)) {
			// 组合产品
			businessDataMap.put("RiskSumRealPay", this.getRiskLossMap(prpLcompensate));
		} else {
			businessDataMap.put(UwHPFactorCode.Acci.KindSumRealPay, this.getKindLossMap(prpLcompensate));
		}
		// 是否是预约协议倒签单
		businessDataMap.put("BackOperationYuyue", isBackOperationForYuyue);
		if ("N".equals(isBackOperationForYuyue)) {
			businessDataMap.put("BackOperation", "Y");
		} else {
			// 是否倒签
			businessDataMap.put("BackOperation", this.isBackOperation(prpLcompensate));
		}
		// 计算代理/经纪倒签单天数
		if ("N".equals(isDailiOrJingji)) {
			// 是否倒签单
			businessDataMap.put("AgentBackOperation", this.isBackOperation(prpLcompensate));
		} else {
			businessDataMap.put("AgentBackOperation", "Y");
		}
		// 非车需要根据核批当前时间来判断是否进行了实收
		businessDataMap.put(UwHPFactorCode.Car.RealPayFlag, checkPay(prpLcompensate));
		// 总核赔金额   添加从共保情况的的分摊金额
		double sumThisPaid = this.getSumCoinsPaid(prpLcompensate);
		// 折算为人民币的金额进行权限控制
		if (prpLcompensate.getExchangeRate() != 0 && prpLcompensate.getExchangeRate() != 1) {
			sumThisPaid = sumThisPaid * prpLcompensate.getExchangeRate();
		}
		businessDataMap.put("SumPaid", new Double(sumThisPaid));
		return businessDataMap;
	}

	/**
	 * 实赔业务因子数据获取 保费是否实收
	 */
	private String checkPay(String policyNo) throws Exception {
		String conditions1 = " policyno = '" + policyNo + "'";
		int realPayFlag = this.getPolicyService().checkPay(conditions1);// -1为未缴费，0为未缴全，1为缴全
		return realPayFlag == 1 ? "Y" : "N";
	}

	/***
	 * 是否不是倒签业务 Y：不是倒签单。N：是倒签单
	 * @param prpLcompensate
	 * @return
	 * @throws Exception
	 */
	private String isBackOperation(PrpLcompensate prpLcompensate) throws Exception {
		DateTime damageDate = null; // 出险时间
		DateTime underWriteDate = null; // 核保通过时间
		PrpLclaim prpLclaim = this.getPrpLclaimService().findPrpLclaim(prpLcompensate.getClaimNo());
		damageDate = new DateTime(prpLclaim.getDamageStartDate(),DateTime.YEAR_TO_DAY);
		PrpCmain prpCmain = this.getPrpCmainService().findByPrimaryKey(prpLcompensate.getPolicyNo());
		underWriteDate = new DateTime(prpCmain.getUnderwriteEndDate(),DateTime.YEAR_TO_DAY);
		if (damageDate != null && underWriteDate != null && damageDate.before(underWriteDate)) {
			//出险日期在核保日期之前，即为倒签单业务
			int backOperatDays = 0;
			backOperatDays = (int) ((underWriteDate.getTime() - damageDate.getTime()) / 86400000);
			if (backOperatDays > 0) {
				return "" + backOperatDays;
			} else {
				return "Y";
			}
		}
		return "Y";
	}

	/***
	 * 得到分险别的核赔金额
	 * @param prpLcompensate
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> getKindLossMap(PrpLcompensate prpLcompensate) throws Exception {
		return this.getKindSumRealPayMap(prpLcompensate.getCompensateNo());
	}
	
    @SuppressWarnings("unchecked")
	public Map<String,Object> getKindSumRealPayMap(String compensateNo) throws Exception{
    	Map<String,Object> kindSumRealPayMap = new HashMap<String,Object>();
    	StringBuffer sb = new StringBuffer();
    	sb.append("Select kindcode,Sum(sumrealpay) sumrealpay");
    	sb.append(" From(");
    	sb.append("Select kindcode,Sum(sumrealpay) sumrealpay From prplloss Where compensateno =? Group By kindcode");
    	sb.append(" Union ");
    	sb.append("Select kindcode,Sum(sumrealpay) sumrealpay From prplpersonloss Where compensateno = ? Group By kindcode");
    	sb.append(" Union ");
    	sb.append("Select kindcode,Sum(sumrealpay) sumrealpay From prplcharge Where compensateno = ? Group By kindcode");
    	sb.append(")");
    	sb.append(" Group By kindcode");
    	Query query = super.getSession().createSQLQuery(sb.toString());
    	query.setString(0, compensateNo);
    	query.setString(1, compensateNo);
    	query.setString(2, compensateNo);
    	List<Object[]> list = query.list();
    	for(Object[] object :list){
    		kindSumRealPayMap.put(String.valueOf(object[0]),object[1]);
    	}
    	return kindSumRealPayMap;
    }

	/***
	 * 得到通用险种的各险种核赔金额
	 * @param prpLcompensate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Object> getRiskLossMap(PrpLcompensate prpLcompensate) throws Exception {
		Map<String,Object> comboDataValueMap = new HashMap<String,Object>();
		Map<String,Object> kindLossMap = this.getKindSumRealPayMap(prpLcompensate.getCompensateNo());
		String productCode = "";
		productCode = this.getProductCode(prpLcompensate.getPolicyNo());
		comboDataValueMap.put("productCode", productCode);
		String strSQL = "('";
		if (kindLossMap.size() > 0) {
			for (Iterator<String> iter = kindLossMap.keySet().iterator(); iter.hasNext();) {
				String kindCode = (String) iter.next();
				strSQL += kindCode + "','";
			}
		} else {
			strSQL = strSQL + "'')";
		}
		strSQL = strSQL.substring(0, strSQL.length() - 2) + ")";
		String statement = "SELECT DISTINCT SUBRISKCODE,KINDCODE FROM PRPDPRODUCTFEE WHERE  PRODUCTCODE = '" + productCode + "' AND KINDCODE IN " + strSQL;
		List<Object[]> resultList = super.getSession().createSQLQuery(statement).list();
		for(Object[] object : resultList){
			String riskCode = String.valueOf(object[0]);
			String kindCode = String.valueOf(object[1]);
			if (!comboDataValueMap.containsKey(riskCode)) {
				comboDataValueMap.put(riskCode, kindLossMap.get(kindCode));
			} else {
				Object valueTemp = null;
				valueTemp = new Double(((Double) comboDataValueMap.get(riskCode)).doubleValue() + ((Double) kindLossMap.get(kindCode)).doubleValue());
				comboDataValueMap.put(riskCode, valueTemp);
			}
		}
		return comboDataValueMap;
	}
	/**
	 * 控制非车分期缴费业务在正常缴费情况下允许核赔 保费是否实收
	 */
	private String checkPay(PrpLcompensate prpLcompensateDto) throws SQLException, Exception {
		String NowDate = DateTime.current().toString(DateTime.YEAR_TO_DAY); //得到当前日期
		String conditions1 = " policyno = '" + prpLcompensateDto.getPolicyNo() + "' and plandate<=to_date('" + NowDate + "','yyyy-mm-dd')";
		int realPayFlag = this.getPolicyService().checkPay(conditions1);// -1为未缴费，0为未缴全，1为缴全
		return realPayFlag == 1 ? "Y" : "N";
	}

	/**
	 *  预赔金额是否超过估损金额的30%
	 */
	private String thirtyPercent(PrpLprepay prpLprepay) throws SQLException, Exception {
		String claimNo = prpLprepay.getClaimNo();
		PrpLclaim prpLclaimDto = this.getPrpLclaimService().findPrpLclaim(claimNo);
		double sumPaid = 0.00;
		sumPaid = prpLprepay.getSumPrePaid();
		double sumClaim = 0.00;
		if (prpLclaimDto != null) {
			sumClaim = prpLclaimDto.getSumClaim();
			if (sumPaid / sumClaim > 0.3) {
				return "N";
			} else {
				return "Y";
			}
		} else {
			throw new Exception("数据异常，请联系管理员！");
		}
	}

	/**
	 * 当是共保时，如为主共保方，则核赔金额/预赔金额按总赔款计；如为从共保方，则核赔金额/预赔金额按共保份额内分摊的赔款计
	 * @throws Exception
	 */
	private double getSumCoinsPaid(PrpLcompensate prpLcompensateDto) throws Exception {
		String strPolicyNo = prpLcompensateDto.getPolicyNo();
		PrpCmain prpCmain = this.getPrpCmainService().findByPrimaryKey(strPolicyNo);
		String strCoinsFlag = prpCmain.getCoinsFlag();
		double sumCoinsPaid = 0;
		if (strCoinsFlag.equals("2") || strCoinsFlag.equals("3")) {// 我方从联共保
			List<PrpCcoins> list = this.getPrpCcoinsService().findByConditions(" policyNO='" + strPolicyNo + "'");
			Iterator<PrpCcoins> it = list.iterator();
			PrpCcoins prpCcoins = null;
			double rate = 1;
			while (it.hasNext()) {
				prpCcoins = it.next();
				if ("2".equals(prpCcoins.getCoinsType())) {
					rate = prpCcoins.getCoinsRate() / 100;
					break;
				}
			}
			sumCoinsPaid = prpLcompensateDto.getSumDutyPaid() * rate;
		} else {
			sumCoinsPaid = prpLcompensateDto.getSumDutyPaid();
		}
		return sumCoinsPaid;
	}

	private double getSumPreCoinsPaid(PrpLprepay prpLprepayDto) throws Exception {
		String strPolicyNo = prpLprepayDto.getPolicyNo();
		PrpCmain prpCmain = this.getPrpCmainService().findByPrimaryKey(strPolicyNo);
		String strCoinsFlag = prpCmain.getCoinsFlag();
		double sumPreCoinsPaid = 0;
		if ("2".equals(strCoinsFlag) || "3".equals(strCoinsFlag)) {// 我方从联共保
			List<PrpCcoins> PrpCcoinsDtoList = this.getPrpCcoinsService().findByConditions(" policyNO='" + strPolicyNo + "'");
			Iterator<PrpCcoins> it = PrpCcoinsDtoList.iterator();
			PrpCcoins prpCcoins = null;
			double rate = 1;
			while (it.hasNext()) {
				prpCcoins = it.next();
				if ("2".equals(prpCcoins.getCoinsType())) {
					rate = prpCcoins.getCoinsRate() / 100;
					break;
				}
			}
			sumPreCoinsPaid = prpLprepayDto.getSumPrePaid() * rate;

		} else {
			sumPreCoinsPaid = prpLprepayDto.getSumPrePaid();
		}
		return sumPreCoinsPaid;
	}

	/**
	 * 财产险业务数据组织
	 * @param dbManager
	 * @param prpLcompensate
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> getBusinessShiPei_RiskType_Other(PrpLcompensate prpLcompensate) throws Exception {
		Map<String,Object> businessDataMap = new HashMap<String,Object>();
		// 分险别的核赔金额
		String riskCode = prpLcompensate.getRiskCode();
		String isBackOperationForYuyue = this.isBackOperationForYuyue(prpLcompensate);
		String isDailiOrJingji = this.isDailiOrJingji(prpLcompensate);
		if (this.isUnitProduct(riskCode)) {
			// 组合产品
			businessDataMap.put("RiskSumRealPay", this.getRiskLossMap(prpLcompensate));
		} else {
			businessDataMap.put(UwHPFactorCode.Acci.KindSumRealPay, this.getKindLossMap(prpLcompensate));
		}
		// 是否是预约协议倒签单
		businessDataMap.put("BackOperationYuyue", isBackOperationForYuyue);
		if ("N".equals(isBackOperationForYuyue)) {
			businessDataMap.put("BackOperation", "Y");
		} else {
			// 是否倒签
			businessDataMap.put("BackOperation", this.isBackOperation(prpLcompensate));
		}
		if ("N".equals(isDailiOrJingji)) {
			// 是否倒签单
			businessDataMap.put("AgentBackOperation", this.isBackOperation(prpLcompensate));
		} else {
			businessDataMap.put("AgentBackOperation", "Y");
		}
		// 是否实收 非车需要根据核批当前时间来判断是否进行了实收
		businessDataMap.put(UwHPFactorCode.Car.RealPayFlag, this.checkPay(prpLcompensate));
		double sumThisPaid = this.getSumCoinsPaid(prpLcompensate);
		// 折算为人民币的金额进行权限控制
		if (prpLcompensate.getExchangeRate() != 0 && prpLcompensate.getExchangeRate() != 1) {
			sumThisPaid = sumThisPaid * prpLcompensate.getExchangeRate();
		}
		// 总核赔金额
		businessDataMap.put("SumPaid", new Double(sumThisPaid));
		return businessDataMap;
	}

	/***
	 * 是否是预约协议倒签单 Y：不是倒签单。N：是倒签单
	 * @param prpLcompensateDto
	 * @return
	 * @throws Exception
	 */
	private String isBackOperationForYuyue(PrpLcompensate prpLcompensateDto) throws Exception {
		DateTime damageDate = null; // 出险时间
		DateTime underWriteDate = null; // 核保通过时间
		PrpLclaim prpLclaim = this.getPrpLclaimService().findPrpLclaim(prpLcompensateDto.getClaimNo());
		damageDate = new DateTime(prpLclaim.getDamageStartDate(),DateTime.YEAR_TO_DAY);
		PrpCmain prpCmain = this.getPrpCmainService().findByPrimaryKey(prpLcompensateDto.getPolicyNo());
		underWriteDate = new DateTime(prpCmain.getUnderwriteEndDate(),DateTime.YEAR_TO_DAY);
		if (damageDate != null && underWriteDate != null && damageDate.before(underWriteDate)) {
			//出险日期在核保日期之前，即为倒签单业务
			int backOperatDays = 0;
			backOperatDays = (int) ((underWriteDate.getTime() - damageDate.getTime()) / 86400000);
			if (("09".equals(prpCmain.getClassCode()) || "10".equals(prpCmain.getClassCode())) && prpCmain.getContractNo() != null && backOperatDays > 0) {
				return "N";
			} else {
				return "Y";
			}
		}
		return "Y";
	}

	/**
	 * 是否是组合产品
	 * @param dbManager
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	private boolean isUnitProduct(String riskCode) throws Exception {
		PrpDrisk prpDrisk = this.getPrpDriskService().findPrpDrisk(riskCode);
		if (prpDrisk != null && prpDrisk.getRiskFlag().length() > 13) {
			if ("T".equals(prpDrisk.getRiskFlag().substring(13, 14))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 根据保单号取得产品代码
	 * @param dbManager
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	private String getProductCode(String policyNo) throws Exception {
		String productCode = "";
		String statement = " SELECT PRODUCTCODE FROM PRPCPRODUCT WHERE POLICYNO = '" + policyNo + "'";
		List<?> list = super.getSession().createSQLQuery(statement).list();
		if(list!=null && !list.isEmpty()){
			return String.valueOf(list.get(0));
		}
		return productCode;
	}

	// 是否是代理/经纪 N：是代理/经纪。Y：不是代理/经纪
	private String isDailiOrJingji(PrpLcompensate prpLcompensate) throws Exception {
		String statement = "policyno = '" + prpLcompensate.getPolicyNo() + "' and BusinessNature in ('1','2','3','4','5','d','g')";
		int count = this.getPrpCmainService().getCount1(statement);
		if (count != 0) {
			return "N";
		} else {
			return "Y";
		}

	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}
}
