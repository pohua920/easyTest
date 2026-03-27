package com.sinosoft.undwrt.undwrtInterface.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.axis.client.ServiceFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpPhead;
//mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266 保發輔助平台,擴增欄位--核心變更需求
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpQinsured;
import com.sinosoft.common.schema.model.PrpQitemKind;
import com.sinosoft.common.schema.model.PrpQmain;
import com.sinosoft.common.schema.model.PrpQmainSub;
import com.sinosoft.common.schema.model.PrpTitemKind;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.common.schema.model.PrpTmainSub;
import com.sinosoft.common.util.DateUtil;
import com.sinosoft.common.util.IConstants;
import com.sinosoft.one.rule.service.facade.DroolsRuleService;
import com.sinosoft.platform.dto.domain.PrpDuserCADto;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.prpall.blsvr.cb.BLPrpCmain;
import com.sinosoft.prpall.blsvr.pg.BLPrpPhead;
import com.sinosoft.prpall.blsvr.pg.BLPrpPheadCovernote;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmain;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmainSub;
import com.sinosoft.prpall.schema.PrpPheadCovernoteSchema;
import com.sinosoft.prpall.schema.PrpPheadSchema;
import com.sinosoft.prpall.schema.PrpTmainSchema;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.prpins.policy.web.EndorseAction;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.util.SubmitUndwrtThread;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.SwfPath;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDealSubmitService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpFeedBackService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.UndwrtService;
import com.sinosoft.undwrt.undwrtInterface.service.facade.TaskService;
import com.sinosoft.undwrt.undwrtRule.service.UndwrtRuleRiskKind;
import com.sinosoft.undwrt.undwrtRule.service.facade.GetBusinessDataService;
import com.sinosoft.undwrt.undwrtRule.vo.BusinessProposalData;
import com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService;

/**
 * 核保處理任務接口.
 */
public class TaskServiceSpringImpl extends GenericDaoHibernate implements TaskService {

	/** 屬性核保服務接口. */
	private UndwrtService undwrtService;
	private Logger loggerRenewal = Logger.getLogger(TaskServiceSpringImpl.class); 

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性工作流路徑定義接口. */
	private SwfPathService swfPathService;

	/** 屬性核保審核處理接口. */
	private CommonDealSubmitService commonDealSubmitService;

	/** 屬性要保書處理接口. */
	private PolicyService policyService;

	/** 屬性核保回寫數據服務接口. */
	private PrpFeedBackService prpFeedBackService;

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/** 屬性規則引擎接口. */
	private DroolsRuleService droolsRuleService;

	/** 屬性獲取業務數據接口. */
	private GetBusinessDataService getBusinessDataService;

