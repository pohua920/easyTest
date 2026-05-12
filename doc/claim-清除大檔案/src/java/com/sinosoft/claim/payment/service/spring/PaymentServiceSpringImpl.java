package com.sinosoft.claim.payment.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;
import ins.framework.utils.DataUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.payment.service.facade.PayMentService;
import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCitemCarExt;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLplan;
import com.sinosoft.claim.schema.model.PrpLplanKind;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarExtService;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLFMappingService;
import com.sinosoft.claim.schema.service.facade.PrpLchargeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLplanKindService;
import com.sinosoft.claim.schema.service.facade.PrpLplanService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.payment.common.interf.webService.PaymentWebService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.utility.string.Str;

/**
 * 收付接口实现类
 * @author 中科软
 */
public class PaymentServiceSpringImpl implements PayMentService {
	/** 预赔登记service */
	private PrpLprepayService prpLprepayService;
	/** 赔款计算书信息service */
	private PrpLcompensateService prpLcompensateService;
	/** 保单基本信息service */
	private PrpCmainService prpCmainService;
	/** 共保信息service */
	private PrpCcoinsService prpCcoinsService;
	/** 赔案收费计划service */
	private PrpLplanService prpLplanService;
	/** 赔案险种组合收费计划service */
	private PrpLplanKindService prpLplanKindService;
	/** 立案信息service */
	private PrpLclaimService prpLclaimService;
	/** 理赔费用与收付原因对照service */
	private PrpLFMappingService prpLFMappingService;
	/** 标的子险信息service */
	private PrpCitemKindService prpCitemKindService;
	/** 机构信息service */
	private PrpDcompanyService prpDcompanyService;
	/** 险种service */
	private PrpDriskService prpDriskService;
	/** 投保车辆扩展信息service */
	private PrpCitemCarExtService prpCitemCarExtService;
	/** 赔款费用service */
	private PrpLchargeService prpLchargeService;
	/** 支付信息service */
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/** 机动车险标的service */
	private PrpCitemCarService prpCitemCarService;
	/** 收付service */
	private PaymentWebService paymentWebService;

	/**
	 * 系统数据交互
	 * @param businessType
	 * @param businessNo
	 * @throws Exception
	 */
	public void transData(String businessType, String businessNo, Map<?, ?> infoMap) throws Exception {
//		if ("Y".equals(businessType)) {
//			transPrepay(businessNo, infoMap);
//		} else 
		if ("C".equals(businessType)) {
			transCompensate(businessNo, infoMap);
		} else if ("Z".equals(businessType)) {
			transCancel(businessNo);
		} else if ("R".equals(businessType)) {
			transReplevy(businessNo, infoMap);
		} else if ("S".equals(businessType)) {
			transRemnant(businessNo, infoMap);
		} else {
			throw new UserException(-98, -1149, "收付類型==" + businessType, "收付類型出錯！");
		}
	}

	/**
	 * 残余物
	 * @param compensateNo
	 * @param infoMap
	 * @throws Exception
	 */
	private void transRemnant(String compensateNo, Map<?, ?> infoMap) throws Exception {
		PrpLcompensate prpLcompensate = getPrpLcompensateService().findPrpLcompensate(compensateNo);
		PrpLclaim prpLclaim = getPrpLclaimService().findPrpLclaim(prpLcompensate.getClaimNo());
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString(DateTime.YEAR_TO_DAY);

		if (null != prpLcompensate) {
			Map<String, String> codeMap = getPrpLFMappingService().findMapByConditions(" 1=1 ");
			Map<String, Object> serialNoMap = new HashMap<String, Object>();
			int serialNo = 0;
			double sumPaid = prpLcompensate.getSumDutyPaid();
			List<PrpLplan> prpLplanList = new ArrayList<PrpLplan>();
			List<PrpLcharge> chargeList = getPrpLchargeService().findPrpLchargeList(compensateNo);
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.compensateNo", compensateNo);
			List<PrpLpayObjectInfo> resultList = getPrpLpayObjectInfoService().findPrpLpayObjectInfo(queryRule);
			Map<String,PrpLpayObjectInfo> chargeMap = new HashMap<String,PrpLpayObjectInfo>();
			List<PrpLpayObjectInfo> payObjectList = new ArrayList<PrpLpayObjectInfo>();
			if (resultList!=null && !resultList.isEmpty()) {
				for (PrpLpayObjectInfo p : resultList) {
					if(PrpLpayObjectInfo.CERTITYPE_CHARGE.equals(p.getId().getCertiType())){//费用的赔付对象
						chargeMap.put(p.getId().getCompensateNo()+"_"+p.getId().getCertiType()+"_"+p.getId().getSerialNo(), p);
					}else if(PrpLpayObjectInfo.CERTITYPE_PAYOBJECT.equals(p.getId().getCertiType())){//追偿收取的对象
						payObjectList.add(p);
					}
				}
			}
			PrpCmain prpCmain = getPrpCmainService().findByPrimaryKey(prpLcompensate.getPolicyNo());
			List<PrpCitemCarExt> prpCitemCarExtList = getPrpCitemCarExtService().findByPolicyNo(prpLcompensate.getPolicyNo());
			String coinsFlag = prpCmain.getCoinsFlag();// 联共保标志
			String isCombin = getIsCombin(prpCmain.getRiskCode());
			DateTime inputDate = DateTime.current();
			PrpLcharge prpLcharge = null;
			PrpLplan prpLplan = null;
//			PrpLplan prpLplans = null;
//			PrpLplanId prpLplanTempId = null;
//			List<PrpLplanKind> prpLplanKindList = null;
			double coinsRate = 1;
			String coinsType1 = "";
			if(!"0".equals(coinsFlag)){
				List<PrpCcoins> prpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLcompensate.getPolicyNo() + "' and coinsCode='"+ConstantCodes.COMPANYCODE+"' ");
				if (null != prpCcoinsList && prpCcoinsList.size() > 0) {
					PrpCcoins prpCcoins = prpCcoinsList.get(0);
					coinsRate = prpCcoins.getCoinsRate() / 100;
					coinsType1 = prpCcoins.getChiefFlag();
				}
			}
			serialNoMap.put("coinsRate_0", coinsRate);
//			if (!"F".equals(prpLcompensate.getClassCode())&& coinsFlag.equals("1")) {// 我方主联共保
//				// "******************我方主联共保****************" + compensateNo);
//				List<PrpCcoins> prpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLcompensate.getPolicyNo() + "'");
//				if (null != prpCcoinsList) {
//					for (int i = 0; i < prpCcoinsList.size(); i++) {
//						PrpCcoins prpCcoins = prpCcoinsList.get(i);
//						String coinsType = prpCcoins.getCoinsType();
//						// double sjFee = 0;
//						if ("0".equals(prpLcompensate.getIsPayForOther()) && !"2".equals(coinsType)) {
//							continue;
//						}
//						// 费用记录
//						if (null != chargeList) {
//							PrpLpayObjectInfo prpLpayObjectInfo = null;
//							for (int j = 0; j < chargeList.size(); j++) {
//								prpLcharge = chargeList.get(j);
//								prpLpayObjectInfo = chargeMap.get(prpLcharge.getId().getCompensateNo()+"_"+PrpLpayObjectInfo.CERTITYPE_CHARGE+"_"+prpLcharge.getId().getSerialNo());
//								prpLplan = new PrpLplan();
//								prpLplan.getId().setCertiType("C");
//								prpLplan.getId().setCertiNo(prpLcompensate.getCompensateNo());
//								prpLplan.getId().setSerialNo(++serialNo);
//								if (coinsType.equals("2")) {
//									prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "Z") + "");
//								} else {
//									prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "Z") + "");
//								}
//								prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
//								prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
//								prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
//								prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
//								prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
//								prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
//								prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
//								prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
//								prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());
//
//								prpLplan.setChargeCode(prpLcharge.getChargeCode());
//								prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
//								prpLplan.setRegistno(prpLclaim.getRegistNo());
//								prpLplan.setClaimNo(prpLcompensate.getClaimNo());
//								prpLplan.setClassCode(prpCmain.getClassCode());
//								prpLplan.setRiskCode(prpLcompensate.getRiskCode());
//								prpLplan.setPlanFeeCurrency(prpLcompensate.getCurrency());
//								prpLplan.setPayNo(1);
//								prpLplan.setTotalPayNo(1);
//								prpLplan.setPlanDate(inputDate);
//								prpLplan.setPlanFee(prpLcharge.getChargeAmount()*prpLcharge.getExchRate()/coinsRate * prpCcoins.getCoinsRate() / 100);
//								prpLplan.setAppliCode(prpCmain.getInsuredCode());
//								prpLplan.setAppliName(prpCmain.getInsuredName());
//								prpLplan.setInsuredCode(prpCmain.getInsuredCode());
//								prpLplan.setInsuredName(prpCmain.getInsuredName());
//								prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
//								prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
//								prpLplan.setAgentCode(prpCmain.getAgentCode());
//								prpLplan.setCoinsCode(prpCcoins.getCoinsCode());
//								prpLplan.setCoinsName(prpCcoins.getCoinsName());
//								prpLplan.setComCode(prpCmain.getComCode());
//								prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
//								prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
//								if (prpLplan.getBranchCode().equals("")) {
//									prpLplan.setBranchCode(prpLplan.getCenterCode());
//								}
//								prpLplan.setMakeCom(prpCmain.getMakeCom());
//								prpLplan.setBusinessNature(prpCmain.getBusinessNature());
//								prpLplan.setHandler1Code(prpCmain.getHandler1Code());
//								prpLplan.setHandlerCode(prpCmain.getHandlerCode());
//								prpLplan.setExchangeRate(prpLcompensate.getExchangeRate());
//								prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLcompensate.getExchangeRate());
//								prpLplan.setUnderWriteDate(inputDate);
//								if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
//									prpLplan.setCarModel(prpCitemCarExtList.get(0).getCartypeCode());
//								}
//								prpLplan.setChannelType(prpCmain.getChannelType());
//								prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
//								prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
//								prpLplan.setIsCombin(isCombin);// 组合险标示
//								prpLplan.setOthFlag("0");
//								prpLplan.setRemark("0");
//								prpLplan.setProcessFlag("0");
//								prpLplan.setInputDate(inputDate);
//								prpLplan.setCoinsFlag(coinsFlag);
//								prpLplan.setCoinsType(coinsType);
//								if (prpLplan.getPlanFee() != 0) {
//									prpLplanList.add(prpLplan);
//									serialNoMap.put("PRPLCHARGE_"+prpCcoins.getId().getSerialNo()+"_"+prpLcharge.getId().getSerialNo(), serialNo);
////									serialNoMap.put(prpLplan.getId().getPayRefReason() + prpLplan.getChargeCode(), new Integer(serialNo));
//								}
//								if (!coinsType.equals("2")) {// 他方从联共冲帳
//									prpLplans = new PrpLplan();
//									prpLplanTempId = prpLplans.getId();
//									prpLplanKindList = prpLplan.getPrpLPlanKinds();
//									prpLplan.setPrpLPlanKinds(null);
//									BeanUtils.copyProperties(prpLplan, prpLplans);
//									//属性的复制是浅拷贝，里面的对象只是复制了一个引用，需要在从新拷贝一边Id，修改新拷贝对象的引用
//									BeanUtils.copyProperties(prpLplan.getId(), prpLplanTempId);
//									prpLplans.setId(prpLplanTempId);
//									prpLplan.setPrpLPlanKinds(prpLplanKindList);
//									prpLplans.getId().setCertiType("C");
//									if (coinsFlag.equals("1")) {
//										prpLplans.getId().setPayRefReason("M" + prpLplans.getId().getPayRefReason().substring(1));
//									} else {
//										prpLplans.getId().setPayRefReason("N" + prpLplans.getId().getPayRefReason().substring(1));
//									}
//									prpLplans.getId().setSerialNo(++serialNo);
//									prpLplans.setPlanFee(-prpLplan.getPlanFee());
//									prpLplans.setPlanFeeCNY(-prpLplan.getPlanFeeCNY());
//									if (prpLplans.getPlanFee() != 0) {
//										prpLplanList.add(prpLplans);
//										serialNoMap.put("PRPCCOINS_" + +prpCcoins.getId().getSerialNo()+"_"+ prpLplan.getId().getSerialNo(), serialNo);
//										serialNoMap.put("PAYREFREASON_" + serialNo, prpLplans.getId().getPayRefReason());
//									}
//								}
//							}
//						}
//						// 赔付对象
//						if (null != payObjectList) {
//							for (PrpLpayObjectInfo prpLpayObjectInfo : payObjectList) {
//								prpLplan = new PrpLplan();
//								prpLplan.getId().setCertiType("C");
//								prpLplan.getId().setCertiNo(prpLpayObjectInfo.getId().getCompensateNo());
//								prpLplan.getId().setSerialNo(++serialNo);
//								prpLplan.getId().setPayRefReason("S60");
//								prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
//								prpLplan.setRegistno(prpLclaim.getRegistNo());
//								prpLplan.setClaimNo(prpLcompensate.getClaimNo());
//								prpLplan.setClassCode(prpCmain.getClassCode());
//								prpLplan.setRiskCode(prpLcompensate.getRiskCode());
//								prpLplan.setPlanFeeCurrency(prpLcompensate.getCurrency());
//								prpLplan.setPayNo(1);
//								prpLplan.setTotalPayNo(1);
//								prpLplan.setPlanDate(inputDate);
//								prpLplan.setPlanFee(prpLpayObjectInfo.getPayAmount() * prpCcoins.getCoinsRate() / 100);
//								prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
//								prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
//								prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
//								prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
//								prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
//								prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
//								prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
//								prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
//								prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());
//								prpLplan.setAppliCode(prpCmain.getInsuredCode());
//								prpLplan.setAppliName(prpCmain.getInsuredName());
//								prpLplan.setInsuredCode(prpCmain.getInsuredCode());
//								prpLplan.setInsuredName(prpCmain.getInsuredName());
//								prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
//								prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
//								prpLplan.setAgentCode(prpCmain.getAgentCode());
//								prpLplan.setCoinsCode(prpCcoins.getCoinsCode());
//								prpLplan.setCoinsName(prpCcoins.getCoinsName());
//								prpLplan.setComCode(prpCmain.getComCode());
//								prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
//								prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
//								if (prpLplan.getBranchCode().equals("")) {
//									prpLplan.setBranchCode(prpLplan.getCenterCode());
//								}
//								prpLplan.setMakeCom(prpCmain.getMakeCom());
//								prpLplan.setBusinessNature(prpCmain.getBusinessNature());
//								prpLplan.setHandler1Code(prpCmain.getHandler1Code());
//								prpLplan.setHandlerCode(prpCmain.getHandlerCode());
//								prpLplan.setExchangeRate(prpLcompensate.getExchangeRate());
//								prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLcompensate.getExchangeRate());
//								prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
//								prpLplan.setIsCombin(isCombin);// 组合险标示
//								prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
//								prpLplan.setOthFlag("0");
//								prpLplan.setRemark("0");
//								prpLplan.setProcessFlag("0");
//								prpLplan.setCoinsFlag(coinsFlag);
//								prpLplan.setCoinsType(coinsType);
//								prpLplan.setInputDate(inputDate);
//								if ("C".equals(prpLpayObjectInfo.getOwnerShip())) {
//									// 如果是现金支付，设置付款日期
//									prpLplan.setRealDate(prpLpayObjectInfo.getPayDate());
//								}
//								if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
//									prpLplan.setCarModel(prpCitemCarExtList.get(0).getCartypeCode());
//								}
//								prpLplan.setChannelType(prpCmain.getChannelType());
//								prpLplan.setUnderWriteDate(inputDate);
//								if (prpLplan.getPlanFee() != 0) {
//									prpLplanList.add(prpLplan);
//									serialNoMap.put("PRPLPAYOBJECTINFO_" + +prpCcoins.getId().getSerialNo()+"_"+ prpLpayObjectInfo.getId().getSerialNo(), serialNo);
//									serialNoMap.put("PAYREFREASON_" + serialNo, prpLplan.getId().getPayRefReason());
//								}
//								if (!coinsType.equals("2")) {// 他方从联共冲帳
//									prpLplans = new PrpLplan();
//									prpLplanKindList = prpLplan.getPrpLPlanKinds();
//									PrpLplanId prplplanIds = prpLplans.getId();
//									prpLplan.setPrpLPlanKinds(null);
//									BeanUtils.copyProperties(prpLplan, prpLplans);
//									BeanUtils.copyProperties(prpLplan.getId(), prplplanIds);
//									prpLplans.setId(prplplanIds);
//									prpLplan.setPrpLPlanKinds(prpLplanKindList);
//									prpLplans.getId().setCertiType("C");
//									prpLplans.getId().setPayRefReason("S60");
//									prpLplans.getId().setSerialNo(++serialNo);
//									prpLplans.setPlanFee(-prpLplan.getPlanFee());
//									prpLplans.setPlanFeeCNY(-prpLplan.getPlanFeeCNY());
//									if (prpLplans.getPlanFee() != 0) {
//										prpLplanList.add(prpLplans);
//										serialNoMap.put("PRPCCOINS_" + +prpCcoins.getId().getSerialNo()+"_"+ prpLplan.getId().getSerialNo(), serialNo);
//										serialNoMap.put("PAYREFREASON_" + serialNo, prpLplans.getId().getPayRefReason());
//									}
//								}
//							}
//						}
//					}
//				}
//			} else {// 独家承保和我方从联共保
				// "********************独家承保和我方从联共保******************" +
				// 费用记录
				if (null != chargeList) {
					PrpLpayObjectInfo prpLpayObjectInfo = null;
					for (int j = 0; j < chargeList.size(); j++) {
						prpLcharge = chargeList.get(j);
						prpLpayObjectInfo = chargeMap.get(prpLcharge.getId().getCompensateNo()+"_"+PrpLpayObjectInfo.CERTITYPE_CHARGE+"_"+prpLcharge.getId().getSerialNo());
						prpLplan = new PrpLplan();
						prpLplan.getId().setCertiType("C");
						prpLplan.getId().setCertiNo(prpLcompensate.getCompensateNo());
						prpLplan.getId().setSerialNo(++serialNo);
						prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "Z") + "");
						prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
						prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
						prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
						prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
						prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
						prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
						prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
						prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
						prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());

