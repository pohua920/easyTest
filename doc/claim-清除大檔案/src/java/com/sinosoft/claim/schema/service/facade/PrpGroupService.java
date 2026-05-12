package com.sinosoft.claim.schema.service.facade;

/**
 * 单证分组接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpGroup;
import com.sinosoft.claim.schema.model.PrpGroupId;

public interface PrpGroupService {

	/**
	 * 保存单证分组信息
	 * @param prpGroup ：传入的单证分组
	 */
	public void save(PrpGroup prpGroup) throws Exception;

	/**
	 * 单证分组信息
	 * @param list :传入的单证分组信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpGroup> list) throws Exception;

	/**
	 * 删除单证分组信息
	 * @param prpGroupId ：传入的单证分组编号
	 */
	public void delete(PrpGroupId prpGroupId) throws Exception;

	/**
	 * 更新单证分组信息
	 * @param prpGroup :传入需要更新的单证分组
	 */
	public void update(PrpGroup prpGroup) throws Exception;

	/**
	 * 根据单证分组编号查询出单证分组信息
	 * @param prpGroupId ：传入的单证分组编号
	 * @return 返回单证分组
	 */
	public PrpGroup findPrpGroup(PrpGroupId prpGroupId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的单证分组页面信息
	 */
	public Page findPrpGroup(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 单证分组 的列表
	 * @param queryRule 查询对象
	 * @return 包含的单证分组  的列表
	 */
	public List<PrpGroup> findPrpGroup(QueryRule queryRule) throws Exception;

	/**
	 * 获取单号编组
	 * @param subGroupNo 子编组
	 * @return strGroupNo 主编组
	 * @throws Exception
	 */
	public String getGroupNo(String subGroupNo) throws Exception;
}
