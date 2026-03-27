package com.sinosoft.claim.schema.service.facade;

/**
 * 键值信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.UtiKey;

public interface UtiKeyService {

	/**
	 * 键值信息信息
	 * @param UtiKey ：传入的键值信息
	 */
	public void save(UtiKey utiKey) throws Exception;

	/**
	 * 保存键值信息
	 * @param list :传入的键值信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<UtiKey> list) throws Exception;

	/**
	 * 删除键值信息信息
	 * @param policyNo ：传入的键值信息编号
	 */
	public void delete(String tableName) throws Exception;

	/**
	 * 更新键值信息信息
	 * @param UtiKey :传入需要更新的键值信息
	 */
	public void update(UtiKey utiKey) throws Exception;

	/**
	 * 根据键值信息编号查询出键值信息信息
	 * @param policyNo ：传入的键值信息编号
	 * @return 返回键值信息
	 */
	public UtiKey findUtiKey(String tableName) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的键值信息页面信息
	 */
	public Page findUtiKey(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取键值信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的键值信息  的集合
	 */
	public List<UtiKey> findUtiKey(QueryRule queryRule) throws Exception;
}