						prpLplan.setChargeCode(prpLcharge.getChargeCode());
						prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
						prpLplan.setRegistno(prpLclaim.getRegistNo());
						prpLplan.setClaimNo(prpLcompensate.getClaimNo());
						prpLplan.setClassCode(prpCmain.getClassCode());
						prpLplan.setRiskCode(prpLcompensate.getRiskCode());
						prpLplan.setPlanFeeCurrency(prpLcompensate.getCurrency());
						prpLplan.setPayNo(1);
						prpLplan.setTotalPayNo(1);
						prpLplan.setPlanDate(inputDate);
						prpLplan.setPlanFee(prpLcharge.getChargeAmount()*prpLcharge.getExchRate());
						prpLplan.setAppliCode(prpCmain.getInsuredCode());
						prpLplan.setAppliName(prpCmain.getInsuredName());
						prpLplan.setInsuredCode(prpCmain.getInsuredCode());
						prpLplan.setInsuredName(prpCmain.getInsuredName());
						prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
						prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
						prpLplan.setAgentCode(prpCmain.getAgentCode());
//						prpLplan.setCoinsCode(prpCcoins.getCoinsCode());
//						prpLplan.setCoinsName(prpCcoins.getCoinsName());
						prpLplan.setComCode(prpCmain.getComCode());
						prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
						prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
						if (prpLplan.getBranchCode().equals("")) {
							prpLplan.setBranchCode(prpLplan.getCenterCode());
						}
						prpLplan.setMakeCom(prpCmain.getMakeCom());
						prpLplan.setBusinessNature(prpCmain.getBusinessNature());
						prpLplan.setHandler1Code(prpCmain.getHandler1Code());
						prpLplan.setHandlerCode(prpCmain.getHandlerCode());
						prpLplan.setExchangeRate(prpLcompensate.getExchangeRate());
						prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLcompensate.getExchangeRate());
						prpLplan.setUnderWriteDate(inputDate);
						if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
							prpLplan.setCarModel(prpCitemCarExtList.get(0).getCartypeCode());
						}
						prpLplan.setChannelType(prpCmain.getChannelType());
						prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
						prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
						prpLplan.setIsCombin(isCombin);// 组合险标示
						prpLplan.setOthFlag("0");
						prpLplan.setRemark("0");
						prpLplan.setProcessFlag("0");
						prpLplan.setInputDate(inputDate);
						prpLplan.setCoinsFlag(coinsFlag);
						prpLplan.setCoinsType(coinsType1);
						if (prpLplan.getPlanFee() != 0) {
							prpLplanList.add(prpLplan);
							serialNoMap.put("PRPLCHARGE_"+"_"+prpLcharge.getId().getSerialNo(), serialNo);
						}
					}
				}
				// 赔付对象
				if (null != payObjectList) {
					for (PrpLpayObjectInfo prpLpayObjectInfo : payObjectList) {
						prpLplan = new PrpLplan();
						prpLplan.getId().setCertiType("C");
						prpLplan.getId().setCertiNo(prpLcompensate.getCompensateNo());
						prpLplan.getId().setSerialNo(++serialNo);
						prpLplan.getId().setPayRefReason("S60");
						prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
						prpLplan.setRegistno(prpLclaim.getRegistNo());
						prpLplan.setClaimNo(prpLcompensate.getClaimNo());
						prpLplan.setClassCode(prpCmain.getClassCode());
						prpLplan.setRiskCode(prpLcompensate.getRiskCode());
						prpLplan.setPlanFeeCurrency(prpLcompensate.getCurrency());

						prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
						prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
						prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
						prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
						prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
						prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
						prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
						prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
						prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());

						prpLplan.setPayNo(1);
						prpLplan.setTotalPayNo(1);
						prpLplan.setPlanDate(inputDate);
						prpLplan.setPlanFee(prpLpayObjectInfo.getPayAmount());
						prpLplan.setAppliCode(prpCmain.getInsuredCode());
						prpLplan.setAppliName(prpCmain.getInsuredName());
						prpLplan.setInsuredCode(prpCmain.getInsuredCode());
						prpLplan.setInsuredName(prpCmain.getInsuredName());
						prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
						prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
						prpLplan.setAgentCode(prpCmain.getAgentCode());
						prpLplan.setComCode(prpCmain.getComCode());
						prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
						prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
						if (prpLplan.getBranchCode().equals("")) {
							prpLplan.setBranchCode(prpLplan.getCenterCode());
						}
						prpLplan.setMakeCom(prpCmain.getMakeCom());
						prpLplan.setBusinessNature(prpCmain.getBusinessNature());
						prpLplan.setHandler1Code(prpCmain.getHandler1Code());
						prpLplan.setHandlerCode(prpCmain.getHandlerCode());
						prpLplan.setExchangeRate(prpLcompensate.getExchangeRate());
						prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLcompensate.getExchangeRate());
						prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
						prpLplan.setOthFlag("0");
						if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
							prpLplan.setCarModel(((PrpCitemCarExt) prpCitemCarExtList.get(0)).getCartypeCode());
						}
						prpLplan.setChannelType(prpCmain.getChannelType());
						prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
						prpLplan.setUnderWriteDate(inputDate);
						prpLplan.setIsCombin(isCombin);// 组合险标示
						prpLplan.setRemark("0");
						prpLplan.setProcessFlag("0");
						prpLplan.setInputDate(inputDate);
						prpLplan.setCoinsFlag(coinsFlag);
						prpLplan.setCoinsType(coinsType1);
						if (prpLplan.getPlanFee() != 0) {
							prpLplanList.add(prpLplan);
							serialNoMap.put("PRPLPAYOBJECTINFO_" + "_"+ prpLpayObjectInfo.getId().getSerialNo(), serialNo);
							serialNoMap.put("PAYREFREASON_" + serialNo, prpLplan.getId().getPayRefReason());
						}
					}
				}
//			}
			// 保存关联方标志位到prplplan的caseType字段中
			Iterator<PrpLplan> iterator = prpLplanList.iterator();
			while (iterator.hasNext()) {
				PrpLplan prplplan = (PrpLplan) iterator.next();
				prplplan.setCaseType(prpCmain.getSubBusinessNature());
			}
			// 保存关联方标志位到prplplan的caseType字段中//PAYREFREASON
			getPrpLplanService().save(prpLplanList);
			// ********************组合险标示isCombin=1组合险、2车险、0非组合险======";
			// 组合险种处理
//			String riskCode = prpLcompensate.getRiskCode();
			List<PrpLplanKind> prpLPlanKindList = new ArrayList<PrpLplanKind>();
//			if (!"2".equals(isCombin)) {// 其他组合险种
//				if (!"F".equals(prpLcompensate.getClassCode())&&coinsFlag.equals("1")) {// 我方主联共保
//					List<PrpCcoins> prpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLcompensate.getPolicyNo() + "' and coinsType='2' ");
//					if (null != prpCcoinsList) {
//						for (int i = 0; i < prpCcoinsList.size(); i++) {
//							PrpCcoins prpCcoins = (PrpCcoins) prpCcoinsList.get(i);
//							coinsRate = prpCcoins.getCoinsRate() / 100;
//							serialNoMap.put("PRPCCOINS_SERIALNO",prpCcoins.getId().getSerialNo());
//							prpLPlanKindList.addAll(getPrpLplanKindService().findRemnantByConditions(prpLcompensate.getCompensateNo(), codeMap, prpCcoins.getCoinsType(), coinsFlag, coinsRate, sumPaid, serialNoMap, damageDate, isCombin));
//						}
//					}
//				} else {
//					double coinsRate = 1;
//					if (coinsFlag.equals("2") || coinsFlag.equals("4")) {
//						List<PrpCcoins> prpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLcompensate.getPolicyNo() + "' and coinsType='1' ");
//						if (null != prpCcoinsList && prpCcoinsList.size() > 0) {
//							PrpCcoins prpCcoins = (PrpCcoins) prpCcoinsList.get(0);
//							coinsRate = prpCcoins.getCoinsRate() / 100;
//						}
//					}
					prpLPlanKindList = getPrpLplanKindService().findRemnantByConditions(prpLcompensate.getCompensateNo(), codeMap, "2", coinsFlag, coinsRate, sumPaid, serialNoMap, damageDate, isCombin);
