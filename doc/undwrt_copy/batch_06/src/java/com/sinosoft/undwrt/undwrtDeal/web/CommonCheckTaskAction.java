package com.sinosoft.undwrt.undwrtDeal.web;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;
import ins.framework.web.Struts2Action;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
//mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理
import java.util.Collection;
import java.util.Date;//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理
import java.util.Iterator;
import java.util.List;
import java.util.Map;//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

//mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期START
import com.sinosoft.common.schema.model.PrpCPmain;
import com.sinosoft.common.schema.model.PrpCPmainLiab;
//mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期END
import com.sinosoft.common.schema.model.PrpCitemCarExt;
import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpQmain;
import com.sinosoft.common.schema.model.PrpTinsured;
import com.sinosoft.common.schema.model.PrpTitemCarExt;
import com.sinosoft.common.schema.model.PrpTmain;
//mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期
import com.sinosoft.common.schema.model.PrpTmainLiab;
import com.sinosoft.common.schema.model.PrpTmainSub;
import com.sinosoft.common.service.facade.PlatConfigRuleService;
import com.sinosoft.intf.commerce.common.StringUtil;
import com.sinosoft.platform.bl.facade.BLUtiOperateLogFacade;
import com.sinosoft.prpall.blsvr.cb.BLPrpCmainCovernote;
import com.sinosoft.prpall.blsvr.pg.BLPrpPheadCovernote;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmainSub;
import com.sinosoft.prpall.blsvr.tb.BLPrpTrenewal;
import com.sinosoft.prpall.schema.PrpCmainCovernoteSchema;
import com.sinosoft.prpall.schema.PrpPheadCovernoteSchema;
import com.sinosoft.prpall.schema.PrpTrenewalSchema;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.reins.base.model.FhReten;
import com.sinosoft.reins.base.service.facade.FhRetenService;
import com.sinosoft.reins.common.model.PrpCDangerUnit;
import com.sinosoft.reins.common.model.PrpCDangerUnitId;
import com.sinosoft.reins.common.model.PrpCReinsTrial;
import com.sinosoft.reins.common.model.PrpPDangerUnit;
import com.sinosoft.reins.common.model.PrpPDangerUnitId;
import com.sinosoft.reins.common.model.PrpPReinsTrial;
import com.sinosoft.reins.common.model.PrpTDangerUnit;
import com.sinosoft.reins.common.model.PrpTDangerUnitId;
import com.sinosoft.reins.common.model.PrpTReinsTrial;
import com.sinosoft.reins.common.model.Prpdriskconfig;
import com.sinosoft.reins.common.service.facade.BLEnquiryService;
import com.sinosoft.reins.common.service.facade.PrpCReinsTrialService;
import com.sinosoft.reins.common.service.facade.PrpLDangerUnitService;
import com.sinosoft.reins.common.service.facade.PrpPReinsTrialService;
import com.sinosoft.reins.common.service.facade.PrpTReinsTrialService;
import com.sinosoft.reins.common.service.facade.PrpdriskconfigService;
import com.sinosoft.reins.common.vo.PrpCDangerUnitVO;
import com.sinosoft.reins.common.vo.PrpPDangerUnitVO;
import com.sinosoft.reins.common.vo.PrpTDangerUnitVO;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.BLFacXLayerService;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.EnquiryService;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.FeoEnquiryService;
import com.sinosoft.reins.out.facultative.enquiry.vo.EnquiryVO;
import com.sinosoft.reins.out.facultative.enquiry.vo.FeoEnquiryVO;
import com.sinosoft.reins.product.code.service.facade.BLReinsService;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.undwrt.common.model.PrpDcode;
import com.sinosoft.undwrt.common.model.PrpDcodeId;
import com.sinosoft.undwrt.common.model.PrpDcompany;
import com.sinosoft.undwrt.common.model.PrpDrisk;
import com.sinosoft.undwrt.common.model.PrpallCertifyTreeXml;
import com.sinosoft.undwrt.common.service.facade.PrpDcodeService;
import com.sinosoft.undwrt.common.service.facade.PrpDcompanyService;
import com.sinosoft.undwrt.common.service.facade.PrpDriskService;
import com.sinosoft.undwrt.common.service.facade.PrpDuserService;
import com.sinosoft.undwrt.common.service.facade.WfMessageService;
import com.sinosoft.undwrt.common.util.Constants;
import com.sinosoft.undwrt.common.util.DateUtil;
import com.sinosoft.undwrt.undwrtBase.model.PrpTnote;
import com.sinosoft.undwrt.undwrtBase.model.SwfNode;
import com.sinosoft.undwrt.undwrtBase.model.SwfPath;
import com.sinosoft.undwrt.undwrtBase.model.UtiUwLevel;
import com.sinosoft.undwrt.undwrtBase.model.UwMaterial;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.model.WfMessage;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService;
import com.sinosoft.undwrt.undwrtBase.service.facade.UtiUwLevelService;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwMaterialService;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonCheckTaskService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService;
import com.sinosoft.undwrt.undwrtDeal.vo.ZHInfoVo;
import com.sinosoft.utiall.blsvr.BLPrpDcompany;
import com.sinosoft.utiall.blsvr.BLPrpDconfigCode;
import com.sinosoft.utility.SysConfig;
import com.sinosoft.utility.string.ChgDate;

/**
 * 核保任務校驗
 */

public class CommonCheckTaskAction extends Struts2Action {
	
	/** 屬性被保险人近三年是否有赔案 */
	private String isClaim;
	private SessionFactory sessionFactory;
	
	/** 屬性處理類型. */
	private String handType;

	/** 屬性編輯類型. */
	private String editType;

	/** 屬性編輯標題. */
	private String editTitle;

	/** 屬性標題. */
	private String handTitle;

	/** 屬性業務類型. */
	private String iBusinessType;

	/** 屬性業務號. */
	private String iBusinessNo;

	/** 屬性工作流號. */
	private String iFlowID;

	/** 屬性查看業務詳細信息的ip. */
	private String iPrpallIp = "";

	/** 屬性險別標誌. */
	private String showDangerItemFlag;

	/** 屬性模板號. */
	private String iModelNo;

	/** 屬性節點號. */
	private String iNodeNo;

	/** 屬性序號. */
	private String iLogNo;

	/** 屬性險種代碼. */
	private String iRiskCode;

	/** 屬性險類代碼. */
	private String iClassCode;

	/** 屬性業務號. */
	private String certiNo;

	/** 屬性業務類型. */
	private String certiType;

	/** 屬性授權. */
	private String authorize;

	/** 屬性險種名稱. */
	private String riskCName;

	/** 屬性節點狀態. */
	private String iNodeStatus;

	/** 屬性保單號. */
	private String messageId;

	/** 屬性核保級別節點最大值. */
	private String nodeNomax;

	/** 屬性用戶節點值. */
	private String userNodeNo;

	/** 屬性危險單位主信息. */
	private List dangerDetail = new ArrayList();

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性工作流路徑定義接口. */
	private SwfPathService swfPathService;

	/** 屬性核保處理意見接口. */
	private UwNotionService uwNotionService;

	/** 屬性工作流節點定義接口. */
	private SwfNodeService swfNodeService;

	/** 屬性機構代碼. */
	private String comCode;

	/** 屬性跳轉頁面返回結果. */
	private String content;

	/** 屬性機構中文名稱. */
	private String comCName;

	/** 屬性XML類型樹. */
	private String typeTreeXML;

	/** 屬性參數字符串. */
	private String paramString;

	/** 屬性請求地址. */
	private String remoteUrl;

	/** 屬性請求地址. */
	private String remoteUrl1;

	/** 屬性處理信息. */
	private WfLog dealInfo;

	/** 屬性報價單信息. */
	private PrpQmain prpQmain;

	/** 屬性工作流路徑. */
	private SwfPath swfPath;

	/** 屬性核保處理意見. */
	List<UwNotion> uwNotionList;

	/** 屬性審批片語. */
	private List<PrpDcode> notionCodeList = new ArrayList<PrpDcode>();

	/** 屬性核保系統查詢接口. */
	private PrpallService prpallService;

	/** 屬性險種接口. */
	private PrpDriskService prpDriskService;

	/** 屬性材料接口. */
	private UwMaterialService uwMaterialService;

	/** 屬性核保服務接口. */
	private CommonCheckTaskService commonCheckTaskService;

	/** 屬性危險單位信息服務接口. */
	private CommonDangerInfoService commonDangerInfoService;

	/** 屬性基礎代碼表接口. */
	private PrpDcodeService prpDcodeService;

	/** 屬性核保系統幫助服務接口. */
	private WfLogHelperService wfLogHelperService;

	/** 屬性機構接口. */
	private PrpDcompanyService prpDcompanyService;

	/** 屬性再保詢價單處理接口. */
	private EnquiryService enquiryService;

	/** 屬性合約自留額計畫接口. */
	private FhRetenService fhRetenService;

	/** 屬性保單的分保試算信息接口. */
	private PrpCReinsTrialService prpCReinsTrialService;

	/** 屬性要保書的分保試算資訊接口. */
	private PrpTReinsTrialService prpTReinsTrialService;

	/** 屬性理賠的危險單位劃分接口. */
	private PrpLDangerUnitService prpLDangerUnitService;

	/** 屬性批單的分保試算信息接口. */
	private PrpPReinsTrialService prpPReinsTrialService;

	/** 屬性臨分超賠接口. */
	private BLFacXLayerService blFacXLayerService;

	/** 屬性再保臨分詢價單頁面處理接口. */
	private BLEnquiryService blEnquiryService;

	/** 屬性再保接口業務處理接口. */
	private BLReinsService blReinsService;

	/** 屬性詢價單信息接口. */
	private FeoEnquiryService feoEnquiryService;

	/** 屬性要保書處理接口. */
	private PolicyService policyService;

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/** 判斷是否為續保業務是true，否false. */
	boolean isRenewal = false;

	/** 核保級別設定接口 */
	private UtiUwLevelService utiUwLevelService;

	private String whetherFacing;

	private List facingList = new ArrayList();

	private boolean existClaim = false;
	
	private String dangerNos;
	
	private List enquiryList = null;

	/** 照會訊息 */
	List<ZHInfoVo> zhList = new ArrayList<ZHInfoVo>();
	
	/** 同險累積查看標誌 */
	private String sameRiskFlag;
	
	private PrpdriskconfigService prpdriskconfigService;
	
	private String reinsIP;
	
	/**是否存在留言列表**/
	private boolean existMessage;
	
	private WfMessageService wfMessageService;
	
	private String rationCode="";
	
	private boolean isAllowEdit;
	
