package com.sinosoft.claim.schema.service.facade;

/**
 * 建安工险保单信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCmainConstruct;

public interface PrpCmainConstructService {

	/**
	 * 保存建安工险保单信息
	 * @param prpLcheck ：传入的建安工险保单信息
	 */
	public void save(PrpCmainConstruct prpCmainConstruct) throws Exception;

	/**
	 * 建安工险保单信息
	 * @param list :传入的建安工险保单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCmainConstruct> list) throws Exception;

	/**
	 * 删除建安工险保单信息
	 * @param prpCmainConstructId ：传入的建安工险保单信息编号
	 */
	public void delete(String policyNo) throws Exception;

	/**
	 * 更新建安工险保单信息
	 * @param prpCmainConstruct :传入需要更新的建安工险保单信息
	 */
	public void update(PrpCmainConstruct prpCmainConstruct) throws Exception;

	/**
	 * 根据建安工险保单信息编号查询出建安工险保单信息
	 * @param prpCmainConstructId ：传入的建安工险保单信息编号
	 * @return 返回建安工险保单信息
	 */
	public PrpCmainConstruct findPrpCmainConstruct(String policyNo) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的建安工险保单信息页面信息
	 */
	public Page findPrpCmainConstruct(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取建安工险保单信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的 建安工险保单信息 的列表
	 */
	public List<PrpCmainConstruct> findPrpCmainConstruct(QueryRule queryRule) throws Exception;
}
