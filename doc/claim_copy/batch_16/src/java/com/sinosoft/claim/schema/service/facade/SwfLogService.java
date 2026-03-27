package com.sinosoft.claim.schema.service.facade;

/**
 * 工作流日志表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.workflow.vo.StatStatusDto;

public interface SwfLogService {
	/**
	 * 保存SwfLog信息
	 * @param SwfLog ：传入的SwfLog
	 */
	public void save(SwfLog swfLog) throws Exception;
	
	/**
	 * SwfLog信息
	 * @param list  :传入的SwfLog信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfLog> list) throws Exception;
	
	/**
	 * 删除SwfLog信息
	 * @param SwfLogId ：传入的SwfLog编号
	 */
	public void delete(String flowID,Integer logNo) throws Exception;

	/**
	 * 更新SwfLog信息
	 * @param SwfLog :传入需要更新的SwfLog
	 */
	public void update(SwfLog swfLog) throws Exception;

	/**
	 * 根据SwfLog编号查询出SwfLog信息
	 * @param SwfLogId ：传入的SwfLog编号
	 * @return 返回SwfLog
	 */
	public SwfLog findSwfLog(String flowID,Integer logNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SwfLog页面信息
	 */
	public Page findSwfLog(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的SwfLog页面信息
	 */
	public List<SwfLog> findSwfLog(QueryRule queryRule) throws Exception;
	/**
	 * 获取logno号
	 * @param flowID
	 * @return LogNo
	 * @throws Exception
	 */
	public int getMaxLogNo(String flowId) throws Exception;
    /**
     * 查询满足模糊查询条件的记录数
     * @param conditions conditions
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
	public int getCount(String condition);
	/**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    public List<SwfLog> findByConditions(String conditions)throws Exception;
	
	/**
	 * 分页查询所有工作流数据 含正流转和已结束转储
	 * @Description: 从View_SwflogAll视图查询，
	 * @author 中科软
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findByPageFromView(String conditions, int pageNo, int pageSize);

	/**
	 * 查询工作流数据 含正流转和已结束转储
	 * @Description: 从View_SwflogAll视图查询，
	 * @author 中科软
	 * @param condition
	 * @return
	 */
	public List<SwfLog> findViewSwfLogAll(String condition);
	/**
	 * 根据条件删除
	 * @author 中科软
	 * @param condition
	 */
	public void deleteByConditions(String condition) throws Exception;
	/**
	 * 分页查询
	 * @author 中科软
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findByPage(String conditions,Integer pageNo,Integer pageSize) throws Exception ;
	/**
	 * 按条件统计节点数据
	 * @author 中科软
	 * @param condition
	 * @return
	 */
	public List<StatStatusDto>  getNodeStatusStat(String condition);
	
	/**
	 * 按条件对节点进行状态统计用户
	 * @param conditions 统计条件
	 * @return List<StatStatusDto>
	 * @throws Exception
	 */
	public List<StatStatusDto> getNodeUserStatusStat(String conditions);
	
	/**
	 * 按条件对节点进行状态统计
	 * @param conditions 统计条件
	 * @return Collection
	 * @throws Exception
	 */
	public List<StatStatusDto> getStatStatus(String conditions) throws Exception;

	/**
	 * 
	 * 获取logNo号
	 * @author 中科软
	 * @param flowID
	 * @param nodeType
	 * @param businessNo
	 * @return
	 */
	public int getMaxNodeLogNo(String flowID, String nodeType, String businessNo) throws Exception;
	
	/**
	 * 
	 * @Description: 
	 * @author 中科软
	 * @param flowID
	 * @throws Exception
	 */
	public void updateFlowStatus(String flowID) throws Exception;
	
	/**
	 * 更新工作流归属机构
	 * @Description: 
	 * @author 中科软
	 * @param flowID
	 * @throws Exception
	 */
	public void updateComCode(String flowID,String comCode) throws Exception;
	
	/**
	 * 更新
	 * @author 中科软
	 * @param swfLog
	 * @throws Exception
	 */
	public void saveOrUpdate(SwfLog swfLog) throws Exception;

	/**
	 * 视图查询数据总数
	 * @author 中科软
	 * @param condition
	 * @return
	 */
	public int getCountViewSwfLogAll(String condition);

	/**
	 * 理算紧急案件清单分页查询
	 * @author 中科软
	 * @param condition
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public Page getUrgentCaseList(String condition, int pageNo, int recordPerPage);

	/**
	 * 理算紧急案件清单分页查询
	 * @author 中科软
	 * @param condition
	 * @param pageNo
	 * @param recordPerPage
	 * @return Page
	 */
	public Page getUndwrtUrgentCaseList(String condition, int pageNo, int recordPerPage);

	/**
  	 * 按自定义SQL查询多条数据
  	 * @param statement 自定义SQL（含Select）
  	 * @param pageNo 页码
  	 * @param recordPerPage 每页显示的行数
  	 * @return Page
  	 * @throws Exception 
  	 */
	public Page findByStatement(String sql, int pageNo, int recordPerPage);
	/**
	 * 根据查询对象获取工作流日志表信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  工作流日志表信息的集合
	 */
	public List<SwfLog> findListByStatement(String sql, int pageNo, int recordPerPage);
	/**
	 * 超时赔付的查询，方法的迁移
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findTimeOutByConditions(String conditions, int pageNo, int pageSize) throws Exception;
}
