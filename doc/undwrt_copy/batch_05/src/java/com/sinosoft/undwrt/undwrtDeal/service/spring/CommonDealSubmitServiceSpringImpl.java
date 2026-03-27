package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.sinosoft.claim.dto.domain.PrpLcompensateDto;
import com.sinosoft.claim.resource.dtofactory.domain.DBPrpLcompensate;
import com.sinosoft.common.schema.model.PrpCPmain;
import com.sinosoft.common.schema.model.PrpCinsured;
import com.sinosoft.common.schema.model.PrpCitemCar;
import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpDprint;
import com.sinosoft.common.schema.model.PrpDprintId;
import com.sinosoft.common.schema.model.PrpJpayRefRecHis;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpPitemKind;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpQinsured;
import com.sinosoft.common.schema.model.PrpQitemCar;
import com.sinosoft.common.schema.model.PrpQmain;
import com.sinosoft.common.schema.model.PrpQmainSub;
import com.sinosoft.common.schema.model.PrpTinsured;
import com.sinosoft.common.schema.model.PrpTitemCar;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.common.schema.model.PrpTmainSub;
import com.sinosoft.common.util.CreateEncodingUtil;
//mantis： CAR0236，處理人員：Sam，需求單編號：CAR0236 核批通過時需回寫CARR_ECARD_TRANSE
import com.sinosoft.common.util.DateUtil;
import com.sinosoft.platform.bl.action.domain.BLPrpDpreauditConfigAction;
import com.sinosoft.platform.bl.facade.BLUtiOperateLogFacade;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.prpall.blsvr.cb.BLPrpCmain;
import com.sinosoft.prpall.blsvr.cb.BLPrpCmainCovernote;
import com.sinosoft.prpall.blsvr.pg.BLPrpPhead;
import com.sinosoft.prpall.blsvr.pg.BLPrpPmainCovernote;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmain;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmainSub;
import com.sinosoft.prpall.dto.domain.PrpCmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpLprepayDto;
import com.sinosoft.prpall.dto.domain.PrpPmainCovernoteDto;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCmainCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpLprepay;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPmainCovernote;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.prpins.policy.web.EndorseAction;
import com.sinosoft.reins.common.service.facade.BLCDangerGetService;
import com.sinosoft.reins.common.service.facade.BLPDangerGetService;
import com.sinosoft.reins.in.facultative.verify.model.PrpReinsVerify;
import com.sinosoft.reins.in.facultative.verify.service.facade.PrpReinsVerifyService;
import com.sinosoft.reins.interf.service.facade.BLTDangerGetService;
import com.sinosoft.reins.out.facultative.enquiry.model.FeoEnquiry;
import com.sinosoft.reins.product.code.service.facade.BLReinsService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.model.PrpDclass;
import com.sinosoft.undwrt.common.model.PrpDcompany;
import com.sinosoft.undwrt.common.model.PrpDrisk;
import com.sinosoft.undwrt.common.model.PrpDuser;
import com.sinosoft.undwrt.common.service.facade.PrpDclassService;
import com.sinosoft.undwrt.common.service.facade.PrpDcompanyService;
import com.sinosoft.undwrt.common.service.facade.PrpDriskService;
import com.sinosoft.undwrt.common.service.facade.PrpDuserService;
/* mantis： OTH0139，處理人員：Sam，需求單編號：OTH0139 --- */
import com.sinosoft.undwrt.common.util.UtilTools;
import com.sinosoft.undwrt.message.service.facade.MessageService;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.PrpDserial;
import com.sinosoft.undwrt.undwrtBase.model.PrpJpayRefRec;
import com.sinosoft.undwrt.undwrtBase.model.SwfNode;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.model.UwNotionId;
import com.sinosoft.undwrt.undwrtBase.model.WfFlowMain;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.model.WfLogExt;
import com.sinosoft.undwrt.undwrtBase.model.WfLogExtId;
import com.sinosoft.undwrt.undwrtBase.model.WfLogId;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfFlowMainService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogExtService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfPackageService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDealSubmitService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.ExpenseControlDealService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpFeedBackService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.WorkFlowService;
import com.sinosoft.undwrt.undwrtDeal.vo.WfGradeVo;
import com.sinosoft.utiall.blsvr.BLPrpDcompany;
import com.sinosoft.utiall.blsvr.BLPrpDconfigCode;
import com.sinosoft.utility.UtiPower;
import com.sinosoft.utility.log.Log;
import com.sinosoft.utility.string.ChgDate;
import com.sinosoft.utility.string.Str;

/**
 * 核保審核處理實現類.
 */
public class CommonDealSubmitServiceSpringImpl extends GenericDaoHibernate implements CommonDealSubmitService {
	
	//mantis： OTH0038，處理人員：Sam，需求單編號：OTH0038 繳費虛擬碼調整
	private Ehcache policyCache;
	
	/** 屬性rule字段的長度. */
	public static final int RULE_LENGTH = 70; // rule字段的长度
	private Logger loggerRenewal = Logger.getLogger(CommonDealSubmitServiceSpringImpl.class); 
	/** 屬性是否審核通過節點. */
	private String nodeType = ""; // 是否审核通过节点

	/** 工作流狀態接口. */
	private String status = "";

	/** 屬性最終核保人代碼. */
	private String underWriteCode = "";

	/** 屬性核保日期. */
	private DateTime underWriteDate = new DateTime(new DateTime().current().toString().substring(0, 10));

	/** 屬性業務類型. */
	private char certiType;

	/** 屬性工作流號. */
	private String lFlowID = "";

	/** 屬性序號. */
	private int lLogNo = 0;

	/** 屬性記錄關聯出單的車險商業險操作員代碼. */
	private String LogOperatorCode = "";// 记录关联出单的车险商业险操作员代码

	/** 屬性是否關聯出單. */
	private boolean IsMainSub = false;// 是否关联出单

	/** 屬性再保接口業務處理接口. */
	private BLReinsService blReinsService;

	/** 屬性再保分入信息確認接口. */
	private PrpReinsVerifyService prpReinsVerifyService;

	/** 屬性保單危險單位處理接口. */
	private BLCDangerGetService bLCDangerGetService;

	/** 屬性批單危險單位處理接口. */
	private BLPDangerGetService bLPDangerGetService;

	/** 屬性要保書危險單位處理接口. */
	private BLTDangerGetService bLTDangerGetService;

	/** 屬性核保回寫數據服務接口. */
	private PrpFeedBackService prpFeedBackService;

	/** 屬性核保處理意見接口. */
	private UwNotionService uwNotionService;

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性工作流接口. */
	private WorkFlowService workFlowService;

	/** 屬性工作流節點定義接口. */
	private SwfNodeService swfNodeService;

	/** 屬性工作流日誌附屬接口. */
	private WfLogExtService wfLogExtService;

	/** 屬性定級信息接口. */
	private WfGradeService wfGradeService;

	/** 屬性核定費用結余服務接口. */
	private ExpenseControlDealService expenseControlDealService;

	/** 屬性工作流包信息接口. */
	private WfPackageService wfPackageService;

	/** 屬性工作流主表接口. */
	private WfFlowMainService wfFlowMainService;

	/** 屬性要保書處理接口. */
	private PolicyService policyService;

	/** 屬性用戶訊息接口. */
	private PrpDuserService prpDuserService;

	/** 屬性機構接口. */
	private PrpDcompanyService prpDcompanyService;

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/** 屬性險種接口. */
	private PrpDriskService prpDriskService;

	/** 屬性險類接口. */
	private PrpDclassService prpDclassService;

	/** 屬性即時訊息服務接口. */
	private MessageService messageService;
	
	private DataSource dataSource;
	
	/*
	mantis： OTH0038，處理人員：Sam，需求單編號：OTH0038--- start
	繳費虛擬碼調整
	*/
	public CommonDealSubmitServiceSpringImpl() {
		new DateTime();
		this.underWriteDate = new DateTime(DateTime.current().toString()
				.substring(0, 10));
		this.lFlowID = "";
		this.lLogNo = 0;
		this.LogOperatorCode = "";
		this.IsMainSub = false;
	}
	/* mantis： OTH0038，處理人員：Sam，需求單編號：OTH0038 --- end */
	
	/**
	 * 獲取屬性即時訊息服務接口.
	 * 
	 * @return 屬性即時訊息服務接口的值
	 */
	public MessageService getMessageService() {
		return messageService;
	}

