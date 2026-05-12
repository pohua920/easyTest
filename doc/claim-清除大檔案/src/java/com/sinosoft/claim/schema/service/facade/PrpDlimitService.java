package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDlimit;
import com.sinosoft.claim.schema.model.PrpDlimitId;

/**
 * @Description 限额免赔代码表的数据访问
 * @author 中科软
 */
public interface PrpDlimitService {
	/**
	 * 保存限额免赔代码信息
	 * @param prpDlimit ：传入的限额免赔代码信息
	 */
	public void save(PrpDlimit prpDlimit) throws Exception;
	/**
	 * 删除限额免赔代码信息
	 * @param riskCode ：属性险种
	 * @param limitCode ：限额/免赔类别代码
	 */
	public void delete(String riskCode, String limitCode) throws Exception;
	/**
	 * 更新限额免赔代码信息
	 * @param prpDlimit :传入需要更新的限额免赔代码信息
	 */
	public void update(PrpDlimit prpDlimit) throws Exception;
	/**
	 * 根据查询对象获取 限额免赔代码信息 的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param rowsPerPage 页面大小
	 * @return 包含的限额免赔代码信息  的列表
	 */
	public List<PrpDlimit> findByConditions(QueryRule queryRule, int pageNo, int rowsPerPage) throws Exception;
	/**
	 * 根据查询对象获取 限额免赔代码信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的限额免赔代码信息  的列表
	 */	
	public List<PrpDlimit> findByConditions(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据限额免赔代码信息编号查询出限额免赔代码信息
	 * @param id ：传入的限额免赔代码信息编号
	 * @return 返回限额免赔代码信息
	 */
	public PrpDlimit findPrpDlimitById(PrpDlimitId id) throws Exception;
}
