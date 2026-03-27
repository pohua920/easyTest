package com.sinosoft.claim.schema.service.facade;
/**
 * 机动车险标的接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopyItemCar;
import com.sinosoft.claim.schema.model.PrpCopyItemCarId;

public interface PrpCopyItemCarService {
	
	/**
	 * 保存机动车险标的信息
	 * @param prpLcheck ：传入的机动车险标的
	 */
	public void save(PrpCopyItemCar prpCopyItemCar) throws Exception;
	
	/**
	 * 机动车险标的信息
	 * @param list  :传入的机动车险标的信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCopyItemCar> list) throws Exception;
	
	/**
	 * 删除机动车险标的信息
	 * @param prpCopyItemCarId ：传入的机动车险标的编号
	 */
	public void delete(PrpCopyItemCarId prpCopyItemCarId) throws Exception;

	/**
	 * 更新机动车险标的信息
	 * @param prpCopyItemCar :传入需要更新的机动车险标的
	 */
	public void update(PrpCopyItemCar prpCopyItemCar) throws Exception;

	/**
	 * 根据机动车险标的编号查询出机动车险标的信息
	 * @param prpCopyItemCarId ：传入的机动车险标的编号
	 * @return 返回机动车险标的
	 */
	public PrpCopyItemCar findPrpCopyItemCar(PrpCopyItemCarId prpCopyItemCarId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的机动车险标的页面信息
	 */
	public Page findPrpCopyItemCar(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  机动车险标的列表
	 * @param queryRule 查询对象
	 * @return 包含的 机动车险标 的列表
	 */
	public List<PrpCopyItemCar> findPrpCopyItemCar(QueryRule queryRule) throws Exception;
}
