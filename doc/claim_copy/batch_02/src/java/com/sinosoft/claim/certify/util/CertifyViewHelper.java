/*
 * @(#)CertifyViewHelper.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.certify.util;

import ins.framework.common.DateTime;
import ins.framework.utils.DataUtils;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.certify.vo.CertifyDto;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLcertifyCollect;
import com.sinosoft.claim.schema.model.PrpLcertifyCollectId;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public abstract class CertifyViewHelper {
	/**
	 * 默认构造方法
	 */
	public CertifyViewHelper() {
	}

	/**
	 * 保存单证时单证页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return stepFlag 标志
	 * @throws Exception
	 */
	public CertifyDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		CertifyDto certifyDto = new CertifyDto();
		PrpLcertifyCollect prpLcertifyCollect = new PrpLcertifyCollect();
		PrpLcertifyCollectId prpLcertifyCollectId = new PrpLcertifyCollectId();
		prpLcertifyCollect.setId(prpLcertifyCollectId);
		prpLcertifyCollectId.setBusinessNo(httpServletRequest.getParameter("prpLcertifyCollectBusinessNo"));
		prpLcertifyCollectId.setLossItemCode(httpServletRequest.getParameter("prpLcertifyCollectLossItemCode"));
		prpLcertifyCollect.setLossItemName(httpServletRequest.getParameter("prpLcertifyCollectLossItemName"));
		prpLcertifyCollect.setPicCount(new BigDecimal(0));
		prpLcertifyCollect.setStartDate(new DateTime(httpServletRequest.getParameter("prpLcertifyCollectStartDate"), DateTime.YEAR_TO_DAY));
		prpLcertifyCollect.setStartHour(httpServletRequest.getParameter("prpLcertifyCollectStartHour"));
		prpLcertifyCollect.setEndDate(new DateTime(httpServletRequest.getParameter("prpLcertifyCollectEndDate"), DateTime.YEAR_TO_DAY));
		prpLcertifyCollect.setEndHour(httpServletRequest.getParameter("prpLcertifyCollectEndHour"));
		prpLcertifyCollect.setOperatorCode(httpServletRequest.getParameter("prpLcertifyCollectOperatorCode"));
		prpLcertifyCollect.setCaseFlag(httpServletRequest.getParameter("prpLcertifyCollectCaseFlag"));
		prpLcertifyCollect.setFlag(httpServletRequest.getParameter("prpLcertifyCollectFlag"));
		prpLcertifyCollect.setUploadYear(httpServletRequest.getParameter("prpLcertifyCollectUploadYear"));
		prpLcertifyCollect.setRiskCode(httpServletRequest.getParameter("prpLcertifyCollectRiskCode"));

		int cltThirdCarCount = Integer.parseInt(DataUtils.nullToZero(httpServletRequest.getParameter("cltThirdCarCount")));
		String cltThirdCarFlag = "";
		for (int i2 = 1; i2 < cltThirdCarCount; i2++) {
			cltThirdCarFlag = cltThirdCarFlag + httpServletRequest.getParameter("cltThirdCarFlag" + i2);
		}
		prpLcertifyCollect.setCltThirdCarFlag(cltThirdCarFlag.trim());
		prpLcertifyCollect.setCltInsureCarFlag(httpServletRequest.getParameter("cltInsureCarFlag"));
		prpLcertifyCollect.setCltPersonFlag(httpServletRequest.getParameter("cltPersonFlag"));
		prpLcertifyCollect.setCltPropFlag(httpServletRequest.getParameter("cltPropFlag"));
		prpLcertifyCollect.setCltCarLossFlag(httpServletRequest.getParameter("cltCarLossFlag"));
		prpLcertifyCollect.setCltAllLossFlag(httpServletRequest.getParameter("cltAllLossFlag"));
		prpLcertifyCollect.setPolicyNo(httpServletRequest.getParameter("prpLcertifyCollectPolicyNo"));
		prpLcertifyCollect.setCollectFlag(httpServletRequest.getParameter("collectFlag"));
		prpLcertifyCollect.setContent(httpServletRequest.getParameter("prpLcertifyCollectContent"));

		certifyDto.setPrpLcertifyCollect(prpLcertifyCollect);
		/*---------------------状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		PrpLclaimStatusId prpLclaimStatusId = new PrpLclaimStatusId();
		prpLclaimStatus.setStatus(httpServletRequest.getParameter("buttonSaveType"));
		prpLclaimStatusId.setBusinessNo(prpLcertifyCollectId.getBusinessNo());
		prpLclaimStatusId.setNodeType("certi");
		prpLclaimStatusId.setSerialNo(0);
		prpLclaimStatus.setId(prpLclaimStatusId);
		prpLclaimStatus.setPolicyNo(prpLcertifyCollect.getPolicyNo());
		prpLclaimStatus.setRiskCode(BusinessRuleUtil.getRiskCode(prpLcertifyCollectId.getBusinessNo(), "RegistNo"));
		// 取得当前用户信息，写操作员信息到实赔中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(prpLcertifyCollect.getStartDate());
		prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		certifyDto.setPrpLclaimStatus(prpLclaimStatus);
		certifyDto.setNodeType(httpServletRequest.getParameter("nodeType"));
		return certifyDto;
	}

	/**
	 * 填写单证页面及查询单证request的生成.
	 * @param httpServletRequest 返回给页面的request
	 * @param certifyDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public abstract void dtoToView(HttpServletRequest httpServletRequest, CertifyDto certifyDto) throws Exception;
}
