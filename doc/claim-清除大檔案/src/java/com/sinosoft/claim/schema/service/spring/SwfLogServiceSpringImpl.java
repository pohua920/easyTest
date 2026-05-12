package com.sinosoft.claim.schema.service.spring;

/**
 * SwfLog信息接口实现类
 * @author 中科软
 */
import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfLogId;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfNodeId;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfNodeService;
import com.sinosoft.claim.workflow.vo.StatStatusDto;
import com.sinosoft.sysframework.common.util.StringUtils;

public class SwfLogServiceSpringImpl extends GenericDaoHibernate<SwfLog, SwfLogId> implements SwfLogService {
	/*** 险种service**/
	private PrpDriskService prpDriskService;
	private SwfNodeService swfNodeService;

	/**
	 * 保存单条信息
	 * @param SwfLog
	 */
	public void save(SwfLog swfLog) throws Exception {
		logger.info("保存SwfLog信息");
		if(DataUtils.emptyToNull(swfLog.getHandlerCode())==null){
			swfLog.setHandlerCode(SwfLog.HANDLERCODE_NONE);
		}
		super.save(swfLog);
	}

	/**
	 * 保存多条条信息
	 * @param List<SwfLog> list
	 */
	public void save(List<SwfLog> list) throws Exception {
		logger.info("保存SwfLog信息");
		for (int i = 0; i < list.size(); i++) {
			this.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfLogId
	 */
	public void delete(String flowID,Integer logNo) throws Exception {
		logger.info("删除SwfLog信息编号为" + new SwfLogId(flowID,logNo) + "的SwfLog信息");
		super.deleteByPK(SwfLog.class, new SwfLogId(flowID,logNo));
	}

	public void update(SwfLog swfLog){
		if(DataUtils.emptyToNull(swfLog.getHandlerCode())==null){
			swfLog.setHandlerCode(SwfLog.HANDLERCODE_NONE);
		}
		super.update(swfLog);
	}
	
	/**
	 * 根据主键查询信息
	 * @param swfLogId
	 */
	public SwfLog findSwfLog(String flowID,Integer logNo) throws Exception {
		logger.info("查询SwfLog信息编号为" + new SwfLogId(flowID,logNo) + "的SwfLog信息");
		return super.get(SwfLog.class, new SwfLogId(flowID,logNo));
	}

	/**
	 * 根据查询条件queryRule 查询分页信息 pageNo 开始的页数 pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfLog(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取SwfLog信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfLog> findSwfLog(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 获取logno号
	 * @param flowID
	 * @return LogNo
	 * @throws Exception
	 */
	public int getMaxLogNo(String flowId) throws Exception {
		int LogNo = -1;
		String statement = "Select max(LogNo+1) from swfLog Where flowID='" + flowId + "'";
		logger.debug(statement);
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		if (list != null && !list.isEmpty()) {
			Number num = (Number)list.get(0);
			if (num!=null) {
				return num.intValue();
			}
		}
		if (LogNo <= 0) {
			LogNo = 1;
		}
		logger.info("DBSwfLog.getMaxLogNo() success!");
		return LogNo;
	}

	@Override
	public int getCount(String conditions) {
		int count = -1;
		if(DataUtils.emptyToNull(conditions)==null){
			conditions = " 1=1 ";
		}
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("SELECT count(*) FROM SwfLog WHERE ").append(conditions);
		List<?> resultSet = HibernateUtils.findbySql(super.getSession(), buffer.toString());
		count = Integer.valueOf(String.valueOf(resultSet.get(0)));
		return count;
	}
	/**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    public List<SwfLog> findByConditions(String conditions)throws Exception{
    	String sql = "select * from swflog where "+conditions;
//    	return super.find(QueryRule.getInstance().addSql(conditions));
    	List<?> list = HibernateUtils.findbySql(super.getSession(), sql, SwfLog.class);
    	List<SwfLog> swflogList = new ArrayList<SwfLog>();
    	for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			SwfLog swfLog = (SwfLog) iterator.next();
			swflogList.add(swfLog);
		}
    	return swflogList;
    }
    

	@Override
	public void deleteByConditions(String condition)throws Exception{
		String sql = "delete from swfLog where " + condition;
		HibernateUtils.executeSql(super.getSession(), sql);
	}
    
    @Override
	public Page findByPageFromView(String conditions, int pageNo, int pageSize) {
		Page page = HibernateUtils.findPagebySql(super.getSession(), this.getViewSql(conditions), pageNo, pageSize);
		List<?> result = page.getResult();
		List<SwfLog> swflogs = new ArrayList<SwfLog>();
		if (result != null && !result.isEmpty()) {
			Object[] object = null;
			for (Iterator<?> it = result.iterator(); it.hasNext();) {
				object = (Object[]) it.next();
				SwfLog swfLog = this.getSwfLogFromViewObject(object);
				convertDto(swfLog,"view");
				swflogs.add(swfLog);
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), swflogs);

	}
	/**
	 * 从View_SwflogAll视图查询工作流数据
	 */
	@Override
	public List<SwfLog> findViewSwfLogAll(String conditions) {
		List<?> result = HibernateUtils.findbySql(super.getSession(), this.getViewSql(conditions));
		List<SwfLog> swflogs = new ArrayList<SwfLog>();
		if (result != null && !result.isEmpty()) {
			Object[] object = null;
			for (Iterator<?> it = result.iterator(); it.hasNext(); swflogs.add(this.getSwfLogFromViewObject(object))) {
				object = (Object[]) it.next();
			}
		}
		return swflogs;
	}
	/**
	 * 根据条件返回从View_SwflogAll视图查询的sql语句
	 * @Description: 若增加或减少字段的查询，请同步修改getSwfLogFromViewObject的对象转换赋值
	 * @author 中科软
	 * @param conditions
	 * @return
	 */
	private String getViewSql(String conditions){
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1=1 ";
		}
		String statement = (new StringBuilder("Select FlowID, LogNo, ModelNo, NodeNo, NodeName, BusinessNo, HandleDept, HandlerCode, HandlerName, FlowInTime, TimeLimit, HandleTime, SubmitTime, NodeStatus, FlowStatus, PackageID, Flag, TaskNo, TaskType, NodeType, TitleStr, BusinessType, RiskCode, KeyIn, KeyOut, DeptName, MainFlowID, SubFlowID, PosX, PosY, EndFlag, BeforeHandlerCode, BeforeHandlerName, PolicyNo, TypeFlag, ComCode, ScheduleID, LossItemCode, LossItemName, InsureCarFlag, HandlerRange, ExigenceGree, RegistNo, InsuredName,processId,actorId,taskId,businessId From View_SwflogAll Where ")).append(conditions).toString();
		return statement;
	}
	
	/***
	 * 从视图View_SwflogAll查出的object数组转换为SwfLog对象
	 * @Description: 
	 * @author 中科软
	 * @param object
	 * @return
	 */
	private SwfLog getSwfLogFromViewObject(Object[] object){
		SwfLog swfLog = new SwfLog();
		swfLog.getId().setFlowID((String) object[0]);
		swfLog.getId().setLogNo(((BigDecimal) object[1]).intValue());
		swfLog.setModelNo(((BigDecimal) object[2]).intValue());
		swfLog.setNodeNo(((BigDecimal) object[3]).intValue());
		swfLog.setNodeName((String) object[4]);
		swfLog.setBusinessNo((String) object[5]);
		swfLog.setHandleDept((String) object[6]);
		swfLog.setHandlerCode((String) object[7]);
		swfLog.setHandlerName((String) object[8]);
		swfLog.setFlowInTime((String) object[9]);
		swfLog.setTimeLimit(((BigDecimal) object[10]).intValue());
		swfLog.setHandleTime((String) object[11]);
		swfLog.setSubmitTime((String) object[12]);
		swfLog.setNodeStatus((String) object[13]);
		swfLog.setFlowStatus((String) object[14]);
		swfLog.setPackageID((String) object[15]);
		swfLog.setFlag((String) object[16]);
		swfLog.setTaskNo(((BigDecimal) object[17]).intValue());
		swfLog.setTaskType((String) object[18]);
		swfLog.setNodeType((String) object[19]);
		swfLog.setTitleStr((String) object[20]);
		swfLog.setBusinessType((String) object[21]);
		swfLog.setRiskCode((String) object[22]);
		swfLog.setKeyIn((String) object[23]);
		swfLog.setKeyOut((String) object[24]);
		swfLog.setDeptName((String) object[25]);
		swfLog.setMainFlowID((String) object[26]);
		swfLog.setSubFlowID((String) object[27]);
		swfLog.setPosX(((BigDecimal) object[28]).intValue());
		swfLog.setPosY(((BigDecimal) object[29]).intValue());
		swfLog.setEndFlag((String) object[30]);
		swfLog.setBeforeHandlerCode((String) object[31]);
		swfLog.setBeforeHandlerName((String) object[32]);
		swfLog.setPolicyNo((String) object[33]);
		swfLog.setTypeFlag((String) object[34]);
		swfLog.setComCode((String) object[35]);
		swfLog.setScheduleID(((BigDecimal) object[36]).intValue());
		swfLog.setLossItemCode((String) object[37]);
		swfLog.setLossItemName((String) object[38]);
		swfLog.setInsureCarFlag((String) object[39]);
		swfLog.setHandlerRange((String) object[40]);
		swfLog.setExigenceGree((String) object[41]);
		swfLog.setRegistNo((String) object[42]);
		swfLog.setInsuredName((String) object[43]);
		swfLog.setProcessId((String) object[44]);
		swfLog.setActorId((String) object[45]);
		if (object[46] != null) {
			swfLog.setTaskId(((BigDecimal) object[46]).longValue());
		}
		swfLog.setBusinessId((String) object[47]);
		return swfLog;
	}

	@Override
	public Page findByPage(String conditions, Integer pageNo, Integer pageSize) throws Exception {
		String sql = "select * from SwfLog where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize,SwfLog.class);
	}

	/**
	 * 按条件对节点进行状态统计
	 */
	@Override
	public List<StatStatusDto> getNodeStatusStat(String condition) {
		if(DataUtils.emptyToNull(condition)==null){
			condition = " 1 = 1 ";
		}
		String statement = " SELECT c.NODETYPE," + " a.CODECNAME," + " c.nodeSTATUS," + " b.CODECNAME," + " count(*) " + " FROM swflog c LEFT JOIN PRPDCODE a ON c.NODETYPE = " + " a.CODECODE and a.CODETYPE = 'ClaimNodeType'"
				+ " LEFT JOIN PRPDCODE b ON c.nodeSTATUS+0 = b.CODECODE+0  and b.CODETYPE = 'ClaimStatus'" + " where " + condition + " GROUP BY  c.NODETYPE," + " a.CODECNAME," + " c.nodeSTATUS," + " b.CODECNAME" + " order by 2,3";
		List<?> result = HibernateUtils.findbySql(super.getSession(), statement);
		List<StatStatusDto> list = new ArrayList<StatStatusDto>();
		if(result!=null && !result.isEmpty()){
			StatStatusDto statStatusDto = null;
			Object[] object = null;
			for(Iterator<?> it = result.iterator();it.hasNext();list.add(statStatusDto)){
				object = (Object[])it.next();
				statStatusDto = new StatStatusDto();
				statStatusDto.setNodeType(String.valueOf(object[0]));
				statStatusDto.setNodeTypeName(String.valueOf(object[1]));
				statStatusDto.setStatus(String.valueOf(object[2]));
				statStatusDto.setStatusName(String.valueOf(object[3]));
				statStatusDto.setCount(((Number)object[4]).intValue());
			}
		}
		return list;
	}

	/**
	 * 按条件对节点进行状态统计用户
	 * @param conditions 统计条件
	 * @return List<StatStatusDto>
	 * @throws Exception
	 */
	public List<StatStatusDto> getNodeUserStatusStat(String conditions) {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1 = 1 ";
		}
		String statement = " SELECT c.NODETYPE," + " a.CODECNAME," + " c.handlerCode," + " c.handlerName," + " c.nodeSTATUS," + " b.CODECNAME," + " count(*) " + " FROM swflog c LEFT JOIN PRPDCODE a ON c.NODETYPE = "
				+ " a.CODECODE and a.CODETYPE = 'ClaimNodeType'" + " LEFT JOIN PRPDCODE b ON c.nodeSTATUS+0 = b.CODECODE+0  and b.CODETYPE = 'ClaimStatus'" + " where " + conditions + " GROUP BY  c.NODETYPE," + " a.CODECNAME," + " c.handlerCode,"
				+ " c.handlerName," + " c.nodeSTATUS," + " b.CODECNAME" + " order by 2,4,5";
		List<?> result = HibernateUtils.findbySql(super.getSession(), statement);
		List<StatStatusDto> list = new ArrayList<StatStatusDto>();
		if (result != null && !result.isEmpty()) {
			StatStatusDto statStatusDto = null;
			Object[] object = null;
			for (Iterator<?> it = result.iterator(); it.hasNext(); list.add(statStatusDto)) {
				object = (Object[]) it.next();
				statStatusDto = new StatStatusDto();
				statStatusDto.setNodeType(String.valueOf(object[0]));
				statStatusDto.setNodeTypeName(String.valueOf(object[1]));
				statStatusDto.setUserCode(String.valueOf(object[2]));
				statStatusDto.setUserName(String.valueOf(object[3]));
				statStatusDto.setStatus(String.valueOf(object[4]));
				statStatusDto.setStatusName(String.valueOf(object[5]));
				statStatusDto.setCount(((Number) object[6]).intValue());
			}
		}
		return list;
	}
	
