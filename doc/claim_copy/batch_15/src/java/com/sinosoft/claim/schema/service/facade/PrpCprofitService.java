package com.sinosoft.claim.schema.service.facade;

/**
 * 优惠信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCprofit;
import com.sinosoft.claim.schema.model.PrpCprofitId;

public interface PrpCprofitService {

	/**
	 * 保存优惠信息
	 * @param prpLcheck ：传入的优惠信息
	 */
	public void save(PrpCprofit prpCprofit) throws Exception;

	/**
	 * 优惠信息
	 * @param list :传入的优惠信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCprofit> list) throws Exception;

	/**
	 * 删除优惠信息
	 * @param prpCprofitId ：传入的优惠信息编号
	 */
	public void delete(PrpCprofitId prpCprofitId) throws Exception;

	/**
	 * 更新优惠信息
	 * @param prpCprofit :传入需要更新的优惠信息
	 */
	public void update(PrpCprofit prpCprofit) throws Exception;

	/**
	 * 根据优惠信息编号查询出优惠信息
	 * @param prpCprofitId ：传入的优惠信息编号
	 * @return 返回优惠信息
	 */
	public PrpCprofit findPrpCprofit(PrpCprofitId prpCprofitId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的优惠信息页面信息
	 */
	public Page findPrpCprofit(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 优惠信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的 优惠信息 的列表
	 */
	public List<PrpCprofit> findPrpCprofit(QueryRule queryRule) throws Exception;
}
