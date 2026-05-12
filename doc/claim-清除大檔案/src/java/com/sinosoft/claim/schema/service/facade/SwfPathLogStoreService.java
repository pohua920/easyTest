package com.sinosoft.claim.schema.service.facade;

/**
 * 工作流路径定义转储表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfPathLogStore;

public interface SwfPathLogStoreService {

	/**
	 * 保存SwfPathLogStore信息
	 * @param SwfPathLogStore ：传入的SwfPathLogStore
	 */
	public void save(SwfPathLogStore swfPathLogStore) throws Exception;
	
	/**
	 * SwfPathLogStore信息
	 * @param list  :传入的SwfPathLogStore信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfPathLogStore> list) throws Exception;
	
	/**
	 * 删除SwfPathLogStore信息
	 * @param SwfPathLogStoreId ：传入的SwfPathLogStore编号
	 */
	public void delete(String flowId,Integer pathNo) throws Exception;

	/**
	 * 更新SwfPathLogStore信息
	 * @param SwfPathLogStore :传入需要更新的SwfPathLogStore
	 */
	public void update(SwfPathLogStore swfPathLogStore) throws Exception;

	/**
	 * 根据SwfPathLogStore编号查询出SwfPathLogStore信息
	 * @param SwfPathLogStoreId ：传入的SwfPathLogStore编号
	 * @return 返回SwfPathLogStore
	 */
	public SwfPathLogStore findSwfPathLogStore(String flowId,Integer pathNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SwfPathLogStore页面信息
	 */
	public Page findSwfPathLogStore(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的SwfPathLogStore页面信息
	 */
	public List<SwfPathLogStore> findSwfPathLogStore(QueryRule queryRule) throws Exception;
	 /**
     * 获取pathno号
     * @param flowID
     * @return LogNo
     * @throws Exception
     */
    public int getMaxPathNo(String flowId) throws Exception;

}
