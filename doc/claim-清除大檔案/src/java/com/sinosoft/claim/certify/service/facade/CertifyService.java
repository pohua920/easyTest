/*
 * @(#)CertifyService.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.certify.service.facade;

import java.sql.SQLException;

import com.sinosoft.claim.certify.vo.CertifyDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 
 */
public interface CertifyService {
	/**
	 * 获得单证信息
	 * @param  certifyNo：单证号
	 * @return 单证对象
	 * @throws Exception
	 */
	public CertifyDto findCertifyDto(String registNo) throws SQLException,UserException,Exception;
	
	/**
	 * 保存单证信息
	 * @param certifyDto
	 * @throws Exception
	 */
	public void save(CertifyDto certifyDto) throws Exception;
	/**
	 * 保存单证信息,带工作流的
	 * @param certifyDto
	 * @throws Exception
	 */
	public void save(CertifyDto certifyDto,WorkFlowDto workFlowDto) throws Exception;
	/**
	 * 保存单证信息,带工作流的
	 * @param certifyDto
	 * @throws Exception
	 */
	public void saveBpm(CertifyDto certifyDto,String relatedClaim,WorkFlowDto workFlowDto) throws Exception;

}
