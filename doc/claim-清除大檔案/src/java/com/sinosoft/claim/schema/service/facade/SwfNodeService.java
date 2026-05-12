package com.sinosoft.claim.schema.service.facade;
/**
 * 工作流节点定义表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfNodeId;

public interface SwfNodeService {

	/**
	 * 保存SwfNode信息
	 * @param SwfNode ：传入的SwfNode
	 */
	public void save(SwfNode swfNode) throws Exception;

	/**
	 * SwfNode信息
	 * @param list :传入的SwfNode信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfNode> list) throws Exception;

	/**
	 * 删除SwfNode信息
	 * @param SwfNodeId ：传入的SwfNode编号
	 */
	public void delete(SwfNodeId swfNodeId) throws Exception;

	/**
	 * 更新SwfNode信息
	 * @param SwfNode :传入需要更新的SwfNode
	 */
	public void update(SwfNode swfNode) throws Exception;

	/**
	 * 根据SwfNode编号查询出SwfNode信息
	 * @param SwfNodeId ：传入的SwfNode编号
	 * @return 返回SwfNode
	 */
	public SwfNode findSwfNode(SwfNodeId swfNodeId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SwfNode页面信息
	 */
	public Page findSwfNode(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的SwfNode页面信息
	 */
	public List<SwfNode> findSwfNode(QueryRule queryRule) throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询条件
	 * @return 包含的SwfNode集合
	 */
	public List<SwfNode> findByConditions(String conditions) throws Exception;

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public boolean checkEndflag(int modelNo, int nodeNo) throws SQLException, Exception;
	/**
	 * 根据查询对象获取SwfNode的数量
	 * @param conditions 查询条件
	 * @return 包含的SwfNode的数量
	 */
	public int getCount(String conditions) throws SQLException, Exception;
	/**
	 * @param modelNo
	 * @param nodeNo
	 * @return
	 * @throws Exception
	 * 更具主键查询节点
	 */
	public SwfNode findByPrimaryKey(Integer modelNo,Integer nodeNo)throws Exception;
	/**查询是否是工作流结束节点,1，表示工作流结束，最后一个节点
	 * @param modelNo
	 * @param nodeNo
	 * @return
	 * @throws Exception
	 */
	public String findEndFlag(Integer modelNo,Integer nodeNo)throws Exception;

}
