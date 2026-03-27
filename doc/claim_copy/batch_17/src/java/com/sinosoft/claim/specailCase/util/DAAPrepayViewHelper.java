/*
 * @(#)DAAPrepayViewHelper.java	Mar 4, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.util;

import ins.framework.common.QueryRule;
import ins.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.compensate.service.facade.PrepayService;
import com.sinosoft.claim.compensate.vo.PrepayDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimFee;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLdoc;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLptext;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.service.facade.PrpLclaimLossService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class DAAPrepayViewHelper extends PrepayViewHelper {
	/** 预赔服务 */
	private PrepayService prepayService;
	/** 立案估损信息服务 */
	private PrpLclaimLossService prpLclaimLossService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 报案viewHelper */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 预赔登记信息服务 */
	private PrpLprepayService prpLprepayService;
	/** 赔款计算书信息服务 */
	private PrpLcompensateService prpLcompensateService;
	/** 理赔状态节点信息服务 */
	private PrpLclaimStatusService prpLclaimStatusService;
	/** 报案信息服务 */
	private PrpLregistService prpLregistService;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 批单viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 代码服务 */
	private CodeService codeService;

	/**
	 * 默认构造方法
	 */
	public DAAPrepayViewHelper() {
	}

	/**
	 * 填写预赔页面及查询立案request的生成.
	 * 填写预赔时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void prepayDtoToView(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		// 查询信息
		PrepayDto prepayDto = prepayService.findByPrimaryKey(claimNo);
		PrpLclaimLoss prpLclaimLoss = prpLclaimLossService.getClaimLoss(prepayDto.getPrpLprepay().getClaimNo());
		httpServletRequest.setAttribute("sumClaim", String.valueOf(prpLclaimLoss.getSumClaim()));
		// 预赔主信息
		PrpLprepay prpLprepay = prepayDto.getPrpLprepay();
		// 预赔计算书号PreCompensateNo
		prpLprepay.setPreCompensateNo(prepayDto.getPrpLprepay().getPreCompensateNo());
		// 赔案号码
		prpLprepay.setClaimNo(prepayDto.getPrpLprepay().getClaimNo());
		// 险种
		prpLprepay.setRiskCode(prepayDto.getPrpLprepay().getRiskCode());
		// 保单号码
		prpLprepay.setPolicyNo(prepayDto.getPrpLprepay().getPolicyNo());
		// 币别代码
		prpLprepay.setCurrency(prepayDto.getPrpLprepay().getCurrency());
		/*
		 * //逾期欠款期数arrearageTimes //逾期欠款金额sumArrearage
		 * //已预（垫）付金额sumBeforePrePaid //本次垫付逾期欠款期数blockUpTimes
		 * //预赔金额sumPrePaid(编辑项目) //总预（垫）付金额sumTotalPrepaid //复核员代码approverCode
		 * //最终核赔人代码underWriteCode //最终核赔人名称underWriteName
		 * //核赔完成日期underWriteEndDate //核赔标志underWriteFlag
		 */
		prpLprepay.setUnderWriteFlag(prepayDto.getPrpLprepay().getUnderWriteFlag());
		prpLprepay.setStartDate(prepayDto.getPrpLprepay().getStartDate());
		prpLprepay.setEndDate(prepayDto.getPrpLprepay().getEndDate());
		prpLprepay.setSumPrePaid(prepayDto.getPrpLprepay().getSumPrePaid());
		// 出单机构
		prpLprepay.setMakeCom(prepayDto.getPrpLprepay().getMakeCom());
		// 业务归属机构代码
		prpLprepay.setComCode(prepayDto.getPrpLprepay().getComCode());
		// 经办人代码
		prpLprepay.setHandlerCode(prepayDto.getPrpLprepay().getHandlerCode());
		// 归属业务员代码
		prpLprepay.setHandler1Code(prepayDto.getPrpLprepay().getHandler1Code());
		// 统计年月
		prpLprepay.setStatisticsYM(prepayDto.getPrpLprepay().getStatisticsYM());
		// 操作员代码
		prpLprepay.setOperatorCode(prepayDto.getPrpLprepay().getOperatorCode());
		// 计算机输入日期
		prpLprepay.setInputDate(prepayDto.getPrpLprepay().getInputDate());
		// 标志字段flag
		prpLprepay.setFlag(prepayDto.getPrpLprepay().getFlag());
		prpLprepay.setStartDate(prepayDto.getPrpLprepay().getStartDate());
		prpLprepay.setEndDate(prepayDto.getPrpLprepay().getEndDate());
		prpLprepay.setSumAmount(prepayDto.getPrpLprepay().getSumAmount());
		prpLprepay.setSumPremium(prepayDto.getPrpLprepay().getSumPremium());

		ClaimDto claimDtoTemp = claimService.findByPrimaryKey(prepayDto.getPrpLprepay().getClaimNo());

		prpLprepay.setDamageStartDate(prepayDto.getPrpLprepay().getDamageStartDate());
		String timeTemp = StringConvert.toStandardTime(claimDtoTemp.getPrpLclaim().getDamageStartHour());
		prpLprepay.setDamageStartHour(timeTemp.substring(0, 2));
		prpLprepay.setDamageStartMinute(timeTemp.substring(3, 5));

		prpLprepay.setDamageAddressType(prepayDto.getPrpLprepay().getDamageAddressType());
		prpLprepay.setDamageAddress(prepayDto.getPrpLprepay().getDamageAddress());
		prpLprepay.setSumClaim(prepayDto.getPrpLprepay().getSumClaim());
		// 加入险类的复制
		prpLprepay.setClassCode(claimDtoTemp.getPrpLclaim().getClassCode());
		// 报案号
		prpLprepay.setRegistNo(claimDtoTemp.getPrpLclaim().getRegistNo());

		// 设置预赔操作的状态为 案件修改 (正处理任务)
		if (prepayDto.getPrpLclaimStatus() == null) {
			prpLprepay.setStatus("4");
		} else {
			if (prepayDto.getPrpLclaimStatus().getStatus().equals("7"))
				prepayDto.getPrpLclaimStatus().setStatus("3");
			prpLprepay.setStatus(prepayDto.getPrpLclaimStatus().getStatus());
		}

		String status = httpServletRequest.getParameter("status");// 从工作流上去状态.以工作流上的为准确
		if (status != null && !status.equals(""))
			prpLprepay.setStatus(status);

		if (!prepayDto.getPrpLprepay().getPolicyNo().equals("")) {
			// 查询保单信息
			PrpLclaim prpLclaim = claimDtoTemp.getPrpLclaim();
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
			if (!CommonUtils.isEmpty(prpCitemCarList)) {
				// 对车型等信息的支持
				PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
				prpLprepay.setClauseType(prpCitemCar.getClauseType());
				prpLprepay.setLicenseNo(prpCitemCar.getLicenseNo());
				prpLprepay.setLicenseColorCode(prpCitemCar.getLicenseColorCode());
				prpLprepay.setLicenseColor(prpCitemCar.getLicenseColorCode());
				prpLprepay.setBrandName(prpCitemCar.getBrandName());
				prpLprepay.setCarKindCode(prpCitemCar.getCarKindCode());
				prpLprepay.setEngineNo(prpCitemCar.getEngineNo());
				prpLprepay.setFrameNo(prpCitemCar.getFrameNo());
			}
		}
		// add by liubvo 20040601
		if (!prepayDto.getPrpLprepay().getClaimNo().equals("")) {
			// 查询保单信息
			ClaimDto claimDto = claimService.findByPrimaryKey(prepayDto.getPrpLprepay().getClaimNo());
			PrpLclaim prpLclaim = new PrpLclaim();
			prpLclaim = claimDto.getPrpLclaim();
			prpLprepay.setDamageStartDate(new DateTime(prpLclaim.getDamageStartDate()));
			String timeTemp1 = StringConvert.toStandardTime(claimDto.getPrpLclaim().getDamageStartHour());
			prpLprepay.setDamageStartHour(timeTemp1.substring(0, 2));
			prpLprepay.setDamageStartMinute(timeTemp1.substring(3, 5));
			// 加入险类的复制
			prpLprepay.setClassCode(prpLclaim.getClassCode());
			prpLprepay.setStartDate(new DateTime(prpLclaim.getStartDate()));
			prpLprepay.setEndDate(new DateTime(prpLclaim.getEndDate()));
			prpLprepay.setDamageAddress(prpLclaim.getDamageAddress());
			prpLprepay.setSumClaim(prpLclaim.getSumClaim());
			prpLprepay.setSumAmount(prpLclaim.getSumAmount());
		}
		// 给预赔文件多行列表准备数据
		PrpLptext prpLptext = new PrpLptext();
		String tempContext = "";
		if (prepayDto.getPrpLptextList() != null) {
			for (int i = 0; i < prepayDto.getPrpLptextList().size(); i++) {
				PrpLptext prpLptextTemp = (PrpLptext) prepayDto.getPrpLptextList().get(i);
				tempContext = tempContext + prpLptextTemp.getContext();
			}
		}
		prpLptext.setContext(tempContext);
		httpServletRequest.setAttribute("prpLptext", prpLptext);

		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLprepay);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, prpLprepay);
		// 查询相同保单号的出险次数
		daaRegistViewHelper.getSamePolicyRegistInfo(httpServletRequest, prpLprepay.getPolicyNo(), claimDtoTemp.getPrpLclaim().getRegistNo());

		// 设置主立案信息内容到窗体表单
		httpServletRequest.setAttribute("prpLprepay", prpLprepay);
		// 设置各个子表信息项到窗体表单
		setSubInfo(httpServletRequest, new ClaimDto());
		// 判断保费是否已经实收
		String conditions1 = " policyno = '" + prpLprepay.getPolicyNo() + "' and plandate < '" + new DateTime(new Date(), DateTime.YEAR_TO_DAY) + "' ";
		int intReturn = 0;
		intReturn = this.policyService.checkPay(conditions1);// -1为未缴费，0为未缴全，1为缴全
		if (intReturn != 1) {
			httpServletRequest.setAttribute("prePayFlag", "0");// 0表示不允许预赔
		} else {
			httpServletRequest.setAttribute("prePayFlag", "1");// 1表示允许预赔
		}
		// 判断保费是否已经实收
	}

	/**
	 * 保存预赔时预赔页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return registDto 预赔数据传输数据结构
	 * @throws Exception
	 */
	public PrepayDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		// 继承对regist,registText表的赋值
		PrepayDto prepayDto = super.viewToDto(httpServletRequest);
		return prepayDto;
	}

	/**
	 * 根据预赔号和保单号和赔案号查询预赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param prepayNo 预赔号
	 * @param policyNo 保单号
	 * @param claimNo 赔案号
	 * @throws Exception
	 */

	public void setPrpLprepayDtoToView(HttpServletRequest httpServletRequest, String prepayNo, String policyNo, String claimNo) throws Exception {
		// 根据输入的保单号，预赔号生成SQL where 子句
		prepayNo = StringUtils.rightTrim(prepayNo);
		policyNo = StringUtils.rightTrim(policyNo);
		claimNo = StringUtils.rightTrim(claimNo);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addLike("preCompensateNo", "%" + prepayNo + "%").addLike("policyNo", "%" + policyNo + "%").addLike("claimNo", "%" + claimNo + "%");
		List<PrpLprepay> prepayList = prpLprepayService.findPrpLprepay(queryRule);
		PrpLprepay prpLprepay = new PrpLprepay();
		prpLprepay.setClaimList(prepayList);
		prpLprepay.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLprepay", prpLprepay);
	}

	/**
	 * 填写预赔页面及查询预赔request的生成.
	 * 填写预赔时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void claimDtoToView(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		// 查询立案信息
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		// 判断是否已出计算书
		String msg = "";
		List<PrpLcompensate> compensateList = prpLcompensateService.findByClaimNo(claimNo);
		if (compensateList.size() > 0) {
			msg = "此賠案已出計算書，不能再進行預賠！";
			throw new UserException(1, 3, "特殊賠案", msg);
		}
		// 取得估损金额的合计
		PrpLclaimLoss prpLclaimLoss = prpLclaimLossService.getClaimLoss(claimNo);
		httpServletRequest.setAttribute("sumClaim", String.valueOf(prpLclaimLoss.getSumClaim()));

		// 预赔主信息
		PrpLprepay prpLprepay = new PrpLprepay();
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		// 预赔计算书号PreCompensateNo
		prpLprepay.setPreCompensateNo("  ");
		// 赔案号码
		prpLprepay.setClaimNo(prpLclaim.getClaimNo());
		// 险种
		prpLprepay.setRiskCode(prpLclaim.getRiskCode());
		// 保单号码
		prpLprepay.setPolicyNo(prpLclaim.getPolicyNo());
		// 币别代码
		prpLprepay.setCurrency(prpLclaim.getCurrency());
		// 报案号
		prpLprepay.setRegistNo(prpLclaim.getRegistNo());

		prpLprepay.setArrearageTimes(0d);
		prpLprepay.setSumArrearage(0d);
		prpLprepay.setSumBeforePrePaid(0d);
		prpLprepay.setBlockUpTimes(0d);
		prpLprepay.setSumTotalPrepaid(0);
		prpLprepay.setUnderWriteCode("");
		prpLprepay.setUnderWriteName("");
		prpLprepay.setUnderWriteEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		/*
		 * //逾期欠款期数arrearageTimes //逾期欠款金额sumArrearage
		 * //已预（垫）付金额sumBeforePrePaid //本次垫付逾期欠款期数blockUpTimes
		 * //预赔金额sumPrePaid(编辑项目) //总预（垫）付金额sumTotalPrepaid //复核员代码approverCode
		 * //最终核赔人代码underWriteCode //最终核赔人名称underWriteName
		 * //核赔完成日期underWriteEndDate //核赔标志underWriteFlag
		 */
		// **************判断保费是否已经实收
		String conditions1 = " policyno = '" + prpLprepay.getPolicyNo() + "' and plandate < '" + new DateTime(new Date(), DateTime.YEAR_TO_DAY) + "' ";
		int intReturn = 0;
		intReturn = this.policyService.checkPay(conditions1);// -1为未缴费，0为未缴全，1为缴全
		String configValue = "2";
		if (configValue.equals("2") && intReturn != 1) {
			httpServletRequest.setAttribute("prePayFlag", "0");// 0表示不允许预赔
		} else {
			httpServletRequest.setAttribute("prePayFlag", "1");// 0表示允许预赔
		}
		prpLprepay.setSumPrePaid(0);
		// 取得当前用户信息，写操作员信息到预赔中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");

		// 出单机构
		prpLprepay.setMakeCom(prpLclaim.getMakeCom());
		// 业务归属机构代码
		prpLprepay.setComCode(prpLclaim.getComCode());
		// 经办人代码
		prpLprepay.setHandlerCode(prpLclaim.getHandlerCode());
		// 归属业务员代码
		prpLprepay.setHandler1Code(user.getUserCode());
		// 统计年月
		prpLprepay.setStatisticsYM(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		// 操作员代码

		prpLprepay.setOperatorCode(user.getUserCode());
		// 计算机输入日期
		prpLprepay.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		// 标志字段flag
		prpLprepay.setFlag(prpLclaim.getFlag());
		prpLprepay.setStartDate(new DateTime(prpLclaim.getStartDate().toString(), DateTime.YEAR_TO_DAY));
		prpLprepay.setEndDate(new DateTime(prpLclaim.getEndDate().toString(), DateTime.YEAR_TO_DAY));
		prpLprepay.setSumAmount(prpLclaim.getSumAmount());
		prpLprepay.setSumPremium(prpLclaim.getSumPremium());
		prpLprepay.setDamageStartDate(new DateTime(prpLclaim.getDamageStartDate().toString(), DateTime.YEAR_TO_DAY));
		String timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());
		prpLprepay.setDamageStartHour(timeTemp.substring(0, 2));
		prpLprepay.setDamageStartMinute(timeTemp.substring(3, 5));
		prpLprepay.setDamageAddressType(prpLclaim.getDamageAddressType());
		prpLprepay.setDamageAddress(prpLclaim.getDamageAddress());
		prpLprepay.setSumClaim(prpLclaim.getSumClaim());
		// reason:需要组织ClassCode这个数据，目的就是写入wflog的ClassCode字段，
		// 因为一个非全险别的预付赔款的核赔员找案子时需要这个字段
		prpLprepay.setClassCode(prpLclaim.getClassCode());
		// modify wangliguang 20080522 end

		// 设置预赔操作的状态为 新案件登记 (未处理任务)
		prpLprepay.setStatus("1");
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		if (!CommonUtils.isEmpty(policyNo)) {
			// 查询保单信息
//			PolicyDto policyDto = this.endorseViewHelper.findForEndorBefore(prpLclaim.getPolicyNo(), new DateTime(prpLclaim.getDamageStartDate()).toString(), prpLclaim.getDamageStartHour());
			List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
			if (!CommonUtils.isEmpty(prpCitemCarList)) {
				// 对车型等信息的支持
				PrpCitemCar prpCitemCarDto = prpCitemCarList.get(0);
				prpLprepay.setClauseType(prpCitemCarDto.getClauseType());
				prpLprepay.setLicenseNo(prpCitemCarDto.getLicenseNo());
				prpLprepay.setLicenseColorCode(prpCitemCarDto.getLicenseColorCode());
				prpLprepay.setLicenseColor(prpCitemCarDto.getLicenseColorCode());
				prpLprepay.setBrandName(prpCitemCarDto.getBrandName());
				prpLprepay.setCarKindCode(prpCitemCarDto.getCarKindCode());
				prpLprepay.setEngineNo(prpCitemCarDto.getEngineNo());
				prpLprepay.setFrameNo(prpCitemCarDto.getFrameNo());
			}
		}

		PrpLptext prpLptext = new PrpLptext();
		// 设值文本的内容
		httpServletRequest.setAttribute("prpLptext", prpLptext);

		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLprepay);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, prpLprepay);
		// 查询相同保单号的出险次数
		daaRegistViewHelper.getSamePolicyRegistInfo(httpServletRequest, prpLprepay.getPolicyNo(), prpLclaim.getRegistNo());

		// 设置主立案信息内容到窗体表单
		httpServletRequest.setAttribute("prpLprepay", prpLprepay);
		// 设置各个子表信息项到窗体表单
		setSubInfo(httpServletRequest, claimDto);
	}

	/**
	 * 根据PrpPrepayDto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param prpPrepayDto 立案的数据类
	 * @throws Exception
	 */
	private void setSubInfo(HttpServletRequest httpServletRequest, ClaimDto claimDto) throws Exception {

		// [涉案车辆]给三者车辆多行列表准备数据
		PrpLthirdParty prpLthirdParty = new PrpLthirdParty();
		List<PrpLthirdParty> arrayList = claimDto.getPrpLthirdPartyList();
		prpLthirdParty.setThirdPartyList(arrayList);

		httpServletRequest.setAttribute("prpLthirdParty", prpLthirdParty);

		// [驾驶员]给驾驶员多行多行列表准备数据
		PrpLdriver prpLdriver = new PrpLdriver();
		List<PrpLdriver> arrayListDriver = claimDto.getPrpLdriverList();
		prpLdriver.setDriverList(arrayListDriver);
		httpServletRequest.setAttribute("prpLdriver", prpLdriver);
		/*
		 * //[查勘信息]给预赔文件多行列表准备数据
		 */
		// [估损金额]给估损金额文件多行列表准备数据
		PrpLclaimFee prpLclaimFee = new PrpLclaimFee();
		httpServletRequest.setAttribute("prpLclaimFee", prpLclaimFee);
		// [单证信息]给索赔单证文件多行列表准备数据
		PrpLdoc prpLdoc = new PrpLdoc();
		List<PrpLdoc> docList = claimDto.getPrpLdocList();
		prpLdoc.setDocList(docList);
		httpServletRequest.setAttribute("prpLdoc", prpLdoc);
	}

	/**
	 * 根据PrpPrepayDto中的已经设置的代码内容，对代码进行名称转换
	 * @param httpServletRequest 返回给页面的request
	 * @param prpPrepayDto 立案的数据类
	 * @param PrepayDto 查询出的数据类
	 * @throws Exception
	 */
	private void changeCodeToName(HttpServletRequest httpServletRequest, PrpLprepay prpLprepay) throws Exception {
		// (1)条款名称的转换
		String clauseType = prpLprepay.getClauseType();
		String clauseName = this.codeService.translateCodeCode("ClauseType", clauseType, true);
		prpLprepay.setClauseName(clauseName);
		// (2)号牌颜色转换
		String licenseColorCodeCode = prpLprepay.getLicenseColorCode();
		String licenseColor = this.codeService.translateCodeCode("LicenseColor", licenseColorCodeCode, true);
		prpLprepay.setLicenseColor(licenseColor);
		// (3)车辆类型转换
		String carKindCode = prpLprepay.getCarKindCode();
		String carKind = this.codeService.translateCodeCode("CarKind", carKindCode, true);
		prpLprepay.setCarKind(carKind);
		// (4)对业务归属结构进行转换
		String comCode = prpLprepay.getComCode();
		String comName = this.codeService.translateComCode(comCode, true);
		prpLprepay.setComName(comName);
		// (5)对归属业务员进行转换
		String handler1Code = prpLprepay.getHandler1Code();
		String handler1Name = this.codeService.translateUserCode(handler1Code, true);
		prpLprepay.setHandler1Name(handler1Name);
		// (6)对代理人进行转换
		String agentCode = prpLprepay.getAgentCode();
		String agentName = this.codeService.translateUserCode(agentCode, true);
		prpLprepay.setAgentName(agentName);
		// (7)对经办人进行转换
		String handlerCode = prpLprepay.getHandlerCode();
		String handlerName = this.codeService.translateUserCode(handlerCode, true);
		prpLprepay.setHandlerName(handlerName);
		// 对币别进行转换
		String currency = prpLprepay.getCurrency();
		String currencyName = this.codeService.translateCurrencyCode(currency, true);
		prpLprepay.setCurrencyName(currencyName);
	}

	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prpPrepayDto 立案的数据类
	 * @throws Exception
	 */
	private void setSelectionList(HttpServletRequest httpServletRequest, PrpLprepay prpLprepay) throws Exception {
		// (1)得到立案类型列表
		List<?> reportTypes = this.codeService.getCodeType("ReportType", prpLprepay.getRiskCode());
		httpServletRequest.setAttribute("reportTypes", reportTypes);
		// (2)得到案件种类列表列表
		List<?> claimTypes = this.codeService.getCodeType("CaseCode", prpLprepay.getRiskCode());
		httpServletRequest.setAttribute("claimTypes", claimTypes);
		// (3)得到出险地址类型列表
		List<?> damageAddressTypes = this.codeService.getCodeType("DamageAddress", prpLprepay.getRiskCode());
		httpServletRequest.setAttribute("damageAddressTypes", damageAddressTypes);
		// (4)得到车辆种类列表
		List<?> carKindCodes = this.codeService.getCodeType("CarKind", prpLprepay.getRiskCode());
		httpServletRequest.setAttribute("carKindCodes", carKindCodes);
		// (5)得到车牌底色列表
		List<?> licenseColorCode = this.codeService.getCodeType("LicenseColor", prpLprepay.getRiskCode());
		httpServletRequest.setAttribute("licenseColorCodes", licenseColorCode);
		// (6)得到赔偿责任列表
		List<?> indemnityDuty = this.codeService.getCodeType("IndemnityDuty", prpLprepay.getRiskCode());
		httpServletRequest.setAttribute("indemnityDutys", indemnityDuty);
		// (7)得到赔案类别列表
		List<?> escapeFlags = this.codeService.getCodeType("CaseCode", prpLprepay.getRiskCode());
		httpServletRequest.setAttribute("escapeFlags", escapeFlags);
		// (8)得到得到性别
		List<?> driverSex = this.codeService.getCodeType("SexCode", prpLprepay.getRiskCode());
		httpServletRequest.setAttribute("driverSexs", driverSex);
		// (9)得到职业分类
		List<?> driverOccupation = this.codeService.getCodeType("Occupation", prpLprepay.getRiskCode());
		httpServletRequest.setAttribute("driverOccupations", driverOccupation);
		// (10)得到文化程度
		List<?> education = this.codeService.getCodeType("Education", prpLprepay.getRiskCode());
		httpServletRequest.setAttribute("educations", education);
		// (11)得到理赔类型的列表 --- 目前无法得到
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写预赔单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public PrepayDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		return new PrepayDto();
	}

	/**
	 * 填写预赔页面及查询预赔request的生成.
	 * 填写预赔时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param prepayDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, PrepayDto prepayDto) throws Exception {
	}

	/**
	 * 根据用户ID和预赔状态查询预赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param status 预赔状态
	 * @param userCode 用户ID
	 * @throws Exception
	 */

	public void setPrpLprepayManageDtoToView(HttpServletRequest httpServletRequest, String status, String userCode) throws Exception {
		// 根据输入的保单号，赔案号生成SQL where 子句
		status = StringUtils.rightTrim(status);
		userCode = StringUtils.rightTrim(userCode);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addLike("status", "%" + status + "%").addLike("handlerCode", "%" + userCode + "%").addLike("id.nodeType", "%prepa%");
		List<PrpLclaimStatus> prpLclaimStatusList = prpLclaimStatusService.findPrpLclaimStatus(queryRule);
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.setClaimList(prpLclaimStatusList);
		prpLclaimStatus.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLclaimStatus", prpLclaimStatus);
	}

	/**
	 * 根据预赔号和保单号,赔案号,案件状态，车牌号码，操作时间查询预赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param prepayNo 预赔号
	 * @param policyNo 保单号
	 * @param claimNo 赔案号
	 * @throws Exception Modify By sunhao 2004-08-24 Reason:增加车牌号，案件状态，操作时间查询条件
	 */

	public void setPrpLprepayDtoToView(HttpServletRequest httpServletRequest, String prepayNo, String policyNo, String claimNo, String licenseNo, String status, String operateDate, String underWriteFlag) throws Exception {
		// 根据输入的保单号，预赔号生成SQL where 子句
		prepayNo = StringUtils.rightTrim(prepayNo);
		policyNo = StringUtils.rightTrim(policyNo);
		claimNo = StringUtils.rightTrim(claimNo);
		licenseNo = StringUtils.rightTrim(licenseNo);
		operateDate = StringUtils.rightTrim(operateDate);
		String strSign = httpServletRequest.getParameter("OperateDateSign");

		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("a.PreCompensateNo", prepayNo, httpServletRequest.getParameter("PrepayNoSign"));
		conditions = conditions + StringConvert.convertString("a.claimNo", claimNo, httpServletRequest.getParameter("ClaimNoSign"));
		conditions = conditions + StringConvert.convertString("a.policyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign"));
		conditions = conditions + StringConvert.convertString("c.licenseNo", licenseNo, httpServletRequest.getParameter("LicenseNoSign"));
		if (status.trim().length() > 0) {
			conditions = conditions + " AND b.status in (" + status + ")";
		}
		if (underWriteFlag.trim().length() > 0) {
			conditions = conditions + " AND a.underWriteFlag in (" + underWriteFlag + ") ";
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions = conditions + StringConvert.convertDate("b.operateDate", operateDate, strSign);
		}
		// 查询预赔信息
		// 得到多行预赔主表信息
		List<PrpLprepay> prepayList = prepayService.findByQueryConditions(conditions);
		PrpLprepay prpLprepay = new PrpLprepay();
		prpLprepay.setClaimList(prepayList);
		prpLprepay.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLprepay", prpLprepay);
	}

	/**
	 * 根据预赔号和保单号和赔案号查询待复核的预赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param prepayNo 预赔号
	 * @param policyNo 保单号
	 * @param claimNo 赔案号
	 * @throws Exception
	 */

	public void getApprovePrepayList(HttpServletRequest httpServletRequest, String prepayNo, String policyNo, String claimNo) throws Exception {
		// 根据输入的保单号，预赔号生成SQL where 子句
		prepayNo = StringUtils.rightTrim(prepayNo);
		policyNo = StringUtils.rightTrim(policyNo);
		claimNo = StringUtils.rightTrim(claimNo);
		String conditions = "1=1  ";
		conditions = conditions + StringConvert.convertString("prplprepay.precompensateNo", prepayNo, httpServletRequest.getParameter("PrepayNoSign"));
		conditions = conditions + StringConvert.convertString("prplprepay.policyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign"));
		conditions = conditions + StringConvert.convertString("prplprepay.claimNo", claimNo, httpServletRequest.getParameter("ClaimNoSign"));
		conditions = conditions + " AND ( prplprepay.ApproverCode IS NULL OR  prplprepay.ApproverCode='' OR prplprepay.UnderWriteFlag='2') and prplclaimstatus.status='3'";
		// 查询预赔信息
		// 得到多行预赔主表信息
		List<PrpLprepay> prepayList = prepayService.findByApproveConditions(conditions);
		PrpLprepay prpLprepay = new PrpLprepay();
		prpLprepay.setClaimList(prepayList);
		prpLprepay.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLprepay", prpLprepay);
	}

	/**
	 * 查询已经报案的数据，计算出现次数来进行显示
	 * @param registDto RegistDto
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void getSamePolicyRegistInfo(PrpLprepay prpLprepay) throws Exception {
		List<PrpLregist> registLastList = new ArrayList<PrpLregist>();
		PrpLregist prpLregistTemp = null;
		String strOperatorCode = "";
		String strOperatorName = "";
		List<PrpLregist> registList = prpLregistService.findSamePolicyRegist(prpLprepay.getPolicyNo());
		int intPerilCount = 0;
		// 转换操作人员的名称，以及计算个数
		for (int i = 0; i < registList.size(); i++) {
			prpLregistTemp = registList.get(i);
			strOperatorCode = prpLregistTemp.getOperatorCode();
			strOperatorName = this.codeService.translateUserCode(strOperatorCode, true);
			prpLregistTemp.setOperatorName(strOperatorName);

			registLastList.add(prpLregistTemp);
			intPerilCount++;
		}
		// 将查询出来的同个保单的数据放入PrpLregistDto的list
		prpLprepay.setRegistList(registLastList);
		// 计算出险的次数
		prpLprepay.setPerilCount(intPerilCount);
	}

	public PrepayService getPrepayService() {
		return prepayService;
	}

	public void setPrepayService(PrepayService prepayService) {
		this.prepayService = prepayService;
	}

	public PrpLclaimLossService getPrpLclaimLossService() {
		return prpLclaimLossService;
	}

	public void setPrpLclaimLossService(PrpLclaimLossService prpLclaimLossService) {
		this.prpLclaimLossService = prpLclaimLossService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
