package com.sinosoft.claim.compensate.web;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.compensate.util.AccidentCompensateViewHelper;
import com.sinosoft.claim.compensate.util.CompensateGenerateImplDubangViewHelper;
import com.sinosoft.claim.compensate.util.CompensateGenerateViewHelper;
import com.sinosoft.claim.compensate.util.SunnyCompensateViewHelper;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.util.BusinessRuleUtil;
import com.sinosoft.sysframework.exceptionlog.UserException;

import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

/**
 * 生成理算报告
 * @Description 
 * @author 中科软
 * @date Mar 5, 2013 9:56:50 PM
 */
public class CompensateGeneratePostAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/** 理算数据整理ViewHelper */
	private SunnyCompensateViewHelper sunnyCompensateViewHelper;
	/** 理算报告数据整理ViewHelper */
	private CompensateGenerateViewHelper compensateGenerateImplCompelViewHelper;
	/** 理算报告数据整理ViewHelper */
	private CompensateGenerateImplDubangViewHelper compensateGenerateImplDubangViewHelper;
	/** 意健险理算数据整理ViewHelper */
	private AccidentCompensateViewHelper accidentCompensateViewHelper;
	/** 代码处理Service */
	private CodeService codeService;

	/**
	 * 生成理算报告处理
	 * @return
	 * @throws Exception
	 */
	public String compensateGeneratePost() throws Exception {
		try {
			HttpServletRequest request = super.getRequest();
			// 取赔款计算书号
			String compensateNo = request.getParameter("prpLcompensateClaimNo");
			String compelFlag = request.getParameter("compelFlag");
			if (DataUtils.emptyToNull(compensateNo) == null && DataUtils.emptyToNull(compelFlag) != null) {
				compensateNo = request.getParameter("policyNo");
				if (DataUtils.emptyToNull(compensateNo) != null && "notCompel".equals(compelFlag)) {
					CompensateDto compensateDto = this.sunnyCompensateViewHelper.quickCaseViewToDto(request);
					this.compensateGenerateImplDubangViewHelper.quickCaseCompensateGenerate(request, compensateDto);
				}
				String compelCompensateNo = request.getParameter("prpLRegistRPolicyNo");

				if (DataUtils.emptyToNull(compelCompensateNo) != null && "compel".equals(compelFlag)) {
					CompensateDto compensateDto = this.sunnyCompensateViewHelper.compelViewToDto(request);
					this.compensateGenerateImplCompelViewHelper.compensateGenerate(request, compensateDto);
				}
			} else {
				logger.debug("產生理算报告的立案号是:" + compensateNo);
				request.setAttribute("compensateNo", compensateNo);
				// 用viewHelper整理界面输入
				String riskCode = BusinessRuleUtil.getRiskCode(compensateNo, "ClaimNo");
				// 增加0503==DAC的险种
				String strRiskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
				String strConfigCode = this.getCodeService().translateRiskCodetoConfigCode(riskCode);
				if ("D".equals(strRiskType)) {
					CompensateDto compensateDto = this.sunnyCompensateViewHelper.viewToDto(request);
					if ("RISKCODE_DAZ".equals(strConfigCode)) {
						// 获得强制保险理算报告书的生成类实例
						this.compensateGenerateImplCompelViewHelper.compensateGenerate(request, compensateDto);
					} else {
						// 获得商业保险理算报告书的生成类实例
						this.compensateGenerateImplDubangViewHelper.compensateGenerate(request, compensateDto);
					}
				} else {
					CompensateDto compensateDto = null;
					if ("E".equals(strRiskType)) {
						compensateDto = this.accidentCompensateViewHelper.viewToDtoForAccident(request);
					} else {
						compensateDto = this.accidentCompensateViewHelper.viewToDto(request);
					}
					this.accidentCompensateViewHelper.generateCtext(request, compensateDto);
				}
			}
		} catch (Exception e) {
			if (e instanceof UserException) {
				UserException ex = (UserException) e;
				super.getRequest().setAttribute("compensateMessage", ex.getErrorMessage());
			} else {
				e.printStackTrace();
			}
		}
		return "generate";
	}

	public SunnyCompensateViewHelper getSunnyCompensateViewHelper() {
		return sunnyCompensateViewHelper;
	}

	public void setSunnyCompensateViewHelper(SunnyCompensateViewHelper sunnyCompensateViewHelper) {
		this.sunnyCompensateViewHelper = sunnyCompensateViewHelper;
	}

	public CompensateGenerateViewHelper getCompensateGenerateImplCompelViewHelper() {
		return compensateGenerateImplCompelViewHelper;
	}

	public void setCompensateGenerateImplCompelViewHelper(CompensateGenerateViewHelper compensateGenerateImplCompelViewHelper) {
		this.compensateGenerateImplCompelViewHelper = compensateGenerateImplCompelViewHelper;
	}

	public CompensateGenerateImplDubangViewHelper getCompensateGenerateImplDubangViewHelper() {
		return compensateGenerateImplDubangViewHelper;
	}

	public void setCompensateGenerateImplDubangViewHelper(CompensateGenerateImplDubangViewHelper compensateGenerateImplDubangViewHelper) {
		this.compensateGenerateImplDubangViewHelper = compensateGenerateImplDubangViewHelper;
	}

	public AccidentCompensateViewHelper getAccidentCompensateViewHelper() {
		return accidentCompensateViewHelper;
	}

	public void setAccidentCompensateViewHelper(AccidentCompensateViewHelper accidentCompensateViewHelper) {
		this.accidentCompensateViewHelper = accidentCompensateViewHelper;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
}
