package com.sinosoft.claim.schema.service.facade;
/**
 * 工作流路径定义表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.model.SwfPathId;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.sysframework.exceptionlog.UserException;

public interface SwfPathService {

	/**
	 * 保存SwfPath信息
	 * @param SwfPath ：传入的SwfPath
	 */
	public void save(SwfPath swfPath) throws Exception;
	
	/**
	 * SwfPath信息
	 * @param list  :传入的SwfPath信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfPath> list) throws Exception;
	
	/**
	 * 删除SwfPath信息
	 * @param SwfPathId ：传入的SwfPath编号
	 */
	public void delete(SwfPathId swfPathId) throws Exception;

	/**
	 * 更新SwfPath信息
	 * @param SwfPath :传入需要更新的SwfPath
	 */
	public void update(SwfPath swfPath) throws Exception;

	/**
	 * 根据SwfPath编号查询出SwfPath信息
	 * @param SwfPathId ：传入的SwfPath编号
	 * @return 返回SwfPath
	 */
	public SwfPath findSwfPath(SwfPathId swfPathId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SwfPath页面信息
	 */
	public Page findSwfPath(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的SwfPath页面信息
	 */
	public List<SwfPath> findSwfPath(QueryRule queryRule) throws Exception;
	public SwfPath getPassPath(WfLog wfLog) throws Exception ;
	public List<SwfPath> findByConditions(String conditions)throws Exception;

	public List<SwfPath> getPathes(int modelNo, int nodelNo, String comCode) throws UserException;
	public List<SwfPath> findByConditions(String conditions,int pageNo,int pageSize) throws Exception ;
	/**
     *取得以某节点为起始节点的所有满足条件且优先级最高的路径以及路径的个数
     *@param modelNo 模板号
     *@param startNodeNo 起始节点号
     *@param certiType  单证类型
     *@param businessNo 业务号
     *@param defaultFlag 是否缺省值--*0:否 1:是
     *@param comCode 机构代码
     *@param dbManager dbManager
     *@throws UserException
     *@throws Exception
     *@return Collection
     */
	public List<SwfPath> getPathes(int modelNo, int startNodeNo, String certiType, String businessNo, String defaultFlag, String comCode) throws UserException, Exception;

}