//				}
//			} else if (isCombin.equals("2")) {
//				prpLPlanKindList = getPrpLplanKindService().findRemnantByConditions(prpLcompensate.getCompensateNo(), codeMap, "1", coinsFlag, 1, sumPaid, serialNoMap, damageDate, isCombin);
//			}
			int planL = prpLplanList.size();
			int kindL = prpLPlanKindList.size();
			if (kindL > 0 && planL > 0) {
				// 尾差处理,因为费用类型的serialNo和赔款的serialNo都是从1开始增加的，费用和赔款有相同的值。金额算在一起了。添加赔付原因做区分
				String planPayRefReason = null;
				String kindPayRefReason = null;
				for (int x = 0; x < planL; x++) {
					int serialNoP = ((PrpLplan) prpLplanList.get(x)).getId().getSerialNo();
					planPayRefReason = ((PrpLplan) prpLplanList.get(x)).getId().getPayRefReason();
					double planFee = ((PrpLplan) prpLplanList.get(x)).getPlanFee();
					double planFeeCNY = ((PrpLplan) prpLplanList.get(x)).getPlanFeeCNY();
					double sumKindFee = 0;
					double sumkindFeeCNY = 0;
					int endSerialNo = -1;
					double endKindFee = 0;
					for (int y = 0; y < kindL; y++) {
						int serialNoK = ((PrpLplanKind) prpLPlanKindList.get(y)).getId().getSerialNo();
						double kindFee = ((PrpLplanKind) prpLPlanKindList.get(y)).getKindFee();
						double kindFeeCNY = ((PrpLplanKind) prpLPlanKindList.get(y)).getPlanFeeCNY();
						kindPayRefReason = ((PrpLplanKind) prpLPlanKindList.get(y)).getId().getPayRefReason();
						if (serialNoP == serialNoK && planPayRefReason.equals(kindPayRefReason)) {
							sumKindFee += kindFee;
							sumkindFeeCNY += kindFeeCNY;
							endSerialNo = y;
							endKindFee = kindFee;
						}
					}
					if (endSerialNo != -1) {
						if (planFee - sumKindFee != 0) {
							((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).setKindFee(endKindFee + planFee - sumKindFee);
						}
						if (planFeeCNY - sumkindFeeCNY != 0) {
							((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).setPlanFeeCNY(endKindFee + planFeeCNY - sumkindFeeCNY);
						}
						if (((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).getKindCode().equals("M") && ((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).getKindFee() == 0) {
							throw new UserException(-98, -1149, "业务号：" + ((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).getId().getCertiNo(), "理算赔款金额中的不计免赔金额有误，请重新理算计算！");
						}
					}
				}
				getPrpLplanKindService().save(prpLPlanKindList);
			}
		}
	}

	/**
	 * 系统数据交互
	 * @param businessType
	 * @param businessNo
	 * @throws Exception
	 */
	public void send(String businessType, String businessNo) throws Exception {
		String condition = "";
		if ("Z".equals(businessType)) {
			condition = "certino = '" + businessNo + "' and OthFlag='1' ";
		} else {
			condition = "certino = '" + businessNo + "'";
		}
		Collection<?> collection = getPrpLplanService().findByConditions(condition);
		String syn = AppConfig.get("sysconst.CLAIMVERIFY_PAYMENT");
		if ("SYN".equals(syn)) {
			if (collection != null && collection.size() > 0) {
				sendSYN(businessType, businessNo);
			}
		} else {
			if (collection != null && collection.size() > 0) {
				getPaymentWebService().transClaimAll(businessType, businessNo);
			}
		}
	}

	/**
	 * 发送SYN
	 * @param businessType
	 * @param businessNo
	 * @throws Exception
	 */
	public void sendSYN(String businessType, String businessNo) throws Exception {
		String condition = "";
		if ("Z".equals(businessType)) {
			condition = "certino = '" + businessNo + "' and OthFlag='1' ";
		} else {
			condition = "certino = '" + businessNo + "'";
		}
		Collection<?> collection = getPrpLplanService().findByConditions(condition);
		if (collection != null && collection.size() > 0) {
			transClaim(businessType, businessNo);
		}
	}

	/**
	 * 预赔核赔通过与收付系统交互数据
	 * @param dbManager
	 * @param preCompensateNo
	 * @throws UserException
	 * @throws SQLException
	 * @throws Exception
	 */
//	public void transPrepay(String preCompensateNo, Map<?, ?> infoMap) throws UserException, SQLException, Exception {
//		PrpLprepay prpLprepay = getPrpLprepayService().findPrpLprepay(preCompensateNo);
//		if (null != prpLprepay) {
//			ArrayList<PrpLplan> prpLplanList = new ArrayList<PrpLplan>();
//			int serialNo = 0;
//			PrpLclaim prpLclaim = getPrpLclaimService().findPrpLclaim(prpLprepay.getClaimNo());
//			PrpCmain prpCmain = getPrpCmainService().findByPrimaryKey(prpLprepay.getPolicyNo());
//			List<PrpCitemCarExt> prpCitemCarExtList = getPrpCitemCarExtService().findByPolicyNo(prpLprepay.getPolicyNo());
//			String coinsFlag = prpCmain.getCoinsFlag();
//			DateTime inputDate = DateTime.current();
//			if (coinsFlag.equals("1") || coinsFlag.equals("3")) {
//				// 主联共保
//				// "********************我方主联共保******coinsFlag==" + coinsFlag);
//				List<PrpCcoins> PrpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLprepay.getPolicyNo() + "'");
//				if (null != PrpCcoinsList) {
//					for (int i = 0; i < PrpCcoinsList.size(); i++) {
//						PrpCcoins prpCcoins = PrpCcoinsList.get(i);
//						String coinsType = prpCcoins.getCoinsType();
//						if ("0".equals(prpLprepay.getIsPayForOther()) && !"1".equals(coinsType)) {
//							continue;
//						}
//						// 预赔记录
//						PrpLplan prpLplan = new PrpLplan();
//						prpLplan.getId().setCertiType("Y");
//						prpLplan.getId().setCertiNo(preCompensateNo);
//						prpLplan.getId().setSerialNo(++serialNo);
//						if (coinsType.equals("1")) {// 我方
//							prpLplan.getId().setPayRefReason("Y10");
//						} else {// 他方从联共保
//							if (coinsFlag.equals("1")) {// 共保
//								prpLplan.getId().setPayRefReason("S50");
//							} else if (coinsFlag.equals("3")) {// 联保
//								prpLplan.getId().setPayRefReason("F50");
//							}
//						}
//						prpLplan.setAccountCode(prpLprepay.getAccountCode());
//						prpLplan.setCustomBankCode(prpLprepay.getBankCode());
//						prpLplan.setCustomBankName(prpLprepay.getCustomBankName());
//						prpLplan.setCertificateCode(prpLprepay.getCertifiCateCode());
//						prpLplan.setOwnerName(prpLprepay.getOwnerName());
//						prpLplan.setOwnerPhoneNo(prpLprepay.getOwnerPhoneNo());
//						prpLplan.setAccountType(prpLprepay.getAccountType());
//						prpLplan.setAccountCurrency(prpLprepay.getAccountCurrency());
//						prpLplan.setOwnerShip(prpLprepay.getOwnership());
//
//						prpLplan.setPolicyNo(prpLprepay.getPolicyNo());
//						prpLplan.setRegistno(prpLclaim.getRegistNo());
//						prpLplan.setClaimNo(prpLprepay.getClaimNo());
//						prpLplan.setClassCode(prpCmain.getClassCode());
//						prpLplan.setRiskCode(prpLprepay.getRiskCode());
//						prpLplan.setPlanFeeCurrency(prpLprepay.getCurrency());
//						prpLplan.setPayNo(1);
//						prpLplan.setTotalPayNo(1);
//						prpLplan.setPlanDate(inputDate);
//						prpLplan.setPlanFee(prpLprepay.getSumPrePaid() * prpCcoins.getCoinsRate() / 100);
//						prpLplan.setAppliCode(prpCmain.getInsuredCode());
//						prpLplan.setAppliName(prpCmain.getInsuredName());
//						prpLplan.setInsuredCode(prpCmain.getInsuredCode());
//						prpLplan.setInsuredName(prpCmain.getInsuredName());
//						prpLplan.setStartDate(new DateTime(new DateTime(prpCmain.getStartDate())));
//						prpLplan.setEndDate(new DateTime(new DateTime(prpCmain.getEndDate())));
//						prpLplan.setAgentCode(prpCmain.getAgentCode());
//						prpLplan.setCoinsCode(prpCcoins.getCoinsCode());
//						prpLplan.setCoinsName(prpCcoins.getCoinsName());
//						prpLplan.setComCode(prpCmain.getComCode());
//						prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
//						prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
//						if (prpLplan.getBranchCode().equals("")) {
//							prpLplan.setBranchCode(prpLplan.getCenterCode());
//						}
//						prpLplan.setMakeCom(prpCmain.getMakeCom());
//						prpLplan.setBusinessNature(prpCmain.getBusinessNature());
//						prpLplan.setHandler1Code(prpCmain.getHandler1Code());
//						prpLplan.setHandlerCode(prpCmain.getHandlerCode());
//						prpLplan.setExchangeRate(prpLprepay.getExchangeRate());
//						prpLplan.setPlanFeeCNY((prpLprepay.getPaidCNY() * prpCcoins.getCoinsRate()) / 100);
//						prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
//						prpLplan.setInputDate(inputDate);
//						prpLplan.setOthFlag("0");
//						prpLplan.setRemark("0");
//						prpLplan.setProcessFlag("0");
//						prpLplan.setCoinsFlag(coinsFlag);
//						prpLplan.setCoinsType(prpCcoins.getCoinsType());
//						if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
//							prpLplan.setCarModel(prpCitemCarExtList.get(0).getCartypeCode());
//						}
//						prpLplan.setChannelType(prpCmain.getChannelType());
//						prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
//						prpLplan.setIsCombin("0");// 组合险标示
//						prpLplan.setUnderWriteDate(inputDate);
//						if (prpLplan.getPlanFee() != 0) {
//							prpLplanList.add(prpLplan);
//						}
//						if (!coinsType.equals("1")) {// 他方从联共冲帳
//							PrpLplan prpLplans = new PrpLplan();
//							List<PrpLplanKind> prpLplanKindList = new ArrayList<PrpLplanKind>();
//							serialNo++;
//							prpLplanKindList = prpLplan.getPrpLPlanKinds();
//							prpLplan.setPrpLPlanKinds(null);
//							BeanUtils.copyProperties(prpLplan, prpLplans);
//							prpLplan.setPrpLPlanKinds(prpLplanKindList);
//							prpLplans.getId().setCertiType("Y");
//							prpLplans.getId().setSerialNo(serialNo);
//							prpLplans.setPlanFee(-prpLplan.getPlanFee());
//							prpLplans.setPlanFeeCNY(-prpLplan.getPlanFeeCNY());
//							if (coinsFlag.equals("1")) {// 共保
//								prpLplans.getId().setPayRefReason("M50");
//							} else if (coinsFlag.equals("3")) {// 联保
//								prpLplans.getId().setPayRefReason("N50");
//							}
//							if (prpLplans.getPlanFee() != 0) {
//								prpLplanList.add(prpLplans);
//							}
//						}
//					}
//				}
//			} else {// 独家承保和从联共保
//				// 预赔记录
//				double coinsRate = 1;
//				String coinsType = "";
//				if (coinsFlag.equals("2") || coinsFlag.equals("4")) {
//					List<PrpCcoins> PrpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLprepay.getPolicyNo() + "' and coinsType='1' ");
//					if (null != PrpCcoinsList && PrpCcoinsList.size() > 0) {
//						PrpCcoins prpCcoins = PrpCcoinsList.get(0);
//						coinsRate = prpCcoins.getCoinsRate() / 100;
//						coinsType = prpCcoins.getCoinsType();
//					}
//				}
//				serialNo++;
//				// "********************独家承保和从联共保******coinsFlag==" +
//				// coinsFlag);
//				PrpLplan prpLplan = new PrpLplan();
//				prpLplan.getId().setCertiType("Y");
//				prpLplan.getId().setCertiNo(preCompensateNo);
//				prpLplan.getId().setSerialNo(serialNo);
//				prpLplan.getId().setPayRefReason("Y10"); // weizeyu 090220
//				prpLplan.setPolicyNo(prpLprepay.getPolicyNo());
//				prpLplan.setRegistno(prpLclaim.getRegistNo());
//				prpLplan.setClaimNo(prpLprepay.getClaimNo());
//				prpLplan.setClassCode(prpCmain.getClassCode());
//				prpLplan.setRiskCode(prpLprepay.getRiskCode());
//				prpLplan.setPlanFeeCurrency(prpLprepay.getCurrency());
//				prpLplan.setPayNo(1);
//				prpLplan.setTotalPayNo(1);
//				prpLplan.setPlanDate(inputDate);
//				prpLplan.setPlanFee(prpLprepay.getSumPrePaid() * coinsRate);
//				prpLplan.setAppliCode(prpCmain.getInsuredCode());
//				prpLplan.setAppliName(prpCmain.getInsuredName());
//				prpLplan.setInsuredCode(prpCmain.getInsuredCode());
//				prpLplan.setInsuredName(prpCmain.getInsuredName());
//				prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
//				prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
//				prpLplan.setAgentCode(prpCmain.getAgentCode());
//				prpLplan.setComCode(prpCmain.getComCode());
//				prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
//				prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
//				if (prpLplan.getBranchCode().equals("")) {
//					prpLplan.setBranchCode(prpLplan.getCenterCode());
//				}
//				prpLplan.setMakeCom(prpCmain.getMakeCom());
//				prpLplan.setBusinessNature(prpCmain.getBusinessNature());
//				prpLplan.setHandler1Code(prpCmain.getHandler1Code());
//				prpLplan.setHandlerCode(prpCmain.getHandlerCode());
//				prpLplan.setExchangeRate(prpLprepay.getExchangeRate());// 签单币别和本位币兑换率
//				prpLplan.setPlanFeeCNY(prpLprepay.getPaidCNY() * coinsRate);// 本位币赔款
//				prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));// 境内外标示
//				prpLplan.setOthFlag("0");
//				if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
//					prpLplan.setCarModel(prpCitemCarExtList.get(0).getCartypeCode());// 车型
//				}
//				prpLplan.setChannelType(prpCmain.getChannelType());// 渠道类型
//				prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
//				prpLplan.setUnderWriteDate(inputDate);
//				prpLplan.setInputDate(inputDate);
//				prpLplan.setIsCombin("0");// 组合险标示
//				prpLplan.setRemark("0");
//				prpLplan.setProcessFlag("0");
//				prpLplan.setCoinsFlag(coinsFlag);
//				prpLplan.setCoinsType(coinsType);
//
//				prpLplan.setAccountCode(prpLprepay.getAccountCode());
//				prpLplan.setCustomBankCode(prpLprepay.getBankCode());
//				prpLplan.setCustomBankName(prpLprepay.getCustomBankName());
//				prpLplan.setCertificateCode(prpLprepay.getCertifiCateCode());
//				prpLplan.setOwnerName(prpLprepay.getOwnerName());
//				prpLplan.setOwnerPhoneNo(prpLprepay.getOwnerPhoneNo());
//				prpLplan.setAccountType(prpLprepay.getAccountType());
//				prpLplan.setAccountCurrency(prpLprepay.getAccountCurrency());
//				prpLplan.setOwnerShip(prpLprepay.getOwnership());
//
//				if (prpLplan.getPlanFee() != 0) {
//					prpLplanList.add(prpLplan);
//				}
//			}
//			// 保存关联方标志位到prplplan的caseType字段中
//			Iterator<PrpLplan> iterator = prpLplanList.iterator();
//			while (iterator.hasNext()) {
//				PrpLplan prpLplan = (PrpLplan) iterator.next();
//				prpLplan.setCaseType(prpCmain.getSubBusinessNature());
//			}
//			getPrpLplanService().save(prpLplanList);
//		}
//	}

	public void transCompensate(String compensateNo, Map<?, ?> infoMap) throws UserException, Exception {
		PrpLcompensate prpLcompensate = getPrpLcompensateService().findPrpLcompensate(compensateNo);
		PrpLclaim prpLclaim = getPrpLclaimService().findPrpLclaim(prpLcompensate.getClaimNo());
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString(DateTime.YEAR_TO_DAY);
		if (null != prpLcompensate) {
			Map<String, String> codeMap = getPrpLFMappingService().findMapByConditions(" 1=1 ");
			Map<String, Object> serialNoMap = new HashMap<String, Object>();
			int serialNo = 0;
			double sumPaid = prpLcompensate.getSumDutyPaid();
//			List<PrpLFMapping> prpLFMappingList = getPrpLFMappingService().findByConditions(" 1=1 ");
//			if (null != prpLFMappingList) {
//				PrpLFMapping prpLFMapping = null;
//				for (int i = 0; i < prpLFMappingList.size(); i++) {
//					prpLFMapping = prpLFMappingList.get(i);
//					codeMap.put(prpLFMapping.getId().getChargeCode() + prpLFMapping.getId().getPayRefReason().substring(0, 1), prpLFMapping.getId().getPayRefReason());
//				}
//			}
			List<PrpLplan> prpLplanList = new ArrayList<PrpLplan>();
			List<PrpLcharge> chargeList = getPrpLchargeService().findPrpLchargeList(compensateNo);
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.compensateNo", compensateNo);
			List<PrpLpayObjectInfo> resultList = getPrpLpayObjectInfoService().findPrpLpayObjectInfo(queryRule);
			Map<String,PrpLpayObjectInfo> chargeMap = new HashMap<String,PrpLpayObjectInfo>();
			List<PrpLpayObjectInfo> payObjectList = new ArrayList<PrpLpayObjectInfo>();
			if (resultList!=null && !resultList.isEmpty()) {
				for (PrpLpayObjectInfo p : resultList) {
					if(PrpLpayObjectInfo.CERTITYPE_CHARGE.equals(p.getId().getCertiType())){//费用的赔付对象
						chargeMap.put(p.getId().getCompensateNo()+"_"+p.getId().getCertiType()+"_"+p.getId().getSerialNo(), p);
					}else if(PrpLpayObjectInfo.CERTITYPE_PAYOBJECT.equals(p.getId().getCertiType())){//追偿收取的对象
						payObjectList.add(p);
					}
				}
			}
			PrpCmain prpCmain = getPrpCmainService().findByPrimaryKey(prpLcompensate.getPolicyNo());
			List<PrpCitemCarExt> prpCitemCarExtList = getPrpCitemCarExtService().findByPolicyNo(prpLcompensate.getPolicyNo());
			String coinsFlag = prpCmain.getCoinsFlag();
			String isCombin = getIsCombin(prpCmain.getRiskCode());
			DateTime inputDate = DateTime.current();
			// 预赔总金额
			List<PrpLplan> prpLplanYList = getPrpLplanService().findByConditions(" claimNo='" + prpLcompensate.getClaimNo() + "' and CertiType='Y' ");
			PrpLplan prpLplanSumY = new PrpLplan();
			double sumPlanfee = 0;
			double sumPlanfeeCNY = 0;
			double sumYForOther = 0;
			double sumYForOtherCNY = 0;
			if (null != prpLplanYList) {
				List<PrpLplanKind> prpLplanKindList = null;
				for (int j = 0; j < prpLplanYList.size(); j++) {
					PrpLplan prpLplanY = (PrpLplan) prpLplanYList.get(j);
					prpLplanKindList = prpLplanY.getPrpLPlanKinds();
					prpLplanY.setPrpLPlanKinds(null);
					BeanUtils.copyProperties(prpLplanY, prpLplanSumY);
					prpLplanY.setPrpLPlanKinds(prpLplanKindList);
					sumPlanfee += prpLplanY.getPlanFee();
					sumPlanfeeCNY += prpLplanY.getPlanFeeCNY();
					if ("S50".equals(prpLplanY.getId().getPayRefReason())) {
						sumYForOther = sumYForOther + prpLplanY.getPlanFee();
						sumYForOtherCNY = sumYForOtherCNY + prpLplanY.getPlanFeeCNY();
					}
				}
			}
			PrpCcoins prpCcoins = null;
			PrpLcharge prpLcharge = null;
//			PrpLplan prpLplanTemp = null;
			PrpLplan prpLplan = null;
//			PrpLplanId prpLplanTempId = null;
//			List<PrpLplanKind>	prpLplanKindList = null;
			double coinsRate = 1;
			String coinsType1 = "";
			if(!"0".equals(coinsFlag)){
				//coinsCode =18 台壽保產物保險股份有限公司
				List<PrpCcoins> prpCcoinsList = getPrpCcoinsService().findByConditions(" policyNo='" + prpLcompensate.getPolicyNo() + "' and coinsCode='"+ConstantCodes.COMPANYCODE+"' ");
				if (null != prpCcoinsList && prpCcoinsList.size() > 0) {
					prpCcoins = prpCcoinsList.get(0);
					coinsRate = prpCcoins.getCoinsRate() / 100;
					coinsType1 = prpCcoins.getChiefFlag();
				}
			}
			serialNoMap.put("coinsRate_0", coinsRate);
//			if (!"F".equals(prpLcompensate.getClassCode())&&coinsFlag.equals("1")) {// 我方主联共保
//				// "******************我方主联共保****************" + compensateNo);
//				String strYFlag = "";// 预赔挂帳标志，用来只送一次预赔（Y20）
//				List<PrpCcoins> prpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLcompensate.getPolicyNo() + "'");
//				if (null != prpCcoinsList) {
//					for (int i = 0; i < prpCcoinsList.size(); i++) {
//						prpCcoins = prpCcoinsList.get(i);
//						String coinsType = prpCcoins.getCoinsType();
//						// double sjFee = 0;
//						if ("0".equals(prpLcompensate.getIsPayForOther()) && !"2".equals(coinsType)) {
//							continue;
//						}
//						// 费用记录
//						if (null != chargeList) {
//							PrpLpayObjectInfo prpLpayObjectInfo = null;
//							for (int j = 0; j < chargeList.size(); j++) {
//								prpLcharge = chargeList.get(j);
//								prpLpayObjectInfo = chargeMap.get(prpLcharge.getId().getCompensateNo()+"_"+PrpLpayObjectInfo.CERTITYPE_CHARGE+"_"+prpLcharge.getId().getSerialNo());
//								prpLplan = new PrpLplan();
//								prpLplan.getId().setCertiType("C");
//								prpLplan.getId().setCertiNo(prpLcompensate.getCompensateNo());
//								prpLplan.getId().setSerialNo(++serialNo);
//								if(prpLcompensate.getCompensateNo().startsWith("D")){
//									prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "D") + "");
//								}else{
//									if (coinsType.equals("2")) {
//										prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "P") + "");
//									} else {
//										prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "S") + "");
////									if (coinsFlag.equals("1")) {// 共保
////									} else if (coinsFlag.equals("3")) {// 联保
////										prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "F") + "");
////									}
//									}
//								}
//								prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
//								prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
//								prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
//								prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
//								prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
//								prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
//								prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
//								prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
//								prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());
//
//								prpLplan.setChargeCode(prpLcharge.getChargeCode());
//								prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
//								prpLplan.setRegistno(prpLclaim.getRegistNo());
//								prpLplan.setClaimNo(prpLcompensate.getClaimNo());
//								prpLplan.setClassCode(prpCmain.getClassCode());
//								prpLplan.setRiskCode(prpLcompensate.getRiskCode());
//								prpLplan.setPlanFeeCurrency(prpLcharge.getCurrency());
//								prpLplan.setPayNo(1);
//								prpLplan.setTotalPayNo(1);
//								prpLplan.setPlanDate(inputDate);
//								prpLplan.setPlanFee(prpLcharge.getChargeAmount()/coinsRate * prpCcoins.getCoinsRate() / 100);
//								prpLplan.setAppliCode(prpCmain.getInsuredCode());
//								prpLplan.setAppliName(prpCmain.getInsuredName());
//								prpLplan.setInsuredCode(prpCmain.getInsuredCode());
//								prpLplan.setInsuredName(prpCmain.getInsuredName());
//								prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
//								prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
//								prpLplan.setAgentCode(prpCmain.getAgentCode());
//								prpLplan.setCoinsCode(prpCcoins.getCoinsCode());
//								prpLplan.setCoinsName(prpCcoins.getCoinsName());
//								prpLplan.setComCode(prpCmain.getComCode());
//								prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
//								prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
//								if (prpLplan.getBranchCode().equals("")) {
//									prpLplan.setBranchCode(prpLplan.getCenterCode());
//								}
//								prpLplan.setMakeCom(prpCmain.getMakeCom());
//								prpLplan.setBusinessNature(prpCmain.getBusinessNature());
//								prpLplan.setHandler1Code(prpCmain.getHandler1Code());
//								prpLplan.setHandlerCode(prpCmain.getHandlerCode());
//								prpLplan.setExchangeRate(prpLcharge.getExchRate());
//								prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLcharge.getExchRate());
//								prpLplan.setPlanFeeCNY(DataUtils.round(prpLplan.getPlanFeeCNY(), 0));
//								prpLplan.setUnderWriteDate(inputDate);
//								if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
//									prpLplan.setCarModel(prpCitemCarExtList.get(0).getCartypeCode());
//								}
//								prpLplan.setChannelType(prpCmain.getChannelType());
//								prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
//								prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
//								prpLplan.setIsCombin(isCombin);// 组合险标示
//								prpLplan.setOthFlag("0");
//								prpLplan.setRemark("0");
//								prpLplan.setProcessFlag("0");
//								prpLplan.setInputDate(inputDate);
//								prpLplan.setCoinsFlag(coinsFlag);
//								prpLplan.setCoinsType(coinsType);
//								if (prpLplan.getPlanFee() != 0) {
//									prpLplanList.add(prpLplan);
//									serialNoMap.put("PRPLCHARGE_"+prpCcoins.getId().getSerialNo()+"_"+prpLcharge.getId().getSerialNo(), serialNo);
//								}
//
//								if (!coinsType.equals("2")) {// 他方从联共冲帳
//									prpLplanTemp = new PrpLplan();
//									prpLplanTempId = prpLplanTemp.getId();
//									prpLplanKindList = prpLplan.getPrpLPlanKinds();
//									prpLplan.setPrpLPlanKinds(null);
//									BeanUtils.copyProperties(prpLplan, prpLplanTemp);
//									//属性的复制是浅拷贝，里面的对象只是复制了一个引用，需要在从新拷贝一边Id，修改新拷贝对象的引用
//									BeanUtils.copyProperties(prpLplan.getId(), prpLplanTempId);
//									prpLplanTemp.setId(prpLplanTempId);
//									prpLplan.setPrpLPlanKinds(prpLplanKindList);
//									prpLplanTemp.getId().setCertiType("C");
//									if (coinsFlag.equals("1")) {
//										prpLplanTemp.getId().setPayRefReason("M" + prpLplanTemp.getId().getPayRefReason().substring(1));
//									} else {
//										prpLplanTemp.getId().setPayRefReason("N" + prpLplanTemp.getId().getPayRefReason().substring(1));
//									}
//									prpLplanTemp.getId().setSerialNo(++serialNo);
//									prpLplanTemp.setPlanFee(-prpLplan.getPlanFee());
//									prpLplanTemp.setPlanFeeCNY(-prpLplan.getPlanFeeCNY());
//									if (prpLplanTemp.getPlanFee() != 0) {
//										prpLplanList.add(prpLplanTemp);
//										serialNoMap.put("PRPCCOINS_"+prpCcoins.getId().getSerialNo()+"_"+prpLplan.getId().getSerialNo(), serialNo);
//										serialNoMap.put("PAYREFREASON_" + serialNo, prpLplanTemp.getId().getPayRefReason());
//									}
//								}
//							}
//						}
//						// 赔付对象
//						for (PrpLpayObjectInfo prpLpayObjectInfo : payObjectList) {
//							prpLplan = new PrpLplan();
//							prpLplan.getId().setCertiType("C");
//							prpLplan.getId().setCertiNo(prpLpayObjectInfo.getId().getCompensateNo());
//							prpLplan.getId().setSerialNo(++serialNo);
//							if (coinsType.equals("2")) {// 我方
//								// 如果费用类型为健保局，支付类型为PTJ
//								if ("6".equals(prpLpayObjectInfo.getPaymentKind())) {
//									prpLplan.getId().setPayRefReason("PTJ");
//								} else if ("7".equals(prpLpayObjectInfo.getPaymentKind())) {
//									// 如果费用类型为同业，支付类型为PTJ
//									prpLplan.getId().setPayRefReason("PTT");
//								} else {
//									prpLplan.getId().setPayRefReason("P60");
//								}
//							} else {// 他方从联共
//								prpLplan.getId().setPayRefReason("S60");
////								if (coinsFlag.equals("1")) {// 共保
////								} else if (coinsFlag.equals("3")) {// 联保
////									prpLplan.getId().setPayRefReason("F60");
////								}
//							}
//							prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
//							prpLplan.setRegistno(prpLclaim.getRegistNo());
//							prpLplan.setClaimNo(prpLcompensate.getClaimNo());
//							prpLplan.setClassCode(prpCmain.getClassCode());
//							prpLplan.setRiskCode(prpLcompensate.getRiskCode());
//							prpLplan.setPlanFeeCurrency(prpLpayObjectInfo.getCurrency());
//							prpLplan.setPayNo(1);
//							prpLplan.setTotalPayNo(1);
//							prpLplan.setPlanDate(inputDate);
//							prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
//							prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
//							prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
//							prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
//							prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
//							prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
//							prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
//							prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
//							prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());
//							prpLplan.setPlanFee(prpLpayObjectInfo.getPayAmount() * prpCcoins.getCoinsRate() / 100);
//							prpLplan.setAppliCode(prpCmain.getInsuredCode());
//							prpLplan.setAppliName(prpCmain.getInsuredName());
//							prpLplan.setInsuredCode(prpCmain.getInsuredCode());
//							prpLplan.setInsuredName(prpCmain.getInsuredName());
//							prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
//							prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
//							prpLplan.setAgentCode(prpCmain.getAgentCode());
//							prpLplan.setCoinsCode(prpCcoins.getCoinsCode());
//							prpLplan.setCoinsName(prpCcoins.getCoinsName());
//							prpLplan.setComCode(prpCmain.getComCode());
//							prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
//							prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
//							if (prpLplan.getBranchCode().equals("")) {
//								prpLplan.setBranchCode(prpLplan.getCenterCode());
//							}
//							prpLplan.setMakeCom(prpCmain.getMakeCom());
//							prpLplan.setBusinessNature(prpCmain.getBusinessNature());
//							prpLplan.setHandler1Code(prpCmain.getHandler1Code());
//							prpLplan.setHandlerCode(prpCmain.getHandlerCode());
//							prpLplan.setExchangeRate(prpLpayObjectInfo.getExchRate());
//							prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLpayObjectInfo.getExchRate());
//							prpLplan.setPlanFeeCNY(DataUtils.round(prpLplan.getPlanFeeCNY(), 0));
//							prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
//							prpLplan.setIsCombin(isCombin);// 组合险标示
//							prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
//							prpLplan.setOthFlag("0");
//							prpLplan.setRemark("0");
//							prpLplan.setProcessFlag("0");
//							prpLplan.setCoinsFlag(coinsFlag);
//							prpLplan.setCoinsType(coinsType);
//							prpLplan.setInputDate(inputDate);
//							if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
//								prpLplan.setCarModel(prpCitemCarExtList.get(0).getCartypeCode());
//							}
//							prpLplan.setChannelType(prpCmain.getChannelType());
//							prpLplan.setUnderWriteDate(inputDate);
//							if ("C".equals(prpLpayObjectInfo.getOwnerShip())) {
//								// 如果是现金支付，设置付款日期
//								prpLplan.setRealDate(prpLpayObjectInfo.getPayDate());
//							}
//							if (prpLplan.getPlanFee() != 0) {
//								prpLplanList.add(prpLplan);
//								serialNoMap.put("PRPLPAYOBJECTINFO_" + +prpCcoins.getId().getSerialNo()+"_"+ prpLpayObjectInfo.getId().getSerialNo(), serialNo);
//								serialNoMap.put("PAYREFREASON_" + serialNo, prpLplan.getId().getPayRefReason());
//							}
//							if(!"1".equals(strYFlag)){
//								int count = getPrpLplanService().getCount(" ClaimNo='" + prpLcompensate.getClaimNo() + "' and PayRefReason='Y10'"); // weizeyu
//								
//								int count1 = getPrpLplanService().getCount(" ClaimNo='" + prpLcompensate.getClaimNo() + "' and PayRefReason='Y20'"); // weizeyu
//								
//								if (count > 0 && count1 <= 0 && !"1".equals(strYFlag)) {
//									// 预付挂帳
//									if (null != prpLplanYList && prpLplanYList.size() > 0) {
//										strYFlag = "1";
//										serialNo++;
//										prpLplanSumY.getId().setCertiNo(prpLcompensate.getCompensateNo());
//										prpLplanSumY.getId().setCertiType("C");
//										prpLplanSumY.getId().setSerialNo(serialNo);
//										prpLplanSumY.getId().setPayRefReason("Y20");
//										prpLplanSumY.setIsCombin("0");
//										prpLplanSumY.setProcessFlag("0");
//										prpLplanSumY.setPlanFee(-sumPlanfee);
//										prpLplanSumY.setPlanFeeCNY(-sumPlanfeeCNY);
//										prpLplanSumY.setInputDate(inputDate);
//										if (sumPlanfee != 0) {
//											prpLplanSumY.setExchangeRate(Str.round(sumPlanfeeCNY / sumPlanfee, 6));
//										} else {
//											prpLplanSumY.setExchangeRate(0D);
//										}
//										if (prpLplanSumY.getPlanFee() != 0) {
//											prpLplanList.add(prpLplanSumY);
//											serialNoMap.put("PRPLPAYOBJECTINFO_" +prpCcoins.getId().getSerialNo()+"_"+ prpLplanSumY.getId().getPayRefReason(), serialNo);
////												serialNoMap.put("PRPLPAYOBJECTINFO_" + serialNo, prpLplan.getId().getPayRefReason());
////												serialNoMap.put(prpLplanSumY.getId().getPayRefReason() + prpLplanSumY.getChargeCode(), new Integer(serialNo));
//											// serialNoMap.put("PRPLPAYOBJECTINFO_"+
//											// prpLplanSumY.getId().getSerialNo(),prpLplanSumY.getId().getPayRefReason());
//										}
//									}
//								}
//							}
//							if (!coinsType.equals("2")) {// 他方从联共冲帳
//								prpLplanTemp = new PrpLplan();
//								prpLplanTempId = prpLplanTemp.getId();
//								prpLplanKindList = prpLplan.getPrpLPlanKinds();
//								prpLplan.setPrpLPlanKinds(null);
//								BeanUtils.copyProperties(prpLplan, prpLplanTemp);
//								//属性的复制是浅拷贝，里面的对象只是复制了一个引用，需要在从新拷贝一边Id，修改新拷贝对象的引用
//								BeanUtils.copyProperties(prpLplan.getId(), prpLplanTempId);
//								prpLplanTemp.setId(prpLplanTempId);
//								prpLplan.setPrpLPlanKinds(prpLplanKindList);
//								prpLplanTemp.getId().setCertiType("C");
//								if (coinsFlag.equals("1")) {
//									prpLplanTemp.getId().setPayRefReason("M60");
//								} else {
//									prpLplanTemp.getId().setPayRefReason("N60");
//								}
//								prpLplanTemp.getId().setSerialNo(++serialNo);
//								prpLplanTemp.setPlanFee(-prpLplan.getPlanFee());
//								prpLplanTemp.setPlanFeeCNY(-prpLplan.getPlanFeeCNY());
//								if (prpLplanTemp.getPlanFee() != 0) {
//									prpLplanList.add(prpLplanTemp);
//									serialNoMap.put("PRPCCOINS_" + +prpCcoins.getId().getSerialNo()+"_"+ prpLplan.getId().getSerialNo(), serialNo);
//									serialNoMap.put("PAYREFREASON_" + serialNo, prpLplanTemp.getId().getPayRefReason());
//								}
//							}
//						}
//					}
//				}
//			} else {// 独家承保和我方从联共保
				// "********************独家承保和我方从联共保******************" +
				// 费用记录
				if (null != chargeList) {
					PrpLpayObjectInfo prpLpayObjectInfo = null;
					for (int j = 0; j < chargeList.size(); j++) {
						prpLcharge =  chargeList.get(j);
						prpLpayObjectInfo = chargeMap.get(prpLcharge.getId().getCompensateNo()+"_"+PrpLpayObjectInfo.CERTITYPE_CHARGE+"_"+prpLcharge.getId().getSerialNo());
						prpLplan = new PrpLplan();
						prpLplan.getId().setCertiType("C");
						prpLplan.getId().setCertiNo(prpLpayObjectInfo.getId().getCompensateNo());
						prpLplan.getId().setSerialNo(++serialNo);
						if(prpLcompensate.getCompensateNo().startsWith("D")){
							prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "D") + "");
						}else{
							if (coinsFlag.equals("2")) {// 共保
								prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "S") + "");
							} else if (coinsFlag.equals("3")) {// 联保
								prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "F") + "");
							} else {
								prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "P") + "");
							}
						}
						prpLplan.setChargeCode(prpLcharge.getChargeCode());
						prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
						prpLplan.setRegistno(prpLclaim.getRegistNo());
						prpLplan.setClaimNo(prpLcompensate.getClaimNo());
						prpLplan.setClassCode(prpCmain.getClassCode());
						prpLplan.setRiskCode(prpLcompensate.getRiskCode());
						prpLplan.setPlanFeeCurrency(prpLcharge.getCurrency());
						prpLplan.setPayNo(1);
						prpLplan.setTotalPayNo(1);
						prpLplan.setPlanDate(inputDate);
						prpLplan.setPlanFee(prpLcharge.getChargeAmount());
						prpLplan.setAppliCode(prpCmain.getInsuredCode());
						prpLplan.setAppliName(prpCmain.getInsuredName());
						prpLplan.setInsuredCode(prpCmain.getInsuredCode());
						prpLplan.setInsuredName(prpCmain.getInsuredName());
						prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
						prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
						prpLplan.setAgentCode(prpCmain.getAgentCode());
						prpLplan.setComCode(prpCmain.getComCode());
						prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
						prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
						if (prpLplan.getBranchCode().equals("")) {
							prpLplan.setBranchCode(prpLplan.getCenterCode());
						}
						prpLplan.setMakeCom(prpCmain.getMakeCom());
						prpLplan.setBusinessNature(prpCmain.getBusinessNature());
						prpLplan.setHandler1Code(prpCmain.getHandler1Code());
						prpLplan.setHandlerCode(prpCmain.getHandlerCode());
						prpLplan.setExchangeRate(prpLcharge.getExchRate());
						prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLcharge.getExchRate());
						prpLplan.setPlanFeeCNY(DataUtils.round(prpLplan.getPlanFeeCNY(), 0));
						prpLplan.setUnderWriteDate(inputDate);
						prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
						prpLplan.setOthFlag("0");
						if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
							prpLplan.setCarModel(prpCitemCarExtList.get(0).getCartypeCode());
						}
						prpLplan.setChannelType(prpCmain.getChannelType());
						prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
						prpLplan.setIsCombin(isCombin);// 组合险标示
						prpLplan.setRemark("0");
						prpLplan.setProcessFlag("0");
						prpLplan.setInputDate(inputDate);
						prpLplan.setCoinsFlag(coinsFlag);
						prpLplan.setCoinsType(coinsType1);
						prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
						prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
						prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
						prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
						prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
						prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
						prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
						prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
						prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());
						if (prpLplan.getPlanFee() != 0) {
							prpLplanList.add(prpLplan);
							serialNoMap.put("PRPLCHARGE_"+"_"+prpLcharge.getId().getSerialNo(), serialNo);
						}
					}
				}
				// 赔付对象
				if (null != payObjectList) {
					for (PrpLpayObjectInfo prpLpayObjectInfo : payObjectList) {
						prpLplan = new PrpLplan();
						prpLplan.getId().setCertiType("C");
						prpLplan.getId().setCertiNo(prpLcompensate.getCompensateNo());
						prpLplan.getId().setSerialNo(++serialNo);
						// 如果费用类型为健保局，支付类型为PTJ
						if ("6".equals(prpLpayObjectInfo.getPaymentKind())) {
							prpLplan.getId().setPayRefReason("PTJ");
						} else if ("7".equals(prpLpayObjectInfo.getPaymentKind())) {
							// 如果费用类型为同业，支付类型为PTJ
							prpLplan.getId().setPayRefReason("PTT");
						} else {
							prpLplan.getId().setPayRefReason("P60");
						}
						prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
						prpLplan.setRegistno(prpLclaim.getRegistNo());
						prpLplan.setClaimNo(prpLcompensate.getClaimNo());
						prpLplan.setClassCode(prpCmain.getClassCode());
						prpLplan.setRiskCode(prpLcompensate.getRiskCode());
						prpLplan.setPlanFeeCurrency(prpLpayObjectInfo.getCurrency());
						prpLplan.setPayNo(1);
						prpLplan.setTotalPayNo(1);
						prpLplan.setPlanDate(inputDate);
						prpLplan.setPlanFee(prpLpayObjectInfo.getPayAmount());
						prpLplan.setAppliCode(prpCmain.getInsuredCode());
						prpLplan.setAppliName(prpCmain.getInsuredName());
						prpLplan.setInsuredCode(prpCmain.getInsuredCode());
						prpLplan.setInsuredName(prpCmain.getInsuredName());
						prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
						prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
						prpLplan.setAgentCode(prpCmain.getAgentCode());
						prpLplan.setComCode(prpCmain.getComCode());
						prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
						prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));

						prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
						prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
						prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
						prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
						prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
						prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
						prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
						prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
						prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());
						if (prpLplan.getBranchCode().equals("")) {
							prpLplan.setBranchCode(prpLplan.getCenterCode());
						}
						prpLplan.setMakeCom(prpCmain.getMakeCom());
						prpLplan.setBusinessNature(prpCmain.getBusinessNature());
						prpLplan.setHandler1Code(prpCmain.getHandler1Code());
						prpLplan.setHandlerCode(prpCmain.getHandlerCode());
						prpLplan.setExchangeRate(prpLpayObjectInfo.getExchRate());
						prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLpayObjectInfo.getExchRate());
						prpLplan.setPlanFeeCNY(DataUtils.round(prpLplan.getPlanFeeCNY(), 0));
						prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
						prpLplan.setOthFlag("0");
						if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
							prpLplan.setCarModel(prpCitemCarExtList.get(0).getCartypeCode());
						}
						prpLplan.setChannelType(prpCmain.getChannelType());
						prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
						prpLplan.setUnderWriteDate(inputDate);
						prpLplan.setIsCombin(isCombin);// 组合险标示
						prpLplan.setRemark("0");
						prpLplan.setProcessFlag("0");
						prpLplan.setInputDate(inputDate);
						prpLplan.setCoinsFlag(coinsFlag);
						prpLplan.setCoinsType(coinsType1);
						if ("C".equals(prpLpayObjectInfo.getOwnerShip())) {
							// 如果是现金支付，设置付款日期
							prpLplan.setRealDate(prpLpayObjectInfo.getPayDate());
						}
						if (prpLplan.getPlanFee() != 0) {
							prpLplanList.add(prpLplan);
							serialNoMap.put("PRPLPAYOBJECTINFO_"+"_"+ prpLpayObjectInfo.getId().getSerialNo(), serialNo);
							serialNoMap.put("PAYREFREASON_" + serialNo, prpLplan.getId().getPayRefReason());
						}

					}
				}
				
				int count = getPrpLplanService().getCount(" ClaimNo='" + prpLcompensate.getClaimNo() + "' and PayRefReason='Y10'");
				int count1 = getPrpLplanService().getCount(" ClaimNo='" + prpLcompensate.getClaimNo() + "' and PayRefReason='Y20'");
				if (count > 0 && count1 <= 0) {
					// 预付挂帳
					if (null != prpLplanYList && prpLplanYList.size() > 0) {
						prpLplanSumY.getId().setCertiNo(prpLcompensate.getCompensateNo());
						prpLplanSumY.getId().setCertiType("C");
						prpLplanSumY.getId().setSerialNo(++serialNo);
						prpLplanSumY.getId().setPayRefReason("Y20");
						prpLplanSumY.setIsCombin("0");
						prpLplanSumY.setProcessFlag("0");
						prpLplanSumY.setPlanFee(-sumPlanfee);
						prpLplanSumY.setPlanFeeCNY(-sumPlanfeeCNY);
						// 预赔输入日期
						prpLplanSumY.setInputDate(inputDate);
						if (sumPlanfee != 0) {
							prpLplanSumY.setExchangeRate(Str.round(sumPlanfeeCNY / sumPlanfee, 6));
						} else {
							prpLplanSumY.setExchangeRate(0D);
						}
						if (prpLplanSumY.getPlanFee() != 0) {
							prpLplanList.add(prpLplanSumY);
						}
					}
				}
