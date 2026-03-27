package com.sinosoft.claim.schema.service.facade;

/**
 * 房屋标的信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCitemHouse;
import com.sinosoft.claim.schema.model.PrpCitemHouseId;

public interface PrpCitemHouseService {

	/**
	 * 保存房屋标的信息
	 * @param prpLcheck ：传入的房屋标的信息
	 */
	public void save(PrpCitemHouse prpCitemHouse) throws Exception;

	/**
	 * 房屋标的信息
	 * @param list :传入的房屋标的信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCitemHouse> list) throws Exception;

	/**
	 * 删除房屋标的信息
	 * @param prpCitemHouseId ：传入的房屋标的信息编号
	 */
	public void delete(PrpCitemHouseId prpCitemHouseId) throws Exception;

	/**
	 * 更新房屋标的信息
	 * @param prpCitemHouse :传入需要更新的房屋标的信息
	 */
	public void update(PrpCitemHouse prpCitemHouse) throws Exception;

	/**
	 * 根据房屋标的信息编号查询出房屋标的信息
	 * @param prpCitemHouseId ：传入的房屋标的信息编号
	 * @return 返回房屋标的信息
	 */
	public PrpCitemHouse findPrpCitemHouse(PrpCitemHouseId prpCitemHouseId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的房屋标的信息页面信息
	 */
	public Page findPrpCitemHouse(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取房屋标的信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的 房屋标的信息 的列表
	 */
	public List<PrpCitemHouse> findPrpCitemHouse(QueryRule queryRule) throws Exception;
}
