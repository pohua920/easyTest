package com.sinosoft.claim.schema.service.facade;

/**
 * 货运险保单信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCmainCargo;

public interface PrpCmainCargoService {

	/**
	 * 保存货运险保单信息
	 * @param prpLcheck ：传入的货运险保单信息
	 */
	public void save(PrpCmainCargo prpCmainCargo) throws Exception;

	/**
	 * 货运险保单信息
	 * @param list :传入的货运险保单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCmainCargo> list) throws Exception;

	/**
	 * 删除货运险保单信息
	 * @param prpCmainCargoId ：传入的货运险保单信息编号
	 */
	public void delete(String policyNo) throws Exception;

	/**
	 * 更新货运险保单信息
	 * @param prpCmainCargo :传入需要更新的货运险保单信息
	 */
	public void update(PrpCmainCargo prpCmainCargo) throws Exception;

	/**
	 * 根据货运险保单信息编号查询出货运险保单信息
	 * @param prpCmainCargoId ：传入的货运险保单信息编号
	 * @return 返回货运险保单信息
	 */
	public PrpCmainCargo findPrpCmainCargo(String policyNo) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的货运险保单信息页面信息
	 */
	public Page findPrpCmainCargo(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取货运险保单  的列表
	 * @param queryRule 查询对象
	 * @return 包含的货运险保单  的列表
	 */
	public List<PrpCmainCargo> findPrpCmainCargo(QueryRule queryRule) throws Exception;
}
