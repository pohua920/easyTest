/*
 * @(#)BLWfCheckAdvanceFacade.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.facade;


/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public interface WfCheckAdvanceService {


	/**
	 * 检查条件
	 * @param ModelNo 模板号
	 * @param StartNodeNo 起始节点号
	 * @param BusinessType 业务类型
	 * @param BusinessNo 业务号
	 * @param DefaultFlag 默认标记
	 * @param userCode 用户代码
	 * @return
	 * @throws Exception
	 */
	public boolean checkAdvanceCondition(int ModelNo, int StartNodeNo, String BusinessType, String BusinessNo, String DefaultFlag, String userCode) throws Exception;

}
