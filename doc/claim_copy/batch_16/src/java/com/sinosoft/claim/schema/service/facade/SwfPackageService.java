package com.sinosoft.claim.schema.service.facade;

/**
 * 工作流日志业务信息表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfPackage;
import com.sinosoft.claim.schema.model.SwfPackageId;

public interface SwfPackageService {

	/**
	 * 保存SwfPackage信息
	 * @param SwfPackage ：传入的SwfPackage
	 */
	public void save(SwfPackage swfPackage) throws Exception;
	
	/**
	 * SwfPackage信息
	 * @param list  :传入的SwfPackage信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfPackage> list) throws Exception;
	
	/**
	 * 删除SwfPackage信息
	 * @param SwfPackageId ：传入的SwfPackage编号
	 */
	public void delete(SwfPackageId swfPackageId) throws Exception;

	/**
	 * 更新SwfPackage信息
	 * @param SwfPackage :传入需要更新的SwfPackage
	 */
	public void update(SwfPackage swfPackage) throws Exception;

	/**
	 * 根据SwfPackage编号查询出SwfPackage信息
	 * @param SwfPackageId ：传入的SwfPackage编号
	 * @return 返回SwfPackage
	 */
	public SwfPackage findSwfPackage(SwfPackageId swfPackageId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SwfPackage页面信息
	 */
	public Page findSwfPackage(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的SwfPackage页面信息
	 */
	public List<SwfPackage> findSwfPackage(QueryRule queryRule) throws Exception;

}
