package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sinosoft.claim.bl.action.custom.BLClaimAction;
import com.sinosoft.common.schema.model.PrpCPinsured;
import com.sinosoft.common.schema.model.PrpDBankInfo;
import com.sinosoft.common.schema.model.PrpJpayRefRecHis;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpPinsured;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpTinsured;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.model.PrpDclass;
import com.sinosoft.undwrt.common.model.PrpDcode;
import com.sinosoft.undwrt.common.model.PrpDcompany;
import com.sinosoft.undwrt.common.model.PrpDrisk;
import com.sinosoft.undwrt.common.model.PrpDuser;
import com.sinosoft.undwrt.common.service.facade.PrpDclassService;
import com.sinosoft.undwrt.common.service.facade.PrpDcodeService;
import com.sinosoft.undwrt.common.service.facade.PrpDcompanyService;
import com.sinosoft.undwrt.common.service.facade.PrpDriskService;
import com.sinosoft.undwrt.common.service.facade.PrpDuserService;
import com.sinosoft.undwrt.common.vo.NodeListVo;
import com.sinosoft.undwrt.common.vo.RiskCategoryCodeVo;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.SwfNode;
import com.sinosoft.undwrt.undwrtBase.model.SwfPath;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService;

/**
 * 核保處理任務服務實現類.
 */
public class TaskDealServiceSpringImpl extends GenericDaoHibernate implements TaskDealService {

	/** 屬性基礎代碼表接口. */
	private PrpDcodeService prpDcodeService;

	/** 屬性險類接口. */
	private PrpDclassService prpDclassService;

	/** 屬性險種接口. */
	private PrpDriskService prpDriskService;

	/** 屬性工作流節點定義接口. */
	private SwfNodeService swfNodeService;

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性機構接口. */
	private PrpDcompanyService prpDcompanyService;

	/** 屬性用戶訊息接口. */
	private PrpDuserService prpDuserService;

	/** 屬性工作流路徑定義. */
	private SwfPathService swfPathService;
	
	/** 屬性要保書處理接口. */
	private PolicyService policyService;
	
	/** 屬性批單處理接口*/
	private EndorseService endorseService;
	/**
	 * 獲取屬性工作流路徑定義.
	 * 
	 * @return 屬性工作流路徑定義的值
	 */
	public SwfPathService getSwfPathService() {
		return swfPathService;
	}

	/**
	 * 設置屬性工作流路徑定義.
	 * 
	 * @param swfPathService
	 *            待設置的工作流路徑定義的值
	 */
	public void setSwfPathService(SwfPathService swfPathService) {
		this.swfPathService = swfPathService;
	}

	/**
	 * 獲取屬性用戶訊息接口.
	 * 
	 * @return 屬性用戶訊息接口的值
	 */
	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	/**
	 * 設置屬性用戶訊息接口.
	 * 
	 * @param prpDuserService
	 *            待設置的用戶訊息接口的值
	 */
	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	/**
	 * 獲取屬性機構接口.
	 * 
	 * @return 屬性機構接口的值
	 */
	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	/**
	 * 設置屬性機構接口.
	 * 
	 * @param prpDcompanyService
	 *            待設置的機構接口的值
	 */
	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	/**
	 * 獲取屬性工作流日誌接口.
	 * 
	 * @return 屬性工作流日誌接口的值
	 */
	public WfLogService getWfLogService() {
		return wfLogService;
	}

	/**
	 * 設置屬性工作流日誌接口.
	 * 
	 * @param wfLogService
	 *            待設置的工作流日誌接口的值
	 */
	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
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

	/**
	 * 獲取屬性基礎代碼表接口.
	 * 
	 * @return 屬性基礎代碼表接口的值
	 */
	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	/**
	 * 設置屬性基礎代碼表接口.
	 * 
	 * @param prpDcodeService
	 *            待設置的基礎代碼表接口的值
	 */
	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	
	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	/**
	 * 獲取通用代碼類型.
	 * 
	 * @return 通用代碼類型集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#findIdentifyTypeList()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findIdentifyTypeList() throws Exception {
		// TODO Auto-generated method stub
		QueryRule queryRule = QueryRule.getInstance();// 获取QueryRule对象的Instance
		queryRule.addEqual("id.codeType", "IdentifyType");
		return prpDcodeService.findPrpDcodeList(queryRule);
	}

	/**
	 * 查找險種大類.
	 * 
	 * @return 險種大類集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#findRiskCodeByRiskCategory()
	 */
	@Override
	public List<RiskCategoryCodeVo> findRiskCodeByRiskCategory() throws Exception {
		// TODO Auto-generated method stub
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.codeType", "RiskCategory");
		queryRule.addEqual("id.validStatus", "1");
		List<PrpDcode> prpDcodeList = prpDcodeService.findPrpDcodeList(queryRule);
		
		List<RiskCategoryCodeVo> riskCodeCollection = new ArrayList<RiskCategoryCodeVo>();
		List<PrpDclass> classCollection = null;
		List<PrpDrisk> riskCollection = null;
		PrpDclass prpDclass = null;
		PrpDrisk prpDrisk = null;
		RiskCategoryCodeVo riskCategoryCodeVo = null;

		for (int i = 0; i < prpDcodeList.size(); i++) {
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("riskCategory", prpDcodeList.get(i).getId().getCodeCode()).addEqual("validStatus", "1");
			classCollection = prpDclassService.findPrpDclassList(queryRule);

			for (int j = 0; j < classCollection.size(); j++) {
				prpDclass = classCollection.get(j);

				queryRule.getRuleList().clear();
				queryRule.getQueryRuleList().clear();
				queryRule.addEqual("prpDclass.classCode", prpDclass.getClassCode()).addEqual("validStatus", "1").addAscOrder("riskCode");
				riskCollection = prpDriskService.findByQureyRuleList(queryRule);

				for (int k = 0; k < riskCollection.size(); k++) {
					prpDrisk = riskCollection.get(k);
					riskCategoryCodeVo = new RiskCategoryCodeVo();
					riskCategoryCodeVo.setRiskCategory(prpDcodeList.get(i).getId().getCodeCode());
					riskCategoryCodeVo.setRiskCode(prpDrisk.getRiskCode());
					riskCategoryCodeVo.setRiskName(prpDrisk.getRiskCName());
					riskCodeCollection.add(riskCategoryCodeVo);
				}
			}
		}
		return riskCodeCollection;
	}

