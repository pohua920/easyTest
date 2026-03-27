/*
 * @(#)GeneralClaimService.java	Mar 5, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.service.facade;


import java.util.List;

import com.sinosoft.claim.generalClaim.vo.GeneralClaimDto;
import com.sinosoft.claim.schema.model.PrpLgeneralClaimTask;
import com.sinosoft.claim.schema.model.PrpLgeneralClaimTaskLog;
import com.sinosoft.claim.schema.model.SwfLog;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public interface GeneralClaimService {
	/**
	 * 当前案件是否通赔过，不在承保地。如果是异地，返回true,不是异地，返回false
	 * @param businessNo
	 * @throws Exception
	 * @return boolean
	 */
//	public boolean isGeneral(String businessNo, UserDto userDto) throws Exception;

	/**
	 * 取得该报案的相关信息
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
//	public List<Object> getClaimStatus(UserDto userDto, String registNo) throws Exception;

	/**
	 * 通赔委托提交
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
//	public void giveInsert(String registNo, String receiveComcode, String remark, UserDto userDto) throws Exception;

	/**
	 * 通赔接收查询
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
//	public Page receiveQuery(String conditions, UserDto userDto, int pageNo, int pageSize) throws Exception;

	/**
	 * 进入通赔接收处理页面
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
//	public Map<String, Object> prepareReceiveInsert(String registNo) throws Exception;

	/**
	 * 查询能够处理某一机构下拥有某项权限的操作员
	 * @throws Exception
	 * @return PageRecord
	 * @author 中科软
	 */
//	public Page queryUserHaveRights(String conditions, int pageNo, int pageSize) throws Exception;

	/**
	 * 通赔收回查询
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
//	public Page regainQuery(String conditions, UserDto userDto, int pageNo, int pageSize) throws Exception;

	/**
	 * 根据当前机构取得该机构的二级机构
	 * @param workFlowDto 理赔工作流流程处理处理任务取消的对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
//	public String getLevelTwoComCode(String comCode) throws SQLException, Exception;

	/**
	 * 根据报案号查询通赔待处理任务
	 * @param businessNo
	 * @throws Exception
	 * @return ArrayList
	 */
	public List<PrpLgeneralClaimTask> queryByRegistNo(String registNo) throws Exception;
	/**
	   * 根据报案号查询通赔已处理任务
	   * @param businessNo
	   * @throws Exception
	   * @return ArrayList
	   */
	public List<PrpLgeneralClaimTaskLog> queryHistoryByRegistNo(String registNo) throws Exception;
	/**
	 * 通赔委托提交
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 * @author 中科软
	 */
	public void giveInsert(List<SwfLog> swfLogDtoList,PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog) throws Exception;
	/**
	 * 通赔委托提交
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 * @author 中科软
	 */
	public void giveInsert(GeneralClaimDto generalClaimDto) throws Exception ;
	/**
	 * 通赔接收提交
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @author 中科软
	 */
//	public void receiveInsert(List swflogList, UserDto userDto) throws Exception;
	/**
	 * 通赔历史查询
	 * @param registNo：报案号
	 * @return Page
	 */
//	public Page historyQuery(UserDto userDto, String generalType, String conditions, int pageNo, int rowsPerPage) throws Exception;
}