	/**
	 * 設置屬性即時訊息服務接口.
	 * 
	 * @param messageService
	 *            待設置的即時訊息服務接口的值
	 */
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
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
	 */
	public void submitTaskQta(String businessNo, String userCode, String iTaskCode) throws Exception, UserException {
		InternationalizationUtil internal = new InternationalizationUtil();
		DateTime underWriteDate = new DateTime(new DateTime().current().toString().substring(0, 10));
		String undwrtFlag = "";
		PrpQmain prpQmain = new PrpQmain();

		try {
			prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");

			if (iTaskCode.equals("2") || iTaskCode.equals("1")) {// 报价审核打回、通过
				if (!prpQmain.getUnderWriteFlag().equals("9")) {// 不是待审核状态
					throw new UserException(-98, -1149, "", internal.getText("undwrt.service.commonDealSubmit.maybeOperated"));
				}
			}

			if (iTaskCode.equals("8")) {// 投保单核保通过
				if (!prpQmain.getUnderWriteFlag().equals("7")) {// 不是生成投保单状态
					throw new UserException(-98, -1149, "", internal.getText("undwrt.service.commonDealSubmit.maybeOperated2"));
				}
			}
			prpFeedBackService.echoQta(businessNo, iTaskCode, userCode, underWriteDate);
			/*
			mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
			新AML
			*/
			prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
			if(prpQmain != null){
				PrpQmain prpQmainCI = new PrpQmain();
				if("A01".equals(prpQmain.getRiskCode())){
					PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
					if(sub != null){
						prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
						//mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 新AML問題
						if(prpQmainCI != null){
							prpFeedBackService.echoQta(prpQmainCI.getProposalNo(), iTaskCode, userCode, underWriteDate);
						}
					}
				}
			}
			/* mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 --- end */
		} catch (UserException ue) {
			ue.printStackTrace();
			throw ue;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		// add by chengkai;20080515;修改双核核保通过时对Error级错误的事务控制;begin
		catch (Error e) {
			e.printStackTrace();// 后台输出错误

			// 将错误写入日志文件
			String strError = "Undwrt Error, Error Times:" + new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString()
					+ ", Error Location:BLWfLogFacade.submitTask" + "\r\n";// 要写明错误产后的位置Error
																			// Location
			strError += "Error Reason:" + e.getMessage(); // 加入错误原因
			strError += "===============================================" + "===============================================";
			Log.init("undwrtError.log", "undwrtError.log", true); // 每个系统采用不一样的文件名。以生成不同文件。文件将在domain下
			Log.println(strError);// 输入内容到日志中

			throw new Exception(e.getMessage());// 抛出外层可以捕获的Exception异常。
		}
	}

	/**
	 * 保存審核意見.
	 * 
	 * @param uwNotion
	 *            核保意見類
	 * @throws Exception
	 *             異常
	 */
	public void saveNotion(UwNotion uwNotion) throws Exception {
		// 保存审批意见
		uwNotionService.insertAll(this.ungroup(uwNotion));
	}

	/**
	 * 將HandleText拆分，組成多個uwNotionDto對象.
	 * 
	 * @param uwNotion
	 *            核保意見類
	 * @return 核保意見類集合
	 */
	public List<UwNotion> ungroup(UwNotion uwNotion) {
		List<UwNotion> uwNotionList = new ArrayList<UwNotion>();
		UwNotion uwNotionNew = null;
		String[] arrHandleText = {}; // 审批意见拆分后的数组
		int i = 0;
		if (uwNotion.getHandleText() == null || uwNotion.getHandleText().equals("")) {
			arrHandleText = new String[1];
			arrHandleText[0] = "";
		} else {
			// 拆分审批意见
			arrHandleText = StringUtils.split(uwNotion.getHandleText(), RULE_LENGTH);
		}
		for (i = 0; i < arrHandleText.length; i++) {
			uwNotionNew = new UwNotion();
			UwNotionId id = new UwNotionId();
			uwNotionNew.setId(id);
			uwNotionNew.getId().setFlowId(uwNotion.getId().getFlowId());
			uwNotionNew.getId().setLineNo(i + 1);
			uwNotionNew.getId().setLogNo(uwNotion.getId().getLogNo());
			uwNotionNew.setHandleText(arrHandleText[i]);
			uwNotionList.add(uwNotionNew);
		}
		return uwNotionList;
	}

	/**
	 * 將HandleText拆分，組成多個uwNotionDto對象.
	 * 
	 * @param uwNotionDto
	 *            核保意見類
	 * @return 核保意見類集合
	 */
	public Collection<UwNotion> ungroupIlog(UwNotion uwNotionDto) {
		Collection<UwNotion> col = new ArrayList<UwNotion>();

		int lineNo = 0;
		QueryRule queryRule = QueryRule.getInstance();

		queryRule.addEqual("id.flowId", uwNotionDto.getId().getFlowId());
		queryRule.addEqual("id.logNo", uwNotionDto.getId().getLogNo());
		queryRule.addIsNull("Flag");
		try {
			lineNo = uwNotionService.findByConditions(queryRule).size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] arrHandleText = {}; // 审批意见拆分后的数组
		int i = 0;
		if (uwNotionDto.getHandleText() == null || uwNotionDto.getHandleText().equals("")) {
			arrHandleText = new String[1];
			arrHandleText[0] = "";
		} else {
			// 拆分审批意见
			arrHandleText = StringUtils.split(uwNotionDto.getHandleText(), RULE_LENGTH);
		}
		for (i = 0; i < arrHandleText.length; i++) {
			UwNotion uwNotionNew = new UwNotion();
			UwNotionId uwNotionNewId = new UwNotionId();
			uwNotionNew.setId(uwNotionNewId);
			uwNotionNew.getId().setFlowId(uwNotionDto.getId().getFlowId());
			uwNotionNew.getId().setLineNo(lineNo + 1 + i);
			uwNotionNew.getId().setLogNo(uwNotionDto.getId().getLogNo());
			uwNotionNew.setHandleText(arrHandleText[i]);
			uwNotionNew.setFlag("1");
			col.add(uwNotionNew);
		}
		return col;
	}

	/**
	 * 插入所有的核保意見.
	 * 
	 * @param utNotionList
	 *            核保意見集合
	 * @throws Exception
	 *             異常
	 */
	public void insertAllIlog(Collection utNotionList) throws Exception {
		UwNotion uwNotionDto = null;
		QueryRule queryRule = QueryRule.getInstance();
		if (utNotionList.iterator().hasNext()) {
			uwNotionDto = (UwNotion) utNotionList.iterator().next();

			queryRule.addEqual("id.flowId", uwNotionDto.getId().getFlowId());
			queryRule.addEqual("id.logNo", uwNotionDto.getId().getLogNo());
			queryRule.addIsNull("Flag");

			uwNotionService.deleteList(uwNotionService.findByConditions(queryRule));

		}
		uwNotionService.insertAll((List) utNotionList);
	}

	/**
	 * 批量下發修改.
	 * 
	 * @param wfLogList
	 *            工作流日誌 list
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void submitBatchTask(Collection wfLogList) throws UserException, Exception {
		WfLog wfLogDto = null;
		int size = 0;
		// 车队核保提交整理
		for (Iterator i = wfLogList.iterator(); i.hasNext();) {
			size++;
			try {
				wfLogDto = (WfLog) i.next();
				this.submitTask(wfLogDto.getId().getFlowId(), wfLogDto.getModelNo(), wfLogDto.getNodeNo(), wfLogDto.getBusinessType(),
						wfLogDto.getBusinessNo(), wfLogDto.getFlowStatus(), "1", wfLogDto.getUserCode(), wfLogDto.getOperatorCode());
			} catch (UserException ue) {
				ue.printStackTrace();
				throw ue;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * 提交核保任務.
	 * 
	 * @param flowID
	 *            工作流號
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param flowStatus
	 *            工作流狀態
	 * @param flag
	 *            核保標誌
	 * @param userCode
	 *            用戶代碼
	 * @param opertorCode
	 *            操作員代碼
	 * @param currendNodeNo
	 *            當前節點號
	 * @param wfGradeDto
	 *            定級信息類
	 * @throws Exception
	 *             異常
	 */
	public void submitTask(String flowID, int modelNo, int nodeNo, String businessType, String businessNo, String flowStatus, String flag, String userCode,
			String opertorCode, int currendNodeNo, WfGradeVo wfGradeDto) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		DBManager dbManager = new DBManager();
		try {
			dbManager.open("undwrtDataSource");
			dbManager.beginTransaction();
			BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
			BLPrpTmain blPrpTmain = new BLPrpTmain();
			BLPrpCmain blPrpCmain = new BLPrpCmain();
			WfLog wfLogDto = new WfLog();
			String strRiskCode = "";
			String strBusinessNo = ""; // 商业险业务号
			String strBusinessNoCI = "";
			String strFlowIDCI = "";
			String strWhere = "";
			Collection<WfLog> wfLogList = new ArrayList<WfLog>();
			boolean blnResult = false;
			String quoteNo = "";
			QueryRule queryRule = QueryRule.getInstance();
			// 提交节点
			if ("T".equals(businessType)) {
				blPrpTmain.getData(businessNo);
				strRiskCode = blPrpTmain.getArr(0).getRiskCode();
				// 需求差异，0501险种变为A01。0507为B01
				if ("A01".equals(strRiskCode) || "0502".equals(strRiskCode) || "0503".equals(strRiskCode) || "0510".equals(strRiskCode)) {
					this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode, currendNodeNo,
							wfGradeDto);
					blPrpTmainSub.getData(businessNo);
					if (blPrpTmainSub.getSize() > 0 && "111".equals(blPrpTmainSub.getArr(0).getFlag())) {
						strBusinessNoCI = blPrpTmainSub.getArr(0).getMainPolicyNo();
						queryRule.addEqual("businessNo", strBusinessNoCI);
						wfLogList = wfLogService.findByQueryRuleList(queryRule);
						Iterator<WfLog> itwflog = wfLogList.iterator();
						if (itwflog.hasNext()) {
							wfLogDto = itwflog.next();
							strFlowIDCI = wfLogDto.getId().getFlowId();
							this.submitTask(dbManager, strFlowIDCI, modelNo, nodeNo, businessType, strBusinessNoCI, flowStatus, flag, userCode, opertorCode,
									currendNodeNo, wfGradeDto);
						}
					}

				} else if ("B01".equals(strRiskCode)) {
					strWhere = "mainpolicyno = '" + businessNo + "'";
					blPrpTmainSub.query(strWhere);
					if (blPrpTmainSub.getSize() > 0 && "111".equals(blPrpTmainSub.getArr(0).getFlag())) {
						// 解决0501已经核保通过0507还未核过的历史数据
						strBusinessNo = blPrpTmainSub.getArr(0).getProposalNo();
						strWhere = "proposalno = '" + strBusinessNo + "'";
						blPrpCmain.query(strWhere);
						if (blPrpCmain.getSize() > 0) {
							this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode,
									currendNodeNo, wfGradeDto);
						} else {
							throw new Exception(internal.getText("undwrt.service.commonDealSubmit.submitWarrantyFirst")
									+ blPrpTmainSub.getArr(0).getProposalNo());
						}
					} else {
						this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode, currendNodeNo,
								wfGradeDto);
					}
				} else {
					this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode, currendNodeNo,
							wfGradeDto);
				}
			} else {
				//mantis： CAR0387，處理人員：DP0706，需求單編號：CAR0387.車險關聯單報價單核保功能調整 START
				if ("B".equals(businessType)) {// 報價單
					PrpQmain prpQmainCheck = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
					if ("A01".equals(prpQmainCheck.getRiskCode())) {
						this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode, currendNodeNo,
								wfGradeDto);
						PrpQmainSub prpqmainsub = policyService.getPrpQmainSubByQuoteno(businessNo);// A01(proposalNo)透過PrpQmainSub尋找關聯B01(MainPolicyNo)
						if (prpqmainsub != null && prpqmainsub.getId() !=null && prpqmainsub.getId().getMainPolicyNo() != null
								&& "111".equals(prpqmainsub.getFlag())) {
							strBusinessNoCI = prpqmainsub.getId().getMainPolicyNo();//關聯的強制險單號
							queryRule.addEqual("businessNo", strBusinessNoCI);
							wfLogList = wfLogService.findByQueryRuleList(queryRule);
							Iterator<WfLog> itwflog = wfLogList.iterator();
							if (itwflog.hasNext()) {
								wfLogDto = itwflog.next();
								strFlowIDCI = wfLogDto.getId().getFlowId();
								this.submitTask(dbManager, strFlowIDCI, modelNo, nodeNo, businessType, strBusinessNoCI, flowStatus, flag, userCode, opertorCode,
										currendNodeNo, wfGradeDto);
							}
						}

					} else if ("B01".equals(prpQmainCheck.getRiskCode())) {
						PrpQmainSub prpqmainsub = policyService.getPrpQmainSubByQuoteno2(businessNo);// B01(MainPolicyNo)透過PrpQmainSub尋找關聯A01(proposalNo)
						if (prpqmainsub != null && prpqmainsub.getId() !=null && prpqmainsub.getId().getProposalNo() != null
								&& "111".equals(prpqmainsub.getFlag())) {
							// 確認A01是否審核通過
							String proposalnoA01 = prpqmainsub.getId().getProposalNo();
							PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(proposalnoA01, "");
							if("1".equals(prpQmain.getUnderWriteFlag()) || "3".equals(prpQmain.getUnderWriteFlag())) {
								//A01已通過，則B01 submitTask
								this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode,
										currendNodeNo, wfGradeDto);
							} else {
								//A01未通過，則顯示"請先提交關聯任意險報價單："
								throw new Exception(internal.getText("undwrt.service.commonDealSubmit.submitWarrantyFirst2")
										+ proposalnoA01);
							}
						} else {
							//單強
							this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode, currendNodeNo,
									wfGradeDto);
						}
					} else{
						this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode, currendNodeNo,
								wfGradeDto);
					}
					
				} else {
					this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode, currendNodeNo,
							wfGradeDto);
				}
				//mantis： CAR0387，處理人員：DP0706，需求單編號：CAR0387.車險關聯單報價單核保功能調整 END
				/*
				mantis： CAR0236，處理人員：Sam，需求單編號：CAR0236--- start
				核批通過時需回寫CARR_ECARD_TRANSE
				*/
				PrpPmain prpPmain = new PrpPmain();
				PrpPhead prpPheadtemp = new PrpPhead();
				prpPheadtemp = endorseService.getPrpPheadByEndorseNo(businessNo);
				if(prpPheadtemp != null){
					prpPmain = prpPheadtemp.getPrpPmains().get(0);	
					if("B01".equals(prpPmain.getRiskCode()) && !"130".equals(prpPheadtemp.getEndorType())){//130 內部佣金批改 不用送
						String endorType = prpPheadtemp.getEndorType();
						String endorseType ="";
						if("85".equals(endorType)){
//							要自己分金批還是文批
							String businessNoRemark = businessNo.substring(6,7);
							if("A".equals(businessNoRemark) || "G".equals(businessNoRemark)){
								//代表金批
								endorseType = "08";
							}else{
								endorseType = "AC";
							}
						}else if ("90".equals(endorType) || "01".equals(endorType)){
							endorseType = "AC";
						}else if ("40".equals(endorType)){
							endorseType = "05";
						}else if ("98".equals(endorType)){
							endorseType = "01";
						}else if ("21".equals(endorType)){
							endorseType = "02";
						}
						String sql = " INSERT INTO CARR_ECARD_TRANSE( POLICYNO , ENDORSENO , ENDORSETYPE , VALIDDATE , INSERTDATE ) " +
								" values( '"+prpPmain.getPolicyNo()+"' , '"+businessNo+"' , '"+endorseType+"' , " +
								" To_Date( '"+DateUtil.formatDate(prpPheadtemp.getValidDate() , "yyyy-MM-dd")+"' , 'yyyy-mm-dd') , Sysdate ) ";
						dbManager.executeUpdate(sql);
					}
				}
				/* mantis： CAR0236，處理人員：Sam，需求單編號：CAR0236 --- end */
			}
			// 投保单审核通过回写报价单状态为8
			queryRule.getQueryRuleList().clear();
			queryRule.getRuleList().clear();
			queryRule.addEqual("id.modelNo", modelNo);
			queryRule.addNotEqual("id.nodeNo", nodeNo);
			queryRule.addEqual("endFlag", "1");
			if ("T".equals(businessType) && (strRiskCode.startsWith("A") || strRiskCode.startsWith("B"))) {
				quoteNo = blPrpTmain.getArr(0).getQuoteNo();
				if (quoteNo.length() > 10) {
					blnResult = swfNodeService.checkEndflag(queryRule);// 是否审核通过节点
					if (blnResult) {
						this.submitTaskQta(quoteNo, userCode, "8");
					}
				}
			}

			// mantis：CAR0530，處理人員：DP0714，批次轉檔件，核保後須回寫intfprpjpayrefrec註記以利自動介接 -- start
			String sql2 = "update INTFPRPJPAYREFREC set ISRENEWLFLAG = '2' where certino = '" + businessNo + "' and certitype = '" + businessType + "'";
			dbManager.executeUpdate(sql2);
			// mantis：CAR0530，處理人員：DP0714，批次轉檔件，核保後須回寫intfprpjpayrefrec註記以利自動介接 -- end

			dbManager.commitTransaction();
		} catch (UserException e) {
			dbManager.rollbackTransaction();
			/*//modify MOUJIAXING start 20151212  回滾
			DBManager dbManager1 = new DBManager();
			dbManager1.open("undwrtDataSource");
			dbManager1.beginTransaction();
			try {
				if("T".equals(businessType)){
					String sql = "update prpTmain set UnderWriteFlag ='9', UnderWriteCode='',UnderWriteName='',UnderWriteEndDate=null,ProposalLevel='' where proposalno='"+businessNo+"' ";
					dbManager1.executeUpdate(sql);
				}else if("E".equals(businessType)){
					String sql = "update prpPhead set UnderWriteFlag ='9', UnderWriteCode='',UnderWriteName='',UnderWriteEndDate=null where endorseno='"+businessNo+"' ";
					dbManager1.executeUpdate(sql);
				}
				dbManager1.commitTransaction();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				dbManager1.rollbackTransaction();
			}finally {
				dbManager1.close();
			}*/
			e.printStackTrace();
			throw new RuntimeException(e.getErrorMessage());
		} catch (Exception e) {
			dbManager.rollbackTransaction();
			/*DBManager dbManager1 = new DBManager();
			dbManager1.open("undwrtDataSource");
			dbManager1.beginTransaction();
			try {
				if("T".equals(businessType)){
					String sql = "update prpTmain set UnderWriteFlag ='9', UnderWriteCode='',UnderWriteName='',UnderWriteEndDate=null,ProposalLevel='' where proposalno='"+businessNo+"' ";
					dbManager1.executeUpdate(sql);
				}else if("E".equals(businessType)){
					String sql = "update prpPhead set UnderWriteFlag ='9', UnderWriteCode='',UnderWriteName='',UnderWriteEndDate=null where endorseno='"+businessNo+"' ";
					dbManager1.executeUpdate(sql);
				}
				dbManager1.commitTransaction();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				dbManager1.rollbackTransaction();
			}finally {
				dbManager1.close();
			}*/
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (Error e) {
			dbManager.rollbackTransaction();
			/*DBManager dbManager1 = new DBManager();
			dbManager1.open("undwrtDataSource");
			dbManager1.beginTransaction();
			try {
				if("T".equals(businessType)){
					String sql = "update prpTmain set UnderWriteFlag ='9', UnderWriteCode='',UnderWriteName='',UnderWriteEndDate=null,ProposalLevel='' where proposalno='"+businessNo+"' ";
					dbManager1.executeUpdate(sql);
				}else if("E".equals(businessType)){
					String sql = "update prpPhead set UnderWriteFlag ='9', UnderWriteCode='',UnderWriteName='',UnderWriteEndDate=null where endorseno='"+businessNo+"' ";
					dbManager1.executeUpdate(sql);
				}
				dbManager1.commitTransaction();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				dbManager1.rollbackTransaction();
			}finally {
				dbManager1.close();
			}
			//modify MOUJIAXING end 20151212  回滾*/
			e.printStackTrace();

			// 将错误写入日志文件
			String strError = "Undwrt Error, Error Times:" + new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString()
					+ ", Error Location:BLWfLogFacade.submitTask" + "\r\n";// 要写明错误产后的位置Error
																			// Location
			strError += "Error Reason:" + e.getMessage(); // 加入错误原因
			strError += "===============================================" + "===============================================";
			Log.init("undwrtError.log", "undwrtError.log", true); // 每个系统采用不一样的文件名。以生成不同文件。文件将在domain下
			Log.println(strError);// 输入内容到日志中

			throw new RuntimeException(e.getMessage());// 抛出外层可以捕获的Exception异常。
		} finally {
			dbManager.close();
		}
	}

	/**
	 * 提交節點.
	 * 
	 * @param flowID
	 *            工作流號
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            提交節點號
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param flowStatus
	 *            流狀態
	 * @param flag
	 *            標誌
	 * @param userCode
	 *            用戶代碼
	 * @param opertorCode
	 *            操作員代碼
	 * @throws Exception
	 *             異常
	 */
	public void submitTask(String flowID, int modelNo, int nodeNo, String businessType, String businessNo, String flowStatus, String flag, String userCode,
			String opertorCode) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		DBManager dbManager = new DBManager();
		try {
			dbManager.open("undwrtDataSource");
			dbManager.beginTransaction();
			BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
			BLPrpTmain blPrpTmain = new BLPrpTmain();
			BLPrpCmain blPrpCmain = new BLPrpCmain();
			WfLog wfLog = new WfLog();
			String strRiskCode = "";
			String strBusinessNo = ""; // 商业险业务号
			String strBusinessNoCI = "";
			String strFlowIDCI = "";
			String strWhere = "";
			//add by liuyangsx1727 投保有加裝配備報價單下發修改問題  20161215 begin
			String sql = null;
			PrpQmainSub prpqmainSub = null;
			PrpQmain prpqmain = null;
			//add by liuyangsx1727 投保有加裝配備報價單下發修改問題  20161215 end
			Collection<WfLog> wfLogList = new ArrayList<WfLog>();
			QueryRule queryRule = QueryRule.getInstance();
			// 提交节点
			if ("T".equals(businessType)) {

				blPrpTmain.getData(businessNo);
				strRiskCode = blPrpTmain.getArr(0).getRiskCode();
				if ("A01".equals(strRiskCode) || "0502".equals(strRiskCode) || "0503".equals(strRiskCode) || "0510".equals(strRiskCode)) {
					this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode);
					blPrpTmainSub.getData(businessNo);
					if (blPrpTmainSub.getSize() > 0 && "111".equals(blPrpTmainSub.getArr(0).getFlag())) {
						strBusinessNoCI = blPrpTmainSub.getArr(0).getMainPolicyNo();
						queryRule.addEqual("businessNo", strBusinessNoCI);
						wfLogList = wfLogService.findByQueryRuleList(queryRule);
						Iterator<WfLog> itwflog = wfLogList.iterator();
						if (itwflog.hasNext()) {
							wfLog = itwflog.next();
							strFlowIDCI = wfLog.getId().getFlowId();
							this.submitTask(dbManager, strFlowIDCI, modelNo, nodeNo, businessType, strBusinessNoCI, flowStatus, flag, userCode, opertorCode);
						}
					}

				} else if ("B01".equals(strRiskCode)) {
					strWhere = "mainpolicyno = '" + businessNo + "'";
					blPrpTmainSub.query(strWhere);
					if (blPrpTmainSub.getSize() > 0 && "111".equals(blPrpTmainSub.getArr(0).getFlag())) {
						// 解决0501已经核保通过0507还未核过的历史数据
						strBusinessNo = blPrpTmainSub.getArr(0).getProposalNo();
						strWhere = "proposalno = '" + strBusinessNo + "'";
						blPrpCmain.query(strWhere);
						if (blPrpCmain.getSize() > 0) {
							this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode);
						} else {
							throw new RuntimeException(internal.getText("undwrt.service.commonDealSubmit.submitWarrantyFirst")
									+ blPrpTmainSub.getArr(0).getProposalNo() + "！");
						}
					} else {
						this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode);
					}
				} else {
					this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode);
				}
				//modify by liuyangsx1727 投保有加裝配備報價單下發修改問題  20161215 begin
			} else if("B".equals(businessType)){
				sql = "select * from prpqmain where proposalno='" + businessNo
						+ "'";
				List list = super.getSession().createSQLQuery(sql)
						.addEntity(PrpQmain.class).list();
				if (null != list && list.size() > 0) {
					prpqmain = (PrpQmain) list.get(0);
				}
				strRiskCode=prpqmain.getRiskCode();
				if ("A01".equals(strRiskCode)) {
					this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode);
					sql = "select * from prpqmainsub where proposalno='" + businessNo
							+ "'";
					List list2 = super.getSession().createSQLQuery(sql)
							.addEntity(PrpQmainSub.class).list();
					if (null != list2 && list2.size() > 0) {
						prpqmainSub = (PrpQmainSub) list2.get(0);
					}
					if (prpqmainSub!=null&& "111".equals(prpqmainSub.getFlag())) {
						strBusinessNoCI=prpqmainSub.getId().getMainPolicyNo();
					}
					queryRule.addEqual("businessNo", strBusinessNoCI);
					wfLogList = wfLogService.findByQueryRuleList(queryRule);
					Iterator<WfLog> itwflog = wfLogList.iterator();
					if (itwflog.hasNext()) {
						wfLog = itwflog.next();
						strFlowIDCI = wfLog.getId().getFlowId();
						this.submitTask(dbManager, strFlowIDCI, modelNo, nodeNo, businessType, strBusinessNoCI, flowStatus, flag, userCode, opertorCode);
					}
				} else if ("B01".equals(strRiskCode)) {
					sql = "select * from prpqmainsub where MAINPOLICYNO='" + businessNo
							+ "'";
					List list3 = super.getSession().createSQLQuery(sql)
							.addEntity(PrpQmainSub.class).list();
					if (null != list3 && list3.size() > 0) {
						prpqmainSub = (PrpQmainSub) list3.get(0);
						throw new RuntimeException(internal.getText("undwrt.service.commonDealSubmit.submitWarrantyFirst")
								+ prpqmainSub.getId().getProposalNo()+ "！");
					}else{
						this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode);
					}
				}else{
					this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode);
				}
				//modify by liuyangsx1727 投保有加裝配備報價單下發修改問題  20161215 end
			}else {
				this.submitTask(dbManager, flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode);
			}
			dbManager.commitTransaction();
		} catch (Exception e) {
			dbManager.rollbackTransaction();
			e.printStackTrace();
			throw e;
		} catch (Error e) {
			dbManager.rollbackTransaction();
			e.printStackTrace();

			// 将错误写入日志文件
			String strError = "Undwrt Error, Error Times:" + new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString()
					+ ", Error Location:BLWfLogFacade.submitTask" + "\r\n";// 要写明错误产后的位置Error
																			// Location
			strError += "Error Reason:" + e.getMessage(); // 加入错误原因
			strError += "===============================================" + "===============================================";
			Log.init("undwrtError.log", "undwrtError.log", true); // 每个系统采用不一样的文件名。以生成不同文件。文件将在domain下
			Log.println(strError);// 输入内容到日志中

			throw new Exception(e.getMessage());// 抛出外层可以捕获的Exception异常。
		} finally {
			dbManager.close();
		}
	}

	/**
	 * 提交任務處理.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param flowId
	 *            工作流號
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            提交節點號
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param flowStatus
	 *            流狀態
	 * @param flag
	 *            標誌：0表示从业务系统提交到双核，1表示双核系统内部提交
	 * @param userCode
	 *            用戶代碼
	 * @param opertorCode
	 *            操作員代碼
	 * @throws SQLException
	 *             SQL異常
	 * @throws Exception
	 *             異常
	 * @throws UserException
	 *             自定義異常
	 */
	public void submitTask(DBManager dbManager, String flowId, int modelNo, int nodeNo, String businessType, String businessNo, String flowStatus, String flag,
			String userCode, String opertorCode) throws SQLException, Exception, UserException {
		try {
			// 提交节点
			this.submit(dbManager, flowId, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode);
		} catch (Exception ee) {
			ee.printStackTrace();
			throw ee;
		}
	}

	/**
	 * 提交任務處理.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param flowId
	 *            工作流號
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            提交節點號
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param flowStatus
	 *            流狀態
	 * @param flag
	 *            標誌：0表示从业务系统提交到双核，1表示双核系统内部提交
	 * @param userCode
	 *            用戶代碼
	 * @param opertorCode
	 *            操作員代碼
	 * @throws SQLException
	 *             SQL異常
	 * @throws Exception
	 *             異常
	 * @throws UserException
	 *             自定義異常
	 * @param currendNodeNo
	 *            當前節點號
	 * @param wfGradeDto
	 *            定級信息
	 * @throws SQLException
	 *             SQL異常
	 * @throws Exception
	 *             異常
	 * @throws UserException
	 *             自定義異常
	 */
	public void submitTask(DBManager dbManager, String flowId, int modelNo, int nodeNo, String businessType, String businessNo, String flowStatus, String flag,
			String userCode, String opertorCode, int currendNodeNo, WfGradeVo wfGradeDto) throws SQLException, Exception, UserException {
		try {
			// 提交节点
			this.submit(dbManager, flowId, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode, currendNodeNo, wfGradeDto);
		} catch (Exception ee) {
			ee.printStackTrace();
			throw ee;
		}
	}

	/**
	 * 提交任務處理.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param flowId
	 *            工作流號
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            提交節點號
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param flowStatus
	 *            流狀態
	 * @param flag
	 *            標誌：0表示从业务系统提交到双核，1表示双核系统内部提交
	 * @param userCode
	 *            用戶代碼
	 * @param opertorCode
	 *            操作員代碼
	 * @throws SQLException
	 *             SQL異常
	 * @throws Exception
	 *             異常
	 * @throws UserException
	 *             自定義異常
	 * @param currendNodeNo
	 *            當前節點號
	 * @param wfGradeDto
	 *            定級信息
	 * @throws SQLException
	 *             SQL異常
	 * @throws Exception
	 *             異常
	 * @throws UserException
	 *             自定義異常
	 */
	public boolean submit(DBManager dbManager, String flowID, int modelNo, int nodeNo, String certiType, String businessNo, String flowStatus, String flag,
			String userCode, String operatorCode, int currendNodeNo, WfGradeVo wfGradeDto) throws UserException, SQLException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		boolean blnReturn = false;
		int intCount = 0;
		double chgPremium = 1;
		char chCertiType = certiType.charAt(0);

		DBPrpCmainCovernote dbPrpCmainCovernote = new DBPrpCmainCovernote(dbManager);

		PrpPmain prpPmain = new PrpPmain();
		PrpDuser prpDuser = new PrpDuser();

		BLPrpPmainCovernote blPrpPmainCovernote = new BLPrpPmainCovernote();
		WfLog wfLogOldDto = new WfLog();
		WfLog wfLogNewDto = new WfLog();
		String strUnderWriteCode = "";

		// 增加见费出单标志位;
		String jFeeFlag = "";

		DateTime underWriteDate = new DateTime(new DateTime().current().toString().substring(0, 10));
		String strBusinessSource = "prp"; // 业务数据来源:reins再保险/prp业务

		QueryRule queryRule = QueryRule.getInstance();

		queryRule.addEqual("id.flowId", flowID.trim());
		try {
			intCount = wfLogService.getCount(queryRule);

			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.flowId", flowID);
			queryRule.addEqual("id.logNo", intCount);
			wfLogOldDto = wfLogService.findByPrimaryKey(queryRule);

			// --1.生成新日志
			this.generate(dbManager, flowID, modelNo, nodeNo, flowStatus, operatorCode);

			intCount++;
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.flowId", flowID);
			queryRule.addEqual("id.logNo", intCount);
			wfLogNewDto = wfLogService.findByPrimaryKey(queryRule);

			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.modelNo", modelNo);
			queryRule.addNotEqual("id.nodeNo", nodeNo);
			queryRule.addEqual("endFlag", "1");

			// 判断是否为审核通过节点 true:是 false：否
			boolean blnResult = swfNodeService.checkEndflag(queryRule);

			// 定级信息处理,暂时不需要此处处理20130716
			// dealGrade(dbManager, flowID, modelNo, nodeNo, certiType,
			// businessNo, userCode, wfGradeDto);

			if (!blnResult) // --2.1.如果当前节点TO不是核保通过节点
			{
				this.nodeType = "0";

				// --2.1.1.判断当前节点TO是否为打回修改节点
				if (nodeNo == 1) {
					strUnderWriteCode = userCode;
					// 倒数第二个参数表示1:远程核保 0:非远程核保
					// 最后一个参数表示reins:再保险 prp:业务
					prpFeedBackService.echo(dbManager, chCertiType, businessNo, "2", strUnderWriteCode, underWriteDate, "1", strBusinessSource);
					/*
					mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
					新AML
					*/
					if("B".equals(chCertiType)){
						PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
						if(prpQmain != null){
							PrpQmain prpQmainCI = new PrpQmain();
							if("A01".equals(prpQmain.getRiskCode())){
								PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
								if(sub != null){
									prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
									if(prpQmainCI != null){
										prpFeedBackService.echo(dbManager, chCertiType, prpQmainCI.getProposalNo(), "2", strUnderWriteCode, underWriteDate, "1", strBusinessSource);
									}
								}
							}
						}
					}
					/* mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 --- end */
				} else {
					if (!wfLogNewDto.getNodeStatus().equals("0") && (wfLogNewDto.getId().getLogNo() == 2 || wfLogOldDto.getNodeNo() == 1)) {
						prpFeedBackService.echoSubmit(chCertiType, businessNo, "9", strBusinessSource);
					} else {
						// System.out.println("条件不成立");
					}
				}
			} else // --2.2. 如果当前的节点TO是核保通过节点
			{
				this.nodeType = "1";

				// 分保控制
				// 临分业务须经再保确认
				String strCondition = "";
				FeoEnquiry feoEnquiryDto = null;
				if (certiType.trim().equals("T")) {
					PrpTmain prpTmain = new PrpTmain();
					prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
					jFeeFlag = prpTmain.getJfeeFlag();

					strUnderWriteCode = prpTmain.getApproverCode();
					strCondition = "proposalNo = '" + businessNo + "'";

					feoEnquiryDto = blReinsService.getFeoEnquiryInfo(strCondition);

					String verifyFlag = "";// 确认状态
					if (feoEnquiryDto != null) {
						verifyFlag = feoEnquiryDto.getVerifyFlag();
						if (verifyFlag.equals("5") || verifyFlag.equals("3") || verifyFlag.equals("9")) {
							// System.out.println("该投保单已经再保确认通过");
						} else if (!verifyFlag.equals("")) {
							throw new Exception(internal.getText("undwrt.service.commonDealSubmit.cannotCheckPass"));
						}
					} else {
						// System.out.println("该投保单无需分保");
					}

					// 分入业务须经再保确认
					if (prpTmain.getBusinessflag().equals("1")) {
						PrpReinsVerify prpReinsVerifyDto = new PrpReinsVerify();
						prpReinsVerifyDto = prpReinsVerifyService.findByConditions(businessNo);
						if (prpReinsVerifyDto == null) {
							throw new Exception(internal.getText("undwrt.service.commonDealSubmit.donotSubmitConfirm"));
						} else {
							String ReinsState = prpReinsVerifyDto.getReinsState();
							if (!ReinsState.equals("1")) {
								throw new Exception(internal.getText("undwrt.service.commonDealSubmit.canotCheck"));
							}
						}
					}
				}
				if (certiType.trim().equals("P")) {
					PrpCmain prpCmain = policyService.getPrpCmainByProposalNo(businessNo);
					if (prpCmain != null) {
					} else {
						// PrpCmainCovernoteDto prpCmainCovernoteDto = new PrpCmainCovernoteDto();
						//prpCmainCovernoteDto = dbPrpCmainCovernote.findByPrimaryKey(businessNo);
					}
					strCondition = "policyNo = '" + businessNo + "'";
					feoEnquiryDto = blReinsService.getFeoEnquiryInfo(strCondition);

					String verifyFlag = "";// 确认状态
					if (feoEnquiryDto != null) {
						verifyFlag = feoEnquiryDto.getVerifyFlag();
						if (verifyFlag.equals("5") || verifyFlag.equals("3") || verifyFlag.equals("9")) {
							// System.out.println("该保单已经再保确认通过");
						} else if (!verifyFlag.equals("")) {
							throw new Exception(internal.getText("undwrt.service.commonDealSubmit.cannotCheckPass"));
						}
					} else {
						// System.out.println("该保单无需分保");
					}
				}
				if (certiType.trim().equals("E")) {
					blPrpPmainCovernote.getData(businessNo);
					if (blPrpPmainCovernote.getSize() == 0) {
						prpPmain = new PrpPmain();
						PrpPhead prpPheadtemp = new PrpPhead();
						prpPheadtemp = endorseService.getPrpPheadByEndorseNo(businessNo);
						prpPmain = prpPheadtemp.getPrpPmains().get(0);						
						jFeeFlag = prpPheadtemp.getJfeeFlag();
						//chgPremium = prpPmain.getChgPremium().doubleValue();
//						if (chgPremium == 0) {
//						    jFeeFlag = "0";
//					    }
						//modefied by zhangruofei 20150127 校验非收费出单和收费出单的批单中只有批减的批单也做非收费出单流程 
						if (!this.checkIsNeadPaid(prpPheadtemp)) {
							jFeeFlag = "0";
						}
						strUnderWriteCode = prpPmain.getApproverCode();
						strCondition = "endorseNo = '" + businessNo + "'";
						feoEnquiryDto = blReinsService.getFeoEnquiryInfo(strCondition);

						String verifyFlag = "";// 确认状态
						if (feoEnquiryDto != null) {
							verifyFlag = feoEnquiryDto.getVerifyFlag();
							if (verifyFlag.equals("5") || verifyFlag.equals("3") || verifyFlag.equals("9")) {
								// System.out.println("该批单已经再保确认通过");
							} else if (!verifyFlag.equals("")) {
								throw new Exception(internal.getText("undwrt.service.commonDealSubmit.cannotCheckPass"));
							}
						} else {
							// System.out.println("该批单无需分保");
						}
					}

					// 分入业务须经再保确认(批单)
					if (prpPmain.getBusinessFlag().equals("1")) {
						PrpReinsVerify prpReinsVerifyDto = new PrpReinsVerify();
						prpReinsVerifyDto = prpReinsVerifyService.findByConditions(businessNo);
						if (prpReinsVerifyDto == null) {
							throw new Exception(internal.getText("undwrt.service.commonDealSubmit.donotSubmitConfirm"));
						} else {
							String ReinsState = prpReinsVerifyDto.getReinsState();
							if (!ReinsState.equals("1"))
								throw new Exception(internal.getText("undwrt.service.commonDealSubmit.canotCheck"));
						}
					}
				}

				// flag: 0表示从业务系统提交到双核，1表示双核系统内部提交
				if (flag.equals("0")) {
					switch (chCertiType) {
					case 'T':
						PrpTmain prpTmain = new PrpTmain();
						prpTmain = policyService.getPrpTmainByPolicyNo(businessNo);
						strUnderWriteCode = prpTmain.getApproverCode();
						// 如果是自动核保是没有复核人的。所以取操作员就可以。
						if (strUnderWriteCode == null || strUnderWriteCode.equals("")) {
							strUnderWriteCode = prpTmain.getOperatorCode();
						}
						break;
					case 'B':
						PrpQmain prpQmain = new PrpQmain();
						prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
						strUnderWriteCode = prpQmain.getApproverCode();
						// 如果是自动核保是没有复核人的。所以取操作员就可以。
						if (strUnderWriteCode == null || strUnderWriteCode.equals("")) {
							strUnderWriteCode = prpQmain.getOperatorCode();
						}
						break;
					case 'P':
						PrpCmain prpCmain = new PrpCmain();
						prpCmain = policyService.getPrpCmainByPolicyNo(businessNo);
						strUnderWriteCode = prpCmain.getApproverCode();
						// 如果是自动核保是没有复核人的。所以取操作员就可以。
						if (strUnderWriteCode == null || strUnderWriteCode.equals("")) {
							strUnderWriteCode = prpCmain.getOperatorCode();
						}
						break;
					case 'E':
						PrpPhead prpPhead = new PrpPhead();
						prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
						if (prpPhead != null) {
							strUnderWriteCode = prpPhead.getApproverCode();
						} else {
							PrpCmainCovernoteDto prpCmainCovernoteDto = new PrpCmainCovernoteDto();
							prpCmainCovernoteDto = dbPrpCmainCovernote.findByPrimaryKey(businessNo);
							strUnderWriteCode = prpCmainCovernoteDto.getApproverCode();
						}

						// 如果是自动核保是没有复核人的。所以取操作员就可以。
						if (strUnderWriteCode == null || strUnderWriteCode.equals("")) {
							strUnderWriteCode = prpPhead.getOperatorCode();
						}
						break;
					default:
					}
				}

				if (flag.equals("1")) {
					strUnderWriteCode = userCode;

				}
				prpDuser = new PrpDuser();
				if (strUnderWriteCode == null || strUnderWriteCode.equals("")) {
					strUnderWriteCode = userCode;
				}

				prpDuser = prpDuserService.getUser(strUnderWriteCode);
				wfLogOldDto.setOperatorCode(strUnderWriteCode);
				wfLogOldDto.setOperatorName(prpDuser.getUserName());
				wfLogService.update(wfLogOldDto);

				if (jFeeFlag != null && jFeeFlag.equals("1")) {
					if (flag.equals("0")) {
						System.out.println("=====開始回寫相關數據====="+businessNo+"========"+chCertiType+"===============");
						prpFeedBackService.echoJF(dbManager, chCertiType, businessNo, "3", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
								currendNodeNo);
						/*
						mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
						新AML
						*/
						if("B".equals(chCertiType)){
							PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
							if(prpQmain != null){
								PrpQmain prpQmainCI = new PrpQmain();
								if("A01".equals(prpQmain.getRiskCode())){
									PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
									if(sub != null){
										prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
										if(prpQmainCI != null){
											prpFeedBackService.echoJF(dbManager, chCertiType, prpQmainCI.getProposalNo(), "3", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
													currendNodeNo);
										}
									}
								}
							}
						}
						/* mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 --- end */
						System.out.println("=====結束回寫相關數據====="+businessNo+"========"+chCertiType+"===============");
					}
					if (flag.equals("1")) {
						System.out.println("=====開始回寫相關數據====="+businessNo+"========"+chCertiType+"===============");
						prpFeedBackService.echoJF(dbManager, chCertiType, businessNo, "1", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
								currendNodeNo);
						/*
						mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
						新AML
						*/
						if("B".equals(chCertiType)){
							PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
							if(prpQmain != null){
								PrpQmain prpQmainCI = new PrpQmain();
								if("A01".equals(prpQmain.getRiskCode())){
									PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
									if(sub != null){
										prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
										if(prpQmainCI != null){
											prpFeedBackService.echoJF(dbManager, chCertiType, prpQmainCI.getProposalNo(), "1", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
													currendNodeNo);
										}
									}
								}
							}
						}
						/* mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 --- end */
						System.out.println("=====結束回寫相關數據====="+businessNo+"========"+chCertiType+"===============");
					}
					// modify by DHCH 20130613 注释回写车险平台（台湾项目不走大陆车险平台） begin
					// 向平台发送预审核数据
					// this.submitCIJF(dbManager, certiType, businessNo);
					// modify by DHCH 20130613 注释回写车险平台（台湾项目不走大陆车险平台） end
				} else {
					if (flag.equals("0")) {
						System.out.println("=====開始回寫相關數據====="+businessNo+"========"+chCertiType+"===============");
						prpFeedBackService.echo(dbManager, chCertiType, businessNo, "3", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
								currendNodeNo);
						/*
						mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
						新AML
						*/
						if("B".equals(chCertiType)){
							PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
							if(prpQmain != null){
								PrpQmain prpQmainCI = new PrpQmain();
								if("A01".equals(prpQmain.getRiskCode())){
									PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
									if(sub != null){
										prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
										if(prpQmainCI != null){
											prpFeedBackService.echo(dbManager, chCertiType, prpQmainCI.getProposalNo(), "3", strUnderWriteCode, underWriteDate, "0", strBusinessSource,currendNodeNo);
										}
									}
								}
							}
						}
						System.out.println("=====結束回寫相關數據====="+businessNo+"========"+chCertiType+"===============");
					}
					if (flag.equals("1")) {
						System.out.println("=====開始回寫相關數據====="+businessNo+"========"+chCertiType+"===============");
						prpFeedBackService.echo(dbManager, chCertiType, businessNo, "1", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
								currendNodeNo);
						if("B".equals(chCertiType)){
							PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
							if(prpQmain != null){
								PrpQmain prpQmainCI = new PrpQmain();
								if("A01".equals(prpQmain.getRiskCode())){
									PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
									if(sub != null){
										prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
										if(prpQmainCI != null){
											prpFeedBackService.echo(dbManager, chCertiType, prpQmainCI.getProposalNo(), "1", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
													currendNodeNo);
										}
									}
								}
							}
						}
						System.out.println("=====結束回寫相關數據====="+businessNo+"========"+chCertiType+"===============");
					}
					/* mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 --- end */
					blPrpPmainCovernote.getData(businessNo);
					if (blPrpPmainCovernote.getSize() == 0) {
						// this.submitCI(dbManager, certiType, businessNo);
						prpFeedBackService.echoMainSub(businessNo);
						if (flag.equals("0")) {
							this.status = "3";
						} else if (flag.equals("1")) {
							this.status = "1";
						}
					}
				}

				// 关闭工作流
				workFlowService.close(flowID);

				this.underWriteCode = strUnderWriteCode;
				this.underWriteDate = underWriteDate;
				this.certiType = chCertiType;
			}

			// 增加消息发送传递的参数中包括新节点的wflogDto和前一个节点的wflogDto对象和是否是结束节点的标示

			// 根据配置文件决定是否打开消息功能
			String msgRunFlag = com.sinosoft.sysframework.reference.AppConfig.get("sysconst.MSG_RUN_FLAG");
			// 根据特殊模板跳过
			String msgSkipModel = com.sinosoft.sysframework.reference.AppConfig.get("sysconst.MSG_SKIP_MODEL");
			msgSkipModel = msgSkipModel == null ? "24" : msgSkipModel;
			StringTokenizer tokenizer = new StringTokenizer(msgSkipModel);
			// 判断是否跳过消息发送的标志位
			boolean skipFlag = false;
			while (tokenizer.hasMoreTokens()) {
				if (tokenizer.nextToken(",").trim().equals(Integer.toString(wfLogOldDto.getModelNo()))) {
					skipFlag = true;
					break;
				}
			}
			int iMsgRunFlag = msgRunFlag == null ? 0 : Integer.parseInt(msgRunFlag);
			if (iMsgRunFlag == 1 && !skipFlag) {
				messageService.send(wfLogNewDto, wfLogOldDto);
			}

			this.underWriteCode = strUnderWriteCode;
			this.certiType = chCertiType;
		} catch (UserException ue) {
			throw ue;
		} catch (SQLException se) {
			throw se;
		} catch (Exception e) {
			throw e;
		}
		return blnReturn;
	}

	/**
	 * 任務提交.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param flowID
	 *            工作流號
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param flowStatus
	 *            流轉狀態標志 0:正常流轉 ':回退
	 * @param flag
	 *            操作標志(0:複核/修改後的提交,':核保核賠中的提交)
	 * @param userCode
	 *            用戶代碼
	 * @param operatorCode
	 *            操作員代碼
	 * @return 成功返回true，失敗返回false
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             SQL異常
	 * @throws Exception
	 *             異常
	 */
	public boolean submit(DBManager dbManager, String flowID, int modelNo, int nodeNo, String certiType, String businessNo, String flowStatus, String flag,
			String userCode, String operatorCode) throws UserException, SQLException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		boolean blnReturn = false;
		int intCount = 0;
		double chgPremium = 1;
		char chCertiType = certiType.charAt(0);
		DBPrpCmainCovernote dbPrpCmainCovernote = new DBPrpCmainCovernote(dbManager);
		DBPrpLcompensate dbPrpLcompensate = new DBPrpLcompensate(dbManager);
		DBPrpLprepay dbPrpLprepay = new DBPrpLprepay(dbManager);
		PrpPmain prpPmain = new PrpPmain();
		PrpDuser prpDuser = new PrpDuser();

		BLPrpPmainCovernote blPrpPmainCovernote = new BLPrpPmainCovernote();
		WfLog wfLogOldDto = new WfLog();
		WfLog wfLogNewDto = new WfLog();
		String strUnderWriteCode = "";

		// 核保通过回写业务表的ProposalLevel
		int intPassLevel = nodeNo;

		// 增加见费出单标志位;
		String jFeeFlag = "";

		DateTime underWriteDate = new DateTime(new DateTime().current().toString().substring(0, 10));
		String strBusinessSource = "prp"; // 业务数据来源:reins再保险/prp业务
		QueryRule queryRule = QueryRule.getInstance();
		try {
			queryRule.addEqual("id.flowId", flowID.trim());
			intCount = wfLogService.findByQueryRuleList(queryRule).size();

			queryRule.getQueryRuleList().clear();
			queryRule.getRuleList().clear();
			queryRule.addEqual("id.flowId", flowID);
			queryRule.addEqual("id.logNo", intCount);
			wfLogOldDto = wfLogService.findByPrimaryKey(queryRule);
			// 核保通过回写业务表的ProposalLevel
			if ("1".equals(wfLogOldDto.getResultCode())) {
				intPassLevel = Integer.parseInt(wfLogOldDto.getPassLevel());
			}

			// --1.生成新日志
			System.out.println("===========開始生成新日誌信息====================");
			this.generate(dbManager, flowID, modelNo, nodeNo, flowStatus, operatorCode);

			intCount++;
			queryRule.getQueryRuleList().clear();
			queryRule.getRuleList().clear();
			queryRule.addEqual("id.flowId", flowID);
			queryRule.addEqual("id.logNo", intCount);
			wfLogNewDto = wfLogService.findByPrimaryKey(queryRule);

			queryRule.getQueryRuleList().clear();
			queryRule.getRuleList().clear();
			queryRule.addEqual("id.modelNo", modelNo);
			queryRule.addNotEqual("id.nodeNo", nodeNo);
			queryRule.addEqual("endFlag", "1");
			boolean blnResult = swfNodeService.checkEndflag(queryRule);
			if (!blnResult) // --2.1.如果当前节点TO不是核保通过节点
			{
				this.nodeType = "0";

				// --2.1.1.判断当前节点TO是否为打回修改节点
				if (nodeNo == 1) {
					strUnderWriteCode = userCode;
					// 倒数第二个参数表示1:远程核保 0:非远程核保
					// 最后一个参数表示reins:再保险 prp:业务
					prpFeedBackService.echo(dbManager, chCertiType, businessNo, "2", strUnderWriteCode, underWriteDate, "1", strBusinessSource);
					/*
					mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
					新AML
					*/
					if("B".equals(chCertiType)){
						PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
						if(prpQmain != null){
							PrpQmain prpQmainCI = new PrpQmain();
							if("A01".equals(prpQmain.getRiskCode())){
								PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
								if(sub != null){
									prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
									//mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 新AML問題
									if(prpQmainCI != null){
										prpFeedBackService.echo(dbManager, chCertiType, prpQmainCI.getProposalNo(), "2", strUnderWriteCode, underWriteDate, "1", strBusinessSource);
									}
								}
							}
						}
					}
					/* mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 --- end */
				} else {
					if (!wfLogNewDto.getNodeStatus().equals("0") && (wfLogNewDto.getId().getLogNo() == 2 || wfLogOldDto.getNodeNo() == 1)) {
						prpFeedBackService.echoSubmit(chCertiType, businessNo, "9", strBusinessSource);
					} else {
						// System.out.println("条件不成立");
					}
				}
			} else // --2.2. 如果当前的节点TO是核保通过节点
			{
				this.nodeType = "1";

				// 临分业务须经再保确认
				String strCondition = "";
				FeoEnquiry feoEnquiryDto = null;
				if (certiType.trim().equals("T")) {
					PrpTmain prpTmain = new PrpTmain();
					prpTmain = policyService.getPrpTmainByProposalNo(businessNo);

					// 获得见费出单标志位;
					jFeeFlag = prpTmain.getJfeeFlag();
					if(null!=prpTmain.getOperateSite() && "B2B&B2C".equals(prpTmain.getOperateSite())) {
						jFeeFlag = "0";
					}
					strUnderWriteCode = prpTmain.getApproverCode();
					strCondition = "proposalNo = '" + businessNo + "'";

					feoEnquiryDto = blReinsService.getFeoEnquiryInfo(strCondition);

					String verifyFlag = "";// 确认状态
					if (feoEnquiryDto != null) {
						verifyFlag = feoEnquiryDto.getVerifyFlag();
						if (verifyFlag.equals("5") || verifyFlag.equals("3") || verifyFlag.equals("9")) {
							// System.out.println("该投保单已经再保确认通过");
						} else if (!verifyFlag.equals("")) {
							throw new Exception(internal.getText("undwrt.service.commonDealSubmit.cannotCheckPass"));
						}
					} else {
						// System.out.println("该投保单无需分保");
					}

					// 分入业务须经再保确认
					if (prpTmain.getBusinessflag().equals("1")) {
						PrpReinsVerify prpReinsVerifyDto = new PrpReinsVerify();
						prpReinsVerifyDto = prpReinsVerifyService.findByConditions(businessNo);
						if (prpReinsVerifyDto == null) {
							throw new Exception(internal.getText("undwrt.service.commonDealSubmit.donotSubmitConfirm"));
						} else {
							String ReinsState = prpReinsVerifyDto.getReinsState();
							if (!ReinsState.equals("1")) {
								throw new Exception(internal.getText("undwrt.service.commonDealSubmit.canotCheck"));
							}
						}
					}
				}
				if (certiType.trim().equals("P")) {
					strCondition = "policyNo = '" + businessNo + "'";
					feoEnquiryDto = blReinsService.getFeoEnquiryInfo(strCondition);

					String verifyFlag = "";// 确认状态
					if (feoEnquiryDto != null) {
						verifyFlag = feoEnquiryDto.getVerifyFlag();
						if (verifyFlag.equals("5") || verifyFlag.equals("3") || verifyFlag.equals("9")) {
							// System.out.println("该保单已经再保确认通过");
						} else if (!verifyFlag.equals("")) {
							throw new Exception(internal.getText("undwrt.service.commonDealSubmit.cannotCheckPass"));
						}
					} else {
						// System.out.println("该保单无需分保");
					}
				}
				if (certiType.trim().equals("E")) {
					blPrpPmainCovernote.getData(businessNo);
					if (blPrpPmainCovernote.getSize() == 0) {
						prpPmain = new PrpPmain();
						PrpPhead prpPheadDtotemp = new PrpPhead();
						prpPheadDtotemp = endorseService.getPrpPheadByEndorseNo(businessNo);
						prpPmain = endorseService.getPrpPheadByEndorseNo(businessNo).getPrpPmains().get(0);

						// 获得见费出单标志位;
						jFeeFlag = prpPheadDtotemp.getJfeeFlag();
						chgPremium = prpPmain.getChgPremium().doubleValue();
						if (chgPremium == 0) {
							jFeeFlag = "0";
						}
						strUnderWriteCode = prpPmain.getApproverCode();

						strCondition = "endorseNo = '" + businessNo + "'";
						feoEnquiryDto = blReinsService.getFeoEnquiryInfo(strCondition);

						String verifyFlag = "";// 确认状态
						if (feoEnquiryDto != null) {
							verifyFlag = feoEnquiryDto.getVerifyFlag();
							if (verifyFlag.equals("5") || verifyFlag.equals("3") || verifyFlag.equals("9")) {
								// System.out.println("该批单已经再保确认通过");
							} else if (!verifyFlag.equals("")) {
								throw new Exception(internal.getText("undwrt.service.commonDealSubmit.canotCheck"));
							}
						} else {
							// System.out.println("该批单无需分保");
						}
					}

					if (prpPmain.getBusinessFlag().equals("1")) {

						PrpReinsVerify prpReinsVerifyDto = new PrpReinsVerify();
						prpReinsVerifyDto = prpReinsVerifyService.findByConditions(businessNo);
						if (prpReinsVerifyDto == null) {
							throw new Exception(internal.getText("undwrt.service.commonDealSubmit.donotSubmitConfirm"));
						} else {
							String ReinsState = prpReinsVerifyDto.getReinsState();
							if (!ReinsState.equals("1"))
								throw new Exception(internal.getText("undwrt.service.commonDealSubmit.canotCheck"));
						}
					}
				}

				// flag: 0表示从业务系统提交到双核，1表示双核系统内部提交
				if (flag.equals("0")) {
					switch (chCertiType) {
					case 'T':
						PrpTmain prpTmain = new PrpTmain();
						prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
						strUnderWriteCode = prpTmain.getApproverCode();
						// 如果是自动核保是没有复核人的。所以取操作员就可以
						if (strUnderWriteCode == null || strUnderWriteCode.equals("")) {
							strUnderWriteCode = prpTmain.getOperatorCode();
						}
						break;
					case 'P':
						PrpCmain prpCmain = new PrpCmain();
						prpCmain = policyService.getPrpCmainByPolicyNo(businessNo);
						strUnderWriteCode = prpCmain.getApproverCode();
						// 如果是自动核保是没有复核人的。所以取操作员就可以
						if (strUnderWriteCode == null || strUnderWriteCode.equals("")) {
							strUnderWriteCode = prpCmain.getOperatorCode();
						}
						break;
					case 'E':
						PrpPhead prpPhead = new PrpPhead();
						prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
						if (prpPhead != null) {
							strUnderWriteCode = prpPhead.getApproverCode();
						} else {
							PrpCmainCovernoteDto prpCmainCovernoteDto = new PrpCmainCovernoteDto();
							prpCmainCovernoteDto = dbPrpCmainCovernote.findByPrimaryKey(businessNo);
							strUnderWriteCode = prpCmainCovernoteDto.getApproverCode();
						}

						// 如果是自动核保是没有复核人的。所以取操作员就可以。begin;
						if (strUnderWriteCode == null || strUnderWriteCode.equals("")) {
							strUnderWriteCode = prpPhead.getOperatorCode();
						}
						break;
					case 'C':
						PrpLcompensateDto prpLcompensateDto = new PrpLcompensateDto();
						prpLcompensateDto = dbPrpLcompensate.findByPrimaryKey(businessNo);
						strUnderWriteCode = prpLcompensateDto.getApproverCode();
						break;
					case 'Y':
						PrpLprepayDto prpLprepayDto = new PrpLprepayDto();
						prpLprepayDto = dbPrpLprepay.findByPrimaryKey(businessNo);
						strUnderWriteCode = prpLprepayDto.getApproverCode();
						break;
					default:
					}
				}
				if (flag.equals("1")) {
					strUnderWriteCode = userCode;
				}

				prpDuser = new PrpDuser();
				if (strUnderWriteCode == null || strUnderWriteCode.equals("")) {
					strUnderWriteCode = userCode;
				}
				prpDuser = prpDuserService.getUser(strUnderWriteCode);
				wfLogOldDto.setOperatorCode(strUnderWriteCode);
				wfLogOldDto.setOperatorName(prpDuser.getUserName());
				wfLogService.update(wfLogOldDto);

				SimpleDateFormat logFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (jFeeFlag != null && jFeeFlag.equals("1")) {
					if (flag.equals("0")) {
						loggerRenewal.error("開始時間："+logFormat.format(new Date())+"核保回寫業務入口"+businessNo);
						long begin= System.currentTimeMillis();
						prpFeedBackService.echoJF(dbManager, chCertiType, businessNo, "3", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
								intPassLevel);
						/*
						mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
						新AML
						*/
						if("B".equals(chCertiType)){
							PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
							if(prpQmain != null){
								PrpQmain prpQmainCI = new PrpQmain();
								if("A01".equals(prpQmain.getRiskCode())){
									PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
									if(sub != null){
										prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
										if(prpQmainCI != null){
											prpFeedBackService.echoJF(dbManager, chCertiType, prpQmainCI.getProposalNo(), "3", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
													intPassLevel);
										}
									}
								}
							}
						}
						/* mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 --- end */
						long end = System.currentTimeMillis();
						loggerRenewal.error("結束時間："+logFormat.format(new Date())+"核保回寫業務入口"+businessNo);
						loggerRenewal.error("核保回寫業務入口所用時間差:----------"+(begin-end));
					}
					if (flag.equals("1")) {
						prpFeedBackService.echoJF(dbManager, chCertiType, businessNo, "1", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
								intPassLevel);
					}
					// modify by DHCH 20130613 注释回写车险平台（台湾项目不走大陆车险平台） begin
					// 向平台发送预审核数据
					// this.submitCIJF(dbManager, certiType, businessNo);
					// modify by DHCH 20130613 注释回写车险平台（台湾项目不走大陆车险平台） end

				} else {
					if (flag.equals("0")) {
						System.out.println("=============開始回寫先關數據==="+businessNo+"=========="+chCertiType+"=======================");
						prpFeedBackService.echo(dbManager, chCertiType, businessNo, "3", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
								intPassLevel);
						/*
						mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
						新AML
						*/
						if("B".equals(chCertiType)){
							PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
							if(prpQmain != null){
								PrpQmain prpQmainCI = new PrpQmain();
								if("A01".equals(prpQmain.getRiskCode())){
									PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
									if(sub != null){
										prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
										if(prpQmainCI != null){
											prpFeedBackService.echo(dbManager, chCertiType, prpQmainCI.getProposalNo(), "3", strUnderWriteCode, underWriteDate, "0", strBusinessSource,intPassLevel);
										}
									}
								}
							}
						}
						/* mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 --- end */
						System.out.println("=============結束回寫先關數據==="+businessNo+"=========="+chCertiType+"=======================");
					}
					if (flag.equals("1")) {
						System.out.println("=============開始回寫先關數據==="+businessNo+"=========="+chCertiType+"=======================");
						prpFeedBackService.echo(dbManager, chCertiType, businessNo, "1", strUnderWriteCode, underWriteDate, "0", strBusinessSource,
								intPassLevel);
						/*
						mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
						新AML
						*/
						if("B".equals(chCertiType)){
							PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
							if(prpQmain != null){
								PrpQmain prpQmainCI = new PrpQmain();
								if("A01".equals(prpQmain.getRiskCode())){
									PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
									if(sub != null){
										prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
										if(prpQmainCI != null){
											prpFeedBackService.echo(dbManager, chCertiType, prpQmainCI.getProposalNo(), "3", strUnderWriteCode, underWriteDate, "0", strBusinessSource,intPassLevel);
										}
									}
								}
							}
						}
						/* mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 --- end */
						System.out.println("=============結束回寫先關數據==="+businessNo+"=========="+chCertiType+"=======================");
					}

					/*blPrpPmainCovernote.getData(businessNo);
					if (blPrpPmainCovernote.getSize() == 0) {*/
						// modify by DHCH 20130613 注释回写车险平台（台湾项目不走大陆车险平台） begin
						// this.submitCI(dbManager, certiType, businessNo);
						// modify by DHCH 20130613 注释回写车险平台（台湾项目不走大陆车险平台） end
						prpFeedBackService.echoMainSub(businessNo);
						if (flag.equals("0")) {
							this.status = "3";
						} else if (flag.equals("1")) {
							this.status = "1";
						}
					//}
				}

				// 关闭工作流
				workFlowService.close(flowID);

				this.underWriteCode = strUnderWriteCode;
				this.underWriteDate = underWriteDate;
				this.certiType = chCertiType;
			}

			// 增加消息发送传递的参数中包括新节点的wflogDto和前一个节点的wflogDto对象和是否是结束节点的标示
			// 根据配置文件决定是否打开消息功能
			String msgRunFlag = com.sinosoft.sysframework.reference.AppConfig.get("sysconst.MSG_RUN_FLAG");
			// 根据特殊模板跳过
			String msgSkipModel = com.sinosoft.sysframework.reference.AppConfig.get("sysconst.MSG_SKIP_MODEL");
			msgSkipModel = msgSkipModel == null ? "24" : msgSkipModel;
			StringTokenizer tokenizer = new StringTokenizer(msgSkipModel);
			// 判断是否跳过消息发送的标志位
			boolean skipFlag = false;
			while (tokenizer.hasMoreTokens()) {
				if (tokenizer.nextToken(",").trim().equals(Integer.toString(wfLogOldDto.getModelNo()))) {
					skipFlag = true;
					break;
				}
			}
			int iMsgRunFlag = msgRunFlag == null ? 0 : Integer.parseInt(msgRunFlag);
			if (iMsgRunFlag == 1 && !skipFlag) {
				messageService.send(wfLogNewDto, wfLogOldDto);
			}

			this.underWriteCode = strUnderWriteCode;
			this.certiType = chCertiType;
		} catch (UserException ue) {
			logger.info("拋出異常信息======================"+businessNo);
			logger.error(getTrace(ue));
			throw ue;
		} catch (SQLException se) {
			logger.info("拋出異常信息======================"+businessNo);
			logger.error(getTrace(se));
			throw se;
		} catch (Exception e) {
			logger.info("拋出異常信息======================"+businessNo);
			logger.error(getTrace(e));
			throw e;
		}
		return blnReturn;
	}

	/**
	 * 雙核預審核回寫業務強三平台結果方法.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @throws SQLException
	 *             SQL異常
	 * @throws Exception
	 *             異常
	 * @throws UserException
	 *             自定義異常
	 */
	public void submitCIJF(DBManager dbManager, String businessType, String businessNo) throws SQLException, Exception, UserException {
		InternationalizationUtil internal = new InternationalizationUtil();
		try {
			BLPrpTmain blPrpTmain = new BLPrpTmain();
			BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
			BLPrpPhead blPrpPhead = new BLPrpPhead();
			String strProposalnoCI = "";
			String strRiskCode = "";
			String strClassCode = "";
			String strComCode = "";
			if ("T".equals(businessType)) {
				blPrpTmain.getData(businessNo);
				blPrpTmainSub.getData(businessNo);
				strRiskCode = blPrpTmain.getArr(0).getRiskCode();
				strClassCode = blPrpTmain.getArr(0).getClassCode();
			} else if ("E".equals(businessType)) {
				blPrpPhead.getData(businessNo);
				strRiskCode = blPrpPhead.getArr(0).getRiskCode();
				strClassCode = blPrpPhead.getArr(0).getClassCode();
				strComCode = blPrpPhead.getArr(0).getComCode();
			}
			if ("B01".equals(strRiskCode)) {
				prpFeedBackService.echoCISubmitJF(dbManager, businessNo, businessType);
			} else {
				if (blPrpTmain.getSize() > 0) {
					// if (blPrpTmain.getArr(0).getUnderWriteFlag().equals("9"))
					// {
					// if ((blPrpTmainSub.getSize() > 0)
					// && ("111".equals(blPrpTmainSub.getArr(0)
					// .getFlag()))) {
					// strProposalnoCI = blPrpTmainSub.getArr(0)
					// .getMainPolicyNo();
					// System.err.println("联合出单交强险"+strProposalnoCI);
					// blPrpFeedBackAction.echoCISubmitJF(dbManager,
					// strProposalnoCI, businessType);
					// }
					// }
					// 添加上海商业险和平台的预确认，begin。(普通批改批增的确认也是走这里)
					strComCode = blPrpTmain.getArr(0).getComCode();
					System.err.println(internal.getText("undwrt.service.commonDealSubmit.submitAdvance"));
					if ("31".equals(strComCode.substring(0, 2)) && ("A".equals(strClassCode) || "B".equals(strClassCode))) {
						System.err.println(internal.getText("undwrt.service.commonDealSubmit.confirmAdvanceJudge") + businessNo);
						prpFeedBackService.echoCISubmitJF(dbManager, businessNo, businessType);
					}
					// add by mxy 20091028 begin TASK-1347 浙江商业险集中
					else if (new UtiPower().checkBIInsure(strComCode, strRiskCode) && ("A".equals(strClassCode) || "B".equals(strClassCode))) {
						System.err.println(internal.getText("undwrt.service.commonDealSubmit.confirmAdvanceJudge2") + businessNo);
						prpFeedBackService.echoCISubmitJF(dbManager, businessNo, businessType);
					}
					// add by mxy 20091028 end TASK-1347 浙江商业险集中
					// 添加上海商业险和平台的预确认，end。

					// 20091009 songshuo 北京商业险集中 begin
					System.err.println(internal.getText("undwrt.service.commonDealSubmit.bjConfirmAdvance"));
					if ("11".equals(strComCode.substring(0, 2)) && ("A".equals(strClassCode) || "B".equals(strClassCode)) && !"0503".equals(strRiskCode)) {
						System.err.println(internal.getText("undwrt.service.commonDealSubmit.confirmAdvanceJudge3") + businessNo);
						prpFeedBackService.echoCISubmitJF(dbManager, businessNo, businessType);
					}
					// 20091009 songshuo 北京商业险集中 end

				}
				// add by mxy 20091028 begin TASK-1347 浙江商业险集中
				else if (blPrpPhead.getSize() > 0) {
					if (new UtiPower().checkBIInsure(strComCode, strRiskCode) && ("A".equals(strClassCode) || "B".equals(strClassCode))
							&& !"0504".equals(strRiskCode) && !"0505".equals(strRiskCode)) {
						System.err.println(internal.getText("undwrt.service.commonDealSubmit.confirmAdvanceJudge4") + businessNo);
						prpFeedBackService.echoCISubmitJF(dbManager, businessNo, businessType);
					}
				}
				// add by mxy 20091028 end TASK-1347 浙江商业险集中
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			throw ee;
		}
	}

	/**
	 * 雙核預審核回寫業務強三平台結果方法..
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void submitCI(DBManager dbManager, String businessType, String businessNo) throws SQLException, Exception, UserException {
		try {
			// BLProposal blProposal = new BLProposal();
			// BLEndorse blEndorse = new BLEndorse();
			BLPrpTmain blPrpTmain = new BLPrpTmain();
			BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
			BLPrpPhead blPrpPhead = new BLPrpPhead();
			String strProposalnoCI = "";
			String strRiskCode = "";
			String strClassCode = "";
			if ("T".equals(businessType)) {
				blPrpTmain.getData(businessNo);
				blPrpTmainSub.getData(businessNo);
				strRiskCode = blPrpTmain.getArr(0).getRiskCode();
				strClassCode = blPrpTmain.getArr(0).getClassCode();
			} else if ("E".equals(businessType)) {
				blPrpPhead.getData(businessNo);
				strRiskCode = blPrpPhead.getArr(0).getRiskCode();
				strClassCode = blPrpPhead.getArr(0).getClassCode();
			}
			if ("B01".equals(strRiskCode)) {
				prpFeedBackService.echoCISubmit(dbManager, businessNo, businessType);
			} else {
				System.err.println("businessNo=asdasdsas====");
				if ("E".equals(businessType) && blPrpPhead.getSize() > 0 && "31".equals(blPrpPhead.getArr(0).getComCode().substring(0, 2))
						&& ("A".equals(strClassCode) || "B".equals(strClassCode))) {
					System.err.println("businessNo=====" + businessNo);
					prpFeedBackService.echoCISubmit(dbManager, businessNo, businessType);

				} else if ("E".equals(businessType) && blPrpPhead.getSize() > 0 && "11".equals(blPrpPhead.getArr(0).getComCode().substring(0, 2))
						&& ("A".equals(strClassCode) || "B".equals(strClassCode))) {
					System.err.println("businessNo  20091015 songshuo =====" + businessNo);
					prpFeedBackService.echoCISubmit(dbManager, businessNo, businessType);
				}
				// add by mxy 20091105 begin TASK-1347 浙江商业险集中
				else if ("E".equals(businessType) && blPrpPhead.getSize() > 0
						&& new UtiPower().checkBIInsure(blPrpPhead.getArr(0).getComCode(), blPrpPhead.getArr(0).getRiskCode())
						&& ("A".equals(strClassCode) || "B".equals(strClassCode))) {
					prpFeedBackService.echoCISubmit(dbManager, businessNo, businessType);
				}
				// add by mxy 20091105 end TASK-1347 浙江商业险集中

			}

		} catch (Exception ee) {
			ee.printStackTrace();
			throw ee;
		}
	}

	/**
	 * 生成工作流中一個節點.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param iFlowID
	 *            工作流號
	 * @param iModelNo
	 *            模板號
	 * @param iNodeNo
	 *            節點號
	 * @param iFlowStatus
	 *            流轉狀態
	 * @param iHandleCode
	 *            經辦人代碼
	 * @return 成功返回true，失敗返回false
	 * @throws SQLException
	 *             SQL異常
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	private boolean generate(DBManager dbManager, String iFlowID, int iModelNo, int iNodeNo, String iFlowStatus, String iHandleCode) throws SQLException,
			UserException, Exception {
		//add by xuhuiling 
		//预先获取当前的缓存
		String cacheKey = "";
		//add by xuhuiling 
		
		InternationalizationUtil internal = new InternationalizationUtil();
		boolean blnReturn = false;
		int intCount = 0;
		String strNowTime = "";
		WfLog wfLogCurrDto = new WfLog();
		WfLog wfLogNextDto = new WfLog();
		SwfNode wfNodeDto = new SwfNode();
		strNowTime = new DateTime().current().toString().substring(0, 19);

		PrpTmain prpTmain = null;
		PrpCmain prpCmain = null;
		PrpQmain prpQmain = null;
		PrpCmainCovernoteDto prpCmainCovernoteDto = null;
		PrpTitemCar prpTitemCar = null;
		PrpCitemCar prpCitemCar = null;
		PrpQitemCar prpQitemCar = null;
		String[] identifyTypeNumber = null;
		String strPolicyNo = "";

		BLUtiOperateLogFacade blUtiOperateLogFacade = new BLUtiOperateLogFacade();
		ChgDate chgDate = new ChgDate();

		String strUserCode = "";
		String strLogSystemCode = "";
		String strLogRiskCode = "";
		String strLogBusinessType = "";
		String strLogBusinessNo = "";
		int intLogLogNo = 0;
		String strLogIsJFeeFlag = "";
		String strLogIsAutoUnderWrite = "";
		String strLogIsILog = "";
		String strLogOperateType = "";
		String strLogOperateTime = "";
		String strLogComCode = "";
		String strLogMakeCom = "";
		String strLogOperatorCode = "";
		String strLogIP = "";
		String strSQL = "";
		String icomCode = "";
		String singleCode = "";
		String singleMember = null;
		boolean blnIfExist = false;
		int iCount = 0;
		QueryRule queryRule = QueryRule.getInstance();
		try {
			Collection<WfLog> wfLogList = new ArrayList<WfLog>();

			queryRule.addEqual("id.flowId", iFlowID);
			queryRule.addEqual("nodeStatus", "0");
			wfLogList = wfLogService.findByQueryRuleList(queryRule);
			if (wfLogList.size() > 0) {
				throw new Exception(internal.getText("undwrt.service.commonDealSubmit.donotOperateAgain"));
			}
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.flowId", iFlowID);
			intCount = wfLogService.getCount(queryRule);

			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.flowId", iFlowID);
			queryRule.addEqual("id.logNo", intCount);
			wfLogCurrDto = wfLogService.findByPrimaryKey(queryRule);
			//add by xuhuiling mantis 4906 begin
			//设置缓存的key
			/*
			mantis： OTH0038，處理人員：Sam，需求單編號：OTH0038--- start
			繳費虛擬碼調整
			*/
			cacheKey = wfLogCurrDto.getBusinessNo()+wfLogCurrDto.getBusinessType()+intCount;
			//判断当前的缓存是否存在这条数据
			Element element = policyCache.get(cacheKey);
			//如果缓存不存在这个数据，说明当前的数据是有效的，可以继续操作
			if(element==null){
				element = new Element(cacheKey,wfLogCurrDto);
				policyCache.put(element);
			}else{
				//否则，当前的单号已经被操作了
				String exceptionMessage = " ,單號"+wfLogCurrDto.getBusinessNo()+internal.getText("undwrt.service.commonDealSubmit.donnotSubmitAgain")+", ";
				System.out.println("exceptionMessage1:"+exceptionMessage);
				throw new Exception(exceptionMessage);//
			}
			/* mantis： OTH0038，處理人員：Sam，需求單編號：OTH0038 --- end */
			//add by xuhuiling mantis 4906 end
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.modelNo", iModelNo);
			queryRule.addEqual("id.nodeNo", iNodeNo);
			swfNodeService.findByPrimaryKey(queryRule);
			wfNodeDto = swfNodeService.findByPrimaryKey(queryRule);

			// 生成WFlog中的业务值时，从相应的业务表中取数 begin
			WfLogId id = new WfLogId();
			wfLogNextDto.setId(id);
			if (wfLogCurrDto.getBusinessType().equals("T")) {
				if ("A".equals(wfLogCurrDto.getClassCode()) || "B".equals(wfLogCurrDto.getClassCode())) {
					prpTitemCar = policyService.getPrpTmainByProposalNo(wfLogCurrDto.getBusinessNo()).getPrpTitemCars().get(0);
					wfLogNextDto.setLicenseNo(prpTitemCar.getLicenseNo());
				}
				identifyTypeNumber = this.getIdentifyTypeNumber(wfLogCurrDto.getRiskCategory(), wfLogCurrDto.getBusinessNo(), "T");
				wfLogNextDto.setIdentifyType(identifyTypeNumber[0]);
				wfLogNextDto.setIdentifyNumber(identifyTypeNumber[1]);
				prpTmain = policyService.getPrpTmainByProposalNo(wfLogCurrDto.getBusinessNo());
			} else if (wfLogCurrDto.getBusinessType().equals("B")) {
				if ("A".equals(wfLogCurrDto.getClassCode()) || "B".equals(wfLogCurrDto.getClassCode())) {
					prpQitemCar = policyService.getPrpQmainByProposalNo(wfLogCurrDto.getBusinessNo(), "quotation").getPrpQitemCars().get(0);
					wfLogNextDto.setLicenseNo(prpQitemCar.getLicenseNo());
				}
				identifyTypeNumber = this.getIdentifyTypeNumber(wfLogCurrDto.getRiskCategory(), wfLogCurrDto.getBusinessNo(), "B");
				wfLogNextDto.setIdentifyType(identifyTypeNumber[0]);
				wfLogNextDto.setIdentifyNumber(identifyTypeNumber[1]);
				prpQmain = policyService.getPrpQmainByProposalNo(wfLogCurrDto.getBusinessNo(), "quotation");
			} else if (wfLogCurrDto.getBusinessType().equals("P")) {
				if (wfLogCurrDto.getRiskCode().equals("9997") || wfLogCurrDto.getRiskCode().equals("9998") || wfLogCurrDto.getRiskCode().equals("9999")) {
					prpCmainCovernoteDto = new DBPrpCmainCovernote(dbManager).findByPrimaryKey(wfLogCurrDto.getBusinessNo());
				} else {
					prpCmain = policyService.getPrpCmainByPolicyNo(wfLogCurrDto.getBusinessNo());
				}
				if ("A".equals(wfLogCurrDto.getClassCode()) || "B".equals(wfLogCurrDto.getClassCode())) {
					prpCitemCar = policyService.getPrpCmainByPolicyNo(wfLogCurrDto.getBusinessNo()).getPrpCitemCars().get(0);
					wfLogNextDto.setLicenseNo(prpCitemCar.getLicenseNo());
				}
				wfLogNextDto.setRelateContractNo(wfLogService.getRelateContractNo(wfLogCurrDto.getRiskCategory(), wfLogCurrDto.getBusinessNo()));
				identifyTypeNumber = this.getIdentifyTypeNumber(wfLogCurrDto.getRiskCategory(), wfLogCurrDto.getBusinessNo(), "P");
				wfLogNextDto.setIdentifyType(identifyTypeNumber[0]);
				wfLogNextDto.setIdentifyNumber(identifyTypeNumber[1]);
			} else if (wfLogCurrDto.getBusinessType().equals("E")) {
				if (wfLogCurrDto.getRiskCode().equals("9997") || wfLogCurrDto.getRiskCode().equals("9998") || wfLogCurrDto.getRiskCode().equals("9999")) {
					strPolicyNo = new DBPrpPmainCovernote(dbManager).findByPrimaryKey(wfLogCurrDto.getBusinessNo()).getPolicyNo();
					prpCmainCovernoteDto = new DBPrpCmainCovernote(dbManager).findByPrimaryKey(strPolicyNo);
				} else {
					strPolicyNo = endorseService.getPrpPheadByEndorseNo(wfLogCurrDto.getBusinessNo()).getPrpPmains().get(0).getPolicyNo();
					prpCmain = policyService.getPrpCmainByPolicyNo(strPolicyNo);
				}
				if ("A".equals(wfLogCurrDto.getClassCode()) || "B".equals(wfLogCurrDto.getClassCode())) {
					prpCitemCar = policyService.getPrpCmainByPolicyNo(strPolicyNo).getPrpCitemCars().get(0);
					wfLogNextDto.setLicenseNo(prpCitemCar.getLicenseNo());
				}
				wfLogNextDto.setRelateContractNo(wfLogService.getRelateContractNo(wfLogCurrDto.getRiskCategory(), strPolicyNo));
				identifyTypeNumber = this.getIdentifyTypeNumber(wfLogCurrDto.getRiskCategory(), strPolicyNo, "P");
				wfLogNextDto.setIdentifyType(identifyTypeNumber[0]);
				wfLogNextDto.setIdentifyNumber(identifyTypeNumber[1]);
			}

			// 为生成新的节点做数据准备
			intCount += 1;
			wfLogNextDto.getId().setFlowId(iFlowID);
			wfLogNextDto.getId().setLogNo(intCount);
			wfLogNextDto.setModelNo(iModelNo);

			PrpDcompany prpDcompany = prpDcompanyService.findByPrimaryKey(wfLogCurrDto.getComCode());
			icomCode = wfLogCurrDto.getComCode();

			BLPrpDpreauditConfigAction blPrpDpreauditConfigAction = new BLPrpDpreauditConfigAction();
			while (!blnIfExist) {
				//delete by shiguojie 20170607 系统优化，减少无用代码 begin
			/*	String conditions = "comcode = '" + icomCode + "' and modelno = '" + iModelNo + "' and nodeno = '" + wfNodeDto.getId().getNodeNo()
						+ "' and validstatus ='1' and riskcode like '%" + wfLogCurrDto.getRiskCode() + "%' or comcode = '" + icomCode + "' and modelno = '"
						+ iModelNo + "' and nodeno = '" + wfNodeDto.getId().getNodeNo() + "' and validstatus ='1' and classcode like '%"
						+ wfLogCurrDto.getClassCode() + "%'";
				iCount = blPrpDpreauditConfigAction.getCount(dbManager, conditions);// 这是什么表？==SELECT
																					// *
																					// FROM
																					// PrpDpreauditConfig
*/				
			
				prpDcompany = prpDcompanyService.findByPrimaryKey(icomCode);
				// 判断是否没有查找到信息，若是没有查到信息。
//				if (iCount == 0) {
					// 若是comcode＝uppercomcode则不在循环
					if (prpDcompany.getComCode().equals(prpDcompany.getPrpDcompany().getComCode())) {
						blnIfExist = true;
					} else {
						icomCode = prpDcompany.getPrpDcompany().getComCode();
					}
//				} else {
//					blnIfExist = true;
//				}
			}

//			if (iCount > 0) {
//				wfLogNextDto.setNodeName(internal.getText("undwrt.service.commonDealSubmit.checkAdvancePass"));
//			} else {
				wfLogNextDto.setNodeName(wfNodeDto.getNodeName());
//			}
				//delete by shiguojie 20170607 系统优化，减少无用代码 end

			wfLogNextDto.setNodeNo(wfNodeDto.getId().getNodeNo());
			wfLogNextDto.setBusinessType(wfLogCurrDto.getBusinessType());
			wfLogNextDto.setBusinessNo(wfLogCurrDto.getBusinessNo());
			wfLogNextDto.setFlowInTime(strNowTime);
			wfLogNextDto.setTimeLimit(wfNodeDto.getTimeLimit());
			wfLogNextDto.setNodeStatus("1");
			wfLogNextDto.setFlowStatus(iFlowStatus);
			wfLogNextDto.setOperatorCode(iHandleCode);
			wfLogNextDto.setPackageId(wfLogCurrDto.getPackageId());
			wfLogNextDto.setClassCode(wfLogCurrDto.getClassCode());
			wfLogNextDto.setRiskCode(wfLogCurrDto.getRiskCode());
			wfLogNextDto.setHandlerCode(wfLogCurrDto.getHandlerCode());
			wfLogNextDto.setHandler1Code(wfLogCurrDto.getHandler1Code());
			wfLogNextDto.setComCode(wfLogCurrDto.getComCode());
			wfLogNextDto.setMakeCom(wfLogCurrDto.getMakeCom());
			wfLogNextDto.setRiskCategory(wfLogCurrDto.getRiskCategory());
			wfLogNextDto.setReinsStatus(wfLogCurrDto.getReinsStatus());
			wfLogNextDto.setPolicyNo(wfLogCurrDto.getPolicyNo());
			wfLogNextDto.setClaimNo(wfLogCurrDto.getClaimNo());
			// ILog的信息
			wfLogNextDto.setResultCode(wfLogCurrDto.getResultCode());
			if (wfLogNextDto.getClassCode().equals("A") || wfLogNextDto.getClassCode().equals("B")) {
				wfLogNextDto.setResultContent(wfLogCurrDto.getResultContent());
			} else {
				String strResultContent = "";
				Collection<WfLogExt> col = null;
				Iterator<WfLogExt> iterator = null;
				WfLogExt wfLogExtDto = new WfLogExt();
				WfLogExt wfLogExtCurrDto = null;

				queryRule.getRuleList().clear();
				queryRule.getQueryRuleList().clear();
				queryRule.addEqual("id.flowId", wfLogCurrDto.getId().getFlowId());
				queryRule.addEqual("id.logNo", wfLogCurrDto.getId().getLogNo());
				col = wfLogExtService.getWfLogExtList(queryRule);
				iterator = col.iterator();
				while (iterator.hasNext()) {
					wfLogExtCurrDto = iterator.next();
					if (wfLogExtCurrDto != null) {
						strResultContent = strResultContent + wfLogExtCurrDto.getResultContent();
					}
				}
				WfLogExtId wfLogExtId = new WfLogExtId();
				wfLogExtDto.setId(wfLogExtId);
				wfLogExtDto.getId().setFlowId(wfLogNextDto.getId().getFlowId());
				wfLogExtDto.getId().setLogNo(wfLogNextDto.getId().getLogNo());
				wfLogExtDto.setResultContent(strResultContent);
				if (!strResultContent.equals("") && strResultContent != null) {
					wfLogExtService.insertAll(this.ungroupIlog(wfLogExtDto));
				}
			}
			wfLogNextDto.setPassLevel(wfLogCurrDto.getPassLevel());
			// 生成wflog中的业务值时，从相应的业务表中取数 end

			// 当生成新节点时,业务数据从main表中提取 begin
			if (wfLogCurrDto.getBusinessType().equals("T")) {
				wfLogNextDto.setHandler1Code(prpTmain.getHandler1Code());
				wfLogNextDto.setHandlerCode(prpTmain.getHandlerCode());
				wfLogNextDto.setComCode(prpTmain.getComCode());
				wfLogNextDto.setMakeCom(prpTmain.getMakeCom());
				wfLogNextDto.setInsuredCode(prpTmain.getInsuredCode());
				wfLogNextDto.setInsuredName(prpTmain.getInsuredName());
				wfLogNextDto.setSumAmount(((BigDecimal) prpTmain.getSumAmount()).doubleValue());
				wfLogNextDto.setSumPremium(((BigDecimal) prpTmain.getSumPremium()).doubleValue());
				wfLogNextDto.setContractNo(prpTmain.getContractNo());
				singleCode = prpTmain.getOperatorCode();
			} else if (wfLogCurrDto.getBusinessType().equals("B")) {
				wfLogNextDto.setHandler1Code(prpQmain.getHandler1Code());
				wfLogNextDto.setHandlerCode(prpQmain.getHandlerCode());
				wfLogNextDto.setComCode(prpQmain.getComCode());
				wfLogNextDto.setMakeCom(prpQmain.getMakeCom());
				wfLogNextDto.setInsuredCode(prpQmain.getInsuredCode());
				wfLogNextDto.setInsuredName(prpQmain.getInsuredName());
				wfLogNextDto.setSumAmount(prpQmain.getSumAmount()==null?0:prpQmain.getSumAmount().doubleValue());
				wfLogNextDto.setSumPremium(prpQmain.getSumPremium()==null?0:prpQmain.getSumPremium().doubleValue());
				wfLogNextDto.setContractNo(prpQmain.getContractNo());
				singleCode = prpQmain.getOperatorCode();
			} else if (wfLogCurrDto.getBusinessType().equals("P")) {
				if (wfLogCurrDto.getRiskCode().equals("9997") || wfLogCurrDto.getRiskCode().equals("9998") || wfLogCurrDto.getRiskCode().equals("9999")) {
					wfLogNextDto.setHandler1Code(prpCmainCovernoteDto.getHandler1Code());
					wfLogNextDto.setHandlerCode(prpCmainCovernoteDto.getHandlerCode());
					wfLogNextDto.setComCode(prpCmainCovernoteDto.getComCode());
					wfLogNextDto.setMakeCom(prpCmainCovernoteDto.getMakeCom());
					wfLogNextDto.setInsuredCode(prpCmainCovernoteDto.getInsuredCode());
					wfLogNextDto.setInsuredName(prpCmainCovernoteDto.getInsuredName());
					wfLogNextDto.setSumAmount(prpCmainCovernoteDto.getSumAmount());
					wfLogNextDto.setSumPremium(prpCmainCovernoteDto.getSumPremium());
					wfLogNextDto.setContractNo(prpCmainCovernoteDto.getContractNo());
					singleCode = prpCmainCovernoteDto.getOperatorCode();
				} else {
					wfLogNextDto.setHandler1Code(prpCmain.getHandler1Code());
					wfLogNextDto.setHandlerCode(prpCmain.getHandlerCode());
					wfLogNextDto.setComCode(prpCmain.getComCode());
					wfLogNextDto.setMakeCom(prpCmain.getMakeCom());
					wfLogNextDto.setInsuredCode(prpCmain.getInsuredCode());
					wfLogNextDto.setInsuredName(prpCmain.getInsuredName());
					wfLogNextDto.setSumAmount(((BigDecimal) prpCmain.getSumAmount()).doubleValue());
					wfLogNextDto.setSumPremium(((BigDecimal) prpCmain.getSumPremium()).doubleValue());
					wfLogNextDto.setContractNo(prpCmain.getContractNo());
					singleCode = prpCmain.getOperatorCode();
				}
			} else if (wfLogCurrDto.getBusinessType().equals("E")) {
				if (wfLogCurrDto.getRiskCode().equals("9997") || wfLogCurrDto.getRiskCode().equals("9998") || wfLogCurrDto.getRiskCode().equals("9999")) {
					wfLogNextDto.setHandler1Code(prpCmainCovernoteDto.getHandler1Code());
					wfLogNextDto.setHandlerCode(prpCmainCovernoteDto.getHandlerCode());
					wfLogNextDto.setComCode(prpCmainCovernoteDto.getComCode());
					wfLogNextDto.setMakeCom(prpCmainCovernoteDto.getMakeCom());
					wfLogNextDto.setInsuredCode(prpCmainCovernoteDto.getInsuredCode());
					wfLogNextDto.setInsuredName(prpCmainCovernoteDto.getInsuredName());
					wfLogNextDto.setSumAmount(prpCmainCovernoteDto.getSumAmount());
					wfLogNextDto.setSumPremium(prpCmainCovernoteDto.getSumPremium());
					wfLogNextDto.setContractNo(prpCmainCovernoteDto.getContractNo());
					singleCode = prpCmainCovernoteDto.getOperatorCode();
				} else {
					wfLogNextDto.setHandler1Code(prpCmain.getHandler1Code());
					wfLogNextDto.setHandlerCode(prpCmain.getHandlerCode());
					wfLogNextDto.setComCode(prpCmain.getComCode());
					wfLogNextDto.setMakeCom(prpCmain.getMakeCom());
					wfLogNextDto.setInsuredCode(prpCmain.getInsuredCode());
					wfLogNextDto.setInsuredName(prpCmain.getInsuredName());
					wfLogNextDto.setSumAmount(((BigDecimal) prpCmain.getSumAmount()).doubleValue());
					wfLogNextDto.setSumPremium(((BigDecimal) prpCmain.getSumPremium()).doubleValue());
					wfLogNextDto.setContractNo(prpCmain.getContractNo());
					singleCode = prpCmain.getOperatorCode();
				}
			}
			// 当生成新节点时,业务数据从main表中提取 end

			// 出單員信息
			wfLogNextDto.setSingleCode(singleCode);
			PrpDuser prpDuser = new PrpDuser();
			prpDuser = prpDuserService.getUser(singleCode);
			if (prpDuser != null) {
				singleMember = prpDuser.getUserName();
			} else {
				singleMember = "";
			}
			wfLogNextDto.setSingleMember(singleMember);

			if (this.getLFlowID() != null && !this.getLFlowID().equals("")) {
				wfLogNextDto.setRelateFlowId(this.getLFlowID());
				wfLogNextDto.setRelateLogNo(this.getLLogNo());
			} else {
				wfLogNextDto.setRelateFlowId(wfLogCurrDto.getRelateFlowId());
				wfLogNextDto.setRelateLogNo(wfLogCurrDto.getRelateLogNo());
			}
			wfLogService.save(wfLogNextDto);

			// 核心业务提交到双核
			if (wfLogCurrDto.getNodeNo() == 1 && wfLogCurrDto.getId().getLogNo() > 1 && wfNodeDto.getEndFlag().equals("0")) {/*
				if (wfLogCurrDto.getBusinessType().equals("T") || wfLogCurrDto.getBusinessType().equals("P") || wfLogCurrDto.getBusinessType().equals("E")
						|| wfLogCurrDto.getBusinessType().equals("B")) {
					strLogSystemCode = "prpall";
					strLogRiskCode = wfLogCurrDto.getRiskCode();
					if (wfLogCurrDto.getBusinessType().equals("T")) {
						strUserCode = policyService.getPrpTmainByProposalNo(wfLogCurrDto.getBusinessNo()).getOperatorCode();
						strLogBusinessType = "T";
						strLogOperateType = "prpall.tb.submittime";
						strLogIsJFeeFlag = policyService.getPrpTmainByProposalNo(wfLogCurrDto.getBusinessNo()).getJfeeFlag();
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					} else if (wfLogCurrDto.getBusinessType().equals("B")) {
						strUserCode = policyService.getPrpQmainByProposalNo(wfLogCurrDto.getBusinessNo(), "quotation").getOperatorCode();
						strLogBusinessType = "B";
						strLogOperateType = "prpall.tb.submittime";
						strLogIsJFeeFlag = policyService.getPrpQmainByProposalNo(wfLogCurrDto.getBusinessNo(), "quotation").getJfeeFlag();
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					} else if (wfLogCurrDto.getBusinessType().equals("P")) {
						if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
							strUserCode = new DBPrpCmainCovernote(dbManager).findByPrimaryKey(wfLogCurrDto.getBusinessNo()).getOperatorCode();
						} else {
							strUserCode = policyService.getPrpCmainByPolicyNo(wfLogCurrDto.getBusinessNo()).getOperatorCode();
						}
						strLogBusinessType = "P";
						strLogOperateType = "prpall.cb.submittime";
						if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
							strLogIsJFeeFlag = "0";
						} else {
							strLogIsJFeeFlag = policyService.getPrpCmainByPolicyNo(wfLogCurrDto.getBusinessNo()).getJfeeFlag();
						}
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					} else if (wfLogCurrDto.getBusinessType().equals("E")) {
						if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
							strUserCode = new DBPrpPmainCovernote(dbManager).findByPrimaryKey(wfLogCurrDto.getBusinessNo()).getOperatorCode();
						} else {
							strUserCode = endorseService.getPrpPheadByEndorseNo(wfLogCurrDto.getBusinessNo()).getPrpPmains().get(0).getOperatorCode();
						}
						strLogBusinessType = "E";
						strLogOperateType = "prpall.pg.submittime";
						if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
							strLogIsJFeeFlag = "0";
						} else {
							strLogIsJFeeFlag = endorseService.getPrpPheadByEndorseNo(wfLogCurrDto.getBusinessNo()).getJfeeFlag();
						}
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					}
					strLogBusinessNo = wfLogCurrDto.getBusinessNo();
					strLogOperateTime = chgDate.getCurrentTime("yyyy-MM-dd HH:mm:ss");
					strLogComCode = wfLogCurrDto.getComCode();
					strLogMakeCom = wfLogCurrDto.getMakeCom();
					strLogOperatorCode = strUserCode;
					intLogLogNo = wfLogCurrDto.getId().getLogNo();
					if (isILog(strLogRiskCode, strLogComCode)) {
						strLogIsILog = "1";
					} else {
						strLogIsILog = "0";
					}
					// 再次提交双核肯定不是自动核保
					strLogIsAutoUnderWrite = "0";
					blUtiOperateLogFacade.save(strLogSystemCode, strLogRiskCode, strLogBusinessType, strLogBusinessNo, intLogLogNo, strLogIsJFeeFlag,
							strLogIsAutoUnderWrite, strLogIsILog, strLogOperateType, strLogOperateTime, strLogComCode, strLogMakeCom, strLogOperatorCode,
							strLogIP);
				}
			*/}
			// 下发修改
			if (wfLogCurrDto.getNodeNo() > wfLogNextDto.getNodeNo()) {
				if (wfLogCurrDto.getBusinessType().equals("T") || wfLogCurrDto.getBusinessType().equals("P") || wfLogCurrDto.getBusinessType().equals("E")
						|| wfLogCurrDto.getBusinessType().equals("B")) {
					strLogSystemCode = "undwrt";
					strLogRiskCode = wfLogCurrDto.getRiskCode();
					// 如果是商业险关联出单则保存操作人员（OperatorCode），在记录交强险时用该操作人员代码。
					if (strLogRiskCode.equals("A01") || strLogRiskCode.equals("0502") || strLogRiskCode.equals("0503") || strLogRiskCode.equals("0510")) {
						if (wfLogCurrDto.getBusinessType().equals("T")) {
							strSQL = "proposalno = '" + wfLogCurrDto.getBusinessNo() + "'";
							BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
							blPrpTmainSub.query(strSQL);
							if (blPrpTmainSub.getSize() > 0 && "11".equals(blPrpTmainSub.getArr(0).getFlag().substring(0, 2))) {
								this.LogOperatorCode = wfLogCurrDto.getOperatorCode();
								this.IsMainSub = true;
							}
						}
						if (wfLogCurrDto.getBusinessType().equals("B")) {
							PrpQmainSub PrpQmainSub = null;
							List list = policyService.getPrpQmainByProposalNo(wfLogCurrDto.getBusinessNo(), "quotation").getPrpQmainSubs();
							if (list.size() > 0) {
								PrpQmainSub = (PrpQmainSub) list.get(0);
								if ("11".equals(PrpQmainSub.getFlag().substring(0, 2))) {
									this.LogOperatorCode = wfLogCurrDto.getOperatorCode();
									this.IsMainSub = true;
								}
							}
						}
					} else {
						if (!strLogRiskCode.equals("B01")) {
							this.LogOperatorCode = "";
							this.IsMainSub = false;
						}
					}
					if (wfLogCurrDto.getBusinessType().equals("T")) {
						strLogBusinessType = "T";
						strLogOperateType = "undwrt.hebao.submitjunior";
						strLogIsJFeeFlag = policyService.getPrpTmainByProposalNo(wfLogCurrDto.getBusinessNo()).getJfeeFlag();
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					} else if (wfLogCurrDto.getBusinessType().equals("B")) {
						strLogBusinessType = "B";
						strLogOperateType = "undwrt.hebao.submitjunior";
						strLogIsJFeeFlag = policyService.getPrpQmainByProposalNo(wfLogCurrDto.getBusinessNo(), "quotation").getJfeeFlag();
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					} else if (wfLogCurrDto.getBusinessType().equals("P")) {
						strLogBusinessType = "P";
						strLogOperateType = "undwrt.hebao.submitjunior";
						if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
							strLogIsJFeeFlag = "0";
						} else {
							strLogIsJFeeFlag = policyService.getPrpCmainByPolicyNo(wfLogCurrDto.getBusinessNo()).getJfeeFlag();
						}
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					} else if (wfLogCurrDto.getBusinessType().equals("E")) {
						strLogBusinessType = "E";
						strLogOperateType = "undwrt.hebao.submitjunior";
						if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
							strLogIsJFeeFlag = "0";
						} else {
							strLogIsJFeeFlag = endorseService.getPrpPheadByEndorseNo(wfLogCurrDto.getBusinessNo()).getJfeeFlag();
						}
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					}
					strLogBusinessNo = wfLogCurrDto.getBusinessNo();
					strLogOperateTime = chgDate.getCurrentTime("yyyy-MM-dd HH:mm:ss");
					strLogComCode = wfLogCurrDto.getComCode();
					strLogMakeCom = wfLogCurrDto.getMakeCom();
					if (strLogRiskCode.equals("B01") && this.IsMainSub) {
						strLogOperatorCode = this.LogOperatorCode;
					} else {
						strLogOperatorCode = wfLogCurrDto.getOperatorCode();
					}
					intLogLogNo = wfLogCurrDto.getId().getLogNo();
					if (isILog(strLogRiskCode, strLogComCode)) {
						strLogIsILog = "1";
					} else {
						strLogIsILog = "0";
					}
					// 下发修改肯定不是自动核保
					strLogIsAutoUnderWrite = "0";
					blUtiOperateLogFacade.save(strLogSystemCode, strLogRiskCode, strLogBusinessType, strLogBusinessNo, intLogLogNo, strLogIsJFeeFlag,
							strLogIsAutoUnderWrite, strLogIsILog, strLogOperateType, strLogOperateTime, strLogComCode, strLogMakeCom, strLogOperatorCode,
							strLogIP);
				}
			}
			if (wfLogCurrDto.getNodeNo() < wfLogNextDto.getNodeNo() && wfLogCurrDto.getNodeNo() > 1 && wfNodeDto.getEndFlag().equals("0")) {// 提交上级
				if (wfLogCurrDto.getBusinessType().equals("T") || wfLogCurrDto.getBusinessType().equals("P") || wfLogCurrDto.getBusinessType().equals("E")
						|| wfLogCurrDto.getBusinessType().equals("B")) {
					strLogSystemCode = "undwrt";
					strLogRiskCode = wfLogCurrDto.getRiskCode();
					// 如果是商业险关联出单则保存操作人员（OperatorCode），在记录交强险时用该操作人员代码。
					if (strLogRiskCode.equals("A01") || strLogRiskCode.equals("0502") || strLogRiskCode.equals("0503") || strLogRiskCode.equals("0510")) {
						if (wfLogCurrDto.getBusinessType().equals("T")) {
							strSQL = "proposalno = '" + wfLogCurrDto.getBusinessNo() + "'";
							BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
							blPrpTmainSub.query(strSQL);
							if (blPrpTmainSub.getSize() > 0 && "11".equals(blPrpTmainSub.getArr(0).getFlag().substring(0, 2))) {
								this.LogOperatorCode = wfLogCurrDto.getOperatorCode();
								this.IsMainSub = true;
							}
						}
						if (wfLogCurrDto.getBusinessType().equals("B")) {
							PrpQmainSub PrpQmainSub = null;
							List list = policyService.getPrpQmainByProposalNo(wfLogCurrDto.getBusinessNo(), "quotation").getPrpQmainSubs();
							if (list.size() > 0) {
								PrpQmainSub = (PrpQmainSub) list.get(0);
								if ("11".equals(PrpQmainSub.getFlag().substring(0, 2))) {
									this.LogOperatorCode = wfLogCurrDto.getOperatorCode();
									this.IsMainSub = true;
								}
							}
						}
					} else {
						if (!strLogRiskCode.equals("B01")) {
							this.LogOperatorCode = "";
							this.IsMainSub = false;
						}
					}
					if (wfLogCurrDto.getBusinessType().equals("T")) {
						strLogBusinessType = "T";
						strLogOperateType = "undwrt.hebao.submitsuperior";
						strLogIsJFeeFlag = policyService.getPrpTmainByProposalNo(wfLogCurrDto.getBusinessNo()).getJfeeFlag();
						if (null == strLogIsJFeeFlag || "".equals(strLogIsJFeeFlag)) {
							strLogIsJFeeFlag = "0";
						}
					} else if (wfLogCurrDto.getBusinessType().equals("B")) {
						strLogBusinessType = "B";
						strLogOperateType = "undwrt.hebao.submitsuperior";
						strLogIsJFeeFlag = policyService.getPrpQmainByProposalNo(wfLogCurrDto.getBusinessNo(), "quotation").getJfeeFlag();
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					} else if (wfLogCurrDto.getBusinessType().equals("P")) {
						strLogBusinessType = "P";
						strLogOperateType = "undwrt.hebao.submitsuperior";
						if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
							strLogIsJFeeFlag = "0";
						} else {
							strLogIsJFeeFlag = policyService.getPrpCmainByPolicyNo(wfLogCurrDto.getBusinessNo()).getJfeeFlag();
						}
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					} else if (wfLogCurrDto.getBusinessType().equals("E")) {
						strLogBusinessType = "E";
						strLogOperateType = "undwrt.hebao.submitsuperior";
						if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
							strLogIsJFeeFlag = "0";
						} else {
							strLogIsJFeeFlag = endorseService.getPrpPheadByEndorseNo(wfLogCurrDto.getBusinessNo()).getJfeeFlag();
						}
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					}
					strLogBusinessNo = wfLogCurrDto.getBusinessNo();
					strLogOperateTime = chgDate.getCurrentTime("yyyy-MM-dd HH:mm:ss");
					strLogComCode = wfLogCurrDto.getComCode();
					strLogMakeCom = wfLogCurrDto.getMakeCom();
					if (strLogRiskCode.equals("B01") && this.IsMainSub) {
						strLogOperatorCode = this.LogOperatorCode;
					} else {
						strLogOperatorCode = wfLogCurrDto.getOperatorCode();
					}
					intLogLogNo = wfLogCurrDto.getId().getLogNo();
					if (isILog(strLogRiskCode, strLogComCode)) {
						strLogIsILog = "1";
					} else {
						strLogIsILog = "0";
					}
					// 提交上级肯定不是自动核保
					strLogIsAutoUnderWrite = "0";
					blUtiOperateLogFacade.save(strLogSystemCode, strLogRiskCode, strLogBusinessType, strLogBusinessNo, intLogLogNo, strLogIsJFeeFlag,
							strLogIsAutoUnderWrite, strLogIsILog, strLogOperateType, strLogOperateTime, strLogComCode, strLogMakeCom, strLogOperatorCode,
							strLogIP);
				}
			}

			wfLogCurrDto.setHandleTime(strNowTime);
			wfLogCurrDto.setNodeStatus("4");
			wfLogCurrDto.setSubmitTime(strNowTime);
			wfLogService.update(wfLogCurrDto);

			blnReturn = true;
		} catch (SQLException se) {
			throw se;
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		}
		return blnReturn;
	}

	/*
	mantis： OTH0139，處理人員：Sam，需求單編號：OTH0139--- start
	保單內容批改規則異動
	*/
	public boolean endorChangeBusiness(String businessType ,String businessNo) {
		if("E".equals(businessType)){//批單
			PrpPhead head = endorseService.getPrpPheadByEndorseNo(businessNo);
			if("85".equals(head.getEndorType()) && ",C,E,C1,F,M,".contains(","+head.getClassCode()+",") ){
				PrpCPmain cpmain = policyService.getPrpCPmainByPolicyNo(head.getPolicyNo());
				PrpCmain cmain = policyService.getPrpCmainByPolicyNo(head.getPolicyNo());
//				業務員及服務人員相關欄位如下
//				1.業務員：登錄證號prpCmain.handlerIdentifyNumber、業務員姓名prpCmain.handlerName
//				2.服務人員：服務人員編號prpCmain.handler1Code、服務人員姓名prpCmain.handler1Name
//				3.代理人/經紀人/壽險機構：prpCmain.agentCode V
//				4.單位：單位代號prpCmain.extraComCode、單位名稱prpCmain.extraComName
//				5.業務來源：prpCmain.businessNature
//				6.通路別：prpCmain.channelType
//				7.介紹人：介紹人登錄證號prpCmain.introducerID、介紹人姓名prpCmain.introducerName X
//				8.歸屬單位：prpCmain.comCode
				if(!UtilTools.getNonNullString(cpmain.getHandlerIdentifyNumber()).equals(UtilTools.getNonNullString(cmain.getHandlerIdentifyNumber())) ||//登錄證號prpCmain.handlerIdentifyNumber
						!UtilTools.getNonNullString(cpmain.getHandlerName()).equals(UtilTools.getNonNullString(cmain.getHandlerName())) ||//業務員姓名prpCmain.handlerName
						!UtilTools.getNonNullString(cpmain.getHandler1Code()).equals(UtilTools.getNonNullString(cmain.getHandler1Code())) ||//服務人員編號prpCmain.handler1Code
						!UtilTools.getNonNullString(cpmain.getHandler1Name()).equals(UtilTools.getNonNullString(cmain.getHandler1Name())) ||//服務人員姓名prpCmain.handler1Name
						!UtilTools.getNonNullString(cpmain.getAgentCode()).equals(UtilTools.getNonNullString(cmain.getAgentCode())) ||//agentCode 因為有可能null
						!UtilTools.getNonNullString(cpmain.getExtraComCode()).equals(UtilTools.getNonNullString(cmain.getExtraComCode())) ||//單位代號prpCmain.extraComCode 因為有可能null
						!UtilTools.getNonNullString(cpmain.getExtraComName()).equals(UtilTools.getNonNullString(cmain.getExtraComName())) ||//單位名稱prpCmain.extraComName 因為有可能null
						!UtilTools.getNonNullString(cpmain.getBusinessNature()).equals(UtilTools.getNonNullString(cmain.getBusinessNature())) ||//業務來源：prpCmain.businessNature
						!UtilTools.getNonNullString(cpmain.getChannelType()).equals(UtilTools.getNonNullString(cmain.getChannelType())) ||//通路別：prpCmain.channelType
						!UtilTools.getNonNullString(cpmain.getComCode()).equals(UtilTools.getNonNullString(cmain.getComCode())) ){//歸屬單位：prpCmain.comCode
					return true;
				}
			}
		}
		return false;
	}
	/* mantis： OTH0139，處理人員：Sam，需求單編號：OTH0139 --- end */
	
	/**
	 * 處理定級信息.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param flowID
	 *            工作流號
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param userCode
	 *            用戶代碼
	 * @param wfGradeDto
	 *            定級信息
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void dealGrade(DBManager dbManager, String flowID, int modelNo, int nodeNo, String certiType, String businessNo, String userCode,
			WfGradeVo wfGradeDto) throws UserException, Exception {
		String strUndwrtGradeClassCode = "";
		String strUndwrtGradeRiskCode = "";
		if (certiType.equals("T")) {
			PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			strUndwrtGradeClassCode = prpTmain.getClassCode();
			strUndwrtGradeRiskCode = prpTmain.getRiskCode();
		} else if (certiType.equals("P")) {
			BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
			blPrpCmainCovernote.getData(businessNo);
			PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(businessNo);
			if (blPrpCmainCovernote.getSize() > 0) {
				strUndwrtGradeClassCode = blPrpCmainCovernote.getArr(0).getClassCode();
				strUndwrtGradeRiskCode = blPrpCmainCovernote.getArr(0).getRiskCode();
			}
			if (prpCmain != null) {
				strUndwrtGradeClassCode = prpCmain.getClassCode();
				strUndwrtGradeRiskCode = prpCmain.getRiskCode();
			}
		} else if (certiType.equals("E")) {
			DBPrpPmainCovernote dbPrpPmainCovernote = new DBPrpPmainCovernote(dbManager);
			PrpPmainCovernoteDto prpPmainCovernoteDto = dbPrpPmainCovernote.findByPrimaryKey(businessNo);
			PrpPmain prpPmain = endorseService.getPrpPheadByEndorseNo(businessNo).getPrpPmains().get(0);
			if (prpPmainCovernoteDto != null) {
				strUndwrtGradeClassCode = prpPmainCovernoteDto.getClassCode();
				strUndwrtGradeRiskCode = prpPmainCovernoteDto.getRiskCode();
			}
			if (prpPmain != null) {
				strUndwrtGradeClassCode = prpPmain.getClassCode();
				strUndwrtGradeRiskCode = prpPmain.getRiskCode();
			}
		}
		if (!strUndwrtGradeClassCode.equals("A") && !strUndwrtGradeClassCode.equals("B")
				&& (!strUndwrtGradeRiskCode.equals("9997") && !strUndwrtGradeRiskCode.equals("9998") && !strUndwrtGradeRiskCode.equals("9999"))) {
			if (!wfGradeDto.getHistoryBusiness().equals("") && !wfGradeDto.getHistoryBusiness().equals("1")) {
				String strGradeRiskCode = "";// 定级险种代码
				String strClassCode = "";
				String strOperatorCode = "";// 操作员代码
				String strGradeCode = "";// 手工定级代码
				String strGradeValue = "";// 定级分值
				String strAgentRate = "";// 中介手续费率
				String strMaxUsableRate = "";// 最大可用费用率
				String strBrokerRate = "";// 经纪人佣金率
				String strOrgRate = "";// 营销组织利益率
				String strBreakevenRate = "";// 基准销售费用率
				String strExtRate1 = "";// 扩展值1
				String strExtRate2 = "";// 扩展值2
				String strExtRate3 = "";// 扩展值3

				strClassCode = wfGradeDto.getClassCode();
				strGradeRiskCode = wfGradeDto.getRiskCode();
				strOperatorCode = wfGradeDto.getOperatorCode();
				strGradeCode = wfGradeDto.getGradeCode();
				strGradeValue = "" + wfGradeDto.getGradeValue();
				strAgentRate = "" + wfGradeDto.getAgentRate();
				strMaxUsableRate = "" + wfGradeDto.getMaxUsableRate();
				strBrokerRate = "" + wfGradeDto.getBrokerRate();
				strOrgRate = "" + wfGradeDto.getOrgRate();
				strBreakevenRate = "" + wfGradeDto.getBreakevenRate();
				strExtRate1 = "" + wfGradeDto.getExtRate1();
				strExtRate2 = "" + wfGradeDto.getExtRate2();
				strExtRate3 = "" + wfGradeDto.getExtRate3();

				wfGradeService.saveWfGrade(flowID, modelNo, nodeNo, certiType, businessNo, userCode, strOperatorCode, strGradeCode, strGradeValue,
						strMaxUsableRate, strBrokerRate, strAgentRate, strOrgRate, strBreakevenRate, strExtRate1, strExtRate2, strExtRate3);
			}
		}
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
		if (riskCategory.trim().equals("E"))// 意健险
		{
			if (businessType.equals("T"))// 投保
			{
				PrpTinsured prpTinsured = new PrpTinsured();
				PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
				if(prpTmain.getPrpTinsureds() != null && prpTmain.getPrpTinsureds().size() > 0 ){
					for(PrpTinsured p : prpTmain.getPrpTinsureds()){
						if("2".equals(p.getInsuredFlag())){
							prpTinsured = p;
							identifyType = prpTinsured.getIdentifyType();
							identifyNumber = prpTinsured.getIdentifyNumber();
							break;
						}
					}
				}
			} else if (businessType.equals("P") || businessType.equals("C") || businessType.equals("Y") || businessType.equals("E"))// 承保
			{
				PrpCinsured prpCinsured = new PrpCinsured();
				PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(businessNo);
				if(prpCmain.getPrpCinsureds() != null && prpCmain.getPrpCinsureds().size() > 0 ){
					for(PrpCinsured p : prpCmain.getPrpCinsureds()){
						if("2".equals(p.getInsuredFlag())){
							prpCinsured = p;//要保人訊息
							identifyType = prpCinsured.getIdentifyType();
							identifyNumber = prpCinsured.getIdentifyNumber();
							break;
						}
					}
				}
			} else if (businessType.equals("B"))// 投保
			{
				PrpQinsured prpQinsured = new PrpQinsured();
				PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
				if(prpQmain.getPrpQinsureds() != null && prpQmain.getPrpQinsureds().size() > 0 ){
					for(PrpQinsured p : prpQmain.getPrpQinsureds()){
						if("2".equals(p.getInsuredFlag())){
							prpQinsured = p;
							identifyType = prpQinsured.getIdentifyType();
							identifyNumber = prpQinsured.getIdentifyNumber();
							break;
						}
					}
				}
			}
		}
		return new String[] { identifyType, identifyNumber };
	}

	/**
	 * 根據險種和機構判斷是否是否使用規則引擎.
	 * 
	 * @param iRiskCode
	 *            險種代碼
	 * @param iComCode
	 *            歸屬機構代碼
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
	 * 對複核後的任務進行處理.
	 * 
	 * @param iModelNo
	 *            模板號
	 * @param iCertiType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iFlag
	 *            是修改還是新啓動標志
	 * @param iNodeNo
	 *            節點號
	 * @param iOption
	 *            是出單員提交還是雙核內部提交
	 * @param iRiskCode
	 *            險種代碼
	 * @param iClassCode
	 *            險類代碼
	 * @param iComCode
	 *            機構代碼
	 * @param iMakeCom
	 *            出單機構
	 * @param iHandlerCode
	 *            經辦人代碼
	 * @param iHandler1Code
	 *            歸屬業務員代碼
	 * @param iUserCode
	 *            用戶代碼
	 * @param iContractNo
	 *            合約號
	 * @param iSingleCode
	 *            出單員代碼
	 * @param dbManager
	 *            數據管理對象
	 * @return 工作流日誌對象的工作流號
	 * @throws SQLException
	 *             SQL異常
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String dealFirstTrans(int iModelNo, String iCertiType, String iBusinessNo, String iFlag, int iNodeNo, String iOption, String iRiskCode,
			String iClassCode, String iComCode, String iMakeCom, String iHandlerCode, String iHandler1Code, String iUserCode, String iContractNo,
			String iSingleCode, DBManager dbManager) throws SQLException, UserException, Exception {
		String strFlowID = "";
		String arrILog[] = null;
		try {
			SimpleDateFormat logFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			loggerRenewal.error("開始時間："+logFormat.format(new Date())+"對複核後的任務進行處理"+iBusinessNo);
			long begin3= System.currentTimeMillis();
			strFlowID = this.dealFirstTrans(iModelNo, iCertiType, iBusinessNo, iFlag, iNodeNo, iOption, iRiskCode, iClassCode, iComCode, iMakeCom,
					iHandlerCode, iHandler1Code, iUserCode, iContractNo, dbManager, arrILog, iSingleCode);
			long end3 = System.currentTimeMillis();
			loggerRenewal.error("結束時間："+logFormat.format(new Date())+"對複核後的任務進行處理"+iBusinessNo);
			loggerRenewal.error("對複核後的任務進行處理所用時間差:----------"+(begin3-end3));
		} catch (Exception e) {
			throw e;
		} finally {
		}
		return strFlowID;
	}

	/**
	 * 對複核後的任務進行處理.
	 * 
	 * @param iModelNo
	 *            模板號
	 * @param iCertiType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iFlag
	 *            是修改還是新啓動標志
	 * @param iNodeNo
	 *            節點號
	 * @param iOption
	 *            是出單員提交還是雙核內部提交
	 * @param iRiskCode
	 *            險種代碼
	 * @param iClassCode
	 *            險類代碼
	 * @param iComCode
	 *            機構代碼
	 * @param iMakeCom
	 *            出單機構
	 * @param iHandlerCode
	 *            經辦人代碼
	 * @param iHandler1Code
	 *            歸屬業務員代碼
	 * @param iUserCode
	 *            用戶代碼
	 * @param iContractNo
	 *            合約號
	 * @param iSingleCode
	 *            出單員代碼
	 * @return 工作流日誌對象的工作流號
	 * @throws SQLException
	 *             SQL異常
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String dealFirstTrans(int iModelNo, String iCertiType, String iBusinessNo, String iFlag, int iNodeNo, String iOption, String iRiskCode,
			String iClassCode, String iComCode, String iMakeCom, String iHandlerCode, String iHandler1Code, String iUserCode, String iContractNo,
			DBManager dbManager, String[] arrILog, String iSingleCode) throws SQLException, UserException, Exception {
		WfLog wfLogDto = new WfLog();
		String strFlowID = "";
		SimpleDateFormat logFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			wfLogDto = this.dealFirst(dbManager, iModelNo, iCertiType, iBusinessNo, iFlag, iRiskCode, iClassCode, iComCode, iMakeCom, iUserCode, iHandlerCode,
					iHandler1Code, iContractNo, arrILog, iSingleCode);

			strFlowID = wfLogDto.getId().getFlowId();
			loggerRenewal.error("開始時間："+logFormat.format(new Date())+"對複核後的任務進行處理"+iBusinessNo);
			long begin3= System.currentTimeMillis();
			this.submit(dbManager, strFlowID, iModelNo, iNodeNo, iCertiType, iBusinessNo, "0", iOption, iUserCode, "");
			long end3 = System.currentTimeMillis();
			loggerRenewal.error("結束時間："+logFormat.format(new Date())+"任務提交"+iBusinessNo);
			loggerRenewal.error("任務提交所用時間差:----------"+(begin3-end3));
		} catch (Exception e) {
			logger.info("拋出異常信息======================"+iBusinessNo);
			logger.error(getTrace(e));
			throw e;
		} finally {
		}
		return strFlowID;
	}

	/**
	 * 對複核後的任務進行處理.
	 * 
	 * @param iModelNo
	 *            模板號
	 * @param iCertiType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iFlag
	 *            是修改還是新啓動標志
	 * @param iNodeNo
	 *            節點號
	 * @param iOption
	 *            是出單員提交還是雙核內部提交
	 * @param iRiskCode
	 *            險種代碼
	 * @param iClassCode
	 *            險類代碼
	 * @param iComCode
	 *            機構代碼
	 * @param iMakeCom
	 *            出單機構
	 * @param iHandlerCode
	 *            經辦人代碼
	 * @param iHandler1Code
	 *            歸屬業務員代碼
	 * @param iUserCode
	 *            用戶代碼
	 * @param iContractNo
	 *            合約號
	 * @param iSingleCode
	 *            出單員代碼
	 * @return 工作流日誌對象的工作流號
	 * @throws SQLException
	 *             SQL異常
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	private WfLog dealFirst(DBManager dbManager, int modelNo, String certiType, String businessNo, String flag, String riskCode, String classCode,
			String comCode, String makeCom, String userCode, String handlerCode, String handler1Code, String contractNo, String[] arrILog, String singleCode)
			throws SQLException, UserException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		WfLog wfLogDto = new WfLog();
		SwfNode wfNodeDto = new SwfNode();
		PrpDuser prpDuser = new PrpDuser();
		PrpDcompany prpDcompany = new PrpDcompany();
		Collection<WfLog> wfLogDtoList = new ArrayList<WfLog>();
		String strFlowID = "";
		String strWfPackageID = "";
		String userName = "";
		String insuredCode = "";
		String insuredName = "";
		String singleMember = "";
		String licenseNo = "";
		String policyNo = "";
		String riskCategory = "";
		String relateContractNo = null;
		String[] identifyTypeNumber = null;

		QueryRule queryRule = QueryRule.getInstance();
		try {
			// 生成工作流信息並返回工作流信息id
			strWfPackageID = wfPackageService.create(modelNo, certiType, businessNo, comCode);

			prpDuser = prpDuserService.findByPrimaryKey(userCode);
			userName = prpDuser.getUserName();
			prpDuser = prpDuserService.findByPrimaryKey(singleCode);
			if (prpDuser != null) {
				singleMember = prpDuser.getUserName();
			} else {
				singleMember = "";
			}

			riskCategory = getRiskCategoryByRiskCode(riskCode);

			if (flag.equals("N")) // 启动工作流
			{
				WfLogId wfLogId = new WfLogId();
				wfLogDto.setId(wfLogId);
				strFlowID = this.getSoleFlowID(businessNo);
				wfLogDto.getId().setFlowId(strFlowID);
				wfLogDto.setRiskCategory(riskCategory);
				wfLogDto.setPackageId(strWfPackageID);

				queryRule.addEqual("id.modelNo", modelNo);
				queryRule.addEqual("id.nodeNo", 1);
				wfNodeDto = swfNodeService.findByPrimaryKey(queryRule);

				wfLogDto.getId().setLogNo(1);
				wfLogDto.setModelNo(modelNo);
				wfLogDto.setNodeNo(1);
				wfLogDto.setNodeName(wfNodeDto.getNodeName());
				wfLogDto.setBusinessType(certiType);
				wfLogDto.setBusinessNo(businessNo);
				
				wfLogDto.setDeptCode(comCode);
				prpDcompany = prpDcompanyService.findByPrimaryKey(comCode);
				wfLogDto.setDeptName(prpDcompany.getComCName());
				
				wfLogDto.setOperatorCode(userCode);
				wfLogDto.setOperatorName(userName);
				wfLogDto.setSingleCode(singleCode);
				wfLogDto.setSingleMember(singleMember);
				wfLogDto.setFlowInTime(new DateTime().current().toString().substring(0, 19));
				wfLogDto.setHandleTime(new DateTime().current().toString().substring(0, 19));
				wfLogDto.setTimeLimit(wfNodeDto.getTimeLimit());
				wfLogDto.setNodeStatus("3");
				wfLogDto.setFlowStatus("0");

				if (contractNo != null && contractNo.length() > 0) {
					wfLogDto.setContractNo(contractNo);
				}
				// 以下信息为业务传送过来的信息
				wfLogDto.setMakeCom(makeCom);
				wfLogDto.setComCode(comCode);
				wfLogDto.setRiskCode(riskCode);
				wfLogDto.setClassCode(classCode);
				wfLogDto.setHandler1Code(handler1Code);
				wfLogDto.setHandlerCode(handlerCode);

				if (certiType.equals("T")) {
					PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
					insuredCode = prpTmain.getInsuredCode();
					insuredName = prpTmain.getInsuredName();
					// 针对车险插入被保人姓名，车牌号码
					if (classCode.equals("A") || classCode.equals("B")) {
						PrpTitemCar prpTitemCar = policyService.getPrpTmainByProposalNo(businessNo).getPrpTitemCars().get(0);
						licenseNo = prpTitemCar.getLicenseNo();
						wfLogDto.setLicenseNo(licenseNo);
					}
					wfLogDto.setInsuredCode(insuredCode);
					wfLogDto.setInsuredName(insuredName);
					identifyTypeNumber = this.getIdentifyTypeNumber(riskCategory, businessNo, "T");
					wfLogDto.setIdentifyType(identifyTypeNumber[0]);
					wfLogDto.setIdentifyNumber(identifyTypeNumber[1]);
					wfLogDto.setSumAmount(((BigDecimal) prpTmain.getSumAmount()).doubleValue());
					wfLogDto.setSumPremium(((BigDecimal) prpTmain.getSumPremium()).doubleValue());
					// modify by wangjun 20130410 去掉判断
					// if (!"05".equals(classCode)) {
					bLTDangerGetService.getTDangerInfo(businessNo);
					// }
				} else if (certiType.equals("P")) {
					if ("9999".equals(riskCode) || "9998".equals(riskCode) || "9997".equals(riskCode)) {
						BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
						blPrpCmainCovernote.getData(businessNo);
						if (blPrpCmainCovernote.getSize() > 0) {
							insuredCode = blPrpCmainCovernote.getArr(0).getInsuredCode();
							insuredName = blPrpCmainCovernote.getArr(0).getInsuredName();
							wfLogDto.setSumAmount(Double.parseDouble(blPrpCmainCovernote.getArr(0).getSumAmount()));
							wfLogDto.setSumPremium(Double.parseDouble(blPrpCmainCovernote.getArr(0).getSumPremium()));
						}
					} else {
						PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(businessNo);
						insuredCode = prpCmain.getInsuredCode();
						insuredName = prpCmain.getInsuredName();
						wfLogDto.setSumAmount(((BigDecimal) prpCmain.getSumAmount()).doubleValue());
						wfLogDto.setSumPremium(((BigDecimal) prpCmain.getSumPremium()).doubleValue());
					}
					if ("A".equals(classCode) || "B".equals(classCode)) {
						PrpCitemCar prpCitemCar = policyService.getPrpCmainByPolicyNo(businessNo).getPrpCitemCars().get(0);
						licenseNo = prpCitemCar.getLicenseNo();
						wfLogDto.setLicenseNo(licenseNo);
					}
					wfLogDto.setInsuredCode(insuredCode);
					wfLogDto.setInsuredName(insuredName);
					relateContractNo = wfLogService.getRelateContractNo(riskCategory, businessNo);
					wfLogDto.setRelateContractNo(relateContractNo);
					identifyTypeNumber = this.getIdentifyTypeNumber(riskCategory, businessNo, "P");
					wfLogDto.setIdentifyType(identifyTypeNumber[0]);
					wfLogDto.setIdentifyNumber(identifyTypeNumber[1]);
					if (!("9999".equals(riskCode) || "9998".equals(riskCode) || "9997".equals(riskCode) || "A".equals(classCode) || "B".equals(classCode))) {
						bLCDangerGetService.getCDangerInfo(businessNo);
					}
				} else if (certiType.equals("E")) {
					if ("9999".equals(riskCode) || "9998".equals(riskCode) || "9997".equals(riskCode)) {
						DBPrpPmainCovernote dbPrpPmainCovernote = new DBPrpPmainCovernote(dbManager);
						PrpPmainCovernoteDto prpPmainCovernoteDto = dbPrpPmainCovernote.findByPrimaryKey(businessNo);
						policyNo = prpPmainCovernoteDto.getPolicyNo();
						DBPrpCmainCovernote dbPrpCmainCovernote = new DBPrpCmainCovernote(dbManager);
						PrpCmainCovernoteDto prpCmainCovernoteDto = dbPrpCmainCovernote.findByPrimaryKey(policyNo);
						insuredCode = prpCmainCovernoteDto.getInsuredCode();
						insuredName = prpCmainCovernoteDto.getInsuredName();
						wfLogDto.setSumAmount(prpCmainCovernoteDto.getSumAmount());
						wfLogDto.setSumPremium(prpCmainCovernoteDto.getSumPremium());
					} else {
						PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
						PrpPmain prpPmain = prpPhead.getPrpPmains().get(0);
						policyNo = prpPmain.getPolicyNo();
						PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(policyNo);
						insuredCode = prpCmain.getInsuredCode();
						insuredName = prpCmain.getInsuredName();
						wfLogDto.setSumAmount(((BigDecimal) prpCmain.getSumAmount()).doubleValue());
						wfLogDto.setSumPremium(((BigDecimal) prpCmain.getSumPremium()).doubleValue());
					}
					if ("A".equals(classCode) || "B".equals(classCode)) {
						PrpCitemCar prpCitemCar = policyService.getPrpCmainByPolicyNo(policyNo).getPrpCitemCars().get(0);
						licenseNo = prpCitemCar.getLicenseNo();
						wfLogDto.setLicenseNo(licenseNo);
					}
					wfLogDto.setInsuredCode(insuredCode);
					wfLogDto.setInsuredName(insuredName);
					relateContractNo = wfLogService.getRelateContractNo(riskCategory, policyNo);
					wfLogDto.setRelateContractNo(relateContractNo);
					identifyTypeNumber = this.getIdentifyTypeNumber(riskCategory, policyNo, "P");
					wfLogDto.setIdentifyType(identifyTypeNumber[0]);
					wfLogDto.setIdentifyNumber(identifyTypeNumber[1]);
					if (!("9999".equals(riskCode) || "9998".equals(riskCode) || "9997".equals(riskCode))) {
						bLPDangerGetService.getPDangerInfo(businessNo, policyNo);
					}
				} else if (certiType.equals("B")) {
					PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
					insuredCode = prpQmain.getInsuredCode();
					insuredName = prpQmain.getInsuredName();
					// 针对车险插入被保人姓名，车牌号码
					if (classCode.equals("A") || classCode.equals("B")) {
						PrpQitemCar prpQitemCar = prpQmain.getPrpQitemCars().get(0);
						licenseNo = prpQitemCar.getLicenseNo();
						wfLogDto.setLicenseNo(licenseNo);
					}
					wfLogDto.setInsuredCode(insuredCode);
					wfLogDto.setInsuredName(insuredName);
					identifyTypeNumber = this.getIdentifyTypeNumber(riskCategory, businessNo, "B");
					wfLogDto.setIdentifyType(identifyTypeNumber[0]);
					wfLogDto.setIdentifyNumber(identifyTypeNumber[1]);
					wfLogDto.setSumAmount(prpQmain.getSumAmount()==null?0:prpQmain.getSumAmount().doubleValue());
					wfLogDto.setSumPremium(prpQmain.getSumPremium()==null?0:prpQmain.getSumPremium().doubleValue());
				}

				// 当arrILog不为null，并且长度是3的时候，说明arrILog是和规则引擎交互返回的信息
				if (arrILog != null) {
					if (arrILog.length == 3) {
						wfLogDto.setResultCode(arrILog[0]); // 返回的操作类型 0下发修改
															// 1自动核保 2人工核保
						if (classCode.equals("A") || classCode.equals("B")) {
							wfLogDto.setResultContent(arrILog[1]);// 发下和人工的原因
						} else {
							WfLogExt wfLogExt = new WfLogExt();
							WfLogExtId wfLogExtId = new WfLogExtId();
							wfLogExt.setId(wfLogExtId);
							wfLogExt.getId().setFlowId(wfLogDto.getId().getFlowId());
							wfLogExt.getId().setLogNo(wfLogDto.getId().getLogNo());
							wfLogExt.setResultContent(arrILog[1]);
							if (!wfLogExt.getResultContent().equals("") && wfLogExt.getResultContent() != null) {
								wfLogExtService.insertAll(this.ungroupIlog(wfLogExt));
							}
						}
						wfLogDto.setPassLevel(arrILog[2]); // 人工的时候能核保通过的级别
					}
				}
				
				/*
				mantis： OTH0038，處理人員：Sam，需求單編號：OTH0038--- start
				繳費虛擬碼調整
				*/
				String cacheKey = "";//缓存的主键
				//设置缓存的key
				cacheKey = wfLogDto.getBusinessNo()+wfLogDto.getBusinessType()+"0";
				//判断当前的缓存是否存在这条数据
				Element element = policyCache.get(cacheKey);
				//如果缓存不存在这个数据，说明当前的数据是有效的，可以继续操作
				if(element==null){
					element = new Element(cacheKey,wfLogDto);
					policyCache.put(element);
				}else{
					//否则，当前的单号已经被操作了
					String exceptionMessage = " ,"+internal.getText("undwrt.service.commonDealSubmit.donnotSubmitAgain")+", ";
					System.out.println("exceptionMessage2:"+exceptionMessage);
					throw new Exception(exceptionMessage);//
				}
				/* mantis： OTH0038，處理人員：Sam，需求單編號：OTH0038 --- end */
				
				wfLogService.save(wfLogDto);

				/*BLUtiOperateLogFacade blUtiOperateLogFacade = new BLUtiOperateLogFacade();
				ChgDate chgDate = new ChgDate();

				String strLogSystemCode = "";
				String strLogRiskCode = "";
				String strLogBusinessType = "";
				String strLogBusinessNo = "";
				int intLogLogNo = 0;
				String strLogIsJFeeFlag = "";
				String strLogIsAutoUnderWrite = "";
				String strLogIsILog = "";
				String strLogOperateType = "";
				String strLogOperateTime = "";
				String strLogComCode = "";
				String strLogMakeCom = "";
				String strLogOperatorCode = "";
				String strLogIP = "";

				if (certiType.equals("T") || certiType.equals("P") || certiType.equals("E") || certiType.equals("B")) {
					strLogSystemCode = "prpall";
					strLogRiskCode = wfLogDto.getRiskCode();
					if (certiType.equals("T")) {
						strLogBusinessType = "T";
						strLogOperateType = "prpall.tb.submittime";
						strLogIsJFeeFlag = policyService.getPrpTmainByProposalNo(wfLogDto.getBusinessNo()).getJfeeFlag();
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					} else if (certiType.equals("B")) {
						strLogBusinessType = "B";
						strLogOperateType = "prpall.tb.submittime";
						strLogIsJFeeFlag = policyService.getPrpQmainByProposalNo(wfLogDto.getBusinessNo(), "quotation").getJfeeFlag();
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					} else if (certiType.equals("P")) {
						strLogBusinessType = "P";
						strLogOperateType = "prpall.cb.submittime";
						if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
							strLogIsJFeeFlag = "0";
						} else {
							strLogIsJFeeFlag = policyService.getPrpCmainByPolicyNo(wfLogDto.getBusinessNo()).getJfeeFlag();
						}
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					} else if (certiType.equals("E")) {
						strLogBusinessType = "E";
						strLogOperateType = "prpall.pg.submittime";
						if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
							strLogIsJFeeFlag = "0";
						} else {
							strLogIsJFeeFlag = endorseService.getPrpPheadByEndorseNo(wfLogDto.getBusinessNo()).getJfeeFlag();
						}
						if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
					}
					strLogBusinessNo = wfLogDto.getBusinessNo();
					strLogOperateTime = chgDate.getCurrentTime("yyyy-MM-dd HH:mm:ss");
					strLogComCode = wfLogDto.getComCode();
					strLogMakeCom = wfLogDto.getMakeCom();
					strLogOperatorCode = wfLogDto.getOperatorCode();
					intLogLogNo = wfLogDto.getId().getLogNo();
					if (isILog(strLogRiskCode, strLogComCode)) {
						strLogIsILog = "1";
					} else {
						strLogIsILog = "0";
					}
					blUtiOperateLogFacade.save(strLogSystemCode, strLogRiskCode, strLogBusinessType, strLogBusinessNo, intLogLogNo, strLogIsJFeeFlag,
							strLogIsAutoUnderWrite, strLogIsILog, strLogOperateType, strLogOperateTime, strLogComCode, strLogMakeCom, strLogOperatorCode,
							strLogIP);
				}*/

				// 如果是出单员，则插入出单员意见
				if (wfLogDto.getNodeNo() == 1) {
					uwNotionService.insertUwNotionByMakeUser(wfLogDto, certiType);
				}

				// 下发修改的时候，下发原因要让出单员看到，所以还要保存在uwnotion里面
				if (arrILog != null) {
					if ("0".equals(arrILog[0])) {
						UwNotion uwNotion = new UwNotion();
						UwNotionId uwNotionId = new UwNotionId();
						uwNotion.setId(uwNotionId);
						uwNotion.getId().setFlowId(wfLogDto.getId().getFlowId());
						uwNotion.getId().setLogNo(wfLogDto.getId().getLogNo());
						uwNotion.setHandleText(arrILog[1]);
						if (!uwNotion.getHandleText().equals("") && uwNotion.getHandleText() != null) {
							this.insertAllIlog(this.ungroupIlog(uwNotion));
						}
					}
				}

				WfFlowMain wfFlowMainDto = new WfFlowMain();
				wfFlowMainDto.setFlowId(strFlowID);
				if (certiType.equals("T") || certiType.equals("P") || certiType.equals("B")) {
					wfFlowMainDto.setFlowName(internal.getText("undwrt.service.commonDealSubmit.underwriteWorkflow"));
				}
				if (certiType.equals("E")) {
					wfFlowMainDto.setFlowName(internal.getText("undwrt.service.commonDealSubmit.checkWorkflow"));
				}
				if (certiType.equals("C")) {
					wfFlowMainDto.setFlowName(internal.getText("undwrt.service.commonDealSubmit.compensateWorkflow"));
				}
				if (certiType.equals("Y")) {
					wfFlowMainDto.setFlowName(internal.getText("undwrt.service.commonDealSubmit.compensateWorkflow"));
				}
				wfFlowMainDto.setFlowStatus("1");
				wfFlowMainDto.setCreatDate(new DateTime().current().toString().substring(0, 19));
				wfFlowMainDto.setModelNo((Long.parseLong(modelNo + "")));
				wfFlowMainDto.setStoreFlag("0");// 设置转储标志，1/需要转储 2/已转储
				wfFlowMainService.insert(wfFlowMainDto);
			}

			if (flag.equals("U")) {
				// 修改业务重新生成危险单位
				if (certiType.equals("T")) {
					// if (!"05".equals(classCode)) {
					bLTDangerGetService.getTDangerInfo(businessNo);
					// }
				} else if (certiType.equals("P")) {
					if ("9999".equals(riskCode) || "9998".equals(riskCode) || "9997".equals(riskCode) || "A".equals(classCode) || "B".equals(classCode)) {

					} else {
						bLCDangerGetService.getCDangerInfo(businessNo);
					}
				} else if (certiType.equals("E")) {
					if ("9999".equals(riskCode) || "9998".equals(riskCode) || "9997".equals(riskCode)) {
					} else {
						PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
						PrpPmain prpPmain = prpPhead.getPrpPmains().get(0);
						policyNo = prpPmain.getPolicyNo();
						bLPDangerGetService.getPDangerInfo(businessNo, policyNo);
					}
				}

				queryRule.getRuleList().clear();
				queryRule.getQueryRuleList().clear();
				queryRule.addEqual("businessNo", businessNo);
				queryRule.addNotEqual("nodeStatus", "0");
				queryRule.addNotEqual("nodeStatus", "4");
				wfLogDtoList = wfLogService.findByQueryRuleList(queryRule);
				Iterator<WfLog> itwflog = wfLogDtoList.iterator();
				while (itwflog.hasNext()) {
					wfLogDto = itwflog.next();
					wfLogDto.setDeptCode(comCode);
					prpDcompany = prpDcompanyService.findByPrimaryKey(comCode);
					wfLogDto.setDeptName(prpDcompany.getComCName());
					wfLogDto.setHandlerCode(handlerCode);
					wfLogDto.setOperatorCode(userCode);
					wfLogDto.setOperatorName(userName);
					wfLogDto.setSingleCode(singleCode);
					wfLogDto.setSingleMember(singleMember);
					wfLogDto.setHandleTime(new DateTime().current().toString().substring(0, 19));
					wfLogDto.setNodeStatus("3");// 3暫存
					wfLogDto.setFlowStatus("0");
					wfLogDto.setPackageId(strWfPackageID);

					// 下发修改、业务修改被保险人，重新提交时，要重新获取被保险人
					if (certiType.equals("T")) {
						PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
						insuredCode = prpTmain.getInsuredCode();
						insuredName = prpTmain.getInsuredName();
					} else if (certiType.equals("B")) {
						PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
						insuredCode = prpQmain.getInsuredCode();
						insuredName = prpQmain.getInsuredName();
					} else if (certiType.equals("P")) {
						if ("9999".equals(riskCode) || "9998".equals(riskCode) || "9997".equals(riskCode)) {
							BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
							blPrpCmainCovernote.getData(businessNo);
							if (blPrpCmainCovernote.getSize() > 0) {
								insuredCode = blPrpCmainCovernote.getArr(0).getInsuredCode();
								insuredName = blPrpCmainCovernote.getArr(0).getInsuredName();
							}
						} else {
							PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(businessNo);
							insuredCode = prpCmain.getInsuredCode();
							insuredName = prpCmain.getInsuredName();
						}
					} else if (certiType.equals("E")) {
						if ("9999".equals(riskCode) || "9998".equals(riskCode) || "9997".equals(riskCode)) {
							DBPrpPmainCovernote dbPrpPmainCovernote = new DBPrpPmainCovernote(dbManager);
							PrpPmainCovernoteDto prpPmainCovernoteDto = dbPrpPmainCovernote.findByPrimaryKey(businessNo);
							policyNo = prpPmainCovernoteDto.getPolicyNo();
							DBPrpCmainCovernote dbPrpCmainCovernote = new DBPrpCmainCovernote(dbManager);
							PrpCmainCovernoteDto prpCmainCovernoteDto = dbPrpCmainCovernote.findByPrimaryKey(policyNo);
							insuredCode = prpCmainCovernoteDto.getInsuredCode();
							insuredName = prpCmainCovernoteDto.getInsuredName();
						} else {
							PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
							PrpPmain prpPmain = prpPhead.getPrpPmains().get(0);
							policyNo = prpPmain.getPolicyNo();
							PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(policyNo);
							insuredCode = prpCmain.getInsuredCode();
							insuredName = prpCmain.getInsuredName();
						}
					}
					wfLogDto.setInsuredCode(insuredCode);
					wfLogDto.setInsuredName(insuredName);

					if (contractNo != null && contractNo.length() > 0) {
						wfLogDto.setContractNo(contractNo);
					}
					// 当arrILog不为null，并且长度是3的时候，说明arrILog是和规则引擎交互返回的信息
					if (arrILog != null) {
						if (arrILog.length == 3) {
							wfLogDto.setResultCode(arrILog[0]); // 返回的操作类型 0下发修改
																// 1自动核保 2人工核保
							if (classCode.equals("A") || classCode.equals("B")) {
								wfLogDto.setResultContent(arrILog[1]);// 发下和人工的原因
							} else {
								WfLogExt wfLogExtDto = new WfLogExt();
								wfLogExtDto.getId().setFlowId(wfLogDto.getId().getFlowId());
								wfLogExtDto.getId().setLogNo(wfLogDto.getId().getLogNo());
								wfLogExtDto.setResultContent(arrILog[1]);
								if (!wfLogExtDto.getResultContent().equals("") && wfLogExtDto.getResultContent() != null) {
									wfLogExtService.insertAll(this.ungroupIlog(wfLogExtDto));
								}
							}
							wfLogDto.setPassLevel(arrILog[2]); // 人工的时候能核保通过的级别
						}
						wfLogService.update(wfLogDto);

						// 如果是出单员，则插入出单员意见
						if (wfLogDto.getNodeNo() == 1) {
							uwNotionService.insertUwNotionByMakeUser(wfLogDto, certiType);
						}
						if (arrILog != null) {
							if ("0".equals(arrILog[0])) {
								UwNotion uwNotionDto = new UwNotion();
								UwNotionId uwNotionId = new UwNotionId();
								uwNotionDto.setId(uwNotionId);
								uwNotionDto.getId().setFlowId(wfLogDto.getId().getFlowId());
								uwNotionDto.getId().setLogNo(wfLogDto.getId().getLogNo());
								uwNotionDto.setHandleText(arrILog[1]);
								if (!uwNotionDto.getHandleText().equals("") && uwNotionDto.getHandleText() != null) {
									this.insertAllIlog(this.ungroupIlog(uwNotionDto));
								}
							}
						}
					}
				}
			}
		} catch (SQLException se) {
			throw se;
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		}
		return wfLogDto;
	}

	/**
	 * 任務撤回.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param prpDuserDto
	 *            用戶信息類
	 * @return 成功返回true，失敗返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean retract(String businessNo, PrpDuserDto prpDuserDto) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		DBManager dbManager = new DBManager();
		boolean blnReturn = false;
		int maxLogNo = 0;
		int nextLogNo = 0;
		int modelNo = 0;
		int nextNodeNo = 0;
		String strFlowId = "";
		String conditions = "";
		String operatorCode = "";
		String flowStatus = "2"; // 针对主动回撤的特殊标志
		String certiType = "";
		String mainProposalNo = "";
		boolean blUnit = false;
		QueryRule queryRule = QueryRule.getInstance();
		try {
			dbManager.open("undwrtDataSource");
			dbManager.beginTransaction();
			BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
			PrpTmainSub prpTmainSub = new PrpTmainSub();
			Collection collection = new ArrayList();
			WfLog wfLogDto = null;
			String strCondition = "";
			strCondition = " FLAG ='111' AND MAINPOLICYNO = '" + businessNo + "'";
			blPrpTmainSub.query(strCondition);
			if (blPrpTmainSub.getSize() > 0) {
				blnReturn = false;
				throw new UserException(-98, -1149, internal.getText("undwrt.service.commonDealSubmit.withdrawContract"),
						internal.getText("undwrt.service.commonDealSubmit.withdrawRelated") + blPrpTmainSub.getArr(0).getProposalNo()
								+ internal.getText("undwrt.service.commonDealSubmit.insuranceList") + "," + "" + businessNo
								+ internal.getText("undwrt.service.commonDealSubmit.withdrawSynchro"));
			} else {
				operatorCode = prpDuserDto.getUserCode();
				queryRule.addEqual("operatorCode", operatorCode);
				queryRule.addEqual("businessNo", businessNo);
				queryRule.addEqual("nodeStatus", "4");
				collection = wfLogService.findByQueryRuleList(queryRule);
				Iterator itcondition = collection.iterator();
				if (itcondition.hasNext()) {
					while (itcondition.hasNext()) {
						wfLogDto = (WfLog) itcondition.next();
						if (wfLogDto.getId().getLogNo() > maxLogNo) {
							maxLogNo = wfLogDto.getId().getLogNo();
							nextNodeNo = wfLogDto.getNodeNo();
						}
						strFlowId = wfLogDto.getId().getFlowId();
						modelNo = wfLogDto.getModelNo();
						certiType = wfLogDto.getBusinessType();
					}
				} else {
					blnReturn = false;
					throw new UserException(-98, -1149, internal.getText("undwrt.service.commonDealSubmit.invalidBusinessNo"),
							internal.getText("undwrt.pages.undwrtDeal.certiNo") + businessNo + internal.getText("undwrt.service.inputValidBusinessNo"));
				}

				nextLogNo = maxLogNo + 1;
				queryRule.getRuleList().clear();
				queryRule.getQueryRuleList().clear();
				queryRule.addNotEqual("nodeNo", 1);
				queryRule.addEqual("id.logNo", nextLogNo);
				queryRule.addEqual("nodeStatus", "1");
				queryRule.addEqual("id.flowId", strFlowId);
				collection = wfLogService.findByQueryRuleList(queryRule);
				Iterator itcondition1 = collection.iterator();
				char chCertiType = certiType.charAt(0);
				if (itcondition1.hasNext()) {
					//modify by CSY 20171225 mantis:5203 批單提交核保後撤回,核批狀態出現"收費出單待繳費" start 
					//第一次提交核批过来禁止撤回
					if(maxLogNo ==1){
						blnReturn = false;
						throw new UserException(-98, -1149, internal.getText("undwrt.action.commonRetactTask.taskWithdrawFail"),
								internal.getText("undwrt.pages.undwrtDeal.certiNo") + businessNo
										+ internal.getText("undwrt.action.commonRetactTask.taskWithdrawFail"));
					}else{
						this.generate(dbManager, strFlowId, modelNo, nextNodeNo, flowStatus, operatorCode);
						if (nextNodeNo == 1) {
							prpFeedBackService.echo(dbManager, chCertiType, businessNo, "4", operatorCode, underWriteDate, "1", "");
							/*
							mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
							新AML
							*/
							if("B".equals(chCertiType)){
								PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
								if(prpQmain != null){
									PrpQmain prpQmainCI = new PrpQmain();
									if("A01".equals(prpQmain.getRiskCode())){
										PrpQmainSub sub = policyService.getPrpQmainSubByQuoteno(businessNo);
										if(sub != null){
											prpQmainCI = policyService.getPrpQmainByProposalNo(sub.getId().getMainPolicyNo(), "quotation");
											//mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 新AML問題
											if(prpQmainCI != null){
												prpFeedBackService.echo(dbManager, chCertiType, prpQmainCI.getProposalNo(), "4", operatorCode, underWriteDate, "1", "");
											}
										}
									}
								}
							}
							/* mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065 --- end */
						}
						blnReturn = true;
					}
					//modify by CSY 20171225 mantis:5203 批單提交核保後撤回,核批狀態出現"收費出單待繳費" end 
				} else {
					blnReturn = false;
					throw new UserException(-98, -1149, internal.getText("undwrt.action.commonRetactTask.taskWithdrawFail"),
							internal.getText("undwrt.pages.undwrtDeal.certiNo") + businessNo
									+ internal.getText("undwrt.action.commonRetactTask.taskWithdrawFail"));
				}

				if ("T".equals(certiType)) {
					String strSQL = "Select * From PrpTmainSub Where ProposalNo = '" + businessNo + "'";
					List list = super.getSession().createSQLQuery(strSQL).addEntity(PrpTmainSub.class).list();
					blPrpTmainSub.getData(businessNo);
					if (list.size() > 0) {
						prpTmainSub = (PrpTmainSub) list.get(0);
						if ("111".equals(prpTmainSub.getFlag())) {
							mainProposalNo = prpTmainSub.getId().getMainPolicyNo();
							blUnit = true;
						}
					}
				}
				if (blUnit) {
					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("operatorCode", operatorCode);
					queryRule.addEqual("businessNo", mainProposalNo);
					queryRule.addEqual("nodeStatus", "4");
					collection = wfLogService.findByQueryRuleList(queryRule);
					Iterator itcondition2 = collection.iterator();
					while (itcondition2.hasNext()) {
						wfLogDto = (WfLog) itcondition2.next();
						if (wfLogDto.getId().getLogNo() > maxLogNo) {
							maxLogNo = wfLogDto.getId().getLogNo();
							nextNodeNo = wfLogDto.getNodeNo();
						}
						strFlowId = wfLogDto.getId().getFlowId();
						modelNo = wfLogDto.getModelNo();
						certiType = wfLogDto.getBusinessType();
					}
					nextLogNo = maxLogNo + 1;
					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addNotEqual("nodeNo", 1);
					queryRule.addEqual("id.logNo", nextLogNo);
					queryRule.addEqual("nodeStatus", "1");
					queryRule.addEqual("id.flowId", strFlowId);
					collection = wfLogService.findByQueryRuleList(queryRule);
					Iterator itcondition3 = collection.iterator();
					if (itcondition3.hasNext()) {
						this.generate(dbManager, strFlowId, modelNo, nextNodeNo, flowStatus, operatorCode);
						if (nextNodeNo == 1) {
							prpFeedBackService.echo(dbManager, chCertiType, mainProposalNo, "4", operatorCode, underWriteDate, "1", "");
						}
						blnReturn = true;
					} else {
						blnReturn = false;
						throw new UserException(-98, -1149, internal.getText("undwrt.service.commonDealSubmit.withdrawTaskFail"),
								internal.getText("undwrt.pages.undwrtDeal.certiNo") + mainProposalNo
										+ internal.getText("undwrt.action.commonRetactTask.taskWithdrawFail"));
					}
				}
			}
			dbManager.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dbManager.close();
		}
		return blnReturn;
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
	 * @return 根據部門和時間生成的信息包號
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
		flowID = comCode.substring(8) + currentYear + currentMonth + currentDay + 
				currentHour + currentMinute + currentSecond + currentMM + 
				(int)(Math.random()*100) + (int)(Math.random()*100);
		return flowID;
	}

	/**
	 * 把規則引擎返回的信息進行拆分,存入wfLogExt表中.
	 * 
	 * @param wfLogExt
	 *            工作流日誌附屬表實體類
	 * @return 工作流日誌附屬表實體類集合
	 */
	public Collection<WfLogExt> ungroupIlog(WfLogExt wfLogExt) {
		Collection<WfLogExt> col = new ArrayList<WfLogExt>();
		int lineNo = 0;
		QueryRule queryRule = QueryRule.getInstance();

		queryRule.addEqual("id.flowId", wfLogExt.getId().getFlowId());
		queryRule.addEqual("id.logNo", wfLogExt.getId().getLogNo());
		queryRule.addIsNull("Flag");
		try {
			lineNo = uwNotionService.findByConditions(queryRule).size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] arrResultContent = {}; // 规则引擎返回的信息拆分后的数组
		int i = 0;
		if (wfLogExt.getResultContent() == null || wfLogExt.getResultContent().equals("")) {
			arrResultContent = new String[1];
			arrResultContent[0] = "";
		} else {
			// 拆分审批意见
			arrResultContent = StringUtils.split(wfLogExt.getResultContent(), RULE_LENGTH);
		}
		for (i = 0; i < arrResultContent.length; i++) {
			WfLogExt wfLogExtNew = new WfLogExt();
			WfLogExtId wfLogExtNewId = new WfLogExtId();
			wfLogExtNew.setId(wfLogExtNewId);
			wfLogExtNew.getId().setFlowId(wfLogExt.getId().getFlowId());
			wfLogExtNew.getId().setLineNo(lineNo + 1 + i);
			wfLogExtNew.getId().setLogNo(wfLogExt.getId().getLogNo());
			wfLogExtNew.setResultContent(arrResultContent[i]);
			wfLogExtNew.setFlag("");
			col.add(wfLogExtNew);
		}
		return col;
	}

	/**
	 * 生成虛擬編碼
	 * 
	 * @param bizNo1
	 *            業務號1
	 * @param bizNo2
	 *            業務號2
	 * @return Map<String,Object> 返回訊息
	 * @throws ParseException
	 */
	public Map<String, Object> genDummyCode(String businessNo, String businessType) throws Exception {
		//mantis： LIA0321，處理人員：Sam，需求單編號：行動裝置保險取消產生系統虛擬碼 Start
		if(businessNo.indexOf("MI") > -1){
			return null;
		}
		//mantis： LIA0321，處理人員：Sam，需求單編號：行動裝置保險取消產生系統虛擬碼 End
		HashMap<String, Object> params = new HashMap();
		String SysCode = "";
		Date tempDate = null;
		String totalIFee = "";
		String PayEndDate = "";
		String firstCode = "";// 商超三段码一
		String secondCode = "";// 商超三段码二
		String thirdCode = "";// 商超三段码三
		String pEndDate = "";
		BigDecimal total = new BigDecimal("0");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<PrpDprint> prpDprints = new ArrayList<PrpDprint>();
		String jfeeflag = "";
		//String editFlag = "";
		PrpTmain prpTmain = null;
		PrpTmain prpTmainCI = null;
		PrpPhead prpPhead = null;
		PrpPmain prpPmain = null;
		PrpTmainSub prpTmainSub=null;
		PrpQmain prpQmain=null;
		PrpQmain prpQmainCI=null;
		List<PrpTmainSub> prpTmainsubs = null;
		List<PrpQmainSub> prpQmainsubs = null;
		PrpQmain prpQmainB = null;
		PrpQmain prpQmainBCI = null;
		PrpQmain prpQmainBI = null; // added by zhangruofei 記錄強制險關聯的任意險報價單
		List<PrpQmainSub> prpQmainsubBs = null;
		String mainPolicyNo;
		BigDecimal qSumPremium=new BigDecimal("0");
		boolean realPayFlagQ = false;
		String quotationNo = "";
		Date startDateBI = null;
		Date startDateCI = null;
		if ("T".equals(businessType)) {
			logger.info("要保書生成虛擬編碼---------------" + businessNo);
			prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			//mantis： LIA0309_0609，處理人員：DP0728，需求單編號：LIA0309_雇主雇補險外部介接程式 Start
			if("EPS".equals(prpTmain.getOperateSite())){
				return null;
			}
			//mantis： LIA0309_0609，處理人員：DP0728，需求單編號：LIA0309_雇主雇補險外部介接程式 End
			jfeeflag = prpTmain.getJfeeFlag();
			//editFlag = prpTmain.getEditFlag();
			//需求变更，from xdw关联单只根据两个单子的总保费之和生成一个虚拟编码，print表保存两条数据20130126
			String quoteNo;
			quoteNo = prpTmain.getQuoteno();
			if(null!=quoteNo && !"".equals(quoteNo))
			{
				logger.info("要保書複製報價單生成虛擬編碼---------------" + businessNo + "------報價單號-------" + quoteNo);
				prpQmain=policyService.getPrpQmainByProposalNo(quoteNo, "quotation");
				if(null!=prpQmain)
				{
					if(null!=prpQmain.getRiskCode() && !"B01".equals(prpQmain.getRiskCode())) {
						logger.info("------報價單號-------" + quoteNo + "報價單 總保費-----" + prpQmain.getSumPremium());
						qSumPremium=qSumPremium.add(prpQmain.getSumPremium());
						prpQmainsubs = prpQmain.getPrpQmainSubs();
						if(prpQmainsubs.size()>0)
						{
							String quoteNoCI=prpQmainsubs.get(0).getId().getMainPolicyNo();
							if(null!=quoteNoCI && !"".equals(quoteNoCI))
							{
								prpQmainCI=policyService.getPrpQmainByProposalNo(quoteNoCI, "quotation");
								if(null!=prpQmainCI)
								{
									qSumPremium=qSumPremium.add(prpQmainCI.getSumPremium());
								}
							}
						}
					} else if(null!=prpQmain.getRiskCode() && "B01".equals(prpQmain.getRiskCode())) {
						logger.info("------報價單號-------" + quoteNo + "報價單 總保費-----" + prpQmain.getSumPremium());
						qSumPremium=qSumPremium.add(prpQmain.getSumPremium());
						prpQmainsubs = policyService.getPrpQmainSubByMainPolicyNo(quoteNo);
						if(null!=prpQmainsubs && prpQmainsubs.size()>0) {
							String quoteNoBI=prpQmainsubs.get(0).getId().getProposalNo();
							if(null!=quoteNoBI && !"".equals(quoteNoBI)) {
								prpQmainBI=policyService.getPrpQmainByProposalNo(quoteNoBI, "quotation");
								if(null!=prpQmainBI)
								{
									qSumPremium=qSumPremium.add(prpQmainBI.getSumPremium());
								}
							}
						}
					}
					
				}
			}
			prpTmainsubs = prpTmain.getPrpTmainSubs();
			if(prpTmainsubs.size()>0)
			{
				logger.info("------要保書關聯強制險要保書號-------" + prpTmainsubs.get(0).getId().getMainPolicyNo());
				mainPolicyNo = prpTmainsubs.get(0).getId().getMainPolicyNo();
				if(null!=mainPolicyNo && "" !=mainPolicyNo)
				{
					prpTmainCI = policyService.getPrpTmainByProposalNo(mainPolicyNo);
					if(null!=prpTmainCI)
					{
						total = total.add(prpTmainCI.getSumPremium());
						startDateCI = prpTmainCI.getStartDate();
					}
				}
			}
			total = total.add(prpTmain.getSumPremium());
			tempDate = prpTmain.getStartDate();
			startDateBI = prpTmain.getStartDate();
			if(null!=startDateBI && null!=startDateCI) {
				if(startDateCI.getTime() < startDateBI.getTime()) {
					tempDate = startDateCI;
				}
			}
			pEndDate = dateFormat.format(prpTmain.getEndDate()).substring(2, 4);
		} else if ("E".equals(businessType)) {
			prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
			PrpCPmain prpCPmain = this.getPrpCPmainByPolicyNo(prpPhead.getPolicyNo());
			//mantis： LIA0309_0609，處理人員：DP0728，需求單編號：LIA0309_雇主雇補險外部介接程式 Start
			if("EPS".equals(prpCPmain.getOperateSite())){
				return null;
			}
			//mantis： LIA0309_0609，處理人員：DP0728，需求單編號：LIA0309_雇主雇補險外部介接程式 End
			jfeeflag = prpPhead.getJfeeFlag();
			prpPmain = prpPhead.getPrpPmains().get(0);
			total = total.add(prpPmain.getChgPremium()==null ? BigDecimal.valueOf(0) :prpPmain.getChgPremium());
			if(total.compareTo(BigDecimal.ZERO)<0) {
				total = total.abs();
			}
			tempDate = prpPhead.getValidDate();
			pEndDate = dateFormat.format(prpCPmain.getEndDate()).substring(2, 4);
		} else if ("B".equals(businessType)) {
			prpQmainB = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
			//modify by dongfan 核心繫統配合需求170作出調整  20170216 
			//modefied by zhangruofei 20150325 规则变更：車險報價單均不生成虛擬編碼
			////mantis： CAR0218，處理人員：dp0708，需求單編號：CAR0218 報價單核保產生虛擬碼增加判斷關聯單較早的起保日。 --start
			// 正式機此段code未被註解,故SIT&UAT同正式機程式做修改:A01&B01不在此產生虛擬碼
			if(null!=prpQmainB.getRiskCode() && ("A01".equals(prpQmainB.getRiskCode())|| "B01".equals(prpQmainB.getRiskCode()))) {
				return params;
			}
			//mantis： CAR0218，處理人員：dp0708，需求單編號：CAR0218 報價單核保產生虛擬碼增加判斷關聯單較早的起保日。 --end
			jfeeflag = prpQmainB.getJfeeFlag();
			//需求变更，from xdw关联单只根据两个单子的总保费之和生成一个虚拟编码，print表保存两条数据20130126
			prpQmainsubBs = prpQmainB.getPrpQmainSubs();
			if(prpQmainsubBs.size()>0)
			{
				mainPolicyNo = prpQmainsubBs.get(0).getId().getMainPolicyNo();
				if(null!=mainPolicyNo && "" !=mainPolicyNo)
				{
					prpQmainBCI = policyService.getPrpQmainByProposalNo(mainPolicyNo, "quotation");
					if(null!=prpQmainBCI)
					{
						total = total.add(prpQmainBCI.getSumPremium());
					}
				}
			}
			total = total.add(prpQmainB.getSumPremium());
			tempDate = prpQmainB.getStartDate();
			pEndDate = dateFormat.format(prpQmainB.getEndDate()).substring(2, 4);
		}
		totalIFee = fullNew(total);
		Calendar calendar = Calendar.getInstance();
		if("E".equals(businessType)&&null != jfeeflag && "1".equals(jfeeflag))
		{
			calendar.setTime(tempDate);
			calendar.add(Calendar.DAY_OF_MONTH, 0);
		}else
		{
			if("E".equals(businessType))
			{
				tempDate = new DateTime(new DateTime().current().toString().substring(0, 10));
			}
			//生成虛擬編號規則，收費出單時為起保日當天
			if(null != jfeeflag && "1".equals(jfeeflag) && !"E".equals(businessType)) {
				//判斷是否是續保件
//				boolean renewFlag = false;
//				PrpTmain prpTmainTemp = policyService.getPrpTmainByProposalNo(businessNo);
//				String quoteNoTemp = prpTmainTemp.getQuoteno();
//				if(null!= editFlag && ("1".equals(editFlag) || "2".equals(editFlag))) {
//					renewFlag = true;
//				}
//				if(null!=quoteNoTemp && !"".equals(quoteNoTemp)) {
//					String hql = "from RenewalInfo where quoteNo= '" + quoteNoTemp + "'";
//					List list = this.findByHql(hql);
//					if(list.size()>0) {
//						renewFlag = true;
//					}
//				}
//				if(renewFlag) {
//					calendar.setTime(tempDate);
//					calendar.add(Calendar.DAY_OF_MONTH, -1);
//				} else {
					calendar.setTime(tempDate);
					calendar.add(Calendar.DAY_OF_MONTH, 0);
//				}
				
			} else {
				calendar.setTime(tempDate);
				calendar.add(Calendar.MONTH, 1);
			}			
		}
		tempDate = calendar.getTime();
		PayEndDate = dateFormat.format(tempDate);

		String payEncode = CreateEncodingUtil.getCreatePayEncoding(PayEndDate);// 识别码
		// 调用公式获得三段码
		// String fee = totalIFee.substring(0,totalIFee.indexOf("."));

		Date endDate = dateFormat.parse(PayEndDate);
		String maxNo = this.saveSerilNo(endDate);
		String intoAccount = "";
		List list = null;
		PrpDprint prpDprint = new PrpDprint();
		PrpDprintId prpDprintId = new PrpDprintId();
		if ("T".equals(businessType)) 
		{
			String hql = "from PrpDprint where proposalNo= '" + businessNo + "'";
			list = this.findByHql(hql);
			//需求变更，报价单转的要保书如果总保费一致且报价单存在虚拟编码，要保书使用报价单的虚拟编码20130126from suc
			logger.info("要保書總保費-------" + businessNo + "------總保費------" + total.doubleValue());
			logger.info("要保書所複製報價單總保費-------" + prpTmain.getQuoteno() + "------總保費------" + qSumPremium.doubleValue());
			logger.info("------要保書與所複製報價單總保費是否相等-------" + (total.doubleValue()==qSumPremium.doubleValue()));
			if(total.doubleValue()==qSumPremium.doubleValue())
			{
				String hql1 = "from PrpDprint where proposalNo= '" + prpTmain.getQuoteno() + "'";
				List listq = this.findByHql(hql1);
				if (listq.size() > 0)
				{
					PrpDprint prpDprintq = (PrpDprint) listq.get(0);
					intoAccount = prpDprintq.getId().getPrintvirtualCode();
					//PrpQmain prpQmainTemp = policyService.getPrpQmainByProposalNo(prpTmain.getQuoteno(),"quotation");
					//String hqlTemp = "from RenewalInfo where quoteNo= '" + businessNo + "'";
					//List listTemp = this.findByHql(hql);
					//String editFlag = prpQmain.getEditFlag();
					//if (list.size() > 0 || "2".equals(editFlag)) {
						realPayFlagQ = true;
						quotationNo = prpTmain.getQuoteno();
					//}	
						logger.info("------要保書所複製報價單存在虛擬碼-------" + "報價單號-----" + quotationNo + "虛擬碼" + intoAccount);
				}
			}
			if (list.size() > 0)
			{
				prpDprint = (PrpDprint) list.get(0);
				intoAccount = prpDprint.getId().getPrintvirtualCode();
				logger.info("------要保書存在虛擬碼-------" + businessNo + "虛擬碼" + intoAccount);
			}
		}
		if ("".equals(intoAccount) || null == intoAccount)
		{
			// mantis：FIR0709，處理人員：DP0714，住火_核心產製多元繳費單新增log做問題排查
			loggerRenewal.info(">>> proposalNo: " + businessNo + ", PayEndDate: " + PayEndDate + ", totalIFee: " + totalIFee + ", maxNo: " + maxNo);
			intoAccount = CreateEncodingUtil.getCreateEncoding(PayEndDate, totalIFee, maxNo);
			// mantis：FIR0709，處理人員：DP0714，住火_核心產製多元繳費單新增log做問題排查
			loggerRenewal.info(">>> intoAccount1: " + intoAccount);
			intoAccount = intoAccount.substring(0, 9) + maxNo + intoAccount.substring(13);
			// mantis：FIR0709，處理人員：DP0714，住火_核心產製多元繳費單新增log做問題排查
			loggerRenewal.info(">>> intoAccount2: " + intoAccount);
			logger.info("------要保書不存在虛擬碼-------" + businessNo + "新生成的虛擬碼" + intoAccount);
		}
		if ("T".equals(businessType)) {
			//如果要保书之前不存在虚拟编码就保存虚拟编码
			//mantis： HAS0226，處理人員：Sam，需求單編號：HAS0226  外部虛擬編號問題處理 Start
			PrpCmain prpCmain = policyService.getPrpCmainByProposalNo(businessNo);
			if(prpCmain != null){
				String newB2B_PRINTVIRTUALCODE =  getNEWB2BPRINTVIRTUALCODE(businessType , prpCmain.getPolicyNo());
				if(!org.apache.commons.lang3.StringUtils.isBlank(newB2B_PRINTVIRTUALCODE)){
					intoAccount = newB2B_PRINTVIRTUALCODE;
				}
			}
			//mantis： HAS0226，處理人員：Sam，需求單編號：HAS0226  外部虛擬編號問題處理 End
			if (null == list || list.size() <= 0)
			{
				prpDprintId.setPrintvirtualCode(intoAccount);
				prpDprintId.setProposalNo(businessNo);
				prpDprint.setId(prpDprintId);
				prpDprint.setRiskCode(prpTmain.getRiskCode());
				prpDprint.setPrintDate(new Date());
				try {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					prpDprint.setValiddate(simpleDateFormat.parse("3013-01-01"));
				} catch (Exception e) {
					logger.info("拋出異常  Exception1------");
					logger.error(getTrace(e));
					e.printStackTrace();
				}
				prpDprint.setFlag("1");
				logger.info("------要保書保存虛擬碼開始-------" + businessNo);
				this.save(prpDprint);
				logger.info("------要保書保存虛擬碼結束-------" + businessNo);
				if(null!=prpTmainCI)
				{
					PrpDprint prpDprintCI = new PrpDprint();
					PrpDprintId prpDprintIdCI = new PrpDprintId();
					String hql = "from PrpDprint where proposalNo= '" + prpTmainCI.getProposalNo() + "'";
					List CIlist = this.findByHql(hql);
					if(CIlist.size()>0)
					{
						prpDprintCI=(PrpDprint) CIlist.get(0);
					}
					prpDprintIdCI.setPrintvirtualCode(intoAccount);
					prpDprintIdCI.setProposalNo(prpTmainCI.getProposalNo());
					prpDprintCI.setId(prpDprintIdCI);
					prpDprintCI.setRiskCode(prpTmainCI.getRiskCode());
					prpDprintCI.setPrintDate(new Date());
					try {
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						prpDprint.setValiddate(simpleDateFormat.parse("3013-01-01"));
					} catch (Exception e) {
						logger.info("拋出異常  Exception2------");
						logger.error(getTrace(e));
						e.printStackTrace();
					}
					prpDprintCI.setFlag("1");
					logger.info("------要保書關聯單號保存虛擬碼開始-------" + prpTmainCI.getProposalNo());
					this.save(prpDprintCI);
					logger.info("------要保書關聯單號保存虛擬碼結束-------" + prpTmainCI.getProposalNo());
				}
			}
			//mantis： HAS0226，處理人員：Sam，需求單編號：HAS0226  外部虛擬編號問題處理
			if (null != prpCmain)
			{
				logger.info("------要保書產生保單 虛擬碼更新介接表開始-------" + prpCmain.getPolicyNo());
				saveprpJpayRefAndCertiNo(prpCmain.getPolicyNo(),"P", intoAccount,realPayFlagQ,quotationNo);
				logger.info("------要保書產生保單 虛擬碼更新介接表結束-------" + prpCmain.getPolicyNo());
				//生成虚拟编码之后更新承保介接表20140821
				this.updateTemporary(prpCmain,intoAccount);
			} else {
				logger.info("------要保書生成虛擬碼更新介接表開始-------" + businessNo);
				saveprpJpayRefAndCertiNo(businessNo,"T", intoAccount,realPayFlagQ,quotationNo);
				logger.info("------要保書生成虛擬碼更新介接表結束-------" + businessNo);
			}
			if(null!=prpTmainCI)
			{
				PrpCmain prpCmainCI = policyService.getPrpCmainByProposalNo(prpTmainCI.getProposalNo());
				if(null!=prpCmainCI)
				{
					logger.info("------要保書產生保單關聯單 虛擬碼更新介接表開始-------" + prpCmainCI);
					saveprpJpayRefAndCertiNo(prpCmainCI.getPolicyNo(),"P",intoAccount,realPayFlagQ,quotationNo);
					logger.info("------要保書產生保單關聯單 虛擬碼更新介接表結束-------" + prpCmainCI);
				}
				else
				{
					logger.info("------要保書關聯單生成虛擬碼更新介接表開始-------" + prpTmainCI.getProposalNo());
					saveprpJpayRefAndCertiNo(prpTmainCI.getProposalNo(),"T", intoAccount,realPayFlagQ,quotationNo);
					logger.info("------要保書關聯單生成虛擬碼更新介接表開始-------" + prpTmainCI.getProposalNo());
				}
			}
			prpDprints.add(prpDprint);
		}
		if ("E".equals(businessType)) {
			//mantis： HAS0226，處理人員：Sam，需求單編號：HAS0226  外部虛擬編號問題處理 Start
			PrpPhead prpPheadTemp = endorseService.getPrpPheadByEndorseNo(businessNo);
			if(prpPheadTemp != null){
				String newB2B_PRINTVIRTUALCODE =  getNEWB2BPRINTVIRTUALCODE(businessType , prpPheadTemp.getEndorseNo());
				//mantis： FIR0660，處理人員：Sam，需求單編號：FIR0660  住火_中信保代網投件批單無法列印繳費單 
				if(!org.apache.commons.lang3.StringUtils.isBlank(newB2B_PRINTVIRTUALCODE) && !"null".equals(newB2B_PRINTVIRTUALCODE)){
					intoAccount = newB2B_PRINTVIRTUALCODE;
				}
			}
			//mantis： HAS0226，處理人員：Sam，需求單編號：HAS0226  外部虛擬編號問題處理 End
			prpDprintId.setPrintvirtualCode(intoAccount);
			prpDprintId.setProposalNo(businessNo);
			prpDprint.setId(prpDprintId);
			prpDprint.setRiskCode(prpPmain.getRiskCode());
			prpDprint.setPrintDate(new Date());
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				prpDprint.setValiddate(simpleDateFormat.parse("3013-01-01"));
			} catch (Exception e) {
			}
			prpDprint.setFlag("1");
			this.save(prpDprint);
			List<PrpJpayRefRec> prpJpayRefRecs = new ArrayList<PrpJpayRefRec>();
			saveprpJpayRefAndCertiNo(businessNo,"E",intoAccount);
			//生成虚拟编码之后更新承保介接表20140821
			this.updateTemporary(prpPhead,intoAccount);
			prpDprints.add(prpDprint);
		}
		if("B".equals(businessType)) {
			//如果要保书之前不存在虚拟编码就保存虚拟编码
			if (null == list || list.size() <= 0){		
				if("B01".equals(prpQmainB.getRiskCode())) {
					List<PrpQmainSub> prpQmainSubListTemp = policyService.getPrpQmainSubByMainPolicyNo(prpQmainB.getProposalNo());
					if(null!=prpQmainSubListTemp && prpQmainSubListTemp.size()>0) {
						String hql = "from PrpDprint where proposalNo= '" + prpQmainSubListTemp.get(0).getId().getProposalNo() + "'";
						List CIlistTemp = this.findByHql(hql);
						if(CIlistTemp.size()>0) {
							PrpDprint prpDprintTemp=(PrpDprint) CIlistTemp.get(0);
							saveprpJpayRefAndCertiNo(businessNo,"B", prpDprintTemp.getId().getPrintvirtualCode());
						}						
						return params;
					}
				}
				prpDprintId.setPrintvirtualCode(intoAccount);
				prpDprintId.setProposalNo(businessNo);
				prpDprint.setId(prpDprintId);
				prpDprint.setRiskCode(prpQmainB.getRiskCode());
				prpDprint.setPrintDate(new Date());
				try {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					prpDprint.setValiddate(simpleDateFormat.parse("3013-01-01"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				prpDprint.setFlag("1");
				this.save(prpDprint);
				if(null!=prpQmainBCI)
				{
					PrpDprint prpDprintCI = new PrpDprint();
					PrpDprintId prpDprintIdCI = new PrpDprintId();
					String hql = "from PrpDprint where proposalNo= '" + prpQmainBCI.getProposalNo() + "'";
					List CIlist = this.findByHql(hql);
					if(CIlist.size()>0)
					{
						prpDprintCI=(PrpDprint) CIlist.get(0);
					}
					prpDprintIdCI.setPrintvirtualCode(intoAccount);
					prpDprintIdCI.setProposalNo(prpQmainBCI.getProposalNo());
					prpDprintCI.setId(prpDprintIdCI);
					prpDprintCI.setRiskCode(prpQmainBCI.getRiskCode());
					prpDprintCI.setPrintDate(new Date());
					try {
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						prpDprintCI.setValiddate(simpleDateFormat.parse("3013-01-01"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					prpDprintCI.setFlag("1");
					this.save(prpDprintCI);
				}
			}
			saveprpJpayRefAndCertiNo(businessNo,"B", intoAccount);
			if(null!=prpQmainBCI)
			{
				saveprpJpayRefAndCertiNo(prpQmainBCI.getProposalNo(),"B",intoAccount);
			}
			prpDprints.add(prpDprint);
		}
		// super.saveAll(prpDprints);
		/*
		 * params.put("intoAccount", intoAccount); params.put("firstCode",
		 * businessCode[0]); params.put("secondCode", businessCode[1]);
		 * params.put("thirdCode", businessCode[2]);
		 */
		return params;
		// return prpDprints;
	}

	/**
	 * 判斷
	 * 
	 * @param obj
	 *            Object對象
	 * @return String 返回信息
	 */
	public String fullNew(Object obj) {

		if (obj == null) {
			return "";
		}

		if (obj instanceof BigDecimal) {
			DecimalFormat decimalFormat = new DecimalFormat("0");
			return decimalFormat.format(obj);
		}
		return obj.toString();
	}

	/**
	 * 保存PrpJpayRefRec
	 * 
	 * @author guoshaohua
	 */
	public void saveprpJpayRefAndCertiNo(String businessNo,String businessType, String intoAccount) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", businessNo);
		queryRule.addEqual("id.certiType", businessType);
		List<PrpJpayRefRec> prpJpayRefRecs = null;
		PrpJpayRefRec prpJpayRefRec = new PrpJpayRefRec();
		prpJpayRefRecs = super.find(PrpJpayRefRec.class, queryRule);
		if(prpJpayRefRecs.size()>0)
		{
			prpJpayRefRec = prpJpayRefRecs.get(0);
			prpJpayRefRec.setVirtualNo(intoAccount);
			//判斷是否是續保件
			boolean isRenewFlag = checkIsRenewal(businessNo,businessType);
			if(isRenewFlag) {
				prpJpayRefRec.setIsRenewlFlag("1");
			} else {
				prpJpayRefRec.setIsRenewlFlag("2");
			}
			super.update(prpJpayRefRec);
		}
	}
	/**
	 * 保存PrpJpayRefRec，增加對續保件報價單轉要保單時收費信息的處理
	 * 因為不能直接更新PrpJpayRefRec表的收費信息，所以選擇更新intfprpjpayrefrec表
	 * @author guoshaohua
	 * @throws Exception 
	 */
	public void saveprpJpayRefAndCertiNo(String businessNo,String businessType, String intoAccount,Boolean realPayFlagQ,String quoteNo) throws Exception {
		System.out.println("單號是========="+businessType+"========="+businessNo+"=======對應的虛擬編碼是=========="+intoAccount);
		System.out.println("是否為報價轉要保==============="+realPayFlagQ+"===========報價單號是============"+quoteNo);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", businessNo);
		queryRule.addEqual("id.certiType", businessType);
		List<PrpJpayRefRec> prpJpayRefRecs = null;
		PrpJpayRefRec prpJpayRefRec = new PrpJpayRefRec();
		prpJpayRefRecs = super.find(PrpJpayRefRec.class, queryRule);
		if(prpJpayRefRecs.size()>0)
		{
			prpJpayRefRec = prpJpayRefRecs.get(0);
			prpJpayRefRec.setVirtualNo(intoAccount);
			//判斷是否是續保件
			boolean isRenewFlag = checkIsRenewal(businessNo,businessType);
			System.out.println("是否為續保件==================="+isRenewFlag);
			if(isRenewFlag) {
				prpJpayRefRec.setIsRenewlFlag("1");
			} else {
				prpJpayRefRec.setIsRenewlFlag("2");
			}
			System.out.println("PrpJpayRefRec========================"+prpJpayRefRec.getVirtualNo()+"=========="+prpJpayRefRec.getIsRenewlFlag());
			if(realPayFlagQ && null!=quoteNo && !"".equals(quoteNo)) {
				List<PrpJpayRefRecHis> prpJpayRefRecQuos = null;
				PrpJpayRefRecHis prpJpayRefRecQuo = new PrpJpayRefRecHis();
				queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.certiNo", quoteNo);
				queryRule.addEqual("id.certiType", "B");
				prpJpayRefRecQuos = super.find(PrpJpayRefRecHis.class, queryRule);
				if(prpJpayRefRecQuos.size()>0) {
					prpJpayRefRecQuo = prpJpayRefRecQuos.get(0);					
					DBManager dbManager = new DBManager();
					try {
						dbManager.open("undwrtDataSource");
						dbManager.beginTransaction();
						String statement = "update intfprpjpayrefrec set payRefNo=?,payRefDate=?,realPayRefFeeCNY=?,exchangeRateCNY=?,realPayRefFlag=? " +
								" ,virtualNo=?, virtualdate=?, isRenewlFlag=? where certiNo=? and certiType=?";
						dbManager.prepareStatement(statement);
						dbManager.setString(1, prpJpayRefRecQuo.getPayRefNo());
						dbManager.setDateTime(2, new DateTime(prpJpayRefRecQuo.getPayRefDate()));
						dbManager.setString(3, prpJpayRefRecQuo.getRealPayRefFeeCNY().toString());
						dbManager.setString(4, prpJpayRefRecQuo.getExchangeRateCNY().toString());
						dbManager.setString(5, prpJpayRefRecQuo.getRealPayRefFlag());
						dbManager.setString(6, intoAccount.toString());
						dbManager.setDateTime(7, new DateTime(prpJpayRefRecQuo.getPayRefDate()));
						dbManager.setString(8, prpJpayRefRec.getIsRenewlFlag().toString());
						dbManager.setString(9, businessNo);
						dbManager.setString(10, businessType);
						dbManager.executePreparedUpdate();
						dbManager.commitTransaction();
					} catch (Exception e) {
						dbManager.rollbackTransaction();
						e.printStackTrace();
					} finally {
						dbManager.close();
					}
				}
			}
			super.update(prpJpayRefRec);
			System.out.println("更新prpJpayRefRec結束=====對應虛擬編碼："+prpJpayRefRec.getVirtualNo()+"------續保標誌位-----"+prpJpayRefRec.getIsRenewlFlag());
		}
	}
	
	/*
	mantis： OTH0038，處理人員：Sam，需求單編號：OTH0038--- start
	繳費虛擬碼調整
	*/
	/**
	 * 批次报价、批量续保生成虚拟编号保存序列號
	 * @param endDate 結束日期
	 * @return String 序列號
	 * @throws Exception 
	 */
	public String saveSerilNo(Date endDate) throws Exception{
		String[] strMaxMinNo = new String[3];
		String strMaxNo = "";
		String strMinNo = "";
		int intCount;
		int intMaxNo;
		int whileCount = 0; // 增加计数器，如果循环20次依然取不到单号，则抛异常
		java.sql.Connection conn = null;
		java.sql.PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = null;
		int intNoLength, intChgLength;
		int SerilNo_LENGTH = 4;
		Str str = new Str();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String endDateStr = sdf.format(endDate);

		int result = 0;
		{
			WHILE_LABEL: while (true) {
				if (whileCount++ > 20) {
					throw new Exception("无法获取单号，请联系管理员！");
				}
				try{
					strMaxMinNo = this.getMaxMinSerialNo(endDate);
					strMaxNo = strMaxMinNo[0];
					strMinNo = strMaxMinNo[1];
					if(org.apache.commons.lang.StringUtils.isBlank(strMaxMinNo[2])){
						continue WHILE_LABEL;//try catch 欄劫不到下面那行NumberFormatException??
					}
					intCount = Integer.parseInt(strMaxMinNo[2]);
		
					if (0 == intCount) {
						String flowNo = "0001";
						try {
							conn = dataSource.getConnection();
							sql = "INSERT INTO PRPDSERIAL(MAXNO,ENDDATE) values(?,to_date(?,'yyyy-mm-dd')) ";
							stat = conn.prepareStatement(sql);
							stat.setString(1, flowNo);
							stat.setString(2, endDateStr);
							result = stat.executeUpdate();
						} catch (Exception ex1) {
							//insert 同一序號只能一次
							if (logger.isWarnEnabled())
								logger.warn("PrpDserial表中插入数据失败,endDate:" + endDate
										+ ";flowNo:" + "0001"+ ";", ex1);
							continue WHILE_LABEL;
						} finally {
							try {
								if (stat != null) {
									stat.close();
								}
							} catch (SQLException e) {
								if (logger.isDebugEnabled()) {
									logger.debug("预处理对象关闭异常" + e.getMessage());
								}
							}
							try {
								if (conn != null) {
									conn.close();
								}
							} catch (SQLException e) {
								if (logger.isDebugEnabled()) {
									logger.debug("数据库连接对象关闭异常" + e.getMessage());
								}
							}
						}
						strMaxMinNo = this.getMaxMinSerialNo(endDate);
						strMaxNo = strMaxMinNo[0];
						strMinNo = strMaxMinNo[1];
						intCount = Integer.parseInt(strMaxMinNo[2]);
					}else{
						strMaxNo = strMaxMinNo[0];
						strMinNo = strMaxMinNo[1];
						intCount = Integer.parseInt(strMaxMinNo[2]);
						if (strMaxNo.trim().equals(strMinNo.trim())) {
							intNoLength = strMinNo.length();
							intMaxNo = Integer.parseInt(strMinNo) + 1;
							strMaxNo = String.valueOf(intMaxNo);
							intChgLength = SerilNo_LENGTH - strMaxNo.length();
			                intChgLength = SerilNo_LENGTH - strMaxNo.length();
							strMaxNo = str.newString("0", intChgLength) + strMaxNo;
							try {
								conn = dataSource.getConnection();

								sql = "update PrpDserial set MAXNO = ? where enddate=to_date(?,'yyyy-mm-dd') AND MAXNO = ? " +
										" AND (( select count(0) from PrpDserial where enddate=to_date( ? ,'yyyy-mm-dd') ) = 1 ) " +
//										mantis： CAR0104，處理人員：Sam，需求單編號：CAR0104，取號表衝突問題處理 多加一個條件檢查
										" AND ? > MAXNO ";
								stat = conn.prepareStatement(sql);
								stat.setString(1, strMaxNo);
								stat.setString(2, endDateStr);
								stat.setString(3, strMinNo);
								stat.setString(4, endDateStr);
								stat.setString(5, strMaxNo);
								result = stat.executeUpdate();
								
							} catch (Exception ex1) {
								if (logger.isWarnEnabled())
									logger.warn("PrpDserial表中插入数据失败,endDate:" + endDate
											+ ";maxNo:" + strMaxNo + ";", ex1);
								continue WHILE_LABEL;
							} finally {
								try {
									if (stat != null) {
										stat.close();
									}
								} catch (SQLException e) {
									if (logger.isDebugEnabled()) {
										logger.debug("预处理对象关闭异常" + e.getMessage());
									}
								}
								try {
									if (conn != null) {
										conn.close();
									}
								} catch (SQLException e) {
									if (logger.isDebugEnabled()) {
										logger.debug("数据库连接对象关闭异常" + e.getMessage());
									}
								}
							}
						/*
						mantis： CAR0195，處理人員：Sam，需求單編號：CAR0195--- start
						車險續保02 51 險種直折件 問題修正
						*/
						}else{
							continue WHILE_LABEL;
						}
						/* mantis： CAR0195，處理人員：Sam，需求單編號：CAR0195 --- end */
					}
					boolean checkFlag = true;
					if(result == 1){
						// 校验是否存在，需要再确认
						checkFlag = false;
						String year = "";
					    year = endDateStr.substring(3,4);
						checkFlag = checkNo("PrpDprint", strMaxNo, endDateStr, year);
					}else{
						checkFlag = false;
					}
					if (!checkFlag) {
						continue WHILE_LABEL;
					}
					break;
				
				}catch(Exception e){
					System.out.println("Exception");
					continue WHILE_LABEL;
				}
			}
		}
		return strMaxNo;
	}
	/* mantis： OTH0038，處理人員：Sam，需求單編號：OTH0038 --- end */

	public PrpCPmain getPrpCPmainByPolicyNo(String policyNo) {
		// 获取QueryRule对象的Instance
		QueryRule queryRule = QueryRule.getInstance();
		PrpCPmain prpCPmain = null;
		if (!"".equals(policyNo)) {
			queryRule.addEqual("policyNo", policyNo.trim());
			prpCPmain = super.findUnique(PrpCPmain.class, queryRule);
			// 性能调优 20110323 liyu start
			// by当该入参为空时，会全表查询prpcmain，导致OutOfMemory系统挂掉,因此加增加控制，如果policyNo为空，抛出异常
		} else {
			throw new RuntimeException("policyNo 不能为空！");
		}
		// 性能调优 20110323 liyu end by
		// 当该入参为空时，会全表查询prpcmain，导致OutOfMemory系统挂掉，因此加增加控制，如果policyNo为空，抛出异常
		return prpCPmain;
	}
	
	/*
	 * 
	 * 生成虚拟编码之后更新层报介接表by wangJun20140820
	 */
	public void updateTemporary(Object obj,String VirtualNO)
	{
		String sql="";
		if (obj.getClass().equals(PrpCmain.class))
		{
			PrpCmain prpCmain=(PrpCmain)obj;
			/*
			mantis： HAS0073，處理人員：Sam，需求單編號：HAS0073--- start
			 新企業平台轉入旅平險繳費對帳
			*/
			if("C1".equals(prpCmain.getClassCode()))
			 {
				if(!org.apache.commons.lang.StringUtils.isBlank(prpCmain.getPolicyNo()) 
						&& prpCmain.getPolicyNo().indexOf("TAS") < 0 
						&& !"NEWB2B".equals(prpCmain.getOperateSite())){
					sql="update waa set waa57='"+VirtualNO+"'WHERE waa02 = '"+ prpCmain.getPolicyNo()+"'";	
				}
			 }
			/* mantis： HAS0073，處理人員：Sam，需求單編號：HAS0073 --- end */
			else if("M".equals(prpCmain.getClassCode()) && "MC".equals(prpCmain.getRiskCode()))
			{
				sql="update isa set isa73='"+VirtualNO+"'WHERE isa02 = '"+ prpCmain.getPolicyNo()+"'";
			}
			else if("M".equals(prpCmain.getClassCode()) && !"MC".equals(prpCmain.getRiskCode()))
			{
				sql="update dda set dda28='"+VirtualNO+"'WHERE dda03 = '"+ prpCmain.getPolicyNo()+"'";
			}
		}		
		else if (obj.getClass().equals(PrpPhead.class)) 
		{
			PrpPhead prpPhead = (PrpPhead)obj;
			if("F".equals(prpPhead.getClassCode()))
			{
				sql="update fga set fga99L='"+VirtualNO+"'WHERE fga023 = '"+ prpPhead.getEndorseNo()+"'";
			}
			else if("C1".equals(prpPhead.getClassCode()))
			 {
				sql="update waa set waa57='"+VirtualNO+"'WHERE waa03 = '"+ prpPhead.getEndorseNo()+"'";
			 }
			else if("M".equals(prpPhead.getClassCode()) && "MC".equals(prpPhead.getRiskCode()))
			{
				sql="update isa set isa73='"+VirtualNO+"'WHERE isa03 = '"+ prpPhead.getEndorseNo()+"'";
			}
			else if("M".equals(prpPhead.getClassCode()) && !"MC".equals(prpPhead.getRiskCode()))
			{
				sql="update dda set dda28='"+VirtualNO+"'WHERE dda04 = '"+ prpPhead.getEndorseNo()+"'";
			}
		}
		if(!"".equals(sql))
		{
			this.getSession().createSQLQuery(sql).executeUpdate();
		}
	}
	/**
	 * 校驗是否需要實收
	 * 收費出單的批單中，如果險種信息中有批增，則需要實收
	 * @param prpPhead
	 * @return true-需要實收  false-无需實收
	 */
	public boolean checkIsNeadPaid(PrpPhead prpPhead) {
		boolean flag = false;
		String jfeeFlag = prpPhead.getJfeeFlag();
		if("0".equals(jfeeFlag)) {
			return false;
		}
		if(null!=prpPhead.getPrpPitemKinds() && prpPhead.getPrpPitemKinds().size()>0) {
			for(PrpPitemKind prpPitemkind: prpPhead.getPrpPitemKinds()) {
				if(null!=prpPitemkind.getChgPremium() && !"".equals(prpPitemkind.getChgPremium())) {
					if(prpPitemkind.getChgPremium().doubleValue()>0) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}
	/**
	 * 判斷是否是續保件
	 * @param businessNo
	 * @param businessType
	 * @return
	 */
	public boolean checkIsRenewal(String businessNo, String businessType) {
		boolean renewalFlag = false;
		if("B".equals(businessType)) {
			PrpQmain prpQmainTemp = policyService.getPrpQmainByProposalNo(businessNo,"quotation");
			String hqlTemp = "from RenewalInfo where quoteNo= '" + businessNo + "'";
			List listTemp = this.findByHql(hqlTemp);
			String editFlag = prpQmainTemp.getEditFlag();
			if (listTemp.size() > 0 || (null!=editFlag && "2".equals(editFlag))) {
				renewalFlag = true;
			} 
		} else if("T".equals(businessType)) {
			PrpTmain prpTmainTemp = policyService.getPrpTmainByProposalNo(businessNo);
			if(null!= prpTmainTemp.getQuoteno() && !"".equals(prpTmainTemp.getQuoteno())) {
				PrpQmain prpQmainTemp = policyService.getPrpQmainByProposalNo(prpTmainTemp.getQuoteno(),"quotation");
				if(null!=prpQmainTemp) {
					String hqlTemp = "from RenewalInfo where quoteNo= '" + prpTmainTemp.getQuoteno() + "'";
					List listTemp = this.findByHql(hqlTemp);
					String editFlag = prpQmainTemp.getEditFlag();
					if (listTemp.size() > 0 || (null!=editFlag && "2".equals(editFlag))) {
						renewalFlag = true;
					}
				}			
			}
			if(prpTmainTemp != null &&"PA".equals(prpTmainTemp.getRiskCode())&& "2".equals(prpTmainTemp.getEditFlag())){
				renewalFlag = true;
			}
			
		} else if("P".equals(businessType)) {
			PrpCmain prpCmainTemp = policyService.getPrpCmainByPolicyNo(businessNo);
			if(null!=prpCmainTemp && null!= prpCmainTemp.getQuoteno() && !"".equals(prpCmainTemp.getQuoteno())) {
				PrpQmain prpQmainTemp = policyService.getPrpQmainByProposalNo(prpCmainTemp.getQuoteno(),"quotation");
				if(null!=prpQmainTemp) {
					String hqlTemp = "from RenewalInfo where quoteNo= '" + prpCmainTemp.getQuoteno() + "'";
					List listTemp = this.findByHql(hqlTemp);
					String editFlag = prpQmainTemp.getEditFlag();
					if (listTemp.size() > 0 || (null!=editFlag && "2".equals(editFlag))) {
						renewalFlag = true;
					}
				}		
			}
		}
		return renewalFlag;
		
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
	 * 獲取屬性序號.
	 * 
	 * @return 屬性序號的值
	 */
	public int getLLogNo() {
		return lLogNo;
	}

	/**
	 * 設置屬性序號.
	 * 
	 * @param lLogNo
	 *            待設置的序號的值
	 */
	public void setLLogNo(int lLogNo) {
		this.lLogNo = lLogNo;
	}

	/**
	 * 獲取屬性工作流號.
	 * 
	 * @return 屬性工作流號的值
	 */
	public String getLFlowID() {
		return lFlowID;
	}

	/**
	 * 設置屬性工作流號.
	 * 
	 * @param lFlowID
	 *            待設置的工作流號的值
	 */
	public void setLFlowID(String lFlowID) {
		this.lFlowID = lFlowID;
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
	 * 獲取屬性再保接口業務處理接口.
	 * 
	 * @return 屬性再保接口業務處理接口的值
	 */
	public BLReinsService getBlReinsService() {
		return blReinsService;
	}

	/**
	 * 設置屬性再保接口業務處理接口.
	 * 
	 * @param blReinsService
	 *            待設置的再保接口業務處理接口的值
	 */
	public void setBlReinsService(BLReinsService blReinsService) {
		this.blReinsService = blReinsService;
	}

	/**
	 * 獲取屬性再保分入信息確認接口.
	 * 
	 * @return 屬性再保分入信息確認接口的值
	 */
	public PrpReinsVerifyService getPrpReinsVerifyService() {
		return prpReinsVerifyService;
	}

	/**
	 * 設置屬性再保分入信息確認接口.
	 * 
	 * @param prpReinsVerifyService
	 *            待設置的再保分入信息確認接口的值
	 */
	public void setPrpReinsVerifyService(PrpReinsVerifyService prpReinsVerifyService) {
		this.prpReinsVerifyService = prpReinsVerifyService;
	}

	/**
	 * 獲取屬性要保書危險單位處理接口.
	 * 
	 * @return 屬性要保書危險單位處理接口的值
	 */
	public BLTDangerGetService getbLTDangerGetService() {
		return bLTDangerGetService;
	}

	/**
	 * 設置屬性要保書危險單位處理接口.
	 * 
	 * @param bLTDangerGetService
	 *            待設置的要保書危險單位處理接口的值
	 */
	public void setbLTDangerGetService(BLTDangerGetService bLTDangerGetService) {
		this.bLTDangerGetService = bLTDangerGetService;
	}

	/**
	 * 獲取屬性批單危險單位處理接口.
	 * 
	 * @return 屬性批單危險單位處理接口的值
	 */
	public BLPDangerGetService getbLPDangerGetService() {
		return bLPDangerGetService;
	}

	/**
	 * 設置屬性批單危險單位處理接口.
	 * 
	 * @param bLPDangerGetService
	 *            待設置的批單危險單位處理接口的值
	 */
	public void setbLPDangerGetService(BLPDangerGetService bLPDangerGetService) {
		this.bLPDangerGetService = bLPDangerGetService;
	}

	/**
	 * 獲取屬性保單危險單位處理接口.
	 * 
	 * @return 屬性保單危險單位處理接口的值
	 */
	public BLCDangerGetService getbLCDangerGetService() {
		return bLCDangerGetService;
	}

	/**
	 * 設置屬性保單危險單位處理接口.
	 * 
	 * @param bLCDangerGetService
	 *            待設置的保單危險單位處理接口的值
	 */
	public void setbLCDangerGetService(BLCDangerGetService bLCDangerGetService) {
		this.bLCDangerGetService = bLCDangerGetService;
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
	 * 獲取屬性核定費用結余服務接口.
	 * 
	 * @return 屬性核定費用結余服務接口的值
	 */
	public ExpenseControlDealService getExpenseControlDealService() {
		return expenseControlDealService;
	}

	/**
	 * 設置屬性核定費用結余服務接口.
	 * 
	 * @param expenseControlDealService
	 *            待設置的核定費用結余服務接口的值
	 */
	public void setExpenseControlDealService(ExpenseControlDealService expenseControlDealService) {
		this.expenseControlDealService = expenseControlDealService;
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
	 * 獲取屬性工作流接口.
	 * 
	 * @return 屬性工作流接口的值
	 */
	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	/**
	 * 設置屬性工作流接口.
	 * 
	 * @param workFlowService
	 *            待設置的工作流接口的值
	 */
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
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
	 * 獲取序號
	 * @param endDate
	 * @return
	 */
	public String[] getMaxMinSerialNo(Date endDate) {
		Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
	    String[] strMaxMinNo = new String[3];
	    String strMaxNo="";
	    String strMinNo="";
	    String count="0";
        try {        	
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String endDateStr = sdf.format(endDate);
        	conn = dataSource.getConnection();
       	    String strHql = " SELECT MAX(maxNo), MIN(maxNo), COUNT(*) FROM PrpDserial " +
                " WHERE endDate = to_date(?,'yyyy-mm-dd') ";
            stat = conn.prepareStatement(strHql);
			stat.setString(1, endDateStr);
			rs = stat.executeQuery();
			if(rs.next()){
				strMaxNo= rs.getString(1);
				strMinNo= rs.getString(2);
				count= rs.getString(3);
				strMaxMinNo[0] = strMaxNo;
				strMaxMinNo[1] = strMinNo;
				strMaxMinNo[2] = count;
			}
			
			stat.close();
			conn.close();
       } catch (Exception ex1) {
       	ex1.printStackTrace();
       	logger.error("getMaxMinSerialNo出錯！");
       } finally {
           releaseResources(stat, conn);
       }
       return strMaxMinNo;
	}
	/**
     * 釋放數據庫資源，包括數據庫連接和PrepareStatement對象
     * @param stat PrepareStatement對象
     * @param conn 數據庫連接
     */
    private void releaseResources(Statement stat, Connection conn) {
        try {
            if (stat != null) {
                stat.close();
            }
        } catch (SQLException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("預處理對象關閉異常", e);
            }
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("數據庫連接對象關閉異常", e);
            }
        }
    }
    /**
     * 檢驗虛擬編碼序號是否被使用過，檢驗的位置是虛擬編碼中“年+天數+序號”
     * @param
     * @return
     * @throws
     */
    private boolean checkNo(String iTableName, String strMinNo, String endDateStr, String year) throws  Exception{
    	StringBuilder strSql = new StringBuilder();
    	String start =endDateStr.substring(0,4)+"-01-01";
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date startDate = null;
		Date endDate = null;
		try{
			startDate = dateFormat.parse(start);
			endDate = dateFormat.parse(endDateStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		long PaymentDay = ((endDate.getTime() - startDate.getTime())
				/ (24 * 60 * 60 * 1000) + 1);
		String day = ""+PaymentDay;
		if(day.length() ==1){
			day = "00"+day;
		}
		if(day.length() ==2){
			day = "0"+day;
		}
    	int intCount = 0;
        strSql.append(" SELECT COUNT(*) FROM ").append(iTableName)
        	.append(" WHERE ").append("substr(printvirtualcode,7,7) ").append("= ?");
        intCount = (int) super.getCount(strSql.toString(),year+day+strMinNo);
        boolean blnResult = false;
        if (intCount >= 1) {
            blnResult = false;
        } else {
            blnResult = true;
        }
        return blnResult;
    }
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }
	
	/*
	mantis： OTH0038，處理人員：Sam，需求單編號：OTH0038--- start
	繳費虛擬碼調整
	*/
	public Ehcache getPolicyCache() {
		return policyCache;
	}

	public void setPolicyCache(Ehcache policyCache) {
		this.policyCache = policyCache;
	}
	/* mantis： OTH0038，處理人員：Sam，需求單編號：OTH0038 --- end */

	//mantis： HAS0226，處理人員：Sam，需求單編號：HAS0226  外部虛擬編號問題處理 Start
	/**
	 * 取得外部虛擬編號
	 * @param endDate
	 * @return
	 */
	public String getNEWB2BPRINTVIRTUALCODE(String businessType , String businessNo) {
		Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String newB2B_PRINTVIRTUALCODE = null;
        try {        	
        	conn = dataSource.getConnection();
        	String strHql = null;
        	if("B".equals(businessType)){
        		strHql = " SELECT PRINTVIRTUALCODE FROM PRPDPRINT_NEWB2B WHERE PRINTVIRTUALCODE is not null " +
           	    		" AND ORDERSEQ = ( SELECT ORDERSEQ FROM PRPCMAIN WHERE POLICYNO = ? ) ";
        		stat = conn.prepareStatement(strHql);
    			stat.setString(1, businessNo);
        	}else{
        		strHql = " SELECT PRINTVIRTUALCODE FROM PRPDPRINT_NEWB2B WHERE PRINTVIRTUALCODE is not null " +
           	    		" AND ORDERSEQ = ( SELECT ORDERSEQ FROM PRPCOPYMAIN WHERE ENDORSENO = ? ) ";
        		stat = conn.prepareStatement(strHql);
    			stat.setString(1, businessNo);
        	}
            
			rs = stat.executeQuery();
			if(rs.next()){
				newB2B_PRINTVIRTUALCODE= rs.getString(1);
			}
			stat.close();
			conn.close();
       } catch (Exception ex1) {
       	ex1.printStackTrace();
       	logger.error("getNEWB2BPRINTVIRTUALCODE出錯！");
       } finally {
           releaseResources(stat, conn);
       }
       return newB2B_PRINTVIRTUALCODE;
	}
	//mantis： HAS0226，處理人員：Sam，需求單編號：HAS0226  外部虛擬編號問題處理 End
}