package com.sinosoft.claim.schema.service.facade;

/**
 * 人伤跟踪接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLacciPersonId;

public interface PrpLacciPersonService {

	/**
	 * 保存人伤跟踪信息
	 * @param prpLacciPerson ：传入的人伤跟踪
	 */
	public void save(PrpLacciPerson prpLacciPerson) throws Exception;

	/**
	 * 人伤跟踪信息
	 * @param list :传入的人伤跟踪信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLacciPerson> list) throws Exception;

	/**
	 * 删除人伤跟踪信息
	 * @param prpLacciPersonId ：传入的人伤跟踪编号
	 */
	public void delete(PrpLacciPersonId prpLacciPersonId) throws Exception;

	/**
	 * 更新人伤跟踪信息
	 * @param prpLacciPerson :传入需要更新的人伤跟踪
	 */
	public void update(PrpLacciPerson prpLacciPerson) throws Exception;

	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param prpLacciPersonId ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpLacciPerson findPrpLacciPerson(PrpLacciPersonId prpLacciPersonId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的c页面信息
	 */
	public Page findPrpLacciPerson(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 人伤跟踪 的列表
	 * @param queryRule 查询对象
	 * @return 包含的人伤跟踪信息  的列表
	 */
	public List<PrpLacciPerson> findPrpLacciPerson(QueryRule queryRule) throws Exception;

	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪信息
	 */
	public PrpLacciPerson findPrpLacciPerson(String certiNo) throws Exception;

	/**
	 * 根据报案号查询出人伤跟踪信息
	 * @param registNo 业务号
	 * @param flag 状态字段
	 * @throws Exception 根据业务号和状态字段删除人伤跟踪信息
	 */
	public void deleteByRegistNo(String registNo, String flag) throws Exception;
	/**
	 * 更新人伤跟踪信息
	 * @param prpLacciPerson :传入需要更新的人伤跟踪
	 */
	public void saveOrUpdate(PrpLacciPerson prpLacciPerson) throws Exception;
	/**
	 * 更新人伤跟踪信息列表
	 * @param prpLacciPerson :传入需要更新的人伤跟踪列表
	 */
	public void saveOrUpdate(List<PrpLacciPerson> list) throws Exception;
	/**
	 * 更新人伤跟踪信息列表
	 * @param prpLacciPerson :传入需要更新的人伤跟踪列表
	 */
	public void updateFlag(PrpLacciPerson prpLacciPerson) throws Exception;
	/**
	 * 根据查询条件查找最大的序号
	 * @param condition :传入查询条件
	 */
	public int findBySeriaNo(String condition) throws Exception;
}
