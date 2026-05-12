package com.sinosoft.claim.compensate.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.compensate.util.AccidentCompensateViewHelper;
import com.sinosoft.claim.compensate.util.SunnyCompensateViewHelper;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.schema.model.PrpLcfeecoins;
import com.sinosoft.claim.util.BusinessRuleUtil;

import ins.framework.web.Struts2Action;

/**
 * 联共保
 * @author 中科软
 */
public class CompensateCoinsPostEditAction extends Struts2Action {
	private static final long serialVersionUID = 1L;

	private CodeService codeService;
	private SunnyCompensateViewHelper sunnyCompensateViewHelper;
	private AccidentCompensateViewHelper accidentCompensateViewHelper;

	public String compensateCoinsPostEdit() throws Exception {
		HttpServletRequest request = super.getRequest();
		String jflag = request.getParameter("jflag");
//		String claimNo = request.getParameter("prpLcompensateClaimNo");
		String sumDutyPaid = request.getParameter("prpLcompensateSumDutyPaid");
//		String riskCode = BusinessRuleUtil.getRiskCode(claimNo, "ClaimNo");
//		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
//		String defaultCharacter = request.getCharacterEncoding();
		if (DataUtils.emptyToNull(jflag) != null) {//Ajax请求的默认编码
			request.setCharacterEncoding("UTF-8");
		}
		CompensateDto compensateDto = null;
//		if ("D".equals(strRiskType)) {
//			compensateDto = this.sunnyCompensateViewHelper.viewToDto(request);
//		} else {
//			if ("E".equals(strRiskType)) {
//				compensateDto = this.accidentCompensateViewHelper.viewToDtoForAccident(request);
//			} else {
//				compensateDto = this.accidentCompensateViewHelper.viewToDto(request);
//			}
//		}
		this.accidentCompensateViewHelper.coinsCreate(request, compensateDto, sumDutyPaid);
		if(DataUtils.emptyToNull(jflag)!=null){
			PrpLcfeecoins prpLcfeecoinsAll = (PrpLcfeecoins) request.getAttribute("prpLcfeecoinsAll");
			HttpServletResponse response = super.getResponse();
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(JSONObject.fromObject(prpLcfeecoinsAll).toString());
			return NONE;
		}
		return "coins";
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public SunnyCompensateViewHelper getSunnyCompensateViewHelper() {
		return sunnyCompensateViewHelper;
	}

	public void setSunnyCompensateViewHelper(SunnyCompensateViewHelper sunnyCompensateViewHelper) {
		this.sunnyCompensateViewHelper = sunnyCompensateViewHelper;
	}

	public AccidentCompensateViewHelper getAccidentCompensateViewHelper() {
		return accidentCompensateViewHelper;
	}

	public void setAccidentCompensateViewHelper(AccidentCompensateViewHelper accidentCompensateViewHelper) {
		this.accidentCompensateViewHelper = accidentCompensateViewHelper;
	}

}
