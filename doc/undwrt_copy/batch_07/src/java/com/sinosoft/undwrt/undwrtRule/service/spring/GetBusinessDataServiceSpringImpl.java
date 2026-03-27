package com.sinosoft.undwrt.undwrtRule.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//mantis： CAR0233，處理人員：Sam，需求單編號：CAR0233，變更31,36,50,51,52,53,54險種核保檢核保額卡控問題
import org.apache.commons.lang3.StringUtils;
import com.sinosoft.common.schema.model.PrpCPcommissionDetail;
import com.sinosoft.common.schema.model.PrpCPinsured;
import com.sinosoft.common.schema.model.PrpCPinsuredNature;
import com.sinosoft.common.schema.model.PrpCPitemCar;
import com.sinosoft.common.schema.model.PrpCPitemKind;
import com.sinosoft.common.schema.model.PrpCPlimit;
import com.sinosoft.common.schema.model.PrpCPmain;
import com.sinosoft.common.schema.model.PrpCPmainLiab;
import com.sinosoft.common.schema.model.PrpPcommissionDetail;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpPitemKind;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpQaddress;
import com.sinosoft.common.schema.model.PrpQitemCar;
import com.sinosoft.common.schema.model.PrpQitemCarExt;
import com.sinosoft.common.schema.model.PrpQitemKind;
import com.sinosoft.common.schema.model.PrpQlimit;
import com.sinosoft.common.schema.model.PrpQmain;
import com.sinosoft.common.schema.model.PrpQmainProp;
import com.sinosoft.common.schema.model.PrpTaddress;
import com.sinosoft.common.schema.model.PrpTcommission;
import com.sinosoft.common.schema.model.PrpTcommissionDetail;
import com.sinosoft.common.schema.model.PrpTinsured;
import com.sinosoft.common.schema.model.PrpTinsuredNature;
import com.sinosoft.common.schema.model.PrpTitemCar;
import com.sinosoft.common.schema.model.PrpTitemCarExt;
import com.sinosoft.common.schema.model.PrpTitemKind;
import com.sinosoft.common.schema.model.PrpTlimit;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.common.schema.model.PrpTmainLiab;
import com.sinosoft.common.schema.model.PrpTmainProp;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.prpins.policy.service.facade.PrpCpMainService;
/* mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266 --- end */
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.util.UtilTools;
import com.sinosoft.undwrt.undwrtBase.model.UwBlackList;
import com.sinosoft.undwrt.undwrtBase.model.UwBlackListId;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwBlackListService;
import com.sinosoft.undwrt.undwrtRule.service.UndwrtRuleRiskKind;
import com.sinosoft.undwrt.undwrtRule.service.facade.GetBusinessDataService;
import com.sinosoft.undwrt.undwrtRule.vo.BusinessProposalData;

/**
 * 獲取業務數據實現類.
 */
public class GetBusinessDataServiceSpringImpl extends GenericDaoHibernate implements GetBusinessDataService  {

	/** 屬性批單處理接口. */
	private EndorseService endorseService;
	
	/** 屬性要保書訊息接口. */
	private PrpCpMainService prpCpMainService;
	
	/** 屬性要保書處理接口. */
	private PolicyService policyService;
	
	private UwBlackListService uwBlackListService;

	private BusinessProposalData businessProposalData;

