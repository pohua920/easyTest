package com.sinosoft.claim.schema.service.facade;

/**
 * 流程主表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfFlowMain;


public interface SwfFlowMainService {
	/**
	 * 保存流程主表信息
	 * @param SwfCondition ：传入的流程主表信息
	 */
	public void save(SwfFlowMain swfFlowMain) throws Exception;
	
	/**
	 * 流程主表信息
	 * @param list  :传入的流程主表信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfFlowMain> list) throws Exception;
	
	/**
	 * 删除SwfFlowMain信息
	 * @param SwfFlowMainId ：传入的SwfFlowMain编号
	 */
	public void delete(String flowId) throws Exception;

	/**
	 * 更新SwfFlowMain信息
	 * @param SwfFlowMain :传入需要更新的SwfFlowMain
	 */
	public void update(SwfFlowMain swfFlowMain) throws Exception;

	/**
	 * 根据SwfFlowMain编号查询出SwfFlowMain信息
	 * @param SwfFlowMainId ：传入的SwfFlowMain编号
	 * @return 返回SwfFlowMain
	 */
	public SwfFlowMain findSwfFlowMain(String flowId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SwfFlowMain页面信息
	 */
	public Page findSwfFlowMain(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的SwfFlowMain页面信息
	 */
	public List<SwfFlowMain> findSwfFlowMain(QueryRule queryRule) throws Exception;
	  /**
     * 按条件查询多条数据
     * @param dbManager DB管理器
     * @param conditions 查询条件
     * @return Collection 包含swfFlowMainDto的集合
     * @throws Exception
     */
    public List<SwfFlowMain> findByConditions(String conditions) throws Exception;

    /**
     * 取满足条件的数据总数
     * @author 中科软
     * @date Apr 1, 2013 9:32:06 PM
     * @param condtions
     * @return
     */
	public int getCount(String condtions) throws Exception;

}