//			}
			// 保存关联方标志位到prplplan的caseType字段中
			for(PrpLplan temp : prpLplanList){
				temp.setCaseType(prpCmain.getSubBusinessNature());
			}
			// 保存关联方标志位到prplplan的caseType字段中
			getPrpLplanService().save(prpLplanList);
			// 组合险标示isCombin=1组合险、2车险、0非组合险;
			// 组合险种处理
			// String riskCode = prpLcompensate.getRiskCode();
			List<PrpLplanKind> prpLPlanKindList = new ArrayList<PrpLplanKind>();
//			if (!"2".equals(isCombin)) {// 其他组合险种
//				if (!"F".equals(prpLcompensate.getClassCode())&&coinsFlag.equals("1")) {// 我方主联共保
//					List<PrpCcoins> prpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLcompensate.getPolicyNo() + "'");
//					if (null != prpCcoinsList) {
//						for (int i = 0; i < prpCcoinsList.size(); i++) {
//							prpCcoins = prpCcoinsList.get(i);
//							coinsRate = prpCcoins.getCoinsRate() / 100;
//							serialNoMap.put("PRPCCOINS_SERIALNO", prpCcoins.getId().getSerialNo());
//							prpLPlanKindList.addAll(getPrpLplanKindService().findPayLossByConditions(prpLcompensate.getCompensateNo(), codeMap, prpCcoins.getCoinsType(), coinsFlag, coinsRate, sumPaid, serialNoMap, damageDate, isCombin));
//						}
//					}
//				} else {
//					double coinsRate = 1;
//					if (coinsFlag.equals("2") || coinsFlag.equals("4")) {
//						List<PrpCcoins> prpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLcompensate.getPolicyNo() + "' and coinsType='1' ");
//						if (null != prpCcoinsList && prpCcoinsList.size() > 0) {
//							prpCcoins = prpCcoinsList.get(0);
//							coinsRate = prpCcoins.getCoinsRate() / 100;
//						}
//					}
					prpLPlanKindList = getPrpLplanKindService().findPayLossByConditions(prpLcompensate.getCompensateNo(), codeMap, "2", coinsFlag, coinsRate, sumPaid, serialNoMap, damageDate, isCombin);
