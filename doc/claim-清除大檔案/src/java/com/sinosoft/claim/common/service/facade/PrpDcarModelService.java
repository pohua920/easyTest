package com.sinosoft.claim.common.service.facade;

import ins.framework.common.Page;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDcarModel;

/**
 * 车型代码的业务对象数据访问接口
 * @author 中科软
 */
public interface PrpDcarModelService {
	
	/**
	 * 根据条件查询车型数据
	 * @author 中科软
	 * @param condition 查询条件
	 * @return
	 */
	public List<PrpDcarModel> findByConditions(String conditions);
	/***
	 * 按条件查询厂商数据
	 * @author 中科软
 	 * @param conditions 查询条件
	 * @param pageNo 起始页
	 * @param rowsPerPage 每页显示条数
	 * @return
	 * @throws Exception
	 */
	public Page findByConditionsFactory(String conditions,int pageNo,int rowsPerPage)throws Exception;
	/***
	 * 分页查询车型数据
	 * @author 中科软
	 * @date Mar 27, 2013 2:35:05 PM
	 * @param conditions 查询条件
	 * @param pageNo 起始页
	 * @param rowsPerPage 每页显示条数
	 * @return
	 * @throws Exception
	 */
	public Page findByPage(String conditions,int pageNo,int rowsPerPage)throws Exception;
	
}
