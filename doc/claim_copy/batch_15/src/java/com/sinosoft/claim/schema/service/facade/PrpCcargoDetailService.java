package com.sinosoft.claim.schema.service.facade;
/**
 * 货运险货运明细信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCcargoDetail;
import com.sinosoft.claim.schema.model.PrpCcargoDetailId;

public interface PrpCcargoDetailService {
	
	/**
	 * 保存货运险货运明细信息信息
	 * @param PrpCcargoDetail ：传入的货运险货运明细信息
	 */
	public void save(PrpCcargoDetail PrpCcargoDetail) throws Exception;
	
	/**
	 * 货运险货运明细信息信息
	 * @param list  :传入的货运险货运明细信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCcargoDetail> list) throws Exception;
	
	/**
	 * 删除货运险货运明细信息信息
	 * @param PrpCcargoDetailId ：传入的货运险货运明细信息编号
	 */
	public void delete(PrpCcargoDetailId PrpCcargoDetailId) throws Exception;

	/**
	 * 更新货运险货运明细信息信息
	 * @param PrpCcargoDetail :传入需要更新的货运险货运明细信息
	 */
	public void update(PrpCcargoDetail PrpCcargoDetail) throws Exception;

	/**
	 * 根据货运险货运明细信息编号查询出货运险货运明细信息信息
	 * @param PrpCcargoDetailId ：传入的货运险货运明细信息编号
	 * @return 返回货运险货运明细信息
	 */
	public PrpCcargoDetail findPrpCcargoDetail(PrpCcargoDetailId PrpCcargoDetailId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的货运险货运明细信息页面信息
	 */
	public Page findPrpCcargoDetail(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取货运险货运明细信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的货运险货运明细信息的列表
	 */
	public List<PrpCcargoDetail> findPrpCcargoDetail(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据货运险货运明细信息编号查询出货运险货运明细信息信息
	 * @param certiNo ：传入的货运险货运明细信息编号
	 * @return 返回货运险货运明细信息
	 */
	public PrpCcargoDetail findPrpCcargoDetail(String certiNo) throws Exception;
}