	/**
	 * 查詢核保級別.
	 * 
	 * @param userCode
	 *            用戶代碼
	 * @param comCode
	 *            機構代碼
	 * @return 核保級別集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#findNodeList(java.lang.String)
	 */
	@Override
	public List<NodeListVo> findNodeList(String userCode, String comCode) throws Exception {
		// TODO Auto-generated method stub
		InternationalizationUtil internal = new InternationalizationUtil();
		QueryRule queryRule = QueryRule.getInstance();
		String strSQL = " modelNo = '23' AND nodeNo <= (SELECT MAX(nodeNo) FROM UtiUwLevel WHERE userCode = '" + userCode + "' AND comCode = '" + comCode
				+ "' AND uwType = 'T') AND nodeNo != '1' ORDER BY nodeNo DESC";
		queryRule.addSql(strSQL);
		List<SwfNode> nodeList = swfNodeService.findByQureyRuleList(queryRule);
		SwfNode swfNode = null;
		NodeListVo nodeListVo = null;
		List<NodeListVo> inodeList = new ArrayList<NodeListVo>();

		nodeListVo = new NodeListVo();
		nodeListVo.setNodeNo("B");
		nodeListVo.setNodeName(internal.getText("undwrt.service.taskDeal.includeAll"));
		inodeList.add(nodeListVo);
		
		nodeListVo = new NodeListVo();
		nodeListVo.setNodeNo("A");
		nodeListVo.setNodeName(internal.getText("undwrt.service.taskDeal.notInclude"));
		inodeList.add(nodeListVo);

		nodeListVo = new NodeListVo();
		nodeListVo.setNodeNo("P");
		nodeListVo.setNodeName(internal.getText("undwrt.service.taskDeal.checkPass"));
		inodeList.add(nodeListVo);

		for (int i = 0; i < nodeList.size(); i++) {
			swfNode = nodeList.get(i);
			nodeListVo = new NodeListVo();
			nodeListVo.setNodeNo(swfNode.getId().getNodeNo() + "");
			nodeListVo.setNodeName(swfNode.getNodeName());
			inodeList.add(nodeListVo);
		}
		return inodeList;
	}

