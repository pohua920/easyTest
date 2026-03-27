package com.sinosoft.claim.schema.service.facade;
/**
 * 工作流条件描述表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfCondition;
import com.sinosoft.claim.schema.model.SwfConditionId;
import com.sinosoft.sysframework.exceptionlog.UserException;


public interface SwfConditionService {
	
	/**
	 * 保存工作流条件描述信息
	 * @param SwfCondition ：传入的SwfCondition
	 */
	public void save(SwfCondition swfCondition) throws Exception;
	
	/**
	 * 工作流条件描述信息
	 * @param list  :传入的工作流条件描述信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfCondition> list) throws Exception;
	
	/**
	 * 删除工作流条件描述信息
	 * @param SwfConditionId ：传入的SwfCondition编号
	 */
	public void delete(SwfConditionId swfConditionId) throws Exception;

	/**
	 * 更新工作流条件描述信息
	 * @param SwfCondition :传入需要更新的SwfCondition
	 */
	public void update(SwfCondition swfCondition) throws Exception;

	/**
	 * 根据SwfCondition编号查询出工作流条件描述信息
	 * @param SwfConditionId ：传入的SwfCondition编号
	 * @return 返回SwfCondition
	 */
	public SwfCondition findSwfCondition(SwfConditionId swfConditionId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SwfCondition页面信息
	 */
	public Page findSwfCondition(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的SwfCondition页面信息
	 */
	public List<SwfCondition> findSwfCondition(QueryRule queryRule) throws Exception;

	/**
	 * 执行sql查看影响行数，
	 * @author 中科软
	 * @date Apr 1, 2013 6:26:57 PM
	 * @param strTemp
	 * @return
	 */
	public boolean executeResult(String sql);
	public List<SwfCondition> findByConditions(String conditions) throws Exception;
	/**
	 * @param conditions
	 * @return
	 * @throws Exception
	 * 查询多少条数
	 */
	public int getCount(String conditions) throws Exception;
	/**
	 * 执行工作流系统发出的sql语句(针对简单描述和SQL描述)
	 * @param businessNo 业务号码
	 * @param wfConditionDto WfConditionDto
	 * @param dbManager DBManager
	 * @throws UserException
	 * @throws Exception
	 * @return boolean
	 */
	public boolean execute(String businessNo, int modelno, int nodeNo, SwfCondition swfCondition, String userCode) throws UserException, Exception;

}
