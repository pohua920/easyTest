package com.sinosoft.claim.schema.service.facade;
/**
 * 工作流意见处理表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.model.SwfNotionId;

public interface SwfNotionService {

	
	/**
	 * 保存SWFNOTION信息
	 * @param swfNotion ：传入的SWFNOTION
	 */
	public void save(SwfNotion swfNotion) throws Exception;
	
	/**
	 * SWFNOTION信息
	 * @param list  :传入的SWFNOTION信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfNotion> list) throws Exception;
	
	/**
	 * 删除SWFNOTION信息
	 * @param swfNotionId ：传入的SWFNOTION编号
	 */
	public void delete(SwfNotionId swfNotionId) throws Exception;

	/**
	 * 更新SWFNOTION信息
	 * @param swfNotion :传入需要更新的SWFNOTION
	 */
	public void update(SwfNotion swfNotion) throws Exception;

	/**
	 * 根据SWFNOTION编号查询出SWFNOTION信息
	 * @param swfNotionId ：传入的SWFNOTION编号
	 * @return 返回SWFNOTION
	 */
	public SwfNotion findSwfNotion(String flowID, Integer logNo,Integer lineNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SWFNOTION页面信息
	 */
	public Page findSwfNotion(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 工作流意见处理信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的 工作流意见处理信息 的集合
	 */
	public List<SwfNotion> findSwfNotion(QueryRule queryRule) throws Exception;
	/**
	 * 获取logno号
	 * @param flowID
	 * @return LogNo
	 * @throws Exception
	 */
	public int getMaxLineNo(String flowID, int logNo) throws Exception;
	

}
