package com.sinosoft.claim.verifyLoss.util;

import ins.framework.common.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.verifyLoss.vo.VerifyLossDto;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 * @ClassName VerifyLossViewHelper
 * @Description 核损ViewHelper类，在该类中完成页面数据的整理
 * @author 中科软
 * @date Feb 19, 2013 12:29:51 PM
 */
public abstract class VerifyLossViewHelper {
	/** 日期格式 yyyy-MM-dd*/
	public static SimpleDateFormat formatter10 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 默认构造方法
	 */
	public VerifyLossViewHelper() {
	}

	/**
	 * 保存核损时核损页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return stepFlag 标志
	 * @throws Exception
	 */
	public VerifyLossDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		String nodeType = httpServletRequest.getParameter("nodeType");

		VerifyLossDto verifyLossDto = new VerifyLossDto();
		// 取得当前用户信息，写操作员信息到核损中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		/*---------------------核损主表 PrpLverifyLoss------------------------------------*/
		PrpLverifyLoss prpLverifyLoss = new PrpLverifyLoss();
		prpLverifyLoss.getId().setRegistNo(httpServletRequest.getParameter("prpLverifyLossRegistNo"));
		prpLverifyLoss.setClaimNo(httpServletRequest.getParameter("prpLverifyLossClaimNo"));
		prpLverifyLoss.setRiskCode(httpServletRequest.getParameter("prpLverifyLossRiskCode"));
		prpLverifyLoss.setPolicyNo(httpServletRequest.getParameter("prpLverifyLossPolicyNo"));
		prpLverifyLoss.setInsuredName(httpServletRequest.getParameter("prpLverifyLossInsuredName"));
		prpLverifyLoss.setLicenseNo(httpServletRequest.getParameter("prpLverifyLossLicenseNo"));
		prpLverifyLoss.setLicenseColorCode(httpServletRequest.getParameter("prpLverifyLossLicenseColorcode"));
		prpLverifyLoss.setCarKindCode(httpServletRequest.getParameter("prpLverifyLossCarKindCode"));
		prpLverifyLoss.setCurrency(httpServletRequest.getParameter("prpLverifyLossCurrency"));
		prpLverifyLoss.setSumPreDefLoss(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLverifyLossSumPreDefLoss"))));
		prpLverifyLoss.setSumDefLoss(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLverifyLossSumDefLoss"))));
		prpLverifyLoss.setMakeCom(httpServletRequest.getParameter("prpLverifyLossMakeCom"));
		prpLverifyLoss.setComCode(httpServletRequest.getParameter("prpLverifyLossComCode"));
		prpLverifyLoss.setHandlerCode(httpServletRequest.getParameter("prpLverifyLossHandlerCode"));
		prpLverifyLoss.setHandlerName(httpServletRequest.getParameter("prpLverifyLossHandlerName"));
		// Reason:在核损页面增加核损意见选项
//		int intPrpLverifyLossLossItemCode = Integer.parseInt(DataUtils.nullToZero(httpServletRequest.getParameter("prpLverifyLossLossItemCode")));
		String prpLverifyLossNodeType = httpServletRequest.getParameter("prpLverifyLossNodeType");
		String strVerifyPriceOpinion = "";
		String strVerifyOpinion = "";
		if ("certa".equals(prpLverifyLossNodeType)) {
			strVerifyPriceOpinion = httpServletRequest.getParameter("verpOpinion");
			strVerifyOpinion = httpServletRequest.getParameter("verifyOpinion");
		}
		prpLverifyLoss.setVerifyOpinion(strVerifyOpinion);
		

		prpLverifyLoss.setVerpOpinion(strVerifyPriceOpinion);
		prpLverifyLoss.setVerpDate(new DateTime(httpServletRequest.getParameter("prpLverifyLossVerpDate")));
		prpLverifyLoss.setVerpApproverCode(httpServletRequest.getParameter("prpLverifyLossVerpApproverCode"));
		prpLverifyLoss.setVerpRemark(httpServletRequest.getParameter("prpLverifyLossVerpRemark"));

		prpLverifyLoss.getId().setLossItemCode(httpServletRequest.getParameter("prpLverifyLossLossItemCode"));
		prpLverifyLoss.setLossItemName(httpServletRequest.getParameter("prpLverifyLossLossItemName"));
		prpLverifyLoss.setInsureCarFlag(httpServletRequest.getParameter("prpLverifyLossInsureCarFlag"));
		if (httpServletRequest.getParameter("prpLverifyLossDefLossDate") == null || "".equals(httpServletRequest.getParameter("prpLverifyLossDefLossDate"))) {
			prpLverifyLoss.setDefLossDate(new Date());
		} else {
			prpLverifyLoss.setDefLossDate(new DateTime(httpServletRequest.getParameter("prpLverifyLossDefLossDate")));
		}
		prpLverifyLoss.setBackCheckRemark(httpServletRequest.getParameter("prpLverifyLossBackCheckRemark"));
		prpLverifyLoss.setVeriwReturnReason(httpServletRequest.getParameter("prpLverifyLossVeriwReturnReason"));
		prpLverifyLoss.setRepairFactoryCode(httpServletRequest.getParameter("prpLrepairFeeRepairFactoryCode"));
		prpLverifyLoss.setRepairFactoryName(httpServletRequest.getParameter("prpLrepairFeeRepairFactoryName"));
		// 定损偏差率
		double prpLverifyLossFirstDefLoss = Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLverifyLossFirstDefLoss")));
		double prpLverifyLossWarpDefLoss = Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLverifyLossWarpDefLoss")));
		prpLverifyLoss.setFirstDefLoss(prpLverifyLossFirstDefLoss);
		prpLverifyLoss.setWarpDefLoss(prpLverifyLossWarpDefLoss);
		// 当提交时表示核损通过underWriteFlag的状态位为1,表示通过
		String buttonSaveType = httpServletRequest.getParameter("buttonSaveType");
		if (buttonSaveType.equals("3") || buttonSaveType.endsWith("4")) {
			prpLverifyLoss.setUnderWriteFlag("1");
		} else {
			prpLverifyLoss.setUnderWriteFlag("0");
		}
		//设置核损人员和核损时间
		prpLverifyLoss.setUnderWriteCode(user.getUserCode());
		prpLverifyLoss.setUnderWriteName(user.getUserName());
		prpLverifyLoss.setUnderWriteEndDate(new DateTime(new Date(),DateTime.YEAR_TO_SECOND));
		prpLverifyLoss.setRemark(httpServletRequest.getParameter("prpLverifyLossRemark"));
		prpLverifyLoss.setVerifyRemark(httpServletRequest.getParameter("prpLverifyLossVerifyRemark"));
		prpLverifyLoss.setFlag(httpServletRequest.getParameter("prpLverifyLossFlag"));
		// reason: 增加保存理算退回的定损的原有数据的保存,若有数据不会被保存冲掉
		prpLverifyLoss.setCompensateApproverCode(httpServletRequest.getParameter("prpLverifyLossCompensateApproverCode"));
		prpLverifyLoss.setCompensateBackDate(new DateTime((String) httpServletRequest.getParameter("prpLverifyLossCompensateBackDate")));
		prpLverifyLoss.setCompensateFlag(httpServletRequest.getParameter("prpLverifyLossCompensateFlag"));
		prpLverifyLoss.setCompensateOpinion(httpServletRequest.getParameter("prpLverifyLossCompensateOpinion"));
		prpLverifyLoss.getId().setNodeType(prpLverifyLossNodeType);
		verifyLossDto.setPrpLverifyLoss(prpLverifyLoss);
		/*---------------------状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.setStatus(httpServletRequest.getParameter("buttonSaveType"));
		prpLclaimStatus.getId().setBusinessNo(httpServletRequest.getParameter("prpLverifyLossRegistNo"));
		prpLclaimStatus.setPolicyNo(httpServletRequest.getParameter("prpLverifyLossPolicyNo"));
		prpLclaimStatus.setRiskCode(prpLverifyLoss.getRiskCode());
		prpLclaimStatus.getId().setNodeType(nodeType);
		prpLclaimStatus.getId().setSerialNo(Integer.valueOf(prpLverifyLoss.getId().getLossItemCode()));
		prpLclaimStatus.setTypeFlag(prpLverifyLoss.getId().getLossItemCode());
		prpLclaimStatus.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLverifyLoss.getId().getLossItemCode())));
		
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(new Date());
		prpLclaimStatus.setOperateDate(new Date());
		verifyLossDto.setPrpLclaimStatus(prpLclaimStatus);

		return verifyLossDto;
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写核损单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public abstract VerifyLossDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception;

	/**
	 * 填写核损页面及查询核损request的生成.
	 * 填写核损时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param verifyLossDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public abstract void dtoToView(HttpServletRequest httpServletRequest, VerifyLossDto verifyLossDto) throws Exception;

}