	/**
	 * 核保任務校驗.
	 * 
	 * @return 頁面跳轉結果
	 * @throws Exception
	 *             異常
	 */
	public String commonCheckTask() throws Exception {
		String forward = "";
		if (iNodeStatus != null) {
			iNodeStatus = iNodeStatus.trim();
		}
		HttpSession session = this.getSession();
		HttpServletRequest req = this.getRequest();
		// 改派用户信息所需
		if (iFlowID != null) {

			session.setAttribute("FlowIDsend", iFlowID);
			session.setAttribute("LogNosend", iLogNo);

		}

		String reinsFlag = req.getParameter("ReinsFlag");

		String handType = (String) session.getAttribute("handType");
		String userCode = (String) session.getAttribute("myUserCode");
		String userName = (String) session.getAttribute("myUserName");
		String comCode = (String) session.getAttribute("myComCode");
		String comName = (String) session.getAttribute("myComCName");
		comCName = (String) session.getAttribute("myComCName");

		QueryRule queryRule = QueryRule.getInstance();

		// 查看业务详细信息的IP
		iPrpallIp = SysConfig.getProperty("prpallIP");
		req.setAttribute("iPrpallIp", iPrpallIp);

		String strSQL = "";
		String nodeStatus = "";
		String businessNo = "";
		String showDangerItemFlag = req.getParameter("showDangerItemFlag");

		// 查看投保单，保单危险单位子信息
		if (showDangerItemFlag != null && showDangerItemFlag.equals("1")) {
			// 临分询价后不允许有任何操作
			if (!this.haveFacInformation(req)) {
				// 更新或保存当前的新危险单位信息
				this.setDangerDetailToViewByDangerNo(req);
				forward = "showDangerItem";
			} else {
				forward = "failure";
				content = getText("undwrt.action.commonCheckTask.cannotDealRiskEst");
			}
			return forward;
		}
		if (showDangerItemFlag != null && showDangerItemFlag.equals("2")) {
			// 更新或保存当前的新危险单位信息
			this.setDangerDetailToViewByDangerNo(req);
			forward = "showEndorseDangerItem";
			return forward;
		}

		/***************** 增加临分超赔信息展示 begin **************/
		// 点击分保意向按钮页面标识
		if (reinsFlag != null) {
			Collection feoXFacDtoList = null;
			reinsFlag = reinsFlag.trim();
			if (reinsFlag != null && reinsFlag.equals("1")) {
				String certiNo = req.getParameter("CertiNo");
				String certiType = req.getParameter("CertiType");
				// 由于临分意向页面中不需要显示危险单位信息，所以此部分拿掉
				this.setDangerInfoToViewByReins(certiNo, certiType);
				// 获取询价单信息，由于不知道询价单号，只能通过proposalNo获取
				EnquiryVO enquiryDto = new EnquiryVO();
				if (certiType != null && !certiType.equals("")) {
					if (certiType.equals("T")) {
						queryRule.addEqual("proposalNo", certiNo);
					} else if (certiType.equals("P")) {
						queryRule.addEqual("policyNo", certiNo);
					} else if (certiType.equals("E")) {
						queryRule.addEqual("endorseNo", certiNo);
					}

					enquiryList = (List) enquiryService.findByConditions(queryRule);
				}
				if (enquiryList != null) {
					Iterator it = enquiryList.iterator();
					while (it.hasNext()) {
						enquiryDto = (EnquiryVO) it.next();
						// 临分超赔信息显示
						feoXFacDtoList = blFacXLayerService.findFeoXFac(enquiryDto.getFeoEnquiryVO().getEnquiryNo(), "undwrt");
					}
					
				}

				// 取得附加自留额
				queryRule = QueryRule.getInstance();
				if (certiType.equals("T")) {
					queryRule.addEqual("id.proposalNo", certiNo);
					queryRule.addEqual("reinsMode", "182");
					Collection prpTreinsTrialList = prpTReinsTrialService.findByConditions(queryRule);
					Iterator iter = prpTreinsTrialList.iterator();
					PrpTReinsTrial prpTreinsTrialDto = new PrpTReinsTrial();
					while (iter.hasNext()) {
						prpTreinsTrialDto = (PrpTReinsTrial) iter.next();
						break;
					}
					if (prpTreinsTrialDto.getShareRate() != null && prpTreinsTrialDto.getShareRate() != 0) {
						enquiryDto.getFeoEnquiryVO()
								.setRemarks(getText("undwrt.action.commonCheckTask.suggestLinFen") + prpTreinsTrialDto.getShareRate() + "%");
					}
				} else if (certiType.equals("P")) {
					queryRule.addEqual("id.policyNo", certiNo);
					queryRule.addEqual("reinsMode", "182");
					Collection prpCreinsTrialList = prpCReinsTrialService.findByConditions(queryRule);
					Iterator iter = prpCreinsTrialList.iterator();
					PrpCReinsTrial prpCreinsTrialDto = new PrpCReinsTrial();
					while (iter.hasNext()) {
						prpCreinsTrialDto = (PrpCReinsTrial) iter.next();
						break;
					}
					if (prpCreinsTrialDto.getShareRate() != null && prpCreinsTrialDto.getShareRate() != 0) {
						enquiryDto.getFeoEnquiryVO()
								.setRemarks(getText("undwrt.action.commonCheckTask.suggestLinFen") + prpCreinsTrialDto.getShareRate() + "%");

					}
				} else if (certiType.equals("E")) {
					queryRule.addEqual("id.endorseNo", certiNo);
					queryRule.addEqual("reinsMode", "182");
					Collection prpPreinstrialList = prpPReinsTrialService.findByConditions(queryRule);
					Iterator iter = prpPreinstrialList.iterator();
					PrpPReinsTrial prpPreinsTrialDto = new PrpPReinsTrial();
					while (iter.hasNext()) {
						prpPreinsTrialDto = (PrpPReinsTrial) iter.next();
						break;
					}
					if (prpPreinsTrialDto.getShareRate() != null && prpPreinsTrialDto.getShareRate() != 0) {
						enquiryDto.getFeoEnquiryVO()
								.setRemarks(getText("undwrt.action.commonCheckTask.suggestLinFen") + prpPreinsTrialDto.getShareRate() + "%");
					}
				}
				// 核保通过后点击"后退按钮"不允许再进行临分意向
				String underwriteFlag = "9";
				if (certiType.equals("T")) {
					PrpTmain prpTmain = prpallService.getPrpTmain(certiNo, certiType);
					underwriteFlag = prpTmain.getUnderWriteFlag();
				} else if (certiType.equals("E")) {
					// 增加预约保险批单;
					BLPrpPheadCovernote blPrpPheadCovernote = new BLPrpPheadCovernote();
					PrpPheadCovernoteSchema prpPheadCovernoteSchema = new PrpPheadCovernoteSchema();
					blPrpPheadCovernote.getData(certiNo);
					if (blPrpPheadCovernote.getSize() > 0) {
						prpPheadCovernoteSchema = blPrpPheadCovernote.getArr(0);
						underwriteFlag = prpPheadCovernoteSchema.getUnderWriteFlag();
					} else {
						PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(certiNo);
						underwriteFlag = prpPhead.getUnderWriteFlag();
					}
				} else if (certiType.equals("P")) {
					BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
					PrpCmainCovernoteSchema prpCmainCovernoteSchema = new PrpCmainCovernoteSchema();
					blPrpCmainCovernote.getData(certiNo);
					if (blPrpCmainCovernote.getSize() > 0) {
						prpCmainCovernoteSchema = blPrpCmainCovernote.getArr(0);
						underwriteFlag = prpCmainCovernoteSchema.getUnderWriteFlag();
					} else {
						PrpCmain prpCmain = prpallService.getPrpCmain(certiNo);
						underwriteFlag = prpCmain.getUnderWriteFlag();
					}
				}
				if (underwriteFlag.equals("1") || underwriteFlag.equals("2") || underwriteFlag.equals("3")) {
					throw new UserException(-98, -3002, "UICommonCheckTaskFacade", getText("undwrt.action.commonCheckTask.donotPermitUpdate"));
				} else {
					blEnquiryService.reinsReceiveToRequest(req, enquiryDto);
					req.setAttribute("feoXFacDtoList", feoXFacDtoList);
					if (!"F01".equals(iRiskCode)) {
						forward = "carReins";
						return forward;
					}
					String[] isFacing = whetherFacing.split(",");
					String[] dangerNo = dangerNos.split(",");
					for(int i=0;i<isFacing.length;i++)
					{
						if("1".equals(isFacing[i]))
						{
							facingList.add(dangerNo[i]);
							if(!(enquiryList.size()>0))
							{
								EnquiryVO enquiryVO = new EnquiryVO();
								FeoEnquiryVO feoEnquiryVO = enquiryVO.getFeoEnquiryVO();
								feoEnquiryVO.setDangerNo(Integer.valueOf(dangerNo[i]));
								enquiryVO.setFeoEnquiryVO(feoEnquiryVO);
								enquiryList.add(enquiryVO);
							}
							else
							{
								boolean find = false;
								for(int j=0;j<enquiryList.size();j++)
								{
									EnquiryVO enquiryVO = (EnquiryVO) enquiryList.get(j);
									FeoEnquiryVO feoEnquiryVO = enquiryVO.getFeoEnquiryVO();
									if(feoEnquiryVO.getDangerNo()==Integer.valueOf(dangerNo[i]))
									{
										find = true;
									}
								}
								if(!find)
								{
									EnquiryVO enquiryVO = new EnquiryVO();
									FeoEnquiryVO feoEnquiryVO = enquiryVO.getFeoEnquiryVO();
									feoEnquiryVO.setDangerNo(Integer.valueOf(dangerNo[i]));
									enquiryVO.setFeoEnquiryVO(feoEnquiryVO);
									enquiryList.add(enquiryVO);
									
								}
							}
	
						}
					}
					forward = "reins";
					return forward;
				}
			}
		}
		/******************* 增加临分超赔展示 end **********************/

		queryRule.addEqual("id.uwType", Constants.UWTYPE_T);
		queryRule.addEqual("id.validStatus", "1");
		queryRule.addEqual("id.userCode", userCode);
		queryRule.addEqual("id.comCode", comCode);
		queryRule.addEqual("id.modelNo", Integer.parseInt(iModelNo));
		List<UtiUwLevel> utiUwLevelList = utiUwLevelService.getUtiUwLevelList(queryRule);

		if (null != utiUwLevelList && utiUwLevelList.size() > 0) {
			userNodeNo = String.valueOf(utiUwLevelList.get(0).getId().getNodeNo());
		}

		queryRule.getRuleList().clear();
		queryRule.getQueryRuleList().clear();
		queryRule.addEqual("id.modelNo", Integer.parseInt(iModelNo));
		queryRule.addEqual("endFlag", "0");
		queryRule.addDescOrder("id.nodeNo");
		List<SwfNode> swfNodeList = swfNodeService.findByQureyRuleList(queryRule);
		if (null != swfNodeList && swfNodeList.size() > 0) {
			nodeNomax = String.valueOf(swfNodeList.get(0).getId().getNodeNo());
		}

		if ("B".equals(iBusinessType)) {
			WfLog wflog = new WfLog();
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", iFlowID);
			queryRule.addEqual("id.logNo", Integer.parseInt(iLogNo));
			wflog = wfLogService.findByPrimaryKey(queryRule);
			//add by xuhuiling 需求150 展示報價單頁面獲取拒限保，風險等級，檢測名單，作業狀態 begin
			String queryWorkStatusSql="";
			queryWorkStatusSql ="select t.refuselimiteinsurance,t.listdetection,t.riskrating,t.workstatus from prpqmain t where t.proposalno ='"+wflog.getBusinessNo()+"'";
			if(queryWorkStatusSql!=null && !"".equals(queryWorkStatusSql)){
				Session sessionW = sessionFactory.getCurrentSession();
				Query queryT = sessionW.createSQLQuery(queryWorkStatusSql);
				List<Object[]> prptmainList = queryT.list();
				for(int k=0;k<prptmainList.size();k++){
					Object[] obj = prptmainList.get(k);
					String refuseLimiteInsurance=obj[0]==null?"":obj[0]+"";
					String listDetection=obj[1]==null?"":obj[1]+"";
					String riskRating=obj[2]==null?"":obj[2]+"";
					String workStatus=obj[3]==null?"":obj[3]+"";
					session.setAttribute("refuseLimiteInsurance", refuseLimiteInsurance);
					session.setAttribute("listDetection", listDetection);
					session.setAttribute("riskRating", riskRating);
					session.setAttribute("workStatus", workStatus);
				}
			}   
			//add by xuhuiling 需求150 展示報價單頁面獲取拒限保，風險等級，檢測名單，作業狀態 end
			nodeStatus = wflog.getNodeStatus();
			if (!"1".equals(nodeStatus) && !userCode.equals(wflog.getOperatorCode())) {
				forward = "failure";
				content = wflog.getOperatorName() + getText("undwrt.action.batchTask.taskDealing");
				return forward;
			}
			String riskCode = (String) req.getParameter("RiskCode");
			if (riskCode == null) {
				riskCode = (String) req.getParameter("iRiskCode");
			}
			if(!"C1".equals(iClassCode) &&!"A".equals(iClassCode) && !"B".equals(iClassCode))
			{
				isAllowEdit =true;
			}
			// 标的信息
			List itemKindList = (ArrayList) prpallService.getQtaCustomTitemKindList(iBusinessNo, riskCode);

			// 审批片语
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.codeType", "HbNotionCode");
			queryRule.addAscOrder("id.codeCode");
			notionCodeList = prpDcodeService.findPrpDcodeList(queryRule);
			uwNotionList = new ArrayList<UwNotion>();
			addHbNotion();
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", iFlowID);
			queryRule.addEqual("id.logNo", Integer.parseInt(iLogNo));
			uwNotionList = uwNotionService.findByConditions(queryRule);
			UwNotion uwNotionDto = new UwNotion();
			if (uwNotionList.size() <= 0) {
				// 新处理，默认'同意'
				//uwNotionDto.setHandleText(getText("undwrt.action.commonCheckTask.agreeUnderwrite"));
				uwNotionList.add(uwNotionDto);
			}
			this.getPassPath(req);
			PrpDcompany prpDcompany = null;
			try {
				prpQmain = policyService.getPrpQmainByProposalNo(iBusinessNo, "");
				//tb险别不显示保险期间和起航日期20140715
				rationCode=prpQmain.getRationCode()==null?"":prpQmain.getRationCode();
				prpDcompany = prpDcompanyService.findByPrimaryKey(prpQmain.getComCode());
				if (prpDcompany != null) {
					comCName = prpDcompany.getComCName();
				}
			} catch (Exception exception) {
				throw exception;
			}
			if (nodeStatus.equals("1") && !editType.equals("query")) {
				wflog.setOperatorCode(userCode);
				wflog.setOperatorName(userName);
				wflog.setDeptCode(comCode);
				wflog.setDeptName(comName);
				wflog.setNodeStatus("2");
				wfLogService.update(wflog);
			}
			req.setAttribute("ItemKind", itemKindList);
			//mantis： CAR0123，處理人員：Sam，需求單編號：CAR0123，延續原CAR0107議題,新增關聯單卡控條件
			session.removeAttribute("relevUndwrtBusiNo");
			// 返回跳转
			return "qtaSuccess";
		}

		int logNo = 0;
		int i = 0;
		int notionListSize = 0;
		String riskCode = "";
		if (req.getParameter("iLogNo") != null && !req.getParameter("iLogNo").equals("")) {
			logNo = Integer.parseInt(new DecimalFormat("#").format(Double.parseDouble(req.getParameter("iLogNo"))));
		}
		if (req.getParameter("iRiskCode") != null && !req.getParameter("iRiskCode").equals("")) {
			riskCode = req.getParameter("iRiskCode");
		}

		WfLog wfLogDto = null;

		queryRule.getRuleList().clear();
		queryRule.getQueryRuleList().clear();
		queryRule.addEqual("id.flowId", iFlowID);
		queryRule.addEqual("id.logNo", logNo);
		wfLogDto = wfLogService.findByPrimaryKey(queryRule);
		//TODO 已经查询出保单的信息，根据保单的类型来判断所需要的工作状态，从哪个表中查询
		// add by xuhuiling 2016年8月21日 begin
		String queryWorkStatusSql = "";
		if("T".equals(wfLogDto.getBusinessType())){
			queryWorkStatusSql ="select t.refuselimiteinsurance,t.listdetection,t.riskrating,t.workstatus from prptmain t where t.proposalno ='"+wfLogDto.getBusinessNo()+"'";
		}else if("E".equals(wfLogDto.getBusinessType())){//批單
			queryWorkStatusSql ="select t.refuselimiteinsurance,t.listdetection,t.riskrating,t.workstatus from prppmain t where t.endorseno ='"+wfLogDto.getBusinessNo()+"'";
		}else if("B".equals(wfLogDto.getBusinessType())){
			queryWorkStatusSql ="select t.refuselimiteinsurance,t.listdetection,t.riskrating,t.workstatus from prpqmain t where t.endorseno ='"+wfLogDto.getBusinessNo()+"'";
		}
		if(queryWorkStatusSql!=null && !"".equals(queryWorkStatusSql)){
			Session sessionW = sessionFactory.getCurrentSession();
			Query queryT = sessionW.createSQLQuery(queryWorkStatusSql);
			List<Object[]> prptmainList = queryT.list();
			for(int k=0;k<prptmainList.size();k++){
				Object[] obj = prptmainList.get(k);
				String refuseLimiteInsurance=obj[0]==null?"":obj[0]+"";
				String listDetection=obj[1]==null?"":obj[1]+"";
				String riskRating=obj[2]==null?"":obj[2]+"";
				String workStatus=obj[3]==null?"":obj[3]+"";
				session.setAttribute("refuseLimiteInsurance", refuseLimiteInsurance);
				session.setAttribute("listDetection", listDetection);
				session.setAttribute("riskRating", riskRating);
				session.setAttribute("workStatus", workStatus);
			}
		}
		//add by xuhuiling 2016年8月21日 end
		
		
		// add by wangcan 2015/12/03
		if ("T".equals(wfLogDto.getBusinessType())) {
			String claimInsuredCode = "select * from prptinsured where riskcode = '"+riskCode+"' and insuredflag = '1' and proposalno = '"+iBusinessNo+"'";
			Session sessionH = sessionFactory.getCurrentSession();
			Query query = sessionH.createSQLQuery(claimInsuredCode).addEntity(PrpTinsured.class);
			List<PrpTinsured> insuredList = query.list();
			StringBuffer isClaimSQL = new StringBuffer(64);
			isClaimSQL.append("SELECT 0 FROM prplclaim WHERE  riskcode = '"+riskCode+"' AND canceldate IS NULL " +
					"AND  dealercode IS NULL AND  inputdate > add_months(SYSDATE, -36) " +
					"AND ( insuredcode = '"+insuredList.get(0).getInsuredCode()+"' or insuredcode ='"+insuredList.get(0).getIdentifyNumber()+"' )");
			
			query = sessionH.createSQLQuery(isClaimSQL.toString());
			List claimList = query.list();
			if(claimList!=null && claimList.size()>0){
				isClaim = "是";
			}else{
				isClaim = "否";
			}
			req.setAttribute("isClaim", isClaim);
		}
		
		session.setAttribute("riskCode", riskCode);

		queryRule.getRuleList().clear();
		queryRule.getQueryRuleList().clear();
		queryRule.addEqual("riskCode", riskCode);
		Collection<PrpDrisk> dRiskList = prpDriskService.findByQureyRuleList(queryRule);

		Iterator<PrpDrisk> itDk = dRiskList.iterator();
		PrpDrisk prpDriskSchema = null;
		if (itDk.hasNext()) {
			prpDriskSchema = itDk.next();
		}
		riskCName = prpDriskSchema.getRiskCName();

		req.setAttribute("riskCName", riskCName);
		req.setAttribute("comCName", comName);

		if (handType == null || editType == null) {
			handType = req.getParameter("handType");
			editType = req.getParameter("editType");
			session.setAttribute("handType", handType);
			session.setAttribute("editType", editType);
			session.setAttribute("handType", handType);
			session.setAttribute("editType", editType);
		}
		if (handType.equals("11")) {
			handTitle = getText("undwrt.action.commonCheckTask.underWrite");
		} else if (handType.equals("22")) {
			handTitle = getText("undwrt.action.commonCheckTask.checkCompensate");
		} else if (handType.equals("33")) {
			handTitle = getText("undwrt.action.commonCheckTask.checkLoss");
		}
		if (editType.equals("deal")) {
			editTitle = getText("undwrt.action.commonCheckTask.dealWith");
		} else if (editType.equals("query")) {
			editTitle = getText("prompt.query");
		}
		session.setAttribute("handTitle", handTitle);
		session.setAttribute("editTitle", editTitle);
		if (wfLogDto != null) {
			// 把ILog返回的操作类型放到request里面
			BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
			if (!"".equals(wfLogDto.getResultCode())) {
				if (!"B01".equals(wfLogDto.getRiskCode())) {
					blPrpTmainSub.getData(wfLogDto.getBusinessNo());
					if (blPrpTmainSub.getSize() > 0) {
						// 关联出单
						if ("111".equals(blPrpTmainSub.getArr(0).getFlag())) {
							req.setAttribute("MainPolicyNo", blPrpTmainSub.getArr(0).getMainPolicyNo());
						}
					}
				}
			} else {
				req.setAttribute("MainPolicyNo", "");
			}
			// 意健险（27）核赔不同签批人校验
			try {
				if (wfLogDto.getClassCode().equals("27") && (wfLogDto.getBusinessType().equals("C") || wfLogDto.getBusinessType().equals("Y"))) {
					if (wfLogDto.getHandlerCode().equals(userCode)) {
						throw new UserException(2005, 829, getText("undwrt.action.commonCheckTask.donotPermitDeal"), "");
					}
				}
			} catch (UserException usee) {
				forward = "fail";
				session.setAttribute("userException", usee);
				return forward;
			}

			// 获取节点状态进行判断
			nodeStatus = wfLogDto.getNodeStatus();
			forward = "success";
			if ("Authorize".equals(authorize)) {// 授权任务审核
				forward = "showAuthorizeControl";
			}

			if (editType.equals("deal")) {
				if (nodeStatus.equals("2") || nodeStatus.equals("3")) { // 正在处理或已处理未提交，如果是他人再处理，进行提示
					if (!userCode.equals(wfLogDto.getOperatorCode())) {
						forward = "failure";
						content = wfLogDto.getOperatorName() + getText("undwrt.action.batchTask.taskDealing");
						return forward;
					}
				} else if (nodeStatus.equals("4")) { // 已提交或已关闭
					content = getText("undwrt.action.batchTask.flowDealed");
				} else if (nodeStatus.equals("0")) {
					content = getText("undwrt.action.batchTask.workFlowDealed");
				}
			} else if (editType.equals("submit")) {
				session.setAttribute("EditType", editType);
			} else if (editType.equals("query")) {
				session.setAttribute("EditType", editType);
			}

			// 收集任务主信息页面数据
			// 摘要信息
			// 审批意见
			uwNotionList = new ArrayList<UwNotion>();
			UwNotion uwNotionDto = new UwNotion();
			// 如果是新处理，默认'同意'
			if (nodeStatus.equals("1")) {
				if (AppConfig.get("sysconst.LOCAL_COMCODE").equals("DONGAN")) {
					if (handType.equals("11")) {
						uwNotionDto.setHandleText(getText("undwrt.action.commonCheckTask.agreeUnderwrite"));
					} else if (handType.equals("22")) {
						uwNotionDto.setHandleText(getText("undwrt.action.commonCheckTask.agreeSettlement"));
					}
				}
				uwNotionList.add(uwNotionDto);
			} else {
				queryRule.getRuleList().clear();
				queryRule.getQueryRuleList().clear();
				queryRule.addEqual("id.flowId", wfLogDto.getId().getFlowId());
				queryRule.addEqual("id.logNo", wfLogDto.getId().getLogNo());
				uwNotionList = uwNotionService.findByConditions(queryRule);

				notionListSize = uwNotionList.size();
				String notion = "";
				for (i = 0; i < notionListSize; i++) {
					uwNotionDto = uwNotionList.get(i);
					notion += uwNotionDto.getHandleText().trim(); // 累加审批意见
				}
				uwNotionList = new ArrayList<UwNotion>();
				uwNotionDto.setHandleText(notion);
				uwNotionList.add(uwNotionDto);
			}

			// 获取审批片语
			if (handType.equals("11")) {
				// 核保初审岗的审核片语,改为可以配置的初审岗
				if (getText("undwrt.action.commonCheckTask.passNode").equals(wfLogDto.getNodeName())) {

					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("id.codeType", "HbNotionCode");
					queryRule.addNotEqual("id.codeCode", "005");
					queryRule.addAscOrder("id.codeCode");
					notionCodeList = prpDcodeService.findPrpDcodeList(queryRule);
					addHbNotion();
				} else {
					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("id.codeType", "HbNotionCode");
					queryRule.addAscOrder("id.codeCode");
					notionCodeList = prpDcodeService.findPrpDcodeList(queryRule);
					addHbNotion();
				}
				// 核保初审岗的审核片语
			} else if (handType.equals("22")) {
				queryRule.getRuleList().clear();
				queryRule.getQueryRuleList().clear();
				queryRule.addEqual("id.codeType", "HpNotionCode");
				queryRule.addAscOrder("id.codeCode");
				notionCodeList = prpDcodeService.findPrpDcodeList(queryRule);
				addHbNotion();
			}

			// 参考信息 黑名单/灰名单/红名单/多重承保/单证信息
			dealInfo = wfLogDto;

			// 更新当前日志状态
			// 将直接调用BL层方法改为通过标准流程调用
			if (!(userCode == null || "".equals(userCode) || userName == null || "".equals(userName) || comCode == null || "".equals(comCode)
					|| comName == null || "".equals(comName))) {
				if (nodeStatus.equals("1") && !editType.equals("query")) {
					wfLogDto.setOperatorCode(userCode);
					wfLogDto.setOperatorName(userName);
					wfLogDto.setDeptCode(comCode);
					wfLogDto.setDeptName(comName);
					wfLogDto.setNodeStatus("2");
					wfLogService.update(wfLogDto);
					// 更新关联单强制险工作流状态20130828
					if ("T".equals(wfLogDto.getBusinessType())) {
						List list = policyService.getPrpTmainByProposalNo(wfLogDto.getBusinessNo()).getPrpTmainSubs();
						if (list.size() > 0) {
							PrpTmainSub prpTmainSub = (PrpTmainSub) list.get(0);
							if ("111".equals(prpTmainSub.getFlag())) {
								String sql = "select * from wflog where businessno='" + prpTmainSub.getId().getMainPolicyNo() + "' and logno='" + logNo + "'";
								List li = (List) wfLogService.findByConditions(sql);
								if (li.size() > 0) {
									WfLog wflogCI = (WfLog) li.get(0);
									wflogCI.setOperatorCode(userCode);
									wflogCI.setOperatorName(userName);
									wflogCI.setDeptCode(comCode);
									wflogCI.setDeptName(comName);
									wflogCI.setNodeStatus("2");
									wfLogService.update(wflogCI);
								}
							}
						}
					}
					BLUtiOperateLogFacade blUtiOperateLogFacade = new BLUtiOperateLogFacade();
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
					boolean blnIsMainSub = false;// 是否关联出单

					if (wfLogDto.getBusinessType().equals("T") || wfLogDto.getBusinessType().equals("P") || wfLogDto.getBusinessType().equals("E")) {
						strLogSystemCode = "undwrt";
						strLogRiskCode = wfLogDto.getRiskCode();
						try {
							if (wfLogDto.getBusinessType().equals("T")) {
								strLogBusinessType = "T";
								strLogOperateType = "undwrt.hebao.proposaldealtime";
								strLogIsJFeeFlag = policyService.getPrpTmainByProposalNo(wfLogDto.getBusinessNo()).getJfeeFlag();
								if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
									strLogIsJFeeFlag = "0";
								}
							} else if (wfLogDto.getBusinessType().equals("P")) {
								strLogBusinessType = "P";
								strLogOperateType = "undwrt.hebao.policydealtime";
								if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
									strLogIsJFeeFlag = "0";
								} else {
									strLogIsJFeeFlag = policyService.getPrpCmainByPolicyNo(wfLogDto.getBusinessNo()).getJfeeFlag();
								}
								if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
									strLogIsJFeeFlag = "0";
								}
							} else if (wfLogDto.getBusinessType().equals("E")) {//批單
								strLogBusinessType = "E";
								strLogOperateType = "undwrt.hebao.endorsedealtime";
								if ("9999".equals(strLogRiskCode) || "9998".equals(strLogRiskCode) || "9997".equals(strLogRiskCode)) {
									strLogIsJFeeFlag = "0";
								} else {
									strLogIsJFeeFlag = endorseService.getPrpPheadByEndorseNo(wfLogDto.getBusinessNo()).getJfeeFlag();
								}
								if (null == strLogIsJFeeFlag || strLogIsJFeeFlag.equals("")) {
									strLogIsJFeeFlag = "0";
								}
							}
						} catch (Exception exception) {
							exception.printStackTrace();
							throw exception;
						}
						strLogBusinessNo = wfLogDto.getBusinessNo();
						strLogOperateTime = chgDate.getCurrentTime("yyyy-MM-dd HH:mm:ss");
						strLogComCode = wfLogDto.getComCode();
						strLogMakeCom = wfLogDto.getMakeCom();
						strLogOperatorCode = wfLogDto.getOperatorCode();
						intLogLogNo = wfLogDto.getId().getLogNo();
						strLogIP = req.getRemoteAddr();
						if (isILog(strLogRiskCode, strLogComCode)) {
							strLogIsILog = "1";
						} else {
							strLogIsILog = "0";
						}
						// 核保员处理肯定不是自动核保
						strLogIsAutoUnderWrite = "0";
						// 如果是交强险且是关联出单，则在单独处理该投保单时不记录时间点(blnIsMainSub为true不记录，为false记录)
						if (strLogRiskCode.equals("B01")) {
							if (wfLogDto.getBusinessType().equals("T")) {
								strSQL = "mainpolicyno = '" + wfLogDto.getBusinessNo() + "'";
								BLPrpTmainSub blPrpTmainSubTmp = new BLPrpTmainSub();
								blPrpTmainSubTmp.query(strSQL);
								if (blPrpTmainSubTmp.getSize() > 0 && "11".equals(blPrpTmainSubTmp.getArr(0).getFlag().substring(0, 2))) {
									blnIsMainSub = true;
								} else {
									blnIsMainSub = false;
								}
							}
						}
						if (!blnIsMainSub) {
							blUtiOperateLogFacade.save(strLogSystemCode, strLogRiskCode, strLogBusinessType, strLogBusinessNo, intLogLogNo, strLogIsJFeeFlag,
									strLogIsAutoUnderWrite, strLogIsILog, strLogOperateType, strLogOperateTime, strLogComCode, strLogMakeCom,
									strLogOperatorCode, strLogIP);
						}
						// 对于商业险关联出单的处理
						if (strLogRiskCode.equals("A01") || strLogRiskCode.equals("0510")) {
							if (wfLogDto.getBusinessType().equals("T")) {
								strSQL = "proposalno = '" + wfLogDto.getBusinessNo() + "'";
								BLPrpTmainSub blPrpTmainSubTmp = new BLPrpTmainSub();
								blPrpTmainSubTmp.query(strSQL);
								if (blPrpTmainSubTmp.getSize() > 0 && "11".equals(blPrpTmainSubTmp.getArr(0).getFlag().substring(0, 2))) {
									strLogRiskCode = "B01";
									strLogBusinessNo = blPrpTmainSubTmp.getArr(0).getMainPolicyNo();
									blUtiOperateLogFacade.save(strLogSystemCode, strLogRiskCode, strLogBusinessType, strLogBusinessNo, intLogLogNo,
											strLogIsJFeeFlag, strLogIsAutoUnderWrite, strLogIsILog, strLogOperateType, strLogOperateTime, strLogComCode,
											strLogMakeCom, strLogOperatorCode, strLogIP);
								}
							}
						}
					}
				}

			} else {
				throw new UserException(-98, -3002, "UICommonCheckTaskFacade", getText("undwrt.action.commonCheckTask.overtimeLoginAgain"));
			}
			// 将直接调用BL层方法改为通过标准流程调用
			session.setAttribute("wfLogDto", wfLogDto);

			// 单证信息
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.businessNo", wfLogDto.getBusinessNo());
			List<UwMaterial> certifyList = uwMaterialService.getUwMaterialList(queryRule);
			session.setAttribute("certifyInfo", certifyList);

			// 根据业务号查找保单号
			businessNo = wfLogDto.getBusinessNo();
			messageId = prpallService.getMessageId(businessNo);
			req.setAttribute("messageId", messageId);
			if (AppConfig.get("sysconst.LOCAL_COMCODE").equals("DONGAN")) {
				wfLogHelperService.checkHistoryInfo(req);
			}

			// 获取危险单位信息及投承保相关信息
			wfLogHelperService.setDangerInfoToView(req);

			String affiliationNo = null;
			if (wfLogDto.getBusinessType().equals("T")) {
				PrpTmain pt = (PrpTmain) req.getAttribute("PrpTmainDto");
				affiliationNo = pt.getComCode();
			} else if (wfLogDto.getBusinessType().equals("E")) {
				PrpCmain pd = (PrpCmain) req.getAttribute("PrpCmainDto");
				affiliationNo = pd.getComCode();
			}
			
			PrpallCertifyTreeXml certifyTreeXml = new PrpallCertifyTreeXml();
			typeTreeXML = certifyTreeXml.getCertifyTree(iBusinessNo);
			paramString = certifyTreeXml.getParamString(affiliationNo, userCode, iBusinessNo, "prpins");
			remoteUrl = AppConfig.get("sysconst.FILEMANAGERIP") + "/filemanager/fileupload/FileUpload";

			// add by gss_20140306_影像资料上传
			HttpServletResponse response = this.getResponse();
			response.setContentType("text/html; charset=utf-8");
			try {
				if (iBusinessNo != null && !"".equals(iBusinessNo)) {
					PlatConfigRuleService platConfigRuleService = (PlatConfigRuleService) ServiceFactory.getService("platConfigRuleService");
					remoteUrl1 = platConfigRuleService.getPlatConfigRule("IMAGE_UPLOAD", "1");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// end_20140306_影像资料上传

			// 查找审核通过路径
			this.getPassPath(req);

			//獲取照會訊息
			String zhBusinessNo = iBusinessNo;
			if ("E".equals(iBusinessType)) {
				PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(iBusinessNo);
				zhBusinessNo = prpPhead.getPolicyNo();
				
				//mantis： FIR0351，處理人員：DP0713，需求單編號：保期起日新增檢核不得早於系統日六個月
				req.setAttribute("endorType", prpPhead.getEndorType());
			}
			zhList = commonCheckTaskService.getZHInfoVolist(iBusinessType, zhBusinessNo);
			reinsIP = SysConfig.getProperty("reinsIP");
			queryRule = QueryRule.getInstance();
			List<Prpdriskconfig> riskConfigList = new ArrayList<Prpdriskconfig>();
			queryRule.addEqual("id.configcode", "SAMERISK_ACCUMULATION_FLAG");
			queryRule.addEqual("id.riskCode",iRiskCode);
			riskConfigList = prpdriskconfigService.findByConditions(queryRule);
			if(riskConfigList.size()>0)
			{
				sameRiskFlag = riskConfigList.get(0).getConfigvalue();
			}
			else
			{
				sameRiskFlag="2";//默认不显示同险累积查看
			}
			
			//mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期 START
			if ("EL".equals(iRiskCode) && "E".equals(iBusinessType)){
				PrpPhead _prpPhead = policyService.getPrpPmainByEndorseNo(iBusinessNo);//for othPolicyNo
				PrpCPmain _prpCPmain = policyService.getPrpCPmainByPolicyNo(_prpPhead.getPolicyNo());
				List<PrpCPmainLiab> ptl = (List<PrpCPmainLiab>) _prpCPmain.getPrpCPmainLiabs();
				String othPolicyNo =null!=ptl&&null!=ptl.get(0)?ptl.get(0).getOthPolicyNo():null;
				req.setAttribute("isSameTime", true);//核心未輸入也不檢核
				if(null!=othPolicyNo){
					PrpCmain _prpCmain = policyService.getPrpCmainByPolicyNo(othPolicyNo);
					String pattern = "MM-dd-yyyy";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					String prpTmainDateString = simpleDateFormat.format(_prpCPmain.getStartDate())+" "+ _prpCPmain.getStartHour()+"~"+simpleDateFormat.format(_prpCPmain.getEndDate())+" "+ _prpCPmain.getEndHour();
					String prpCmainDateString = simpleDateFormat.format(_prpCmain.getStartDate())+" "+ _prpCmain.getStartHour()+"~"+simpleDateFormat.format(_prpCmain.getEndDate())+" "+ _prpCmain.getEndHour();
					if(!prpTmainDateString.equals(prpCmainDateString)){
						req.setAttribute("isSameTime", false);
					}
				}
			}
			//mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期 END

			// mantis：EGN0109，處理人員：DP0714，新增檢核保期起日不能超過三個月(含工程險、商火、水險) -- start
			if ("MC".equals(iRiskCode)) {
				String rationCode = "";
				if (wfLogDto.getBusinessType().equals("P")) {
					PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(iBusinessNo);
					if (prpCmain!=null && StringUtils.isNotBlank(prpCmain.getRationCode())) {
						rationCode = prpCmain.getRationCode();
					}
				} else if (wfLogDto.getBusinessType().equals("T")) {
					PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(iBusinessNo);
					if (prpTmain!=null && StringUtils.isNotBlank(prpTmain.getRationCode())) {
						rationCode = prpTmain.getRationCode();
					}
				} else if (wfLogDto.getBusinessType().equals("E")) {
					PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(iBusinessNo);
					if (prpPhead!=null && prpPhead.getPrpPmains()!=null && prpPhead.getPrpPmains().size()>0) {
						PrpPmain prpPmain = prpPhead.getPrpPmains().get(0);
						if (StringUtils.isNotBlank(prpPmain.getRationCode())) {
							rationCode = prpPmain.getRationCode();
						}
					}
				}
				req.setAttribute("rationCode", rationCode);
			}
			// mantis：EGN0109，處理人員：DP0714，新增檢核保期起日不能超過三個月(含工程險、商火、水險) -- end
			
			// 批单信息转向另外一个页面处理
			if ("P".equals(iBusinessType) && !"failure".equals(forward)) {
				forward = "PolicySuccess";
				return forward;
			}
			if ("E".equals(iBusinessType) && !"failure".equals(forward) && "Authorize".equals(authorize)) {
				forward = "showAuthorizeControlEndorse";// 授权任务审核
				return forward;
			}
			if ("E".equals(iBusinessType) && !"failure".equals(forward)) {
				forward = "EndorseSuccess";
				return forward;
			}
		} else {
			forward = "failure";
			content = getText("undwrt.action.commonCheckTask.workflowDataQueryFail");
		}
		//水险需求某张单子如果存在备注记录，用颜色进行标注
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("wfMessageId.messageId", businessNo);
		queryRule.addEqual("wfMessageId.lineNo", 1);
		queryRule.addEqual("wfMessageId.serialNo", 1);
		WfMessage wfMessage = wfMessageService.getUniqueMessage(queryRule);
		if(null!=wfMessage)
		{
			existMessage=true;
		}
		// 后去投保单号码并且验证此投保单是否是续保业务，如果是续保业务则将续保标记放入request中 begin
		BLPrpTrenewal blPrpTrenewal = new BLPrpTrenewal();
		blPrpTrenewal.getData(iBusinessNo);
		Vector PrpTrenewalSchemas = blPrpTrenewal.getSchemas();
		if (!"A01".equals(iRiskCode) && !"B01".equals(iRiskCode)) {
			String insuredCode = null;
			if ("T".equals(iBusinessType)) {
				PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(iBusinessNo);
				insuredCode = prpTmain.getInsuredCode();
				//tb险别不显示保险期间和起航日期20140715
				rationCode=prpTmain.getRationCode()==null?"":prpTmain.getRationCode();
			} else if ("E".equals(iBusinessType)) {
				PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(iBusinessNo);
				insuredCode = prpPhead.getInsuredCode();
				//tb险别不显示保险期间和起航日期20140715
				rationCode=prpPhead.getPrpPmains().get(0).getRationCode()==null?"":prpPhead.getPrpPmains().get(0).getRationCode();
			}
			existClaim = wfLogHelperService.isExistClaims(insuredCode);
		} else if (PrpTrenewalSchemas.size() > 0) { // 是续保业务投保单
			// isRenewal = true;
			String oldPolicyNo = ((PrpTrenewalSchema) PrpTrenewalSchemas.get(0)).getOldPolicyNo();
			req.setAttribute("oldPolicyNo", oldPolicyNo);
			// 如果该保单是续保业务则查询续保保单是否存在 存在立案
			try {
				isRenewal = wfLogHelperService.isExistPrplregis(oldPolicyNo);

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		req.setAttribute("isRenewal", isRenewal);
		
		//mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   start 原因  業務人員失效減核問題-核保系統檢核 
		PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(iBusinessNo);
		
		//mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期 START
		if ("EL".equals(iRiskCode) && "T".equals(iBusinessType)){
			PrpTmain _prpTmain = policyService.getPrpTmainByProposalNo(iBusinessNo);
			List<PrpTmainLiab> ptl = (List<PrpTmainLiab>) _prpTmain.getPrpTmainLiabs();
			String othPolicyNo =null!=ptl&&null!=ptl.get(0)?ptl.get(0).getOthPolicyNo():null;
			req.setAttribute("isSameTime", true);//核心未輸入也不檢核
			if(null!=othPolicyNo){
				PrpCmain _prpCmain = policyService.getPrpCmainByPolicyNo(othPolicyNo);
				String pattern = "MM-dd-yyyy";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String prpTmainDateString = simpleDateFormat.format(_prpTmain.getStartDate())+" "+ _prpTmain.getStartHour()+"~"+simpleDateFormat.format(_prpTmain.getEndDate())+" "+ _prpTmain.getEndHour();
				String prpCmainDateString = simpleDateFormat.format(_prpCmain.getStartDate())+" "+ _prpCmain.getStartHour()+"~"+simpleDateFormat.format(_prpCmain.getEndDate())+" "+ _prpCmain.getEndHour();
				if(!prpTmainDateString.equals(prpCmainDateString)){
					req.setAttribute("isSameTime", false);
				}
			}
		}
		//mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期 END
		//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理START
		if("T".equals(iBusinessType)){
			String validRiskC ="AB、AR、AT、BN、CN、EL、EM、ER、MN、PB、PR、SC、TC、TD、TL";
			if(validRiskC.indexOf(iRiskCode) != -1){
				Calendar sysTime= Calendar.getInstance();
				sysTime.add(Calendar.MONTH, 3);
				sysTime.set(Calendar.HOUR_OF_DAY, 23);
				sysTime.set(Calendar.MINUTE, 59);
				sysTime.set(Calendar.SECOND, 59);
				sysTime.set(Calendar.MILLISECOND, 0);
				if(!"AR".equals(iRiskCode)){
					Calendar startTime = Calendar.getInstance();
					startTime.setTime(prpTmain.getStartDate());
					startTime.set(Calendar.HOUR_OF_DAY, 23);
					startTime.set(Calendar.MINUTE, 59);
					startTime.set(Calendar.SECOND, 59);
					startTime.set(Calendar.MILLISECOND, 0);
					if(startTime.after(sysTime)){
						req.setAttribute("overThreeMonthMsg", "保單起始日超過三個月，請確認資料!");
					}
				}else{
					//mantis： LIA0327，處理人員：DP0706，需求單編號：LIA0327START
					//取AR險:館藏品保險期間/參展品保險期間/運送品保險期間
					String arMsg = "";
					Map<String, Object> arMap = commonCheckTaskService.queryARStartDate(iBusinessNo);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					
					String museum = "";//館藏品保險期間
					if(arMap.containsKey("MUSEUMSTARTDATE")){
						museum = (String) arMap.get("MUSEUMSTARTDATE");
						if(StringUtils.isNotBlank(museum)){
							Date museumStartDate = sdf.parse(museum);
							Calendar startTime = Calendar.getInstance();
							startTime.setTime(museumStartDate);
							startTime.set(Calendar.HOUR_OF_DAY, 23);
							startTime.set(Calendar.MINUTE, 59);
							startTime.set(Calendar.SECOND, 59);
							startTime.set(Calendar.MILLISECOND, 0);
							if(startTime.after(sysTime)){
								arMsg ="館藏品";
							}
						}
						
					}
							
					
					String show = "";//參展品保險期間
					if(arMap.containsKey("SHOWSTARTDATE")){
						show = (String) arMap.get("SHOWSTARTDATE");
						if(StringUtils.isNotBlank(show)){
							Date showStartDate  = sdf.parse(show);					
							Calendar startTime = Calendar.getInstance();
							startTime.setTime(showStartDate);
							startTime.set(Calendar.HOUR_OF_DAY, 23);
							startTime.set(Calendar.MINUTE, 59);
							startTime.set(Calendar.SECOND, 59);
							startTime.set(Calendar.MILLISECOND, 0);
							if(startTime.after(sysTime)){
								if("".equals(arMsg)){
									arMsg ="參展品";
								} else {
									arMsg +="、參展品";
								}	
							}
						}
					}
							
					
					
					String transport = "";//參展品保險期間
					if(arMap.containsKey("TRANSPORTSTARTDATE")){
						transport =(String) arMap.get("TRANSPORTSTARTDATE");
						if(StringUtils.isNotBlank(transport)){
							Date transportStartDate = sdf.parse(transport);				
							Calendar startTime = Calendar.getInstance();
							startTime.setTime(transportStartDate);
							startTime.set(Calendar.HOUR_OF_DAY, 23);
							startTime.set(Calendar.MINUTE, 59);
							startTime.set(Calendar.SECOND, 59);
							startTime.set(Calendar.MILLISECOND, 0);
							if(startTime.after(sysTime)){
								if("".equals(arMsg)){
									arMsg ="運送品";
								} else {
									arMsg +="、運送品";
								}	
							}
						}
					}
					//mantis： LIA0327，處理人員：DP0706，需求單編號：LIA0327END
					
					if(!"".equals(arMsg)){
						req.setAttribute("overThreeMonthMsg", "保單"+arMsg+"保險期間起始日超過三個月，請確認資料!");
					}
				}
			}
		}
		//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理END
		
		boolean validIdentifyNumber = commonCheckTaskService.checkValidsTatus(prpTmain);
		boolean validstatusUsercode = commonCheckTaskService.checkPrpduser(prpTmain.getHandler1Code());
		req.setAttribute("validIdentifyNumber", validIdentifyNumber);
		req.setAttribute("validstatusUsercode", validstatusUsercode);
		//mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   end
		
		// 后去投保单号码并且验证此投保单是否是续保业务，如果是续保业务则将续保标记放入request中 end
		return forward;
	}

	/**
	 * 是否有臨分信息.
	 * 
	 * @param req
	 *            請求對象
	 * @return 有返回true，沒有返回false
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public boolean haveFacInformation(HttpServletRequest req) throws SQLException, Exception {
		// TODO Auto-generated method stub
		String businessNo = req.getParameter("businessNo");
		String businessType = req.getParameter("businessType");
		String dangerNo = req.getParameter("dangerNo");
		if (businessType.equals("T")) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("proposalNo", businessNo);
			queryRule.addEqual("dangerNo", Integer.parseInt(dangerNo));
			int count = feoEnquiryService.findByConditions(queryRule).size();
			if (count != 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 合並拆分危險單位程序 ,生成危險單位拆分頁面信息.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void setDangerDetailToViewByDangerNo(HttpServletRequest req) throws Exception {

		String businessNo = req.getParameter("businessNo");
		String businessType = req.getParameter("businessType");
		String dangerNo = req.getParameter("dangerNo");
		String riskCode = req.getParameter("riskCode");
		String enterFlag = req.getParameter("enterFlag");
		String riskLevelDesc = ""; // 风险类别的描述
		Collection dangerDetail = null;
		Collection planCurrencyType = null;
		Collection dangerExItemKind = null;
		Collection fhRetenList = null;
		String uwYear = "";
		String flag = "0";
		QueryRule queryRule = QueryRule.getInstance();

		String underwriteFlag = "";
		if (businessType.equals("T")) {
			PrpTmain prpTmain = prpallService.getPrpTmain(businessNo, businessType);
			req.removeAttribute("prpTmainDto");
			req.setAttribute("prpTmainDto", prpTmain);// 详细信息页面使用
			underwriteFlag = prpTmain.getUnderWriteFlag();
			uwYear = prpTmain.getStartDate().toString().substring(0, 4);
			queryRule.addEqual("id.uwYear", uwYear);
			queryRule.addEqual("id.riskCode", riskCode);
			fhRetenList = fhRetenService.findByConditions(queryRule);
			if (fhRetenList.size() > 0 && fhRetenList != null) {
				Iterator iterator = fhRetenList.iterator();
				while (iterator.hasNext()) {
					FhReten fhRetenDto = (FhReten) iterator.next();
					if (fhRetenDto.getFlag().equals("0")) {
						flag = "0";
					} else {
						flag = "1";
					}
					break;
				}
			}
		} else if (businessType.equals("E")) {
			PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
			underwriteFlag = prpPhead.getUnderWriteFlag();
			PrpCmain prpCmain = prpallService.getPrpCmain(prpPhead.getPolicyNo());
			uwYear = prpCmain.getStartDate().toString().substring(0, 4);
			queryRule.addEqual("id.uwYear", uwYear);
			queryRule.addEqual("id.riskCode", riskCode);
			fhRetenList = fhRetenService.findByConditions(queryRule);
			if (fhRetenList.size() > 0 && fhRetenList != null) {
				Iterator iterator = fhRetenList.iterator();
				while (iterator.hasNext()) {
					FhReten fhRetenDto = (FhReten) iterator.next();
					if (fhRetenDto.getFlag().equals("0")) {
						flag = "0";
					} else {
						flag = "1";
					}
					break;
				}
			}
			// 修改未出险的业务不能进行重新风险评估
			String haveClaim = "N";
			String policyNo = prpPhead.getPolicyNo();
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("policyNo", policyNo);
			Collection prpLdangerUnitList = prpLDangerUnitService.findByConditions(queryRule);
			if (prpLdangerUnitList != null && prpLdangerUnitList.size() > 0) {
				haveClaim = "Y";
			}
			req.setAttribute("haveClaim", haveClaim);

		} else if (businessType.equals("P")) {
			PrpCmain prpCmain = prpallService.getPrpCmain(businessNo);
			underwriteFlag = prpCmain.getUnderWriteFlag();
			uwYear = prpCmain.getStartDate().toString().substring(0, 4);
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("uwYear", uwYear);
			queryRule.addEqual("riskcode", riskCode);
			fhRetenList = fhRetenService.findByConditions(queryRule);
			if (fhRetenList.size() > 0 && fhRetenList != null) {
				Iterator iterator = fhRetenList.iterator();
				while (iterator.hasNext()) {
					FhReten fhRetenDto = (FhReten) iterator.next();
					if (fhRetenDto.getFlag().equals("0")) {
						flag = "0";
					} else {
						flag = "1";
					}
					break;
				}
			}
		}
		if (enterFlag == null || enterFlag.equals("") || !enterFlag.equals("1")) {
			if (underwriteFlag.equals("1") || underwriteFlag.equals("2") || underwriteFlag.equals("3")) {
				throw new UserException(-98, -3001, "UICommonDangerInfoAction.setDangerDetailToViewByDangerNo()",
						getText("undwrt.action.commonCheckTask.sendedReinsure"));
			}
		}

		if (businessType.equals("E")) {
			dangerNo = req.getParameter("hiDangerNo");
		}

		// 主要初始化风险等级等页面上有的信息
		this.initDangerInfo(businessNo, businessType, dangerNo, riskCode);
		dangerDetail = prpallService.getDangerDetail(businessType, businessNo, dangerNo);
		req.removeAttribute("DangerDetail");
		req.setAttribute("DangerDetail", dangerDetail);

		// 查询出fhreten表中 自留额的最低限额和最高限额，并传到页面中。
		if (businessType.equals("T")) {
			PrpTDangerUnitVO prpTdangerUnitDto = (PrpTDangerUnitVO) (dangerDetail.iterator().next());
			riskLevelDesc = prpTdangerUnitDto.getRiskLevelDesc();
		} else if (businessType.equals("P")) {
			PrpCDangerUnitVO prpCdangerUnitDto = (PrpCDangerUnitVO) (dangerDetail.iterator().next());
			riskLevelDesc = prpCdangerUnitDto.getRiskLevelDesc();
		} else if (businessType.equals("E")) {
			PrpPDangerUnitVO prpPdangerUnitDto = (PrpPDangerUnitVO) (dangerDetail.iterator().next());
			riskLevelDesc = prpPdangerUnitDto.getRiskLevelDesc();
		}

		String strConditon = " riskCode ='" + riskCode + "' and riskLevelDesc ='" + riskLevelDesc + "' and uwYear ='" + uwYear + "'";
		Collection retenValueColl = commonDangerInfoService.getRetenValue(strConditon);
		if (retenValueColl != null && retenValueColl.size() == 1) {
			FhReten fhretenDto = (FhReten) retenValueColl.iterator().next();
			String lowRetentionValue = fhretenDto.getLowRetentionValue() + "";
			String heiRetentionValue = fhretenDto.getRetentionValue() + "";
			req.setAttribute("lowRetentionValue", lowRetentionValue);
			req.setAttribute("heiRetentionValue", heiRetentionValue);
			System.out.println(lowRetentionValue);
		} else {
			req.setAttribute("lowRetentionValue", "0.0");
			req.setAttribute("heiRetentionValue", "0.0");
		}

		// 生成标的项
		this.getDangerItemToView(businessNo, dangerNo, businessType, req);

		planCurrencyType = prpallService.getPlanCurrencyType(businessNo, businessType);
		if (planCurrencyType != null) {
			req.setAttribute("planCurrencyType", planCurrencyType);
		}

		// 生成除外责任
		dangerExItemKind = commonDangerInfoService.getDangerExItemKind(riskCode);
		req.setAttribute("dangerExItemKind", dangerExItemKind);
		req.setAttribute("dangerExItemKind2", dangerExItemKind);
		req.setAttribute("flag", flag);
	}

	/**
	 * 初始化危險單位信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @param dangerNo
	 *            危險單位號
	 * @param riskCode
	 *            險種代碼
	 * @throws Exception
	 *             異常
	 */
	public void initDangerInfo(String businessNo, String businessType, String dangerNo, String riskCode) throws Exception {
		ArrayList dangerUnitList = new ArrayList();

		if (businessType.equals("T")) {
			PrpTDangerUnit prpTdangerUnitDto = new PrpTDangerUnit();
			PrpTmain prpTmain = prpallService.getPrpTmain(businessNo, businessType);
			// 增加显示一类风险的风险等级，风险名称，自留额，自留额币别显示
			PrpTDangerUnit tempPrpTDangerUnitDto = prpallService.getDangerRiskLevel("T", businessNo, "1");
			// 获取已存在的危险单位
			PrpTDangerUnit dangerUnitDto = prpallService.getDangerRiskLevel("T", businessNo, dangerNo);
			if (dangerUnitDto != null) {// 以前存在的危险单位
				if (dangerUnitDto.getRiskCode().equals(riskCode)) {
					// 页面存在的riskcode和库里的riskcode相等，证明没有修改riskcode。从×dangerunit里面获取风险等级即可
					return;
				}
			}
			// 从fhreten里面获取默认风险等级
			Collection fhReten = this.getRetenValue("riskclass ='126' and riskcode ='" + riskCode + "'" + " and uwyear ='" + prpTmain.getStartDate().getYear()
					+ "'");
			Iterator iterator = fhReten.iterator();
			while (iterator.hasNext()) {
				FhReten fhRetenDto = (FhReten) iterator.next();
				prpTdangerUnitDto.setReTCurrency(fhRetenDto.getCurrency());
				prpTdangerUnitDto.setRiskLevel(fhRetenDto.getRiskLevel());
				prpTdangerUnitDto.setRiskLevelDesc(fhRetenDto.getRiskLevelDesc());
				prpTdangerUnitDto.setRetentionValue(fhRetenDto.getRetentionValue());
			}

			if (riskCode.substring(0, 2).equals("27") || riskCode.substring(0, 2).equals("15") || riskCode.equals("2201") || riskCode.equals("0109")) {
				prpTdangerUnitDto.setSpeCurrency(tempPrpTDangerUnitDto.getSpeCurrency());
				prpTdangerUnitDto.setSpeValue(tempPrpTDangerUnitDto.getSpeValue());
			}
			PrpTDangerUnitId id = new PrpTDangerUnitId();
			id.setProposalNo(businessNo);
			id.setDangerNo(Integer.parseInt(dangerNo));
			prpTdangerUnitDto.setId(id);
			prpTdangerUnitDto.setRiskCode(riskCode);
			prpTdangerUnitDto.setCoinsFlag(prpTmain.getCoinsFlag());
			prpTdangerUnitDto.setShareholderFlag(prpTmain.getShareHolderFlag());
			prpTdangerUnitDto.setBusinessFlag(prpTmain.getBusinessflag());
			prpTdangerUnitDto.setBusinessNature(prpTmain.getBusinessNature());
			prpTdangerUnitDto.setChannelType(prpTmain.getChannelType());
			List<PrpTitemCarExt> vPrpTitemCarExt = policyService.getPrpTmainByProposalNo(prpTmain.getProposalNo()).getPrpTitemCarExts();
			if (vPrpTitemCarExt.size() == 0) {
				prpTdangerUnitDto.setCarTypeCode("");
			} else {
				PrpTitemCarExt prpTitemCarExt = (PrpTitemCarExt) vPrpTitemCarExt.get(0);
				prpTdangerUnitDto.setCarTypeCode(prpTitemCarExt.getCarTypeCode());
			}
			if (null != prpTmain.getExchangeRate()) {
				prpTdangerUnitDto.setExchRateCNY((prpTmain.getExchangeRate()).doubleValue());
			}
			dangerUnitList.add(prpTdangerUnitDto);
			this.saveDangerUnit(dangerUnitList, businessType);
		}
		if (businessType.equals("P")) {
			PrpCDangerUnit prpCdangerUnitDto = new PrpCDangerUnit();
			PrpCmain prpCmain = prpallService.getPrpCmain(businessNo);
			// 增加显示一类风险的风险等级，风险名称，自留额，自留额币别显示 begin
			PrpCDangerUnit tempPrpCDangerUnitDto = prpallService.getCDangerRiskLevel("P", businessNo, "1");
			// 获取已存在的危险单位
			PrpCDangerUnit dangerUnitDto = prpallService.getCDangerRiskLevel("P", businessNo, dangerNo);
			if (dangerUnitDto != null) {// 以前存在的危险单位
				if (dangerUnitDto.getRiskCode().equals(riskCode)) {
					// 页面存在的riskcode和库里的riskcode相等，证明没有修改riskcode。从×dangerunit里面获取风险等级即可
					return;
				}
			}
			// 从fhreten里面获取默认风险等级
			Collection fhReten = this.getRetenValue("riskclass ='126' and riskcode ='" + riskCode + "'" + " and uwyear ='" + prpCmain.getStartDate().getYear()
					+ "'");
			Iterator iterator = fhReten.iterator();
			while (iterator.hasNext()) {
				FhReten fhRetenDto = (FhReten) iterator.next();
				prpCdangerUnitDto.setReTCurrency(fhRetenDto.getCurrency());
				prpCdangerUnitDto.setRiskLevel(fhRetenDto.getRiskLevel());
				prpCdangerUnitDto.setRiskLevelDesc(fhRetenDto.getRiskLevelDesc());
				prpCdangerUnitDto.setRetentionValue(fhRetenDto.getRetentionValue());

			}
			PrpCDangerUnitId id = new PrpCDangerUnitId();
			id.setPolicyNo(businessNo);
			id.setDangerNo(Integer.parseInt(dangerNo));
			prpCdangerUnitDto.setId(id);
			prpCdangerUnitDto.setRiskCode(riskCode);
			prpCdangerUnitDto.setCoinsFlag(prpCmain.getCoinsFlag());
			prpCdangerUnitDto.setShareholderFlag(prpCmain.getShareHolderFlag());
			prpCdangerUnitDto.setBusinessFlag(prpCmain.getBusinessFlag());
			if (riskCode.substring(0, 2).equals("27") || riskCode.substring(0, 2).equals("15") || riskCode.equals("2201") || riskCode.equals("0109")) {
				prpCdangerUnitDto.setSpeCurrency(tempPrpCDangerUnitDto.getSpeCurrency());
				prpCdangerUnitDto.setSpeValue(tempPrpCDangerUnitDto.getSpeValue());
			}

			// 增加显示一类风险的风险等级，风险名称，自留额，自留额币别显示 begin
			dangerUnitList.add(prpCdangerUnitDto);
			this.saveDangerUnit(dangerUnitList, businessType);
		}
		if (businessType.equals("C")) {

		}

		if (businessType.equals("E")) {
			PrpPDangerUnit prpPdangerUnitDto = new PrpPDangerUnit();
			PrpPmain prpPmain = endorseService.getPrpPheadByEndorseNo(businessNo).getPrpPmains().get(0);
			// 增加显示一类风险的风险等级，风险名称，自留额，自留额币别显示 begin
			PrpPDangerUnit tempPrpPDangerUnitDto = prpallService.getPDangerRiskLevel("E", businessNo, "1");
			// 获取已存在的危险单位
			PrpPDangerUnit dangerUnitDto = prpallService.getPDangerRiskLevel("E", businessNo, dangerNo);
			if (dangerUnitDto != null) {// 以前存在的危险单位
				if (dangerUnitDto.getRiskCode().equals(riskCode)) {
					// 页面存在的riskcode和库里的riskcode相等，证明没有修改riskcode。从×dangerunit里面获取风险等级即可
					return;
				}
			}
			// ///
			// 从fhreten里面获取默认风险等级
			Collection fhReten = this.getRetenValue("riskclass ='126' and riskcode ='" + riskCode + "'" + " and uwyear ='" + prpPmain.getStartDate().getYear()
					+ "'");
			Iterator iterator = fhReten.iterator();
			while (iterator.hasNext()) {
				FhReten fhRetenDto = (FhReten) iterator.next();
				prpPdangerUnitDto.setReTCurrency(fhRetenDto.getCurrency());
				prpPdangerUnitDto.setRiskLevel(fhRetenDto.getRiskLevel());
				prpPdangerUnitDto.setRiskLevelDesc(fhRetenDto.getRiskLevelDesc());
				prpPdangerUnitDto.setRetentionValue(fhRetenDto.getRetentionValue());
			}
			PrpPDangerUnitId id = new PrpPDangerUnitId();
			id.setEndorseNo(businessNo);
			id.setDangerNo(Integer.parseInt(dangerNo));
			prpPdangerUnitDto.setId(id);
			prpPdangerUnitDto.setPolicyNo(prpPmain.getPolicyNo());
			prpPdangerUnitDto.setRiskCode(riskCode);
			prpPdangerUnitDto.setCurrency(prpPmain.getCurrency());
			prpPdangerUnitDto.setCoinsFlag(prpPmain.getCoinsFlag());
			prpPdangerUnitDto.setShareholderFlag(prpPmain.getShareHolderFlag());
			prpPdangerUnitDto.setBusinessFlag(prpPmain.getBusinessFlag());

			if (riskCode.substring(0, 2).equals("27") || riskCode.substring(0, 2).equals("15") || riskCode.equals("2201") || riskCode.equals("0109")) {
				prpPdangerUnitDto.setSpeCurrency(tempPrpPDangerUnitDto.getSpeCurrency());
				prpPdangerUnitDto.setSpeValue(tempPrpPDangerUnitDto.getSpeValue());
			}

			// modify begin by lihua 2008-10-28 少车型
			List<PrpCitemCarExt> vPrpCitemCarExt = policyService.getPrpCmainByPolicyNo(prpPmain.getPolicyNo()).getPrpCitemCarExts();
			if (vPrpCitemCarExt.size() == 0) {
				prpPdangerUnitDto.setCarTypeCode("");
			} else {
				PrpCitemCarExt prpCitemCarExt = (PrpCitemCarExt) vPrpCitemCarExt.get(0);
				prpPdangerUnitDto.setCarTypeCode(prpCitemCarExt.getCartypeCode());
			}

			prpPdangerUnitDto.setBusinessNature(prpPmain.getBusinessNature());
			prpPdangerUnitDto.setChannelType(prpPmain.getChannelType());
			prpPdangerUnitDto.setExchRateCNY((prpPmain.getExchangeRate()).doubleValue());

			// modify end by lihua 2008-10-28
			prpPdangerUnitDto.setSpeCurrency(tempPrpPDangerUnitDto.getSpeCurrency());
			prpPdangerUnitDto.setSpeValue(tempPrpPDangerUnitDto.getSpeValue());
			// 增加显示一类风险的风险等级，风险名称，自留额，自留额币别显示
			dangerUnitList.add(prpPdangerUnitDto);
			this.saveDangerUnit(dangerUnitList, businessType);
		}
	}

	/**
	 * 保存壹個危險單位主信息.
	 * 
	 * @param dangerList
	 *            危險單位信息
	 * @param businessType
	 *            業務類型
	 * @throws Exception
	 *             異常
	 */
	public void saveDangerUnit(ArrayList dangerList, String businessType) throws Exception {
		try {
			if (businessType.equals("T")) {
				prpallService.savePrpTdangerUnit(dangerList);
			}
			if (businessType.equals("P")) {
				prpallService.savePrpCdangerUnit(dangerList);
			}
			if (businessType.equals("E")) {
				prpallService.savePrpPdangerUnit(dangerList);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 取自留額信息
	 * 
	 * @param strCondition
	 *            查詢條件
	 * @return 自留額信息
	 * @throws Exception
	 */

	public Collection getRetenValue(String strConditon) throws Exception {
		return blReinsService.getFhRetenValue(strConditon);

	}

	/*
	 * 分保意向中取每个危险单位的相关信息
	 */

	/**
	 * 把危險單位信息拆分到頁面.
	 * 
	 * @param certiNo
	 *            業務號
	 * @param certiType
	 *            業務類型
	 * @throws Exception
	 *             異常
	 */
	public void setDangerInfoToViewByReins(String certiNo, String certiType) throws Exception {
		HttpSession session = this.getSession();
		session.setAttribute("CertiNo", certiNo);
		session.setAttribute("CertiType", certiType);
	}

	/**
	 * 取得以某節點爲起始節點的滿足條件的路徑.
	 * 
	 * @param wfLog
	 *            日誌工作流類
	 * @return 滿足條件的路徑
	 * @throws Exception
	 *             異常
	 */
	public void getPassPath(HttpServletRequest request) throws Exception {
		String iModelNo = StringUtils.trimToEmpty(request.getParameter("iModelNo"));
		String iNodeNo = StringUtils.trimToEmpty(request.getParameter("iNodeNo"));
		WfLog wfLogDto = new WfLog();
		wfLogDto.setModelNo(Integer.parseInt(iModelNo));
		wfLogDto.setNodeNo(Integer.parseInt(iNodeNo));
		swfPath = swfPathService.getPassPath(wfLogDto);
		request.setAttribute("SwfPathDto", swfPath);
	}

	/**
	 * 獲取危險單位的子信息數據到頁面.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @param businessType
	 *            業務類型
	 * @param req
	 *            請求對象
	 * @return 危險單位的子信息
	 * @throws Exception
	 *             異常
	 */
	public void getDangerItemToView(String businessNo, String dangerNo, String businessType, HttpServletRequest req) throws Exception {
		Collection dangerItemList = new ArrayList();

		dangerItemList = prpallService.getDangerItemList(businessNo, dangerNo, businessType);
		req.removeAttribute("ItemKind");
		req.setAttribute("ItemKind", dangerItemList);
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

		strWhere = " funtype = 'ILog' And recordtype = 'ILog' And riskCode = '" + iRiskCode + "' " + " And comcode in (" + strWhereCom + ")"
				+ " And validStatus = '1'";
		// 是否使用ILog配置在PrpDconfigCode表里面。
		BLPrpDconfigCode blPrpDconfigCode = new BLPrpDconfigCode();
		blPrpDconfigCode.query(strWhere);
		if (blPrpDconfigCode.getSize() > 0) {
			isILog = true;
		}
		return isILog;
	}
	//责任险
	public void addHbNotion()
	{
		if("C".equals(iClassCode))
		{
			PrpDcode PrpDcode = new PrpDcode();
			PrpDcodeId prpDcodeId = new PrpDcodeId();
			prpDcodeId.setCodeCode("008");
			prpDcodeId.setCodeCName(getText("undwrt.CommonDealContent.HbNotion"));
			prpDcodeId.setCodeType("HbNotionCode");
			prpDcodeId.setNewCodeCode("008");
			prpDcodeId.setValidStatus("1");
			PrpDcode.setId(prpDcodeId);
			notionCodeList.add(PrpDcode);
		}
	}
	
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	/**
	 * 獲取屬性處理類型.
	 * 
	 * @return 屬性處理類型的值
	 */
	public String getHandType() {
		return handType;
	}

	/**
	 * 設置屬性處理類型.
	 * 
	 * @param handType
	 *            待設置的處理類型的值
	 */
	public void setHandType(String handType) {
		this.handType = handType;
	}

	/**
	 * 獲取屬性編輯類型.
	 * 
	 * @return 屬性編輯類型的值
	 */
	public String getEditType() {
		return editType;
	}

	/**
	 * 設置屬性編輯類型.
	 * 
	 * @param editType
	 *            待設置的編輯類型的值
	 */
	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * 獲取屬性編輯標題.
	 * 
	 * @return 屬性編輯標題的值
	 */
	public String getEditTitle() {
		return editTitle;
	}

	/**
	 * 設置屬性編輯標題.
	 * 
	 * @param editTitle
	 *            待設置的編輯標題的值
	 */
	public void setEditTitle(String editTitle) {
		this.editTitle = editTitle;
	}

	/**
	 * 獲取屬性標題.
	 * 
	 * @return 屬性標題的值
	 */
	public String getHandTitle() {
		return handTitle;
	}

	/**
	 * 設置屬性標題.
	 * 
	 * @param handTitle
	 *            待設置的標題的值
	 */
	public void setHandTitle(String handTitle) {
		this.handTitle = handTitle;
	}

	/**
	 * 獲取屬性業務類型.
	 * 
	 * @return 屬性業務類型的值
	 */
	public String getiBusinessType() {
		return iBusinessType;
	}

	/**
	 * 設置屬性業務類型.
	 * 
	 * @param iBusinessType
	 *            待設置的業務類型的值
	 */
	public void setiBusinessType(String iBusinessType) {
		this.iBusinessType = iBusinessType;
	}

	/**
	 * 獲取屬性業務號.
	 * 
	 * @return 屬性業務號的值
	 */
	public String getiBusinessNo() {
		return iBusinessNo;
	}

	/**
	 * 設置屬性業務號.
	 * 
	 * @param iBusinessNo
	 *            待設置的業務號的值
	 */
	public void setiBusinessNo(String iBusinessNo) {
		this.iBusinessNo = iBusinessNo;
	}

	/**
	 * 獲取屬性險種名稱.
	 * 
	 * @return 屬性險種名稱的值
	 */
	public String getRiskCName() {
		return riskCName;
	}

	/**
	 * 設置屬性險種名稱.
	 * 
	 * @param riskCName
	 *            待設置的險種名稱的值
	 */
	public void setRiskCName(String riskCName) {
		this.riskCName = riskCName;
	}

	/**
	 * 獲取屬性工作流號.
	 * 
	 * @return 屬性工作流號的值
	 */
	public String getiFlowID() {
		return iFlowID;
	}

	/**
	 * 設置屬性工作流號.
	 * 
	 * @param iFlowID
	 *            待設置的工作流號的值
	 */
	public void setiFlowID(String iFlowID) {
		this.iFlowID = iFlowID;
	}

	/**
	 * 獲取屬性查看業務詳細信息的ip.
	 * 
	 * @return 屬性查看業務詳細信息的ip的值
	 */
	public String getiPrpallIp() {
		return iPrpallIp;
	}

	/**
	 * 設置屬性查看業務詳細信息的ip.
	 * 
	 * @param iPrpallIp
	 *            待設置的查看業務詳細信息的ip的值
	 */
	public void setiPrpallIp(String iPrpallIp) {
		this.iPrpallIp = iPrpallIp;
	}

	/**
	 * 獲取屬性模板號.
	 * 
	 * @return 屬性模板號的值
	 */
	public String getiModelNo() {
		return iModelNo;
	}

	/**
	 * 設置屬性模板號.
	 * 
	 * @param iModelNo
	 *            待設置的模板號的值
	 */
	public void setiModelNo(String iModelNo) {
		this.iModelNo = iModelNo;
	}

	/**
	 * 獲取屬性節點號.
	 * 
	 * @return 屬性節點號的值
	 */
	public String getiNodeNo() {
		return iNodeNo;
	}

	/**
	 * 設置屬性節點號.
	 * 
	 * @param iNodeNo
	 *            待設置的節點號的值
	 */
	public void setiNodeNo(String iNodeNo) {
		this.iNodeNo = iNodeNo;
	}

	/**
	 * 獲取屬性序號.
	 * 
	 * @return 屬性序號的值
	 */
	public String getiLogNo() {
		return iLogNo;
	}

	/**
	 * 設置屬性序號.
	 * 
	 * @param iLogNo
	 *            待設置的序號的值
	 */
	public void setiLogNo(String iLogNo) {
		this.iLogNo = iLogNo;
	}

	/**
	 * 獲取屬性險種代碼.
	 * 
	 * @return 屬性險種代碼的值
	 */
	public String getiRiskCode() {
		return iRiskCode;
	}

	/**
	 * 設置屬性險種代碼.
	 * 
	 * @param iRiskCode
	 *            待設置的險種代碼的值
	 */
	public void setiRiskCode(String iRiskCode) {
		this.iRiskCode = iRiskCode;
	}

	/**
	 * 獲取屬性險類代碼.
	 * 
	 * @return 屬性險類代碼的值
	 */
	public String getiClassCode() {
		return iClassCode;
	}

	/**
	 * 設置屬性險類代碼.
	 * 
	 * @param iClassCode
	 *            待設置的險類代碼的值
	 */
	public void setiClassCode(String iClassCode) {
		this.iClassCode = iClassCode;
	}

	/**
	 * 獲取屬性險別標誌.
	 * 
	 * @return 屬性險別標誌的值
	 */
	public String getShowDangerItemFlag() {
		return showDangerItemFlag;
	}

	/**
	 * 設置屬性險別標誌.
	 * 
	 * @param showDangerItemFlag
	 *            待設置的險別標誌的值
	 */
	public void setShowDangerItemFlag(String showDangerItemFlag) {
		this.showDangerItemFlag = showDangerItemFlag;
	}

	/**
	 * 獲取屬性審批片語.
	 * 
	 * @return 屬性審批片語的值
	 */
	public List<PrpDcode> getNotionCodeList() {
		return notionCodeList;
	}

	/**
	 * 設置屬性審批片語.
	 * 
	 * @param notionCodeList
	 *            待設置的審批片語的值
	 */
	public void setNotionCodeList(List<PrpDcode> notionCodeList) {
		this.notionCodeList = notionCodeList;
	}

	/**
	 * 獲取屬性危險單位主信息.
	 * 
	 * @return 屬性危險單位主信息的值
	 */
	public List getDangerDetail() {
		return dangerDetail;
	}

	/**
	 * 設置屬性危險單位主信息.
	 * 
	 * @param dangerDetail
	 *            待設置的危險單位主信息的值
	 */
	public void setDangerDetail(List dangerDetail) {
		this.dangerDetail = dangerDetail;
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
	 * 獲取屬性工作流路徑.
	 * 
	 * @return 屬性工作流路徑的值
	 */
	public SwfPath getSwfPath() {
		return swfPath;
	}

	/**
	 * 設置屬性工作流路徑.
	 * 
	 * @param swfPath
	 *            待設置的工作流路徑的值
	 */
	public void setSwfPath(SwfPath swfPath) {
		this.swfPath = swfPath;
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
	 * 獲取屬性機構中文名稱.
	 * 
	 * @return 屬性機構中文名稱的值
	 */
	public String getComCName() {
		return comCName;
	}

	/**
	 * 設置屬性機構中文名稱.
	 * 
	 * @param comCName
	 *            待設置的機構中文名稱的值
	 */
	public void setComCName(String comCName) {
		this.comCName = comCName;
	}

	/**
	 * 獲取屬性業務號.
	 * 
	 * @return 屬性業務號的值
	 */
	public String getCertiNo() {
		return certiNo;
	}

	/**
	 * 設置屬性業務號.
	 * 
	 * @param certiNo
	 *            待設置的業務號的值
	 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	/**
	 * 獲取屬性業務類型.
	 * 
	 * @return 屬性業務類型的值
	 */
	public String getCertiType() {
		return certiType;
	}

	/**
	 * 設置屬性業務類型.
	 * 
	 * @param certiType
	 *            待設置的業務類型的值
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * 獲取屬性授權.
	 * 
	 * @return 屬性授權的值
	 */
	public String getAuthorize() {
		return authorize;
	}

	/**
	 * 設置屬性授權.
	 * 
	 * @param authorize
	 *            待設置的授權的值
	 */
	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}

	/**
	 * 獲取屬性保單號.
	 * 
	 * @return 屬性保單號的值
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * 設置屬性保單號.
	 * 
	 * @param messageId
	 *            待設置的保單號的值
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
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
	 * 獲取屬性處理信息.
	 * 
	 * @return 屬性處理信息的值
	 */
	public WfLog getDealInfo() {
		return dealInfo;
	}

	/**
	 * 設置屬性處理信息.
	 * 
	 * @param dealInfo
	 *            待設置的處理信息的值
	 */
	public void setDealInfo(WfLog dealInfo) {
		this.dealInfo = dealInfo;
	}

	/**
	 * 獲取屬性核保處理意見.
	 * 
	 * @return 屬性核保處理意見的值
	 */
	public List<UwNotion> getUwNotionList() {
		return uwNotionList;
	}

	/**
	 * 設置屬性核保處理意見.
	 * 
	 * @param uwNotionList
	 *            待設置的核保處理意見的值
	 */
	public void setUwNotionList(List<UwNotion> uwNotionList) {
		this.uwNotionList = uwNotionList;
	}

	/**
	 * 獲取屬性報價單信息.
	 * 
	 * @return 屬性報價單信息的值
	 */
	public PrpQmain getPrpQmain() {
		return prpQmain;
	}

	/**
	 * 設置屬性報價單信息.
	 * 
	 * @param prpQmain
	 *            待設置的報價單信息的值
	 */
	public void setPrpQmain(PrpQmain prpQmain) {
		this.prpQmain = prpQmain;
	}

	/**
	 * 獲取屬性核保系統幫助服務接口.
	 * 
	 * @return 屬性核保系統幫助服務接口的值
	 */
	public WfLogHelperService getWfLogHelperService() {
		return wfLogHelperService;
	}

	/**
	 * 設置屬性核保系統幫助服務接口.
	 * 
	 * @param wfLogHelperService
	 *            待設置的核保系統幫助服務接口的值
	 */
	public void setWfLogHelperService(WfLogHelperService wfLogHelperService) {
		this.wfLogHelperService = wfLogHelperService;
	}

	/**
	 * 獲取屬性機構代碼.
	 * 
	 * @return 屬性機構代碼的值
	 */
	public String getComCode() {
		return comCode;
	}

	/**
	 * 設置屬性機構代碼.
	 * 
	 * @param comCode
	 *            待設置的機構代碼的值
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 獲取屬性危險單位信息服務接口.
	 * 
	 * @return 屬性危險單位信息服務接口的值
	 */
	public CommonDangerInfoService getCommonDangerInfoService() {
		return commonDangerInfoService;
	}

	/**
	 * 設置屬性危險單位信息服務接口.
	 * 
	 * @param commonDangerInfoService
	 *            待設置的危險單位信息服務接口的值
	 */
	public void setCommonDangerInfoService(CommonDangerInfoService commonDangerInfoService) {
		this.commonDangerInfoService = commonDangerInfoService;
	}

	/**
	 * 獲取屬性核保服務接口.
	 * 
	 * @return 屬性核保服務接口的值
	 */
	public CommonCheckTaskService getCommonCheckTaskService() {
		return commonCheckTaskService;
	}

	/**
	 * 設置屬性核保服務接口.
	 * 
	 * @param commonCheckTaskService
	 *            待設置的核保服務接口的值
	 */
	public void setCommonCheckTaskService(CommonCheckTaskService commonCheckTaskService) {
		this.commonCheckTaskService = commonCheckTaskService;
	}

	/**
	 * 獲取屬性材料接口.
	 * 
	 * @return 屬性材料接口的值
	 */
	public UwMaterialService getUwMaterialService() {
		return uwMaterialService;
	}

	/**
	 * 設置屬性材料接口.
	 * 
	 * @param uwMaterialService
	 *            待設置的材料接口的值
	 */
	public void setUwMaterialService(UwMaterialService uwMaterialService) {
		this.uwMaterialService = uwMaterialService;
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
	 * 獲取屬性核保系統查詢接口.
	 * 
	 * @return 屬性核保系統查詢接口的值
	 */
	public PrpallService getPrpallService() {
		return prpallService;
	}

	/**
	 * 設置屬性核保系統查詢接口.
	 * 
	 * @param prpallService
	 *            待設置的核保系統查詢接口的值
	 */
	public void setPrpallService(PrpallService prpallService) {
		this.prpallService = prpallService;
	}

	/**
	 * 獲取屬性節點狀態.
	 * 
	 * @return 屬性節點狀態的值
	 */
	public String getiNodeStatus() {
		return iNodeStatus;
	}

	/**
	 * 設置屬性節點狀態.
	 * 
	 * @param iNodeStatus
	 *            待設置的節點狀態的值
	 */
	public void setiNodeStatus(String iNodeStatus) {
		this.iNodeStatus = iNodeStatus;
	}

	/**
	 * 獲取屬性跳轉頁面返回結果.
	 * 
	 * @return 屬性跳轉頁面返回結果的值
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 設置屬性跳轉頁面返回結果.
	 * 
	 * @param content
	 *            待設置的跳轉頁面返回結果的值
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 獲取屬性XML類型樹.
	 * 
	 * @return 屬性XML類型樹的值
	 */
	public String getTypeTreeXML() {
		return typeTreeXML;
	}

	/**
	 * 設置屬性XML類型樹.
	 * 
	 * @param typeTreeXML
	 *            待設置的XML類型樹的值
	 */
	public void setTypeTreeXML(String typeTreeXML) {
		this.typeTreeXML = typeTreeXML;
	}

	/**
	 * 獲取屬性參數字符串.
	 * 
	 * @return 屬性參數字符串的值
	 */
	public String getParamString() {
		return paramString;
	}

	/**
	 * 設置屬性參數字符串.
	 * 
	 * @param paramString
	 *            待設置的參數字符串的值
	 */
	public void setParamString(String paramString) {
		this.paramString = paramString;
	}

	/**
	 * 獲取屬性請求地址.
	 * 
	 * @return 屬性請求地址的值
	 */
	public String getRemoteUrl() {
		return remoteUrl;
	}

	/**
	 * 設置屬性請求地址.
	 * 
	 * @param remoteUrl
	 *            待設置的請求地址的值
	 */
	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	public String getRemoteUrl1() {
		return remoteUrl1;
	}

	public void setRemoteUrl1(String remoteUrl1) {
		this.remoteUrl1 = remoteUrl1;
	}

	/**
	 * 獲取屬性再保詢價單處理接口.
	 * 
	 * @return 屬性再保詢價單處理接口的值
	 */
	public EnquiryService getEnquiryService() {
		return enquiryService;
	}

	/**
	 * 設置屬性再保詢價單處理接口.
	 * 
	 * @param enquiryService
	 *            待設置的再保詢價單處理接口的值
	 */
	public void setEnquiryService(EnquiryService enquiryService) {
		this.enquiryService = enquiryService;
	}

	/**
	 * 獲取屬性合約自留額計畫接口.
	 * 
	 * @return 屬性合約自留額計畫接口的值
	 */
	public FhRetenService getFhRetenService() {
		return fhRetenService;
	}

	/**
	 * 設置屬性合約自留額計畫接口.
	 * 
	 * @param fhRetenService
	 *            待設置的合約自留額計畫接口的值
	 */
	public void setFhRetenService(FhRetenService fhRetenService) {
		this.fhRetenService = fhRetenService;
	}

	/**
	 * 獲取屬性保單的分保試算信息接口.
	 * 
	 * @return 屬性保單的分保試算信息接口的值
	 */
	public PrpCReinsTrialService getPrpCReinsTrialService() {
		return prpCReinsTrialService;
	}

	/**
	 * 設置屬性保單的分保試算信息接口.
	 * 
	 * @param prpCReinsTrialService
	 *            待設置的保單的分保試算信息接口的值
	 */
	public void setPrpCReinsTrialService(PrpCReinsTrialService prpCReinsTrialService) {
		this.prpCReinsTrialService = prpCReinsTrialService;
	}

	/**
	 * 獲取屬性要保書的分保試算資訊接口.
	 * 
	 * @return 屬性要保書的分保試算資訊接口的值
	 */
	public PrpTReinsTrialService getPrpTReinsTrialService() {
		return prpTReinsTrialService;
	}

	/**
	 * 設置屬性要保書的分保試算資訊接口.
	 * 
	 * @param prpTReinsTrialService
	 *            待設置的要保書的分保試算資訊接口的值
	 */
	public void setPrpTReinsTrialService(PrpTReinsTrialService prpTReinsTrialService) {
		this.prpTReinsTrialService = prpTReinsTrialService;
	}

	/**
	 * 獲取屬性理賠的危險單位劃分接口.
	 * 
	 * @return 屬性理賠的危險單位劃分接口的值
	 */
	public PrpLDangerUnitService getPrpLDangerUnitService() {
		return prpLDangerUnitService;
	}

	/**
	 * 設置屬性理賠的危險單位劃分接口.
	 * 
	 * @param prpLDangerUnitService
	 *            待設置的理賠的危險單位劃分接口的值
	 */
	public void setPrpLDangerUnitService(PrpLDangerUnitService prpLDangerUnitService) {
		this.prpLDangerUnitService = prpLDangerUnitService;
	}

	/**
	 * 獲取屬性批單的分保試算信息接口.
	 * 
	 * @return 屬性批單的分保試算信息接口的值
	 */
	public PrpPReinsTrialService getPrpPReinsTrialService() {
		return prpPReinsTrialService;
	}

	/**
	 * 設置屬性批單的分保試算信息接口.
	 * 
	 * @param prpPReinsTrialService
	 *            待設置的批單的分保試算信息接口的值
	 */
	public void setPrpPReinsTrialService(PrpPReinsTrialService prpPReinsTrialService) {
		this.prpPReinsTrialService = prpPReinsTrialService;
	}

	/**
	 * 獲取屬性臨分超賠接口.
	 * 
	 * @return 屬性臨分超賠接口的值
	 */

	public BLFacXLayerService getBlFacXLayerService() {
		return blFacXLayerService;
	}

	/**
	 * 設置屬性臨分超賠接口.
	 * 
	 * @param facXLayerService
	 *            待設置的臨分超賠接口的值
	 */

	public void setBlFacXLayerService(BLFacXLayerService blFacXLayerService) {
		this.blFacXLayerService = blFacXLayerService;
	}

	/**
	 * 獲取屬性再保臨分詢價單頁面處理接口.
	 * 
	 * @return 屬性再保臨分詢價單頁面處理接口的值
	 */
	public BLEnquiryService getBlEnquiryService() {
		return blEnquiryService;
	}

	/**
	 * 設置屬性再保臨分詢價單頁面處理接口.
	 * 
	 * @param enquiryViewHelperService
	 *            待設置的再保臨分詢價單頁面處理接口的值
	 */

	public void setBlEnquiryService(BLEnquiryService blEnquiryService) {
		this.blEnquiryService = blEnquiryService;
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
	 * 獲取屬性詢價單信息接口.
	 * 
	 * @return 屬性詢價單信息接口的值
	 */
	public FeoEnquiryService getFeoEnquiryService() {
		return feoEnquiryService;
	}

	/**
	 * 設置屬性詢價單信息接口.
	 * 
	 * @param feoEnquiryService
	 *            待設置的詢價單信息接口的值
	 */
	public void setFeoEnquiryService(FeoEnquiryService feoEnquiryService) {
		this.feoEnquiryService = feoEnquiryService;
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
	 * 獲取屬性是否為續保業務.
	 * 
	 * @return 屬性是否為續保業務的值
	 */
	public boolean isRenewal() {
		return isRenewal;
	}

	/**
	 * 設置屬性是否為續保業務.
	 * 
	 * @param isRenewal
	 *            待設置的續保業務的值
	 */
	public void setRenewal(boolean isRenewal) {
		this.isRenewal = isRenewal;
	}

	/**
	 * 獲取屬性核保級別節點最大值.
	 * 
	 * @return 屬性核保級別節點最大值
	 */
	public String getNodeNomax() {
		return nodeNomax;
	}

	/**
	 * 設置屬性核保級別節點最大值.
	 * 
	 * @param nodeNomax
	 *            屬性核保級別節點最大值
	 */
	public void setNodeNomax(String nodeNomax) {
		this.nodeNomax = nodeNomax;
	}

	/**
	 * 獲取屬性用戶節點值.
	 * 
	 * @return 屬性用戶節點值
	 */
	public String getUserNodeNo() {
		return userNodeNo;
	}

	/**
	 * 設置屬性用戶節點值.
	 * 
	 * @param userNodeNo
	 *            屬性用戶節點值
	 */
	public void setUserNodeNo(String userNodeNo) {
		this.userNodeNo = userNodeNo;
	}

	/**
	 * 獲取核保級別設定接口.
	 * 
	 * @return the 核保級別設定接口
	 */
	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	/**
	 * 設置核保級別設定接口.
	 * 
	 * @param utiUwLevelService
	 *            the new 核保級別設定接口
	 */
	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

	public String getWhetherFacing() {
		return whetherFacing;
	}

	public void setWhetherFacing(String whetherFacing) {
		this.whetherFacing = whetherFacing;
	}

	public List getFacingList() {
		return facingList;
	}

	public void setFacingList(List facingList) {
		this.facingList = facingList;
	}

	public boolean isExistClaim() {
		return existClaim;
	}

	public void setExistClaim(boolean existClaim) {
		this.existClaim = existClaim;
	}

	/**
	 * 獲取照會訊息.
	 * 
	 * @return the 照會訊息
	 */
	public List<ZHInfoVo> getZhList() {
		return zhList;
	}

	/**
	 * 設置照會訊息.
	 * 
	 * @param zhList
	 *            the new 照會訊息
	 */
	public void setZhList(List<ZHInfoVo> zhList) {
		this.zhList = zhList;
	}

	public String getDangerNos() {
		return dangerNos;
	}

	public void setDangerNos(String dangerNos) {
		this.dangerNos = dangerNos;
	}

	public List getEnquiryList() {
		return enquiryList;
	}

	public void setEnquiryList(List enquiryList) {
		this.enquiryList = enquiryList;
	}
	/**
	 * 獲取屬性同險累積查看標誌.
	 * 
	 * @return 屬性用戶節點值
	 */
	public String getSameRiskFlag() {
		return sameRiskFlag;
	}
	/**
	 * 設置屬性同險累積查看標誌.
	 * 
	 * @param sameRiskFlag
	 *            同險累積查看標誌
	 */
	public void setSameRiskFlag(String sameRiskFlag) {
		this.sameRiskFlag = sameRiskFlag;
	}

	public PrpdriskconfigService getPrpdriskconfigService() {
		return prpdriskconfigService;
	}

	public void setPrpdriskconfigService(PrpdriskconfigService prpdriskconfigService) {
		this.prpdriskconfigService = prpdriskconfigService;
	}

	public String getReinsIP() {
		return reinsIP;
	}

	public void setReinsIP(String reinsIP) {
		this.reinsIP = reinsIP;
	}

	public boolean isExistMessage() {
		return existMessage;
	}

	public void setExistMessage(boolean existMessage) {
		this.existMessage = existMessage;
	}

	public String getRationCode() {
		return rationCode;
	}

	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}

	public WfMessageService getWfMessageService() {
		return wfMessageService;
	}

	public void setWfMessageService(WfMessageService wfMessageService) {
		this.wfMessageService = wfMessageService;
	}

	public boolean isAllowEdit() {
		return isAllowEdit;
	}

	public void setAllowEdit(boolean isAllowEdit) {
		this.isAllowEdit = isAllowEdit;
	}
	
}
