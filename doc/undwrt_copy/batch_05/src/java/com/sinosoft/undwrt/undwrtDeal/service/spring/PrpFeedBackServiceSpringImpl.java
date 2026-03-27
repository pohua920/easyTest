package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sinosoft.claim.dto.domain.PrpCrenewalDto;
import com.sinosoft.claim.resource.dtofactory.domain.DBPrpCrenewal;
import com.sinosoft.common.schema.model.PrpCPmain;
import com.sinosoft.common.schema.model.PrpCitemCar;
import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpCmainSub;
import com.sinosoft.common.schema.model.PrpCmainSubId;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpPitemKind;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpQmain;
import com.sinosoft.common.schema.model.PrpQmainSub;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.common.schema.model.PrpTmainSub;
import com.sinosoft.indiv.ci.blsvr.BLCIEndorValid;
import com.sinosoft.indiv.ci.blsvr.BLCIInsureValid;
import com.sinosoft.indiv.ci.interf.DummyPolicyValid11Decoder;
import com.sinosoft.indiv.ci.interf.DummyPolicyValid11Encoder;
import com.sinosoft.indiv.ci.interf.DummyPolicyValid31Decoder;
import com.sinosoft.indiv.ci.interf.DummyPolicyValid31Encoder;
import com.sinosoft.indiv.ci.interf.EbaoProxy;
import com.sinosoft.indiv.ci.interf.EndorseValid31Decoder;
import com.sinosoft.indiv.ci.interf.EndorseValid31Encoder;
import com.sinosoft.indiv.ci.interf.EndorseValidDecoder;
import com.sinosoft.indiv.ci.interf.EndorseValidEncoder;
import com.sinosoft.indiv.ci.interf.PolicyCancelDecoder;
import com.sinosoft.indiv.ci.interf.PolicyCancelEncoder;
import com.sinosoft.indiv.ci.interf.PolicyValid11Decoder;
import com.sinosoft.indiv.ci.interf.PolicyValid11Encoder;
import com.sinosoft.indiv.ci.interf.PolicyValid31Decoder;
import com.sinosoft.indiv.ci.interf.PolicyValid31Encoder;
import com.sinosoft.indiv.ci.interf.PolicyValidDecoder;
import com.sinosoft.indiv.ci.interf.PolicyValidEncoder;
import com.sinosoft.indiv.ci.interf.PolicyWithDrawValid11Encoder;
import com.sinosoft.indiv.ci.interf.PolicyWithDrawValid31Decoder;
import com.sinosoft.indiv.ci.interf.PolicyWithDrawValid31Encoder;
import com.sinosoft.indiv.ci.interf.PolicyWithDrawValidDecoder;
import com.sinosoft.indiv.ci.interfS.DummyEndorseValidBusinessDecoder;
import com.sinosoft.indiv.ci.interfS.DummyEndorseValidBusinessEncoder;
import com.sinosoft.indiv.ci.interfS.DummyPolicyValidBusinessDecoder;
import com.sinosoft.indiv.ci.interfS.DummyPolicyValidBusinessEncoder;
import com.sinosoft.indiv.ci.interfS.DummyPolicyValidOtherDecoder;
import com.sinosoft.indiv.ci.interfS.DummyPolicyValidOtherEncoder;
import com.sinosoft.indiv.ci.interfS.EndorseValidBusinessDecoder;
import com.sinosoft.indiv.ci.interfS.EndorseValidBusinessEncoder;
import com.sinosoft.indiv.ci.interfS.EndorseValidOtherDecoder;
import com.sinosoft.indiv.ci.interfS.EndorseValidOtherEncoder;
import com.sinosoft.indiv.ci.interfS.PolicyCancelOtherDecoder;
import com.sinosoft.indiv.ci.interfS.PolicyCancelOtherEncoder;
import com.sinosoft.indiv.ci.interfS.PolicyValidBusinessDecoder;
import com.sinosoft.indiv.ci.interfS.PolicyValidBusinessEncoder;
import com.sinosoft.indiv.ci.interfS.PolicyValidOtherDecoder;
import com.sinosoft.indiv.ci.interfS.PolicyValidOtherEncoder;
import com.sinosoft.indiv.ci.interfS.PolicyWithDrawValidBusinessDecoder;
import com.sinosoft.indiv.ci.interfS.PolicyWithDrawValidBusinessEncoder;
import com.sinosoft.indiv.ci.interfS.PolicyWithDrawValidOtherDecoder;
import com.sinosoft.indiv.ci.interfS.PolicyWithDrawValidOtherEncoder;
import com.sinosoft.indiv.ci.interfS.SinoProxy;
import com.sinosoft.prpall.bl.action.domain.BLPrpCPbatchAction;
import com.sinosoft.prpall.bl.action.domain.BLPrpCbatchAction;
import com.sinosoft.prpall.blsvr.cb.BLCPolicyCovernote;
import com.sinosoft.prpall.blsvr.cb.BLPolicy;
import com.sinosoft.prpall.blsvr.cb.BLPolicyCovernote;
import com.sinosoft.prpall.blsvr.cb.BLPolicyOrigin;
import com.sinosoft.prpall.blsvr.cb.BLPrpCmain;
import com.sinosoft.prpall.blsvr.cb.BLPrpCmainCovernote;
import com.sinosoft.prpall.blsvr.misc.BLPrpCommission;
import com.sinosoft.prpall.blsvr.misc.BLPrpMiddleCost;
import com.sinosoft.prpall.blsvr.pg.BLEndorse;
import com.sinosoft.prpall.blsvr.pg.BLEndorseCovernote;
import com.sinosoft.prpall.blsvr.pg.BLPrpPcarshipTax;
import com.sinosoft.prpall.blsvr.pg.BLPrpPheadCovernote;
import com.sinosoft.prpall.blsvr.pg.BLPrpPmain;
import com.sinosoft.prpall.blsvr.pg.BLPrpPmainCovernote;
import com.sinosoft.prpall.blsvr.tb.BLProposal;
import com.sinosoft.prpall.blsvr.tb.BLProposalPolicy;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmain;
import com.sinosoft.prpall.dto.domain.PrpCPbatchDto;
import com.sinosoft.prpall.dto.domain.PrpCbatchDto;
import com.sinosoft.prpall.dto.domain.PrpCproductDto;
import com.sinosoft.prpall.dto.domain.PrpLclaimDto;
import com.sinosoft.prpall.dto.domain.PrpLcompensateDto;
import com.sinosoft.prpall.dto.domain.PrpLprepayDto;
import com.sinosoft.prpall.dto.domain.PrpPheadCovernoteDto;
import com.sinosoft.prpall.interf.Visa;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpLclaim;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpLcompensate;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpLprepay;
import com.sinosoft.prpall.schema.PrpCmainCovernoteSchema;
import com.sinosoft.prpall.schema.PrpCmainSchema;
import com.sinosoft.prpall.schema.PrpPmainSchema;
import com.sinosoft.prpall.schema.PrpTmainSchema;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.prpins.policy.service.facade.PrpCpMainService;
import com.sinosoft.reins.common.model.PrpCDangerUnit;
import com.sinosoft.reins.common.service.facade.PrpCDangerCoinsService;
import com.sinosoft.reins.common.service.facade.PrpCDangerItemService;
import com.sinosoft.reins.common.service.facade.PrpCDangerPlanService;
import com.sinosoft.reins.common.service.facade.PrpCDangerRiskService;
import com.sinosoft.reins.common.service.facade.PrpCDangerTotService;
import com.sinosoft.reins.common.service.facade.PrpCDangerUnitService;
import com.sinosoft.reins.common.service.facade.PrpCReinsShareService;
import com.sinosoft.reins.common.service.facade.PrpCReinsTrialService;
import com.sinosoft.reins.common.service.facade.PrpLDangerCoinsService;
import com.sinosoft.reins.common.service.facade.PrpLDangerItemService;
import com.sinosoft.reins.common.service.facade.PrpLDangerTotService;
import com.sinosoft.reins.common.service.facade.PrpLDangerUnitService;
import com.sinosoft.reins.common.service.facade.PrpLReinsShareService;
import com.sinosoft.reins.common.service.facade.PrpLReinsTrialService;
import com.sinosoft.reins.common.service.facade.PrpPDangerCoinsService;
import com.sinosoft.reins.common.service.facade.PrpPDangerItemService;
import com.sinosoft.reins.common.service.facade.PrpPDangerPlanService;
import com.sinosoft.reins.common.service.facade.PrpPDangerRiskService;
import com.sinosoft.reins.common.service.facade.PrpPDangerTotService;
import com.sinosoft.reins.common.service.facade.PrpPDangerUnitService;
import com.sinosoft.reins.common.service.facade.PrpPReinsShareService;
import com.sinosoft.reins.common.service.facade.PrpPReinsTrialService;
import com.sinosoft.reins.common.service.facade.PrpTDangerCoinsService;
import com.sinosoft.reins.common.service.facade.PrpTDangerPlanService;
import com.sinosoft.reins.common.service.facade.PrpTDangerRiskService;
import com.sinosoft.reins.common.service.facade.PrpTReinsShareService;
import com.sinosoft.reins.common.service.facade.PrpTReinsTrialService;
import com.sinosoft.reins.common.service.facade.PrpTdangerItemService;
import com.sinosoft.reins.common.service.facade.PrpTdangerTotService;
import com.sinosoft.reins.common.service.facade.PrpTdangerUnitService;
import com.sinosoft.reins.in.facultative.verify.service.facade.PrpReinsNotionService;
import com.sinosoft.reins.in.facultative.verify.service.facade.PrpReinsVerifyService;
import com.sinosoft.reins.interf.web.ReinsUndrtInterfAction;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.FeoCoinsService;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.FeoEngageService;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.FeoEnquiryService;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.FeoItemService;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.FeoPlanService;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.FeoReinsReceiveService;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.FeoReinsVerifyService;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.FeoTotService;
import com.sinosoft.reins.product.code.service.facade.BLReinsService;
import com.sinosoft.sff.interf.PaymentIntfFacade;
import com.sinosoft.sff.interf.PrpTransSffIntf;
import com.sinosoft.sff.interf.PrpallPremiumInterf;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.model.PrpDuser;
import com.sinosoft.undwrt.common.service.facade.PrpDuserService;
import com.sinosoft.undwrt.common.vo.CommonDangerUnitSerialNoVo;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.PrpJfTime;
import com.sinosoft.undwrt.undwrtBase.model.PrpJfTimeId;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDealSubmitService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.ExpenseControlDealService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpFeedBackService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService;
//mantis： LIA0278，處理人員：Sam，需求單編號：LIA0278 責任險分期功能修正測試
import com.sinosoft.undwrt.undwrtInterface.service.spring.PrpTransSffIntf2;
import com.sinosoft.utility.SysConfig;
import com.sinosoft.utility.UtiPower;
import com.sinosoft.utility.database.DbPool;
import com.sinosoft.visa.bl.action.custom.BLInterfaceVsMarkAction;

/**
 * 核保回寫數據服務實現類
 */
public class PrpFeedBackServiceSpringImpl extends GenericDaoHibernate implements PrpFeedBackService {

	/** 屬性是否為分入業務標誌. */
	private boolean isReinsCeded = false;

	/** 屬性核定費用結余服務接口. */
	private ExpenseControlDealService expenseControlDealService;

	/** 屬性詢價單共保信息接口. */
	private FeoCoinsService feoCoinsService;

	/** 屬性詢價單特別約定資訊接口. */
	private FeoEngageService feoEngageService;

	/** 屬性詢價單信息接口. */
	private FeoEnquiryService feoEnquiryService;

	/** 屬性詢價單保險標的資訊接口. */
	private FeoItemService feoItemService;

	/** 屬性詢價單分期信息接口. */
	private FeoPlanService feoPlanService;

	/** 屬性詢價單分保接受人資訊接口. */
	private FeoReinsReceiveService feoReinsReceiveService;

	/** 屬性詢價單歷次確認意見接口. */
	private FeoReinsVerifyService feoReinsVerifyService;

	/** 屬性詢價單金額合計資訊接口. */
	private FeoTotService feoTotService;

	/** 屬性保單危險單位共保資訊接口. */
	private PrpCDangerCoinsService prpCDangerCoinsService;

	/** 屬性保單危險單位標的資訊接口. */
	private PrpCDangerItemService prpCDangerItemService;

	/** 屬性保單危險單位交費計畫接口. */
	private PrpCDangerPlanService prpCDangerPlanService;

	/** 屬性保單危險單位交費計畫接口. */
	private PrpCDangerRiskService prpCDangerRiskService;

	/** 屬性保單危險單位金額合計資訊接口. */
	private PrpCDangerTotService prpCDangerTotService;

	/** 屬性保單危險單位臨分接口. */
	private PrpCDangerUnitService prpCDangerUnitService;

	/** 屬性保單分保試算結果資訊接口. */
	private PrpCReinsShareService prpCReinsShareService;

	/** 屬性保單的分保試算信息接口. */
	private PrpCReinsTrialService prpCReinsTrialService;

	/** 屬性賠案分攤試算結果資訊接口. */
	private PrpLReinsShareService prpLReinsShareService;

	/** 屬性賠案的分攤試算信息接口. */
	private PrpLReinsTrialService prpLReinsTrialService;

	/** 屬性批單危險單位共保資訊接口. */
	private PrpPDangerCoinsService prpPDangerCoinsService;

	/** 屬性批單的危險單位標的資訊接口. */
	private PrpPDangerItemService prpPDangerItemService;

	/** 屬性批單危險單位交付計畫接口. */
	private PrpPDangerPlanService prpPDangerPlanService;

	/** 屬性批單危險單位風險評估接口. */
	private PrpPDangerRiskService prpPDangerRiskService;

	/** 屬性批單危險單位金額合計資訊接口. */
	private PrpPDangerTotService prpPDangerTotService;

	/** 屬性批單的危險單位劃分接口. */
	private PrpPDangerUnitService prpPDangerUnitService;

	/** 屬性批單分保試算結果資訊接口. */
	private PrpPReinsShareService prpPReinsShareService;

	/** 屬性批單的分保試算信息接口. */
	private PrpPReinsTrialService prpPReinsTrialService;

	/** 屬性要保書危險單位共保資訊接口. */
	private PrpTDangerCoinsService prpTDangerCoinsService;

	/** 屬性要保書危險單位標的資訊接口. */
	private PrpTdangerItemService prpTdangerItemService;

	/** 屬性要保書危險單位交費計畫. */
	private PrpTDangerPlanService prpTDangerPlanService;

	/** 屬性要保書危險單位風險評估接口. */
	private PrpTDangerRiskService prpTDangerRiskService;

	/** 屬性要保書危險單位金額合計資訊接口. */
	private PrpTdangerTotService prpTdangerTotService;

	/** 屬性投保單的危險單位劃分接口. */
	private PrpTdangerUnitService prpTdangerUnitService;

	/** 屬性分保試算結果資訊接口. */
	private PrpTReinsShareService prpTReinsShareService;

	/** 屬性要保書的分保試算資訊接口. */
	private PrpTReinsTrialService prpTReinsTrialService;

	/** 屬性再保接口業務處理接口. */
	private BLReinsService blReinsService;

	/** 屬性賠案危險單位共保資訊接口. */
	private PrpLDangerCoinsService prpLDangerCoinsService;

	/** 屬性理賠的危險單位資訊接口. */
	private PrpLDangerItemService prpLDangerItemService;

	/** 屬性理賠危險單位金額合計資訊接口. */
	private PrpLDangerTotService prpLDangerTotService;

	/** 屬性理賠的危險單位劃分接口. */
	private PrpLDangerUnitService prpLDangerUnitService;

	/** 屬性再保分入資訊確認意見接口. */
	private PrpReinsNotionService prpReinsNotionService;

	/** 屬性再保分入信息確認接口. */
	private PrpReinsVerifyService prpReinsVerifyService;

	/** 屬性要保書處理接口. */
	private PolicyService policyService;

	/** 屬性核保調用再保接口. */
	private ReinsUndrtInterfAction reinsUndrtInterfAction;

	/** 屬性用戶訊息接口. */
	private PrpDuserService prpDuserService;

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/** 屬性要保書訊息接口. */
	private PrpCpMainService prpCpMainService;
	
	/** 屬性核保審核處理接口. */
	private CommonDealSubmitService commonDealSubmitService;
	//add by xuhuiling begin
	/** 屬性更新作業狀態接口*/
	private WfLogService wfLogService;
	/** 屬性查詢狀態接口*/
	private TaskDealService taskDealService;
	//add by xuhuiling end
	
