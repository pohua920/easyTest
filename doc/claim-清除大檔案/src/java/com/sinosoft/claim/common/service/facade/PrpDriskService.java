package com.sinosoft.claim.common.service.facade;

/**
 *险种表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDrisk;

public interface PrpDriskService {
	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @return Collection 包含prpDrisk的集合
	 * @throws Exception
	 */
	public List<PrpDrisk> findByConditions(String conditions);

	/**
	 * 根据险种编号查询出险种信息
	 * @param riskCode ：传入的险种编号
	 * @return 返回险种
	 */
	public PrpDrisk findPrpDrisk(String riskCode);

	/**
	 * 保存险种信息
	 * @param prpDrisk ：传入的险种
	 */
	public void save(PrpDrisk prpDrisk) throws Exception;

	/**
	 * 保存或修改
	 * @param prpDrisk 险类
	 * @throws Exception
	 */
	public void saveOrUpdate(PrpDrisk prpDrisk) throws Exception;

	/**
	 * 删除险种信息
	 * @param riskCode ：传入的险种编号
	 */
	public void delete(String riskCode) throws Exception;

	/**
	 * 保存险种信息
	 * @param list:保存险种信息
	 */
	public void save(List<PrpDrisk> list) throws Exception;

	/**
	 * @description: 险种修改
	 * @param PrpDrisk prpDrisk
	 * @throws Exception
	 */
	public void update(PrpDrisk prpDrisk);

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的险种页面信息
	 */
	public Page findPrpDrisk(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的险种页面信息
	 */
	public Page findPrpDrisk(String conditions, int pageNo, int pageSize) throws Exception;

	/**
	 * 按条件查询多条数据
	 * @param queryRule 查询条件
	 * @return Collection 包含prpDrisk的集合
	 * @throws Exception
	 */
	public List<PrpDrisk> findPrpDrisk(QueryRule queryRule) throws Exception;

	/**
     * 通过险种大类从PrpDclass表查出该大类的所有ClassCode，
     * 再从PrpDrisk表查出这些ClassCode的所有RiskCode，展现在页面上。
     * @return
	 * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public List findRiskCodeByRiskCategory() throws Exception;
}
