package com.sinosoft.claim.schema.service.facade;
/**
 * 模版主表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfModelMain;

public interface SwfModelMainService {

	/**
	 * 保存SwfModelMain信息
	 * @param SwfModelMain ：传入的SwfModelMain
	 */
	public void save(SwfModelMain swfModelMain) throws Exception;
	
	/**
	 * SwfModelMain信息
	 * @param list  :传入的SwfModelMain信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfModelMain> list) throws Exception;
	
	/**
	 * 删除SwfModelMain信息
	 * @param SwfModelMainId ：传入的SwfModelMain编号
	 */
	public void delete(Integer modelNo) throws Exception;

	/**
	 * 更新SwfModelMain信息
	 * @param SwfModelMain :传入需要更新的SwfModelMain
	 */
	public void update(SwfModelMain swfModelMain) throws Exception;

	/**
	 * 根据SwfModelMain编号查询出SwfModelMain信息
	 * @param SwfModelMainId ：传入的SwfModelMain编号
	 * @return 返回SwfModelMain
	 */
	public SwfModelMain findSwfModelMain(Integer modelNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SwfModelMain页面信息
	 */
	public Page findSwfModelMain(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的SwfModelMain页面信息
	 */
	public List<SwfModelMain> findSwfModelMain(QueryRule queryRule) throws Exception;

}
