package com.sinosoft.claim.compensate.util;

import ins.framework.common.ServiceFactory;
import ins.framework.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
import com.sinosoft.app.common.util.StringUtil;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 * <p>
 * Title: CompensateViewHelper
 * </p>
 * <p>
 * Description:实赔ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 */

public abstract class CompensateViewHelper {
	/** 理算文字信息每行能显示的最大字符长度 */
	public int RULE_LENGTH = 70; // rule字段的长度
	/** 文字说明类型 */
	public static final String PAY_TEXT = "4";
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 使用的日期格式yyyy-MM-dd */
	public static SimpleDateFormat formatter10 = new SimpleDateFormat("yyyy-MM-dd");
	private CodeService codeService ; 

	/**
	 * 取初始化信息需要的数据的整理. 填写实赔单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public abstract CompensateDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception;

	/**
	 * 填写实赔页面及查询实赔request的生成.
	 * 填写实赔时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param compensateDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public abstract void dtoToView(HttpServletRequest httpServletRequest, CompensateDto compensateDto) throws Exception;

	/**
	 * 保存实赔时实赔页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param request
	 * @return compensateDto 实赔数据传输数据结构
	 * @throws Exception
	 */
	public CompensateDto viewToDto(HttpServletRequest request) throws Exception {
		String riskCode = request.getParameter("prpLcompensateRiskCode");
		String configRiskCode = this.getCodeService().translateRiskCodetoRiskType(riskCode);
		CompensateDto compensateDto = new CompensateDto();
		/*---------------------实赔主表prpLcompensate------------------------------------*/
		String caseType = request.getParameter("prpLcompensateCaseType");
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
//		prpLcompensate.setSharingFlag("0");
//		if (request.getParameter("prpLcompensateSharingFlag") != null) {
//			prpLcompensate.setSharingFlag(DataUtils.nullToEmpty(request.getParameter("prpLcompensateSharingFlag")));// 同業共摊
//		}

		if (request.getParameter("prpLcompensateDamageStartDate") != null) {
			prpLcompensate.setDamageStartDate(new DateTime(request.getParameter("prpLcompensateDamageStartDate")));
		}
		if (request.getParameter("DamageStartHour") != null) {
			prpLcompensate.setDamageStartHour((String) request.getParameter("DamageStartHour"));
		}
		if (request.getParameter("prpLcompensateIsCompulsoryBchainClaim") != null) {
			prpLcompensate.setIsCompulsoryBchainClaim(DataUtils.nullToEmpty(request.getParameter("prpLcompensateIsCompulsoryBchainClaim")));// 是否為強制險區塊鏈攤賠案件 
		}
		//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
		prpLcompensate.setCompensateNo((String) request.getAttribute("compensateNo"));
		prpLcompensate.setLflag((String) request.getParameter("prpLcompensateLFlag"));
		prpLcompensate.setLflag((String) request.getParameter("LFlag"));
		prpLcompensate.setCaseNo((String) request.getParameter("prpLcompensateCaseNo"));
		prpLcompensate.setTimes(Integer.parseInt(request.getParameter("prpLcompensateTimes")));
		prpLcompensate.setClassCode((String) request.getParameter("prpLcompensateClassCode"));
		prpLcompensate.setRiskCode((String) request.getParameter("prpLcompensateRiskCode"));
		prpLcompensate.setClaimNo((String) request.getParameter("prpLcompensateClaimNo"));
		prpLcompensate.setPolicyNo((String) request.getParameter("prpLcompensatePolicyNo"));
		prpLcompensate.setDeductCond((String) request.getParameter("prpLcompensateDeductCond"));
		prpLcompensate.setPreserveDate(new DateTime(request.getParameter("prpLcompensatePreserveDate")));
		prpLcompensate.setCheckAgentCode((String) request.getParameter("prpLcompensateCheckagentcode"));
		prpLcompensate.setCheckAgentName((String) request.getParameter("prpLcompensateCheckagentname"));
		prpLcompensate.setSurveyorName((String) request.getParameter("prpLcompensateSurveyorname"));
		prpLcompensate.setCounterClaimerName((String) request.getParameter("prpLcompensateCounterclaimername"));
		prpLcompensate.setDutyDescription((String) request.getParameter("prpLcompensateDutyDescription"));
		prpLcompensate.setCurrency((String) request.getParameter("prpLcompensateCurrency"));
		prpLcompensate.setSumLoss(Double.parseDouble(request.getParameter("prpLcompensateSumLoss")));
		prpLcompensate.setSumRest(Double.parseDouble(request.getParameter("prpLcompensateSumRest")));
		prpLcompensate.setSumDutyPaid(Double.parseDouble(request.getParameter("prpLcompensateSumDutyPaid")));
		prpLcompensate.setSumNoDutyFee(Double.parseDouble(request.getParameter("prpLcompensateSumNoDutyFee")));
		prpLcompensate.setSumPaid(Double.parseDouble(request.getParameter("prpLcompensateSumPaid")));
		prpLcompensate.setSumPrePaid(Double.parseDouble(request.getParameter("prpLcompensateSumPrePaid")));
		prpLcompensate.setSumThisPaid(Double.parseDouble(request.getParameter("prpLcompensateSumThisPaid")));
		prpLcompensate.setReceiverName((String) request.getParameter("prpLcompensateReceiverName"));
		prpLcompensate.setBank((String) request.getParameter("prpLcompensateSumSelfValue"));
		prpLcompensate.setAccount((String) request.getParameter("prpLcompensateAccount"));
		prpLcompensate.setMakeCom((String) request.getParameter("prpLcompensateMakeCom"));
		prpLcompensate.setComCode((String) request.getParameter("prpLcompensateComCode"));
		prpLcompensate.setHandlerCode((String) request.getParameter("prpLcompensateHandlerCode"));
		prpLcompensate.setHandler1Code((String) request.getParameter("prpLcompensateHandler1Code"));
		prpLcompensate.setApproverCode((String) request.getParameter("prpLcompensateApproverCode"));
		prpLcompensate.setUnderWriteCode((String) request.getParameter("prpLcompensateUnderWriteCode"));
		prpLcompensate.setUnderWriteName((String) request.getParameter("prpLcompensateUnderWriteName"));
		prpLcompensate.setStatisticsYM(new DateTime(request.getParameter("prpLcompensateStatisticsYM")));
		prpLcompensate.setOperatorCode((String) request.getParameter("prpLcompensateOperatorCode"));
		prpLcompensate.setInputDate(new DateTime(request.getParameter("prpLcompensateInputDate"),DateTime.YEAR_TO_SECOND));
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
		String hospDays = request.getParameter("prpLcompensateHospitalizedDays");
		prpLcompensate.setHospitalizedDays(StringUtil.isBlank(hospDays)?null:Integer.parseInt(hospDays));
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
		String underWriteEndDate = request.getParameter("prpLcompensateUnderWriteEndDate");
		if(CommonUtils.isEmpty(underWriteEndDate)){
			prpLcompensate.setUnderWriteEndDate(new DateTime(underWriteEndDate));
		}
		prpLcompensate.setUnderWriteFlag((String) request.getParameter("prpLcompensateUnderWriteFlag"));
		prpLcompensate.setRemark((String) request.getParameter("prpLcompensateRemark"));
		prpLcompensate.setFlag((String) request.getParameter("prpLcompensateFlag"));
		prpLcompensate.setLicenseNo((String) request.getParameter("prpLcompensateLicenseNo"));
		// 是否是团单免导标志
		prpLcompensate.setTermFlag((String) request.getParameter("termFlag"));
		// 是否代付赔款
		prpLcompensate.setIsPayForOther(request.getParameter("isPayForOther"));
		prpLcompensate.setIndemnityDutyRate(Double.parseDouble(request.getParameter("prpLcompensateIndemnityDutyRate")));
		prpLcompensate.setIndemnityDuty((String) request.getParameter("indemnityDuty"));
		prpLcompensate.setCaseType(caseType);
		prpLcompensate.setFinallyFlag((String) request.getParameter("prpLcompensateFinallyFlag"));
		//互冲计算书号码
		prpLcompensate.setMutualCompensateNo(request.getParameter("prpLcompensateMutualCompensateNo"));
		// 获取理赔结论
		String prpLcompensateResult = request.getParameter("result");
		prpLcompensate.setResult(prpLcompensateResult);
		// 向理算Dto中添加被保险人姓名
		prpLcompensate.setInsuredName(request.getParameter("prpLcompensateInsuredName"));
		// 向理算Dto中添加被保险人联系电话
		prpLcompensate.setInsuredPhoneNumber(request.getParameter("prpLcheckPhoneNumber"));
		// 向理算Dto中添加互碰自赔标志
		prpLcompensate.setPayselfFlag(request.getParameter("payselfFlag"));
		// 向理算Dto中添加出险原因
		prpLcompensate.setDamageName(request.getParameter("prpLcompensateDamageName"));
		prpLcompensate.setDamageCode(request.getParameter("prpLcompensateDamageCode"));
		// 向理算Dto中添加报单保额
		prpLcompensate.setSumAmount(Double.parseDouble(request.getParameter("prpLcompensateSumAmount")));
		// 客制化需求 赔款支付对象存入 PrpLpayObjectInfo 2013-05-06
		// // 赔款支付对象进行保存
		// 如果支付对象不是被保险人需录入例外事项原因
		if ("1".equals(request.getParameter("ifInsuredName"))) {
			prpLcompensate.setExceptions(request.getParameter("exceptions"));// 例外事项原因
			if ("9".equals(request.getParameter("exceptions"))) {// 例外事项原因选择其他时需录入原因描述
				prpLcompensate.setReason(request.getParameter("reason"));// 例外事项原因描述
			} else {
				prpLcompensate.setReason("");
			}
		} else {
			prpLcompensate.setExceptions("");
			prpLcompensate.setReason("");
		}
		// 对赔款支付对象进行保存 end
		// 加到ArrayList中
		/** add by chenjie 2013-04-27 客制化需求start prpLcompensateAccountType */
		/** 賠付代號 1一次赔付结案\2免赔结案\3部分赔付\4最後一次赔付\5代位求偿/残余物处理摊回\6已付赔款调整 */
		prpLcompensate.setPayCode(request.getParameter("prpLcompensatePayCode"));
		/** 全損/分損代號 1全损\2分损 */
		prpLcompensate.setLossType(request.getParameter("prpLcompensateLossType"));
		/** 肇事類型 肇事类型：1:有肇责，计次\2:无肇责，不计次\3:有肇责，不计次 */
		prpLcompensate.setAccidentType(DataUtils.dbNullToEmpty(request.getParameter("prpLcompensateAccidentType")));
		/** 對方車肇事責任百分比 */
		prpLcompensate.setOppositeIndemnityDuty(Double.valueOf(DataUtils.nullToZero(request.getParameter("prpLcompensateOppositeIndemnityDuty"))));
		/** 其他肇事責任百分比 */
		prpLcompensate.setOtherIndemnityDuty(Double.valueOf(DataUtils.nullToZero(request.getParameter("prpLcompensateOtherIndemnityDuty"))));
		/** 獨立處理費用 */
		prpLcompensate.setIndependentCosts(Double.valueOf(DataUtils.nullToZero(request.getParameter("prpLcompensateIndependentCosts"))));
		/** 是否有残余物 1：是；0：否 */
		String prpLcompensateRemnants = request.getParameter("prpLcompensateRemnants");
		if(prpLcompensateRemnants!=null){
			prpLcompensate.setRemnants(prpLcompensateRemnants);
		}else{
			prpLcompensate.setRemnants("0");
		}
		/** 健保局追償狀況 1本赔案无健保追偿情形\2本赔案尚待健保追偿\3健保全数付清\4本次健保追偿为分次追偿 */
		prpLcompensate.setChasingLossesStatus(request.getParameter("prpLcompensateChasingLossesStatus"));
		/**
		 * 給付追償情況：1賠款已全數賠付給所有受害人結案、2本次賠款為分次賠付給受害人、3追償金已追償完畢結案、4本次追償為分次追償、5免賠結案、6
		 * 放棄追償
		 */
		prpLcompensate.setPaySituation(request.getParameter("prpLcompensatePaySituation"));
		prpLcompensate.setSpeedFlag(request.getParameter("prpLcompensateSpeedFlag"));
		prpLcompensate.setSubrogation(request.getParameter("prpLcompensateSubrogation"));
		prpLcompensate.setOtherClaimNo(request.getParameter("prpLcompensateOtherClaimNo"));
		prpLcompensate.setOtherPolicyNo(request.getParameter("prpLcompensateOtherPolicyNo"));
		//增加 理算文件備齊日 车险，伤害险
		prpLcompensate.setFileReadyDate(request.getParameter("prpLcompensateFileReadyDate"));
		//意健险增加 零結賠案不計次
		if(ConstantCodes.CLASSCODE_E.equals(configRiskCode)){
			//增加 零結賠案不計次,  '0':'否','1':'是'
			prpLcompensate.setNoPaidClaim(request.getParameter("prpLcompensateNoPaidClaim"));
		} else if("Y".equals(configRiskCode)){//水险
			prpLcompensate.setShipCName(DataUtils.dbNullToEmpty(request.getParameter("prpLcompensateShipCName")));//船名
			prpLcompensate.setShipModel(DataUtils.dbNullToEmpty(request.getParameter("prpLcompensateShipModel")));//機型
			String nationalityCode = DataUtils.dbNullToEmpty(request.getParameter("prpLcompensateNationalityCode"));//國籍編號
			if(nationalityCode.length()>10){
				nationalityCode = nationalityCode.substring(0, 10);
			}
			prpLcompensate.setNationalityCode(nationalityCode);
			prpLcompensate.setClaimAgent(DataUtils.dbNullToEmpty(request.getParameter("prpLcompensateClaimAgent")));//理賠代理
			prpLcompensate.setStartSiteCountry(DataUtils.dbNullToEmpty(request.getParameter("prpLcompensateStartSiteCountry")));//航程 始发
			prpLcompensate.setStartSitePort(DataUtils.dbNullToEmpty(request.getParameter("prpLcompensateStartSitePort")));//航程 始发
			prpLcompensate.setEndSiteCountry(DataUtils.dbNullToEmpty(request.getParameter("prpLcompensateEndSiteCountry")));//航程 终达
			prpLcompensate.setEndSitePort(DataUtils.dbNullToEmpty(request.getParameter("prpLcompensateEndSitePort")));//航程 终达
		}
		prpLcompensate.setInformReinsFlag(DataUtils.dbNullToEmpty(request.getParameter("prpLcompensateInformReinsFlag")));//再保摊赔发送通知
		prpLcompensate.setContextNo(DataUtils.dbNullToEmpty(request.getParameter("prpLcompensateContextNo")));//理算说明内容
		prpLcompensate.setSpeedFlag(request.getParameter("prpLcompensateSpeedFlag"));
		prpLcompensate.setCoinsFlag(request.getParameter("prpLcompensateCoinsFlag"));
		
		prpLcompensate.setPropAccidentType(DataUtils.dbNullToEmpty(request.getParameter("prpLcompensatePropAccidentType")));// add by 中科軟 20150601 需求變更-095 
		
		/**客制化需求 */
		compensateDto.setPrpLcompensate(prpLcompensate);

		List<PrpLctext> prpLctextList = new ArrayList<PrpLctext>();
		String TextTemp = request.getParameter("prpLctextContextInnerHTML");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH,"GBK");
		// 得到连接串,下面将其切分到数组
		PrpLctext prpLctext = null;
		for (int k = 0; k < rules.length; k++) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo((String) request.getAttribute("compensateNo"));
			prpLctext.setContext(rules[k]);
			prpLctext.getId().setLineNo(k + 1);
			prpLctext.getId().setTextType("1");
			prpLctextList.add(prpLctext);
		}
		TextTemp = request.getParameter("prpLctextContextPayTextInnerHTML");
		rules = StringUtils.split(TextTemp, RULE_LENGTH,"GBK");
		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules.length; k++) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo((String) request.getAttribute("compensateNo"));
			prpLctext.setContext(rules[k]);
			prpLctext.getId().setLineNo(k + 1);
			prpLctext.getId().setTextType(PAY_TEXT);
			prpLctextList.add(prpLctext);
		}
		// 如果是意外健康险的赔款计算过程的话，目前增加了一个text-type=5的过程
		TextTemp = request.getParameter("prpLctextContextAccientTextInnerHTML");
		rules = StringUtils.split(TextTemp, RULE_LENGTH,"GBK");
		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules.length; k++) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo((String) request.getAttribute("compensateNo"));
			prpLctext.setContext(rules[k]);
			prpLctext.getId().setLineNo(k + 1);
			prpLctext.getId().setTextType("5");
			prpLctextList.add(prpLctext);
		}
		compensateDto.setPrpLctextList(prpLctextList);
		/*---------------------状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		if (!"".equals(DataUtils.dbNullToEmpty(caseType))) {
			if (caseType.trim().equals("3") || caseType.trim().equals("4") || caseType.trim().equals("6")) {
				prpLclaimStatus.setTypeFlag(caseType);
				prpLclaimStatus.getId().setNodeType("speci");
				prpLclaimStatus.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(caseType)));
			} else {
				prpLclaimStatus.getId().setNodeType("compe");
				prpLclaimStatus.getId().setSerialNo(0);
				prpLclaimStatus.setTypeFlag("2");
			}
		} else {
			prpLclaimStatus.getId().setNodeType("compe");
			prpLclaimStatus.getId().setSerialNo(0);
			prpLclaimStatus.setTypeFlag("2");
		}

		prpLclaimStatus.setStatus(request.getParameter("buttonSaveType"));
		prpLclaimStatus.getId().setBusinessNo(prpLcompensate.getCompensateNo());
		prpLclaimStatus.setPolicyNo(prpLcompensate.getPolicyNo());
		prpLclaimStatus.setRiskCode(request.getParameter("prpLcompensateRiskCode"));
		// 取得当前用户信息，写操作员信息到实赔中
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(prpLcompensate.getInputDate());
		prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		compensateDto.setPrpLclaimStatus(prpLclaimStatus);
		PrpLclaim prpLclaim = null;
		// 客户需求，在理算环节可以添加修改巨灾代码,並保留原有代码逻辑
		String prpLcompensateClaimNo = request.getParameter("prpLcompensateClaimNo");
		if (DataUtils.emptyToNull(prpLcompensateClaimNo) != null) {
			prpLclaim = this.getPrpLclaimService().findPrpLclaim(prpLcompensateClaimNo);
			if (prpLclaim != null) {
				String strCatastropheCode1 = request.getParameter("prpCatastropheCode1");
				String strCatastropheName1 = request.getParameter("prpCatastropheName1");
				String strCatastropheCode2 = request.getParameter("prpCatastropheCode2");
				String strCatastropheName2 = request.getParameter("prpCatastropheName2");
				// 对是否涉及担保进行更新
				String strFGuaranteeFlag = request.getParameter("guaranteeFlag");
				if (!"".equals(DataUtils.dbNullToEmpty(strFGuaranteeFlag))) {
					prpLclaim.setGuaranteeFlag(strFGuaranteeFlag);
				}
				// 对是否涉及诉讼、追偿进行更新
				String strReferLawFlag = request.getParameter("referLawFlag");
				if (!"".equals(DataUtils.dbNullToEmpty(strReferLawFlag))) {
					prpLclaim.setReferLawFlag(strReferLawFlag);
				}
				String strReplevyFlag = request.getParameter("replevyFlag");
				if (!"".equals(DataUtils.dbNullToEmpty(strReplevyFlag))) {
					prpLclaim.setReplevyFlag(strReplevyFlag);
				}
				String replevyRemark = request.getParameter("prpLcompensateReplevyRemark");
				prpLclaim.setReplevyRemark(replevyRemark);
				prpLclaim.setCatastropheCode1(strCatastropheCode1);
				prpLclaim.setCatastropheName1(strCatastropheName1);
				prpLclaim.setCatastropheCode2(strCatastropheCode2);
				prpLclaim.setCatastropheName2(strCatastropheName2);
				// 对接收客户索赔申请时间 进行更新
				String strStartApplyPayDate = request.getParameter("startApplyPayDate");
				if (!"".equals(DataUtils.dbNullToEmpty(strStartApplyPayDate))) {
					prpLclaim.setStartApplyPayDate(new DateTime(strStartApplyPayDate, DateTime.YEAR_TO_DAY));
				}
				String prpLcompensateDamageCode = request.getParameter("prpLcompensateDamageCode");
				String prpLcompensateDamageName = request.getParameter("prpLcompensateDamageName");
				if(DataUtils.emptyToNull(DataUtils.dbNullToEmpty(prpLcompensateDamageCode))!=null
						&& DataUtils.emptyToNull(DataUtils.dbNullToEmpty(prpLcompensateDamageName))!=null){
					prpLclaim.setDamageCode(DataUtils.dbNullToEmpty(prpLcompensateDamageCode).trim());
					prpLclaim.setDamageName(prpLcompensateDamageName);
				}
			}
		}
		compensateDto.setPrpLclaim(prpLclaim);
		return compensateDto;
	}

	/**
	 * 保存简易赔案实赔时实赔页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return compensateDto 实赔数据传输数据结构
	 * @throws Exception
	 */
	public CompensateDto quickCaseViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		CompensateDto compensateDto = new CompensateDto();
		/*---------------------实赔主表prpLcompensate------------------------------------*/

		PrpLcompensate prpLcompensate = new PrpLcompensate();
		prpLcompensate.setCompensateNo((String) httpServletRequest.getAttribute("compensateNo"));
		prpLcompensate.setLflag((String) httpServletRequest.getParameter("prpLcompensateLFlag"));
		prpLcompensate.setLflag("L");
		prpLcompensate.setCaseNo((String) httpServletRequest.getParameter("prpLcompensateCaseNo"));
		prpLcompensate.setTimes(1);
		prpLcompensate.setRegistNo((String) httpServletRequest.getParameter("registNo"));
		String claimNo = (String) httpServletRequest.getAttribute("prpLclaimNo");
		PrpLclaim prpLclaimClassCode = getPrpLclaimService().findPrpLclaim(claimNo);
		prpLcompensate.setClassCode(prpLclaimClassCode.getClassCode());
		prpLcompensate.setRiskCode(httpServletRequest.getParameter("riskCode"));
		prpLcompensate.setClaimNo(claimNo);
		prpLcompensate.setPolicyNo((String) httpServletRequest.getParameter("policyNo"));
		prpLcompensate.setDeductCond("");
		prpLcompensate.setPreserveDate(new DateTime(httpServletRequest.getParameter("prpLcompensatePreserveDate")));
		prpLcompensate.setCheckAgentCode((String) httpServletRequest.getParameter("prpLcompensateCheckagentcode"));
		prpLcompensate.setCheckAgentName((String) httpServletRequest.getParameter("prpLcompensateCheckagentname"));
		prpLcompensate.setSurveyorName((String) httpServletRequest.getParameter("prpLcompensateSurveyorname"));
		prpLcompensate.setCounterClaimerName((String) httpServletRequest.getParameter("prpLcompensateCounterclaimername"));
		prpLcompensate.setDutyDescription((String) httpServletRequest.getParameter("prpLcompensateDutyDescription"));
		prpLcompensate.setCurrency(ConstantCodes.LOCAL_CURRENCY);
		prpLcompensate.setSumLoss(Double.parseDouble(httpServletRequest.getParameter("prpLcompensateSumLoss")));
		prpLcompensate.setSumRest(0.0);
		prpLcompensate.setSumDutyPaid(Double.parseDouble(httpServletRequest.getParameter("prpLcompensateSumDutyPaid")));
		prpLcompensate.setSumNoDutyFee(Double.parseDouble(httpServletRequest.getParameter("prpLcompensateSumNoDutyFee")));
		prpLcompensate.setSumPaid(Double.parseDouble(httpServletRequest.getParameter("prpLcompensateSumPaid")));
		prpLcompensate.setSumPrePaid(0.0);
		prpLcompensate.setSumThisPaid(Double.parseDouble(httpServletRequest.getParameter("prpLcompensateSumDutyPaid")));
		prpLcompensate.setReceiverName((String) httpServletRequest.getParameter("prpLcompensateReceiverName"));
		prpLcompensate.setBank((String) httpServletRequest.getParameter("prpLcompensateSumSelfValue"));
		prpLcompensate.setAccount((String) httpServletRequest.getParameter("prpLcompensateAccount"));
		prpLcompensate.setMakeCom(httpServletRequest.getParameter("prpLcompensateMakeCom"));
		prpLcompensate.setComCode(httpServletRequest.getParameter("prpLcompensateComCode"));
		prpLcompensate.setHandlerCode(httpServletRequest.getParameter("prpLcompensateHandlerCode"));
		prpLcompensate.setHandler1Code((String) httpServletRequest.getParameter("prpLcompensateHandler1Code"));
		prpLcompensate.setApproverCode((String) httpServletRequest.getParameter("prpLcompensateApproverCode"));
		prpLcompensate.setUnderWriteCode((String) httpServletRequest.getParameter("prpLcompensateUnderWriteCode"));
		prpLcompensate.setUnderWriteName((String) httpServletRequest.getParameter("prpLcompensateUnderWriteName"));
		prpLcompensate.setStatisticsYM(new DateTime(httpServletRequest.getParameter("prpLcompensateStatisticsYM")));
		prpLcompensate.setOperatorCode(httpServletRequest.getParameter("prpLcompensateOperatorCode"));
		prpLcompensate.setInputDate(new DateTime(new Date(),DateTime.YEAR_TO_SECOND));
		prpLcompensate.setUnderWriteEndDate(new DateTime(httpServletRequest.getParameter("prpLcompensateUnderWriteEndDate")));
		prpLcompensate.setUnderWriteFlag((String) httpServletRequest.getParameter("prpLcompensateUnderWriteFlag"));
		prpLcompensate.setRemark((String) httpServletRequest.getParameter("prpLcompensateRemark"));
		prpLcompensate.setFlag((String) httpServletRequest.getParameter("prpLcompensateFlag"));
		// 对赔款支付对象进行保存
		prpLcompensate.setAccountCode(httpServletRequest.getParameter("prpLCompensateAccountCode"));
		prpLcompensate.setBankCode(httpServletRequest.getParameter("prpLCompensateBankCode"));
		prpLcompensate.setBankName(httpServletRequest.getParameter("prpLCompensateBankName"));
		prpLcompensate.setCustomBankCode(httpServletRequest.getParameter("prpLCompensateCustomBankCode"));
		prpLcompensate.setCustomBankName(httpServletRequest.getParameter("prpLCompensateCustomBankName"));
		prpLcompensate.setCertifiCateCode(httpServletRequest.getParameter("prpLCompensateCertificateCode"));
		prpLcompensate.setOwnerName(httpServletRequest.getParameter("prpLCompensateOwnerName"));
		prpLcompensate.setOwnerPhoneNo(httpServletRequest.getParameter("prpLCompensateOwnerPhoneNo"));
		prpLcompensate.setAccountCurrency(httpServletRequest.getParameter("prpLCompensateAccountCurrency"));
		prpLcompensate.setOwnership(httpServletRequest.getParameter("prpLCompensateOwnership"));
		prpLcompensate.setAccountType(httpServletRequest.getParameter("prpLCompensateAccountType"));
		prpLcompensate.setIndemnityDutyRate(Double.parseDouble(httpServletRequest.getParameter("prpLcompensateIndemnityDutyRate")));
		prpLcompensate.setIndemnityDuty((String) httpServletRequest.getParameter("indemnityDuty"));
		prpLcompensate.setCaseType("C");// 简易赔案只走实赔流程
		prpLcompensate.setFinallyFlag("0");
		String[] quickcaseLicenseNo = httpServletRequest.getParameterValues("checkPrpLthirdPartyDtoLicenseNo");
		String[] quickcaseInsureCarFlag = httpServletRequest.getParameterValues("checkPrpLthirdPartyDtoInsureCarFlag");
		for (int i = 0; i < quickcaseLicenseNo.length; i++) {
			if (quickcaseInsureCarFlag[i].equals("1")) {
				prpLcompensate.setLicenseNo(quickcaseLicenseNo[i]);
				break;
			}
		}
		// 获取理赔结论
		String prpLcompensateResult = httpServletRequest.getParameter("result");
		prpLcompensate.setResult(prpLcompensateResult);
		// 向理算Dto中添加被保险人姓名
		prpLcompensate.setInsuredName(httpServletRequest.getParameter("prpLcompensateInsuredName"));
		// 向理算Dto中添加被保险人联系电话
		prpLcompensate.setInsuredPhoneNumber(httpServletRequest.getParameter("prpLcheckPhoneNumber"));
		// 向理算Dto中添加出险原因
		prpLcompensate.setDamageName(httpServletRequest.getParameter("prpLcompensateDamageName"));
		// 向理算Dto中添加报单保额
		prpLcompensate.setSumAmount(Double.parseDouble(httpServletRequest.getParameter("prpLcompensateSumAmount")));
		// 向理算Dto中添加报单保额
		// 加到ArrayList中
		compensateDto.setPrpLcompensate(prpLcompensate);
		/*---------------------文本表PrpLctextDto------------------------------------*/
		List<PrpLctext> prpLctextList = new ArrayList<PrpLctext>();
		String TextTemp = httpServletRequest.getParameter("prpLctextContextInnerHTML");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH,"GBK");
		// 得到连接串,下面将其切分到数组
		PrpLctext prpLctext = null;
		for (int k = 0; k < rules.length; k++) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo((String) httpServletRequest.getAttribute("compensateNo"));
			prpLctext.setContext(rules[k]);
			prpLctext.getId().setLineNo(k + 1);
			prpLctext.getId().setTextType("1");
			prpLctextList.add(prpLctext);
		}
		// compensateDto
		compensateDto.setPrpLctextList(prpLctextList);
		/*---------------------状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.getId().setNodeType("compe");
		prpLclaimStatus.getId().setSerialNo(0);
		prpLclaimStatus.setTypeFlag("2");

		prpLclaimStatus.setStatus(httpServletRequest.getParameter("buttonSaveType"));
		prpLclaimStatus.getId().setBusinessNo(prpLcompensate.getCompensateNo());
		prpLclaimStatus.setPolicyNo(prpLcompensate.getPolicyNo());
		prpLclaimStatus.setRiskCode(httpServletRequest.getParameter("prpLcompensateRiskCode"));
		// 取得当前用户信息，写操作员信息到实赔中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(prpLcompensate.getInputDate());
		prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		compensateDto.setPrpLclaimStatus(prpLclaimStatus);
		PrpLclaim prpLclaim = null;
		Object buttonStatus = httpServletRequest.getParameter("buttonSaveType");
		if (buttonStatus != null && buttonStatus.toString().trim().equals("4")) {
			prpLclaim = new PrpLclaim();
			prpLclaim.setClaimNo(httpServletRequest.getParameter("prpLprepayClaimNo"));
			prpLclaim.setSumPaid(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLprepaySumPrePaid"))));
		}
		compensateDto.setPrpLclaim(prpLclaim);
		return compensateDto;
	}

	/**
	 * 保存简易赔案实赔时实赔页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return compensateDto 实赔数据传输数据结构
	 * @throws Exception
	 */
	public CompensateDto compelViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		CompensateDto compensateDto = new CompensateDto();
		/*---------------------实赔主表prpLcompensate------------------------------------*/
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		prpLcompensate.setCompensateNo((String) httpServletRequest.getAttribute("compelCompensateNo"));
		prpLcompensate.setLflag((String) httpServletRequest.getParameter("prpLcompensateLFlag"));
		prpLcompensate.setLflag("L");
		prpLcompensate.setCaseNo((String) httpServletRequest.getParameter("prpLcompensateCaseNo"));
		prpLcompensate.setTimes(1);
		String claimNo = (String) httpServletRequest.getAttribute("compelPrpLclaimNo");
		PrpLclaim prpLclaimClassCode = getPrpLclaimService().findPrpLclaim(claimNo);
		prpLcompensate.setClassCode(prpLclaimClassCode.getClassCode());
		prpLcompensate.setRiskCode(httpServletRequest.getParameter("compelRiskCode"));
		prpLcompensate.setClaimNo(claimNo);
		prpLcompensate.setPolicyNo((String) httpServletRequest.getParameter("prpLRegistRPolicyNo"));
		prpLcompensate.setDeductCond("");
		prpLcompensate.setPreserveDate(new DateTime(httpServletRequest.getParameter("prpLcompensatePreserveDate")));
		prpLcompensate.setCheckAgentCode((String) httpServletRequest.getParameter("prpLcompensateCheckagentcode"));
		prpLcompensate.setCheckAgentName((String) httpServletRequest.getParameter("prpLcompensateCheckagentname"));
		prpLcompensate.setSurveyorName((String) httpServletRequest.getParameter("prpLcompensateSurveyorname"));
		prpLcompensate.setCounterClaimerName((String) httpServletRequest.getParameter("prpLcompensateCounterclaimername"));
		prpLcompensate.setDutyDescription((String) httpServletRequest.getParameter("prpLcompensateDutyDescription"));
		prpLcompensate.setCurrency(ConstantCodes.LOCAL_CURRENCY);
		prpLcompensate.setSumLoss(Double.parseDouble(httpServletRequest.getParameter("prpLcompensateSumLoss")));
		prpLcompensate.setSumRest(Double.parseDouble(httpServletRequest.getParameter("prpLcompensateSumRest")));
		prpLcompensate.setSumDutyPaid(Double.parseDouble(httpServletRequest.getParameter("compelPrpLcompensateSumDutyPaid")));
		prpLcompensate.setSumNoDutyFee(Double.parseDouble(httpServletRequest.getParameter("compelPrpLcompensateSumNoDutyFee")));
		prpLcompensate.setSumPaid(Double.parseDouble(httpServletRequest.getParameter("compelPrpLcompensateSumPaid")));
		prpLcompensate.setSumThisPaid(Double.parseDouble(httpServletRequest.getParameter("compelPrpLcompensateSumDutyPaid")));
		prpLcompensate.setReceiverName((String) httpServletRequest.getParameter("prpLcompensateReceiverName"));
		prpLcompensate.setBank((String) httpServletRequest.getParameter("prpLcompensateSumSelfValue"));
		prpLcompensate.setAccount((String) httpServletRequest.getParameter("prpLcompensateAccount"));
		prpLcompensate.setMakeCom((String) httpServletRequest.getParameter("prpLcompensateMakeCom"));
		prpLcompensate.setComCode((String) httpServletRequest.getParameter("prpLcompensateComCode"));
		prpLcompensate.setHandlerCode(httpServletRequest.getParameter("prpLcompensateHandlerCode"));
		prpLcompensate.setHandler1Code((String) httpServletRequest.getParameter("prpLcompensateHandler1Code"));
		prpLcompensate.setApproverCode((String) httpServletRequest.getParameter("prpLcompensateApproverCode"));
		prpLcompensate.setUnderWriteCode((String) httpServletRequest.getParameter("prpLcompensateHandlerCode"));
		prpLcompensate.setUnderWriteName((String) httpServletRequest.getParameter("prpLcompensateUnderWriteName"));
		prpLcompensate.setStatisticsYM(new DateTime(httpServletRequest.getParameter("prpLcompensateStatisticsYM")));
		prpLcompensate.setOperatorCode((String) httpServletRequest.getParameter("prpLcompensateOperatorCode"));
		prpLcompensate.setInputDate(new DateTime(new Date(),DateTime.YEAR_TO_SECOND));
		prpLcompensate.setUnderWriteEndDate(new DateTime(httpServletRequest.getParameter("prpLcompensateUnderWriteEndDate")));
		prpLcompensate.setUnderWriteFlag((String) httpServletRequest.getParameter("prpLcompensateUnderWriteFlag"));
		prpLcompensate.setRemark((String) httpServletRequest.getParameter("prpLcompensateRemark"));
		prpLcompensate.setFlag((String) httpServletRequest.getParameter("prpLcompensateFlag"));
		prpLcompensate.setIndemnityDutyRate(Double.parseDouble(httpServletRequest.getParameter("prpLcompensateIndemnityDutyRate")));
		prpLcompensate.setIndemnityDuty((String) httpServletRequest.getParameter("indemnityDuty"));
		prpLcompensate.setCaseType("C");// 简易赔案只走实赔流程

		prpLcompensate.setFinallyFlag("0");
		// 获取理赔结论
		String prpLcompensateResult = httpServletRequest.getParameter("result");
		prpLcompensate.setResult(prpLcompensateResult);
		// 向理算Dto中添加被保险人姓名
		prpLcompensate.setInsuredName(httpServletRequest.getParameter("prpLcompensateInsuredName"));
		// 向理算Dto中添加被保险人联系电话
		prpLcompensate.setInsuredPhoneNumber(httpServletRequest.getParameter("prpLcheckPhoneNumber"));
		// 向理算Dto中添加出险原因
		prpLcompensate.setDamageName(httpServletRequest.getParameter("prpLcompensateDamageName"));
		String licenseNo = "";
		String[] prpLchecklicenseNo = httpServletRequest.getParameterValues("checkPrpLthirdPartyDtoLicenseNo");
		String[] insureCarFlag = httpServletRequest.getParameterValues("checkPrpLthirdPartyDtoInsureCarFlag");
		for (int i = 0; i < prpLchecklicenseNo.length; i++) {
			if ("1".equals(insureCarFlag[i])) {
				licenseNo = prpLchecklicenseNo[i];
				break;
			}
		}
		prpLcompensate.setLicenseNo(licenseNo);
		// 向理算Dto中添加报单保额
		// 加到ArrayList中
		compensateDto.setPrpLcompensate(prpLcompensate);
		/*---------------------文本表PrpLctextDto------------------------------------*/
		List<PrpLctext> prpLctextList = new ArrayList<PrpLctext>();
		String TextTemp = httpServletRequest.getParameter("compelPrpLctextContextInnerHTML");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH,"GBK");
		// 得到连接串,下面将其切分到数组
		PrpLctext prpLctext = null;
		for (int k = 0; k < rules.length; k++) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo((String) httpServletRequest.getAttribute("compelCompensateNo"));
			prpLctext.setContext(rules[k]);
			prpLctext.getId().setLineNo(k + 1);
			prpLctext.getId().setTextType("1");
			prpLctextList.add(prpLctext);
		}
		compensateDto.setPrpLctextList(prpLctextList);
		/*---------------------状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.getId().setNodeType("compe");
		prpLclaimStatus.getId().setSerialNo(0);
		prpLclaimStatus.setTypeFlag("2");

		prpLclaimStatus.setStatus(httpServletRequest.getParameter("buttonSaveType"));
		prpLclaimStatus.getId().setBusinessNo(prpLcompensate.getCompensateNo());
		prpLclaimStatus.setPolicyNo(prpLcompensate.getPolicyNo());
		prpLclaimStatus.setRiskCode(httpServletRequest.getParameter("prpLcompensateRiskCode"));
		// 取得当前用户信息，写操作员信息到实赔中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(prpLcompensate.getInputDate());
		prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		compensateDto.setPrpLclaimStatus(prpLclaimStatus);
		PrpLclaim prpLclaim = null;
		Object buttonStatus = httpServletRequest.getParameter("buttonSaveType");
		if (buttonStatus != null && buttonStatus.toString().trim().equals("4")) {
			prpLclaim = new PrpLclaim();
			prpLclaim.setClaimNo(httpServletRequest.getParameter("prpLprepayClaimNo"));
			prpLclaim.setSumPaid(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLprepaySumPrePaid"))));
		}
		compensateDto.setPrpLclaim(prpLclaim);
		return compensateDto;
	}

	public PrpLclaimService getPrpLclaimService() {
		if (prpLclaimService == null) {
			return (PrpLclaimService) ServiceFactory.getService("prpLclaimService");
		}
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	
}
