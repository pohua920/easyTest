package com.sinosoft.claim.compensate.util;

import ins.framework.common.DateTime;
//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.utils.StringUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;

import com.sinosoft.app.common.util.StringUtil;
import com.sinosoft.claim.check.service.facade.AcciCheckService;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.util.DAAClaimViewHelper;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpPitemKindService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.vo.ICollections;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.endcase.service.facade.RecaseService;
import com.sinosoft.claim.endcase.vo.ReCaseDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.regist.vo.RegistClaimInfoDto;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.schema.model.PrpCCargoItem;
import com.sinosoft.claim.schema.model.PrpCaddress;
import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCitemShip;
import com.sinosoft.claim.schema.model.PrpCitemShipId;
import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainCarGoSub;
import com.sinosoft.claim.schema.model.PrpCmainCargo;
import com.sinosoft.claim.schema.model.PrpCplane;
import com.sinosoft.claim.schema.model.PrpCplaneId;
import com.sinosoft.claim.schema.model.PrpDlimit;
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLcfee;
import com.sinosoft.claim.schema.model.PrpLcfeecoins;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimCredit;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclause;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLearthquakeFund;
import com.sinosoft.claim.schema.model.PrpLfracture;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLltextModel;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpersonHospital;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.service.facade.PrpCCargoItemService;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredNatureService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCitemShipService;
import com.sinosoft.claim.schema.service.facade.PrpCmainCarGoSubService;
import com.sinosoft.claim.schema.service.facade.PrpCmainCargoService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpCplaneService;
import com.sinosoft.claim.schema.service.facade.PrpDriskRateService;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckService;
import com.sinosoft.claim.schema.service.facade.PrpLcfeecoinsService;
import com.sinosoft.claim.schema.service.facade.PrpLclauseService;
import com.sinosoft.claim.schema.service.facade.PrpLfractureService;
import com.sinosoft.claim.schema.service.facade.PrpLltextModelService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonHospitalService;
//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfNotionService;
import com.sinosoft.claim.ui.control.action.UIExchAction;
import com.sinosoft.claim.ui.control.viewHelper.SendUndwrtViewHelper;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.function.insutil.dto.domain.PrpDexchDto;
import com.sinosoft.reins.common.model.PrpLDangerItem;
import com.sinosoft.sysframework.common.util.MoneyUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.utility.string.Str;

/**
 * @Description 意健险理算数据整理工具类
 * @author 中科软
 */
public class AccidentCompensateViewHelper extends CompensateViewHelper {

	/** 立案服务 */
	private ClaimService claimService;
	/** 联共保赔付金额分摊服务 */
	private PrpLcfeecoinsService prpLcfeecoinsService;
	/** 意键险报案对象服务 */
	private AcciCheckService acciCheckService;
	/** 意健险调查信息服务 */
	private PrpLacciCheckService prpLacciCheckService;
	/** 立案ViewHelper */
	private DAAClaimViewHelper daaClaimViewHelper;
	/** 报案ViewHelper */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 理算实赔服务 */
	private CompensateService compensateService;
	/** 报案服务 */
	private RegistService registService;
	/** 重开赔案信息服务 */
	private RecaseService recaseService;
	/** 代码信息服务 */
	private CodeService codeService;
	/** 理赔节点状态ViewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 标的子险信息服务 */
	private PrpCitemKindService prpCitemKindService;
	/** 共保信息服务 */
	private PrpCcoinsService prpCcoinsService;
	/** 保单基本信息服务 */
	private PrpCmainService prpCmainService;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 工作流意见处理信息服务 */
	private SwfNotionService swfNotionService;
	/** 赔款计算文字列表 */
	List<PrpLctext> prpLctextlist = new ArrayList<PrpLctext>();
	/** 再保管理对象 */
	private ReinsServiceManager reinsServiceManager;
	/** 工作流服务 */
	private SwfLogService swfLogService;
	/** 骨折部位service */
	private PrpLfractureService prpLfractureService;
	/** 就诊医院service */
	private PrpLpersonHospitalService prpLpersonHospitalService;
	private EndorseService endorseService;
	private PrpPitemKindService prpPitemKindService;
	private PrpCinsuredNatureService prpCinsuredNatureService;
	/** 理算说明处理 */
	private PrpLltextModelService prpLltextModelService;
	/**  承保范围  */
	private PrpLclauseService prpLclauseService;
	/**  查询日额  */
	private PrpDriskRateService prpDriskRateService;

	private CompensateGenerateLossViewHelper compensateGenerateLossViewHelper;
	private PrpCmainCarGoSubService prpCmainCarGoSubService;
	private PrpCitemShipService prpCitemShipService;
	private PrpCmainCargoService prpCmainCargoService;
	private PrpCCargoItemService prpCCargoItemService;
	private PrpCaddressService prpCaddressService;
	private PrpCplaneService prpCplaneService;

	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
	private PrpLpersonLossService prpLpersonLossService;
	/**
	 * 默认构造方法
	 */
	public AccidentCompensateViewHelper() {
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写实赔单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return compensateDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public CompensateDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		CompensateDto compensateDto = new CompensateDto();
		return compensateDto;
	}