//				}
//			} else if (isCombin.equals("2")) {
//				prpLPlanKindList = getPrpLplanKindService().findPayLossByConditions(prpLcompensate.getCompensateNo(), codeMap, "1", coinsFlag, 1, sumPaid, serialNoMap, damageDate, isCombin);
//			}
			int planL = prpLplanList.size();
			int kindL = prpLPlanKindList.size();
			if (kindL > 0 && planL > 0) {
				// 尾差处理,因为费用类型的serialNo和赔款的serialNo都是从1开始增加的，费用和赔款有相同的值。金额算在一起了。添加赔付原因做区分
				String payRefReason = null;
				String kindPayRefReason = null;
				for (int x = 0; x < planL; x++) {
					int serialNoP = prpLplanList.get(x).getId().getSerialNo();
					double planFee = prpLplanList.get(x).getPlanFee();
					double planFeeCNY = prpLplanList.get(x).getPlanFeeCNY();
					payRefReason = prpLplanList.get(x).getId().getPayRefReason();
					double sumKindFee = 0;
					double sumkindFeeCNY = 0;
					int endSerialNo = -1;
					double endKindFee = 0;
					double endKindFeeCNY = 0;
					for (int y = 0; y < kindL; y++) {
						int serialNoK = prpLPlanKindList.get(y).getId().getSerialNo();
						double kindFee = prpLPlanKindList.get(y).getKindFee();
						double kindFeeCNY = prpLPlanKindList.get(y).getPlanFeeCNY();
						kindPayRefReason = prpLPlanKindList.get(y).getId().getPayRefReason();
						if (serialNoP == serialNoK && payRefReason.equals(kindPayRefReason)) {
							sumKindFee += kindFee;
							sumkindFeeCNY += kindFeeCNY;
							endSerialNo = y;
							endKindFee = kindFee;
							endKindFeeCNY = kindFeeCNY;
						}
					}
					if (endSerialNo != -1) {
						if (planFee - sumKindFee != 0) {
							((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).setKindFee(endKindFee + planFee - sumKindFee);
						}
						if (planFeeCNY - sumkindFeeCNY != 0) {
							((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).setPlanFeeCNY(endKindFeeCNY + planFeeCNY - sumkindFeeCNY);
						}
					}
				}
				getPrpLplanKindService().save(prpLPlanKindList);
			}
		}

	}

	public void transCancel(String claimNo) throws UserException, SQLException, Exception {
		DateTime inputDate = DateTime.current();
		Collection<?> prpLplanList = getPrpLplanService().findByConditions(" claimNo = '" + claimNo + "'");
		if (null != prpLplanList) {
			Iterator<?> it = prpLplanList.iterator();
			while (it.hasNext()) {
				PrpLplan prpLplan = (PrpLplan)it.next();
				prpLplan.setPlanDate(inputDate);
				prpLplan.setInputDate(inputDate);
				prpLplan.setPlanFee(-prpLplan.getPlanFee());
				prpLplan.setPlanFeeCNY(-prpLplan.getPlanFeeCNY());
				prpLplan.setOthFlag("1");
				getPrpLplanService().save(prpLplan);
				String conditions = " certiNo ='" + prpLplan.getId().getCertiNo() + "' and certiType ='" + prpLplan.getId().getCertiType() + "' and SerialNo = '" + prpLplan.getId().getSerialNo() + "' and PayRefReason ='"
						+ prpLplan.getId().getPayRefReason() + "'";
				Collection<?> prpLPlanKindList = getPrpLplanKindService().findByConditions(conditions);
				if (null != prpLPlanKindList) {
					ArrayList<PrpLplanKind> prpLPlanKindListTemp = new ArrayList<PrpLplanKind>();
					Iterator<?> itK = prpLPlanKindList.iterator();
					while (itK.hasNext()) {
						PrpLplanKind prpLPlanKind = (PrpLplanKind) itK.next();
						prpLPlanKind.setInputDate(inputDate);
						prpLPlanKind.setKindFee(-prpLPlanKind.getKindFee());
						prpLPlanKind.setPlanFeeCNY(-prpLPlanKind.getPlanFeeCNY());
						prpLPlanKind.setFlag("1");
						prpLPlanKindListTemp.add(prpLPlanKind);
					}
					getPrpLplanKindService().save(prpLPlanKindListTemp);
				}
			}
		}
	}

	public void transReplevy(String compensateNo, Map<?, ?> infoMap) throws UserException, Exception {
		PrpLcompensate prpLcompensate = getPrpLcompensateService().findPrpLcompensate(compensateNo);
		PrpLclaim prpLclaim = getPrpLclaimService().findPrpLclaim(prpLcompensate.getClaimNo());
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString(DateTime.YEAR_TO_DAY);

		if (null != prpLcompensate) {
			Map<String, String> codeMap = getPrpLFMappingService().findMapByConditions(" 1=1 ");
			Map<String, Object> serialNoMap = new HashMap<String, Object>();
			int serialNo = 0;
			double sumPaid = prpLcompensate.getSumDutyPaid();
//			List<PrpLFMapping> prpLFMappingList = getPrpLFMappingService().findByConditions(" 1=1 ");
//			if (null != prpLFMappingList) {
//				for (int i = 0; i < prpLFMappingList.size(); i++) {
//					PrpLFMapping prpLFMapping = prpLFMappingList.get(i);
//					codeMap.put(prpLFMapping.getId().getChargeCode() + prpLFMapping.getId().getPayRefReason().substring(0, 1), prpLFMapping.getId().getPayRefReason());
//				}
//			}
			List<PrpLplan> prpLplanList = new ArrayList<PrpLplan>();
			List<PrpLcharge> chargeList = getPrpLchargeService().findPrpLchargeList(compensateNo);
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.compensateNo", compensateNo);
			List<PrpLpayObjectInfo> resultList = getPrpLpayObjectInfoService().findPrpLpayObjectInfo(queryRule);
			Map<String,PrpLpayObjectInfo> chargeMap = new HashMap<String,PrpLpayObjectInfo>();
			List<PrpLpayObjectInfo> payObjectList = new ArrayList<PrpLpayObjectInfo>();
			if (resultList!=null && !resultList.isEmpty()) {
				for (PrpLpayObjectInfo p : resultList) {
					if(PrpLpayObjectInfo.CERTITYPE_CHARGE.equals(p.getId().getCertiType())){//费用的赔付对象
						chargeMap.put(p.getId().getCompensateNo()+"_"+p.getId().getCertiType()+"_"+p.getId().getSerialNo(), p);
					}else if(PrpLpayObjectInfo.CERTITYPE_PAYOBJECT.equals(p.getId().getCertiType())){//追偿收取的对象
						payObjectList.add(p);
					}
				}
			}
			PrpCmain prpCmain = getPrpCmainService().findByPrimaryKey(prpLcompensate.getPolicyNo());
			List<PrpCitemCarExt> prpCitemCarExtList = getPrpCitemCarExtService().findByPolicyNo(prpLcompensate.getPolicyNo());
			String coinsFlag = prpCmain.getCoinsFlag();
			String isCombin = getIsCombin(prpCmain.getRiskCode());
			DateTime inputDate = DateTime.current();
			PrpCcoins prpCcoins = null;
			PrpLplan prpLplan = null;
//			PrpLplan prpLplans = null;
			PrpLcharge prpLcharge = null;
//			List<PrpLplanKind> prpLplanKindList = null;
//			PrpLplanId prpLplanTempId = null;
			double coinsRate = 1;
			String coinsType1 = "";
			if("0".equals(coinsFlag)){
				List<PrpCcoins> prpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLcompensate.getPolicyNo() + "' and coinsCode='"+ConstantCodes.COMPANYCODE+"' ");
				if (null != prpCcoinsList && prpCcoinsList.size() > 0) {
					prpCcoins = prpCcoinsList.get(0);
					coinsRate = prpCcoins.getCoinsRate() / 100;
					coinsType1 = prpCcoins.getChiefFlag();
				}
			}
			serialNoMap.put("coinsRate_0", coinsRate);
//			if (!"F".equals(prpLcompensate.getClassCode())&&coinsFlag.equals("1")) {// 我方主联共保
//				// "******************我方主联共保****************" + compensateNo);
//				List<PrpCcoins> prpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLcompensate.getPolicyNo() + "'");
//				if (null != prpCcoinsList) {
//					for (int i = 0; i < prpCcoinsList.size(); i++) {
//						prpCcoins = prpCcoinsList.get(i);
//						String coinsType = prpCcoins.getCoinsType();
//						// double sjFee = 0;
//						if ("0".equals(prpLcompensate.getIsPayForOther()) && !"2".equals(coinsType)) {
//							continue;
//						}
//						// 费用记录
//						if (null != chargeList) {
//							PrpLpayObjectInfo prpLpayObjectInfo = null;
//							for (int j = 0; j < chargeList.size(); j++) {
//								prpLcharge = chargeList.get(j);
//								prpLpayObjectInfo = chargeMap.get(prpLcharge.getId().getCompensateNo()+"_"+PrpLpayObjectInfo.CERTITYPE_CHARGE+"_"+prpLcharge.getId().getSerialNo());
//								prpLplan = new PrpLplan();
//								prpLplan.getId().setCertiType("C");
//								prpLplan.getId().setCertiNo(prpLcompensate.getCompensateNo());
//								prpLplan.getId().setSerialNo(++serialNo);
//								if (coinsType.equals("2")) {
//									prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "Z") + "");
//								} else {
//									prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "Z") + "");
//								}
//								prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
//								prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
//								prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
//								prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
//								prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
//								prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
//								prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
//								prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
//								prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());
//
//								prpLplan.setChargeCode(prpLcharge.getChargeCode());
//								prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
//								prpLplan.setRegistno(prpLclaim.getRegistNo());
//								prpLplan.setClaimNo(prpLcompensate.getClaimNo());
//								prpLplan.setClassCode(prpCmain.getClassCode());
//								prpLplan.setRiskCode(prpLcompensate.getRiskCode());
//								prpLplan.setPlanFeeCurrency(prpLcharge.getCurrency());
//								prpLplan.setPayNo(1);
//								prpLplan.setTotalPayNo(1);
//								prpLplan.setPlanDate(inputDate);
//								prpLplan.setPlanFee(prpLcharge.getChargeAmount()/coinsRate * prpCcoins.getCoinsRate() / 100);
//								prpLplan.setAppliCode(prpCmain.getInsuredCode());
//								prpLplan.setAppliName(prpCmain.getInsuredName());
//								prpLplan.setInsuredCode(prpCmain.getInsuredCode());
//								prpLplan.setInsuredName(prpCmain.getInsuredName());
//								prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
//								prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
//								prpLplan.setAgentCode(prpCmain.getAgentCode());
//								prpLplan.setCoinsCode(prpCcoins.getCoinsCode());
//								prpLplan.setCoinsName(prpCcoins.getCoinsName());
//								prpLplan.setComCode(prpCmain.getComCode());
//								prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
//								prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
//								if (prpLplan.getBranchCode().equals("")) {
//									prpLplan.setBranchCode(prpLplan.getCenterCode());
//								}
//								prpLplan.setMakeCom(prpCmain.getMakeCom());
//								prpLplan.setBusinessNature(prpCmain.getBusinessNature());
//								prpLplan.setHandler1Code(prpCmain.getHandler1Code());
//								prpLplan.setHandlerCode(prpCmain.getHandlerCode());
//								prpLplan.setExchangeRate(prpLcharge.getExchRate());
//								prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLplan.getExchangeRate());
//								prpLplan.setPlanFeeCNY(DataUtils.round(prpLplan.getPlanFeeCNY(), 0));
//								prpLplan.setUnderWriteDate(inputDate);
//								if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
//									prpLplan.setCarModel(prpCitemCarExtList.get(0).getCartypeCode());
//								}
//								prpLplan.setChannelType(prpCmain.getChannelType());
//								prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
//								prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
//								prpLplan.setIsCombin(isCombin);// 组合险标示
//								prpLplan.setOthFlag("0");
//								prpLplan.setRemark("0");
//								prpLplan.setProcessFlag("0");
//								prpLplan.setInputDate(inputDate);
//								prpLplan.setCoinsFlag(coinsFlag);
//								prpLplan.setCoinsType(coinsType);
//								if (prpLplan.getPlanFee() != 0) {
//									prpLplanList.add(prpLplan);
//									serialNoMap.put("PRPLCHARGE_"+prpCcoins.getId().getSerialNo()+"_"+prpLcharge.getId().getSerialNo(), serialNo);
////									serialNoMap.put(prpLplan.getId().getPayRefReason() + prpLplan.getChargeCode(), new Integer(serialNo));
//								}
//								if (!coinsType.equals("2")) {// 他方从联共冲帳
//									prpLplans = new PrpLplan();
//									prpLplanTempId = prpLplans.getId();
//									prpLplanKindList = prpLplan.getPrpLPlanKinds();
//									prpLplan.setPrpLPlanKinds(null);
//									BeanUtils.copyProperties(prpLplan, prpLplans);
//									//属性的复制是浅拷贝，里面的对象只是复制了一个引用，需要在从新拷贝一边Id，修改新拷贝对象的引用
//									BeanUtils.copyProperties(prpLplan.getId(), prpLplanTempId);
//									prpLplans.setId(prpLplanTempId);
//									prpLplan.setPrpLPlanKinds(prpLplanKindList);
//									prpLplans.getId().setCertiType("C");
//									if (coinsFlag.equals("1")) {
//										prpLplans.getId().setPayRefReason("M" + prpLplans.getId().getPayRefReason().substring(1));
//									} else {
//										prpLplans.getId().setPayRefReason("N" + prpLplans.getId().getPayRefReason().substring(1));
//									}
//									prpLplans.getId().setSerialNo(++serialNo);
//									prpLplans.setPlanFee(-prpLplan.getPlanFee());
//									prpLplans.setPlanFeeCNY(-prpLplan.getPlanFeeCNY());
//									if (prpLplans.getPlanFee() != 0) {
//										prpLplanList.add(prpLplans);
//										serialNoMap.put("PRPCCOINS_" + +prpCcoins.getId().getSerialNo()+"_"+ prpLplan.getId().getSerialNo(), serialNo);
//										serialNoMap.put("PAYREFREASON_" + serialNo, prpLplans.getId().getPayRefReason());
//									}
//								}
//							}
//						}
//						// 赔付对象
//						for (PrpLpayObjectInfo prpLpayObjectInfo : payObjectList) {
//							prpLplan = new PrpLplan();
//							prpLplan.getId().setCertiType("C");
//							prpLplan.getId().setCertiNo(prpLpayObjectInfo.getId().getCompensateNo());
//							prpLplan.getId().setSerialNo(++serialNo);
//							if (coinsType.equals("2")) {// 我方
//								prpLplan.getId().setPayRefReason("Z60");
//							} else {// 他方从联共
//								prpLplan.getId().setPayRefReason("S60");
////								if (coinsFlag.equals("1")) {// 共保
////								} else if (coinsFlag.equals("3")) {// 联保
////									prpLplan.getId().setPayRefReason("F60");
////								}
//							}
//							prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
//							prpLplan.setRegistno(prpLclaim.getRegistNo());
//							prpLplan.setClaimNo(prpLcompensate.getClaimNo());
//							prpLplan.setClassCode(prpCmain.getClassCode());
//							prpLplan.setRiskCode(prpLcompensate.getRiskCode());
//							prpLplan.setPlanFeeCurrency(prpLpayObjectInfo.getCurrency());
//							prpLplan.setPayNo(1);
//							prpLplan.setTotalPayNo(1);
//							prpLplan.setPlanDate(inputDate);
//							prpLplan.setPlanFee((-1)*prpLpayObjectInfo.getPayAmount() * prpCcoins.getCoinsRate() / 100);
//							prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
//							prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
//							prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
//							prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
//							prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
//							prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
//							prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
//							prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
//							prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());
//							prpLplan.setAppliCode(prpCmain.getInsuredCode());
//							prpLplan.setAppliName(prpCmain.getInsuredName());
//							prpLplan.setInsuredCode(prpCmain.getInsuredCode());
//							prpLplan.setInsuredName(prpCmain.getInsuredName());
//							prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
//							prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
//							prpLplan.setAgentCode(prpCmain.getAgentCode());
//							prpLplan.setCoinsCode(prpCcoins.getCoinsCode());
//							prpLplan.setCoinsName(prpCcoins.getCoinsName());
//							prpLplan.setComCode(prpCmain.getComCode());
//							prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
//							prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
//							if (prpLplan.getBranchCode().equals("")) {
//								prpLplan.setBranchCode(prpLplan.getCenterCode());
//							}
//							prpLplan.setMakeCom(prpCmain.getMakeCom());
//							prpLplan.setBusinessNature(prpCmain.getBusinessNature());
//							prpLplan.setHandler1Code(prpCmain.getHandler1Code());
//							prpLplan.setHandlerCode(prpCmain.getHandlerCode());
//							prpLplan.setExchangeRate(prpLpayObjectInfo.getExchRate());
//							prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLplan.getExchangeRate());
//							prpLplan.setPlanFeeCNY(DataUtils.round(prpLplan.getPlanFeeCNY(), 0));
//							prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
//							prpLplan.setIsCombin(isCombin);// 组合险标示
//							prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
//							prpLplan.setOthFlag("0");
//							prpLplan.setRemark("0");
//							prpLplan.setProcessFlag("0");
//							prpLplan.setCoinsFlag(coinsFlag);
//							prpLplan.setCoinsType(coinsType);
//							prpLplan.setInputDate(inputDate);
//							if ("C".equals(prpLpayObjectInfo.getOwnerShip())) {
//								// 如果是现金支付，设置付款日期
//								prpLplan.setRealDate(prpLpayObjectInfo.getPayDate());
//							}
//							if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
//								prpLplan.setCarModel(prpCitemCarExtList.get(0).getCartypeCode());
//							}
//							prpLplan.setChannelType(prpCmain.getChannelType());
//							prpLplan.setUnderWriteDate(inputDate);
//							if (prpLplan.getPlanFee() != 0) {
//								prpLplanList.add(prpLplan);
//								serialNoMap.put("PRPLPAYOBJECTINFO_"+prpCcoins.getId().getSerialNo()+"_"+ prpLpayObjectInfo.getId().getSerialNo(), serialNo);
//								serialNoMap.put("PAYREFREASON_" + serialNo, prpLplan.getId().getPayRefReason());
////									serialNoMap.put(prpLplan.getId().getPayRefReason() + prpLpayObjectInfo.getId().getSerialNo(), new Integer(prpLpayObjectInfo.getId().getSerialNo()));
////									serialNoMap.put("PRPLPAYOBJECTINFO_" + prpLplan.getId().getSerialNo(), prpLplan.getId().getPayRefReason());
//							}
//							if (!coinsType.equals("2")) {// 他方从联共冲帳
//								prpLplans = new PrpLplan();
//								prpLplanTempId = prpLplans.getId();
//								prpLplanKindList = prpLplan.getPrpLPlanKinds();
//								prpLplan.setPrpLPlanKinds(null);
//								BeanUtils.copyProperties(prpLplan, prpLplans);
//								//属性的复制是浅拷贝，里面的对象只是复制了一个引用，需要在从新拷贝一边Id，修改新拷贝对象的引用
//								BeanUtils.copyProperties(prpLplan.getId(), prpLplanTempId);
//								prpLplans.setId(prpLplanTempId);
//								prpLplan.setPrpLPlanKinds(prpLplanKindList);
//								prpLplans.getId().setCertiType("C");
//								if (coinsFlag.equals("1")) {
//									prpLplans.getId().setPayRefReason("M60");
//								} else {
//									prpLplans.getId().setPayRefReason("N60");
//								}
//								prpLplans.getId().setSerialNo(++serialNo);
//								prpLplans.setPlanFee(-prpLplan.getPlanFee());
//								prpLplans.setPlanFeeCNY(-prpLplan.getPlanFeeCNY());
//								if (prpLplans.getPlanFee() != 0) {
//									prpLplanList.add(prpLplans);
//									serialNoMap.put("PRPCCOINS_" +prpCcoins.getId().getSerialNo()+"_"+ prpLplan.getId().getSerialNo(), serialNo);
//									serialNoMap.put("PAYREFREASON_" + serialNo, prpLplans.getId().getPayRefReason());
//								}
//							}
//						}
//					}
//				}
//			} else {// 独家承保和我方从联共保
				// "********************独家承保和我方从联共保******************" +
				// 费用记录
				if (null != chargeList) {
					PrpLpayObjectInfo prpLpayObjectInfo = null;
					for (int j = 0; j < chargeList.size(); j++) {
						prpLcharge =  chargeList.get(j);
						prpLpayObjectInfo = chargeMap.get(prpLcharge.getId().getCompensateNo()+"_"+PrpLpayObjectInfo.CERTITYPE_CHARGE+"_"+prpLcharge.getId().getSerialNo());
						prpLplan = new PrpLplan();
						prpLplan.getId().setCertiType("C");
						prpLplan.getId().setCertiNo(prpLcompensate.getCompensateNo());
						prpLplan.getId().setSerialNo(++serialNo);
						if (coinsFlag.equals("2")) {// 共保
							prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "Z") + "");
						} else if (coinsFlag.equals("3")) {// 联保
							prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "Z") + "");
						} else {
							prpLplan.getId().setPayRefReason(codeMap.get(prpLcharge.getChargeCode() + "Z") + "");
						}
						prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
						prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
						prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
						prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
						prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
						prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
						prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
						prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
						prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());

						prpLplan.setChargeCode(prpLcharge.getChargeCode());
						prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
						prpLplan.setRegistno(prpLclaim.getRegistNo());
						prpLplan.setClaimNo(prpLcompensate.getClaimNo());
						prpLplan.setClassCode(prpCmain.getClassCode());
						prpLplan.setRiskCode(prpLcompensate.getRiskCode());
						prpLplan.setPlanFeeCurrency(prpLcharge.getCurrency());
						prpLplan.setPayNo(1);
						prpLplan.setTotalPayNo(1);
						prpLplan.setPlanDate(inputDate);
						prpLplan.setPlanFee(prpLcharge.getChargeAmount());
						prpLplan.setAppliCode(prpCmain.getInsuredCode());
						prpLplan.setAppliName(prpCmain.getInsuredName());
						prpLplan.setInsuredCode(prpCmain.getInsuredCode());
						prpLplan.setInsuredName(prpCmain.getInsuredName());
						prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
						prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
						prpLplan.setAgentCode(prpCmain.getAgentCode());
						prpLplan.setComCode(prpCmain.getComCode());
						prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
						prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
						if (prpLplan.getBranchCode().equals("")) {
							prpLplan.setBranchCode(prpLplan.getCenterCode());
						}
						prpLplan.setMakeCom(prpCmain.getMakeCom());
						prpLplan.setBusinessNature(prpCmain.getBusinessNature());
						prpLplan.setHandler1Code(prpCmain.getHandler1Code());
						prpLplan.setHandlerCode(prpCmain.getHandlerCode());
						prpLplan.setExchangeRate(prpLcharge.getExchRate());
						prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLplan.getExchangeRate());
						prpLplan.setPlanFeeCNY(DataUtils.round(prpLplan.getPlanFeeCNY(), 0));
						prpLplan.setUnderWriteDate(inputDate);
						prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
						prpLplan.setOthFlag("0");
						if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
							prpLplan.setCarModel(((PrpCitemCarExt) prpCitemCarExtList.get(0)).getCartypeCode());
						}
						prpLplan.setChannelType(prpCmain.getChannelType());
						prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
						prpLplan.setIsCombin(isCombin);// 组合险标示
						prpLplan.setRemark("0");
						prpLplan.setProcessFlag("0");
						prpLplan.setInputDate(inputDate);
						prpLplan.setCoinsFlag(coinsFlag);
						// coinsType add by caozhigang 2009-05-13 start
						prpLplan.setCoinsType(coinsType1);
						// coinsType add by caozhigang 2009-05-13 end
						if (prpLplan.getPlanFee() != 0) {
							prpLplanList.add(prpLplan);
							serialNoMap.put("PRPLCHARGE_"+"_"+prpLcharge.getId().getSerialNo(), serialNo);
						}
					}
				}
				// 赔付对象
				for (PrpLpayObjectInfo prpLpayObjectInfo : payObjectList) {
					prpLplan = new PrpLplan();
					prpLplan.getId().setCertiType("C");
					prpLplan.getId().setCertiNo(prpLcompensate.getCompensateNo());
					prpLplan.getId().setSerialNo(++serialNo);
					prpLplan.getId().setPayRefReason("Z60");
					prpLplan.setPolicyNo(prpLcompensate.getPolicyNo());
					prpLplan.setRegistno(prpLclaim.getRegistNo());
					prpLplan.setClaimNo(prpLcompensate.getClaimNo());
					prpLplan.setClassCode(prpCmain.getClassCode());
					prpLplan.setRiskCode(prpLcompensate.getRiskCode());
					prpLplan.setPlanFeeCurrency(prpLpayObjectInfo.getCurrency());

					prpLplan.setAccountCode(prpLpayObjectInfo.getAccountCode());
					prpLplan.setCustomBankCode(prpLpayObjectInfo.getBankCode());
					prpLplan.setCustomBankName(prpLpayObjectInfo.getCustomBankName());
					prpLplan.setCertificateCode(prpLpayObjectInfo.getCertificateCode());
					prpLplan.setOwnerName(prpLpayObjectInfo.getOwnerName());
					prpLplan.setOwnerPhoneNo(prpLpayObjectInfo.getOwnerPhoneNo());
					prpLplan.setAccountType(prpLpayObjectInfo.getAccountType());
					prpLplan.setAccountCurrency(prpLpayObjectInfo.getAccountCurrency());
					prpLplan.setOwnerShip(prpLpayObjectInfo.getOwnerShip());

					prpLplan.setPayNo(1);
					prpLplan.setTotalPayNo(1);
					prpLplan.setPlanDate(inputDate);
					prpLplan.setPlanFee((-1)*prpLpayObjectInfo.getPayAmount());
					prpLplan.setAppliCode(prpCmain.getInsuredCode());
					prpLplan.setAppliName(prpCmain.getInsuredName());
					prpLplan.setInsuredCode(prpCmain.getInsuredCode());
					prpLplan.setInsuredName(prpCmain.getInsuredName());
					prpLplan.setStartDate(new DateTime(prpCmain.getStartDate()));
					prpLplan.setEndDate(new DateTime(prpCmain.getEndDate()));
					prpLplan.setAgentCode(prpCmain.getAgentCode());
					prpLplan.setComCode(prpCmain.getComCode());
					prpLplan.setBranchCode(getBranchCode(prpCmain.getComCode()));
					prpLplan.setCenterCode(getCenterCode((String) infoMap.get("comCode")));
					if (prpLplan.getBranchCode().equals("")) {
						prpLplan.setBranchCode(prpLplan.getCenterCode());
					}
					prpLplan.setMakeCom(prpCmain.getMakeCom());
					prpLplan.setBusinessNature(prpCmain.getBusinessNature());
					prpLplan.setHandler1Code(prpCmain.getHandler1Code());
					prpLplan.setHandlerCode(prpCmain.getHandlerCode());
					prpLplan.setExchangeRate(prpLpayObjectInfo.getExchRate());
					prpLplan.setPlanFeeCNY(prpLplan.getPlanFee() * prpLplan.getExchangeRate());
					prpLplan.setPlanFeeCNY(DataUtils.round(prpLplan.getPlanFeeCNY(), 0));
					prpLplan.setLocationFlag(getLocationFlag(prpCmain.getNationFlag()));
					prpLplan.setOthFlag("0");
					if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
						prpLplan.setCarModel(((PrpCitemCarExt) prpCitemCarExtList.get(0)).getCartypeCode());
					}
					prpLplan.setChannelType(prpCmain.getChannelType());
					prpLplan.setAgriType(prpCmain.getAgriType());// 涉农
					prpLplan.setUnderWriteDate(inputDate);
					prpLplan.setIsCombin(isCombin);// 组合险标示
					prpLplan.setRemark("0");
					prpLplan.setProcessFlag("0");
					prpLplan.setInputDate(inputDate);
					prpLplan.setCoinsFlag(coinsFlag);
					prpLplan.setCoinsType(coinsType1);
					if (prpLplan.getPlanFee() != 0) {
						prpLplanList.add(prpLplan);
						serialNoMap.put("PRPLPAYOBJECTINFO_"+"_"+ prpLpayObjectInfo.getId().getSerialNo(), serialNo);
						serialNoMap.put("PAYREFREASON_" + serialNo, prpLplan.getId().getPayRefReason());
//							serialNoMap.put(prpLplan.getId().getPayRefReason() + prpLpayObjectInfo.getId().getSerialNo(), new Integer(prpLpayObjectInfo.getId().getSerialNo()));
//							serialNoMap.put("PRPLPAYOBJECTINFO_" + prpLplan.getId().getSerialNo(), prpLplan.getId().getPayRefReason());
					}
				}
