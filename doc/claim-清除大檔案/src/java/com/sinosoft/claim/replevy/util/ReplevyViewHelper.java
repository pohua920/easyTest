/*
 * @(#)ReplevyViewHelper.java	Mar 11, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.replevy.util;

import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PowerService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.common.vo.EndorseDto;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.endcase.service.facade.EndcaseService;
import com.sinosoft.claim.endcase.util.EndcaseViewHelper;
import com.sinosoft.claim.endcase.vo.EndcaseDto;
import com.sinosoft.claim.replevy.service.facade.ReplevyService;
import com.sinosoft.claim.replevy.vo.ReplevyUndwrtDto;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLcaseNo;
import com.sinosoft.claim.schema.model.PrpLcfeecoins;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.model.Prplreplevyhistory;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLcfeecoinsService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLctextService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLreplevyService;
import com.sinosoft.claim.schema.service.facade.PrplreplevyhistoryService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.UtiUserGradeService;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.common.util.MoneyUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.log.Logger;

/**
 * mantis：CLM0028 ，處理人員：BK007 蘇哲，需求單編號：CLM0028 追償處理賠付對象管控、追償計算書增加追償說明
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class ReplevyViewHelper extends EndcaseViewHelper {
	/** Log日志对象 */
	private static Logger log = Logger.getLogger(ReplevyViewHelper.class.getName());
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 赔款计算书信息服务 */
	private PrpLcompensateService prpLcompensateService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 追偿信息历史记录信息服务 */
	private PrplreplevyhistoryService prpLreplevyhistoryService;
	/** 权益转让及追偿登记信息服务 */
	private PrpLreplevyService prpLreplevyService;
	/** 结案服务 */
	private EndcaseService endcaseService;
	/** 联共保赔付金额分摊信息服务 */
	private PrpLcfeecoinsService prpLcfeecoinsService;
	/** 理算实赔服务 */
	private CompensateService compensateService;
	/** 赔款计算文字信息服务 */
	private PrpLctextService prpLctextService;
	/** 追偿服务 */
	private ReplevyService replevyService;
	/** 用户基本信息服务 */
	private PrpDuserService prpDuserService;
	/** 支付对象信息服务 */
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/** 代码服务 */
	private CodeService codeService;
	/** 保單相關信息服務 */
	private PolicyService policyService;
	/** 批改相關信息服務 */
	private EndorseService endorseService;

	private PrpCitemCarService prpCitemCarService;
	
	private UtiUserGradeService utiUserGradeService;
	
	private PrpLcheckService prpLcheckService;
	
	private SwfLogService swfLogService;
	
	private WorkFlowService workFlowService;
	
	private PrpCinsuredService prpCinsuredService;
	
	private PrpCmainService prpCmainService;
	private PrpCitemKindService prpCitemKindService;
	private EndorseViewHelper endorseViewHelper;
	private PowerService powerService;
	/**
	 * 默认构造方法
	 */
	public ReplevyViewHelper() {
	}

	private int RULE_LENGTH = 70; // rule字段的长度
	
	/***
	 * 追償查詢登錄
	 * @param httpServletRequest
	 * @param pageNo
	 * @param recordPerPage
	 * @throws Exception
	 */
	public void replevyQueryForAddQuery(HttpServletRequest httpServletRequest, int pageNo, int pageSize) throws Exception {
		String claimNo = StringUtils.rightTrim(httpServletRequest.getParameter("ClaimNo")); // 赔案号
		String policyNo = StringUtils.rightTrim(httpServletRequest.getParameter("PolicyNo")); // 保单号
		String licenseNo = StringUtils.rightTrim(httpServletRequest.getParameter("LicenseNo"));// 车牌号
		String comCode = StringUtils.rightTrim(httpServletRequest.getParameter("comCode"));// 承保机构
		String insuredName = StringUtils.rightTrim(httpServletRequest.getParameter("InsuredName"));// 被保险人名称
		String registNo = StringUtils.rightTrim(httpServletRequest.getParameter("RegistNo"));// 报案号
		String replevyFlag = httpServletRequest.getParameter("replevyFlag");//追償標記
		// 得到页面选择查询情况 * or = 再组合SQL
		String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String licenseNoSign = httpServletRequest.getParameter("LicenseNoSign");
		String comCodeSign = httpServletRequest.getParameter("comCodeSign");
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		StringBuffer sql = new StringBuffer("");
		if(DataUtils.emptyToNull(claimNo)!=null){
			sql.append(StringConvert.convertString("c.claimNo", claimNo, claimNoSign));
		}
		if(DataUtils.emptyToNull(registNo)!=null){
			sql.append(StringConvert.convertString("c.registNo", registNo, registNoSign));
		}
		if(DataUtils.emptyToNull(policyNo)!=null){
			sql.append(StringConvert.convertString("c.policyNo", policyNo, policyNoSign));
		}
		if(DataUtils.emptyToNull(licenseNo)!=null){
			sql.append(StringConvert.convertString("r.licenseNo", licenseNo, licenseNoSign));
		}
		if(DataUtils.emptyToNull(comCode)!=null){
			sql.append(StringConvert.convertString("r.comCode", comCode, comCodeSign));
		}
		if(DataUtils.emptyToNull(comCode)!=null){
			sql.append(StringConvert.convertString("r.insuredName", insuredName, insuredNameSign));
		}
		sql.append("and c.replevyFlag = '"+replevyFlag+"' ");
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		sql.append(powerService.addRiskPower(userDto, "r","claim") + uiPowerInterface.addCustomerPower(userDto, "r", "", "ComCode"));
		Page page = this.getPrpLclaimService().findReplevyCase(sql.toString(), pageNo, pageSize);
		List<?> list = page.getResult();
		if(list!=null && !list.isEmpty()){
			Iterator<?> it = list.iterator();
			String conditions = null;
			List<PrpLcompensate> tempList = null;
			PrpLcompensate p = null;
			while(it.hasNext()){
				PrpLclaim temp = (PrpLclaim)it.next();
				temp.setHasReplevy("0");
				if("1".equals(temp.getReplevyFlag())){
					conditions = " compensateNo like 'R"+temp.getClaimNo()+"%' order by compensateNo ";
					tempList = this.prpLcompensateService.findByConditions(conditions);
					if(tempList!=null && !tempList.isEmpty()){//已做追償登錄
						p = tempList.get(0);
						if(p.getCompensateNo().endsWith("00")){
							if(tempList.size()==1){
								temp.setHasReplevy("0".equals(p.getUnderWriteFlag())?"1":"3");
							}else{
								temp.setHasReplevy("2");
							}
						}
					}
				}
			}
		}
		httpServletRequest.setAttribute("page", page);
	}

	/**
	 * @param 追偿处理搜索结果
	 * @param pageNo
	 * @param recordPerPage
	 * @throws Exception
	 */
	public void replevyQueryDtoToView(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
		// 得到页面参数
		httpServletRequest.setCharacterEncoding(ConstantCodes.YUI_CHARSET);
		String claimNo = StringUtils.rightTrim(httpServletRequest.getParameter("ClaimNo")); // 赔案号
		String caseNo = StringUtils.rightTrim(httpServletRequest.getParameter("CaseNo")); // 归档号
		String policyNo = StringUtils.rightTrim(httpServletRequest.getParameter("PolicyNo")); // 保单号
		String operateDate = StringUtils.rightTrim(httpServletRequest.getParameter("OperateDate"));// 操作时间(结案时间)
		String licenseNo = StringUtils.rightTrim(httpServletRequest.getParameter("LicenseNo"));// 车牌号
		String comCode = StringUtils.rightTrim(httpServletRequest.getParameter("comCode"));// 承保机构
		String claimDate = StringUtils.rightTrim(httpServletRequest.getParameter("claimDate"));// 立案时间
		String insuredName = StringUtils.rightTrim(httpServletRequest.getParameter("InsuredName"));// 被保险人名称
		String replevyLimitDate = StringUtils.rightTrim(httpServletRequest.getParameter("ReplevyLimitDate"));// 追偿时效
		String registNo = StringUtils.rightTrim(httpServletRequest.getParameter("RegistNo"));// 报案号

		// 得到页面选择查询情况 * or = 再组合SQL
		String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
		String caseNoSign = httpServletRequest.getParameter("CaseNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String operateDateSign = httpServletRequest.getParameter("OperateDateSign");
		String licenseNoSign = httpServletRequest.getParameter("LicenseNoSign");
		String comCodeSign = httpServletRequest.getParameter("comCodeSign");
		String claimDateSign = httpServletRequest.getParameter("claimDateSign");
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		String replevyLimitDateSign = httpServletRequest.getParameter("ReplevyLimitDateSign");
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		conditions.append(StringConvert.convertString("caseNo", caseNo, caseNoSign));
		conditions.append(StringConvert.convertString("policyno", policyNo, policyNoSign));
		conditions.append(StringConvert.convertString("comCode", comCode, comCodeSign));
		conditions.append(StringConvert.convertString("insuredName", insuredName, insuredNameSign));
		if (claimDate != null && !claimDate.trim().equals("")) {
			conditions.append(StringConvert.convertDate("claimDate", claimDate, claimDateSign));
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions.append(StringConvert.convertDate("endcaseDate", operateDate, operateDateSign));
		}
		if (replevyLimitDate != null && !replevyLimitDate.equals("")) {
			conditions.append(StringConvert.convertDate("replevyLimitDate", replevyLimitDate, replevyLimitDateSign));
		}
		StringBuffer strLiceseNo = new StringBuffer("");
		if (registNo != null && !registNo.trim().equals("")) {
			conditions.append(StringConvert.convertString("registNo", registNo, registNoSign));
			strLiceseNo.append(StringConvert.convertString("registNo", registNo, registNoSign));
		}
		//立案表没有车牌号信息，查询报案表
		if(licenseNo!=null&&!"".equals(licenseNo)){
			strLiceseNo.insert(0, " and registNo in (select registNo from prpLregist where 1=1 ");
			strLiceseNo.append(StringConvert.convertString("licenseNo", licenseNo, licenseNoSign));
			strLiceseNo.append(") ");
			conditions.append(strLiceseNo);
		}
		conditions.append(" AND CANCELDATE IS NULL AND CLAIMDATE IS NOT NULL AND ENDCASEDATE IS NOT NULL AND CASENO IS NOT NULL ");

		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions.append(powerService.addRiskPower(userDto, "prpLclaim","claim") + uiPowerInterface.addCustomerPower(userDto, "prpLclaim", "", "ComCode"));
		conditions.append(" ORDER BY CLAIMDATE DESC ");
		// 得到多行结案主表信息
		log.info("start to search,please waiting ...");
		Page page = prpLclaimService.findByConditions(conditions.toString(), pageNo, recordPerPage);
		log.info("end search,please waiting for result...");
		httpServletRequest.setAttribute("page", page);
		PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
		prpLcaseNo.setCaseList(page.getResult());
		prpLcaseNo.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcaseNoDto", prpLcaseNo);
	}

	/**
	 * @param 追償處理、追償登錄修改查詢
	 * @param pageNo
	 * @param recordPerPage
	 * @throws Exception
	 */
	public void replevyQueryForAdd(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
		// 得到页面参数
		String claimNo = StringUtils.rightTrim(httpServletRequest.getParameter("ClaimNo")); // 赔案号
		String compensateNo = StringUtils.rightTrim(httpServletRequest.getParameter("CompensateNo")); // 计算书号
		String policyNo = StringUtils.rightTrim(httpServletRequest.getParameter("PolicyNo")); // 保单号
		String registNo = StringUtils.rightTrim(httpServletRequest.getParameter("RegistNo"));// 报案号
		// 得到页面选择查询情况 * or = 再组合SQL
		String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
		String compensateNoSign = httpServletRequest.getParameter("CompensateNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		if (DataUtils.emptyToNull(claimNo)!=null) {
			conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		}
		if (DataUtils.emptyToNull(compensateNo)!=null) {
			conditions.append(StringConvert.convertString("compensateNo", compensateNo, compensateNoSign));
		}
		if (DataUtils.emptyToNull(policyNo)!=null) {
			conditions.append(StringConvert.convertString("policyno", policyNo, policyNoSign));
		}
		if (DataUtils.emptyToNull(registNo)!=null) {
			conditions.append(" AND exists (select 0 from prplclaim where prplcompensate.claimno=prplclaim.claimno ");
			conditions.append(StringConvert.convertString("prplclaim.registNo", registNo, registNoSign));
			conditions.append(" ) ");
		}
		conditions.append(" and compensateNo like 'R%00' AND CASETYPE = 'R' AND UNDERWRITEFLAG = '0' ");
		String editType = httpServletRequest.getParameter("editType");
		if("editQuery".equals(editType)){//追償登錄修改
			conditions.append(" and not exists (select 0 from swflogstore where swflogstore.businessno like CONCAT(CONCAT('R',prplcompensate.claimno),'%') and swflogstore.businessno<>prplcompensate.compensateno ) ");
		}
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions.append(powerService.addRiskPower(userDto, "prplcompensate","claim") + uiPowerInterface.addCustomerPower(userDto, "prplcompensate", "", "ComCode"));
		conditions.append(" ORDER BY INPUTDATE DESC ");
		Page page = prpLcompensateService.findByConditions(conditions.toString(), pageNo, recordPerPage);
		List<?> list = page.getResult();
		if(list!=null && !list.isEmpty()){
			PrpLcompensate prpLcompensate = null;
			PrpDuser prpDuser = null;
			Iterator<?> it = list.iterator();
			while(it.hasNext()){
				prpLcompensate = (PrpLcompensate) it.next();
				prpDuser = prpDuserService.findPrpDuser(prpLcompensate.getOperatorCode());
				prpLcompensate.setOperatorName(prpDuser!=null?prpDuser.getUserName():"");
			}
		}
		httpServletRequest.setAttribute("prpLcompensateList", list);
		httpServletRequest.setAttribute("page", page);
	}
	
	/***
	 * 追偿计算书，填充
	 * @param httpServletRequest
	 * @param compensateNo
	 * @param prpLclaim
	 * @param editType
	 * @throws Exception 
	 */
	private PrpLcompensate viewToPrpLcompensate(HttpServletRequest httpServletRequest,String editType,String compensateNo,PrpLclaim prpLclaim) throws Exception{
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		PrpLcompensate prpLcompensate = new PrpLcompensate();;
		if("addQuery".equals(editType) || "ADD".equals(editType)){//追偿登录、追偿处理
			prpLcompensate.setCompensateNo(compensateNo);
			prpLcompensate.setLflag("0");
			prpLcompensate.setInputDate(new Date());
			prpLcompensate.setUnderWriteFlag("0");//默認初始狀態為
			prpLcompensate.setCaseType("R");
			prpLcompensate.setFinallyFlag("0");
			if("ADD".equals(editType)){
				prpLcompensate.setUnderWriteFlag("9");//默認初始狀態為
			}
			//从立案填充计算书的部分讯息
			prpLcompensate.setClaimNo(prpLclaim.getClaimNo());
			prpLcompensate.setPolicyNo(prpLclaim.getPolicyNo());
			prpLcompensate.setCaseNo(prpLclaim.getCaseNo());//结案号码
			prpLcompensate.setClassCode(prpLclaim.getClassCode());
			prpLcompensate.setRiskCode(prpLclaim.getRiskCode());
			prpLcompensate.setCurrency(prpLclaim.getCurrency());
			prpLcompensate.setSumLoss(prpLclaim.getSumPaid());//追偿存总赔偿金额
			prpLcompensate.setMakeCom(prpLclaim.getMakeCom());
			prpLcompensate.setComCode(prpLclaim.getComCode());
			prpLcompensate.setHandler1Code(prpLclaim.getHandler1Code());
			UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
			prpLcompensate.setHandlerCode(userDto.getUserCode());
			prpLcompensate.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
			PrpCmain prpCmain = this.getPolicyService().findPrpCmainDtoByPrimaryKey(prpLcompensate.getPolicyNo());
			if (prpCmain != null) {
				prpLcompensate.setCheckAgentCode(prpCmain.getAppliCode());
				prpLcompensate.setCheckAgentName(prpCmain.getAppliName());
			}
			prpLcompensate.setSurveyorName(prpLclaim.getInsuredName());
		}else if("editQuery".equals(editType) || "EDIT".equals(editType)){//登录讯息修改、追偿驳回修改
			PropertyUtils.copyProperties(prpLcompensate,this.prpLcompensateService.findPrpLcompensate(compensateNo));
			prpLcompensate.setUnderWriteFlag("9");//默認初始狀態為
		}
		String replevyTypeCode = httpServletRequest.getParameter("ReplevyTypeCode");// 追偿类型
		String prpLreplevyRepleviedName = httpServletRequest.getParameter("prpLreplevyRepleviedName");// 被追偿人名称
		String prpLreplevyReplevyReason = httpServletRequest.getParameter("prpLreplevyReplevyReason");// 追偿原因
		String prpLreplevyReclaimDate = httpServletRequest.getParameter("prpLreplevyReclaimDate");// 追偿起期
		String prpLreplevyValidDate = httpServletRequest.getParameter("prpLreplevyValidDate");// 本次追回日期
		String replevyLimitDate = httpServletRequest.getParameter("ReplevyLimitDate");// 追偿时效
		String isPayForOtherFlag = httpServletRequest.getParameter("isPayForOtherFlag");// 补充追偿原因
		String sumThisPaid = httpServletRequest.getParameter("SumThisPaid");// 本次追偿收入
		String sumThisCharge = httpServletRequest.getParameter("SumThisCharge");// 本次追偿费用
		String prpLreplevyNote = httpServletRequest.getParameter("prpLreplevyNote");// 备注
		String paySituation = httpServletRequest.getParameter("paySituation");//強制險給付追償情況
		String oppositeClaimNo = httpServletRequest.getParameter("prpLcompensateOppositeClaimNo");//對方賠案號碼
		String oppositeClaimOfficer = httpServletRequest.getParameter("prpLcompensateOppositeClaimOfficer");//對方理賠員
		String payCodeType = httpServletRequest.getParameter("prpLcompensatePayCodeType");//賠付代號
		String compelPayType = httpServletRequest.getParameter("prpLcompensateCompelPayType");//强制险赔付类别
		String idNumber = httpServletRequest.getParameter("prpLreplevyIdNumber");//身份證字號
		String contactTelephone = httpServletRequest.getParameter("prpLreplevyContactTelephone");//聯絡電話
		String contactAddress = httpServletRequest.getParameter("prpLreplevyContactAddress");//聯絡地址
		String totalTimes = httpServletRequest.getParameter("prpLreplevyTotalTimes");//總期數
		String replevyTimes = httpServletRequest.getParameter("prpLreplevyReplevyTimes");//已追償期數
		int times = Integer.parseInt(httpServletRequest.getParameter("prpLreplevyTimes")); // 追償次數\
		String startSitePort = httpServletRequest.getParameter("prpLreplevyStartSitePort");
		String startSiteCountry = httpServletRequest.getParameter("prpLreplevyStartSiteCountry");
		String endSitePort = httpServletRequest.getParameter("prpLreplevyEndSitePort");
		String endSiteCountry = httpServletRequest.getParameter("prpLreplevyEndSiteCountry");
		
		prpLcompensate.setTimes(times);
		prpLcompensate.setDeductCond(prpLreplevyReclaimDate);
		prpLcompensate.setPreserveDate(new DateTime(replevyLimitDate));
		prpLcompensate.setCounterClaimerName(prpLreplevyRepleviedName);
		prpLcompensate.setDutyDescription(prpLreplevyReplevyReason);
		prpLcompensate.setSumDutyPaid(-Double.parseDouble(sumThisPaid));
		prpLcompensate.setSumNoDutyFee(Double.parseDouble(sumThisCharge));
		prpLcompensate.setSumPaid((-Double.parseDouble(sumThisPaid)) + Double.parseDouble(sumThisCharge));
		prpLcompensate.setSumPrePaid(0);
		prpLcompensate.setSumThisPaid(-Double.parseDouble(sumThisPaid));
		if (DataUtils.emptyToNull(prpLreplevyValidDate) != null) {
			prpLcompensate.setStatisticsYM(new DateTime(prpLreplevyValidDate, DateTime.YEAR_TO_DAY));
		}
		prpLcompensate.setOperatorCode(user.getUserCode());
		prpLcompensate.setOperatorName(user.getUserName());
		prpLcompensate.setOppositeClaimNo(oppositeClaimNo);
		prpLcompensate.setOppositeClaimOfficer(oppositeClaimOfficer);
		prpLcompensate.setPayCodeType(payCodeType);
		prpLcompensate.setCompelPayType(compelPayType);
		prpLcompensate.setRemark(prpLreplevyNote);
		prpLcompensate.setPaySituation(paySituation);
		prpLcompensate.setIndemnityDuty(replevyTypeCode);
		prpLcompensate.setIsPayForOther(isPayForOtherFlag);
		prpLcompensate.setTotalTimes(Integer.parseInt(DataUtils.nullToZero(totalTimes)));
		prpLcompensate.setReplevyTimes(Integer.parseInt(DataUtils.nullToZero(replevyTimes)));
		prpLcompensate.setIdNumber(DataUtils.dbNullToEmpty(idNumber));
		prpLcompensate.setContactTelephone(DataUtils.dbNullToEmpty(contactTelephone));
		prpLcompensate.setContactAddress(DataUtils.dbNullToEmpty(contactAddress));
		prpLcompensate.setStartSitePort(startSitePort);
		prpLcompensate.setStartSiteCountry(startSiteCountry);
		prpLcompensate.setEndSitePort(endSitePort);
		prpLcompensate.setEndSiteCountry(endSiteCountry);
		/***  add by 中科軟 20150601 需求變更-095 begin ***/
		String riskCode = prpLclaim.getRiskCode();
		String riskType = codeService.translateRiskCodetoRiskType(riskCode);
		if (ConstantCodes.CLASSCODE_D.equals(riskType)) {
			if(ConstantCodes.RISKCODE_DAZ.equals(riskCode)){
				prpLcompensate.setAccidentType(prpLclaim.getPropAccidentType());
			} else {
				prpLcompensate.setAccidentType(prpLclaim.getCarAccidentType());//車體險肇責類型
				prpLcompensate.setPropAccidentType(prpLclaim.getPropAccidentType());//責任險肇責類型
			}
		}
		/***  add by 中科軟 20150601 需求變更-095 end ***/
		//mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -start
		if("A01".equals(riskCode) || "B01".equals(riskCode)){
			/** 本車肇事責任百分比 */
			prpLcompensate.setIndemnityDutyRate(Double.valueOf(DataUtils.nullToZero(httpServletRequest.getParameter("prpLcompensateIndemnityDutyRate"))));
			/** 對方車肇事責任百分比 */
			prpLcompensate.setOppositeIndemnityDuty(Double.valueOf(DataUtils.nullToZero(httpServletRequest.getParameter("prpLcompensateOppositeIndemnityDuty"))));
			/** 其他肇事責任百分比 */
			prpLcompensate.setOtherIndemnityDuty(Double.valueOf(DataUtils.nullToZero(httpServletRequest.getParameter("prpLcompensateOtherIndemnityDuty"))));
			/** 賠付代號 1一次赔付结案\2免赔结案\3部分赔付\4最後一次赔付\5代位求偿/残余物处理摊回\6已付赔款调整 */
			prpLcompensate.setPayCode(httpServletRequest.getParameter("prpLcompensatePayCode"));
		}
		//mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -end
		return prpLcompensate;
	}
	/**
	 * 追偿保存前收集页面数据
	 * @param httpServletRequest
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public CompensateDto viewToDto(HttpServletRequest httpServletRequest, String compensateNo) throws Exception {
		CompensateDto compensateDto = new CompensateDto();
		String editType = httpServletRequest.getParameter("editType");
//		String riskType = this.codeService.translateRiskCodetoRiskType(httpServletRequest.getParameter("riskCode"));
		String prpLreplevyClaimNo = httpServletRequest.getParameter("prpLreplevyClaimNo"); //立案号码
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLreplevyClaimNo);
		PrpLcompensate prpLcompensate = this.viewToPrpLcompensate(httpServletRequest, editType, compensateNo, prpLclaim);
		// ----------------------------追偿收入---------------------------------------------------------
		String[] prpLlossSerialNo = httpServletRequest.getParameterValues("prpLlossSerialNo");// 序号
		String[] prpllossKindCode = httpServletRequest.getParameterValues("prpLlossKindCode");// 險別代碼
		String[] prpLlossSumLoss = httpServletRequest.getParameterValues("prpLlossSumLoss");// 預估追償金額
		String[] prpLlossSumRealPay = httpServletRequest.getParameterValues("prpLlossSumRealPay");// 追償金額
		String[] prpLlossRemark = httpServletRequest.getParameterValues("prpLlossRemark");// 備註
		String[] prpLlossPayObjectSerialNo = httpServletRequest.getParameterValues("prpLlossPayObjectSerialNo");//收取對象序號
		String[] prpLlossPreSumloss = httpServletRequest.getParameterValues("prpLlossPreSumloss");//修改前法务预估
		String[] prpLlossCurrency = httpServletRequest.getParameterValues("prpLlossCurrency");
		String[] prpLlossCurrency1 = httpServletRequest.getParameterValues("prpLlossCurrency1");
		String[] prpLlossCurrency2 = httpServletRequest.getParameterValues("prpLlossCurrency2");
		String[] prpLlossCurrency3 = httpServletRequest.getParameterValues("prpLlossCurrency3");
		String[] prpLlossCurrency4 = httpServletRequest.getParameterValues("prpLlossCurrency4");
		String[] prpLlossDtoExchRate = httpServletRequest.getParameterValues("prpLlossDtoExchRate");
		String[] prpLlossItemKindNo = httpServletRequest.getParameterValues("prpLlossItemKindNo");
		List<PrpCitemKind> prpCitemKindList = prpCitemKindService.findByConditions("policyNo ='"+prpLcompensate.getPolicyNo()+"'");
		String itemKindNo = null;
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		if (prpLlossSerialNo != null) {
			PrpLloss prpLloss = null;
			double calSumLoss = 0d;//預估賠償金額之和
			for (int lossCount = 1; lossCount < prpllossKindCode.length; lossCount++) {
				calSumLoss +=Double.parseDouble(prpLlossSumLoss[lossCount]);
				prpLloss = new PrpLloss();
				prpLloss.getId().setSerialNo(lossCount);
				prpLloss.getId().setCompensateNo(compensateNo);
				prpLloss.setRiskCode(prpLcompensate.getRiskCode());
				prpLloss.setPolicyNo(prpLcompensate.getPolicyNo());
				prpLloss.setKindCode(prpllossKindCode[lossCount]);
				itemKindNo = CommonUtils.getValue(prpLlossItemKindNo, lossCount);
				if(!CommonUtils.isEmpty(itemKindNo)){
					prpLloss.setItemKindNo(Integer.parseInt(itemKindNo));
				}
				for(PrpCitemKind prpCitemKind : prpCitemKindList){
					if(prpLloss.getKindCode().equals(prpCitemKind.getKindCode())&&prpLloss.getItemKindNo()==prpCitemKind.getId().getItemKindNo().intValue()){
						prpLloss.setItemCode(prpCitemKind.getItemCode());
						prpLloss.setLossName(prpCitemKind.getItemName()==null ? prpCitemKind.getItemDetailName(): prpCitemKind.getItemName());
						break;
					}
				}
				prpLloss.setCurrency(CommonUtils.getValue(prpLlossCurrency, lossCount));
				prpLloss.setExchRate(Double.parseDouble(CommonUtils.getValue(prpLlossDtoExchRate, lossCount)));
				prpLloss.setCurrency1(CommonUtils.getValue(prpLlossCurrency1, lossCount));
				prpLloss.setCurrency2(CommonUtils.getValue(prpLlossCurrency2, lossCount));
				prpLloss.setCurrency3(CommonUtils.getValue(prpLlossCurrency3, lossCount));
				prpLloss.setCurrency4(CommonUtils.getValue(prpLlossCurrency4, lossCount));
				prpLloss.setSumLoss(Double.parseDouble(prpLlossSumLoss[lossCount]));
				prpLloss.setSumRealPay(0 - Double.parseDouble(prpLlossSumRealPay[lossCount]));
				prpLloss.setRemark(prpLlossRemark[lossCount]);
				prpLloss.setPayObjectSerialNo(prpLlossPayObjectSerialNo[lossCount]);
				if("editQuery".equals(editType) || ("EDIT".equals(editType) && compensateNo.endsWith("00"))){
					prpLloss.setPreSumloss(Double.parseDouble(prpLlossPreSumloss[lossCount]));
				}
				prpLlossList.add(prpLloss);
			}
			prpLcompensate.setSumLoss(calSumLoss);
		}
		compensateDto.setPrpLcompensate(prpLcompensate);
		compensateDto.setPrpLlossList(prpLlossList);
		List<PrpLcharge> prpLchargeList = new ArrayList<PrpLcharge>();
		// -----------------------------追償費用---------------------------------------------------------
		String[] prpLchargeSerialNo = httpServletRequest.getParameterValues("prpLchargeSerialNo");
		String[] prpLchargeKindCode = httpServletRequest.getParameterValues("prpLchargeKindCode");// 險別代碼
		String[] prpLchargeChargeCode = httpServletRequest.getParameterValues("prpLchargeChargeCode");// 费用代码
		String[] prpLchargeChargeName = httpServletRequest.getParameterValues("prpLchargeChargeName");// 費用名稱
		String[] prpLchargePayObjectType = httpServletRequest.getParameterValues("prpLchargePayObjectType");// 支付類別
		String[] prpLchargePayObjectCode = httpServletRequest.getParameterValues("prpLchargePayObjectCode");// 支付對象編碼
		String[] prpLchargePayObjectName = httpServletRequest.getParameterValues("prpLchargePayObjectName");// 支付對象名稱
		String[] prpLchargeCurrency = httpServletRequest.getParameterValues("prpLchargeCurrency");// 幣種
		String[] prpLchargeChargeReport = httpServletRequest.getParameterValues("prpLchargeChargeReport");// 費用金額
		String[] prpLchargeChargeAmount = httpServletRequest.getParameterValues("prpLchargeChargeAmount");// 實際費用
		// liuwei---2013-5-7---------------------------增加追償費用对象数据收集，增加收款人對象信息的收集,利用CertiType,业务类型01赔款，02费用来区分-------------------------------------------------
		// ----------追償費用对象数据收集----CertiType='02'----------
		String[] prpLchargeOwnerShip = httpServletRequest.getParameterValues("prpLchargeOwnerShip");//
		String[] prpLchargeOwnerName = httpServletRequest.getParameterValues("prpLchargeOwnerName");//
		String[] prpLchargeUniformNo = httpServletRequest.getParameterValues("prpLchargeUniformNo");//
		String[] prpLchargeCutBack = httpServletRequest.getParameterValues("prpLchargeCutBack");//
		String[] prpLchargeBankCode = httpServletRequest.getParameterValues("prpLchargeBankCode");//
		String[] prpLchargeBankName = httpServletRequest.getParameterValues("prpLchargeBankName");//
		String[] prpLchargeAccountCode = httpServletRequest.getParameterValues("prpLchargeAccountCode");//
		String[] prpLchargeCustomBankCode = httpServletRequest.getParameterValues("prpLchargeCustomBankCode");//
		String[] prpLchargeCustomBankName = httpServletRequest.getParameterValues("prpLchargeCustomBankName");//
		String[] prpLchargeAreaCode = httpServletRequest.getParameterValues("prpLchargeAreaCode");//
		String[] prpLchargeCourierAddress = httpServletRequest.getParameterValues("prpLchargeCourierAddress");//
		String[] prpLchargeCertificateCode = httpServletRequest.getParameterValues("prpLchargeCertificateCode");//證件類型
		String[] prpLchargeItemKindNo = httpServletRequest.getParameterValues("prpLchargeItemKindNo");
		
		List<PrpLpayObjectInfo> prpLpayObjectInfoList = new ArrayList<PrpLpayObjectInfo>();
		// -------追償費用的一部分数据，如险别代码等，已经收集到prpLcharge对象里面了
		PrpLpayObjectInfo prpLpayObjectInfo = null;
		if (prpLchargeSerialNo != null) {
			PrpLcharge prpLcharge = null;
			for (int index = 1; index < prpLchargeKindCode.length; index++) {
				prpLcharge = new PrpLcharge();
				prpLcharge.getId().setSerialNo(index);
				prpLcharge.getId().setCompensateNo(compensateNo);
				prpLcharge.setRiskCode(prpLcompensate.getRiskCode());
				prpLcharge.setPolicyNo(prpLcompensate.getPolicyNo());
				prpLcharge.setKindCode(prpLchargeKindCode[index]);
				itemKindNo = CommonUtils.getValue(prpLchargeItemKindNo, index);
				if(!CommonUtils.isEmpty(itemKindNo)){
					prpLcharge.setItemKindNo(Integer.parseInt(itemKindNo));
				}
				prpLcharge.setChargeCode(prpLchargeChargeCode[index]);
				prpLcharge.setChargeName(prpLchargeChargeName[index]);
				prpLcharge.setCurrency(prpLchargeCurrency[index]);
				prpLcharge.setChargeReport(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeReport[index])));
				prpLcharge.setChargeAmount(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeAmount[index])));
				prpLcharge.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_SECOND));
				prpLcharge.setPayObjectCode(prpLchargePayObjectCode[index]);
				prpLcharge.setPayObjectType(prpLchargePayObjectType[index]);
				prpLcharge.setPayObjectName(prpLchargePayObjectName[index]);
				prpLcharge.setOwnerShip(prpLchargeOwnerShip[index]);
				if ("B".equals(prpLcharge.getOwnerShip())) {
				} else if ("C".equals(prpLcharge.getOwnerShip()) || "Q".equals(prpLcharge.getOwnerShip())) {
					prpLcharge.setOwnerName(prpLchargeOwnerName[index]);
					prpLcharge.setCertifiCateCode(prpLchargeCertificateCode[index]);
				}
				prpLchargeList.add(prpLcharge);
				
				prpLpayObjectInfo = new PrpLpayObjectInfo();
				// 增加对支付对象的保存
				prpLpayObjectInfo.getId().setCompensateNo(compensateNo);
				prpLpayObjectInfo.getId().setCertiType(PrpLpayObjectInfo.CERTITYPE_CHARGE);
				prpLpayObjectInfo.getId().setSerialNo(index);
				prpLpayObjectInfo.setRiskCode(prpLcompensate.getRiskCode());
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
				//存实际费用
				prpLpayObjectInfo.setPayAmount(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeAmount[index])));
				//證件類型
				prpLpayObjectInfo.setCertificateCode(prpLchargeCertificateCode[index]);
				prpLpayObjectInfoList.add(prpLpayObjectInfo);
			}
		}
		compensateDto.setPrpLchargeList(prpLchargeList);
		List<PrpLctext> prpLcTextList = new ArrayList<PrpLctext>();
		String prpLctextContext = httpServletRequest.getParameter("prpLrtextContext");
		String[] rules = StringUtils.split(prpLctextContext, RULE_LENGTH, "GBK");
		PrpLctext prpLctext = null;
		for (int k = 0; k < rules.length; k++) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo(compensateNo);
			prpLctext.setContext(rules[k]);
			prpLctext.getId().setLineNo(k + 1);
			prpLctext.getId().setTextType("26");
			prpLcTextList.add(prpLctext);
		}
		//追偿协商的调整原因
		String prpLctextContextInnerHTML = httpServletRequest.getParameter("prpLctextContextInnerHTML");
		rules = StringUtils.split(prpLctextContextInnerHTML, RULE_LENGTH, "GBK");
		for (int k = 0; k < rules.length; k++) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo(compensateNo);
			prpLctext.setContext(rules[k]);
			prpLctext.getId().setLineNo(k + 1);
			prpLctext.getId().setTextType("30");
			prpLcTextList.add(prpLctext);
		}
		/*---------------------联共保信息PrpLcfeecoinsDto------------------------------------*/
		List<PrpLcfeecoins> prpLcfeecoinsList = new ArrayList<PrpLcfeecoins>();
		PrpLcfeecoins prpLcfeecoins = null;
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
		if (prpLcfeecoinsSerialNo != null) {
			for (int index = 1; index < prpLcfeecoinsSerialNo.length; index++) {
				prpLcfeecoins = new PrpLcfeecoins();
				prpLcfeecoins.getId().setBusinessNo(compensateNo);
				prpLcfeecoins.setPolicyNo(prpLcompensate.getPolicyNo());
				prpLcfeecoins.setRiskCode(prpLcompensate.getRiskCode());
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
		// 差异化start,----add by

		// ---------收款人對象信息的收集-----setCertiType='01'-----
		String[] prpLpayObjectInfoOwnerShip = httpServletRequest.getParameterValues("prpLpayObjectInfoOwnerShip");
		String[] prpLpayObjectInfoPayAmount = httpServletRequest.getParameterValues("prpLpayObjectInfoPayAmount");
		String[] prpLpayObjectInfoOwnerName = httpServletRequest.getParameterValues("prpLpayObjectInfoOwnerName");
		String[] prpLpayObjectInfoUniformNo = httpServletRequest.getParameterValues("prpLpayObjectInfoUniformNo");
		String[] prpLpayObjectInfoBeneficiaryPhone = httpServletRequest.getParameterValues("prpLpayObjectInfoBeneficiaryPhone");
		String[] prpLpayObjectInfoAreaCode = httpServletRequest.getParameterValues("prpLpayObjectInfoAreaCode");
		String[] prpLpayObjectInfoCourierAddress = httpServletRequest.getParameterValues("prpLpayObjectInfoCourierAddress");
		String[] prpLpayObjectInfoCutBack = httpServletRequest.getParameterValues("prpLpayObjectInfoCutBack");
		String[] prpLpayObjectInfoAccountCode = httpServletRequest.getParameterValues("prpLpayObjectInfoAccountCode");
		String[] prpLpayObjectInfoBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoBankCode");
		String[] prpLpayObjectInfoBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoBankName");
		String[] prpLpayObjectInfoCustomBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankCode");
		String[] prpLpayObjectInfoCustomBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankName");
		String[] prpLpayObjectInfoCertificateCode = httpServletRequest.getParameterValues("prpLpayObjectInfoCertificateCode");//證件類型
		String[] prpLpayObjectInfoManager = httpServletRequest.getParameterValues("prpLpayObjectInfoManager");
		String[] prpLpayObjectInfoCurrency = httpServletRequest.getParameterValues("prpLpayObjectInfoCurrency");//支付币别
		String[] prpLpayObjectInfoAccountCurrency = httpServletRequest.getParameterValues("prpLpayObjectInfoAccountCurrency");//支付币别
		String[] prpLpayObjectInfoExchRate = httpServletRequest.getParameterValues("prpLpayObjectInfoExchRate");//支付币别
		for (int index = 1; index < prpLpayObjectInfoOwnerShip.length; index++) {
			prpLpayObjectInfo = new PrpLpayObjectInfo();
			prpLpayObjectInfo.getId().setCompensateNo(compensateNo);
			prpLpayObjectInfo.getId().setSerialNo(index);
			prpLpayObjectInfo.getId().setCertiType("01");
			prpLpayObjectInfo.setRiskCode(prpLcompensate.getRiskCode());
			prpLpayObjectInfo.setCertificateCode(" ");
			prpLpayObjectInfo.setOwnerShip(prpLpayObjectInfoOwnerShip[index]);
			prpLpayObjectInfo.setPayAmount(Double.parseDouble(DataUtils.nullToZero(prpLpayObjectInfoPayAmount[index])));
			prpLpayObjectInfo.setRepLevyManager(prpLpayObjectInfoManager[index]);
			prpLpayObjectInfo.setOwnerName(prpLpayObjectInfoOwnerName[index]);
			prpLpayObjectInfo.setUniformNo(prpLpayObjectInfoUniformNo[index]);
			prpLpayObjectInfo.setBeneficiaryPhone(prpLpayObjectInfoBeneficiaryPhone[index]);
			prpLpayObjectInfo.setCourierAddress(prpLpayObjectInfoCourierAddress[index]);
			prpLpayObjectInfo.setAreaCode(prpLpayObjectInfoAreaCode[index]);
			prpLpayObjectInfo.setCutBack(prpLpayObjectInfoCutBack[index]);
			prpLpayObjectInfo.setAccountCode(prpLpayObjectInfoAccountCode[index]);
			prpLpayObjectInfo.setBankCode(prpLpayObjectInfoBankCode[index]);
			prpLpayObjectInfo.setBankName(prpLpayObjectInfoBankName[index]);
			prpLpayObjectInfo.setCustomBankCode(prpLpayObjectInfoCustomBankCode[index]);
			prpLpayObjectInfo.setCustomBankName(prpLpayObjectInfoCustomBankName[index]);
			prpLpayObjectInfo.setCertificateCode(prpLpayObjectInfoCertificateCode[index]);
			prpLpayObjectInfo.setCurrency(CommonUtils.getValue(prpLpayObjectInfoCurrency,index));
			prpLpayObjectInfo.setAccountCurrency(CommonUtils.getValue(prpLpayObjectInfoAccountCurrency,index));
			prpLpayObjectInfo.setExchRate(DataUtils.getDouble(DataUtils.emptyToNull(CommonUtils.getValue(prpLpayObjectInfoExchRate,index))));
			prpLpayObjectInfoList.add(prpLpayObjectInfo);
		}
		compensateDto.setPrpLpayObjectInfoList(prpLpayObjectInfoList);
		ClaimDto claimDto = claimService.findByPrimaryKey(prpLreplevyClaimNo);
		Prplreplevyhistory prplreplevyhistory = prpLreplevyhistoryService.findPrplreplevyhistory(compensateNo);
		if (prplreplevyhistory == null) {
			prplreplevyhistory = new Prplreplevyhistory();
		}
		prplreplevyhistory.setBusinessNo(compensateNo);
		prplreplevyhistory.setRegistNo(claimDto.getPrpLclaim().getRegistNo());
		prplreplevyhistory.setClaimNo(prpLreplevyClaimNo);
		prplreplevyhistory.setPolicyNo(prpLcompensate.getPolicyNo());
		prplreplevyhistory.setRiskCode(prpLcompensate.getRiskCode());
		prplreplevyhistory.setReplevytimes(prpLcompensate.getTimes());
		prplreplevyhistory.setReplevytype(prpLcompensate.getIndemnityDuty());
		prplreplevyhistory.setCurrency(prpLcompensate.getCurrency());
		prplreplevyhistory.setReplevysumpaid(prpLcompensate.getSumThisPaid());
		prplreplevyhistory.setReplevyfee(prpLcompensate.getSumNoDutyFee());
		prplreplevyhistory.setOperatorCode(prpLcompensate.getOperatorCode());
		prplreplevyhistory.setOperatorname(prpLcompensate.getOperatorName());
		prplreplevyhistory.setReplevytime(prpLcompensate.getStatisticsYM());
		prplreplevyhistory.setReplevyendtime(prpLcompensate.getPreserveDate());
		prplreplevyhistory.setComCode(prpLclaim.getComCode());
		prplreplevyhistory.setComname(prpLclaim.getComName());
		prplreplevyhistory.setReplevyreason(prpLcompensate.getDutyDescription());
		if (prpLctextContext.length() > 1990) {
			prpLctextContext = prpLctextContext.substring(0, 1990);
		}
		prplreplevyhistory.setReplevytext(prpLctextContext);
		prpLreplevyhistoryService.saveOrUpdate(prplreplevyhistory);
		compensateDto.setPrpLctextList(prpLcTextList);
		// 提交成功後显示信息
		return compensateDto;
	}
	
	
	/***
	 * 追偿查询
	 * @param httpServletRequest
	 * @param pageNo
	 * @param recordPerPage
	 * @throws Exception
	 */
	public void prpLcompensateListToView(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
		// 得到追偿查询条件
		String claimNo = StringUtils.rightTrim(httpServletRequest.getParameter("ClaimNo")); // 赔案号
		String compensateNo = StringUtils.rightTrim(httpServletRequest.getParameter("CompensateNo")); // 计算书号
		String policyNo = StringUtils.rightTrim(httpServletRequest.getParameter("PolicyNo")); // 保单号
		String registNo = StringUtils.rightTrim(httpServletRequest.getParameter("RegistNo"));// 报案号
		String prpLreplevyRepleviedName = StringConvert.getParam(httpServletRequest, "prpLreplevyRepleviedName", ConstantCodes.YUI_CHARSET); // 被追偿人名称
		String inputStartDate = httpServletRequest.getParameter("InputStartDate"); // 起始日期
		String inputEndDate = httpServletRequest.getParameter("InputEndDate"); // 截止日期
		String validStartDate = httpServletRequest.getParameter("ValidStartDate");// 追偿日期
		String validEndDate = httpServletRequest.getParameter("ValidEndDate");// 追偿日期
		String preserveDate = httpServletRequest.getParameter("PreserveDate");//即将到期日期
		// 得到操作符号
		String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
		String compensateNoSign = httpServletRequest.getParameter("CompensateNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String repleviedNameSign = httpServletRequest.getParameter("RepleviedNameSign");
//		String validDateSign = httpServletRequest.getParameter("ValidDateSign");
		// 组合查询条件
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		conditions.append(StringConvert.convertString("compensateNo", compensateNo, compensateNoSign));
		conditions.append(StringConvert.convertString("policyNo", policyNo, policyNoSign));
		conditions.append(StringConvert.convertString("counterClaimerName", prpLreplevyRepleviedName, repleviedNameSign));
		conditions.append(StringConvert.convertDate("statisticsYM", validStartDate, ">="));
		conditions.append(StringConvert.convertDate("statisticsYM", validEndDate, "<="));
		conditions.append(StringConvert.convertDate("inputDate", inputStartDate, ">="));
		conditions.append(StringConvert.convertDate("inputDate", inputEndDate, "<="));
		if(!CommonUtils.isEmpty(preserveDate)){
			conditions.append(" and add_months(preserveDate,-4)<=to_date('"+preserveDate+"','yyyy-mm-dd') and preserveDate>=to_date('"+preserveDate+"','yyyy-mm-dd')");
		}
		
		if (DataUtils.emptyToNull(registNo) != null) {
			String claimNoTemp = this.codeService.translateBusinessCode(registNo, true);// 将报案号转为立案号
			conditions.append("claimNo = '"+DataUtils.dbNullToEmpty(claimNoTemp)+"'");
		}
		conditions.append("And caseType='R' And underWriteFlag ='1'");
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions.append(powerService.addRiskPower(userDto, "prplcompensate","claim") + uiPowerInterface.addCustomerPower(userDto, "prplcompensate", "", "ComCode"));
		Page page = prpLcompensateService.findByConditions(conditions.toString(), pageNo, recordPerPage);
		List<PrpLcompensate> prpLcompensateList = page.getResult();
		prpLcompensate.setCompensateList(prpLcompensateList);
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
		// 将查询信息发送到页面
		httpServletRequest.setAttribute("prpLcompensateList", prpLcompensateList);
		httpServletRequest.setAttribute("page", page);
	}
	/**
	 * 追偿申请，查询页面显示信息
	 * @param httpServletRequest
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public PrpLcompensate EndCaseToView(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
		String policyNo = httpServletRequest.getParameter("policyNo");
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
		String caseNo = "";
		EndcaseDto endCaseDto = endcaseService.findByPrimaryKey(claimNo);
		if (endCaseDto != null && endCaseDto.getPrpLcaseNoList().size() > 0) {
			prpLcaseNo = endCaseDto.getPrpLcaseNoList().get(0);
		}
		if (prpLcaseNo != null) {
			caseNo = prpLcaseNo.getId().getCaseNo();
		}
		prpLcompensate.setRegistNo(prpLclaim.getRegistNo());
		prpLcompensate.setClaimNo(claimNo);
		prpLcompensate.setPolicyNo(policyNo);
		prpLcompensate.setCaseNo(caseNo);
		prpLcompensate.setSumLoss(prpLclaim.getSumPaid());
		prpLcompensate.setRiskCode(prpLclaim.getRiskCode());
		prpLcompensate.setSumNoDutyFee(0.0);
		prpLcompensate.setMakeCom(prpLclaim.getMakeCom());
		prpLcompensate.setComCode(prpLclaim.getComCode());
		prpLcompensate.setHandler1Code(prpLclaim.getHandler1Code());
		prpLcompensate.setHandlerName(user.getUserName());
		prpLcompensate.setHandlerCode(user.getUserCode());
		prpLcompensate.setTimes(0);//追償登錄時，默認的追償次數0
		// 追償時效默認為出險后兩年內,B01-强制险：赔付后2年，M-水险：1年，FD-員工誠實保證保險：15年。
		String riskCode = prpLclaim.getRiskCode();
		String riskType = codeService.translateRiskCodetoRiskType(riskCode);
		Date preserveDate = null;
		if(ConstantCodes.CLASSCODE_Y.equals(riskType)||ConstantCodes.RISKCODE_DAZ.equals(riskCode)||ConstantCodes.RISKCODE_FD.equals(riskCode)){
			String conditions = "compensateNo =(select MIN(compensateNo) from prpLcompensate where claimno ='"+claimNo+"' and  compensateno like 'C%' and (UnderWriteFlag = '1' or UnderWriteFlag = '3'))";
			List<PrpLcompensate>prpLcompensateList = prpLcompensateService.findByConditions(conditions);
			if(prpLcompensateList.size()>0){
				preserveDate = prpLcompensateList.get(0).getUnderWriteEndDate();
			}
			if(preserveDate!=null){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(preserveDate);
				if(ConstantCodes.RISKCODE_DAZ.equals(riskCode)){
					calendar.add(Calendar.YEAR, 2);
				}else if(ConstantCodes.CLASSCODE_Y.equals(riskType)){
					calendar.add(Calendar.YEAR, 1);
				}else{
					calendar.add(Calendar.YEAR, 15);
				}
				preserveDate = calendar.getTime();
			}
		}
		if(preserveDate==null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(prpLclaim.getDamageStartDate());
			calendar.add(Calendar.YEAR, 2);
			preserveDate = calendar.getTime();
		}
		prpLcompensate.setPreserveDate(preserveDate);
		prpLcompensate.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
		prpLcompensate.setPayCodeType("1");// 默認賠付代號為1.一般賠案
		prpLcompensate.setPaySituation("3");// 追償金已追償完畢結案;
		prpLcompensate.setUnderWriteFlag("0");// 初始計算書狀態
		// mantis： CLM0106 ，處理人員：BK007  蘇哲，需求單編號：CLM0106.新核心案件賠付速別預設值更改為速件
		prpLcompensate.setSpeedFlag("N"); // 默認速別為 "N"
		// 保險期間
		prpLcompensate.setIsPayForOther("0");// 联共保功能暂不开放，暂时写死
		prpLcompensate.setCurrency(prpLclaim.getCurrency());
		List<PrpLloss> limitList = this.compensateService.getReplevyInfoByClaim(prpLcompensate.getClaimNo());
		httpServletRequest.setAttribute("limitList", limitList);
		List<PrpCitemKind> prpCitemKindList = prpCitemKindService.findByConditions("policyNo ='"+policyNo+"'");
		if (limitList != null) {
			PrpLloss prpLlossTemp = null;
			for (int i = 0; i < limitList.size(); i++) {
				prpLlossTemp = limitList.get(i);
				prpLlossTemp.setKindName(this.codeService.translateKindCode(prpLlossTemp.getRiskCode(), prpLlossTemp.getKindCode(), true));
				prpLlossTemp.setSumRealPay(0);
				prpLlossTemp.setCurrency(ConstantCodes.LOCAL_CURRENCY);
				for(PrpCitemKind prpCitemKind : prpCitemKindList){
					if(prpLlossTemp.getKindCode().equals(prpCitemKind.getKindCode())){
						prpLlossTemp.setItemKindNo(prpCitemKind.getId().getItemKindNo());
						break;
					}
				}
			}
		}
		PrpLloss prpLloss = new PrpLloss();
		prpLloss.setPrpLlossList(limitList);
		httpServletRequest.setAttribute("prpLloss", prpLloss);
		this.setPublicToView(httpServletRequest, prpLcompensate);
		// 各方联共保分摊信息
		PrpLcfeecoins prpLcfeecoins = new PrpLcfeecoins();
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.businessNo", "").addAscOrder("id.serialNo");
		List<PrpLcfeecoins> LcfeecoinsList = prpLcfeecoinsService.findPrpLcfeecoins(queryRule);
		prpLcfeecoins.setPrpLcfeecoinsList(LcfeecoinsList);
		httpServletRequest.setAttribute("prpLcfeecoins", prpLcfeecoins);
		Prplreplevyhistory prplreplevyhistory = new Prplreplevyhistory();
		httpServletRequest.setAttribute("RiskCodeName", this.codeService.translateRiskCode(prpLclaim.getRiskCode(), true));
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		httpServletRequest.setAttribute("prplreplevyhistory", prplreplevyhistory);
		this.setSelectionList(httpServletRequest);
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){//意健險設置被保險人序號
			this.setFamilyNo(httpServletRequest, prpLclaim);
		}
		//mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -start
		if (ConstantCodes.RISKCODE_DAZ.equals(prpLcompensate.getRiskCode())) {
			// 强制险 赔付代号
			httpServletRequest.setAttribute("payCodeList", ConstantsCollection.qzPayCodeList);
			// 肇责百分比
			httpServletRequest.setAttribute("indemnityDutyList", ConstantsCollection.indemnityDutyList);
			if("3".equals(prpLcompensate.getPayCode())){
				prpLcompensate.setPayCode("5");
			}
		} else if(ConstantCodes.RISKCODE_DAA.equals(prpLcompensate.getRiskCode())) {
			// 任意险 赔付代号
			httpServletRequest.setAttribute("payCodeList", ConstantsCollection.payCodeList);
			// 肇责百分比
			httpServletRequest.setAttribute("indemnityDutyList", ConstantsCollection.indemnityDutyList);
			if("3".equals(prpLcompensate.getPayCode())){
				prpLcompensate.setPayCode("5");
			}
		}
		//mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -end
		return prpLcompensate;
	}
	
	private void setFamilyNo(HttpServletRequest httpServletRequest , PrpLclaim prpLclaim) throws Exception{
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		String insuredCode = prpLclaim.getInsuredCode();
		String insuredName = prpLclaim.getInsuredName();
		List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(prpLclaim.getPolicyNo(), damageDate, damageHour, insuredCode, insuredName);
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
		if (prpCinsured != null) {
			httpServletRequest.setAttribute("familyNo", prpCinsured.getId().getSerialNo());
		}
	}
	/***
	 * 設置公共查看部份，從其他內容帶入
	 * @param request
	 * @param prpLcompensate
	 * @throws Exception 
	 */
	public void setPublicToView(HttpServletRequest request,PrpLcompensate prpLcompensate) throws Exception{
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		String configCode = this.codeService.translateRiskCodetoConfigCode(prpLclaim.getRiskCode());
		String riskType = this.codeService.translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		request.setAttribute("prpLclaim", prpLclaim);
		request.setAttribute("configCode", configCode);
		request.setAttribute("riskType", riskType);
		String claimNo = prpLclaim.getClaimNo();
		if("RISKCODE_DAZ".equals(configCode)){//強制險要取賠付日期，為第一張計算書審核通過的時間
			String sql = " claimno = '"+claimNo+"' and compensateno like 'C"+claimNo+"%' and (UnderWriteFlag = '1' or UnderWriteFlag = '3') order by compensateno asc";
			List<PrpLcompensate> tempList = prpLcompensateService.findPrpLcompensate(QueryRule.getInstance().addSql(sql));
			if(tempList!=null && !tempList.isEmpty()){
				prpLcompensate.setPayDate(tempList.get(0).getUnderWriteEndDate());
			}
		}
		prpLcompensate.setComName(this.codeService.translateComCode(prpLcompensate.getComCode(), true));
		prpLcompensate.setHandler1Name(this.codeService.translateUserCode(prpLcompensate.getHandler1Code(), true));
		prpLcompensate.setDamageStartDate(prpLclaim.getDamageStartDate());
		//增加出險時間的顯示
		String timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());
		prpLcompensate.setDamageStartHour(timeTemp.substring(0, 2));
		prpLcompensate.setDamageStartMinute(timeTemp.substring(3, 5));
		String indemnityDutyRateName = this.getCodeService().translateCodeCode("IndemnityDuty", prpLclaim.getIndemnityDuty(), true);
		prpLcompensate.setIndemnityDutyName(indemnityDutyRateName);
		//保險期間
		prpLcompensate.setStartDate(prpLclaim.getStartDate());
		prpLcompensate.setStartHour(prpLclaim.getStartHour());
		prpLcompensate.setEndDate(prpLclaim.getEndDate());
		prpLcompensate.setEndHour(prpLclaim.getEndHour());
		List<PrpCitemCar> prpCitemCarList = this.getPrpCitemCarService().findPrpCitemCar(QueryRule.getInstance().addEqual("id.policyNo", prpLcompensate.getPolicyNo()));
		PrpCitemCar prpCitemCar = null;
		if (prpCitemCarList != null && !prpCitemCarList.isEmpty()) {
			// 对车型等信息的支持
			prpCitemCar = prpCitemCarList.get(0);
			prpLcompensate.setClauseType(prpCitemCar.getClauseType());
			prpLcompensate.setClauseName(this.getCodeService().translateCodeCode("ClauseType", prpCitemCar.getClauseType(), true));
			prpLcompensate.setLicenseNo(prpCitemCar.getLicenseNo());
			prpLcompensate.setClauseTypeCode(prpCitemCar.getClauseType());
			prpLcompensate.setPurchasePrice(String.valueOf(prpCitemCar.getPurchasePrice()));
			prpLcompensate.setLicenseColorCode(prpCitemCar.getLicenseColorCode());
			prpLcompensate.setLicenseColor(this.getCodeService().translateCodeCode("LicenseColor", prpCitemCar.getLicenseColorCode(), true));
			prpLcompensate.setBrandName(prpCitemCar.getBrandName());
			prpLcompensate.setCarKindCode(prpCitemCar.getCarKindCode());
			prpLcompensate.setCarKind(this.getCodeService().translateCodeCode("CarKind", prpCitemCar.getCarKindCode(), true));
			prpLcompensate.setEngineNo(prpCitemCar.getEngineNo());
			prpLcompensate.setFrameNo(prpCitemCar.getFrameNo());
			prpLcompensate.setSeatCount(String.valueOf(prpCitemCar.getSeatCount()));
		}else{
			prpCitemCar = new PrpCitemCar();
		}
		List<?> tempList = null;
		if("Y".equals(riskType)){//水险追偿
			String conditions = " compensateNo like 'C" + prpLclaim.getClaimNo() + "%' order by compensateNo asc ";
			tempList = this.prpLcompensateService.findByConditions(conditions);
			if (!CommonUtils.isEmpty(tempList)) {
				PrpLcompensate compe = (PrpLcompensate) tempList.get(0);
				prpLcompensate.setStartSitePort(DataUtils.dbNullToEmpty(compe.getStartSitePort()));
				prpLcompensate.setStartSiteCountry(DataUtils.dbNullToEmpty(compe.getStartSiteCountry()));
				prpLcompensate.setEndSitePort(DataUtils.dbNullToEmpty(compe.getEndSitePort()));
				prpLcompensate.setEndSiteCountry(DataUtils.dbNullToEmpty(compe.getEndSiteCountry()));
				prpLcompensate.setShipCName(DataUtils.dbNullToEmpty(compe.getShipCName()));
			}
			PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(prpLclaim.getPolicyNo());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(prpCmain.getInputDate());
			prpLcompensate.setPolicyYear(String.valueOf(calendar.get(Calendar.YEAR)));
			if ("RISKCODE_YMC".equals(configCode)) {
				prpLcompensate.setSailStartDate(prpCmain.getStartDate());// 開航日期
			}
		}
		request.setAttribute("prpCitemCar", prpCitemCar);
		double totalSumPaid = 0.00;// 本案合计追偿收入
		double totalSumFee = 0.00;// 本案合计追偿费用
		PrpLcompensate prpLcompensateTemp = new PrpLcompensate();
		String conditions = "caseType = 'R' And claimNo='" + claimNo + "' and UnderWriteFlag = '1' and times <= "+prpLcompensate.getTimes()+"  Order By times Desc";
		QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
		List<PrpLcompensate> prpLcompensateList = prpLcompensateService.findPrpLcompensate(queryRule);
		if (prpLcompensateList != null && prpLcompensateList.size() > 0) {
			for (int i = 0; i < prpLcompensateList.size(); i++) {
				prpLcompensateTemp = prpLcompensateList.get(i);
				if(!prpLcompensateTemp.getCompensateNo().equals(prpLcompensate.getCompensateNo())){
					//追偿查看时，对已审核通过的，要过滤本次
					totalSumPaid += prpLcompensateTemp.getSumThisPaid();
					totalSumFee += prpLcompensateTemp.getSumNoDutyFee();
				}
			}
		}
		prpLcompensate.setSumPaidAll(0-totalSumPaid);// 借用此字段，用於页面展示本案合计追偿收入
		prpLcompensate.setSumDutyPaid1(totalSumFee);// 借用此字段，用於页面展示本案合计追偿收入
		prpLcompensate.setCurrencyName(this.codeService.translateCurrencyCode(prpLcompensate.getCurrency(), true));
		// 联共保标志
		PrpCmain prpCmain = this.getPolicyService().findPrpCmainDtoByPrimaryKey(prpLcompensate.getPolicyNo());
		String appliName = "";//要保人
		if (prpCmain != null) {
			appliName = prpCmain.getAppliName();
		}
		prpLcompensate.setInsuredName(prpLclaim.getInsuredName());
		prpLcompensate.setAppliName(appliName);
		
		//mantis：CLM0029 ，處理人員：DP0713，需求單編號：CLM0029 追償處理險種增刪控制
		//此處個案中會發生 prpCmain為null導致無法繼續執行，增加判斷
		request.setAttribute("coinsFlag", prpCmain!=null?prpCmain.getCoinsFlag():"");
		
		PrpLcheck prpLcheck = new PrpLcheck();
		queryRule = QueryRule.getInstance().addSql(" registNo ='"+prpLclaim.getRegistNo()+"'");
		List<PrpLcheck> checkList = this.prpLcheckService.findPrpLcheck(queryRule);
		if(checkList!=null && !checkList.isEmpty()){
			prpLcheck = checkList.get(0);
		}
		request.setAttribute("prpLcheck", prpLcheck);
	}

	/**
	 * 追償處理，追偿修改，审核整理页面显示信息
	 * @param httpServletRequest
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public PrpLcompensate CompensateToView(HttpServletRequest httpServletRequest, String compensateNo) throws Exception {
		CompensateDto compensateDto = compensateService.findByPrimaryKey(compensateNo);
		Prplreplevyhistory prplreplevyhistory = prpLreplevyhistoryService.findPrplreplevyhistory(compensateNo);
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String editType = httpServletRequest.getParameter("editType");
		if ("ADD".equals(editType)) {// 追償登錄
			prpLcompensate.setCompensateNo("");
			prpLcompensate.setTimes(prpLcompensate.getTimes() + 1);
			prpLcompensate.setStatisticsYM(new Date());
		}
		if (prpLcompensate.getStatisticsYM() != null && ("ADD".equals(editType) || "EDIT".equals(editType))) {
			/** 本位币（新台币）对其他币种的当日汇率 */
			httpServletRequest.setAttribute("baseToExch", this.getCodeService().findBasePrpDexch(prpLcompensate.getStatisticsYM(), ConstantCodes.LOCAL_CURRENCY));
			/** 其他币种对的本位币（新台币）当日汇率 */
			httpServletRequest.setAttribute("exchToBase", this.getCodeService().findExchPrpDexch(prpLcompensate.getStatisticsYM(), ConstantCodes.LOCAL_CURRENCY));
		}
		prpLcompensate.setRegistNo(compensateDto.getPrpLclaim().getRegistNo());
		// 显示追偿金额时和录入时正负号一致，並保证追偿修改无操作提交不会引起正负号变化
		prpLcompensate.setSumThisPaid(0 - prpLcompensate.getSumThisPaid());
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			List<PrpLloss> replevyLlossList = this.compensateService.getPrpLlossForReplevy(prpLcompensate.getClaimNo());//初始化預估追償金額訊息
			for (PrpLloss tempPrpLloss : prpLlossList) {
				tempPrpLloss.setKindName(this.codeService.translateKindCode(tempPrpLloss.getRiskCode(), tempPrpLloss.getKindCode(), true));
				tempPrpLloss.setSumRealPay(0 - tempPrpLloss.getSumRealPay());
				for(PrpLloss p : replevyLlossList){
					if(p.getKindCode().equals(tempPrpLloss.getKindCode())){
						tempPrpLloss.setSumDefPay(p.getSumDefPay());//设置赔款金额
						break;
					}
				}
			}
		}
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		this.setPublicToView(httpServletRequest, prpLcompensate);
		// 各方联共保分摊信息
		PrpLcfeecoins prpLcfeecoins = new PrpLcfeecoins();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.businessNo", compensateNo).addAscOrder("id.serialNo");
		List<PrpLcfeecoins> LcfeecoinsList = prpLcfeecoinsService.findPrpLcfeecoins(queryRule);
		prpLcfeecoins.setPrpLcfeecoinsList(LcfeecoinsList);
		httpServletRequest.setAttribute("prpLcfeecoins", prpLcfeecoins);
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		if (prpLctextList != null && !prpLctextList.isEmpty()) {
			StringBuffer tempContext = new StringBuffer("");
			StringBuffer tempContextAdjReason = new StringBuffer("");
			for (PrpLctext p : prpLctextList) {
				if("26".equals(p.getId().getTextType())){//追偿文字
					tempContext.append(p.getContext());
				}else if("30".equals(p.getId().getTextType())){//追偿协商的调整原因
					tempContextAdjReason.append(p.getContext());
				}
			}
			PrpLctext prpLctext = new PrpLctext();
			prpLctext.setContext(tempContext.toString());
			PrpLctext prpLctextAdjReason  = new PrpLctext();
			prpLctextAdjReason.setContext(tempContextAdjReason.toString());
			httpServletRequest.setAttribute("prpLctext", prpLctext);
			httpServletRequest.setAttribute("prpLctextAdjReason", prpLctextAdjReason);
		}
		PrpLloss prpLloss = new PrpLloss();
		prpLloss.setPrpLlossList(prpLlossList);
		httpServletRequest.setAttribute("prpLloss", prpLloss);
		httpServletRequest.setAttribute("prplreplevyhistoryDto", prplreplevyhistory);
		httpServletRequest.setAttribute("prpLClaim", prpLclaim);
		String riskCodeName = this.codeService.translateRiskCode(prpLcompensate.getRiskCode(), true);
		httpServletRequest.setAttribute("RiskCodeName", riskCodeName);
		httpServletRequest.setAttribute("editType", editType);
		// 货币代码的列表
		Map<String, String> currencyMap = this.getCodeService().findPayCurrencyMap();
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
		// 赔款费用信息多行列表准备数据
		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
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
		PrpLcharge prpLcharge = new PrpLcharge();
		prpLcharge.setPrpLchargeList(prpLchargeList);
		httpServletRequest.setAttribute("prpLcharge", prpLcharge);
		/****************** 赔款费用信息 end *********************************/
		// --------------------------end---------------------------------
		this.setSelectionList(httpServletRequest);
		List<PrpLloss> limitList  = this.compensateService.getReplevyInfoByClaim(prpLcompensate.getClaimNo());
		httpServletRequest.setAttribute("limitList", limitList);
		//审核讯息
		String flowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
		if("SHOW".equals(editType)){
			flowID = this.getWorkFlowService().findViewFlowIDBybusinessNo(compensateNo);
		}
		if (DataUtils.dbNullToEmpty(flowID).length() > 0) {
			boolean flag = "1".equals(prpLcompensate.getUnderWriteFlag());
			List<SwfLog> swfLogList = this.getWorkFlowService().findSwfLogWithNotion(flowID, flag);
			httpServletRequest.setAttribute("swfLogList", swfLogList);
			String logNo = httpServletRequest.getParameter("swfLogLogNo"); // 工作流logNo
			if (DataUtils.dbNullToEmpty(logNo).length() > 0) {
				SwfLog swfLog = null;
				if (flag) {
					swfLog = this.getWorkFlowService().findSwfLogStoreDtoByPrimaryKey(flowID, Integer.parseInt(logNo)).toSwfLog();
				} else {
					swfLog = this.getWorkFlowService().findByPrimaryKey(flowID, Integer.parseInt(logNo));
				}
				httpServletRequest.setAttribute("swfLog", swfLog);
			}
		}
		String riskType = this.codeService.translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){//意健險設置被保險人序號
			this.setFamilyNo(httpServletRequest, prpLclaim);
		}
		//mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -start
		if (ConstantCodes.RISKCODE_DAZ.equals(prpLcompensate.getRiskCode())) {
			// 强制险 赔付代号
			httpServletRequest.setAttribute("payCodeList", ConstantsCollection.qzPayCodeList);
			// 肇责百分比
			httpServletRequest.setAttribute("indemnityDutyList", ConstantsCollection.indemnityDutyList);
			if("3".equals(prpLcompensate.getPayCode())){
				prpLcompensate.setPayCode("5");
			}
		} else if(ConstantCodes.RISKCODE_DAA.equals(prpLcompensate.getRiskCode())) {
			// 任意险 赔付代号
			httpServletRequest.setAttribute("payCodeList", ConstantsCollection.payCodeList);
			// 肇责百分比
			httpServletRequest.setAttribute("indemnityDutyList", ConstantsCollection.indemnityDutyList);
			if("3".equals(prpLcompensate.getPayCode())){
				prpLcompensate.setPayCode("5");
			}
		}
		//mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -end
		return prpLcompensate;
	}

	/**
	 * @param 追偿计算书打印
	 * @param pageNo
	 * @param recordPerPage
	 * @throws Exception
	 */
	public void replevyPrintQueryDtoToView(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
		// 得到页面参数
		String compensateNo = StringUtils.rightTrim(httpServletRequest.getParameter("compensateNo")); // 计算书号
		CompensateDto compensateDto = compensateService.findByPrimaryKey(compensateNo);
		if (compensateDto != null) {
			PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
			if (prpLcompensate != null) {
				if (!"R".equals(prpLcompensate.getCaseType())) {
					throw new UserException(1003, 98, "列印錯誤", "您輸入的不是追償計算書號！");
				}
				if ("R".equals(prpLcompensate.getCaseType())) {
					if (!"1".equals(prpLcompensate.getUnderWriteFlag())) {
						throw new UserException(1003, 98, "列印錯誤", "您想要打印的計算書尚未審核通過！");
					}
				}
			}
		} else {
			throw new UserException(1003, 98, "列印錯誤", "查不到所需要的信息，請確認您輸入的查詢條件准確無誤！");
		}
		PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
		String riskCode = prpLclaim.getRiskCode(); // 保单险种代码
		String strRiskName = this.codeService.translateRiskCode(riskCode, true);
		EndorseDto endorseDto = this.endorseService.findByConditions(prpLclaim.getPolicyNo());// 根据保单号得到批单对象
		List<PrpPhead> prpPheadDtoList = endorseDto.getPrpPheadList();
		String strEndorseNo = "";
		if (prpPheadDtoList != null && prpPheadDtoList.size() > 0) {
			PrpPhead prpPheadDto = endorseDto.getPrpPheadList().get(0);
			strEndorseNo = prpPheadDto.getEndorseNo();
		}
		StringBuffer strInsuredDate = new StringBuffer();
		DateTime startDate = new DateTime(prpLclaim.getStartDate());
		DateTime endDate = new DateTime(prpLclaim.getStartDate());
		strInsuredDate.append("自 " + startDate.getYear()).append("年" + startDate.getMonth()).append("月" + startDate.getDay()).append("日&nbsp;&nbsp;&nbsp;零&nbsp;&nbsp;&nbsp;时起").append("至 " + endDate.getYear()).append("年" + endDate.getMonth())
				.append("月" + endDate.getDay()).append("日 二十四 時止");
		PrpCmain prpCmain = this.getPolicyService().findPrpCmainDtoByPrimaryKey(prpLclaim.getPolicyNo());
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		double sumThisPaid = prpLcompensate.getSumThisPaid();
		double sumNoDutyFee = prpLcompensate.getSumNoDutyFee();
		if (sumThisPaid < 0) {
			sumThisPaid = 0 - sumThisPaid;
		}
		String cSumThisPaid = MoneyUtils.toChinese(sumThisPaid, prpLcompensate.getCurrency());
		if (sumNoDutyFee < 0) {
			sumNoDutyFee = 0 - sumNoDutyFee;
		}
		String cSumNoDutyFee = MoneyUtils.toChinese(sumNoDutyFee, prpLcompensate.getCurrency());
		String currencyName = this.codeService.translateCurrencyCode(prpCmain.getCurrency(), true);
		List<PrpLcharge> prpLChargeList = compensateDto.getPrpLchargeList();
		PrpLcharge prpLcharge = null;
		double checkFee = 0.0;
		double lawFee = 0.0;
		double dCheckFee = 0.0;
		double rewardFee = 0.0;
		double elseFee = 0.0;
		if (prpLChargeList != null && prpLChargeList.size() > 0) {
			for (int i = 0; i < prpLChargeList.size(); i++) {
				prpLcharge = prpLChargeList.get(i);
				if ("04".equals(prpLcharge.getChargeCode())) {// 查勘费
					checkFee += prpLcharge.getChargeAmount();
				} else if ("05".equals(prpLcharge.getChargeCode())) {// 诉讼费
					lawFee += prpLcharge.getChargeAmount();
				} else if ("06".equals(prpLcharge.getChargeCode())) {// 代查勘费
					dCheckFee += prpLcharge.getChargeAmount();
				} else if ("08".equals(prpLcharge.getChargeCode())) {// 奖励费
					rewardFee += prpLcharge.getChargeAmount();
				} else if ("99".equals(prpLcharge.getChargeCode())) {// 其他
					elseFee += prpLcharge.getChargeAmount();
				}
			}
		}
		httpServletRequest.setAttribute("checkFee", checkFee);
		httpServletRequest.setAttribute("lawFee", lawFee);
		httpServletRequest.setAttribute("dCheckFee", dCheckFee);
		httpServletRequest.setAttribute("rewardFee", rewardFee);
		httpServletRequest.setAttribute("elseFee", elseFee);

		httpServletRequest.setAttribute("sumThisPaid", sumThisPaid);
		httpServletRequest.setAttribute("sumNoDutyFee", sumNoDutyFee);

		httpServletRequest.setAttribute("cSumNoDutyFee", cSumNoDutyFee);
		httpServletRequest.setAttribute("cSumThisPaid", cSumThisPaid);
		httpServletRequest.setAttribute("prpCmainDto", prpCmain);
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		httpServletRequest.setAttribute("strRiskName", strRiskName);
		httpServletRequest.setAttribute("strEndorseNo", strEndorseNo);
		httpServletRequest.setAttribute("compensateDto", compensateDto);
		httpServletRequest.setAttribute("currencyName", currencyName);

		httpServletRequest.setAttribute("strInsuredDate", strInsuredDate.toString());

	}
	
	private void setSelectionList(HttpServletRequest httpServletRequest) throws Exception {
		httpServletRequest.setAttribute("compelPaySituationList", ConstantsCollection.compelPaySituationList);
		httpServletRequest.setAttribute("payObjectTypeList", ConstantsCollection.payObjectTypeList);
		httpServletRequest.setAttribute("compelPayTypeList", ConstantsCollection.compelPayTypeList);
		httpServletRequest.setAttribute("prpLpayObjectInfoCurrencyList", this.codeService.findPayCurrencyMap());
		httpServletRequest.setAttribute("LOCAL_CURRENCY", ConstantCodes.LOCAL_CURRENCY);
	}

	/**
	 * @param 追偿审核搜索结果
	 * @param pageNo
	 * @param recordPerPage
	 * @throws Exception
	 */
	public void replevyQueryForUndwrt(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
		String editType = httpServletRequest.getParameter("editType");
		// 得到页面参数
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		String underWriteFlag = httpServletRequest.getParameter("underWriteFlag");
		String claimNo = StringUtils.rightTrim(httpServletRequest.getParameter("ClaimNo")); // 赔案号
		String compensateNo = StringUtils.rightTrim(httpServletRequest.getParameter("CompensateNo")); // 计算书号
		String policyNo = StringUtils.rightTrim(httpServletRequest.getParameter("PolicyNo")); // 保单号
		String registNo = StringUtils.rightTrim(httpServletRequest.getParameter("RegistNo"));// 报案号
		// 得到页面选择查询情况 * or = 再组合SQL
		String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
		String compensateNoSign = httpServletRequest.getParameter("CompensateNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		StringBuffer conditions = new StringBuffer("");
		conditions.append("((s.flowID is null and c.compensateNo <> CONCAT(CONCAT('R',c.claimno),'00') ) or s.nodeStatus < 4 ) ");
		conditions.append(" and c.caseType = 'R' AND c.underWriteFlag = '"+underWriteFlag+"' ");
		conditions.append(" and (s.handlerCode='" + user.getUserCode() + "' or s.handlerCode ='" + SwfLog.HANDLERCODE_NONE + "' ) ");
		if (DataUtils.emptyToNull(claimNo)!=null) {
			conditions.append(StringConvert.convertString("c.claimNo", claimNo, claimNoSign));
		}
		if (DataUtils.emptyToNull(compensateNo)!=null) {
			conditions.append(StringConvert.convertString("c.compensateNo", compensateNo, compensateNoSign));
		}
		if (DataUtils.emptyToNull(policyNo)!=null) {
			conditions.append(StringConvert.convertString("c.policyNo", policyNo, policyNoSign));
		}
		if (DataUtils.emptyToNull(registNo)!=null) {
			conditions.append(" AND  c.claimNo in (select claimNo FROM PrpLclaim WHERE registNo = '" + registNo + "') ");
		}
		if("UNDWRT".equals(editType)){
			String  undwrtType = httpServletRequest.getParameter("UndwrtType");//審核類型
			if("0".equals(undwrtType)){//追償協商
				conditions.append(" and c.compensateNo = CONCAT(CONCAT('R',c.claimno),'00') ");
			}else if("1".equals(undwrtType)){//一般追償
				conditions.append(" and c.compensateNo <> CONCAT(CONCAT('R',c.claimno),'00') ");
			}
			conditions.append(" and s.nodeno in ( ");
			conditions.append(" select distinct decode(gradecode,'006',1,'009',1,'010',2,'011',3,'012',4,'013',5 ) ");
			conditions.append(" from UtiUserGrade where usercode= '" + user.getUserCode() + "'");
			conditions.append(" and comcode = '"+ user.getComCode() +"'");
			conditions.append(" and gradecode in ('006','009','010','011','012','013') ");
			conditions.append(" ) ");
		}
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions.append(powerService.addRiskPower(userDto, "c","claim") + new UIPowerInterface().addPower(userDto, "c", "", "comCode"));
		conditions.append(" ORDER BY s.flowInTime desc,c.inputDate DESC ");
		Page page = this.replevyService.findUndwrtByConditions(conditions.toString(), pageNo, recordPerPage);
		List<?> list = page.getResult();
		if(list!=null && !list.isEmpty()){
			ReplevyUndwrtDto undwrtDto = null;
			PrpDuser prpDuser = null;
			Iterator<?> it = list.iterator();
			while(it.hasNext()){
				undwrtDto = (ReplevyUndwrtDto) it.next();
				if(DataUtils.emptyToNull(undwrtDto.getOperatorName())==null){
					prpDuser = prpDuserService.findPrpDuser(undwrtDto.getOperatorCode());
					undwrtDto.setOperatorName(prpDuser!=null?prpDuser.getUserName():"");
				}
			}
		}
		httpServletRequest.setAttribute("prpLcompensateList", list);
		httpServletRequest.setAttribute("page", page);
	}
	//mantis：CLM0144，處理人員：DP0713，需求單編號：CLM0144，新核心-追償審核流程錯誤問題確認 START
	/***
	 * 根据赔案号码自动处理追偿登录。（核赔时“有追償”，审核通过时）
	 * @param claimNo
	 * @throws Exception 
	 */
	public CompensateDto autoReplevy(String claimNo) throws Exception{
		return autoReplevy(claimNo,null);
	}
	/***
	 * 根据赔案号码自动处理追偿登录。（核赔时“有追償”，审核通过时）
	 * @param claimNo
	 * @throws Exception 
	 */
	public CompensateDto autoReplevy(String claimNo,String kindCode) throws Exception{
		Map<String, Object> session = ActionContext.getContext().getSession();
		UserDto user = (UserDto)session.get("user");
		CompensateDto compensateDto = new CompensateDto();
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
		PrpLcompensate prpLcompensate = new PrpLcompensate();;
		prpLcompensate.setCompensateNo("R" + claimNo + "00");
		prpLcompensate.setLflag("0");
		prpLcompensate.setTimes(0);
		prpLcompensate.setInputDate(new Date());
		prpLcompensate.setUnderWriteFlag("0");// 默認初始狀態為
		prpLcompensate.setCaseType("R");
		prpLcompensate.setFinallyFlag("0");
		// 从立案填充计算书的部分讯息
		prpLcompensate.setClaimNo(prpLclaim.getClaimNo());
		prpLcompensate.setPolicyNo(prpLclaim.getPolicyNo());
		prpLcompensate.setCaseNo(prpLclaim.getCaseNo());// 结案号码
		prpLcompensate.setClassCode(prpLclaim.getClassCode());
		prpLcompensate.setRiskCode(prpLclaim.getRiskCode());
		prpLcompensate.setCurrency(prpLclaim.getCurrency());
		prpLcompensate.setMakeCom(prpLclaim.getMakeCom());
		prpLcompensate.setComCode(prpLclaim.getComCode());
		prpLcompensate.setHandler1Code(prpLclaim.getHandler1Code());
		prpLcompensate.setHandlerCode(prpLclaim.getHandler1Code());
		prpLcompensate.setIndemnityDuty(prpLclaim.getIndemnityDuty());
		prpLcompensate.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
		PrpCmain prpCmain = this.getPolicyService().findPrpCmainDtoByPrimaryKey(prpLcompensate.getPolicyNo());
		if (prpCmain != null) {
			prpLcompensate.setCheckAgentCode(prpCmain.getAppliCode());
			prpLcompensate.setCheckAgentName(prpCmain.getAppliName());
		}
		prpLcompensate.setCounterClaimerName(prpLclaim.getInsuredName());
		// 追償時效默認為出險后兩年內,强制险：赔付后2年，水险：1年，员工诚实责任保险保险：15年。
		String riskCode = prpLclaim.getRiskCode();
		String riskType = codeService.translateRiskCodetoRiskType(riskCode);
		/***  add by 中科軟 20150601 需求變更-095 begin ***/
		if (ConstantCodes.CLASSCODE_D.equals(riskType)) {
			if(ConstantCodes.RISKCODE_DAZ.equals(riskCode)){
				prpLcompensate.setAccidentType(prpLclaim.getPropAccidentType());
			} else {
				prpLcompensate.setAccidentType(prpLclaim.getCarAccidentType());//車體險肇責類型
				prpLcompensate.setPropAccidentType(prpLclaim.getPropAccidentType());//責任險肇責類型
			}
		}
		/***  add by 中科軟 20150601 需求變更-095 end ***/
		Date preserveDate = null;
		if(ConstantCodes.CLASSCODE_Y.equals(riskType)||ConstantCodes.RISKCODE_DAZ.equals(riskCode)||ConstantCodes.RISKCODE_FD.equals(riskCode)){
			String conditions = "compensateNo =(select MIN(compensateNo) from prpLcompensate where claimno ='"+claimNo+"' and  compensateno like 'C%' and (UnderWriteFlag = '1' or UnderWriteFlag = '3'))";
			List<PrpLcompensate>prpLcompensateList = prpLcompensateService.findByConditions(conditions);
			if(prpLcompensateList.size()>0){
				preserveDate = prpLcompensateList.get(0).getUnderWriteEndDate();
			}
			if(preserveDate!=null){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(preserveDate);
				if(ConstantCodes.RISKCODE_DAZ.equals(riskCode)){
					calendar.add(Calendar.YEAR, 2);
				}else if(ConstantCodes.CLASSCODE_Y.equals(riskType)){
					calendar.add(Calendar.YEAR, 1);
				}else{
					calendar.add(Calendar.YEAR, 15);
				}
				preserveDate = calendar.getTime();
			}
		}
		if(preserveDate==null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(prpLclaim.getDamageStartDate());
			calendar.add(Calendar.YEAR, 2);
			preserveDate = calendar.getTime();
		}
		prpLcompensate.setPreserveDate(preserveDate);
		
		prpLcompensate.setPayCodeType("1");// 默認賠付代號為1.一般賠案
		prpLcompensate.setPaySituation("3");// 追償金已追償完畢結案;
		prpLcompensate.setUnderWriteFlag("0");// 初始計算書狀態
		// 保險期間
		prpLcompensate.setIsPayForOther("0");// 联共保功能暂不开放，暂时写死
		prpLcompensate.setCurrency(prpLclaim.getCurrency());
		prpLcompensate.setSurveyorName(prpLclaim.getInsuredName());
		prpLcompensate.setOperatorCode(user.getUserCode());
		prpLcompensate.setOperatorName(user.getUserName());
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		List<PrpLloss> replevyLlossList = this.compensateService.getPrpLlossForReplevy(prpLcompensate.getClaimNo());
		if (replevyLlossList != null && !replevyLlossList.isEmpty()) {
			PrpLloss prpLloss = null;
			double calSumLoss = 0d;
			int i = 1;
			log.info("CLM0144 autoReplevy.replevyLlossList.size() "+replevyLlossList.size());
			for(PrpLloss tempPrpLloss : replevyLlossList){
				if(null!=kindCode){//CLM0144 只寫入指定kindCode的prpLloss
					log.info("CLM0144 autoReplevy. prpLcompensate.getCompensateNo():"+prpLcompensate.getCompensateNo()+"/kindCode:"+kindCode+" eq tempPrpLloss.getKindCode()"+tempPrpLloss.getKindCode()+"="+(kindCode.equals(tempPrpLloss.getKindCode())));
					if(kindCode.equals(tempPrpLloss.getKindCode())){
						prpLloss = new PrpLloss();
						prpLloss.getId().setSerialNo(i++);
						prpLloss.getId().setCompensateNo(prpLcompensate.getCompensateNo());
						prpLloss.setPolicyNo(prpLcompensate.getPolicyNo());
						prpLloss.setCurrency(prpLcompensate.getCurrency());
						prpLloss.setExchRate(prpLcompensate.getExchangeRate());
						prpLloss.setCurrency1(prpLcompensate.getCurrency());
						prpLloss.setCurrency2(prpLcompensate.getCurrency());
						prpLloss.setCurrency3(prpLcompensate.getCurrency());
						prpLloss.setCurrency4(prpLcompensate.getCurrency());
						prpLloss.setRiskCode(tempPrpLloss.getRiskCode());
						prpLloss.setKindCode(tempPrpLloss.getKindCode());
						prpLloss.setSumLoss(tempPrpLloss.getSumLoss());
						prpLlossList.add(prpLloss);

						calSumLoss +=prpLloss.getSumLoss();
					}
				}else{//原路線
					prpLloss = new PrpLloss();
					prpLloss.getId().setSerialNo(i++);
					prpLloss.getId().setCompensateNo(prpLcompensate.getCompensateNo());
					prpLloss.setPolicyNo(prpLcompensate.getPolicyNo());
					prpLloss.setCurrency(prpLcompensate.getCurrency());
					prpLloss.setExchRate(prpLcompensate.getExchangeRate());
					prpLloss.setCurrency1(prpLcompensate.getCurrency());
					prpLloss.setCurrency2(prpLcompensate.getCurrency());
					prpLloss.setCurrency3(prpLcompensate.getCurrency());
					prpLloss.setCurrency4(prpLcompensate.getCurrency());
					prpLloss.setRiskCode(tempPrpLloss.getRiskCode());
					prpLloss.setKindCode(tempPrpLloss.getKindCode());
					prpLloss.setSumLoss(tempPrpLloss.getSumLoss());
					prpLlossList.add(prpLloss);
					
					calSumLoss +=prpLloss.getSumLoss();
				}
			}
			log.info("CLM0144 autoReplevy.setSumLoss ="+calSumLoss);
			prpLcompensate.setSumLoss(calSumLoss);
		}
		compensateDto.setPrpLcompensate(prpLcompensate);
		compensateDto.setPrpLlossList(prpLlossList);
		String condition = " policyNo = '" + prpLclaim.getPolicyNo() + "' and insuredflag = '1' and insuredCode = '" + prpLclaim.getInsuredCode() + "' order by serialNo asc ";
		List<PrpCinsured> insuredList = this.getPrpCinsuredService().findPrpCinsured(QueryRule.getInstance().addSql(condition));
		if(insuredList!=null && !insuredList.isEmpty()){
			List<PrpLpayObjectInfo> prpLpayObjectInfoList = new ArrayList<PrpLpayObjectInfo>();
			PrpCinsured prpCinsured = insuredList.get(0);
			PrpLpayObjectInfo prpLpayObjectInfo = new PrpLpayObjectInfo();
			prpLpayObjectInfo.getId().setCompensateNo(prpLcompensate.getCompensateNo());
			prpLpayObjectInfo.getId().setSerialNo(1);
			prpLpayObjectInfo.getId().setCertiType(PrpLpayObjectInfo.CERTITYPE_PAYOBJECT);
			prpLpayObjectInfo.setRiskCode(prpLcompensate.getRiskCode());
			prpLpayObjectInfo.setCertificateCode(prpCinsured.getIdentifytype());
			prpLpayObjectInfo.setOwnerShip("B");//默认汇款
			prpLpayObjectInfo.setOwnerName(prpCinsured.getInsuredName());
			prpLpayObjectInfo.setUniformNo(prpCinsured.getIdentifyNumber());
			prpLpayObjectInfo.setCutBack("1");
			prpLpayObjectInfoList.add(prpLpayObjectInfo);
			compensateDto.setPrpLpayObjectInfoList(prpLpayObjectInfoList);
		}
		return compensateDto;
	}
	//mantis：CLM0144，處理人員：DP0713，需求單編號：CLM0144，新核心-追償審核流程錯誤問題確認 END
	
	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrplreplevyhistoryService getPrpLreplevyhistoryService() {
		return prpLreplevyhistoryService;
	}

	public void setPrpLreplevyhistoryService(PrplreplevyhistoryService prpLreplevyhistoryService) {
		this.prpLreplevyhistoryService = prpLreplevyhistoryService;
	}

	public PrpLreplevyService getPrpLreplevyService() {
		return prpLreplevyService;
	}

	public void setPrpLreplevyService(PrpLreplevyService prpLreplevyService) {
		this.prpLreplevyService = prpLreplevyService;
	}

	public EndcaseService getEndcaseService() {
		return endcaseService;
	}

	public void setEndcaseService(EndcaseService endcaseService) {
		this.endcaseService = endcaseService;
	}

	public PrpLcfeecoinsService getPrpLcfeecoinsService() {
		return prpLcfeecoinsService;
	}

	public void setPrpLcfeecoinsService(PrpLcfeecoinsService prpLcfeecoinsService) {
		this.prpLcfeecoinsService = prpLcfeecoinsService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public PrpLctextService getPrpLctextService() {
		return prpLctextService;
	}

	public void setPrpLctextService(PrpLctextService prpLctextService) {
		this.prpLctextService = prpLctextService;
	}

	public ReplevyService getReplevyService() {
		return replevyService;
	}

	public void setReplevyService(ReplevyService replevyService) {
		this.replevyService = replevyService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}

	public void setPrpLpayObjectInfoService(PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
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
	
	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}
	
	public PrpCitemCarService getPrpCitemCarService() {
		return prpCitemCarService;
	}

	public void setPrpCitemCarService(PrpCitemCarService prpCitemCarService) {
		this.prpCitemCarService = prpCitemCarService;
	}

	public UtiUserGradeService getUtiUserGradeService() {
		return utiUserGradeService;
	}

	public void setUtiUserGradeService(UtiUserGradeService utiUserGradeService) {
		this.utiUserGradeService = utiUserGradeService;
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}

	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
	}
	
	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	@Override
	public EndcaseDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		return null;
	}

	@Override
	public void dtoToView(HttpServletRequest httpServletRequest, EndcaseDto endcaseDto) throws Exception {
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PowerService getPowerService() {
		return powerService;
	}

	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}
	
}