	/**
	 * 回寫相關數據信息.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underWriteCode
	 *            最終核保人代碼
	 * @param underWriteDate
	 *            核保完成日期
	 * @param flag
	 *            核保標誌位
	 * @param businessSource
	 *            業務來源
	 * @return 成功返回true,失敗返回false
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public boolean echo(DBManager dbManager, char certiType, String businessNo, String status, String underWriteCode, DateTime underWriteDate, String flag,
			String businessSource) throws UserException, SQLException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String serverName = "";
		String dbName = "";
		boolean blnReturn = false;
		try {
			if (flag == "1") // 打回修改
			{
				// 打回业务修改时，删除分保试算、危险单位相关信息 begin
				QueryRule queryRule = QueryRule.getInstance();
				switch (certiType) {
				case 'T':
					queryRule.addEqual("id.proposalNo", businessNo);
					prpTdangerItemService.deleteByConditions(queryRule);
					prpTdangerTotService.deleteByConditions(queryRule);
					prpTDangerPlanService.deleteByConditions(queryRule);
					prpTDangerRiskService.deleteByConditions(queryRule);
					// 增加共保信息删除
					prpTDangerCoinsService.deleteByConditions(queryRule);
					// 删除主表之前，应该先删除子表记录
					prpTReinsTrialService.deleteByConditions(queryRule);
					prpTReinsShareService.deleteByConditions(queryRule);
					prpTdangerUnitService.deleteByConditions(queryRule);

					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("proposalNo", businessNo);
					feoReinsVerifyService.deleteByConditions(queryRule);
					feoReinsReceiveService.deleteByConditions(queryRule);
					feoEngageService.deleteByConditions(queryRule);
					feoTotService.deleteByConditions(queryRule);
					feoItemService.deleteByConditions(queryRule);
					feoCoinsService.deleteByConditions(queryRule);
					feoPlanService.deleteByConditions(queryRule);
					feoEnquiryService.deleteByConditions(queryRule);

					// 分入业务打回修改时处理
					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("id.certiNo", businessNo);
					prpReinsNotionService.deleteByConditions(queryRule);
					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("certiNo", businessNo);
					prpReinsVerifyService.deleteByConditions(queryRule);
					break;
				case 'P':
					queryRule.addEqual("id.policyNo", businessNo);
					prpCDangerItemService.deleteByConditions(queryRule);
					prpCDangerTotService.deleteByConditions(queryRule);
					prpCDangerPlanService.deleteByConditions(queryRule);
					prpCDangerRiskService.deleteByConditions(queryRule);
					// 增加共保信息删除
					prpCDangerCoinsService.deleteByConditions(queryRule);
					// 删除主表之前，应该先删除子表记录
					prpCReinsTrialService.deleteByConditions(queryRule);
					prpCReinsShareService.deleteByConditions(queryRule);
					prpCDangerUnitService.deleteByConditions(queryRule);
					feoReinsVerifyService.deleteByConditions(queryRule);

					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("policyNo", businessNo);
					feoReinsReceiveService.deleteByConditions(queryRule);
					feoEngageService.deleteByConditions(queryRule);
					feoTotService.deleteByConditions(queryRule);
					feoItemService.deleteByConditions(queryRule);
					feoCoinsService.deleteByConditions(queryRule);
					feoPlanService.deleteByConditions(queryRule);
					feoEnquiryService.deleteByConditions(queryRule);
					break;
				case 'E':
					queryRule.addEqual("id.endorseNo", businessNo);
					prpPDangerItemService.deleteByConditions(queryRule);
					prpPDangerItemService.deleteByConditions(queryRule);
					prpPDangerTotService.deleteByConditions(queryRule);
					prpPDangerPlanService.deleteByConditions(queryRule);
					prpPDangerRiskService.deleteByConditions(queryRule);
					// 增加共保信息删除
					prpPDangerCoinsService.deleteByConditions(queryRule);
					// 删除主表之前，应该先删除子表记录
					prpPReinsTrialService.deleteByConditions(queryRule);
					prpPReinsShareService.deleteByConditions(queryRule);
					prpPDangerUnitService.deleteByConditions(queryRule);

					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("endorseNo", businessNo);
					feoReinsVerifyService.deleteByConditions(queryRule);
					feoReinsReceiveService.deleteByConditions(queryRule);
					feoEngageService.deleteByConditions(queryRule);
					feoTotService.deleteByConditions(queryRule);
					feoItemService.deleteByConditions(queryRule);
					feoCoinsService.deleteByConditions(queryRule);
					feoPlanService.deleteByConditions(queryRule);
					feoEnquiryService.deleteByConditions(queryRule);
					break;
				case 'C':
					queryRule.addEqual("certiNo", businessNo);
					prpLDangerItemService.deleteByConditions(queryRule);
					prpLDangerTotService.deleteByConditions(queryRule);
					// 增加共保信息删除
					prpLDangerCoinsService.deleteByConditions(queryRule);
					prpLDangerCoinsService.deleteByConditions(queryRule);
					// 删除主表之前，应该先删除子表记录
					prpLReinsTrialService.deleteByConditions(queryRule);
					prpLReinsShareService.deleteByConditions(queryRule);
					prpLDangerUnitService.deleteByConditions(queryRule);
					break;
				case 'B':
					break;
				default:
					throw new UserException(-98, -1149, "BLPrpFeedBack.echo()", internal.getText("undwrt.service.proFeeBack.haveNoTheType"));
				}
				// 打回业务修改时，删除分保试算、危险单位相关信息 end
			}
			switch (certiType) {
			case 'T':
				this.echoProposal(businessNo, status, underWriteCode, underWriteDate, flag, serverName, dbName, businessSource);
				blnReturn = true;
				break;
			case 'P':
				this.echoPolicy(businessNo, status, underWriteCode, underWriteDate, flag, serverName, dbName, businessSource);
				blnReturn = true;
				break;
			case 'E':
				this.echoEndor(dbManager, businessNo, status, underWriteCode, underWriteDate, flag, serverName, dbName, businessSource);
				blnReturn = true;
				break;
			case 'B':
				this.echoQta(businessNo, status, underWriteCode, underWriteDate);
				blnReturn = true;
				break;
			default:
				throw new UserException(-98, -1149, "BLPrpFeedBack.echo()", internal.getText("undwrt.service.proFeeBack.haveNoTheType"));
			}
			
			
			// 核保、核批通过的后续处理
			if (status.trim().equals("3") || status.trim().equals("1")) {
				String businessType = String.valueOf(certiType);
				if ("B".equals(businessType)) {
					PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo,"quotation");
					String hql = "from RenewalInfo where quoteNo= '" + businessNo + "'";
					List list = this.findByHql(hql);
					String editFlag = prpQmain.getEditFlag();
					if (list.size() > 0 || "2".equals(editFlag)) {
						echoPrp(businessType, businessNo, underWriteCode, dbManager, "");
					}
					return blnReturn;
				}
				echoPrp(businessType, businessNo, underWriteCode, dbManager, "");
			}
		} catch (SQLException se) {
			throw se;
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		}
		return blnReturn;
	}

	/**
	 * 回寫相關數據信息.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underWriteCode
	 *            最終核保人代碼
	 * @param underWriteDate
	 *            the 核保完成日期
	 * @param flag
	 *            核保標誌位
	 * @param businessSource
	 *            業務來源
	 * @param currendNodeNo
	 *            當前節點號
	 * @return 成功返回true,失敗返回false
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public boolean echo(DBManager dbManager, char certiType, String businessNo, String status, String underWriteCode, DateTime underWriteDate, String flag,
			String businessSource, int currendNodeNo) throws UserException, SQLException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String serverName = "";
		String dbName = "";
		boolean blnReturn = false;
		try {
			if (flag == "1") // 打回修改
			{
				// 打回业务修改时，删除分保试算、危险单位相关信息 begin
				QueryRule queryRule = QueryRule.getInstance();
				switch (certiType) {
				case 'T':
					queryRule.addEqual("id.proposalNo", businessNo);
					prpTdangerItemService.deleteByConditions(queryRule);
					prpTdangerTotService.deleteByConditions(queryRule);
					prpTDangerPlanService.deleteByConditions(queryRule);
					prpTDangerRiskService.deleteByConditions(queryRule);
					// 增加共保信息删除
					prpTDangerCoinsService.deleteByConditions(queryRule);
					// 删除主表之前，应该先删除子表记录
					prpTReinsTrialService.deleteByConditions(queryRule);
					prpTReinsShareService.deleteByConditions(queryRule);
					prpTdangerUnitService.deleteByConditions(queryRule);

					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("proposalNo", businessNo);
					feoReinsVerifyService.deleteByConditions(queryRule);
					feoReinsReceiveService.deleteByConditions(queryRule);
					feoEngageService.deleteByConditions(queryRule);
					feoTotService.deleteByConditions(queryRule);
					feoItemService.deleteByConditions(queryRule);
					feoCoinsService.deleteByConditions(queryRule);
					feoPlanService.deleteByConditions(queryRule);
					feoEnquiryService.deleteByConditions(queryRule);

					// 分入业务打回修改时处理
					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("id.certiNo", businessNo);
					prpReinsNotionService.deleteByConditions(queryRule);
					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("certiNo", businessNo);
					prpReinsVerifyService.deleteByConditions(queryRule);
					break;
				case 'P':
					queryRule.addEqual("id.policyNo", businessNo);
					prpCDangerItemService.deleteByConditions(queryRule);
					prpCDangerTotService.deleteByConditions(queryRule);
					prpCDangerPlanService.deleteByConditions(queryRule);
					prpCDangerRiskService.deleteByConditions(queryRule);
					// 增加共保信息删除
					prpCDangerCoinsService.deleteByConditions(queryRule);
					// 删除主表之前，应该先删除子表记录
					prpCReinsTrialService.deleteByConditions(queryRule);
					prpCReinsShareService.deleteByConditions(queryRule);
					prpCDangerUnitService.deleteByConditions(queryRule);

					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("policyNo", businessNo);
					feoReinsVerifyService.deleteByConditions(queryRule);
					feoReinsReceiveService.deleteByConditions(queryRule);
					feoEngageService.deleteByConditions(queryRule);
					feoTotService.deleteByConditions(queryRule);
					feoItemService.deleteByConditions(queryRule);
					feoCoinsService.deleteByConditions(queryRule);
					feoPlanService.deleteByConditions(queryRule);
					feoEnquiryService.deleteByConditions(queryRule);
					break;
				case 'E':
					queryRule.addEqual("id.endorseNo", businessNo);
					prpPDangerItemService.deleteByConditions(queryRule);
					prpPDangerTotService.deleteByConditions(queryRule);
					prpPDangerPlanService.deleteByConditions(queryRule);
					prpPDangerRiskService.deleteByConditions(queryRule);
					// 增加共保信息删除
					prpPDangerCoinsService.deleteByConditions(queryRule);
					// 删除主表之前，应该先删除子表记录
					prpPReinsTrialService.deleteByConditions(queryRule);
					prpPReinsShareService.deleteByConditions(queryRule);
					prpPDangerUnitService.deleteByConditions(queryRule);

					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("endorseNo", businessNo);
					feoReinsVerifyService.deleteByConditions(queryRule);
					feoReinsReceiveService.deleteByConditions(queryRule);
					feoEngageService.deleteByConditions(queryRule);
					feoTotService.deleteByConditions(queryRule);
					feoItemService.deleteByConditions(queryRule);
					feoCoinsService.deleteByConditions(queryRule);
					feoPlanService.deleteByConditions(queryRule);
					feoEnquiryService.deleteByConditions(queryRule);
					break;
				case 'C':
					queryRule.addEqual("certiNo", businessNo);
					prpLDangerItemService.deleteByConditions(queryRule);
					prpLDangerTotService.deleteByConditions(queryRule);
					// 增加共保信息删除
					prpLDangerCoinsService.deleteByConditions(queryRule);
					// 删除主表之前，应该先删除子表记录
					prpLReinsTrialService.deleteByConditions(queryRule);
					prpLReinsShareService.deleteByConditions(queryRule);
					prpLDangerUnitService.deleteByConditions(queryRule);
					break;
				default:
					throw new UserException(-98, -1149, "BLPrpFeedBack.echo()", internal.getText("undwrt.service.proFeeBack.haveNoTheType"));
				}
				// 打回业务修改时，删除分保试算、危险单位相关信息 end
			}

			String authorize_control = "1";
			switch (certiType) {
			case 'T':
				PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
				authorize_control = this.echoProposal(businessNo, status, underWriteCode, underWriteDate, flag, serverName, dbName, businessSource,
						currendNodeNo);
				//商火和住火的要保书审核通过时只修改要保书状态，不再送收付和再保20140327
				//mantis： FIR0557，處理人員：bj085，需求單編號：FIR0557 住火_續保件批單調整審核通過壓回續保註記邏輯 START
				//因正式機無此段程式 故調整為一致
//				if("F".equals(prpTmain.getClassCode()) || "R".equals(prpTmain.getClassCode())||("PA".equals(prpTmain.getRiskCode())&&"2".equals(prpTmain.getEditFlag())))
				if("F".equals(prpTmain.getClassCode()) || "R".equals(prpTmain.getClassCode()))
				{
					// add by CSY 20160418 伤害险送收付 start 
//					if("PA".equals(prpTmain.getRiskCode())&&"2".equals(prpTmain.getEditFlag())){
//						PrpTransSffIntf prpTransSffIntf = new PrpTransSffIntf();
//						PrpallPremiumInterf prpallPremiumInterf = new PrpallPremiumInterf();
//						DbPool dbpool = new DbPool();
//						dbpool.setDBManager(dbManager);
//						BLPrpCmain blPrpCmain = new BLPrpCmain();
//						blPrpCmain.getData(dbpool, prpTmain.getProposalNo());
//						prpTransSffIntf.transData(dbpool, "T", prpTmain.getProposalNo());
//						prpallPremiumInterf.transPremiumData(dbpool, "ALL", prpTmain.getProposalNo());
//						// 取险种代码，0402自动分险别挂帐，调用双核中的程序 end
//					}
					// add by CSY 20160418 伤害险送收付 end  
					blnReturn = true;
					return blnReturn;
				}
				//mantis： FIR0557，處理人員：bj085，需求單編號：FIR0557 住火_續保件批單調整審核通過壓回續保註記邏輯 END
				blnReturn = true;
				break;
			case 'P':
				this.echoPolicy(businessNo, status, underWriteCode, underWriteDate, flag, serverName, dbName, businessSource, currendNodeNo);
				blnReturn = true;
				break;
			case 'E':
				authorize_control = this.echoEndor(dbManager, businessNo, status, underWriteCode, underWriteDate, flag, serverName, dbName, businessSource);
				blnReturn = true;
				break;
			case 'B':
				System.out.println("==============開始回寫Q表信息=======================");
				this.echoQta(businessNo, status, underWriteCode, underWriteDate);
				System.out.println("==============回寫Q表信息結束=======================");
				blnReturn = true;
				break;
			default:
				throw new UserException(-98, -1149, "BLPrpFeedBack.echo()", internal.getText("undwrt.service.proFeeBack.haveNoTheType"));
			}
			// 核保、核批通过的后续处理
			if (status.trim().equals("3") || status.trim().equals("1")) {
				String businessType = String.valueOf(certiType);
				if ("B".equals(businessType)) {
					PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo,"quotation");
					//add by zhanghuanqi 20150211 火險批次轉檔報價單送收付添加  begin
					if("F02".equals(prpQmain.getRiskCode()) ){
						String  batchNo = prpQmain.getBatchNO();
						String sendPaymentFlag = prpQmain.getSendPaymentFlag();//modyfy by  mjx  火险批次转档判断曾加一条件
						System.out.print(batchNo);
						if(batchNo != null  && !"".equals(batchNo)&&!"2".equals(sendPaymentFlag)){
							echoPrp(businessType, businessNo, underWriteCode, dbManager, "");
							return blnReturn;
						}
						
					}
					//add by zhanghuanqi 20150211 火險批次轉檔報價單送收付添加  end
					String hql = "from RenewalInfo where quoteNo= '" + businessNo + "'";
					List list = this.findByHql(hql);
					String editFlag = prpQmain.getEditFlag();
					if (list.size() > 0 || "2".equals(editFlag)) {
						echoPrp(businessType, businessNo, underWriteCode, dbManager, "");
					} else {
						//modefied by zhangruofei 20150208 報價單都要送收付介接
						//modefied by wangJun 20150305关联单强制险审核通过不送收付介接，任意险审核通过时一并送 begin
//						if("A01".equals(prpQmain.getRiskCode()) || "B01".equals(prpQmain.getRiskCode())) {
//							echoPrp(businessType, businessNo, underWriteCode, dbManager, "");
//						}
						if("B01".equals(prpQmain.getRiskCode()))//關聯單報價單核保問題
						{
//							String strSql="select * from prpqmainsub where mainpolicyno='"+businessNo+"'";
//							List prpQmainSublist = this.getSession().createSQLQuery(strSql).list();
//							if(prpQmainSublist.size()<=0)
//							{
							System.out.println("============強制險報價單==="+businessNo+"===開始送收付介接================================");
								echoPrp(businessType, businessNo, underWriteCode, dbManager, "");//任意險與強制險分別送收付介接
							System.out.println("============強制險報價單==="+businessNo+"===結束送收付介接=================================");
//							}
							
						}
//						{
//							List<PrpQmainSub> prpQmainsubs = new ArrayList<PrpQmainSub>();
//							prpQmainsubs = prpQmain.getPrpQmainSubs();
//							if(prpQmainsubs.size()>0)
//							{
//								if("111".equals(prpQmainsubs.get(0).getFlag())) {
//									echoPrp(businessType, prpQmainsubs.get(0).getId().getMainPolicyNo(), underWriteCode, dbManager, "");
//								}
//							}
						if("A01".equals(prpQmain.getRiskCode())){
							System.out.println("=============任意險報價單==="+businessNo+"===開始送收付介接==============================");
							echoPrp(businessType, businessNo, underWriteCode, dbManager, "");//任意險與強制險分別送收付介接
							System.out.println("=============任意險報價單==="+businessNo+"===結束送收付介接==============================");
						}
						//modefied by wangJun 20150305关联单强制险审核通过不送收付介接，任意险审核通过时一并送 end
					}
					
					return blnReturn;
				}
				echoPrp(businessType, businessNo, underWriteCode, dbManager, authorize_control);
			}
		} catch (SQLException se) {
			throw se;
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		}
		return blnReturn;
	}

	/**
	 * 雙核回寫業務入口方法（核保通過/不通過後）.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underWriteCode
	 *            最終核保人代碼
	 * @param underWriteDate
	 *            核保完成日期
	 * @param flag
	 *            標誌
	 * @param businessSource
	 *            業務來源
	 * @param nodeNo
	 *            節點號
	 * @return 成功返回true，失敗返回false
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public boolean echoJF(DBManager dbManager, char certiType, String businessNo, String status, String underWriteCode, DateTime underWriteDate, String flag,
			String businessSource, int nodeNo) throws UserException, SQLException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String serverName = "";
		String dbName = "";
		boolean blnReturn = false;
		try {
			if (flag == "1") // 打回修改开始
			{
				QueryRule queryRule = QueryRule.getInstance();
				switch (certiType) {
				case 'T':
					queryRule.addEqual("id.proposalNo", businessNo);
					prpTdangerItemService.deleteByConditions(queryRule);
					prpTdangerTotService.deleteByConditions(queryRule);
					prpTDangerPlanService.deleteByConditions(queryRule);
					prpTDangerRiskService.deleteByConditions(queryRule);
					prpTDangerCoinsService.deleteByConditions(queryRule);
					prpTReinsTrialService.deleteByConditions(queryRule);
					prpTReinsShareService.deleteByConditions(queryRule);
					prpTdangerUnitService.deleteByConditions(queryRule);

					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("proposalNo", businessNo);
					feoReinsVerifyService.deleteByConditions(queryRule);
					feoReinsReceiveService.deleteByConditions(queryRule);
					feoEngageService.deleteByConditions(queryRule);
					feoTotService.deleteByConditions(queryRule);
					feoItemService.deleteByConditions(queryRule);
					feoCoinsService.deleteByConditions(queryRule);
					feoPlanService.deleteByConditions(queryRule);
					feoEnquiryService.deleteByConditions(queryRule);

					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("certiNo", businessNo);
					prpReinsNotionService.deleteByConditions(queryRule);
					prpReinsVerifyService.deleteByConditions(queryRule);
					break;
				case 'P':
					queryRule.addEqual("id.policyNo", businessNo);
					prpCDangerItemService.deleteByConditions(queryRule);
					prpCDangerTotService.deleteByConditions(queryRule);
					prpCDangerPlanService.deleteByConditions(queryRule);
					prpCDangerRiskService.deleteByConditions(queryRule);
					prpCDangerCoinsService.deleteByConditions(queryRule);
					prpCReinsTrialService.deleteByConditions(queryRule);
					prpCReinsShareService.deleteByConditions(queryRule);
					prpCDangerUnitService.deleteByConditions(queryRule);

					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("policyNo", businessNo);
					feoReinsVerifyService.deleteByConditions(queryRule);
					feoReinsReceiveService.deleteByConditions(queryRule);
					feoEngageService.deleteByConditions(queryRule);
					feoTotService.deleteByConditions(queryRule);
					feoItemService.deleteByConditions(queryRule);
					feoCoinsService.deleteByConditions(queryRule);
					feoPlanService.deleteByConditions(queryRule);
					feoEnquiryService.deleteByConditions(queryRule);
					break;
				case 'E':
					queryRule.addEqual("id.endorseNo", businessNo);
					prpPDangerItemService.deleteByConditions(queryRule);
					prpPDangerTotService.deleteByConditions(queryRule);
					prpPDangerPlanService.deleteByConditions(queryRule);
					prpPDangerRiskService.deleteByConditions(queryRule);
					prpPDangerCoinsService.deleteByConditions(queryRule);
					prpPReinsTrialService.deleteByConditions(queryRule);
					prpPReinsShareService.deleteByConditions(queryRule);
					prpPDangerUnitService.deleteByConditions(queryRule);

					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("endorseNo", businessNo);
					feoReinsVerifyService.deleteByConditions(queryRule);
					feoReinsReceiveService.deleteByConditions(queryRule);
					feoEngageService.deleteByConditions(queryRule);
					feoTotService.deleteByConditions(queryRule);
					feoItemService.deleteByConditions(queryRule);
					feoCoinsService.deleteByConditions(queryRule);
					feoPlanService.deleteByConditions(queryRule);
					feoEnquiryService.deleteByConditions(queryRule);
					break;
				case 'C':
					queryRule.addEqual("certiNo", businessNo);
					prpLDangerItemService.deleteByConditions(queryRule);
					prpLDangerTotService.deleteByConditions(queryRule);
					prpLDangerCoinsService.deleteByConditions(queryRule);
					prpLReinsTrialService.deleteByConditions(queryRule);
					prpLReinsShareService.deleteByConditions(queryRule);
					prpLDangerUnitService.deleteByConditions(queryRule);
					break;
				default:
					throw new UserException(-98, -1149, "BLPrpFeedBack.echo()", internal.getText("undwrt.service.proFeeBack.haveNoTheType"));
				}
			}

			// 打回修改开始
			switch (certiType) {
			case 'T':
				this.echoProposalJF(businessNo, status, underWriteCode, underWriteDate, flag, serverName, dbName, businessSource, nodeNo);
				blnReturn = true;
				break;
			case 'E':
				this.echoEndorJF(businessNo, status, underWriteCode, underWriteDate, flag, serverName, dbName, businessSource);
				blnReturn = true;
				break;
			case 'B':
				this.echoQta(businessNo, status, underWriteCode, underWriteDate);
				blnReturn = true;
				break;
			default:
				throw new UserException(-98, -1149, "BLPrpFeedBack.echo()", internal.getText("undwrt.service.proFeeBack.haveNoTheType"));
			}
			if (status.trim().equals("3") || status.trim().equals("1")) {
				String businessType = String.valueOf(certiType);
				if ("B".equals(businessType)) {
					PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(businessNo,"quotation");
					String hql = "from RenewalInfo where quoteNo= '" + businessNo + "'";
					List list = this.findByHql(hql);
					String editFlag = prpQmain.getEditFlag();
					if (list.size() > 0 || "2".equals(editFlag)) {
						echoPrpJF(businessType, businessNo, underWriteCode, dbManager);
					}
					return blnReturn;
				}
				echoPrpJF(businessType, businessNo, underWriteCode, dbManager);
			}
		} catch (SQLException se) {
			throw se;
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		}
		return blnReturn;
	}

	/**
	 * 核保回寫業務入口方法(提交核保）.
	 * 
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param flag
	 *            核保標誌位
	 * @param businessSource
	 *            業務來源
	 * @return 成功返回true，失敗返回false
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public boolean echoSubmit(char certiType, String businessNo, String flag, String businessSource) throws UserException, SQLException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		boolean blnReturn = false;
		PrpTmain prpTmain = new PrpTmain();
		PrpCmain prpCmain = new PrpCmain();
		PrpPhead prpPhead = new PrpPhead();
		PrpQmain prpQmain = new PrpQmain();
		PrpPheadCovernoteDto prpPheadCovernoteDto = new PrpPheadCovernoteDto();
		PrpCmainCovernoteSchema prpCmainCovernoteSchema = new PrpCmainCovernoteSchema();
		// 不再需要理赔jar包
		// DBPrpLprepay dbPrpLprepay = new DBPrpLprepay(dbManager);
		BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
		BLPrpPheadCovernote blPrpPheadCovernote = new BLPrpPheadCovernote();
		// DBPrpPheadCovernote dbPrpPheadCovernote = new DBPrpPheadCovernote(
		// dbManager);
		// DBPrpLcompensate dbPrpLcompensate = new DBPrpLcompensate(dbManager);

		try {
			switch (certiType) {
			case 'T':
				prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
				prpTmain.setUnderWriteFlag(flag);
				this.update(prpTmain);
				logger.info("查詢核保狀態:"+businessNo+"的核保狀態是:"+prpTmain.getUnderWriteFlag());
				blnReturn = true;
				break;
			case 'B':
				prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
				prpQmain.setUnderWriteFlag(flag);
				this.update(prpQmain);
				blnReturn = true;
				break;
			case 'P':
				if (businessSource.trim().equalsIgnoreCase("reins")) {
				} else {
					blPrpCmainCovernote.getData(businessNo);
					if (blPrpCmainCovernote.getSize() > 0) {
						prpCmainCovernoteSchema = blPrpCmainCovernote.getArr(0);
						prpCmainCovernoteSchema.setUnderWriteFlag(flag);
						blPrpCmainCovernote.update();
					} else {
						prpCmain = policyService.getPrpCmainByPolicyNo(businessNo);
						prpCmain.setUnderWriteFlag(flag);
						this.update(prpCmain);
					}
				}
				blnReturn = true;
				break;
			case 'E':
				if (businessSource.trim().equalsIgnoreCase("reins")) {
				} else {
					blPrpPheadCovernote.getData(businessNo);
					if (blPrpPheadCovernote.getSize() > 0) {
						// prpPheadCovernoteDto = dbPrpPheadCovernote
						// .findByPrimaryKey(businessNo);
						prpPheadCovernoteDto.setUnderWriteFlag(flag);
						this.update(prpPheadCovernoteDto);
					} else {
						prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
						prpPhead.setUnderWriteFlag(flag);
						this.update(prpPhead);
					}
				}
				blnReturn = true;
				break;
			/*
			 * case 'Y': prpLprepayDto =
			 * dbPrpLprepay.findByPrimaryKey(businessNo);
			 * prpLprepayDto.setUnderWriteFlag(flag);
			 * dbPrpLprepay.update(prpLprepayDto); blnReturn = true; break; case
			 * 'C': if (businessSource.trim().equalsIgnoreCase("reins")) { }
			 * else { prpLcompensateDto = dbPrpLcompensate
			 * .findByPrimaryKey(businessNo);
			 * prpLcompensateDto.setUnderWriteFlag(flag);
			 * dbPrpLcompensate.update(prpLcompensateDto); } blnReturn = true;
			 * break;
			 */
			default:
				throw new UserException(-98, -1149, "WfLog.query()", internal.getText("undwrt.service.proFeeBack.haveNoTheType"));
			}
		} catch (UserException ue) {
			throw ue;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			throw e;
		}
		return blnReturn;
	}

	/**
	 * 回寫授權數據.
	 * 
	 * @param iBussinessType
	 *            業務類型
	 * @param iBussinessNo
	 *            業務號
	 * @return 返回授權信息
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.PrpFeedBackService#echoAuthorizePrp(java.lang.String,
	 *      java.lang.String)
	 */
	public String echoAuthorizePrp(String iBussinessType, String iBussinessNo) throws UserException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String riskCode = "";
		String policyNo = "";
		String info = "";
		DBManager dbManager = new DBManager();
		try {
			dbManager.open("undwrtDataSource");
			dbManager.beginTransaction();
			DbPool dbpool = new DbPool();
			dbpool.setDBManager(dbManager);
			boolean authorize = false;
			// 蒋佳奇确定一张投保单只能生成一张保单，所以把老的返回collection改为实体类
			if (iBussinessType.equals("T")) {
				PrpCmain prpCmain = policyService.getPrpCmainByProposalNo(iBussinessNo);
				if (prpCmain != null) {
					policyNo = prpCmain.getPolicyNo();
					if ("7".equals(prpCmain.getUnderWriteFlag())) {
						authorize = true;
						prpCmain.setUnderWriteFlag("1");
						prpCmain.setStartDate(new com.sinosoft.sysframework.common.datatype.DateTime(new java.util.Date(), 13));
						this.update(prpCmain);
						PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(iBussinessNo);
						if (prpTmain != null) {
							prpTmain.setUnderWriteFlag("1");
							this.update(prpTmain);
						}
					}
				}
			}
			if (iBussinessType.equals("E")) {
				PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(iBussinessNo);
				if (prpPhead != null) {
					if ("7".equals(prpPhead.getUnderWriteFlag())) {
						authorize = true;
						prpPhead.setUnderWriteFlag("1");
						prpPhead.setValidDate(new com.sinosoft.sysframework.common.datatype.DateTime(new java.util.Date(), 13));
						this.update(prpPhead);
					}
				}
			}

			if (authorize) {
				// 生成中间成本和手续费
				if (iBussinessType.equals("T") || iBussinessType.equals("P")) {
					BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
					blPrpCmainCovernote.getData(iBussinessNo);
					if (blPrpCmainCovernote.getSize() > 0) {

					} else {
						BLPrpMiddleCost blPrpMiddleCost = new BLPrpMiddleCost();
						blPrpMiddleCost.createDisPremium(dbpool, "P", policyNo);
						BLPrpCommission blPrpCommission = new BLPrpCommission();
						blPrpCommission.createCommission(dbpool, "P", policyNo);
					}
				}
				// 批单变化引起的手续费和中间成本的变化
				if (iBussinessType.equals("E")) {
					BLPrpPmainCovernote blPrpPmainCovernote = new BLPrpPmainCovernote();
					blPrpPmainCovernote.getData(iBussinessNo);
					if (blPrpPmainCovernote.getSize() > 0) {

					} else {
						BLPrpMiddleCost blPrpMiddleCost = new BLPrpMiddleCost();
						blPrpMiddleCost.createDisPremium(dbpool, "E", iBussinessNo);
						BLPrpCommission blPrpCommission = new BLPrpCommission();
						blPrpCommission.createCommission(dbpool, "E", iBussinessNo);
					}
				}
				// 转入应收应付信息
				// 分入业务不生成收付费信息但需生成分入帐单
				if (!this.isReinsCeded(iBussinessNo, iBussinessType)) {
					BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
					blPrpCmainCovernote.getData(iBussinessNo);
					String strJudicalCode = ""; // 暂保单、预约协议实收保费标志－0.实收 1.未实收
												// 当做实收时转入应收应付信息
					if (blPrpCmainCovernote.getSize() > 0) {
						strJudicalCode = blPrpCmainCovernote.getArr(0).getJudicalCode();
						if ("1".equals(strJudicalCode)) {
							PrpTransSffIntf prpTransSffIntf = new PrpTransSffIntf();
							PrpallPremiumInterf prpallPremiumInterf = new PrpallPremiumInterf();
							prpTransSffIntf.transCovernoteData(dbpool, iBussinessType, iBussinessNo);
							prpallPremiumInterf.transPremiumData(dbpool, "ALL", iBussinessNo);
							// 收付改造调整end
						}
					} else {
						PrpTransSffIntf prpTransSffIntf = new PrpTransSffIntf();
						PrpallPremiumInterf prpallPremiumInterf = new PrpallPremiumInterf();
						// 取险种代码，0402自动分险别挂帐，调用双核中的程序
						if (iBussinessType.equals("T") || iBussinessType.equals("P")) {
							// 取险种代码，0402自动分险别挂帐，调用双核中的程序
							BLPrpCmain blPrpCmain = new BLPrpCmain();
							blPrpCmain.getData(dbpool, policyNo);
							riskCode = blPrpCmain.getArr(0).getRiskCode();
							if (riskCode.equals("0402") && iBussinessType.equals("T")) {
								prpTransSffIntf.transData0402(dbpool, "P", policyNo);
								prpallPremiumInterf.transPremiumData(dbpool, "ALL", policyNo);
							} else {
								prpTransSffIntf.transData(dbpool, "P", policyNo);
								prpallPremiumInterf.transPremiumData(dbpool, "ALL", policyNo);
							}
						} else {
							BLPrpPmainCovernote blPrpPmainCovernote = new BLPrpPmainCovernote();
							blPrpPmainCovernote.getData(iBussinessNo);
							if (blPrpPmainCovernote.getSize() > 0) {
								String chgPremium = blPrpPmainCovernote.getArr(0).getChgPremium();
								if (!chgPremium.equals("0.0")) {
									prpTransSffIntf.transCovernoteData(dbpool, iBussinessType, iBussinessNo);
									prpallPremiumInterf.transPremiumData(dbpool, "ALL", iBussinessNo);
								}
							} else {
								prpTransSffIntf.transData(dbpool, iBussinessType, iBussinessNo);
								BLPrpPcarshipTax blPrpPcarsipTax = new BLPrpPcarshipTax();
								blPrpPcarsipTax.getData(iBussinessNo);
								if (blPrpPcarsipTax.getSize() > 0) {
									prpTransSffIntf.transCarShipTax(dbpool, "E", iBussinessNo);
								}
								prpallPremiumInterf.transPremiumData(dbpool, "ALL", iBussinessNo);
							}
						}
					}
				}

				// 圆丰产品送与财务接口表
				if (iBussinessType.equals("T") || iBussinessType.equals("E")) {
					/*
					 * blPrpInvestIntfAction.undwrtSaveAccount(dbManager,
					 * iBussinessType, iBussinessNo, policyNo);
					 */
				}
				if (iBussinessType.equals("T")) {
					info = internal.getText("undwrt.service.prpFeeBack.warranty") + policyNo
							+ internal.getText("undwrt.service.prpFeeBack.authoritySuccessPass");
				}
				if (iBussinessType.equals("E")) {
					info = internal.getText("undwrt.service.prpFeeBack.endorsement") + iBussinessNo
							+ internal.getText("undwrt.service.prpFeeBack.authoritySuccessPass");
				}

			} else {
				if (iBussinessType.equals("T")) {
					info = internal.getText("undwrt.service.prpFeeBack.thisInsuranceSlip") + iBussinessNo
							+ internal.getText("undwrt.service.prpFeeBack.thisWarranty") + internal.getText("undwrt.service.prpFeeBack.haveAuthorited");
				}
				if (iBussinessType.equals("E")) {
					info = internal.getText("undwrt.service.prpFeeBack.thisEndorsement") + iBussinessNo
							+ internal.getText("undwrt.service.prpFeeBack.haveAuthorited");
				}
			}
		} catch (Exception e) {
			dbManager.rollbackTransaction();
			e.printStackTrace();
			if (iBussinessType.equals("T")) {
				info = internal.getText("undwrt.service.prpFeeBack.warranty") + policyNo + internal.getText("undwrt.service.prpFeeBack.authorityFail");
			}
			if (iBussinessType.equals("E")) {
				info = internal.getText("undwrt.service.prpFeeBack.endorsement") + iBussinessNo + internal.getText("undwrt.service.prpFeeBack.authorityFail");
			}
			throw e;
		} finally {
			dbManager.commitTransaction();
			dbManager.close();
		}
		return info;
	}

	/**
	 * 判斷是否是分入業務.
	 * 
	 * @param bussinessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 是返回true，否返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean isReinsCeded(String bussinessNo, String businessType) throws Exception {

		// 分入业务批单信息也不送收付
		BLPrpTmain blPrpTmain = new BLPrpTmain();
		BLPrpCmain blPrpCmain = new BLPrpCmain();
		BLPrpPmain blPrpPmain = new BLPrpPmain();
		PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
		PrpCmainSchema prpCmainSchema = new PrpCmainSchema();
		PrpPmainSchema prpPmainSchema = new PrpPmainSchema();
		if (businessType.equals("T")) {
			blPrpTmain.getData(bussinessNo);
			if (blPrpTmain.getSize() > 0) {
				prpTmainSchema = blPrpTmain.getArr(0);
				if (prpTmainSchema.getBusinessFlag().equals("1")) {
					return true;
				}
			}
		} else if (businessType.equals("E")) {
			blPrpPmain.getData(bussinessNo);
			if (blPrpPmain.getSize() > 0) {
				prpPmainSchema = blPrpPmain.getArr(0);
				if (prpPmainSchema.getBusinessFlag().equals("1")) {
					return true;
				}
			}
		} else if (businessType.equals("P")) {
			blPrpCmain.getData(bussinessNo);
			if (blPrpCmain.getSize() > 0) {
				prpCmainSchema = blPrpCmain.getArr(0);
				if (prpCmainSchema.getBusinessFlag().equals("1")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 回寫報價單信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.PrpFeedBackService#echoQta(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      com.sinosoft.sysframework.common.datatype.DateTime)
	 */
	public void echoQta(String businessNo, String status, String underwriteCode, DateTime underwriteDate) throws UserException, SQLException, Exception {

		try {
			this.echoProposalQta(businessNo, status, underwriteCode, underwriteDate, "", "", "", "");
		} catch (UserException ue) {
			throw ue;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		}

		catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 回寫要保書主表數據.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @param flag
	 *            標誌
	 * @param serverName
	 *            服務器名稱
	 * @param dbname
	 *            數據庫名稱
	 * @param businessSource
	 *            業務來源
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	private void echoProposal(String businessNo, String status, String underwriteCode, DateTime underwriteDate, String flag, String serverName, String dbname,
			String businessSource) throws UserException, SQLException, Exception {
		PrpTmain prpTmain = new PrpTmain();
		PrpDuser prpDuser = new PrpDuser();
		try {
			// 再保险没有投保核保则返回
			if (businessSource.trim().equals("reins")) {
				return;
			}

			// 更新投保单主表数据
			prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			if (status.trim().equals("3")) {
			}
			if (status.trim().equals("2")) {
				prpTmain.setApproverCode("");
			}
			prpDuser = prpDuserService.getUser(underwriteCode);
			prpTmain.setUnderWriteFlag(status);
			prpTmain.setUnderWriteCode(underwriteCode);
			prpTmain.setUnderWriteName(prpDuser.getUserName());
			prpTmain.setUnderWriteEndDate(underwriteDate);
			this.update(prpTmain);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 回寫要保書對應報價單數據.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @param flag
	 *            標誌
	 * @param serverName
	 *            服務器名稱
	 * @param dbname
	 *            數據庫名稱
	 * @param businessSource
	 *            業務來源
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	private void echoProposalQta(String businessNo, String status, String underwriteCode, DateTime underwriteDate, String flag, String serverName,
			String dbname, String businessSource) throws UserException, SQLException, Exception {
		PrpQmain prpQmain = new PrpQmain();
		PrpDuser prpDuser = new PrpDuser();
		String underwriteName = "";
		try {
			// 再保险没有投保核保则返回
			if (businessSource.trim().equals("reins")) {
				return;
			}

			prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");

			prpDuser = prpDuserService.findByPrimaryKey(underwriteCode);
			if (prpDuser != null) {
				underwriteName = prpDuser.getUserName();
			}
			if (status.trim().equals("3")) {
				underwriteCode = "AutoUndwrt";
				underwriteName = "自動核保";
			}
			if (status.trim().equals("2")) {
				prpQmain.setApproverCode("");
			}

			// 更新报价单主表数据
			prpQmain.setUnderWriteFlag(status);
			prpQmain.setUnderWriteCode(underwriteCode);
			prpQmain.setUnderWriteName(underwriteName);
			prpQmain.setUnderWriteEndDate(underwriteDate);
			// add by xuhuiling begin
			String valueType = taskDealService.getRenGongKaiGuanStatu();
			String workStatus = taskDealService.getWorkStatusForBusiNo(
					businessNo, "B");
			// 當人工開關為開啟時並且作業狀態為不執行時講作業狀態修改為人工審核標記
			if (valueType != null && valueType.equals("1")&& workStatus != null&& !"".equals(workStatus)
					&& !"00".equals(workStatus) && !"04".equals(workStatus)) {
				// 需要講 ‘拒限保’，‘名單檢測’，‘風險評級’ 這三個值設置”“
				prpQmain.setRefuseLimiteInsurance("");
				prpQmain.setListDetection("");
				prpQmain.setRiskRating("");
				prpQmain.setWorkStatus("07");
			}
			// add by xuhuiling end
			policyService.updatePrpQmain(prpQmain);
			System.out.println("==============更新報價單信息==========");
			System.out.println("========UnderWriteFlag======"+prpQmain.getUnderWriteFlag());
			System.out.println("========UnderWriteCode======"+prpQmain.getUnderWriteCode());
			System.out.println("========UnderWriteName======"+prpQmain.getUnderWriteName());
			System.out.println("========UnderWriteEndDate======"+prpQmain.getUnderWriteEndDate());
		} catch (UserException ue) {
			throw ue;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 回寫要保書主表數據.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @param flag
	 *            標誌
	 * @param serverName
	 *            服務器名稱
	 * @param dbname
	 *            數據庫名稱
	 * @param businessSource
	 *            業務來源
	 * @param currendNodeNo
	 *            當前節點號
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	private String echoProposal(String businessNo, String status, String underwriteCode, DateTime underwriteDate, String flag, String serverName,
			String dbname, String businessSource, int currendNodeNo) throws UserException, SQLException, Exception {
		PrpTmain prpTmain = new PrpTmain();
		PrpDuser prpDuser = new PrpDuser();
		String authorize_control = "0";
		try {
			// 再保险没有投保核保则返回
			if (businessSource.trim().equals("reins")) {
				return "";
			}

			// 更新投保单主表数据
			prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			if (status.trim().equals("3")) {
				// underwriteCode = prpTmainDto.getApproverCode();
			}
			if (status.trim().equals("2")) {
				prpTmain.setApproverCode("");
			}
			// 商业车险增加授权控制 modify by yishengcheng
			/*
			 * if("0501,0502".indexOf(prpTmainDto.getRiskCode())!=-1){ status =
			 * "7"; //商业车险增加授权控制标志位为7 authorize_control = "1"; }
			 */
			prpDuser = prpDuserService.getUser(underwriteCode);
			if("F".equals(prpTmain.getClassCode()) || "R".equals(prpTmain.getClassCode()))
			{
				prpTmain.setUnderWriteFlag("5");
			//mantis： FIR0557，處理人員：bj085，需求單編號：FIR0557 住火_續保件批單調整審核通過壓回續保註記邏輯 START		
			//因正式機無此段程式 故調整為一致
//			}else if("PA".equals(prpTmain.getRiskCode())&&"2".equals(prpTmain.getEditFlag())){// add by CSY 20160418 伤害险续保 险生成预核保 start 
//				prpTmain.setUnderWriteFlag("5");
				// add by CSY 20160418 伤害险续保 险生成预核保 end
				//mantis： FIR0557，處理人員：bj085，需求單編號：FIR0557 住火_續保件批單調整審核通過壓回續保註記邏輯 END
			}else{
				prpTmain.setUnderWriteFlag(status);
			}
			prpTmain.setUnderWriteCode(underwriteCode);
			prpTmain.setUnderWriteName(prpDuser.getUserName());
			prpTmain.setUnderWriteEndDate(underwriteDate);
			prpTmain.setProposalLevel("" + currendNodeNo);
			// this.update(prpTmain);
			// add by xuhuiling begin
			String valueType = taskDealService.getRenGongKaiGuanStatu();
			String workStatus = taskDealService.getWorkStatusForBusiNo(
					businessNo, "T");
			// 當人工開關為開啟時並且作業狀態為不執行時講作業狀態修改為人工審核標記
			if (valueType != null && valueType.equals("1")&& workStatus != null&& !"".equals(workStatus)
					&& !"00".equals(workStatus) && !"04".equals(workStatus)) {
				// 需要講 ‘拒限保’，‘名單檢測’，‘風險評級’ 這三個值設置”“
				prpTmain.setRefuseLimiteInsurance("");
				prpTmain.setListDetection("");
				prpTmain.setRiskRating("");
				prpTmain.setWorkStatus("07");
			}
			// add by xuhuiling end
			this.updateTmain(prpTmain);
		} catch (Exception e) {
			throw e;
		}
		return authorize_control;
	}

	/**
	 * 見費出單回寫業務數據.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @param flag
	 *            標誌
	 * @param serverName
	 *            服務器名稱
	 * @param dbname
	 *            數據庫名稱
	 * @param businessSource
	 *            業務來源
	 * @param nodeNo
	 *            節點號
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	private void echoProposalJF(String businessNo, String status, String underwriteCode, DateTime underwriteDate, String flag, String serverName,
			String dbname, String businessSource, int nodeNo) throws UserException, SQLException, Exception {
		PrpTmain prpTmain = new PrpTmain();
		PrpJfTime prpJfTime = new PrpJfTime();
		PrpJfTimeId id = new PrpJfTimeId();
		PrpDuser prpDuser = new PrpDuser();
		String statusJF = "";
		try {
			// 再保险没有投保核保则返回
			if (businessSource.trim().equals("reins")) {
				return;
			}

			// 更新投保单主表数据
			prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			if (("1").equals(prpTmain.getBusinessflag())) {
				this.isReinsCeded = true;
			}
			if (status.trim().equals("1")) {
				statusJF = "5";// 见费出单的自动核保时标志为6
				// underwriteCode = prpTmainDto.getApproverCode();
			} else if (status.trim().equals("2")) {
				prpTmain.setApproverCode("");
				statusJF = "2";
			} else if (status.trim().equals("3")) {
				statusJF = "6";// 见费出单的自动核保时标志为6
				// underwriteCode = prpTmainDto.getApproverCode();
			}
			prpDuser = prpDuserService.getUser(underwriteCode);
			prpTmain.setUnderWriteFlag(statusJF);
			prpTmain.setUnderWriteCode(underwriteCode);
			prpTmain.setUnderWriteName(prpDuser.getUserName());
			prpTmain.setProposalLevel("" + nodeNo);
			// add by chengkai;20080529;由于生成投保单时已经有标志位所以这里不进行赋值
			// prpTmainDto.setJFeeFlag("1");

			prpTmain.setPreCheckDate(underwriteDate);
			prpTmain.setUnderWriteEndDate(underwriteDate);
			this.update(prpTmain);
			id.setBusinessNo(businessNo);
			id.setCertiType("T");
			prpJfTime.setId(id);
			// prpJFTimeDto.setPrePayRefTim((DateTime.current()));
			this.save(prpJfTime);

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 回寫保單數據.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @param flag
	 *            標誌
	 * @param serverName
	 *            服務器名稱
	 * @param dbname
	 *            數據庫名稱
	 * @param businessSource
	 *            業務來源
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	private void echoPolicy(String businessNo, String status, String underwriteCode, DateTime underwriteDate, String flag, String serverName, String dbname,
			String businessSource) throws UserException, SQLException, Exception {
		PrpCmain prpCmain = new PrpCmain();
		PrpDuser prpDuser = new PrpDuser();
		// DBPrpCproduct dbPrpCproduct = new DBPrpCproduct(dbManager);
		PrpCproductDto prpCproductDto = null;
		Collection collection = null;
		Iterator iterator = null;
		String strSql = "";
		// 保单传数给再保险类
		try {
			if (businessSource.trim().equals("reins")) {

			} else {
				prpCmain = policyService.getPrpCmainByPolicyNo(businessNo);
				if (status.trim().equals("3")) {
					underwriteCode = prpCmain.getApproverCode();

					// add by chengkai 20070718，如果是自动核保是没有复核人的。所以取操作员就可以。begin;
					if (underwriteCode == null || underwriteCode.equals("")) {
						underwriteCode = prpCmain.getOperatorCode();
					}
					// add by chengkai 20070718，如果是自动核保是没有复核人的。所以取操作员就可以。end;
				}
				prpDuser = prpDuserService.getUser(underwriteCode);
				BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
				PrpCmainCovernoteSchema prpCmainCovernoteSchema = new PrpCmainCovernoteSchema();
				blPrpCmainCovernote.getData(businessNo);
				if (blPrpCmainCovernote.getSize() > 0) {
					prpCmainCovernoteSchema = blPrpCmainCovernote.getArr(0);
					prpCmainCovernoteSchema.setUnderWriteFlag(status);
					prpCmainCovernoteSchema.setUnderWriteCode(underwriteCode);
					prpCmainCovernoteSchema.setUnderWriteName(prpDuser.getUserName());
					prpCmainCovernoteSchema.setUnderWriteEndDate("" + underwriteDate);
					blPrpCmainCovernote.update();

					// add by zhaoning20091013 begin Reason 增加费用联动控制策略
					strSql = " PolicyNo = '" + prpCmainCovernoteSchema.getPolicyNo() + "'";
					// collection = dbPrpCproduct.findByConditions(strSql);
					collection = new ArrayList();
					iterator = collection.iterator();
					while (iterator.hasNext()) {
						prpCproductDto = (PrpCproductDto) iterator.next();
						break;
					}
					if (prpCproductDto != null) {
						expenseControlDealService.echoExpenseControl("P", prpCmainCovernoteSchema.getPolicyNo(), prpCmainCovernoteSchema.getPolicyNo(),
								prpCmainCovernoteSchema.getComCode(), prpCmainCovernoteSchema.getRiskCode(), prpCproductDto.getProductCode(), "");
					} else {
						expenseControlDealService.echoExpenseControl("P", prpCmainCovernoteSchema.getPolicyNo(), prpCmainCovernoteSchema.getPolicyNo(),
								prpCmainCovernoteSchema.getComCode(), prpCmainCovernoteSchema.getRiskCode(), "", "");
					}
				} else {
					prpCmain.setUnderWriteFlag(status);
					prpCmain.setUnderWriteCode(underwriteCode);
					prpCmain.setUnderWriteName(prpDuser.getUserName());
					prpCmain.setUnderWriteEndDate(underwriteDate);
					this.update(prpCmain);

					// add by zhaoning20091013 begin Reason 增加费用联动控制策略
					strSql = " PolicyNo = '" + prpCmain.getPolicyNo() + "'";
					// collection = dbPrpCproduct.findByConditions(strSql);
					collection = new ArrayList();
					iterator = collection.iterator();
					while (iterator.hasNext()) {
						prpCproductDto = (PrpCproductDto) iterator.next();
						break;
					}
					if (prpCproductDto != null) {
						expenseControlDealService.echoExpenseControl("P", prpCmain.getPolicyNo(), prpCmain.getPolicyNo(), prpCmain.getComCode(),
								prpCmain.getRiskCode(), prpCproductDto.getProductCode(), "");
					} else {
						expenseControlDealService.echoExpenseControl("P", prpCmain.getPolicyNo(), prpCmain.getPolicyNo(), prpCmain.getComCode(),
								prpCmain.getRiskCode(), "", "");
					}
				}
			}
		} catch (UserException ue) {
			throw ue;
		} catch (SQLException sqle) {
			sqle.printStackTrace();

			throw sqle;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 回寫保單數據信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @param flag
	 *            標誌
	 * @param serverName
	 *            服務器名稱
	 * @param dbname
	 *            數據庫名稱
	 * @param businessSource
	 *            業務來源
	 * @param currendNodeNo
	 *            當前節點號
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	private void echoPolicy(String businessNo, String status, String underwriteCode, DateTime underwriteDate, String flag, String serverName, String dbname,
			String businessSource, int currendNodeNo) throws UserException, SQLException, Exception {
		PrpCmain prpCmain = new PrpCmain();
		PrpDuser prpDuser = new PrpDuser();
		// DBPrpCproduct dbPrpCproduct = new DBPrpCproduct(dbManager);
		PrpCproductDto prpCproductDto = null;
		Collection collection = null;
		Iterator iterator = null;
		String strSql = "";
		// 保单传数给再保险类
		// FIX0322 modify by zhupengju begin
		// DBDataSource dbDataSource = DBFactory.getDB("undwrtDataSource");
		// dbPool.open(dbDataSource.getJndiName());
		// FIX0322 modify by zhupengju end
		try {
			if (businessSource.trim().equals("reins")) {

			} else {
				prpCmain = policyService.getPrpCmainByPolicyNo(businessNo);
				if (status.trim().equals("3")) {
					underwriteCode = prpCmain.getApproverCode();

					// add by chengkai 20070718，如果是自动核保是没有复核人的。所以取操作员就可以。begin;
					if (underwriteCode == null || underwriteCode.equals("")) {
						underwriteCode = prpCmain.getOperatorCode();
					}
					// add by chengkai 20070718，如果是自动核保是没有复核人的。所以取操作员就可以。end;
				}
				prpDuser = prpDuserService.getUser(underwriteCode);
				BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
				PrpCmainCovernoteSchema prpCmainCovernoteSchema = new PrpCmainCovernoteSchema();
				blPrpCmainCovernote.getData(businessNo);
				if (blPrpCmainCovernote.getSize() > 0) {
					prpCmainCovernoteSchema = blPrpCmainCovernote.getArr(0);
					prpCmainCovernoteSchema.setUnderWriteFlag(status);
					prpCmainCovernoteSchema.setUnderWriteCode(underwriteCode);
					prpCmainCovernoteSchema.setUnderWriteName(prpDuser.getUserName());
					prpCmainCovernoteSchema.setUnderWriteEndDate("" + underwriteDate);
					prpCmainCovernoteSchema.setProposalLevel("" + currendNodeNo);
					blPrpCmainCovernote.update();

					// add by zhaoning20091013 begin Reason 增加费用联动控制策略
					strSql = " PolicyNo = '" + prpCmainCovernoteSchema.getPolicyNo() + "'";
					// collection = dbPrpCproduct.findByConditions(strSql);
					collection = new ArrayList();
					iterator = collection.iterator();
					while (iterator.hasNext()) {
						prpCproductDto = (PrpCproductDto) iterator.next();
						break;
					}
					if (prpCproductDto != null) {
						expenseControlDealService.echoExpenseControl("P", prpCmainCovernoteSchema.getPolicyNo(), prpCmainCovernoteSchema.getPolicyNo(),
								prpCmainCovernoteSchema.getComCode(), prpCmainCovernoteSchema.getRiskCode(), prpCproductDto.getProductCode(), "");
					} else {
						expenseControlDealService.echoExpenseControl("P", prpCmainCovernoteSchema.getPolicyNo(), prpCmainCovernoteSchema.getPolicyNo(),
								prpCmainCovernoteSchema.getComCode(), prpCmainCovernoteSchema.getRiskCode(), "", "");
					}
					// add by zhaoning20091013 end
				} else {
					prpCmain.setUnderWriteFlag(status);
					prpCmain.setUnderWriteCode(underwriteCode);
					prpCmain.setUnderWriteName(prpDuser.getUserName());
					prpCmain.setUnderWriteEndDate(underwriteDate);
					this.update(prpCmain);

					// add by zhaoning20091013 begin Reason 增加费用联动控制策略
					strSql = " PolicyNo = '" + prpCmain.getPolicyNo() + "'";
					// collection = dbPrpCproduct.findByConditions(strSql);
					iterator = collection.iterator();
					while (iterator.hasNext()) {
						prpCproductDto = (PrpCproductDto) iterator.next();
						break;
					}
					if (prpCproductDto != null) {
						expenseControlDealService.echoExpenseControl("P", prpCmain.getPolicyNo(), prpCmain.getPolicyNo(), prpCmain.getComCode(),
								prpCmain.getRiskCode(), prpCproductDto.getProductCode(), "");
					} else {
						expenseControlDealService.echoExpenseControl("P", prpCmain.getPolicyNo(), prpCmain.getPolicyNo(), prpCmain.getComCode(),
								prpCmain.getRiskCode(), "", "");
					}
				}
			}
		} catch (UserException ue) {
			throw ue;
		} catch (SQLException sqle) {
			sqle.printStackTrace();

			throw sqle;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 回寫批單數據業務信息.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @param flag
	 *            標誌
	 * @param serverName
	 *            服務器名稱
	 * @param dbname
	 *            數據庫名稱
	 * @param businessSource
	 *            業務來源
	 * @return “0”
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	private String echoEndor(DBManager dbManager, String businessNo, String status, String underwriteCode, DateTime underwriteDate, String flag,
			String serverName, String dbname, String businessSource) throws UserException, SQLException, Exception {
		// DBPrpPheadCovernote dbPrpPheadCovernote = new DBPrpPheadCovernote(
		// dbManager);
		PrpPheadCovernoteDto prpPheadCovernoteDto = new PrpPheadCovernoteDto();
		BLEndorseCovernote blEndorseCovernote = new BLEndorseCovernote();
		PrpPhead prpPhead = new PrpPhead();
		PrpDuser prpDuser = new PrpDuser();
		DbPool dbPool = new DbPool();
		BLPrpPheadCovernote blPrpPheadCovernote = new BLPrpPheadCovernote();
		blPrpPheadCovernote.getData(businessNo);
		dbPool.setDBManager(dbManager);
		String reendorNo = "";
		String StrOthFlag = "";
		String StrProposalno = "";
		String authorize_control = "0";
		try {
			if (businessSource.trim().equals("reins")) {
			} else {

				prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
				// prpPheadCovernoteDto = dbPrpPheadCovernote
				// .findByPrimaryKey(businessNo);
				if (status.trim().equals("3")) {
					underwriteCode = prpPhead.getApproverCode();

					// add by chengkai 20070718，如果是自动核保是没有复核人的。所以取操作员就可以。begin;
					if (underwriteCode == null || underwriteCode.equals("")) {
						underwriteCode = prpPhead.getOperatorCode();
					}
					// add by chengkai 20070718，如果是自动核保是没有复核人的。所以取操作员就可以。end;
				}
				if (status.trim().equals("2")) {
					if (blPrpPheadCovernote.getSize() > 0) {
						prpPheadCovernoteDto.setApproverCode("");
					} else {
						prpPhead.setApproverCode("");
					}
				}
				prpDuser = prpDuserService.getUser(underwriteCode);
				if (blPrpPheadCovernote.getSize() > 0) {
					prpPheadCovernoteDto.setUnderWriteFlag(status);
					prpPheadCovernoteDto.setUnderWriteCode(underwriteCode);
					prpPheadCovernoteDto.setUnderWriteName(prpDuser.getUserName());
					prpPheadCovernoteDto.setUnderWriteEndDate(underwriteDate);
					// dbPrpPheadCovernote.update(prpPheadCovernoteDto);
				} else {
					// 商业车险增加授权控制, modify by yishengcheng
					/*
					 * if ("0501,0502".indexOf(prpPheadDto.getRiskCode()) != -1)
					 * { status = "7"; // 商业车险增加授权控制标志位为7 authorize_control =
					 * "1"; }
					 */
					prpPhead.setUnderWriteFlag(status);
					prpPhead.setUnderWriteCode(underwriteCode);
					prpPhead.setUnderWriteName(prpDuser.getUserName());
					prpPhead.setUnderWriteEndDate(underwriteDate);
					
					// add by xuhuiling begin
					PrpPmain prpPmain = prpPhead.getPrpPmains().get(0);
					String valueType = taskDealService.getRenGongKaiGuanStatu();
					String workStatus = taskDealService.getWorkStatusForBusiNo(
							businessNo, "E");
					// 當人工開關為開啟時並且作業狀態為不執行時講作業狀態修改為人工審核標記
					if (valueType != null && valueType.equals("1") && workStatus != null&& !"".equals(workStatus)
							&& !"00".equals(workStatus) && !"04".equals(workStatus)) {
						// 需要講 ‘拒限保’，‘名單檢測’，‘風險評級’ 這三個值設置”“
						prpPmain.setRefuseLimiteInsurance("");
						prpPmain.setListDetection("");
						prpPmain.setRiskRating("");
						prpPmain.setWorkStatus("07");
					}
					// add by xuhuiling end
					this.update(prpPhead);
				}
				// 核批通过后(1)批单缓存CP表覆盖保单C表(2)转再保险数据
				if (status.trim().equals("3") || status.trim().equals("1")) {
					String strPolicyNo = "";
					if (blPrpPheadCovernote.getSize() > 0) {
						// 批改流程调整
						strPolicyNo = prpPheadCovernoteDto.getPolicyNo();
						BLCPolicyCovernote blCPolicyCovernote = new BLCPolicyCovernote();
						// modify begin by zhangTC 2006-04-17 核批不在一个事务
						blCPolicyCovernote.getData(dbPool, strPolicyNo); // 取CP表
						// modify end by zhangTC 2006-04-17 核批不在一个事务
						BLPolicyCovernote blPolicyCovernote = new BLPolicyCovernote();
						blPolicyCovernote.evaluateFromCPToC(blCPolicyCovernote); // 用CP表内容生成C表??????
						// 需要转换dbpool
						blPolicyCovernote.saveForEndor(dbPool); // C表先删后插，主表更新
					} else {
						// 批改流程调整
						strPolicyNo = prpPhead.getPolicyNo();
						PrpCPmain prpCPmain = new PrpCPmain();
						PrpTmain prpTmain = new PrpTmain();
						prpCPmain = prpCpMainService.getPrpCpMainByPolicyNo(strPolicyNo);
						StrOthFlag = prpCPmain.getOthFlag();
						StrProposalno = prpCPmain.getProposalNo();
						prpTmain = policyService.getPrpTmainByProposalNo(StrProposalno);
						prpTmain.setOthFlag(StrOthFlag);
						this.update(prpTmain);
						/*
						 * BLPolicy blPolicy = new BLPolicy();
						 * //blPolicy.evaluateFromCPToC(blCPolicy); //
						 * 用CP表内容生成C表?????? // 需要转换dbpool if
						 * ("27,03".indexOf(prpPhead.getClassCode()) > -1 &&
						 * !"2727".equals(prpPhead.getRiskCode()) &&
						 * !"2729".equals(prpPhead.getRiskCode()) &&
						 * !"2739".equals(prpPhead.getRiskCode())) { //
						 * blPolicy.
						 * saveGroupForEndor(dbPool,prpPheadDto.getEndorseNo());
						 * // // C表先删后插，主表更新 blPolicy.saveGroupForEndor(dbPool,
						 * prpPhead.getEndorseNo(), prpPhead.getEndorType()); //
						 * C表先删后插，主表更新 } else { //blPolicy.saveForEndor(dbPool);
						 * // C表先删后插，主表更新 }
						 */
						this.proposalToPolicyOrCPToC("E", businessNo);
						// modify begin by fengbo 20070420 这部分已放到再保接口，删掉相应程序
						// 核批通过时，再保生成分批单start
						// modify begin 2007-04-10 bylihua 双核与再保接口分开
						reinsUndrtInterfAction.reinsReendorCal(businessNo);
						PrpJfTime prpJfTime = new PrpJfTime();
						PrpJfTimeId id = new PrpJfTimeId();
						id.setBusinessNo(businessNo);
						id.setCertiType("E");
						prpJfTime.setId(id);
						prpJfTime.setPolicyNo(strPolicyNo);
						prpJfTime.setFalg("0");
						prpJfTime.setUndwrtTime(new DateTime(new DateTime().current().toString().substring(0, 10)));
						this.save(prpJfTime);
					}
					// modify end 2007-04-10 bylihua 双核与再保接口分开
					// modify end by fengbo 20070420 这部分已放到再保接口，删掉相应程序
					// 核批通过时，再保生成分批单end

					// ETL需求：批单核批通过往prpincrment表插入一条记录 GPIC 20081007 begin
					BLProposalPolicy blProposalPolicy = new BLProposalPolicy();
					blProposalPolicy.echoCrment(dbPool, businessNo, strPolicyNo, 1);
					// ETL需求：批单核批通过往prpincrment表插入一条记录 GPIC 20081007 begin

					// add by mxy 20090422 begin 车险停驶批改
					// 临时注掉的代码，海超不确定有用20130823
					/*
					 * if (prpPhead != null &&
					 * ("A".equals(prpPhead.getClassCode(
					 * ))||"B".equals(prpPhead.getClassCode()))) { PrpPheadSub
					 * prpPheadSub = new PrpPheadSub(); String strSQL =
					 * "select * from PrpPheadSub Where endorseno = (select max(EndorseNo) from prppheadsub where PolicyNo = '"
					 * + strPolicyNo + "' and EndorseNo != '" + businessNo +
					 * "' and ValidStatus = '1')"; prpPheadSub = (PrpPheadSub)
					 * super
					 * .getSession().createSQLQuery(strSQL).addEntity(PrpPheadSub
					 * .class); prpPheadSub.setValidstatus("0");
					 * this.update(prpPheadSub); }
					 */
				}
			}
		} catch (UserException ue) {
			throw ue;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			throw e;
		} finally {

		}
		return authorize_control;
	}

	/**
	 * 回寫見費出單批單數據業務信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @param flag
	 *            標誌
	 * @param serverName
	 *            服務器名稱
	 * @param dbname
	 *            數據庫名稱
	 * @param businessSource
	 *            業務來源
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	private void echoEndorJF(String businessNo, String status, String underwriteCode, DateTime underwriteDate, String flag, String serverName, String dbname,
			String businessSource) throws UserException, SQLException, Exception {
		PrpPhead prpPhead = new PrpPhead();
		PrpDuser prpDuser = new PrpDuser();
		PrpJfTime prpJfTime = new PrpJfTime();
		PrpJfTimeId id = new PrpJfTimeId();
		String statusJF = "";
		try {
			if (businessSource.trim().equals("reins")) {
			} else {

				prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
				if (status.trim().equals("1")) {
					statusJF = "5";
				} else if (status.trim().equals("2")) {
					prpPhead.setApproverCode("");
					statusJF = "2";
				} else if (status.trim().equals("3")) {
					underwriteCode = prpPhead.getApproverCode();
					statusJF = "6";
					// add by chengkai 20070718，如果是自动核保是没有复核人的。所以取操作员就可以。begin;
					if (underwriteCode == null || underwriteCode.equals("")) {
						underwriteCode = prpPhead.getOperatorCode();
					}
					// add by chengkai 20070718，如果是自动核保是没有复核人的。所以取操作员就可以。end;
				}
				prpDuser = prpDuserService.getUser(underwriteCode);
				//校驗是否需要實收，收費出單的批單若不需要實收，則置為核保通過狀態
				boolean needPaidFlag = this.checkIsNeadPaid(prpPhead);
				if(needPaidFlag) {
					prpPhead.setUnderWriteFlag("5");
				} else {
					prpPhead.setUnderWriteFlag("1");
				}			
				prpPhead.setUnderWriteCode(underwriteCode);
				prpPhead.setUnderWriteName(prpDuser.getUserName());
				prpPhead.setUnderWriteEndDate(underwriteDate);

				// add by chengkai;20080529;由于生成投保单时已经有标志位所以这里不进行赋值
				// prpPheadDto.setJFeeFlag("1");

				// prpPheadDto.set预审核时间(underwriteDate);
				// prpPheadDto.set见费出单标志(underwriteDate);
				this.update(prpPhead);
				id.setBusinessNo(businessNo);
				id.setCertiType("E");
				prpJfTime.setId(id);
				// prpJFTimeDto.setPrePayRefTim((DateTime.current()));
				this.save(prpJfTime);
			}
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	/**
	 * 回寫預賠付業務數據信息.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @param flag
	 *            標誌
	 * @param serverName
	 *            服務器名稱
	 * @param Dbname
	 *            數據庫名稱
	 * @param businessSource
	 *            業務來源
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void echoPreCompensate(DBManager dbManager, String businessNo, String status, String underwriteCode, DateTime underwriteDate, String flag,
			String serverName, String Dbname, String businessSource) throws UserException, SQLException, Exception {
		DBPrpLprepay dbPrpLprepay = new DBPrpLprepay(dbManager);
		PrpLprepayDto prpLprepayDto = new PrpLprepayDto();
		PrpDuser prpDuser = new PrpDuser();
		// FIX0322 modify by zhupengju begin
		// DBDataSource dbDataSource = DBFactory.getDB("undwrtDataSource");
		DbPool dbPool = new DbPool();
		// dbPool.open(dbDataSource.getJndiName());
		dbPool.setDBManager(dbManager);
		// FIX0322 modify by zhupengju end
		try {
			prpLprepayDto = dbPrpLprepay.findByPrimaryKey(businessNo);
			if (status.trim().equals("3")) {
				underwriteCode = prpLprepayDto.getApproverCode();
			}
			if (status.trim().equals("2")) {
				prpLprepayDto.setApproverCode("");
			}
			prpDuser = prpDuserService.getUser(underwriteCode);
			prpLprepayDto.setUnderWriteFlag(status);
			prpLprepayDto.setUnderWriteCode(underwriteCode);
			prpLprepayDto.setUnderWriteName(prpDuser.getUserName());
			prpLprepayDto.setUnderWriteEndDate(underwriteDate);
			dbPrpLprepay.update(prpLprepayDto);
			// 预赔核赔通过后转再保险数据
			if (status.trim().equals("3") || status.trim().equals("1")) {

				// FIX0406 delete by zhangTC begin
				// BLPayToReins blPayToReins = new BLPayToReins();
				// BLPrepay blPrepay = new BLPrepay();
				// 转换dbpool

				// blPrepay.getData(dbPool, prpLprepayDto.getPreCompensateNo());
				// blPayToReins.prepayToReins(dbPool, blPrepay);
				// FIX0406 delete by zhangTC end
				// 东安个性转财务接口
				// if
				// (AppConfig.get("sysconst.UFSOFT_DATAINTERFACE").equals("1"))
				// {
				// com.sinosoft.prpall.dbsvr.lp.DBPrpLprepay dbPrpLprepayOld =
				// new com.sinosoft.prpall.dbsvr.lp.DBPrpLprepay();
				// dbPrpLprepayOld.setUnderWriteFlag(status);
				// dbPrpLprepayOld.setUnderWriteCode(underwriteCode);
				// dbPrpLprepayOld
				// .setUnderWriteName(prpDuserDto.getUserName());
				// dbPrpLprepayOld.setUnderWriteEndDate(new DateTime(
				// underwriteDate).toString());
				// new BLPrpIfaceExchange().saveIfaceExchange(dbPool,
				// dbPrpLprepayOld, businessNo, "1003");
				// }

			}
		} catch (UserException ue) {
			throw ue;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 回寫預賠付業務數據信息.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @param flag
	 *            標誌
	 * @param serverName
	 *            服務器名稱
	 * @param dbname
	 *            數據庫名稱
	 * @param businessSource
	 *            業務來源
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void echoCompensate(DBManager dbManager, String businessNo, String status, String underwriteCode, DateTime underwriteDate, String flag,
			String serverName, String dbname, String businessSource) throws UserException, SQLException, Exception {
		DBPrpLcompensate dbPrpLcompensate = new DBPrpLcompensate(dbManager);
		com.sinosoft.prpall.dbsvr.lp.DBPrpLcompensate dbPrpLcompensateOld = new com.sinosoft.prpall.dbsvr.lp.DBPrpLcompensate();
		PrpLcompensateDto prpLcompensateDto = new PrpLcompensateDto();
		PrpLclaimDto prpLclaimDto = new PrpLclaimDto();
		PrpDuser prpDuser = new PrpDuser();
		// FIX0322 modify by zhupengju begin
		// DBDataSource dbDataSource = DBFactory.getDB("undwrtDataSource");
		DbPool dbPool = new DbPool();
		// dbPool.open(dbDataSource.getJndiName());
		dbPool.setDBManager(dbManager);
		// FIX0322 modify by zhupengju end
		// 自动结案标志 0/关闭 1/开启
		status = status.trim();
		String endCaseFlag = AppConfig.get("sysconst.AUTO_ENDCASE");

		String caseNo = ""; // 赔案号
		String strEndcasetext = ""; // 结案报告
		String certiNo = ""; // 计算书号
		String claimNo = ""; // 立案号
		try {
			if (businessSource.equals("reins")) {
			} else {
				prpLcompensateDto = dbPrpLcompensate.findByPrimaryKey(businessNo);

				certiNo = prpLcompensateDto.getCompensateNo(); // 理赔计算书号
				claimNo = prpLcompensateDto.getClaimNo(); // 立案号
				if (status.trim().equals("3")) {
					underwriteCode = prpLcompensateDto.getApproverCode();
				}
				if (status.equals("2")) {
					prpLcompensateDto.setApproverCode("");
				}
				if (endCaseFlag != null && endCaseFlag.equals("0")) // 原核赔通过方式
				{
					prpDuser = prpDuserService.getUser(underwriteCode);
					prpLcompensateDto.setUnderWriteFlag(status);
					prpLcompensateDto.setUnderWriteCode(underwriteCode);
					prpLcompensateDto.setUnderWriteName(prpDuser.getUserName());
					prpLcompensateDto.setUnderWriteEndDate(underwriteDate);
					dbPrpLcompensate.update(prpLcompensateDto);

					// 实赔核赔通过后回写PrpLclaim表中的SumPaid字段。该步骤原来在保存赔款计算书时做。
					if (status.equals("3") || status.equals("1")) {
						DBPrpLclaim dbPrpLclaim = new DBPrpLclaim(dbManager);
						double dbSumPaid = 0d;
						String strClaimNo = prpLcompensateDto.getClaimNo();
						prpLclaimDto = dbPrpLclaim.findByPrimaryKey(strClaimNo);
						dbSumPaid = prpLclaimDto.getSumPaid();
						dbSumPaid = dbSumPaid + prpLcompensateDto.getSumDutyPaid();
						prpLclaimDto.setSumPaid(dbSumPaid);
						// 在回写prplclaim表的sumpaid的字段之后回写currency字段
						prpLclaimDto.setCurrency(prpLcompensateDto.getCurrency());
						dbPrpLclaim.update(prpLclaimDto);
						if (AppConfig.get("sysconst.LOCAL_COMCODE").equals("DONGAN")) {
							dbPrpLcompensateOld.setUnderWriteFlag(status);
							dbPrpLcompensateOld.setUnderWriteCode(underwriteCode);
							dbPrpLcompensateOld.setUnderWriteName(prpDuser.getUserName());
							dbPrpLcompensateOld.setUnderWriteEndDate(new DateTime(underwriteDate).toString());
							dbPrpLcompensateOld.setPolicyNo(prpLcompensateDto.getPolicyNo());
							dbPrpLcompensateOld.setRiskCode(prpLcompensateDto.getRiskCode());
						}
						// if (AppConfig.get("sysconst.UFSOFT_DATAINTERFACE")
						// .equals("1")
						// && AppConfig.get(
						// "sysconst.UFSOFT_DATAINTERFACE_LP")
						// .equals("1")) {
						// new BLPrpIfaceExchange().saveIfaceExchange(dbPool,
						// dbPrpLcompensateOld, businessNo, "1003");
						// }
					}
				}

				// 实赔核赔通过后转再保险数据 2005-07-29 注释
				// if (status.equals("3") || status.equals("1"))
				// {
				// 转换dbpool
				// blCompensate.getData(dbPool,
				// prpLcompensateDto.getCompensateNo());

				// blPayToReins.compensateToReins(dbPool, blCompensate);
				// }
				/*
				 * //安华需求：自动结案 if (endCaseFlag.equals("1")) { if
				 * (status.equals("3") || status.equals("1")) { DateTime date =
				 * new DateTime(new java.util.Date()); Bill bill = new Bill();
				 * String bizNo = bill.getNo("prplcompensate",
				 * prpLcompensateDto.getRiskCode(), prpDuserDto.getComCode(),
				 * date.getYear(), ""); blClaim.getData(claimNo);
				 * blPayToReins.endCaseToReins(dbPool, blClaim, bizNo);
				 * bill.putNo("prplcompensate", bizNo); int intRecaseCount =
				 * dbPrpLrecase.getCount("ClaimNo='" + claimNo + "'"); if
				 * (intRecaseCount > 0) { dbPrpLrecase.getInfo(claimNo,
				 * String.valueOf(intRecaseCount));
				 * dbPrpLrecase.setCloseCaseUserCode(underwriteCode);
				 * dbPrpLrecase.setCloseCaseDate(date.current().toString());
				 * dbPrpLrecase.update(dbPool); }
				 * 
				 * //得到赔案号 caseNo = bill.getNo("prplcaseno", //表名
				 * prpLcompensateDto.getRiskCode(), //险种代码
				 * prpDuserDto.getComCode(), //部门代码 date.getYear(), //起保年份 "");
				 * //session id PrpLcaseNoSchema prpLcaseNoSchema = new
				 * PrpLcaseNoSchema(); blPrpLcaseNo.setArr(prpLcaseNoSchema);
				 * prpLcaseNoSchema
				 * .setCertiNo(prpLcompensateDto.getCompensateNo());
				 * prpLcaseNoSchema.setCertiType("C");
				 * prpLcaseNoSchema.setCaseNo(caseNo);
				 * prpLcaseNoSchema.setFlag(""); blPrpLcaseNo.save(dbPool,
				 * status, underwriteCode, underwriteDate.toString());
				 * //自动生成结案报告 车险 if
				 * (prpLcompensateDto.getRiskCode().equals("0501")) {
				 * blCompensate.getData(prpLcompensateDto.getCompensateNo());
				 * blCompensate.generateCarLtext(underwriteCode); strEndcasetext
				 * = blCompensate.getBLPrpLltext().getEndcaseText(); BLPrpLltext
				 * blPrpLltext = new BLPrpLltext(); PrpLltextSchema
				 * prpLltextSchema = new PrpLltextSchema();
				 * blPrpLltext.setArr(prpLltextSchema);
				 * prpLltextSchema.setClaimNo(claimNo);
				 * prpLltextSchema.setTextType("08"); //TextType 08为结案报告
				 * prpLltextSchema.setContext(strEndcasetext);
				 * prpLltextSchema.setFlag(""); blPrpLltext.save(dbPool); } } }
				 * //end 自动结案
				 */
				// 实赔核赔通过后回写PrpCitemCarExt表
				if (AppConfig.get("sysconst.LOCAL_COMCODE").equals("DONGAN")) {
					if (prpLcompensateDto.getClassCode().equals("A") || prpLcompensateDto.getClassCode().equals("B")) {
						// new
						// BLPrpCitemCarExt().CompensateRefresh(dbPrpLcompensateOld);
					}
				}
			}
		} catch (UserException ue) {
			throw ue;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			throw e;
		} finally {
			// FIX0322 delete by zhupengju begin
			// dbPool.close();
			// FIX0322 delete by zhupengju end

		}
	}

	/**
	 * 核保通過後回寫業務信息.
	 * 
	 * @param iBussinessType
	 *            業務類型
	 * @param iBussinessNo
	 *            業務號
	 * @param userCode
	 *            用戶代碼
	 * @param dbManager
	 *            數據管理對象
	 * @param authorize_control
	 *            授權標誌位
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void echoPrp(String iBussinessType, String iBussinessNo, String userCode, DBManager dbManager, String authorize_control) throws UserException,
			Exception {
		String policyNo = "";
		String riskCode = "";// 增加险种代码，用于送收付的判断
		DbPool dbpool = new DbPool();
		dbpool.setDBManager(dbManager);
		try {
			if (iBussinessType.equals("T")) // 投保单
			{
				policyNo = this.proposalToPolicyOrCPToC("T", iBussinessNo);
				// 回写Tmain表保单号
				PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(iBussinessNo);
				if (prpTmain != null) {
					prpTmain.setPolicyNo(policyNo);
					this.save(prpTmain);
				}
				System.out.println("新增送再保前日志*********************************** 业务单号为"+policyNo);
				// 审核通过时调用再保新接口
				reinsUndrtInterfAction.reinsRepolicyCal(iBussinessNo, policyNo);
				System.out.println("新增送再保后日志*********************************** 业务单号为"+policyNo);

				// 保单补录时的回写,20140103生产环境日期格式报错，确定没用，临时注掉
				// echoVisa(dbManager, policyNo);

				// 批量保单单证接口，核保通过后回写批量流水号状态，20140103生产环境日期格式报错，确定没用，临时注掉
				// echoVisaForBatch(dbManager, policyNo, iBussinessType); //
				// 批量单证回写
				PrpJfTime prpJfTime = new PrpJfTime();
				PrpJfTimeId id = new PrpJfTimeId();
				id.setBusinessNo(iBussinessNo);
				id.setCertiType("T");
				prpJfTime.setId(id);
				prpJfTime.setPolicyNo(policyNo);
				prpJfTime.setFalg("0");
				prpJfTime.setUndwrtTime(new DateTime(new DateTime().current().toString().substring(0, 10)));
				this.save(prpJfTime);
			}

			if (iBussinessType.equals("E")) // 批单
			{
				DBPrpCrenewal dbPrpCrenewal = new DBPrpCrenewal(dbManager);
				String endorType = "";
				String oldPolicyNo = "";
				PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(iBussinessNo);
				//mantis：MAR0073，處理人員：DP0728，需求單編號：水險MC批單匯率問題
				riskCode= prpPhead.getRiskCode();
				if (prpPhead != null) {
					policyNo = prpPhead.getPolicyNo();
					endorType = prpPhead.getEndorType();
					if (!policyNo.equals("")) {
						PrpCrenewalDto prpCrenewalDto = dbPrpCrenewal.findByPrimaryKey(policyNo);
						if (prpCrenewalDto != null) {
							oldPolicyNo = prpCrenewalDto.getOldPolicyNo();
						}
					}
				}
				// 20140103生产环境日期格式报错，确定没用，临时注掉
				// echoVisaForBatch(dbManager, policyNo, iBussinessType); //
				// 批量单证回写
				if (!oldPolicyNo.equals("")) { // oldpolicyNo不为空则表示该保单是续保单
					//mantis： FIR0557，處理人員：bj085，需求單編號：FIR0557 住火_續保件批單調整審核通過壓回續保註記邏輯 START
					if("F02".equals(prpPhead.getRiskCode())){
						//住火續保件做註銷或中途退保不回壓前一年度保單 PRPCMAIN.OTHFLAG 被續保註記
					}else{
						echoPolicyForRenewal(endorType, oldPolicyNo); // 续保单注销和全单退保要回写被续保单状态
					}
					//mantis： FIR0557，處理人員：bj085，需求單編號：FIR0557 住火_續保件批單調整審核通過壓回續保註記邏輯 END
				}
			}

			// 增加保单核保通过后转入原始保单表的信息
			if (iBussinessType.equals("P")) // 保单
			{
				BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
				blPrpCmainCovernote.getData(iBussinessNo);
				if (blPrpCmainCovernote.getSize() > 0) {

				} else {
					BLPolicy blPolicy = new BLPolicy();
					blPolicy.getData(dbpool, iBussinessNo);
					// 核保通过后存储原始保单数据开始
					BLPolicyOrigin blPolicyOrigin = new BLPolicyOrigin();
					blPolicyOrigin.policyToOriginPolicy(dbpool, blPolicy);
					policyNo = iBussinessNo;
					cRepolicyInfo(policyNo, "P");
				}
			}

			// 生成中间成本和手续费
			if (!"1".equals(authorize_control)) {// 商业车险增加授权控制,暂时则不送收费 modify by
				// 临时注掉防止产生错误的批单号20140106
				/*
				 * if (iBussinessType.equals("T") || iBussinessType.equals("P"))
				 * { BLPrpCmainCovernote blPrpCmainCovernote = new
				 * BLPrpCmainCovernote();
				 * blPrpCmainCovernote.getData(iBussinessNo); if
				 * (blPrpCmainCovernote.getSize() > 0) {
				 * 
				 * } else { BLPrpMiddleCost blPrpMiddleCost = new
				 * BLPrpMiddleCost(); blPrpMiddleCost.createDisPremium(dbpool,
				 * "P", policyNo); BLPrpCommission blPrpCommission = new
				 * BLPrpCommission(); blPrpCommission.createCommission(dbpool,
				 * "P", policyNo); } } // 批单变化引起的手续费和中间成本的变化 if
				 * (iBussinessType.equals("E")) { BLPrpPmainCovernote
				 * blPrpPmainCovernote = new BLPrpPmainCovernote();
				 * blPrpPmainCovernote.getData(iBussinessNo); if
				 * (blPrpPmainCovernote.getSize() > 0) {
				 * 
				 * } else { BLPrpMiddleCost blPrpMiddleCost = new
				 * BLPrpMiddleCost(); blPrpMiddleCost.createDisPremium(dbpool,
				 * "E", iBussinessNo); BLPrpCommission blPrpCommission = new
				 * BLPrpCommission(); blPrpCommission.createCommission(dbpool,
				 * "E", iBussinessNo); } }
				 */

				// 转入应收应付信息
				// 分入业务不生成收付费信息但需生成分入帐单
				if (!this.isReinsCeded(iBussinessNo, iBussinessType)) {
					/*BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
					blPrpCmainCovernote.getData(iBussinessNo);
					String strJudicalCode = ""; // 暂保单、预约协议实收保费标志－0.实收 1.未实收
					if (blPrpCmainCovernote.getSize() > 0) {
						strJudicalCode = blPrpCmainCovernote.getArr(0).getJudicalCode();
						// 当做实收时转入应收应付信息
						if ("1".equals(strJudicalCode)) {
							PrpTransSffIntf prpTransSffIntf = new PrpTransSffIntf();
							PrpallPremiumInterf prpallPremiumInterf = new PrpallPremiumInterf();
							prpTransSffIntf.transCovernoteData(dbpool, iBussinessType, iBussinessNo);
							prpallPremiumInterf.transPremiumData(dbpool, "ALL", iBussinessNo);
						}
					} else {*/
						PrpTransSffIntf prpTransSffIntf = new PrpTransSffIntf();
						PrpallPremiumInterf prpallPremiumInterf = new PrpallPremiumInterf();
						//mantis：MAR0070，處理人員：DP0728，需求單編號：MC件轉核保保費計算與承保不一致問題
						PrpTransSffIntf2 prpTransSffIntf2 = new PrpTransSffIntf2();
						// 取险种代码，0402自动分险别挂帐，调用双核中的程序
						if (iBussinessType.equals("T") || iBussinessType.equals("P")) {
							// 取险种代码，0402自动分险别挂帐，调用双核中的程序 begin
							BLPrpCmain blPrpCmain = new BLPrpCmain();
							blPrpCmain.getData(dbpool, policyNo);
							riskCode = blPrpCmain.getArr(0).getRiskCode();
							//mantis： LIA0309，處理人員：DP0728，需求單編號：三合一險種核保問題
							if (riskCode.equals("0402") && iBussinessType.equals("T")) {
								prpTransSffIntf.transData0402(dbpool, "P", policyNo);
								prpallPremiumInterf.transPremiumData(dbpool, "ALL", policyNo);
							} else {
								//mantis：MAR0070，處理人員：DP0728，需求單編號：MC件轉核保保費計算與承保不一致問題 Start
								//mantis： LIA0316，處理人員：DP0728，需求單編號：行動裝置保險業務員檢核WS與預約式保單入單調整
								//mantis： LIA0309，處理人員：DP0728，需求單編號：三合一險種核保問題
								if( ",MC,MI,EM,ER,GA,".contains(","+blPrpCmain.getArr(0).getRiskCode()+",")){
									prpTransSffIntf2.transData(dbpool, "P", policyNo);
								}else{
									prpTransSffIntf.transData(dbpool, "P", policyNo);
								}
								//mantis：MAR0070，處理人員：DP0728，需求單編號：MC件轉核保保費計算與承保不一致問題 End 
								prpallPremiumInterf.transPremiumData(dbpool, "ALL", policyNo);
							}
							// 取险种代码，0402自动分险别挂帐，调用双核中的程序 end
						} else {
							/*BLPrpPmainCovernote blPrpPmainCovernote = new BLPrpPmainCovernote();
							blPrpPmainCovernote.getData(iBussinessNo);
							if (blPrpPmainCovernote.getSize() > 0) {
								String chgPremium = blPrpPmainCovernote.getArr(0).getChgPremium();
								if (!chgPremium.equals("0.0")) {
									prpTransSffIntf.transCovernoteData(dbpool, iBussinessType, iBussinessNo);
									prpallPremiumInterf.transPremiumData(dbpool, "ALL", iBussinessNo);
								}
							} else {*/
							System.out.println("=============開始回寫收付數據==="+iBussinessNo+"=========="+iBussinessType+"=======================");
							//mantis：MAR0069，處理人員：DP0713，需求單編號：MC的批單收據列印顯示調整 Start
							if(iBussinessType.equals("T")){
								BLPrpCmain blPrpCmain = new BLPrpCmain();
								blPrpCmain.getData(dbpool, policyNo);
								riskCode = blPrpCmain.getArr(0).getRiskCode();
								if( "MC".equals(riskCode) ){
									prpTransSffIntf2.transData(dbpool, iBussinessType, iBussinessNo);
								}else{
									prpTransSffIntf.transData(dbpool, iBussinessType, iBussinessNo);
								}
							}else{
								//mantis：MAR0073，處理人員：DP0728，需求單編號：水險MC批單匯率問題 Start
								if( "MC".equals(riskCode) ){
									prpTransSffIntf2.transData(dbpool, iBussinessType, iBussinessNo);
								}else{
									prpTransSffIntf.transData(dbpool, iBussinessType, iBussinessNo);
								}
								//mantis：MAR0073，處理人員：DP0728，需求單編號：水險MC批單匯率問題 End
							}
							//mantis：MAR0069，處理人員：DP0713，需求單編號：MC的批單收據列印顯示調整 End
								BLPrpPcarshipTax blPrpPcarsipTax = new BLPrpPcarshipTax();
								blPrpPcarsipTax.getData(iBussinessNo);
								if (blPrpPcarsipTax.getSize() > 0) {
									prpTransSffIntf.transCarShipTax(dbpool, "E", iBussinessNo);
								}
								prpallPremiumInterf.transPremiumData(dbpool, "ALL", iBussinessNo);
							System.out.println("=============結束回寫收付數據==="+iBussinessNo+"=========="+iBussinessType+"=======================");
							//}
						}
					//}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
	}

	/**
	 * 核保通過後回寫收付費信息(投保單/批單).
	 * 
	 * @param iBussinessType
	 *            業務類型
	 * @param iBussinessNo
	 *            業務號
	 * @param userCode
	 *            用戶代碼
	 * @param dbManager
	 *            數據管理對象
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void echoPrpJF(String iBussinessType, String iBussinessNo, String userCode, DBManager dbManager) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		dbpool.setDBManager(dbManager);
		try {
			// 送PrpCommission/PrpCommissiondetail表投保单数据
			BLPrpCommission blPrpCommission = new BLPrpCommission();
			blPrpCommission.createCommission(dbpool, iBussinessType, iBussinessNo);
			// 送手付费数据
			// this.isReinsCeded判断是否分入业务-如果是分入业务则不传收付费
			if (!this.isReinsCeded) {
				PrpTransSffIntf prpTransSffIntf = new PrpTransSffIntf();
				PaymentIntfFacade paymentIntfFacade = new PaymentIntfFacade();
				PrpallPremiumInterf prpallPremiumInterf = new PrpallPremiumInterf();
				prpTransSffIntf.transData(dbpool, iBussinessType, iBussinessNo);
				prpallPremiumInterf.transPremiumData(dbpool, "ALL", iBussinessNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
	}

	/**
	 * 補錄保單單證狀態回寫（保單）.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param policyNo
	 *            保單號
	 * @throws Exception
	 *             異常
	 */
	public void echoVisa(DBManager dbManager, String policyNo) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		BLInterfaceVsMarkAction blInterfaceVsMarkAction = new BLInterfaceVsMarkAction();
		boolean vsStatus = false;
		PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(policyNo);
		PrpCitemCar prpCitemCar = prpCmain.getPrpCitemCars().get(0);
		PrpDuser prpDuser = null;
		// modeify by zhangruifeng ;20070801 begin 增加机动车强制责任保险粤港两地车保单补录时登记保险标志；

		if ("05".equals(prpCmain.getClassCode()) || "A".equals(prpCmain.getClassCode()) || "B".equals(prpCmain.getClassCode())) {
			System.out.println(internal.getText("undwrt.service.prpFeeBack.registeInsuranceFlag"));
			if (prpCitemCar.getVisaCode() != null && !prpCitemCar.getVisaCode().equals("") && prpCitemCar.getCarDealerCode() != null
					&& !prpCitemCar.getCarDealerCode().equals("")) {
				vsStatus = blInterfaceVsMarkAction.checkVisaCodeValid(dbManager, prpCitemCar.getVisaCode(), prpCitemCar.getCarDealerCode(),
						prpCmain.getHandler1Code());
				prpDuser = prpDuserService.getUser(prpCmain.getHandler1Code());
			}
			if (vsStatus) {
				blInterfaceVsMarkAction.doUsed(dbManager, prpCitemCar.getVisaCode(), prpCitemCar.getCarDealerCode(), policyNo, prpDuser.getUserCode(),
						prpDuser.getUserName());
			}
		}
		// modeify by zhangruifeng 20070801 end
		String visaSerialNo = prpCmain.getPrintNo();
		// modify by zhaoning20090623 begin
		// Reason:不处理PolicySort为"I"的保单(该保单是激活卡保单)
		if (prpCmain.getVisaCode() != null && !prpCmain.getVisaCode().equals("") && prpCmain.getPrintNo() != null && !prpCmain.getPolicySort().equals("I")
				&& !prpCmain.getPrintNo().equals("")) {
			// 校验单证流水号的合法性
			vsStatus = blInterfaceVsMarkAction.checkVisaCodeValid(dbManager, prpCmain.getVisaCode(), prpCmain.getPrintNo(), prpCmain.getHandler1Code());
			prpDuser = prpDuserService.getUser(prpCmain.getHandler1Code());
			if (vsStatus) {
				blInterfaceVsMarkAction.doUsed(dbManager, prpCmain.getVisaCode(), visaSerialNo, policyNo, prpDuser.getUserCode(), prpDuser.getUserName());
			}
			// modify by chengkai;20070629;增加补录按出单员进行验证的操作,先不考虑代理点出单的问题;end;
		}
		// modify by zhaoning20090623 end
	}

	/**
	 * 補錄保單單證狀態回寫（保單）.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param policyNo
	 *            保單號
	 * @param iBussinessType
	 *            業務類型
	 * @throws Exception
	 *             異常
	 */
	public void echoVisaForBatch(DBManager dbManager, String policyNo, String iBussinessType) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		BLInterfaceVsMarkAction blInterfaceVsMarkAction = new BLInterfaceVsMarkAction();
		ArrayList batchList = new ArrayList();
		BLPrpCbatchAction blPrpCbatchAction = new BLPrpCbatchAction();
		BLPrpCPbatchAction blPrpCPbatchAction = new BLPrpCPbatchAction();
		if (iBussinessType.equals("T") || iBussinessType.equals("P")) {
			batchList = (ArrayList) blPrpCbatchAction.findByConditions(dbManager, " policyNo='" + policyNo + "'");
		} else if (iBussinessType.equals("E")) {
			batchList = (ArrayList) blPrpCPbatchAction.findByConditions(dbManager, " policyNo='" + policyNo + "'");
		}
		if (batchList == null || batchList.size() == 0) { // 如果没有批量信息则不处理
			return;
		}

		boolean vsStatus = false;
		BigInteger billStartNo = null;
		int billCount = 0;
		PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(policyNo);
		PrpDuser prpDuser = null;
		String visaSerialNo = "";
		String visaStatus = "";
		String visaCode = "";

		// modify by zhaoning20090623 begin
		// Reason:不处理PolicySort为"I"的保单(该保单是激活卡保单)
		if (prpCmain.getPolicySort().equals("I")) {
			return;
		}
		// modify by zhaoning20090623 end

		// add by chengkai;20070907;获得单证类型的长度;begin
		Visa visa = new Visa();
		visa.getVisaCode(prpCmain.getComCode(), prpCmain.getRiskCode(), "P");
		int intVisaHeight = visa.getVisaNoLength();
		// add by chengkai;20070907;获得单证类型的长度;end

		for (int i = 0; i < batchList.size(); i++) {
			if (iBussinessType.equals("T") || iBussinessType.equals("P")) {
				PrpCbatchDto batchDto = (PrpCbatchDto) batchList.get(i);
				visaStatus = batchDto.getVisaStatus();
				visaCode = batchDto.getVisaCode();
				billStartNo = new BigInteger(batchDto.getBillStartNo());
				billCount = batchDto.getBillCount();
			}
			if (iBussinessType.equals("E")) {
				PrpCPbatchDto batchDto = (PrpCPbatchDto) batchList.get(i);
				visaStatus = batchDto.getVisaStatus();
				visaCode = batchDto.getVisaCode();
				billStartNo = new BigInteger(batchDto.getBillStartNo());
				billCount = batchDto.getBillCount();
			}

			for (int j = 0; j < billCount; j++) {
				if (j > 0) {
					billStartNo = billStartNo.add(new BigInteger("1"));
				}
				visaSerialNo = String.valueOf(billStartNo);
				// 补0操作

				// modify by chengkai;20070907;根据动态的单证流水号长度进行补零;end;
				// int intLength = 10 - visaSerialNo.length();
				int intLength = intVisaHeight - visaSerialNo.length();
				// modify by chengkai;20070907;根据动态的单证流水号长度进行补零;end;

				for (int x = 0; x < intLength; x++) {
					visaSerialNo = "0" + visaSerialNo;
				}
				// 投保时需要考虑单证为可使用状态，更新单证状态 日后需要考虑将不满足条件的单证流水号告知客户
				if (iBussinessType.equals("T")) {
					vsStatus = blInterfaceVsMarkAction.checkVisaCodeValid(dbManager, visaCode, visaSerialNo, prpCmain.getHandler1Code());
					// modify by chengkai;按归属业务员进行校验。end;

					// add by chengkai; 20070905;如果单证有使用的则抛出异常，进行交验;begin;
					if (!vsStatus) {
						throw new UserException(-98, -1149, "BLPrpFeedbackAction.echoVisaForBatch()", internal.getText("undwrt.service.prpFeeBack.confirm"));
					}
					// add by chengkai; 20070905;如果单证有使用的则抛出异常，进行交验;end;
				}
				// 批改时无需考虑单证状态，按状态直接回写
				if (iBussinessType.equals("E")) {
					vsStatus = true;
				}
				if (vsStatus) {
					// modify by chengkai;20070905;更改为归属业务员获得流水号使用的人员;begin;
					// prpDuserDto =
					// dbPrpDuser.findByPrimaryKey(prpCmainDto.getOperatorCode());
					prpDuser = prpDuserService.getUser(prpCmain.getHandler1Code());
					// modify by chengkai;20070905;更改为归属业务员获得流水号使用的人员;end;

					// add by chenkai begin 20060526 根据状态回写流水号状态
					if (visaStatus.equals("04")) {
						blInterfaceVsMarkAction.doUsedForBatch(dbManager, visaCode, visaSerialNo, policyNo, prpDuser.getUserCode(), prpDuser.getUserName());

					} else if (visaStatus.equals("06")) {
						blInterfaceVsMarkAction.doAnnulY(dbManager, visaCode, visaSerialNo, prpDuser.getUserCode(), prpDuser.getUserName());
					}
					// add by chenkai end 20060526 根据状态回写流水号状态
				}
			}
		}
	}

	/**
	 * 續保單保單注銷、全單退保核批通過時，置被續保單的標志位爲未續保狀態（prpcmain.othflag[;]=0）
	 * 
	 * @param endorType
	 *            業務類型
	 * @param oldPolicyNo
	 *            保單號
	 * @throws Exception
	 *             異常
	 */
	public void echoPolicyForRenewal(String endorType, String oldPolicyNo) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		if (endorType.equals(""))
			throw new UserException(-98, -1149, "BLPrpFeedbackAction.echoPolicyForRenewal()", internal.getText("undwrt.service.prpFeeBack.validateData"));
		if (oldPolicyNo.equals(""))
			throw new UserException(-98, -1149, "BLPrpFeedbackAction.echoPolicyForRenewal()", internal.getText("undwrt.service.prpFeeBack.checkData"));
		if (endorType.equals("19") || endorType.equals("21")) { // 保单注销或者全单退保
			PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(oldPolicyNo);
			String othFlag = prpCmain.getOthFlag();
			// othflag小于两位的，用0补足两位
			int intLength = othFlag.length();
			if (intLength < 2) {
				for (int i = 0; i < 2 - intLength; i++) {
					othFlag = othFlag + "0";
				}
			}
			// othflag大于等于两位的，设第二位为0
			if (othFlag.length() > 2) {
				othFlag = othFlag.substring(0, 1) + "0" + othFlag.substring(2);
			} else if (othFlag.length() == 2) {
				othFlag = othFlag.substring(0, 1) + "0";
			}
			prpCmain.setOthFlag(othFlag);
			this.update(prpCmain);
		}
	}

	/**
	 * 保單審核通過時，根據危險單位生成生成分保單.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @throws Exception
	 *             異常
	 */
	public void cRepolicyInfo(String businessNo, String businessType) throws Exception {
		String repolicyNo = "";
		PrpCmain prpCmain = null;
		Collection serialNoInfo = null;
		int dangerNo = 0;
		// add begin by zhaijq 20060406 2799含健康险条款暂时不分保
		if (blReinsService.includeHealthKindCode(prpCmain.getPolicyNo(), "T")) {
			return;
		}
		boolean ifOffLineCal = blReinsService.ifOfflineCalRisk(prpCmain.getRiskCode());
		//mantis： FIR0557，處理人員：bj085，需求單編號：FIR0557 住火_續保件批單調整審核通過壓回續保註記邏輯 Start
		//因正式機無此段程式 故調整為一致 正式機 ifTreatyValid 只有一個參數 故上版至PROD時 需用一個參數的程式 
		boolean ifTreatyValid = blReinsService.ifTreatyValid(String.valueOf(prpCmain.getStartDate().getYear()));
//		boolean ifTreatyValid = blReinsService.ifTreatyValid(String.valueOf(prpCmain.getStartDate().getYear()),prpCmain.getRiskCode());
		//mantis： FIR0557，處理人員：bj085，需求單編號：FIR0557 住火_續保件批單調整審核通過壓回續保註記邏輯 End
		// 如果险种配置为离线计算险种或者合约未就绪，先置危险单位离线计算标志
		if (ifOffLineCal == true || ifTreatyValid == false) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("policyNo", prpCmain.getPolicyNo());
			Collection prpCdangerUnitDtoList = prpCDangerUnitService.findByConditions(queryRule);
			Iterator itDanger = prpCdangerUnitDtoList.iterator();
			while (itDanger.hasNext()) {
				PrpCDangerUnit prpCdangerUnitDto = (PrpCDangerUnit) itDanger.next();
				prpCdangerUnitDto.setReinsureFlag("2");
				prpCDangerUnitService.update(prpCdangerUnitDto);
			}
		}
		// 如果险种配置为离线计算险种，则在核保通过时不进行分保试算以及进行再保处理
		if (ifOffLineCal == true) {
			return;
		}
		Iterator iterator = serialNoInfo.iterator();
		while (iterator.hasNext()) {
			CommonDangerUnitSerialNoVo commonDangerUnitSerialNoDto = (CommonDangerUnitSerialNoVo) iterator.next();
			dangerNo = Integer.parseInt(commonDangerUnitSerialNoDto.getDangerNo());
			// 双核与再保接口调整
			reinsUndrtInterfAction.reinsRepolicyCal(businessNo);
		}
	}

	/**
	 * 強三關聯回寫prpCmainSub表數據修正方法，將強制保單號按商業投保單號回寫到prpCmainSub.
	 * 
	 * @param businessNo
	 *            業務號
	 * @throws Exception
	 *             異常
	 */
	public void echoMainSub(String businessNo) throws Exception {
		String strWhere = "";
		String strProposalno = "";
		String strPolicyno = "";
		String strPolicynoCI = "";
		try {
			PrpCmain prpCMain = policyService.getPrpCmainByProposalNo(businessNo);
			if (prpCMain != null) {
				strPolicynoCI = prpCMain.getPolicyNo();
			}
			strWhere = "select * from PrpTmainSub where Mainpolicyno='" + businessNo + "'";
			List list = super.getSession().createSQLQuery(strWhere).addEntity(PrpTmainSub.class).list();
			if (list.size() > 0) {
				strProposalno = ((PrpTmainSub) list.get(0)).getId().getProposalNo();
				PrpCmain prpCmain = policyService.getPrpCmainByProposalNo(strProposalno);
				if (prpCmain != null) {
					strPolicyno = prpCmain.getPolicyNo();
					String strSQL = " Select * From PrpCmainSub Where PolicyNo = '" + strPolicyno + "' And MainPolicyNo = '" + businessNo + "' ";
					List list2 = super.getSession().createSQLQuery(strSQL).addEntity(PrpCmainSub.class).list();
					if (list2.size() > 0) {
						PrpCmainSub prpCmainSub = (PrpCmainSub) list2.get(0);
						this.delete(prpCmainSub);
						PrpCmainSubId id = new PrpCmainSubId();
						id.setPolicyNo(strPolicyno);
						id.setMainPolicyNo(strPolicynoCI);
						prpCmainSub.setId(id);
						this.save(prpCmainSub);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 雙核回寫業務強三平台結果方法.
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
	public void echoCISubmit(DBManager dbManager, String businessNo, String businessType) throws UserException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String context = "";
		String response = "";
		String iRiskCode = "";
		String iComCode = "";
		DbPool dbPool = new DbPool();
		dbPool.setDBManager(dbManager);
		com.sinosoft.prpall.blsvr.cb.BLPrpCmain blPrpCmain = new com.sinosoft.prpall.blsvr.cb.BLPrpCmain();
		BLPolicy blPolicy = new BLPolicy();
		BLEndorse blEndorse = new BLEndorse();
		if (businessType.equals("T")) {// 投保单核保通过后回写
			System.out.println(internal.getText("undwrt.service.prpFeeBack.validateInteractive") + businessNo);
			PrpCmain prpCmain = policyService.getPrpCmainByProposalNo(businessNo);
			// add by chengkai;20071210;如果已经和交强险平台交互过就不行再交互了，避免补登时的错误;begin;
			BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
			blCIInsureValid.query(dbPool, " PROPOSALNO = '" + businessNo + "' AND VALIDNO IS NOT NULL");
			if (blCIInsureValid.getSize() > 0) {
				return;
			}
			// add by chengkai;20071210;如果已经和交强险平台交互过就不行再交互了，避免补登时的错误;begin;

			// add by
			// chengkai;20080609;弥补平台重复投保的内容,但必须知道平台保单号和确认平台已确认我公司的承保;begin
			// 目前是按是否生成投保单号的规则进行判断
			PrpTmain prpTmain = new PrpTmain();
			prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			if (prpTmain.getManualType() != null && !prpTmain.equals("0") && prpTmain.getPolicyNo() != null && !prpTmain.getPolicyNo().equals("")) {
				return;
			}
			// add by chengkai;20080609;弥补平台重复投保的内容,但必须知道平台保单号和确认平台已确认我公司的承保;end

			if (prpCmain != null) {
				// 核保不通过时，回写核心的表来标识一下

			} else {
				blPolicy.getData(dbPool, prpCmain.getPolicyNo());
				boolean utiPowerFlag = true;
				iRiskCode = blPolicy.getBLPrpCmain().getArr(0).getRiskCode();
				iComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
				UtiPower utiPower = new UtiPower();
				utiPowerFlag = utiPower.checkCIInsure(iRiskCode, iComCode);
				if (utiPowerFlag && iRiskCode.equals("B01")) {// add by xuning
					// 暂时写死交强险代码为0507
					if ("31".equals(iComCode.substring(0, 2)))//
					{
						PolicyValid31Encoder policyValidEncoder = new PolicyValid31Encoder();
						PolicyValid31Decoder policyValidDecoder = new PolicyValid31Decoder();
						context = policyValidEncoder.encode(blPolicy);
						System.out.println(internal.getText("undwrt.service.prpFeeBack.shXML") + context);
						if (context != null) {
							response = EbaoProxy.getInstance().request(context, iComCode);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.shXML") + response);
							policyValidDecoder.decode(dbPool, blPolicy, response);
						}
					} else if ("11".equals(iComCode.substring(0, 2))) {
						PolicyValid11Encoder policyValidEncoder = new PolicyValid11Encoder();
						PolicyValid11Decoder policyValidDecoder = new PolicyValid11Decoder();
						context = policyValidEncoder.encode(blPolicy);
						System.out.println(internal.getText("undwrt.service.prpFeeBack.bjXML") + context);
						if (context != null) {
							response = EbaoProxy.getInstance().request(context, iComCode);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.bjXML") + response);
							policyValidDecoder.decode(dbPool, blPolicy, response);
						}
						// zhanglong 20091210 task-2296 增加山东条件判断
					} else if ("37".equals(iComCode.substring(0, 2)) || "43".equals(iComCode.substring(0, 2)) || "32".equals(iComCode.substring(0, 2))
							|| "33".equals(iComCode.substring(0, 2))) {// add by
																		// zhangshi
																		// 增加江苏条件;20080922
																		// 增加浙江条件
						System.out.println(internal.getText("undwrt.service.prpFeeBack.platform"));

						// modify by zhangshi;20080731;处理湖南摩托车拖拉机不走交强险平台。begin。
						String strCarKindCode = "";
						strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
						// modify by zhangruifeng 20080228
						// reason:处理低速载货汽车不走交强险平台
						if (!("M0,M2,J1,J2".indexOf(strCarKindCode) > -1)) {
							PolicyValidOtherEncoder policyValidEncoder = new PolicyValidOtherEncoder();
							PolicyValidOtherDecoder policyValidDecoder = new PolicyValidOtherDecoder();
							context = policyValidEncoder.encode(blPolicy);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.insureXML") + context);
							if (context != null) {
								response = EbaoProxy.getInstance().request(context, iComCode);
								System.out.println(internal.getText("undwrt.service.prpFeeBack.insureXML") + response);
								policyValidDecoder.decode(dbPool, blPolicy, response);
							}
						}
						// modify by zhangshi;20080731;end。
					} else {
						// modify by
						// zhouliubin;20070804;处理江苏摩托车拖拉机不走交强险平台。begin。
						String strCarKindCode = "";
						strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
						// modify by zhangruifeng 20080228
						// reason:处理低速载货汽车不走交强险平台
						if (!("M0,M2,J1,J2,H1".indexOf(strCarKindCode) > -1)) {
							PolicyValidOtherEncoder policyValidEncoder = new PolicyValidOtherEncoder();
							PolicyValidOtherDecoder policyValidDecoder = new PolicyValidOtherDecoder();
							context = policyValidEncoder.encode(blPolicy);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.insureXML") + context);
							if (context != null) {
								response = EbaoProxy.getInstance().request(context, iComCode);
								System.out.println(internal.getText("undwrt.service.prpFeeBack.insureXML") + response);
								policyValidDecoder.decode(dbPool, blPolicy, response);
							}
						}
						// modify by zhouliubin;20070804;处理江苏摩托车拖拉机不走交强险平台。end。
					}
				} else {
					if ("31".equals(iComCode.substring(0, 2)))//
					{
						PolicyValid31Encoder policyValidEncoder = new PolicyValid31Encoder();
						PolicyValid31Decoder policyValidDecoder = new PolicyValid31Decoder();
						context = policyValidEncoder.encode(blPolicy);
						System.out.println("------ruquest--=" + context);
						if (context != null) {
							response = EbaoProxy.getInstance().request(context, iComCode);
							System.out.println("------ruquest--=" + response);
							policyValidDecoder.decode(dbPool, blPolicy, response);
						}
					}
					// 20091010 songshuo 北京商业险集中
					if ("11".equals(iComCode.substring(0, 2)) && !iRiskCode.equals("0503"))//
					{
						PolicyValid11Encoder policyValidEncoder = new PolicyValid11Encoder();
						PolicyValid11Decoder policyValidDecoder = new PolicyValid11Decoder();
						context = policyValidEncoder.encode(blPolicy);
						System.out.println("------ruquest--=" + context);
						if (context != null) {
							response = EbaoProxy.getInstance().request(context, iComCode);
							System.out.println("------response--=" + response);
							policyValidDecoder.decode(dbPool, blPolicy, response);
						}
					}

					// add by mxy 20091027 begin TASK-1347 浙江商业险集中
					if (new UtiPower().checkBIInsure(iComCode, iRiskCode))//
					{
						PolicyValidBusinessEncoder policyValidEncoder = new PolicyValidBusinessEncoder();
						PolicyValidBusinessDecoder policyValidDecoder = new PolicyValidBusinessDecoder();
						context = policyValidEncoder.encode(blPolicy);
						System.out.println("------ruquest--=" + context);
						if (context != null) {
							response = SinoProxy.getInstance().request(context, iComCode, iRiskCode);
							System.out.println("------response--=" + response);
							policyValidDecoder.decode(blPolicy, response, dbPool, "");
						}
					}
					// add by mxy 20091027 end TASK-1347 浙江商业险集中

				}

			}
		} else if (businessType.equals("P")) {// 保单核保通过后回写
			blPolicy.getData(dbPool, businessNo);
			boolean utiPowerFlag = true;
			iRiskCode = blPolicy.getBLPrpCmain().getArr(0).getRiskCode();
			iComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
			UtiPower utiPower = new UtiPower();
			utiPowerFlag = utiPower.checkCIInsure(iRiskCode, iComCode);
			if (utiPowerFlag && iRiskCode.equals("B01")) {

				PolicyValidEncoder policyValidEncoder = new PolicyValidEncoder();
				PolicyValidDecoder policyValidDecoder = new PolicyValidDecoder();
				context = policyValidEncoder.encode(blPolicy);
				System.out.println(internal.getText("undwrt.service.prpFeeBack.insureXML") + context);
				response = EbaoProxy.getInstance().request(context, iComCode);
				System.out.println(internal.getText("undwrt.service.prpFeeBack.insureXML") + response);
				policyValidDecoder.decode(dbPool, blPolicy, response);

			}
		} else if (businessType.equals("E")) {// 批单核保通过后回写
			blEndorse.getData(dbPool, businessNo);
			boolean utiPowerFlag = true;
			iRiskCode = blEndorse.getBLPrpPhead().getArr(0).getRiskCode();
			iComCode = blEndorse.getBLPrpPhead().getArr(0).getComCode();
			UtiPower utiPower = new UtiPower();
			utiPowerFlag = utiPower.checkCIInsure(iRiskCode, iComCode);
			String endorType = blEndorse.getBLPrpPhead().getArr(0).getEndorType();
			if (utiPowerFlag && iRiskCode.equals("B01")) {
				if (endorType.equals("19")) {
					if ("31".equals(iComCode.substring(0, 2))) {
						// 保单注销批改
						PolicyCancelEncoder policyCancelEncoder = new PolicyCancelEncoder();
						PolicyCancelDecoder policyCancelDecoder = new PolicyCancelDecoder();
						context = policyCancelEncoder.encode(blEndorse);
						System.out.println(internal.getText("undwrt.service.prpFeeBack.shXML") + context);
						// System.out.println("------ruquest-191-=" + context);
						response = EbaoProxy.getInstance().request(context, iComCode);
						System.out.println(internal.getText("undwrt.service.prpFeeBack.shXML") + response);
						// System.out.println("------response-192-=" +
						// response);
						policyCancelDecoder.decode(dbPool, blEndorse, response);
					} else if ("11".equals(iComCode.substring(0, 2))) {
						// zhanglong 20091210 task-2296 增加山东条件判断
					} else if ("37".equals(iComCode.substring(0, 2)) || "43".equals(iComCode.substring(0, 2)) || "32".equals(iComCode.substring(0, 2))
							|| "33".equals(iComCode.substring(0, 2))) {// add by
																		// zhangshi
																		// 增加江苏条件;增加浙江条件20080922
						// modify by zhangshi;20080731;处理湖南摩托车拖拉机不走交强险平台。begin。
						String strCarKindCode = "";
						blPolicy.getData(dbPool, blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
						strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
						if (!("M0,M2,J1,J2".indexOf(strCarKindCode) > -1)) {
							// 保单注销批改
							PolicyCancelOtherEncoder policyCancelEncoder = new PolicyCancelOtherEncoder();
							PolicyCancelOtherDecoder policyCancelDecoder = new PolicyCancelOtherDecoder();
							context = policyCancelEncoder.encode(blEndorse);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementXML") + context);
							response = EbaoProxy.getInstance().request(context, iComCode);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementBackXML") + response);
							policyCancelDecoder.decode(dbPool, blEndorse, response);
						}
						// modify by zhangshi;20080731;end。

					} else {
						// modify by
						// zhouliubin;20070804;处理江苏摩托车拖拉机不走交强险平台。begin。
						String strCarKindCode = "";
						blPolicy.getData(dbPool, blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
						strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
						// modify by zhangruifeng 20080228
						// reason:处理低速载货汽车不走交强险平台
						if (!("M0,M2,J1,J2,H1".indexOf(strCarKindCode) > -1)) {
							// 保单注销批改
							PolicyCancelOtherEncoder policyCancelEncoder = new PolicyCancelOtherEncoder();
							PolicyCancelOtherDecoder policyCancelDecoder = new PolicyCancelOtherDecoder();
							context = policyCancelEncoder.encode(blEndorse);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementXML") + context);
							System.out.println("------response-192-=" + response);
							response = EbaoProxy.getInstance().request(context, iComCode);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementBackXML") + response);
							policyCancelDecoder.decode(dbPool, blEndorse, response);
						}
						// add by zhouliubin; 20070804;处理江苏摩托车拖拉机不走交强险平台 end。
					}
				} else if (endorType.equals("21")) {
					if ("11".equals(iComCode.substring(0, 2))) {

						// 全单退保批改
						// modify by zhouliubin;20070617;begin;
						PolicyWithDrawValid11Encoder policyWithDrawValid11Encoder = new PolicyWithDrawValid11Encoder();
						PolicyWithDrawValidDecoder policyWithDrawValidDecoder = new PolicyWithDrawValidDecoder();

						context = policyWithDrawValid11Encoder.encode(blEndorse);
						System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementXML") + context);
						// System.out.println("------ruquest-211-=" + context);
						response = EbaoProxy.getInstance().request(context, iComCode);
						System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementBackXML") + response);
						// System.out.println("------response-212-=" +
						// response);
						policyWithDrawValidDecoder.decode(dbPool, blEndorse, response);
						// modify by zhouliubin;20070617;end;
					} else if ("31".equals(iComCode.substring(0, 2))) {
						// 全单退保批改
						PolicyWithDrawValid31Encoder policyWithDrawValid31Encoder = new PolicyWithDrawValid31Encoder();
						PolicyWithDrawValid31Decoder policyWithDrawValid31Decoder = new PolicyWithDrawValid31Decoder();

						context = policyWithDrawValid31Encoder.encode(blEndorse);
						System.out.println("------ruquest-211-=" + context);
						response = EbaoProxy.getInstance().request(context, iComCode);
						policyWithDrawValid31Decoder.decode(dbPool, blEndorse, response);
						System.out.println("------ruquest-211-=" + response);
						// zhanglong 20091210 task-2296 增加山东条件判断
					} else if ("37".equals(iComCode.substring(0, 2)) || "43".equals(iComCode.substring(0, 2)) || "32".equals(iComCode.substring(0, 2))
							|| "33".equals(iComCode.substring(0, 2))) {// add by
																		// zhangshi
																		// 增加江苏条件;增加浙江条件
																		// 20080922
						// 全单退保批改
						// modify by
						// zhangshi;20080731;处理湖南摩托车拖拉机不走交强险平台。begin。
						String strCarKindCode = "";
						blPolicy.getData(dbPool, blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
						strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
						if (!("M0,M2,J1,J2".indexOf(strCarKindCode) > -1)) {
							PolicyWithDrawValidOtherEncoder policyWithDrawValidEncoder = new PolicyWithDrawValidOtherEncoder();
							PolicyWithDrawValidOtherDecoder policyWithDrawValidDecoder = new PolicyWithDrawValidOtherDecoder();

							context = policyWithDrawValidEncoder.encode(blEndorse);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementXML") + context);
							response = EbaoProxy.getInstance().request(context, iComCode);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementBackXML") + response);
							policyWithDrawValidDecoder.decode(dbPool, blEndorse, response);
						}
						// add by zhangshi; 20080731;end。
					} else {
						// 全单退保批改
						// modify by
						// zhouliubin;20070804;处理江苏摩托车拖拉机不走交强险平台。begin。
						String strCarKindCode = "";
						blPolicy.getData(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
						strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
						// modify by zhangruifeng 20080228
						// reason:处理低速载货汽车不走交强险平台
						if (!("M0,M2,J1,J2,H1".indexOf(strCarKindCode) > -1)) {
							PolicyWithDrawValidOtherEncoder policyWithDrawValidEncoder = new PolicyWithDrawValidOtherEncoder();
							PolicyWithDrawValidOtherDecoder policyWithDrawValidDecoder = new PolicyWithDrawValidOtherDecoder();

							context = policyWithDrawValidEncoder.encode(blEndorse);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementXML") + context);
							response = EbaoProxy.getInstance().request(context, iComCode);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementBackXML") + response);
							policyWithDrawValidDecoder.decode(dbPool, blEndorse, response);
						}
						// add by zhouliubin; 20070804;处理江苏摩托车拖拉机不走交强险平台 end。
					}
					// add by jiangchenghua 20080606 手续费批改和手续费注销批改时不走交强险平台
				} else if (endorType.indexOf("57") > -1 || endorType.equals("89")) {
				} else if ((endorType.indexOf("80") > -1 || endorType.equals("81")) && "3302".equals(iComCode.substring(0, 4))) {// TASK-3016
																																	// 庄元
																																	// 宁波公司参照当地其他公司做法开通停驶/复驶功能
				} else if (!endorType.equals("01")) {
					if ("11".equals(iComCode.substring(0, 2))) {

						// add by
						// chengkai;20071210;如果已经和交强险平台交互过就不行再交互了，避免补登时的错误;begin;
						BLCIEndorValid blCIEndorValid = new BLCIEndorValid();
						blCIEndorValid.query(dbPool, " ENDORSENO = '" + businessNo + "' AND  ENDORSEVALIDNO IS NOT NULL ");
						if (blCIEndorValid.getSize() > 0) {
							return;
						}
						// add by
						// chengkai;20071210;如果已经和交强险平台交互过就不行再交互了，避免补登时的错误;begin;

						EndorseValidEncoder endorseValidEncoder = new EndorseValidEncoder();
						EndorseValidDecoder endorseValidDecoder = new EndorseValidDecoder();
						context = endorseValidEncoder.encode(blEndorse);
						System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementXML") + context);
						// System.out.println("------ruquest--=" + context);
						response = EbaoProxy.getInstance().request(context, iComCode);
						System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementBackXML") + response);
						// System.out.println("------response--=" + response);
						endorseValidDecoder.decode(dbPool, blEndorse, response);
					} else if ("31".equals(iComCode.substring(0, 2))) {
						// add by
						// chengkai;20071210;如果已经和交强险平台交互过就不行再交互了，避免补登时的错误;begin;
						BLCIEndorValid blCIEndorValid = new BLCIEndorValid();
						blCIEndorValid.query(dbPool, " ENDORSENO = '" + businessNo + "' AND  ENDORSEVALIDNO IS NOT NULL ");
						if (blCIEndorValid.getSize() > 0) {
							return;
						}
						// add by
						// chengkai;20071210;如果已经和交强险平台交互过就不行再交互了，避免补登时的错误;begin;

						EndorseValid31Encoder endorseValid31Encoder = new EndorseValid31Encoder();
						EndorseValid31Decoder endorseValid31Decoder = new EndorseValid31Decoder();
						context = endorseValid31Encoder.encode(blEndorse);
						System.out.println("------ruquest--=" + context);
						response = EbaoProxy.getInstance().request(context, iComCode);
						System.out.println("------response--=" + response);
						endorseValid31Decoder.decode(dbPool, blEndorse, response);

					}
					// zhanglong 20091210 task-2296 增加山东条件判断
					else if ("37".equals(iComCode.substring(0, 2)) || "43".equals(iComCode.substring(0, 2)) || "32".equals(iComCode.substring(0, 2))
							|| "33".equals(iComCode.substring(0, 2))) {// add by
																		// zhangshi
																		// 增加江苏条件；增加浙江条件
																		// 20080922
						// modify by
						// zhangshi;20080731;处理湖南摩托车拖拉机不走交强险平台。begin。
						String strCarKindCode = "";
						blPolicy.getData(dbPool, blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
						strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
						if (!("M0,M2,J1,J2".indexOf(strCarKindCode) > -1)) {
							EndorseValidOtherEncoder endorseValidEncoder = new EndorseValidOtherEncoder();
							EndorseValidOtherDecoder endorseValidDecoder = new EndorseValidOtherDecoder();
							context = endorseValidEncoder.encode(blEndorse);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementXML") + context);
							response = EbaoProxy.getInstance().request(context, iComCode);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementBackXML") + response);
							endorseValidDecoder.decode(dbPool, blEndorse, response);
						}
						// add by zhangshi; 20080731;end。
					} else {
						// modify by
						// zhouliubin;20070804;处理江苏摩托车拖拉机不走交强险平台。begin。
						String strCarKindCode = "";
						blPolicy.getData(dbPool, blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
						strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
						// modify by zhangruifeng 20080228
						// reason:处理低速载货汽车不走交强险平台
						if (!("M0,M2,J1,J2,H1".indexOf(strCarKindCode) > -1)) {
							EndorseValidOtherEncoder endorseValidEncoder = new EndorseValidOtherEncoder();
							EndorseValidOtherDecoder endorseValidDecoder = new EndorseValidOtherDecoder();
							context = endorseValidEncoder.encode(blEndorse);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementXML") + context);
							response = EbaoProxy.getInstance().request(context, iComCode);
							System.out.println(internal.getText("undwrt.service.prpFeeBack.endrosementBackXML") + response);
							endorseValidDecoder.decode(dbPool, blEndorse, response);
						}
						// add by zhouliubin; 20070804;处理江苏摩托车拖拉机不走交强险平台 end。
					}
				}
			} else {
				System.err.println(internal.getText("undwrt.service.prpFeeBack.goIntoSH"));
				if (endorType.equals("19")) {
				} else if (endorType.equals("21")) {
					System.err.println(internal.getText("undwrt.service.prpFeeBack.goIntoSH2"));
					if ("31".equals(iComCode.substring(0, 2))) {
						// 全单退保批改
						System.err.println(internal.getText("undwrt.service.prpFeeBack.goIntoSH3"));
						PolicyWithDrawValid31Encoder policyWithDrawValid31Encoder = new PolicyWithDrawValid31Encoder();
						PolicyWithDrawValid31Decoder policyWithDrawValid31Decoder = new PolicyWithDrawValid31Decoder();

						context = policyWithDrawValid31Encoder.encode(blEndorse);
						System.out.println("------ruquest-211-=" + context);
						response = EbaoProxy.getInstance().request(context, iComCode);
						policyWithDrawValid31Decoder.decode(dbPool, blEndorse, response);
						System.out.println("------ruquest-211-=" + response);
					}
					// 20091015 songshuo 北京商业险集中
					if ("11".equals(iComCode.substring(0, 2)) && !iRiskCode.equals("0503")) {
						// 全单退保批改
						System.err.println(internal.getText("undwrt.service.prpFeeBack.goIntoBJ"));
						PolicyWithDrawValid11Encoder policyWithDrawValid11Encoder = new PolicyWithDrawValid11Encoder();
						PolicyWithDrawValidDecoder policyWithDrawValidDecoder = new PolicyWithDrawValidDecoder();

						context = policyWithDrawValid11Encoder.encode(blEndorse);
						System.out.println("------ruquest-211-=" + context);
						response = EbaoProxy.getInstance().request(context, iComCode);
						policyWithDrawValidDecoder.decode(dbPool, blEndorse, response);
						System.out.println("------ruquest-211-=" + response);
					}
					// add by mxy 20091028 begin TASK-1347 浙江商业险集中
					if (new UtiPower().checkBIInsure(iComCode, iRiskCode)) {
						// 全单退保批改
						System.out.println("======" + internal.getText("undwrt.service.prpFeeBack.ZHJinteractiveBegin") + "======");
						PolicyWithDrawValidBusinessEncoder policyWithDrawValidBusinessEncoder = new PolicyWithDrawValidBusinessEncoder();
						PolicyWithDrawValidBusinessDecoder policyWithDrawValidBusinessDecoder = new PolicyWithDrawValidBusinessDecoder();
						context = policyWithDrawValidBusinessEncoder.encode(blEndorse);
						System.err.println(internal.getText("undwrt.service.prpFeeBack.ZHJXML") + "====" + context);
						response = SinoProxy.getInstance().request(context, iComCode, iRiskCode);
						System.err.println(internal.getText("undwrt.service.prpFeeBack.ZHJXMLBack") + "====" + response);
						policyWithDrawValidBusinessDecoder.decode(dbPool, blEndorse, response);
						System.out.println("======" + internal.getText("undwrt.service.prpFeeBack.ZHJinteractiveEnd") + "======");
					}
					// add by mxy 20091028 end TASK-1347 浙江商业险集中
				} else if (endorType.indexOf("57") > -1 || endorType.equals("89"))// 手续费批改和手续费注销批改不送平台
				{
				} else if (!endorType.equals("01")) {
					if ("31".equals(iComCode.substring(0, 2))) {

						// add by
						// chengkai;20071210;如果已经和交强险平台交互过就不行再交互了，避免补登时的错误;begin;
						BLCIEndorValid blCIEndorValid = new BLCIEndorValid();
						blCIEndorValid.query(dbPool, " ENDORSENO = '" + businessNo + "' AND  ENDORSEVALIDNO IS NOT NULL ");
						if (blCIEndorValid.getSize() > 0) {
							return;
						}
						// add by
						// chengkai;20071210;如果已经和交强险平台交互过就不行再交互了，避免补登时的错误;begin;

						EndorseValid31Encoder endorseValid31Encoder = new EndorseValid31Encoder();
						EndorseValid31Decoder endorseValid31Decoder = new EndorseValid31Decoder();
						context = endorseValid31Encoder.encode(blEndorse);
						System.out.println("------ruquest--=" + context);
						response = EbaoProxy.getInstance().request(context, iComCode);
						System.out.println("------response--=" + response);
						endorseValid31Decoder.decode(dbPool, blEndorse, response);
					}
					// 20091012 songshuo 北京商业险集中
					else if ("11".equals(iComCode.substring(0, 2)) && !iRiskCode.equals("0503")) {

						// add by
						// chengkai;20071210;如果已经和交强险平台交互过就不行再交互了，避免补登时的错误;begin;
						BLCIEndorValid blCIEndorValid = new BLCIEndorValid();
						blCIEndorValid.query(dbPool, " ENDORSENO = '" + businessNo + "' AND  ENDORSEVALIDNO IS NOT NULL ");
						if (blCIEndorValid.getSize() > 0) {
							return;
						}
						// add by
						// chengkai;20071210;如果已经和交强险平台交互过就不行再交互了，避免补登时的错误;begin;

						EndorseValidEncoder endorseValidEncoder = new EndorseValidEncoder();
						EndorseValidDecoder endorseValidDecoder = new EndorseValidDecoder();
						context = endorseValidEncoder.encode(blEndorse);
						System.out.println("------ruquest--=" + context);
						response = EbaoProxy.getInstance().request(context, iComCode);
						System.out.println("------response--=" + response);
						endorseValidDecoder.decode(dbPool, blEndorse, response);
					}
					// 20091012 songshuo 北京商业险集中
					// add by mxy 20091028 begin TASK-1347 浙江商业险集中
					else if (new UtiPower().checkBIInsure(iComCode, iRiskCode)) {
						System.out.println("======" + internal.getText("undwrt.service.prpFeeBack.ZHJinteractiveBegin2") + "======");
						EndorseValidBusinessEncoder endorseValidBusinessEncoder = new EndorseValidBusinessEncoder();
						EndorseValidBusinessDecoder endorseValidBusinessDecoder = new EndorseValidBusinessDecoder();
						context = endorseValidBusinessEncoder.encode(blEndorse);
						System.err.println(internal.getText("undwrt.service.prpFeeBack.ZHJXMLSendXML") + "====" + context);
						response = SinoProxy.getInstance().request(context, iComCode, iRiskCode);
						System.err.println(internal.getText("undwrt.service.prpFeeBack.ZHJXMLBackXML") + "====" + response);
						endorseValidBusinessDecoder.decode(dbPool, blEndorse, response);
						System.out.println("======" + internal.getText("undwrt.service.prpFeeBack.ZHJinteractiveEnd2") + "======");
					}
					// add by mxy 20091028 end TASK-1347 浙江商业险集中
				} // add by mxy 20091028 begin TASK-1347 浙江商业险集中
				else if (endorType.equals("01") && new UtiPower().checkBIInsure(iComCode, iRiskCode)) {
					System.out.println("======" + internal.getText("undwrt.service.prpFeeBack.ZHJinteractiveStart3") + "======");
					EndorseValidBusinessEncoder endorseValidBusinessEncoder = new EndorseValidBusinessEncoder();
					EndorseValidBusinessDecoder endorseValidBusinessDecoder = new EndorseValidBusinessDecoder();
					context = endorseValidBusinessEncoder.encode(blEndorse);
					System.err.println(internal.getText("undwrt.service.prpFeeBack.ZHJXMLSend") + "====" + context);
					response = SinoProxy.getInstance().request(context, iComCode, iRiskCode);
					System.err.println(internal.getText("undwrt.service.prpFeeBack.ZHJXMLback") + "====" + response);
					endorseValidBusinessDecoder.decode(dbPool, blEndorse, response);
					System.out.println("======" + internal.getText("undwrt.service.prpFeeBack.ZHJinteractiveEnd3") + "======");
				}
				// add by mxy 20091028 end TASK-1347 浙江商业险集中

			}

		}
	}

	/**
	 * 雙核預審核回寫業務強三平台結果方法.
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
	public void echoCISubmitJF(DBManager dbManager, String businessNo, String businessType) throws UserException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String context = "";
		String response = "";
		String iRiskCode = "";
		String iComCode = "";
		String strCarKindCode = "";
		DbPool dbPool = new DbPool();
		dbPool.setDBManager(dbManager);
		BLProposal blProposal = new BLProposal();
		BLEndorse blEndorse = new BLEndorse();
		DummyPolicyValid11Encoder dummyPolicyValid11Encoder = new DummyPolicyValid11Encoder();
		DummyPolicyValid11Decoder dummyPolicyValid11Decoder = new DummyPolicyValid11Decoder();
		DummyPolicyValid31Encoder dummyPolicyValid31Encoder = new DummyPolicyValid31Encoder();
		DummyPolicyValid31Decoder dummyPolicyValid31Decoder = new DummyPolicyValid31Decoder();
		DummyPolicyValidBusinessEncoder dummyPolicyValidBusinessEncoder = new DummyPolicyValidBusinessEncoder();
		DummyPolicyValidBusinessDecoder dummyPolicyValidBusinessDecoder = new DummyPolicyValidBusinessDecoder();
		if (businessType.equals("T")) {// 投保单核保通过后回写
			blProposal.getData(dbPool, businessNo);
			boolean utiPowerFlag = true;
			iRiskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
			iComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
			UtiPower utiPower = new UtiPower();
			utiPowerFlag = utiPower.checkCIInsure(iRiskCode, iComCode);
			strCarKindCode = blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode();
			if (utiPowerFlag && iRiskCode.equals("B01")) {// add by xuning

				// add by chengkai;北京才进行预处理的判断，而且调用的文件是独立北京的。begin
				if ("11".equals(iComCode.substring(0, 2))) {
					context = dummyPolicyValid11Encoder.encode(blProposal);
					System.out.println("--context=" + context);
					if (context != null) {
						response = EbaoProxy.getInstance().request(context, iComCode);
						System.out.println("--response=" + response);
						dummyPolicyValid11Decoder.decode(dbPool, blProposal, response);
					}
				}// add by chengkai;北京才进行预处理的判断，而且调用的文件是独立北京的。end
					// add by zhangfan;上海才进行预处理的判断。begin
				else if ("31".equals(iComCode.substring(0, 2))) {
					System.err.println(internal.getText("undwrt.service.prpFeeBack.goSHconfirm"));
					context = dummyPolicyValid31Encoder.encode(blProposal);
					System.out.println("--context=" + context);
					if (context != null) {
						response = EbaoProxy.getInstance().request(context, iComCode);
						System.out.println("--response=" + response);
						dummyPolicyValid31Decoder.decode(dbPool, blProposal, response);
					}
				}// add by zhangfan;上海才进行预处理的判断。end
					// zhanglong 20091210 task-2296 增加山东条件判断
				else if (("37".equals(iComCode.substring(0, 2)) || "43".equals(iComCode.substring(0, 2)) || "32".equals(iComCode.substring(0, 2)) || "33"
						.equals(iComCode.substring(0, 2))) && !("M0,M2,J1,J2".indexOf(strCarKindCode) > -1)) { // add
																												// by
																												// zhangshi
																												// 增加江苏、浙江条件
					// add by zhangshi;湖南进行预处理的判断
					DummyPolicyValidOtherEncoder dummyPolicyValidOtherEncoder = new DummyPolicyValidOtherEncoder();
					DummyPolicyValidOtherDecoder dummyPolicyValidOtherDecoder = new DummyPolicyValidOtherDecoder();

					context = dummyPolicyValidOtherEncoder.encode(blProposal);
					System.out.println(internal.getText("undwrt.service.prpFeeBack.confirmAdvanceSend") + context);
					if (context != null) {
						response = EbaoProxy.getInstance().request(context, iComCode);
						System.out.println(internal.getText("undwrt.service.prpFeeBack.confirmAdvanceBack") + response);
						dummyPolicyValidOtherDecoder.decode(dbPool, blProposal, response);
					}
				}
			} else {
				// add by zhangfan;上海才进行预处理的判断。begin
				if ("31".equals(iComCode.substring(0, 2))) {
					System.err.println(internal.getText("undwrt.service.prpFeeBack.goSHconfirm"));
					context = dummyPolicyValid31Encoder.encode(blProposal);
					System.out.println("--context=" + context);
					if (context != null) {
						response = EbaoProxy.getInstance().request(context, iComCode);
						System.out.println("--response=" + response);
						dummyPolicyValid31Decoder.decode(dbPool, blProposal, response);
					}
				}// add by zhangfan;上海才进行预处理的判断。end
					// 20091009 songshuo 北京商业险集中
				else if ("11".equals(iComCode.substring(0, 2)) && !iRiskCode.equals("0503")) {
					System.err.println(internal.getText("undwrt.service.prpFeeBack.goBJconfirm"));
					context = dummyPolicyValid11Encoder.encode(blProposal);
					System.out.println("--context=" + context);
					if (context != null) {
						response = EbaoProxy.getInstance().request(context, iComCode);
						System.out.println("--response=" + response);
						dummyPolicyValid11Decoder.decode(dbPool, blProposal, response);
					}
				}// 20091009 songshuo 北京商业险集中
					// add by mxy 20091027 begin TASK-1347 浙江商业险集中
				else if (new UtiPower().checkBIInsure(iComCode, iRiskCode)) {
					System.err.println(internal.getText("undwrt.service.prpFeeBack.goZHJconfirm"));
					context = dummyPolicyValidBusinessEncoder.encode(blProposal);
					System.out.println("--context=" + context);
					if (context != null) {
						response = SinoProxy.getInstance().request(context, iComCode, iRiskCode);
						System.out.println("--response=" + response);
						dummyPolicyValidBusinessDecoder.decode(dbPool, blProposal, response);
					}
				}
				// add by mxy 20091027 end TASK-1347 浙江商业险集中
			}
		}
		// add by mxy 20091027 begin TASK-1347 浙江商业险集中
		else if (businessType.equals("E")) {// 批单核保通过后回写
			blEndorse.getData(dbPool, businessNo);
			iRiskCode = blEndorse.getBLPrpPhead().getArr(0).getRiskCode();
			iComCode = blEndorse.getBLPrpPhead().getArr(0).getComCode();
			BLCIEndorValid blCIEndorValid = new BLCIEndorValid();
			if (!iRiskCode.equals("B01") && new UtiPower().checkBIInsure(iComCode, iRiskCode)) {
				blCIEndorValid.query(" EndorseNo = '" + businessNo + "'");
				if (blCIEndorValid != null && blCIEndorValid.getSize() > 0) {
					blEndorse.setBLCIEndorValid(blCIEndorValid);
					DummyEndorseValidBusinessEncoder dummyEndorseValidBusinessEncoder = new DummyEndorseValidBusinessEncoder();
					DummyEndorseValidBusinessDecoder dummyEndorseValidBusinessDecoder = new DummyEndorseValidBusinessDecoder();
					context = dummyEndorseValidBusinessEncoder.encode(blEndorse);
					System.out.println(internal.getText("undwrt.service.prpFeeBack.ZHJconfirmAdvance") + "=====" + context);
					response = SinoProxy.getInstance().request(context, iComCode, iRiskCode);
					System.out.println(internal.getText("undwrt.service.prpFeeBack.ZHJconfirmAdvance") + "=====" + response);
					dummyEndorseValidBusinessDecoder.decode(dbPool, blEndorse, response);
				}
			}
		}
	}

	/**
	 * 要保書转保單或者批單更新保單數據.
	 * 
	 * @param certiType
	 *            業務類型
	 * @param certiNo
	 *            業務號
	 * @return 保單號
	 * @throws Exception
	 *             異常
	 */
	public String proposalToPolicyOrCPToC(String certiType, String certiNo) throws Exception {
		String policyNo = "";
		String uRLPath = "";
		String businessMsg = "";
		String strMessage = "";
		StringBuffer strBuffer = null;

		OutputStream outputStream = null;
		OutputStreamWriter writer = null;
		InputStream inputStream = null;
		BufferedReader reader = null;
		uRLPath = SysConfig.getProperty("ProposalToPolicyServlet");
//		uRLPath = "http://localhost:7001/prpins/PrpinsToUndwrtServlet";
		URL urlServlet = new URL(uRLPath);
		// 设置参数
		HttpURLConnection servletConnection = (HttpURLConnection) urlServlet.openConnection();
		servletConnection.setUseCaches(false);
		servletConnection.setDoOutput(true);
		servletConnection.setDoInput(true);
		servletConnection.setRequestMethod("POST");
		servletConnection.setAllowUserInteraction(true);
		servletConnection.connect();

		// 发送业务信息
		businessMsg = certiType + "," + certiNo;

		outputStream = servletConnection.getOutputStream();
		writer = new OutputStreamWriter(outputStream);
		writer.write(businessMsg);
		writer.flush();
		writer.close();
		// 接收返回参数
		inputStream = servletConnection.getInputStream();
		reader = new BufferedReader(new InputStreamReader(inputStream));
		strBuffer = new StringBuffer();
		while ((strMessage = reader.readLine()) != null) {
			strBuffer.append(strMessage);
		}
		if (strBuffer.length() < 1) {
			System.out.println("strBuffer=" + strBuffer);
			// throw new Exception(businessMsg + "生成保單失敗");
			throw new Exception(businessMsg + "調用承保服務失敗");
		} else {
			policyNo = strBuffer.toString();
		}
		return policyNo;
	}

	/**
	 * 爲了走servelet解決事務未提交讀出錯誤數據加的方法的問題，更新要保書數據.
	 * 
	 * @param prpTmain
	 *            要保書信息類
	 * @throws Exception
	 *             異常
	 */
	public void updateTmain(PrpTmain prpTmain) throws Exception {
		DBManager dbManager = new DBManager();
		try {
			dbManager.open("undwrtDataSource");
			dbManager.beginTransaction();
			String statement = "update PrpTmain set UnderWriteFlag=?,UnderWriteCode=?,UnderWriteName=?,UnderWriteEndDate=?,ProposalLevel=?,refuseLimiteInsurance=?,listDetection=?,riskRating=?,workStatus=? where proposalno=?";
			dbManager.prepareStatement(statement);
			dbManager.setString(1, prpTmain.getUnderWriteFlag());
			dbManager.setString(2, prpTmain.getUnderWriteCode());
			dbManager.setString(3, prpTmain.getUnderWriteName());
			dbManager.setDateTime(4,
					new DateTime(prpTmain.getUnderWriteEndDate()));
			dbManager.setString(5, prpTmain.getProposalLevel());
			// add by xuhuiling  更新四個值的狀態
			dbManager.setString(6, prpTmain.getRefuseLimiteInsurance());
			dbManager.setString(7, prpTmain.getListDetection());
			dbManager.setString(8, prpTmain.getRiskRating());
			dbManager.setString(9, prpTmain.getWorkStatus());
			// add by xuhuiling  更新四個值的狀態
			dbManager.setString(10, prpTmain.getProposalNo());
			dbManager.executePreparedUpdate();
			dbManager.commitTransaction();
		} catch (Exception exception) {
			dbManager.rollbackTransaction();
			throw exception;
		} finally {
			dbManager.close();
		}
	}
	
	/**
	 * 删除指定投保单号(批单号)，危险单位号的相关主信息和子信息.
	 * 
	 * @param businessType
	 *            业务类型
	 * @param businessNo
	 *            业务号
	 * @param dangerNo
	 *            危险单位号
	 * @throws Exception
	 *             the exception
	 */
	public void deletePrpDangerUnitAndItem(String businessType, String businessNo, String dangerNo) throws Exception {
		if("T".equals(businessType)){
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.proposalNo", businessNo);
			queryRule.addEqual("id.dangerNo", Integer.valueOf(dangerNo));
			prpTdangerItemService.deleteByConditions(queryRule);
			prpTDangerPlanService.deleteByConditions(queryRule);
			prpTdangerTotService.deleteByConditions(queryRule);
			prpTDangerRiskService.deleteByConditions(queryRule);
			prpTDangerCoinsService.deleteByConditions(queryRule);
			prpTReinsTrialService.deleteByConditions(queryRule);
			prpTReinsShareService.deleteByConditions(queryRule);
			prpTdangerUnitService.deleteByConditions(queryRule);
		}else if("P".equals(businessType)){
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", businessNo);
			queryRule.addEqual("id.dangerNo", Integer.valueOf(dangerNo));
			prpCDangerItemService.deleteByConditions(queryRule);
			prpCDangerTotService.deleteByConditions(queryRule);
			prpCDangerPlanService.deleteByConditions(queryRule);
			prpCDangerRiskService.deleteByConditions(queryRule);
			// 增加共保信息删除
			prpCDangerCoinsService.deleteByConditions(queryRule);
			// 删除主表之前，应该先删除子表记录
			prpCReinsTrialService.deleteByConditions(queryRule);
			prpCReinsShareService.deleteByConditions(queryRule);
			prpCDangerUnitService.deleteByConditions(queryRule);
		}else if("E".equals(businessType)){
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.endorseNo", businessNo);
			queryRule.addEqual("id.dangerNo", Integer.valueOf(dangerNo));
			prpPDangerItemService.deleteByConditions(queryRule);
			prpPDangerTotService.deleteByConditions(queryRule);
			prpPDangerPlanService.deleteByConditions(queryRule);
			prpPDangerRiskService.deleteByConditions(queryRule);
			// 增加共保信息删除
			prpPDangerCoinsService.deleteByConditions(queryRule);
			// 删除主表之前，应该先删除子表记录
			prpPReinsTrialService.deleteByConditions(queryRule);
			prpPReinsShareService.deleteByConditions(queryRule);
			prpPDangerUnitService.deleteByConditions(queryRule);
		}
		
			
			
	}
	
	/**
	 * 獲取屬性核保調用再保接口.
	 * 
	 * @return 屬性核保調用再保接口的值
	 */
	public ReinsUndrtInterfAction getReinsUndrtInterfAction() {
		return reinsUndrtInterfAction;
	}

	/**
	 * 設置屬性核保調用再保接口.
	 * 
	 * @param reinsUndrtInterfAction
	 *            待設置的核保調用再保接口的值
	 */
	public void setReinsUndrtInterfAction(ReinsUndrtInterfAction reinsUndrtInterfAction) {
		this.reinsUndrtInterfAction = reinsUndrtInterfAction;
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
	 * 獲取屬性詢價單共保信息接口.
	 * 
	 * @return 屬性詢價單共保信息接口的值
	 */
	public FeoCoinsService getFeoCoinsService() {
		return feoCoinsService;
	}

	/**
	 * 設置屬性詢價單共保信息接口.
	 * 
	 * @param feoCoinsService
	 *            待設置的詢價單共保信息接口的值
	 */
	public void setFeoCoinsService(FeoCoinsService feoCoinsService) {
		this.feoCoinsService = feoCoinsService;
	}

	/**
	 * 獲取屬性詢價單特別約定資訊接口.
	 * 
	 * @return 屬性詢價單特別約定資訊接口的值
	 */
	public FeoEngageService getFeoEngageService() {
		return feoEngageService;
	}

	/**
	 * 設置屬性詢價單特別約定資訊接口.
	 * 
	 * @param feoEngageService
	 *            待設置的詢價單特別約定資訊接口的值
	 */
	public void setFeoEngageService(FeoEngageService feoEngageService) {
		this.feoEngageService = feoEngageService;
	}

	/**
	 * 獲取屬性詢價單保險標的資訊接口.
	 * 
	 * @return 屬性詢價單保險標的資訊接口的值
	 */
	public FeoItemService getFeoItemService() {
		return feoItemService;
	}

	/**
	 * 設置屬性詢價單保險標的資訊接口.
	 * 
	 * @param feoItemService
	 *            待設置的詢價單保險標的資訊接口的值
	 */
	public void setFeoItemService(FeoItemService feoItemService) {
		this.feoItemService = feoItemService;
	}

	/**
	 * 獲取屬性詢價單分期信息接口.
	 * 
	 * @return 屬性詢價單分期信息接口的值
	 */
	public FeoPlanService getFeoPlanService() {
		return feoPlanService;
	}

	/**
	 * 設置屬性詢價單分期信息接口.
	 * 
	 * @param feoPlanService
	 *            待設置的詢價單分期信息接口的值
	 */
	public void setFeoPlanService(FeoPlanService feoPlanService) {
		this.feoPlanService = feoPlanService;
	}

	/**
	 * 獲取屬性詢價單分保接受人資訊接口.
	 * 
	 * @return 屬性詢價單分保接受人資訊接口的值
	 */
	public FeoReinsReceiveService getFeoReinsReceiveService() {
		return feoReinsReceiveService;
	}

	/**
	 * 設置屬性詢價單分保接受人資訊接口.
	 * 
	 * @param feoReinsReceiveService
	 *            待設置的詢價單分保接受人資訊接口的值
	 */
	public void setFeoReinsReceiveService(FeoReinsReceiveService feoReinsReceiveService) {
		this.feoReinsReceiveService = feoReinsReceiveService;
	}

	/**
	 * 獲取屬性詢價單歷次確認意見接口.
	 * 
	 * @return 屬性詢價單歷次確認意見接口的值
	 */
	public FeoReinsVerifyService getFeoReinsVerifyService() {
		return feoReinsVerifyService;
	}

	/**
	 * 設置屬性詢價單歷次確認意見接口.
	 * 
	 * @param feoReinsVerifyService
	 *            待設置的詢價單歷次確認意見接口的值
	 */
	public void setFeoReinsVerifyService(FeoReinsVerifyService feoReinsVerifyService) {
		this.feoReinsVerifyService = feoReinsVerifyService;
	}

	/**
	 * 獲取屬性詢價單金額合計資訊接口.
	 * 
	 * @return 屬性詢價單金額合計資訊接口的值
	 */
	public FeoTotService getFeoTotService() {
		return feoTotService;
	}

	/**
	 * 設置屬性詢價單金額合計資訊接口.
	 * 
	 * @param feoTotService
	 *            待設置的詢價單金額合計資訊接口的值
	 */
	public void setFeoTotService(FeoTotService feoTotService) {
		this.feoTotService = feoTotService;
	}

	/**
	 * 獲取屬性保單危險單位共保資訊接口.
	 * 
	 * @return 屬性保單危險單位共保資訊接口的值
	 */
	public PrpCDangerCoinsService getPrpCDangerCoinsService() {
		return prpCDangerCoinsService;
	}

	/**
	 * 設置屬性保單危險單位共保資訊接口.
	 * 
	 * @param prpCDangerCoinsService
	 *            待設置的保單危險單位共保資訊接口的值
	 */
	public void setPrpCDangerCoinsService(PrpCDangerCoinsService prpCDangerCoinsService) {
		this.prpCDangerCoinsService = prpCDangerCoinsService;
	}

	/**
	 * 獲取屬性保單危險單位標的資訊接口.
	 * 
	 * @return 屬性保單危險單位標的資訊接口的值
	 */
	public PrpCDangerItemService getPrpCDangerItemService() {
		return prpCDangerItemService;
	}

	/**
	 * 設置屬性保單危險單位標的資訊接口.
	 * 
	 * @param prpCDangerItemService
	 *            待設置的保單危險單位標的資訊接口的值
	 */
	public void setPrpCDangerItemService(PrpCDangerItemService prpCDangerItemService) {
		this.prpCDangerItemService = prpCDangerItemService;
	}

	/**
	 * 獲取屬性保單危險單位交費計畫接口.
	 * 
	 * @return 屬性保單危險單位交費計畫接口的值
	 */
	public PrpCDangerPlanService getPrpCDangerPlanService() {
		return prpCDangerPlanService;
	}

	/**
	 * 設置屬性保單危險單位交費計畫接口.
	 * 
	 * @param prpCDangerPlanService
	 *            待設置的保單危險單位交費計畫接口的值
	 */
	public void setPrpCDangerPlanService(PrpCDangerPlanService prpCDangerPlanService) {
		this.prpCDangerPlanService = prpCDangerPlanService;
	}

	/**
	 * 獲取屬性保單危險單位交費計畫接口.
	 * 
	 * @return 屬性保單危險單位交費計畫接口的值
	 */
	public PrpCDangerRiskService getPrpCDangerRiskService() {
		return prpCDangerRiskService;
	}

	/**
	 * 設置屬性保單危險單位交費計畫接口.
	 * 
	 * @param prpCDangerRiskService
	 *            待設置的保單危險單位交費計畫接口的值
	 */
	public void setPrpCDangerRiskService(PrpCDangerRiskService prpCDangerRiskService) {
		this.prpCDangerRiskService = prpCDangerRiskService;
	}

	/**
	 * 獲取屬性保單危險單位金額合計資訊接口.
	 * 
	 * @return 屬性保單危險單位金額合計資訊接口的值
	 */
	public PrpCDangerTotService getPrpCDangerTotService() {
		return prpCDangerTotService;
	}

	/**
	 * 設置屬性保單危險單位金額合計資訊接口.
	 * 
	 * @param prpCDangerTotService
	 *            待設置的保單危險單位金額合計資訊接口的值
	 */
	public void setPrpCDangerTotService(PrpCDangerTotService prpCDangerTotService) {
		this.prpCDangerTotService = prpCDangerTotService;
	}

	/**
	 * 獲取屬性保單危險單位臨分接口.
	 * 
	 * @return 屬性保單危險單位臨分接口的值
	 */
	public PrpCDangerUnitService getPrpCDangerUnitService() {
		return prpCDangerUnitService;
	}

	/**
	 * 設置屬性保單危險單位臨分接口.
	 * 
	 * @param prpCDangerUnitService
	 *            待設置的保單危險單位臨分接口的值
	 */
	public void setPrpCDangerUnitService(PrpCDangerUnitService prpCDangerUnitService) {
		this.prpCDangerUnitService = prpCDangerUnitService;
	}

	/**
	 * 獲取屬性保單分保試算結果資訊接口.
	 * 
	 * @return 屬性保單分保試算結果資訊接口的值
	 */
	public PrpCReinsShareService getPrpCReinsShareService() {
		return prpCReinsShareService;
	}

	/**
	 * 設置屬性保單分保試算結果資訊接口.
	 * 
	 * @param prpCReinsShareService
	 *            待設置的保單分保試算結果資訊接口的值
	 */
	public void setPrpCReinsShareService(PrpCReinsShareService prpCReinsShareService) {
		this.prpCReinsShareService = prpCReinsShareService;
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
	 * 獲取屬性賠案分攤試算結果資訊接口.
	 * 
	 * @return 屬性賠案分攤試算結果資訊接口的值
	 */
	public PrpLReinsShareService getPrpLReinsShareService() {
		return prpLReinsShareService;
	}

	/**
	 * 設置屬性賠案分攤試算結果資訊接口.
	 * 
	 * @param prpLReinsShareService
	 *            待設置的賠案分攤試算結果資訊接口的值
	 */
	public void setPrpLReinsShareService(PrpLReinsShareService prpLReinsShareService) {
		this.prpLReinsShareService = prpLReinsShareService;
	}

	/**
	 * 獲取屬性賠案的分攤試算信息接口.
	 * 
	 * @return 屬性賠案的分攤試算信息接口的值
	 */
	public PrpLReinsTrialService getPrpLReinsTrialService() {
		return prpLReinsTrialService;
	}

	/**
	 * 設置屬性賠案的分攤試算信息接口.
	 * 
	 * @param prpLReinsTrialService
	 *            待設置的賠案的分攤試算信息接口的值
	 */
	public void setPrpLReinsTrialService(PrpLReinsTrialService prpLReinsTrialService) {
		this.prpLReinsTrialService = prpLReinsTrialService;
	}

	/**
	 * 獲取屬性批單危險單位共保資訊接口.
	 * 
	 * @return 屬性批單危險單位共保資訊接口的值
	 */
	public PrpPDangerCoinsService getPrpPDangerCoinsService() {
		return prpPDangerCoinsService;
	}

	/**
	 * 設置屬性批單危險單位共保資訊接口.
	 * 
	 * @param prpPDangerCoinsService
	 *            待設置的批單危險單位共保資訊接口的值
	 */
	public void setPrpPDangerCoinsService(PrpPDangerCoinsService prpPDangerCoinsService) {
		this.prpPDangerCoinsService = prpPDangerCoinsService;
	}

	/**
	 * 獲取屬性批單的危險單位標的資訊接口.
	 * 
	 * @return 屬性批單的危險單位標的資訊接口的值
	 */
	public PrpPDangerItemService getPrpPDangerItemService() {
		return prpPDangerItemService;
	}

	/**
	 * 設置屬性批單的危險單位標的資訊接口.
	 * 
	 * @param prpPDangerItemService
	 *            待設置的批單的危險單位標的資訊接口的值
	 */
	public void setPrpPDangerItemService(PrpPDangerItemService prpPDangerItemService) {
		this.prpPDangerItemService = prpPDangerItemService;
	}

	/**
	 * 獲取屬性批單危險單位交付計畫接口.
	 * 
	 * @return 屬性批單危險單位交付計畫接口的值
	 */
	public PrpPDangerPlanService getPrpPDangerPlanService() {
		return prpPDangerPlanService;
	}

	/**
	 * 設置屬性批單危險單位交付計畫接口.
	 * 
	 * @param prpPDangerPlanService
	 *            待設置的批單危險單位交付計畫接口的值
	 */
	public void setPrpPDangerPlanService(PrpPDangerPlanService prpPDangerPlanService) {
		this.prpPDangerPlanService = prpPDangerPlanService;
	}

	/**
	 * 獲取屬性批單危險單位風險評估接口.
	 * 
	 * @return 屬性批單危險單位風險評估接口的值
	 */
	public PrpPDangerRiskService getPrpPDangerRiskService() {
		return prpPDangerRiskService;
	}

	/**
	 * 設置屬性批單危險單位風險評估接口.
	 * 
	 * @param prpPDangerRiskService
	 *            待設置的批單危險單位風險評估接口的值
	 */
	public void setPrpPDangerRiskService(PrpPDangerRiskService prpPDangerRiskService) {
		this.prpPDangerRiskService = prpPDangerRiskService;
	}

	/**
	 * 獲取屬性批單危險單位金額合計資訊接口.
	 * 
	 * @return 屬性批單危險單位金額合計資訊接口的值
	 */
	public PrpPDangerTotService getPrpPDangerTotService() {
		return prpPDangerTotService;
	}

	/**
	 * 設置屬性批單危險單位金額合計資訊接口.
	 * 
	 * @param prpPDangerTotService
	 *            待設置的批單危險單位金額合計資訊接口的值
	 */
	public void setPrpPDangerTotService(PrpPDangerTotService prpPDangerTotService) {
		this.prpPDangerTotService = prpPDangerTotService;
	}

	/**
	 * 獲取屬性批單的危險單位劃分接口.
	 * 
	 * @return 屬性批單的危險單位劃分接口的值
	 */
	public PrpPDangerUnitService getPrpPDangerUnitService() {
		return prpPDangerUnitService;
	}

	/**
	 * 設置屬性批單的危險單位劃分接口.
	 * 
	 * @param prpPDangerUnitService
	 *            待設置的批單的危險單位劃分接口的值
	 */
	public void setPrpPDangerUnitService(PrpPDangerUnitService prpPDangerUnitService) {
		this.prpPDangerUnitService = prpPDangerUnitService;
	}

	/**
	 * 獲取屬性批單分保試算結果資訊接口.
	 * 
	 * @return 屬性批單分保試算結果資訊接口的值
	 */
	public PrpPReinsShareService getPrpPReinsShareService() {
		return prpPReinsShareService;
	}

	/**
	 * 設置屬性批單分保試算結果資訊接口.
	 * 
	 * @param prpPReinsShareService
	 *            待設置的批單分保試算結果資訊接口的值
	 */
	public void setPrpPReinsShareService(PrpPReinsShareService prpPReinsShareService) {
		this.prpPReinsShareService = prpPReinsShareService;
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
	 * 獲取屬性要保書危險單位共保資訊接口.
	 * 
	 * @return 屬性要保書危險單位共保資訊接口的值
	 */
	public PrpTDangerCoinsService getPrpTDangerCoinsService() {
		return prpTDangerCoinsService;
	}

	/**
	 * 設置屬性要保書危險單位共保資訊接口.
	 * 
	 * @param prpTDangerCoinsService
	 *            待設置的要保書危險單位共保資訊接口的值
	 */
	public void setPrpTDangerCoinsService(PrpTDangerCoinsService prpTDangerCoinsService) {
		this.prpTDangerCoinsService = prpTDangerCoinsService;
	}

	/**
	 * 獲取屬性要保書危險單位標的資訊接口.
	 * 
	 * @return 屬性要保書危險單位標的資訊接口的值
	 */
	public PrpTdangerItemService getPrpTdangerItemService() {
		return prpTdangerItemService;
	}

	/**
	 * 設置屬性要保書危險單位標的資訊接口.
	 * 
	 * @param prpTdangerItemService
	 *            待設置的要保書危險單位標的資訊接口的值
	 */
	public void setPrpTdangerItemService(PrpTdangerItemService prpTdangerItemService) {
		this.prpTdangerItemService = prpTdangerItemService;
	}

	/**
	 * 獲取屬性要保書危險單位交費計畫.
	 * 
	 * @return 屬性要保書危險單位交費計畫的值
	 */
	public PrpTDangerPlanService getPrpTDangerPlanService() {
		return prpTDangerPlanService;
	}

	/**
	 * 設置屬性要保書危險單位交費計畫.
	 * 
	 * @param prpTDangerPlanService
	 *            待設置的要保書危險單位交費計畫的值
	 */
	public void setPrpTDangerPlanService(PrpTDangerPlanService prpTDangerPlanService) {
		this.prpTDangerPlanService = prpTDangerPlanService;
	}

	/**
	 * 獲取屬性要保書危險單位風險評估接口.
	 * 
	 * @return 屬性要保書危險單位風險評估接口的值
	 */
	public PrpTDangerRiskService getPrpTDangerRiskService() {
		return prpTDangerRiskService;
	}

	/**
	 * 設置屬性要保書危險單位風險評估接口.
	 * 
	 * @param prpTDangerRiskService
	 *            待設置的要保書危險單位風險評估接口的值
	 */
	public void setPrpTDangerRiskService(PrpTDangerRiskService prpTDangerRiskService) {
		this.prpTDangerRiskService = prpTDangerRiskService;
	}

	/**
	 * 獲取屬性要保書危險單位金額合計資訊接口.
	 * 
	 * @return 屬性要保書危險單位金額合計資訊接口的值
	 */
	public PrpTdangerTotService getPrpTdangerTotService() {
		return prpTdangerTotService;
	}

	/**
	 * 設置屬性要保書危險單位金額合計資訊接口.
	 * 
	 * @param prpTdangerTotService
	 *            待設置的要保書危險單位金額合計資訊接口的值
	 */
	public void setPrpTdangerTotService(PrpTdangerTotService prpTdangerTotService) {
		this.prpTdangerTotService = prpTdangerTotService;
	}

	/**
	 * 獲取屬性投保單的危險單位劃分接口.
	 * 
	 * @return 屬性投保單的危險單位劃分接口的值
	 */
	public PrpTdangerUnitService getPrpTdangerUnitService() {
		return prpTdangerUnitService;
	}

	/**
	 * 設置屬性投保單的危險單位劃分接口.
	 * 
	 * @param prpTdangerUnitService
	 *            待設置的投保單的危險單位劃分接口的值
	 */
	public void setPrpTdangerUnitService(PrpTdangerUnitService prpTdangerUnitService) {
		this.prpTdangerUnitService = prpTdangerUnitService;
	}

	/**
	 * 獲取屬性分保試算結果資訊接口.
	 * 
	 * @return 屬性分保試算結果資訊接口的值
	 */
	public PrpTReinsShareService getPrpTReinsShareService() {
		return prpTReinsShareService;
	}

	/**
	 * 設置屬性分保試算結果資訊接口.
	 * 
	 * @param prpTReinsShareService
	 *            待設置的分保試算結果資訊接口的值
	 */
	public void setPrpTReinsShareService(PrpTReinsShareService prpTReinsShareService) {
		this.prpTReinsShareService = prpTReinsShareService;
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
	 * 獲取屬性賠案危險單位共保資訊接口.
	 * 
	 * @return 屬性賠案危險單位共保資訊接口的值
	 */
	public PrpLDangerCoinsService getPrpLDangerCoinsService() {
		return prpLDangerCoinsService;
	}

	/**
	 * 設置屬性賠案危險單位共保資訊接口.
	 * 
	 * @param prpLDangerCoinsService
	 *            待設置的賠案危險單位共保資訊接口的值
	 */
	public void setPrpLDangerCoinsService(PrpLDangerCoinsService prpLDangerCoinsService) {
		this.prpLDangerCoinsService = prpLDangerCoinsService;
	}

	/**
	 * 獲取屬性理賠的危險單位資訊接口.
	 * 
	 * @return 屬性理賠的危險單位資訊接口的值
	 */
	public PrpLDangerItemService getPrpLDangerItemService() {
		return prpLDangerItemService;
	}

	/**
	 * 設置屬性理賠的危險單位資訊接口.
	 * 
	 * @param prpLDangerItemService
	 *            待設置的理賠的危險單位資訊接口的值
	 */
	public void setPrpLDangerItemService(PrpLDangerItemService prpLDangerItemService) {
		this.prpLDangerItemService = prpLDangerItemService;
	}

	/**
	 * 獲取屬性理賠危險單位金額合計資訊接口.
	 * 
	 * @return 屬性理賠危險單位金額合計資訊接口的值
	 */
	public PrpLDangerTotService getPrpLDangerTotService() {
		return prpLDangerTotService;
	}

	/**
	 * 設置屬性理賠危險單位金額合計資訊接口.
	 * 
	 * @param prpLDangerTotService
	 *            待設置的理賠危險單位金額合計資訊接口的值
	 */
	public void setPrpLDangerTotService(PrpLDangerTotService prpLDangerTotService) {
		this.prpLDangerTotService = prpLDangerTotService;
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
	 * 獲取屬性再保分入資訊確認意見接口.
	 * 
	 * @return 屬性再保分入資訊確認意見接口的值
	 */
	public PrpReinsNotionService getPrpReinsNotionService() {
		return prpReinsNotionService;
	}

	/**
	 * 設置屬性再保分入資訊確認意見接口.
	 * 
	 * @param prpReinsNotionService
	 *            待設置的再保分入資訊確認意見接口的值
	 */
	public void setPrpReinsNotionService(PrpReinsNotionService prpReinsNotionService) {
		this.prpReinsNotionService = prpReinsNotionService;
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
	 * 獲取屬性要保書訊息接口.
	 * 
	 * @return 屬性要保書訊息接口的值
	 */
	public PrpCpMainService getPrpCpMainService() {
		return prpCpMainService;
	}

	/**
	 * 設置屬性要保書訊息接口.
	 * 
	 * @param prpCpMainService
	 *            待設置的要保書訊息接口的值
	 */
	public void setPrpCpMainService(PrpCpMainService prpCpMainService) {
		this.prpCpMainService = prpCpMainService;
	}
	
	//add by xuhuiling begin
	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public TaskDealService getTaskDealService() {
		return taskDealService;
	}

	public void setTaskDealService(TaskDealService taskDealService) {
		this.taskDealService = taskDealService;
	}
	//add by xuhuiling end

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

	@Override
	public boolean callPrpinsAml(String businessType, String businessNo,String userCode)
			throws Exception {
		String policyNo = "";
		String uRLPath = "";
		String businessMsg = "";
		String strMessage = "";
		StringBuffer strBuffer = null;

		OutputStream outputStream = null;
		OutputStreamWriter writer = null;
		InputStream inputStream = null;
		BufferedReader reader = null;
		uRLPath = SysConfig.getProperty("UndwrtForUndwrtToAML");
//		uRLPath = "http://localhost:7001/prpins/PrpinsToUndwrtServlet";
//		uRLPath = "http://localhost:7001/prpins/ForUndwrtToAML";
		System.out.println("遠程路徑："+uRLPath);
		URL urlServlet = new URL(uRLPath);
		// 设置参数
		HttpURLConnection servletConnection = (HttpURLConnection) urlServlet.openConnection();
		servletConnection.setUseCaches(false);
		servletConnection.setDoOutput(true);
		servletConnection.setDoInput(true);
		servletConnection.setRequestMethod("POST");
		servletConnection.setAllowUserInteraction(true);
		servletConnection.connect();

		// 发送业务信息
		businessMsg = businessType + "," + businessNo+","+userCode;

		outputStream = servletConnection.getOutputStream();
		writer = new OutputStreamWriter(outputStream);
		writer.write(businessMsg);
		writer.flush();
		writer.close();
		// 接收返回参数
		inputStream = servletConnection.getInputStream();
		reader = new BufferedReader(new InputStreamReader(inputStream));
		strBuffer = new StringBuffer();
		while ((strMessage = reader.readLine()) != null) {
			strBuffer.append(strMessage);
		}
		System.out.println("responseText: "+strBuffer.toString());
		String returnMessage = strBuffer.toString();
		if(returnMessage!=null && "00".equals(returnMessage)){
			return true;
		}else{
			return false;
		}
	}
}