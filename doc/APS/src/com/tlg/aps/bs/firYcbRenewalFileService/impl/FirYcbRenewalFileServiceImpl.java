package com.tlg.aps.bs.firYcbRenewalFileService.impl;

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

import com.tlg.aps.bs.firVerifyService.FirVerifyDatasService;
import com.tlg.aps.bs.firYcbRenewalFileService.FirYcbRenewalFileService;
import com.tlg.aps.bs.firYcbRenewalFileService.YcbRenewalFileService;
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
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;
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

/** mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程 **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirYcbRenewalFileServiceImpl implements FirYcbRenewalFileService {

	private static final Logger logger = Logger.getLogger(FirYcbRenewalFileServiceImpl.class);
	private ConfigUtil configUtil;
	
	private FirBatchInfoService firBatchInfoService;
	private FirAgtrnBatchMainService firAgtrnBatchMainService;
	private YcbRenewalFileService ycbRenewalFileService;
	private FirAgtTocoreMainService firAgtTocoreMainService;
	private PrpdAgreementService prpdAgreementService;
	private FirSpService firSpService;
	private FirVerifyDatasService firVerifyDatasService;
	private RfrcodeService rfrcodeService;
	private FirAgtTocoreInsuredService firAgtTocoreInsuredService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;

	private static final String OUTMSG = "msg";
	private static final String OUTSTATUS = "status";

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
		// 接收參數無值:新增執行記錄檔/發送郵件
		FirBatchLog firBatchLog;
		String batchNo = programId.substring(8) + "_" + new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		if (!StringUtil.isSpace(sb.toString())) {
			Result result = ycbRenewalFileService.insertFirBatchLog(excuteTime, userId, programId, "F", sb.toString(),
					batchNo);
			if (result.getResObject() != null) {
				firBatchLog = (FirBatchLog) result.getResObject();
				String mailMsg = sendEmail(firBatchLog.getBatchNo(), excuteTime, "F", sb.toString(), programId);
				if (!StringUtil.isSpace(mailMsg)) {
					ycbRenewalFileService.updateFirBatchLog("F", mailMsg, userId, firBatchLog);
				}
			}
			return this.getReturnResult("接收參數無值，結束排程");
		}
		
		//查詢FIR_BATCH_INFO設定檔
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

		// FIR_BATCH_INFO設定檔結果:新增執行記錄檔
		result = ycbRenewalFileService.insertFirBatchLog(excuteTime, userId, programId, status, msg, batchNo);
		firBatchLog = (FirBatchLog) result.getResObject();
		if ("S".equals(status)) {//排程結束，以下動作不再執行。(連信件都不發送)
			return this.getReturnResult("查詢狀態為N，不執行排程");
		}
		
		//續件資料處理
		if (result.getResObject() != null) {
			BigDecimal batchLogOid = firBatchLog.getOid();
			Map<String, String> returnData = null;
			returnData = callSp(batchNo, userId, rnDate);

			if ("N".equals(returnData.get(OUTSTATUS))) {//檔案無資料:更新執行記錄檔/EMAIL
				ycbRenewalFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
				updateFirBatchLog("F", returnData.get(OUTMSG), userId, batchLogOid);
				sendEmail(batchNo, excuteTime, returnData.get(OUTSTATUS), returnData.get(OUTMSG), programId);
				return this.getReturnResult("檔案無資料");
			} else if ("F".equals(returnData.get(OUTSTATUS))) {//執行產生元大續件資料SP失敗:更新執行記錄檔/EMAIL
				updateFirBatchLog("F", returnData.get(OUTMSG), userId, batchLogOid);
				sendEmail(batchNo, excuteTime, "F", returnData.get(OUTMSG), programId);
				return this.getReturnResult("執行產生元大續件資料SP失敗");
			} else if ("S".equals(returnData.get(OUTSTATUS))) {

				try {// 資料檢核
					returnData = dataReview(batchNo, userId);

				} catch (Exception e) {
					sendEmail(batchNo, excuteTime, "F", e.toString(), programId);
					updateFirBatchLog("F", e.toString(), userId, batchLogOid);
					return this.getReturnResult("資料檢核執行失敗");
				}

				if ("Y".equals(returnData.get("transStatus"))) {
					ycbRenewalFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
					sendEmail(batchNo, excuteTime, "S", returnData.get(OUTMSG), programId);
					updateFirBatchLog("S", returnData.get(OUTMSG), userId, batchLogOid);
				} else if ("E".equals(returnData.get("transStatus"))) {
					ycbRenewalFileService.updateFirAgtrnBatchMain(batchNo, userId, returnData);
					sendEmail(batchNo, excuteTime, "F", returnData.get(OUTMSG), programId);
					updateFirBatchLog("F", returnData.get(OUTMSG), userId, batchLogOid);
				}
			}

		}

		return this.getReturnResult("執行完成");
	}
	
	
	/**
	 * 呼叫SP_FIR_AGTRN_YCB
	 * @param batchNo
	 * @param userId
	 * @param rnDate
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map<String, String> callSp(String batchNo, String userId, String rnDate) throws SystemException, Exception {
		Map<String, Object> params = new HashMap<>();
		Map<String, String> returnData = new HashMap<>();
		int returnValue = 0;
		params.put("inBatchNo", batchNo);
		params.put("inRnYyyymm", rnDate);
		params.put("inUser", userId);
		params.put("outResult", null);

		returnValue = firSpService.runSpFirAgtrnYcb(params);

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
			returnData.put("msg", "執行產生元大續件資料SP失敗");
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
				

				// 取佣金率start
				params.clear();
				params.put("businesssourcecode", "I00006");
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
				
				//mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 20250310議題重啟
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
				firEqFundQueryVo.setSourceType("YCB-RN");
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
				firWsParamVo.setSourceType("YCBRN");
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
						//mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 20250310議題重啟
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
				firPremWsParamVo.setSourceType("YCBRN");
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
						//mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 20250310議題重啟
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

						//mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 20250310議題重啟
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
					ycbRenewalFileService.updateFirAgtTocore(main, firAgtTocoreInsuredToUpdateList);
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

				ycbRenewalFileService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl, batchNo, userId, main.getBatchSeq());
				
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
	

	private void updateFirBatchLog(String status, String outMsg, String userId, BigDecimal oid) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setOid(oid);
		ycbRenewalFileService.updateFirBatchLog(status, outMsg, userId, firBatchLog);
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

	public FirAgtrnBatchMainService getFirAgtrnBatchMainService() {
		return firAgtrnBatchMainService;
	}

	public void setFirAgtrnBatchMainService(FirAgtrnBatchMainService firAgtrnBatchMainService) {
		this.firAgtrnBatchMainService = firAgtrnBatchMainService;
	}

	public FirSpService getFirSpService() {
		return firSpService;
	}

	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
	}


	public YcbRenewalFileService getYcbRenewalFileService() {
		return ycbRenewalFileService;
	}


	public void setYcbRenewalFileService(YcbRenewalFileService ycbRenewalFileService) {
		this.ycbRenewalFileService = ycbRenewalFileService;
	}


	public FirAgtTocoreMainService getFirAgtTocoreMainService() {
		return firAgtTocoreMainService;
	}


	public void setFirAgtTocoreMainService(FirAgtTocoreMainService firAgtTocoreMainService) {
		this.firAgtTocoreMainService = firAgtTocoreMainService;
	}


	public PrpdAgreementService getPrpdAgreementService() {
		return prpdAgreementService;
	}


	public void setPrpdAgreementService(PrpdAgreementService prpdAgreementService) {
		this.prpdAgreementService = prpdAgreementService;
	}


	public FirVerifyDatasService getFirVerifyDatasService() {
		return firVerifyDatasService;
	}


	public void setFirVerifyDatasService(FirVerifyDatasService firVerifyDatasService) {
		this.firVerifyDatasService = firVerifyDatasService;
	}


	public RfrcodeService getRfrcodeService() {
		return rfrcodeService;
	}


	public void setRfrcodeService(RfrcodeService rfrcodeService) {
		this.rfrcodeService = rfrcodeService;
	}


	public FirAgtTocoreInsuredService getFirAgtTocoreInsuredService() {
		return firAgtTocoreInsuredService;
	}


	public void setFirAgtTocoreInsuredService(FirAgtTocoreInsuredService firAgtTocoreInsuredService) {
		this.firAgtTocoreInsuredService = firAgtTocoreInsuredService;
	}


	public FirAgtrnBatchDtlService getFirAgtrnBatchDtlService() {
		return firAgtrnBatchDtlService;
	}


	public void setFirAgtrnBatchDtlService(FirAgtrnBatchDtlService firAgtrnBatchDtlService) {
		this.firAgtrnBatchDtlService = firAgtrnBatchDtlService;
	}

	

}