//			}
			// 保存关联方标志位到prplplan的caseType字段中
			Iterator<PrpLplan> iterator = prpLplanList.iterator();
			while (iterator.hasNext()) {
				PrpLplan prplplan = (PrpLplan) iterator.next();
				prplplan.setCaseType(prpCmain.getSubBusinessNature());
			}
			// 保存关联方标志位到prplplan的caseType字段中//PAYREFREASON
			getPrpLplanService().save(prpLplanList);
			// ********************组合险标示isCombin=1组合险、2车险、0非组合险======";
			// 组合险种处理
			List<PrpLplanKind> prpLPlanKindList = new ArrayList<PrpLplanKind>();
//			if (!"2".equals(isCombin)) {// 其他组合险种
//				if (!"F".equals(prpLcompensate.getClassCode())&&coinsFlag.equals("1")) {// 我方主联共保
//					List<PrpCcoins> prpCcoinsList = getPrpCcoinsService().findByConditions(" policyNO='" + prpLcompensate.getPolicyNo() + "' and coinsType='2' ");
//					if (null != prpCcoinsList) {
//						for (int i = 0; i < prpCcoinsList.size(); i++) {
//							prpCcoins = (PrpCcoins) prpCcoinsList.get(i);
//							coinsRate = prpCcoins.getCoinsRate() / 100;
//							serialNoMap.put("PRPCCOINS_SERIALNO", prpCcoins.getId().getSerialNo());
//							prpLPlanKindList.addAll(getPrpLplanKindService().findReplevyLossByConditions(prpLcompensate.getCompensateNo(), codeMap, prpCcoins.getCoinsType(), coinsFlag, coinsRate, sumPaid, serialNoMap, damageDate, isCombin));
//						}
//					}
//				} else {
					prpLPlanKindList = getPrpLplanKindService().findReplevyLossByConditions(prpLcompensate.getCompensateNo(), codeMap, "2", coinsFlag, coinsRate, sumPaid, serialNoMap, damageDate, isCombin);
