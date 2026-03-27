package com.sinosoft.claim.schema.service.facade;
/**
 * 车辆定损接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLcarLoss;
import com.sinosoft.claim.schema.model.PrpLcarLossId;

public interface PrpLcarLossService {
	
	/**
	 * 保存车辆定损信息
	 * @param prpLcarLoss ：传入的车辆定损
	 */
	public void save(PrpLcarLoss prpLcarLoss) throws Exception;
	
	/**
	 * 车辆定损信息
	 * @param list  :传入的车辆定损信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcarLoss> list) throws Exception;
	
	/**
	 * 删除车辆定损信息
	 * @param prpLcarLossId ：传入的车辆定损编号
	 */
	public void delete(PrpLcarLossId prpLcarLossId) throws Exception;

	/**
	 * 更新车辆定损信息
	 * @param prpLcarLoss :传入需要更新的车辆定损
	 */
	public void update(PrpLcarLoss prpLcarLoss) throws Exception;

	/**
	 * 根据车辆定损编号查询出车辆定损信息
	 * @param prpLcarLossId ：传入的车辆定损编号
	 * @return 返回车辆定损
	 */
	public PrpLcarLoss findPrpLcarLoss(PrpLcarLossId prpLcarLossId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的车辆定损页面信息
	 */
	public Page findPrpLcarLoss(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 车辆定损 的列表
	 * @param queryRule 查询对象
	 * @return 包含的 车辆定损 的列表
	 */
	public List<PrpLcarLoss> findPrpLcarLoss(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据车辆定损编号查询出车辆定损信息
	 * @param certiNo ：传入的车辆定损编号
	 * @return 返回车辆定损
	 */
	public PrpLcarLoss findPrpLcarLoss(String certiNo) throws Exception;
}
