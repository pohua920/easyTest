package com.sinosoft.claim.schema.service.facade;

/**
 * 航空险接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCplane;
import com.sinosoft.claim.schema.model.PrpCplaneId;

public interface PrpCplaneService {

	/**
	 * 保存航空险信息
	 * @param prpLcheck ：传入的航空险
	 */
	public void save(PrpCplane prpCplane) throws Exception;

	/**
	 * 航空险信息
	 * @param list :传入的航空险信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCplane> list) throws Exception;

	/**
	 * 删除航空险信息
	 * @param prpCplaneId ：传入的航空险编号
	 */
	public void delete(PrpCplaneId prpCplaneId) throws Exception;

	/**
	 * 更新航空险信息
	 * @param prpCplane :传入需要更新的航空险
	 */
	public void update(PrpCplane prpCplane) throws Exception;

	/**
	 * 根据航空险编号查询出航空险信息
	 * @param prpCplaneId ：传入的航空险编号
	 * @return 返回航空险
	 */
	public PrpCplane findPrpCplane(PrpCplaneId prpCplaneId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的航空险页面信息
	 * @deprecated 请用findByPage代替
	 */
	public Page findPrpCplane(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**
	 * 根据查询对象获取航空险页面信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的航空险页面信息的列表
	 */
	public List<PrpCplane> findPrpCplane(QueryRule queryRule) throws Exception;

	/**
	 * 分页查询PrpCplane
	 * @author 中科软
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception;
}