//				}
//			} else if (isCombin.equals("2")) {
//				// "********************车险riskCode=======" + riskCode);
//				prpLPlanKindList = getPrpLplanKindService().findReplevyLossByConditions(prpLcompensate.getCompensateNo(), codeMap, "1", coinsFlag, 1, sumPaid, serialNoMap, damageDate, isCombin);
//			}
			int planL = prpLplanList.size();
			int kindL = prpLPlanKindList.size();
			if (kindL > 0 && planL > 0) {
				// 尾差处理,因为费用类型的serialNo和赔款的serialNo都是从1开始增加的，费用和赔款有相同的值。金额算在一起了。添加赔付原因做区分
				String planPayRefReason = null;
				String kindPayRefReason = null;
				for (int x = 0; x < planL; x++) {
					int serialNoP = ((PrpLplan) prpLplanList.get(x)).getId().getSerialNo();
					planPayRefReason = ((PrpLplan) prpLplanList.get(x)).getId().getPayRefReason();
					double planFee = ((PrpLplan) prpLplanList.get(x)).getPlanFee();
					double planFeeCNY = ((PrpLplan) prpLplanList.get(x)).getPlanFeeCNY();
					double sumKindFee = 0;
					double sumkindFeeCNY = 0;
					int endSerialNo = -1;
					double endKindFee = 0;
					double endKindFeeCNY = 0d;
					for (int y = 0; y < kindL; y++) {
						int serialNoK = ((PrpLplanKind) prpLPlanKindList.get(y)).getId().getSerialNo();
						double kindFee = ((PrpLplanKind) prpLPlanKindList.get(y)).getKindFee();
						double kindFeeCNY = ((PrpLplanKind) prpLPlanKindList.get(y)).getPlanFeeCNY();
						kindPayRefReason = ((PrpLplanKind) prpLPlanKindList.get(y)).getId().getPayRefReason();
						if (serialNoP == serialNoK && planPayRefReason.equals(kindPayRefReason)) {
							sumKindFee += kindFee;
							sumkindFeeCNY += kindFeeCNY;
							endSerialNo = y;
							endKindFee = kindFee;
							endKindFeeCNY = kindFeeCNY;
						}
					}
					if (endSerialNo != -1) {
						if (planFee - sumKindFee != 0) {
							((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).setKindFee(endKindFee + planFee - sumKindFee);
						}
						if (planFeeCNY - sumkindFeeCNY != 0) {
							((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).setPlanFeeCNY(endKindFeeCNY + planFeeCNY - sumkindFeeCNY);
						}
						if (((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).getKindCode().equals("M") && ((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).getKindFee() == 0) {
							throw new UserException(-98, -1149, "業務號：" + ((PrpLplanKind) prpLPlanKindList.get(endSerialNo)).getId().getCertiNo(), "理算赔款金额中的不计免赔金额有误，请重新理算计算！");
						}
					}
				}
				getPrpLplanKindService().save(prpLPlanKindList);
			}
		}

	}

	/**
	 * 转换境内境外标示
	 * @param iNationFlag
	 * @return
	 * @throws Exception
	 */
	public String getLocationFlag(String iNationFlag) throws Exception {
		String strLocationFlag = "1";
		if ("0".equals(iNationFlag)) { // 国外
			strLocationFlag = "2";
		}
		return strLocationFlag;
	}

	/**
	 * 获取组合险标示
	 * @param dbManager
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public String getIsCombin(String riskCode) throws Exception {
		PrpDrisk prpDrisk = getPrpDriskService().findPrpDrisk(riskCode);
		if ("D".equals(ConstantCodes.carClassMap.get(prpDrisk.getClassCode()))) {// 车险判定
			return "2";
		}
		if (!CommonUtils.isEmpty(prpDrisk.getFlag()) && prpDrisk.getFlag().length() > 1 && "2".equals(prpDrisk.getFlag().substring(1, 2))) {
			return "1";
		}
		return "0";
	}

	/**
	 * @param iComCode 部门代码
	 * @throw Exception
	 * @desc 根据输入的单位代码，查询该单位所属的核算单位代码
	 */
	public String getCenterCode(String iComCode) throws Exception {
		String centerCode = "";
		if (CommonUtils.isEmpty(iComCode)) {
			return ""; // 代码空直接返回空字符串
		}
		try {
			PrpDcompany company = getPrpDcompanyService().findByPrimaryKey(iComCode);
			if (company == null) {
				throw new UserException(0, -1, "核算單位", "找不到該出單機構的核算單位");
			}
			if (CommonUtils.isEmpty(company.getAcntUnit())) {
				throw new UserException(0, -1, "核算單位", "該出單機構不歸屬於任何核算單位");
			} else {
				centerCode = company.getAcntUnit();
			}
		} catch (Exception e) {
			throw e;
		}
		return centerCode;
	}

	/**
	 * 获取机构代码所属的基层单位代码
	 * @author 中科软
	 * @param dbpool DbPool 连接池
	 * @param iComCode String 机构代码
	 * @throws Exception
	 * @return String 基层单位
	 */
	public String getBranchCode(String iComCode) throws Exception {
		String strBranchCode = "";
		String strComCode = iComCode;
		try {
			while (!(strComCode.equals("") || strComCode == null)) {
				PrpDcompany comp = getPrpDcompanyService().findByPrimaryKey(strComCode);
				if (comp.getCenterFlag() != null && (comp.getCenterFlag().equals("1") || comp.getCenterFlag().equals("2"))) {
					if (comp.getCenterFlag().equals("2")) {
						strBranchCode = strComCode;
					}
					break;
				}
				strComCode = comp.getPrpDcompany().getComCode();
			}
		} catch (Exception e) {
			throw e;
		}
		return strBranchCode;
	}

	private boolean transClaim(String iCertiType, String iCertiNo) throws UserException, Exception {
		boolean blnReturn = false;
		int intStatus = 0;
		getPaymentWebService().transClaimAll(iCertiType, iCertiNo);
		if (intStatus > 0) {
			blnReturn = true;
		}
		return blnReturn;
	}

	public PrpLprepayService getPrpLprepayService() {
		if (prpLprepayService == null) {
			prpLprepayService = (PrpLprepayService) ServiceFactory.getService("prpLprepayService");
		}
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		if (prpLcompensateService == null) {
			prpLcompensateService = (PrpLcompensateService) ServiceFactory.getService("prpLcompensateService");
		}
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpCmainService getPrpCmainService() {
		if (null == prpCmainService) {
			prpCmainService = (PrpCmainService) ServiceFactory.getService("prpCmainService");
		}
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpLclaimService getPrpLclaimService() {
		if (prpLclaimService == null) {
			prpLclaimService = (PrpLclaimService) ServiceFactory.getService("prpLclaimService");
		}
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLFMappingService getPrpLFMappingService() {
		if (prpLFMappingService == null) {
			prpLFMappingService = (PrpLFMappingService) ServiceFactory.getService("prpLFMappingService");
		}
		return prpLFMappingService;
	}

	public void setPrpLFMappingService(PrpLFMappingService prpLFMappingService) {
		this.prpLFMappingService = prpLFMappingService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		if (prpCitemKindService == null) {
			prpCitemKindService = (PrpCitemKindService) ServiceFactory.getService("prpCitemKindService");
		}
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		if (prpDcompanyService == null) {
			prpDcompanyService = (PrpDcompanyService) ServiceFactory.getService("prpDcompanyService");
		}
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public PrpDriskService getPrpDriskService() {
		if (prpDriskService == null) {
			prpDriskService = (PrpDriskService) ServiceFactory.getService("prpDriskService");
		}
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public PrpCitemCarExtService getPrpCitemCarExtService() {
		if (prpCitemCarExtService == null) {
			prpCitemCarExtService = (PrpCitemCarExtService) ServiceFactory.getService("prpCitemCarExtService");
		}
		return prpCitemCarExtService;
	}

	public void setPrpCitemCarExtService(PrpCitemCarExtService prpCitemCarExtService) {
		this.prpCitemCarExtService = prpCitemCarExtService;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		if (prpCcoinsService == null) {
			prpCcoinsService = (PrpCcoinsService) ServiceFactory.getService("prpCcoinsService");
		}
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}

	public PrpLchargeService getPrpLchargeService() {
		if (prpLchargeService == null) {
			prpLchargeService = (PrpLchargeService) ServiceFactory.getService("prpLchargeService");
		}
		return prpLchargeService;
	}

	public void setPrpLchargeService(PrpLchargeService prpLchargeService) {
		this.prpLchargeService = prpLchargeService;
	}

	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		if (prpLpayObjectInfoService == null) {
			prpLpayObjectInfoService = (PrpLpayObjectInfoService) ServiceFactory.getService("prpLpayObjectInfoService");
		}
		return prpLpayObjectInfoService;
	}

	public void setPrpLpayObjectInfoService(PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}

	public PrpCitemCarService getPrpCitemCarService() {
		if (prpCitemCarService == null) {
			prpCitemCarService = (PrpCitemCarService) ServiceFactory.getService("prpCitemCarService");
		}
		return prpCitemCarService;
	}

	public void setPrpCitemCarService(PrpCitemCarService prpCitemCarService) {
		this.prpCitemCarService = prpCitemCarService;
	}

	public PaymentWebService getPaymentWebService() {
		if (paymentWebService == null) {
			paymentWebService = (PaymentWebService) ServiceFactory.getService("paymentWebService");
		}
		return paymentWebService;
	}

	public void setPaymentWebService(PaymentWebService paymentWebService) {
		this.paymentWebService = paymentWebService;
	}

	public PrpLplanService getPrpLplanService() {
		if (prpLplanService == null) {
			prpLplanService = (PrpLplanService) ServiceFactory.getService("prpLplanService");
		}
		return prpLplanService;
	}

	public void setPrpLplanService(PrpLplanService prpLplanService) {
		this.prpLplanService = prpLplanService;
	}

	public PrpLplanKindService getPrpLplanKindService() {
		if (prpLplanKindService == null) {
			prpLplanKindService = (PrpLplanKindService) ServiceFactory.getService("prpLplanKindService");
		}
		return prpLplanKindService;
	}

	public void setPrpLplanKindService(PrpLplanKindService prpLplanKindService) {
		this.prpLplanKindService = prpLplanKindService;
	}

}
