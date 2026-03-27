package com.sinosoft.claim.schema.service.facade;
/**
 * 理赔菜单表接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLmenu;

public interface PrpLmenuService {
	
	/**
	 * 立案基本信息
	 * @param PrpLmenu ：传入的理赔菜单信息
	 */
	public void save(PrpLmenu prpLmenu) throws Exception;
	
	/**
	 * 保存理赔菜单信息
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLmenu> list) throws Exception;
	
	/**
	 * 删除理赔菜单信息信息
	 * @param policyNo ：传入的理赔菜单信息编号
	 */
	public void delete(String funcID) throws Exception;

	/**
	 * 更新理赔菜单信息信息
	 * @param PrpLmenu :传入需要更新的理赔菜单信息
	 */
	public void update(PrpLmenu prpLmenu) throws Exception;

	/**
	 * 根据理赔菜单信息编号查询出理赔菜单信息信息
	 * @param policyNo ：传入的理赔菜单信息编号
	 * @return 返回理赔菜单信息
	 */
	public PrpLmenu findPrpLmenu(String funcID) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的理赔菜单信息页面信息
	 */
	public Page findPrpLmenu(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  的集合
	 */
	public List<PrpLmenu> findPrpLmenu(QueryRule queryRule) throws Exception;
}
