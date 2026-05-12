/*
 * @(#)BLSWFPathForAdvanceAction.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.facade;


import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author <中科软>
 * @Date <Feb 21, 2013>
 * @description
 */
public interface SwfPathForAdvanceService {

	

	/**
	 * 取得以某节点为起始节点的所有满足条件且优先级最高的路径以及路径的个数
	 * @param modelNo 模板号
	 * @param startNodeNo 起始节点号
	 * @param certiType 单证类型
	 * @param businessNo 业务号
	 * @param defaultFlag 是否缺省值--*0:否 1:是
	 * @param comCode 机构代码
	 * @throws UserException
	 * @throws Exception
	 * @return Collection
	 */
	public boolean getAdvancePathes(int modelNo, int startNodeNo, String certiType, String businessNo, String defaultFlag, String userCode) throws UserException, Exception;

	/**
	 * @递归向上获取路径条件
	 * @modify reason:原来查找算法根据前几位来获取，修改为根据PrpDcompany的上级机构UpperComCode获取路径条件
	 */
	public String getCondition(String iComCode, int iModelNo, int iPathNo) throws Exception;

}