	/**
	 * 填写实赔页面及查询实赔request的生成.
	 * 填写实赔时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, CompensateDto compensateDto) throws Exception {
		// 得到request的PrpLcompensateForm用於显示
		PrpLcompensate prpLcompensateDto = compensateDto.getPrpLcompensate();
		// 意外健康险默认是，车险、财产险、责任险、货船险默认否
		httpServletRequest.setAttribute("prpLcompensateDto", prpLcompensateDto);
	}

	/**
	 * 非车险 出新的赔款计算数的时候初始化赔付标的的信息
	 * @throws Exception
	 */
	public List<PrpLloss> initPropLossItem(HttpServletRequest httpServletRequest,ClaimDto claimDto) throws Exception {
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String riskCode = prpLclaim.getRiskCode();
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		String riskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
		//火险带出赔付标的
		if(ConstantCodes.CLASSCODE_Q.equals(riskType)){
			String conditions = "  claimNo = '" + prpLclaim.getClaimNo() + "' and (UnderWriteFlag = '1' or UnderWriteFlag = '3')";
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null , null);
			List<PrpLcompensate> compensateDtoList = this.compensateService.findByConditions(conditions);
			if(CommonUtils.isEmpty(compensateDtoList)){
				PrpLloss prpLloss = null;
				for(PrpLclaimLoss prpLclaimLoss : claimDto.getPrpLclaimLossList()){
					prpLloss = new PrpLloss();
					prpLloss.getId().setCompensateNo("");
					prpLloss.setRiskCode(riskCode);
					prpLloss.setPolicyNo(prpLclaim.getPolicyNo());
					prpLloss.getId().setSerialNo(prpLclaimLoss.getId().getSerialNo());
					prpLloss.setDangerNo(prpLclaimLoss.getDangerNo());
					prpLloss.setKindCode(prpLclaimLoss.getKindCode());
					prpLloss.setKindName(prpLclaimLoss.getKindName());
					prpLloss.setItemCode(prpLclaimLoss.getItemCode());
					prpLloss.setLossName(prpLclaimLoss.getItemDetailName());
					prpLloss.setItemKindNo(prpLclaimLoss.getItemKindNo());
					prpLloss.setAmount(prpLclaimLoss.getAmount());
					prpLloss.setCurrency(prpLclaim.getCurrency());
					prpLloss.setCurrencyName(prpLclaimLoss.getCurrencyName());
					prpLloss.setCurrency1(prpLclaim.getCurrency());
					prpLloss.setCurrency2(prpLclaim.getCurrency());
					prpLloss.setCurrency4(prpLclaim.getCurrency());
					prpLloss.setCurrency3(prpLclaimLoss.getCurrency());
					prpLloss.setSumDefPay(prpLclaimLoss.getSumClaim());
					prpLloss.setSumLoss(prpLclaimLoss.getSumClaim());
					prpLloss.setSumRest(prpLclaimLoss.getKindRest());
					for(PrpCitemKind prpCitemKind : prpCitemKindList){
						if(prpCitemKind.getKindCode()!=null&&prpCitemKind.getItemCode()!=null){
							if(prpCitemKind.getKindCode().equals(prpLloss.getKindCode())&&prpCitemKind.getItemCode().equals(prpLloss.getItemCode())){
								prpLloss.setItemValue(prpCitemKind.getValue()==null?0:prpCitemKind.getValue());
								prpLloss.setDeductible(prpCitemKind.getDeductible()==null?0:prpCitemKind.getDeductible());
								prpLloss.setDeductiblerate(prpCitemKind.getDeductibleRate()==null?0:prpCitemKind.getDeductibleRate());
								break;
							}
						}
					}
					prpLlossList.add(prpLloss);
				}
			}
		}
		return prpLlossList;
	}

	/**
	 * 填写实赔页面及查询实赔request的生成.
	 * 填写实赔时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param PrpLcompensate 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void claimDtoToView(HttpServletRequest httpServletRequest, String claimNo, String editType) throws Exception {
		// 特殊赔案标志
		String caseType = httpServletRequest.getParameter("caseType");// 特殊赔案标志
		ClaimDto claimDto = this.claimService.findByPrimaryKey(claimNo);
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLclaim.getRiskCode());
		// 设置claimDto中所有估损险别的险别名称
		setClaimLossKindName(claimDto);
		// 将事故者信息压到页面上
		PrpLacciPerson prpLacciPerson = claimDto.getPrpLacciPerson();
		if (prpLacciPerson == null) {
			prpLacciPerson = new PrpLacciPerson();
		}
		prpLacciPerson.setPrpLacciPersonList(claimDto.getPrpLacciPersonList());
		httpServletRequest.setAttribute("prpLacciPerson", prpLacciPerson);
		String strBusinessNatureName = this.codeService.translateCodeCode("BusinessNature", prpLclaim.getBusinessNature(), true);
		prpLclaim.setBusinessNatureName(strBusinessNatureName);
		// 險種為FD、TC、TD、AP、PF時，欄位“是否涉及追償”設置為“是”。
		if ("RISKCODE_ZAP".equals(configCode) || "RISKCODE_ZFD".equals(configCode) || "RISKCODE_ZPF".equals(configCode) || "RISKCODE_ZTC".equals(configCode) || "RISKCODE_ZTD".equals(configCode)) {
			prpLclaim.setReplevyFlag("1");
		}
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		// 要在界面上显示一些立案信息
		List<RegistClaimInfoDto> registClaimList = this.claimService.findByPolicyNo(prpLclaim.getPolicyNo());
		httpServletRequest.setAttribute("registClaimList", registClaimList);
		PrpLcfeecoins prpLcfeecoins = new PrpLcfeecoins();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(" businessno = ''");
		prpLcfeecoins.setPrpLcfeecoinsList(this.prpLcfeecoinsService.findPrpLcfeecoins(queryRule));
		httpServletRequest.setAttribute("prpLcfeecoins", prpLcfeecoins);
		// 得到是否重开赔案
		int recount = 0;
		// 查询重开赔案信息
		ReCaseDto reCaseDto = this.recaseService.findByPrimaryKey(claimNo, 1);
		PrpLrecase prpLrecase = reCaseDto.getPrpLrecase();
		if (prpLrecase != null && DataUtils.emptyToNull(prpLrecase.getId().getClaimNo()) != null) {
			recount = 1;
			httpServletRequest.setAttribute("recaseFlag", String.valueOf(recount));
		}
		httpServletRequest.setAttribute("recaseFlag", String.valueOf(recount));
		// 赔款计算书主信息
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		// 带出调查费用合计值-----------------------------------------------------------
		String acciCheck = " registNo='" + prpLclaim.getRegistNo() + "'";
		List<PrpLacciCheck> prpLacciCheckList = this.acciCheckService.findByConditionsAcciCheck(acciCheck);
		double sumCheckFee = 0.00;
		for (PrpLacciCheck prplacciCheck : prpLacciCheckList) {
			sumCheckFee += CommonUtils.getDouble(prplacciCheck.getCheckFee());
		}
		prpLcompensate.setSumCheckFee(sumCheckFee);
		prpLcompensate.setCaseType(prpLclaim.getCaseType());
		// 特殊赔案标志
		if (!CommonUtils.isEmpty(caseType)) {
			prpLcompensate.setCaseType(caseType);
		} else {
			// 正常的流程，进行赔付
			prpLcompensate.setCaseType("2");
		}
		prpLcompensate.setCompensateNo("");
		prpLcompensate.setEditType(editType.trim());
		prpLcompensate.setLflag(prpLclaim.getLflag());
		prpLcompensate.setCaseNo(prpLclaim.getCaseNo());
		prpLcompensate.setTimes(1);
		prpLcompensate.setEscapeFlag(prpLclaim.getEscapeFlag());
		prpLcompensate.setIndemnityDuty(prpLclaim.getIndemnityDuty());
		prpLcompensate.setClassCode(prpLclaim.getClassCode());
		prpLcompensate.setRiskCode(prpLclaim.getRiskCode());
		prpLcompensate.setClaimNo(prpLclaim.getClaimNo());
		prpLcompensate.setPolicyNo(prpLclaim.getPolicyNo());
		prpLcompensate.setDeductCond(httpServletRequest.getParameter("DeductibleTerm"));
		prpLcompensate.setPreserveDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLcompensate.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
		prpLcompensate.setIndemnityDuty(prpLclaim.getIndemnityDuty());
		prpLcompensate.setUnderWriteFlag("0");
		prpLcompensate.setBusinessNature(prpLclaim.getBusinessNature());
		prpLcompensate.setDamageCode(prpLclaim.getDamageCode());
		prpLcompensate.setDamageName(prpLclaim.getDamageName());
		prpLcompensate.setUnderWriteFlag("0");
		// 初始化是否是团单免导标志
		prpLcompensate.setTermFlag(prpLclaim.getTermFlag());
		// 为共保我方赔款费用和代付赔款配用初始化
		prpLcompensate.setSumCoinForOther(0);
		prpLcompensate.setSumCoinForOtherFee(0);
		prpLcompensate.setSumCoinUs(0);
		prpLcompensate.setSumCoinUsFee(0);
		
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
		//本次住院天數
		prpLcompensate.setHospitalizedDays(null!=prpLcompensate.getHospitalizedDays() && prpLcompensate.getHospitalizedDays()>=0?prpLcompensate.getHospitalizedDays():prpLclaim.getHospitalizedDays());
		String conditions_forCompensateHis = "compensateNo like 'C"+prpLclaim.getClaimNo()+"%' AND (underWriteFlag =1 OR underWriteFlag =3) order by compensateNo ";
		List<PrpLcompensate> PrpLcompensateHis = this.compensateService.findByConditions(conditions_forCompensateHis);

		int sumHospitalizedDay = 0;
		String tailCompensateNo = "";//本案計算書號尾數
		if(prpLcompensate.getCompensateNo().indexOf("C"+claimNo)!=-1){
			tailCompensateNo = prpLcompensate.getCompensateNo().replace("C"+claimNo, "");
		}
		for(PrpLcompensate compensateHis :PrpLcompensateHis){

			String tailCompensateNoHit="";
			if(compensateHis.getCompensateNo().indexOf("C"+claimNo)!=-1){
				tailCompensateNoHit = compensateHis.getCompensateNo().replace("C"+claimNo, "");
			}
			//""==tailCompensateNo未處理理算任務出現的狀況 還沒有任何的計算書號
			if(""==tailCompensateNo || Integer.parseInt(tailCompensateNoHit,10)<Integer.parseInt(tailCompensateNo,10)){//當前這筆及大於的  都不要計算至累加天
				if(null!=compensateHis.getHospitalizedDays()){
					sumHospitalizedDay+=compensateHis.getHospitalizedDays();
				}
			}
		}
		prpLcompensate.setSumHospitalizedDay(sumHospitalizedDay);//本次事故累計住院天數(不含本次)
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END

		prpLcompensate.setSumPaidAll(prpLclaim.getSumPaid());
		if ("".equals(prpLclaim.getCurrency())) {
			prpLclaim.setCurrency(ConstantCodes.LOCAL_CURRENCY);
		}
		prpLcompensate.setCurrency(prpLclaim.getCurrency());
		String currencyName = this.getCodeService().translateCurrencyCode(prpLcompensate.getCurrency(), true);
		prpLcompensate.setCurrencyName(currencyName);

		prpLcompensate.setMakeCom(prpLclaim.getMakeCom());
		prpLcompensate.setComCode(prpLclaim.getComCode());
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		prpLcompensate.setHandlerCode(user.getUserCode());
		prpLcompensate.setHandlerName(user.getUserName());
		prpLcompensate.setHandler1Code(prpLclaim.getHandler1Code());
		prpLcompensate.setStatisticsYM(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLcompensate.setOperatorCode(prpLclaim.getOperatorCode());
		prpLcompensate.setInputDate(new DateTime(new Date(),DateTime.YEAR_TO_SECOND));
		prpLcompensate.setUnderWriteEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLcompensate.setRemark(prpLclaim.getRemark());
		prpLcompensate.setFlag(prpLclaim.getFlag());
		// 设置实赔操作的状态为 新案件登记 (未处理任务)
		prpLcompensate.setStatus("1");
		prpLcompensate.setInsuredCode(prpLclaim.getInsuredCode());
		prpLcompensate.setInsuredName(prpLclaim.getInsuredName());
		prpLcompensate.setStartDate(prpLclaim.getStartDate());
		prpLcompensate.setStartHour(prpLclaim.getStartHour());
		prpLcompensate.setEndDate(prpLclaim.getEndDate());
		prpLcompensate.setEndHour(prpLclaim.getEndHour());
		prpLcompensate.setClauseType(prpLclaim.getClauseType());
		prpLcompensate.setDamageStartDate(prpLclaim.getDamageStartDate());
		String timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());
		prpLcompensate.setDamageStartHour(timeTemp.substring(0, 2));
		prpLcompensate.setDamageStartMinute(timeTemp.substring(3, 5));
		prpLcompensate.setDamageAddressType(prpLclaim.getDamageAddressType());
		//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
		prpLcompensate.setAddressCode(prpLclaim.getAddressCode());
		prpLcompensate.setDamageAddress(prpLclaim.getDamageAddress());
		prpLcompensate.setSumAmount(prpLclaim.getSumAmount());
		String sumAmount = String.valueOf(prpLclaim.getSumAmount());
		httpServletRequest.setAttribute("sumAmount", sumAmount);
		prpLcompensate.setSumPremium(prpLclaim.getSumPremium());
		prpLcompensate.setSumClaim(prpLclaim.getSumClaim());
		// 添加报案号
		prpLcompensate.setRegistNo(prpLclaim.getRegistNo());
		prpLcompensate.setCaseType(prpLclaim.getCaseType());
		prpLcompensate.setCaseTypeName(this.getCodeService().translateCodeCode("CaseCode", prpLclaim.getCaseType(), true));
		// 添加案件类型
		prpLcompensate.setClaimType(prpLclaim.getClaimType());
		prpLcompensate.setClaimTypeName(this.getCodeService().translateCodeCode("CaseCode", prpLclaim.getClaimType(), true));
		// 货运险添加赔付对象
		prpLcompensate.setCounterClaimerName((claimDto.getPrpLext() == null) ? "" : claimDto.getPrpLext().getSalvor());
		prpLcompensate.setCoinsFlag(prpLclaim.getCoinsFlag());
		// 是否案终赔付：意外健康险,财产险、责任险、默认是，车险、货船险默认否。 2005-9-16
		prpLcompensate.setClaimDate(prpLclaim.getClaimDate());
		prpLcompensate.setReceiptDate(prpLclaim.getReceiptDate());
		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLcompensate.getRiskCode());
		httpServletRequest.setAttribute("riskType", strRiskType);
		prpLcompensate.setFinallyFlag("1");// 结案类型默认结案
		if (DataUtils.emptyToNull(prpLcompensate.getResult()) == null) {
			// 设置理赔结论的默认值
			prpLcompensate.setResult(prpLcompensate.getDefaultCompensateResult());
		}
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
		prpLcompensate.setAppliName(prpCmain.getAppliName());// 增加投保人的信息
		// 查询保单信息
		if(ConstantCodes.CLASSCODE_Y.equals(strRiskType)){//水险理算内容初始化
			PrpCitemShip prpCitemShip = this.prpCitemShipService.findPrpCitemShip(new PrpCitemShipId(policyNo , 1));
			if (prpCitemShip != null) {// OH,EV,FV,EW,FW
				if ("RISKCODE_YOH".equals(configCode) || "RISKCODE_YEV".equals(configCode) 
						|| "RISKCODE_YFV".equals(configCode) || "RISKCODE_YEW".equals(configCode) || "RISKCODE_YFW".equals(configCode)) {
					prpLcompensate.setShipCName(DataUtils.dbNullToEmpty(prpCitemShip.getShipCName()));
					if ("RISKCODE_YOH".equals(configCode)||"RISKCODE_YFV".equals(configCode)) {
						prpLcompensate.setSailScope(DataUtils.dbNullToEmpty(prpCitemShip.getSailScope()));
					}
				}
			}
			if ("RISKCODE_YMC".equals(configCode)) {
				if (!CommonUtils.isEmpty(prpLclaim.getEndorseNo())) {
					PrpLclaim tempPrpLclaim = claimService.generateCargoInfo(null, prpLclaim.getEndorseNo());
					prpLcompensate.setShipCName(tempPrpLclaim.getShipCName());
					prpLcompensate.setEndSitePort(tempPrpLclaim.getEndSitePort());
					prpLcompensate.setClaimAgent(tempPrpLclaim.getClaimAgent());
					prpLcompensate.setStartSitePort(tempPrpLclaim.getStartSitePort());//起運地
					prpLcompensate.setAreaCode(tempPrpLclaim.getAreaCode());
					prpLcompensate.setCargoNo(tempPrpLclaim.getCargoNo());
				} else {
					String conditions = "policyNo = '"+policyNo+"' order by serialno ";
					List<PrpCmainCarGoSub> prpCmainCarGoSubList = this.prpCmainCarGoSubService.findPrpCmainCarGoSub(conditions);
					PrpCmainCarGoSub prpCmainCarGoSub = null;
					if (!CommonUtils.isEmpty(prpCmainCarGoSubList)) {
						prpCmainCarGoSub = prpCmainCarGoSubList.get(0);
					}
					if (prpCmainCarGoSub != null) {
						prpLcompensate.setShipCName(DataUtils.dbNullToEmpty(prpCmainCarGoSub.getSiteName()));
						prpLcompensate.setEndSitePort(prpCmainCarGoSub.getPortName());//中轉地/目的地
					}
					PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
					if (prpCmainCargo != null) {
						prpLcompensate.setClaimAgent(prpCmainCargo.getCheckAgentCode());
						prpLcompensate.setStartSitePort(prpCmainCargo.getStartSiteName());//起運地
						// 根據進出口別代號欄位判斷, 如果是出口, 由承保帶出中轉地/目的地的編號;如果是進口,由承保帶出起運地編號
						if ("1".equals(prpCmainCargo.getPreserveInfo())) {// 進口
							prpLcompensate.setAreaCode(prpCmainCargo.getStartSiteCode());
						} else if ("2".equals(prpCmainCargo.getPreserveInfo())) {// 出口
							prpLcompensate.setAreaCode(prpCmainCarGoSub != null ? prpCmainCarGoSub.getPortCode() : "");
						}
					}
					queryRule = QueryRule.getInstance();
					queryRule.addEqual("id.policyNo", policyNo);
					List<PrpCCargoItem> prpCCargoItemList = this.prpCCargoItemService.findPrpCCargoItem(queryRule);
					if(!CommonUtils.isEmpty(prpCCargoItemList)){
						prpLcompensate.setCargoNo(prpCCargoItemList.get(0).getCargoBigTypeCode());
					}
				}
			}
			if ("RISKCODE_YAV".equals(configCode)) {
				PrpCplane prpCplane = this.prpCplaneService.findPrpCplane(new PrpCplaneId(policyNo, 1));
				if (prpCplane != null) {
					prpLcompensate.setShipModel(DataUtils.dbNullToEmpty(prpCplane.getPlaneType()));
				}
			}
		}
		String insuredCode = prpLclaim.getInsuredCode();
		String insuredName = prpLclaim.getInsuredName();
		String prpCinsuredBearer = "";
		List<PrpCinsuredNature> prpCinsuredNatureList = null;
		List<PrpCinsured> prpCinsuredList = null;
		List<PrpCitemKind> prpCitemKindList = null;
		int familyNo = 0;
		PrpCinsured tempPrpCinsured = null;
		if (ConstantCodes.CLASSCODE_E.equals(strRiskType)) {
			//意健險被保險人訊息單獨處理
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
			int[] serialNos = this.endorseViewHelper.getPrpCinsuredSerialNos(prpCinsuredList);
			tempPrpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
			familyNo = tempPrpCinsured.getId().getSerialNo();
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, familyNo);
			prpCinsuredNatureList = this.endorseViewHelper.findPrpCinsuredNatureFromCopy(policyNo, damageDate, damageHour, serialNos);
			httpServletRequest.setAttribute("familyNo", familyNo);
		} else {
			prpCinsuredNatureList = this.endorseViewHelper.findPrpCinsuredNatureFromCopy(policyNo, damageDate, damageHour);
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour);
			tempPrpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		}
		prpLclaim.setFamilyNo(tempPrpCinsured.getId().getSerialNo());
		// 获得保单默认的险别
		String defaultKindCode = this.daaClaimViewHelper.getDefaultKindCodeByPolicyDto(prpCitemKindList);
		httpServletRequest.setAttribute("defaultKindCode", defaultKindCode);
		httpServletRequest.setAttribute("shareHolderFlag", prpCmain.getShareHolderFlag());
		httpServletRequest.setAttribute("prpCitemKindList", prpCitemKindList);
		httpServletRequest.setAttribute("prpCinsured", tempPrpCinsured);
		// 按保单上事故者所保的险别查找历史赔付额
		// 获取事故者的分户序号,根据分户序号查找所保险别
		for (PrpCinsured prpCinsured : prpCinsuredList) {
			// 1表示个人，2表示单位客户
			if ("1".equals(prpCinsured.getInsuredType())) {
				for (PrpCinsuredNature prpCinsuredNature : prpCinsuredNatureList) {
					if (prpCinsured.getId().getSerialNo().intValue() == prpCinsuredNature.getId().getSerialNo().intValue()) {
						prpCinsured.setPrpCinsuredNature(prpCinsuredNature);
					}
				}
			}
			if("2".equals(prpCinsured.getInsuredFlag())){
				prpCinsuredBearer = prpCinsured.getBearer()==null?"0":prpCinsured.getBearer();
			}
		}
		httpServletRequest.setAttribute("prpCinsuredBearer", prpCinsuredBearer);
		httpServletRequest.setAttribute("prpCinsuredList", prpCinsuredList);
		//设置同险代号
		if(ConstantCodes.CLASSCODE_Q.equals(strRiskType)){
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", policyNo);
			List<PrpCaddress> prpCaddressList = this.prpCaddressService.findPrpCaddress(queryRule);
			for(PrpCaddress prpCaddress : prpCaddressList){
				if(!CommonUtils.isEmpty(prpCaddress.getSameAddressNo())){
					prpLcompensate.setSameAddressNo(prpCaddress.getSameAddressNo());
					break;
				}
			}
		}
		// 签单币别不是CNY时，给出提示，並提供当前兑换率
		// 获取兑换率信息
		UIExchAction uiExchAction = new UIExchAction();
		List<PrpDexchDto> prpDexchList = (List<PrpDexchDto>) uiExchAction.getExchOfMaxDate(DateTime.current().toString().substring(0, 10));

		// reason:签单币别不是CNY时，给出提示，並提供当前兑换率
		PrpDexchDto prpDexch = null;
		String currency = claimDto.getPrpLclaim().getCurrency();
		for (int i = 0; i < prpDexchList.size(); i++) {
			if (prpDexchList.get(i).getBaseCurrency().equals(currency)) {
				prpDexch = prpDexchList.get(i);
			}
		}
		httpServletRequest.setAttribute("prpDexch", prpDexch);
		httpServletRequest.setAttribute("prpDexchList", prpDexchList);
		// 赋值初始的计算书Times信息
		String conditions = " ClaimNo= '" + claimNo + "'";
		List<PrpLcompensate> prplCompensateListTemp = this.getCompensateService().findByConditions(conditions);
		if (prplCompensateListTemp != null) {
			prpLcompensate.setTimes(prplCompensateListTemp.size() + 1);
		} else {
			prpLcompensate.setTimes(1);
		}
		PrpLctext prpLctext = new PrpLctext();
		// 设值文本的内容
		httpServletRequest.setAttribute("prpLctext", prpLctext);
		PrpLctext prpLctextPayText = new PrpLctext();
		StringBuffer payText = new StringBuffer("");
		payText.append("請將賠款 付給\r\n");
		payText.append("單位:\r\n");
		payText.append("開戶行:\r\n");
		payText.append("帳號:\r\n");
		payText.append("付訖日期及方式:\r\n");
		prpLctextPayText.getId().setTextType(PAY_TEXT);
		prpLctextPayText.setContext(payText.toString());
		// 设值赔款户头说明的内容
		httpServletRequest.setAttribute("prpLctextPayText", prpLctextPayText);
		// 增加意外健康险的赔款计算过程的保存,目前设置为text_type 5；
		PrpLctext prpLctextAccidentTextDto = new PrpLctext();
		prpLctextAccidentTextDto.getId().setTextType("5");
		prpLctextAccidentTextDto.setContext("");
		httpServletRequest.setAttribute("prpLctextAccidentText", prpLctextAccidentTextDto);
		this.getDaaRegistViewHelper().getSamePolicyRegistInfo(httpServletRequest, prpLcompensate.getPolicyNo(), prpLclaim.getRegistNo());
		// 设置主实赔信息内容到窗体表单
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
		// 在界面上显示险种名称
		httpServletRequest.setAttribute("riskCName", this.getCodeService().translateRiskCode(prpLcompensate.getRiskCode(), true));
		// 设置各个子表信息项到窗体表单
		CompensateDto compensateDto = new CompensateDto();
		compensateDto.setPrpLcompensate(prpLcompensate);
		compensateDto = this.getCompensateService().findByAppendInformation(compensateDto);
		// 初始化标的（从定核损带过来）
		// 需求變更#83 非車二次調整 begin 
//		List<PrpLloss> prpLlossList = this.initPropLossItem(httpServletRequest, claimDto);
//		compensateDto.setPrpLlossList(prpLlossList);
		this.compensateGenerateLossViewHelper.generateLoss(httpServletRequest , compensateDto, claimDto);
		// 需求變更#83 非車二次調整 end 
		// 缴费标志，判断保费是否交付
//		String policyNo = prpLcompensate.getPolicyNo();
		int intPayFee = this.checkPay(httpServletRequest, policyNo);
		prpLcompensate.setPayFee(intPayFee);
		// 获取危险单位信息
		Collection<?> reinsDangerUnitCollection = reinsServiceManager.getReinsService().getDangerUnit(prpLcompensate.getPolicyNo(), new com.sinosoft.sysframework.common.datatype.DateTime(prpLclaim.getDamageStartDate()));
		httpServletRequest.setAttribute("ReinsDangerUnitCollection", reinsDangerUnitCollection);
		// 得到共保和股东业务信息临分信息
		List<PrpCcoins> coinsList = new ArrayList<PrpCcoins>();
		httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
		httpServletRequest.setAttribute("shareHolderFlag", prpCmain.getShareHolderFlag());
		if ("1".equals(prpCmain.getCoinsFlag())) {
			coinsList = this.getPrpCcoinsService().findPrpCcoins(QueryRule.getInstance().addSql("policyNo = '" + policyNo + "'"));
			if (coinsList != null && coinsList.size() > 0) {
				for (PrpCcoins prpCcoins : coinsList) {
					if ("1".equals(prpCcoins.getChiefFlag())) {
						httpServletRequest.setAttribute("chiefFlag", prpCcoins.getChiefFlag());
						break;
					}
				}
			}
		}
		double sumFacShare = reinsServiceManager.getReinsService().getSumFacShare(policyNo,
				new com.sinosoft.sysframework.common.datatype.DateTime(prpLcompensate.getDamageStartDate(), com.sinosoft.sysframework.common.datatype.DateTime.YEAR_TO_DAY));
		httpServletRequest.setAttribute("tempReinsFlag", sumFacShare > 0 ? "1" : "0");
		compensateDto.setPrpLcompensate(prpLcompensate);
		String registNo = this.getCodeService().translateBusinessCode(prpLcompensate.getClaimNo(), false);
		RegistDto registDto = this.getRegistService().findByPrimaryKey(registNo);
		compensateDto.setPrpLregistExtList(registDto.getPrpLregistExtList());
		compensateDto.setPrpLclaim(prpLclaim);
		setSubInfo(httpServletRequest, strRiskType, compensateDto, prpCitemKindList);
		StringBuffer text = new StringBuffer("");
		if (claimDto.getPrpLltextList() != null) {
			for (Iterator<?> iter = claimDto.getPrpLltextList().iterator(); iter.hasNext();) {
				PrpLltext element = (PrpLltext) iter.next();
				if ("08".equals(element.getId().getTextType())) {
					text.append(element.getContext());
				}
			}
		}
		PrpLltext prpLltext = new PrpLltext();
		if (!"E".equals(strRiskType)) {
			text = new StringBuffer("");
		}
		prpLltext.getId().setTextType("08");
		prpLltext.setContext(text.toString());
		httpServletRequest.setAttribute("prpLltext", prpLltext);
		
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
		//理算任務
		settingPAF4567(httpServletRequest,prpLcompensate);
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
		
		// 设置理赔结论列表
		httpServletRequest.setAttribute("compensateResultMap", getCompensateResultList());
		// 将调查费默认为费用信息中的查勘费
		PrpLcharge prpLcharge = this.getDefaultPrpLchargeFromAcciCheckFee(claimDto);
		if (prpLcharge != null) {
			List<PrpLcharge> prpLchargeList = new ArrayList<PrpLcharge>();
			prpLchargeList.add(prpLcharge);
			prpLcharge.setPrpLchargeList(prpLchargeList);
			httpServletRequest.setAttribute("prpLcharge", prpLcharge);
			// 设置理算对象中的赔款费用值为调查费
			prpLcompensate.setSumNoDutyFee(prpLcharge.getChargeAmount());
		}
		// 送审初复核初始化
		SendUndwrtViewHelper sendUndwrtViewHelper = new SendUndwrtViewHelper();
		sendUndwrtViewHelper.LoadingSendUndwrt(httpServletRequest, claimNo, "compe");
		// 非车页面代码迁移
		// 需求變更#83二次調整   add by 中科軟 begin 
		httpServletRequest.setAttribute("reservedEstimateList", ConstantsCollection.reservedEstimateList);
		// 需求變更#83二次調整   add by 中科軟 end 
		this.showJspPage(httpServletRequest, editType);
		setClaimCredit(httpServletRequest, claimDto.getPrpLclaimCredit());
	}
	
	/**
	 * CC綜合保險帶出信用卡訊息
	 * @param request
	 * @param prpLclaimCredit
	 */
	private void setClaimCredit(HttpServletRequest request, PrpLclaimCredit prpLclaimCredit){
		if(prpLclaimCredit != null){
			request.setAttribute("prpLclaimCredit", prpLclaimCredit);
			request.setAttribute("creditBankList", this.codeService.findPrpDcodeByConditions(" codeType='CreditType' and validStatus = '1' and codeLevel = '1' order by codeCode "));
			String bankCode = prpLclaimCredit.getBankCode();
			if(!CommonUtils.isEmpty(bankCode)){
				request.setAttribute("creditTypeList", this.codeService.findPrpDcodeByConditions(" codeType='CreditType' and validStatus = '1' and codeLevel = '2' and upperCode = '"+ bankCode +"' order by codeCode "));
			}
		}
	}

	public void showJspPage(HttpServletRequest request, String editType) throws Exception {
		// modify by liuyanmei add start 20051027
		// -只允许有核赔权限的人看到申请调查按钮---------
		String cancheck = "0";
		// String userCode = "";
		// UserDto userDto = (UserDto)
		// request.getSession().getAttribute("user");
		// userCode = userDto.getUserCode();
		// UIPowerInterface uiPowerInterface= new UIPowerInterface(); //权限校验
		// boolean checkPower = false; // 是否通过校验
		// --只允许上次提调结束後才能再次申请提调--------
		// String registNo = request.getParameter("RegistNo");
		String fowid = request.getParameter("swfLogFlowID");
		// String logNo = request.getParameter("swfLogLogNo");
		String checkNotOver = "0";
		String conditions = "  flowid='" + fowid + "' and nodetype='check'";
		SwfLog swfLog = null;
		// UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		// System.out.println("--conditions2---1--"+conditions2);
		// Collection swfLogDtoList =
		// uiWorkFlowAction.findNodesByConditions(conditions2);
		// System.out.println("--conditions2---2--"+conditions2);
		List<SwfLog> swfLogList = swfLogService.findByConditions(conditions);
		if (swfLogList.isEmpty()) {
			checkNotOver = "0";
		} else if (swfLogList.size() != 0) {
			if (swfLogList.size() > 0) {
				swfLog = (SwfLog) swfLogList.get(0);
				String nodeStatus = swfLog.getNodeStatus();
				if (!nodeStatus.equals("4")) {
					checkNotOver = "1";
				}
			}
		}
		String core_URL = AppConfig.get("sysconst.Core_URL");
		request.setAttribute("core_URL", core_URL);
		request.setAttribute("LOCAL_CURRENCY", ConstantCodes.LOCAL_CURRENCY);
		request.setAttribute("checkNotOver", checkNotOver);
		request.setAttribute("cancheck", cancheck);
		// 用于独立处理费用的判断，如果是强制险则显示，任意险隐藏
		request.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
	}

	/**
	 * 将调查费默认为费用信息中的查勘费
	 * @author 中科软
	 * @date Feb 26, 2013 7:29:30 PM
	 * @param claimDto
	 * @return
	 * @throws Exception
	 */
	private PrpLcharge getDefaultPrpLchargeFromAcciCheckFee(ClaimDto claimDto) throws Exception {
		// 获得意健险的多次调查费用之和
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		double sumAcciCheckFee = this.prpLacciCheckService.getAcciCheckFeeByRegistNo(prpLclaim.getRegistNo());
		PrpLcharge prpLcharge = null;
		if (sumAcciCheckFee > 0) {
			prpLcharge = new PrpLcharge();
			prpLcharge.setDangerNo(1);
			// 设置默认的序列为1
			prpLcharge.getId().setSerialNo(1);
			// 设置默认的险别代码和险别名称为立案时的险别名称和险别代码;
			List<PrpLclaimLoss> claimLossList = claimDto.getPrpLclaimLossList();
			if (claimLossList != null && claimLossList.size() > 0) {
				PrpLclaimLoss claimLoss = (PrpLclaimLoss) claimLossList.get(0);
				prpLcharge.setKindCode(claimLoss.getKindCode());
				prpLcharge.setKindName(claimLoss.getKindName());
				prpLcharge.setItemKindNo(claimLoss.getItemKindNo());
			}
			// 设置默认的费用类别为W-調查費
			prpLcharge.setChargeCode("W");
			prpLcharge.setChargeName("調查費");
			// 设置默认的币别为CNY-人民币
			prpLcharge.setCurrency(ConstantCodes.LOCAL_CURRENCY);
			prpLcharge.setCurrencyName(ConstantCodes.LOCAL_CURRENCYNAME);
			// 设置费用为调查费
			prpLcharge.setChargeReport(sumAcciCheckFee);
			prpLcharge.setChargeAmount(sumAcciCheckFee);
			// 设置计入赔款金额为0
			prpLcharge.setSumRealPay(0);
		}
		return prpLcharge;
	}

	/**
	 * 设置ClaimDto中的 险别名KindName
	 * @param claimDto
	 * @throws Exception
	 */
	private void setClaimLossKindName(ClaimDto claimDto) throws Exception {
		claimDto.getPrpLclaimLossList();
		for (PrpLclaimLoss claimLoss : claimDto.getPrpLclaimLossList()) {
			setClaimLossKindName(claimLoss);
		}
	}

	/**
	 * 设置PrpLclaimLoss的险别名称(根据它的险别代码)
	 * @param claimLoss
	 * @throws Exception
	 */
	private void setClaimLossKindName(PrpLclaimLoss claimLoss) throws Exception {
		String kindName = this.getCodeService().translateKindCode(claimLoss.getRiskCode(), claimLoss.getKindCode(), true);
		claimLoss.setKindName(kindName);
	}

	/**
	 * 根据赔款计算书号查询预赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param compensateNo 赔款计算书号
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void compensateDtoView(HttpServletRequest httpServletRequest, String compensateNo, String editType) throws Exception {
		// 特殊赔案标志,从工作流上获得。
		String caseType = httpServletRequest.getParameter("caseType");
		CompensateDto compensateDto = this.compensateService.findByPrimaryKey(compensateNo, caseType);
		// 赔款计算书主信息
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		prpLcompensate.setEditType(editType.trim());
		// 是否重开赔案
		String claimNo = prpLcompensate.getClaimNo().trim();
		int recount = 0;
		// 查询重开赔案信息
		ReCaseDto reCaseDto = this.recaseService.findByPrimaryKey(claimNo, 1);
		PrpLrecase prpLrecase = reCaseDto.getPrpLrecase();
		if (prpLrecase != null && DataUtils.emptyToNull(prpLrecase.getId().getClaimNo()) != null) {
			recount = 1;
			httpServletRequest.setAttribute("recaseFlag", String.valueOf(recount));
		}
		httpServletRequest.setAttribute("recaseFlag", String.valueOf(recount));
		PrpLcfeecoins prpLcfeecoins = new PrpLcfeecoins();
		String conditions = " businessno = '" + compensateNo + "' order by serialNo";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		List<PrpLcfeecoins> prpLcfeecoinsList = this.prpLcfeecoinsService.findPrpLcfeecoins(queryRule);
		prpLcfeecoins.setPrpLcfeecoinsList(prpLcfeecoinsList);
		// 对共保情况我方金额和代赔金额初始化
		double prpLcompensateSumCoinUs = 0;// 共保案件时我方赔款分摊金额
		double prpLcompensateSumCoinUsFee = 0;// 共保案件时我方费用分摊金额
		double prpLcompensateSumCoinForOther = 0;// 共保案件我方代他方赔款金额
		double prpLcompensateSumCoinForOtherFee = 0;// 共保案件我方代他方费用金额
		for (PrpLcfeecoins temp : prpLcfeecoinsList) {
			if ("0".equals(temp.getLossFeeType()) && "2".equals(temp.getCoinsType())) {
				prpLcompensateSumCoinUs += temp.getCoinsSumPaid();
			} else if (temp.getLossFeeType().equals("1") && temp.getCoinsType().equals("2")) {
				prpLcompensateSumCoinUsFee += temp.getCoinsSumPaid();
			}
			if ("1".equals(prpLcompensate.getIsPayForOther())) {
				if (temp.getLossFeeType().equals("0") && !temp.getCoinsType().equals("2")) {
					prpLcompensateSumCoinForOther += temp.getCoinsSumPaid();
				} else if (temp.getLossFeeType().equals("1") && !temp.getCoinsType().equals("2")) {
					prpLcompensateSumCoinForOtherFee += temp.getCoinsSumPaid();
				}
			}
		}
		prpLcompensate.setSumCoinUs(prpLcompensateSumCoinUs);
		prpLcompensate.setSumCoinUsFee(prpLcompensateSumCoinUsFee);
		prpLcompensate.setSumCoinForOther(prpLcompensateSumCoinForOther);
		prpLcompensate.setSumCoinForOtherFee(prpLcompensateSumCoinForOtherFee);
		httpServletRequest.setAttribute("prpLcfeecoins", prpLcfeecoins);
		// 得到币别中文名称
		prpLcompensate.setCurrencyName(this.getCodeService().translateCurrencyCode(prpLcompensate.getCurrency(), true));
		// 设置实赔操作的状态为 案件修改 (正处理任务)
		PrpLclaimStatus prpLclaimStatus = compensateDto.getPrpLclaimStatus();
		if (prpLclaimStatus != null) {
			// 设置初始化的内容
			prpLcompensate = compensateDto.getPrpLcompensate();
			if ("7".equals(prpLclaimStatus.getStatus())) {
				compensateDto.getPrpLclaimStatus().setStatus("3");
			}
			prpLcompensate.setStatus(prpLclaimStatus.getStatus());
		} else {
			// 已提交，已经处理完毕的状态
			prpLcompensate.setStatus("4");
		}
		String status = httpServletRequest.getParameter("status");// 从工作流上去状态
		//mantis：CLM0211，處理人員：DP0713，需求單編號：新核心-CA工程險理算處理畫面舊有按鈕移除
		System.out.println(prpLcompensate.getCompensateNo()+"/CLM0211_param status:"+httpServletRequest.getParameter("status")+"/getStatus:"+prpLcompensate.getStatus());
		if (DataUtils.emptyToNull(status) != null) {
			//mantis：CLM0211，處理人員：DP0713，需求單編號：新核心-CA工程險理算處理畫面舊有按鈕移除
			System.out.println(prpLcompensate.getCompensateNo()+"/CLM0211_acc status:"+status);
			prpLcompensate.setStatus(status);
		}
		ClaimDto claimDto = null;
		PrpLclaim prpLclaim = null;
		if (DataUtils.emptyToNull(claimNo) != null) {
			// 查询保单信息
			claimDto = this.claimService.findByPrimaryKey(claimNo);
			prpLclaim = claimDto.getPrpLclaim();
			// 带出调查费用合计值
			conditions = " registNo='" + prpLclaim.getRegistNo() + "'";
			List<PrpLacciCheck> prpLacciCheckList = this.acciCheckService.findByConditionsAcciCheck(conditions);
			double sumCheckFee = 0.00;
			for (PrpLacciCheck temp : prpLacciCheckList) {
				sumCheckFee += CommonUtils.getDouble(temp.getCheckFee());
			}
			prpLcompensate.setSumCheckFee(sumCheckFee);
			// 将事故者信息压到页面上
			httpServletRequest.setAttribute("prpLacciPerson", claimDto.getPrpLacciPerson());
			prpLcompensate.setDamageStartDate(prpLclaim.getDamageStartDate());
			prpLcompensate.setIndemnityDuty(prpLclaim.getIndemnityDuty());//
			prpLcompensate.setEscapeFlag(prpLclaim.getEscapeFlag());//
			String timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());
			prpLcompensate.setDamageStartHour(timeTemp.substring(0, 2));
			prpLcompensate.setDamageStartMinute(timeTemp.substring(3, 5));
			prpLcompensate.setStartDate(prpLclaim.getStartDate());
			prpLcompensate.setStartHour(prpLclaim.getStartHour());
			prpLcompensate.setEndDate(prpLclaim.getEndDate());
			prpLcompensate.setEndHour(prpLclaim.getEndHour());
			//mantis：CLM0274，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
			prpLcompensate.setAddressCode(prpLclaim.getAddressCode());
			prpLcompensate.setDamageAddress(prpLclaim.getDamageAddress());
			prpLcompensate.setSumClaim(prpLclaim.getSumClaim());
			prpLcompensate.setSumAmount(prpLclaim.getSumAmount());
			prpLcompensate.setInsuredName(prpLclaim.getInsuredName());
			prpLcompensate.setInsuredCode(prpLclaim.getInsuredCode());
			prpLcompensate.setSumPaidAll(prpLclaim.getSumPaid());
			prpLcompensate.setHandlerName(this.getCodeService().translateUserCode(prpLcompensate.getHandlerCode(), true));
			// 添加出险原因
			prpLcompensate.setDamageCode(prpLclaim.getDamageCode());
			prpLcompensate.setDamageName(prpLclaim.getDamageName());
			// 添加案件类型
			prpLcompensate.setClaimType(prpLclaim.getClaimType());
			prpLcompensate.setClaimTypeName(this.getCodeService().translateCodeCode("CaseCode", prpLclaim.getClaimType(), true));
			// 添加报案号
			prpLcompensate.setRegistNo(prpLclaim.getRegistNo());

			prpLcompensate.setClaimDate(prpLclaim.getClaimDate());
			prpLcompensate.setReceiptDate(prpLclaim.getReceiptDate());
		}
		// 获取兑换率信息
		// 获取兑换率信息
		UIExchAction uiExchAction = new UIExchAction();
		List<PrpDexchDto> prpDexchList = (List<PrpDexchDto>) uiExchAction.getExchOfMaxDate(DateTime.current().toString().substring(0, 10));

		// reason:签单币别不是CNY时，给出提示，並提供当前兑换率
		PrpDexchDto prpDexch = null;
		String currency = claimDto.getPrpLclaim().getCurrency();
		for (int i = 0; i < prpDexchList.size(); i++) {
			if (prpDexchList.get(i).getBaseCurrency().equals(currency)) {
				prpDexch = prpDexchList.get(i);
			}
		}
		httpServletRequest.setAttribute("prpDexch", prpDexch);
		httpServletRequest.setAttribute("prpDexchList", prpDexchList);
		// 查询保单信息
		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLcompensate.getRiskCode());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		String insuredCode = prpLclaim.getInsuredCode();
		String insuredName = prpLclaim.getInsuredName();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		List<PrpCinsuredNature> prpCinsuredNatureList = null;
		List<PrpCinsured> prpCinsuredList = null;
		List<PrpCitemKind> prpCitemKindList = null;
		int familyNo = 0;
		PrpCinsured tempPrpCinsured = null;
		if (ConstantCodes.CLASSCODE_E.equals(strRiskType)) {
			//意健險被保險人訊息單獨處理
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
			int[] serialNos = this.endorseViewHelper.getPrpCinsuredSerialNos(prpCinsuredList);
			tempPrpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
			familyNo = tempPrpCinsured.getId().getSerialNo();
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, familyNo);
			prpCinsuredNatureList = this.endorseViewHelper.findPrpCinsuredNatureFromCopy(policyNo, damageDate, damageHour, serialNos);
			httpServletRequest.setAttribute("familyNo", familyNo);
		} else {
			prpCinsuredNatureList = this.endorseViewHelper.findPrpCinsuredNatureFromCopy(policyNo, damageDate, damageHour);
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour);
			tempPrpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		}
		prpLclaim.setFamilyNo(tempPrpCinsured.getId().getSerialNo());
		httpServletRequest.setAttribute("prpCitemKindList", prpCitemKindList);
		// 得到共保和股东业务信息临分信息
		httpServletRequest.setAttribute("shareHolderFlag", prpCmain.getShareHolderFlag());
		httpServletRequest.setAttribute("prpCinsured", tempPrpCinsured);
		List<PrpCcoins> coinsList = new ArrayList<PrpCcoins>();
		// 查询保单信息
		if (ConstantCodes.CLASSCODE_Y.equals(strRiskType)) {// 水险理算内容初始化
			String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLcompensate.getRiskCode());
			PrpCmainCarGoSub prpCmainCarGoSub = this.prpCmainCarGoSubService.findPrpCmainCarGoSub(policyNo, 1);
			PrpCitemShip prpCitemShip = this.prpCitemShipService.findPrpCitemShip(new PrpCitemShipId(policyNo, 1));
			if (prpCitemShip != null) {// OH,EV,FV,EW,FW
				if ("RISKCODE_YOH".equals(configCode) || "RISKCODE_YFV".equals(configCode)) {
					prpLcompensate.setSailScope(DataUtils.dbNullToEmpty(prpCitemShip.getSailScope()));
				}
			}
			if ("RISKCODE_YMC".equals(configCode)) {
				if (!CommonUtils.isEmpty(prpLclaim.getEndorseNo())) {
					PrpLclaim tempPrpLclaim = claimService.generateCargoInfo(null, prpLclaim.getEndorseNo());
					prpLcompensate.setShipCName(tempPrpLclaim.getShipCName());
					prpLcompensate.setEndSitePort(tempPrpLclaim.getEndSitePort());
					prpLcompensate.setClaimAgent(tempPrpLclaim.getClaimAgent());
					prpLcompensate.setStartSitePort(tempPrpLclaim.getStartSitePort());//起運地
					prpLcompensate.setAreaCode(tempPrpLclaim.getAreaCode());
					prpLcompensate.setCargoNo(tempPrpLclaim.getCargoNo());
				}  else {
					if (prpCmainCarGoSub != null) {
						prpLcompensate.setEndSitePort(prpCmainCarGoSub.getPortName());//中轉地/目的地
					}
					PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
					if (prpCmainCargo != null) {
						prpLcompensate.setClaimAgent(prpCmainCargo.getCheckAgentCode());
						prpLcompensate.setStartSitePort(prpCmainCargo.getStartSiteName());//起運地
						// 根據進出口別代號欄位判斷, 如果是出口, 由承保帶出中轉地/目的地的編號;如果是進口,由承保帶出起運地編號
						if ("1".equals(prpCmainCargo.getPreserveInfo())) {// 進口
							prpLcompensate.setAreaCode(prpCmainCargo.getStartSiteCode());
						} else if ("2".equals(prpCmainCargo.getPreserveInfo())) {// 出口
							prpLcompensate.setAreaCode(prpCmainCarGoSub != null ? prpCmainCarGoSub.getPortCode() : "");
						}
					}
					queryRule = QueryRule.getInstance();
					queryRule.addEqual("id.policyNo", policyNo);
					List<PrpCCargoItem> prpCCargoItemList = this.prpCCargoItemService.findPrpCCargoItem(queryRule);
					if(!CommonUtils.isEmpty(prpCCargoItemList)){
						prpLcompensate.setCargoNo(prpCCargoItemList.get(0).getCargoBigTypeCode());
					}
				}
			}
		}
		httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
		httpServletRequest.setAttribute("shareHolderFlag", prpCmain.getShareHolderFlag());
		if ("1".equals(prpCmain.getCoinsFlag())) {
			coinsList = this.prpCcoinsService.findPrpCcoins(QueryRule.getInstance().addSql("policyNo = '" + prpCmain.getPolicyNo() + "'"));
			if (coinsList != null && coinsList.size() > 0) {
				for (PrpCcoins prpCcoins : coinsList) {
					if ("1".equals(prpCcoins.getChiefFlag())) {
						httpServletRequest.setAttribute("chiefFlag", prpCcoins.getChiefFlag());
						break;
					}
				}
			}
		}
		// 获得保单默认的险别
		String defaultKindCode = this.getDaaClaimViewHelper().getDefaultKindCodeByPolicyDto(prpCitemKindList);
		httpServletRequest.setAttribute("defaultKindCode", defaultKindCode);
		// 增加投保人的信息
		prpLcompensate.setAppliName(prpCmain.getAppliName());
		double sumFacShare = reinsServiceManager.getReinsService().getSumFacShare(prpLcompensate.getPolicyNo(), new com.sinosoft.sysframework.common.datatype.DateTime(prpLcompensate.getDamageStartDate(), DateTime.YEAR_TO_DAY));
		httpServletRequest.setAttribute("tempReinsFlag", sumFacShare > 0 ? "1" : "0");
		String prpCinsuredBearer = "0";
		for (PrpCinsured prpCinsured : prpCinsuredList) {
			// 1表示个人，2表示单位客户
			if ("1".equals(prpCinsured.getInsuredType())) {
				for (PrpCinsuredNature prpCinsuredNature : prpCinsuredNatureList) {
					if (prpCinsured.getId().getSerialNo().intValue() == prpCinsuredNature.getId().getSerialNo().intValue()) {
						prpCinsured.setPrpCinsuredNature(prpCinsuredNature);
					}
				}
			}
			if("2".equals(prpCinsured.getInsuredFlag())){
				prpCinsuredBearer = prpCinsured.getBearer()==null?"0":prpCinsured.getBearer();
			}
		}
		httpServletRequest.setAttribute("prpCinsuredBearer", prpCinsuredBearer);
		httpServletRequest.setAttribute("prpCinsuredList", prpCinsuredList);
		//设置同险代号
		if(ConstantCodes.CLASSCODE_Q.equals(strRiskType)){
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", policyNo);
			List<PrpCaddress> prpCaddressList = this.prpCaddressService.findPrpCaddress(queryRule);
			for (PrpCaddress prpCaddress : prpCaddressList) {
				if (!CommonUtils.isEmpty(prpCaddress.getSameAddressNo())) {
					prpLcompensate.setSameAddressNo(prpCaddress.getSameAddressNo());
					break;
				}
			}
		}
		// 获取危险单位信息
		Collection<?> reinsDangerUnitCollection = reinsServiceManager.getReinsService().getDangerUnit(prpLclaim.getPolicyNo(), new com.sinosoft.sysframework.common.datatype.DateTime(prpLclaim.getDamageStartDate()));
		httpServletRequest.setAttribute("ReinsDangerUnitCollection", reinsDangerUnitCollection);
		// 理算报告的信息
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		StringBuffer tempContext1 = new StringBuffer("");
		StringBuffer tempContext4 = new StringBuffer("");
		StringBuffer tempContext5 = new StringBuffer("");
		StringBuffer tempContext05 = new StringBuffer("");
		String textType = "";
		for (PrpLctext prpLctext : prpLctextList) {
			textType = prpLctext.getId().getTextType();
			if ("1".equals(textType)) {
				tempContext1.append(prpLctext.getContext());
			} else if ("4".equals(textType)) {// 付款说明
				tempContext4.append(prpLctext.getContext());
			} else if ("5".equals(textType)) {// 意外健康险的理算计算过程
				tempContext5.append(prpLctext.getContext());
			} else if ("05".equals(textType)) {// 结案报告
				tempContext05.append(prpLctext.getContext());
			}
		}
		PrpLctext prpLctext = new PrpLctext();
		prpLctext.setContext(tempContext1.toString());
		prpLctext.getId().setTextType("1");
		httpServletRequest.setAttribute("prpLctext", prpLctext);
		// 付款说明
		PrpLctext prpLctextPayText = new PrpLctext();
		prpLctextPayText.setContext(tempContext4.toString());
		prpLctextPayText.getId().setTextType(PAY_TEXT);
		httpServletRequest.setAttribute("prpLctextPayText", prpLctextPayText);
		// 增加意外健康险的理算计算过程的保存
		PrpLctext prpLctextAccidentText = new PrpLctext();
		prpLctextAccidentText.setContext(tempContext5.toString());
		prpLctextAccidentText.getId().setTextType("5");
		httpServletRequest.setAttribute("prpLctextAccidentText", prpLctextAccidentText);
//		List<PrpLltext> prpLltextList = compensateDto.getPrpLltextList();
		// （結案類型：部分未結， 後續理算說明 ）- （結案類型：已結案，理算報告 - 結案報告）
//		StringBuffer tempContext08 = new StringBuffer("");
//		for (PrpLltext prpLltext : prpLltextList) {
//			textType = prpLltext.getId().getTextType();
//			if ("08".equals(textType)) {
//				tempContext08.append(prpLltext.getContext());
//			}
//		}
		PrpLltext prpLltext = new PrpLltext();
		prpLltext.setContext(tempContext05.toString());
		prpLltext.getId().setTextType("08");
		httpServletRequest.setAttribute("prpLltext", prpLltext);//未处理可以从立案文字表带入，正处理、已处理应从赔款计算文字表取数。
		for (PrpLloss prpLloss : compensateDto.getPrpLlossList()) {
			prpLloss.setKindName(this.getCodeService().translateKindCode("KindCode", prpLloss.getKindCode(), true));
			prpLloss.setCurrency2Name(this.getCodeService().translateCurrencyCode(prpLloss.getCurrency2(), true));
		}
		for (PrpLpersonLoss prpLpersonLoss : compensateDto.getPrpLpersonLossList()) {
			prpLpersonLoss.setInjuryGradeName(this.getCodeService().translateCodeCode("InjuryGrade", prpLpersonLoss.getInjuryGrade(), true));
		}
		// 查询相同保单号的出险次数
		this.daaRegistViewHelper.getSamePolicyRegistInfo(httpServletRequest, prpLcompensate.getPolicyNo(), prpLclaim.getRegistNo());
		httpServletRequest.setAttribute("prpLqualityCheckList", compensateDto.getPrpLqualityCheckList());
		// 添加案件性质
		prpLcompensate.setCaseType(compensateDto.getPrpLcompensate().getCaseType());
		prpLcompensate.setCaseTypeName(this.getCodeService().translateCodeCode("CaseCode", compensateDto.getPrpLcompensate().getClaimType(), true));
		// 设置主实赔信息内容到窗体表单
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
		httpServletRequest.setAttribute("riskType", strRiskType);
		// 在界面上显示险种名称
		httpServletRequest.setAttribute("riskCName", this.getCodeService().translateRiskCode(prpLcompensate.getRiskCode(), true));
		List<PrpLacciPerson> prpLacciPersonList = compensateDto.getPrpLacciPersonList();
		PrpLacciPerson prpLacciPerson = null;
		if (prpLacciPersonList != null && prpLacciPersonList.size() > 0) {
			prpLacciPerson = prpLacciPersonList.get(0);
		} else {
			prpLacciPerson = new PrpLacciPerson();
		}
		prpLacciPerson.setPrpLacciPersonList(prpLacciPersonList);
		
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
		//理算任務
		settingPAF4567(httpServletRequest,prpLcompensate);
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
		
		httpServletRequest.setAttribute("prpLacciPerson", prpLacciPerson);
//		prpLclaim = compensateDto.getPrpLclaim();
		String strBusinessNatureName = this.codeService.translateCodeCode("BusinessNature", prpLclaim.getBusinessNature(), true);
		prpLclaim.setBusinessNatureName(strBusinessNatureName);
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		compensateDto.setPrpLclaim(prpLclaim);
		setSubInfo(httpServletRequest, strRiskType , compensateDto , prpCitemKindList);
		// 设置理赔结论列表
		httpServletRequest.setAttribute("compensateResultMap", getCompensateResultList());
		// 在理算环节如果是核赔退回的单子，必须显示退回原因
		if (DataUtils.emptyToNull(status) != null) {
			if ("3".equals(status)) {
				String flowId = httpServletRequest.getParameter("swfLogFlowID");
				List<SwfNotion> swfNotionList = this.getSwfNotionService().findSwfNotion(QueryRule.getInstance().addSql(" flowid = '" + flowId + "'"));
				httpServletRequest.setAttribute("swfNotionList", swfNotionList);
			}
		}
		// 送审初复核初始化
		SendUndwrtViewHelper sendUndwrtViewHelper = new SendUndwrtViewHelper();
		sendUndwrtViewHelper.LoadingSendUndwrt(httpServletRequest, compensateNo, "compp");
		// 非车页面代码迁移
		// 需求變更#83二次調整   add by 中科軟 begin 
		httpServletRequest.setAttribute("reservedEstimateList", ConstantsCollection.reservedEstimateList);
		// 需求變更#83二次調整   add by 中科軟 end 
		this.showJspPage(httpServletRequest, editType);
		setClaimCredit(httpServletRequest, claimDto.getPrpLclaimCredit());
	}

	/**
	 * 保存实赔时实赔页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return compensateDto 实赔数据传输数据结构
	 * @throws Exception
	 */
	public CompensateDto viewToDto(HttpServletRequest httpServletRequest) throws SQLException, Exception {
		// 继承对compensate,compensateText表的赋值
		CompensateDto compensateDto = super.viewToDto(httpServletRequest);
//		String prpLcompensatePolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
//		PolicyDto policyDto = this.getPolicyService().findByPrimaryKey(prpLcompensatePolicyNo);
		// 增加投保人复制
		compensateDto.getPrpLcompensate().setAppliName((String) httpServletRequest.getParameter("prpLcompensateAppliName"));
		// 联共保信息PrpLcfeecoins
		String prpLcfeecoinsCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLcfeecoinsPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String prpLcfeecoinsRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String[] prpLcfeecoinsSerialNo = httpServletRequest.getParameterValues("prpLcoinsSerialNo");
		String[] prpLcfeecoinsChargeCode = httpServletRequest.getParameterValues("prpLcoinsChargeCode");
		String[] prpLcfeecoinsChargeName = httpServletRequest.getParameterValues("prpLcoinsChargeName");
		String[] prpLcfeecoinsCurrency = httpServletRequest.getParameterValues("prpLcoinsCurrency");
		String[] prpLcfeecoinsLossFeeType = httpServletRequest.getParameterValues("prpLcoinsLossFeeType");
		String[] prpLcfeecoinsCoinsCode = httpServletRequest.getParameterValues("prpLcoinsCoinsCode");
		String[] prpLcfeecoinsCoinsName = httpServletRequest.getParameterValues("prpLcoinsCoinsName");
		String[] prpLcfeecoinsCoinsType = httpServletRequest.getParameterValues("prpLcoinsCoinsType");
		String[] prpLcfeecoinsChiefFlag = httpServletRequest.getParameterValues("prpLcoinsChiefFlag");
		String[] prpLcfeecoinsCoinsRate = httpServletRequest.getParameterValues("prpLcoinsCoinsRate");
		String[] prpLcfeecoinsCoinsSumpaid = httpServletRequest.getParameterValues("prpLcoinsCoinsSumpaid");
		String[] prpLcfeecoinsSumpaid = httpServletRequest.getParameterValues("prpLcoinsSumpaid");
		String[] prpLcoinsFlag = httpServletRequest.getParameterValues("prpLcoinsFlag");
		List<PrpLcfeecoins> prpLcfeecoinsList = new ArrayList<PrpLcfeecoins>();
		PrpLcfeecoins prpLcfeecoins = null;
		if (prpLcfeecoinsSerialNo != null && prpLcfeecoinsSerialNo.length > 0) {
			for (int index = 1; index < prpLcfeecoinsSerialNo.length; index++) {
				prpLcfeecoins = new PrpLcfeecoins();
				prpLcfeecoins.getId().setBusinessNo(prpLcfeecoinsCompensateNo);
				prpLcfeecoins.setPolicyNo(prpLcfeecoinsPolicyNo);
				prpLcfeecoins.setRiskCode(prpLcfeecoinsRiskCode);
				prpLcfeecoins.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLcfeecoinsSerialNo[index])));
				prpLcfeecoins.setChargeCode(prpLcfeecoinsChargeCode[index]);
				prpLcfeecoins.setChargeName(prpLcfeecoinsChargeName[index]);
				prpLcfeecoins.setCurrency(prpLcfeecoinsCurrency[index]);
				prpLcfeecoins.setLossFeeType(prpLcfeecoinsLossFeeType[index]);
				prpLcfeecoins.setCoinsCode(prpLcfeecoinsCoinsCode[index]);
				prpLcfeecoins.setCoinsName(prpLcfeecoinsCoinsName[index]);
				prpLcfeecoins.setCoinsType(prpLcfeecoinsCoinsType[index]);
				prpLcfeecoins.setChiefFlag(prpLcfeecoinsChiefFlag[index]);
				prpLcfeecoins.setCoinsRate(Double.parseDouble(DataUtils.nullToZero(prpLcfeecoinsCoinsRate[index])));
				prpLcfeecoins.setCoinsSumPaid(Double.parseDouble(DataUtils.nullToZero(prpLcfeecoinsCoinsSumpaid[index])));
				prpLcfeecoins.setSumPaid(Double.parseDouble(DataUtils.nullToZero(prpLcfeecoinsSumpaid[index])));
				prpLcfeecoins.setFlag(prpLcoinsFlag[index]);
				prpLcfeecoinsList.add(prpLcfeecoins);
			}
		}
		// 联共保信息收集结束
		compensateDto.setPrpLcfeecoinsList(prpLcfeecoinsList);
		// 索赔申请人信息
		// 从界面得到输入数组
		String proposerClaimNo = httpServletRequest.getParameter("claimNo");
		String proposerPolicyNo = httpServletRequest.getParameter("prpLclaimPolicyNo");
		String[] proposerName = httpServletRequest.getParameterValues("proposerName");
		String[] proposerSerialNo = httpServletRequest.getParameterValues("prpLacciPersonSerialNo");
		String[] proposerIdentifyNumber = httpServletRequest.getParameterValues("proposerIdentifyNumber");
		String[] proposerRelation = httpServletRequest.getParameterValues("relationCode");
		String[] proposerPhone = httpServletRequest.getParameterValues("proposerPhone");
		String[] proposerAddress = httpServletRequest.getParameterValues("proposerAddress");
		String proposerFamilyNo = httpServletRequest.getParameter("personFamilyNo");
		// 对象赋值
		List<PrpLacciPerson> prpLacciPersonList = new ArrayList<PrpLacciPerson>();
		if (proposerSerialNo != null && proposerSerialNo.length > 0) {
			PrpLacciPerson prpLacciPerson = null;
			for (int index = 1; index < proposerSerialNo.length; index++) {
				prpLacciPerson = new PrpLacciPerson();
				prpLacciPerson.setAcciName(proposerName[index]);
				prpLacciPerson.getId().setCertiNo(proposerClaimNo);
				prpLacciPerson.getId().setCertiType("03");
				prpLacciPerson.setPolicyNo(proposerPolicyNo);
				prpLacciPerson.setFlag("1"); // 标志是索赔人
				prpLacciPerson.setIdentifyNumber(proposerIdentifyNumber[index]);
				prpLacciPerson.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(proposerSerialNo[index])));
				prpLacciPerson.setAddress(proposerAddress[index]);
				prpLacciPerson.setFamilyNo(0); // 家庭序号
				prpLacciPerson.setPhone(proposerPhone[index]);
				prpLacciPerson.setRelationCode(proposerRelation[index]);
				String relationName = "";
				if ("1".equals(proposerRelation[index])) {
					relationName = "被保險人本人";
				} else if ("2".equals(proposerRelation[index])) {
					relationName = "指定受益人";
				} else if ("3".equals(proposerRelation[index])) {
					relationName = "被保險人之繼承人";
				} else if (proposerRelation[index].equals("4")) {
					relationName = "被保險人之監護人";
				}
				prpLacciPerson.setRelationName(relationName);
				prpLacciPerson.setFamilyNo(DataUtils.getInteger(DataUtils.emptyToNull(proposerFamilyNo)));
				// 加入集合
				prpLacciPersonList.add(prpLacciPerson);
			}
		}
		// 意健险立案集合中加入索赔申请人
		compensateDto.setPrpLacciPersonList(prpLacciPersonList);
		// 赔付标的信息prpLlossDto
		// 从界面得到输入数组
		String prpLlossDtoCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLlossDtoRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLlossDtoPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String[] prpLlossDtoSerialNo = httpServletRequest.getParameterValues("prpLlossDtoSerialNo");
		String[] prpLlossDtoItemKindNo = httpServletRequest.getParameterValues("prpLlossDtoItemKindNo");
		String[] prpLlossDtoFamilyNo = httpServletRequest.getParameterValues("prpLlossDtoFamilyNo");
		String[] prpLlossDtoFamilyName = httpServletRequest.getParameterValues("prpLlossDtoFamilyName");
		String[] prpLlossDtoKindCode = httpServletRequest.getParameterValues("prpLlossDtoKindCode");
		String[] prpLlossDtoLicenseNo = httpServletRequest.getParameterValues("prpLlossDtoLicenseNo");
		String[] prpLlossDtoItemCode = httpServletRequest.getParameterValues("prpLlossDtoItemCode");
		String[] prpLlossDtoLossName = httpServletRequest.getParameterValues("prpLlossDtoLossName");
		String[] prpLlossDtoItemAddress = httpServletRequest.getParameterValues("prpLlossDtoItemAddress");
		String[] prpLlossDtoFeeTypeCode = httpServletRequest.getParameterValues("prpLlossDtoFeeTypeCode");
		String[] prpLlossDtoFeeTypeName = httpServletRequest.getParameterValues("prpLlossDtoFeeTypeName");
		String[] prpLlossDtoLossQuantity = httpServletRequest.getParameterValues("prpLlossDtoLossQuantity");
		String[] prpLlossDtoUnit = httpServletRequest.getParameterValues("prpLlossDtoUnit");
		String[] prpLlossDtoUnitPrice = httpServletRequest.getParameterValues("prpLlossDtoUnitPrice");
		String[] prpLlossDtoBuyDate = httpServletRequest.getParameterValues("prpLlossDtoBuyDate");
		String[] prpLlossDtoDepreRate = httpServletRequest.getParameterValues("prpLlossDtoDepreRate");
		String[] prpLlossDtoCurrency = httpServletRequest.getParameterValues("prpLlossDtoCurrency");
		String[] prpLlossDtoAmount = httpServletRequest.getParameterValues("prpLlossDtoAmount");
		String[] prpLlossDtoCurrency1 = httpServletRequest.getParameterValues("prpLlossDtoCurrency1");
		String[] prpLlossDtoItemValue = httpServletRequest.getParameterValues("prpLlossDtoItemValue");
		String[] prpLlossDtoCurrency2 = httpServletRequest.getParameterValues("prpLlossDtoCurrency2");
		String[] prpLlossDtoSumLoss = httpServletRequest.getParameterValues("prpLlossDtoSumLoss");
		String[] prpLlossDtoSumRest = httpServletRequest.getParameterValues("prpLlossDtoSumRest");
		String[] prpLlossDtoIndemnityDutyRate = httpServletRequest.getParameterValues("prpLlossDtoIndemnityDutyRate");
		String[] prpLlossDtoClaimRate = httpServletRequest.getParameterValues("prpLlossDtoClaimRate");
		String[] prpLlossDtoCurrency3 = httpServletRequest.getParameterValues("prpLlossDtoCurrency3");
		String[] prpLlossDtoCurrency4 = httpServletRequest.getParameterValues("prpLlossDtoCurrency4");
		String[] prpLlossDtoDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoDeductibleRate");
		String[] prpLlossDtoDeductible = httpServletRequest.getParameterValues("prpLlossDtoDeductible");
		String[] prpLlossDtoSumRealPay = httpServletRequest.getParameterValues("prpLlossDtoSumRealPay");
		String[] prpLlossDtoFlag = httpServletRequest.getParameterValues("prpLlossDtoFlag");
		// 危险单位序号
		String[] prpLlossDtoDangerNo = httpServletRequest.getParameterValues("prpLlossDtoDangerNo");
		// 危险单位序号
		// 賠付對象讯息
		String[] prpLlossDtoPayObjectSerialNo = httpServletRequest.getParameterValues("prpLlossDtoPayObjectSerialNo");
		// 赔付币prpLlossDtoCurrency4别对本位币（新台币）的汇率
		String[] prpLlossDtoExchRate = httpServletRequest.getParameterValues("prpLlossDtoExchRate");
		// 需求變更#83二次調整   add by 中科軟 begin 
		String[] prpLlossDtoReservedEstimate = httpServletRequest.getParameterValues("prpLlossDtoReservedEstimate");
		// 需求變更#83二次調整   add by 中科軟 end 
		// 对象赋值
		double exchRate = 1d;
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		if (prpLlossDtoSerialNo != null) {
			PrpLloss prpLloss = null;
			for (int index = 1; index < prpLlossDtoSerialNo.length; index++) {
				prpLloss = new PrpLloss();
				prpLloss.setPolicyNo(prpLlossDtoPolicyNo);
				prpLloss.setRiskCode(prpLlossDtoRiskCode);
				prpLloss.getId().setCompensateNo(prpLlossDtoCompensateNo);
				prpLloss.getId().setSerialNo(index);
				prpLloss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLlossDtoItemKindNo[index])));
				prpLloss.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLlossDtoFamilyNo[index])));
				prpLloss.setFamilyName(prpLlossDtoFamilyName[index]);
				prpLloss.setKindCode(prpLlossDtoKindCode[index]);
				prpLloss.setLicenseNo(prpLlossDtoLicenseNo[index]);
				prpLloss.setItemCode(prpLlossDtoItemCode[index]);
				prpLloss.setLossName(prpLlossDtoLossName[index]);
				prpLloss.setItemAddress(prpLlossDtoItemAddress[index]);
				prpLloss.setFeeTypeCode(prpLlossDtoFeeTypeCode[index]);
				prpLloss.setFeeTypeName(prpLlossDtoFeeTypeName[index]);
				prpLloss.setLossQuantity(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoLossQuantity[index])));
				prpLloss.setUnit(prpLlossDtoUnit[index]);
				prpLloss.setUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoUnitPrice[index])));
				prpLloss.setBuyDate(new DateTime(prpLlossDtoBuyDate[index]));
				prpLloss.setDepreRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDepreRate[index])));
				prpLloss.setCurrency(prpLlossDtoCurrency[index]);
				prpLloss.setAmount(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoAmount[index])));
				prpLloss.setCurrency1(prpLlossDtoCurrency1[index]);
				prpLloss.setItemValue(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoItemValue[index])));
				prpLloss.setCurrency2(prpLlossDtoCurrency2[index]);
				prpLloss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumLoss[index])));
				prpLloss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumRest[index])));
				prpLloss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoIndemnityDutyRate[index])));
				prpLloss.setClaimRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoClaimRate[index])));
				prpLloss.setCurrency3(prpLlossDtoCurrency3[index]);
				prpLloss.setDeductiblerate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDeductibleRate[index])));
				prpLloss.setDeductible(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDeductible[index])));
				prpLloss.setCurrency4(prpLlossDtoCurrency4[index]);//
				prpLloss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumRealPay[index])));
				prpLloss.setFlag(prpLlossDtoFlag[index]);
				// 加入集合
				// 危险单位序号
				prpLloss.setDangerNo(Integer.parseInt(DataUtils.nullToZero(prpLlossDtoDangerNo[index])));
				// 需求變更#83二次調整   add by 中科軟 begin 
				prpLloss.setReservedEstimate(prpLlossDtoReservedEstimate[index]);
				// 需求變更#83二次調整   add by 中科軟 end 
				prpLloss.setPayObjectSerialNo(prpLlossDtoPayObjectSerialNo[index]);
				exchRate = 1d;// 默認為賠付幣別（NTD）對本位幣（NTD）的匯率
				if (!ConstantCodes.LOCAL_CURRENCY.equals(prpLloss.getCurrency())) {// 本位幣則
					exchRate = Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLlossDtoExchRate, index)));
				}
				prpLloss.setExchRate(exchRate);
				prpLlossList.add(prpLloss);
			}
		}
		// 赔付标的信息
		compensateDto.setPrpLlossList(prpLlossList);
		// 从界面得到输入数组
		String prpLpersonLossCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLpersonLossRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLpersonLossPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");

		/** 赔付人员处理 */
		// 理赔拆分危险单位
		String[] prpLpersonLossSerialNo = httpServletRequest.getParameterValues("prpLpersonLossSerialNo");
		String[] personLossSerialNo = httpServletRequest.getParameterValues("personLossSerialNo");
		String[] prpLpersonLossDangerNo = httpServletRequest.getParameterValues("prpLpersonLossDangerNo");
		String[] personLossPersonNo = httpServletRequest.getParameterValues("personLossPersonNo");
		String[] prpLpersonLossPersonName = httpServletRequest.getParameterValues("prpLpersonLossPersonName");
		String[] prpLpersonLossSex = httpServletRequest.getParameterValues("prpLpersonLossSex");
		String[] prpLpersonLossAge = httpServletRequest.getParameterValues("prpLpersonLossAge");
		String[] prpLpersonLossIdentifyNumber = httpServletRequest.getParameterValues("prpLpersonLossIdentifyNumber");
		// 险别最大赔付额
		String[] prpLpersonLossMaxPaid = httpServletRequest.getParameterValues("prpLpersonLossMaxPaid");
		String[] prpLpersonLossHisPaid = httpServletRequest.getParameterValues("prpLpersonLossHisPaid");
		String[] prpLpersonLossPoliceName = httpServletRequest.getParameterValues("prpLpersonLossPoliceName");
		String[] prpLpersonLossPoliceUnits = httpServletRequest.getParameterValues("prpLpersonLossPoliceUnits");
		/** 就诊医院 */
		String[] hospitalPersonNo = httpServletRequest.getParameterValues("hospitalPersonNo");
		String[] prpLpersonHospitalHospitalCode = httpServletRequest.getParameterValues("prpLpersonHospitalHospitalCode");
		String[] prpLpersonHospitalHospitalName = httpServletRequest.getParameterValues("prpLpersonHospitalHospitalName");
		String[] prpLpersonHospitalInHospDate = httpServletRequest.getParameterValues("prpLpersonHospitalInHospDate");
		String[] prpLpersonHospitalOutHospDate = httpServletRequest.getParameterValues("prpLpersonHospitalOutHospDate");
		String[] prpLpersonHospitalDoctor = httpServletRequest.getParameterValues("prpLpersonHospitalDoctor");
		String[] prpLpersonHospitalDiagnosisDivision = httpServletRequest.getParameterValues("prpLpersonHospitalDiagnosisDivision");
		String[] prpLpersonHospitalDiagnosisName = httpServletRequest.getParameterValues("prpLpersonHospitalDiagnosisName");
		// 水险添加
		String[] prpLpersonLossCertificateCode = httpServletRequest.getParameterValues("prpLpersonLossCertificateCode");// 证件类型
		String[] prpLpersonLossMedicalCode = httpServletRequest.getParameterValues("prpLpersonLossMedicalCode");// 是否以健保身份就診
		// Y是，N否
		String[] prpLpersonLossCasualties = httpServletRequest.getParameterValues("prpLpersonLossCasualties");// 傷亡情形
		String[] prpLpersonLossExchRate = httpServletRequest.getParameterValues("prpLpersonLossExchRate");// 汇率
		String[] prpLpersonLossBirthday = httpServletRequest.getParameterValues("prpLpersonLossBirthday");// 生日
		String[] prpLpersonLossMobilePhone = httpServletRequest.getParameterValues("prpLpersonLossMobilePhone");// 受害人电话
		String[] prpLpersonLossHospitalCode = httpServletRequest.getParameterValues("prpLpersonLossHospitalCode");
		String[] prpLpersonLossHospitalName = httpServletRequest.getParameterValues("prpLpersonLossHospitalName");
		String[] prpLpersonLossDoctor = httpServletRequest.getParameterValues("prpLpersonLossDoctor");
		String[] medicDeathFlag = httpServletRequest.getParameterValues("medicDeathFlag");
		String[] prpLpersonLossSumDefPay = httpServletRequest.getParameterValues("prpLpersonLossSumDefPay");// 核定賠償

		/** 赔付险别信息 */
		String[] prpLpersonLossPersonNo = httpServletRequest.getParameterValues("prpLpersonLossPersonNo");
		String[] prpLpersonLossItemKindNo = httpServletRequest.getParameterValues("prpLpersonLossItemKindNo");
		String[] prpLpersonLossFamilyNo = httpServletRequest.getParameterValues("prpLpersonLossFamilyNo");
		String[] prpLpersonLossFamilyName = httpServletRequest.getParameterValues("prpLpersonLossFamilyName");
		String[] prpLpersonLossKindCode = httpServletRequest.getParameterValues("prpLpersonLossKindCode");
		String[] prpLpersonLossLiabCode = httpServletRequest.getParameterValues("prpLpersonLossLiabCode");
		String[] prpLpersonLossLiabName = httpServletRequest.getParameterValues("prpLpersonLossLiabName");
		String[] prpLpersonLossJobCode = httpServletRequest.getParameterValues("prpLpersonLossJobCode");
		String[] prpLpersonLossJobName = httpServletRequest.getParameterValues("prpLpersonLossJobName");
		String[] prpLpersonLossLiabDetailCode = httpServletRequest.getParameterValues("prpLpersonLossLiabDetailCode");
		String[] prpLpersonLossLiabDetailName = httpServletRequest.getParameterValues("prpLpersonLossLiabDetailName");
		String[] prpLpersonLossItemAddress = httpServletRequest.getParameterValues("prpLpersonLossItemAddress");
		String[] prpLpersonLossLossQuantity = httpServletRequest.getParameterValues("prpLpersonLossLossQuantity");
		String[] prpLpersonLossUnit = httpServletRequest.getParameterValues("prpLpersonLossUnit");
		String[] prpLpersonLossUnitAmount = httpServletRequest.getParameterValues("prpLpersonLossUnitAmount");
		String[] prpLpersonLossCurrency = httpServletRequest.getParameterValues("prpLpersonLossCurrency");
		String[] prpLpersonLossAmount = httpServletRequest.getParameterValues("prpLpersonLossAmount");
		String[] prpLpersonLossCurrency1 = httpServletRequest.getParameterValues("prpLpersonLossCurrency1");
		String[] prpLpersonLossItemValue = httpServletRequest.getParameterValues("prpLpersonLossItemValue");
		String[] prpLpersonLossCurrency2 = httpServletRequest.getParameterValues("prpLpersonLossCurrency2");
		String[] prpLpersonLossSumLoss = httpServletRequest.getParameterValues("prpLpersonLossSumLoss");
		String[] prpLpersonLossSumRest = httpServletRequest.getParameterValues("prpLpersonLossSumRest");
		String[] prpLpersonLossIndemnityDutyRate = httpServletRequest.getParameterValues("prpLpersonLossIndemnityDutyRate");
		String[] prpLpersonLossClaimRate = httpServletRequest.getParameterValues("prpLpersonLossClaimRate");
		String[] prpLpersonLossCurrency3 = httpServletRequest.getParameterValues("prpLpersonLossCurrency3");
		String[] prpLpersonLossDeductibleRate = httpServletRequest.getParameterValues("prpLpersonLossDeductibleRate");
		String[] prpLpersonLossDeductible = httpServletRequest.getParameterValues("prpLpersonLossDeductible");
		String[] prpLpersonLossCurrency4 = httpServletRequest.getParameterValues("prpLpersonLossCurrency4");
		String[] prpLpersonLossSumRealPay = httpServletRequest.getParameterValues("prpLpersonLossSumRealPay");
		String[] prpLpersonLossFlag = httpServletRequest.getParameterValues("prpLpersonLossFlag");
		// 賠付對象訊息
		String[] prpLpersonLossPayObjectSerialNo = httpServletRequest.getParameterValues("prpLpersonLossPayObjectSerialNo");
		String[] prpLpersonLossContractingScope = httpServletRequest.getParameterValues("prpLpersonLossContractingScope");
		String[] prpLpersonLossPaymentType = httpServletRequest.getParameterValues("prpLpersonLossPaymentType");
		String[] prpLpersonLossPaymentType1 = httpServletRequest.getParameterValues("prpLpersonLossPaymentType1");
		String[] prpLpersonLossPaymentType2 = httpServletRequest.getParameterValues("prpLpersonLossPaymentType2");
		String[] prpLpersonLossPaymentRate = httpServletRequest.getParameterValues("prpLpersonLossPaymentRate");
		String[] prpLpersonLossPaymentContent = httpServletRequest.getParameterValues("prpLpersonLossPaymentContent");
		String[] prpLpersonLossFractureSite = httpServletRequest.getParameterValues("prpLpersonLossFractureSite");
		String[] prpLpersonLossNotHospitalDays = httpServletRequest.getParameterValues("prpLpersonLossNotHospitalDays");
		String[] prpLpersonLossFractureDegree = httpServletRequest.getParameterValues("prpLpersonLossFractureDegree");
		// String[] prpLpersonLossRemark =
		// httpServletRequest.getParameterValues("prpLpersonLossRemark");
		// 伤残等级,入院日期,出院日期,住院天数
		String[] prpLpersonLossInjuryGrade = httpServletRequest.getParameterValues("prpLpersonLossInjuryGrade");
		// String[] prpLpersonLossInHospDate =
		// httpServletRequest.getParameterValues("prpLpersonLossInHospDate");
		// String[] prpLpersonLossOutHospDate =
		// httpServletRequest.getParameterValues("prpLpersonLossOutHospDate");
		String[] prpLpersonLossHospitalDays = httpServletRequest.getParameterValues("prpLpersonLossHospitalDays");
		String[] prpLpersonLossDeathDate = httpServletRequest.getParameterValues("prpLpersonLossDeathDate");
		String[] prpLpersonLossDeathAddressCode = httpServletRequest.getParameterValues("prpLpersonLossDeathAddressCode");
		String[] prpLpersonLossDeathAddressName = httpServletRequest.getParameterValues("prpLpersonLossDeathAddressName");
		String[] prpLpersonLossProsecutorsOffice = httpServletRequest.getParameterValues("prpLpersonLossProsecutorsOffice");
		String[] prpLpersonLossDeathPlace = httpServletRequest.getParameterValues("prpLpersonLossDeathPlace");
		String[] prpLpersonLossDeathManner = httpServletRequest.getParameterValues("prpLpersonLossDeathManner");
		String[] prpLpersonLossProsecutor = httpServletRequest.getParameterValues("prpLpersonLossProsecutor");
		String[] prpLpersonLossDeathCertificateDate = httpServletRequest.getParameterValues("prpLpersonLossDeathCertificateDate");
		String[] prpLpersonLossCourtDoctor = httpServletRequest.getParameterValues("prpLpersonLossCourtDoctor");
		/** 残废项目 */
		String[] prpLpersonLossInjuryCode = httpServletRequest.getParameterValues("prpLpersonLossInjuryCode");
		String[] prpLpersonLossInjuryName = httpServletRequest.getParameterValues("prpLpersonLossInjuryName");
		/** 残废程度 */
		String[] prpLpersonLossInjuryItemCode = httpServletRequest.getParameterValues("prpLpersonLossInjuryItemCode");
		String[] prpLpersonLossInjuryItemName = httpServletRequest.getParameterValues("prpLpersonLossInjuryItemName");
		String[] prpLpersonLossAddPremium = httpServletRequest.getParameterValues("prpLpersonLossAddPremium");// 补充保费
		// 需求變更#83二次調整   add by 中科軟 begin 
		String[] prpLpersonLossReservedEstimate = httpServletRequest.getParameterValues("prpLpersonLossReservedEstimate");// 保留預估
		// 需求變更#83二次調整   add by 中科軟 end 
		// 加入危险单位处理
		// 目前只有一个危险单位，所以和标的信息放在一起处理，如果，有多个危险单位必须放入危险单位信息里面处理！
		List<PrpLDangerItem> prpLprpLdangerItemList = new ArrayList<PrpLDangerItem>(); // 理赔的危险单位信息表
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<PrpLpersonLoss> prpLpersonLossList = new ArrayList<PrpLpersonLoss>();
		List<PrpLpersonHospital> prpLpersonHospitalList = new ArrayList<PrpLpersonHospital>();
		PrpLpersonLoss prpLpersonLoss = null;
		PrpLpersonHospital prpLpersonHospital = null;
		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLpersonLossRiskCode);
		if ("E".equals(strRiskType)) {
			if (prpLpersonLossPersonNo != null && prpLpersonLossPersonNo.length > 0) {
				for (int index = 1; index < prpLpersonLossPersonNo.length; index++) {
					prpLpersonLoss = new PrpLpersonLoss();
					prpLpersonLoss.setPolicyNo(prpLpersonLossPolicyNo);
					prpLpersonLoss.setRiskCode(prpLpersonLossRiskCode);
					prpLpersonLoss.getId().setCompensateNo(prpLpersonLossCompensateNo);
					prpLpersonLoss.getId().setSerialNo(index);
					prpLpersonLoss.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossFamilyNo[index])));
					prpLpersonLoss.setLiabCode(prpLpersonLossLiabCode[index]);
					prpLpersonLoss.setLiabName(prpLpersonLossLiabName[index]);
					prpLpersonLoss.setJobCode(prpLpersonLossJobCode[index]);
					prpLpersonLoss.setJobName(prpLpersonLossJobName[index]);
					prpLpersonLoss.setItemAddress(prpLpersonLossItemAddress[index]);
					prpLpersonLoss.setUnit(prpLpersonLossUnit[index]);
					prpLpersonLoss.setCurrency(prpLpersonLossCurrency[index]);
					prpLpersonLoss.setAmount(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossAmount[index])));
					prpLpersonLoss.setCurrency1(prpLpersonLossCurrency1[index]);
					prpLpersonLoss.setItemValue(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossItemValue[index])));
					prpLpersonLoss.setCurrency2(prpLpersonLossCurrency2[index]);
					prpLpersonLoss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRest[index])));
					prpLpersonLoss.setClaimRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossClaimRate[index])));
					prpLpersonLoss.setDeductible(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductible[index])));
					prpLpersonLoss.setCurrency4(prpLpersonLossCurrency4[index]);
					prpLpersonLoss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRealPay[index])));
					prpLpersonLoss.setFlag(prpLpersonLossFlag[index]);
					prpLpersonLoss.setUnitAmount(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossUnitAmount[index])));

					prpLpersonLoss.setLossQuantity(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossLossQuantity[index])));
					prpLpersonLoss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumLoss[index])));
					prpLpersonLoss.setCurrency3(prpLpersonLossCurrency3[index]);
					prpLpersonLoss.setFamilyName(prpLpersonLossFamilyName[index]);
					prpLpersonLoss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossIndemnityDutyRate[index])));
					prpLpersonLoss.setDeductiblerate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductibleRate[index])));
					prpLpersonLoss.setPersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossPersonNo[index])));
					prpLpersonLoss.setKindCode(DataUtils.nullToZero(prpLpersonLossKindCode[index]));
					prpLpersonLoss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossItemKindNo[index])));
					prpLpersonLoss.setPayObjectSerialNo(DataUtils.dbNullToEmpty(prpLpersonLossPayObjectSerialNo[index]));
					// 需求變更#83二次調整   add by 中科軟 begin 
					prpLpersonLoss.setReservedEstimate(DataUtils.dbNullToEmpty(prpLpersonLossReservedEstimate[index]));
					// 需求變更#83二次調整   add by 中科軟 end 
					prpLpersonLoss.setContractingScope(prpLpersonLossContractingScope[index]);
					prpLpersonLoss.setPaymentType(prpLpersonLossPaymentType[index]);
					prpLpersonLoss.setPaymentType1(prpLpersonLossPaymentType1[index]);
					prpLpersonLoss.setPaymentType2(prpLpersonLossPaymentType2[index]);
					prpLpersonLoss.setPaymentRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossPaymentRate[index])));
					prpLpersonLoss.setPaymentContent(prpLpersonLossPaymentContent[index]);
					prpLpersonLoss.setFractureSite(prpLpersonLossFractureSite[index]);
					prpLpersonLoss.setNotHospitalDays(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossNotHospitalDays[index])));
					prpLpersonLoss.setFractureDegree(prpLpersonLossFractureDegree[index]);
					if (!StringUtil.isBlank(prpLpersonLossDeathDate[index])) {
						prpLpersonLoss.setDeathDate(new DateTime(prpLpersonLossDeathDate[index]));
					}
					prpLpersonLoss.setDeathAddressCode(prpLpersonLossDeathAddressCode[index]);
					prpLpersonLoss.setDeathAddressName(prpLpersonLossDeathAddressName[index]);
					prpLpersonLoss.setProsecutorsOffice(prpLpersonLossProsecutorsOffice[index]);
					prpLpersonLoss.setDeathPlace(prpLpersonLossDeathPlace[index]);
					prpLpersonLoss.setDeathManner(prpLpersonLossDeathManner[index]);
					prpLpersonLoss.setProsecutor(prpLpersonLossProsecutor[index]);
					if (!StringUtil.isBlank(prpLpersonLossDeathCertificateDate[index])) {
						prpLpersonLoss.setDeathCertificateDate(new DateTime(prpLpersonLossDeathCertificateDate[index]));
					}
					prpLpersonLoss.setCourtDoctor(prpLpersonLossCourtDoctor[index]);

					for (int index2 = 1; index2 < personLossPersonNo.length; index2++) {
						if (prpLpersonLossPersonNo[index].equals(personLossPersonNo[index2])) {
							prpLpersonLoss.setDangerNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossDangerNo[index2])));
							prpLpersonLoss.setAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossAge[index2])));
							prpLpersonLoss.setPersonName(prpLpersonLossPersonName[index2]);
							prpLpersonLoss.setSex(prpLpersonLossSex[index2]);
							prpLpersonLoss.setIdentifyNumber(prpLpersonLossIdentifyNumber[index2]);
							prpLpersonLoss.setDblMaxPaid(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossMaxPaid[index2])));
							prpLpersonLoss.setMaxpaid(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossMaxPaid[index2])));
							prpLpersonLoss.setHispaid(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossHisPaid[index2])));
							prpLpersonLoss.setPoliceName(prpLpersonLossPoliceName[index2]);
							prpLpersonLoss.setPoliceUnits(prpLpersonLossPoliceUnits[index2]);
						}
					}
					prpLpersonLossList.add(prpLpersonLoss);
				}
				for (int index = 1; index < hospitalPersonNo.length; index++) {
					prpLpersonHospital = new PrpLpersonHospital();
					prpLpersonHospital.getId().setCompensateNo(prpLpersonLossCompensateNo);
					prpLpersonHospital.getId().setSerialNo(index);
					prpLpersonHospital.setPersonNo(Integer.parseInt(hospitalPersonNo[index]));
					prpLpersonHospital.setHospitalCode(prpLpersonHospitalHospitalCode[index]);
					prpLpersonHospital.setHospitalName(prpLpersonHospitalHospitalName[index]);
					if (!StringUtil.isBlank(prpLpersonHospitalInHospDate[index])) {
						prpLpersonHospital.setInHospDate(new DateTime(prpLpersonHospitalInHospDate[index]));
					}
					if (!StringUtil.isBlank(prpLpersonHospitalOutHospDate[index])) {
						prpLpersonHospital.setOutHospDate(new DateTime(prpLpersonHospitalOutHospDate[index]));
					}
					prpLpersonHospital.setDoctor(prpLpersonHospitalDoctor[index]);
					prpLpersonHospital.setDiagnosisDivision(prpLpersonHospitalDiagnosisDivision[index]);
					prpLpersonHospital.setDiagnosisName(prpLpersonHospitalDiagnosisName[index]);
					prpLpersonHospitalList.add(prpLpersonHospital);
				}
			}
		} else if ("D".equals(strRiskType)) {
			if (personLossSerialNo != null && personLossSerialNo.length > 0) {
				for (int index = 1; index < personLossSerialNo.length; index++) {
					prpLpersonLoss = new PrpLpersonLoss();
					prpLpersonLoss.setPolicyNo(prpLpersonLossPolicyNo);
					prpLpersonLoss.setRiskCode(prpLpersonLossRiskCode);
					prpLpersonLoss.getId().setCompensateNo(prpLpersonLossCompensateNo);
					prpLpersonLoss.getId().setSerialNo(index);
					prpLpersonLoss.setIdentifyNumber(prpLpersonLossIdentifyNumber[index]);
					prpLpersonLoss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossItemKindNo[index])));
					prpLpersonLoss.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossFamilyNo[index])));
					prpLpersonLoss.setLiabCode(prpLpersonLossLiabCode[index]);
					prpLpersonLoss.setLiabName(prpLpersonLossLiabName[index]);
					prpLpersonLoss.setJobCode(prpLpersonLossJobCode[index]);
					prpLpersonLoss.setJobName(prpLpersonLossJobName[index]);
					prpLpersonLoss.setItemAddress(prpLpersonLossItemAddress[index]);
					prpLpersonLoss.setUnit(prpLpersonLossUnit[index]);
					prpLpersonLoss.setCurrency(prpLpersonLossCurrency[index]);
					prpLpersonLoss.setAmount(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLpersonLossAmount, index))));
					prpLpersonLoss.setCurrency1(prpLpersonLossCurrency1[index]);
					prpLpersonLoss.setItemValue(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossItemValue[index])));
					prpLpersonLoss.setCurrency2(prpLpersonLossCurrency2[index]);
					prpLpersonLoss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRest[index])));
					prpLpersonLoss.setClaimRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossClaimRate[index])));
					prpLpersonLoss.setDeductible(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductible[index])));
					prpLpersonLoss.setCurrency4(prpLpersonLossCurrency4[index]);
					prpLpersonLoss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRealPay[index])));
					prpLpersonLoss.setFlag(prpLpersonLossFlag[index]);
					prpLpersonLoss.setLiabDetailCode(prpLpersonLossLiabDetailCode[index]);
					prpLpersonLoss.setLiabDetailName(prpLpersonLossLiabDetailName[index]);
					prpLpersonLoss.setUnitAmount(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossUnitAmount[index])));
					prpLpersonLoss.setHospitalDays(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossHospitalDays[index])));
					prpLpersonLoss.setLossQuantity(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossLossQuantity[index])));
					prpLpersonLoss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumLoss[index])));
					for (int index2 = 0; index2 < prpLpersonLossSerialNo.length; index2++) {
						if (prpLpersonLossSerialNo[index2].equals(personLossSerialNo[index])) {
							prpLpersonLoss.setSex(prpLpersonLossSex[index2]);
							prpLpersonLoss.setPersonName(prpLpersonLossPersonName[index2]);
							prpLpersonLoss.setAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossAge[index2])));
							prpLpersonLoss.setCurrency3(prpLpersonLossCurrency3[index2]);
							prpLpersonLoss.setFamilyName(prpLpersonLossFamilyName[index2]);// 车牌号码
							prpLpersonLoss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossIndemnityDutyRate[index2])));
							prpLpersonLoss.setKindCode(prpLpersonLossKindCode[index2]);
							prpLpersonLoss.setDeductiblerate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductibleRate[index2])));
							prpLpersonLoss.setPersonNo(index2);
							break;
						}
					}
					// 加入集合
					prpLpersonLossList.add(prpLpersonLoss);
				}
			}
		} else {
			if (personLossSerialNo != null && personLossSerialNo.length > 0) {
				for (int index = 1; index < personLossSerialNo.length; index++) {
					prpLpersonLoss = new PrpLpersonLoss();
					prpLpersonLoss.setPolicyNo(prpLpersonLossPolicyNo);
					prpLpersonLoss.setRiskCode(prpLpersonLossRiskCode);
					prpLpersonLoss.getId().setCompensateNo(prpLpersonLossCompensateNo);
					prpLpersonLoss.getId().setSerialNo(index);
					prpLpersonLoss.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossFamilyNo[index])));
					prpLpersonLoss.setLiabCode(prpLpersonLossLiabCode[index]);
					prpLpersonLoss.setLiabName(prpLpersonLossLiabName[index]);
					prpLpersonLoss.setJobCode(prpLpersonLossJobCode[index]);
					prpLpersonLoss.setJobName(prpLpersonLossJobName[index]);
					prpLpersonLoss.setItemAddress(prpLpersonLossItemAddress[index]);
					prpLpersonLoss.setUnit(prpLpersonLossUnit[index]);
					prpLpersonLoss.setCurrency(prpLpersonLossCurrency[index]);
					prpLpersonLoss.setAmount(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLpersonLossAmount, index))));
					prpLpersonLoss.setCurrency1(prpLpersonLossCurrency1[index]);
					prpLpersonLoss.setItemValue(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossItemValue[index])));
					prpLpersonLoss.setCurrency2(prpLpersonLossCurrency2[index]);
					prpLpersonLoss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRest[index])));
					prpLpersonLoss.setClaimRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossClaimRate[index])));
					prpLpersonLoss.setDeductible(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductible[index])));
					prpLpersonLoss.setCurrency4(prpLpersonLossCurrency4[index]);
					prpLpersonLoss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRealPay[index])));
					prpLpersonLoss.setFlag(prpLpersonLossFlag[index]);
					prpLpersonLoss.setLiabDetailCode(prpLpersonLossLiabDetailCode[index]);
					prpLpersonLoss.setLiabDetailName(prpLpersonLossLiabDetailName[index]);
					prpLpersonLoss.setFeeCategory(CommonUtils.getValue(medicDeathFlag, index)); // medicDeathFlag
					prpLpersonLoss.setInjuryGrade(CommonUtils.getValue(prpLpersonLossInjuryGrade, index));// 残废等级
					prpLpersonLoss.setSumDefPay(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLpersonLossSumDefPay, index))));// 核定赔偿
					prpLpersonLoss.setUnitAmount(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossUnitAmount[index])));
					prpLpersonLoss.setLossQuantity(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossLossQuantity[index])));
					prpLpersonLoss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLpersonLossSumLoss, index))));
					prpLpersonLoss.setCurrency3(prpLpersonLossCurrency3[index]);
					prpLpersonLoss.setFamilyName(CommonUtils.getValue(prpLpersonLossFamilyName, index));
					// 调整for保存不了多条赔付标的信息，原因：险别index不对
					prpLpersonLoss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossItemKindNo[index]))); // 需调整
					prpLpersonLoss.setKindCode(prpLpersonLossKindCode[index]);
					exchRate = 1d;// 默認為賠付幣別（NTD）對本位幣（NTD）的匯率
					if (!ConstantCodes.LOCAL_CURRENCY.equals(prpLpersonLoss.getCurrency())) {// 本位幣則
						exchRate = Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLpersonLossExchRate, index)));
					}
					prpLpersonLoss.setExchRate(exchRate);
					prpLpersonLoss.setPayObjectSerialNo(DataUtils.dbNullToEmpty(prpLpersonLossPayObjectSerialNo[index]));
					// 需求變更#83二次調整   add by 中科軟 begin 
					prpLpersonLoss.setReservedEstimate(DataUtils.dbNullToEmpty(prpLpersonLossReservedEstimate[index]));
					// 需求變更#83二次調整   add by 中科軟 end 
					prpLpersonLoss.setInjuryCode(CommonUtils.getValue(prpLpersonLossInjuryCode, index));
					prpLpersonLoss.setInjuryName(CommonUtils.getValue(prpLpersonLossInjuryName, index));
					prpLpersonLoss.setInjuryItemCode(CommonUtils.getValue(prpLpersonLossInjuryItemCode, index));
					prpLpersonLoss.setInjuryItemName(CommonUtils.getValue(prpLpersonLossInjuryItemName, index));

					// 人员序号即索引，主项记录
					int personNo = Integer.parseInt(personLossSerialNo[index]);
					prpLpersonLoss.setPersonNo(personNo);
					prpLpersonLoss.setDeductiblerate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductibleRate[personNo])));
					prpLpersonLoss.setDangerNo(Integer.parseInt(DataUtils.nullToZero(CommonUtils.getValue(prpLpersonLossDangerNo, personNo))));
					prpLpersonLoss.setPersonName(CommonUtils.getValue(prpLpersonLossPersonName, personNo));
					prpLpersonLoss.setSex(CommonUtils.getValue(prpLpersonLossSex, personNo));
					String birthday = CommonUtils.getValue(prpLpersonLossBirthday, personNo);
					if (DataUtils.emptyToNull(birthday) != null) {
						prpLpersonLoss.setBirthday(format.parse(birthday));
					}
					prpLpersonLoss.setAge(Integer.parseInt(DataUtils.nullToZero(CommonUtils.getValue(prpLpersonLossAge, personNo))));
					prpLpersonLoss.setCertificateCode(CommonUtils.getValue(prpLpersonLossCertificateCode, personNo));
					prpLpersonLoss.setMedicalCode(CommonUtils.getValue(prpLpersonLossMedicalCode, personNo));
					prpLpersonLoss.setIdentifyNumber(CommonUtils.getValue(prpLpersonLossIdentifyNumber, personNo));
					prpLpersonLoss.setMobilePhone(CommonUtils.getValue(prpLpersonLossMobilePhone, personNo));
					prpLpersonLoss.setHospitalCode(CommonUtils.getValue(prpLpersonLossHospitalCode, personNo));
					prpLpersonLoss.setHospitalName(CommonUtils.getValue(prpLpersonLossHospitalName, personNo));
					prpLpersonLoss.setDoctor(CommonUtils.getValue(prpLpersonLossDoctor, personNo));
					prpLpersonLoss.setCasualties(CommonUtils.getValue(prpLpersonLossCasualties, personNo));
					prpLpersonLoss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLpersonLossIndemnityDutyRate, personNo))));
					prpLpersonLoss.setAddPremium(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLpersonLossAddPremium, personNo))));
					// 获取危险单位序号
					prpLpersonLossList.add(prpLpersonLoss);
				}
			}
		}
		// 费用信息
		compensateDto.setPrpLpersonLossList(prpLpersonLossList);

		// 从界面得到输入数组
		String prpLchargePolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String prpLchargeRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLchargeCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String[] prpLchargeSerialNo = httpServletRequest.getParameterValues("prpLchargeSerialNo");
		String[] prpLchargeKindCode = httpServletRequest.getParameterValues("prpLchargeKindCode");
		String[] prpLchargeChargeCode = httpServletRequest.getParameterValues("prpLchargeChargeCode");
		String[] prpLchargeChargeName = httpServletRequest.getParameterValues("prpLchargeChargeName");
		String[] prpLchargeCurrency = httpServletRequest.getParameterValues("prpLchargeCurrency");
		String[] prpLchargeChargeAmount = httpServletRequest.getParameterValues("prpLchargeChargeAmount");
		String[] prpLchargeSumRealPay = httpServletRequest.getParameterValues("prpLchargeSumRealPay");
		String[] prpLchargeFlag = httpServletRequest.getParameterValues("prpLchargeFlag");
		String[] prpLchargeChargeReport = httpServletRequest.getParameterValues("prpLchargeChargeReport");
		String[] prpLchargePayObjectType = httpServletRequest.getParameterValues("prpLchargePayObjectType");
		String[] prpLchargePayObjectCode = httpServletRequest.getParameterValues("prpLchargePayObjectCode");
		String[] prpLchargePayObjectName = httpServletRequest.getParameterValues("prpLchargePayObjectName");
		String[] prpLchargeDangerNo = httpServletRequest.getParameterValues("prpLchargeDangerNo");

		// 增加对支付对象的保存
		String[] prpLchargeOwnerShip = httpServletRequest.getParameterValues("prpLchargeOwnerShip");// 費用支付方式
		String[] prpLchargeOwnerName = httpServletRequest.getParameterValues("prpLchargeOwnerName");// 賠付對象
		String[] prpLchargePaymentKind = httpServletRequest.getParameterValues("prpLchargePaymentKind");// 賠付类型
		String[] prpLchargeUniformNo = httpServletRequest.getParameterValues("prpLchargeUniformNo");// ID/統一編號
		String[] prpLchargeCutBack = httpServletRequest.getParameterValues("prpLchargeCutBack");// 禁背
		String[] prpLchargeBankCode = httpServletRequest.getParameterValues("prpLchargeBankCode");// 總行代號
		String[] prpLchargeBankName = httpServletRequest.getParameterValues("prpLchargeBankName");// 總行名稱
		String[] prpLchargeAccountCode = httpServletRequest.getParameterValues("prpLchargeAccountCode");// 匯款帳號
		String[] prpLchargeCustomBankCode = httpServletRequest.getParameterValues("prpLchargeCustomBankCode");// 分行代號
		String[] prpLchargeCustomBankName = httpServletRequest.getParameterValues("prpLchargeCustomBankName");// 分行名稱
		String[] prpLchargeAreaCode = httpServletRequest.getParameterValues("prpLchargeAreaCode");// 郵遞區號
		String[] prpLchargeCourierAddress = httpServletRequest.getParameterValues("prpLchargeCourierAddress");// 郵遞地址
		String[] prpLchargeCurrencyForPayObject = httpServletRequest.getParameterValues("prpLchargeCurrencyForPayObject");// 支付币别
		String[] prpLchargeCertificateCode = httpServletRequest.getParameterValues("prpLchargeCertificateCode");// 证件类型
		String[] prpLchargeExchRate = httpServletRequest.getParameterValues("prpLchargeExchRate");// 匯率
		String[] prpLchargeFeeSerialNo = httpServletRequest.getParameterValues("prpLchargeFeeSerialNo");// 代扣費用序號
		String[] prpLchargeBeneficiaryPhone = httpServletRequest.getParameterValues("prpLchargeBeneficiaryPhone");// 受款人電話
		String[] prpLchargeAccountCurrency = httpServletRequest.getParameterValues("prpLchargeAccountCurrency");// 账户币别
		String[] prpLchargeItemKindNo = httpServletRequest.getParameterValues("prpLchargeItemKindNo");//险别序号
		// 对象赋值
		// 赔款费用信息prpLcharge
		List<PrpLcharge> prpLchargeList = new ArrayList<PrpLcharge>();
		PrpLpayObjectInfo prpLpayObjectInfo = null;
		String feeSerialNo = null;
		List<PrpLpayObjectInfo> prpLpayObjectInfoList = new ArrayList<PrpLpayObjectInfo>();
		if (prpLchargeSerialNo != null && prpLchargeSerialNo.length > 0) {
			PrpLcharge prpLcharge = null;
			for (int index = 1; index < prpLchargeSerialNo.length; index++) {
				prpLcharge = new PrpLcharge();
				prpLcharge.setPolicyNo(prpLchargePolicyNo);
				prpLcharge.setRiskCode(prpLchargeRiskCode);
				prpLcharge.getId().setCompensateNo(prpLchargeCompensateNo);
				prpLcharge.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLchargeSerialNo[index])));
				prpLcharge.setKindCode(prpLchargeKindCode[index]);
				prpLcharge.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(CommonUtils.getValue(prpLchargeItemKindNo, index))));
				prpLcharge.setChargeCode(prpLchargeChargeCode[index]);
				prpLcharge.setChargeName(prpLchargeChargeName[index]);
				prpLcharge.setCurrency(prpLchargeCurrency[index]);
				prpLcharge.setChargeAmount(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeAmount[index])));
				prpLcharge.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLchargeSumRealPay[index])));
				prpLcharge.setOwnerShip(prpLchargeOwnerShip[index]);
				prpLcharge.setFlag(prpLchargeFlag[index]);
				if (prpLchargeChargeReport == null || prpLchargeChargeReport.length < 1) {
					prpLcharge.setChargeReport(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeAmount[index]))); // add
				} else {
					prpLcharge.setChargeReport(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeReport[index])));
				}
				exchRate = 1d;
				if (!ConstantCodes.LOCAL_CURRENCY.equals(prpLcharge.getCurrency())) {// 本位幣則
					exchRate = Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLchargeExchRate, index)));
				}
				prpLcharge.setExchRate(exchRate);
				feeSerialNo = CommonUtils.getValue(prpLchargeFeeSerialNo, index);
				if (DataUtils.emptyToNull(feeSerialNo) != null) {
//					if (feeSerialNo.length() > 2) {
//						feeSerialNo = feeSerialNo.substring(0, 2);
//					}
					prpLcharge.setFeeSerialNo(Integer.valueOf(feeSerialNo));
				}
				// 意健险理算不存在该字段
				prpLcharge.setPayObjectCode(CommonUtils.getValue(prpLchargePayObjectCode, index));
				prpLcharge.setPayObjectType(CommonUtils.getValue(prpLchargePayObjectType, index));
				prpLcharge.setPayObjectName(CommonUtils.getValue(prpLchargePayObjectName, index));

				prpLcharge.setOwnerShip(prpLchargeOwnerShip[index]);
				prpLcharge.setAccountCode(prpLchargeAccountCode[index]);
				prpLcharge.setBankCode(prpLchargeBankCode[index]);
				prpLcharge.setBankName(prpLchargeBankName[index]);
				prpLcharge.setDangerNo(Integer.parseInt(DataUtils.nullToZero(prpLchargeDangerNo[index])));

				prpLpayObjectInfo = new PrpLpayObjectInfo();
				// 增加对支付对象的保存
				prpLpayObjectInfo.getId().setCompensateNo(prpLchargeCompensateNo);
				prpLpayObjectInfo.getId().setCertiType(PrpLpayObjectInfo.CERTITYPE_CHARGE);
				prpLpayObjectInfo.getId().setSerialNo(index);
				prpLpayObjectInfo.setRiskCode(prpLchargeRiskCode);
				prpLpayObjectInfo.setKindCode(prpLchargeKindCode[index]);
				prpLpayObjectInfo.setOwnerName(prpLchargeOwnerName[index]);// 賠付對象
				prpLpayObjectInfo.setCertificateCode(prpLchargeCertificateCode[index]);// 證件類型
				prpLpayObjectInfo.setUniformNo(prpLchargeUniformNo[index]);// 證件號碼
				prpLpayObjectInfo.setBeneficiaryPhone(CommonUtils.getValue(prpLchargeBeneficiaryPhone, index));
				prpLpayObjectInfo.setOwnerShip(prpLchargeOwnerShip[index]);
				if ("B".equals(prpLchargeOwnerShip[index])) {// 汇款
					prpLpayObjectInfo.setBankCode(prpLchargeBankCode[index]);
					prpLpayObjectInfo.setBankName(prpLchargeBankName[index]);
					prpLpayObjectInfo.setAccountCode(prpLchargeAccountCode[index]);
					prpLpayObjectInfo.setCustomBankCode(prpLchargeCustomBankCode[index]);
					prpLpayObjectInfo.setCustomBankName(prpLchargeCustomBankName[index]);
					prpLpayObjectInfo.setAreaCode(prpLchargeAreaCode[index]);
					prpLpayObjectInfo.setCourierAddress(prpLchargeCourierAddress[index]);
				} else if ("Q".equals(prpLchargeOwnerShip[index])) {// 支票
					prpLpayObjectInfo.setCutBack(prpLchargeCutBack[index]);
					prpLpayObjectInfo.setAreaCode(prpLchargeAreaCode[index]);
					prpLpayObjectInfo.setCourierAddress(prpLchargeCourierAddress[index]);
				}
				prpLpayObjectInfo.setAccountCurrency(CommonUtils.getValue(prpLchargeAccountCurrency, index));
				prpLpayObjectInfo.setCurrency(prpLchargeCurrencyForPayObject[index]);
				prpLpayObjectInfo.setExchRate(exchRate);
				prpLpayObjectInfo.setPaymentKind(CommonUtils.getValue(prpLchargePaymentKind, index));
				// 存实际费用
				prpLpayObjectInfo.setPayAmount(prpLcharge.getChargeAmount());
				prpLpayObjectInfoList.add(prpLpayObjectInfo);
				prpLchargeList.add(prpLcharge);
			}
		}
		// 赔款费用信息
		compensateDto.setPrpLchargeList(prpLchargeList);

		// 客制化开发，收集賠款給付對象資訊，start,liuwei
		/******************* 賠付對象信息 start ******************************/
		// 从界面得到输入数组
		String prpLpayObjectInfoPaycodeType = httpServletRequest.getParameter("prpLpayObjectInfoPaycodeType");
		String[] prpLpayObjectInfoOwnerShip = httpServletRequest.getParameterValues("prpLpayObjectInfoOwnerShip");
		String[] prpLpayObjectInfoPayAmount = httpServletRequest.getParameterValues("prpLpayObjectInfoPayAmount");
		String[] prpLpayObjectInfoOwnerName = httpServletRequest.getParameterValues("prpLpayObjectInfoOwnerName");
		String[] prpLpayObjectInfoPaymentKind = httpServletRequest.getParameterValues("prpLpayObjectInfoPaymentKind");
		String[] prpLpayObjectInfoUniformNo = httpServletRequest.getParameterValues("prpLpayObjectInfoUniformNo");
		String[] prpLpayObjectInfoBeneficiaryPhone = httpServletRequest.getParameterValues("prpLpayObjectInfoBeneficiaryPhone");
		String[] prpLpayObjectInfoCutBack = httpServletRequest.getParameterValues("prpLpayObjectInfoCutBack");
		String[] prpLpayObjectInfoBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoBankCode");
		String[] prpLpayObjectInfoBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoBankName");
		String[] prpLpayObjectInfoAccountCode = httpServletRequest.getParameterValues("prpLpayObjectInfoAccountCode");
		String[] prpLpayObjectInfoCustomBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankCode");
		String[] prpLpayObjectInfoCustomBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankName");
		String[] prpLpayObjectInfoCourierAddress = httpServletRequest.getParameterValues("prpLpayObjectInfoCourierAddress");
		String[] prpLpayObjectInfoAreaCode = httpServletRequest.getParameterValues("prpLpayObjectInfoAreaCode");
		String[] prpLpayObjectInfoPayDate = httpServletRequest.getParameterValues("prpLpayObjectInfoPayDate");
		String[] prpLpayObjectInfoMobilePhoneNo = httpServletRequest.getParameterValues("prpLpayObjectInfoMobilePhoneNo");
		String[] prpLpayObjectInfoCurrency = httpServletRequest.getParameterValues("prpLpayObjectInfoCurrency");
		String[] prpLpayObjectInfoAccountCurrency = httpServletRequest.getParameterValues("prpLpayObjectInfoAccountCurrency");
		String[] prpLpayObjectInfoExchRate = httpServletRequest.getParameterValues("prpLpayObjectInfoExchRate");
		String[] prpLpayObjectInfoCertificateCode = httpServletRequest.getParameterValues("prpLpayObjectInfoCertificateCode");

		for (int index = 1; index < prpLpayObjectInfoOwnerShip.length; index++) {
			prpLpayObjectInfo = new PrpLpayObjectInfo();
			prpLpayObjectInfo.getId().setCompensateNo((String) httpServletRequest.getAttribute("compensateNo"));
			prpLpayObjectInfo.getId().setSerialNo(index);
			prpLpayObjectInfo.getId().setCertiType(PrpLpayObjectInfo.CERTITYPE_PAYOBJECT);
			prpLpayObjectInfo.setRiskCode(prpLlossDtoRiskCode);
			prpLpayObjectInfo.setPaycodeType(prpLpayObjectInfoPaycodeType);
			prpLpayObjectInfo.setOwnerShip(prpLpayObjectInfoOwnerShip[index]);
			prpLpayObjectInfo.setPayAmount(Double.parseDouble(DataUtils.nullToZero(prpLpayObjectInfoPayAmount[index])));
			prpLpayObjectInfo.setCurrency(CommonUtils.getValue(prpLpayObjectInfoCurrency, index));
			prpLpayObjectInfo.setAccountCurrency(CommonUtils.getValue(prpLpayObjectInfoAccountCurrency, index));
			exchRate = 1d;
			if (!ConstantCodes.LOCAL_CURRENCY.equals(prpLpayObjectInfo.getCurrency())) {// 本位幣則
				exchRate = Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLpayObjectInfoExchRate, index)));
			}
			prpLpayObjectInfo.setExchRate(exchRate);
			prpLpayObjectInfo.setOwnerName(prpLpayObjectInfoOwnerName[index]);
			prpLpayObjectInfo.setPaymentKind(prpLpayObjectInfoPaymentKind[index]);
			prpLpayObjectInfo.setCertificateCode(prpLpayObjectInfoCertificateCode[index]);
			prpLpayObjectInfo.setUniformNo(prpLpayObjectInfoUniformNo[index]);
			prpLpayObjectInfo.setBeneficiaryPhone(prpLpayObjectInfoBeneficiaryPhone[index]);
			if (PrpLpayObjectInfo.OWNERSHIP_B.equals(prpLpayObjectInfo.getOwnerShip())) {
				prpLpayObjectInfo.setBankCode(prpLpayObjectInfoBankCode[index]);
				prpLpayObjectInfo.setBankName(prpLpayObjectInfoBankName[index]);
				prpLpayObjectInfo.setAccountCode(prpLpayObjectInfoAccountCode[index]);
				prpLpayObjectInfo.setCustomBankCode(prpLpayObjectInfoCustomBankCode[index]);
				prpLpayObjectInfo.setCustomBankName(prpLpayObjectInfoCustomBankName[index]);
			} else if (PrpLpayObjectInfo.OWNERSHIP_Q.equals(prpLpayObjectInfo.getOwnerShip())) {
				prpLpayObjectInfo.setCutBack(prpLpayObjectInfoCutBack[index]);
			} else if (PrpLpayObjectInfo.OWNERSHIP_C.equals(prpLpayObjectInfo.getOwnerShip())) {
				if (!"".equals(prpLpayObjectInfoPayDate[index]) && prpLpayObjectInfoPayDate[index] != null) {
					prpLpayObjectInfo.setPayDate(format.parse(prpLpayObjectInfoPayDate[index]));
				}
				prpLpayObjectInfo.setMobilePhoneNo(prpLpayObjectInfoMobilePhoneNo[index]);
			}
			prpLpayObjectInfo.setCourierAddress(prpLpayObjectInfoCourierAddress[index]);
			prpLpayObjectInfo.setAreaCode(prpLpayObjectInfoAreaCode[index]);
			prpLpayObjectInfoList.add(prpLpayObjectInfo);
		}
		compensateDto.setPrpLpayObjectInfoList(prpLpayObjectInfoList);
		/******************* 賠付對象信息 end ******************************/

		String prpLearthquakeFundEarthquakeFundNo = httpServletRequest.getParameter("prpLearthquakeFundEarthquakeFundNo");
		String prpLearthquakeFundDamageStartDate = httpServletRequest.getParameter("prpLearthquakeFundDamageStartDate");
		String prpLearthquakeFundDamageStartHour = httpServletRequest.getParameter("prpLearthquakeFundDamageStartHour");
		String prpLearthquakeFundDamageStartMinute = httpServletRequest.getParameter("prpLearthquakeFundDamageStartMinute");
		String[] prpLearthquakeFundCompanyCode = httpServletRequest.getParameterValues("prpLearthquakeFundCompanyCode");
		String[] prpLearthquakeFundComCode = httpServletRequest.getParameterValues("prpLearthquakeFundComCode");
		String[] prpLearthquakeFundPolicyNo = httpServletRequest.getParameterValues("prpLearthquakeFundPolicyNo");
		String[] prpLearthquakeFundClaimNo = httpServletRequest.getParameterValues("prpLearthquakeFundClaimNo");
		String[] prpLearthquakeFundTimes = httpServletRequest.getParameterValues("prpLearthquakeFundTimes");
		String[] prpLearthquakeFundAddressNo = httpServletRequest.getParameterValues("prpLearthquakeFundAddressNo");
		if(prpLearthquakeFundCompanyCode!=null&&prpLearthquakeFundCompanyCode.length>1){
			PrpLearthquakeFund prpLearthquakeFund = null;
			List<PrpLearthquakeFund> prpLearthquakeFundList = new ArrayList<PrpLearthquakeFund>();
			DateTime dateTime = null;
			if(prpLearthquakeFundDamageStartDate!=null&&prpLearthquakeFundDamageStartDate.length()>0){
				dateTime = new DateTime(prpLearthquakeFundDamageStartDate,DateTime.YEAR_TO_DAY);
			}
			for(int i=1;i<prpLearthquakeFundCompanyCode.length;i++){
				prpLearthquakeFund = new PrpLearthquakeFund();
				prpLearthquakeFund.getId().setCompensateNo(compensateDto.getPrpLcompensate().getCompensateNo());
				prpLearthquakeFund.getId().setSerialNo(i);
				prpLearthquakeFund.setEarthquakeFundNo(prpLearthquakeFundEarthquakeFundNo);
				prpLearthquakeFund.setDamageStartDate(dateTime);
				prpLearthquakeFund.setDamageStartHour(prpLearthquakeFundDamageStartHour);
				prpLearthquakeFund.setDamageStartMinute(prpLearthquakeFundDamageStartMinute);
				prpLearthquakeFund.setRiskCode(compensateDto.getPrpLcompensate().getRiskCode());
				prpLearthquakeFund.setCompanyCode(prpLearthquakeFundCompanyCode[i]);
				prpLearthquakeFund.setComCode(prpLearthquakeFundComCode[i]);
				prpLearthquakeFund.setPolicyNo(prpLearthquakeFundPolicyNo[i]);
				prpLearthquakeFund.setClaimNo(prpLearthquakeFundClaimNo[i]);
				prpLearthquakeFund.setTimes(Integer.parseInt(DataUtils.nullToZero(prpLearthquakeFundTimes[i])));
				prpLearthquakeFund.setAddressNo(prpLearthquakeFundAddressNo[i]);
				prpLearthquakeFundList.add(prpLearthquakeFund);
			}
			compensateDto.setPrpLearthquakeFundList(prpLearthquakeFundList);
		}
		
		List<PrpLcfee> prpLcfeeList = new ArrayList<PrpLcfee>();
		// 从界面得到输入数组
		String prpLcfeeCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLcfeePolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String prpLcfeeRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLcfeeCurrency1 = httpServletRequest.getParameter("prpLcompensateCurrency");
		String prpLcfeeSumThisPaid = httpServletRequest.getParameter("prpLcompensateSumThisPaid");
		// 对象赋值
		// 赔款计算金额信息
		PrpLcfee prpLcfee = new PrpLcfee();
		prpLcfee.getId().setCompensateNo(prpLcfeeCompensateNo);
		prpLcfee.getId().setPolicyNo(prpLcfeePolicyNo);
		prpLcfee.setRiskCode(prpLcfeeRiskCode);
		prpLcfee.getId().setCurrency(prpLcfeeCurrency1);
		prpLcfee.setSumPaid(Double.parseDouble(DataUtils.nullToZero(prpLcfeeSumThisPaid)));
		prpLcfee.setFlag("");
		prpLcfeeList.add(prpLcfee);
		compensateDto.setPrpLcfeeList(prpLcfeeList);
		// 如果案件属於案终赔付，则需要结案报告文本
		String prpLcompensateFinallyFlag = httpServletRequest.getParameter("prpLcompensateFinallyFlag");
		// 由於不是第一张计算书的情况下，要说明後续情况，所以就要保存数据了
		List<PrpLltext> prpLltextList = new ArrayList<PrpLltext>();
		String strTextTemp = httpServletRequest.getParameter("prpLltextContextInnerHTML");
		String[] rulesTemp = StringUtils.split(strTextTemp, RULE_LENGTH, "GBK");
		PrpLltext prpLltext = null;
		PrpLctext prpLctext = null;
		for (int i = 0; i < rulesTemp.length; i++) {
			prpLltext = new PrpLltext();
			prpLltext.getId().setClaimNo((String) httpServletRequest.getParameter("prpLcompensateClaimNo"));
			prpLltext.setContext(rulesTemp[i]);
			prpLltext.getId().setLineNo(i + 1);
			prpLltext.getId().setTextType("08");
			prpLltextList.add(prpLltext);

			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo((String) httpServletRequest.getAttribute("compensateNo"));
			prpLctext.getId().setTextType("05");
			prpLctext.getId().setLineNo(i + 1);
			prpLctext.setContext(rulesTemp[i]);
			compensateDto.getPrpLctextList().add(prpLctext);
		}
		compensateDto.setPrpLltextList(prpLltextList);
		compensateDto.getPrpLcompensate().setFinallyFlag(prpLcompensateFinallyFlag);
		// 危险单位信息处理,目前只有一个危险单位
		// 将危险单位标的信息保存在集合中
		// 加入危险单位处理
		compensateDto.setPrpLprpLdangerItemList(prpLprpLdangerItemList);
		
		// 对标的信息进行处理，得到合计信息
		// 主子表金额是否一致校验
		double prpLCompensateSumRealPay = 0.00;
		double prpLLossSumRealPay = 0.00;
		double prpLPersonLossSumRealPay = 0.00;
		double prpLChargeSumRealPay = 0.00;
		prpLCompensateSumRealPay = compensateDto.getPrpLcompensate().getSumThisPaid() + compensateDto.getPrpLcompensate().getSumPrePaid();
		List<PrpLloss> prpLlossListTemp = compensateDto.getPrpLlossList();// 赔付标的信息
		if (prpLlossListTemp != null && !prpLlossListTemp.isEmpty()) {
			for (PrpLloss temp : prpLlossListTemp) {
				exchRate = 1d;// 默認為賠付幣別（NTD）對本位幣（NTD）的匯率
				if (!ConstantCodes.LOCAL_CURRENCY.equals(temp.getCurrency())) {// 本位幣則
					if (temp.getExchRate() != null) {
						exchRate = temp.getExchRate();
					}
				}
				BigDecimal b = new BigDecimal(Double.toString(temp.getSumRealPay() * exchRate)).setScale(0, BigDecimal.ROUND_HALF_UP);
				prpLLossSumRealPay += b.doubleValue();
			}
		}
		List<PrpLpersonLoss> prpLpersonLossListTemp = compensateDto.getPrpLpersonLossList();// 赔付人员信息
		if (prpLpersonLossListTemp != null && prpLpersonLossListTemp.size() > 0) {
			for (PrpLpersonLoss temp : prpLpersonLossListTemp) {
				exchRate = 1d;// 默認為賠付幣別（NTD）對本位幣（NTD）的匯率
				if (!ConstantCodes.LOCAL_CURRENCY.equals(temp.getCurrency())) {// 本位幣則
					if (temp.getExchRate() != null) {
						exchRate = temp.getExchRate();
					}
				}
				BigDecimal b = new BigDecimal(Double.toString(temp.getSumRealPay() * exchRate)).setScale(0, BigDecimal.ROUND_HALF_UP);
				prpLPersonLossSumRealPay += b.doubleValue();
			}
		}
		List<PrpLcharge> prpLchargeListTemp = compensateDto.getPrpLchargeList();// 赔款费用信息
		if (prpLchargeListTemp != null && prpLchargeListTemp.size() > 0) {
			for (PrpLcharge temp : prpLchargeListTemp) {
				exchRate = 1d;// 默認為賠付幣別（NTD）對本位幣（NTD）的匯率
				if (!ConstantCodes.LOCAL_CURRENCY.equals(temp.getCurrency())) {// 本位幣則
					if (temp.getExchRate() != null) {
						exchRate = temp.getExchRate();
					}
				}
				BigDecimal b = new BigDecimal(Double.toString(temp.getSumRealPay() * exchRate)).setScale(0, BigDecimal.ROUND_HALF_UP);
				prpLChargeSumRealPay += b.doubleValue();
			}
		}
