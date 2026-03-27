package com.sinosoft.claim.schema.service.facade;

/**
 * 货运险保单信息明细接口
 * @author 中科软
 */
import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainCarGoSub;

public interface PrpCmainCarGoSubService {

	/**
	 * 根据货运险保单信息编号查询出货运险保单信息
	 * @param prpCmainCargoId ：传入的货运险保单信息编号
	 * @return 返回货运险保单信息
	 */
	public PrpCmainCarGoSub findPrpCmainCarGoSub(String policyNo,Integer serialNo) throws Exception;

	/**
	 * 根据查询对象获取货运险保单 的列表
	 * @param queryRule 查询对象
	 * @return 包含的货运险保单 的列表
	 */
	public List<PrpCmainCarGoSub> findPrpCmainCarGoSub(String conditions) throws Exception;
}
