/*
 * @(#)CertifyPayeeViewHelper.java	Jan 24, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.certify.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.schema.model.PrpLcertifyPayee;
import com.sinosoft.claim.schema.model.PrpLcertifyPayeeId;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyPayeeService;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class CertifyPayeeViewHelper {

	/** 领款人信息服务 */
	private PrpLcertifyPayeeService prpLcertifyPayeeService;
	/** 代码服务 */
	private CodeService codeService;

	/**
	 * 上传单证
	 * @param httpServletRequest
	 * @throws Exception
	 */
	public void insert(HttpServletRequest httpServletRequest) throws Exception {
		this.prpLcertifyPayeeService.save(viewTODtoList(httpServletRequest));
	}

	/**
	 * 
	 * 保存单证时单证页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	public List<PrpLcertifyPayee> viewTODtoList(HttpServletRequest httpServletRequest) throws Exception {

		String registNo = httpServletRequest.getParameter("RegistNo");
		String claimNo = codeService.translateBusinessCode(registNo, true);
		String policyNo = httpServletRequest.getParameter("policyNo");
		String riskCode = httpServletRequest.getParameter("riskCode");

		String[] payeeTypeCode = httpServletRequest.getParameterValues("prpLcertifyPayeePayeeTypeCode");
		String[] payeeTypeName = httpServletRequest.getParameterValues("prpLcertifyPayeePayeeTypeName");

		String[] relationsCode = httpServletRequest.getParameterValues("prpLcertifyPayeeRelationsCode");
		String[] relationsName = httpServletRequest.getParameterValues("prpLcertifyPayeeRelationsName");

		String[] payeeName = httpServletRequest.getParameterValues("prpLcertifyPayeePayeeName");
		String[] licenseTypeCode = httpServletRequest.getParameterValues("prpLcertifyPayeeLicenseTypeCode");
		String[] licenseTypeName = httpServletRequest.getParameterValues("prpLcertifyPayeeLicenseTypeName");

		String[] licenseCode = httpServletRequest.getParameterValues("prpLcertifyPayeeLicenseCode");
		String[] linker = httpServletRequest.getParameterValues("prpLcertifyPayeeLinker");
		String[] bankType = httpServletRequest.getParameterValues("prpLcertifyPayeeBankType");
		String[] bankCardNo = httpServletRequest.getParameterValues("prpLcertifyPayeeBankCardNo");
		String[] linkerTel = httpServletRequest.getParameterValues("prpLcertifyPayeeLinkerTel");
		List<PrpLcertifyPayee> prpLcertifyPayeeList = new ArrayList<PrpLcertifyPayee>();
		PrpLcertifyPayee prpLcertifyPayee = null;
		PrpLcertifyPayeeId prpLcertifyPayeeId = null;
		for (int i = 1; i < payeeTypeCode.length; i++) {
			prpLcertifyPayee = new PrpLcertifyPayee();
			prpLcertifyPayeeId = new PrpLcertifyPayeeId();
			prpLcertifyPayeeId.setRegistNo(registNo);
			prpLcertifyPayeeId.setPolicyNo(policyNo);
			prpLcertifyPayeeId.setSerialNo(new BigDecimal(i));
			prpLcertifyPayee.setId(prpLcertifyPayeeId);
			prpLcertifyPayee.setClaimNo(claimNo);
			prpLcertifyPayee.setRiskCode(riskCode);
			prpLcertifyPayee.setPayeeTypeCode(payeeTypeCode[i]);
			prpLcertifyPayee.setPayeeTypeName(payeeTypeName[i]);
			prpLcertifyPayee.setRelationsCode(relationsCode[i]);
			prpLcertifyPayee.setRelationsName(relationsName[i]);
			prpLcertifyPayee.setPayeeName(payeeName[i]);
			prpLcertifyPayee.setLicenseTypeCode(licenseTypeCode[i]);
			prpLcertifyPayee.setLicenseTypeName(licenseTypeName[i]);
			prpLcertifyPayee.setLicenseCode(licenseCode[i]);
			prpLcertifyPayee.setLinker(linker[i]);
			prpLcertifyPayee.setBankType(bankType[i]);
			prpLcertifyPayee.setBankCardNo(bankCardNo[i]);
			prpLcertifyPayee.setLinkerTel(linkerTel[i]);
			prpLcertifyPayeeList.add(prpLcertifyPayee);
		}
		return prpLcertifyPayeeList;
	}

	public PrpLcertifyPayeeService getPrpLcertifyPayeeService() {
		return prpLcertifyPayeeService;
	}

	public void setPrpLcertifyPayeeService(PrpLcertifyPayeeService prpLcertifyPayeeService) {
		this.prpLcertifyPayeeService = prpLcertifyPayeeService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