	/**
	 * 按条件对节点进行状态统计
	 * @param conditions 统计条件
	 * @return Collection
	 * @throws Exception
	 */
	public List<StatStatusDto> getStatStatus(String conditions) throws Exception {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1 = 1 ";
		}
		String statement = " SELECT NodeType, " + " NodeName," + " NodeStatus," + " count(*) " + " FROM swfLog" + " WHERE " + conditions + " GROUP BY NodeType," + " NodeName," + "NodeStatus" + " order by NodeType,NodeStatus desc";
		List<?> result = HibernateUtils.findbySql(super.getSession(), statement);
		List<StatStatusDto> list = new ArrayList<StatStatusDto>();
		if (result != null && !result.isEmpty()) {
			StatStatusDto statStatusDto = null;
			Object[] object = null;
			for (Iterator<?> it = result.iterator(); it.hasNext(); list.add(statStatusDto)) {
				object = (Object[]) it.next();
				statStatusDto = new StatStatusDto();
				statStatusDto.setNodeType(String.valueOf(object[0]));
				statStatusDto.setNodeTypeName(String.valueOf(object[1]));
				statStatusDto.setStatus(String.valueOf(object[2]));
				statStatusDto.setCount(((Number) object[3]).intValue());
			}
		}
		return list;
	}

	@Override
	public int getMaxNodeLogNo(String flowID, String nodeType, String businessNo) {
		int logNo = -1;
		String statement = "Select max(LogNo) from swfLog Where flowID='" + flowID + "' and  nodeType='" + nodeType + "' and businessNo='" + businessNo + "'";
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		if (list != null && !list.isEmpty()) {
			Number num = (Number) list.get(0);
			if (num != null) {
				return num.intValue();
			}
		}
		if (logNo <= 0) {
			logNo = 1;
		}
		return logNo;
	}

	@Override
	public void updateFlowStatus(String flowID) throws Exception {
		String condition = " flowId='" + StringUtils.rightTrim(flowID) + "'";
		String statement = " update swflog set flowStatus='1' Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
	}
	
	@Override
	public void updateComCode(String flowID,String comCode) throws Exception {
		String condition = " flowId='" + StringUtils.rightTrim(flowID) + "'";
		String statement = " update swflog set comCode='"+comCode+"' Where " + condition;
		System.err.println("statement="+statement);
		HibernateUtils.executeSql(super.getSession(), statement);
	}

	@Override
	public void saveOrUpdate(SwfLog swfLog) throws Exception {
		if(DataUtils.emptyToNull(swfLog.getHandlerCode())==null){
			swfLog.setHandlerCode(SwfLog.HANDLERCODE_NONE);
		}
		Session session = super.getSession();
		session.saveOrUpdate(session.merge(swfLog));
		
	}

	@Override
	public int getCountViewSwfLogAll(String condition) {
		String sql = "Select count(*) from View_SwfLogAll Where " + condition;
		List<?> resultSet = HibernateUtils.findbySql(super.getSession(), sql);
		return Integer.valueOf(String.valueOf(resultSet.get(0)));
	}

	@Override
	public Page getUrgentCaseList(String condition, int pageNo, int pageSize) {
        StringBuffer buffer = new StringBuffer(200);
        //拼SQL语句
        buffer.append("SELECT ");
        buffer.append("swflog.registno,");
        buffer.append("swflog.policyno,");
        buffer.append("prplclaim.insuredname,");
        buffer.append("swflog.HandlerCode,");
        buffer.append("swflog.HandlerName,");
        buffer.append("swflog.FlowInTime,");
        buffer.append("swflog.comcode,");
        buffer.append("decode(swflog.NodeStatus,'0','未處理','1','正處理(佔號)','2','處理中(暫存)','3','核賠退回') As nodestatus,");
        buffer.append("decode(trunc(sysdate,'DD')-trunc(prplclaim.startapplypaydate,'DD'),Null,0,trunc(sysdate,'DD')-trunc(prplclaim.startapplypaydate,'DD')-1) As Dalydays ");
        buffer.append("FROM SwfLog,prplclaim ");
        buffer.append("where Not Exists (Select * From prplplan Where prplplan.claimno = prplclaim.claimno) ");
        buffer.append("And SwfLog.Registno = prplclaim.registno and ");        
        buffer.append(condition);
        buffer.append(" order by Dalydays Desc");
        Page page = HibernateUtils.findPagebySql(super.getSession(), buffer.toString(), pageNo, pageSize);
        List<SwfLog> resultList = new ArrayList<SwfLog>();
        SwfLog swfLog = null;
        Object[] object = null;
		for(Iterator<?> it = page.getResult().iterator();it.hasNext();resultList.add(swfLog)){
			object = (Object[]) it.next();// 每行记录不在是一个对象 而是一个数组
			swfLog = new SwfLog();
            swfLog.setRegistNo(String.valueOf(object[0]));
            swfLog.setPolicyNo(String.valueOf(object[1]));
            swfLog.setInsuredName(String.valueOf(object[2]));
            swfLog.setHandlerCode(String.valueOf(object[3]));
            swfLog.setHandlerName(DataUtils.dbNullToEmpty(String.valueOf(object[4])));
            swfLog.setFlowInTime(String.valueOf(object[5]));
            swfLog.setComCode(String.valueOf(object[6]));
            swfLog.setNodeStatus(String.valueOf(object[7]));
            swfLog.setTimeLimit(Integer.valueOf(String.valueOf(object[8])));//借用此字段来存储等待时间
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);
	}

	@Override
	public Page getUndwrtUrgentCaseList(String condition, int pageNo, int pageSize) {
        StringBuffer buffer = new StringBuffer(200);
        //拼SQL语句
        buffer.append("SELECT ");
        buffer.append("prplclaim.registno,");
        buffer.append("prplclaim.policyno,");
        buffer.append("prplclaim.insuredname,");
        buffer.append("wflog.operatorcode,");
        buffer.append("wflog.operatorname,");
        buffer.append("wflog.FlowInTime,");
        buffer.append("wflog.comcode,");
        buffer.append("decode(wflog.nodeno,'4','非車險核賠初審崗','5','非車險核賠二級C','6','非車險核賠二級B','7','非車險核賠二級A','8','非車險核賠一級C','9','非車險核賠一級B','10','非車險核賠一級A','12','非車險核賠一級AA') As nodeNo,");
        buffer.append("decode(trunc(sysdate,'DD')-trunc(prplclaim.startapplypaydate,'DD'),Null,0,trunc(sysdate,'DD')-trunc(prplclaim.startapplypaydate,'DD')-1) As Dalydays ");
        buffer.append("FROM wfLog,prplclaim ");
        buffer.append("where Not Exists (Select * From prplplan Where prplplan.claimno = prplclaim.claimno) ");
        buffer.append("And wfLog.claimno = prplclaim.claimno and ");        
        buffer.append(condition);
        buffer.append(" order by Dalydays Desc");
        Page page = HibernateUtils.findPagebySql(super.getSession(), buffer.toString(), pageNo, pageSize);
        List<SwfLog> resultList = new ArrayList<SwfLog>();
        SwfLog swfLog = null;
        Object[] object = null;
		for(Iterator<?> it = page.getResult().iterator();it.hasNext();resultList.add(swfLog)){
			object = (Object[]) it.next();// 每行记录不在是一个对象 而是一个数组
			swfLog = new SwfLog();
            swfLog.setRegistNo(String.valueOf(object[0]));
            swfLog.setPolicyNo(String.valueOf(object[1]));
            swfLog.setInsuredName(String.valueOf(object[2]));
            swfLog.setHandlerCode(String.valueOf(object[3]));
            swfLog.setHandlerName(DataUtils.dbNullToEmpty(String.valueOf(object[4])));
            swfLog.setFlowInTime(String.valueOf(object[5]));
            swfLog.setComCode(String.valueOf(object[6]));
            swfLog.setNodeStatus(String.valueOf(object[7]));
            swfLog.setTimeLimit(Integer.valueOf(String.valueOf(object[8])));//借用此字段来存储等待时间
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);
	}

	/**
  	 * 按自定义SQL查询多条数据
  	 * @param statement 自定义SQL（含Select）
  	 * @param pageNo 页码
  	 * @param recordPerPage 每页显示的行数
  	 * @return Page
  	 * @throws Exception 
  	 */
	@Override
	public Page findByStatement(String sql, int pageNo, int recordPerPage) {
		Page page = null;
		List<SwfLog> resultList = this.findListByStatement(sql, pageNo, recordPerPage);
		long count = HibernateUtils.getCountbySql(getSession(), sql);
		page = new Page((pageNo-1)*recordPerPage, count, recordPerPage, resultList);
		return page;
	}
	/**
  	 * 按自定义SQL查询多条数据
  	 * @param statement 自定义SQL（含Select）
  	 * @param pageNo 页码
  	 * @param recordPerPage 每页显示的行数
  	 * @return List<SwfLog>
  	 * @throws Exception 
  	 */
	@Override
	public List<SwfLog> findListByStatement(String sql, int pageNo, int recordPerPage) {
		SwfLog swfLog = null;
		List<SwfLog> resultList = new ArrayList<SwfLog>();
		List<?> tempList = HibernateUtils.findbySql(getSession(), sql, pageNo, recordPerPage);
    	for (Iterator<?> iter = tempList.iterator(); iter.hasNext();) {
			Object[] object = (Object[]) iter.next();// 每行记录不在是一个对象 而是一个数组
			swfLog = new SwfLog();
			swfLog.getId().setFlowID((String) object[0]);
			swfLog.getId().setLogNo(((BigDecimal) object[1]).intValue());
			swfLog.setNodeStatus((String) object[2]);
			swfLog.setRiskCode((String) object[3]);
			swfLog.setNodeType((String) object[4]);
			swfLog.setBusinessNo((String) object[5]);
			swfLog.setPolicyNo((String) object[6]);
			swfLog.setModelNo(((BigDecimal) object[7]).intValue());
			swfLog.setNodeNo(((BigDecimal) object[8]).intValue());
			swfLog.setInsuredName((String) object[9]);
			swfLog.setHandlerCode((String) object[10]);
			swfLog.setHandlerName((String) object[11]);
			if (swfLog.getHandlerName()==null || "".equals(swfLog.getHandlerName())) {
				swfLog.setHandlerName((String) object[25]);
			}
			swfLog.setTypeFlag((String) object[12]);
			swfLog.setRegistNo((String) object[13]);
			swfLog.setHandleTime((String) object[14]);
			swfLog.setKeyIn((String) object[15]);
			swfLog.setKeyOut((String) object[16]);
			swfLog.setLossItemCode((String) object[17]);
			swfLog.setLossItemName((String) object[18]);
			swfLog.setiFlowID((String) object[19]);
			swfLog.setiModelNo(((BigDecimal) object[20]).intValue());
			swfLog.setiNodeNo(((BigDecimal) object[21]).intValue());
			swfLog.setBusinessType((String) object[22]);
			swfLog.setiBusinessNo((String) object[23]);
			swfLog.setiLogNo(((BigDecimal) object[24]).intValue());

			resultList.add(swfLog);
			convertDto(swfLog, "view");
		}
		return resultList;
	}
	
	/**
     * 转换Dto
     * @param swfLog swfLog
     * @param mode 模式
     * @throws Exception
     */
	public void convertDto(SwfLog swfLog, String mode){
		if (swfLog == null) {
			return;
		}
		if (mode.equals("view")) {
			try {
				PrpDrisk prpDrisk = prpDriskService.findPrpDrisk(swfLog.getRiskCode());
				if (prpDrisk != null) {
					swfLog.setRiskCodeName(prpDrisk.getRiskCName());
				} else {
					swfLog.setRiskCodeName(swfLog.getRiskCode());
				}
				if(swfLog.getModelNo()!=null&&swfLog.getNodeNo()!=null) {
					SwfNodeId swfNodeId = new SwfNodeId();
					swfNodeId.setModelNo(swfLog.getModelNo());
					swfNodeId.setNodeNo(swfLog.getNodeNo());
					SwfNode SwfNode = swfNodeService.findSwfNode(swfNodeId);
					if(SwfNode!=null) {
						swfLog.setNodeName(SwfNode.getNodeName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 超时赔付的查询，方法的迁移
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findTimeOutByConditions(String conditions, int pageNo, int pageSize) throws Exception {
		StringBuffer buffer = new StringBuffer(200);
		// 拼SQL语句
		buffer.append("SELECT ");
		buffer.append("a.businessno,");
		buffer.append("a.policyno,");
		buffer.append("a.riskcode,");
		buffer.append("a.insuredname,");
		buffer.append("a.flowintime FROM");
		buffer.append(conditions);
		List<Object[]> list = (List<Object[]>) HibernateUtils.findbySql(super.getSession(), buffer.toString(),pageNo,pageSize);
		List<SwfLog> swfLogList = new ArrayList<SwfLog>();
		SwfLog swfLog = null;
		Object[] objs = null;
		DateTime dateTime = null;
		for (int i = 0; i < list.size(); i++) {
			objs = list.get(i);
			swfLog = new SwfLog();
			swfLog.setBusinessNo(objs[0].toString());
			swfLog.setPolicyNo(objs[1].toString());
			swfLog.setRiskCode(objs[2].toString());
			swfLog.setInsuredName(objs[3] == null ? "" : objs[3].toString());
			if (objs[4] != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dateTime = new DateTime(dateFormat.parse((String) objs[4]));
			}
			swfLog.setOperateDate(dateTime);
			this.convertDto(swfLog, "view");
			swfLogList.add(swfLog);
		}
		StringBuffer statement = new StringBuffer();
		statement.append("SELECT count(*) FROM ");
		statement.append(conditions);
		long count = HibernateUtils.getCountbyCountSql(super.getSession(), statement.toString());
		Page page = new Page((pageNo - 1) * pageSize, count, pageSize, swfLogList);
		return page;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}
	
}