	/** 屬性自動核保規則訊息. */
	private String droolsRulesMessage;
	/** 核保系統查詢接口 */
	private PrpallService prpallService;
	/**
	/**核保處理任務服務接口類.*/ 
	private TaskDealService taskDealService;
	/**
	 * 提交核保.
	 * 
	 * @param modelType
	 *            模板類型
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param classCode
	 *            險類代碼
	 * @param comCode
	 *            機構代碼
	 * @param makecom
	 *            出單機構
	 * @param userCode
	 *            用戶代碼
	 * @param handlerCode
	 *            經辦人代碼
	 * @param handler1Code
	 *            歸屬業務員代碼
	 * @param contractNo
	 *            合約號
	 * @param singleCode
	 *            出單員代碼
	 * @return 工作流號
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             SQL異常
	 * @throws Exception
	 *             異常
	 */
	public String start(DBManager dbManager,String modelType, String certiType, String businessNo, String riskCode, String classCode, String comCode, String makecom,
			String userCode, String handlerCode, String handler1Code, String contractNo, String singleCode) throws UserException, SQLException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		Collection<SwfPath> wfPathDtoList = new ArrayList<SwfPath>();
		SwfPath wfPathDto = new SwfPath();
		SimpleDateFormat logFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String flowID = "";
		String startType = "";
		int modelNo = 0;
		int intStartNodeNo = 1; // 起始节点默认为1
		int intEndNodeNo = 0;
		int j = 0;
		String defaultFlag = "1";
		String underwriteFlagMain = ""; // 核保状态
		String otherFlagMain = "";// 其它标志字段
		try {
//			dbManager.beginTransaction();

			BLPrpCmain blPrpCmain = new BLPrpCmain();
			BLPrpTmain blPrpTmain = new BLPrpTmain();
			PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
			BLPrpPhead blPrpPhead = new BLPrpPhead();
			PrpPheadSchema prpPheadSchema = new PrpPheadSchema();

			if (!("12").equals(modelType)) {// 自动核保不需做此控制
				if (certiType.equals("T")) {
					blPrpTmain.getData(businessNo);
					if (blPrpTmain.getSize() > 0) {
						prpTmainSchema = blPrpTmain.getArr(0);
						underwriteFlagMain = prpTmainSchema.getUnderWriteFlag();
					}
				} else if (certiType.equals("E")) {
					BLPrpPheadCovernote blPrpPheadCovernote = new BLPrpPheadCovernote();
					PrpPheadCovernoteSchema prpPheadCovernoteSchema = new PrpPheadCovernoteSchema();
					blPrpPheadCovernote.getData(businessNo);
					if (blPrpPheadCovernote.getSize() > 0) {
						prpPheadCovernoteSchema = blPrpPheadCovernote.getArr(0);
						underwriteFlagMain = prpPheadCovernoteSchema.getUnderWriteFlag();
					} else {
						blPrpPhead.getData(businessNo);
						if (blPrpPhead.getSize() > 0) {
							prpPheadSchema = blPrpPhead.getArr(0);
							underwriteFlagMain = prpPheadSchema.getUnderWriteFlag();
						}
					}
				} else if (certiType.equals("B")) {
					PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotaion");
					if (null != prpQmain) {
						underwriteFlagMain = prpQmain.getUnderWriteFlag();
					}
				}
				// underwriteFlag标志为7的是cargo系统提交核保未成功的特殊处理
				if (!(("").equals(underwriteFlagMain) || underwriteFlagMain == null || ("0").equals(underwriteFlagMain) || ("7").equals(underwriteFlagMain)
						|| ("2").equals(underwriteFlagMain) || ("4").equals(underwriteFlagMain))) {
					// 核心提交双核时，核心的状态必须是初始状态、主动回撤或者打回状态。
					throw new Exception(" ,"+internal.getText("undwrt.service.task.canotOperateContinue")+", ");
				}
			}

			// 核心提交时判断投保单是否为撤单状态。条件：prptmain表中otherflag字段第四位为2时为撤单。
			if (certiType.equals("T")) {
				blPrpTmain.getData(businessNo);
				if (blPrpTmain.getSize() > 0) {
					prpTmainSchema = blPrpTmain.getArr(0);
					otherFlagMain = prpTmainSchema.getOthFlag();
				}

				if (!(("").equals(otherFlagMain) || otherFlagMain == null) && ("2").equals(otherFlagMain.substring(3, 4))) {
					throw new Exception(internal.getText("undwrt.pages.undwrtDeal.insureBill") + businessNo
							+ internal.getText("undwrt.service.task.canotOperateContinue2"));
				}

				// 提交时判断该投保单是否已经审核通过。
				blPrpCmain.findByPropsoalNo(businessNo);
				if (blPrpCmain.getSize() > 0) {
					throw new Exception(internal.getText("undwrt.pages.undwrtDeal.insureBill") + businessNo
							+ internal.getText("undwrt.service.task.cannotOperateContinue3"));
				}
			}

			// 1-根据模版类型,险种,部门编码获得模版号
			modelNo = undwrtService.getModelNo(modelType,classCode,riskCode, comCode);
			System.out.println("======獲取模板類型=========="+modelNo+"=========================");
			if (modelNo == 0) {
				throw new Exception(internal.getText("undwrt.service.task.haveNoTheTemplate"));
			}

			// 2-判断工作流是启动还是修改
			startType = this.checkStartType(businessNo);
			System.out.println("======判斷工作流狀態========"+startType);
			boolean blAutoCheck = false;
			// 允许自动核保时，EndNodeNo取当前模板的审核通过节点，否则按原配置条件找EndNodeNo
			if (blAutoCheck) {
				SwfPath swfPathDto = new SwfPath();
				WfLog wfLogDto = new WfLog();
				wfLogDto.setModelNo(modelNo);
				wfLogDto.setNodeNo(1);
				swfPathDto = swfPathService.getPassPath(wfLogDto);
				if (swfPathDto != null) {
					intEndNodeNo = Integer.parseInt(String.valueOf(swfPathDto.getEndNodeNo()));
				}
			} else {
				wfPathDtoList = swfPathService.getPathes(modelNo, intStartNodeNo, certiType, businessNo, defaultFlag, comCode);
				Iterator<SwfPath> itwfpath = wfPathDtoList.iterator();
				while (itwfpath.hasNext()) {
					j++;
					wfPathDto = itwfpath.next();
					if (j == 1) {
						intEndNodeNo = Integer.parseInt(String.valueOf(wfPathDto.getEndNodeNo()));
						break;
					}
				}
				if (j == 0) {
					throw new UserException(-98, -1004, this.getClass().getName());
				}
			}
			// 允许自动核保时，EndNodeNo取当前模板的终止节点，否则按原配置条件找EndNodeNo
			loggerRenewal.error("開始時間："+logFormat.format(new Date())+"對複核後的任務進行處理"+businessNo);
			long begin3= System.currentTimeMillis();
			flowID = commonDealSubmitService.dealFirstTrans(modelNo, certiType, businessNo, startType, intEndNodeNo, "0", riskCode, classCode, comCode,
					makecom, handlerCode, handler1Code, userCode, contractNo, singleCode, dbManager);//關聯單報價單核保問題
			long end3 = System.currentTimeMillis();
			loggerRenewal.error("結束時間："+logFormat.format(new Date())+"對複核後的任務進行處理"+businessNo);
			loggerRenewal.error("對複核後的任務進行處理所用時間差:----------"+(begin3-end3));
			//			dbManager.commitTransaction();
		} catch (UserException ue) {
//			dbManager.rollbackTransaction();
			ue.printStackTrace();
			logger.error(getTrace(ue));
			throw ue;
		} catch (SQLException e) {
//			dbManager.rollbackTransaction();
			logger.error(getTrace(e));
			throw e;
		} catch (Exception e) {
//			dbManager.rollbackTransaction();
			logger.error(getTrace(e));
			throw e;
		} finally {
		}
		return flowID;
	}

	/**
	 * 报价单提交核保(暫時只提供批量续保的报价单調用)
	 * 
	 * @param businessNo
	 *            业务号
	 * @return
	 * 
	 * @see com.sinosoft.undwrt.undwrtInterface.service.facade.TaskService#startQta(java.lang.String)
	 */
	@Override
	public void startQta(String businessNo, String businessType, String strModelType) throws Exception {
		DBManager dbManager = new DBManager();
		DateTime underWriteDate = new DateTime(new DateTime().current().toString().substring(0, 10));
		String taskCode = "";
		String userCode = "";

		if ("12".equals(strModelType)) {
			taskCode = "3";// 报价单自动审核通过任务代码为3
		} else if ("11".equals(strModelType)) {
			taskCode = "9";// 报价单待审核任务代码为9
		}
		try {
			dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
			dbManager.beginTransaction();
			prpFeedBackService.echo(dbManager, 'B', businessNo, taskCode, userCode, underWriteDate, "0", "prp");
			/*
			mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
			新AML
			*/
			PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
			if(prpQmain != null){
				PrpQmain prpQmainCI = new PrpQmain();
				if("A01".equals(prpQmain.getRiskCode())){
					PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
					if(sub != null){
						prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
						prpFeedBackService.echo(dbManager, 'B', prpQmainCI.getProposalNo(), taskCode, userCode, underWriteDate, "0", "prp");
					}
				}
			}
			/* mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 --- end */
			dbManager.commitTransaction();
		} catch (UserException e) {
			dbManager.rollbackTransaction();
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			dbManager.rollbackTransaction();
			e.printStackTrace();
			throw e;
		} finally {
			dbManager.close();
		}
	}
	
	public String checkData(String businessNo, String businessType) throws Exception {
		DBManager dbManager = new DBManager();
		try{
			dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
			dbManager.beginTransaction();//將開啓事務與提交事務放在外層   關聯單報價單核保問題
			if("BA".equals(businessType)){
				//mantis： CAR0210，處理人員：Sam，需求單編號：CAR0210，續保關聯單任意險待核保 強制險也需待核保
				List list = super.findByHql("from PrpQmain where batchNO=? and underWriteFlag in ('0','2','4') order by riskcode",businessNo);
				System.out.println("Undwrt:==batchSubmit==============================" + businessType + "-" + businessNo);
				for(int i=0;i<list.size();i++){
					try{
						if("0".equals(((PrpQmain)list.get(i)).getUnderWriteFlag()) || "2".equals(((PrpQmain)list.get(i)).getUnderWriteFlag())
								||"4".equals(((PrpQmain)list.get(i)).getUnderWriteFlag())) {
							//add by gaojunfeng 需求150 車險續保處理    20160901 start
							PrpQmain prpQmain=((PrpQmain)list.get(i));
							String strUserCode=prpQmain.getOperatorCode();
							String editFlag=prpQmain.getEditFlag();
							String valueType = taskDealService.getRenGongKaiGuanStatu();//獲取開關狀態
							String updateWorkStatusSQL="";
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							List renewallist=policyService.getRenewalInfoByQuoteno(((PrpQmain)list.get(i)).getProposalNo());
							if(IConstants.PRPINS_RISK_CAR.equals(prpQmain.getRiskCode())||IConstants.PRPINS_RISK_CARCI.equals(prpQmain.getRiskCode())){
								if("0".equals(valueType)){//人工維護開關關閉
									if("1".equals(editFlag)||"2".equals(editFlag)||renewallist.size()>0){//續保的單子設置作業狀態不執行
										updateWorkStatusSQL = "update PrpQmain set workstatus='00' where proposalno='"+((PrpQmain)list.get(i)).getProposalNo()+"'";//更新作業狀態為不執行
									    wfLogService.updateMainStatus(updateWorkStatusSQL);
									}else{//批次報價前台導入的單子設置作業狀態查詢中，記錄時間，調用AML系統
										String callAmlDate =df.format(new Date());					
										updateWorkStatusSQL = "update PrpQmain set workstatus='02',callamldate=to_date('"+callAmlDate+"','YYYY-MM-DD HH24:MI:SS') where proposalno='"+((PrpQmain)list.get(i)).getProposalNo()+"'";
										wfLogService.updateMainStatus(updateWorkStatusSQL);
										businessType="B";
										prpFeedBackService.callPrpinsAml(businessType,((PrpQmain)list.get(i)).getProposalNo(), strUserCode);//調用AML系統				
									}
								}else{//開關開啟
									if((IConstants.PRPINS_RISK_CAR.equals(prpQmain.getRiskCode())||IConstants.PRPINS_RISK_CARCI.equals(prpQmain.getRiskCode()))&&
									  		  ("1".equals(editFlag)||"2".equals(editFlag)||list.size()>0)){//續保的單子設置作業狀態為不執行
										updateWorkStatusSQL = "update PrpQmain set workstatus='00' where proposalno='"+((PrpQmain)list.get(i)).getProposalNo()+"'";//更新作業狀態為不執行
									    wfLogService.updateMainStatus(updateWorkStatusSQL);
									}else{//批次報價前台導入生成的報價單設置作業狀態為待再查詢并記錄時間
										String callAmlDate = df.format(new Date());
										updateWorkStatusSQL = "update PrpQmain set workstatus='01',callamldate=to_date('"+callAmlDate+"','YYYY-MM-DD HH24:MI:SS') where proposalno='"+((PrpQmain)list.get(i)).getProposalNo()+"'";//更新作業狀態為待再查詢
										wfLogService.updateMainStatus(updateWorkStatusSQL);
									}
								}
							}
							//add by gaojunfeng 需求150 車險續保處理   20160901 end 
							this.startPrepare(dbManager,((PrpQmain)list.get(i)).getProposalNo(), "B");
						}
					}catch(Exception e){
						e.printStackTrace();
						//add by xuhuilling mantis 4906 重覆點擊提交核保begin
						if(e.getMessage()!=null){
							throw e;
						}
						//add by xuhuilling mantis 4906 重覆點擊提交核保begin
					}
				}
				return "";
			}else{
				System.out.println("============開始進入startPrepare方法==============");
				String returnFlowId = this.startPrepare(dbManager,businessNo, businessType);
				//modefied by zhangruofei 20150211 自動核保通過的報價單需要生成虛擬編碼
				if("B".equals(businessType)) {
					if(null!=returnFlowId && !"".equals(returnFlowId)) {
						if(returnFlowId.contains(",2,")) {							
							commonDealSubmitService.genDummyCode(businessNo, businessType);
						}
					}				
				}
				//modefied by zhangruofei 20150424 自動核保通過的要保書需要生成虛擬編碼
				if("T".equals(businessType)) {
					if(null!=returnFlowId && !"".equals(returnFlowId)) {
						if(returnFlowId.contains(",2,")) {							
							commonDealSubmitService.genDummyCode(businessNo, businessType);
						}
					}				
				}
				//modefied by zhangruofei 20150910 begin 為避免關聯單的強制險沒有提交核保問題出現，要保書關聯單提交核保時只提交任意險，在核保系統處理關聯單
				if("T".equals(businessType)) {
					if(null!=returnFlowId && !"".equals(returnFlowId)) {
						PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
						if(null!=prpTmain && null!=prpTmain.getRiskCode()) {
							if("A01".equals(prpTmain.getRiskCode())) {
								if(null!=prpTmain.getPrpTmainSubs() && prpTmain.getPrpTmainSubs().size()>0) {
									PrpTmainSub prpTmainSub = prpTmain.getPrpTmainSubs().get(0);
									if(null!=prpTmainSub.getFlag() && "111".equals(prpTmainSub.getFlag())) {
										logger.info("關聯強制險要保書開始進入核保,要保書號是:"+prpTmainSub.getId().getMainPolicyNo());
										String returnFlowIdCI = this.startPrepare(dbManager,prpTmainSub.getId().getMainPolicyNo(), businessType);
										if(null!=returnFlowIdCI && !"".equals(returnFlowIdCI)) {
											if(returnFlowIdCI.contains(",2,")) {							
												commonDealSubmitService.genDummyCode(prpTmainSub.getId().getMainPolicyNo(), businessType);
											}
										}
										returnFlowId = returnFlowId+",CI,"+returnFlowIdCI;
									}
								}
							}
						}
					}
				}
				if("B".equals(businessType)) {
					if(null!=returnFlowId && !"".equals(returnFlowId)) {
						PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
						if(null!=prpQmain && null!=prpQmain.getRiskCode()) {
							if("A01".equals(prpQmain.getRiskCode())) {
								if(null!=prpQmain.getPrpQmainSubs() && prpQmain.getPrpQmainSubs().size()>0) {
									PrpQmainSub prpQmainSub = prpQmain.getPrpQmainSubs().get(0);
									if(null!=prpQmainSub.getFlag() && "111".equals(prpQmainSub.getFlag())) {
										/*
										mantis： CAR0175，處理人員：Sam，需求單編號：CAR0175--- start
										車險報價單核保時，若任意險自動核保失敗，強制險也跟著狀態走
										*/
										String returnFlowIdCI = "";
										if(returnFlowId.contains(",1,")) {
											this.startPrepareByA01Error(dbManager,prpQmainSub.getId().getMainPolicyNo(), businessType);
											returnFlowIdCI = returnFlowId;
										}else{
											returnFlowIdCI = this.startPrepare(dbManager,prpQmainSub.getId().getMainPolicyNo(), businessType);
											if(null!=returnFlowIdCI && !"".equals(returnFlowIdCI)) {
												if(returnFlowIdCI.contains(",2,")) {							
													commonDealSubmitService.genDummyCode(prpQmainSub.getId().getMainPolicyNo(), businessType);
												}
											}
										}
										returnFlowId = returnFlowId+",CI,"+returnFlowIdCI;
										/* mantis： CAR0175，處理人員：Sam，需求單編號：CAR0175 --- end */
									}
								}
							}
						}
					}
				}
				//mantis： HAS0226，處理人員：Sam，需求單編號：HAS0226  外部虛擬編號問題處理 Start
				//WS走核保沒走虛擬碼 所以複製過來貼在這
				if("E".equals(businessType)) {
					QueryRule queryRule = QueryRule.getInstance();
					queryRule.addEqual("businessNo", businessNo);
					WfLog wflog = wfLogService.findByQueryRule(queryRule).get(0);
					String nodeStatus = wflog.getNodeStatus();
					if ("0".equals(nodeStatus)) {
						// 生成虚拟编号20131326 by wangJun
						PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
						PrpPmain prpPmain = prpPhead.getPrpPmains().get(0);
						//PrpPmain prpPmain = endorseService.getPrpPheadByEndorseNo(BusinessNo).getPrpPmains().get(0);
						if (null != prpPmain.getChgPremium() && prpPmain.getChgPremium().doubleValue() > 0) {
							commonDealSubmitService.genDummyCode(businessNo, businessType);
						}
						//收費出單的批單都要生成虛擬編號 modefied by zhangruofei 20150126
						if (null != prpPmain.getChgPremium() && (prpPmain.getChgPremium().compareTo(BigDecimal.ZERO)<1)) {
							if(commonDealSubmitService.checkIsNeadPaid(prpPhead)) {
								commonDealSubmitService.genDummyCode(businessNo, businessType);
							}						
						}
					}
				}
				//mantis： HAS0226，處理人員：Sam，需求單編號：HAS0226  外部虛擬編號問題處理 End
				//modefied by zhangruofei 20150910 begin 為避免關聯單的強制險沒有提交核保問題出現，要保書關聯單提交核保時只提交任意險，在核保系統處理關聯單
				return returnFlowId;
			}
		} catch (UserException ue) {
			dbManager.rollbackTransaction();
			ue.printStackTrace();
			throw ue;
        } catch (SQLException e) {
			dbManager.rollbackTransaction();
			throw e; 
		} catch (Exception e) {
			dbManager.rollbackTransaction();
			System.out.println("Undwrt:==batchSubmitException==============================" + businessType + "-" + businessNo);
			throw e;
		} finally {
			dbManager.close();
		}
	}

	/**
	 * 提交核保处理方法（提交核保准备数据）
	 * 
	 * @param businessNo
	 *            业务号
	 * @param businessType
	 *            业务类型
	 * @throws Exception
	 *             异常
	 * 
	 * @see com.sinosoft.undwrt.undwrtInterface.service.facade.TaskService#startPrepare(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String startPrepare(DBManager dbManager,String businessNo, String businessType) throws Exception {
		System.out.println("Undwrt:==startPrepare==============================" + businessType + "-" + businessNo + "进入提交核保流程.");
		logger.info("Undwrt:==startPrepare==============================" + businessType + "-" + businessNo + "进入提交核保流程.");
		boolean autoUndwrtFlag = false;
		String strFlowId = "";
		SimpleDateFormat logFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strModelType = "", // 模版类型
		strRiskCode = "", // 险种代码
		strClassCode = "", // 险类代码
		strComCode = "", // 归属机构
		strMakeCom = "", // 出单机构
		strUserCode = "", // 用户代码-核保处理人员
		strHandlerCode = "", // 经办人代码
		strHandler1Code = "", // 归属业务员代码
		contractNo = "", // 合约号/合同号
		strOperatorCode = ""; // 出单员代码
        //add by yjm 20151124 旅平險B2B的数据自动核保通过  start
		String operatorSite = "";
		//add by yjm 20151124 旅平險B2B的数据自动核保通过  end
		//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過  start
		String policyType = "";//大小保單類型
		//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過 end
		// add by CSY 20160418 伤害险续保 start 
		String editflag = "" ;//伤害险 续保
		// add by CSY 20160418 end  
		//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
		boolean addressVerify = false;
		//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END
		PrpTmain prpTmain = null;
		PrpPhead prpPhead = null;
		PrpQmain prpQmian = null;
		//mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245 強任費率不一致的的需求
		String undwrtmark = null;
		if ("T".equals(businessType)) {
			prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			if (prpTmain != null) {
				strRiskCode = prpTmain.getRiskCode();
				strComCode = prpTmain.getComCode();
				strMakeCom = prpTmain.getMakeCom();
				strClassCode = prpTmain.getClassCode();
				strHandlerCode = prpTmain.getHandlerCode();
				strHandler1Code = prpTmain.getHandler1Code();
				contractNo = prpTmain.getContractNo();
				strOperatorCode = prpTmain.getOperatorCode();
				strUserCode = prpTmain.getOperatorCode();
				//add by yjm 20151124 旅平險B2B的数据自动核保通过  start
				operatorSite = prpTmain.getOperateSite();
				//add by yjm 20151124 旅平險B2B的数据自动核保通过  end
				//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過  start
				policyType = prpTmain.getBiznosysflag();//大小保單類型 1是主保单，2是小保单
				//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過 end
				editflag = prpTmain.getEditFlag() ;
				
				//add by gaojunfeng 需求150 車險B2B的處理  20160830 start 
				String workStatus="";
				if((IConstants.PRPINS_RISK_CAR.equals(prpTmain.getRiskCode())||IConstants.PRPINS_RISK_CARCI.equals(prpTmain.getRiskCode()))
						&&"B2B&B2C".equals(operatorSite)){
					workStatus = "update prptmain set workstatus='00'　where proposalno='"+businessNo+"'";//更新作業狀態為不執行
				    wfLogService.updateMainStatus(workStatus);
				}
				//add by gaojunfeng 需求150 車險B2B的處理   20160830 end
				//mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245 強任費率不一致的的需求
				undwrtmark = prpTmain.getUndwrtmark();
			}
		}
		else if ("E".equals(businessType)) {
			prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
			if (prpPhead != null) {
				strRiskCode = prpPhead.getRiskCode();
				strComCode = prpPhead.getComCode();
				strClassCode = prpPhead.getClassCode();
				strMakeCom = prpPhead.getMakeCom();
				strHandlerCode = prpPhead.getHandlerCode();
				strHandler1Code = prpPhead.getHandler1Code();
				strOperatorCode = prpPhead.getOperatorCode();
				strUserCode = prpPhead.getOperatorCode();
				//add by yjm 20151222 旅平險B2B的数据自动核保通过  start
				if(prpPhead.getPrpPmains()!=null && !prpPhead.getPrpPmains().isEmpty()){
				    operatorSite = prpPhead.getPrpPmains().get(0).getOperateSite();
				}
				//add by yjm 20151222 旅平險B2B的数据自动核保通过  end
				//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過  start
				if(prpPhead.getPrpPmains()!=null && !prpPhead.getPrpPmains().isEmpty()){
					policyType = prpPhead.getPrpPmains().get(0).getBiznosysflag();//大小保單類型 1是主保单，2是小保单
				}
				//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過 end
			}
		}
		else if ("B".equals(businessType)) {
			System.out.println("==========獲取prpqmain表的信息=========="+businessNo+"===================");
			prpQmian = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
			if (prpQmian != null) {
				strRiskCode = prpQmian.getRiskCode();
				strComCode = prpQmian.getComCode();
				strClassCode = prpQmian.getClassCode();
				strMakeCom = prpQmian.getMakeCom();
				strHandlerCode = prpQmian.getHandlerCode();
				strHandler1Code = prpQmian.getHandler1Code();
				strOperatorCode = prpQmian.getOperatorCode();
				strUserCode = prpQmian.getOperatorCode();
				System.out.println("=========獲取報價單信息====");
				System.out.println("===strRiskCode===="+strRiskCode);
				System.out.println("===strComCode===="+strComCode);
				System.out.println("===strClassCode===="+strClassCode);
				System.out.println("===strMakeCom===="+strMakeCom);
				System.out.println("===strHandlerCode===="+strHandlerCode);
				System.out.println("===strHandler1Code===="+strHandler1Code);
				System.out.println("===strOperatorCode===="+strOperatorCode);
				System.out.println("===strUserCode===="+strUserCode);
				//mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245 強任費率不一致的的需求
				undwrtmark = prpQmian.getUndwrtmark();
			}
		}
		//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
		//B2C、B2B2C、新企業平台不進去
		if("".equals(operatorSite) || operatorSite == null){
			if ("T".equals(businessType) || "B".equals(businessType)) {
				//若非正規化成功且判定通過
				/**
				 * 以下兩種狀態不會卡
				 * 
				 * 3 - 正規化成功且判定通過
				 * 7 - 人工判定完成
				 */
				String normastatus = "";
				if("T".equals(businessType)){
					normastatus = prpTmain.getNormastatus();
				}
				if("B".equals(businessType)){
					normastatus = prpQmian.getNormastatus();
				}
				if("3".equals(normastatus) || "7".equals(normastatus)){
					addressVerify = true;
				}
			}else{
				addressVerify = true;
			}
		}else{
			///B2C、B2B2C、新企業平台不去判斷地址正規化....
			addressVerify = true;
		}
		//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END

		strModelType = "11";// 人工核保
		//add by yjm 20151009 自動核保通過檢驗message start
		String[] autoMessage = new String[4];//[0]代表放回訊息 [1]代表數據操作來源，現只有TA的B2B用 [3]代表小保單現只有TE飛安險用
		//mantis： HAS0254，處理人員：Sam，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳+WS調整 Start
		//mantis： HAS0264，處理人員：Sam，需求單編號：HAS0264_傷害險中信銀行投優化取消新增I99058改為舊有I99050
		if ("T".equals(businessType) && "I99050".equals(prpTmain.getBusinessNature()) && "mob".equals(prpTmain.getProjectCode())) {
			autoMessage[0] = prpTmain.getBusinessNature();
		}
		//mantis： HAS0254，處理人員：Sam，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳+WS調整 End
		//add by yjm 20151009 自動核保通過檢驗message end
		//add by yjm 20151124 旅平險B2B的数据自动核保通过  start
		autoMessage[1] = operatorSite;
		//add by yjm 20151124 旅平險B2B的数据自动核保通过  end
		//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過  start
		autoMessage[2] = policyType;
		//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過 end
		autoMessage[3] = editflag ;
		/*
		mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266--- start
		保發輔助平台,擴增欄位--核心變更需求
		*/
		String notifyOrNot = null;
		//mantis： CAR0394，處理人員：DP0717，需求單編號：CAR0394.新核心增加要被保險人關係欄位相關檢核
		boolean relationIsOther = false;
		if("A01".equals(strRiskCode) || "B01".equals(strRiskCode) )
		{
			if ("B".equals(businessType)) {
				//先檢查
				if("A01".equals(strRiskCode)){//因要保人為必填欄位 故不防null
					//單任不卡控 關聯單時需檢查強制險是否有填寫
					if(prpQmian.getPrpQmainSubs() !=  null && prpQmian.getPrpQmainSubs().size() > 0 ){
						PrpQmain prpQmianCI = policyService.getPrpQmainByProposalNo(prpQmian.getPrpQmainSubs().get(0).getId().getMainPolicyNo(), "quotation");
						if(prpQmianCI != null){//有關聯單 強制險
							PrpQinsured insured = null;
							for(PrpQinsured q:prpQmianCI.getPrpQinsureds()){
								if("2".equals(q.getInsuredFlag())){//要保人
									insured = q;
									break;
								}
							}
							if(StringUtils.isBlank(insured.getMobile()) && StringUtils.isBlank(insured.getEmail())){
								notifyOrNot = "Y";//當報價單時 電子信箱與手機未輸入 即改人工核保
							}
						}
					}
				}else if("B01".equals(strRiskCode)){//任意險 因要保人為必填欄位 故不防null
					PrpQinsured insured = null;
					for(PrpQinsured q:prpQmian.getPrpQinsureds()){
						if("2".equals(q.getInsuredFlag())){//要保人
							insured = q;
							break;
						}
					}
					if(StringUtils.isBlank(insured.getMobile()) && StringUtils.isBlank(insured.getEmail())){
						notifyOrNot = "Y";//當報價單時 電子信箱與手機未輸入 即改人工核保
					}
				}
				
				//mantis： CAR0394，處理人員：DP0717，需求單編號：CAR0394.新核心增加要被保險人關係欄位相關檢核 start
				PrpQinsured insured = null;
				for(PrpQinsured q:prpQmian.getPrpQinsureds()){
					if("1".equals(q.getInsuredFlag())){//被保人
						insured = q;
						break;
					}
				}
				if("05".equals(insured.getInsuredIdentity())){
					relationIsOther = true;
				}
				//mantis： CAR0394，處理人員：DP0717，需求單編號：CAR0394.新核心增加要被保險人關係欄位相關檢核 end
			}
			
			//mantis： CAR0394，處理人員：DP0717，需求單編號：CAR0394.新核心增加要被保險人關係欄位相關檢核 start
			if(relationIsOther){
				autoUndwrtFlag = false;
				droolsRulesMessage = "undwrt.rule.relationIsOther";
			}else if("Y".equals(notifyOrNot)){
			//mantis： CAR0394，處理人員：DP0717，需求單編號：CAR0394.新核心增加要被保險人關係欄位相關檢核 end
				/*
				mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245--- start
				強任費率不一致的的需求
				 */
				//代表需人工審核 CAR0245 自動核保false
				autoUndwrtFlag = false;
				droolsRulesMessage = "undwrt.rule.NotifyOrNot";
			}else if("Y".equals(undwrtmark)){
				/* mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266 --- end */
				//代表需人工審核 CAR0245 自動核保false
				autoUndwrtFlag = false;
				droolsRulesMessage = "undwrt.rule.RateNotConsistent";
			}else{
				loggerRenewal.error("開始時間："+logFormat.format(new Date())+"判斷是否自動核保"+businessNo);
				long begin1= System.currentTimeMillis();
				if("T".equals(businessType) || "B".equals(businessType))
				{
					loggerRenewal.error("開始時間："+logFormat.format(new Date())+"拒保業務獲取報價單和要保書業務數據."+businessNo);
				    long begin2= System.currentTimeMillis();
					BusinessProposalData condition = getBusinessDataService.getBusinessData(businessNo, businessType);
					long end2 = System.currentTimeMillis();
					loggerRenewal.error("結束時間："+logFormat.format(new Date())+"拒保業務獲取報價單和要保書業務數據."+businessNo);
					loggerRenewal.error("拒保業務獲取報價單和要保書業務數據所用時間差:----------"+(begin2-end2));
					droolsRuleService.executeRules("undwrtReject.xml", condition);
					loggerRenewal.error("開始時間：~~~~droolsRuleService.executeRules(undwrtReject.xml, condition)~~~~"+businessNo);
					if(!condition.getResult())
					{
						return strFlowId+",3,undwrt.rule.serviceRejected";
					}
				}
				autoUndwrtFlag = isAutoUndwrt(businessNo, businessType);
				long end1 = System.currentTimeMillis();
				loggerRenewal.error("結束時間："+logFormat.format(new Date())+"判斷是否自動核保"+businessNo);
				loggerRenewal.error("判斷是否自動核保所用時間差:----------"+(begin1-end1));
			}
			/* mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245 --- end */
		}
		else
		{
			/*
			mantis： LIA0092，處理人員：Sam，需求單編號：LIA0092--- start
			普通批改-核保&保費試算
			*/
			if("PE".equals(strRiskCode)){
				boolean isDoubleInsurance = checkDoubleInsuranceByPE(businessNo);
				if(isDoubleInsurance){
					System.out.println("被保險寵物於保期內重複投保寵物險");
					return strFlowId+",1,undwrt.action.commonDealSubmit.doubleInsurance";
				}
			}
			/* mantis： LIA0092，處理人員：Sam，需求單編號：LIA0092 --- end */
			autoUndwrtFlag = isNoCarAutoUndwrt(businessNo,businessType,strRiskCode,autoMessage);
		}
		
		//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
		if(!addressVerify){
			//若未經過判定，全都轉人工核保
			autoUndwrtFlag = false;
		}
		//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END
		
		//mantis： HAS0130，處理人員：DP0706，需求單編號：HAS0130_傷害系統 內之 防疫險保單 自動核保規則修改 START
		if(businessNo.indexOf("HP") > 0 && "T".equals(businessType)){
			autoUndwrtFlag = true; // 防疫險
			System.out.println("=====autoUndwrtFlag，proposalno:"+businessNo);
		}
		//mantis： HAS0130，處理人員：DP0706，需求單編號：HAS0130_傷害系統 內之 防疫險保單 自動核保規則修改 END
		
		System.out.println("=====是否自動核保========"+autoUndwrtFlag);
		if (autoUndwrtFlag) {
			strModelType = "12";// 自动核保
			/*
			mantis： CAR0210，處理人員：Sam，需求單編號：CAR0210--- start
			續保關聯單任意險待核保 強制險也需待核保
			*/
			if("B01".equals(strRiskCode)){//如果是續保的強制險 要先判斷 任意險是否自動核保通過
				List<PrpQmainSub> listA01 = policyService.getPrpQmainSubByMainPolicyNo(businessNo);
				if(listA01 != null && listA01.size() > 0 ){
					String underWriteFlagByA01 = getUnderWriteFlag(listA01.get(0).getId().getProposalNo());
					if("9".equals(underWriteFlagByA01)){
						strModelType = "11";
						System.out.println("=====強制險單號========"+businessNo+"========為關聯單，因任意險自動核保不通過，此單號也被改為待核保。");
					}
				}
			}
			/* mantis： CAR0210，處理人員：Sam，需求單編號：CAR0210 --- end */
		}
		System.out.println("=========是否自動核保通過標誌======="+strModelType);
		if ("T".equals(businessType) || "E".equals(businessType) || "B".equals(businessType)) {
			loggerRenewal.error("開始時間："+logFormat.format(new Date())+"提交核保"+businessNo);
			long begin2= System.currentTimeMillis();
			strFlowId = this.start(dbManager,strModelType, businessType, businessNo, strRiskCode, strClassCode, strComCode, strMakeCom, strUserCode, strHandlerCode,
					strHandler1Code, contractNo, strOperatorCode);
			long end2 = System.currentTimeMillis();
			loggerRenewal.error("結束時間："+logFormat.format(new Date())+"提交核保"+businessNo);
			loggerRenewal.error("提交核保所用時間差:----------"+(begin2-end2));
		}
		if ("B".equals(businessType)) {
			if ("11".equals(strModelType)) {
				System.out.println("Undwrt:==startPrepare==============================" + businessType + "-" + businessNo + "提交核保成功.");
				
				//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
				if(!addressVerify){
					return strFlowId + ",4,undwrt.address.format.invalid";						
				}
				//if("A01".equals(strRiskCode) || "B01".equals(strRiskCode)){
					//若為車險報價時，且為人工核保時，且地址未正規化
					//if(!addressVerify){
						//return strFlowId + ",4,undwrt.address.format.invalid";						
					//}
				//}else 
				//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END			
				//update by yjm 20151009 住火返回核批失败原因 start
				if("F02".equals(strRiskCode)){
					return strFlowId + ",1," + autoMessage[0];
				}else{
				    return strFlowId + ",1," + droolsRulesMessage;
				}
				//update by yjm 20151009 住火返回核批失败原因 end
			}
		}
		System.out.println("Undwrt:==startPrepare==============================" + businessType + "-" + businessNo + "提交核保成功.");
		logger.info("Undwrt:==startPrepare==============================" + businessType + "-" + businessNo + "提交核保成功.");
		if ("T".equals(businessType) || "E".equals(businessType)) {
			if ("11".equals(strModelType)) {
				//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
				if(!addressVerify){
						return strFlowId + ",1,undwrt.address.format.invalid";						
				}
				//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END	
				//update by yjm 20151009 住火返回核批失败原因 start
				if("T".equals(businessType) && "F02".equals(strRiskCode)){
				    return strFlowId + ",1,"+autoMessage[0];
				}else{
					return strFlowId + ",1,undwrt.submit.Success";
				}
			}
		}
		return strFlowId+",2,undwrt.autoUndwrt.Success";
	}

	/*
	mantis： CAR0175，處理人員：Sam，需求單編號：CAR0175--- start
	車險報價單核保時，若任意險自動核保失敗，強制險也跟著狀態走
	*/	
	/**
	 * 車險報價單任意險自動核保失敗時，強制險跟著失敗的method
	 */
	@Override
	public String startPrepareByA01Error(DBManager dbManager,String businessNo, String businessType) throws Exception {
		System.out.println("Undwrt:==startPrepare==============================" + businessType + "-" + businessNo + "进入提交核保流程.");
		logger.info("Undwrt:==startPrepare==============================" + businessType + "-" + businessNo + "进入提交核保流程.");
		boolean autoUndwrtFlag = false;
		String strFlowId = "";
		SimpleDateFormat logFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strModelType = "", // 模版类型
		strRiskCode = "", // 险种代码
		strClassCode = "", // 险类代码
		strComCode = "", // 归属机构
		strMakeCom = "", // 出单机构
		strUserCode = "", // 用户代码-核保处理人员
		strHandlerCode = "", // 经办人代码
		strHandler1Code = "", // 归属业务员代码
		contractNo = "", // 合约号/合同号
		strOperatorCode = ""; // 出单员代码
        //add by yjm 20151124 旅平險B2B的数据自动核保通过  start
		String operatorSite = "";
		//add by yjm 20151124 旅平險B2B的数据自动核保通过  end
		//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過  start
		String policyType = "";//大小保單類型
		//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過 end
		// add by CSY 20160418 伤害险续保 start 
		String editflag = "" ;//伤害险 续保
		// add by CSY 20160418 end  
		
		PrpQmain prpQmian = null;
		System.out.println("==========獲取prpqmain表的信息=========="+businessNo+"===================");
		prpQmian = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
		if (prpQmian != null) {
			strRiskCode = prpQmian.getRiskCode();
			strComCode = prpQmian.getComCode();
			strClassCode = prpQmian.getClassCode();
			strMakeCom = prpQmian.getMakeCom();
			strHandlerCode = prpQmian.getHandlerCode();
			strHandler1Code = prpQmian.getHandler1Code();
			strOperatorCode = prpQmian.getOperatorCode();
			strUserCode = prpQmian.getOperatorCode();
			System.out.println("=========獲取報價單信息====");
			System.out.println("===strRiskCode===="+strRiskCode);
			System.out.println("===strComCode===="+strComCode);
			System.out.println("===strClassCode===="+strClassCode);
			System.out.println("===strMakeCom===="+strMakeCom);
			System.out.println("===strHandlerCode===="+strHandlerCode);
			System.out.println("===strHandler1Code===="+strHandler1Code);
			System.out.println("===strOperatorCode===="+strOperatorCode);
			System.out.println("===strUserCode===="+strUserCode);
			
		}
	

		strModelType = "11";// 人工核保
		//add by yjm 20151009 自動核保通過檢驗message start
		String[] autoMessage = new String[4];//[0]代表放回訊息 [1]代表數據操作來源，現只有TA的B2B用 [3]代表小保單現只有TE飛安險用
		//add by yjm 20151009 自動核保通過檢驗message end
		//add by yjm 20151124 旅平險B2B的数据自动核保通过  start
		autoMessage[1] = operatorSite;
		//add by yjm 20151124 旅平險B2B的数据自动核保通过  end
		//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過  start
		autoMessage[2] = policyType;
		//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過 end
		autoMessage[3] = editflag ;
		System.out.println("=========是否自動核保通過標誌======="+strModelType);
		if ("T".equals(businessType) || "E".equals(businessType) || "B".equals(businessType)) {
			loggerRenewal.error("開始時間："+logFormat.format(new Date())+"提交核保"+businessNo);
			long begin2= System.currentTimeMillis();
			strFlowId = this.start(dbManager,strModelType, businessType, businessNo, strRiskCode, strClassCode, strComCode, strMakeCom, strUserCode, strHandlerCode,
					strHandler1Code, contractNo, strOperatorCode);
			long end2 = System.currentTimeMillis();
			loggerRenewal.error("結束時間："+logFormat.format(new Date())+"提交核保"+businessNo);
			loggerRenewal.error("提交核保所用時間差:----------"+(begin2-end2));
		}
		if ("B".equals(businessType)) {
			if ("11".equals(strModelType)) {
				System.out.println("Undwrt:==startPrepare==============================" + businessType + "-" + businessNo + "提交核保成功.");
				//update by yjm 20151009 住火返回核批失败原因 start
				if("F02".equals(strRiskCode)){
					return strFlowId + ",1," + autoMessage[0];
				}else{
				    return strFlowId + ",1," + droolsRulesMessage;
				}
				//update by yjm 20151009 住火返回核批失败原因 end
			}
		}
		System.out.println("Undwrt:==startPrepare==============================" + businessType + "-" + businessNo + "提交核保成功.");
		logger.info("Undwrt:==startPrepare==============================" + businessType + "-" + businessNo + "提交核保成功.");
		if ("T".equals(businessType) || "E".equals(businessType)) {
			if ("11".equals(strModelType)) {
				//update by yjm 20151009 住火返回核批失败原因 start
				if("T".equals(businessType) && "F02".equals(strRiskCode)){
				    return strFlowId + ",1,"+autoMessage[0];
				}else{
					return strFlowId + ",1,undwrt.submit.Success";
				}
			}
		}
		return strFlowId+",2,undwrt.autoUndwrt.Success";
	}
	/* mantis： CAR0175，處理人員：Sam，需求單編號：CAR0175 --- end */
	
	/**
	 * 双核接口方法.
	 * 
	 * @param lFlowID
	 *            工作流號
	 * @param lLogNo
	 *            序號
	 * @param modelType
	 *            模板類型
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param classCode
	 *            險類代碼
	 * @param comCode
	 *            歸屬機構代碼
	 * @param makecom
	 *            出單機構
	 * @param userCode
	 *            用戶代碼
	 * @param handlerCode
	 *            經辦人代碼
	 * @param handler1Code
	 *            歸屬業務員代碼
	 * @param contractNo
	 *            合同號
	 * @param flag
	 *            標誌
	 * @return String
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             SQL異常
	 * @throws Exception
	 *             異常
	 */
	public String start(String lFlowID, int lLogNo, String modelType, String certiType, String businessNo, String riskCode, String classCode, String comCode,
			String makecom, String userCode, String handlerCode, String handler1Code, String contractNo, String flag) throws UserException, SQLException,
			Exception {
		//String flowID = this.start(modelType, certiType, businessNo, riskCode, classCode, comCode, makecom, userCode, handlerCode, handler1Code, contractNo, "");
		return "";
	}

	/**
	 * 提交任務撤回.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param userCode
	 *            用戶代碼
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             SQL異常
	 * @throws Exception
	 *             異常
	 */
	public void retract(String businessNo, String userCode) throws UserException, SQLException, Exception {
		PrpDuserDto prpDuserDto = new PrpDuserDto();
		prpDuserDto.setUserCode(userCode);
		commonDealSubmitService.retract(businessNo, prpDuserDto);
	}

	/**
	 * 判斷工作流為新啟動還是待修改.
	 * 
	 * @param iBusinessNo
	 *            業務號
	 * @return flag(U:修改,N:新啟動,0:出錯)
	 * @throws SQLException
	 *             SQL異常
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String checkStartType(String iBusinessNo) throws SQLException, UserException, Exception {
		String startType = "";
		int intCount = 0;
		int intCount1 = 0;
		QueryRule queryRule = QueryRule.getInstance();
		try {
			queryRule.addEqual("businessNo", iBusinessNo);
			queryRule.addNotEqual("nodeStatus", "0");
			queryRule.addNotEqual("nodeStatus", "4");
			intCount = wfLogService.getCount(queryRule);
			if (intCount == 0) {
				startType = "N";
			}
			if (intCount == 1) {
				queryRule.addEqual("id.logNo", 2);
				intCount1 = wfLogService.getCount(queryRule);
				if (intCount1 == 0) {
					startType = "U";
				} else {
					throw new UserException(-98, -1106, this.getClass().getName());
				}
			}
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		}
		return startType;
	}

	/**
	 * 檢查是否可以進行自動審核.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 檢查結果
	 * @throws UserException
	 *             用戶異常
	 * @throws Exception
	 *             異常
	 */
	public boolean isAutoUndwrt(String businessNo, String businessType) throws UserException, Exception {
		String strRenewalFlag = "";
		boolean hasPath = false;
		//add by gaojunfeng 需求150 獲取人工開關和作業狀態 start
		String valueType = taskDealService.getRenGongKaiGuanStatu();//獲取開關狀態
		System.out.println(valueType+":valueType");
		String workStatus = taskDealService.getWorkStatusForBusiNo(businessNo, businessType);//獲取作業狀態
		System.out.println(workStatus+":workStatus   ");
		//add by gaojunfeng 需求150 獲取人工開關和作業狀態 end
		//開關關閉0且作業狀態為待再查詢04或不執行00，或開關開啟時走核保規則
	if(("0".equals(valueType)&&("04".equals(workStatus)||"00".equals(workStatus)))||"1".equals(valueType)){
		if ("T".equals(businessType)) {
			PrpTmain prpTmain = null;
			prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			if (prpTmain != null && prpTmain.getPrpTrenewals().size()>0) {
				if(prpTmain.getPrpTrenewals().size()>0)
				{
				strRenewalFlag = prpTmain.getPrpTrenewals().get(0).getFlag();// 批量续保标志
				}
			}
//			if ("1".equals(strRenewalFlag)) {// 批量续保的业务自动审核
//				return true;
//			}
			//要保書添加自動核保規則 20150403 modefied by zhangruofei 
			//modefied by zhangruofei 20150422 begin 暫時不使用drools校驗，只根據投保的險種是否包含47險種和強制險中的全部或其中一個
//			InternationalizationUtil internal = new InternationalizationUtil();
//			droolsRulesMessage = "";
//			BusinessProposalData condition = getBusinessDataService.getProposalCarAutoBusinessData(businessNo, businessType);
//			droolsRuleService.executeRules("undwrtAutomaticProposal.xml", condition);
//			if (!condition.isRulesCheckFlag()) {
//				throw new RuntimeException(internal.getText("undwrt.service.task.contractAdmin"));
//			}
//			hasPath = condition.getResult();
//			if (hasPath) {
//				return true;
//			} else {
//				droolsRulesMessage = condition.getStrResultMessage();
//			}
			if(!"B2B&B2C".equals(prpTmain.getOperateSite())) {
				if(checkKindCodeAuto(businessNo, businessType)) {
					return true;
				}
			}			
			//modefied by zhangruofei 20150422 begin 暫時不使用drools校驗，只根據投保的險種是否包含47險種和強制險中的全部或其中一個
			if("B2B&B2C".equals(prpTmain.getOperateSite())){//B2B、B2C自动核保
				return true;
			}
		} else if ("B".equals(businessType)) {
			PrpQmain prpQmian = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
			// AS400业务的报价单自动审核通过
			String editFlag = prpQmian.getEditFlag();
			// 批量续保的报价单自动审核通过
			String hql = "from RenewalInfo where quoteNo= '" + businessNo + "'";
			List list = this.findByHql(hql);
			//modefied by zhangruofei 20141027 续保的数据也需要进行核保规则校验(限保车牌、车种等)
//			if ("1".equals(editFlag) || "2".equals(editFlag) || list.size() > 0) {
//				return true;
//			}
			InternationalizationUtil internal = new InternationalizationUtil();
			droolsRulesMessage = "";
			//modefied by zhangruofei 20141223  報價單有加裝設備不能自動核保通過	
			//modify by liuyangsx1727 投保有加裝配備報價單下發修改問題  20161215 begin
			if("A01".equals(prpQmian.getRiskCode())||"B01".equals(prpQmian.getRiskCode())) {
				if(null!=prpQmian.getPrpQcarDevices() && prpQmian.getPrpQcarDevices().size()>0) {
					droolsRulesMessage = "undwrt.rule.havePrpCarDevices";
					return false;
				}
			}
			//modify by liuyangsx1727 投保有加裝配備報價單下發修改問題  20161215 end
			//add by songxin 20160824 mantis5128 機車(18險種)機車整車失竊限額損失保險 start
			/*
			mantis： CAR0181，處理人員：Sam，需求單編號：CAR0181--- start
			續保18險種自動核保通過處理
			*/
			/*
			if("A01".equals(prpQmian.getRiskCode())){
				List<PrpQitemKind> prpQitemkindList = prpQmian.getPrpQitemKinds();
				for(PrpQitemKind prpQitemkind : prpQitemkindList){
					if("18".equals(prpQitemkind.getKindCode())){
						droolsRulesMessage = "undwrt.rule.haveKindCode18";
						return false;
					}
				}
			}
			*/
			/* mantis： CAR0181，處理人員：Sam，需求單編號：CAR0181 --- end */
			//add by songxin 20160824 mantis5128 機車(18險種)機車整車失竊限額損失保險 start
			BusinessProposalData condition = getBusinessDataService.getBusinessProposalData(businessNo, businessType);
			droolsRuleService.executeRules("undwrtAutomatic.xml", condition);
			if (!condition.isRulesCheckFlag()) {
				throw new RuntimeException(internal.getText("undwrt.service.task.contractAdmin"));
			}
			hasPath = condition.getResult();
			if (hasPath) {
				return true;
			} else {
				droolsRulesMessage = condition.getStrResultMessage();
			}
		}
		if ("E".equals(businessType)) 
		{
			PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
			String endorType = prpPhead.getEndorType();
			if("40".equals(endorType) || "90".equals(endorType) || "91".equals(endorType) || "100".equals(prpPhead.getEndorType()))
			{
				return true;
			}
		}
	 }else{//add by gaojunfeng 需求150 20160827 start
		 if ("T".equals(businessType)||"E".equals(businessType)) {	
			 return false;
		 }else{
			//update by zhangjiabao mantis5149 需求150 針對洗錢防制自動核保不通過轉為人工核保的警示  20161208  begin
			//droolsRulesMessage = "undwrt.rule.badRecords";
			droolsRulesMessage = "請洽客服提交區部，辦理覆核事宜！";
			//update by zhangjiabao mantis5149 需求150 針對洗錢防制自動核保不通過轉為人工核保的警示  20161208  end
		    return false;  
		 }
		//add by gaojunfeng 需求150 20160827 end
	 }
		
		return false;
	}
	/**
	 * 檢查非車險種是否可以進行自動審核.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 檢查結果
	 * 
	 * @throws Exception
	 *             異常
	 */
	public boolean isNoCarAutoUndwrt(String businessNo,String businessType,String riskCode,String[] autoMessage) throws Exception{
		//add by xuhuiling 需求150 獲取人工開關和作業狀態 begin
		String valueType = taskDealService.getRenGongKaiGuanStatu();
		String workStatus = taskDealService.getWorkStatusForBusiNo(businessNo, businessType);
		//add by xuhuiling mantis5579 譜絡批單洗錢狀態有誤 (TA普洛的批單要自動核保通過)20170830 begin
		if("E".equals(businessType)&&"TA".equals(riskCode)&&(!"".equals(autoMessage[1])&&autoMessage[1] != null)){
			workStatus = "00";
		}
		//mantis： LIA0287，處理人員：Sam，需求單編號：LIA0287 行動裝置保險WS功能開發 Start
		if("MI".equals(riskCode) && !"".equals(autoMessage[1])&&autoMessage[1] != null){
			return true;
		}
		//mantis： LIA0309，處理人員：Sam，需求單編號：三合一WS自動核保通過 autoMessage[1] = operatorSite B2B2C?
		if(",EM,ER,GA,".contains(","+riskCode+",") && !StringUtils.isBlank(autoMessage[1])){
			return true;
		}
		//mantis： LIA0287，處理人員：Sam，需求單編號：LIA0287 行動裝置保險WS功能開發 End
		//add by xuhuiling mantis5579 譜絡批單洗錢狀態有誤  (TA普洛的批單要自動核保通過)20170830 xuhuiling begin
		//add by xuhuiling 需求150 獲取人工開關和作業狀態 end
		/*
		mantis： FIR0189，處理人員：Sam，需求單編號：FIR0189--- start
		核保 08 狀態 住火 自動核保流程設定通過
		*/
		if("08".equals(workStatus) && "T".equals(businessType) && "F02".equals(riskCode)){
			return true;
		}
		if((valueType.equals("0")&&("04".equals(workStatus)||"00".equals(workStatus)))||"1".equals(valueType)){
		if("E".equals(businessType)){
			PrpPhead prpPhead = new PrpPhead();
			prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
			if(null!=prpPhead.getEndorType() && "100".equals(prpPhead.getEndorType())){
				return true;
			}
			//add by 穎瑞 mantis5607 保單不出批改 20171010 begin
			if(null!=prpPhead.getEndorType() && "200".equals(prpPhead.getEndorType())&&"M".equals(prpPhead.getClassCode())){
				return true;
			}
			//add by 穎瑞 mantis5607 保單不出批改 20171010 end
			//add by yjm 20151222 普洛批單自動核批通過 start
			// modify by CSy 20160203 普洛批单核批通过 从B2B修改为非空 start 
			if("TA".equals(riskCode) && !"".equals(autoMessage[1])&&autoMessage[1] != null){
				return true;
			}else if("TE".equals(riskCode) && ("1".equals(autoMessage[2]) || "2".equals(autoMessage[2]))){
				return true;
				//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過 
			}
			// modify by CSy 20160203 普洛批单核批通过 从B2B修改为非空 end  
			//add by yjm 20151222 普洛批單自動核批通過 end
		}else if(("B".equals(businessType)||"T".equals(businessType)) && ("F02".equals(riskCode) ))//add by yjm 住火要保书自动核保规则添加  
		{
			boolean hasPath = false;
			InternationalizationUtil internal = new InternationalizationUtil();
			droolsRulesMessage = "";
			BusinessProposalData condition = null;
			//add by xuhuiling 給住火webservice轉檔增加自動核保判斷 begin
			if(autoMessage[1] != null&&!"".equals(autoMessage[1])&&"T".equals(businessType)){
				return true;
			}
			//add by xuhuiling 給住火webservice轉檔增加自動核保判斷 end
			if("B".equals(businessType)){
				condition = getBusinessDataService.getLiveFireBusinessData(businessNo, businessType);
			}else{
				condition = getBusinessDataService.getTLiveFireBusinessData(businessNo, businessType);
			}
			//condition.setHaveClaim(true);
			if(null==condition.getBusinessNature() || "".equals(condition.getBusinessNature()))
			{
				autoMessage[0] = "0";//非批次数据，不需自動審核通過
				return false;
			}
			else{
				if("B".equals(businessType)){
					droolsRuleService.executeRules("undwrtAutomatic_F02Report.xml", condition);
				}else{
					droolsRuleService.executeRules("undwrtAutomaticT_F02Report.xml", condition);
				}
				if (!condition.isRulesCheckFlag()) {
					throw new RuntimeException(internal.getText("undwrt.service.task.contractAdmin"));
				}
				hasPath = condition.getResult();
				if(!hasPath){
					autoMessage[0] = condition.getStrResultMessage();
				}
				return hasPath;
			}
		//add by yjm 20151124 旅平險B2B的数据自动核保通过  start
		// modify by CSy 20160203 旅平險自动核保通过 从B2B修改为非空 start 
		}else if("TA".equals(riskCode) && !"".equals(autoMessage[1])&&autoMessage[1] != null){
			return true;
		//mantis： LIA0087，處理人員：Sam，需求單編號：LIA0087 新WS-成單用(目前只有寵物險)寵物險 WS件自動核保通過  autoMessage[1] 會記operateSite B2B2C
		}else if("PE".equals(riskCode) && !"".equals(autoMessage[1])&&autoMessage[1] != null){
			return true;
		//HAS0277 Start
		}else if("PA".equals(riskCode)&&(autoMessage[1] != null&&!"".equals(autoMessage[1]))&&"T".equals(businessType)){
			//mantis： HAS0254，處理人員：Sam，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳+WS調整 Start
			if(!StringUtils.isBlank(autoMessage[0])){
				return false;
			}
			//mantis： HAS0254，處理人員：Sam，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳+WS調整 End
			return true;
		}else if("PA".equals(riskCode)&& "2".equals(autoMessage[3])){// add by CSY 20160418 PA提交核保自动核保通过
			return true ;
		//HAS0277 End
		}else if("TE".equals(riskCode) && ("1".equals(autoMessage[2]) || "2".equals(autoMessage[2]))){
			return true;//add by yjm 20160312 TE飛安險主保單以及小保單自動核保通過 
		}
		//modify by xuhuiling PAwebservice生成要保书自动核保通过20170811 begin
//		else if("PA".equals(riskCode)&&){
//			//mantis： HAS0254，處理人員：Sam，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳+WS調整 Start
//			if(!StringUtils.isBlank(autoMessage[0])){
//				return false;
//			}
//			//mantis： HAS0254，處理人員：Sam，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳+WS調整 End
//			return true;
//		}
		//modify by xuhuiling PAwebservice生成要保书自动核保通过20170811 end
		// modify by CSy 20160203 旅平險自动核保通过 从B2B修改为非空 end  
		//add by yjm 20151124 旅平險B2B的数据自动核保通过  end
		}
        //add by xuhuiling 住火自動核保時候值為空 begin
		if(("F02".equals(riskCode))&&("B".equals(businessType)||"T".equals(businessType))){
			autoMessage[0] = "反洗錢系統未設置完成";
		}
        //add by xuhuiling 住火自動核保時候值為空 end
		return false;
	}
	
	public void checkMainSubQatSubmit(String businessType, String businessNo ) {
		try{
			deleteQmainSubmit(businessType , businessNo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 系統異常刪除保單數據，更新報價要報書狀態-Servlet生成保單數據無法回滾.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param passNodeName
	 *            審核通過節點名稱
	 */
	public void deleteQmainSubmit(String businessType, String businessNo){
			System.out.println("刪除報價單訊息==begin============");
			PrpQmain prpQmain = null;
			prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("businessNo", businessNo);
			WfLog wflog = wfLogService.findByQueryRule(queryRule).get(0);
			RecoveryStatusQtaSubmit(businessNo);
			prpQmain.setUnderWriteFlag("0");
			System.out.println("刪除報價單訊息==end============");
			//writeBackStatus(prpQmain);
	}
	/**
	 * Write back status.
	 * 
	 * 目前系统走Servlet 无法正常控制事务,发生异常时回写underwriteflag的值为9
	 * 
	 * @param obj
	 *            the obj
	 */
	public void writeBackStatus(Object obj) {
		if (obj.getClass().equals(PrpTmain.class)) {
			prpallService.updateTmain((PrpTmain) obj);
		}
		if (obj.getClass().equals(PrpCmain.class)) {
			prpallService.updateCmain((PrpCmain) obj);
		}
		if (obj.getClass().equals(PrpQmain.class)) {
			prpallService.updateQmain((PrpQmain) obj);
		}
	}
	public void RecoveryStatusQtaSubmit(String businessNo)//關聯單報價單核保問題
	{
		Session sessionH = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		sessionH.beginTransaction();
		System.out.println("報價單刪除訊息開始========="+businessNo);
		sessionH.createSQLQuery("delete  FROM INTFPRPJPAYREFKIND  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		sessionH.createSQLQuery("delete  FROM INTFPRPJPAYREFREC  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		sessionH.createSQLQuery("delete  FROM PRPCJPLAN  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		sessionH.createSQLQuery("delete  FROM PRPCJPLANKIND  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		sessionH.createSQLQuery("delete  FROM PRPJFTIME  WHERE POLICYNO = '"+ businessNo+"'").executeUpdate();
		sessionH.createSQLQuery("delete  FROM PRPJPAYREFKIND  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		sessionH.createSQLQuery("delete  FROM PRPJPAYREFREC  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		sessionH.createSQLQuery("delete  FROM PRPJPLANFEE  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		System.out.println("報價單刪除訊息進行中======刪除了收付訊息==="+businessNo);
		PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
		
		if(null!=prpQmain && null!=prpQmain.getRiskCode()) {
			if("A01".equals(prpQmain.getRiskCode())) {
				if(null!=prpQmain.getPrpQmainSubs() && prpQmain.getPrpQmainSubs().size()>0) {
					PrpQmainSub prpQmainSub = prpQmain.getPrpQmainSubs().get(0);
					if(null!=prpQmainSub.getFlag() && "111".equals(prpQmainSub.getFlag())) {
						System.out.println("關聯單報價單刪除訊息開始======刪除了收付訊息==="+businessNo);
						sessionH.createSQLQuery("delete  FROM INTFPRPJPAYREFKIND  WHERE CERTINO = '"+ prpQmainSub.getId().getMainPolicyNo()+"'").executeUpdate();
						sessionH.createSQLQuery("delete  FROM INTFPRPJPAYREFREC  WHERE CERTINO = '"+ prpQmainSub.getId().getMainPolicyNo()+"'").executeUpdate();
						sessionH.createSQLQuery("delete  FROM PRPCJPLAN  WHERE CERTINO = '"+ prpQmainSub.getId().getMainPolicyNo()+"'").executeUpdate();
						sessionH.createSQLQuery("delete  FROM PRPCJPLANKIND  WHERE CERTINO = '"+ prpQmainSub.getId().getMainPolicyNo()+"'").executeUpdate();
						sessionH.createSQLQuery("delete  FROM PRPJFTIME  WHERE POLICYNO = '"+ prpQmainSub.getId().getMainPolicyNo()+"'").executeUpdate();
						sessionH.createSQLQuery("delete  FROM PRPJPAYREFKIND  WHERE CERTINO = '"+ prpQmainSub.getId().getMainPolicyNo()+"'").executeUpdate();
						sessionH.createSQLQuery("delete  FROM PRPJPAYREFREC  WHERE CERTINO = '"+ prpQmainSub.getId().getMainPolicyNo()+"'").executeUpdate();
						sessionH.createSQLQuery("delete  FROM PRPJPLANFEE  WHERE CERTINO = '"+ prpQmainSub.getId().getMainPolicyNo()+"'").executeUpdate();
						System.out.println("關聯單報價單刪除訊息進行中======刪除了收付訊息==="+businessNo);
					}
				}
			}
		}
		sessionH.getTransaction().commit();
		System.out.println("報價單刪除訊息已提交======="+businessNo);
	};
	/**
	 * 只投保強制險和任意險47險種的全部或者其中一個的要保書，可以自動核保通過
	 * @param businessNo
	 * @param businessType
	 * @return
	 * @throws Exception
	 */
	public boolean checkKindCodeAuto(String businessNo, String businessType) throws  Exception {
		boolean flag = false;
		if ("T".equals(businessType)) {
			PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			if(null!=prpTmain) {
				if("A01".equals(prpTmain.getRiskCode())) {
					 //mantis： CAR0445，處理人員：DP0706，CAR0445.車險要保書提交,自動核保規則開發START
					 //	任意險要保書出單如為「複製報價單」作業時，當存檔後且點選「提交核保時」需檢查複製的報價單核保狀態(prpqmain.underwriteflag )
					 // 如為『1 或 3』則走同47&48險種的自動核保規則。(直接為自動核保通過)
					 // 如不為『1 或 3』則同原先核保規則。
					 // 若為複製報價單時，要保書存檔時送核保時會將被複製的"報價單"underwriteflag更新為7(產生報價單)，故判斷時也須加入underwriteflag = 7
					if(prpTmain.getQuoteno() !=null && !"".equals(prpTmain.getQuoteno())){//為「複製報價單」
						PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(prpTmain.getQuoteno(), "quotation");
						if("1".equals(prpQmain.getUnderWriteFlag()) || "3".equals(prpQmain.getUnderWriteFlag()) || "7".equals(prpQmain.getUnderWriteFlag())){
							return true;//自動核保通過
						}
					} 					
					//如任意險要保書出單如不為「複製報價單」作業時照舊有規則進行資料流轉。(執行下面原程式邏輯)
					//mantis： CAR0445，處理人員：DP0706，CAR0445.車險要保書提交,自動核保規則開發END
					String hql="from PrpTitemKind where proposalNo= '" + businessNo + "'";
					List prpTitemKindList=this.findByHql(hql);
					if(null!=prpTitemKindList && prpTitemKindList.size() > 0) {
						if(prpTitemKindList.size()>1) {
							return flag;
						} else {
							PrpTitemKind prpTitemKind =(PrpTitemKind) prpTitemKindList.get(0);
							if(!"47".equals(prpTitemKind.getKindCode()) && !"48".equals(prpTitemKind.getKindCode())) {
								return flag;
							} else {
								flag = true;
							}
						}
					}
				} else if("B01".equals(prpTmain.getRiskCode())) {
					BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
					String strWhere = "";
					strWhere = "mainpolicyno = '" + businessNo + "'";
					blPrpTmainSub.query(strWhere);
					if (blPrpTmainSub.getSize() > 0 && "111".equals(blPrpTmainSub.getArr(0).getFlag())) {
						String strBusinessNo = blPrpTmainSub.getArr(0).getProposalNo();
						boolean flagSub = checkKindCodeAuto(strBusinessNo,businessType);
						if(flagSub) {
							flag = true;
						}
					} else {
						flag = true;
					}
				}
			}
			
			
		}
		return flag;
	}
	/**
	 * 獲取屬性核保審核處理接口.
	 * 
	 * @return 屬性核保審核處理接口的值
	 */
	public CommonDealSubmitService getCommonDealSubmitService() {
		return commonDealSubmitService;
	}

	/**
	 * 設置屬性核保審核處理接口.
	 * 
	 * @param commonDealSubmitService
	 *            待設置的核保審核處理接口的值
	 */
	public void setCommonDealSubmitService(CommonDealSubmitService commonDealSubmitService) {
		this.commonDealSubmitService = commonDealSubmitService;
	}

	/**
	 * 獲取屬性工作流路徑定義接口.
	 * 
	 * @return 屬性工作流路徑定義接口的值
	 */
	public SwfPathService getSwfPathService() {
		return swfPathService;
	}

	/**
	 * 設置屬性工作流路徑定義接口.
	 * 
	 * @param swfPathService
	 *            待設置的工作流路徑定義接口的值
	 */
	public void setSwfPathService(SwfPathService swfPathService) {
		this.swfPathService = swfPathService;
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
	 * 獲取屬性核保服務接口.
	 * 
	 * @return 屬性核保服務接口的值
	 */
	public UndwrtService getUndwrtService() {
		return undwrtService;
	}

	/**
	 * 設置屬性核保服務接口.
	 * 
	 * @param undwrtService
	 *            待設置的核保服務接口的值
	 */
	public void setUndwrtService(UndwrtService undwrtService) {
		this.undwrtService = undwrtService;
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
	 * 獲取屬性核保回寫數據服務接口.
	 * 
	 * @return 屬性核保回寫數據服務接口的值
	 */
	public PrpFeedBackService getPrpFeedBackService() {
		return prpFeedBackService;
	}

	/**
	 * 設置屬性核保回寫數據服務接口.
	 * 
	 * @param prpFeedBackService
	 *            待設置的核保回寫數據服務接口的值
	 */
	public void setPrpFeedBackService(PrpFeedBackService prpFeedBackService) {
		this.prpFeedBackService = prpFeedBackService;
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
	 * 獲取屬性規則引擎接口.
	 * 
	 * @return 屬性規則引擎接口的值
	 */
	public DroolsRuleService getDroolsRuleService() {
		return droolsRuleService;
	}

	/**
	 * 設置屬性規則引擎接口.
	 * 
	 * @param droolsRuleService
	 *            待設置的規則引擎接口的值
	 */
	public void setDroolsRuleService(DroolsRuleService droolsRuleService) {
		this.droolsRuleService = droolsRuleService;
	}

	/**
	 * 獲取屬性業務數據接口.
	 * 
	 * @return 屬性業務數據接口的值
	 */
	public GetBusinessDataService getGetBusinessDataService() {
		return getBusinessDataService;
	}

	/**
	 * 設置屬性業務數據接口.
	 * 
	 * @param getBusinessDataService
	 *            待設置的業務數據接口的值
	 */
	public void setGetBusinessDataService(GetBusinessDataService getBusinessDataService) {
		this.getBusinessDataService = getBusinessDataService;
	}

	/**
	 * 獲取屬性自動核保規則訊息.
	 * 
	 * @return 屬性自動核保規則訊息
	 */
	public String getDroolsRulesMessage() {
		return droolsRulesMessage;
	}

	/**
	 * 設置屬性自動核保規則訊息.
	 * 
	 * @param getBusinessDataService
	 *            待設置的自動核保規則訊息的值
	 */
	public void setDroolsRulesMessage(String droolsRulesMessage) {
		this.droolsRulesMessage = droolsRulesMessage;
	}

	public PrpallService getPrpallService() {
		return prpallService;
	}

	public void setPrpallService(PrpallService prpallService) {
		this.prpallService = prpallService;
	}
	
	/**
	 * 查詢提交核保前的核保狀態
	 */
	public String queryUnderWriteFlag(String businessNo,String bussineeType){
		String underWriteFlag = "";
		if("B".equals(bussineeType)){
			PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
			underWriteFlag = underWriteFlag + prpQmain.getUnderWriteFlag();
			if(null!=prpQmain && null!=prpQmain.getRiskCode()) {
				if("A01".equals(prpQmain.getRiskCode())) {
					if(null!=prpQmain.getPrpQmainSubs() && prpQmain.getPrpQmainSubs().size()>0) {
						PrpQmainSub prpQmainSub = prpQmain.getPrpQmainSubs().get(0);
						if(null!=prpQmainSub.getFlag() && "111".equals(prpQmainSub.getFlag())) {
							PrpQmain prpQmainCI = policyService.getPrpQmainByProposalNo(prpQmainSub.getId().getMainPolicyNo(), "quotation");
							underWriteFlag = underWriteFlag + "," + prpQmainCI.getUnderWriteFlag();
						}
					}
				}
			}
		}
		return underWriteFlag;
	}
	/**
	 * 異常時更新核保狀態為初始值
	 */
	public void updateUnderWriteFlag(String businessNo,String bussineeType,String underWriteFlag){
		if("B".equals(bussineeType)){
			PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
			if(underWriteFlag.indexOf(",") > 0){
				prpQmain.setUnderWriteFlag(underWriteFlag.substring(0,underWriteFlag.indexOf(",")));
			}else{
				prpQmain.setUnderWriteFlag(underWriteFlag);//update by songxin 20160530 自動核保失敗時，回滾核保狀態
			}
			if(null!=prpQmain && null!=prpQmain.getRiskCode()) {
				//update by songxin 20160530 自動核保失敗時，回滾核保狀態 start
				List<PrpQmainSub> prpQmainsubs = prpQmain.getPrpQmainSubs();
				PrpQmainSub prpQmainSub = null;
				if(prpQmainsubs != null && prpQmainsubs.size()>0){
					prpQmainSub = prpQmainsubs.get(0);
				
//					PrpQmainSub prpQmainSub = prpQmain.getPrpQmainSubs().get(0);
					if(null!=prpQmainSub.getFlag() && "111".equals(prpQmainSub.getFlag())) {
						PrpQmain prpQmainCI = policyService.getPrpQmainByProposalNo(prpQmainSub.getId().getMainPolicyNo(), "quotation");
						if(underWriteFlag.indexOf(",") > 0){
							prpQmainCI.setUnderWriteFlag(underWriteFlag.substring(underWriteFlag.indexOf(",") + 1));
						}
						try {
							policyService.updatePrpQmain(prpQmainCI);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				//update by songxin 20160530 自動核保失敗時，回滾核保狀態 end
			}
			try {
				policyService.updatePrpQmain(prpQmain);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public TaskDealService getTaskDealService() {
		return taskDealService;
	}

	public void setTaskDealService(TaskDealService taskDealService) {
		this.taskDealService = taskDealService;
	}
	
	public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }
	  //add by songzhewen 20170301 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin
	@Override
	public PrpDuserCADto findByPrimaryKey(String userCode) throws Exception {
		 DBManager dbManager = new DBManager();
		 dbManager.open("ddccDataSource");
		 StringBuffer buffer = new StringBuffer(200);
	        //拼SQL語句
	        buffer.append("SELECT ");
	        buffer.append("UserCode,");
	        buffer.append("PasswordLifeType,");
	        buffer.append("PasswordLife,");
	        buffer.append("ValidLifeType,");
	        buffer.append("ValidLife,");
	        buffer.append("ValidSetDate,");
	        buffer.append("ValidBeginDate,");
	        buffer.append("ValidExpireDate,");
	        buffer.append("IPAddress,");
	        buffer.append("ShowPriceFlag,");
	        buffer.append("OptionFlag,");
	        buffer.append("Flag ");
	        buffer.append("FROM PrpDuserCA ");
	        if(logger.isDebugEnabled()){
	            StringBuffer debugBuffer =  new StringBuffer(buffer.length()*4);
	            debugBuffer.append(buffer.toString());
	            debugBuffer.append("WHERE ");
	            debugBuffer.append("UserCode=").append("'").append(userCode).append("'");
	            logger.debug(debugBuffer.toString());
	        }
	        buffer.append("WHERE ");
	        buffer.append("UserCode = ?");
	        PrpDuserCADto prpDuserCADto = null;
	        dbManager.prepareStatement(buffer.toString());
	        //設置條件字段;
	        dbManager.setString(1,userCode);
	        ResultSet resultSet = dbManager.executePreparedQuery();
	        if(resultSet.next()){
	            prpDuserCADto = new PrpDuserCADto();
	            prpDuserCADto.setUserCode(dbManager.getString(resultSet,1));
	            prpDuserCADto.setPasswordLifeType(dbManager.getString(resultSet,2));
	            prpDuserCADto.setPasswordLife(dbManager.getString(resultSet,3));
	            prpDuserCADto.setValidLifeType(dbManager.getString(resultSet,4));
	            prpDuserCADto.setValidLife(dbManager.getString(resultSet,5));
	            prpDuserCADto.setValidSetDate(dbManager.getDateTime(resultSet,DateTime.YEAR_TO_DAY,6));
	            prpDuserCADto.setValidBeginDate(dbManager.getDateTime(resultSet,DateTime.YEAR_TO_DAY,7));
	            prpDuserCADto.setValidExpireDate(dbManager.getDateTime(resultSet,DateTime.YEAR_TO_DAY,8));
	            prpDuserCADto.setIPAddress(dbManager.getString(resultSet,9));
	            prpDuserCADto.setShowPriceFlag(dbManager.getString(resultSet,10));
	            prpDuserCADto.setOptionFlag(dbManager.getString(resultSet,11));
	            prpDuserCADto.setFlag(dbManager.getString(resultSet,12));
	        }
	             resultSet.close();
	         	dbManager.close();
	             return prpDuserCADto;
	}

	@Override
	public void update(PrpDuserCADto prpDuserCADto) throws Exception {
		DBManager dbManager = new DBManager();
   	 dbManager.open("ddccDataSource");
	   StringBuffer buffer = new StringBuffer(200);
       buffer.append("UPDATE PrpDuserCA SET ");
       buffer.append("PasswordLifeType = ?, ");
       buffer.append("PasswordLife = ?, ");
       buffer.append("ValidLifeType = ?, ");
       buffer.append("ValidLife = ?, ");
       buffer.append("ValidSetDate = ?, ");
       buffer.append("ValidBeginDate = ?, ");
       buffer.append("ValidExpireDate = ?, ");
       buffer.append("IPAddress = ?, ");
       buffer.append("ShowPriceFlag = ?, ");
       buffer.append("OptionFlag = ?, ");
       buffer.append("Flag = ? ");
       if(logger.isDebugEnabled()){
           StringBuffer debugBuffer =  new StringBuffer(buffer.length()*4);
           debugBuffer.append("UPDATE PrpDuserCA SET ");
           debugBuffer.append("PasswordLifeType = '" + prpDuserCADto.getPasswordLifeType() + "', ");
           debugBuffer.append("PasswordLife = '" + prpDuserCADto.getPasswordLife() + "', ");
           debugBuffer.append("ValidLifeType = '" + prpDuserCADto.getValidLifeType() + "', ");
           debugBuffer.append("ValidLife = '" + prpDuserCADto.getValidLife() + "', ");
           debugBuffer.append("ValidSetDate = '" + prpDuserCADto.getValidSetDate() + "', ");
           debugBuffer.append("ValidBeginDate = '" + prpDuserCADto.getValidBeginDate() + "', ");
           debugBuffer.append("ValidExpireDate = '" + prpDuserCADto.getValidExpireDate() + "', ");
           debugBuffer.append("IPAddress = '" + prpDuserCADto.getIPAddress() + "', ");
           debugBuffer.append("ShowPriceFlag = '" + prpDuserCADto.getShowPriceFlag() + "', ");
           debugBuffer.append("OptionFlag = '" + prpDuserCADto.getOptionFlag() + "', ");
           debugBuffer.append("Flag = '" + prpDuserCADto.getFlag() + "' ");
           debugBuffer.append("WHERE ");
           debugBuffer.append("UserCode=").append("'").append(prpDuserCADto.getUserCode()).append("'");
           logger.debug(debugBuffer.toString());
       }

       buffer.append("WHERE ");
       buffer.append("UserCode = ?");

			dbManager.prepareStatement(buffer.toString());
       //設置更新字段;
       dbManager.setString(1,prpDuserCADto.getPasswordLifeType());
       dbManager.setString(2,prpDuserCADto.getPasswordLife());
       dbManager.setString(3,prpDuserCADto.getValidLifeType());
       dbManager.setString(4,prpDuserCADto.getValidLife());
       dbManager.setDateTime(5,prpDuserCADto.getValidSetDate());
       dbManager.setDateTime(6,prpDuserCADto.getValidBeginDate());
       dbManager.setDateTime(7,prpDuserCADto.getValidExpireDate());
       dbManager.setString(8,prpDuserCADto.getIPAddress());
       dbManager.setString(9,prpDuserCADto.getShowPriceFlag());
       dbManager.setString(10,prpDuserCADto.getOptionFlag());
       dbManager.setString(11,prpDuserCADto.getFlag());
       //設置條件字段;
       dbManager.setString(12,prpDuserCADto.getUserCode());
       dbManager.executePreparedUpdate();
       dbManager.close();
		
	}
	  //add by songzhewen 20170301 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改end

	@Override
	public void checkDataForRenewal(List<PrpQmain> list)throws Exception {
		DBManager dbManager = new DBManager();
		SimpleDateFormat logFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
			dbManager.beginTransaction();//將開啓事務與提交事務放在外層   關聯單報價單核保問題
			loggerRenewal.error("進入的線程對象"+Thread.currentThread()+"~~~~~~~~~~~~~獲取該線程提交核保的集合數據~~~~~~~~~~~~~~~"+list.size());
			if(list != null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					try{
						if("0".equals(((PrpQmain)list.get(i)).getUnderWriteFlag()) || "2".equals(((PrpQmain)list.get(i)).getUnderWriteFlag())
								||"4".equals(((PrpQmain)list.get(i)).getUnderWriteFlag())) {
							//add by gaojunfeng 需求150 車險續保處理    20160901 start
							PrpQmain prpQmain=((PrpQmain)list.get(i));
							String strUserCode=prpQmain.getOperatorCode();
							String editFlag=prpQmain.getEditFlag();
							String valueType = taskDealService.getRenGongKaiGuanStatu();//獲取開關狀態
							String updateWorkStatusSQL="";
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							List renewallist=policyService.getRenewalInfoByQuoteno(((PrpQmain)list.get(i)).getProposalNo());
							if(IConstants.PRPINS_RISK_CAR.equals(prpQmain.getRiskCode())||IConstants.PRPINS_RISK_CARCI.equals(prpQmain.getRiskCode())){
								if("0".equals(valueType)){//人工維護開關關閉
									if("1".equals(editFlag)||"2".equals(editFlag)||renewallist.size()>0){//續保的單子設置作業狀態不執行
										updateWorkStatusSQL = "update PrpQmain set workstatus='00' where proposalno='"+((PrpQmain)list.get(i)).getProposalNo()+"'";//更新作業狀態為不執行
									    wfLogService.updateMainStatus(updateWorkStatusSQL);
									}
								}else{//開關開啟
									if((IConstants.PRPINS_RISK_CAR.equals(prpQmain.getRiskCode())||IConstants.PRPINS_RISK_CARCI.equals(prpQmain.getRiskCode()))&&
									  		  ("1".equals(editFlag)||"2".equals(editFlag)||list.size()>0)){//續保的單子設置作業狀態為不執行
										updateWorkStatusSQL = "update PrpQmain set workstatus='00' where proposalno='"+((PrpQmain)list.get(i)).getProposalNo()+"'";//更新作業狀態為不執行
									    wfLogService.updateMainStatus(updateWorkStatusSQL);
									}
								}
							}
							//add by gaojunfeng 需求150 車險續保處理   20160901 end 
							this.startPrepare(dbManager,((PrpQmain)list.get(i)).getProposalNo(), "B");
						}
					}catch(Exception e){
						e.printStackTrace();
						//add by xuhuilling mantis 4906 重覆點擊提交核保begin
						if(e.getMessage()!=null){
							throw e;
						}
						//add by xuhuilling mantis 4906 重覆點擊提交核保begin
					}
				}
			}
		} catch (UserException ue) {
			dbManager.rollbackTransaction();
			ue.printStackTrace();
			throw ue;
        } catch (SQLException e) {
			dbManager.rollbackTransaction();
			throw e; 
		} catch (Exception e) {
			dbManager.rollbackTransaction();
			throw e;
		} finally {
			dbManager.close();
		}
			
	}

	@Override
	public void checkDateByThread(String businessNo, String businessType)
			throws Exception {
		
		DBManager dbManager = new DBManager();
		 try {
			 dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
			 List<PrpQmain> list = super.findByHql("from PrpQmain where batchNO=? and underWriteFlag in ('0','2','4')",businessNo);
			
		        int thread_count = 5;
		        CountDownLatch endSigle = new CountDownLatch(thread_count);
		        //每个线程的平均批次号个数
		        int avg_count = (int)Math.floor(list.size()*1.0/thread_count);
		        int count = list.size()%thread_count;
		        Thread[] threadVec = new Thread[thread_count];
		        int b=0;
		        for (int i= 0;i < thread_count;i++)  
		        {  
		        	int begin=0,end=0;
		        	if(i == count){
		        		b=i*(avg_count+1);
		        	}
		        	if(i < count){
		        		begin = i*(avg_count+1);
		        		end = (i+1)*(avg_count+1);
		        	}else{
		        		begin = b+(i-count)*avg_count;
		        		end = b+(i-count+1)*avg_count;
		        	}
		            threadVec[i] = new SubmitUndwrtThread(list.subList(begin,end),endSigle);  
		        	threadVec[i].start();  
		        }
				endSigle.await();
	        }catch(Exception e){
	        	// TODO Auto-generated catch block
				e.printStackTrace();
				
	        }finally{
	        	 dbManager.close();
	        }
		
	}

	/*
	mantis： CAR0210，處理人員：Sam，需求單編號：CAR0210--- start
	續保關聯單任意險待核保 強制險也需待核保
	*/
	/**
	 * 檢查續保任意險關聯單之任意險是否自動核保失敗 取得任意險核保狀態
	 * 因Hibernate會取到未改前的catch 故用DBManager作業
	 * @param PROPOSALNO
	 * @return
	 * @throws Exception
	 */
	public String getUnderWriteFlag(String PROPOSALNO) throws Exception {
		String underWriteFlag = null;
		DBManager dbManager = new DBManager();
		ResultSet resultSet = null;
		try{
			dbManager.open("ddccDataSource");
			StringBuffer buffer = new StringBuffer(200);
			buffer.append(" SELECT underWriteFlag ");
			buffer.append(" from prpqmain ");
			buffer.append(" where PROPOSALNO = ? ");
			dbManager.prepareStatement(buffer.toString());
			dbManager.setString(1,PROPOSALNO);
			resultSet = dbManager.executePreparedQuery();
			if(resultSet.next()){
				underWriteFlag = dbManager.getString(resultSet,1);
			}
			return underWriteFlag;
		}catch(Exception e){
			System.out.println("getUnderWriteFlag Exception :"+e);
		}finally{
			try {
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				dbManager.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
		return null;
	}
	/* mantis： CAR0210，處理人員：Sam，需求單編號：CAR0210 --- end */

	/*
	mantis： LIA0092，處理人員：Sam，需求單編號：LIA0092--- start
	普通批改-核保&保費試算
	*/
	/**
	 * 寵物險檢視是否重覆投保
	 * 因Hibernate會取到未改前的catch 故用DBManager作業
	 * @param PROPOSALNO
	 * @return
	 * @throws Exception
	 */
	public boolean checkDoubleInsuranceByPE(String proposalno) throws Exception {
		DBManager dbManager = new DBManager();
		ResultSet resultSet = null;
		boolean isDoubleInsurance = false;
		try{
			dbManager.open("ddccDataSource");
			StringBuffer buffer = new StringBuffer(200);
			buffer.append(" SELECT STARTDATE , STARTHOUR , ENDDATE , ENDHOUR , IDENTIFYNUMBER ");
			buffer.append(" FROM PRPTPE PE INNER JOIN PRPTMAIN TM ON TM.PROPOSALNO = PE.PROPOSALNO ");
			buffer.append(" WHERE CERTIFIEDDOCUMENTS LIKE '%1%' ");
			buffer.append(" AND PE.PROPOSALNO = ? ");
			dbManager.prepareStatement(buffer.toString());
			dbManager.setString(1,proposalno);
			resultSet = dbManager.executePreparedQuery();
			String identifynumber = null;
			Date startDate = null;
			Date endDate = null;
			Integer startHour = null;
			Integer endHour = null;
			if(resultSet.next()){
				startDate = dbManager.getDateTime(resultSet,1);
				startHour = dbManager.getInt(resultSet,2);
				endDate = dbManager.getDateTime(resultSet,3);
				endHour = dbManager.getInt(resultSet,4);
				identifynumber = dbManager.getString(resultSet,5);
			}
			if(!StringUtils.isBlank(identifynumber)){
				//是晶片 檢查保單
				//mantis： LIA0204，處理人員：DP0706，寵物險送審失敗 Start
				String startDT = DateUtil.formatDate(startDate,"YYYYMMdd")+startHour;
				String endDT = DateUtil.formatDate(endDate,"YYYYMMdd")+endHour;
				//mantis： LIA0204，處理人員：DP0706，寵物險送審失敗 End
				buffer = new StringBuffer(200);
				buffer.append(" SELECT POLICYNO FROM PRPCMAIN WHERE POLICYNO IN ( ");
				buffer.append(" 	SELECT POLICYNO FROM PRPCPE WHERE IDENTIFYNUMBER = ? AND CERTIFIEDDOCUMENTS like '%1%' ");
				buffer.append(" ) AND RISKCODE='PE' ");
				buffer.append(" AND ( ");
				buffer.append(" 		TO_CHAR(STARTDATE,'YYYYMMDD')||STARTHOUR BETWEEN ? AND ? ");
				buffer.append(" 		OR TO_CHAR(ENDDATE,'YYYYMMDD')||ENDHOUR BETWEEN ? AND ? ");
				buffer.append(" 		OR ? BETWEEN TO_CHAR(STARTDATE,'YYYYMMDD')||STARTHOUR AND TO_CHAR(ENDDATE,'YYYYMMDD')||ENDHOUR "); 
				buffer.append(" 		OR ? BETWEEN TO_CHAR(STARTDATE,'YYYYMMDD')||STARTHOUR AND TO_CHAR(ENDDATE,'YYYYMMDD')||ENDHOUR ");
				buffer.append(" ) AND POLICYNO NOT IN( ");//以下為查詢非退保
				buffer.append(" 	SELECT POLICYNO FROM PRPPHEAD ");
				buffer.append(" 	WHERE ENDORTYPE IN('19','21','98')  ");
				buffer.append(" 	AND UNDERWRITEFLAG IN ('1','3') ");
				buffer.append(" ) and TO_CHAR(ENDDATE,'YYYYMMDD')||ENDHOUR <> ?");//mantis： LIA0204，處理人員：DP0706，寵物險送審失敗 
				dbManager.prepareStatement(buffer.toString());
				dbManager.setString(1,identifynumber);
				dbManager.setString(2,startDT);
				dbManager.setString(3,endDT);
				dbManager.setString(4,startDT);
				dbManager.setString(5,endDT);
				dbManager.setString(6,startDT);
				dbManager.setString(7,endDT);
				dbManager.setString(8,startDT);//mantis： LIA0204，處理人員：DP0706，寵物險送審失敗
				resultSet = dbManager.executePreparedQuery();
				if(resultSet.next()){
					isDoubleInsurance = true;
				}
			}else{
				return false;
			}
		}catch(Exception e){
			System.out.println(" checkDoubleInsuranceByPE Exception :"+e);
		}finally{
			try {
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				dbManager.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
		return isDoubleInsurance;
	}
	/* mantis： LIA0092，處理人員：Sam，需求單編號：LIA0092 --- end */
	public static void main(String arg[]){
		System.out.println(DateUtil.formatDate(new Date(),"YYYYMMdd"));	
	}
	
	
}