package com.sinosoft.claim.common.util;

import ins.framework.common.DateTime;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLmessage;
import com.sinosoft.claim.schema.model.PrpLmessageId;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLmessageService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.ui.control.action.UICodeAction;

/**
 * <p>
 * Title: DAAMessageViewhelper
 * </p>
 * <p>
 * Description:理赔流转讨论留言页面的Viewhelper类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company:sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class MessageViewHelper {
	/** 理赔流转讨论留言信息服务 */
	private PrpLmessageService prpLmessageService;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 报案信息服务 */
	private PrpLregistService prpLregistService;
	private CodeService codeService;

	/**
	 * 默认构造方法
	 */
	public MessageViewHelper() {
	}

	/**
	 * 整理讨论留言信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	public PrpLmessage viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		String registNo = "";
		registNo = httpServletRequest.getParameter("prpLmessageRegistNo");
		PrpLmessage prpLmessage = new PrpLmessage();
		PrpLmessageId prpLmessageId = new PrpLmessageId();
		prpLmessage.setContext(httpServletRequest.getParameter("prpLmessageContext"));
		prpLmessageId.setRegistNo(httpServletRequest.getParameter("prpLmessageRegistNo"));
		prpLmessageId.setSerialNo(prpLmessageService.findMaxNo(registNo));
		prpLmessageId.setLineNo(1);
		prpLmessage.setId(prpLmessageId);
		prpLmessage.setClaimNo(httpServletRequest.getParameter("prpLmessageClaimNo"));

		prpLmessage.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_SECOND));
		prpLmessage.setRiskCode(httpServletRequest.getParameter("prpLmessageRiskCode"));
		prpLmessage.setPolicyNo(httpServletRequest.getParameter("prpLmessagePolicyNo"));
		prpLmessage.setNodeType(httpServletRequest.getParameter("prpLmessageNodeType"));
		prpLmessage.setOperatorName(httpServletRequest.getParameter("prpLmessageOperatorName"));
		return prpLmessage;
	}

	/**
	 * 设置讨论留言信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, PrpLmessage prpLmessageDto) throws Exception {
		httpServletRequest.setAttribute("prpLmessageDto", prpLmessageDto);
	}

	/**
	 * 得到留言列表
	 * @param httpServletRequest HttpServletRequest
	 * @throws Exception
	 */
	public void getMessage(HttpServletRequest httpServletRequest) throws Exception {

		// Modify By sunhao 2004-08-29
		String registNo = httpServletRequest.getParameter("registNo");
		String claimNo = httpServletRequest.getParameter("claimNo");
		if (registNo == null) {
			if (claimNo != null) {// 查询报案号码
				PrpLclaim prpLclaimDto = prpLclaimService.findPrpLclaim(claimNo);
				registNo = prpLclaimDto.getRegistNo();
			}
		}
		List<PrpLmessage> messageList = prpLmessageService.findPrpLmessageByRegistNo(registNo);
		PrpLmessage prpLmessageTemp = null;
		UICodeAction uiCodeAction = UICodeAction.getInstance();
		for (int i = 0; i < messageList.size(); i++) {
			prpLmessageTemp = messageList.get(i);
			//调查节点留言中节点类型应该是调查而不是查勘
			String strRiskType = getCodeService().translateRiskCodetoRiskType(prpLmessageTemp.getRiskCode());
			if (prpLmessageTemp.getNodeType().equals("check") && "E".equals(strRiskType)) {
				prpLmessageTemp.setNodeType("調查");
			} else if (prpLmessageTemp.getNodeType().equals("audch") && "E".equals(strRiskType)) {
				prpLmessageTemp.setNodeTypeName("調查審核");
			} else if (prpLmessageTemp.getNodeType().equals("rcase")) {
				prpLmessageTemp.setNodeType("重開賠案");
			//mantis：CLM0082 ，處理人員：BK007  蘇哲，需求單編號：CLM0082.追償作業加入賠案處裡紀錄 -start
			} else if (prpLmessageTemp.getNodeType().equals("replevy")) {
				prpLmessageTemp.setNodeType("追償");
			//mantis：CLM0082 ，處理人員：BK007  蘇哲，需求單編號：CLM0082.追償作業加入賠案處裡紀錄 -end
			} else {
				prpLmessageTemp.setNodeType(uiCodeAction.translateCodeCode("ClaimNodeType", prpLmessageTemp.getNodeType(), true));
			}
			// modify by liuyanmei 调查节点留言中节点类型应该是调查而不是查勘 20060106 end
		}
		httpServletRequest.setAttribute("prpLmessageList", messageList);
	}

	/**
	 * 留言保存页面查询相关信息
	 * @param httpServletRequest HttpServletRequest
	 * @throws Exception
	 * @return PrpLmessageDto
	 */
	public void queryRelateInfoToDto(HttpServletRequest httpServletRequest) throws Exception {
		String businessNo = httpServletRequest.getParameter("businessNo");
		String nodeType = httpServletRequest.getParameter("nodeType");
		String policyNo = httpServletRequest.getParameter("policyNo");
		String riskCode = httpServletRequest.getParameter("riskCode");
		String claimNo = httpServletRequest.getParameter("claimNo");
		PrpLmessage prpLmessage = new PrpLmessage();

		UICodeAction uiCodeAction = UICodeAction.getInstance();
		prpLmessage = findAllMessage(businessNo, nodeType, policyNo, riskCode, claimNo);
		String riskType = codeService.translateRiskCodetoRiskType(riskCode);
		if (prpLmessage.getNodeType().equals("check") && ConstantCodes.CLASSCODE_E.equals(riskType)) {
			prpLmessage.setNodeTypeName("調查");
		} else if (prpLmessage.getNodeType().equals("audch") && ConstantCodes.CLASSCODE_E.equals(riskType)) {
			prpLmessage.setNodeTypeName("調查審核");
		} else if ("taskView".equals(prpLmessage.getNodeType())) {
			prpLmessage.setNodeTypeName("任務查询");
		} else {
			prpLmessage.setNodeTypeName(uiCodeAction.translateCodeCode("ClaimNodeType", prpLmessage.getNodeType(), true));
		}
		// 取得当前用户代码
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		prpLmessage.setOperatorCode(user.getUserCode());
		// 取得被保险人姓名
		String insuredName = httpServletRequest.getParameter("insuredName");
		if (insuredName == null || "".equals(insuredName)) {
			PrpLregist prpLregist = prpLregistService.findPrpLregist(businessNo);
			if (prpLregist != null) {
				insuredName = prpLregist.getInsuredName();
			} else {
				PrpLclaim prplclaim = prpLclaimService.findPrpLclaim(businessNo);
				if (prplclaim != null) {
					insuredName = prplclaim.getInsuredName();
				}
			}
		}

		httpServletRequest.setAttribute("insuredName", insuredName);
		// 将当前用户代码转换成姓名
		String operatorName = uiCodeAction.translateUserCode(user.getUserCode(), true);
		prpLmessage.setOperatorName(operatorName);
		httpServletRequest.setAttribute("prpLmessage", prpLmessage);
	}

	/**
	 * 保存留言页面相关查询
	 * @param bussinessNo String 业务号
	 * @param nodeType String 节点类型
	 * @throws Exception
	 * @return Object
	 */
	public PrpLmessage findAllMessage(String businessNo, String nodeType, String policyNo, String riskCode, String claimNo) throws Exception {
		String registNo = "";

		if (nodeType.equals("regis")) {
			registNo = businessNo;
		} else if (nodeType.equals("sched") || nodeType.equals("schel")) {
			registNo = businessNo;
		} else if (nodeType.equals("check")) {
			registNo = businessNo;
		} else if (nodeType.equals("propc")) {
			registNo = businessNo;
		} else if (nodeType.equals("propv")) {
			registNo = businessNo;
		} else if (nodeType.equals("claim")) {
			registNo = businessNo;
		} else if (nodeType.equals("certi")) {
			List<PrpLclaim> prpLclaimlist = prpLclaimService.findByRegistNo(businessNo);
			if (prpLclaimlist != null && prpLclaimlist.size() > 0) {
				PrpLclaim prpLclaimDto = prpLclaimlist.get(0);
				claimNo = prpLclaimDto.getClaimNo();
				riskCode = prpLclaimDto.getRiskCode();
				registNo = prpLclaimDto.getRegistNo();

			} else {
				// 如果在prpLclaim表中没有查到该报案号记录,从prpLregist表中查险种代码
				PrpLregist prpLregist = prpLregistService.findPrpLregist(businessNo);
				if (prpLregist != null) {
					riskCode = prpLregist.getRiskCode();
					registNo = prpLregist.getRegistNo();
				}
			}
		} else if (nodeType.equals("certa")) {
			registNo = businessNo;
		} else if (nodeType.equals("verif")) {
			registNo = businessNo;
		} else if (nodeType.equals("prepa") || nodeType.equals("compe")) {
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(businessNo);
			if (prpLclaim != null) {
				registNo = prpLclaim.getRegistNo();
			}
		} else if (nodeType.equals("endca")) {
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
			if (prpLclaim != null) {
				riskCode = prpLclaim.getRiskCode();
			}
			registNo = businessNo;
		} else if (nodeType.equals("veric")) {
			registNo = businessNo;
		} else {// 不明节点一律认为是报案号
			registNo = businessNo;
		}
		// 取得系统当前时间
		DateTime dateTime = new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY);
		// 查到结果,赋值
		PrpLmessage prpLmessage = new PrpLmessage();
		PrpLmessageId prpLmessageId = new PrpLmessageId();
		prpLmessageId.setRegistNo(registNo);
		prpLmessage.setId(prpLmessageId);
		prpLmessage.setInputDate(dateTime);
		prpLmessage.setNodeType(nodeType);
		prpLmessage.setPolicyNo(policyNo);
		prpLmessage.setRiskCode(riskCode);
		if(CommonUtils.isEmpty(claimNo)&&!CommonUtils.isEmpty(registNo)){
			List<PrpLclaim> prpLclaimlist = prpLclaimService.findByRegistNo(registNo);
			if (prpLclaimlist != null && prpLclaimlist.size() > 0) {
				PrpLclaim prpLclaim = prpLclaimlist.get(0);
				claimNo = prpLclaim.getClaimNo();
			}
		}
		prpLmessage.setClaimNo(claimNo);
		return prpLmessage;
	}

	/**
	 * 根据送审审核意见和片语组织DTO
	 * @param httpServletRequest
	 * @return PrpLmessageDto
	 * @throws Exception
	 */
	public PrpLmessage viewToUndwrtDto(HttpServletRequest httpServletRequest) throws Exception {
		String registNo = httpServletRequest.getParameter("registno");
		int messgeID = this.prpLmessageService.findMaxNo(registNo);
		String claimNo = httpServletRequest.getParameter("claimno");
		String reCaseReason = httpServletRequest.getParameter("reCaseReason");// 审核意见
		String undwrtPhrase = "";
		if ("A".equals(httpServletRequest.getParameter("undwrtPhrase"))) {
			undwrtPhrase = "同意";
		} else if ("B".equals(httpServletRequest.getParameter("undwrtPhrase"))) {
			undwrtPhrase = "需覆核";
		} else {
			undwrtPhrase = "不同意";
		}
		PrpLmessage prpLmessage = new PrpLmessage();
		prpLmessage.setContext(undwrtPhrase + " " + httpServletRequest.getParameter("undwrtTextContextInnerHTML"));
		prpLmessage.getId().setRegistNo(registNo);
		prpLmessage.setClaimNo("");
		prpLmessage.getId().setSerialNo(messgeID);
		prpLmessage.getId().setLineNo(1);
		prpLmessage.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_SECOND));
		String riskCode = httpServletRequest.getParameter("riskcode");
		if (riskCode == null) {
			riskCode = httpServletRequest.getParameter("riskCode");
		}
		prpLmessage.setRiskCode(riskCode);
		prpLmessage.setPolicyNo(httpServletRequest.getParameter("policyno"));
		String nodeType = httpServletRequest.getParameter("nodeType");
		prpLmessage.setNodeType("aud" + nodeType.substring(0, 2));
		// 将重开赔案审核意见写写进赔案处理记录的prplmessage表
		if (!"".equals(nodeType) && "rcase".equals(nodeType)) {
			prpLmessage.setContext(reCaseReason);
			prpLmessage.setClaimNo(claimNo);
			prpLmessage.setNodeType(nodeType);
		}
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		prpLmessage.setOperatorCode(user.getUserCode());
		prpLmessage.setOperatorName(user.getUserName());
		return prpLmessage;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLmessageService getPrpLmessageService() {
		return prpLmessageService;
	}

	public void setPrpLmessageService(PrpLmessageService prpLmessageService) {
		this.prpLmessageService = prpLmessageService;
	}
	
	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
