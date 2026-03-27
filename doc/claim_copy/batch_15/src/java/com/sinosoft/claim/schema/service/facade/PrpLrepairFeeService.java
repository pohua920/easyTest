package com.sinosoft.claim.schema.service.facade;
/**
 * 修理费用清单接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLrepairFee;
import com.sinosoft.claim.schema.model.PrpLrepairFeeId;

public interface PrpLrepairFeeService {
	
	/**
	 * 保存修理费用清单信息
	 * @param prpLrepairFee ：传入的修理费用清单
	 */
	public void save(PrpLrepairFee prpLrepairFee) throws Exception;
	
	/**
	 * 修理费用清单信息
	 * @param list  :传入的修理费用清单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLrepairFee> list) throws Exception;
	
	/**
	 * 删除修理费用清单信息
	 * @param prpLrepairFeeId ：传入的修理费用清单编号
	 */
	public void delete(PrpLrepairFeeId prpLrepairFeeId) throws Exception;

	/**
	 * 更新修理费用清单信息
	 * @param prpLrepairFee :传入需要更新的修理费用清单
	 */
	public void update(PrpLrepairFee prpLrepairFee) throws Exception;

	/**
	 * 根据修理费用清单编号查询出修理费用清单信息
	 * @param prpLrepairFeeId ：传入的修理费用清单编号
	 * @return 返回修理费用清单
	 */
	public PrpLrepairFee findPrpLrepairFee(PrpLrepairFeeId prpLrepairFeeId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的修理费用清单页面信息
	 */
	public Page findPrpLrepairFee(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取修理费用清单  的集合
	 * @param queryRule 查询对象
	 * @return 包含的 修理费用清单 的集合
	 */
	public List<PrpLrepairFee> findPrpLrepairFee(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据修理费用清单编号查询出修理费用清单信息
	 * @param certiNo ：传入的修理费用清单编号
	 * @return 返回修理费用清单
	 */
	public PrpLrepairFee findPrpLrepairFee(String certiNo) throws Exception;
}
