package com.sinosoft.claim.schema.service.facade;

/**
 * 单证号使用登记接口
 * @author 中科软
 */
import java.util.List;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import com.sinosoft.claim.schema.model.PrpMaxUse;
import com.sinosoft.claim.schema.model.PrpMaxUseId;

public interface PrpMaxUseService {

	/**
	 * 保存单证号使用登记信息
	 * @param prpMaxUse ：传入的单证号使用登记
	 */
	public void save(PrpMaxUse prpMaxUse) throws Exception;

	/**
	 * 单证号使用登记信息
	 * @param list :传入的单证号使用登记信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpMaxUse> list) throws Exception;

	/**
	 * 删除单证号使用登记信息
	 * @param prpMaxUseId ：传入的单证号使用登记编号
	 */
	public void delete(PrpMaxUseId prpMaxUseId) throws Exception;

	/**
	 * 更新单证号使用登记信息
	 * @param prpMaxUse :传入需要更新的单证号使用登记
	 */
	public void update(PrpMaxUse prpMaxUse) throws Exception;

	/**
	 * 根据单证号使用登记编号查询出单证号使用登记信息
	 * @param prpMaxUseId ：传入的单证号使用登记编号
	 * @return 返回单证号使用登记
	 */
	public PrpMaxUse findPrpMaxUse(PrpMaxUseId prpMaxUseId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的单证号使用登记页面信息
	 */
	public Page findPrpMaxUse(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取单证号使用登记信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  单证号使用登记信息的集合
	 */
	public List<PrpMaxUse> findPrpMaxUse(QueryRule queryRule) throws Exception;
	/**
	 * 保存单证号使用登记信息
	 * @param prpMaxUse ：传入的单证号使用登记
	 */
	public void saveByNewTransaction(PrpMaxUse prpMaxUse) throws Exception;
	/**
	 * 删除单证号使用登记信息
	 * @param prpMaxUseId ：传入的单证号使用登记编号
	 */
	public void deleteByNewTransaction(PrpMaxUseId prpMaxUseId) throws Exception;
}
