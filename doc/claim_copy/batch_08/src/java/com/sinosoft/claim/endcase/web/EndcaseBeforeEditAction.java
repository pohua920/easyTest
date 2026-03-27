package com.sinosoft.claim.endcase.web;

import ins.framework.web.Struts2Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.compensate.util.DAACompensateViewHelper;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.endcase.service.facade.EndcaseService;
import com.sinosoft.claim.endcase.util.DAAEndcaseViewHelper;
import com.sinosoft.claim.endcase.vo.EndcaseDto;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 分发HTTP GET 车险理赔结案前查询保单请求
 * <p>
 * Title: 车险理赔结案前查询保单信息
 * </p>
 * <p>
 * Description: 车险理赔结案前查询保单信息系统样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class EndcaseBeforeEditAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	/**结案处理数据收集*/
	private DAAEndcaseViewHelper daaEndcaseViewHelper;
	/**理算处理数据收集*/
	private DAACompensateViewHelper daaCompensateViewHelper;
	/**结案服务*/
	private EndcaseService endcaseService;
	/**保单服务*/
	private PolicyService policyService;
	/**险别配置服务*/
	private PrpDriskConfigService prpDriskConfigService;
	/**编辑类型*/
	private String editType;
	/**工作流处理数据收集*/
	private WorkFlowViewHelper workFlowViewHelper;
	/**工作流处理服务*/
	private WorkFlowService workFlowService;
	
	/**
	 * 结案处理
	 * @return
	 * @throws Exception
	 */
	public String endcaseBeforeEdit() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		// （特殊赔案：垫支付）
		String dfFlag = httpServletRequest.getParameter("dfFlag");
		httpServletRequest.setAttribute("dfFlag", dfFlag);
		String claimNo = httpServletRequest.getParameter("ClaimNo"); // 赔案号
		String caseNo = ""; // 赔案号
		String riskCode = httpServletRequest.getParameter("riskCode"); // 险种
		String forward = ""; // 向前
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user"); // 用户信息
		httpServletRequest.setAttribute("editType", editType);
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		// 1.查询立案信息,整理输入，用於初始界面显示
		if (editType.equals("ADD")) {
			boolean blnRecaseFlag = daaEndcaseViewHelper.checkRecase(claimNo); // 是否重开赔案，且已结案
			// 立案已经结案的不许再进行结案登记
			boolean blnEndcaseFlag = daaEndcaseViewHelper.checkEndcase(claimNo);
			if (blnEndcaseFlag == true && blnRecaseFlag == true) { // 增加了重开赔案是否也已经结案的判断
				return "success";
			}
			// **************判断保费是否已经实收
			EndcaseDto endcaseDto = endcaseService.findByPrimaryKey(claimNo);
			String conditions1 = " policyno = '" + endcaseDto.getPrpLclaim().getPolicyNo() + "'";
			int intReturn = 0;
			intReturn = policyService.checkPay(conditions1);// -1为未缴费，0为未缴全，1为缴全
			// 获取系统设置信息
			String configValue = ""; // 获取保费未实收是否允许结案
			configValue = prpDriskConfigService.getConfigValue("ALLOW_UNPAYED_ENDCASE", endcaseDto.getPrpLclaim().getRiskCode());
			if (configValue == null || configValue.equals("")) {
				throw new UserException(1, 3, "platform", "請聯係管理員，在平台配置系統中進行 " + endcaseDto.getPrpLclaim().getRiskCode() + "險種 '保費未實收是否允許結案'的初始化！");
			}
			// 如果configValue =2 intReturn！=1则表示未交费结案
			if (configValue.equals("2") && intReturn != 1) {
				throw new UserException(1, 3, "endcase", "保費未實收，係統不允許結案！");
			}
			// 取得赔款计算书是否通过的信息
			if (dfFlag != null && !dfFlag.equals("") && dfFlag.equals("Y")) {
			} else {
				// （特殊赔案：垫支付）
				int intCompensateFlag = daaEndcaseViewHelper.checkCompensate(httpServletRequest, claimNo);
				if (intCompensateFlag == 0) { // 有问题，如果是垫付，不应该有计算书的，这个是怎么考虑的。
					throw new UserException(1, 3, "endcase", "此立案" + endcaseDto.getPrpLclaim().getClaimNo() + "沒有賠款計算書，不能結案！");
				}
				if (intCompensateFlag < 0) {
					throw new UserException(1, 3, "endcase", "存在沒有核賠通過的預賠、實賠或追償計算書，不能結案！");
				}
			}
			// 进行占号分析，只有当有工作流号码和LogNo的时候才能使用。
			// 如果没有flowID和logno则不进行判断。
			String flowID = httpServletRequest.getParameter("swfLogFlowID");
			String logNo = httpServletRequest.getParameter("swfLogLogNo");
			if (flowID != null && logNo != null) {
				SwfLog swfLogDto = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
				if (swfLogDto.getHoldNode() == false) {
					String msg = "案件'" + claimNo + "'已經被代碼:'" + swfLogDto.getHandlerCode() + "',名稱:'" + swfLogDto.getHandlerName() + "'的用戶所佔用,請選擇其它案件進行處理!";
					throw new UserException(1, 3, "工作流", msg);
				}
			}
			daaEndcaseViewHelper.claimDtoToView(httpServletRequest, claimNo, editType);
			if (riskCode == null || riskCode.length() < 1 || riskCode.trim().equals("")) {
				riskCode = BusinessRuleUtil.getRiskCode(claimNo, "ClaimNo");
			}
		}
		// 查询结案信息,整理输入，用於初始界面显示
		if (editType.equals("EDIT") || editType.equals("SHOW")) {
			claimNo = httpServletRequest.getParameter("prpLendcaseEndcaseNo"); // 结案号
			daaEndcaseViewHelper.endcaseDtoView(httpServletRequest, claimNo);
			if (riskCode == null || riskCode.length() < 1 || riskCode.trim().equals("")) {
				riskCode = BusinessRuleUtil.getRiskCode(caseNo, "CaseNo");
			}
		}
		WorkFlowQueryDto workFlowQueryDto = new WorkFlowQueryDto();
		workFlowQueryDto.setClaimNo(claimNo);
		httpServletRequest.setAttribute("caseType", httpServletRequest.getParameter("caseType"));
		daaCompensateViewHelper.setPrpLcompensateToView(httpServletRequest, workFlowQueryDto);
		// 取得forward
		forward = BusinessRuleUtil.getForward(httpServletRequest, riskCode, "endca", editType, 1);
		return forward;
	}

	public DAAEndcaseViewHelper getDaaEndcaseViewHelper() {
		return daaEndcaseViewHelper;
	}

	public void setDaaEndcaseViewHelper(DAAEndcaseViewHelper daaEndcaseViewHelper) {
		this.daaEndcaseViewHelper = daaEndcaseViewHelper;
	}

	public EndcaseService getEndcaseService() {
		return endcaseService;
	}

	public void setEndcaseService(EndcaseService endcaseService) {
		this.endcaseService = endcaseService;
	}

	public DAACompensateViewHelper getDaaCompensateViewHelper() {
		return daaCompensateViewHelper;
	}

	public void setDaaCompensateViewHelper(DAACompensateViewHelper daaCompensateViewHelper) {
		this.daaCompensateViewHelper = daaCompensateViewHelper;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

}
