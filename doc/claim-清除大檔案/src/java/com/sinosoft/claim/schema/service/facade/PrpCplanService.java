package com.sinosoft.claim.schema.service.facade;

/**
 * 收费计划接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCplan;
import com.sinosoft.claim.schema.model.PrpCplanId;

public interface PrpCplanService {

	/**
	 * 保存收费计划信息
	 * @param prpLcheck ：传入的收费计划
	 */
	public void save(PrpCplan prpCplan) throws Exception;

	/**
	 * 收费计划信息
	 * @param list :传入的收费计划信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCplan> list) throws Exception;

	/**
	 * 删除收费计划信息
	 * @param prpCplanId ：传入的收费计划编号
	 */
	public void delete(PrpCplanId prpCplanId) throws Exception;

	/**
	 * 更新收费计划信息
	 * @param prpCplan :传入需要更新的收费计划
	 */
	public void update(PrpCplan prpCplan) throws Exception;

	/**
	 * 根据收费计划编号查询出收费计划信息
	 * @param prpCplanId ：传入的收费计划编号
	 * @return 返回收费计划
	 */
	public PrpCplan findPrpCplan(PrpCplanId prpCplanId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的收费计划页面信息
	 */
	public Page findPrpCplan(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 收费计划 的列表
	 * @param queryRule 查询对象
	 * @return 包含的收费计划  的列表
	 */
	public List<PrpCplan> findPrpCplan(QueryRule queryRule) throws Exception;
}
