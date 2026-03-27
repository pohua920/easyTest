package com.sinosoft.claim.schema.service.facade;

/**
 * 工作流日志表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.claim.schema.model.WfLogId;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.dto.custom.UndwrtSubmitDto;

public interface WfLogService {
	/**
	 * 根据查询对象获取 WfLog表的附属表信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的WfLog表的附属表信息 的集合
	 */
	public List<WfLog> findByConditions(String conditions) throws Exception;

	/**
	 * 根据查询对象获取 WfLog表的附属表信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的WfLog表的附属表信息 的集合
	 */
	public List<WfLog> findByQueryRuleList(QueryRule queryRule) throws Exception;
	
	@Deprecated
	public int getCount(QueryRule queryRule) throws Exception;

	/**
	 * 查询
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public List<WfLog> findByHqlList(String hql) throws Exception;

	/**
	 * 查询列表
	 * @param queryRule
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findUserTaskList(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**
	 * 更新工作流信息
	 * @param wfLog
	 * @throws Exception
	 */
	public void update(WfLog wfLog) throws Exception;

	/**
	 * 保存工作流信息
	 * @param wfLog
	 * @throws Exception
	 */
	public void save(WfLog wfLog) throws Exception;

	/**
	 * 按主键查询工作流信息
	 * @param flowId
	 * @param logNo
	 * @return
	 * @throws Exception
	 */
	public WfLog findByPrimaryKey(String flowId, Integer logNo) throws Exception;

	/**
	 * 获取回退节点列表
	 * @param FlowId 流水号
	 * @param LogNo 序号
	 * @param nodeNo 节点号
	 * @return Collection 回退节点及路径的集合
	 * @throws Exception
	 * @author 中科软
	 */
	public List<WfLog> getBackList(String FlowId, int LogNo, int nodeNo) throws Exception;

	/**
	 * 获得组号列表
	 * @param conditions 查询条件
	 * @return Collection 包含uwGroupDto的集合
	 * @throws Exception
	 */
	public List<?> getPackageId(String conditions) throws Exception;

	// 把wflog对象转化为wflogvo对象
	// public List<WfLogVo> findBySql(String sql) throws Exception;

	public List<WfLog> findByStatementQta(String statement, int pageNo, int rowsPerPage, boolean blnAll) throws Exception;


	/**
	 * 关闭工作流时，置nodestatus为"0"
	 * @param dbManager DBManager
	 * @param flowID String
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateNodeStatusByFlowID(String flowID) throws SQLException, Exception;

	public int getMaxLogNo(String flowId) throws Exception;

	public List<WfLog> findByFlowId(QueryRule queryRule) throws Exception;

	/**
	 * 收回双核工作流
	 * @throws Exception
	 */
	public void recycleWflog(SwfLog swfLogDto) throws Exception;
	
	/**
	 * 提交节点
	 * @param flowID 流水号
	 * @param modelNo 使用模板号
	 * @param nodeNo 提交节点号
	 * @param businessType 业务类型
	 * @param businessNo 业务号
	 * @param flowStatus 流向
	 * @param flag 标志
	 * @param userCode 用户代码
	 * @param opertorCode 操作员代码
	 * @throws Exception
	 * @author 中科软
	 */
	public int submitTask(String flowID, int modelNo, int nodeNo, String businessType, String businessNo, String flowStatus, String flag, String userCode, String opertorCode,Map<String,String> infoMap) throws Exception;
	/**
	 * 提交任务处理
	 * @param dbManager DBManager
	 * @param iFlowId String
	 * @param iModelNo int
	 * @param iNodeNo int
	 * @param iBusinessType String
	 * @param iBusinessNo String
	 * @param iFlowStatus String
	 * @param flag String //0表示从业务系统提交到双核，1表示双核系统内部提交
	 * @param iUserCode String
	 * @param iOpertorCode String
	 * @throws SQLException
	 * @throws Exception
	 */
	public void submitTaskNotReturn(String flowId, int modelNo, int nodeNo, String businessType, String businessNo, String flowStatus, String flag, String userCode, String opertorCode,Map<String,String> infoMap) throws SQLException, Exception, UserException;
	/**
	 *关闭工作流服务
	 *@param DBManager 数据管理器
	 *@param flowID 工作流号
	 *@throws SQLException,Exception
	 *@return
	 */
	public void close(String flowID) throws SQLException, Exception;
	/* (non-Javadoc)
	 * @see com.sinosoft.claim.schema.service.facade.WfLogService#getCount(java.lang.String)
	 * 更具条件查询有多少条
	 */
	public int getCount(String conditions) throws Exception;

	/**
	 * 送再保需要查询数据
	 */
	public WfLog findByMaxLognoAndBusinessNo(String businessNo) throws Exception;
	/**
	 * 增加放弃任务功能
	 * @param conditions 自定义SQL
	 * @param
	 * @return
	 * @throws Exception
	 * @author 中科软
	 */
	public void undoTask(String flowId, int logNo) throws Exception;
	/**
	 * 按自定义SQL查询多条数据
	 * @param conditions 自定义SQL
	 * @param blnAll 自定义参数
	 * @return Collection 包含wfLogDto的集合
	 * @throws Exception
	 * @author 中科软
	 */
	public List<WfLog> findByStatement(String conditions) throws Exception;
	public List<?> getWorkFlowQueryView(String sql) throws Exception;
	/**
	 * 获取提交用户列表
	 * @param modelNo int
	 * @param nodeNo int
	 * @param businessType char
	 * @param businessNo String
	 * @param flag String
	 * @return Collection
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<?> getSubmitUserList(int modelNo, int nodeNo, String businessType, String businessNo, String flag) throws SQLException, Exception;
	/**
	 * 流过滤器
	 * @param workflowList ArrayList
	 * @param dbManager DBManager
	 * @throws Exception
	 * @return ArrayList
	 * @author 中科软
	 */
	public List<WfLog> wfLogFilter(List<?> workflowList) throws Exception;
	/*
	 * 按自定义SQL查询多条数据 @author 中科软
	 */
	public Page findByConditions(String conditions, int pageNo, int pageSize, boolean blnAll) throws Exception;
	/**
	 * 调用理赔工作流发送方法
	 * @param dbManager DBManager
	 * @param wfLogOldDto WfLogDto
	 * @param wfLogNewDto WfLogDto
	 * @param interMethod String
	 * @throws Exception
	 * @return flag boolean
	 */
	public int submitClaim(WfLog wfLogOld, WfLog wfLogNew, String interMethod) throws Exception;
	/**
	 * 检查是否理赔工作流数据
	 * @param dbManager DBManager
	 * @param flowID String
	 * @param modelNo int
	 * @param nodeNo int
	 * @param businessType String 业务类型
	 * @throws Exception
	 * @return flag boolean
	 */
	public int checkSubmitClaim(String flowID, int modelNo, int nodeNo, String businessType) throws Exception;
	/**
	 * 根据部门和时间生成信息包号
	 * @param int iModelno 模版号
	 * @param String iBusinessno 业务号 throws UserException,Exception
	 */
	public String getSoleFlowID(String comCode) throws UserException, Exception;
	public List<WfLog> findByStatement(String sql, int pageNo, int rowsPerPage, boolean blnAll) throws Exception;
	/**
	 * 根据险种和机构判断是否是否使用规则引擎
	 * @param iRiskCode 险种代码
	 * @param iComCode 归属机构代码
	 * @return true 使用规则引擎 false 不使用规则引擎
	 * @throws UserException
	 * @throws Exception
	 */
	public boolean isILog(String iRiskCode, String iComCode) throws UserException, Exception ;
	public WfLog findByWflog(WfLogId wfLogId) throws Exception;
	public void saveOrUpdate(WfLog wflog) throws Exception;
	/**
	 * @desc 判断工作流为新启动还是待修改
	 * @param iBusinessNo
	 *            业务号
	 * @param dbManager
	 *            DBManager
	 * @return flag(U:修改,N:新启动,0:出错)
	 * @author 中科软
	 */
	public String checkStartType(String iBusinessNo) throws SQLException, UserException, Exception;	
	/**
	 * 双核接口方法
	 * @param modelType String
	 * @param certiType String
	 * @param businessNo String
	 * @param riskCode String
	 * @param classCode String
	 * @param comCode String
	 * @param makecom String
	 * @param userCode String
	 * @param handlerCode String
	 * @param handler1Code String
	 * @param contractNo String
	 * @throws UserException
	 * @throws SQLException
	 * @throws Exception
	 * @return String
	 */
	public String start(UndwrtSubmitDto undwrtSubmitDto,Map<String,String> infoMap) throws UserException, SQLException, Exception;
	
	/**
	 * 核赔查询时用到此方法
	 * @param statement
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 */
	public PageRecord findByStatementFromWflog(String statement ,int pageNo ,int rowsPerPage) throws Exception;
	
	/**
     *从View_wfLogAll视图里查询数据
     * @throws Exception 
     */
	public PageRecord findView_wfLogAll(String statement ,int pageNo ,int rowsPerPage) throws Exception;
	
	/**
	 * 按自定义SQL查询多条数据
	 * @param statement 自定义SQL（含Select）
	 * @param pageNo 页号
	 * @param rowsPerPage 每页的行数
	 * @param blnAll  BOOLEAN
	 * @return Collection
	 * @throws Exception
	 */
	public Page findWfLogObjectAllByStatement(String statement,int pageNo ,int rowsPerPage) throws Exception;
}
