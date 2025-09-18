package com.tlg.aps.bs.firBotRenewalFileService.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firBotRenewalFileService.BotRenewalFileService;
import com.tlg.aps.bs.firBotRenewalFileService.FirBotRenewalFileService;
import com.tlg.aps.bs.firVerifyService.FirVerifyDatasService;
import com.tlg.aps.vo.Aps055BotDetailVo;
import com.tlg.aps.vo.FirAddressCheckVo;
import com.tlg.aps.vo.FirAddressRuleObj;
import com.tlg.aps.vo.FirAmountWsParamVo;
import com.tlg.aps.vo.FirEqFundQueryVo;
import com.tlg.aps.vo.FirInsPremVo;
import com.tlg.aps.vo.FirPremWsParamVo;
import com.tlg.aps.vo.FirVerifyVo;
import com.tlg.aps.vo.RuleReponseDetailVo;
import com.tlg.aps.vo.RuleReponseVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBotFd;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;
import com.tlg.prpins.service.FirAgtBotFdService;
import com.tlg.prpins.service.FirAgtTocoreInsuredService;
import com.tlg.prpins.service.FirAgtTocoreMainService;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnBatchMainService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.sales.entity.PrpdAgreement;
import com.tlg.sales.service.PrpdAgreementService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.entity.Rfrcode;
import com.tlg.xchg.service.RfrcodeService;

