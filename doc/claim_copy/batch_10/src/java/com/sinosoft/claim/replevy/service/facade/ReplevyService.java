/*
 * @(#)ReplevyService.java	Mar 11, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.replevy.service.facade;

import ins.framework.common.Page;

import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 
 */
public interface ReplevyService {
	/**
	 * 保存追偿信息
	 * @param compensateDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveBpm(CompensateDto compensateDto,WorkFlowDto workFlowDto)throws Exception;
	/**
	 * 审核通过
	 * @param businessNo
	 * @param nodeType
	 * @param compensateNo
	 * @param httpServletRequest
	 * @throws Exception
	 */
	public void saveUndwrtBpm(String nodeType,String compensateNo,WorkFlowDto workFlowDto) throws Exception;
	/***
	 * 分页查询待审核追偿
	 * @param string
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public Page findUndwrtByConditions(String string, int pageNo, int recordPerPage);
	/***
	 * 追償審覈駁回出單員修改
	 * @param compensateNo 計算書號
	 * @param workFlowDto
	 */
	public void saveUndwrtBack(String compensateNo, WorkFlowDto workFlowDto) throws Exception;
	/***
	 * 追償審批通過
	 * @param compensateNo  計算書號
	 * @param workFlowDto
	 */
	public void saveUndwrtPass(String compensateNo, WorkFlowDto workFlowDto) throws Exception;

}
