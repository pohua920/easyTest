package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDcodeId;

/**
 * 代码信息接口
 * @author 中科软
 */
public interface PrpDcodeService {
	/**
	 * 根据查询条件获取通用代码的列表
	 * @param condition 查询条件
	 * @return 包含的 通用代码 的列表
	 */
	public List<PrpDcode> findByConditions(String conditions) throws Exception;

	/**
	 * 根据查询语句获取通用代码 的列表
	 * @param sql 查询语句(完整sql)
	 * @return 包含的 通用代码 的列表
	 */
	public List<PrpDcode> findPrpDcodeBySql(String sql);

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的通用代码页面信息
	 */
	public List<PrpDcode> findPrpDcode(QueryRule queryRule, int pageNo, int pageSize);

	/**
	 * 根据查询对象获取 通用代码 的列表
	 * @param queryRule 查询对象
	 * @return 包含的通用代码 的列表
	 */
	public List<PrpDcode> findPrpDcode(QueryRule queryRule) throws Exception;

	/**
	 * 查询指定页通用代码数据
	 * @param queryRule
	 * @param pageNo
	 * @param pageSize
	 * @return 包含的通用代码页面信息
	 */
	public Page findByConditions(String conditions, int pageNo, int pageSize);

	/**
	 * 根据类型和代码获取通用代码
	 * @param codeType 类型
	 * @param codeCode 代码
	 * @return 包含的 通用代码
	 */
	public PrpDcode findByPrimaryKey(String codeType, String codeCode) throws Exception;

	/**
	 * 查询通用代码数据信息
	 * @param prpDcodeId ：传入的通用代码数据信息编号
	 */
	public PrpDcode findPrpDcode(PrpDcodeId prpDcodeId) throws Exception;

	/**
	 * 保存通用代码数据信息
	 * @param PrpDcode ：传入的通用代码数据
	 */
	public void save(PrpDcode prpDcode) throws Exception;

	/**
	 * 保存通用代码数据信息
	 * @param list:保存通用代码数据信息
	 */
	public void save(List<PrpDcode> list) throws Exception;

	/**
	 * 删除通用代码数据信息
	 * @param prpDcodeId ：传入的通用代码数据信息编号
	 */
	public void delete(PrpDcodeId prpDcodeId) throws Exception;

	/**
	 * 更新通用代码数据信息
	 * @param prpDcode :传入需要更新的通用代码数据信息
	 */
	public void update(PrpDcode prpDcode) throws Exception;
	/**
	 * 根据类型和代码获取通用代码,根据newCodeCode关联表PrpDcodeRisk查询
	 * @param codeType 类型
	 * @param codeCode 代码
	 * @return 包含的 通用代码
	 */
	public PrpDcode findByPrimaryKey(String codeType, String codeCode,String riskCode)throws Exception;
	/**
	 * 分页查询通用代码表的数据
	 */
	public Page findByConditionBySql(String conditions, int pageNo, int pageSize);
}
