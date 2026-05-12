package com.sinosoft.claim.schema.service.facade;

/**
 * 工作流日志表转储表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfLogStore;

public interface SwfLogStoreService {

	/**
	 * 保存工作流日志表转储表信息
	 * @param swflogstore ：传入的工作流日志表转储信息
	 */
	public void save(SwfLogStore swfLogStore) throws Exception;

	/**
	 * 工作流日志表转储表信息
	 * @param list :传入的工作流日志表转储表信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfLogStore> list) throws Exception;

	/**
	 * 删除工作流日志表转储表信息
	 * @param swflogstoreId ：传入的工作流日志表转储信息编号
	 */
	public void delete(String flowID, int logNo) throws Exception;

	/**
	 * 更新工作流日志表转储表信息
	 * @param swflogstore :传入需要更新的工作流日志表转储信息
	 */
	public void update(SwfLogStore swfLogStore) throws Exception;

	/**
	 * 根据工作流日志表转储信息编号查询出工作流日志表转储表信息
	 * @param swflogstoreId ：传入的工作流日志表转储信息编号
	 * @return 返回工作流日志表转储信息
	 */
	public SwfLogStore findSwfLogStore(String flowID, int logNo) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的工作流日志表转储信息页面信息
	 */
	public Page findSwfLogStore(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	public List<SwfLogStore> findSwfLogStore(QueryRule queryRule) throws Exception;

	/**
	 * 获取logno号
	 * @param flowID
	 * @return LogNo
	 * @throws Exception
	 */
	public int getMaxLogNo(String flowId) throws Exception;
	/**
	 * 分页查询
	 * @author 中科软
	 * @param condition
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public Page findByPage(String condition, int pageNo, int recordPerPage);

	/**
	 * 获取logNo号
	 * @author 中科软
	 * @param flowID
	 * @param nodeType
	 * @param businessNo
	 * @return
	 */
	public int getMaxNodeLogNo(String flowID, String nodeType, String businessNo);

}
