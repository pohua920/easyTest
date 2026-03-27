package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.sinosoft.common.schema.model.PrpCmainSub;
import com.sinosoft.prpall.dto.domain.PrpCmainDto;
import com.sinosoft.prpall.dto.domain.PrpTmainDto;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.model.PrpDclass;
import com.sinosoft.undwrt.common.model.PrpDrisk;
import com.sinosoft.undwrt.common.service.facade.PrpDclassService;
import com.sinosoft.undwrt.common.service.facade.PrpDriskService;
import com.sinosoft.undwrt.common.util.MsgAction;
import com.sinosoft.undwrt.common.vo.WfLogVo;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.model.WfLogId;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfFlowMainService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogExtService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfPackageService;
import com.sinosoft.utiall.blsvr.BLPrpDcompany;
import com.sinosoft.utiall.blsvr.BLPrpDconfigCode;
import com.sinosoft.platform.dto.domain.PrpDuserDto;

/**
 * 工作流日誌實現類.
 */
public class WfLogServiceSpringImpl extends GenericDaoHibernate<WfLog, WfLogId> implements WfLogService {

	/** 屬性工作流節點定義接口. */
	private SwfNodeService swfNodeService;

	/** 屬性定級信息接口. */
	private WfGradeService wfGradeService;

	/** 屬性核保處理意見接口. */
	private UwNotionService uwNotionService;

	/** 屬性工作流主表接口. */
	private WfFlowMainService wfFlowMainService;

	/** 屬性工作流包信息接口. */
	private WfPackageService wfPackageService;

	/** 屬性工作流日誌附屬接口. */
	private WfLogExtService wfLogExtService;

	/** 屬性消息發送接口. */
	private MsgAction msgAction;

	/** 屬性標志. */
	private String flag = "";

	/** 屬性工作流號. */
	private String lFlowID = "";

	/** 屬性序號. */
	private int lLogNo = 0;

	/** 屬性記錄關聯出單的車險商業險操作員代碼. */
	private String LogOperatorCode = "";

	/** 屬性是否關聯出單. */
	private boolean IsMainSub = false;

	/** 屬性是否審核通過節點. */
	private String nodeType = "";

	/** 屬性最終核保人代碼. */
	private String underWriteCode = "";

	/** 屬性核保日期. */
	private DateTime underWriteDate = new DateTime(new DateTime().current().toString().substring(0, 10));

	/** 屬性業務類型. */
	private char certiType;

	/** 工作流狀態接口. */
	private String status = "";

	/** 屬性要保書處理接口. */
	private PolicyService policyService;

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/** 屬性險種接口. */
	private PrpDriskService prpDriskService;

	/** 屬性險類接口. */
	private PrpDclassService prpDclassService;
	
	/** 用户获取session*/
	//mantis： ???????，處理人員：Sam，需求單編號：??????? 正式區無此CODE
//	private HttpSession httpSession;

	// -----------------------------------

	/**
	 * 根據條件查詢工作流日誌.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的集合
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#findByQueryRuleList(ins.framework.common.QueryRule)
	 */
	@Override
	public List<WfLog> findByQueryRuleList(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

	/**
	 * 根據條件查詢工作流日誌.
	 * 
	 * @param hql
	 *            查詢條件
	 * @return 滿足條件的集合
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#findByHqlList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WfLog> findByHqlList(String hql) {
		Session session = super.getSession();
		SQLQuery query = session.createSQLQuery(hql).addEntity(WfLog.class);
		List<WfLog> list = query.list();
		// return
		// super.getSession().createSQLQuery(hql).addEntity(WfLog.class).list();
		return list;
	}

	/**
	 * 根據條件查詢工作流日誌.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @param pageNo
	 *            頁碼
	 * @param pageSize
	 *            每頁的記錄條數
	 * @return page對象
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#findUserTaskList(ins.framework.common.QueryRule,
	 *      int, int)
	 */
	@Override
	public Page findUserTaskList(QueryRule queryRule, int pageNo, int pageSize) {

		Page page = null;
		// super.find(queryRule, pageNo, pageSize);
		List list = super.findBySql(queryRule.toString(), pageNo, pageSize);

		return page;
	}

	/**
	 * 根據主鍵查找工作流日誌.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 工作流日誌對象
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#findByPrimaryKey(ins.framework.common.QueryRule)
	 */
	@Override
	public WfLog findByPrimaryKey(QueryRule queryRule) {
		WfLog wflog = super.findUnique(queryRule);
		return wflog;
	}

	/**
	 * 根據工作流號查找工作流日誌.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的集合
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#findByFlowId(ins.framework.common.QueryRule)
	 */
	@Override
	public List<WfLog> findByFlowId(QueryRule queryRule) {

		return super.find(queryRule);
	}

	/**
	 * 根據条件 查找工作流日誌.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的集合
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#findByFlowId(ins.framework.common.QueryRule)
	 */
	@Override
	public List<WfLog> findByQueryRule(QueryRule queryRule) {

		return super.find(queryRule);
	}

	/**
	 * 根據條件查詢工作流日誌.
	 * 
	 * @param conditions
	 *            條件
	 * @return 滿足條件的集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#findByConditions(java.lang.String)
	 */
	public Collection findByConditions(String conditions) throws Exception {

		// 定义返回结果集合
		List<WfLog> list = super.getSession().createSQLQuery(conditions).addEntity(WfLog.class).list();
		return list;
	}

	/**
	 * 根據條件查詢工作流日誌.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @return 滿足條件的集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#findByConditions(java.lang.String,
	 *      int, int)
	 */
	@Override
	public Collection findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 提交節點.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param userCode
	 *            用戶代碼
	 * @param iTaskCode
	 *            任務代碼
	 * @throws Exception
	 *             異常
	 * @throws UserException
	 *             自定義異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#submitTaskQta(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void submitTaskQta(String businessNo, String userCode, String iTaskCode) throws Exception, UserException {
		// TODO Auto-generated method stub

	}

