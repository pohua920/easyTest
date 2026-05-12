package com.sinosoft.claim.certainLoss.util;

import ins.framework.common.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 * <p>
 * Title: CertainLossViewHelper
 * </p>
 * <p>
 * Description:定损ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2004
 * </p>
 * @author 中科软
 * <br>
 */
public abstract class CertainLossViewHelper {
	/** 工作流服务 */
	private WorkFlowService workFlowService;
	/**
	 * 默认构造方法
	 */
	public CertainLossViewHelper() {
	}

	/**
	 * 保存定损时定损页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return stepFlag 标志
	 * @throws Exception
	 */
	public CertainLossDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
//		String userCode = user.getUserCode();
//		String userName = user.getUserName();

		CertainLossDto certainLossDto = new CertainLossDto();
		/*---------------------定损主表 PrpLverifyLoss------------------------------------*/
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

		prpLverifyLoss.getId().setLossItemCode(httpServletRequest.getParameter("prpLverifyLossLossItemCode"));
		prpLverifyLoss.setLossItemName(httpServletRequest.getParameter("prpLverifyLossLossItemName"));
		prpLverifyLoss.setInsureCarFlag(httpServletRequest.getParameter("prpLverifyLossInsureCarFlag"));
		if (httpServletRequest.getParameter("prpLverifyLossDefLossDate") == null || "".equals(httpServletRequest.getParameter("prpLverifyLossDefLossDate"))) {
			prpLverifyLoss.setDefLossDate(new DateTime(new Date()));
		} else {
			prpLverifyLoss.setDefLossDate(new DateTime(httpServletRequest.getParameter("prpLverifyLossDefLossDate"), DateTime.YEAR_TO_DAY));
		}
		prpLverifyLoss.setUnderWriteCode(httpServletRequest.getParameter("prpLverifyLossUnderWriteCode"));
		prpLverifyLoss.setUnderWriteName(httpServletRequest.getParameter("prpLverifyLossUnderWriteName"));
//		prpLverifyLoss.setUnderWriteEndDate(new DateTime(new Date()));

		prpLverifyLoss.setUnderWriteFlag(httpServletRequest.getParameter("prpLverifyLossUnderWriteFlag"));
		prpLverifyLoss.setRemark(httpServletRequest.getParameter("prpLverifyLossRemark"));
		prpLverifyLoss.setVerifyRemark(httpServletRequest.getParameter("prpLverifyLossVerifyRemark"));
		prpLverifyLoss.setVeriwReturnReason(httpServletRequest.getParameter("prpLverifyLossVeriwReturnReason"));
		prpLverifyLoss.setFlag(httpServletRequest.getParameter("prpLverifyLossFlag"));
		// reason:增加修理厂类型和修理厂名称
		prpLverifyLoss.setRepairFactoryCode(httpServletRequest.getParameter("prpLrepairFeeRepairFactoryCode"));
		prpLverifyLoss.setRepairFactoryName(httpServletRequest.getParameter("prpLrepairFeeRepairFactoryName"));
		// reason: 增加保存理算退回的定损的原有数据的保存,若有数据不会被保存冲掉
		prpLverifyLoss.setCompensateApproverCode(httpServletRequest.getParameter("prpLverifyLossCompensateApproverCode"));
		prpLverifyLoss.setCompensateBackDate(new DateTime((String) httpServletRequest.getParameter("prpLverifyLossCompensateBackDate"), DateTime.YEAR_TO_DAY));
		prpLverifyLoss.setCompensateFlag(httpServletRequest.getParameter("prpLverifyLossCompensateFlag"));
		prpLverifyLoss.setCompensateOpinion(httpServletRequest.getParameter("prpLverifyLossCompensateOpinion"));
		// 定损偏差率
		// 初次定损金额取第一次定损提交的金额
		String saveType = httpServletRequest.getParameter("buttonSaveType");
		double prpLverifyLossFirstDefLoss = Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLverifyLossFirstDefLoss")));
		double prpLverifyLossWarpDefLoss = Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLverifyLossWarpDefLoss")));
		if (prpLverifyLossFirstDefLoss <= 0) {
			prpLverifyLossFirstDefLoss = prpLverifyLossWarpDefLoss;
		}
		if (saveType != null && "4".equals(saveType)) {
			prpLverifyLoss.setFirstDefLoss(prpLverifyLossFirstDefLoss);
			prpLverifyLoss.setWarpDefLoss(prpLverifyLossFirstDefLoss);
		}
		certainLossDto.setPrpLverifyLoss(prpLverifyLoss);
		// 每次改动的时候，把改动人，改动时间，改动的金额，写到swfnotion表里
		List<SwfNotion> notionList = new ArrayList<SwfNotion>();
		int maxLineNo = this.getWorkFlowService().getSwfNotionMaxLineNo(httpServletRequest.getParameter("swfLogFlowID"), Integer.parseInt(DataUtils.nullToZero(httpServletRequest.getParameter("swfLogLogNo"))));
		SwfNotion swfNotion = null;
		if (prpLverifyLossFirstDefLoss != prpLverifyLossWarpDefLoss) {
			swfNotion = new SwfNotion();
			swfNotion.getId().setFlowID((String) httpServletRequest.getParameter("swfLogFlowID"));
			swfNotion.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(httpServletRequest.getParameter("swfLogLogNo"))));
			swfNotion.getId().setLineNo(maxLineNo);
			swfNotion.setHandleText(user.getUserName() + "在" + new DateTime(new Date(), DateTime.YEAR_TO_DAY) + "修改了定損金額:" + prpLverifyLossWarpDefLoss + "元");
			notionList.add(swfNotion);
		}
		certainLossDto.setSwfNotionList(notionList);
		// 人伤、财产定损，没有核损环节，定损提交时即核损通过
		String buttonSaveType = httpServletRequest.getParameter("buttonSaveType");
		String nodeType = httpServletRequest.getParameter("nodeType");
		//没有核损设置核损人员和定损人员相同
//		if ("propc".equals(nodeType) && "4".equals(buttonSaveType)) {
//			prpLverifyLoss.setUnderWriteCode(userCode);
//			prpLverifyLoss.setUnderWriteName(userName);
//			prpLverifyLoss.setUnderWriteEndDate(new DateTime(new Date()));
//			prpLverifyLoss.setUnderWriteFlag("1");
//		}
		prpLverifyLoss.getId().setNodeType(nodeType);
		prpLverifyLoss.getId().setNodeType(nodeType);
		/*---------------------状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.getId().setBusinessNo(httpServletRequest.getParameter("prpLverifyLossRegistNo"));
		prpLclaimStatus.getId().setNodeType(nodeType);
		prpLclaimStatus.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLverifyLoss.getId().getLossItemCode())));
		prpLclaimStatus.setPolicyNo(httpServletRequest.getParameter("prpLverifyLossPolicyNo"));
		prpLclaimStatus.setRiskCode(httpServletRequest.getParameter("prpLverifyLossRiskCode"));
		prpLclaimStatus.setStatus(buttonSaveType);
		prpLclaimStatus.setTypeFlag(prpLverifyLoss.getId().getLossItemCode());
		// 取得当前用户信息，写操作员信息到定损中
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
		prpLclaimStatus.setOperateDate(new DateTime(new Date()));
		certainLossDto.setPrpLclaimStatus(prpLclaimStatus);
		return certainLossDto;
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写定损单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public abstract CertainLossDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception;

	/**
	 * 填写定损页面及查询定损request的生成.
	 * 填写定损时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param certainLossDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public abstract void dtoToView(HttpServletRequest httpServletRequest, CertainLossDto certainLossDto) throws Exception;

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

}
