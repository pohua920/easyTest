/*
 * @(#)PrpLregistLogService.java	Jan 30, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.schema.service.facade;
/**
 * 报案修改轨迹信息表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLregistLog;
import com.sinosoft.claim.schema.model.PrpLregistLogId;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 
 */
public interface PrpLregistLogService {
	/**
	 * @param queryRule
	 * @return
	 * @throws Exception
	 * 根据条件查询
	 */
	public List<PrpLregistLog> findByQuery(QueryRule queryRule)throws Exception;
	/**
	 * @param logId
	 * @param registNo
	 * @throws Exception
	 * 保存轨迹信息
	 */
	public void save(String logId,String registNo)throws Exception;
	
	/**
	 * 保存报案修改轨迹信息
	 * @param prpLthirdCarLoss ：传入的报案修改轨迹
	 */
	public void save(PrpLregistLog prpLregistLog) throws Exception;
	
	/**
	 * 保存报案修改轨迹信息
	 * @param list:保存报案修改轨迹信息
	 */
	public void save(List<PrpLregistLog> list) throws Exception;
	
	/**
	 * 删除报案修改轨迹信息
	 * @param prpLthirdCarLossId ：传入的报案修改轨迹编号
	 */
	public void delete(PrpLregistLogId prpLregistLogId) throws Exception;

	/**
	 * 更新报案修改轨迹信息
	 * @param prpLthirdCarLoss :传入需要更新的报案修改轨迹
	 */
	public void update(PrpLregistLog prpLregistLog) throws Exception;

	/**
	 * 根据报案修改轨迹编号查询出报案修改轨迹信息
	 * @param prpLthirdCarLossId ：传入的报案修改轨迹编号
	 * @return 返回报案修改轨迹
	 */
	public PrpLregistLog findPrpLregistLog(PrpLregistLogId prpLregistLogId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的报案修改轨迹页面信息
	 */
	public Page findPrpLregistLog(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	public List<PrpLregistLog> findPrpLregistLog(QueryRule queryRule) throws Exception;

}
