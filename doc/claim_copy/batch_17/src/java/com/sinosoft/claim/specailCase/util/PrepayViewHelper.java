/*
 * @(#)PrepayViewHelper.java	Mar 4, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.util;

import ins.framework.common.DateTime;
import ins.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.compensate.vo.PrepayDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLptext;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public abstract class PrepayViewHelper {
	private CodeService codeService;
	private int RULE_LENGTH = 70; // rule字段的长度

	/**
	 * 默认构造方法
	 */
	public PrepayViewHelper() {
	}

	/**
	 * 保存预赔时报案页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return prepayDto 报案数据传输数据结构
	 * @throws Exception
	 */
	public PrepayDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		PrepayDto prepayDto = new PrepayDto();
		/*---------------------预赔主表prpLprepay------------------------------------*/
		PrpLprepay prpLprepay = new PrpLprepay();
		prpLprepay.setPreCompensateNo((String) httpServletRequest.getAttribute("prpLprepayPreCompensateNo"));
		prpLprepay.setClaimNo(httpServletRequest.getParameter("prpLprepayClaimNo"));
		prpLprepay.setRiskCode(httpServletRequest.getParameter("prpLprepayRiskCode"));
		prpLprepay.setPolicyNo(httpServletRequest.getParameter("prpLprepayPolicyNo"));
		prpLprepay.setCurrency(httpServletRequest.getParameter("prpLprepayCurrency"));
		prpLprepay.setArrearageTimes(Double.parseDouble(httpServletRequest.getParameter("prpLprepayArrearageTimes")));
		prpLprepay.setSumArrearage(Double.parseDouble(httpServletRequest.getParameter("prpLprepaySumArrearage")));
		prpLprepay.setSumBeforePrePaid(Double.parseDouble(httpServletRequest.getParameter("prpLprepaySumBeforePrePaid")));
		prpLprepay.setBlockUpTimes(Double.parseDouble(httpServletRequest.getParameter("prpLprepayBlockUpTimes")));
		String prpLprepaySumPrePaid = httpServletRequest.getParameter("prpLprepaySumPrePaid");
		if(prpLprepaySumPrePaid==null||"".equals(prpLprepaySumPrePaid)){
			prpLprepay.setSumPrePaid(0D);
		}else{
			prpLprepay.setSumPrePaid(Double.parseDouble(prpLprepaySumPrePaid));
		}
		prpLprepay.setSumTotalPrepaid(Double.parseDouble(httpServletRequest.getParameter("prpLprepaySumTotalPrepaid")));
		prpLprepay.setComCode(httpServletRequest.getParameter("prpLprepayComCode"));
		prpLprepay.setMakeCom(httpServletRequest.getParameter("prpLprepayMakeCom"));
		prpLprepay.setHandlerCode(httpServletRequest.getParameter("prpLprepayHandlerCode"));
		prpLprepay.setHandler1Code(httpServletRequest.getParameter("prpLprepayHandler1Code"));
		prpLprepay.setApproverCode(httpServletRequest.getParameter("prpLprepayApproverCode"));
		prpLprepay.setUnderWriteCode(httpServletRequest.getParameter("prpLprepayUnderWriteCode"));
		prpLprepay.setUnderWriteName(httpServletRequest.getParameter("prpLprepayUnderWriteName"));
		prpLprepay.setStatisticsYM(new DateTime(httpServletRequest.getParameter("prpLprepayStatisticsYM")));
		prpLprepay.setOperatorCode(httpServletRequest.getParameter("prpLprepayOperatorCode"));
		prpLprepay.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLprepay.setUnderWriteEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLprepay.setUnderWriteFlag(httpServletRequest.getParameter("prpLprepayUnderWriteFlag"));
		prpLprepay.setFlag(httpServletRequest.getParameter("prpLprepayFlag"));
		prpLprepay.setCaseType(httpServletRequest.getParameter("prpLprepayCaseType"));
		// add by zhangyurui 对赔款支付对象进行保存 begin
		prpLprepay.setOwnership(httpServletRequest.getParameter("prpLCompensateOwnership"));
		if ("B".equals(prpLprepay.getOwnership())) {
			prpLprepay.setAccountCode(httpServletRequest.getParameter("prpLCompensateAccountCode"));
			prpLprepay.setBankCode(httpServletRequest.getParameter("prpLCompensateBankCode"));
			prpLprepay.setBankName(httpServletRequest.getParameter("prpLCompensateBankName"));
			prpLprepay.setCustomBankCode(httpServletRequest.getParameter("prpLCompensateCustomBankCode"));
			prpLprepay.setCustomBankName(httpServletRequest.getParameter("prpLCompensateCustomBankName"));
			prpLprepay.setCertifiCateCode(httpServletRequest.getParameter("prpLCompensateCertificateCode"));
			prpLprepay.setOwnerName(httpServletRequest.getParameter("prpLCompensateOwnerName"));
			prpLprepay.setOwnerPhoneNo(httpServletRequest.getParameter("prpLCompensateOwnerPhoneNo"));
			prpLprepay.setAccountCurrency(httpServletRequest.getParameter("prpLCompensateAccountCurrency"));
			prpLprepay.setAccountType(httpServletRequest.getParameter("prpLCompensateAccountType"));
		} else if ("C".equals(prpLprepay.getOwnership()) || "Q".equals(prpLprepay.getOwnership())) {
			prpLprepay.setCertifiCateCode(httpServletRequest.getParameter("prpLCompensateCertificateCodeCQ"));
			prpLprepay.setOwnerName(httpServletRequest.getParameter("prpLCompensateOwnerNameCQ"));
		}
		// add by zhangyurui 对赔款支付对象进行保存 end
		// add by zhangyurui 2009-05-26 增加是否代付赔款 begin
		prpLprepay.setIsPayForOther(httpServletRequest.getParameter("isPayForOther"));
		// add by zhangyurui 2009-05-26 增加是否代付赔款 end
		// 加入险类
		if (null != prpLprepay.getRiskCode()){
			String classCode = getCodeService().translateClassCodeByRiskCode(prpLprepay.getRiskCode());
			prpLprepay.setClassCode(classCode);
		} else {
			prpLprepay.setClassCode(httpServletRequest.getParameter("prpLprepayClassCode"));
		}

		// add by liuwei at 2011-07-13 如果支付对象不是被保险人需录入例外事项原因 start
		if ("1".equals(httpServletRequest.getParameter("ifInsuredName"))) {
			prpLprepay.setExceptions(httpServletRequest.getParameter("exceptions"));// 例外事项原因
			if ("9".equals(httpServletRequest.getParameter("exceptions"))) {// 例外事项原因选择其他时需录入原因描述
				prpLprepay.setReason(httpServletRequest.getParameter("reason"));// 例外事项原因描述
			} else {
				prpLprepay.setReason("");
			}
		} else {
			prpLprepay.setExceptions("");
			prpLprepay.setReason("");
		}
		// add by liuwei at 2011-07-13 如果支付对象不是被保险人需录入例外事项原因 end
		// 加到ArrayList中
		prepayDto.setPrpLprepay(prpLprepay);

		/*---------------------预赔文本表PrpLptextDto------------------------------------*/
		List<PrpLptext> prpLptextList = new ArrayList<PrpLptext>();
		String TextTemp = httpServletRequest.getParameter("prpLptextContextInnerHTML");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH);
		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules.length; k++) {
			PrpLptext prpLptext = new PrpLptext();
			prpLptext.getId().setPreCompensateNo((String) httpServletRequest.getAttribute("prpLprepayPreCompensateNo"));
			prpLptext.setContext(rules[k]);
			prpLptext.getId().setLineNo(new Long(k + 1));
			prpLptextList.add(prpLptext);
		}
		// prepayDto
		prepayDto.setPrpLptextList(prpLptextList);

		/*---------------------立案操作状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.setStatus(httpServletRequest.getParameter("buttonSaveType"));
		prpLclaimStatus.getId().setBusinessNo(prpLprepay.getPreCompensateNo());
		prpLclaimStatus.setPolicyNo(prpLprepay.getPolicyNo());
		prpLclaimStatus.setTypeFlag("5");
		prpLclaimStatus.getId().setNodeType("speci");
		prpLclaimStatus.getId().setSerialNo(5);

		prpLclaimStatus.setRiskCode(httpServletRequest.getParameter("prpLprepayRiskCode"));
		// 取得当前用户信息，写操作员信息到实赔中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(prpLprepay.getInputDate());
		prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prepayDto.setPrpLclaimStatus(prpLclaimStatus);

		PrpLclaim prpLclaim = null;
		String buttonStatus = httpServletRequest.getParameter("buttonSaveType");
		if (buttonStatus != null && buttonStatus.toString().trim().equals("4")) {
			prpLclaim = new PrpLclaim();
			prpLclaim.setClaimNo(httpServletRequest.getParameter("prpLprepayClaimNo"));
			String prpLprepaySumPrePaidTemp = httpServletRequest.getParameter("prpLprepaySumPrePaid");
			if(prpLprepaySumPrePaidTemp==null||"".equals(prpLprepaySumPrePaidTemp)){
				prpLclaim.setSumPaid(0d);
			}else{
				prpLclaim.setSumPaid(Double.parseDouble(prpLprepaySumPrePaidTemp));
			}
		}
		prepayDto.setPrpLclaim(prpLclaim);
		return prepayDto;
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写报案单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public abstract PrepayDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception;

	/**
	 * 填写报案页面及查询报案request的生成.
	 * 填写报案时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param prepayDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public abstract void dtoToView(HttpServletRequest httpServletRequest, PrepayDto prepayDto) throws Exception;

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