	/**
	 * 根據條件查找報價單.
	 * 
	 * @param statement
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @param blnView
	 *            是否走視圖
	 * @return page對象
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#findByStatementQta(java.lang.String,
	 *      int, int, boolean)
	 */
	public Page findByStatementQta(String statement, int pageNo, int rowsPerPage, boolean bln) throws Exception {
		WfLog wfLogDto = null;
		// List<WfLog> wfLogList = wfLogService.findByHqlList(statement);
		List<Object[]> list = new ArrayList<Object[]>();
		List<WfLog> wfLogList = new ArrayList<WfLog>();
		try {
			list = this.getSession().createSQLQuery(statement).list();
			Iterator<Object[]> it = list.iterator();
			while (it.hasNext()) {
				int index = 0;
				wfLogDto = new WfLog();
				Object[] row = it.next();
				wfLogDto.setBusinessNo((String) row[0]);
				wfLogDto.setRiskCode((String) row[1]);
				wfLogDto.setClassCode((String) row[2]);
				wfLogDto.setContractNo((String) row[3]);
				wfLogDto.setInsuredName((String) row[4]);
				wfLogDto.setComCode((String) row[5]);
				wfLogDto.setNodeStatus((String) row[6]);
				wfLogDto.setFlowInTime(new DateTime((Date) row[7]).toString());
				wfLogDto.setOperatorCode((String) row[8]);
				wfLogDto.setLicenseNo((String) row[9]);
				wfLogDto.setBusinessType("B");

				wfLogList.add(wfLogDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int totalCount = wfLogList.size();
		List<WfLog> collection = new ArrayList<WfLog>();
		int startIndex = rowsPerPage * (pageNo - 1);
		int endIndex = startIndex + rowsPerPage;
		PrpDcompany prpDcompany = null;
		PrpDuser prpDuser = null;
		WfLog wfLog = null;
		for (int i = startIndex; i < endIndex; i++) {
			if (i >= totalCount) {
				break;
			}

			wfLog = wfLogList.get(i);
			prpDcompany = prpDcompanyService.findByPrimaryKey(wfLog.getComCode());
			prpDuser = prpDuserService.findByPrimaryKey(wfLog.getOperatorCode());
			wfLog.setComName(prpDcompany.getComCName());
			wfLog.setOperatorName(prpDuser.getUserName());

			collection.add(wfLogList.get(i));
		}
		Page page = new Page(0, collection.size(), 15, collection);
		return page;
	}

	/**
	 * 根據條件查找報價單.
	 * 
	 * @param statement
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @param bln
	 *            是否走視圖
	 * @return PageRecord 對象
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#findByStatementQtaPageRecord(java.lang.String,
	 *      int, int, boolean)
	 */
	public PageRecord findByStatementQtaPageRecord(String statement, int pageNo, int rowsPerPage, boolean bln) throws Exception {
		WfLog wfLogDto = null;
		// List<WfLog> wfLogList = wfLogService.findByHqlList(statement);
		List<WfLog> wfLogList = new ArrayList<WfLog>();
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = this.getSession().createSQLQuery(statement).list();
			Iterator<Object[]> it = list.iterator();
			while (it.hasNext()) {
				wfLogDto = new WfLog();
				Object[] row = it.next();
				wfLogDto.setBusinessNo((String) row[0]);
				wfLogDto.setRiskCode((String) row[1]);
				wfLogDto.setClassCode((String) row[2]);
				wfLogDto.setContractNo((String) row[3]);
				wfLogDto.setInsuredName((String) row[4]);
				wfLogDto.setComCode((String) row[5]);
				wfLogDto.setNodeStatus((String) row[6]);
				wfLogDto.setFlowInTime(new DateTime((Date) row[7]).toString());
				wfLogDto.setOperatorCode((String) row[8]);
				wfLogDto.setLicenseNo((String) row[9]);
				wfLogDto.setBusinessType("B");
				wfLogList.add(wfLogDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int totalCount = wfLogList.size();
		List<WfLog> collection = new ArrayList<WfLog>();
		int startIndex = rowsPerPage * (pageNo - 1);
		int endIndex = startIndex + rowsPerPage;
		PrpDcompany prpDcompany = null;
		PrpDuser prpDuser = null;
		WfLog wfLog = null;
		for (int i = startIndex; i < endIndex; i++) {
			if (i >= totalCount) {
				break;
			}

			wfLog = wfLogList.get(i);
			prpDcompany = prpDcompanyService.findByPrimaryKey(wfLog.getComCode());
			prpDuser = prpDuserService.findByPrimaryKey(wfLog.getOperatorCode());
			wfLog.setComName(prpDcompany.getComCName());
			wfLog.setOperatorName(prpDuser.getUserName());

			collection.add(wfLogList.get(i));
		}
		PageRecord pageRecord = new PageRecord(totalCount, pageNo, 1, rowsPerPage, collection);
		return pageRecord;
	}

	/**
	 * 根據條件查找所有報價單.
	 * 
	 * @param statement
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @param blnView
	 *            是否走視圖
	 * @return page對象
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#findAllByStatementQta(java.lang.String,
	 *      int, int, boolean)
	 */
	public Page findAllByStatementQta(String statement, int pageNo, int rowsPerPage, boolean bln) throws Exception {
		WfLog wfLogDto = null;
		List<WfLog> bigCollection = new ArrayList<WfLog>();
		List<Object[]> list = new ArrayList<Object[]>();

		// bigCollection = wfLogService.findByHqlList(statement);
		try {
			list = this.getSession().createSQLQuery(statement).list();
			Iterator<Object[]> it = list.iterator();
			while (it.hasNext()) {
				wfLogDto = new WfLog();
				Object[] row = it.next();
				wfLogDto.setBusinessNo((String) row[0]);
				wfLogDto.setRiskCode((String) row[1]);
				wfLogDto.setClassCode((String) row[2]);
				wfLogDto.setContractNo((String) row[3]);
				wfLogDto.setInsuredName((String) row[4]);
				wfLogDto.setComCode((String) row[5]);
				wfLogDto.setNodeStatus((String) row[6]);
				wfLogDto.setFlowInTime(new DateTime((Date) row[7]).toString());
				wfLogDto.setOperatorCode((String) row[8]);
				wfLogDto.setLicenseNo((String) row[9]);
				wfLogDto.setBusinessType("B");

				bigCollection.add(wfLogDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int totalCount = bigCollection.size();
		List collection = new ArrayList();
		int startIndex = rowsPerPage * (pageNo - 1);
		int endIndex = startIndex + rowsPerPage;
		PrpDcompany prpDcompany = null;
		PrpDuser prpDuser = null;
		WfLog wfLog = null;
		for (int i = 0; i < totalCount; i++) {
			if (i >= bigCollection.size()) {
				break;
			}

			wfLog = bigCollection.get(i);
			prpDcompany = prpDcompanyService.findByPrimaryKey(wfLog.getComCode());
			prpDuser = prpDuserService.findByPrimaryKey(wfLog.getOperatorCode());
			wfLog.setComName(prpDcompany.getComCName());
			wfLog.setOperatorName(prpDuser.getUserName());

			collection.add(bigCollection.get(i));
		}

		Page page = new Page(0, collection.size(), 15, collection);
		return page;
	}

	/**
	 * 根據條件查找工作流日誌.
	 * 
	 * @param statement
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @param bln
	 *            是否走視圖
	 * @return PageRecord對象
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#findByStatementPageRecord(java.lang.String,
	 *      int, int, boolean)
	 */
	public PageRecord findByStatementPageRecord(String statement, int pageNo, int rowsPerPage, boolean bln) throws Exception {
		bln = false;
		List<WfLog> bigCollection = new ArrayList<WfLog>();
		if (bln) {
			/*
			 * bigCollection = (List) dbView_wfLogAll.findByStatement(statement,
			 * 0, 0, true);
			 */
		} else {
			bigCollection = wfLogService.findByHqlList(statement);
		}
		int totalCount = bigCollection.size();
		List collection = new ArrayList();
		int startIndex = rowsPerPage * (pageNo - 1);
		int endIndex = startIndex + rowsPerPage;
		PrpDcompany prpDcompany = null;
		PrpDuser prpDuser = null;
		WfLog wfLog = null;
		// View_wfLogAllDto view_wfLogAllDto = null;
		String strAcciName = "";
		BLClaimAction blClaimAction = new BLClaimAction();
		for (int i = startIndex; i < endIndex; i++) {
			if (i >= bigCollection.size()) {
				break;
			}
			if (bln) {
				/*
				 * view_wfLogAllDto = (View_wfLogAllDto) bigCollection.get(i);
				 * prpDcompanyDto = dbPrpDcompany
				 * .findByPrimaryKey(view_wfLogAllDto.getComCode());
				 * view_wfLogAllDto.setComName(prpDcompanyDto.getComCName()); //
				 * modify by dengwenchun 2006-4-11 cause: //
				 * 核赔查询列表中增加事故者姓名(当险类为27并且是核赔业务时
				 * ，核赔查询列表中被保险人名称的值，根据立案号从prplacciperson表中获得AcciName) // begin
				 * if (view_wfLogAllDto.getClassCode().equals("27") &&
				 * (view_wfLogAllDto.getBusinessType().equals("C") ||
				 * view_wfLogAllDto .getBusinessType().equals("Y"))) {
				 * strAcciName = blClaimAction .findByPrimaryKey(dbManager,
				 * wfLogDto.getClaimNo()) .getPrpLacciPersonDto().getAcciName();
				 * if (strAcciName != null && !strAcciName.equals("")) {
				 * view_wfLogAllDto.setInsuredName(strAcciName); } }
				 */
			} else {
				wfLog = bigCollection.get(i);
				prpDcompany = prpDcompanyService.findByPrimaryKey(wfLog.getComCode());
				if(prpDcompany != null){
					wfLog.setComName(prpDcompany.getComCName());
				}
				//查询服务人员名称
				prpDuser = prpDuserService.findByPrimaryKey(wfLog.getHandler1Code());
				if(prpDuser != null){//给prpduser添加非空检验 add by xuhuiling
					wfLog.setHandler1Name(prpDuser.getUserName());
				}
				String busno = bigCollection.get(i).getBusinessNo();
				if("T".equals(wfLog.getBusinessType())){//要保
					String claimInsuredCode = "select * from prptinsured where  insuredflag = '2' and proposalno ='"+busno+"'";
					Session sessionH = super.getSessionFactory().getCurrentSession();
					Query query = sessionH.createSQLQuery(claimInsuredCode).addEntity(PrpTinsured.class);
					List<PrpTinsured> insuredList = query.list();
					for(int k=0;k<insuredList.size();k++){
						wfLog.setAppliName(insuredList.get(k).getInsuredName());
					}
					//add by xuhuiling 需求150 20160819 查詢數據庫四個屬性的值 begin
					//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
					String sql="select t.refuselimiteinsurance,t.listdetection,t.riskrating,t.workstatus,t.normastatus from prptmain t where t.proposalno ='"+busno+"'";
					//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END
					Query queryT = sessionH.createSQLQuery(sql);
					List<Object[]> prptmainList = queryT.list();
					for(int k=0;k<prptmainList.size();k++){
						Object[] obj = prptmainList.get(k);
						wfLog.setRefuseLimiteInsurance(obj[0]==null?"":obj[0]+"");
						wfLog.setListDetection(obj[1]==null?"":obj[1]+"");
						wfLog.setRiskRating(obj[2]==null?"":obj[2]+"");//風險等級
						wfLog.setWorkStatus(obj[3]==null?"":obj[3]+"");//工作狀態
						//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
						wfLog.setNormastatus(obj[4]==null?"":obj[4]+"");//地址正規化狀態
						//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END
					}
					//add by xuhuiling 需求150 20160819 查詢數據庫四個屬性的值 end
					
					//add by songxin 新增繳費資料輸入模塊 start
					PrpTmain prptmain = policyService.getPrpTmainByProposalNo(busno);
					if(null != prptmain ){				
						if(null != prptmain.getRiskCode() && ("A01".equals(prptmain.getRiskCode()) 
								|| "B01".equals(prptmain.getRiskCode()))){
							//add by xuhuiling 需求167 20161226 begin
							String superPay=this.querySuperpay(busno);
							wfLog.setSuperpay(superPay);
							//add by xuhuiling 需求167 20161226 end
							if(null != prptmain.getJfeeFlag() && "1".equals(prptmain.getJfeeFlag())){
								if(null != prptmain.getUnderWriteFlag() &&("5".equals(prptmain.getUnderWriteFlag()) 
										|| "6".equals(prptmain.getUnderWriteFlag()))){
									QueryRule queryRule = QueryRule.getInstance();
									queryRule.addEqual("id.certiNo", busno);
									List<PrpJpayRefRecHis> prpJpayRefRecHises = null;
									PrpJpayRefRecHis prpJpayRefRecHis = new PrpJpayRefRecHis();
									prpJpayRefRecHises = super.find(PrpJpayRefRecHis.class, queryRule);
									if(null!=prpJpayRefRecHises && prpJpayRefRecHises.size()>0) {
										wfLog.setUnderwriteflag(prptmain.getUnderWriteFlag());
										wfLog.setJfeeflag(prptmain.getJfeeFlag());
										wfLog.setRealpayrefflag("1");
									}else{
										wfLog.setUnderwriteflag(prptmain.getUnderWriteFlag());
										wfLog.setJfeeflag(prptmain.getJfeeFlag());
										wfLog.setRealpayrefflag("0");
									}
								}
							}
						}
					}
					//add by songxin 新增繳費資料輸入模塊 end
				}else if("P".equals(wfLog.getBusinessType())){
					String claimInsuredCode = "select * from prppinsured where  insuredflag = '2' and endorseno ='"+busno+"'";
					Session sessionH = super.getSessionFactory().getCurrentSession();
					Query query = sessionH.createSQLQuery(claimInsuredCode).addEntity(PrpPinsured.class);
					List<PrpPinsured> insuredList = query.list();
					for(int j=0;j<insuredList.size();j++){
						wfLog.setAppliName(insuredList.get(j).getInsuredName());
					}
				}
				if("E".equals(wfLog.getBusinessType()) || "P".equals(wfLog.getBusinessType())){
					String prppmain = "select * from prppmain where endorseno ='"+busno+"'";
					Session sessionH = super.getSessionFactory().getCurrentSession();
					Query query = sessionH.createSQLQuery(prppmain).addEntity(PrpPmain.class);
					List<PrpPmain> prpPmain = query.list();
					//add by xuhuiling 需求150 20160819 查詢數據庫四個屬性的值 begin
					String sql="select p.refuselimiteinsurance,p.listdetection,p.riskrating,p.workstatus from prppmain p where p.endorseno ='"+busno+"'";
					Query queryT = sessionH.createSQLQuery(sql);
					List<Object[]> prppmainList = queryT.list();
					for(int k=0;k<prppmainList.size();k++){
						Object[] obj = prppmainList.get(k);
						wfLog.setRefuseLimiteInsurance(obj[0]==null?"":obj[0]+"");
						wfLog.setListDetection(obj[1]==null?"":obj[1]+"");
						wfLog.setRiskRating(obj[2]==null?"":obj[2]+"");
						wfLog.setWorkStatus(obj[3]==null?"":obj[3]+"");
					}
					//add by xuhuiling 需求150 20160819 查詢數據庫四個屬性的值 end
					//add by songxin 新增繳費資料輸入模塊 start
					PrpPhead prpphead = endorseService.getPrpPheadByEndorseNo(busno);
					if(null != prpphead){
						if(null != prpphead.getRiskCode() && ("A01".equals(prpphead.getRiskCode()) 
								|| "B01".equals(prpphead.getRiskCode()))){
							if(null != prpphead.getUnderWriteFlag() && ("5".equals(prpphead.getUnderWriteFlag()) 
									|| "6".equals(prpphead.getUnderWriteFlag()))){
								PrpPmain prppmainNew = prpphead.getPrpPmains().get(0);
								if(null!=prppmainNew && prppmainNew.getChgPremium().intValue()>0){
									QueryRule queryRule = QueryRule.getInstance();
									queryRule.addEqual("id.certiNo", busno);
									List<PrpJpayRefRecHis> prpJpayRefRecHises = null;
									PrpJpayRefRecHis prpJpayRefRecHis = new PrpJpayRefRecHis();
									prpJpayRefRecHises = super.find(PrpJpayRefRecHis.class, queryRule);
									if(null!=prpJpayRefRecHises && prpJpayRefRecHises.size()>0) {
										wfLog.setUnderwriteflag(prpphead.getUnderWriteFlag());
										wfLog.setJfeeflag("1");
										wfLog.setRealpayrefflag("1");
									}else{
										wfLog.setUnderwriteflag(prpphead.getUnderWriteFlag());
										wfLog.setJfeeflag("1");
										wfLog.setRealpayrefflag("0");
									}
								}
							}
						}
					}
					//add by songxin 新增繳費資料輸入模塊 end
					wfLog.setSumPremium(prpPmain.get(0).getSumPremium().add(prpPmain.get(0).getChgPremium()).doubleValue());
				}
				//add by xuhuiling 需求150 20160819 begin
				if("B".equals(wfLog.getBusinessType())){//報價的添加四個
					Session sessionH = super.getSessionFactory().getCurrentSession();
					//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
					String sql="select q.refuselimiteinsurance,q.listdetection,q.riskrating,q.workstatus,q.normastatus from prpqmain q where q.proposalno ='"+busno+"'";
					//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END
					Query queryT = sessionH.createSQLQuery(sql);
					List<Object[]> prpqmainList = queryT.list();
					for(int k=0;k<prpqmainList.size();k++){
						Object[] obj = prpqmainList.get(k);
						wfLog.setRefuseLimiteInsurance(obj[0]==null?"":obj[0]+"");
						wfLog.setListDetection(obj[1]==null?"":obj[1]+"");
						wfLog.setRiskRating(obj[2]==null?"":obj[2]+"");
						wfLog.setWorkStatus(obj[3]==null?"":obj[3]+"");
						//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
						wfLog.setNormastatus(obj[4]==null?"":obj[4]+"");
						//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END
					}	
				}
				//add by xuhuiling 需求150 20160819 end
				//add by wangcan 2015/12/12
				double premium = wfLog.getSumPremium();
				int newpremium = (int)Math.rint(premium);
				wfLog.setPremium(newpremium);
				/*
				 * // 核赔查询列表中增加事故者姓名(当险类为27并且是核赔业务时，核赔查询列表中被保险人名称的值，
				 * 根据立案号从prplacciperson表中获得AcciName) if
				 * (wfLog.getClassCode().equals("27") &&
				 * (wfLog.getBusinessType().equals("C") || wfLog
				 * .getBusinessType().equals("Y"))) { strAcciName =
				 * blClaimAction .findByPrimaryKey(dbManager,
				 * wfLog.getClaimNo()) .getPrpLacciPersonDto().getAcciName(); if
				 * (strAcciName != null && !strAcciName.equals("")) {
				 * wfLog.setInsuredName(strAcciName); } }
				 */
			}
			collection.add(bigCollection.get(i));
		}

		PageRecord pageRecord = new PageRecord(totalCount, pageNo, 1, rowsPerPage, collection);
		return pageRecord;
	}

	/**
	 * 根據條件查找工作流日誌.
	 * 
	 * @param statement
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @param blnView
	 *            是否走視圖
	 * @return page對象
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#findByStatement(java.lang.String,
	 *      int, int, boolean)
	 */
	public Page findByStatement(String statement, int pageNo, int rowsPerPage, boolean bln) throws Exception {
		bln = false;
		List<WfLog> bigCollection = new ArrayList<WfLog>();
		if (bln) {
			/*
			 * bigCollection = (List) dbView_wfLogAll.findByStatement(statement,
			 * 0, 0, true);
			 */
		} else {
			bigCollection = wfLogService.findByHqlList(statement);
		}
		int totalCount = bigCollection.size();
		List collection = new ArrayList();
		int startIndex = rowsPerPage * (pageNo - 1);
		int endIndex = startIndex + rowsPerPage;
		PrpDcompany prpDcompany = null;
		PrpDuser prpDuser = null;
		WfLog wfLog = null;
		// View_wfLogAllDto view_wfLogAllDto = null;
		String strAcciName = "";
		
		BLClaimAction blClaimAction = new BLClaimAction();
		for (int i = startIndex; i < endIndex; i++) {
			if (i >= bigCollection.size()) {
				break;
			}
			if (bln) {
				/*
				 * view_wfLogAllDto = (View_wfLogAllDto) bigCollection.get(i);
				 * prpDcompanyDto = dbPrpDcompany
				 * .findByPrimaryKey(view_wfLogAllDto.getComCode());
				 * view_wfLogAllDto.setComName(prpDcompanyDto.getComCName()); //
				 * modify by dengwenchun 2006-4-11 cause: //
				 * 核赔查询列表中增加事故者姓名(当险类为27并且是核赔业务时
				 * ，核赔查询列表中被保险人名称的值，根据立案号从prplacciperson表中获得AcciName) // begin
				 * if (view_wfLogAllDto.getClassCode().equals("27") &&
				 * (view_wfLogAllDto.getBusinessType().equals("C") ||
				 * view_wfLogAllDto .getBusinessType().equals("Y"))) {
				 * strAcciName = blClaimAction .findByPrimaryKey(dbManager,
				 * wfLogDto.getClaimNo()) .getPrpLacciPersonDto().getAcciName();
				 * if (strAcciName != null && !strAcciName.equals("")) {
				 * view_wfLogAllDto.setInsuredName(strAcciName); } }
				 */
			} else {
				wfLog = bigCollection.get(i);
				//mantis： OTH0169 ，處理人員： DP0708 ，需求單編號： OTH0169  業務員資料異動檢核 Start
				if(wfLog.getComCode()!=null&&!"".equals(wfLog.getComCode())){
					prpDcompany = prpDcompanyService.findByPrimaryKey(wfLog.getComCode());
					if(prpDcompany != null){
						wfLog.setComName(prpDcompany.getComCName());	
					}
				}
				if(wfLog.getHandler1Code()!=null&&!"".equals(wfLog.getHandler1Code())){
					prpDuser = prpDuserService.findByPrimaryKey(wfLog.getHandler1Code());
					if(prpDuser != null){
						wfLog.setHandler1Name(prpDuser.getUserName());	
					}
				}
				//mantis： OTH0169 ，處理人員： DP0708 ，需求單編號： OTH0169  業務員資料異動檢核 End
				String busno = bigCollection.get(i).getBusinessNo();
				if("T".equals(wfLog.getBusinessType())){
					String claimInsuredCode = "select * from prptinsured where  insuredflag = '2' and proposalno ='"+busno+"'";
					Session sessionH = super.getSessionFactory().getCurrentSession();
					Query query = sessionH.createSQLQuery(claimInsuredCode).addEntity(PrpTinsured.class);
					List<PrpTinsured> insuredList = query.list();
					for(int k=0;k<insuredList.size();k++){
						wfLog.setAppliName(insuredList.get(k).getInsuredName());
					}
				}else if("P".equals(wfLog.getBusinessType()) || "E".equals(wfLog.getBusinessType())){//批單
					String claimInsuredCode = "select * from prpcpinsured where  insuredflag = '2' and " +
							"policyno = (select policyno from prpphead where endorseno = '"+busno+"')";
					//add by lidongdong 20160606 reason:批改後查詢顯示批改後名字 begin
					String claimInsuredCode2= "select * from prpcpinsured where  insuredflag = '1' and " +
							"policyno = (select policyno from prpphead where endorseno = '"+busno+"')";
					Session sessionH = super.getSessionFactory().getCurrentSession();
					Query query = sessionH.createSQLQuery(claimInsuredCode).addEntity(PrpCPinsured.class);
					Query query2 = sessionH.createSQLQuery(claimInsuredCode2).addEntity(PrpCPinsured.class);
					List<PrpCPinsured> insuredList2 = query2.list();
					List<PrpCPinsured> insuredList = query.list();
					for(int j=0;j<insuredList.size();j++){
						wfLog.setAppliName(insuredList.get(j).getInsuredName());
					}
					for(int j=0;j<insuredList2.size();j++){
						wfLog.setInsuredName(insuredList2.get(0).getInsuredName());
					}
					//add by lidongdong 20160606 reason:批改後查詢顯示批改後名字 end
				}
			
				/*
				 * // 核赔查询列表中增加事故者姓名(当险类为27并且是核赔业务时，核赔查询列表中被保险人名称的值，
				 * 根据立案号从prplacciperson表中获得AcciName) if
				 * (wfLog.getClassCode().equals("27") &&
				 * (wfLog.getBusinessType().equals("C") || wfLog
				 * .getBusinessType().equals("Y"))) { strAcciName =
				 * blClaimAction .findByPrimaryKey(dbManager,
				 * wfLog.getClaimNo()) .getPrpLacciPersonDto().getAcciName(); if
				 * (strAcciName != null && !strAcciName.equals("")) {
				 * wfLog.setInsuredName(strAcciName); } }
				 */
			}
			collection.add(bigCollection.get(i));
		}

		Page page = new Page(0, collection.size(), 15, collection);
		return page;
	}
	//add by xuhuiling 需求150 查詢工作狀態  begin
	public String  getRenGongKaiGuanStatu() throws Exception{
		String sql = "select t.Valuetype from uticonfig t where t.configcode = 'MANUALMAINTENANCESWITCH'";
		Session sessionH = super.getSessionFactory().getCurrentSession();
		Query queryT = sessionH.createSQLQuery(sql);
		String renGongKaiGuanStatu = "";
		List<Object> prptmainList = queryT.list();
		for(int k=0;k<prptmainList.size();k++){
			Object obj = prptmainList.get(k);
			renGongKaiGuanStatu = obj==null?"":obj+"";
		}
		return renGongKaiGuanStatu;
	}
	//add by xuhuiling 需求150 查询人工工作状态 end

	/**
	 * 獲取選中的所有記錄的提交路徑.
	 * 
	 * @param checkboxSelectCollection
	 *            選中的所有記錄集合
	 * @return 提交路徑得集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#prepareBatchSubmitSuperior(java.util.List)
	 */
	public List[] prepareBatchSubmitSuperior(List checkboxSelectCollection) throws Exception {
		List[] submitList = { new ArrayList(), new ArrayList() };// 0:yesToSubmit;
																	// 1:noToSubmit.
		SwfPath swfPath = null;
		WfLog wfLog = null;
		for (int i = 0; i < checkboxSelectCollection.size(); i++) {
			wfLog = (WfLog) checkboxSelectCollection.get(i);
			swfPath = this.getPathEndNode(wfLog);
			if (wfLog != null) {
				wfLog.setNodeNo(Integer.parseInt(swfPath.getEndNodeNo()));
				wfLog.setNodeName(swfPath.getEndNodeName());
				submitList[0].add(wfLog);
			} else {
				submitList[1].add(wfLog);
			}
		}
		return submitList;
	}

	// 查询以某节点为起始节点的（所有）路径上的终止节点。
	// 该终止节点不能是出单员节点且不能是审核通过节点。
	/**
	 * 獲取屬性路徑終止節點.
	 * 
	 * @param wfLog
	 *            工作流日誌
	 * @return 屬性路徑終止節點的值
	 * @throws Exception
	 *             異常
	 */
	public SwfPath getPathEndNode(WfLog wfLog) throws Exception {
		// 根据ModelNo和NodeNo（WfLog.ModelNo=SwfPath.ModelNo and
		// WfLog.NodeNo=SwfPath.StartNodeNo）
		// 从SwfPath表查出EndNodeNo（上级节点）.
		SwfPath swfPath = null, swfPath2 = null;
		SwfNode swfNode = null;
		int priority = Integer.MIN_VALUE;
		int modelNo = wfLog.getModelNo(), nodeNo = wfLog.getNodeNo();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("modelNo", modelNo);
		queryRule.addEqual("startNodeNo", nodeNo);
		List<SwfPath> bigSwfPathList = swfPathService.getSwfPathList(queryRule);

		for (int i = 0; i < bigSwfPathList.size(); i++) {
			swfPath = bigSwfPathList.get(i);
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.modelNo", modelNo);
			queryRule.addEqual("id.nodeNo", swfPath.getEndNodeNo());
			swfNode = swfNodeService.findByPrimaryKey(queryRule);
			if (swfNode != null && swfNode.getId().getNodeNo() != 1 && !swfNode.getEndFlag().equals("1")) {
				if (swfPath.getPriority() > priority) {
					swfPath2 = swfPath;
					priority = swfPath.getPriority();
				}
			}
		}
		return swfPath2;
	}

	/**
	 * 批量提交上級.
	 * 
	 * @param taskCollection
	 *            任務集合
	 * @param notionCollection
	 *            核保意見類集合
	 * @param prpDuserDto
	 *            用戶信息類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#batchSubmitSuperior(java.util.List,
	 *      java.util.List, com.sinosoft.platform.dto.domain.PrpDuserDto)
	 */
	@Override
	public void batchSubmitSuperior(List taskCollection, List notionCollection, PrpDuserDto prpDuserDto) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 獲取選中的所有記錄的下發路徑.
	 * 
	 * @param checkboxSelectCollection
	 *            選中的所有記錄
	 * @return 下發路徑得集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#prepareBatchSubmitJunior(java.util.List)
	 */
	@Override
	public List prepareBatchSubmitJunior(List checkboxSelectCollection) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 批量下發修改.
	 * 
	 * @param taskCollection
	 *            任務集合
	 * @param notionCollection
	 *            核保意見類集合
	 * @param prpDuserDto
	 *            用戶信息類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#batchSubmitJunior(java.util.List,
	 *      java.util.List, com.sinosoft.platform.dto.domain.PrpDuserDto)
	 */
	@Override
	public void batchSubmitJunior(List taskCollection, List notionCollection, PrpDuserDto prpDuserDto) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 批量撤銷.
	 * 
	 * @param checkboxSelectCollection
	 *            選中的記錄集合
	 * @return 記錄集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#prepareBatchUndo(java.util.List)
	 */
	@Override
	public List[] prepareBatchUndo(List checkboxSelectCollection) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 批量撤銷.
	 * 
	 * @param taskCollection
	 *            任務集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService#batchUndo(java.util.List)
	 */
	@Override
	public void batchUndo(List taskCollection) throws Exception {
		// TODO Auto-generated method stub

	}
//add by xuhuiling 需求150 獲取工作狀態 20160823 begin
	public String getWorkStatusForBusiNo(String busiNo,String busiType) throws Exception {
		String queryWorkstatusSql = "";
		String workStatus = "";
		if(busiType.equals("T")){
			queryWorkstatusSql="select t.workstatus from prptmain t where t.proposalno ='"+busiNo+"'";
		}else if(busiType.equals("B")){
			queryWorkstatusSql="select t.workstatus from prpqmain t where t.proposalno ='"+busiNo+"'";
		}else if(busiType.equals("E")){
			queryWorkstatusSql="select t.workstatus from prppmain t where t.endorseno ='"+busiNo+"'";
		}
		if(queryWorkstatusSql!=null && !"".equals(queryWorkstatusSql)){
			Session sessionH = super.getSessionFactory().getCurrentSession();
			Query queryT = sessionH.createSQLQuery(queryWorkstatusSql);
			List<Object> prptmainList = queryT.list();
			for(int k=0;k<prptmainList.size();k++){
				Object obj = prptmainList.get(k);
				workStatus=obj==null?"":obj+"";//工作狀態
			}
		}
		return workStatus;
	}
	/**
	 * songxin
	 * 獲取要保書的費用信息和核保狀態
	 * @param busiNo
	 * @return
	 */
	public String getPayrefAndUnd(String busiNo) throws Exception{
		String flag = "";
		PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(busiNo);
		if(null!=prpTmain) {
			if(null!=prpTmain.getRiskCode() && ("A01".equals(prpTmain.getRiskCode())
					|| "B01".equals(prpTmain.getRiskCode()))) {
				if(null!=prpTmain.getJfeeFlag() && "1".equals(prpTmain.getJfeeFlag())) {
					if(null!=prpTmain.getUnderWriteFlag() 
							&& ("5".equals(prpTmain.getUnderWriteFlag())|| "6".equals(prpTmain.getUnderWriteFlag()))) {
						QueryRule queryRule = QueryRule.getInstance();
						queryRule.addEqual("id.certiNo", busiNo);
						List<PrpJpayRefRecHis> prpJpayRefRecHises = null;
						PrpJpayRefRecHis prpJpayRefRecHis = new PrpJpayRefRecHis();
						prpJpayRefRecHises = super.find(PrpJpayRefRecHis.class, queryRule);
						if(null!=prpJpayRefRecHises && prpJpayRefRecHises.size()>0) {
							flag = "1";//已經實收的
						} else {
							flag = "2";//可以進行輸入繳費資料的
						}
					} else {
						flag = "3";//不是預核保通過狀態
					}
				} else {
					flag = "4";//不是見費出單的要保書
				}
			} else {
				flag = "5";//不是強制險/任意險的要保書
			}
		}
		return flag;
	}
	/**
	 * @author zhangruofei
	 * 更新介接表的實收狀態
	 * @param busiNo
	 * @return
	 */
	public void saveIntfPrpjpayrefrec(String[] busiNo,String certitype) throws Exception{
		DBManager dbManager = new DBManager();
		String statement = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfnew = new SimpleDateFormat("yyyyMMdd");
		Date sysDate = new Date();
		String todaydate = sdfnew.format(sysDate);
		String Payrefdate = Integer.parseInt(busiNo[4].substring(0, 3))+1911+busiNo[4].substring(3, busiNo[4].length());
		String expireDate = "";
		if(null != busiNo[8] && !"".equals(busiNo[8])){
			expireDate = Integer.parseInt(busiNo[8].substring(0, 3))+1911+busiNo[8].substring(3, busiNo[8].length());
		}
		//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 START
		try{
			dbManager.open("undwrtDataSource");
			dbManager.beginTransaction();
			if(null != busiNo[8] && !"".equals(busiNo[8])){
				statement = "update intfprpjpayrefrec set "
				          +"realpayrefflag = ?,Exchangeratecny=?,Realpayreffeecny=?,Payrefno=?,"
						  +"Payrefdate=?,payWay=?,mainPolicyno=?,payDate=?,checkAccount=?,payAmount=?,expireDate=?,"
				          +"creditAmount=?,checkAmount=?,issuerName=?,checkpayFlag=?,checkpayown=?,"
				          +"checkpayTime=?,approvalCode=?"
				          +" where certino in (?,?)  and certitype = ?";
					dbManager.prepareStatement(statement);
					dbManager.setString(1, "1");//實收標誌
					dbManager.setString(2, "1");//實收本位幣匯率
					dbManager.setString(3, "0");//實收本位幣金額
					dbManager.setString(4, "AR"+todaydate+"0001");//支付單號
					dbManager.setDateTime(5, new DateTime(sdf.parse(Payrefdate)));//收付日期
					dbManager.setString(6, busiNo[5]);//繳費方式
					dbManager.setString(7, busiNo[3]);//強制證號
					dbManager.setDateTime(8, new DateTime(sdf.parse(Payrefdate)));//繳費日期
					dbManager.setString(9, busiNo[6]);//支票號碼
					dbManager.setString(10, busiNo[7]);//收費金額
					dbManager.setDateTime(11, new DateTime(sdf.parse(expireDate)));//到期日期
					dbManager.setString(12, busiNo[9]);//信用卡金額
					dbManager.setString(13, busiNo[10]);//支票金額
					dbManager.setString(14, busiNo[11]);//開票人
					dbManager.setString(15, "1");//是否收費註記
					dbManager.setString(16, busiNo[13]);//收費註記執行人
					//dbManager.setString(17, new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(sysDate));//收費註記執行時間
					dbManager.setDateTime(17, new DateTime(new Date(),16));//收費註記執行時間
					dbManager.setString(18, busiNo[12]);//授權碼
					dbManager.setString(19, busiNo[0]);
					dbManager.setString(20,busiNo[3]);
					dbManager.setString(21,certitype);	
			}else{
				statement = "update intfprpjpayrefrec set "
				          +"realpayrefflag = ?,Exchangeratecny=?,Realpayreffeecny=?,Payrefno=?,"
						  +"Payrefdate=?,payWay=?,mainPolicyno=?,payDate=?,checkAccount=?,payAmount=?,"
				          +"creditAmount=?,checkAmount=?,issuerName=?,checkpayFlag=?,checkpayown=?,"
						  +"checkpayTime=?,approvalCode=?"
				          +" where certino in (?,?)  and certitype = ?";
					dbManager.prepareStatement(statement);
					dbManager.setString(1, "1");//實收標誌
					dbManager.setString(2, "1");//實收本位幣匯率
					dbManager.setString(3, "0");//實收本位幣金額
					dbManager.setString(4, "AR"+todaydate+"0001");//支付單號
					dbManager.setDateTime(5, new DateTime(sdf.parse(Payrefdate)));//收付日期
					dbManager.setString(6, busiNo[5]);//繳費方式
					dbManager.setString(7, busiNo[3]);//強制證號
					dbManager.setDateTime(8, new DateTime(sdf.parse(Payrefdate)));//繳費日期
					dbManager.setString(9, busiNo[6]);//支票號碼
					dbManager.setString(10, busiNo[7]);//收費金額
					dbManager.setString(11, busiNo[9]);//信用卡金額
					dbManager.setString(12, busiNo[10]);//支票金額
					dbManager.setString(13, busiNo[11]);//開票人
					dbManager.setString(14, "1");//是否收費註記
					dbManager.setString(15, busiNo[13]);//收費註記執行人
					dbManager.setDateTime(16, new DateTime(new Date(),16));//收費註記執行時間
					dbManager.setString(17, busiNo[12]);//授權碼
					dbManager.setString(18, busiNo[0]);
					dbManager.setString(19,busiNo[3]);
					dbManager.setString(20,certitype);	
			}
			dbManager.executePreparedUpdate();
			dbManager.commitTransaction();
		}catch(Exception e){
			dbManager.rollbackTransaction();
			e.printStackTrace();
		}finally{
			dbManager.close();
		}
		//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 END	
	}
	/**
	 * 银行信息查詢
	 * @param bankCode 銀行代碼
	 * @return String 銀行名稱
	 */
	public PrpDBankInfo queryBankInfo(String bankCode) throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		if (!"".equals(bankCode)) {
			queryRule.addEqual("bankCode", bankCode.trim());
		}
		PrpDBankInfo prpDBankInfo = super.findUnique(PrpDBankInfo.class, queryRule);
		return prpDBankInfo;
	}
	public List queryPrpDprint(String busiNo) throws Exception {
		List list = null;
		String hql = "from PrpDprint where proposalNo= '" + busiNo + "'";
		list = this.findByHql(hql);
		return list;
	}
	/**
	 * 
	 * <p>功能描述:[方法功能中文描述]</p>
	 * @param busiNo
	 * @return
	 * @throws Exception
	 * @author:xuhuiling
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public String querySuperpay(String busiNo) throws Exception{
		String queryquerySuperPaySql = "";
		String superPay = "";
		queryquerySuperPaySql="select t.superPay from prptmain t where t.proposalno ='"+busiNo+"'";
		if(queryquerySuperPaySql!=null && !"".equals(queryquerySuperPaySql)){
			Session sessionH = super.getSessionFactory().getCurrentSession();
			Query queryT = sessionH.createSQLQuery(queryquerySuperPaySql);
			List<Object> prptmainList = queryT.list();
			for(int k=0;k<prptmainList.size();k++){
				Object obj = prptmainList.get(k);
				superPay=obj==null?"":obj+"";//superPay的值
			}
		}
		return superPay;
		
	}
}


