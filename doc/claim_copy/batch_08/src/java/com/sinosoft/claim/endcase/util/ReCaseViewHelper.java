package com.sinosoft.claim.endcase.util;

import javax.servlet.http.HttpServletRequest;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.endcase.service.facade.RecaseService;
import com.sinosoft.claim.endcase.vo.ReCaseDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * <p>
 * Title: EndcaseViewHelper
 * </p>
 * <p>
 * Description:结案ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */
public class ReCaseViewHelper {
	/** 重开赔案服务 */
	private RecaseService recaseService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 代码服务 */
	private CodeService codeService;
	/**
	 * 默认构造方法
	 */
	public ReCaseViewHelper() {
	}

	/**
	 * 从前台收集重开赔案信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	public ReCaseDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		String claimNo = httpServletRequest.getParameter("ClaimNo");
		String appRecaseReason = httpServletRequest.getParameter("appRecaseReason");

		int serialNo = 0;
		String conditions = "claimNo ='" + claimNo + "'";
		if (recaseService.findByConditions(conditions) != null) {
			int maxSerialNo = recaseService.getMaxSerialNo(claimNo);
			serialNo = maxSerialNo + 1;
		} else {
			serialNo = 1;
		}
		ReCaseDto reCaseDto = new ReCaseDto();
		// 给重开赔案表赋值
		PrpLrecase prpLrecase = new PrpLrecase();
		prpLrecase.getId().setClaimNo(claimNo);
		prpLrecase.getId().setSerialNo(serialNo);
		prpLrecase.setOpenCaseDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
		prpLrecase.setOpenCaseUserCode(user.getUserCode());
		prpLrecase.setOpenCaseComCode(user.getComCode());
		prpLrecase.setReCaseReason(appRecaseReason);
		reCaseDto.setPrpLrecase(prpLrecase);
		return reCaseDto;
	}

	/**
	 * 收集重开赔案的信息
	 * @param httpServletRequest
	 * @param claimNo
	 * @throws Exception
	 */
	public void claimNoDtoToView(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		//对操作员名称进行转换
		String endCaserCode = prpLclaim.getEndCaserCode();
		String endCaserName = codeService.translateUserCode(endCaserCode, true);
		prpLclaim.setEndCaserName(endCaserName);
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
	}

	/**
	 * 为增加是否允许重开赔案的判断作准备
	 * @param httpServletRequest
	 * @param claimNo
	 * @throws Exception
	 */
	public void recasemaxDtoToView(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		String conditions = "claimNo ='" + claimNo + "'";
		if (recaseService.findByConditions(conditions) != null) {
			int maxSerialNo = recaseService.getMaxSerialNo(claimNo);
			ReCaseDto reCasedto = recaseService.findByPrimaryKey(claimNo, maxSerialNo);
			PrpLrecase prpLrecase = reCasedto.getPrpLrecase();
			httpServletRequest.setAttribute("prpLrecase", prpLrecase);
		}
	}

	public RecaseService getRecaseService() {
		return recaseService;
	}

	public void setRecaseService(RecaseService recaseService) {
		this.recaseService = recaseService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}