//		String coinsFlag = policyDto.getPrpCmain().getCoinsFlag();
//		if ("2".equals(coinsFlag) || "3".equals(coinsFlag)) {// 从（联、共）保取总费用
//			double coinsRate = 0;
//			List<PrpCcoins> prpCcoinsList = this.getPrpCcoinsService().findPrpCcoins(QueryRule.getInstance().addSql("policyno='" + prpLcompensatePolicyNo + "' and coinstype='2'"));
//			if (prpCcoinsList != null && !prpCcoinsList.isEmpty()) {
//				for (Iterator<PrpCcoins> iterator = prpCcoinsList.iterator(); iterator.hasNext();) {
//					coinsRate = iterator.next().getCoinsRate();
//				}
//			}
//			BigDecimal bigSumAmount = new BigDecimal(new DecimalFormat(".00").format(prpLChargeSumRealPay));
//			BigDecimal bigCoinsRate = new BigDecimal(new DecimalFormat(".00").format(coinsRate / 100));
//			if(bigCoinsRate.doubleValue()!=0){
//				prpLChargeSumRealPay = bigSumAmount.divide(bigCoinsRate, BigDecimal.ROUND_HALF_UP).doubleValue();
//			}
//		}
		double sumTemp = prpLLossSumRealPay + prpLPersonLossSumRealPay + prpLChargeSumRealPay;
		if (Math.abs(prpLCompensateSumRealPay / sumTemp - 1) > 0.0001) {
			throw new UserException(-98, -1, "實賠", "本次賠付總金額與各分項損失彙總金額不相等，請檢查各項金額及費用,重新輸入！<br>" + "標的損失總金額：" + prpLLossSumRealPay + "<br>" + "人傷損失總金額：" + prpLPersonLossSumRealPay + "<br>" + "計入賠款費用總金額：" + prpLChargeSumRealPay + "<br>"
					+ "各分項損失彙總金額 = 標的損失總金額+人傷損失總金額+計入賠款費用總金額 = " + sumTemp + "<br>" + "本次賠付總金額：" + prpLCompensateSumRealPay + "<br>" + "本次賠付總金額與各分項損失彙總金額相差 " + (prpLCompensateSumRealPay - sumTemp));
		}
		return compensateDto;

	}

	/**
	 * 检查缴费标志 返回值 int -1为未缴费，0为未缴全，1为缴全
	 * @param httpServletRequest 返回给页面的request
	 * @param policyNo 赔案号
	 * @throws Exception
	 */
	public int checkPay(HttpServletRequest httpServletRequest, String policyNo) throws Exception {
		// 取得赔款计算书信息
		String conditions = " policyno = '" + policyNo + "'";
		return this.getPolicyService().checkPay(conditions);
	}

	/**
	 * 根据compensateDto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param riskType 險類
	 * @param compensateDto 实赔的数据类 
	 * @param prpCitemKindList 保險標的責任訊息
	 * @throws Exception
	 */
	private void setSubInfo(HttpServletRequest httpServletRequest,String riskType , CompensateDto compensateDto , List<PrpCitemKind> prpCitemKindList) throws Exception {
		PrpCengage prpCengage = new PrpCengage();
		List<PrpCengage> prpCengageList = compensateDto.getPrpCengageList();
		List<PrpCengage> cengageListTemp = new ArrayList<PrpCengage>();
		if (prpCengageList != null) {
			PrpCengage prpCengageDtoTemp = new PrpCengage();
			for (PrpCengage temp : prpCengageList) {
				if (temp.getTitleFlag().equals("0")) {
					prpCengageDtoTemp = new PrpCengage();
					cengageListTemp.add(prpCengageDtoTemp);
					PropertyUtils.copyProperties(prpCengageDtoTemp, temp);
					prpCengageDtoTemp.setContext(prpCengageDtoTemp.getContext() + temp.getClauses() + "</br>");
				} else {
					prpCengageDtoTemp.setContext(prpCengageDtoTemp.getContext() + temp.getClauses() + "</br>");
				}
			}
		}
		prpCengage.setPrpCengageList(cengageListTemp);
		httpServletRequest.setAttribute("prpCengage", prpCengage);
		// 赔偿限额/免赔额信息多行列表准备数据
		PrpDlimit prpDlimit = new PrpDlimit();
		prpDlimit.setPrpDlimitList(compensateDto.getPrpDlimitList());
		httpServletRequest.setAttribute("prpDlimit", prpDlimit);
		// 货币代码的列表
		// List<PrpDcurrency> collection =
		// this.getCodeService().findPayCurrencyMap();
		Map<String, String> currencyMap = this.getCodeService().findPayCurrencyMap();
		// 赔偿限额/免赔额信息多行列表准备数据
		PrpClimit prpClimit = new PrpClimit();
		
		List<PrpClimit> prpClimitList = compensateDto.getPrpClimitList();
		for (PrpClimit temp : prpClimitList) {
			if (currencyMap.containsKey(temp.getId().getCurrency())) {
				temp.setCurrencyName(currencyMap.get(temp.getId().getCurrency()).toString());
			}
			if ("1".equals(temp.getId().getLimitGrade())) {
				temp.getId().setLimitGrade("保單");
			} else {
				if (!CommonUtils.isEmpty(prpCitemKindList)) {
					Iterator<PrpCitemKind> itemkindlist = prpCitemKindList.iterator();
					PrpCitemKind prpCitemKind = null;
					while (itemkindlist.hasNext()) {
						prpCitemKind = (PrpCitemKind) itemkindlist.next();
						if (prpCitemKind.getId().getItemKindNo().equals(temp.getId().getLimitNo())) {
							if (prpCitemKind.getFlag().trim().equals("1")) {
								temp.getId().setLimitGrade(prpCitemKind.getItemDetailName());
							} else {
								temp.getId().setLimitGrade(prpCitemKind.getKindName());
							}
						}
					}
				}
			}
			temp.setLimitTypeName(this.getCodeService().translateLimit(temp.getRiskCode(), temp.getId().getLimitType(), true));
		}
		prpClimit.setPrpClimitList(prpClimitList);
		httpServletRequest.setAttribute("prpClimit", prpClimit);

		// 赔付标的信息多行列表准备数据
		PrpLloss prpLloss = new PrpLloss();
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null && prpLlossList.size() > 0) {
			for (PrpLloss temp : prpLlossList) {
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrencyName(currencyMap.get(temp.getCurrency()).toString());
				}
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrency1Name(currencyMap.get(temp.getCurrency1()).toString());
				}
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrency2Name(currencyMap.get(temp.getCurrency2()).toString());
				}
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrency3Name(currencyMap.get(temp.getCurrency3()).toString());
				}
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrency4Name(currencyMap.get(temp.getCurrency4()).toString());
				}
				temp.setKindName(this.getCodeService().translateKindCode(temp.getRiskCode(), temp.getKindCode(), true));
			}
		}
		prpLloss.setPrpLlossList(prpLlossList);
		httpServletRequest.setAttribute("prpLloss", prpLloss);

		// 赔付人员信息多行列表准备数据
		PrpLpersonLoss prpLpersonLoss = new PrpLpersonLoss();
		List<PrpLpersonLoss> prpLpersonLossList = compensateDto.getPrpLpersonLossList();
		if (prpLpersonLossList != null && prpLpersonLossList.size() > 0) {
			for (PrpLpersonLoss temp : prpLpersonLossList) {
				if (currencyMap.containsKey(temp.getCurrency2())) {
					temp.setCurrency2Name(currencyMap.get(temp.getCurrency2()).toString());
				}
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrencyName(currencyMap.get(temp.getCurrency()).toString());
				}
				temp.setKindName(this.getCodeService().translateKindCode(temp.getRiskCode(), temp.getKindCode(), true));
				temp.setInjuryGradeName(this.getCodeService().translateCodeCode("InjuryGrade", temp.getInjuryGrade(), true));
				temp.setInjuryItemName(this.getCodeService().translateCodeCode("InjuryCode", temp.getInjuryItemCode(), true));
			}
		}
		prpLpersonLoss.setPrpLpersonLossList(prpLpersonLossList);
		httpServletRequest.setAttribute("prpLpersonLoss", prpLpersonLoss);

		/****************** 支付对象信息 start *********************************/
		List<PrpLpayObjectInfo> tempPrpLpayObjectInfoList = compensateDto.getPrpLpayObjectInfoList();
		List<PrpLpayObjectInfo> prpLpayObjectInfoList = new ArrayList<PrpLpayObjectInfo>();
		List<PrpLpayObjectInfo> chargePrpLpayObjectInfoList = new ArrayList<PrpLpayObjectInfo>();
		if (tempPrpLpayObjectInfoList != null && !tempPrpLpayObjectInfoList.isEmpty()) {
			for (PrpLpayObjectInfo prpLpayObjectInfo : tempPrpLpayObjectInfoList) {
				// 赔付对象信息
				if (PrpLpayObjectInfo.CERTITYPE_PAYOBJECT.equals(prpLpayObjectInfo.getId().getCertiType())) {
					prpLpayObjectInfoList.add(prpLpayObjectInfo);
				} else if (PrpLpayObjectInfo.CERTITYPE_CHARGE.equals(prpLpayObjectInfo.getId().getCertiType())) {
					chargePrpLpayObjectInfoList.add(prpLpayObjectInfo);
				}
			}
		}
		PrpLpayObjectInfo prpLpayObjectInfo = new PrpLpayObjectInfo();
		prpLpayObjectInfo.setPrpLpayObjectInfoList(prpLpayObjectInfoList);
		httpServletRequest.setAttribute("prpLpayObjectInfo", prpLpayObjectInfo);
		/****************** 支付对象信息 end *********************************/
		/****************** 赔款费用信息 start *********************************/
		PrpLcharge prpLcharge = new PrpLcharge();
		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
		// 赔款费用信息多行列表准备数据
		if (prpLchargeList != null && !prpLchargeList.isEmpty()) {
			for (PrpLcharge temp : prpLchargeList) {
				temp.setKindName(this.getCodeService().translateKindCode(temp.getRiskCode(), temp.getKindCode(), true));
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrencyName(currencyMap.get(temp.getCurrency()).toString());
				}
				// 客制化增加赔付信息
				for (PrpLpayObjectInfo tempObject : chargePrpLpayObjectInfoList) {
					if (tempObject.getId().getSerialNo().intValue() == temp.getId().getSerialNo().intValue()) {
						temp.setPrpLpayObjectInfo(tempObject);
						break;
					}
				}
			}
		}
		prpLcharge.setPrpLchargeList(prpLchargeList);
		httpServletRequest.setAttribute("prpLcharge", prpLcharge);
		/****************** 赔款费用信息 end *********************************/

		// 查询出本次案件的的理算号码
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String hql = null;
		if (StringUtil.isBlank(prpLcompensate.getCompensateNo())) {
			hql = "claimNo ='" + prpLcompensate.getClaimNo() + "' and underWriteFlag in ('1','3')";
		} else {
			hql = "claimNo ='" + prpLcompensate.getClaimNo() + "' and underWriteFlag in ('1','3') and compensateNo <> '" + prpLcompensate.getCompensateNo() + "'";
		}
		List<PrpLcompensate> prpLcompensateList = compensateService.findByConditions(hql);
		Map<String, String> offsetCompensateNoMap = new HashMap<String, String>();
		for (PrpLcompensate temp : prpLcompensateList) {
			offsetCompensateNoMap.put(temp.getCompensateNo(), temp.getCompensateNo());
		}
		httpServletRequest.setAttribute("offsetCompensateNoMap", offsetCompensateNoMap);

		if (prpLpersonLoss.getPrpLpersonLossList() != null && !prpLpersonLoss.getPrpLpersonLossList().isEmpty()) {
			// 需求變更#83二次調整   update by 中科軟 begin 
			if (!CommonUtils.isEmpty(prpLcompensate.getCompensateNo())) {//已經存在的計算書則需要查詢帶出
				List<PrpLpersonHospital> prpLpersonHospitalList = prpLpersonHospitalService.findPrpLpersonHospital(compensateDto.getPrpLcompensate().getCompensateNo());
				List<PrpLpersonHospital> hospitalList = null;
				for (PrpLpersonLoss personTemp : prpLpersonLoss.getPrpLpersonLossList()) {
					hospitalList = new ArrayList<PrpLpersonHospital>();
					for (PrpLpersonHospital hospitalTemp : prpLpersonHospitalList) {
						if (personTemp.getPersonNo() == hospitalTemp.getPersonNo()) {
							hospitalList.add(hospitalTemp);
						}
					}
					personTemp.setPrpLpersonHospitalList(hospitalList);
				}
			}
			// 需求變更#83二次調整   update by 中科軟 end 
		}
		PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
		if (ConstantCodes.CLASSCODE_E.equals(riskType)) {
			PrpLclause prpLclause = null;
			double dayAmount = 0D;
			for (PrpCitemKind prpCitemKind : prpCitemKindList) {
				prpLclause = prpLclauseService.findPrpLclause(prpCitemKind.getKindCode());
				if(prpLclause!=null){
					if (!CommonUtils.isEmpty(prpLclause.getRange2())) {
						prpCitemKind.setContractingScope(prpLclause.getRange2());// 承保范围设定
					}
					if (!CommonUtils.isEmpty(prpLclause.getProductCode())) {
						prpCitemKind.setCommodityCode(prpLclause.getProductCode());// 商品设定
					}
					if(!CommonUtils.isEmpty(prpLclause.getCoverageratio())){
						prpCitemKind.setCoverageratio(prpLclause.getCoverageratio());
					}
				}
				dayAmount = prpCitemKind.getAmount();    //prpDriskRateService.findDayAmount(policyDto.getPrpCmain(), prpCitemKind);
				prpCitemKind.setDayAmount(dayAmount);
			}
			List<PrpCinsured> prpCinsuredList = (List<PrpCinsured>) httpServletRequest.getAttribute("prpCinsuredList");
			String insuredCode = prpLclaim.getInsuredCode()==null? "" : prpLclaim.getInsuredCode();
			PrpCinsured prpCinsured = null;
			for(int i=prpCinsuredList.size();i>0;i--){
				prpCinsured = prpCinsuredList.get(i-1);
				if("1".equals(prpCinsured.getInsuredFlag())){
					if(insuredCode.equals(prpCinsured.getInsuredCode())||(prpCinsured.getInsuredCode()==null&&insuredCode.equals(prpCinsured.getIdentifyNumber()))){
						prpLclaim.setFamilyNo(prpCinsured.getId().getSerialNo());
						break;
					}
				}
			}
		}
		PrpLearthquakeFund prpLearthquakeFund = new PrpLearthquakeFund();
		List<PrpLearthquakeFund> prpLearthquakeFundList = compensateDto.getPrpLearthquakeFundList();
		if(prpLearthquakeFundList!=null&&prpLearthquakeFundList.size()>0){
			prpLearthquakeFund.setPrpLearthquakeFundList(prpLearthquakeFundList);
			PrpLearthquakeFund temp = prpLearthquakeFundList.get(0);
			prpLearthquakeFund.setEarthquakeFundNo(temp.getEarthquakeFundNo());
			prpLearthquakeFund.setDamageStartDate(temp.getDamageStartDate());
			prpLearthquakeFund.setDamageStartHour(temp.getDamageStartHour());
			prpLearthquakeFund.setDamageStartMinute(temp.getDamageStartMinute());
		}else{
			prpLearthquakeFund.setDamageStartDate(compensateDto.getPrpLcompensate().getDamageStartDate());
			prpLearthquakeFund.setDamageStartHour(compensateDto.getPrpLcompensate().getDamageStartHour());
			prpLearthquakeFund.setDamageStartMinute(compensateDto.getPrpLcompensate().getDamageStartMinute());
		}
		httpServletRequest.setAttribute("prpLearthquakeFund", prpLearthquakeFund);
		setSelectionList(httpServletRequest, compensateDto.getPrpLcompensate() , riskType);
	}

	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcompensate 实赔的数据类
	 * @param riskType 險種大類
	 * @throws Exception
	 */
	private void setSelectionList(HttpServletRequest httpServletRequest, PrpLcompensate prpLcompensate , String riskType) throws Exception {
		/** 设置骨折程度和骨折部位 */
		String riskCode = prpLcompensate.getRiskCode();
		List<PrpLfracture> fractureDegreeList = prpLfractureService.findPrpLfracture("FractureDegree");
		httpServletRequest.setAttribute("fractureDegreeList", fractureDegreeList);
		List<PrpLfracture> fractureSiteList = prpLfractureService.findPrpLfracture("FractureSite");
		httpServletRequest.setAttribute("fractureSiteList", fractureSiteList);
		httpServletRequest.setAttribute("prosecutorsOfficeList", ConstantsCollection.prosecutorsOfficeList);
		httpServletRequest.setAttribute("deathMannerList", ConstantsCollection.deathMannerList);
		httpServletRequest.setAttribute("deathPlaceList", ConstantsCollection.deathPlaceList);
		httpServletRequest.setAttribute("paymentKindList", ConstantsCollection.paymentKindList);
		httpServletRequest.setAttribute("shipPaymentKindList", ConstantsCollection.shipPaymentKindList);
		httpServletRequest.setAttribute("payObjectTypeList", ConstantsCollection.payObjectTypeList);
		// 支付对象 帳號歸屬人證件類型
		Map<String, String> CertificateTypeList = ConstantsCollection.prpdpaymentaccountCertificateTypeList;
		httpServletRequest.setAttribute("prpdpaymentaccountCertificateTypeList", CertificateTypeList);
		/** 结案类型 */
		httpServletRequest.setAttribute("closedTypeList", ConstantsCollection.closedTypeList);
		/** 赔偿速度 */
		httpServletRequest.setAttribute("speedFlagList", ConstantsCollection.speedFlagList);
		// 支付对象 支付币种
		httpServletRequest.setAttribute("prpLpayObjectInfoCurrencyList", codeService.findPayCurrencyMap());
		PrpLclaim prpLclaim = (PrpLclaim) httpServletRequest.getAttribute("prpLclaim");
		/** 本位币（新台币）对其他币种的当日汇率 */
		httpServletRequest.setAttribute("baseToExch", this.getCodeService().findBasePrpDexch(prpLclaim.getDamageStartDate(), ConstantCodes.LOCAL_CURRENCY));
		/** 其他币种对的本位币（新台币）当日汇率 */
		httpServletRequest.setAttribute("exchToBase", this.getCodeService().findExchPrpDexch(prpLclaim.getDamageStartDate(), ConstantCodes.LOCAL_CURRENCY));
		/** 傷亡情形 */
		httpServletRequest.setAttribute("casualtiesList", ConstantsCollection.casualtiesList);
		/** 残废等级 */
		Map<String, String> injuryGradeMap = new HashMap<String, String>();
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(riskCode);
		/** EV 漁業漁船意外責任保險、EW 遊艇意外責任保險、FV 漁業漁船船員僱主責任保險（只有這三個險種有失能等級，其它險種沒有） */
		if ("RISKCODE_YEV".equals(configCode) || "RISKCODE_YEW".equals(configCode) || "RISKCODE_YFV".equals(configCode)) {
			injuryGradeMap = ConstantsCollection.injuryGradeList_Y;
		}
		httpServletRequest.setAttribute("injuryGradeList", injuryGradeMap);
		// 受害人健保就醫代號
		httpServletRequest.setAttribute("medicalCodeList", ConstantsCollection.medicalCodeList);
		// 理算说明配置
		String conditions = " riskType = '" + riskType + "' and ( riskCode is null or riskCode = '" + riskCode + "' ) and lineNo = 1 order by contextNo asc ";
		List<PrpLltextModel> prpLltextModelList = this.getPrpLltextModelService().findByConditions(conditions);
		List<PrpLltextModel> CompeContext = new ArrayList<PrpLltextModel>();
		if (prpLltextModelList != null && !prpLltextModelList.isEmpty()) {
			for (PrpLltextModel p : prpLltextModelList) {
				if (riskCode.equals(p.getRiskCode())) {
					CompeContext.add(p);
				}
			}
		}
		// 险种没有配置则取险类的
		if (CompeContext.isEmpty()) {
			CompeContext.addAll(prpLltextModelList);
		}
		httpServletRequest.setAttribute("CompeContext", CompeContext);
	}

	/**
	 * STUB-ONLY 生成理算报告
	 * @param 无
	 * @throws UserException
	 * @throws Exception
	 */
	public void generateCtext(HttpServletRequest httpServletRequest, CompensateDto compensateDto) throws UserException, Exception {
		this.createReport(httpServletRequest, compensateDto);
	}

	/**
	 * STUB-ONLY 生成理算报告
	 * @param 无
	 * @throws UserException
	 * @throws Exception
	 */
	public void createReport(HttpServletRequest httpServletRequest, CompensateDto compensateDto) throws UserException, Exception {
		PrpLcfee prpLcfee = null;
		// 原来用数组保存理算报告正文的方式改为集合
		List<String> prpLctextList = new ArrayList<String>();
		int intLossCurr = 0;
		int intChargeCurr = 0;
		int intPersonLossCurr = 0;
		int i, j = 0;
		double dblSumRealpay = 0;
		double dblSumLoss = 0;
		double dblSumCharge = 0;
		double dblSumPerson = 0;
		double dblSumJE = 0;
		String strSpace = "    ";
		String strChineseMoney = "";
		String strCompensateNo = "";
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		if (prpLcompensate == null) {
			throw new UserException(-98, -1, "計算報告", "沒有計算書訊息，請確認！");
		}
		Vector<PrpLloss> tmpPrpLlossList = new Vector<PrpLloss>();
		Vector<PrpLpersonLoss> tmpPrpLpersonLossList = new Vector<PrpLpersonLoss>();
		Vector<PrpLcharge> tmpPrpLchargeList = new Vector<PrpLcharge>();
		// 修改意键险的默认行长度
		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLcompensate.getRiskCode());
		if ("E".equals(strRiskType)) {
			RULE_LENGTH = 80;
		} else {
			RULE_LENGTH = 70;
		}
		strCompensateNo = prpLcompensate.getCompensateNo();
		// 1.对标的、费用、人员赔付信息进行分类汇总
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		PrpLloss prpLloss = null;
		for (i = 0; i < prpLlossList.size(); i++) {
			prpLloss = prpLlossList.get(i);
			for (j = 0; j < intLossCurr; j++) {
				PrpLloss tempPrpLloss = tmpPrpLlossList.get(j);
				if ((prpLloss.getLossName().equals(tempPrpLloss.getLossName()) && prpLloss.getFamilyName().equals(tempPrpLloss.getFamilyName()) && prpLloss.getCurrency4().equals(tempPrpLloss.getCurrency4()))
						|| (prpLloss.getLossName().equals(tempPrpLloss.getLossName()) && prpLloss.getCurrency4().equals(tempPrpLloss.getCurrency4()) && (prpLloss.getDeductible() == tempPrpLloss.getDeductible()) && (prpLloss.getSumRest() == tempPrpLloss
								.getSumRest()))) {
					tempPrpLloss.setSumRealPay(tempPrpLloss.getSumRealPay() + prpLloss.getSumRealPay());
					tempPrpLloss.setSumLoss(tempPrpLloss.getSumLoss() + prpLloss.getSumLoss());
					tmpPrpLlossList.set(j, tempPrpLloss);
					break;
				}
			}
			if (intLossCurr == 0) {
				intLossCurr += 1;
				tmpPrpLlossList.add(prpLloss);
			}
			if (j >= intLossCurr) {
				intLossCurr += 1;
				tmpPrpLlossList.add(prpLloss);
			}
		}
		// 赔款费用信息
		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
		PrpLcharge prpLcharge = null;
		for (i = 0; i < prpLchargeList.size(); i++) {
			prpLcharge = (PrpLcharge) compensateDto.getPrpLchargeList().get(i);
			for (j = 0; j < intChargeCurr; j++) {
				PrpLcharge prpLchargeDtoCurr = new PrpLcharge();
				prpLchargeDtoCurr = (PrpLcharge) tmpPrpLchargeList.get(j);
				if (prpLcharge.getChargeCode().equals(prpLchargeDtoCurr.getChargeCode()) && prpLcharge.getCurrency().equals(prpLchargeDtoCurr.getCurrency())) {
					prpLchargeDtoCurr.setChargeAmount(prpLchargeDtoCurr.getChargeAmount() + prpLcharge.getChargeAmount());
					prpLchargeDtoCurr.setSumRealPay(prpLchargeDtoCurr.getSumRealPay() + prpLcharge.getSumRealPay());
					tmpPrpLchargeList.set(j, prpLchargeDtoCurr);
					break;
				}
			}
			if (intChargeCurr == 0) {
				intChargeCurr = intChargeCurr + 1;
				tmpPrpLchargeList.add(prpLcharge);
			}
			if (j >= intChargeCurr) {
				intChargeCurr = intChargeCurr + 1;
				tmpPrpLchargeList.add(prpLcharge);
			}
		}
		List<PrpLpersonLoss> prpLpersonLossList = compensateDto.getPrpLpersonLossList();
		List<PrpLpersonLoss> prpLpersonLossListCopy = new ArrayList<PrpLpersonLoss>();
		PrpLpersonLoss prpLpersonLoss = null;
		PrpLpersonLoss tempPrpLpersonLoss = null;
		PrpLpersonLoss tempPrpLpersonLossCurr = null;
		for (i = 0; i < prpLpersonLossList.size(); i++) {
			prpLpersonLoss = (PrpLpersonLoss) compensateDto.getPrpLpersonLossList().get(i);
			tempPrpLpersonLoss = new PrpLpersonLoss();
			// 复制对象数据
			tempPrpLpersonLoss.setAmount(prpLpersonLoss.getAmount());
			tempPrpLpersonLoss.setUnitAmount(prpLpersonLoss.getUnitAmount());
			tempPrpLpersonLoss.setPersonName(prpLpersonLoss.getPersonName());
			tempPrpLpersonLoss.setPersonNo(prpLpersonLoss.getPersonNo());
			tempPrpLpersonLoss.setFamilyNo(prpLpersonLoss.getFamilyNo());
			tempPrpLpersonLoss.setKindCode(prpLpersonLoss.getKindCode());
			tempPrpLpersonLoss.setSumRealPay(prpLpersonLoss.getSumRealPay());
			tempPrpLpersonLoss.setCurrency4(prpLpersonLoss.getCurrency4());
			tempPrpLpersonLoss.setLiabDetailName(prpLpersonLoss.getLiabDetailName());
			tempPrpLpersonLoss.setRemark(prpLpersonLoss.getRemark());
			tempPrpLpersonLoss.setRiskCode(prpLpersonLoss.getRiskCode());
			tempPrpLpersonLoss.setDblMaxPaid(prpLpersonLoss.getDblMaxPaid());
			prpLpersonLossListCopy.add(tempPrpLpersonLoss);
			for (j = 0; j < intPersonLossCurr; j++) {
				tempPrpLpersonLossCurr = tmpPrpLpersonLossList.get(j);
				if (prpLpersonLoss.getPersonName().equals(tempPrpLpersonLossCurr.getPersonName()) && prpLpersonLoss.getCurrency4().equals(tempPrpLpersonLossCurr.getCurrency4())
						&& (prpLpersonLoss.getPersonNo() == tempPrpLpersonLossCurr.getPersonNo())) {
					tempPrpLpersonLossCurr.setSumRealPay(tempPrpLpersonLossCurr.getSumRealPay() + prpLpersonLoss.getSumRealPay());
					tmpPrpLpersonLossList.set(j, tempPrpLpersonLossCurr);
					break;
				}
			}
			if (intPersonLossCurr == 0) {
				intPersonLossCurr += 1;
				tmpPrpLpersonLossList.add(prpLpersonLoss);
			}
			if (j >= intPersonLossCurr) {
				intPersonLossCurr += 1;
				tmpPrpLpersonLossList.add(prpLpersonLoss);
			}
		}
		// 2.计算书正文
		// 2.1 标的损失理算报告
		if (intLossCurr > 0) {
			prpLctextList.add("      ");
			prpLctextList.add("***<賠付標的：>***");
			PrpLloss prpLlossCurr = null;
			for (i = 0; i < intLossCurr; i++) {
				prpLlossCurr = tmpPrpLlossList.get(i);
				dblSumRealpay = prpLlossCurr.getSumRealPay();
				// 允许赔款金额为负值
				if (dblSumRealpay < 0) {
					dblSumRealpay = -1 * dblSumRealpay;
					strChineseMoney = "負" + MoneyUtils.toChinese(dblSumRealpay, prpLlossCurr.getCurrency3());
				} else {
					strChineseMoney = MoneyUtils.toChinese(dblSumRealpay, prpLlossCurr.getCurrency4());
				}
				// 受损标的赔偿金额
				prpLctextList.add(strSpace + "受損標的賠償金額：" + this.getCodeService().translateCurrencyCode(prpLlossCurr.getCurrency4(), true) + strChineseMoney.trim() + strSpace + prpLlossCurr.getCurrency4() + prpLlossCurr.getSumRealPay());
				// 计算公式
				prpLctextList.add(strSpace + strSpace + strSpace + strSpace);
				double deductibleRate = 0;
				double deductible = 0;
				double deductible1 = 0;
				deductibleRate = prpLlossCurr.getDeductiblerate();
				deductible = prpLlossCurr.getDeductible();
				if (prpLlossCurr.getRiskCode().substring(1, 2).equals("YD")) {
					deductible1 = prpLlossCurr.getAmount() * deductibleRate / 100;
				} else {
					deductible1 = prpLlossCurr.getSumLoss() * deductibleRate / 100;
				}
				if (deductible < deductible1) {
					deductible = deductible1;
				}
				if (prpLlossCurr.getRiskCode().equals(this.getCodeService().translateProductCode("RISKCODE_ZFG"))) {
					prpLctextList.add(strSpace + "標的損失× 賠付比例－ 免賠額＝");
				} else {
					if (prpLlossCurr.getDeductible() > 0) {
						prpLctextList.add(strSpace + "(標的損失－ 殘值) × 賠付比例－ 免賠額＝");
					} else {
						prpLctextList.add(strSpace + "(標的損失－ 殘值) × 賠付比例×(1-免賠率) ＝");
					}
				}
				if (prpLlossCurr.getRiskCode().equals(this.getCodeService().translateProductCode("RISKCODE_ZFG"))) {
					prpLctextList.add(strSpace + prpLlossCurr.getSumLoss() + "×" + prpLlossCurr.getClaimRate() + "％" + "－" + String.valueOf(deductible) + "＝" + prpLlossCurr.getSumRealPay());
				} else {
					if (prpLlossCurr.getDeductible() > 0) {
						prpLctextList.add(strSpace + "(" + prpLlossCurr.getSumLoss() + "－" + prpLlossCurr.getSumRest() + ")" + "×" + prpLlossCurr.getClaimRate() + "％" + "－" + String.valueOf(deductible) + "＝" + prpLlossCurr.getSumRealPay());
					} else {
						prpLctextList.add(strSpace + "(" + prpLlossCurr.getSumLoss() + "－" + prpLlossCurr.getSumRest() + ")" + "×" + prpLlossCurr.getClaimRate() + "％*(1" + "-" + String.valueOf(prpLlossCurr.getDeductiblerate()) + ")＝"
								+ prpLlossCurr.getSumRealPay());
					}
				}

				dblSumLoss = dblSumLoss + dblSumRealpay;
				prpLctextList.add("   ");

			}
		}

		// 2.3 人员赔付理算报告
		if (intPersonLossCurr > 0) {
			prpLctextList.add("***<賠付人員：>***");
			for (i = 0; i < intPersonLossCurr; i++) {
				PrpLpersonLoss PrpLpersonLossCurr = null;
				PrpLpersonLossCurr = (PrpLpersonLoss) tmpPrpLpersonLossList.get(i);
				prpLctextList.add(strSpace + "人員名稱：" + PrpLpersonLossCurr.getPersonName());
				strRiskType = this.getCodeService().translateRiskCodetoRiskType(PrpLpersonLossCurr.getRiskCode());
				if ("E".equals(strRiskType)) {
					prpLctextList.add(strSpace + strSpace + "人員賠付金額= (核損金額- 免賠額) × 賠償比例 ");
					prpLctextList.add(strSpace + strSpace + strSpace + strSpace + strSpace + " = " + PrpLpersonLossCurr.getCurrency4() + PrpLpersonLossCurr.getSumRealPay());
				} else {
					prpLctextList.add(strSpace + strSpace + "人員賠付金額：" + PrpLpersonLossCurr.getCurrency4() + "  " + PrpLpersonLossCurr.getSumRealPay());
				}
				dblSumPerson = dblSumPerson + PrpLpersonLossCurr.getSumRealPay();
			}
			prpLctextList.add("   ");
		}
		// 费用信息
		if (intChargeCurr > 0) {
			prpLctextList.add("***<費用信息：>***");
			for (i = 0; i < tmpPrpLchargeList.size(); i++) {
				prpLcharge = tmpPrpLchargeList.get(i);
				prpLctextList.add(strSpace + prpLcharge.getChargeName() + "：" + prpLcharge.getCurrency() + prpLcharge.getSumRealPay());
				dblSumCharge = dblSumCharge + prpLcharge.getSumRealPay();
			}
			prpLctextList.add("   ");
		}
		// 3.赔付信息汇总
		dblSumJE = dblSumLoss + dblSumCharge + dblSumPerson;
		prpLctextList.add("***<金額合計：>***");
		if (compensateDto.getPrpLcfeeList() != null) {
			for (i = 0; i < compensateDto.getPrpLcfeeList().size(); i++) {
				prpLcfee = new PrpLcfee();
				prpLcfee = (PrpLcfee) compensateDto.getPrpLcfeeList().get(i);
				// 允许赔款金额为负
				boolean bflag = false;
				if (prpLcfee.getSumPaid() < 0) {
					bflag = true;
					prpLcfee.setSumPaid(-1 * (prpLcfee.getSumPaid()));
				}
				strChineseMoney = MoneyUtils.toChinese(prpLcfee.getSumPaid(), prpLcfee.getId().getCurrency());
				if (bflag) {
					prpLctextList.add(strSpace + "沖减" + this.getCodeService().translateCurrencyCode(prpLcfee.getId().getCurrency(), true) + strChineseMoney.trim() + strSpace + prpLcfee.getId().getCurrency().trim() + prpLcfee.getSumPaid());
				} else {
					prpLctextList.add(strSpace + this.getCodeService().translateCurrencyCode(prpLcfee.getId().getCurrency(), true) + strChineseMoney.trim() + strSpace + prpLcfee.getId().getCurrency().trim() + prpLcfee.getSumPaid());
				}
			}
		}
		PrpLctext prpLctext = null;
		// reason, classcode不能substring这样取，並且6的也需要和7一样的格式
		if ("27".equals(prpLcompensate.getClassCode()) || "26".equals(prpLcompensate.getClassCode())) {
			// 意键险理赔根据客户要求，需要根据案件性质显示相应的模板。
			// 得到连接串,下面将其切分到数组
			String textTemp = null;
			String[] rulesAcci = null;
			// 分险别进行赔款汇总
			intPersonLossCurr = 0;
			tmpPrpLpersonLossList.clear();
			PrpLpersonLoss prpLpersonLossCurr = null;
			for (int intCount = 0; intCount < prpLpersonLossListCopy.size(); intCount++) {
				prpLpersonLoss = prpLpersonLossListCopy.get(intCount);
				for (j = 0; j < intPersonLossCurr; j++) {
					prpLpersonLossCurr = (PrpLpersonLoss) tmpPrpLpersonLossList.get(j);
					if (prpLpersonLoss.getPersonName().equals(prpLpersonLossCurr.getPersonName()) && prpLpersonLoss.getCurrency4().equals(prpLpersonLossCurr.getCurrency4()) && (prpLpersonLoss.getPersonNo() == prpLpersonLossCurr.getPersonNo())
							&& prpLpersonLoss.getKindCode().equals(prpLpersonLossCurr.getKindCode())) {
						prpLpersonLossCurr.setSumRealPay(prpLpersonLossCurr.getSumRealPay() + prpLpersonLoss.getSumRealPay());
						tmpPrpLpersonLossList.set(j, prpLpersonLossCurr);
						break;
					}
				}
				if (intPersonLossCurr == 0) {
					intPersonLossCurr += 1;
					tmpPrpLpersonLossList.add(prpLpersonLoss);
				}
				if (j >= intPersonLossCurr) {
					intPersonLossCurr += 1;
					tmpPrpLpersonLossList.add(prpLpersonLoss);
				}
			}
			PrpLpersonLoss prpLpersonLossNew = null;
			if (compensateDto.getPrpLpersonLossList() != null && compensateDto.getPrpLpersonLossList().size() > 0) {
				prpLpersonLossNew = compensateDto.getPrpLpersonLossList().get(0);
			}
			String insuredName = prpLcompensate.getInsuredName(); // 出险人信息
			String strSex = "1".equals(prpLpersonLossNew.getSex()) ? "先生" : "女士";
			String damageName = prpLcompensate.getDamageName(); // 出险原因
			String policyNo = prpLcompensate.getPolicyNo(); // 报单号
			String claimNo = prpLcompensate.getClaimNo(); // 立案号
			String strAddInfo = "";
			// 获得投保人名称
			String strAppliName = prpLcompensate.getAppliName();
			String strClaimType = prpLcompensate.getCaseType(); // 案件类型
			if ("7".equals(strClaimType)) { // 7:为拒赔案件
				prpLctextList.clear();
				prpLctextList.add("  ");
				prpLctextList.add("尊敬的" + insuredName + strSex);
				textTemp = "您的理賠申請本公司已經獲悉，根據保險條款及相關法律，並經審慎核定您所提供的有關資料與證明，本公司認為，" + "您的申請事由不能成立，並做如下處理： ";
				rulesAcci = StringUtils.split(textTemp, RULE_LENGTH, "GBK");
				for (int k = 0; k < rulesAcci.length; k++) {
					prpLctextList.add(strSpace + rulesAcci[k]);
				}
				prpLctextList.add(strSpace + "不予立案並退件/不予給付保險金/退還解約金" + ConstantCodes.LOCAL_CURRENCY + "0.00元 ,解除本保險合約。");
				prpLctextList.add(strSpace + "本公司做出上述決定的理由是：");
				prpLctextList.add("  ");
				textTemp = "若您對本公司的處理有異議，可於接到本通知之日起十日內向本公司理賠部門尋求解釋。若您覺得仍無法獲得滿意的答复，您還享有以下權利： ";
				rulesAcci = StringUtils.split(textTemp, RULE_LENGTH, "GBK");
				for (int k = 0; k < rulesAcci.length; k++) {
					prpLctextList.add(strSpace + rulesAcci[k]);
				}
				prpLctextList.add(strSpace + "向仲裁機關申請仲裁/向人民法院提起訴訟");
				prpLctextList.add(strSpace + "請申請並審慎運用您的上述權利。");
			} else if (strClaimType.equals("8")) { // 8:为协议案件
				prpLctextList.clear();
				prpLctextList.add("  ");
				textTemp = "茲有" + policyNo + "號保單項下之權利人向本公司提出理賠申請，經雙方協商，達成如下協議:";
				rulesAcci = StringUtils.split(textTemp, RULE_LENGTH, "GBK");
				for (int k = 0; k < rulesAcci.length; k++) {
					prpLctextList.add(strSpace + rulesAcci[k]);
				}
				prpLctextList.add(strSpace + "1. 保險公司同意給付保險金" + ConstantCodes.LOCAL_CURRENCY + "" + dblSumJE + "元整；");
				prpLctextList.add(strSpace + "2.上述保險金款項由" + insuredName + "代表全體權利人受領；");
				prpLctextList.add(strSpace + "3.全體權利人同意放棄上述保單項下其他所有與本保險事故有關之權利。");
				prpLctextList.add("合計給付保險金：" + ConstantCodes.LOCAL_CURRENCY + "" + dblSumJE + "元");
			} else if (strClaimType.equals("5")) { // 5:为通融案件
				prpLctextList.clear();
				prpLctextList.add("  ");
				textTemp = "茲有" + policyNo + "號保單項下" + insuredName + "（被保險人）發生" + damageName + "事故不屬於本公司保險責任," + policyNo + "保單項下" + "之權利人向本公司提出通融理賠申請，經本公司審核後研究決定，同意通融給付並達成如下協議： ";
				rulesAcci = StringUtils.split(textTemp, RULE_LENGTH, "GBK");
				for (int k = 0; k < rulesAcci.length; k++) {
					prpLctextList.add(strSpace + rulesAcci[k]);
				}
				prpLctextList.add(strSpace + "1. 保險公司同意通融給付保險金" + ConstantCodes.LOCAL_CURRENCY + "" + dblSumJE + "元整");
				prpLctextList.add(strSpace + "2.上述保險金款項由" + insuredName + "代表全體權利人受領；");
				prpLctextList.add(strSpace + "3.全體權利人同意放棄上述保單項下其他所有與本保險事故有關之權利。");
				prpLctextList.add("合計給付保險金：" + ConstantCodes.LOCAL_CURRENCY + "" + dblSumJE + "元");
			} else {
				// 否则为0：一般赔案 1：速决赔案 B：议审案件中的一种
				prpLctextList.clear();
				prpLctextList.add("  ");
				// 客户要求修改审核的格式
				// 事故壹案提出理赔申请，经本公司审核属保险责任范围，同意如下批复：
				textTemp = "茲有客戶（賠案號" + claimNo + "）提出索賠申請，經本公司審核，同意如下各項批复：";
				rulesAcci = StringUtils.split(textTemp, RULE_LENGTH, "GBK");
				for (int k = 0; k < rulesAcci.length; k++) {
					prpLctextList.add(strSpace + rulesAcci[k]);
				}
				textTemp = "1、" + policyNo + "保單項下：要保人：（" + strAppliName + "） 被保險人：（" + insuredName + "）";
				rulesAcci = StringUtils.split(textTemp, RULE_LENGTH, "GBK");
				for (int k = 0; k < rulesAcci.length; k++) {
					prpLctextList.add(strSpace + rulesAcci[k]);
				}
				String TextTemp2 = "";
				textTemp = "";
				prpLpersonLossNew = new PrpLpersonLoss();
				for (int intCount = 0; intCount < tmpPrpLpersonLossList.size(); intCount++) {
					prpLpersonLossNew = (PrpLpersonLoss) tmpPrpLpersonLossList.get(intCount);
					double dblUnitAmountSum2 = prpLpersonLossNew.getDblMaxPaid() - prpLpersonLossNew.getSumRealPay(); // 为核减後的险别保险金额
					strAddInfo = "";
					String codeNameNew = this.getCodeService().translateKindCode(prpLpersonLossNew.getRiskCode(), prpLpersonLossNew.getKindCode(), true);
					// 如果保险金额全赔完，则置为0元，且给提示信息。
					if (dblUnitAmountSum2 < 0.0) {
						dblUnitAmountSum2 = 0.0;
					}
					if (prpLpersonLossNew.getSumRealPay() < 0) {
						double dbSumRealPay = 0;
						dbSumRealPay = prpLpersonLossNew.getSumRealPay() * (-1);
						textTemp = textTemp + "  依據《" + codeNameNew + "條款》第 條第 款計算給付 （" + insuredName + "）保險金" + ConstantCodes.LOCAL_CURRENCYNAME + "：負" + MoneyUtils.toChinese(dbSumRealPay, ConstantCodes.LOCAL_CURRENCY) + "（$ "
								+ prpLpersonLossNew.getSumRealPay() + "）。" + strAddInfo;
						TextTemp2 = TextTemp2 + "  （" + this.getCodeService().translateRiskCode(prpLpersonLossNew.getRiskCode(), true) + "）保險金額增加" + ConstantCodes.LOCAL_CURRENCYNAME + ""
								+ MoneyUtils.toChinese(dbSumRealPay, ConstantCodes.LOCAL_CURRENCY) + "（" + dbSumRealPay + "$ ）。";
					} else {
						textTemp = textTemp + "  依據《" + codeNameNew + "條款》第 條第 款計算給付 （" + insuredName + "）保險金" + ConstantCodes.LOCAL_CURRENCYNAME + "：" + MoneyUtils.toChinese(prpLpersonLossNew.getSumRealPay(), ConstantCodes.LOCAL_CURRENCY) + "（$ "
								+ prpLpersonLossNew.getSumRealPay() + "）。" + strAddInfo;
						TextTemp2 = TextTemp2 + "  （" + this.getCodeService().translateRiskCode(prpLpersonLossNew.getRiskCode(), true) + "）保險金額沖減為" + ConstantCodes.LOCAL_CURRENCYNAME + ""
								+ MoneyUtils.toChinese(dblUnitAmountSum2, ConstantCodes.LOCAL_CURRENCY) + "（" + dblUnitAmountSum2 + "$ ）。";
					}
				}
				rulesAcci = StringUtils.split(textTemp, RULE_LENGTH, "GBK");
				for (int k = 0; k < rulesAcci.length; k++) {
					prpLctextList.add(strSpace + rulesAcci[k]);
				}
				textTemp = "2、自批改之日起" + policyNo + "保單項下：";
				rulesAcci = StringUtils.split(textTemp, RULE_LENGTH, "GBK");
				for (int k = 0; k < rulesAcci.length; k++) {
					prpLctextList.add(strSpace + rulesAcci[k]);
				}
				rulesAcci = StringUtils.split(TextTemp2, RULE_LENGTH, "GBK");
				for (int k = 0; k < rulesAcci.length; k++) {
					prpLctextList.add(strSpace + rulesAcci[k]);
				}
				textTemp = "3、其他不變。";
				rulesAcci = StringUtils.split(textTemp, RULE_LENGTH, "GBK");
				for (int k = 0; k < rulesAcci.length; k++) {
					prpLctextList.add(strSpace + rulesAcci[k]);
				}
			}
			for (i = 1; i <= prpLctextList.size(); i++) {
				prpLctext = new PrpLctext();
				prpLctext.getId().setCompensateNo(strCompensateNo);
				prpLctext.getId().setTextType("1");
				prpLctext.getId().setLineNo(i);
				prpLctext.setContext((String) prpLctextList.get(i - 1));
				prpLctextlist.add(prpLctext);
			}
		} else { // 非意健险理算模板信息
			for (i = 1; i <= prpLctextList.size(); i++) {
				prpLctext = new PrpLctext();
				prpLctext.getId().setCompensateNo(strCompensateNo);
				prpLctext.getId().setTextType("1");
				prpLctext.getId().setLineNo(i);
				prpLctext.setContext((String) prpLctextList.get(i - 1));
				prpLctextlist.add(prpLctext);
			}
		}
		prpLctext.setPrpLctextList(prpLctextlist);
		httpServletRequest.setAttribute("prpLctext", prpLctext);
	}

	@SuppressWarnings("unused")
	private void setLossFromPolicy(HttpServletRequest httpServletRequest, PolicyDto policyDto) {
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		PrpLloss prpLloss = null;
		if (policyDto.getPrpCitemKindList() != null) {
			PrpCitemKind prpCitemKind = null;
			int index = 1;
			for (Iterator<PrpCitemKind> iter = policyDto.getPrpCitemKindList().iterator(); iter.hasNext();) {
				prpCitemKind = iter.next();
				prpLloss = new PrpLloss();
				prpLloss.getId().setSerialNo(index++);
				// 理赔拆分危险单位
				prpLloss.setDangerNo(prpCitemKind.getId().getItemKindNo());
				prpLloss.setKindCode(prpCitemKind.getKindCode());
				prpLloss.setKindName(prpCitemKind.getKindName());
				prpLloss.setLossName(prpCitemKind.getItemDetailName());
				prpLloss.setItemValue(prpCitemKind.getAmount());
				prpLloss.setAmount(prpCitemKind.getAmount());
				prpLloss.setCurrency(prpCitemKind.getCurrency());
				prpLloss.setCurrency2(prpCitemKind.getCurrency());
				prpLloss.setCurrency3(prpCitemKind.getCurrency());
				prpLloss.setCurrency4(prpCitemKind.getCurrency());
				prpLloss.setDeductible(prpCitemKind.getDeductible());
				prpLloss.setDeductiblerate(prpCitemKind.getDeductibleRate());
				prpLloss.setClaimRate(prpCitemKind.getClaimRate());
				if (prpLloss.getCurrency().equals(ConstantCodes.LOCAL_CURRENCY)) {
					prpLloss.setCurrencyName(ConstantCodes.LOCAL_CURRENCYNAME);
				}
				if (prpLloss.getCurrency().equals("USD")) {
					prpLloss.setCurrencyName("美元");
				}
				if (prpLloss.getCurrency1().equals(ConstantCodes.LOCAL_CURRENCY)) {
					prpLloss.setCurrency1Name(ConstantCodes.LOCAL_CURRENCYNAME);
				}
				if (prpLloss.getCurrency1().equals("USD")) {
					prpLloss.setCurrency1Name("美元");
				}
				if (prpLloss.getCurrency2().equals(ConstantCodes.LOCAL_CURRENCY)) {
					prpLloss.setCurrency2Name(ConstantCodes.LOCAL_CURRENCYNAME);
				}
				if (prpLloss.getCurrency2().equals("USD")) {
					prpLloss.setCurrency2Name("美元");
				}
				if (prpLloss.getCurrency3().equals(ConstantCodes.LOCAL_CURRENCY)) {
					prpLloss.setCurrency3Name(ConstantCodes.LOCAL_CURRENCYNAME);
				}
				if (prpLloss.getCurrency3().equals("USD")) {
					prpLloss.setCurrency3Name("美元");
				}
				if (prpLloss.getCurrency4().equals(ConstantCodes.LOCAL_CURRENCY)) {
					prpLloss.setCurrency4Name(ConstantCodes.LOCAL_CURRENCYNAME);
				}
				if (prpLloss.getCurrency4().equals("USD")) {
					prpLloss.setCurrency4Name("美元");
				}
				prpLlossList.add(prpLloss);
			}
		}
		prpLloss = new PrpLloss();
		prpLloss.setPrpLlossList(prpLlossList);
		httpServletRequest.setAttribute("prpLloss", prpLloss);
	}

	@SuppressWarnings("unused")
	private void setChargeFromPolicy(HttpServletRequest httpServletRequest, PolicyDto policyDto) throws Exception {
		List<PrpLcharge> prpLchargeList = new ArrayList<PrpLcharge>();
		PrpLcharge prpLcharge = null;
		if (policyDto.getPrpCitemKindList() != null) {
			int index = 1;
			PrpCitemKind prpCitemKind = null;
			for (Iterator<PrpCitemKind> iter = policyDto.getPrpCitemKindList().iterator(); iter.hasNext();) {
				prpCitemKind = (PrpCitemKind) iter.next();
				prpLcharge = new PrpLcharge();
				prpLcharge.getId().setSerialNo(index++);
				// 理赔拆分危险单位
				prpLcharge.setDangerNo(prpCitemKind.getId().getItemKindNo());
				prpLcharge.setKindCode(prpCitemKind.getKindCode());
				prpLcharge.setKindName(prpCitemKind.getKindName());
				prpLcharge.setCurrency(prpCitemKind.getCurrency());
				prpLcharge.setCurrencyName(this.getCodeService().translateCurrencyCode(prpCitemKind.getCurrency(), "C".equals(policyDto.getPrpCmain().getLanguage())));
				prpLchargeList.add(prpLcharge);
			}
		}
		prpLcharge = new PrpLcharge();
		prpLcharge.setPrpLchargeList(prpLchargeList);
		httpServletRequest.setAttribute("prpLcharge", prpLcharge);
	}

	/**
	 * 意外险数据整理
	 * @param httpServletRequest
	 * @return compensateDto 实赔数据传输数据结构
	 * @throws Exception
	 */
	public CompensateDto viewToDtoForAccident(HttpServletRequest httpServletRequest) throws Exception {
		// 继承对compensate,compensateText表的赋值
		CompensateDto compensateDto = super.viewToDto(httpServletRequest);
		// 从界面得到输入数组
		String prpLpersonLossCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLpersonLossRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLpersonLossPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");

		/** 赔付人员处理 */
		// 理赔拆分危险单位
		String[] prpLpersonLossDangerNo = httpServletRequest.getParameterValues("prpLpersonLossDangerNo");
		String[] personLossPersonNo = httpServletRequest.getParameterValues("personLossPersonNo");
		String[] prpLpersonLossPersonName = httpServletRequest.getParameterValues("prpLpersonLossPersonName");
		String[] prpLpersonLossSex = httpServletRequest.getParameterValues("prpLpersonLossSex");
		String[] prpLpersonLossAge = httpServletRequest.getParameterValues("prpLpersonLossAge");
		String[] prpLpersonLossIdentifyNumber = httpServletRequest.getParameterValues("prpLpersonLossIdentifyNumber");
		// 险别最大赔付额
		String[] prpLpersonLossMaxPaid = httpServletRequest.getParameterValues("prpLpersonLossMaxPaid");
		String[] prpLpersonLossHisPaid = httpServletRequest.getParameterValues("prpLpersonLossHisPaid");
		String[] prpLpersonLossPoliceName = httpServletRequest.getParameterValues("prpLpersonLossPoliceName");
		String[] prpLpersonLossPoliceUnits = httpServletRequest.getParameterValues("prpLpersonLossPoliceUnits");
		/** 就诊医院 */
		String[] hospitalPersonNo = httpServletRequest.getParameterValues("hospitalPersonNo");
		String[] prpLpersonHospitalHospitalCode = httpServletRequest.getParameterValues("prpLpersonHospitalHospitalCode");
		String[] prpLpersonHospitalHospitalName = httpServletRequest.getParameterValues("prpLpersonHospitalHospitalName");
		String[] prpLpersonHospitalInHospDate = httpServletRequest.getParameterValues("prpLpersonHospitalInHospDate");
		String[] prpLpersonHospitalOutHospDate = httpServletRequest.getParameterValues("prpLpersonHospitalOutHospDate");
		String[] prpLpersonHospitalDoctor = httpServletRequest.getParameterValues("prpLpersonHospitalDoctor");
		String[] prpLpersonHospitalDiagnosisDivision = httpServletRequest.getParameterValues("prpLpersonHospitalDiagnosisDivision");
		String[] prpLpersonHospitalDiagnosisName = httpServletRequest.getParameterValues("prpLpersonHospitalDiagnosisName");

		/** 赔付险别信息 */
		String[] prpLpersonLossPersonNo = httpServletRequest.getParameterValues("prpLpersonLossPersonNo");
		String[] prpLpersonLossItemKindNo = httpServletRequest.getParameterValues("prpLpersonLossItemKindNo");
		String[] prpLpersonLossFamilyNo = httpServletRequest.getParameterValues("prpLpersonLossFamilyNo");
		String[] prpLpersonLossFamilyName = httpServletRequest.getParameterValues("prpLpersonLossFamilyName");
		String[] prpLpersonLossKindCode = httpServletRequest.getParameterValues("prpLpersonLossKindCode");
		String[] prpLpersonLossLiabCode = httpServletRequest.getParameterValues("prpLpersonLossLiabCode");
		String[] prpLpersonLossLiabName = httpServletRequest.getParameterValues("prpLpersonLossLiabName");
		String[] prpLpersonLossJobCode = httpServletRequest.getParameterValues("prpLpersonLossJobCode");
		String[] prpLpersonLossJobName = httpServletRequest.getParameterValues("prpLpersonLossJobName");
		// String[] prpLpersonLossLiabDetailCode =
		// httpServletRequest.getParameterValues("prpLpersonLossLiabDetailCode");
		// String[] prpLpersonLossLiabDetailName =
		// httpServletRequest.getParameterValues("prpLpersonLossLiabDetailName");
		String[] prpLpersonLossItemAddress = httpServletRequest.getParameterValues("prpLpersonLossItemAddress");
		String[] prpLpersonLossLossQuantity = httpServletRequest.getParameterValues("prpLpersonLossLossQuantity");
		String[] prpLpersonLossUnit = httpServletRequest.getParameterValues("prpLpersonLossUnit");
		String[] prpLpersonLossUnitAmount = httpServletRequest.getParameterValues("prpLpersonLossUnitAmount");
		String[] prpLpersonLossCurrency = httpServletRequest.getParameterValues("prpLpersonLossCurrency");
		String[] prpLpersonLossAmount = httpServletRequest.getParameterValues("prpLpersonLossAmount");
		String[] prpLpersonLossCurrency1 = httpServletRequest.getParameterValues("prpLpersonLossCurrency1");
		String[] prpLpersonLossItemValue = httpServletRequest.getParameterValues("prpLpersonLossItemValue");
		String[] prpLpersonLossCurrency2 = httpServletRequest.getParameterValues("prpLpersonLossCurrency2");
		String[] prpLpersonLossSumLoss = httpServletRequest.getParameterValues("prpLpersonLossSumLoss");
		String[] prpLpersonLossSumRest = httpServletRequest.getParameterValues("prpLpersonLossSumRest");
		String[] prpLpersonLossIndemnityDutyRate = httpServletRequest.getParameterValues("prpLpersonLossIndemnityDutyRate");
		String[] prpLpersonLossClaimRate = httpServletRequest.getParameterValues("prpLpersonLossClaimRate");
		String[] prpLpersonLossCurrency3 = httpServletRequest.getParameterValues("prpLpersonLossCurrency3");
		String[] prpLpersonLossDeductibleRate = httpServletRequest.getParameterValues("prpLpersonLossDeductibleRate");
		String[] prpLpersonLossDeductible = httpServletRequest.getParameterValues("prpLpersonLossDeductible");
		String[] prpLpersonLossCurrency4 = httpServletRequest.getParameterValues("prpLpersonLossCurrency4");
		String[] prpLpersonLossSumRealPay = httpServletRequest.getParameterValues("prpLpersonLossSumRealPay");
		String[] prpLpersonLossFlag = httpServletRequest.getParameterValues("prpLpersonLossFlag");
		// 需求變更#83二次調整   add by 中科軟 begin 
		// 保留預估
		String[] prpLpersonLossReservedEstimate = httpServletRequest.getParameterValues("prpLpersonLossReservedEstimate");
		// 需求變更#83二次調整   add by 中科軟 end 
		// 賠付對象訊息
		String[] prpLpersonLossPayObjectSerialNo = httpServletRequest.getParameterValues("prpLpersonLossPayObjectSerialNo");
		String[] prpLpersonLossContractingScope = httpServletRequest.getParameterValues("prpLpersonLossContractingScope");
		String[] prpLpersonLossPaymentType = httpServletRequest.getParameterValues("prpLpersonLossPaymentType");
		String[] prpLpersonLossPaymentType1 = httpServletRequest.getParameterValues("prpLpersonLossPaymentType1");
		String[] prpLpersonLossPaymentType2 = httpServletRequest.getParameterValues("prpLpersonLossPaymentType2");
		String[] prpLpersonLossPaymentRate = httpServletRequest.getParameterValues("prpLpersonLossPaymentRate");
		String[] prpLpersonLossPaymentContent = httpServletRequest.getParameterValues("prpLpersonLossPaymentContent");
		String[] prpLpersonLossFractureSite = httpServletRequest.getParameterValues("prpLpersonLossFractureSite");
		String[] prpLpersonLossNotHospitalDays = httpServletRequest.getParameterValues("prpLpersonLossNotHospitalDays");
		String[] prpLpersonLossFractureDegree = httpServletRequest.getParameterValues("prpLpersonLossFractureDegree");
		// String[] prpLpersonLossRemark =
		// httpServletRequest.getParameterValues("prpLpersonLossRemark");
		// 伤残等级,入院日期,出院日期,住院天数
		// String[] prpLpersonLossInjuryGrade =
		// httpServletRequest.getParameterValues("prpLpersonLossInjuryGrade");
		// String[] prpLpersonLossInHospDate =
		// httpServletRequest.getParameterValues("prpLpersonLossInHospDate");
		// String[] prpLpersonLossOutHospDate =
		// httpServletRequest.getParameterValues("prpLpersonLossOutHospDate");
		// String[] prpLpersonLossHospitalDays =
		// httpServletRequest.getParameterValues("prpLpersonLossHospitalDays");
		String[] prpLpersonLossDeathDate = httpServletRequest.getParameterValues("prpLpersonLossDeathDate");
		String[] prpLpersonLossDeathAddressCode = httpServletRequest.getParameterValues("prpLpersonLossDeathAddressCode");
		String[] prpLpersonLossDeathAddressName = httpServletRequest.getParameterValues("prpLpersonLossDeathAddressName");
		String[] prpLpersonLossProsecutorsOffice = httpServletRequest.getParameterValues("prpLpersonLossProsecutorsOffice");
		String[] prpLpersonLossDeathPlace = httpServletRequest.getParameterValues("prpLpersonLossDeathPlace");
		String[] prpLpersonLossDeathManner = httpServletRequest.getParameterValues("prpLpersonLossDeathManner");
		String[] prpLpersonLossProsecutor = httpServletRequest.getParameterValues("prpLpersonLossProsecutor");
		String[] prpLpersonLossDeathCertificateDate = httpServletRequest.getParameterValues("prpLpersonLossDeathCertificateDate");
		String[] prpLpersonLossCourtDoctor = httpServletRequest.getParameterValues("prpLpersonLossCourtDoctor");
		String[] prpLpersonLossExchRate = httpServletRequest.getParameterValues("prpLpersonLossExchRate");
		// 加入危险单位处理
		// 目前只有一个危险单位，所以和标的信息放在一起处理，如果，有多个危险单位必须放入危险单位信息里面处理！
		List<PrpLDangerItem> prpLprpLdangerItemList = new ArrayList<PrpLDangerItem>(); // 理赔的危险单位信息表

		List<PrpLpersonLoss> prpLpersonLossList = new ArrayList<PrpLpersonLoss>();
		List<PrpLpersonHospital> prpLpersonHospitalList = new ArrayList<PrpLpersonHospital>();
		PrpLpersonLoss prpLpersonLoss = null;
		PrpLpersonHospital prpLpersonHospital = null;
		if (prpLpersonLossPersonNo != null && prpLpersonLossPersonNo.length > 0) {
			Map<String,List<PrpLpersonHospital>> tempMap = new HashMap<String,List<PrpLpersonHospital>>();
			for (int index = 1; index < hospitalPersonNo.length; index++) {				
				prpLpersonHospital = new PrpLpersonHospital();
				prpLpersonHospital.getId().setCompensateNo(prpLpersonLossCompensateNo);
				prpLpersonHospital.getId().setSerialNo(index);
				prpLpersonHospital.setPersonNo(Integer.parseInt(hospitalPersonNo[index]));
				prpLpersonHospital.setHospitalCode(prpLpersonHospitalHospitalCode[index]);
				prpLpersonHospital.setHospitalName(prpLpersonHospitalHospitalName[index]);
				if (!StringUtil.isBlank(prpLpersonHospitalInHospDate[index])) {
					prpLpersonHospital.setInHospDate(new DateTime(prpLpersonHospitalInHospDate[index]));
				}
				if (!StringUtil.isBlank(prpLpersonHospitalOutHospDate[index])) {
					prpLpersonHospital.setOutHospDate(new DateTime(prpLpersonHospitalOutHospDate[index]));
				}
				prpLpersonHospital.setDoctor(prpLpersonHospitalDoctor[index]);
				prpLpersonHospital.setDiagnosisDivision(prpLpersonHospitalDiagnosisDivision[index]);
				prpLpersonHospital.setDiagnosisName(prpLpersonHospitalDiagnosisName[index]);
				if(tempMap.containsKey(hospitalPersonNo[index])){
					tempMap.get(hospitalPersonNo[index]).add(prpLpersonHospital);
				}else{
					List<PrpLpersonHospital> tempList = new ArrayList<PrpLpersonHospital>();
					tempList.add(prpLpersonHospital);
					tempMap.put(hospitalPersonNo[index], tempList);
				}
				prpLpersonHospitalList.add(prpLpersonHospital);
			}
			for (int index = 1; index < prpLpersonLossPersonNo.length; index++) {
				prpLpersonLoss = new PrpLpersonLoss();
				prpLpersonLoss.setPolicyNo(prpLpersonLossPolicyNo);
				prpLpersonLoss.setRiskCode(prpLpersonLossRiskCode);
				prpLpersonLoss.getId().setCompensateNo(prpLpersonLossCompensateNo);
				prpLpersonLoss.getId().setSerialNo(index);
				prpLpersonLoss.setLiabCode(prpLpersonLossLiabCode[index]);
				prpLpersonLoss.setLiabName(prpLpersonLossLiabName[index]);
				prpLpersonLoss.setJobCode(prpLpersonLossJobCode[index]);
				prpLpersonLoss.setJobName(prpLpersonLossJobName[index]);
				prpLpersonLoss.setItemAddress(prpLpersonLossItemAddress[index]);
				prpLpersonLoss.setUnit(prpLpersonLossUnit[index]);
				prpLpersonLoss.setCurrency(prpLpersonLossCurrency[index]);
				prpLpersonLoss.setExchRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossExchRate[index])));
				prpLpersonLoss.setAmount(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossAmount[index])));
				prpLpersonLoss.setCurrency1(prpLpersonLossCurrency1[index]);
				prpLpersonLoss.setItemValue(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossItemValue[index])));
				prpLpersonLoss.setCurrency2(prpLpersonLossCurrency2[index]);
				prpLpersonLoss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRest[index])));
				prpLpersonLoss.setClaimRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossClaimRate[index])));
				prpLpersonLoss.setDeductible(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductible[index])));
				prpLpersonLoss.setCurrency4(prpLpersonLossCurrency4[index]);
				prpLpersonLoss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRealPay[index])));
				prpLpersonLoss.setFlag(prpLpersonLossFlag[index]);
				// prpLpersonLoss.setLiabDetailCode(prpLpersonLossLiabDetailCode[index]);
				// prpLpersonLoss.setLiabDetailName(prpLpersonLossLiabDetailName[index]);
				prpLpersonLoss.setUnitAmount(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossUnitAmount[index])));

				// prpLpersonLoss.setInjuryGrade(prpLpersonLossInjuryGrade[index]);
				// prpLpersonLoss.setHospitalDays(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossHospitalDays[index])));
				// prpLpersonLoss.setInHospDate(new
				// DateTime(prpLpersonLossInHospDate[index]));
				// prpLpersonLoss.setOutHospDate(new
				// DateTime(prpLpersonLossOutHospDate[index]));
				prpLpersonLoss.setLossQuantity(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossLossQuantity[index])));
				prpLpersonLoss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumLoss[index])));
				prpLpersonLoss.setCurrency3(prpLpersonLossCurrency3[index]);
				prpLpersonLoss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossIndemnityDutyRate[index])));
				prpLpersonLoss.setDeductiblerate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductibleRate[index])));
				prpLpersonLoss.setPersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossPersonNo[index])));
				prpLpersonLoss.setKindCode(DataUtils.nullToZero(prpLpersonLossKindCode[index]));
				prpLpersonLoss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossItemKindNo[index])));
				prpLpersonLoss.setPayObjectSerialNo(DataUtils.dbNullToEmpty(prpLpersonLossPayObjectSerialNo[index]));
				// 需求變更#83二次調整   add by 中科軟 begin 
				// 保留預估
				prpLpersonLoss.setReservedEstimate(DataUtils.dbNullToEmpty(prpLpersonLossReservedEstimate[index]));
				// 需求變更#83二次調整   add by 中科軟 end 
				prpLpersonLoss.setContractingScope(prpLpersonLossContractingScope[index]);
				prpLpersonLoss.setPaymentType(prpLpersonLossPaymentType[index]);
				prpLpersonLoss.setPaymentType1(prpLpersonLossPaymentType1[index]);
				prpLpersonLoss.setPaymentType2(prpLpersonLossPaymentType2[index]);
				prpLpersonLoss.setPaymentRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossPaymentRate[index])));
				prpLpersonLoss.setPaymentContent(prpLpersonLossPaymentContent[index]);
				prpLpersonLoss.setFractureSite(prpLpersonLossFractureSite[index]);
				prpLpersonLoss.setNotHospitalDays(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossNotHospitalDays[index])));
				prpLpersonLoss.setFractureDegree(prpLpersonLossFractureDegree[index]);
				if (!StringUtil.isBlank(prpLpersonLossDeathDate[index])) {
					prpLpersonLoss.setDeathDate(new DateTime(prpLpersonLossDeathDate[index]));
				}
				prpLpersonLoss.setDeathAddressCode(prpLpersonLossDeathAddressCode[index]);
				prpLpersonLoss.setDeathAddressName(prpLpersonLossDeathAddressName[index]);
				prpLpersonLoss.setProsecutorsOffice(prpLpersonLossProsecutorsOffice[index]);
				prpLpersonLoss.setDeathPlace(prpLpersonLossDeathPlace[index]);
				prpLpersonLoss.setDeathManner(prpLpersonLossDeathManner[index]);
				prpLpersonLoss.setProsecutor(prpLpersonLossProsecutor[index]);
				if (!StringUtil.isBlank(prpLpersonLossDeathCertificateDate[index])) {
					prpLpersonLoss.setDeathCertificateDate(new DateTime(prpLpersonLossDeathCertificateDate[index]));
				}
				prpLpersonLoss.setCourtDoctor(prpLpersonLossCourtDoctor[index]);
				//設置受害人醫院為其
				List<PrpLpersonHospital> tempList = tempMap.get(prpLpersonLossPersonNo[index]);
				PrpLpersonHospital tempHospital = tempList.get(tempList.size()-1);
				prpLpersonLoss.setHospitalCode(tempHospital.getHospitalCode());
				prpLpersonLoss.setHospitalName(tempHospital.getHospitalName());
				for (int index2 = 1; index2 < personLossPersonNo.length; index2++) {
					if (prpLpersonLossPersonNo[index].equals(personLossPersonNo[index2])) {
						prpLpersonLoss.setDangerNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossDangerNo[index2])));
						prpLpersonLoss.setAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossAge[index2])));
						prpLpersonLoss.setPersonName(prpLpersonLossPersonName[index2]);
						prpLpersonLoss.setSex(prpLpersonLossSex[index2]);
						prpLpersonLoss.setIdentifyNumber(prpLpersonLossIdentifyNumber[index2]);
						prpLpersonLoss.setDblMaxPaid(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossMaxPaid[index2])));
						prpLpersonLoss.setMaxpaid(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossMaxPaid[index2])));
						prpLpersonLoss.setHispaid(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossHisPaid[index2])));
						prpLpersonLoss.setPoliceName(prpLpersonLossPoliceName[index2]);
						prpLpersonLoss.setPoliceUnits(prpLpersonLossPoliceUnits[index2]);
						prpLpersonLoss.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossFamilyNo[index2])));
						prpLpersonLoss.setFamilyName(prpLpersonLossFamilyName[index2]);
					}
				}
				prpLpersonLossList.add(prpLpersonLoss);
			}
		}
		// 赔付人员信息
		compensateDto.setPrpLpersonLossList(prpLpersonLossList);
		compensateDto.setPrpLpersonHospitalList(prpLpersonHospitalList);
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		compensateDto.setPrpLlossList(prpLlossList);

		// 从界面得到输入数组
		String proposerClaimNo = (String) httpServletRequest.getParameter("prpLcompensateClaimNo");
		String proposerPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String[] proposerName = httpServletRequest.getParameterValues("proposerName");
		String[] proposerSerialNo = httpServletRequest.getParameterValues("prpLacciPersonSerialNo");
		String[] proposerIdentifyNumber = httpServletRequest.getParameterValues("proposerIdentifyNumber");
		String[] proposerRelation = httpServletRequest.getParameterValues("relationCode");
		String[] proposerPhone = httpServletRequest.getParameterValues("proposerPhone");
		String[] proposerAddress = httpServletRequest.getParameterValues("proposerAddress");

		// 索赔申请人信息
		List<PrpLacciPerson> prpLacciPersonList = new ArrayList<PrpLacciPerson>();
		if (proposerSerialNo != null && proposerSerialNo.length > 0) {
			PrpLacciPerson prpLacciPerson = null;
			for (int index = 1; index < proposerSerialNo.length; index++) {
				prpLacciPerson = new PrpLacciPerson();
				prpLacciPerson.setAcciName(proposerName[index]);
				prpLacciPerson.getId().setCertiNo(proposerClaimNo);
				prpLacciPerson.getId().setCertiType("03");
				prpLacciPerson.setPolicyNo(proposerPolicyNo);
				prpLacciPerson.setFlag("1"); // 标志是索赔人
				prpLacciPerson.setIdentifyNumber(proposerIdentifyNumber[index]);
				prpLacciPerson.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(proposerSerialNo[index])));
				prpLacciPerson.setAddress(proposerAddress[index]);
				prpLacciPerson.setFamilyNo(0); // 家庭序号
				prpLacciPerson.setPhone(proposerPhone[index]);
				prpLacciPerson.setRelationCode(proposerRelation[index]);
				String relationName = "";
				if (proposerRelation[index].equals("1")) {
					relationName = "被保險人本人";
				} else if (proposerRelation[index].equals("2")) {
					relationName = "指定受益人";
				} else if (proposerRelation[index].equals("3")) {
					relationName = "被保險人之繼承人";
				} else if (proposerRelation[index].equals("4")) {
					relationName = "被保險人之監護人";
				}
				prpLacciPerson.setRelationName(relationName);
				prpLacciPerson.setFamilyNo(1);
				// 加入集合
				prpLacciPersonList.add(prpLacciPerson);
			}
		}
		// 意健险立案集合中加入索赔申请人
		compensateDto.setPrpLacciPersonList(prpLacciPersonList);

		// 从界面得到输入数组
		String prpLchargePolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String prpLchargeRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLchargeCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String[] prpLchargeSerialNo = httpServletRequest.getParameterValues("prpLchargeSerialNo");
		String[] prpLchargeKindCode = httpServletRequest.getParameterValues("prpLchargeKindCode");
		String[] prpLchargeChargeCode = httpServletRequest.getParameterValues("prpLchargeChargeCode");
		String[] prpLchargeChargeName = httpServletRequest.getParameterValues("prpLchargeChargeName");
		String[] prpLchargeCurrency = httpServletRequest.getParameterValues("prpLchargeCurrency");
		String[] prpLchargeChargeAmount = httpServletRequest.getParameterValues("prpLchargeChargeAmount");
		String[] prpLchargeSumRealPay = httpServletRequest.getParameterValues("prpLchargeSumRealPay");
		String[] prpLchargeFlag = httpServletRequest.getParameterValues("prpLchargeFlag");
		String[] prpLchargeChargeReport = httpServletRequest.getParameterValues("prpLchargeChargeReport"); // add
		// 20060512
		// 理赔拆分危险单位
		String[] prpLchargeDangerNo = httpServletRequest.getParameterValues("prpLchargeDangerNo");
		// 增加对支付对象的保存
		// String[] prpLchargeAccountCode =
		// httpServletRequest.getParameterValues("prpLchargeAccountCode");
		// String[] prpLchargeAccountType =
		// httpServletRequest.getParameterValues("prpLchargeAccountType");
		// String[] prpLchargeBankCode =
		// httpServletRequest.getParameterValues("prpLchargeBankCode");
		// String[] prpLchargeBankName =
		// httpServletRequest.getParameterValues("prpLchargeBankName");
		// String[] prpLchargeOwnerName =
		// httpServletRequest.getParameterValues("prpLchargeOwnerName");
		// String[] prpLchargeCertifiCateCode =
		// httpServletRequest.getParameterValues("prpLchargeCertifiCateCode");
		// String[] prpLchargePhoneNo =
		// httpServletRequest.getParameterValues("prpLchargePhoneNo");
		// String[] prpLchargeCustomBankName =
		// httpServletRequest.getParameterValues("prpLchargeCustomBankName");
		// String[] prpLchargeAccountCurrency =
		// httpServletRequest.getParameterValues("prpLchargeAccountCurrency");
		// String[] prpLchargeOwnerShip =
		// httpServletRequest.getParameterValues("prpLchargeOwnerShip");

		// String[] prpLchargeOwnerNameCQ =
		// httpServletRequest.getParameterValues("prpLchargeOwnerNameCQ");
		// String[] prpLchargeCertifiCateCodeCQ =
		// httpServletRequest.getParameterValues("prpLchargeCertifiCateCodeCQ");
		 //增加费用支付类别、支付对象名称
		String[] prpLchargePayObjectType = httpServletRequest.getParameterValues("prpLchargePayObjectType");
		String[] prpLchargePayObjectCode = httpServletRequest.getParameterValues("prpLchargePayObjectCode");
		String[] prpLchargePayObjectName = httpServletRequest.getParameterValues("prpLchargePayObjectName");

		// 增加对支付对象的保存
		String[] prpLchargeOwnerShip = httpServletRequest.getParameterValues("prpLchargeOwnerShip");// 費用支付方式
		String[] prpLchargeOwnerName = httpServletRequest.getParameterValues("prpLchargeOwnerName");// 賠付對象
		String[] prpLchargeUniformNo = httpServletRequest.getParameterValues("prpLchargeUniformNo");// ID/統一編號
		String[] prpLchargeCutBack = httpServletRequest.getParameterValues("prpLchargeCutBack");// 禁背
		String[] prpLchargeBankCode = httpServletRequest.getParameterValues("prpLchargeBankCode");// 總行代號
		String[] prpLchargeBankName = httpServletRequest.getParameterValues("prpLchargeBankName");// 總行名稱
		String[] prpLchargeAccountCode = httpServletRequest.getParameterValues("prpLchargeAccountCode");// 匯款帳號
		String[] prpLchargeCustomBankCode = httpServletRequest.getParameterValues("prpLchargeCustomBankCode");// 分行代號
		String[] prpLchargeCustomBankName = httpServletRequest.getParameterValues("prpLchargeCustomBankName");// 分行名稱
		String[] prpLchargeAreaCode = httpServletRequest.getParameterValues("prpLchargeAreaCode");// 郵遞區號
		String[] prpLchargeCourierAddress = httpServletRequest.getParameterValues("prpLchargeCourierAddress");// 郵遞地址
		String[] prpLchargeCurrencyForPayObject = httpServletRequest.getParameterValues("prpLchargeCurrencyForPayObject");// 支付幣別
		String[] prpLchargeCertificateCode = httpServletRequest.getParameterValues("prpLchargeCertificateCode");// 证件类型
		String[] prpLchargeAccountCurrency = httpServletRequest.getParameterValues("prpLchargeAccountCurrency");// 币别
		String[] prpLchargeExchRate = httpServletRequest.getParameterValues("prpLchargeExchRate");// 汇率
		String[] prpLchargeFeeSerialNo = httpServletRequest.getParameterValues("prpLchargeFeeSerialNo");// 代扣費用序號
		String[] prpLchargeItemKindNo = httpServletRequest.getParameterValues("prpLchargeItemKindNo");//险别序号
		//mantis： CLM0113，處理人員：BK007 蘇哲，需求單編號：CLM0113   傷害險增加AML功能
		String[] prpLchargeAMLFlag = httpServletRequest.getParameterValues("prpLchargeAMLFlag");// AML命中結果
		
		// 赔款费用信息
		List<PrpLcharge> prpLchargeList = new ArrayList<PrpLcharge>();
		List<PrpLpayObjectInfo> prpLpayObjectInfoList = new ArrayList<PrpLpayObjectInfo>();
		PrpLpayObjectInfo prpLpayObjectInfo = null;
		// 对象赋值
		if (prpLchargeSerialNo != null && prpLchargeSerialNo.length > 0) {
			PrpLcharge prpLcharge = null;
			for (int index = 1; index < prpLchargeSerialNo.length; index++) {
				prpLcharge = new PrpLcharge();
				prpLcharge.setPolicyNo(prpLchargePolicyNo);
				prpLcharge.setRiskCode(prpLchargeRiskCode);
				prpLcharge.getId().setCompensateNo(prpLchargeCompensateNo);
				prpLcharge.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLchargeSerialNo[index])));
				prpLcharge.setKindCode(prpLchargeKindCode[index]);
				prpLcharge.setChargeCode(prpLchargeChargeCode[index]);
				prpLcharge.setChargeName(prpLchargeChargeName[index]);
				prpLcharge.setCurrency(prpLchargeCurrency[index]);
				prpLcharge.setExchRate(Double.parseDouble(DataUtils.nullToZero(prpLchargeExchRate[index])));
				prpLcharge.setChargeAmount(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeAmount[index])));
				prpLcharge.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLchargeSumRealPay[index])));
				prpLcharge.setFlag(prpLchargeFlag[index]);
				prpLcharge.setOwnerShip(prpLchargeOwnerShip[index]);
				prpLcharge.setAccountCode(prpLchargeAccountCode[index]);
				// prpLcharge.setAccountType(prpLchargeAccountType[index]);
				prpLcharge.setBankCode(prpLchargeBankCode[index]);
				prpLcharge.setBankName(prpLchargeBankName[index]);
				prpLcharge.setCustomBankName(prpLchargeCustomBankName[index]);
				prpLcharge.setOwnerName(prpLchargeOwnerName[index]);
				prpLcharge.setPayObjectCode(CommonUtils.getValue(prpLchargePayObjectCode, index));
				prpLcharge.setPayObjectType(CommonUtils.getValue(prpLchargePayObjectType, index));
				prpLcharge.setPayObjectName(CommonUtils.getValue(prpLchargePayObjectName, index));
				if (prpLchargeChargeReport != null && prpLchargeChargeReport.length > 0) {
					prpLcharge.setChargeReport(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeReport[index]))); // add
				}
				prpLcharge.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(CommonUtils.getValue(prpLchargeItemKindNo, index))));
				// 理赔拆分危险单位
				prpLcharge.setDangerNo(Integer.parseInt(DataUtils.nullToZero(prpLchargeDangerNo[index])));
				//mantis： CLM0113，處理人員：BK007 蘇哲，需求單編號：CLM0113   傷害險增加AML功能 -start
				if (prpLchargeAMLFlag!=null&&prpLchargeAMLFlag.length>0) {
					prpLcharge.setAmlFlag(prpLchargeAMLFlag[index]) ;
					prpLcharge.setAmlDate(new Date()) ;
				}
				//mantis： CLM0113，處理人員：BK007 蘇哲，需求單編號：CLM0113   傷害險增加AML功能 -end

				if(!CommonUtils.isEmpty(prpLchargeFeeSerialNo[index])){
					prpLcharge.setFeeSerialNo(Integer.parseInt(prpLchargeFeeSerialNo[index]));
				}
				prpLpayObjectInfo = new PrpLpayObjectInfo();
				// 增加对支付对象的保存
				prpLpayObjectInfo.getId().setCompensateNo(prpLchargeCompensateNo);
				prpLpayObjectInfo.getId().setCertiType(PrpLpayObjectInfo.CERTITYPE_CHARGE);
				prpLpayObjectInfo.getId().setSerialNo(index);
				prpLpayObjectInfo.setRiskCode(prpLchargeRiskCode);
				prpLpayObjectInfo.setKindCode(prpLchargeKindCode[index]);

				prpLpayObjectInfo.setOwnerName(prpLchargeOwnerName[index]);
				prpLpayObjectInfo.setUniformNo(prpLchargeUniformNo[index]);
				prpLpayObjectInfo.setAreaCode(prpLchargeAreaCode[index]);
				prpLpayObjectInfo.setCourierAddress(prpLchargeCourierAddress[index]);
				prpLpayObjectInfo.setOwnerShip(prpLchargeOwnerShip[index]);
				if ("B".equals(prpLchargeOwnerShip[index])) {// 汇款
					prpLpayObjectInfo.setBankCode(prpLchargeBankCode[index]);
					prpLpayObjectInfo.setBankName(prpLchargeBankName[index]);
					prpLpayObjectInfo.setAccountCode(prpLchargeAccountCode[index]);
					prpLpayObjectInfo.setCustomBankCode(prpLchargeCustomBankCode[index]);
					prpLpayObjectInfo.setCustomBankName(prpLchargeCustomBankName[index]);
				} else if ("Q".equals(prpLchargeOwnerShip[index])) {// 支票
					prpLpayObjectInfo.setCutBack(prpLchargeCutBack[index]);
				}
				// 存实际费用
				prpLpayObjectInfo.setPayAmount(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeAmount[index])));
				// 存支付币别
				prpLpayObjectInfo.setAccountCurrency(prpLchargeAccountCurrency[index]);
				prpLpayObjectInfo.setCurrency(prpLchargeCurrencyForPayObject[index]);
				prpLpayObjectInfo.setCertificateCode(prpLchargeCertificateCode[index]);
				//mantis： CLM0113，處理人員：BK007 蘇哲，需求單編號：CLM0113   傷害險增加AML功能 -start
				if (prpLchargeAMLFlag!=null&&prpLchargeAMLFlag.length>0) {
					prpLpayObjectInfo.setAmlFlag(prpLchargeAMLFlag[index]) ;
					prpLpayObjectInfo.setAmlDate(new Date()) ;
				}
				//mantis： CLM0113，處理人員：BK007 蘇哲，需求單編號：CLM0113   傷害險增加AML功能 -end
				prpLpayObjectInfo.setExchRate(prpLcharge.getExchRate());
				prpLchargeList.add(prpLcharge);
				prpLpayObjectInfoList.add(prpLpayObjectInfo);
			}
		}
		// 赔款费用信息
		compensateDto.setPrpLchargeList(prpLchargeList);

		// 客制化开发，收集賠款給付對象資訊，start,liuwei
		/******************* 賠付對象信息 start ******************************/
		// 从界面得到输入数组
		String prpLpayObjectInfoPaycodeType = httpServletRequest.getParameter("prpLpayObjectInfoPaycodeType");
		String[] prpLpayObjectInfoOwnerShip = httpServletRequest.getParameterValues("prpLpayObjectInfoOwnerShip");
		String[] prpLpayObjectInfoPayAmount = httpServletRequest.getParameterValues("prpLpayObjectInfoPayAmount");
		String[] prpLpayObjectInfoOwnerName = httpServletRequest.getParameterValues("prpLpayObjectInfoOwnerName");
		String[] prpLpayObjectInfoPaymentKind = httpServletRequest.getParameterValues("prpLpayObjectInfoPaymentKind");
		String[] prpLpayObjectInfoUniformNo = httpServletRequest.getParameterValues("prpLpayObjectInfoUniformNo");
		String[] prpLpayObjectInfoBeneficiaryPhone = httpServletRequest.getParameterValues("prpLpayObjectInfoBeneficiaryPhone");
		String[] prpLpayObjectInfoCutBack = httpServletRequest.getParameterValues("prpLpayObjectInfoCutBack");
		String[] prpLpayObjectInfoBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoBankCode");
		String[] prpLpayObjectInfoBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoBankName");
		String[] prpLpayObjectInfoAccountCode = httpServletRequest.getParameterValues("prpLpayObjectInfoAccountCode");
		String[] prpLpayObjectInfoCustomBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankCode");
		String[] prpLpayObjectInfoCustomBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankName");
		String[] prpLpayObjectInfoCourierAddress = httpServletRequest.getParameterValues("prpLpayObjectInfoCourierAddress");
		String[] prpLpayObjectInfoAreaCode = httpServletRequest.getParameterValues("prpLpayObjectInfoAreaCode");
		String[] prpLpayObjectInfoPayDate = httpServletRequest.getParameterValues("prpLpayObjectInfoPayDate");
		String[] prpLpayObjectInfoMobilePhoneNo = httpServletRequest.getParameterValues("prpLpayObjectInfoMobilePhoneNo");
		String[] prpLpayObjectInfoCurrency = httpServletRequest.getParameterValues("prpLpayObjectInfoCurrency");// 支付幣別
		String[] prpLpayObjectInfoCertificateCode = httpServletRequest.getParameterValues("prpLpayObjectInfoCertificateCode");// 证件类型
		String[] prpLpayObjectInfoAccountCurrency = httpServletRequest.getParameterValues("prpLpayObjectInfoAccountCurrency");// 支付幣別
		String[] prpLpayObjectInfoExchRate = httpServletRequest.getParameterValues("prpLpayObjectInfoExchRate");// 支付幣別
		//mantis： CLM0113，處理人員：BL061 張明財，需求單編號：CLM0113   傷害險增加AML功能
		String[] prpLpayObjectInfoAMLFlag = httpServletRequest.getParameterValues("prpLpayObjectInfoAMLFlag");
		//mantis：CLM0113，處理人員：BL061 張明財，需求單編號：CLM0113   傷害險增加AML功能
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int index = 1; index < prpLpayObjectInfoOwnerShip.length; index++) {
			prpLpayObjectInfo = new PrpLpayObjectInfo();
			prpLpayObjectInfo.getId().setCompensateNo((String) httpServletRequest.getAttribute("compensateNo"));
			prpLpayObjectInfo.getId().setSerialNo(index);
			prpLpayObjectInfo.getId().setCertiType(PrpLpayObjectInfo.CERTITYPE_PAYOBJECT);

			prpLpayObjectInfo.setRiskCode(prpLpersonLossRiskCode);
			prpLpayObjectInfo.setPaycodeType(prpLpayObjectInfoPaycodeType);
			prpLpayObjectInfo.setOwnerShip(prpLpayObjectInfoOwnerShip[index]);
			prpLpayObjectInfo.setPayAmount(Double.parseDouble(DataUtils.nullToZero(prpLpayObjectInfoPayAmount[index])));
			prpLpayObjectInfo.setOwnerName(prpLpayObjectInfoOwnerName[index]);
			prpLpayObjectInfo.setPaymentKind(prpLpayObjectInfoPaymentKind[index]);
			prpLpayObjectInfo.setUniformNo(prpLpayObjectInfoUniformNo[index]);
			prpLpayObjectInfo.setBeneficiaryPhone(prpLpayObjectInfoBeneficiaryPhone[index]);
			if (PrpLpayObjectInfo.OWNERSHIP_B.equals(prpLpayObjectInfo.getOwnerShip())) {
				prpLpayObjectInfo.setBankCode(prpLpayObjectInfoBankCode[index]);
				prpLpayObjectInfo.setBankName(prpLpayObjectInfoBankName[index]);
				prpLpayObjectInfo.setAccountCode(prpLpayObjectInfoAccountCode[index]);
				prpLpayObjectInfo.setCustomBankCode(prpLpayObjectInfoCustomBankCode[index]);
				prpLpayObjectInfo.setCustomBankName(prpLpayObjectInfoCustomBankName[index]);
			} else if (PrpLpayObjectInfo.OWNERSHIP_Q.equals(prpLpayObjectInfo.getOwnerShip())) {
				prpLpayObjectInfo.setCutBack(prpLpayObjectInfoCutBack[index]);
			} else if (PrpLpayObjectInfo.OWNERSHIP_C.equals(prpLpayObjectInfo.getOwnerShip())) {
				if (!"".equals(prpLpayObjectInfoPayDate[index]) && prpLpayObjectInfoPayDate[index] != null) {
					prpLpayObjectInfo.setPayDate(format.parse(prpLpayObjectInfoPayDate[index]));
				}
				prpLpayObjectInfo.setMobilePhoneNo(prpLpayObjectInfoMobilePhoneNo[index]);
			}
			prpLpayObjectInfo.setCourierAddress(prpLpayObjectInfoCourierAddress[index]);
			prpLpayObjectInfo.setAreaCode(prpLpayObjectInfoAreaCode[index]);
			prpLpayObjectInfo.setAccountCurrency(prpLpayObjectInfoAccountCurrency[index]);
			prpLpayObjectInfo.setCurrency(prpLpayObjectInfoCurrency[index]);// 支付币别
			prpLpayObjectInfo.setExchRate(Double.parseDouble(DataUtils.nullToZero(prpLpayObjectInfoExchRate[index])));
			prpLpayObjectInfo.setCertificateCode(prpLpayObjectInfoCertificateCode[index]);// 证件类型
			//mantis： CLM0113，處理人員：BL061 張明財，需求單編號：CLM0113   傷害險增加AML功能 start
			if (prpLpayObjectInfoAMLFlag!=null&&prpLpayObjectInfoAMLFlag.length>0) {
				prpLpayObjectInfo.setAmlFlag(prpLpayObjectInfoAMLFlag[index]) ;
				prpLpayObjectInfo.setAmlDate(new Date()) ;
			}	
			//mantis：CLM0113，處理人員：BL061 張明財，需求單編號：CLM0113   傷害險增加AML功能 end 
			prpLpayObjectInfoList.add(prpLpayObjectInfo);
		}
		compensateDto.setPrpLpayObjectInfoList(prpLpayObjectInfoList);
		/******************* 賠付對象信息 end ******************************/

		// 从界面得到输入数组
		String prpLcfeeCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLcfeePolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String prpLcfeeRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLcfeeCurrency1 = httpServletRequest.getParameter("prpLcompensateCurrency");
		String prpLcfeeSumThisPaid = httpServletRequest.getParameter("prpLcompensateSumThisPaid");
		// 对象赋值
		// 赔款计算金额信息
		List<PrpLcfee> prpLcfeeList = new ArrayList<PrpLcfee>();
		PrpLcfee prpLcfee = new PrpLcfee();
		prpLcfee.getId().setCompensateNo(prpLcfeeCompensateNo);
		prpLcfee.getId().setPolicyNo(prpLcfeePolicyNo);
		prpLcfee.setRiskCode(prpLcfeeRiskCode);
		prpLcfee.getId().setCurrency(prpLcfeeCurrency1);
		prpLcfee.setSumPaid(Double.parseDouble(DataUtils.nullToZero(prpLcfeeSumThisPaid)));
		prpLcfee.setFlag("");
		prpLcfeeList.add(prpLcfee);
		compensateDto.setPrpLcfeeList(prpLcfeeList);
		// 如果案件属於案终赔付，则需要结案报告文本
		String prpLcompensateFinallyFlag = httpServletRequest.getParameter("prpLcompensateFinallyFlag");
		List<PrpLltext> prpLltextList = new ArrayList<PrpLltext>();
		if ("1".equals(prpLcompensateFinallyFlag)) {
			String TextTemp = httpServletRequest.getParameter("prpLltextContextInnerHTML");
			String[] rules = StringUtils.split(TextTemp, RULE_LENGTH, "GBK");
			// 得到连接串,下面将其切分到数组
			PrpLltext prpLltext = null;
			String prpLcompensateClaimNo = httpServletRequest.getParameter("prpLcompensateClaimNo");
			for (int k = 0; k < rules.length; k++) {
				prpLltext = new PrpLltext();
				prpLltext.getId().setClaimNo(prpLcompensateClaimNo);
				prpLltext.setContext(rules[k]);
				prpLltext.getId().setLineNo(k + 1);
				prpLltext.getId().setTextType("08");
				prpLltextList.add(prpLltext);
			}
		}
		compensateDto.setPrpLltextList(prpLltextList);
		// 危险单位信息处理,目前只有一个危险单位
		// 将危险单位标的信息保存在集合中
		compensateDto.setPrpLprpLdangerItemList(prpLprpLdangerItemList);
		// 对标的信息进行处理，得到，合计信息

		String prpLcfeecoinsCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLcfeecoinsPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String prpLcfeecoinsRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String[] prpLcfeecoinsSerialNo = httpServletRequest.getParameterValues("prpLcoinsSerialNo");
		String[] prpLcfeecoinsChargeCode = httpServletRequest.getParameterValues("prpLcoinsChargeCode");
		String[] prpLcfeecoinsChargeName = httpServletRequest.getParameterValues("prpLcoinsChargeName");
		String[] prpLcfeecoinsCurrency = httpServletRequest.getParameterValues("prpLcoinsCurrency");
		String[] prpLcfeecoinsLossFeeType = httpServletRequest.getParameterValues("prpLcoinsLossFeeType");
		String[] prpLcfeecoinsCoinsCode = httpServletRequest.getParameterValues("prpLcoinsCoinsCode");
		String[] prpLcfeecoinsCoinsName = httpServletRequest.getParameterValues("prpLcoinsCoinsName");
		String[] prpLcfeecoinsCoinsType = httpServletRequest.getParameterValues("prpLcoinsCoinsType");
		String[] prpLcfeecoinsChiefFlag = httpServletRequest.getParameterValues("prpLcoinsChiefFlag");
		String[] prpLcfeecoinsCoinsRate = httpServletRequest.getParameterValues("prpLcoinsCoinsRate");
		String[] prpLcfeecoinsCoinsSumpaid = httpServletRequest.getParameterValues("prpLcoinsCoinsSumpaid");
		String[] prpLcfeecoinsSumpaid = httpServletRequest.getParameterValues("prpLcoinsSumpaid");
		String[] prpLcoinsFlag = httpServletRequest.getParameterValues("prpLcoinsFlag");

		List<PrpLcfeecoins> prpLcfeecoinsList = new ArrayList<PrpLcfeecoins>();
		if (prpLcfeecoinsSerialNo != null && prpLcfeecoinsSerialNo.length > 0) {
			PrpLcfeecoins prpLcfeecoins = null;
			for (int index = 1; index < prpLcfeecoinsSerialNo.length; index++) {
				prpLcfeecoins = new PrpLcfeecoins();
				prpLcfeecoins.getId().setBusinessNo(prpLcfeecoinsCompensateNo);
				prpLcfeecoins.setPolicyNo(prpLcfeecoinsPolicyNo);
				prpLcfeecoins.setRiskCode(prpLcfeecoinsRiskCode);
				prpLcfeecoins.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLcfeecoinsSerialNo[index])));
				prpLcfeecoins.setChargeCode(prpLcfeecoinsChargeCode[index]);
				prpLcfeecoins.setChargeName(prpLcfeecoinsChargeName[index]);
				prpLcfeecoins.setCurrency(prpLcfeecoinsCurrency[index]);
				prpLcfeecoins.setLossFeeType(prpLcfeecoinsLossFeeType[index]);
				prpLcfeecoins.setCoinsCode(prpLcfeecoinsCoinsCode[index]);
				prpLcfeecoins.setCoinsName(prpLcfeecoinsCoinsName[index]);
				prpLcfeecoins.setCoinsType(prpLcfeecoinsCoinsType[index]);
				prpLcfeecoins.setChiefFlag(prpLcfeecoinsChiefFlag[index]);
				prpLcfeecoins.setCoinsRate(Double.parseDouble(DataUtils.nullToZero(prpLcfeecoinsCoinsRate[index])));
				prpLcfeecoins.setCoinsSumPaid(Double.parseDouble(DataUtils.nullToZero(prpLcfeecoinsCoinsSumpaid[index])));
				prpLcfeecoins.setSumPaid(Double.parseDouble(DataUtils.nullToZero(prpLcfeecoinsSumpaid[index])));
				prpLcfeecoins.setFlag(prpLcoinsFlag[index]);
				prpLcfeecoinsList.add(prpLcfeecoins);
			}
		}
		compensateDto.setPrpLcfeecoinsList(prpLcfeecoinsList);// 联共保信息收集结束
		// 主子表金额是否一致校验
		double prpLCompensateSumRealPay = 0.00;
		double prpLLossSumRealPay = 0.00;
		double prpLPersonLossSumRealPay = 0.00;
		double prpLChargeSumRealPay = 0.00;
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		prpLCompensateSumRealPay = prpLcompensate.getSumThisPaid() + prpLcompensate.getSumPrePaid();
		if (!prpLlossList.isEmpty()) {
			for (PrpLloss prpLloss : compensateDto.getPrpLlossList()) {
				prpLLossSumRealPay += (prpLloss.getSumRealPay() + prpLloss.getExceptDeductiblePay())*prpLloss.getExchRate();
			}
		}
		if (!prpLpersonLossList.isEmpty()) {
			for (PrpLpersonLoss prpLpersonLossTemp : prpLpersonLossList) {
				prpLPersonLossSumRealPay += (prpLpersonLossTemp.getSumRealPay()+ prpLpersonLossTemp.getExceptDeductiblePay())*prpLpersonLossTemp.getExchRate();
			}
		}
		if (!prpLchargeList.isEmpty()) {
			for (PrpLcharge prpLcharge : prpLchargeList) {
				prpLChargeSumRealPay += (prpLcharge.getSumRealPay() + prpLcharge.getExceptDeductiblePay())*prpLcharge.getExchRate();
			}
		}

