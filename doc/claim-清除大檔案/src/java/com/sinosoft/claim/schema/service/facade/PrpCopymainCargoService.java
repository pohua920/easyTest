package com.sinosoft.claim.schema.service.facade;

/**
 * 货运险保单信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCopymainCargo;

public interface PrpCopymainCargoService {

	/**
	 * 保存货运险保单信息
	 * @param prpLcheck ：传入的货运险保单信息
	 */
	public void save(PrpCopymainCargo prpCopymainCargo) throws Exception;

	/**
	 * 货运险保单信息
	 * @param list :传入的货运险保单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCopymainCargo> list) throws Exception;

	/**
	 * 删除货运险保单信息
	 * @param endorseNo ：传入的货运险保单信息编号
	 */
	public void delete(String endorseNo) throws Exception;

	/**
	 * 更新货运险保单信息
	 * @param prpCopymainCargo :传入需要更新的货运险保单信息
	 */
	public void update(PrpCopymainCargo prpCopymainCargo) throws Exception;

	/**
	 * 根据货运险保单信息编号查询出货运险保单信息
	 * @param prpCopymainCargoId ：传入的货运险保单信息编号
	 * @return 返回货运险保单信息
	 */
	public PrpCopymainCargo findPrpCopymainCargo(String endorseNo) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的货运险保单信息页面信息
	 */
	public Page findPrpCopymainCargo(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取货运险保单  的列表
	 * @param queryRule 查询对象
	 * @return 包含的货运险保单  的列表
	 */
	public List<PrpCopymainCargo> findPrpCopymainCargo(QueryRule queryRule) throws Exception;
}