	/**
	 * 獲取回退節點列表.
	 * 
	 * @param FlowId
	 *            工作流號
	 * @param LogNo
	 *            序號
	 * @param nodeNo
	 *            節點號
	 * @return 回退節點的集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getBackList(java.lang.String,
	 *      int, int)
	 */
	@Override
	// modified by wangjun
	public Collection getBackList(String FlowId, int LogNo, int nodeNo) throws Exception {
		ArrayList wfLogList = new ArrayList();
		ArrayList logList = new ArrayList();
		WfLog wfLogDto = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.flowId", FlowId);
		queryRule.addLessThan("id.logNo", LogNo);
		queryRule.addLessThan("nodeNo", nodeNo);

		try {
			// 获得该流水号历史记录集，不含最近一条
			wfLogList = (ArrayList) super.find(queryRule);
			int wfLogListSize = wfLogList.size();
			Vector vecLogList = new Vector();
			int i = 0;
			// 过滤重复记录
			for (i = 0; i < wfLogListSize; i++) {
				wfLogDto = (WfLog) wfLogList.get(i);
				if (!vecLogList.contains(String.valueOf(wfLogDto.getNodeNo()) + "_" + String.valueOf(wfLogDto.getModelNo()))) {
					logList.add(wfLogDto);
				}
				// 组合节点号+模板号,用于过滤相同模板号且相同节点号的重复记录
				vecLogList.add(String.valueOf(wfLogDto.getNodeNo()) + "_" + String.valueOf(wfLogDto.getModelNo()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return logList;
	}

	/**
	 * 撤銷任務.
	 * 
	 * @param iFlowID
	 *            工作流號
	 * @param iLogNo
	 *            序號
	 * @return 成功返回true，失敗返回false
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#undo(java.lang.String,
	 *      int)
	 */
	@Override
	public boolean undo(String iFlowID, int iLogNo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 獲取提交用戶列表.
	 * 
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param flag
	 *            標誌
	 * @return 要提交的用戶列表集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getSubmitUserList(int,
	 *      int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Collection getSubmitUserList(int modelNo, int nodeNo, String businessType, String businessNo, String flag) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 檢查被保人曆史信息（曆史投保、曆史賠付）.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 被保人歷史信息
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#checkHistoryInfo(java.lang.String)
	 */
	@Override
	public Vector checkHistoryInfo(String businessNo) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 獲得工作流查詢視圖.
	 * 
	 * @param sql
	 *            查詢的sql
	 * @return 工作流查詢視圖
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getWorkFlowQueryView(java.lang.String)
	 */
	@Override
	public ArrayList getWorkFlowQueryView(String sql) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 獲得工作流日誌表某工作流號所有記錄中的最大序號.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 最大序號
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getMaxLogNo(ins.framework.common.QueryRule)
	 */
	@Override
	public int getMaxLogNo(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return super.find(queryRule).get(0).getId().getLogNo();
	}

	/**
	 * 交費計劃中的幣種信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 幣種信息
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getPlanCurrencyType(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Collection getPlanCurrencyType(String businessNo, String businessType) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 自定義獲取標的主信息，標的地址，標的郵編.
	 * 
	 * @param proposalNo
	 *            要保單號
	 * @param riskCode
	 *            險種代碼
	 * @return the custom prp titem kind list
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getCustomPrpTitemKindList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Collection getCustomPrpTitemKindList(String proposalNo, String riskCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 獲取用戶險別列表.
	 * 
	 * @param policyNo
	 *            保單號
	 * @param riskCode
	 *            險種代碼
	 * @return 滿足條件的集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getCustomPrpCitemKindList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Collection getCustomPrpCitemKindList(String policyNo, String riskCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 獲取用戶險別列表.
	 * 
	 * @param endorseNo
	 *            批單號
	 * @param riskCode
	 *            險種代碼
	 * @return 滿足條件的列表集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getCustomPrpPitemKindList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Collection getCustomPrpPitemKindList(String endorseNo, String riskCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 得到壹個危險單位的所用子信息.
	 * 
	 * @param businessNo
	 *            業務號碼
	 * @param itemNo
	 *            標的信息序號
	 * @param businessType
	 *            業務類型
	 * @return 滿足條件的列表集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getDangerItemList(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public Collection getDangerItemList(String businessNo, String itemNo, String businessType) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 得到指定危險單位序號的危險單位信息(只適用于T,P,E).
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位序號
	 * @return 符合條件的列表集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getDangerDetail(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public Collection getDangerDetail(String businessType, String businessNo, String dangerNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 獲取投保單信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 投保單信息類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getPrpTmain(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public PrpTmainDto getPrpTmain(String businessNo, String businessType) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 獲取保單信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保單信息類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getPrpCmain(java.lang.String)
	 */
	@Override
	public PrpCmainDto getPrpCmain(String businessNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 更新工作流日誌.
	 * 
	 * @param wfLog
	 *            工作流日誌對象
	 * @see ins.framework.dao.GenericDaoHibernate#update(java.io.Serializable)
	 */
	@Override
	public void update(WfLog wfLog) {
		InternationalizationUtil internal = new InternationalizationUtil();
		logger.debug(internal.getText("undwrt.service.wfLog.renewBusinessNo") + wfLog.getBusinessNo() + internal.getText("undwrt.service.wfLog.workflowStatue"));
		WfLog wfLogNew = super.get(wfLog.getId());
		super.update(wfLogNew);
		// super.update(wfLog);
	}

	/**
	 * 獲得業務類型，業務號數組.
	 * 
	 * @param riskCategory
	 *            險種大類
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 業務類型，業務號數組
	 * @throws Exception
	 *             異常
	 */
	private String[] getIdentifyTypeNumber(String riskCategory, String businessNo, String businessType) throws Exception {
		String identifyType = null, identifyNumber = null;
		return new String[] { identifyType, identifyNumber };
	}

	/**
	 * 查詢滿足條件的記錄數.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄數
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getCount(ins.framework.common.QueryRule)
	 */
	@Override
	public int getCount(QueryRule queryRule) {

		return super.find(queryRule).size();
	}

	/**
	 * 根據險種和機構判斷是否是否使用規則引擎.
	 * 
	 * @param iRiskCode
	 *            險種代碼
	 * @param iComCode
	 *            機構代碼
	 * @return true 使用規則引擎 false 不使用規則引擎
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public boolean isILog(String iRiskCode, String iComCode) throws UserException, Exception {
		boolean isILog = false;
		String strWhere = "";
		String strWhereCom = " 1=1 And validstatus = '1'  Start With Comcode = '" + iComCode + "' "
				+ " Connect By Prior Uppercomcode = Comcode And Uppercomcode <> Prior Comcode ";
		// 递归查询所有的上级机构
		BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
		blPrpDcompany.query(strWhereCom);
		strWhereCom = "'" + iComCode + "'";
		for (int i = 0; i < blPrpDcompany.getSize(); i++) {
			strWhereCom += ",'" + blPrpDcompany.getArr(i).getComCode() + "' ";
		}

		strWhere = " funtype = 'ILog' And recordtype = 'ILog' And riskcode = '" + iRiskCode + "' " + " And comcode in (" + strWhereCom + ")"
				+ " And validStatus = '1'";
		// 是否使用ILog配置在PrpDconfigCode表里面。
		BLPrpDconfigCode blPrpDconfigCode = new BLPrpDconfigCode();
		blPrpDconfigCode.query(strWhere);
		if (blPrpDconfigCode.getSize() > 0) {
			isILog = true;
		}
		return isILog;
	}

	/**
	 * 按條件查詢多條數據,爲了返回wflogVo集合..
	 * 
	 * @param sql
	 *            查詢條件
	 * @return 滿足條件的集合
	 * @throws Exception
	 *             異常
	 */
	public List<WfLogVo> findBySql(String sql) throws Exception {
		StringBuffer statement = new StringBuffer(200);
		statement.append(" SELECT FlowID,LogNo,ModelNo,NodeNo,NodeName,");
		statement.append(" DeptCode,DeptName,OperatorCode,OperatorName,");
		statement.append(" FlowInTime,TimeLimit,HandleTime,SubmitTime,");
		statement.append(" NodeStatus,FlowStatus,PackageID,BusinessType,");
		statement.append(" BusinessNo,ContractNo,ClassCode,RiskCode,");
		statement.append(" MakeCom,ComCode,HandlerCode,Handler1Code,");
		statement.append(" RelateFlowID,RelateLogNo,PosX,PosY,Flag,");
		statement.append(" LicenseNo,RelateContractNo,RiskCategory,InsuredCode,");
		statement.append(" InsuredName,IdentifyType,IdentifyNumber,ReinsStatus,");
		statement.append(" PolicyNo,ClaimNo,SumAmount,SumPremium,ResultCode,ResultContent,PassLevel,singleCode,singleMember FROM WfLog Where ");
		statement.append(sql);
		if (logger.isDebugEnabled()) {
			logger.debug(statement.toString());
		}
		List li = super.getSession().createSQLQuery(statement.toString()).list();
		Iterator it = li.iterator();
		int count = 0;
		// 定义返回结果集合
		List<WfLogVo> list = new ArrayList();
		WfLogVo wfLogDto = null;
		try {
			while (it.hasNext()) {
				count++;
				int index = 0;
				Object[] obj = (Object[]) it.next();
				wfLogDto = new WfLogVo();
				WfLogId id = new WfLogId();
				id.setFlowId((String) obj[index++]);
				id.setLogNo(((BigDecimal) obj[index++]).intValue());
				wfLogDto.setId(id);
				wfLogDto.setModelNo(((BigDecimal) obj[index++]).intValue());
				wfLogDto.setNodeNo(((BigDecimal) obj[index++]).intValue());
				wfLogDto.setNodeName((String) obj[index++]);
				wfLogDto.setDeptCode((String) obj[index++]);
				wfLogDto.setDeptName((String) obj[index++]);
				wfLogDto.setOperatorCode((String) obj[index++]);
				wfLogDto.setOperatorName((String) obj[index++]);
				wfLogDto.setFlowInTime((String) obj[index++]);
				wfLogDto.setTimeLimit(((BigDecimal) obj[index++]).intValue());
				wfLogDto.setHandleTime((String) obj[index++]);
				wfLogDto.setSubmitTime((String) obj[index++]);
				wfLogDto.setNodeStatus((String) obj[index++]);
				wfLogDto.setFlowStatus((String) obj[index++]);
				wfLogDto.setPackageId((String) obj[index++]);
				wfLogDto.setBusinessType((String) obj[index++]);
				wfLogDto.setBusinessNo((String) obj[index++]);
				wfLogDto.setContractNo((String) obj[index++]);
				wfLogDto.setClassCode((String) obj[index++]);
				wfLogDto.setRiskCode((String) obj[index++]);
				wfLogDto.setMakeCom((String) obj[index++]);
				wfLogDto.setComCode((String) obj[index++]);
				wfLogDto.setHandlerCode((String) obj[index++]);
				wfLogDto.setHandler1Code((String) obj[index++]);
				wfLogDto.setRelateFlowId((String) obj[index++]);
				wfLogDto.setRelateLogNo(((BigDecimal) obj[index++]).intValue());
				wfLogDto.setPosX(((BigDecimal) obj[index++]).intValue());
				wfLogDto.setPosY(((BigDecimal) obj[index++]).intValue());
				wfLogDto.setFlag((String) obj[index++]);
				wfLogDto.setLicenseNo((String) obj[index++]);
				wfLogDto.setRelateContractNo((String) obj[index++]);
				wfLogDto.setRiskCategory((String) obj[index++]);
				wfLogDto.setInsuredCode((String) obj[index++]);
				wfLogDto.setInsuredName((String) obj[index++]);
				wfLogDto.setIdentifyType((String) obj[index++]);
				wfLogDto.setIdentifyNumber((String) obj[index++]);
				wfLogDto.setReinsStatus((String) obj[index++]);
				wfLogDto.setPolicyNo((String) obj[index++]);
				wfLogDto.setClaimNo((String) obj[index++]);

				wfLogDto.setSumAmount(((BigDecimal) obj[index++]).doubleValue());
				wfLogDto.setSumPremium(((BigDecimal) obj[index++]).doubleValue());
				wfLogDto.setResultCode((String) obj[index++]);
				wfLogDto.setResultContent((String) obj[index++]);
				wfLogDto.setPassLevel((String) obj[index++]);
				// add by yangfang 2011-05-30增加出单员
				wfLogDto.setSingleCode((String) obj[index++]);
				wfLogDto.setSingleMember((String) obj[index++]);
				list.add(wfLogDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 關閉工作流時，置nodestatus爲"0".
	 * 
	 * @param flowID
	 *            工作流號
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void updateNodeStatusByFlowID(String flowID) throws SQLException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.flowId", flowID);
		List<WfLog> ListwfLog = this.findByFlowId(queryRule);
		for (int i = 0; i < ListwfLog.size(); i++) {
			WfLog wfLog = ListwfLog.get(i);
			wfLog.setNodeStatus("0");
			/*
			mantis： ???????，處理人員：Sam，需求單編號：???????--- start
			正式區無此CODE 功能為 當審核通過時 想多寫下列欄位(正式區不會寫) 但因為當初沒上到正式區 為維持兩邊程式一致 故先mark掉
			*/
			/*
			//add by xuhuiling 判断是否是审核通过的信息 20160908 begin
			if(wfLog.getNodeName().equals(internal.getText("undwrt.action.commonDealSubmit.checkPass"))){
				HttpSession session = this.getHttpSession();
				if(session != null){//防止獲取不到session
					PrpDuserDto prpDuser = (PrpDuserDto) session.getAttribute("user");
					//modify by xuhuilign 自動審核通過的數據獲取不到session所以空指針
					if(prpDuser != null){
						wfLog.setDeptCode(prpDuser.getLoginComCode());//登陸的公司代號
						wfLog.setDeptName(prpDuser.getComName());//登陸公司的姓名
						wfLog.setOperatorCode(prpDuser.getUserCode());//登陸用戶的編號
						wfLog.setOperatorName(prpDuser.getUserName());//登陸用戶姓名
						wfLog.setHandleTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//獲取當前系統的時間
				    }
				}
			}
			//add by xuhuiling 判断是否是审核通过的信息 20160908 end
			*/
			/* mantis： ???????，處理人員：Sam，需求單編號：??????? --- end */
		}
		this.saveAll(ListwfLog);
	}

	/**
	 * 根據險種代碼獲取險種大類代碼.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @return 險種大類代碼
	 * @throws Exception
	 *             異常
	 */
	private String getRiskCategoryByRiskCode(String riskCode) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("riskCode", riskCode);
		PrpDrisk prpDrisk = prpDriskService.findByPrimaryKey(queryRule);
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("classCode", prpDrisk.getPrpDclass().getClassCode());
		PrpDclass prpDclass = prpDclassService.findByPrimaryKey(queryRule);
		return prpDclass.getRiskCategory();
	}

	/**
	 * 根據部門和時間生成信息包號.
	 * 
	 * @param comCode
	 *            機構代碼
	 * @return 部門和時間生成信息包號
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String getSoleFlowID(String comCode) throws UserException, Exception {
		String flowID = "";
		String currentTime = new DateTime().current().toString();
		String currentYear = currentTime.substring(0, 4);
		String currentMonth = currentTime.substring(5, 7);
		String currentDay = currentTime.substring(8, 10);
		String currentHour = currentTime.substring(11, 13);
		String currentMinute = currentTime.substring(14, 16);
		String currentSecond = currentTime.substring(17, 19);
		String currentMM = currentTime.substring(20, 23);
		flowID = comCode + currentYear + currentMonth + currentDay + currentHour + currentMinute + currentSecond + currentMM;
		return flowID;

	}

	/**
	 * 保存工作流日誌.
	 * 
	 * @param wfLog
	 *            工作流日誌對象
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#save(com.sinosoft.undwrt.undwrtBase.model.WfLog)
	 */
	@Override
	public void save(WfLog wfLog) {
		// TODO Auto-generated method stub
		super.save(wfLog);
	}

	/**
	 * 獲得合約號.
	 * 
	 * @param riskCategory
	 *            險種大類代碼
	 * @param businessNo
	 *            業務號
	 * @return 滿足查詢條件的合約號
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getRelateContractNo(java.lang.String,
	 *      java.lang.String)
	 */
	public String getRelateContractNo(String riskCategory, String businessNo) throws Exception {
		String relateContractNo = ""; // lijibin:初始化要等于""，而不是null
		if (riskCategory.equals("Y"))// 货运险 水险
		{
			List collection = policyService.getPrpCmainByPolicyNo(businessNo).getPrpCmainSubs();
			// lijibin modify 20050823 不是所有的保单都预约协议号的
			if (collection.size() > 0) {
				PrpCmainSub prpCmainSub = (PrpCmainSub) collection.get(0);
				relateContractNo = prpCmainSub.getId().getMainPolicyNo();
			}
		}
		return relateContractNo;
	}

	/**
	 * 獲取屬性核保處理意見接口.
	 * 
	 * @return 屬性核保處理意見接口的值
	 */
	public UwNotionService getUwNotionService() {
		return uwNotionService;
	}

	/**
	 * 設置屬性核保處理意見接口.
	 * 
	 * @param uwNotionService
	 *            待設置的核保處理意見接口的值
	 */
	public void setUwNotionService(UwNotionService uwNotionService) {
		this.uwNotionService = uwNotionService;
	}

	/**
	 * 獲取屬性工作流節點定義接口.
	 * 
	 * @return 屬性工作流節點定義接口的值
	 */
	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	/**
	 * 設置屬性工作流節點定義接口.
	 * 
	 * @param swfNodeService
	 *            待設置的工作流節點定義接口的值
	 */
	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}

	/**
	 * 獲取屬性定級信息接口.
	 * 
	 * @return 屬性定級信息接口的值
	 */
	public WfGradeService getWfGradeService() {
		return wfGradeService;
	}

	/**
	 * 設置屬性定級信息接口.
	 * 
	 * @param wfGradeService
	 *            待設置的定級信息接口的值
	 */
	public void setWfGradeService(WfGradeService wfGradeService) {
		this.wfGradeService = wfGradeService;
	}

	/**
	 * 獲取屬性工作流主表接口.
	 * 
	 * @return 屬性工作流主表接口的值
	 */
	public WfFlowMainService getWfFlowMainService() {
		return wfFlowMainService;
	}

	/**
	 * 設置屬性工作流主表接口.
	 * 
	 * @param wfFlowMainService
	 *            待設置的工作流主表接口的值
	 */
	public void setWfFlowMainService(WfFlowMainService wfFlowMainService) {
		this.wfFlowMainService = wfFlowMainService;
	}

	/**
	 * 獲取屬性工作流包信息接口.
	 * 
	 * @return 屬性工作流包信息接口的值
	 */
	public WfPackageService getWfPackageService() {
		return wfPackageService;
	}

	/**
	 * 設置屬性工作流包信息接口.
	 * 
	 * @param wfPackageService
	 *            待設置的工作流包信息接口的值
	 */
	public void setWfPackageService(WfPackageService wfPackageService) {
		this.wfPackageService = wfPackageService;
	}

	/**
	 * 獲取屬性工作流日誌附屬接口.
	 * 
	 * @return 屬性工作流日誌附屬接口的值
	 */
	public WfLogExtService getWfLogExtService() {
		return wfLogExtService;
	}

	/**
	 * 設置屬性工作流日誌附屬接口.
	 * 
	 * @param wfLogExtService
	 *            待設置的工作流日誌附屬接口的值
	 */
	public void setWfLogExtService(WfLogExtService wfLogExtService) {
		this.wfLogExtService = wfLogExtService;
	}

	/**
	 * 獲取屬性工作流號.
	 * 
	 * @return 屬性工作流號的值
	 */
	public String getlFlowID() {
		return lFlowID;
	}

	/**
	 * 設置屬性工作流號.
	 * 
	 * @param lFlowID
	 *            待設置的工作流號的值
	 */
	public void setlFlowID(String lFlowID) {
		this.lFlowID = lFlowID;
	}

	/**
	 * 獲取屬性序號.
	 * 
	 * @return 屬性序號的值
	 */
	public int getlLogNo() {
		return lLogNo;
	}

	/**
	 * 設置屬性序號.
	 * 
	 * @param lLogNo
	 *            待設置的序號的值
	 */
	public void setlLogNo(int lLogNo) {
		this.lLogNo = lLogNo;
	}

	/**
	 * 獲取屬性是否審核通過節點.
	 * 
	 * @return 屬性是否審核通過節點的值
	 */
	public String getNodeType() {
		return nodeType;
	}

	/**
	 * 設置屬性是否審核通過節點.
	 * 
	 * @param nodeType
	 *            待設置的是否審核通過節點的值
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * 獲取屬性最終核保人代碼.
	 * 
	 * @return 屬性最終核保人代碼的值
	 */
	public String getUnderWriteCode() {
		return underWriteCode;
	}

	/**
	 * 設置屬性最終核保人代碼.
	 * 
	 * @param underWriteCode
	 *            待設置的最終核保人代碼的值
	 */
	public void setUnderWriteCode(String underWriteCode) {
		this.underWriteCode = underWriteCode;
	}

	/**
	 * 獲取屬性核保日期.
	 * 
	 * @return 屬性核保日期的值
	 */
	public DateTime getUnderWriteDate() {
		return underWriteDate;
	}

	/**
	 * 設置屬性核保日期.
	 * 
	 * @param underWriteDate
	 *            待設置的核保日期的值
	 */
	public void setUnderWriteDate(DateTime underWriteDate) {
		this.underWriteDate = underWriteDate;
	}

	/**
	 * 獲取屬性業務類型.
	 * 
	 * @return 屬性業務類型的值
	 */
	public char getCertiType() {
		return certiType;
	}

	/**
	 * 設置屬性業務類型.
	 * 
	 * @param certiType
	 *            待設置的業務類型的值
	 */
	public void setCertiType(char certiType) {
		this.certiType = certiType;
	}

	/**
	 * 獲取屬性標志.
	 * 
	 * @return 屬性標志的值
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * 設置屬性標志.
	 * 
	 * @param flag
	 *            待設置的標志的值
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 獲取屬性記錄關聯出單的車險商業險操作員代碼.
	 * 
	 * @return 屬性記錄關聯出單的車險商業險操作員代碼的值
	 */
	public String getLogOperatorCode() {
		return LogOperatorCode;
	}

	/**
	 * 設置屬性記錄關聯出單的車險商業險操作員代碼.
	 * 
	 * @param logOperatorCode
	 *            待設置的記錄關聯出單的車險商業險操作員代碼的值
	 */
	public void setLogOperatorCode(String logOperatorCode) {
		LogOperatorCode = logOperatorCode;
	}

	/**
	 * 檢查是否是關聯單.
	 * 
	 * @return 是返回true，否返回false
	 */
	public boolean isIsMainSub() {
		return IsMainSub;
	}

	/**
	 * 設置屬性是否關聯.
	 * 
	 * @param isMainSub
	 *            待設置的是否關聯的值
	 */
	public void setIsMainSub(boolean isMainSub) {
		IsMainSub = isMainSub;
	}

	/**
	 * 獲取工作流狀態接口.
	 * 
	 * @return 工作流狀態接口的值
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 設置工作流狀態接口.
	 * 
	 * @param status
	 *            待設置的工作流狀態的值
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 檢查是否理賠工作流數據.
	 * 
	 * @param flowID
	 *            工作流號
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param businessType
	 *            業務類型
	 * @return 是返回true，否返回false
	 * @throws Exception
	 *             異常
	 */
	public String checkSubmitClaim(String flowID, int modelNo, int nodeNo, String businessType) throws Exception {
		String businessNo = "";
		String flag = "0";
		// System.out.println("回退任务调用理赔工作流回退接口businessType="+businessType);
		if (businessType != null && (businessType.equals("C") || businessType.equals("Y"))) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", flowID.trim());
			int intCount = this.getCount(queryRule);

			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", flowID).addEqual("id.logNo", intCount - 1);
			WfLog wfLogOldDto = this.findByPrimaryKey(queryRule);

			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", flowID).addEqual("id.logNo", intCount);
			WfLog wfLogNewDto = this.findByPrimaryKey(queryRule);

			businessNo = wfLogNewDto.getBusinessNo();
			// 回退任务调用理赔工作流回退接口
			// System.out.println("回退任务调用理赔工作流回退接口inwfLogNewDto.getRelateFlowID()="+wfLogNewDto.getRelateFlowID());
			if (wfLogNewDto.getRelateFlowId() != null && !wfLogNewDto.getRelateFlowId().equals("")) {
				// System.out.println("回退任务调用理赔工作流回退接口in");
				// 是否审核通过节点
				queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.modelNo", modelNo);
				queryRule.addNotEqual("id.nodeNo", nodeNo);
				queryRule.addEqual("endFlag", "1");
				boolean blnResult = swfNodeService.checkEndflag(queryRule);

				String interMethod = "";
				// 如果当前节点不是审核通过节点
				if (!blnResult) {
					// System.out.println("非审核通过节点");
					// 如果是回退理赔节点
					if (nodeNo == 1) {
						interMethod = "backVericToCompp";
					} else {
						interMethod = "addInformationOnVeric";
					}
				} else // 如果当前的节点TO是核保通过节点
				{
					// System.out.println("审核通过节点");
					if (AppConfig.get("sysconst.AUTO_ENDCASE").equals("1") && (businessType.equals("C"))) // 自动结案
					{
						interMethod = "passVericAndCloseFlow";
					} else // 手工结案
					{
						interMethod = "passVeric";
					}
				}
			}
		}
		// FIX0406 add by zhangTC begin
		if (flag.equals("-99")) {
			throw new Exception("请求理赔工作流连接失败！请与系统管理员联系！");
		} else if (flag.equals("-98")) {
			throw new Exception("理赔工作流无返回值！请与系统管理员联系！");
		} else if (flag.equals("-1")) {
			throw new Exception("理赔工作流:没查询到工作流节点！请与系统管理员联系！");
		} else if (flag.equals("-2")) {
			throw new Exception("理赔工作流:业务号不是这个工作流上的业务号码！请与系统管理员联系！");
		} else if (flag.equals("-3")) {
			throw new Exception("理赔工作流:已经回退过了的案件的节点！请与系统管理员联系！");
		} else if (flag.equals("-5")) {
			throw new Exception("理赔工作流:工作流流程整理异常！请与系统管理员联系！");
		}
		// add by zhaolu 20060810 start
		else if (flag.equals("-10")) {
			throw new Exception("理赔工作流:归档号为空！请与系统管理员联系！");
		}
		// add by zhaolu 20060810 end
		else if (flag.equals("-998") || flag.equals("-999") || flag.equals("-997")) {
			throw new Exception("未知错误！请与系统管理员联系！");
		} else {
			flag = "0";
		}
		// FIX0406 add by zhangTC end
		return flag;
	}

	/**
	 * 獲取屬性包號.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @return 屬性包號的值
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService#getPackageId(java.lang.String)
	 */
	@Override
	public Collection getPackageId(String conditions) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 獲取屬性消息發送接口.
	 * 
	 * @return 屬性消息發送接口的值
	 */
	public MsgAction getMsgAction() {
		return msgAction;
	}

	/**
	 * 設置屬性消息發送接口.
	 * 
	 * @param msgAction
	 *            待設置的消息發送接口的值
	 */
	public void setMsgAction(MsgAction msgAction) {
		this.msgAction = msgAction;
	}

	/**
	 * 獲取屬性要保書處理接口.
	 * 
	 * @return 屬性要保書處理接口的值
	 */
	public PolicyService getPolicyService() {
		return policyService;
	}

	/**
	 * 設置屬性要保書處理接口.
	 * 
	 * @param policyService
	 *            待設置的要保書處理接口的值
	 */
	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	/**
	 * 獲取屬性批單處理接口.
	 * 
	 * @return 屬性批單處理接口的值
	 */
	public EndorseService getEndorseService() {
		return endorseService;
	}

	/**
	 * 設置屬性批單處理接口.
	 * 
	 * @param endorseService
	 *            待設置的批單處理接口的值
	 */
	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	/**
	 * 獲取屬性險種接口.
	 * 
	 * @return 屬性險種接口的值
	 */
	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	/**
	 * 設置屬性險種接口.
	 * 
	 * @param prpDriskService
	 *            待設置的險種接口的值
	 */
	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	/**
	 * 獲取屬性險類接口.
	 * 
	 * @return 屬性險類接口的值
	 */
	public PrpDclassService getPrpDclassService() {
		return prpDclassService;
	}

	/**
	 * 設置屬性險類接口.
	 * 
	 * @param prpDclassService
	 *            待設置的險類接口的值
	 */
	public void setPrpDclassService(PrpDclassService prpDclassService) {
		this.prpDclassService = prpDclassService;
	}
	
	/*
	mantis： ???????，處理人員：Sam，需求單編號：???????--- start
	正式區無此CODE
	*//*
	public HttpSession getHttpSession() {
		return httpSession;
	}
	@Override
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}*/
	/* mantis： ???????，處理人員：Sam，需求單編號：??????? --- end */
	
   /**
	 * 需求150更新狀態的sql語句
	 * @author xuhuiling
	 * @param sql 需要执行的sql
	 * @param dbManager 用于处理事物的请求，方便共享同一个连接
	 */
	public int updateMainStatus(String sql) throws Exception {
		Session session = super.getSession();
 		SQLQuery query = session.createSQLQuery(sql);
		int num =  query.executeUpdate();
		session.flush();
		return num;
	}
	
}
