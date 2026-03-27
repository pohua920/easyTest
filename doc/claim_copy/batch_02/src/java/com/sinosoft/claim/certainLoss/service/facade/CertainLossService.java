package com.sinosoft.claim.certainLoss.service.facade;

import ins.framework.common.Page;

import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
  
/**
 * 定损对象CertainLoss
 * <p>
 * Title: 车险理赔样本定损action
 * </p>
 * <p>
 * Description: 车险理赔样本定损action
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * @author 中科软
 * </p>
 */
public interface CertainLossService {
	/**
	 * 保存定损
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(CertainLossDto certainLossDto) throws SQLException, Exception;

	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception;

	/**
	 * 删除定损
	 * @param registNo
	 * @param dbManager 数据连接
	 * @throws SQLException
	 * @throws Exception
	 */
	public void delete(String registNo) throws SQLException, Exception;

	/**
	 * 获得定损
	 * @param registNo
	 * @param dbManager 数据连接
	 * @return 自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public CertainLossDto findByPrimaryKey(String registNo, String lossItemCode,String nodeType) throws SQLException, Exception;

	/**
	 * 获得定损
	 * @param registNo
	 * @param dbManager 数据连接
	 * @return 自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public CertainLossDto findByPrimaryKey(String registNo) throws SQLException, Exception;

	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(CertainLossDto certainLossDto, CheckDto checkDto) throws SQLException, Exception;

	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(CertainLossDto certainLossDto, CheckDto checkDto, WorkFlowDto workFlowDto) throws SQLException, Exception;

	/**
     *
	 * 定损查询
	 * @Description: 
	 * @author 中科软
	 * @param conditions
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public Page findByQueryConditions(String conditions, int pageNo, int recordPerPage) throws Exception;
	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void saveBpm(CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception;
	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception,
	 * 人伤
	 */
	public void saveBpm_wound(CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception;
	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception,
	 *财产
	 */

	public void saveBpm_propc(CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception;
	/**
	 * 保存定损带工作流
	 * @param certainLossDto：自定义定损对象
	 * @throws SQLException
	 * @throws Exception,
	 *三者
	 */
	public void saveBpm_certa_three(CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception;
	/**
	 * 获得定损
	 * @param registNo
	 * * @param underWriteEndDate 核损时间
	 * @return 自定义定损对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public CertainLossDto findByUnderWriteEndDate(String registNo,Date underWriteEndDate) throws SQLException, Exception;
}
