package com.sinosoft.claim.verifyLoss.service.facade;

import ins.framework.common.Page;
import com.sinosoft.claim.verifyLoss.vo.VerifyLossDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

/**
 * 核损信息处理接口
 * @author 中科软
 *
 */
public interface VerifyLossService {
	/**
	 * 保存核损
	 * @param VerifyLossDto：核损对象DTO
	 * @throws Exception
	 */
	public void save(VerifyLossDto verifyLossDto)throws Exception;
	/**
	 * 保存核损带工作流
	 * @param VerifyLossDto：核损对象DTO
	 * @throws Exception
	 */
	public void save(VerifyLossDto verifyLossDto,WorkFlowDto workFlowDto)throws Exception;
	/**
	 * 删除核损
	 * @param registNo：核损号
	 * @throws Exception
	 */
	public void delete(String registNo,String nodeType)throws Exception;
	/**
	 * 获得核损信息
	 * @param  registNo：核损号
	 * @return 核损对象
	 * @throws Exception
	 */
	public VerifyLossDto findByPrimaryKey(String registNo)throws Exception;
	
	/**
	 * 判断核损号是否存在
	 * @param registNo:核损号
	 * @return 是/否
	 * @throws Exception
	 */
	public boolean isExist(String registNo,String lossItemCode,String nodeType)throws Exception;
	/**
	 * 获得核损信息
	 * @param  registNo：核损号
	 * @return 核损对象
	 * @throws Exception
	 */
	public VerifyLossDto findByPrimaryKey(String registNo,String lossItemCode,String nodeType)throws Exception;
	/**
	 * 
	 * <b>function: 分页查询核损信息</b> 
	 * @Description: 
	 * @author 中科软
	 * @param conditions
	 * @param nodeType
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public Page findByCondition(String conditions,int pageNo,int recordPerPage)throws Exception;
	/**
	 * 保存工作流信息
	 * @param verifyLossDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveBpm(String jbpmNodeType,VerifyLossDto verifyLossDto, WorkFlowDto workFlowDto) throws Exception;
	/**
	 * 保存工作流信息
	 * @param verifyLossDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveBpm_verify_three(String jbpmNodeType,VerifyLossDto verifyLossDto, WorkFlowDto workFlowDto) throws Exception;
}
