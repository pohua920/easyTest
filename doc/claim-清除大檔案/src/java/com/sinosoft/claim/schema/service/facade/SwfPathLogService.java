package com.sinosoft.claim.schema.service.facade;
/**
 * 工作流路径日志表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.schema.model.SwfPathLogId;

public interface SwfPathLogService {

	/**
	 * 保存SwfPathLog信息
	 * @param SwfPathLog ：传入的SwfPathLog
	 */
	public void save(SwfPathLog swfPathLog) throws Exception;
	
	/**
	 * SwfPathLog信息
	 * @param list  :传入的SwfPathLog信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfPathLog> list) throws Exception;
	
	/**
	 * 删除SwfPathLog信息
	 * @param SwfPathLogId ：传入的SwfPathLog编号
	 */
	public void delete(SwfPathLogId swfPathLogId) throws Exception;

	/**
	 * 更新SwfPathLog信息
	 * @param SwfPathLog :传入需要更新的SwfPathLog
	 */
	public void update(SwfPathLog swfPathLog) throws Exception;

	/**
	 * 根据SwfPathLog编号查询出SwfPathLog信息
	 * @param SwfPathLogId ：传入的SwfPathLog编号
	 * @return 返回SwfPathLog
	 */
	public SwfPathLog findSwfPathLog(SwfPathLogId swfPathLogId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SwfPathLog页面信息
	 */
	public Page findSwfPathLog(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的SwfPathLog页面信息
	 */
	public List<SwfPathLog> findSwfPathLog(QueryRule queryRule) throws Exception;
	/**
     * 获取pathno号
     * @param flowID
     * @return LogNo
     * @throws Exception
     */
    public int getMaxPathNo(String flowId) throws Exception;
    /**
     * 取满足条件的数据条数
     * @author 中科软
     * @param string
     * @return
     */
	public int getCount(String condtions);
	/**
	 * 根据条件删除
	 * @author 中科软
	 * @param conditions
	 */
	public void deleteByConditions(String conditions) throws Exception;

}
