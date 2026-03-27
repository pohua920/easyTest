package com.sinosoft.claim.schema.service.facade;
/**
 * 工作流路径图形表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfPathGraph;
import com.sinosoft.claim.schema.model.SwfPathGraphId;

public interface SwfPathGraphService {

	/**
	 * 保存SwfPathGraph信息
	 * @param SwfPathGraph ：传入的SwfPathGraph
	 */
	public void save(SwfPathGraph swfPathGraph) throws Exception;
	
	/**
	 * SwfPathGraph信息
	 * @param list  :传入的SwfPathGraph信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfPathGraph> list) throws Exception;
	
	/**
	 * 删除SwfPathGraph信息
	 * @param SwfPathGraphId ：传入的SwfPathGraph编号
	 */
	public void delete(SwfPathGraphId swfPathGraphId) throws Exception;

	/**
	 * 更新SwfPathGraph信息
	 * @param SwfPathGraph :传入需要更新的SwfPathGraph
	 */
	public void update(SwfPathGraph swfPathGraph) throws Exception;

	/**
	 * 根据SwfPathGraph编号查询出SwfPathGraph信息
	 * @param SwfPathGraphId ：传入的SwfPathGraph编号
	 * @return 返回SwfPathGraph
	 */
	public SwfPathGraph findSwfPathGraph(SwfPathGraphId swfPathGraphId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SwfPathGraph页面信息
	 */
	public Page findSwfPathGraph(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的SwfPathGraph页面信息
	 */
	public List<SwfPathGraph> findSwfPathGraph(QueryRule queryRule) throws Exception;

}