//		PrpCmain prpCmain = this.getPrpCmainService().findByPrimaryKey(prpLcfeecoinsPolicyNo);
//		if ("2".equals(prpCmain.getCoinsFlag()) || "3".equals(prpCmain.getCoinsFlag())) {// 从（联、共）保取总费用
//			double coinsRate = 0;
//			String condtions = "policyno='" + prpLcfeecoinsPolicyNo + "' and coinstype='2'";
//			List<PrpCcoins> prpCcoinsList = this.getPrpCcoinsService().findPrpCcoins(QueryRule.getInstance().addSql(condtions));
//			if (prpCcoinsList != null && !prpCcoinsList.isEmpty()) {
//				for (Iterator<PrpCcoins> iterator = prpCcoinsList.iterator(); iterator.hasNext();) {
//					coinsRate = iterator.next().getCoinsRate();
//				}
//			}
//			BigDecimal bigSumAmount = new BigDecimal(new DecimalFormat(".00").format(prpLChargeSumRealPay));
//			BigDecimal bigCoinsRate = new BigDecimal(new DecimalFormat(".00").format(coinsRate / 100));
//			prpLChargeSumRealPay = bigSumAmount.divide(bigCoinsRate, BigDecimal.ROUND_HALF_UP).doubleValue();
//		}
		double sumTemp = prpLLossSumRealPay + prpLPersonLossSumRealPay + prpLChargeSumRealPay;
		if (Math.abs(prpLCompensateSumRealPay / sumTemp - 1) > 0.0001) {
			throw new UserException(-1009,-1,"本次賠付總金額與各分項損失匯總金額不相等，請檢查各項金額及費用,重新輸入！<br>" + "標的損失總金額：" + prpLLossSumRealPay + "<br> " + "人傷損失總金額：" + prpLPersonLossSumRealPay + "<br>" + "計入賠款費用總金額：" + prpLChargeSumRealPay + "<br>"
					+ "各分項損失匯總金額= 標的損失總金額+人傷損失總金額+計入賠款費用總金額= " + sumTemp + "<br>" + "本次賠付總金額：" + prpLCompensateSumRealPay + "< br>" + "本次賠付總金額與各分項損失匯總金額相差" + (prpLCompensateSumRealPay - sumTemp));
		}
		return compensateDto;

	}

	/**
	 * 获得理赔结论下拉列表选项
	 * @return
	 */
	private Collection<?> getCompensateResultList() {
		return ICollections.getCompensateResultList();
	}

	public void coinsCreate(HttpServletRequest httpServletRequest, CompensateDto compensateDto, String sumDutyPaid) throws UserException, Exception {
		this.createCoinsDetail(httpServletRequest, compensateDto, sumDutyPaid);
	}

	/**
	 * coinsFlag: 0-独家承保，1-主辦业务，2-非主辦，3-分保业务
	 * 主辦，非主辦  chiefFlag 2--主辦，1-非主辦方
	 * 分保 业务  coinsType  1-主承保人，2-共保人
	 * coinsRate 共保比例
	 * @param httpServletRequest
	 * @param compensateDto
	 * @param sumDutyPaid
	 * @throws UserException
	 * @throws Exception
	 */
	public void createCoinsDetail(HttpServletRequest httpServletRequest, CompensateDto compensateDto, String sumDutyPaid) throws UserException, Exception {
		double dblsumDutyPaid = Double.parseDouble(sumDutyPaid);// 赔款合计取得
		String mergeCurrency = httpServletRequest.getParameter("MergeCurrency");
		if (mergeCurrency == null) {
			mergeCurrency = ConstantCodes.LOCAL_CURRENCY;
		}
//		PrpLcharge prpLcharge = new PrpLcharge();
//		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
//		prpLcharge.setPrpLchargeList(prpLchargeList);
//		httpServletRequest.setAttribute("prpLcharge", prpLcharge);
		String policyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String conditions = " policyno = '" + policyNo + "'";
		List<PrpCcoins> list = this.getPrpCcoinsService().findPrpCcoins(QueryRule.getInstance().addSql(conditions));
		int listSize = list.size();// 取得联共保人的个数，便於最後一个做减法
		int count = 0;
		int losscount = 0;// 为赔款减法计数
		double coinsSumPaid = 0.00;
		PrpLcfeecoins prpLcfeecoins = null;
		List<PrpLcfeecoins> prpLcfeecoinsList = new ArrayList<PrpLcfeecoins>();
		PrpCcoins prpCcoins = null;
		if (list != null && !list.isEmpty()) {
			for (Iterator<PrpCcoins> it = list.iterator(); it.hasNext();) {
				losscount++;
				prpCcoins = it.next();
				double singleLoss = Str.round(dblsumDutyPaid * (prpCcoins.getCoinsRate() / 100), 2);// 分摊赔款取得
				if (losscount != listSize) {
					coinsSumPaid += singleLoss;
				}
				prpLcfeecoins = new PrpLcfeecoins();
				prpLcfeecoins.getId().setSerialNo(++count);
				prpLcfeecoins.setCurrency(mergeCurrency);
				prpLcfeecoins.setChargeCode("");
				prpLcfeecoins.setChargeName("");
				prpLcfeecoins.setChiefFlag(prpCcoins.getChiefFlag());
				prpLcfeecoins.setCoinsCode(prpCcoins.getCoinsCode());
				prpLcfeecoins.setCoinsName(prpCcoins.getCoinsName());
				prpLcfeecoins.setCoinsRate(prpCcoins.getCoinsRate());
				if (losscount != listSize) {
					prpLcfeecoins.setCoinsSumPaid(singleLoss);
				} else {
					prpLcfeecoins.setCoinsSumPaid(Str.round(dblsumDutyPaid - coinsSumPaid, 2));
				}
				prpLcfeecoins.setSumPaid(dblsumDutyPaid);
				prpLcfeecoins.setCoinsType(prpCcoins.getCoinsType());
				prpLcfeecoins.setLossFeeType("0");
				prpLcfeecoinsList.add(prpLcfeecoins);
			}
		}
//		double consRate = 1;
//		conditions = " policyno = '" + policyNo + "' and coinstype='2'";
//		List<PrpCcoins> listCcoins = this.getPrpCcoinsService().findPrpCcoins(QueryRule.getInstance().addSql(conditions));
//		if (listCcoins != null && !listCcoins.isEmpty()) {
//			for (Iterator<PrpCcoins> it = listCcoins.iterator(); it.hasNext(); consRate = prpCcoins.getCoinsRate()) {
//				prpCcoins = it.next();
//			}
//		}
//		for (PrpLcharge temp : prpLchargeList) {
//			losscount = 0;// 为赔款减法计数
//			coinsSumPaid = 0.00;
//			for (Iterator<PrpCcoins> it = list.iterator(); it.hasNext();) {
//				losscount++;
//				prpCcoins = it.next();
//				double singleCharge = Str.round(temp.getChargeAmount() / (consRate / 100) * (prpCcoins.getCoinsRate() / 100), 2);
//				// if ("03".equals(temp.getChargeCode())) {
//				// singleCharge = Str.round(temp.getSumRealPay() / (consRate /
//				// 100) * (prpCcoins.getCoinsRate() / 100), 2);
//				// }
//				if (losscount != listSize) {
//					coinsSumPaid += singleCharge;
//				}
//				prpLcfeecoins = new PrpLcfeecoins();
//				prpLcfeecoins.getId().setSerialNo(++count);
//				prpLcfeecoins.setCurrency(temp.getCurrency());
//				prpLcfeecoins.setChargeCode(temp.getChargeCode());
//				prpLcfeecoins.setChargeName(temp.getChargeName());
//				prpLcfeecoins.setChiefFlag(prpCcoins.getChiefFlag());
//				prpLcfeecoins.setCoinsCode(prpCcoins.getCoinsCode());
//				prpLcfeecoins.setCoinsName(prpCcoins.getCoinsName());
//				prpLcfeecoins.setCoinsRate(prpCcoins.getCoinsRate());
//				if (losscount != listSize) {
//					prpLcfeecoins.setCoinsSumPaid(singleCharge);
//				} else {
//					double tempcharge = Str.round(temp.getChargeAmount() / (consRate / 100) - coinsSumPaid, 2);
//					// if ("03".equals(temp.getChargeCode())) {
//					// tempcharge = Str.round(temp.getSumRealPay() / (consRate /
//					// 100) - coinsSumPaid, 2);
//					// }
//					prpLcfeecoins.setCoinsSumPaid(tempcharge);
//				}
//				prpLcfeecoins.setCoinsType(prpCcoins.getCoinsType());
//				prpLcfeecoins.setLossFeeType("1");
//				prpLcfeecoinsList.add(prpLcfeecoins);
//			}
//		}
		PrpLcfeecoins prpLcfeecoinsAll = new PrpLcfeecoins();
		prpLcfeecoinsAll.setPrpLcfeecoinsList(prpLcfeecoinsList);
		httpServletRequest.setAttribute("prpLcfeecoinsAll", prpLcfeecoinsAll);
	}


	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
	public void settingPAF4567(HttpServletRequest httpServletRequest,PrpLcompensate prpLcompensate) throws Exception{
		//理算任務
		Double PAF5_AMOUNT = 0.0;
		Double PAF6_AMOUNT = 0.0;
		Double PAF7_AMOUNT = 0.0;
		Double PAF5_SUMLOSS= 0.0;
		Double PAF6_SUMLOSS= 0.0;
		Double PAF456_SUMLOSS=0.0;
		if(prpLcompensate.getRiskCode().equals("PA")){
			System.out.println("CLM0231 理算--------START");
			List<PrpCitemKind> prpCitemKindListPaf5 = this.endorseViewHelper.findPrpCitemKind(prpLcompensate.getPolicyNo(), "PAF5");
			for(PrpCitemKind paf5:prpCitemKindListPaf5){
				PAF5_AMOUNT = paf5.getAmount();//PAF7 來源為保單
			}
			List<PrpCitemKind> prpCitemKindListPaf6 = this.endorseViewHelper.findPrpCitemKind(prpLcompensate.getPolicyNo(), "PAF6");
			for(PrpCitemKind paf6:prpCitemKindListPaf6){
				PAF6_AMOUNT = paf6.getAmount();//PAF7 來源為保單
			}
			List<PrpCitemKind> prpCitemKindListPaf7 = this.endorseViewHelper.findPrpCitemKind(prpLcompensate.getPolicyNo(), "PAF7");
			for(PrpCitemKind paf7:prpCitemKindListPaf7){
				PAF7_AMOUNT = paf7.getAmount();//PAF7 來源為保單
			}
//			String conditions_forCompensateHis = "compensateNo like 'C"+prpLcompensate.getClaimNo()+"%' AND (underWriteFlag =1 OR underWriteFlag =3) order by compensateNo ";
			String conditions_forCompensateHis = "POLICYNO = '"+prpLcompensate.getPolicyNo()+"' AND (underWriteFlag =1 OR underWriteFlag =3) order by compensateNo ";
			List<PrpLcompensate> PrpLcompensateHisList = this.compensateService.findByConditions(conditions_forCompensateHis);
			for(PrpLcompensate prpLcompensateHit:PrpLcompensateHisList){
				System.out.println(prpLcompensateHit.getCompensateNo());

				QueryRule queryRulePerson = QueryRule.getInstance();
				queryRulePerson.addEqual("id.compensateNo", prpLcompensateHit.getCompensateNo());
				queryRulePerson.addEqual("policyNo", prpLcompensateHit.getPolicyNo());
				queryRulePerson.addAscOrder("personNo");
				queryRulePerson.addAscOrder("id.serialNo");
				List<PrpLpersonLoss> prplPersonLossList = this.prpLpersonLossService.findPrpLpersonLoss(queryRulePerson);
				for(PrpLpersonLoss plpl:prplPersonLossList){
					if(plpl.getKindCode().equals("PAF4") || plpl.getKindCode().equals("PAF5") || plpl.getKindCode().equals("PAF6")){
						PAF456_SUMLOSS+=plpl.getSumLoss();
						System.out.println(plpl.getKindCode()+"_SUMLOSS:"+plpl.getSumLoss()+"/sum:"+PAF456_SUMLOSS);
					}
				}
			}

			
			String conditions_forPersonLossHis = "POLICYNO = '"+prpLcompensate.getPolicyNo()+"' AND COMPENSATENO like 'C"+prpLcompensate.getClaimNo()+"%' ";
			List<PrpLpersonLoss> PrpLpersonLossHisList = this.prpLpersonLossService.findByConditions(conditions_forPersonLossHis);
			for(PrpLpersonLoss prpLpersonLoss:PrpLpersonLossHisList){
				if(prpLpersonLoss.getKindCode().equals("PAF5")){
					PAF5_SUMLOSS+=prpLpersonLoss.getSumLoss();
				}
				if(prpLpersonLoss.getKindCode().equals("PAF6")){
					PAF6_SUMLOSS+=prpLpersonLoss.getSumLoss();
				}
			}
			
			System.out.println(prpLcompensate.getPolicyNo()+"_PAF7_AMOUNT:"+PAF7_AMOUNT);
			httpServletRequest.setAttribute("PAF5_SUMLOSS", PAF5_SUMLOSS);
			httpServletRequest.setAttribute("PAF6_SUMLOSS", PAF6_SUMLOSS);
			httpServletRequest.setAttribute("PAF456_SUMLOSS", PAF456_SUMLOSS);
			httpServletRequest.setAttribute("PAF5_AMOUNT", PAF5_AMOUNT);
			httpServletRequest.setAttribute("PAF6_AMOUNT", PAF6_AMOUNT);
			httpServletRequest.setAttribute("PAF7_AMOUNT", PAF7_AMOUNT);
			System.out.println("CLM0231 理算--------END");
		}
	}
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
	
	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLcfeecoinsService getPrpLcfeecoinsService() {
		return prpLcfeecoinsService;
	}

	public void setPrpLcfeecoinsService(PrpLcfeecoinsService prpLcfeecoinsService) {
		this.prpLcfeecoinsService = prpLcfeecoinsService;
	}

	public AcciCheckService getAcciCheckService() {
		return acciCheckService;
	}

	public void setAcciCheckService(AcciCheckService acciCheckService) {
		this.acciCheckService = acciCheckService;
	}

	public PrpLacciCheckService getPrpLacciCheckService() {
		return prpLacciCheckService;
	}

	public void setPrpLacciCheckService(PrpLacciCheckService prpLacciCheckService) {
		this.prpLacciCheckService = prpLacciCheckService;
	}

	public DAAClaimViewHelper getDaaClaimViewHelper() {
		return daaClaimViewHelper;
	}

	public void setDaaClaimViewHelper(DAAClaimViewHelper daaClaimViewHelper) {
		this.daaClaimViewHelper = daaClaimViewHelper;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public RecaseService getRecaseService() {
		return recaseService;
	}

	public void setRecaseService(RecaseService recaseService) {
		this.recaseService = recaseService;
	}

	public List<PrpLctext> getPrpLctextlist() {
		return prpLctextlist;
	}

	public void setPrpLctextlist(List<PrpLctext> prpLctextlist) {
		this.prpLctextlist = prpLctextlist;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public SwfNotionService getSwfNotionService() {
		return swfNotionService;
	}

	public void setSwfNotionService(SwfNotionService swfNotionService) {
		this.swfNotionService = swfNotionService;
	}

	public ReinsServiceManager getReinsServiceManager() {
		return reinsServiceManager;
	}

	public void setReinsServiceManager(ReinsServiceManager reinsServiceManager) {
		this.reinsServiceManager = reinsServiceManager;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	public PrpPitemKindService getPrpPitemKindService() {
		return prpPitemKindService;
	}

	public void setPrpPitemKindService(PrpPitemKindService prpPitemKindService) {
		this.prpPitemKindService = prpPitemKindService;
	}

	public PrpLfractureService getPrpLfractureService() {
		return prpLfractureService;
	}

	public void setPrpLfractureService(PrpLfractureService prpLfractureService) {
		this.prpLfractureService = prpLfractureService;
	}

	public PrpLpersonHospitalService getPrpLpersonHospitalService() {
		return prpLpersonHospitalService;
	}

	public void setPrpLpersonHospitalService(PrpLpersonHospitalService prpLpersonHospitalService) {
		this.prpLpersonHospitalService = prpLpersonHospitalService;
	}

	public PrpCinsuredNatureService getPrpCinsuredNatureService() {
		return prpCinsuredNatureService;
	}

	public void setPrpCinsuredNatureService(PrpCinsuredNatureService prpCinsuredNatureService) {
		this.prpCinsuredNatureService = prpCinsuredNatureService;
	}

	public PrpLltextModelService getPrpLltextModelService() {
		return prpLltextModelService;
	}

	public void setPrpLltextModelService(PrpLltextModelService prpLltextModelService) {
		this.prpLltextModelService = prpLltextModelService;
	}

	public PrpLclauseService getPrpLclauseService() {
		return prpLclauseService;
	}

	public void setPrpLclauseService(PrpLclauseService prpLclauseService) {
		this.prpLclauseService = prpLclauseService;
	}

	public PrpDriskRateService getPrpDriskRateService() {
		return prpDriskRateService;
	}

	public void setPrpDriskRateService(PrpDriskRateService prpDriskRateService) {
		this.prpDriskRateService = prpDriskRateService;
	}

	public CompensateGenerateLossViewHelper getCompensateGenerateLossViewHelper() {
		return compensateGenerateLossViewHelper;
	}

	public void setCompensateGenerateLossViewHelper(CompensateGenerateLossViewHelper compensateGenerateLossViewHelper) {
		this.compensateGenerateLossViewHelper = compensateGenerateLossViewHelper;
	}

	public PrpCmainCarGoSubService getPrpCmainCarGoSubService() {
		return prpCmainCarGoSubService;
	}

	public void setPrpCmainCarGoSubService(PrpCmainCarGoSubService prpCmainCarGoSubService) {
		this.prpCmainCarGoSubService = prpCmainCarGoSubService;
	}

	public PrpCitemShipService getPrpCitemShipService() {
		return prpCitemShipService;
	}

	public void setPrpCitemShipService(PrpCitemShipService prpCitemShipService) {
		this.prpCitemShipService = prpCitemShipService;
	}

	public PrpCmainCargoService getPrpCmainCargoService() {
		return prpCmainCargoService;
	}

	public void setPrpCmainCargoService(PrpCmainCargoService prpCmainCargoService) {
		this.prpCmainCargoService = prpCmainCargoService;
	}

	public PrpCCargoItemService getPrpCCargoItemService() {
		return prpCCargoItemService;
	}

	public void setPrpCCargoItemService(PrpCCargoItemService prpCCargoItemService) {
		this.prpCCargoItemService = prpCCargoItemService;
	}

	public PrpCaddressService getPrpCaddressService() {
		return prpCaddressService;
	}

	public void setPrpCaddressService(PrpCaddressService prpCaddressService) {
		this.prpCaddressService = prpCaddressService;
	}

	public PrpCplaneService getPrpCplaneService() {
		return prpCplaneService;
	}

	public void setPrpCplaneService(PrpCplaneService prpCplaneService) {
		this.prpCplaneService = prpCplaneService;
	}
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
	public PrpLpersonLossService getPrpLpersonLossService() {
		return prpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		this.prpLpersonLossService = prpLpersonLossService;
	}
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
}