/** /** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業 **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBotRenewalFileServiceImpl implements FirBotRenewalFileService {

	private static final Logger logger = Logger.getLogger(FirBotRenewalFileServiceImpl.class);
	private ConfigUtil configUtil;
	private FirBatchInfoService firBatchInfoService;
	private PrpdAgreementService prpdAgreementService;
	private RfrcodeService rfrcodeService;
	private FirAgtrnBatchMainService firAgtrnBatchMainService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;

	private static final String OUTMSG = "msg";
	private static final String OUTSTATUS = "status";

	private BotRenewalFileService botRenewalFileService;
	private FirSpService firSpService;
	private FirAgtTocoreMainService firAgtTocoreMainService;
	private FirAgtTocoreInsuredService firAgtTocoreInsuredService;

	private FirVerifyDatasService firVerifyDatasService;
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
	private FirAgtBotFdService firAgtBotFdService;

	@Override
	public Result runToReceiveData(String userId, Date excuteTime, String programId, String rnDate) throws Exception {
		// 新增執行記錄檔、判斷排程是否可以執行
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值。");
		}
		if (excuteTime == null) {
			sb.append("轉檔時間無內容值。");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值。");
		}
		if (StringUtil.isSpace(rnDate)) {
			sb.append("續保年月無內容值。");
		}
		FirBatchLog firBatchLog;
		String batchNo = programId.substring(8) + "_" + new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		if (!StringUtil.isSpace(sb.toString())) {
			Result result = botRenewalFileService.insertFirBatchLog(excuteTime, userId, programId, "F", sb.toString(),
					batchNo);
			if (result.getResObject() != null) {
				firBatchLog = (FirBatchLog) result.getResObject();
				String mailMsg = sendEmail(firBatchLog.getBatchNo(), excuteTime, "F", sb.toString(), programId);
				if (!StringUtil.isSpace(mailMsg)) {
					botRenewalFileService.updateFirBatchLog("F", mailMsg, userId, firBatchLog);
				}
			}
			return this.getReturnResult("接收參數無值，結束排程");
		}
		String status = "1";
		String msg = "";
		Map<String, String> params = new HashMap<>();
		params.put("prgId", programId + "_STATUS");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if (result.getResObject() != null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			if ("N".equals(firBatchInfo.getMailTo())) {
				status = "S";
				msg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			}
		} else {
			return this.getReturnResult("查無此排程代號，請詢問技術人員");
		}

		result = botRenewalFileService.insertFirBatchLog(excuteTime, userId, programId, status, msg, batchNo);
		firBatchLog = (FirBatchLog) result.getResObject();
		if (status.equals("S")) {
			return this.getReturnResult("查詢狀態為N，不執行排程");
		}
		if (result.getResObject() != null) {
			BigDecimal batchLogOid = firBatchLog.getOid();
			Map<String, String> returnData = null;
			returnData = callSp(batchNo, userId, rnDate);

			if ("N".equals(returnData.get(OUTSTATUS))) {
				botRenewalFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
				updateFirBatchLog("F", returnData.get(OUTMSG), userId, batchLogOid);
				sendEmail(batchNo, excuteTime, returnData.get(OUTSTATUS), returnData.get(OUTMSG), programId);
				return this.getReturnResult("檔案無資料");
			} else if ("F".equals(returnData.get(OUTSTATUS))) {
				updateFirBatchLog("F", returnData.get(OUTMSG), userId, batchLogOid);
				sendEmail(batchNo, excuteTime, "F", returnData.get(OUTMSG), programId);
				return this.getReturnResult("執行產生台銀續件資料SP失敗");
			} else if ("S".equals(returnData.get(OUTSTATUS))) {

				try {// 資料檢核
					returnData = dataReview(batchNo, userId);

				} catch (Exception e) {
					sendEmail(batchNo, excuteTime, "F", e.toString(), programId);
					updateFirBatchLog("F", e.toString(), userId, batchLogOid);
					return this.getReturnResult("資料檢核執行失敗");
				}

				if ("Y".equals(returnData.get("transStatus"))) {
					botRenewalFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
					sendEmail(batchNo, excuteTime, "S", returnData.get(OUTMSG), programId);
					updateFirBatchLog("S", returnData.get(OUTMSG), userId, batchLogOid);
				} else if ("E".equals(returnData.get("transStatus"))) {
					botRenewalFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
					sendEmail(batchNo, excuteTime, "F", returnData.get(OUTMSG), programId);
					updateFirBatchLog("F", returnData.get(OUTMSG), userId, batchLogOid);
				}
			}

		}

		return this.getReturnResult("執行完成");
	}
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程  start**/
	@Override
	public Result compareFDIntoCore(String userId, Date excuteTime, String programId) throws Exception {
		// 新增執行記錄檔、判斷排程是否可以執行
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值。");
		}
		if (excuteTime == null) {
			sb.append("轉檔時間無內容值。");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值。");
		}
		FirBatchLog firBatchLog;
		String batchNo = "BOTRN_FD" + new SimpleDateFormat("yyMMddHHmmss").format(excuteTime);
		if (!StringUtil.isSpace(sb.toString())) {
			Result result = botRenewalFileService.insertFirBatchLog(excuteTime, userId, programId, "F", sb.toString(),
					batchNo);
			if (result.getResObject() != null) {
				firBatchLog = (FirBatchLog) result.getResObject();
				Map empty=new HashMap();
				sendFdEmail(firBatchLog.getBatchNo(), excuteTime, "F", sb.toString(), programId,empty,empty);
			}
			return this.getReturnResult("接收參數無值，結束排程");
		}
		String status = "1";
		String msg = "";
		Map<String, String> params = new HashMap<>();
		params.put("prgId", programId + "_STATUS");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if (result.getResObject() != null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			if ("N".equals(firBatchInfo.getMailTo())) {
				status = "S";
				msg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			}
		} else {
			return this.getReturnResult("查無此排程代號，請詢問技術人員");
		}
		
		result = botRenewalFileService.insertFirBatchLog(excuteTime, userId, programId, status, msg, batchNo);
		firBatchLog = (FirBatchLog) result.getResObject();
		if (result.getResObject() != null) {
			BigDecimal batchLogOid = firBatchLog.getOid();
			Map<String, Object> returnData = null;
			
			if(!status.equals("S")){
				returnData = compareData(batchNo, excuteTime, userId);				
			}
			if ("N".equals(returnData.get(OUTSTATUS))) {
				updateFirBatchLog("N", msg, userId, batchLogOid);
				return this.getReturnResult("無資料");
			} else if ("S".equals(returnData.get(OUTSTATUS))) {
				msg += sendFdEmail(batchNo, excuteTime, "S", (String)returnData.get(OUTMSG), programId,(Map)returnData.get("dataNum"),(Map)returnData.get("errData"));
				updateFirBatchLog("S", msg, userId, batchLogOid);
			} else if ("F".equals(returnData.get(OUTSTATUS))) {
				msg += sendFdEmail(batchNo, excuteTime, "F", (String)returnData.get(OUTMSG), programId,(Map)returnData.get("dataNum"),(Map)returnData.get("errData"));
				updateFirBatchLog("F", msg, userId, batchLogOid);
			}

		}
		
		return this.getReturnResult("執行完成");
	}
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 end**/

	public Map<String, String> callSp(String batchNo, String userId, String rnDate) throws SystemException, Exception {
		Map<String, Object> params = new HashMap<>();
		Map<String, String> returnData = new HashMap<>();
		int returnValue = 0;
		params.put("inBatchNo", batchNo);
		params.put("inRnYyyymm", rnDate);
		params.put("inUser", userId);
		params.put("outResult", null);

		returnValue = firSpService.runSpFirAgtrnBot(params);

		if (returnValue == 0) {
			params.clear();
			params.put("batchNo", batchNo);
			Result result = firAgtrnBatchMainService.findFirAgtrnBatchMainByUk(params);
			if (result.getResObject() != null) {
				FirAgtrnBatchMain firAgtrnBatchMain = (FirAgtrnBatchMain) result.getResObject();
				if ("Z".equals(firAgtrnBatchMain.getFileStatus())) {
					returnData.put("status", "N"); // 檔案無資料
					returnData.put("msg", "檔案無資料");
				} else {
					returnData.put("status", "S");

				}
			}

		} else {
			returnData.put("status", "F");
			returnData.put("msg", "執行產生台銀續件資料SP失敗");
		}
		return returnData;
	}

	// 資料檢核處理 需檢核所有參數必填
	@SuppressWarnings("unchecked")
	private Map<String, String> dataReview(String batchNo, String userId) throws Exception {
		Map<String, String> returnData = new HashMap<>();
		Map<String, String> params = new HashMap<>();
		Map<String, Object> params2 = new HashMap<>();
		String dataStatus = "";
		int dataqtyS = 0;
		int dataqtyF = 0;
		String transStatus = "";
		String remark = "";

		if (StringUtil.isSpace(batchNo)) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "批次號碼未輸入，無法執行。");
			return returnData;
		}
		if (StringUtil.isSpace(userId)) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "執行人員未輸入，無法執行。");
			return returnData;
		}

		Result result;

		params.put("batchNo", batchNo);
		result = firAgtTocoreMainService.findFirAgtTocoreMainByParams(params);

		if (result.getResObject() == null) {
			returnData.put(OUTSTATUS, "0");
			returnData.put(OUTMSG, "");
			return returnData;
		}
		List<FirAgtTocoreMain> firAgtTocoreMainList = (List<FirAgtTocoreMain>) result.getResObject();
		String tmpCommrateF = "";
		String tmpCommrateQ = "";
		try {

			for (FirAgtTocoreMain main : firAgtTocoreMainList) {
				StringBuilder tmpErrMsg = new StringBuilder();
				StringBuilder tmpWarnMsg = new StringBuilder();
				if ("11".equals(main.getTemp1())) {

					// 取佣金率start
					params.clear();
					params.put("businesssourcecode", "I99004");
					result = prpdAgreementService.findPrpdAgreementJoinDetail(params);
					List<PrpdAgreement> prpdAgreementList = (List<PrpdAgreement>) result.getResObject();
					if (prpdAgreementList.size() == 2) {
						for (PrpdAgreement p : prpdAgreementList) {
							if ("FR2".equals(p.getKindcode())) {
								tmpCommrateQ = p.getTopCommission();
							}
							if ("FR3".equals(p.getKindcode())) {
								tmpCommrateF = p.getTopCommission();
							}
						}
					} else {
						tmpErrMsg.append("無法取得核心系統佣金率或佣金率設定異常；");
					}
					// 取佣金率end

					// 複保險檢核 start
					String dquakeStatus = "";
					String dquakeNo = "";
					String fAmt = null;
					//mantis：FIR0620，處理人員：BJ085，需求單編號：FIR0620 住火-台銀續保作業
					//String qAmt = null;
					String structure = "";
					BigDecimal highriseFee = new BigDecimal(0);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					Calendar c = Calendar.getInstance();
					c.setTime(main.getStartdate());
					c.add(Calendar.YEAR, 1);
					String endDate = sdf.format(c.getTime());
					BigDecimal premiumF = new BigDecimal(0);
					BigDecimal premiumQ = new BigDecimal(0);
					BigDecimal premiumT = new BigDecimal(0);
					BigDecimal rateF = new BigDecimal(0);
					FirEqFundQueryVo firEqFundQueryVo = new FirEqFundQueryVo();
					firEqFundQueryVo.setStartDate(sdf.format(main.getStartdate()));
					firEqFundQueryVo.setEndDate(endDate);
					firEqFundQueryVo.setPostcode(main.getAddresscode());
					firEqFundQueryVo.setAddress(main.getAddressdetailinfo());
					firEqFundQueryVo.setSourceType("BOT-RN");
					firEqFundQueryVo.setSourceUserid(userId);
					FirVerifyVo firVerifyVo;
					firVerifyVo = firVerifyDatasService.queryDoubleInsVerify(firEqFundQueryVo);
					dquakeNo = firVerifyVo.getDquakeNo();
					dquakeStatus = firVerifyVo.getDquakeStatus();
					tmpWarnMsg.append(firVerifyVo.getWarnMsg());
					// 複保險檢核 end

					// 呼叫webService保額計算
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
					FirPremcalcTmp firPremcalcTmp = new FirPremcalcTmp();
					FirAmountWsParamVo firWsParamVo = new FirAmountWsParamVo();
					firWsParamVo.setSourceType("BOTRN");
					firWsParamVo.setSourceUser(userId);
					firWsParamVo.setCalcType("1");
					firWsParamVo.setCalcDate(sdf2.format(main.getStartdate()));
					firWsParamVo.setChannelType("20");
					firWsParamVo.setPostcode(main.getAddresscode());
					firWsParamVo.setWallno(main.getWallmaterial());
					firWsParamVo.setRoofno(main.getRoofmaterial());
					firWsParamVo.setSumfloors(main.getSumfloors());
					firWsParamVo.setBuildarea(main.getBuildarea());
					firWsParamVo.setDecorFee("0");
					/* mantis：FIR0620，處理人員：BJ085，需求單編號：FIR0620 住火-台銀續保作業 start
					 * 住火、地震保額一率取到萬元*/
//					String amountFlag = null;
//					long amountFLastCheck = main.getAmountFLast() != 0 ? main.getAmountFLast() % 10000 : null;
//					long amountQLastCheck = main.getAmountQLast() != 0 ? main.getAmountQLast() % 10000 : null;
//					if (amountFLastCheck != 0 && amountQLastCheck == 0) {
//						amountFlag = "f";
//					} else if (amountFLastCheck == 0 && amountQLastCheck != 0) {
//						amountFlag = "D";
//					} else if (amountFLastCheck != 0 && amountQLastCheck != 0) {
//						amountFlag = "A";
//					}
//					if (amountFlag != null) {
//						firWsParamVo.setAmountFlag(amountFlag);
//					}
					/* mantis：FIR0620，處理人員：BJ085，需求單編號：FIR0620 住火-台銀續保作業 end*/
					BigDecimal quakeAmt = new BigDecimal(0);
					BigDecimal firAmt = new BigDecimal(0);
					String famtStatus = "";
					BigDecimal oidFirPremcalcTmp = new BigDecimal(0);
					try {
						firPremcalcTmp = firVerifyDatasService.firAmountCal(firWsParamVo);
						oidFirPremcalcTmp = firPremcalcTmp.getOid();
						if ("Y".equals(firPremcalcTmp.getReturnType())) {
							firAmt = firPremcalcTmp.getFsAmt();
							quakeAmt = firPremcalcTmp.getEqAmt().setScale(0);
							fAmt = main.getAmountF();
							//mantis：FIR0620，處理人員：BJ085，需求單編號：FIR0620 住火-台銀續保作業
							//qAmt = main.getAmountQ();

							// 判斷火險保額是否足額
							if (!StringUtil.isSpace(fAmt)
									&& new BigDecimal(fAmt).compareTo(firPremcalcTmp.getFsMaxAmt()) > 0) {
								famtStatus = "3";
								tmpWarnMsg.append("檢核-火險超額" + fAmt + "(上限保額:" + firPremcalcTmp.getFsMaxAmt() + ")；");
							} else if (!StringUtil.isSpace(fAmt) && new BigDecimal(fAmt).compareTo(firAmt) < 0) {
								famtStatus = "2";
								tmpWarnMsg.append("火險不足額" + fAmt + "(建議保額：" + firAmt + ")；");
							} else {
								famtStatus = "1";// 足額
							}

						} else {
							tmpErrMsg.append("檢核-保額計算WS異常(" + firPremcalcTmp.getOid() + "):"
									+ firPremcalcTmp.getReturnMsg() + "；");
						}

					} catch (Exception e) {
						logger.error("保額計算失敗", e);
						tmpErrMsg.append("檢核-保額計算WS無回應；");
					}
					// 保額計算end

					// 呼叫webservice計算保費
					FirPremWsParamVo firPremWsParamVo = new FirPremWsParamVo();
					firPremWsParamVo.setSourceType("BOTRN");
					firPremWsParamVo.setSourceUser(userId);
					firPremWsParamVo.setCalcType("2");
					firPremWsParamVo.setCalcDate(sdf2.format(main.getStartdate()));
					firPremWsParamVo.setChannelType("20");

					ArrayList<FirInsPremVo> firInsPremVoList = new ArrayList<>();
					if (fAmt != null) {
						if (new BigDecimal(fAmt).compareTo(new BigDecimal(0)) > 0) {
							FirInsPremVo firInsPremVoF = new FirInsPremVo();
							firInsPremVoF.setRiskcode("F02");
							firInsPremVoF.setKindcode("FR3");
							firInsPremVoF.setParaType("1");
							firInsPremVoF.setPara01(fAmt);
							firInsPremVoF.setPara02(main.getWallmaterial());
							firInsPremVoF.setPara03(main.getRoofmaterial());
							firInsPremVoF.setPara04(main.getSumfloors());
							firInsPremVoF.setPara05("N");
							firInsPremVoList.add(firInsPremVoF);
						}
					}
					if (quakeAmt != null) {
						if (quakeAmt.compareTo(new BigDecimal(0)) > 0) {
							FirInsPremVo firInsPremVoQ = new FirInsPremVo();
							firInsPremVoQ.setRiskcode("F02");
							firInsPremVoQ.setKindcode("FR2");
							//mantis：FIR0620，處理人員：BJ085，需求單編號：FIR0620 住火-台銀續保作業
							firInsPremVoQ.setPara01(quakeAmt.toString());
							firInsPremVoList.add(firInsPremVoQ);
						}

					}
					firPremWsParamVo.setInsPremList(firInsPremVoList);
					try {
						firPremcalcTmp = firVerifyDatasService.firPremCal(firPremWsParamVo);

						List<FirPremcalcTmpdtl> premList = firPremcalcTmp.getFirPremcalcTmpdtlList();
						FirPremcalcTmpdtl fr2Dtl = null;
						FirPremcalcTmpdtl fr3Dtl = null;
						structure = main.getStructure();
						if ("Y".equals(firPremcalcTmp.getReturnType())) {
							for (int p = 0; p < premList.size(); p++) {
								if ("FR2".equals(premList.get(p).getKindcode())) {
									fr2Dtl = premList.get(p);
								} else if ("FR3".equals(premList.get(p).getKindcode())) {
									fr3Dtl = premList.get(p);
								}
							}
							highriseFee = firPremcalcTmp.getFireHigh();
							structure = firPremcalcTmp.getFireStructure();
							if (!StringUtil.isSpace(fAmt)) {
								premiumF = fr3Dtl.getPremium();
								rateF=fr3Dtl.getPremRate();
							}

							//mantis：FIR0620，處理人員：BJ085，需求單編號：FIR0620 住火-台銀續保作業
							if (quakeAmt.compareTo(new BigDecimal(0)) == 1) {
								premiumQ = fr2Dtl.getPremium();
							}
							premiumT=premiumF.add(premiumQ);
							if(main.getPremiumFLast()!=null){
								if (new BigDecimal(main.getPremiumFLast()).compareTo(premiumF) != 0) {
									tmpWarnMsg.append("火險保費計算結果與前期(" + main.getPremiumFLast() + ")資料不一致；");
								}
							}
							if(main.getPremiumQLast()!=null){
								if (new BigDecimal(main.getPremiumQLast()).compareTo(premiumQ) != 0) {
									tmpWarnMsg.append("地震險保費計算結果與前期(" + main.getPremiumQLast() + ")資料不一致；");
								}
							}
							if (!main.getStructure().equals(firPremcalcTmp.getFireStructure())) {
								tmpWarnMsg.append("WS計算建築等級結果與前期 (" + main.getStructure() + ")資料不一致；");
							}

						} else {
							tmpErrMsg.append("檢核-保費計算WS計算發生錯誤，請聯繫負責人(" + firPremcalcTmp.getOid() + ")"
									+ firPremcalcTmp.getReturnMsg() + "；");
						}

					} catch (Exception e) {
						logger.error("保費計算WS失敗", e);
						tmpErrMsg.append("檢核-保費計算WS無回應，未計算保費；");
					}
					// 計算保費end

					// 建築等級說明文字處理start
					String structureText = "";
					if (!StringUtil.isSpace(main.getWallmaterial()) && !StringUtil.isSpace(main.getRoofmaterial())
							&& !StringUtil.isSpace(main.getSumfloors()) && !StringUtil.isSpace(structure)) {

						String wallname = firVerifyDatasService.findPrpdNewCode("WallMaterial", main.getWallmaterial());
						if (StringUtil.isSpace(wallname)) {
							tmpErrMsg.append("建築等級說明-外牆(" + main.getWallmaterial() + ")查無名稱；");
						}
						String roofname = firVerifyDatasService.findPrpdNewCode("RoofMaterial", main.getRoofmaterial());
						if (StringUtil.isSpace(roofname)) {
							tmpErrMsg.append("建築等級說明-屋頂(" + main.getRoofmaterial() + ")查無名稱；");
						}
						String floors = main.getSumfloors() + "層樓";
						String stxt = getStructure(structure);
						structureText = wallname + roofname + floors + stxt;
					} else {
						tmpWarnMsg.append("其他-因資料不齊全未進行建築等級說明文字處理；");
					}
					// 建築等級說明文字處理end

					// 地址正確性檢核start
					String addrStatus = "";
					String addrDetail = "";
					FirAddressCheckVo firAddressCheckVo = new FirAddressCheckVo();
					firAddressCheckVo.setZip(main.getAddresscode());
					firAddressCheckVo.setAddress(main.getAddressdetailinfo());
					firAddressCheckVo.setStructure(structure);
					firAddressCheckVo.setFloors(main.getSumfloors());

					firVerifyVo = firVerifyDatasService.addressVerify(firAddressCheckVo);

					addrStatus = firVerifyVo.getAddrStatus();
					addrDetail = firVerifyVo.getAddrDetail();
					if (!"".equals(firVerifyVo.getWarnMsg())) {
						tmpWarnMsg.append("檢核-" + firVerifyVo.getWarnMsg());
					}
					// 地址正確性檢核end

					// 颱風洪水檢核start
					String[] areaArr = { "基隆市", "宜蘭縣", "花蓮縣", "台東縣", "屏東縣" };
					if (("3".equals(structure) || "5".equals(structure) || "6".equals(structure))) {
						params.clear();
						params.put("codetype ", "PostCode");
						params.put("codecode", main.getAddresscode());
						result = rfrcodeService.findRfrcodeByParams(params);
						if (result.getResObject() != null) {
							List<Rfrcode> rfrcodeList = (List<Rfrcode>) result.getResObject();
							if (Arrays.asList(areaArr).contains(rfrcodeList.get(0).getCodename().substring(1, 3))) {
								tmpWarnMsg.append("易淹水地區；");
							}
						}
					}
					// 颱風洪水檢核end

					// 稽核議題檢核start
					if (!StringUtil.isSpace(main.getAddresscode()) && !StringUtil.isSpace(main.getAddressdetailinfo())
							&& !StringUtil.isSpace(structure) && !StringUtil.isSpace(main.getSumfloors())
							&& !StringUtil.isSpace(main.getWallmaterial())
							&& !StringUtil.isSpace(main.getRoofmaterial())) {
						FirAddressRuleObj firAddressRuleObj = new FirAddressRuleObj();
						firAddressRuleObj.setRiskcode("F02");
						firAddressRuleObj.setFuncType("P");
						firAddressRuleObj.setRulePrefix("FIR");
						firAddressRuleObj.setPostcode(main.getAddresscode());// 郵遞區號三碼
						firAddressRuleObj.setAddress(main.getAddressdetailinfo());
						firAddressRuleObj.setAddrStructure(structure);
						firAddressRuleObj.setAddrSumfloors(main.getSumfloors());
						firAddressRuleObj.setAddrWall(main.getWallmaterial());
						firAddressRuleObj.setAddrRoof(main.getRoofmaterial());
						try {
							RuleReponseVo ruleReponseVo = firVerifyDatasService.firAddressRule(firAddressRuleObj);
							if ("0".equals(ruleReponseVo.getStatus())) {
								List<RuleReponseDetailVo> ruleList = ruleReponseVo.getDetailList();
								if (!ruleList.isEmpty()) {
									for (int j = 0; j < ruleList.size(); j++) {
										if ("0".equals(ruleList.get(j).getRuleResult())) {
											tmpWarnMsg.append(ruleList.get(j).getRuleMsg());
										}
									}
								}
							}
						} catch (Exception e) {
							logger.error("webService呼叫火險稽核議題檢核異常", e);
							tmpWarnMsg.append("檢核-稽核議題WS無回應或異常" + e + ";");
						}
					} else {
						tmpWarnMsg.append("檢核-因資料不齊全未進行稽核議題檢核；");
					}
					// 稽核議題檢核end

					// 要被保人資料處理且更新要保資料關係人檔start

					params2.put("batchNo", main.getBatchNo());
					params2.put("batchSeq", main.getBatchSeq());
					result = firAgtTocoreInsuredService.findFirAgtTocoreInsuredByParams(params2);
					List<FirAgtTocoreInsured> firAgtTocoreInsuredList = (List<FirAgtTocoreInsured>) result
							.getResObject();
					List<FirAgtTocoreInsured> firAgtTocoreInsuredToUpdateList = new ArrayList<>();
					for (FirAgtTocoreInsured f : firAgtTocoreInsuredList) {
						String id = f.getIdentifynumber();
						String identity = "";
						if ("2".equals(f.getInsuredflag())) {
							identity = "要保人";
						}
						if ("1".equals(f.getInsuredflag())) {
							identity = "被保人";
						}
						if (!StringUtil.isSpace(id)) {
							try {
								Map<String, String> verifyMap = firVerifyDatasService.verifyID(id);
								if (!StringUtil.isSpace(verifyMap.get("errMsg"))) {
									tmpErrMsg.append("第" + f.getBatchSeq() + "筆" + identity + "ID-"
											+ verifyMap.get("errMsg") + "；");
								}
								if (!StringUtil.isSpace(verifyMap.get("insuredNature"))) {
									String insuredNature = verifyMap.get("insuredNature");
									f.setInsurednature(insuredNature);
								}
								if (!StringUtil.isSpace(verifyMap.get("idType"))) {
									String idtype = verifyMap.get("idType");
									f.setIdentifytype(idtype);
									if ("04".equals(idtype)) {
										tmpErrMsg.append(
												"第" + f.getBatchSeq() + "筆" + identity + "ID-證號類型可能為稅籍編號或異常，請再確認。；");
									}
								}
								String gender=verifyMap.get("gender");
								f.setGender(gender);
								firAgtTocoreInsuredToUpdateList.add(f);
							} catch (Exception e) {
								logger.error("verifyID WS Exception", e);
								tmpErrMsg.append("呼叫證號檢核WS異常:" + e + "；");
							}
						} else {
							tmpErrMsg.append("第" + f.getBatchSeq() + "筆" + identity + "ID-未輸入；");
						}
					}
					// 要被保人資料處理且更新要保資料關係人檔end

					// 更新APS要保資料主檔 start
					main.setRepeatpolicyno(dquakeNo);
					main.setStructure(structure);
					main.setStructureText(structureText);
					main.setHighrisefee(highriseFee);
					main.setPremiumF(premiumF.toString());
					main.setCommrateF(new BigDecimal(tmpCommrateF));
					main.setAmountQ(quakeAmt.toString());
					main.setPremiumQ(premiumQ.toString());
					main.setPremiumT(premiumT.toString());
					main.setCommrateQ(new BigDecimal(tmpCommrateQ));
					main.setRateF(rateF);

					try {
						botRenewalFileService.updateFirAgtTocore(main, firAgtTocoreInsuredToUpdateList);
						dataStatus = "2";
						dataqtyS++;
					} catch (Exception e) {
						dataStatus = "1";
						tmpErrMsg.append("更新主檔異常：" + e.getMessage());
						dataqtyF++;
					}
					FirAgtrnBatchDtl firAgtrnBatchDtl = new FirAgtrnBatchDtl();
					// params2.clear();
					// params2.put("batchNo",batchNo);
					// params2.put("batchSeq",main.getBatchSeq());
					// result =
					// firAgtrnBatchDtlService.findFirAgtrnBatchDtlByParams(params2);
					// List<FirAgtrnBatchDtl>firAgtrnBatchDtlList=(List<FirAgtrnBatchDtl>)result.getResObject();
					// firAgtrnBatchDtl=firAgtrnBatchDtlList.get(0);
					firAgtrnBatchDtl.setDataStatus(dataStatus);
					firAgtrnBatchDtl.setDquakeStatus(dquakeStatus);
					firAgtrnBatchDtl.setDquakeNo(dquakeNo);
					firAgtrnBatchDtl.setOidFirPremcalcTmp(oidFirPremcalcTmp.longValue());
					firAgtrnBatchDtl.setFamtStatus(famtStatus);
					firAgtrnBatchDtl.setWsFirAmt(firAmt.longValue());
					firAgtrnBatchDtl.setWsQuakeAmt(quakeAmt.intValue());
					firAgtrnBatchDtl.setAddrStatus(addrStatus);
					firAgtrnBatchDtl.setAddrDetail(addrDetail);
					firAgtrnBatchDtl.setCheckErrMsg(tmpErrMsg.toString());
					firAgtrnBatchDtl.setCheckWarnMsg(tmpWarnMsg.toString());
					firAgtrnBatchDtl.setIupdate(userId);
					firAgtrnBatchDtl.setDupdate(new Date());

					botRenewalFileService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl, batchNo, userId, main.getBatchSeq());
				} else {
					FirAgtrnBatchDtl firAgtrnBatchDtl = new FirAgtrnBatchDtl();
					firAgtrnBatchDtl.setDataStatus("2");
					firAgtrnBatchDtl.setIupdate(userId);
					firAgtrnBatchDtl.setDupdate(new Date());
					dataqtyS++;
					botRenewalFileService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl, batchNo, userId, main.getBatchSeq());

				}
			}
			transStatus = "Y";
		} catch (Exception e) {
			transStatus = "E";
			remark = e.getMessage();
		}
		params.clear();
		returnData.put("dataqtyS", String.valueOf(dataqtyS));
		returnData.put("dataqtyF", String.valueOf(dataqtyF));
		returnData.put("transStatus", transStatus);
		returnData.put("msg", remark);
		

		return returnData;
	}

	private String getStructure(String structure) {
		String text = "";
		if ("1".equals(structure)) {
			text = " – 特一等建築";
		}
		if ("2".equals(structure)) {
			text = " – 特二等建築";
		}
		if ("3".equals(structure)) {
			text = " – 頭等";
		}
		if ("5".equals(structure)) {
			text = " – 二等";
		}
		if ("6".equals(structure)) {
			text = " – 三等";
		}
		if ("7".equals(structure)) {
			text = " – 露天";
		}
		return text;
	}

	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo, Date excuteTime, String status, String errMsg, String programId) {
		Mailer mailer = new Mailer();
		StringBuilder tmpMsg = new StringBuilder();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("prgId", programId );
			Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
			if (result.getResObject() == null) {
				return "無法取得FIR_BATCH_INFO資料，無法寄送MAIL";
			}
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String subject = firBatchInfo.getMailSubject() + "-" + sdf.format(new Date());
			String mailTo = firBatchInfo.getMailTo();
//			 mailTo = "cd094@ctbcins.com";
			String mailCc = firBatchInfo.getMailCc();

			StringBuilder sb = new StringBuilder();
			sb.append("<p>批次號碼：" + batchNo + "</p>");
			sb.append("<p>轉檔時間：" + sdf.format(excuteTime) + "</p>");
			if (status.equals("S")) {
				sb.append("<p>執行狀態：完成</p>");
				params.clear();
				params.put("batchNo", batchNo);
				Result mainResult = firAgtrnBatchMainService.findFirAgtrnBatchMainByParams(params);

				params.clear();
				params.put("batchNo", batchNo);
				params.put("sortBy", "BATCH_SEQ");
				Result dtlResult = firAgtrnBatchDtlService.findFirAgtrnBatchDtlByParams(params);
				if (mainResult.getResObject() == null) {
					tmpMsg.append("FIR_AGTRN_BATCH_MAIN批次主檔查無資料，請洽系統人員。");
				} else if (dtlResult.getResObject() == null) {
					tmpMsg.append("FIR_AGTRN_BATCH_DTL批次明細檔查無資料，請洽系統人員。");
				} else {
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td>檔案名稱</td>");
					sb.append("<td>檔案狀態</td>");
					sb.append("<td>原始資料筆數</td>");
					sb.append("<td>成功筆數</td>");
					sb.append("<td>失敗筆數</td>");
					sb.append("</tr>");
					List<FirAgtrnBatchMain> firAgtrnBatchMainList = (List<FirAgtrnBatchMain>) mainResult.getResObject();
					for (FirAgtrnBatchMain firAgtrnBatchMain : firAgtrnBatchMainList) {
						sb.append("<tr>");
						sb.append("<td>" + firAgtrnBatchMain.getFilename() + "</td>");
						String fileStatus = "";
						if (firAgtrnBatchMain.getTransStatus().equals("Y")) {
							fileStatus = "正常";
						} else if (firAgtrnBatchMain.getTransStatus().equals("F")) {
							fileStatus = "失敗";
						} else {
							fileStatus = "檔案無資料";
						}
						sb.append("<td>" + fileStatus + "</td>");
						sb.append("<td>" + firAgtrnBatchMain.getDataqtyT() + "</td>");
						sb.append("<td>" + firAgtrnBatchMain.getDataqtyS() + "</td>");
						sb.append("<td>" + firAgtrnBatchMain.getDataqtyF() + "</td>");
						sb.append("</tr>");
					}
					sb.append("</table>");

					sb.append("<p>本次處理明細如下：</p>");
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td>序號</td>");
					sb.append("<td>受理編號</td>");
					sb.append("<td>續保保單號</td>");
					sb.append("<td>資料狀態</td>");
					sb.append("<td>資料檢核異常訊息</td>");
					sb.append("<td>資料檢核提示訊息</td>");
					sb.append("</tr>");
					List<FirAgtrnBatchDtl> firAgtrnBatchDtlList = (List<FirAgtrnBatchDtl>) dtlResult.getResObject();
					String dataStatus = "";
					for (FirAgtrnBatchDtl firAgtrnBatchDtl : firAgtrnBatchDtlList) {
						sb.append("<tr>");
						sb.append("<td>" + firAgtrnBatchDtl.getBatchSeq() + "</td>");
						sb.append("<td>" + firAgtrnBatchDtl.getOrderseq() + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtrnBatchDtl.getOldpolicyno()) + "</td>");
						if (firAgtrnBatchDtl.getDataStatus().equals("0")) {
							dataStatus = "未處理";
						} else if (firAgtrnBatchDtl.getDataStatus().equals("1")) {
							dataStatus = "資料驗證失敗";
						} else {
							dataStatus = "寫入APS暫存檔成功";
						}
						sb.append("<td>" + dataStatus + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtrnBatchDtl.getCheckErrMsg()) + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtrnBatchDtl.getCheckWarnMsg()) + "</td>");
						sb.append("</tr>");
					}
					sb.append("</table>");
				}
			} else if (status.equals("N")) {
				sb.append("<p>執行狀態：無檔案 </p>");
			} else {// status = "F"
				sb.append("<p>執行狀態：失敗</p>");
				sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
			}
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("FBRN sendEmail Exception", e);
		}
		return tmpMsg.toString();
	}
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 start**/
	private Map<String, Object> compareData(String batchNo, Date excuteTime, String userId)throws Exception{
		Result result = firAgtrnBatchMainService.findBatchMainForBotrnIntoCore();
		List<Aps055BotDetailVo> firAgtTocoreList = (List<Aps055BotDetailVo>) result.getResObject();
		Map<String, Object>  params= new HashMap<>();
		Map<String, Integer>  dataNum= new HashMap<>();
		Map<String, Object>  returnResult= new HashMap<>();
		Map<String, String>  errData= new HashMap<>();
		int totalNum=0;
		int successNum=0;
		int unHandleNum=0;
		int failNum=0;
		StringBuilder  checkMsg=new StringBuilder(); 
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd");
		//mantis：FIR0620，處理人員：BJ085，需求單編號：FIR0620 住火-台銀續保作業
		if(firAgtTocoreList == null || firAgtTocoreList.isEmpty()){
			returnResult.put(OUTSTATUS, "N");
		}else{
			
			totalNum=firAgtTocoreList.size();
			for(Aps055BotDetailVo vo : firAgtTocoreList){
				try{
					FirAgtrnBatchDtl firAgtrnBatchDtl=new FirAgtrnBatchDtl();
					FirAgtTocoreMain firAgtTocoreMain=new FirAgtTocoreMain();
					checkMsg.setLength(0);
					params.put("orderseq", vo.getOrderseq());
					params.put("sortBy", "oid");
					Result fdResult = firAgtBotFdService.findFirAgtBotFdByParams(params);
					ArrayList<FirAgtBotFd> list=(ArrayList)fdResult.getResObject();
					FirAgtBotFd firAgtBotFd=null;					
					if(list!=null){
						firAgtBotFd=list.get(0);
					}else{
						unHandleNum++;
						continue;
					}
					if(firAgtBotFd!=null&&"Y".equals(firAgtBotFd.getCompareFlag())){
						firAgtrnBatchDtl.setDataStatus("2");
						firAgtrnBatchDtl.setIupdate(userId);
						firAgtrnBatchDtl.setDupdate(new Date());
						firAgtrnBatchDtl.setBatchSeq(vo.getBatchSeq());
						firAgtrnBatchDtl.setBatchNo(vo.getBatchNo());
						
						firAgtTocoreMain.setLoanaccount1(firAgtBotFd.getLoanaccount());
						firAgtTocoreMain.setBatchSeq(vo.getBatchSeq());
						firAgtTocoreMain.setBatchNo(vo.getBatchNo());
						firAgtTocoreMain.setIupdate(userId);
						firAgtTocoreMain.setDupdate(new Date());
						
						firAgtTocoreMainService.updateFirAgtTocoreMain(firAgtTocoreMain);
						firAgtrnBatchDtlService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl);
						
					}else if(firAgtBotFd!=null&&"N".equals(firAgtBotFd.getCompareFlag())){
						
						//檢核開始
						//1.簽署日期
						if(firAgtBotFd.getFdDate()!=null){
							
						}else{
							checkMsg.append("簽署日期為空或異常;");
						}
						//2.火險生效日
						if(!StringUtil.isSpace(vo.getAmountF())){
							if(Integer.parseInt(vo.getAmountF()) > 0){
								if(firAgtBotFd.getStartdateF()==null){
									checkMsg.append("續保資料有火險但簽署檔火險生效日異常;");
								}else{
									if(!vo.getStartdate().equals(sdf.format(firAgtBotFd.getStartdateF()))){
										checkMsg.append("續保資料生效日"+vo.getStartdate()+"與簽署檔火險生效日"+sdf.format(firAgtBotFd.getStartdateF())+"不同，已替換為台銀資料;");
										firAgtTocoreMain.setStartdate(firAgtBotFd.getStartdateF());
									}
								}
							}
						}
						//3.地震生效日 
						if(Integer.parseInt(vo.getAmountQ())>0){
							if(firAgtBotFd.getStartdateQ()==null){
								checkMsg.append("續保資料有地震險但簽署檔地震生效日異常;");
							}else{
								if(!vo.getStartdate().equals(sdf.format(firAgtBotFd.getStartdateQ()))){
									checkMsg.append("續保資料生效日"+vo.getStartdate()+"與簽署檔火險生效日"+sdf.format(firAgtBotFd.getStartdateQ())+"不同，已替換為台銀資料;");
									//跟火險同一欄位，如果沒有火險才塞
									if(firAgtTocoreMain.getStartdate()==null){
										firAgtTocoreMain.setStartdate(firAgtBotFd.getStartdateQ());
									}
								}
							}
						}
						//4.委扣帳號
						if(StringUtil.isSpace(vo.getAccountno())||!vo.getAccountno().equals(firAgtBotFd.getAccountNo())){
							checkMsg.append("續保資料扣款帳號"+vo.getAccountno()+"與簽署檔委扣帳號 "+firAgtBotFd.getAccountNo()+"不同，已替換為台銀資料;");
							firAgtTocoreMain.setAccountno(firAgtBotFd.getAccountNo());
						}
						//5.分行代號
						if(StringUtil.isSpace(vo.getExtracomcode())||vo.getExtracomcode().length()<7||!vo.getExtracomcode().substring(3, 6).equals(firAgtBotFd.getBranchNo())){
							checkMsg.append("續保資料分行代號"+vo.getExtracomcode()+"與簽署檔分行代號"+firAgtBotFd.getBranchNo()+"不一致;");
						}
						//6.招攬員編
						if(StringUtil.isSpace(vo.getTemp2())||!vo.getTemp2().equals(firAgtBotFd.getBankSales())){
							checkMsg.append("續保資料業務員員編"+vo.getTemp2()+"與簽署檔招攬員編"+firAgtBotFd.getBankSales()+"不同，已替換為台銀資料;");
							firAgtTocoreMain.setTemp2(firAgtBotFd.getBankSales());
						}
						//7.是否約定續保
						if(StringUtil.isSpace(firAgtBotFd.getIsAutoRenew())){
							if(!firAgtBotFd.getIsAutoRenew().equals(vo.getIsAutoRenew())){
								checkMsg.append("續保資料續保約定註記"+vo.getIsAutoRenew()+"與簽署檔"+firAgtBotFd.getIsAutoRenew()+"不同，已替換為台銀資料;");	
								firAgtTocoreMain.setIsAutoRenew(firAgtBotFd.getIsAutoRenew());
							}
						}
						//8.火險保額
						if(firAgtBotFd.getAmountF()!=null){
							if(!firAgtBotFd.getAmountF().equals(Long.parseLong(vo.getAmountF()))){
								checkMsg.append("續保資料火險保額"+vo.getAmountF()+"與簽署檔"+firAgtBotFd.getAmountF()+"不同，已替換為台銀資料;");
								firAgtTocoreMain.setAmountF(String.valueOf(firAgtBotFd.getAmountF()));
							}
						}
						//9.地震險保額
						if(firAgtBotFd.getAmountQ()!=null){
							if(!firAgtBotFd.getAmountQ().equals(Long.parseLong(vo.getAmountQ()))){
								checkMsg.append("續保資料地震險保額"+vo.getAmountQ()+"與簽署檔"+firAgtBotFd.getAmountQ()+"不同，已替換為台銀資料;");
								firAgtTocoreMain.setAmountQ(String.valueOf(firAgtBotFd.getAmountQ()));
							}
						}
						//10.放款帳號
						if(firAgtBotFd.getLoanaccount()!=null){
							firAgtTocoreMain.setLoanaccount1(firAgtBotFd.getLoanaccount());
						}
						
						if(checkMsg.length()==0){
							firAgtrnBatchDtl.setDataStatus("2");
							firAgtBotFd.setCompareFlag("Y");
							
						}else{
							firAgtrnBatchDtl.setDataStatus("C");
							firAgtBotFd.setCompareFlag("E");
							firAgtBotFd.setCompareResult(checkMsg.toString());
							
						}
						firAgtrnBatchDtl.setIupdate(userId);
						firAgtrnBatchDtl.setDupdate(new Date());
						firAgtrnBatchDtl.setBatchSeq(vo.getBatchSeq());
						firAgtrnBatchDtl.setBatchNo(vo.getBatchNo());
						
						firAgtBotFd.setCompareBatchNo(batchNo);	
						firAgtBotFd.setCompareUser(userId);
						firAgtBotFd.setCompareTime(new Date());
						
						firAgtTocoreMain.setBatchSeq(vo.getBatchSeq());
						firAgtTocoreMain.setBatchNo(vo.getBatchNo());
						firAgtTocoreMain.setIupdate(userId);
						firAgtTocoreMain.setDupdate(new Date());
						
						botRenewalFileService.updateAgtTocoreMainAndAgtrnBatchDtlAndAgtBotFd(firAgtTocoreMain, firAgtrnBatchDtl, firAgtBotFd);
						successNum++;
					}
				}catch(Exception e){
					logger.error("FD檔比對轉核心檢核錯誤:"+e);
					errData.put(vo.getOrderseq(),e.toString());
					failNum++;
					
				}
				
			}
			dataNum.put("totalNum", totalNum);
			dataNum.put("unHandleNum",unHandleNum);
			dataNum.put("successNum", successNum);
			dataNum.put("failNum",failNum);
			if(failNum>0){
				returnResult.put(OUTSTATUS, "F");		
				returnResult.put(OUTMSG, "儲存失敗");		
			}else{
				returnResult.put(OUTSTATUS, "S");		
				returnResult.put(OUTMSG, "");		
			}
			returnResult.put("dataNum",dataNum);
			returnResult.put("errData",errData);
		}
		return returnResult;
	}
	
	
	@SuppressWarnings("unchecked")
	private String sendFdEmail(String batchNo, Date excuteTime, String status, String errMsg, String programId,Map<String,Integer> dataNum,Map<String,String> errData) {
		Mailer mailer = new Mailer();
		StringBuilder tmpMsg = new StringBuilder();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("prgId", programId );
			Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
			if (result.getResObject() == null) {
				return "無法取得FIR_BATCH_INFO資料，無法寄送MAIL";
			}
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
			String subject = firBatchInfo.getMailSubject() + "-" + sdf.format(new Date());
			String mailTo = firBatchInfo.getMailTo();
//			mailTo = "cd094@ctbcins.com";
			String mailCc = firBatchInfo.getMailCc();
//			mailCc="";

			StringBuilder sb = new StringBuilder();
			sb.append("<p>批次號碼：" + batchNo + "</p>");
			sb.append("<p>轉檔時間：" + sdf.format(excuteTime) + "</p>");
			
			if("S".equals(status)){
				sb.append("<p>執行狀態：完成</p>");
			}else if("F".equals(status)){
				sb.append("<p>執行狀態：異常</p>");
			}
			sb.append("<p>錯誤訊息:"+errMsg+"</p>");
			sb.append("<p>續保資料鎖定需比對筆數:"+dataNum.get("totalNum")+"</p>");
			sb.append("<p>比對簽署檔完成資料筆數:"+dataNum.get("successNum")+"</p>");
			sb.append("<p>比對簽署檔失敗資料筆數:"+dataNum.get("failNum")+"</p>");
			sb.append("<p>未存在簽署檔資料筆數:"+dataNum.get("unHandleNum")+"</p>");
			if(dataNum.get("successNum")!=0){
				sb.append("<p>本次處理簽署檔如下:</p>");
				params.clear();
				params.put("compareBatchNo", batchNo);
				Result fdResult = firAgtBotFdService.findFirAgtBotFdByParams(params);
				
				if (fdResult.getResObject() == null) {
					tmpMsg.append("FIR_AGT_BOT_FD火險保經代臺銀FD簽署檔查無資料，請洽系統人員。");
				} else {
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td>受理編號</td>");
					sb.append("<td>簽署日期</td>");
					sb.append("<td>資料比對註記</td>");
					sb.append("<td>資料比對結果</td>");
					sb.append("</tr>");
					List<FirAgtBotFd> firAgtrnFdList = (List<FirAgtBotFd>) fdResult.getResObject();
					for (FirAgtBotFd firAgtBotFd : firAgtrnFdList) {
						sb.append("<tr>");
						sb.append("<td>" + firAgtBotFd.getOrderseq() + "</td>");
						if(firAgtBotFd.getFdDate()!=null){
							sb.append("<td>" + sdf2.format(firAgtBotFd.getFdDate()) + "</td>");
						}else{
							sb.append("<td>"+"</td>");
						}
						String flagStatus = "";
						if ("Y".equals(firAgtBotFd.getCompareFlag())) {
							flagStatus = "比對完成";
						} else if ("N".equals(firAgtBotFd.getCompareFlag())) {
							flagStatus = "比對未完成";
						} else if("E".equals(firAgtBotFd.getCompareFlag())){
							flagStatus = "比對或檢核異常";
						} else {
							flagStatus = "未定義";
						}
						sb.append("<td>" + flagStatus + "</td>");
						
						if(firAgtBotFd.getCompareResult()!=null){
							sb.append("<td>" + firAgtBotFd.getCompareResult()+ "</td>");
						}else{
							sb.append("<td>"+ "</td>");
						}
						sb.append("</tr>");
					}
					sb.append("</table>");
				}
			}
			if(dataNum.get("failNum")!=0){
				sb.append("<table border=1 style='border-collapse: collapse;'>");
				sb.append("<tr bgcolor='#70bbd9'>");
				sb.append("<td>受理編號</td>");
				sb.append("<td>錯誤訊息</td>");
				sb.append("</tr>");
				for(String key:errData.keySet()){
					sb.append("<tr>");
					sb.append("<td>" + key + "</td>");
					sb.append("<td>" + errData.get(key) + "</td>");
					sb.append("</tr>");
				}
				sb.append("</table>");
			}
			
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("BOTFD sendEmail Exception", e);
		}
		return tmpMsg.toString();
	}
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 end**/

	private void updateFirBatchLog(String status, String outMsg, String userId, BigDecimal oid) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setOid(oid);
		botRenewalFileService.updateFirBatchLog(status, outMsg, userId, firBatchLog);
	}

	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public PrpdAgreementService getPrpdAgreementService() {
		return prpdAgreementService;
	}

	public void setPrpdAgreementService(PrpdAgreementService prpdAgreementService) {
		this.prpdAgreementService = prpdAgreementService;
	}

	public RfrcodeService getRfrcodeService() {
		return rfrcodeService;
	}

	public void setRfrcodeService(RfrcodeService rfrcodeService) {
		this.rfrcodeService = rfrcodeService;
	}

	public FirAgtrnBatchMainService getFirAgtrnBatchMainService() {
		return firAgtrnBatchMainService;
	}

	public void setFirAgtrnBatchMainService(FirAgtrnBatchMainService firAgtrnBatchMainService) {
		this.firAgtrnBatchMainService = firAgtrnBatchMainService;
	}

	public FirAgtrnBatchDtlService getFirAgtrnBatchDtlService() {
		return firAgtrnBatchDtlService;
	}

	public void setFirAgtrnBatchDtlService(FirAgtrnBatchDtlService firAgtrnBatchDtlService) {
		this.firAgtrnBatchDtlService = firAgtrnBatchDtlService;
	}

	public FirVerifyDatasService getFirVerifyDatasService() {
		return firVerifyDatasService;
	}

	public void setFirVerifyDatasService(FirVerifyDatasService firVerifyDatasService) {
		this.firVerifyDatasService = firVerifyDatasService;
	}

	public BotRenewalFileService getBotRenewalFileService() {
		return botRenewalFileService;
	}

	public void setBotRenewalFileService(BotRenewalFileService botRenewalFileService) {
		this.botRenewalFileService = botRenewalFileService;
	}

	public FirSpService getFirSpService() {
		return firSpService;
	}

	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
	}

	public FirAgtTocoreMainService getFirAgtTocoreMainService() {
		return firAgtTocoreMainService;
	}

	public void setFirAgtTocoreMainService(FirAgtTocoreMainService firAgtTocoreMainService) {
		this.firAgtTocoreMainService = firAgtTocoreMainService;
	}

	public FirAgtTocoreInsuredService getFirAgtTocoreInsuredService() {
		return firAgtTocoreInsuredService;
	}

	public void setFirAgtTocoreInsuredService(FirAgtTocoreInsuredService firAgtTocoreInsuredService) {
		this.firAgtTocoreInsuredService = firAgtTocoreInsuredService;
	}

	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 start**/
	public FirAgtBotFdService getFirAgtBotFdService() {
		return firAgtBotFdService;
	}

	public void setFirAgtBotFdService(FirAgtBotFdService firAgtBotFdService) {
		this.firAgtBotFdService = firAgtBotFdService;
	}
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 end**/
	

}
