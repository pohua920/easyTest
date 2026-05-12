/*
 * @(#)GeneralClaimService.java	Mar 5, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.sendUndwrt.service.facade;

import com.sinosoft.claim.schema.model.UtiUwLevel;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public interface SendUndwrtService {
	/**
	 * 获得核赔权限对应的金额
	 */
	public String findFactorValue(String comCode, int nodeNo, String riskCode) throws Exception;

	/**
	 * 获得向上第一个核赔人员
	 */
	public UtiUwLevel findUpUwLevel(String comCode, int nodeNo, String riskCode) throws Exception;

	/**
	 * 送审初复核调派
	 */
	public void proxy(String flowID, int logNo, String toUserCode) throws Exception;
}