	/**
	 * 獲取業務數據.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 業務數據類
	 * @see com.sinosoft.undwrt.undwrtRule.service.facade.GetBusinessDataService#getBusinessProposalData(java.lang.String,
	 *      java.lang.String)
	 */
	public BusinessProposalData getBusinessProposalData(String businessNo, String businessType) {
		BusinessProposalData businessProposalData = new BusinessProposalData();
		try {
			if ("T".equals(businessType)) {
				PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
				businessProposalData.setAmount(prpTmain.getSumAmount().doubleValue());
				businessProposalData.setClassCode(prpTmain.getClassCode());
				businessProposalData.setRiskCode(prpTmain.getRiskCode());
				businessProposalData.setComCode(prpTmain.getComCode());
				if (prpTmain.getPrpTitemCars().size() > 0) {
					PrpTitemCar prpTitemCar = prpTmain.getPrpTitemCars().get(0);
					// 车辆种类
					businessProposalData.setCarKind(prpTitemCar.getCarKindCode());
					// 使用性质
					businessProposalData.setUseNature(prpTitemCar.getUseNatureCode());
				}
				//mantis： CAR0233，處理人員：Sam，需求單編號：CAR0233，變更31,36,50,51,52,53,54險種核保檢核保額卡控問題
				String multipleKindCode = getGroupPrptItemKind("T",businessNo);
				if (prpTmain.getPrpTitemKinds().size() > 0) {
					for (int i = 0; i < prpTmain.getPrpTitemKinds().size(); i++) {
						PrpTitemKind prpTitemKind = prpTmain.getPrpTitemKinds().get(i);
						//mantis： CAR0233，處理人員：Sam，需求單編號：CAR0233，變更31,36,50,51,52,53,54險種核保檢核保額卡控問題
						if(multipleKindCode.contains(","+prpTitemKind.getKindCode()+",")){
							for(int j = 0; j < prpTmain.getPrpTlimits().size(); j++){
								PrpTlimit prpTlimit = prpTmain.getPrpTlimits().get(j);
								if(prpTitemKind.getId().getItemKindNo().toString().equals(prpTlimit.getId().getLimitNo().toString())){
									UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
									kind.setKindCode(prpTitemKind.getKindCode() + prpTlimit.getId().getLimitType());
									kind.setAmount(prpTlimit.getLimitFee().doubleValue());
									businessProposalData.addRiskKind(kind.getKindCode(), kind);
								}
							}
						} else {
							UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
							kind.setKindCode(prpTitemKind.getKindCode());
							kind.setAmount(prpTitemKind.getAmount().doubleValue());
							businessProposalData.addRiskKind(kind.getKindCode(), kind);
						}
					}
				}
			} else if ("E".equals(businessType)) {
				PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
				PrpCPmain prpCPmain = prpCpMainService.getPrpCpMainByPolicyNo(prpPhead.getPolicyNo());
				businessProposalData.setClassCode(prpPhead.getClassCode());
				businessProposalData.setRiskCode(prpPhead.getRiskCode());
				businessProposalData.setComCode(prpPhead.getComCode());
				if (prpCPmain.getPrpCPitemCars().size() > 0) {
					PrpCPitemCar prpCPitemCar = prpCPmain.getPrpCPitemCars().get(0);
					businessProposalData.setCarKind(prpCPitemCar.getCarKindCode());
				}
				//mantis： CAR0233，處理人員：Sam，需求單編號：CAR0233，變更31,36,50,51,52,53,54險種核保檢核保額卡控問題
				String multipleKindCode = getGroupPrptItemKind("P",businessNo);
				if (prpCPmain.getPrpCPitemKinds().size() > 0) {
					for (int i = 0; i < prpCPmain.getPrpCPitemKinds().size(); i++) {
						PrpCPitemKind prpCPitemKind = prpCPmain.getPrpCPitemKinds().get(i);
						//mantis： CAR0233，處理人員：Sam，需求單編號：CAR0233，變更31,36,50,51,52,53,54險種核保檢核保額卡控問題
						if(multipleKindCode.contains(","+prpCPitemKind.getKindCode()+",")){
							for(int j = 0; j < prpCPmain.getPrpCPlimits().size(); j++){
								PrpCPlimit prpCPlimit = prpCPmain.getPrpCPlimits().get(j);
								if(prpCPitemKind.getId().getItemKindNo().toString().equals(prpCPlimit.getId().getLimitNo().toString())){
									UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
									kind.setKindCode(prpCPitemKind.getKindCode() + prpCPlimit.getId().getLimitType());
									kind.setAmount(prpCPlimit.getLimitFee().doubleValue());
									businessProposalData.addRiskKind(kind.getKindCode(), kind);
								}
							}
						} else {
							UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
							kind.setKindCode(prpCPitemKind.getKindCode());
							kind.setAmount(prpCPitemKind.getAmount().doubleValue());
							businessProposalData.addRiskKind(kind.getKindCode(), kind);
						}
					}
				}
			}
			else if ("B".equals(businessType))
			{
				PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
				String underWriteFlag = prpQmain.getUnderWriteFlag();
				//9为审核通过的规则，非9为提交核保的规则
				if(!"9".equals(underWriteFlag))
			{
				PrpQitemCar prpQitemCar = prpQmain.getPrpQitemCars().get(0);
				businessProposalData.setCarKind(prpQitemCar.getCarKindCode());
				UwBlackList uwBlackList = new UwBlackList();
				UwBlackListId id = new UwBlackListId();
				boolean flow=true;
				String modelCode = prpQitemCar.getModelCode();
				QueryRule queryRule = QueryRule.getInstance();
				List list;
				if(flow)
				{
					queryRule.addEqual("id.blackListType", "5");
					queryRule.addEqual("id.blackListCode", prpQitemCar.getCarKindCode());
					list = uwBlackListService.getUwBlackList(queryRule);
					if(list.size()>0)
					{
						businessProposalData.setLimitedCarKind(true);
						flow = false;
					}
				}
				if(flow)
				{	
					queryRule = QueryRule.getInstance();
					queryRule.addEqual("id.blackListType", "4");
					queryRule.addEqual("id.blackListCode", modelCode);
					list = uwBlackListService.getUwBlackList(queryRule);
					if(list.size()>0)
					{
						businessProposalData.setLimitedModelCode(true);
						flow = false;
					}
					else
					{
						queryRule = QueryRule.getInstance();
						queryRule.addEqual("id.blackListType", "4");
						queryRule.addEqual("flag", "*");
						list = uwBlackListService.getUwBlackList(queryRule);
						for(int i=0;i<list.size();i++)
						{
							uwBlackList=(UwBlackList) list.get(i);
							if(modelCode.startsWith(uwBlackList.getId().getBlackListCode()))
							{
								businessProposalData.setLimitedModelCode(true);
								flow = false;
								break;
							}
						}
					}
				}
				if(flow)
				{
					queryRule = QueryRule.getInstance();
					String licenseNo= prpQitemCar.getLicenseNo();
					queryRule.addEqual("id.blackListType", "2");
					queryRule.addEqual("id.blackListCode", licenseNo);
					list = uwBlackListService.getUwBlackList(queryRule);
					if(list.size()>0)
					{
						businessProposalData.setLimitedLicenseNo(true);
						flow = false;
					}
					else
					{
						queryRule = QueryRule.getInstance();
						queryRule.addEqual("id.blackListType", "2");
						queryRule.addEqual("flag", "*");
						list = uwBlackListService.getUwBlackList(queryRule);
						for(int i=0;i<list.size();i++)
						{
							uwBlackList=(UwBlackList) list.get(i);
							if(null!=licenseNo && licenseNo.startsWith(uwBlackList.getId().getBlackListCode()))
							{
								businessProposalData.setLimitedLicenseNo(true);
								flow = false;
								break;
							}
						}
					}
				}
				if(flow)
				{
					queryRule = QueryRule.getInstance();
					String identifyCard="";
					for(int i=0;i<prpQmain.getPrpQinsureds().size();i++)
					{
						if(prpQmain.getPrpQinsureds().get(i).getInsuredFlag().equals("1"))
						{
							identifyCard= prpQmain.getPrpQinsureds().get(i).getIdentifyNumber();
							break;
						}
					}
					queryRule.addEqual("id.blackListType", "1");
					queryRule.addEqual("id.blackListCode", identifyCard);
					list = uwBlackListService.getUwBlackList(queryRule);
					if(list.size()>0)
					{
						businessProposalData.setLimitedIdentification(true);
						flow = false;
					}
					else
					{
						queryRule = QueryRule.getInstance();
						queryRule.addEqual("id.blackListType", "1");
						queryRule.addEqual("flag", "*");
						for(int i=0;i<list.size();i++)
						{
							uwBlackList=(UwBlackList) list.get(i);
							if(identifyCard.startsWith(uwBlackList.getId().getBlackListCode()))
							{
								businessProposalData.setLimitedIdentification(true);
								flow = false;
								break;
							 }
						}
					}
				}
				if(flow)
				{
					queryRule = QueryRule.getInstance();
					String engineNo= prpQitemCar.getEngineNo();
					queryRule.addEqual("id.blackListType", "3");
					queryRule.addEqual("id.blackListCode", engineNo);
					list = uwBlackListService.getUwBlackList(queryRule);
					if(list.size()>0)
					{
						businessProposalData.setLimitedEngineNo(true);
						flow = false;
					}
					else
					{
						queryRule = QueryRule.getInstance();
						queryRule.addEqual("id.blackListType", "3");
						queryRule.addEqual("flag", "*");
						for(int i=0;i<list.size();i++)
						{
							uwBlackList=(UwBlackList) list.get(i);
							if(engineNo.startsWith(uwBlackList.getId().getBlackListCode()))
							{
								businessProposalData.setLimitedEngineNo(true);
								flow = false;
								break;
							}
						}
					}
				}
				
				//mantis： CAR0107，處理人員：DP0706，需求單編號：CAR0107: 增加限定投保名單檢核類別 START
				//提交核保時卡控需人工核保，並顯示提示訊息「限保車號(業務篩選)，請
				// 洽核保人員，自動核保不通過，轉入人工核保。」(適用報價)
				if(flow)
				{
					queryRule = QueryRule.getInstance();
					String idno="";
					for(int i=0;i<prpQmain.getPrpQinsureds().size();i++)
					{
						if(prpQmain.getPrpQinsureds().get(i).getInsuredFlag().equals("1"))
						{
							idno= prpQmain.getPrpQinsureds().get(i).getIdentifyNumber();
							break;
						}
					}
					String blackListCode = prpQitemCar.getLicenseNo()+","+idno;
					queryRule.addEqual("id.blackListType", "7");
					queryRule.addEqual("id.blackListCode", blackListCode);
					list = uwBlackListService.getUwBlackList(queryRule);
					if(list.size()>0)
					{
						businessProposalData.setLimitedLicenseNoAndIdno(true);
						flow = false;
					}
				}
				//mantis： CAR0107，處理人員：DP0706，需求單編號：CAR0107: 增加限定投保名單檢核類別 END
				
				PrpQitemCarExt prpQitemCarExt=new PrpQitemCarExt();
				prpQitemCarExt= prpQmain.getPrpQitemCarExts().get(0);
				businessProposalData.setRiskCode(prpQmain.getRiskCode());
				if("A01".equals(prpQmain.getRiskCode()))
				{
					if(prpQitemCarExt.getFloatRateA()!=null)
					{
						businessProposalData.setFloatRateA(prpQitemCarExt.getFloatRateA().doubleValue());
					}
					if(prpQitemCarExt.getFloatRateG()!=null)
					{
						businessProposalData.setFloatRateG(prpQitemCarExt.getFloatRateG().doubleValue());
					}
				}
				if("B01".equals(prpQmain.getRiskCode()))
				{
				//強制險沒有floatRateA,上線緊急,先寫為0
				businessProposalData.setFloatRateA(0);
				businessProposalData.setFloatRateG(prpQitemCarExt.getFloatRateG().doubleValue());
				}
				businessProposalData.setUseYears(prpQitemCar.getUseYears().doubleValue());
				String hql="from PrpQitemKind where proposalNo= '" + businessNo + "'";
				List prpQitemKindList=this.findByHql(hql);
				if (prpQitemKindList.size() > 0) 
				{
					for (int i = 0; i < prpQitemKindList.size(); i++) {
					UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
					PrpQitemKind prpQitemKind =(PrpQitemKind) prpQitemKindList.get(i);
					kind.setKindCode(prpQitemKind.getKindCode());
					kind.setAmount(prpQitemKind.getAmount().doubleValue());
					businessProposalData.addRiskKind(kind.getKindCode(), kind);
					}
					
				}
			}
			else
			{
				String hql="from PrpQitemKind where proposalNo= '" + businessNo + "'";
				
				businessProposalData.setClassCode(prpQmain.getClassCode());
				businessProposalData.setRiskCode(prpQmain.getRiskCode());
				businessProposalData.setComCode(prpQmain.getComCode());
				List prpQitemKindList=this.findByHql(hql);
				if (prpQitemKindList.size() > 0) {
					for (int i = 0; i < prpQitemKindList.size(); i++) {
						PrpQitemKind prpQitemKind = (PrpQitemKind) prpQitemKindList.get(i);
						
						if("31".equals(prpQitemKind.getKindCode()) || "3A".equals(prpQitemKind.getKindCode()) || "51".equals(prpQitemKind.getKindCode())){
							for(int j = 0; j < prpQmain.getPrpQlimits().size(); j++){
								PrpQlimit prpQlimit = prpQmain.getPrpQlimits().get(j);
								if(prpQitemKind.getId().getItemKindNo().toString().equals(prpQlimit.getId().getLimitNo().toString())){
									UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
									kind.setKindCode(prpQitemKind.getKindCode() + prpQlimit.getId().getLimitType());
									kind.setAmount(prpQlimit.getLimitFee().doubleValue());
									businessProposalData.addRiskKind(kind.getKindCode(), kind);
								}
							}
						} else {
							UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
							kind.setKindCode(prpQitemKind.getKindCode());
							kind.setAmount(prpQitemKind.getAmount().doubleValue());
							businessProposalData.addRiskKind(kind.getKindCode(), kind);
						}
					}
				}
				
				if(prpQmain.getPrpQitemCars().size()>0 && prpQmain.getPrpQitemCars().get(0) != null){
					PrpQitemCar prpQitemCar = prpQmain.getPrpQitemCars().get(0);
					businessProposalData.setCarKind(prpQitemCar.getCarKindCode());
				}
			}
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return businessProposalData;
	}
	
	/**
	 * 拒保業務獲取報價單和要保書業務數據.
	 * @param businessNo 業務號
	 * @param businessType 業務類型
	 * @return 業務數據類
	 */
	public BusinessProposalData getBusinessData(String businessNo, String businessType)
	{
		BusinessProposalData businessProposalData = new BusinessProposalData();
		if("T".equals(businessType))
		{
			PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			businessProposalData.setBusinessNature(prpTmain.getBusinessNature());
			businessProposalData.setHander1Code(prpTmain.getHandler1Code());
		}
		else if("B".equals(businessType))
		{
			PrpQmain prpQmain=policyService.getPrpQmainByProposalNo(businessNo,
					"quotation");
			businessProposalData.setBusinessNature(prpQmain.getBusinessNature());
			businessProposalData.setHander1Code(prpQmain.getHandler1Code());
		}
		return businessProposalData;
	}
	/**
	 * 獲取業務數據.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 業務數據類
	 * @throws Exception 
	 * @see com.sinosoft.undwrt.undwrtRule.service.facade.GetBusinessDataService#getBusinessProposalData(java.lang.String,
	 *      java.lang.String)
	 */
	public BusinessProposalData getUnCarBusinessData(String businessNo, String businessType) throws Exception 
	{
		BusinessProposalData businessData = new BusinessProposalData();
		businessData.setBusinessType(businessType);
		if("T".equals(businessType))
		{
			PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			String othFlag = prpTmain.getOthFlag();
			prpTmain.getPrpTitemKinds();
			double costRate=0;
			int  daysMinus = 0;
			Date startDate = prpTmain.getStartDate();
			Date underWriteDate = prpTmain.getUnderWriteEndDate();
			
			if("F".equals(prpTmain.getClassCode())){
				Date signDate=prpTmain.getSignDate();//获取建档日期
				daysMinus = daysBetween(startDate,signDate);//火险保单追溯日=起保日-建檔日期
			}else if(null!=underWriteDate){
				daysMinus = daysBetween(startDate,underWriteDate);
			}
			businessData.setPolicydays(daysMinus);
			businessData.setBusinessNature(othFlag.substring(16, 17));
			businessData.setRiskCode(prpTmain.getRiskCode());
			businessData.setClassCode(prpTmain.getClassCode());
			businessData.setComCode(prpTmain.getComCode());
			if("F01".equals(prpTmain.getRiskCode()))
			{
				List<PrpTcommissionDetail> tcommissionDetailList = prpTmain.getPrpTcommissionDetails();
				businessData.setAmount(prpTmain.getSumAmount().doubleValue()/1000000);
				for(int i=0;i<tcommissionDetailList.size();i++)
				{
				PrpTcommissionDetail prpTcommissionDetail = tcommissionDetailList.get(i);
				if(null!=prpTcommissionDetail.getCostRate() && costRate<=prpTcommissionDetail.getCostRate().doubleValue())
				{
					costRate=prpTcommissionDetail.getCostRate().doubleValue();
				}
				}
				businessData.setCostRate(costRate);
			}
			else if("C1".equals(prpTmain.getClassCode()))
			{
				
				/*List<PrpTinsured> prpTinsuredList = prpTmain.getPrpTinsureds();
				Date endDte = prpTmain.getEndDate();
				Date nowDate = new Date();
				List BMInumbers = new ArrayList();
				List positions = new ArrayList();
				List occupationVersion =  new ArrayList();
				if(null!=endDte)
				{
					daysMinus = daysBetween(startDate,endDte);
				}
				businessData.setPolicydays(daysMinus);
				daysMinus = daysBetween(startDate,nowDate);
				businessData.setGreaterNowDate(daysMinus);
				for(int j=0;j<prpTinsuredList.size();j++)
				{
					PrpTinsured prpTinsured = prpTinsuredList.get(j);
					for(int r=0;r<prpTinsured.getPrpTinsuredNatures().size();r++)
					{
						PrpTinsuredNature insuredNature = prpTinsured.getPrpTinsuredNatures().get(r);
						if(null!=insuredNature.getDutyLevel() && Integer.valueOf(insuredNature.getDutyLevel())>Integer.valueOf(dutyLevel))
						{
							dutyLevel = insuredNature.getDutyLevel();
						}
						if(null!=insuredNature.getAge()&&insuredNature.getAge()>insuredAge)
						{
							insuredAge = Integer.parseInt(String.valueOf(insuredNature.getAge()));
						}
						double BMInumber = 0;
						if(null!=insuredNature.getBMINumber())
						{
							BMInumber = insuredNature.getBMINumber().doubleValue();
						}
						String positionCode = insuredNature.getPositionCode();
						if(null!=positionCode && positionCode.equals("1"))
						{
							positionCode="A";
						}
						String occuVer = insuredNature.getOccupationVersion();
						if(null!=occuVer && occuVer.equals("2"))
						{
							occuVer="A";
						}
						positions.add(positionCode);
						occupationVersion.add(occuVer);
						BMInumbers.add(BMInumber);
					}
					
				}
				if (prpTmain.getPrpTitemKinds().size() > 0)
				{
					for (int i = 0; i < prpTmain.getPrpTitemKinds().size(); i++)
					{
						PrpTitemKind prpTitemKind = prpTmain.getPrpTitemKinds().get(i);
						UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
						kind.setKindCode(prpTitemKind.getKindCode());
						kind.setAmount(prpTitemKind.getAmount().doubleValue()/10000);
						businessData.addRiskKind(kind.getKindCode(), kind);
					}
				}
				if(prpTmain.getPrpTrenewals().size()>0)
				{
					businessData.setIsRenewal("1");
				}
				else
				{
					businessData.setIsRenewal("0");
				}
				businessData.setDutyLevel(dutyLevel);
				businessData.setInsuredAge(insuredAge);
				businessData.setBMInumber(BMInumbers);
				//缴别
				int payTime=prpTmain.getPayTimes();
				if(payTime==1)
				{
					businessData.setPayKind("A");
				}
				businessData.setPositionCodes(positions);
				businessData.setOccuVersions(occupationVersion);*/
			}
			else if("C".equals(prpTmain.getClassCode()))
			{
				int totalAmountCoeffi=0;
				int deathDamage = 0;
				int accentDeath = 0;
				int accentDamage = 0;
				int perHumanInjury=0;
				int golfAmount = 0;
				int contractAmount=0;
				int tolAmount=0; 
				PrpTmainLiab prpTmainLiab = prpTmain.getPrpTmainLiabs().get(0);
				Map<String,Double> flexiAmount = new HashMap<String,Double>();
				Date endDte = prpTmain.getEndDate();
				String otherFlag = othFlag.substring(0,1);
				if(null!=endDte)
				{
					daysMinus = daysBetween(startDate,endDte);
				}
				//新件保单追溯天数
				if(!"1".equals(otherFlag))
				{
					//businessData.setInsurePeriod(((daysMinus-1)/365)+1);
					//保险期间1年校验 
					//获取一年后日期
					Calendar calVaildDate = Calendar.getInstance();
					calVaildDate.setTime(startDate);
					calVaildDate.add(Calendar.YEAR, 1);
					//终保日期
					Calendar calEndDate = Calendar.getInstance();
					calEndDate.setTime(endDte);
					
					
					if((calEndDate.compareTo(calVaildDate)) <= 0){
						businessData.setInsurePeriod(1);
					}else if((calEndDate.compareTo(calVaildDate)) > 0){
						businessData.setInsurePeriod(2);
					}
					

					
				}
				if(prpTmainLiab.getContractAmount()!=null)
				{
				 contractAmount=prpTmainLiab.getContractAmount().intValue();
				}
				if ((prpTmain.getPrpTitemKinds().size() > 0 )&&(!"PR".equals(prpTmain.getRiskCode())))
				{
					// add by wangcan2015/12/15 增加责任险核保规则 start
					if("FC".equals(prpTmain.getRiskCode())){
						contractAmount= 0;
					}
					
					for (int i = 0; i < prpTmain.getPrpTitemKinds().size(); i++)
					{
						PrpTitemKind prpTitemKind = prpTmain.getPrpTitemKinds().get(i);
						if("AB".equals(prpTmain.getRiskCode()) || "BN".equals(prpTmain.getRiskCode())){
							
							if(totalAmountCoeffi<nullToInt(prpTitemKind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpTitemKind.getAmount());
							}
						}
						//mantis： LIA0214，處理人員：DP0706，新商品上新核心需求-AT-建築師技師消防師(士)
						if("AE".equals(prpTmain.getRiskCode()) ||"AT".equals(prpTmain.getRiskCode())){
							
							if(totalAmountCoeffi<nullToInt(prpTitemKind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpTitemKind.getAmount());
							}
						}
						
						if("CN".equals(prpTmain.getRiskCode())){
						
							if(perHumanInjury<nullToInt(prpTitemKind.getPerHumanInjury()))
							{
								perHumanInjury = nullToInt(prpTitemKind.getPerHumanInjury());
							}
							
							if(accentDeath< nullToInt(prpTitemKind.getPerAccidentDeaths()))
							{
								accentDeath= nullToInt(prpTitemKind.getPerAccidentDeaths());
							}
							
							if(accentDamage<nullToInt(prpTitemKind.getPerAccidentDamage()))
							{
								accentDamage = nullToInt(prpTitemKind.getPerAccidentDamage());
							}
							
							if(totalAmountCoeffi<nullToInt(prpTitemKind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpTitemKind.getAmount());
							}
						
						}	
						
						//mantis： LIA0101，處理人員：DP0713，需求單編號：LIA0101 EL新核心系統建置
						//mantis： LIA0120，處理人員：DP0706，需求單編號：LIA0120  責任險-ER雇補險_新核心系統建置
						if("ER".equals(prpTmain.getRiskCode()) || "EL".equals(prpTmain.getRiskCode()) || "EM".equals(prpTmain.getRiskCode())){
						
							if(perHumanInjury<nullToInt(prpTitemKind.getPerHumanInjury()))
							{
								perHumanInjury = nullToInt(prpTitemKind.getPerHumanInjury());
							}
							
							if(deathDamage<(nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage());
							}
							
							if(totalAmountCoeffi<nullToInt(prpTitemKind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpTitemKind.getAmount());
							}
						
						}
						if("FD".equals(prpTmain.getRiskCode())){
						
							if(deathDamage<(nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage());
							}
						
						}
						if("GF".equals(prpTmain.getRiskCode())){//待更正，保额为一杆进洞
							
							if(golfAmount<nullToInt(prpTitemKind.getAmount()))
								{
									golfAmount = nullToInt(prpTitemKind.getAmount());
								}
						
						}
						if("LF".equals(prpTmain.getRiskCode())){
							
							if(perHumanInjury<nullToInt(prpTitemKind.getPerHumanInjury()))
							{
								perHumanInjury = nullToInt(prpTitemKind.getPerHumanInjury());
							}
							
							if(accentDeath< nullToInt(prpTitemKind.getPerAccidentDeaths()))
							{
								accentDeath= nullToInt(prpTitemKind.getPerAccidentDeaths());
							}
							
							if(accentDamage<nullToInt(prpTitemKind.getPerAccidentDamage()))
							{
								accentDamage = nullToInt(prpTitemKind.getPerAccidentDamage());
							}
							
							if(totalAmountCoeffi<nullToInt(prpTitemKind.getTotalAmountCoeffi()))
							{
								totalAmountCoeffi = nullToInt(prpTitemKind.getTotalAmountCoeffi());
							}
						}
						if("MN".equals(prpTmain.getRiskCode())){
							
							if(prpTitemKind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpTitemKind.getAmount().intValue();
							}
						}
						//mantis： LIA0218，處理人員：DP0706，新商品上新核心需求-CV-傳染病費用補償保險
						if("PB".equals(prpTmain.getRiskCode()) || "CV".equals(prpTmain.getRiskCode())){
							

							if(perHumanInjury<nullToInt(prpTitemKind.getPerHumanInjury()))
							{
								perHumanInjury = nullToInt(prpTitemKind.getPerHumanInjury());
							}
							
							if(accentDeath< nullToInt(prpTitemKind.getPerAccidentDeaths()))
							{
								accentDeath= nullToInt(prpTitemKind.getPerAccidentDeaths());
							}
							
							if(accentDamage<nullToInt(prpTitemKind.getPerAccidentDamage()))
							{
								accentDamage = nullToInt(prpTitemKind.getPerAccidentDamage());
							}
							
							if(totalAmountCoeffi<nullToInt(prpTitemKind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpTitemKind.getAmount());
							}
						}	
						if("PR".equals(prpTmain.getRiskCode())){
							

							if(perHumanInjury<nullToInt(prpTitemKind.getPerHumanInjury()))
							{
								perHumanInjury = nullToInt(prpTitemKind.getPerHumanInjury());
							}
							
							if(accentDeath< nullToInt(prpTitemKind.getPerAccidentDeaths()))
							{
								accentDeath= nullToInt(prpTitemKind.getPerAccidentDeaths());
							}
							
							if(accentDamage<nullToInt(prpTitemKind.getPerAccidentDamage()))
							{
								accentDamage = nullToInt(prpTitemKind.getPerAccidentDamage());
							}
							
							if(totalAmountCoeffi<nullToInt(prpTitemKind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpTitemKind.getAmount());
							}
						}	
						if("AR".equals(prpTmain.getRiskCode())){
							
							if(tolAmount<nullToInt(prpTitemKind.getAmount()))
							{
								tolAmount = nullToInt(prpTitemKind.getAmount());
							}
						}	
						if("BB".equals(prpTmain.getRiskCode()) || "BC".equals(prpTmain.getRiskCode())){
							
							if(prpTitemKind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpTitemKind.getAmount().intValue();
							}
						}
						if("BL".equals(prpTmain.getRiskCode()) || "SB".equals(prpTmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage());
							}
						}
						if("BR".equals(prpTmain.getRiskCode()) || "GS".equals(prpTmain.getRiskCode())){
							
							if(tolAmount<nullToInt(prpTitemKind.getAmount()))
							{
								tolAmount = nullToInt(prpTitemKind.getAmount());
							}
						}
						if("CC".equals(prpTmain.getRiskCode())){
							
							if(prpTitemKind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpTitemKind.getAmount().intValue();
							}
						}
						if("CB".equals(prpTmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage());
							}
						}

						if("DI".equals(prpTmain.getRiskCode()) || "DO".equals(prpTmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage());
							}
						}
						if("DS".equals(prpTmain.getRiskCode())){
							
							if(prpTitemKind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpTitemKind.getAmount().intValue();
							}
						}
						if("FC".equals(prpTmain.getRiskCode())){
							
							if(prpTitemKind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpTitemKind.getAmount().intValue();
							}
						}
						if("TE".equals(prpTmain.getRiskCode())){
							
							if(prpTitemKind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpTitemKind.getAmount().intValue();
							}
						}
						if("MF".equals(prpTmain.getRiskCode()) || "MP".equals(prpTmain.getRiskCode()) || "TL".equals(prpTmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage());
							}
						}
						if("PC".equals(prpTmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage());
							}
						}
						if("PM".equals(prpTmain.getRiskCode()) || "TP".equals(prpTmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage());
							}
						}
						if("SC".equals(prpTmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage());
							}
						}
						if("SP".equals(prpTmain.getRiskCode())){
							
							if(tolAmount<nullToInt(prpTitemKind.getAmount()))
							{
								tolAmount = nullToInt(prpTitemKind.getAmount());
							}
						}
						if("ST".equals(prpTmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpTitemKind.getPerAccidentDeaths()) + nullToInt(prpTitemKind.getPerAccidentDamage());
							}
						}
						if("TC".equals(prpTmain.getRiskCode()) || "TD".equals(prpTmain.getRiskCode())){
							
							if(tolAmount<nullToInt(prpTitemKind.getAmount()))
							{
								tolAmount = nullToInt(prpTitemKind.getAmount());
							}
						}
					}
					flexiAmount.put("AGG", totalAmountCoeffi/10000.0000);//保险期间内累计保险金额AGG
					flexiAmount.put("AOA", deathDamage/10000.0000);//每一意外事故体伤及财损AOA
					flexiAmount.put("BD", accentDeath/10000.0000);	//每一意外事故体伤或死亡BD
					flexiAmount.put("PD", accentDamage/10000.0000);//每一意外事故财产损失PD
					flexiAmount.put("AOP", perHumanInjury/10000.0000);//每一个人体伤或死亡AOP
					flexiAmount.put("YGJD", golfAmount/10000.0000);//一杆进洞 
					flexiAmount.put("DXZG", contractAmount/10000.0000);//单项最高
					flexiAmount.put("tolAmount", tolAmount/10000.0000);//总保险金额
				}
				// add by wangcan2015/12/15 增加责任险核保规则 end
				
				//add by zhanghuanqi 20150416 修改PR保额规则 begin
				if("PR".equals(prpTmain.getRiskCode())){
					List<PrpTlimit> prpTlimits  = new ArrayList<PrpTlimit>();
					prpTlimits = prpTmain.getPrpTlimits();
					if(prpTlimits.size()>0){
						for(int i = 0; i < prpTlimits.size(); i++){
							PrpTlimit prpTlimit = prpTlimits.get(i);
							if(prpTlimit != null){
								//modify by xuhuiling PR处理主险下的现在校验 20161117 begin
								if("100".equals(prpTlimit.getId().getLimitType().toString())){//每一个人体伤或死亡AOP
									perHumanInjury = nullToInt(prpTlimit.getLimitFee());
								}else if("101".equals(prpTlimit.getId().getLimitType().toString())){//每一意外事故体伤或死亡BD
									accentDeath = nullToInt(prpTlimit.getLimitFee());
								}else if("102".equals(prpTlimit.getId().getLimitType().toString())){//每一意外事故财产损失PD(AOA)每一意外事故体伤及财损AOA
									accentDamage = nullToInt(prpTlimit.getLimitFee());
								}/*else if("4".equals(prpTlimit.getId().getLimitNo().toString())){//每一意外事故财产损失PD
									
								}*/else if("104".equals(prpTlimit.getId().getLimitType().toString())){//保险期间内累计保险金额AGG
									totalAmountCoeffi = nullToInt(prpTlimit.getLimitFee());
								}
								//modify by xuhuiling PR处理主险下的现在校验 20161117 end
							}
							
						}
					}
					//每一个人体伤或死亡AOP
					flexiAmount.put("AOP", perHumanInjury/10000.0000);
					//每一意外事故体伤或死亡BD
					flexiAmount.put("BD", accentDeath/10000.0000);
					//每一意外事故财产损失PD
					flexiAmount.put("PD", accentDamage/10000.0000);
/*					//每一意外事故体伤及财损AOA
					flexiAmount.put("AOA", deathDamage/10000.0000);*/
					//保险期间内累计保险金额AGG
					flexiAmount.put("AGG", totalAmountCoeffi/10000.0000);

				}
				//add by zhanghuanqi 20150416 修改PR保额规则 end
				
				flexiAmount.put("tolAmount", prpTmain.getSumAmount()==null? 0:prpTmain.getSumAmount().intValue()/10000.0000);
				
				List<PrpTcommissionDetail> tcommissionDetailList = prpTmain.getPrpTcommissionDetails();
				for(int i=0;i<tcommissionDetailList.size();i++)
				{
				PrpTcommissionDetail prpTcommissionDetail = tcommissionDetailList.get(i);
				if(costRate<=nullToInt(prpTcommissionDetail.getCostRate()))
				{
					costRate=nullToInt(prpTcommissionDetail.getCostRate());
				}
				}
				String projectCode = prpTmain.getProjectCode();
				//专案不检测佣金比例
				if(projectCode!=null && !"".equals(projectCode))
				{
					businessData.setCostRate(-1);
				}
				else
				{
					businessData.setCostRate(costRate);
				}
				businessData.setExtendPeriod(UtilTools.isNumeric(prpTmainLiab.getExtendReportPeriod())? Integer.valueOf(prpTmainLiab.getExtendReportPeriod()) : 0);
				businessData.setFlexiableAmount(flexiAmount);
				businessData.setProjectCode(prpTmain.getProjectCode());
				Object object= super.findBySql("select approvalNo from prpTmain  where proposalNo = ?", prpTmain.getProposalNo()).get(0) ;
				if(object != null){
					businessData.setApprovalNo(object.toString());
				}

			}
			else if("E".equals(prpTmain.getClassCode()))
			{
				List<PrpTcommission> tcommissionDetailList = prpTmain.getPrpTcommissions();
				PrpTmainLiab prpTmainLiab=new PrpTmainLiab();
				if(prpTmain.getPrpTmainLiabs().size()>0){
					prpTmainLiab = prpTmain.getPrpTmainLiabs().get(0);
				}
				businessData.setAmount(prpTmain.getSumAmount().doubleValue()/1000000);
				for(int i=0;i<tcommissionDetailList.size();i++)
				{
					PrpTcommission prpTcommission = tcommissionDetailList.get(i);
					if(null!=prpTcommission.getCostRate() && costRate<=prpTcommission.getCostRate().doubleValue())
					{
						costRate=prpTcommission.getCostRate().doubleValue();
					}
				}
				String projectCode = prpTmain.getProjectCode();
				//专案不检测佣金比例
				if(projectCode!=null && !"".equals(projectCode))
				{
					businessData.setCostRate(-1);
				}
				else
				{
					businessData.setCostRate(costRate);
				}
				if(prpTmain.getPrpTmainConstructs().size()>0 && prpTmain.getPrpTmainConstructs().get(0)!=null)
				{
					businessData.setConstructType(prpTmain.getPrpTmainConstructs().get(0).getConstructType()==null?"":prpTmain.getPrpTmainConstructs().get(0).getConstructType());
				}
				Date endDte = prpTmain.getEndDate();
				if(null!=endDte)
				{
					daysMinus = daysBetween(startDate,endDte);
				}
				businessData.setInsurePeriod(daysMinus/365);
				businessData.setExtendPeriod(UtilTools.isNumeric(prpTmainLiab.getExtendReportPeriod())? Integer.valueOf(prpTmainLiab.getExtendReportPeriod()) : 0);
			}
			else if("M".equals(prpTmain.getClassCode()))
			{   
				//modify by xuhuiling 5220_20161011-MC2核保權限設定有問題(USD) 20161121 begin
				businessData.setAmount((prpTmain.getSumAmount().doubleValue())*(prpTmain.getExchangeRate().doubleValue())/10000);
				//modify by xuhuiling 5220_20161011-MC2核保權限設定有問題(USD) 20161121 end
				List<PrpTcommissionDetail> tcommissionDetailList = prpTmain.getPrpTcommissionDetails();
				for(int i=0;i<tcommissionDetailList.size();i++)
				{
					PrpTcommissionDetail prpTcommissionDetail = tcommissionDetailList.get(i);
					if(costRate<=nullToInt(prpTcommissionDetail.getCostRate()))
					{
						costRate=nullToInt(prpTcommissionDetail.getCostRate());
					}
				}
				businessData.setCostRate(costRate);
			}
			else
			{
				if (prpTmain.getPrpTitemKinds().size() > 0)
				{
					for (int i = 0; i < prpTmain.getPrpTitemKinds().size(); i++)
					{
						PrpTitemKind prpTitemKind = prpTmain.getPrpTitemKinds().get(i);
						PrpTcommissionDetail prpTcommissionDetail = prpTmain.getPrpTcommissionDetails().get(i);
						UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
						kind.setKindCode(prpTitemKind.getKindCode());
						kind.setAmount(prpTitemKind.getAmount().doubleValue()/1000000);
						if(null!=prpTcommissionDetail.getCostRate())
						{
						 kind.setKindCostRate(prpTcommissionDetail.getCostRate().doubleValue());
						}
						businessData.addRiskKind(kind.getKindCode(), kind);
					}
				}
			}
			
		}
		else if("E".equals(businessType))
		{
			PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
			PrpCPmain prpCPmain = prpCpMainService.getPrpCpMainByPolicyNo(prpPhead.getPolicyNo());
			PrpPmain prpPmain = prpPhead.getPrpPmains().get(0);
			String othFlag = prpCPmain.getOthFlag();
			double costRate=0;
			int  daysMinus = 0;
			int cancelDays = 0;
			Date startDate = prpCPmain.getStartDate();
			Date nowDate = new Date();
			Date underWriteDate = prpCPmain.getUnderWriteEndDate();
			Date validDate = prpPhead.getValidDate();
			if("F".equals(prpCPmain.getClassCode())){
				Date inputDate = prpCPmain.getInputDate();//获取批单录入日期
				daysMinus = daysBetween(validDate,inputDate);//批单追溯日=批单录入日期-批单生效日期
			}else if(null!=underWriteDate)
			{
				daysMinus = daysBetween(startDate,underWriteDate);
			}
			//增加批改类型的判断，只有注销类型的才给注销天数赋值20140731
			if("19".equals(prpPhead.getEndorType()))
			{
				cancelDays =  daysBetween(startDate,validDate);
			}
			businessData.setEndorsedays(daysMinus);
			businessData.setCanceldays(cancelDays);
			if("C1".equals(prpCPmain.getClassCode())){
				daysMinus = daysBetween(validDate,nowDate);
			}else{
				daysMinus = daysBetween(startDate,nowDate);
			}
			businessData.setGreaterNowDate(daysMinus);
			businessData.setBusinessNature(othFlag.substring(16, 17));
			businessData.setRiskCode(prpCPmain.getRiskCode());
			businessData.setClassCode(prpCPmain.getClassCode());
			businessData.setComCode(prpCPmain.getComCode());
			if("F01".equals(prpCPmain.getRiskCode()))
			{
				List<PrpCPcommissionDetail> cpcommissionDetailList = prpCPmain.getPrpCPcommissionDetails();
				businessData.setAmount(prpCPmain.getSumAmount().doubleValue()/1000000);
				for(int i=0;i<cpcommissionDetailList.size();i++)
				{
					PrpCPcommissionDetail prpCPcommissionDetail = cpcommissionDetailList.get(i);
					if(null!=prpCPcommissionDetail.getCostRate() && costRate<=prpCPcommissionDetail.getCostRate().doubleValue())
					{
						costRate=prpCPcommissionDetail.getCostRate().doubleValue();
					}
				}
				businessData.setCostRate(costRate);
	
			}
			else if("C1".equals(prpCPmain.getClassCode()))
			{
				String dutyLevel="0";
				int insuredAge= 0;
				List<PrpCPinsured> prpCpinsuredList = prpCPmain.getPrpCPinsureds();
				List BMInumbers = new ArrayList();
				List positions = new ArrayList();
				List occupationVersion =  new ArrayList();
				Date endDte = prpCPmain.getEndDate();
				if(null!=endDte)
				{
					daysMinus = daysBetween(startDate,endDte);
				}
				businessData.setPolicydays(daysMinus);
				if(daysBetween(endDte,nowDate)>0)
				{
					businessData.setGreaterEndDate("Y");
				}
				for(int j=0;j<prpCpinsuredList.size();j++)
				{
					PrpCPinsured prpCpinsured = prpCpinsuredList.get(j);
					for(int r=0;r<prpCpinsured.getPrpCPinsuredNatures().size();r++)
					{
						PrpCPinsuredNature insuredNature = prpCpinsured.getPrpCPinsuredNatures().get(r);
						if(null!=insuredNature.getDutyLevel() && Integer.valueOf(insuredNature.getDutyLevel())>Integer.valueOf(dutyLevel))
						{
							dutyLevel = insuredNature.getDutyLevel();
						}
						if(null!=insuredNature.getAge()&&insuredNature.getAge()>insuredAge)
						{
							insuredAge = Integer.parseInt(String.valueOf(insuredNature.getAge()));
						}
						double BMInumber = 0;
						if(null!=insuredNature.getBMINumber())
						{
							BMInumber = insuredNature.getBMINumber().doubleValue();
						}
						String positionCode = insuredNature.getPositionCode();
						if(null!=positionCode && positionCode.equals("1"))
						{
							positionCode="A";
						}
						String occuVer = insuredNature.getOccupationVersion();
						if(null!=occuVer && occuVer.equals("1"))
						{
							occuVer="A";
						}
						positions.add(positionCode);
						occupationVersion.add(occuVer);
						BMInumbers.add(BMInumber);
					}
					
				}
				if (prpCPmain.getPrpCPitemKinds().size() > 0)
				{
					for (int i = 0; i < prpCPmain.getPrpCPitemKinds().size(); i++)
					{
						PrpCPitemKind prpCpitemKind = prpCPmain.getPrpCPitemKinds().get(i);
						UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
						kind.setKindCode(prpCpitemKind.getKindCode());
						kind.setAmount(prpCpitemKind.getAmount().doubleValue()/10000);
						businessData.addRiskKind(kind.getKindCode(), kind);
					}
				}
				businessData.setDutyLevel(dutyLevel);
				businessData.setInsuredAge(insuredAge);
				businessData.setBMInumber(BMInumbers);
				businessData.setPositionCodes(positions);
				businessData.setOccuVersions(occupationVersion);
				int payTime=prpCPmain.getPayTimes();
				if(payTime==1)
				{
					businessData.setPayKind("A");
				}
				businessData.setEndorType(prpPhead.getEndorType());
			}
			else if("C".equals(prpCPmain.getClassCode()))
			{
				int totalAmountCoeffi=0;
				int deathDamage = 0;
				int accentDeath = 0;
				int accentDamage = 0;
				int perHumanInjury=0;
				int golfAmount = 0;
				int contractAmount=0;
				int tolAmount=0; 
				double chgPremium = 0;
				/*
				mantis： LIA0092，處理人員：Sam，需求單編號：LIA0092--- start
				普通批改-核保&保費試算
				*/
				PrpCPmainLiab prpCPmainLiab = new PrpCPmainLiab();
				if(prpCPmain.getPrpCPmainLiabs() != null && prpCPmain.getPrpCPmainLiabs().size() > 0 ){
					prpCPmain.getPrpCPmainLiabs().get(0);	
				}
				/* mantis： LIA0092，處理人員：Sam，需求單編號：LIA0092 --- end */
				Map<String,Double> flexiAmount = new HashMap<String,Double>();
				Date endDte = prpCPmain.getEndDate();
				String otherFlag = othFlag.substring(0,1);
				if(null!=endDte)
				{
					daysMinus = daysBetween(startDate,endDte);
				}
				//新件保单追溯天数
				if(!"1".equals(otherFlag))
				{
					businessData.setInsurePeriod(daysMinus/365);
				}
				if(prpCPmainLiab.getContractAmount()!=null)
				{
				 contractAmount=prpCPmainLiab.getContractAmount().intValue();
				}
				// add by wangcan2015/12/15 增加责任险核保规则 start
				if (prpCPmain.getPrpCPitemKinds().size() > 0){
					for (int i = 0; i < prpCPmain.getPrpCPitemKinds().size(); i++){
						
						PrpCPitemKind prpcpitemkind = prpCPmain.getPrpCPitemKinds().get(i);
						
						if("AB".equals(prpCPmain.getRiskCode()) || "BN".equals(prpCPmain.getRiskCode())){
							
							if(totalAmountCoeffi<nullToInt(prpcpitemkind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpcpitemkind.getAmount());
							}
						}
						//mantis： LIA0214，處理人員：DP0706，新商品上新核心需求-AT-建築師技師消防師(士)
						if("AE".equals(prpCPmain.getRiskCode())|| "AT".equals(prpCPmain.getRiskCode())){
							
							if(totalAmountCoeffi<nullToInt(prpcpitemkind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpcpitemkind.getAmount());
							}
						}
						
						if("CN".equals(prpCPmain.getRiskCode())){
						
							if(perHumanInjury<nullToInt(prpcpitemkind.getPerHumanInjury()))
							{
								perHumanInjury = nullToInt(prpcpitemkind.getPerHumanInjury());
							}
							
							if(accentDeath< nullToInt(prpcpitemkind.getPerAccidentDeaths()))
							{
								accentDeath= nullToInt(prpcpitemkind.getPerAccidentDeaths());
							}
							
							if(accentDamage<nullToInt(prpcpitemkind.getPerAccidentDamage()))
							{
								accentDamage = nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
							
							if(totalAmountCoeffi<nullToInt(prpcpitemkind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpcpitemkind.getAmount());
							}
						
						}	
						
						//mantis： LIA0101，處理人員：DP0713，需求單編號：LIA0101 EL新核心系統建置
						if("EL".equals(prpCPmain.getRiskCode()) || "EM".equals(prpCPmain.getRiskCode())){
						
							if(perHumanInjury<nullToInt(prpcpitemkind.getPerHumanInjury()))
							{
								perHumanInjury = nullToInt(prpcpitemkind.getPerHumanInjury());
							}
							
							if(deathDamage<(nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
							
							if(totalAmountCoeffi<nullToInt(prpcpitemkind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpcpitemkind.getAmount());
							}
						
						}
						if("FD".equals(prpCPmain.getRiskCode())){
						
							if(deathDamage<(nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
						
						}
						if("GF".equals(prpCPmain.getRiskCode())){//待更正，保额为一杆进洞
							
							if(golfAmount<nullToInt(prpcpitemkind.getAmount()))
								{
									golfAmount = nullToInt(prpcpitemkind.getAmount());
								}
						
						}
						if("LF".equals(prpCPmain.getRiskCode())){
							
							if(perHumanInjury<nullToInt(prpcpitemkind.getPerHumanInjury()))
							{
								perHumanInjury = nullToInt(prpcpitemkind.getPerHumanInjury());
							}
							
							if(accentDeath< nullToInt(prpcpitemkind.getPerAccidentDeaths()))
							{
								accentDeath= nullToInt(prpcpitemkind.getPerAccidentDeaths());
							}
							
							if(accentDamage<nullToInt(prpcpitemkind.getPerAccidentDamage()))
							{
								accentDamage = nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
							
							if(totalAmountCoeffi<nullToInt(prpcpitemkind.getTotalAmountCoeffi()))
							{
								totalAmountCoeffi = nullToInt(prpcpitemkind.getTotalAmountCoeffi());
							}
						}
						if("MN".equals(prpCPmain.getRiskCode())){
							
							if(prpcpitemkind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpcpitemkind.getAmount().intValue();
							}
						}	
						//mantis： LIA0218，處理人員：DP0706，新商品上新核心需求-CV-傳染病費用補償保險
						if("PB".equals(prpCPmain.getRiskCode()) || "CV".equals(prpCPmain.getRiskCode())){
							

							if(perHumanInjury<nullToInt(prpcpitemkind.getPerHumanInjury()))
							{
								perHumanInjury = nullToInt(prpcpitemkind.getPerHumanInjury());
							}
							
							if(accentDeath< nullToInt(prpcpitemkind.getPerAccidentDeaths()))
							{
								accentDeath= nullToInt(prpcpitemkind.getPerAccidentDeaths());
							}
							
							if(accentDamage<nullToInt(prpcpitemkind.getPerAccidentDamage()))
							{
								accentDamage = nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
							
							if(totalAmountCoeffi<nullToInt(prpcpitemkind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpcpitemkind.getAmount());
							}
						}	
						if("PR".equals(prpCPmain.getRiskCode())){
							

							if(perHumanInjury<nullToInt(prpcpitemkind.getPerHumanInjury()))
							{
								perHumanInjury = nullToInt(prpcpitemkind.getPerHumanInjury());
							}
							
							if(accentDeath< nullToInt(prpcpitemkind.getPerAccidentDeaths()))
							{
								accentDeath= nullToInt(prpcpitemkind.getPerAccidentDeaths());
							}
							
							if(accentDamage<nullToInt(prpcpitemkind.getPerAccidentDamage()))
							{
								accentDamage = nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
							
							if(totalAmountCoeffi<nullToInt(prpcpitemkind.getAmount()))
							{
								totalAmountCoeffi = nullToInt(prpcpitemkind.getAmount());
							}
						}	
						if("AR".equals(prpCPmain.getRiskCode())){
							
							if(tolAmount<nullToInt(prpcpitemkind.getAmount()))
							{
								tolAmount = nullToInt(prpcpitemkind.getAmount());
							}
						}	
						if("BB".equals(prpCPmain.getRiskCode()) || "BC".equals(prpCPmain.getRiskCode())){
							
							if(prpcpitemkind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpcpitemkind.getAmount().intValue();
							}
						}
						if("BL".equals(prpCPmain.getRiskCode()) || "SB".equals(prpCPmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
						}
						if("BR".equals(prpCPmain.getRiskCode()) || "GS".equals(prpCPmain.getRiskCode())){
							
							if(tolAmount<nullToInt(prpcpitemkind.getAmount()))
							{
								tolAmount = nullToInt(prpcpitemkind.getAmount());
							}
						}
						if("CC".equals(prpCPmain.getRiskCode())){
							
							if(prpcpitemkind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpcpitemkind.getAmount().intValue();
							}
						}
						if("CB".equals(prpCPmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
						}

						if("DI".equals(prpCPmain.getRiskCode()) || "DO".equals(prpCPmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
						}
						if("DS".equals(prpCPmain.getRiskCode())){
							
							if(prpcpitemkind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpcpitemkind.getAmount().intValue();
							}
						}
						if("FC".equals(prpCPmain.getRiskCode())){
							
							if(prpcpitemkind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpcpitemkind.getAmount().intValue();
							}
						}
						if("TE".equals(prpCPmain.getRiskCode())){
							
							if(prpcpitemkind.getAmount().intValue()>contractAmount)
							{
								contractAmount = prpcpitemkind.getAmount().intValue();
							}
						}
						if("MF".equals(prpCPmain.getRiskCode()) || "MP".equals(prpCPmain.getRiskCode()) || "TL".equals(prpCPmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
						}
						if("PC".equals(prpCPmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
						}
						if("PM".equals(prpCPmain.getRiskCode()) || "TP".equals(prpCPmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
						}
						if("SC".equals(prpCPmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
						}
						if("SP".equals(prpCPmain.getRiskCode())){
							
							if(tolAmount<nullToInt(prpcpitemkind.getAmount()))
							{
								tolAmount = nullToInt(prpcpitemkind.getAmount());
							}
						}
						if("ST".equals(prpCPmain.getRiskCode())){
							
							if(deathDamage<(nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage())))
							{
								deathDamage = nullToInt(prpcpitemkind.getPerAccidentDeaths()) + nullToInt(prpcpitemkind.getPerAccidentDamage());
							}
						}
						if("TC".equals(prpCPmain.getRiskCode()) || "TD".equals(prpCPmain.getRiskCode())){
							
							if(tolAmount<nullToInt(prpcpitemkind.getAmount()))
							{
								tolAmount = nullToInt(prpcpitemkind.getAmount());
							}
						}
					}
					
					flexiAmount.put("AGG", totalAmountCoeffi/10000.0000);//保险期间内累计保险金额AGG
					flexiAmount.put("AOA", deathDamage/10000.0000);//每一意外事故体伤及财损AOA
					flexiAmount.put("BD", accentDeath/10000.0000);	//每一意外事故体伤或死亡BD
					flexiAmount.put("PD", accentDamage/10000.0000);//每一意外事故财产损失PD
					flexiAmount.put("AOP", perHumanInjury/10000.0000);//每一个人体伤或死亡AOP
					flexiAmount.put("YGJD", golfAmount/10000.0000);//一杆进洞 
					flexiAmount.put("DXZG", contractAmount/10000.0000);//单项最高
					flexiAmount.put("tolAmount", tolAmount/10000.0000);//总保险金额
				}
				
				
//				if (prpPhead.getPrpPitemKinds().size() > 0)
//				{
//					for (int i = 0; i < prpPhead.getPrpPitemKinds().size(); i++)
//					{
//						PrpPitemKind prpPitemKind =  prpPhead.getPrpPitemKinds().get(i);
////						if(totalAmountCoeffi<nullToInt(prpPitemKind.getTotalAmountCoeffi()))
////						{
////							totalAmountCoeffi = nullToInt(prpPitemKind.getTotalAmountCoeffi());
////						}
////						if("TL".equals(prpPmain.getRiskCode())){
////							if(deathDamage<nullToInt(prpPitemKind.getPerHumanInjury())&&"TL".equals(prpPitemKind.getKindCode()))
////							{
////								deathDamage = nullToInt(prpPitemKind.getPerHumanInjury());
////							}
////						}else{
////							if(deathDamage<(nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage())))
////							{
////								deathDamage = nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage());
////							}
////						}
////						if(accentDeath< nullToInt(prpPitemKind.getPerAccidentDeaths()))
////						{
////							accentDeath= nullToInt(prpPitemKind.getPerAccidentDeaths());
////						}
////						if(accentDamage<nullToInt(prpPitemKind.getPerAccidentDamage()))
////						{
////							accentDamage = nullToInt(prpPitemKind.getPerAccidentDamage());
////						}
////						if(perHumanInjury<nullToInt(prpPitemKind.getPerHumanInjury()))
////						{
////							perHumanInjury = nullToInt(prpPitemKind.getPerHumanInjury());
////						}
////						if(golfAmount<nullToInt(prpPitemKind.getAmount()))
////						{
////							golfAmount = nullToInt(prpPitemKind.getAmount());
////						}
//						
//						if("AB".equals(prpPmain.getRiskCode()) || "BN".equals(prpPmain.getRiskCode())){
//							
//							if(totalAmountCoeffi<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								totalAmountCoeffi = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}
//						
//						if("AE".equals(prpPmain.getRiskCode())){
//							
//							if(totalAmountCoeffi<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								totalAmountCoeffi = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}
//						
//						if("CN".equals(prpPmain.getRiskCode())){
//						
//							if(perHumanInjury<nullToInt(prpPitemKind.getPerHumanInjury()))
//							{
//								perHumanInjury = nullToInt(prpPitemKind.getPerHumanInjury());
//							}
//							
//							if(accentDeath< nullToInt(prpPitemKind.getPerAccidentDeaths()))
//							{
//								accentDeath= nullToInt(prpPitemKind.getPerAccidentDeaths());
//							}
//							
//							if(accentDamage<nullToInt(prpPitemKind.getPerAccidentDamage()))
//							{
//								accentDamage = nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//							
//							if(totalAmountCoeffi<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								totalAmountCoeffi = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						
//						}	
//						
//						if("EM".equals(prpPmain.getRiskCode())){
//						
//							if(perHumanInjury<nullToInt(prpPitemKind.getPerHumanInjury()))
//							{
//								perHumanInjury = nullToInt(prpPitemKind.getPerHumanInjury());
//							}
//							
//							if(deathDamage<(nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage())))
//							{
//								deathDamage = nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//							
//							if(totalAmountCoeffi<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								totalAmountCoeffi = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						
//						}
//						if("FD".equals(prpPmain.getRiskCode())){
//						
//							if(deathDamage<(nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage())))
//							{
//								deathDamage = nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//						
//						}
//						if("GF".equals(prpPmain.getRiskCode())){//待更正，保额为一杆进洞
//							
//							if(golfAmount<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//								{
//									golfAmount = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//								}
//						
//						}
//						if("LF".equals(prpPmain.getRiskCode())){
//							
//							if(perHumanInjury<nullToInt(prpPitemKind.getPerHumanInjury()))
//							{
//								perHumanInjury = nullToInt(prpPitemKind.getPerHumanInjury());
//							}
//							
//							if(accentDeath< nullToInt(prpPitemKind.getPerAccidentDeaths()))
//							{
//								accentDeath= nullToInt(prpPitemKind.getPerAccidentDeaths());
//							}
//							
//							if(accentDamage<nullToInt(prpPitemKind.getPerAccidentDamage()))
//							{
//								accentDamage = nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//							
//							if(totalAmountCoeffi<nullToInt(prpPitemKind.getTotalAmountCoeffi()))
//							{
//								totalAmountCoeffi = nullToInt(prpPitemKind.getTotalAmountCoeffi());
//							}
//						}
//						if("MN".equals(prpPmain.getRiskCode())){
//							
//							if(contractAmount<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								contractAmount = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}	
//						if("PB".equals(prpPmain.getRiskCode())){
//							
//							if(perHumanInjury<nullToInt(prpPitemKind.getPerHumanInjury()))
//							{
//								perHumanInjury = nullToInt(prpPitemKind.getPerHumanInjury());
//							}
//							
//							if(accentDeath< nullToInt(prpPitemKind.getPerAccidentDeaths()))
//							{
//								accentDeath= nullToInt(prpPitemKind.getPerAccidentDeaths());
//							}
//							
//							if(accentDamage<nullToInt(prpPitemKind.getPerAccidentDamage()))
//							{
//								accentDamage = nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//							
//							if(totalAmountCoeffi<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								totalAmountCoeffi = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}	
//						if("PR".equals(prpPmain.getRiskCode())){
//							
//
//							if(perHumanInjury<nullToInt(prpPitemKind.getPerHumanInjury()))
//							{
//								perHumanInjury = nullToInt(prpPitemKind.getPerHumanInjury());
//							}
//							
//							if(accentDeath< nullToInt(prpPitemKind.getPerAccidentDeaths()))
//							{
//								accentDeath= nullToInt(prpPitemKind.getPerAccidentDeaths());
//							}
//							
//							if(accentDamage<nullToInt(prpPitemKind.getPerAccidentDamage()))
//							{
//								accentDamage = nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//							
//							if(totalAmountCoeffi<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								totalAmountCoeffi = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}	
//						if("AR".equals(prpPmain.getRiskCode())){
//							
//
//							if(contractAmount<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								contractAmount = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}	
//						if("BB".equals(prpPmain.getRiskCode()) || "BC".equals(prpPmain.getRiskCode())){
//							
//
//							if(contractAmount<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								contractAmount = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}
//						if("BL".equals(prpPmain.getRiskCode()) || "SB".equals(prpPmain.getRiskCode())){
//							
//							if(deathDamage<(nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage())))
//							{
//								deathDamage = nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//						}
//						if("BR".equals(prpPmain.getRiskCode()) || "GS".equals(prpPmain.getRiskCode())){
//							
//							if(tolAmount<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								tolAmount = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}
//						if("CC".equals(prpPmain.getRiskCode())){
//							
//
//							if(contractAmount<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								contractAmount = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}
//						if("CB".equals(prpPmain.getRiskCode())){
//							
//							if(deathDamage<(nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage())))
//							{
//								deathDamage = nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//						}
//
//						if("DI".equals(prpPmain.getRiskCode()) || "DO".equals(prpPmain.getRiskCode())){
//							
//							if(deathDamage<(nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage())))
//							{
//								deathDamage = nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//						}
//						if("DS".equals(prpPmain.getRiskCode())){
//							
//
//							if(contractAmount<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								contractAmount = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}
//						if("FC".equals(prpPmain.getRiskCode())){
//							
//
//							if(contractAmount<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								contractAmount = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}
//						if("TE".equals(prpPmain.getRiskCode())){
//							
//
//							if(contractAmount<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								contractAmount = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}
//						if("MF".equals(prpPmain.getRiskCode()) || "MP".equals(prpPmain.getRiskCode()) || "TL".equals(prpPmain.getRiskCode())){
//							
//							if(deathDamage<(nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage())))
//							{
//								deathDamage = nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//						}
//						if("PC".equals(prpPmain.getRiskCode())){
//							
//							if(deathDamage<(nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage())))
//							{
//								deathDamage = nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//						}
//						if("PM".equals(prpPmain.getRiskCode()) || "TP".equals(prpPmain.getRiskCode())){
//							
//							if(deathDamage<(nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage())))
//							{
//								deathDamage = nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//						}
//						if("SC".equals(prpPmain.getRiskCode())){
//							
//							if(deathDamage<(nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage())))
//							{
//								deathDamage = nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//						}
//						if("SP".equals(prpPmain.getRiskCode())){
//							
//							if(tolAmount<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								tolAmount = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}
//						if("ST".equals(prpPmain.getRiskCode())){
//							
//							if(deathDamage<(nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage())))
//							{
//								deathDamage = nullToInt(prpPitemKind.getPerAccidentDeaths()) + nullToInt(prpPitemKind.getPerAccidentDamage());
//							}
//						}
//						if("TC".equals(prpPmain.getRiskCode()) || "TD".equals(prpPmain.getRiskCode())){
//							
//							if(tolAmount<nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount())))
//							{
//								tolAmount = nullToInt(prpPitemKind.getAmount().add(prpPitemKind.getChgAmount()));
//							}
//						}
//					}
//					flexiAmount.put("AGG", totalAmountCoeffi/10000.0000);//保险期间内累计保险金额AGG
//					flexiAmount.put("AOA", deathDamage/10000.0000);//每一意外事故体伤及财损AOA
//					flexiAmount.put("BD", accentDeath/10000.0000);	//每一意外事故体伤或死亡BD
//					flexiAmount.put("PD", accentDamage/10000.0000);//每一意外事故财产损失PD
//					flexiAmount.put("AOP", perHumanInjury/10000.0000);//每一个人体伤或死亡AOP
//					flexiAmount.put("YGJD", golfAmount/10000.0000);//一杆进洞 
//					flexiAmount.put("DXZG", contractAmount/10000.0000);//单项最高
//					flexiAmount.put("tolAmount", tolAmount/10000.0000);//总保险金额
//				}
				// add by wangcan2015/12/15 增加责任险核保规则 end
				List<PrpPcommissionDetail> pcommissionDetailList = prpPhead.getPrpPcommissionDetails();
				for(int i=0;i<pcommissionDetailList.size();i++)
				{
					PrpPcommissionDetail prpPcommissionDetail = pcommissionDetailList.get(i);
				if(costRate<=nullToInt(prpPcommissionDetail.getCostRate()))
				{
					costRate=nullToInt(prpPcommissionDetail.getCostRate());
				}
				}
				String projectCode = prpCPmain.getProjectCode();
				//专案不检测佣金比例
				if(projectCode!=null && !"".equals(projectCode))
				{
					businessData.setCostRate(-1);
				}
				else
				{
					businessData.setCostRate(costRate);
				}
				businessData.setExtendPeriod(UtilTools.isNumeric(prpCPmainLiab.getExtendReportPeriod()) ? Integer.valueOf(prpCPmainLiab.getExtendReportPeriod()) : 0);
				businessData.setFlexiableAmount(flexiAmount);
				businessData.setChgPremium(prpPmain.getChgPremium().doubleValue());
				businessData.setProjectCode(prpPmain.getProjectCode());
				Object object= super.findBySql("select approvalNo from prpPmain  where endorseNo = ?", prpPmain.getEndorseNo()).get(0) ;
				if(object != null){
					businessData.setApprovalNo(object.toString());
				}
				
			}
			else if("E".equals(prpCPmain.getClassCode()))
			{
				List<PrpPcommissionDetail> pcommissionDetailList = prpPhead.getPrpPcommissionDetails();
/*				PrpCPmainLiab prpCPmainLiab = prpCPmain.getPrpCPmainLiabs().get(0);*/	
				PrpCPmainLiab prpCPmainLiab=new PrpCPmainLiab();
				if(prpCPmain.getPrpCPmainLiabs().size()>0){
					prpCPmainLiab = prpCPmain.getPrpCPmainLiabs().get(0);
				}
				businessData.setAmount(prpCPmain.getSumAmount().doubleValue()/1000000);
				for(int i=0;i<pcommissionDetailList.size();i++)
				{
					PrpPcommissionDetail prpPcommissionDetail = pcommissionDetailList.get(i);
					if(null!=prpPcommissionDetail.getCostRate() && costRate<=prpPcommissionDetail.getCostRate().doubleValue())
					{
						costRate=prpPcommissionDetail.getCostRate().doubleValue();
					}
				}
				String projectCode = prpCPmain.getProjectCode();
				//专案不检测佣金比例
				if(projectCode!=null && !"".equals(projectCode))
				{
					businessData.setCostRate(-1);
				}
				else
				{
					businessData.setCostRate(costRate);
				}
				if(prpCPmain.getPrpCPmainConstructs().size()>0 && prpCPmain.getPrpCPmainConstructs().get(0)!=null)
				{
					businessData.setConstructType(prpCPmain.getPrpCPmainConstructs().get(0).getConstructType()==null?"":prpCPmain.getPrpCPmainConstructs().get(0).getConstructType());
				}
				Date endDte = prpCPmain.getEndDate();
				if(null!=endDte)
				{
					daysMinus = daysBetween(startDate,endDte);
				}
				businessData.setInsurePeriod(daysMinus/365);
				businessData.setExtendPeriod(UtilTools.isNumeric(prpCPmainLiab.getExtendReportPeriod()) ? Integer.valueOf(prpCPmainLiab.getExtendReportPeriod()) : 0);
				if(prpPmain.getChgPremium().doubleValue()<0){
					businessData.setChgPremium(Math.abs(prpPmain.getChgPremium().doubleValue()));
				}
			}
			else if("M".equals(prpCPmain.getClassCode()))
			{
				businessData.setAmount(prpCPmain.getSumAmount().doubleValue()/10000);
				List<PrpPcommissionDetail> pcommissionDetailList = prpPhead.getPrpPcommissionDetails();
				for(int i=0;i<pcommissionDetailList.size();i++)
				{
					PrpPcommissionDetail prpPcommissionDetail = pcommissionDetailList.get(i);
					if(costRate<=nullToInt(prpPcommissionDetail.getCostRate()))
					{
						costRate=nullToInt(prpPcommissionDetail.getCostRate());
					}
				}
				businessData.setCostRate(costRate);
			}
			else
			{
				if (prpCPmain.getPrpCPitemKinds().size() > 0)
				{
					for (int i = 0; i < prpCPmain.getPrpCPitemKinds().size(); i++)
					{
						PrpCPitemKind prpCpitemKind = prpCPmain.getPrpCPitemKinds().get(i);
						PrpCPcommissionDetail prpCpcommissionDetail = prpCPmain.getPrpCPcommissionDetails().get(i);
						UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
						kind.setKindCode(prpCpitemKind.getKindCode());
						kind.setAmount(prpCpitemKind.getAmount().doubleValue()/1000000);
						if(null!=prpCpcommissionDetail.getCostRate())
						{
						 kind.setKindCostRate(prpCpcommissionDetail.getCostRate().doubleValue());
						}
						businessData.addRiskKind(kind.getKindCode(), kind);
					}
				}
			}
		}
		return businessData;
	}
	/**
	 * 
	 * @description:TODO
	 * @param businessNo
	 * @param businessType
	 * @return
	 * @throws Exception
	 * @author wangJun 住火报价单自动核保规则获取业务数据 20150309
	 */
	public BusinessProposalData getLiveFireBusinessData(String businessNo, String businessType) throws Exception 
	{
		BusinessProposalData businessReportData = new BusinessProposalData();
		businessReportData.setBusinessType(businessType);
		PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
		List<PrpQmainProp> prpQmainProps = prpQmain.getPrpQmainProps();
		businessReportData.setAmount(prpQmain.getSumAmount().doubleValue()/10000);
		businessReportData.setConstructType(prpQmainProps.get(0).getStructure());
		//批次号码借用businessNature字段 20150309
		businessReportData.setBusinessNature(prpQmain.getBatchNO());
		//是否存在理賠記錄查詢
		List<PrpQaddress> prpQaddressList = prpQmain.getPrpQaddresses();
		String addressDetailInfo = "";
		boolean isHaveClaim = false;
		if(prpQaddressList!=null && !prpQaddressList.isEmpty()){
			for(PrpQaddress prpQaddress:prpQaddressList){
				addressDetailInfo = prpQaddress.getAddressDetailInfo();
				isHaveClaim = checkClaimRecord(addressDetailInfo,prpQmain.getRiskCode());
			    if(isHaveClaim){
			    	break;
			    }
			}
		}
		businessReportData.setHaveClaim(isHaveClaim);
		return businessReportData;
		
	}
	
	/**
	 * 
	 * @description:TODO
	 * @param businessNo
	 * @param businessType
	 * @return
	 * @throws Exception
	 * @author yjm 住火要保书自动核保规则获取业务数据 20150922
	 */
	public BusinessProposalData getTLiveFireBusinessData(String businessNo, String businessType) throws Exception 
	{
		BusinessProposalData businessReportData = new BusinessProposalData();
		businessReportData.setBusinessType(businessType);
		PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
		List<PrpTmainProp> prpTmainProps = prpTmain.getPrpTmainProps();
		businessReportData.setAmount(prpTmain.getSumAmount().doubleValue()/10000);
		businessReportData.setConstructType(prpTmainProps.get(0).getStructure());
		//批次号码借用businessNature字段 20150309
		businessReportData.setBusinessNature(prpTmain.getBatchNO());
		//是否存在理賠記錄查詢
		List<PrpTaddress> prpTaddressList = prpTmain.getPrpTaddresses();
		String addressDetailInfo = "";
		boolean isHaveClaim = false;
		if(prpTaddressList!=null && !prpTaddressList.isEmpty()){
			for(PrpTaddress prpTaddress:prpTaddressList){
				addressDetailInfo = prpTaddress.getAddressDetailInfo();
				isHaveClaim = checkClaimRecord(addressDetailInfo,prpTmain.getRiskCode());
			    if(isHaveClaim){
			    	break;
			    }
			}
		}
		businessReportData.setHaveClaim(isHaveClaim);
		
		return businessReportData;
		
	}
	
	public boolean checkClaimRecord(String addressDetailInfo,String riskCode){
		boolean isHaveClaim = false;//是否有理賠記錄
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT 0 from prplclaim,prpcmain where prplclaim.policyno = prpcmain.policyno" +
		        " AND EXISTS (select 0 from PrpCaddress where  PrpCaddress.policyno=prpcmain.policyno AND PrpCaddress.addressDetailInfo=?" +
		        " AND ((prpcmain.startdate > add_months(SYSDATE, -24) AND prpcmain.startdate <=SYSDATE) OR (prpcmain.enddate > add_months(SYSDATE, -24) AND prpcmain.enddate <=SYSDATE)))"+
				" AND prpcmain.riskcode = ? AND prplclaim.canceldate IS NULL" +
				" AND prplclaim.dealercode IS NULL AND prplclaim.damagestartdate > add_months(SYSDATE, -24)" +
				" AND (prplclaim.damagestartdate > prpcmain.startdate OR  (prplclaim.damagestartdate = prpcmain.startdate AND to_number(substr(prplclaim.damagestarthour, 0, 2)) > prpcmain.starthour))" +
				" AND (prplclaim.damagestartdate < prpcmain.enddate OR (prplclaim.damagestartdate = prpcmain.enddate AND to_number(substr(prplclaim.damagestarthour, 0, 2)) < prpcmain.endhour))" +
				" AND NOT EXISTS (SELECT 0 FROM prpphead WHERE prpphead.policyno = prplclaim.policyno AND (prplclaim.damagestartdate > prpphead.validdate OR (prplclaim.damagestartdate = prpphead.validdate AND to_number(substr(prplclaim.damagestarthour,0,2)) > prpphead.validhour )))");
		List<Object>  records = super.findBySql(sql.toString(),addressDetailInfo,riskCode);
		if(records!=null && !records.isEmpty()){
			isHaveClaim = true;
		}
		return isHaveClaim;
	}
	
	/**
	 * 車險要保書自動核保規則獲取業務數據
	 * @param businessNo
	 * @param businessType
	 * @return
	 * @throws Exception
	 */
	public BusinessProposalData getProposalCarAutoBusinessData(String businessNo, String businessType) throws Exception {
		BusinessProposalData businessProposalData = new BusinessProposalData();
		try {
			if ("T".equals(businessType)) {
				PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
				String underWriteFlag = prpTmain.getUnderWriteFlag();
				//9为审核通过的规则，非9为提交核保的规则
				if(!"9".equals(underWriteFlag)) {
				    PrpTitemCar prpTitemCar = prpTmain.getPrpTitemCars().get(0);
				    //businessProposalData.setCarKind(prpTitemCar.getCarKindCode());
				    UwBlackList uwBlackList = new UwBlackList();
				    UwBlackListId id = new UwBlackListId();
				    boolean flow=true;
				    String modelCode = prpTitemCar.getModelCode();
				    QueryRule queryRule = QueryRule.getInstance();
				    List list;
					if(flow) {
						queryRule.addEqual("id.blackListType", "5");
						queryRule.addEqual("id.blackListCode", prpTitemCar.getCarKindCode());
						list = uwBlackListService.getUwBlackList(queryRule);
						if(list.size()>0) {
							businessProposalData.setLimitedCarKind(true);
							flow = false;
						}
					}
					if(flow) {
						queryRule = QueryRule.getInstance();
						queryRule.addEqual("id.blackListType", "4");
						queryRule.addEqual("id.blackListCode", modelCode);
						list = uwBlackListService.getUwBlackList(queryRule);
						if(list.size()>0) {
							businessProposalData.setLimitedModelCode(true);
							flow = false;
						} else{
							queryRule = QueryRule.getInstance();
							queryRule.addEqual("id.blackListType", "4");
							queryRule.addEqual("flag", "*");
							list = uwBlackListService.getUwBlackList(queryRule);
							for(int i=0;i<list.size();i++) {
								uwBlackList=(UwBlackList) list.get(i);
								if(modelCode.startsWith(uwBlackList.getId().getBlackListCode())) {
									businessProposalData.setLimitedModelCode(true);
									flow = false;
									break;
								}
							}
						}
					}
					if(flow) {
						queryRule = QueryRule.getInstance();
						String licenseNo= prpTitemCar.getLicenseNo();
						queryRule.addEqual("id.blackListType", "2");
						queryRule.addEqual("id.blackListCode", licenseNo);
						list = uwBlackListService.getUwBlackList(queryRule);
						if(list.size()>0) {
							businessProposalData.setLimitedLicenseNo(true);
							flow = false;
						} else {
							queryRule = QueryRule.getInstance();
							queryRule.addEqual("id.blackListType", "2");
							queryRule.addEqual("flag", "*");
							list = uwBlackListService.getUwBlackList(queryRule);
							for(int i=0;i<list.size();i++) {
								uwBlackList=(UwBlackList) list.get(i);
								if(null!=licenseNo && licenseNo.startsWith(uwBlackList.getId().getBlackListCode())) {
									businessProposalData.setLimitedLicenseNo(true);
									flow = false;
									break;
								}
							}
						}
					}
					if(flow) {
						queryRule = QueryRule.getInstance();
						String identifyCard="";
						for(int i=0;i<prpTmain.getPrpTinsureds().size();i++) {
							if(prpTmain.getPrpTinsureds().get(i).getInsuredFlag().equals("1")) {
								identifyCard= prpTmain.getPrpTinsureds().get(i).getIdentifyNumber();
								break;
							}
						}
						queryRule.addEqual("id.blackListType", "1");
						queryRule.addEqual("id.blackListCode", identifyCard);
						list = uwBlackListService.getUwBlackList(queryRule);
						if(list.size()>0) {
							businessProposalData.setLimitedIdentification(true);
							flow = false;
						} else {
							queryRule = QueryRule.getInstance();
							queryRule.addEqual("id.blackListType", "1");
							queryRule.addEqual("flag", "*");
							for(int i=0;i<list.size();i++) {
								uwBlackList=(UwBlackList) list.get(i);
								if(identifyCard.startsWith(uwBlackList.getId().getBlackListCode())) {
									businessProposalData.setLimitedIdentification(true);
									flow = false;
									break;
								}
							}
						}
					}
					if(flow) {
						queryRule = QueryRule.getInstance();
						String engineNo= prpTitemCar.getEngineNo();
						queryRule.addEqual("id.blackListType", "3");
						queryRule.addEqual("id.blackListCode", engineNo);
						list = uwBlackListService.getUwBlackList(queryRule);
						if(list.size()>0) {
							businessProposalData.setLimitedEngineNo(true);
							flow = false;
						} else {
							queryRule = QueryRule.getInstance();
							queryRule.addEqual("id.blackListType", "3");
							queryRule.addEqual("flag", "*");
							for(int i=0;i<list.size();i++) {
								uwBlackList=(UwBlackList) list.get(i);
								if(engineNo.startsWith(uwBlackList.getId().getBlackListCode())) {
									businessProposalData.setLimitedEngineNo(true);
									flow = false;
									break;
								}
							}
						}
					}
				PrpTitemCarExt prpTitemCarExt=new PrpTitemCarExt();
				prpTitemCarExt= prpTmain.getPrpTitemCarExts().get(0);
				businessProposalData.setRiskCode(prpTmain.getRiskCode());
//				if("A01".equals(prpTmain.getRiskCode())) {
//					if(prpTitemCarExt.getFloatRateA()!=null) {
//						businessProposalData.setFloatRateA(prpTitemCarExt.getFloatRateA().doubleValue());
//					}
//					if(prpTitemCarExt.getFloatRateG()!=null) {
//						businessProposalData.setFloatRateG(prpTitemCarExt.getFloatRateG().doubleValue());
//					}
//				}
//				if("B01".equals(prpTmain.getRiskCode())) {
//				    //強制險沒有floatRateA,上線緊急,先寫為0
//				    businessProposalData.setFloatRateA(0);
//				    businessProposalData.setFloatRateG(prpTitemCarExt.getFloatRateG().doubleValue());
//				}
				businessProposalData.setUseYears(prpTitemCar.getUseYears().doubleValue());
				String hql="from PrpTitemKind where proposalNo= '" + businessNo + "'";
				List prpTitemKindList=this.findByHql(hql);
				if(prpTitemKindList.size() > 0) {
					for(int i = 0; i < prpTitemKindList.size(); i++) {
					    UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
					    PrpTitemKind prpTitemKind =(PrpTitemKind) prpTitemKindList.get(i);
					    kind.setKindCode(prpTitemKind.getKindCode());
					    kind.setAmount(prpTitemKind.getAmount().doubleValue());
					    businessProposalData.addRiskKind(kind.getKindCode(), kind);
				    }	
				}
			} else {
				String hql="from PrpTitemKind where proposalNo= '" + businessNo + "'";
				businessProposalData.setClassCode(prpTmain.getClassCode());
				businessProposalData.setRiskCode(prpTmain.getRiskCode());
				businessProposalData.setComCode(prpTmain.getComCode());
				List prpTitemKindList=this.findByHql(hql);
				//mantis： CAR0233，處理人員：Sam，需求單編號：CAR0233，變更31,36,50,51,52,53,54險種核保檢核保額卡控問題
				String multipleKindCode = getGroupPrptItemKind("T",businessNo);
				if (prpTitemKindList.size() > 0) {
					for (int i = 0; i < prpTitemKindList.size(); i++) {
						PrpTitemKind prpTitemKind = (PrpTitemKind) prpTitemKindList.get(i);
						//mantis： CAR0233，處理人員：Sam，需求單編號：CAR0233，變更31,36,50,51,52,53,54險種核保檢核保額卡控問題
						if(multipleKindCode.contains(","+prpTitemKind.getKindCode()+",")){
							for(int j = 0; j < prpTmain.getPrpTlimits().size(); j++){
								PrpTlimit prpTlimit = prpTmain.getPrpTlimits().get(j);
								if(prpTitemKind.getId().getItemKindNo().toString().equals(prpTlimit.getId().getLimitNo().toString())){
									UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
									kind.setKindCode(prpTitemKind.getKindCode() + prpTlimit.getId().getLimitType());
									kind.setAmount(prpTlimit.getLimitFee().doubleValue());
									businessProposalData.addRiskKind(kind.getKindCode(), kind);
								}
							}
						} else {
							UndwrtRuleRiskKind kind = new UndwrtRuleRiskKind();
							kind.setKindCode(prpTitemKind.getKindCode());
							kind.setAmount(prpTitemKind.getAmount().doubleValue());
							businessProposalData.addRiskKind(kind.getKindCode(), kind);
						}
					}
				}			
//				if(prpTmain.getPrpTitemCars().size()>0 && prpTmain.getPrpTitemCars().get(0) != null){
//					PrpTitemCar prpTitemCar = prpTmain.getPrpTitemCars().get(0);
//					businessProposalData.setCarKind(prpTitemCar.getCarKindCode());
//				}
			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return businessProposalData;
	
	}
	/**   
     * 計算兩個日期之前的天數差
     * @param smdate 日期 參數 
     * @param bdate  日期 參數
     * @return 相差的天數  
     * @throws Exception   
     */     
    public static int daysBetween(Date smdate,Date udate) throws Exception     
    {     
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
        smdate=sdf.parse(sdf.format(smdate));   
        udate=sdf.parse(sdf.format(udate));   
        Calendar cal = Calendar.getInstance();     
        cal.setTime(smdate);     
        long time1 = cal.getTimeInMillis();                  
        cal.setTime(udate);     
        long time2 = cal.getTimeInMillis();
        long timeBetween = time2-time1;
        long between_days=(timeBetween)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));    
        
    } 
    
    public int nullToInt(BigDecimal b)
    {
    	if(b==null)
    	{
    		return 0;
    	}
    	return b.intValue();
    }

	/**
	 * 獲取屬性要保書處理接口.
	 * 
	 * @return 屬性要保書處理接口的值
	 */
	public PolicyService getPolicyService() {
		return policyService;
	}

	/**
	 * 設置屬性要保書處理接口.
	 * 
	 * @param policyService
	 *            待設置的要保書處理接口的值
	 */
	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	/**
	 * 獲取屬性要保書訊息接口.
	 * 
	 * @return 屬性要保書訊息接口的值
	 */
	public PrpCpMainService getPrpCpMainService() {
		return prpCpMainService;
	}

	/**
	 * 設置屬性要保書訊息接口.
	 * 
	 * @param prpCpMainService
	 *            待設置的要保書訊息接口的值
	 */
	public void setPrpCpMainService(PrpCpMainService prpCpMainService) {
		this.prpCpMainService = prpCpMainService;
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

	public UwBlackListService getUwBlackListService() {
		return uwBlackListService;
	}

	public void setUwBlackListService(UwBlackListService uwBlackListService) {
		this.uwBlackListService = uwBlackListService;
	}
	/**
	 *  ADD  BY  MOUJIAXING START  20151212  增加校验  
	 *  
	 */
	@Override
	public String checkUndwrtRules(String businessNo, String businessType) {
		// TODO Auto-generated method stub
		//aad by lidongdong 20160317 reason:增加保额累积校验 begin
		if("T".equals(businessType)){
			String policyNo = null;
			PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			int dutyLevel= 1;//職業等級
			long insuredAge= 0L;//被保險人年齡
			int insuredSerialNo=0;//被保險人序號
			int familyNo =  0;//條款所屬被保險人序號
			if("TA".equals(prpTmain.getRiskCode())||"PA".equals(prpTmain.getRiskCode())||"GA".equals(prpTmain.getRiskCode())){
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				List<PrpTinsured> prpTinsureds2 = prpTmain.getPrpTinsureds();
			    List<PrpTitemKind> prpTitemKinds2 = prpTmain.getPrpTitemKinds();
			    for(PrpTinsured prpTinsured : prpTinsureds2){
			    	//關係人類型（1：被保險人）
				    String insuredFlag = prpTinsured.getInsuredFlag();
				    if("1".equals(insuredFlag)){//被保險人
						String insuredName=prpTinsured.getInsuredName();//被保險人名稱
						insuredSerialNo = prpTinsured.getId().getSerialNo();//被保險人序號
						if (prpTinsured.getPrpTinsuredNatures()!=null && prpTinsured.getPrpTinsuredNatures().size()>0) {
							PrpTinsuredNature insuredNature = prpTinsured.getPrpTinsuredNatures().get(0);
							insuredAge = insuredNature.getAge();
						    //職業等級
						    if(insuredNature.getDutyLevel()==null){
						    	dutyLevel=1;
					    	}else{
					    		dutyLevel=Integer.parseInt(insuredNature.getDutyLevel());
						    }
							for (PrpTitemKind prpTitemKind : prpTitemKinds2) {
								familyNo = prpTitemKind.getFamilyNo()==null?-1:prpTitemKind.getFamilyNo().intValue();
							    if(insuredSerialNo == familyNo){
							    	//條款代號
									String  kindCode=prpTitemKind.getKindCode();
									//查詢該條款限額
									String sql="select amount,remark from prpdriskcondition where kindcode="+"'"+kindCode+"'"+" and "+insuredAge+"<=highage and "+insuredAge+">=lowage and "+dutyLevel+"<=highdutylevel and "+dutyLevel+">=lowdutylevel";
								    List<Object[]> list = super.findBySql(sql);
								    //有限額時，進行校驗
								    if(list.size()>0){
								    	//身份證號
								    	String identifyNumber = prpTinsured.getIdentifyNumber();
								    	//起始日期
										String startDateNew = sdf.format(prpTitemKind.getStartDate());
										//起止日期
										String endDateNew = sdf.format(prpTitemKind.getEndDate());
										//最高限額
								    	BigDecimal limitAmount = BigDecimal.ZERO;
								    	//校驗訊息
								    	String remark="";
								    	Object[] limitInfo = list.get(0);
								    	if(limitInfo[0]!=null){
								    		limitAmount = (BigDecimal)limitInfo[0];//最高限額
								    	}
								    	if(limitInfo[1]!=null){
								    		remark = limitInfo[1].toString();//校驗訊息
								    	}
								    	//查詢該被保險人的當前保險期間內所投保其他保單的總保額
										BigDecimal querySumAmount = this.querySumAmount(identifyNumber, kindCode, startDateNew, endDateNew, policyNo);
										BigDecimal sumamount = querySumAmount.add(prpTitemKind.getAmount());
								    	if(sumamount.compareTo(limitAmount)>0){
								    		remark=remark.replaceAll("AAA",insuredName);
											remark=remark.replaceAll("BBB", sumamount.toString());
											return remark;
								    	}
								    }
									
							    }
							  
							 }
						}
				    }
			    }
			/*if("TA".equals(prpTmain.getRiskCode())||"PA".equals(prpTmain.getRiskCode())){
				List<PrpTinsured> prpTinsureds2 = prpTmain.getPrpTinsureds();
				List<PrpTitemKind> prpTitemKinds2 = prpTmain.getPrpTitemKinds();
				String  areaFlag ="";
				for (int i = 0; i < prpTinsureds2.size(); i++) {
					if("2".equals(prpTinsureds2.get(i).getInsuredFlag())){
						areaFlag = getTravelArea(businessNo,businessType);
				    }
				}
				for (int i = 0; i < prpTinsureds2.size(); i++) {
					    String insuredFlag = prpTinsureds2.get(i).getInsuredFlag();
					    if("1".equals(insuredFlag)){
					    	PrpTinsured prpTinsured = prpTinsureds2.get(i);
					    	  String serialNo = prpTinsured.getId().getSerialNo().toString();
					    	for (int j = 0; j <prpTinsured.getPrpTinsuredNatures().size(); j++) {
					    		if(serialNo.equals(prpTinsured.getPrpTinsuredNatures().get(j).getId().getSerialNo().toString())){
							       if(prpTinsured.getPrpTinsuredNatures().get(j).getAge()< 15){
							    	   for (int k = 0; k < prpTitemKinds2.size(); k++) {
							    		   String familyNo = prpTitemKinds2.get(k).getFamilyNo()+"";
							    		   String serialNoInsured = serialNo+"";
										   if(familyNo!=null&&serialNoInsured!=null&&serialNoInsured.equals(familyNo)){
											   if("TA0A".equals(prpTitemKinds2.get(k).getKindCode())||"TR23".equals(prpTitemKinds2.get(k).getKindCode())||"PA00".equals(prpTitemKinds2.get(k).getKindCode())){
											      String identifyNumber = prpTinsured.getIdentifyNumber();
												  String startDate = prpTitemKinds2.get(k).getStartDate().toString();
												  String endDate = prpTitemKinds2.get(k).getEndDate().toString();
												  String startDateNew = Integer.parseInt(startDate.substring(
												 		0, 4))+ "/"+ startDate.substring(5, 7)+"/"+startDate.substring(8, 10);
												  String endDateNew = Integer.parseInt(endDate.substring(
															0, 4))+ "/"+ endDate.substring(5, 7)+"/"+endDate.substring(8, 10);
													
												 String  kindCode="";
												if("1".equals(areaFlag)){
													kindCode = "TA0A";
												}else if("2".equals(areaFlag)){
													kindCode = "TR23";
												}
												String querySumAmount = querySumAmount(identifyNumber, kindCode, startDateNew, endDateNew, policyNo);
												BigDecimal parseInt2 = new BigDecimal(0);
												if(querySumAmount!=null){
												     parseInt2 = new BigDecimal(querySumAmount);
												}
												if(prpTitemKinds2.get(k).getAmount()!=null&&prpTitemKinds2.get(k).getAmount().add(parseInt2).compareTo(new BigDecimal(2000000))>0){
													return  kindCode+"條款在此保期時間內有效保額不能超過200萬";
												}
											   }
										   }
									   }
							       }
					    		}
							 
							}
					    }    
				}
			}*/
		}
		}else if("E".equals(businessType)){
			PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
			PrpCPmain prpCPmain = prpCpMainService.getPrpCpMainByPolicyNo(prpPhead.getPolicyNo());
			String policyNo = prpCPmain.getPolicyNo();
			int dutyLevel= 1;//職業等級
			long insuredAge= 0L;//被保險人年齡
			int insuredSerialNo=0;//被保險人序號
			int familyNo =  0;//條款所屬被保險人序號
			if("TA".equals(prpCPmain.getRiskCode())||"PA".equals(prpCPmain.getRiskCode())||"GA".equals(prpCPmain.getRiskCode())){
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				List<PrpCPinsured> prpCPinsureds = prpCPmain.getPrpCPinsureds();
			    List<PrpCPitemKind> prpCPitemKinds = prpCPmain.getPrpCPitemKinds();
			    for(PrpCPinsured prpCPinsured : prpCPinsureds){
			    	//關係人類型（1：被保險人）
				    String insuredFlag = prpCPinsured.getInsuredFlag();
				    if("1".equals(insuredFlag)){//被保險人
						String insuredName=prpCPinsured.getInsuredName();//被保險人名稱
						insuredSerialNo = prpCPinsured.getId().getSerialNo();//被保險人序號
						if (prpCPinsured.getPrpCPinsuredNatures()!=null && prpCPinsured.getPrpCPinsuredNatures().size()>0) {
							PrpCPinsuredNature insuredNature = prpCPinsured.getPrpCPinsuredNatures().get(0);
							insuredAge = insuredNature.getAge();
						    //職業等級
						    if(insuredNature.getDutyLevel()==null){
						    	dutyLevel=1;
					    	}else{
					    		dutyLevel=Integer.parseInt(insuredNature.getDutyLevel());
						    }
							for (PrpCPitemKind prpCPitemKind : prpCPitemKinds) {
								familyNo = prpCPitemKind.getFamilyNo()==null?-1:prpCPitemKind.getFamilyNo().intValue();
							    if(insuredSerialNo == familyNo){
							    	//條款代號
									String  kindCode=prpCPitemKind.getKindCode();
									//查詢該條款限額
									String sql="select amount,remark from prpdriskcondition where kindcode="+"'"+kindCode+"'"+" and "+insuredAge+"<=highage and "+insuredAge+">=lowage and "+dutyLevel+"<=highdutylevel and "+dutyLevel+">=lowdutylevel";
								    List<Object[]> list = super.findBySql(sql);
								    //有限額時，進行校驗
								    if(list.size()>0){
								    	//身份證號
								    	String identifyNumber = prpCPinsured.getIdentifyNumber();
								    	//起始日期
										String startDateNew = sdf.format(prpCPitemKind.getStartDate());
										//起止日期
										String endDateNew = sdf.format(prpCPitemKind.getEndDate());
										//最高限額
								    	BigDecimal limitAmount = BigDecimal.ZERO;
								    	//校驗訊息
								    	String remark="";
								    	Object[] limitInfo = list.get(0);
								    	if(limitInfo[0]!=null){
								    		limitAmount = (BigDecimal)limitInfo[0];//最高限額
								    	}
								    	if(limitInfo[1]!=null){
								    		remark = limitInfo[1].toString();//校驗訊息
								    	}
								    	//查詢該被保險人的當前保險期間內所投保其他保單的總保額
										BigDecimal querySumAmount = this.querySumAmount(identifyNumber, kindCode, startDateNew, endDateNew, policyNo);
										BigDecimal sumamount = querySumAmount.add(prpCPitemKind.getAmount());
								    	if(sumamount.compareTo(limitAmount)>0){
								    		remark=remark.replaceAll("AAA",insuredName);
											remark=remark.replaceAll("BBB", sumamount.toString());
											return remark;
								    	}
								    }
									
							    }
							  
							 }
						}
				    }
			    }
			/*if("TA".equals(prpCPmain.getRiskCode())){
				List<PrpCPinsured> prpCPinsureds2 = prpCPmain.getPrpCPinsureds();
				List<PrpCPitemKind> prpCPitemKinds2 = prpCPmain.getPrpCPitemKinds();
				String  areaFlag ="";
				for (int i = 0; i < prpCPinsureds2.size(); i++) {
					if("2".equals(prpCPinsureds2.get(i).getInsuredFlag())){
						areaFlag = getTravelArea(prpPhead.getPolicyNo(),businessType);
				    }
				}
				for (int i = 0; i < prpCPinsureds2.size(); i++) {
					    String insuredFlag = prpCPinsureds2.get(i).getInsuredFlag();
					    if("1".equals(insuredFlag)){
					    	PrpCPinsured prpCPinsured = prpCPinsureds2.get(i);
					    	 String serialNo = prpCPinsured.getId().getSerialNo().toString();
					    	for (int j = 0; j <prpCPinsured.getPrpCPinsuredNatures().size(); j++) {
					    		if(serialNo.equals(prpCPinsured.getPrpCPinsuredNatures().get(j).getId().getSerialNo().toString())){
							       if(prpCPinsured.getPrpCPinsuredNatures().get(j).getAge()< 15){
							    	   for (int k = 0; k < prpCPitemKinds2.size(); k++) {
							    		   String familyNo = prpCPitemKinds2.get(k).getFamilyNo()+"";
							    		   String serialNoInsured = serialNo+"";
										   if(familyNo!=null&&serialNoInsured!=null&&serialNoInsured.equals(familyNo)){
											   if("TA0A".equals(prpCPitemKinds2.get(k).getKindCode())||"TR23".equals(prpCPitemKinds2.get(k).getKindCode())){
											      String identifyNumber = prpCPinsured.getIdentifyNumber();
												  String startDate = prpCPitemKinds2.get(k).getStartDate().toString();
												  String endDate = prpCPitemKinds2.get(k).getEndDate().toString();
												  String startDateNew = Integer.parseInt(startDate.substring(
												 		0, 4))+ "/"+ startDate.substring(5, 7)+"/"+startDate.substring(8, 10);
												  String endDateNew = Integer.parseInt(endDate.substring(
															0, 4))+ "/"+ endDate.substring(5, 7)+"/"+endDate.substring(8, 10);
													
												 String  kindCode="";
												if("1".equals(areaFlag)){
													kindCode = "TA0A";
												}else if("2".equals(areaFlag)){
													kindCode = "TR23";
												}
												String querySumAmount = querySumAmount(identifyNumber, kindCode, startDateNew, endDateNew, policyNo);
												BigDecimal parseInt2 = new BigDecimal(0);
												if(querySumAmount!=null){
												     parseInt2 = new BigDecimal(querySumAmount);
												}
												if(prpCPitemKinds2.get(k).getAmount()!=null&&prpCPitemKinds2.get(k).getAmount().add(parseInt2).compareTo(new BigDecimal(2000000))>0){
													return  kindCode+"條款在此保期時間內有效保額不能超過200萬";
												}
											   }
										   }
									   }
							       }
					    		}
							 
							}
					    }    
				}
			}*/
		    }
		}
		return "";	
	}
	//aad by lidongdong 20160317 reason:增加保额累积校验 end
    private BigDecimal querySumAmount(String identifyNumber, String kindCode,
			String startDateNew, String endDateNew, String policyNo) {
		// TODO Auto-generated method stub
    	String sql = "select sum(t1.amount) from Prpcitemkind t1,prpcinsured t2 " +
				"  where  t1.policyno = t2.policyno  and t1.familyno=t2.serialno "+
				"  and t2.insuredflag='1' and t2.Identifynumber = ? and t1.kindcode= ?"+ 
				"  and (((t1.startdate >= to_date('"+startDateNew+"','yyyy/MM/dd') and t1.startdate <= to_date('"+endDateNew+"','yyyy/MM/dd'))" +
				"  or (t1.enddate >= to_date('"+startDateNew+"','yyyy/MM/dd') and t1.enddate <= to_date('"+endDateNew+"','yyyy/MM/dd'))))";
    	if(policyNo != null){
    		sql += "and t1.policyno <> '"+policyNo+"'";
    	}
		List<BigDecimal> findBySql = this.findBySql(sql,identifyNumber,kindCode);
		BigDecimal sumAmount=BigDecimal.ZERO;
		if(findBySql.size()>0){
			if(findBySql.get(0)!=null){
		        sumAmount = findBySql.get(0);
		    }
		}
		return sumAmount;
	}

	/**
     * 查询地区位置
     * @param businessNo
     * @param businessType
     * @return
     */
	private String getTravelArea(String businessNo, String businessType) {
		// TODO Auto-generated method stub
		 String  travelArea="";
		 String sql="";
		 if("T".equals(businessType)){
			   sql="select  travelArea from  prptinsured where proposalno ='"+businessNo+"' AND  insuredFlag ='2' "; 
		 }else{
			   sql="select  travelArea from  prpCPinsured where policyno ='"+businessNo+"' AND  insuredFlag ='2' "; 
		 }
		     List list = super.findBySql(sql);
		     if(list.size()>0){
		    	 travelArea=(String) list.get(0);
		     }
		return travelArea;
	}

	@Override
	public boolean checkUndwrtRules(String businessNo) {
		// TODO Auto-generated method stub
		PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
		boolean  flag  = true;
        if("C1".equals(prpTmain.getClassCode())){
			List<PrpTinsured> prpTinsureds2 = prpTmain.getPrpTinsureds();
    	    for (int i = 0; i < prpTinsureds2.size(); i++) {
    	    	if("1".equals(prpTinsureds2.get(i).getInsuredFlag())){
			      flag = getUwBlackList(prpTinsureds2.get(i).getIdentifyNumber());
    	    	}
			}
        }
		return flag;
	}

	private boolean getUwBlackList(String identifyNumber) {
		// TODO Auto-generated method stub
		boolean  flag  =  true;
		String  sql = "select  *  From  UwBlackList  where identifyNumber ='"+identifyNumber+"' and  blacklisttype='E' and  CheatMeans is null  ";
		  List list = super.findBySql(sql);
		     if(list.size()>0){
		    	 flag =false;
		     }
		return flag;
	}

	/*
	mantis： CAR0233，處理人員：Sam，需求單編號：CAR0233--- start
	變更31,36,50,51,52,53,54險種核保檢核保額卡控問題
	*/
	private String getGroupPrptItemKind(String type , String proposalNo) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(proposalNo)){
			return null;//要保書號不可空白
		}
		String result = "";
		if("T".equals(type)){//要保書
			String sql = "select KINDCODE from prptitemkind where PROPOSALNO = ? " +
	    			" AND ITEMKINDNO in ( " +
	    			" 	select LIMITNO from prptlimit where PROPOSALNO = ?  " +
	    			"	group by LIMITNO having count(0) > 1 "+
	    			" ) ";
	    	
			List<String> findBySql = this.findBySql(sql,proposalNo,proposalNo);
			if(findBySql.size()>0){
				for(String kineCode : findBySql){
					result += ","+kineCode;
				}
				result += ",";
			}
		}else if("P".equals(type)){//批單
			String sql = "select KINDCODE from prpcpitemkind where policyno = ? " +
	    			" AND ITEMKINDNO in ( " +
	    			" 	select LIMITNO from prpcplimit where policyno = ?  " +
	    			"	group by LIMITNO having count(0) > 1 "+
	    			" ) ";
	    	
			List<String> findBySql = this.findBySql(sql,proposalNo,proposalNo);
			if(findBySql.size()>0){
				for(String kineCode : findBySql){
					result += ","+kineCode;
				}
				result += ",";
			}
		}else{
			result = null;
		}
		return result;
	}
	/* mantis： CAR0233，處理人員：Sam，需求單編號：CAR0233 --- end */
	
	/*
	mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245--- start
	強任費率不一致的的需求
	*/
	public String getUndwrMark(String type , String proposalNo ){
		String result = null;
		if("T".equals(type)){
			String sql = "select undwrtmark from prptmain where PROPOSALNO = ? ";
			List<String> findBySql = this.findBySql(sql,proposalNo);
			if(findBySql.size()>0){
				for(String undwrtmark : findBySql){
					result = undwrtmark;
				}
			}
		}else{
			String sql = "select undwrtmark from prpqmain where PROPOSALNO = ? ";
			List<String> findBySql = this.findBySql(sql,proposalNo);
			if(findBySql.size()>0){
				for(String undwrtmark : findBySql){
					result = undwrtmark;
				}
			}
		}
		return result;
	}
	/* mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245 --- end */
	
	/*
	mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266--- start
	保發輔助平台,擴增欄位--核心變更需求  回寫報價單PRPQMAIN.NOTIFYORNOT ='Y'。
	*/
	public void updateNotifyOrNot(String proposalNo ) throws Exception{
		DBManager dbManager = new DBManager();
		try {
			dbManager.open("undwrtDataSource");
			dbManager.beginTransaction();
			String sql = "UPDATE PRPQMAIN SET NotifyOrNot ='Y' where PROPOSALNO = ? ";
			dbManager.prepareStatement(sql);
			dbManager.setString(1, proposalNo);
			dbManager.executePreparedUpdate();
			dbManager.commitTransaction();
		} catch (Exception e) {
			dbManager.rollbackTransaction();
			e.printStackTrace();
		} finally {
			dbManager.close();
		}
	}
	/* mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266 --- end */
}
