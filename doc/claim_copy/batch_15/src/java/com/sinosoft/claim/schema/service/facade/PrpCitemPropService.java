package com.sinosoft.claim.schema.service.facade;

/**
 * 财产险标的信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCitemProp;
import com.sinosoft.claim.schema.model.PrpCitemPropId;

public interface PrpCitemPropService {

	/**
	 * 保存财产险标的信息
	 * @param prpLcheck ：传入的财产险标的信息
	 */
	public void save(PrpCitemProp prpCitemProp) throws Exception;

	/**
	 * 财产险标的信息
	 * @param list :传入的财产险标的信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCitemProp> list) throws Exception;

	/**
	 * 删除财产险标的信息
	 * @param prpCitemPropId ：传入的财产险标的信息编号
	 */
	public void delete(PrpCitemPropId prpCitemPropId) throws Exception;

	/**
	 * 更新财产险标的信息
	 * @param prpCitemProp :传入需要更新的财产险标的信息
	 */
	public void update(PrpCitemProp prpCitemProp) throws Exception;

	/**
	 * 根据财产险标的信息编号查询出财产险标的信息
	 * @param prpCitemPropId ：传入的财产险标的信息编号
	 * @return 返回财产险标的信息
	 */
	public PrpCitemProp findPrpCitemProp(PrpCitemPropId prpCitemPropId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的财产险标的信息页面信息
	 */
	public Page findPrpCitemProp(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取财产险标的  的列表
	 * @param queryRule 查询对象
	 * @return 包含的财产险标的  的列表
	 */
	public List<PrpCitemProp> findPrpCitemProp(QueryRule queryRule) throws Exception;
}
