package com.tlg.aps.bs.firGenRenewListService.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firGenRenewListService.FirGenRenewListService;
import com.tlg.aps.bs.firGenRenewListService.GenRenewListService;
import com.tlg.aps.bs.firVerifyService.FirVerifyDatasService;
import com.tlg.aps.vo.FirAmountWsParamVo;
import com.tlg.aps.vo.FirInsPremVo;
import com.tlg.aps.vo.FirPremWsParamVo;
import com.tlg.aps.vo.FirRenewListForPremVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;
import com.tlg.prpins.entity.FirRenewList;
import com.tlg.prpins.entity.FirRenewListDtl;
import com.tlg.prpins.entity.Prpcpayment;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirRenewListDtlService;
import com.tlg.prpins.service.FirRenewListService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.prpins.service.PrpcpaymentService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/* mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirGenRenewListServiceImpl implements FirGenRenewListService {

	private static final Logger logger = Logger.getLogger(FirGenRenewListServiceImpl.class);
	private ConfigUtil configUtil;
	private FirSpService firSpService;
	private FirBatchInfoService firBatchInfoService;
	private FirRenewListService firRenewListService;
	private FirRenewListDtlService firRenewListDtlService;
	private GenRenewListService genRenewListService;
	private PrpcpaymentService prpcpaymentService;
	private FirVerifyDatasService firVerifyDatasService;

	@Override
	public Result runToGenerateList(String rnYymm, Date excuteTime, String userId, String programId)
			throws SystemException, Exception {
		StringBuilder sb = new StringBuilder();
		String tmpStatus = "";
		String tmpMsg = "";
		String tmpBatchNo = "";
		String mailMsg = "";
		String tmpDataQty = "";
		String tmpQcamtFlag = "";
		String tmpFcamtFlag = "";

		// 檢核傳入參數
		if (StringUtil.isSpace(rnYymm)) {
			sb.append("續保年月無內容值或內容值錯誤。(FIR_RENEW_LIST)");
		}
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值或內容值錯誤。(FIR_RENEW_LIST)");
		}
		if (excuteTime == null) {
			sb.append("執行時間無內容值或內容值錯誤。(FIR_RENEW_LIST)");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值或內容值錯誤。(FIR_RENEW_LIST)");
		}

		// 判斷排程是否可以執行
		Map<String, String> params = new HashMap<>();
		tmpBatchNo = "FRN_" + new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		if (StringUtil.isSpace(sb.toString())) {
			params.put("prgId", "FIR_RENEW_LIST_STATUS");
			Result result = firBatchInfoService.findFirBatchInfoByUK(params);
			if (result.getResObject() != null && ((FirBatchInfo) result.getResObject()).getMailTo().equals("N")) {
				tmpStatus = "S";
				tmpMsg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			} else {
				tmpStatus = "1";
			}
		} else {
			tmpStatus = "F";
			tmpMsg = "接收參數." + sb.toString();
		}

		// 新增FIR_BATCH_LOG批次程式執行記錄檔
		Result result = genRenewListService.insertFirBatchLog(excuteTime, userId, programId, tmpStatus, tmpMsg,
				tmpBatchNo);
		
		if (tmpStatus.equals("S")) {
			return this.getReturnResult("查詢狀態為N，不執行排程");
		}
		if (tmpStatus.equals("F")) {
			return this.getReturnResult(tmpMsg);
		}
		
		FirBatchLog firBatchLog = null;
		if (result.getResObject() != null) {
			firBatchLog = (FirBatchLog) result.getResObject();
		}

		if ("1".equals(tmpStatus)) {
			// 呼叫第一支SP產生住火應續件清單
			int returnValue = this.callSp1(tmpBatchNo, userId, rnYymm);
			if (returnValue != 0) {
				tmpStatus = "F";
				tmpMsg = "執行SP失敗(SP_FIR_RENEW_LIST)";
				mailMsg = sendEmail(tmpBatchNo, "", "", "", excuteTime, tmpStatus, tmpMsg, programId);
				return this.getReturnResult(tmpMsg);
			}
			
			//呼叫第二支SP更新續件資料及重算保額、保費
			Map<String, String> returnData = updateRenewList(tmpBatchNo, userId);
			tmpStatus = returnData.get("tmpStatus");
			tmpMsg = returnData.get("tmpMsg");
			if("S".equals(tmpStatus)) {
				tmpDataQty = returnData.get("tmpDataQty");
				tmpQcamtFlag = returnData.get("tmpQcamtFlag");
				tmpFcamtFlag = returnData.get("tmpFcamtFlag"); 						
			}
			
		} else if ("F".equals(tmpStatus)) {
			mailMsg = sendEmail(tmpBatchNo, "", "", "", excuteTime, tmpStatus, tmpMsg, programId);
		}
		
		mailMsg = sendEmail(tmpBatchNo, tmpDataQty, tmpFcamtFlag, tmpQcamtFlag, excuteTime, tmpStatus,
				tmpMsg, programId);
		genRenewListService.updateFirBatchLog(tmpStatus, tmpMsg, userId, firBatchLog);

		logger.info("mailMsg =" + mailMsg);
		if (!StringUtil.isSpace(mailMsg)) {
			genRenewListService.updateFirBatchLog("F", mailMsg, userId, firBatchLog);
		}
		return this.getReturnResult(StringUtil.isSpace(tmpMsg) ? "執行完成" : tmpMsg);
	}

	/**
	 * 提供產生應續件清單後呼叫SP_FIR_RENEW_LIST_UPDATE更新應續件資料，
	 * 及呼叫CWP計算保額保費並更新回應續件資料中
	 * @param tmpBatchNo
	 * @param userId
	 * @return Map returnData
	 * @throws SystemException,Exception
	 */
	@Override
	public Map<String, String> updateRenewList(String tmpBatchNo, String userId) throws SystemException, Exception {
		Map<String, String> returnData = new HashMap<>();
		
		Map<String, String> params = new HashMap<>();
		params.put("batchNo", tmpBatchNo);
		Result result = firRenewListService.findFirRenewListByUk(params);

		if (result.getResObject() != null) {
			FirRenewList firRenewList = (FirRenewList) result.getResObject();
			if (firRenewList.getDataqty() == null || firRenewList.getDataqty() == 0) {
				returnData.put("tmpStatus", "N");
				returnData.put("tmpMsg", "執行完成:無資料");
			}

			Integer tmpDataQty = firRenewList.getDataqty();
			String tmpQcamtFlag = firRenewList.getQcamtFlag();
			String tmpFcamtFlag = firRenewList.getFcamtFlag();

			int returnValue;
			try {
				returnValue = callSp2(tmpBatchNo, userId);
				if (returnValue != 0) {
					returnData.put("tmpStatus", "F");
					returnData.put("tmpMsg", "執行SP失敗(SP_FIR_RENEW_LIST)");
				}
			} catch (Exception e1) {
				returnData.put("tmpStatus", "F");
				returnData.put("tmpMsg", "執行SP失敗(SP_FIR_RENEW_LIST)");
			}

			result = firRenewListDtlService.findRenewListForPrem(params);
			List<FirRenewListForPremVo> rnList = (List<FirRenewListForPremVo>) result.getResObject();
			for (FirRenewListForPremVo rnData : rnList) {
				if ("1".equals(rnData.getRnType())) {
					params = new HashMap<>();
					params.put("policyno", rnData.getOldPolicyno());
					result = prpcpaymentService.findPrpcpaymentByParams(params);
					List<Prpcpayment> paymentList = (List<Prpcpayment>) result.getResObject();
					Prpcpayment prpcpayment = paymentList.get(0);

					FirRenewListDtl dtl = new FirRenewListDtl();
					String rnCreditno = prpcpayment.getRnCreditno();
					dtl.setOid(rnData.getOid());
					if(rnCreditno.length()>4) {
						dtl.setRnPaydata(rnCreditno.substring(rnCreditno.length() - 4, rnCreditno.length()));
					}
					if (!StringUtil.isSpace(rnData.getProposalno())) {
						dtl.setDataStatus("Y");
					}
					genRenewListService.updateFirRenewListDtl(dtl, userId);
				} else {
					String wkOid1 = "";
					String wkOid2 = "";
					StringBuilder wkRemark = new StringBuilder();
					BigDecimal wkQamt = new BigDecimal(0);
					BigDecimal wkFamt = new BigDecimal(0);
					BigDecimal wkQprem = new BigDecimal(0);
					BigDecimal wkFprem = new BigDecimal(0);
					BigDecimal wkFprem2 = new BigDecimal(0);
					BigDecimal wkFamt2 = new BigDecimal(0);
					if (null != rnData.getOldAmtF2()) {
						wkFamt2 = new BigDecimal(rnData.getOldAmtF2());
					}

					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

					// 判斷是否要重算火險或地震險保額，其中一項是Y就必須重算
					if ("Y".equals(tmpQcamtFlag) || "Y".equals(tmpFcamtFlag)) {
						try {
							FirAmountWsParamVo firWsParamVo = new FirAmountWsParamVo();
							firWsParamVo.setSourceType("FIR_RENEW_LIST");
							firWsParamVo.setSourceUser(userId);
							firWsParamVo.setCalcType("1");
							firWsParamVo.setCalcDate(sdf.format(rnData.getOldEnddate()));
							firWsParamVo.setChannelType("20");
							firWsParamVo.setPostcode(rnData.getAddresscode());
							firWsParamVo.setWallno(rnData.getWallmaterial());
							firWsParamVo.setRoofno(rnData.getRoofmaterial());
							firWsParamVo.setSumfloors(rnData.getSumfloors());
							firWsParamVo.setBuildarea(rnData.getBuildarea().toString());
							firWsParamVo.setDecorFee("0");

							/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 start */
							//因應造價表調整，外銀保額需取到萬元，中信件保額則以前一年度為主，中信件才判斷保額取到元或萬元並傳入amountFlag
							// 判斷保額要計算到萬元或元，地震、火險分開
							String oldAmtQ = "";
							String oldAmtF = "";
							String qYuan = "0000";
							String fYuan = "0000";
							if (null != rnData.getOldAmtQ()) {
								oldAmtQ = rnData.getOldAmtQ().toString();
								// 判斷保額有到萬元
								if(oldAmtQ.length() > 4 && "2".equals(rnData.getRnType())) {
									qYuan = oldAmtQ.substring(oldAmtQ.length() - 4, oldAmtQ.length());
								}
							}
							if (null != rnData.getOldAmtF1()) {
								oldAmtF = rnData.getOldAmtF1().toString();
								// 判斷保額有到萬元
								if(oldAmtF.length() > 4 && "2".equals(rnData.getRnType())) {
									fYuan = oldAmtF.substring(oldAmtF.length() - 4, oldAmtF.length());
								}
							}
							/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 end */

							if ("0000".equals(qYuan) && !"0000".equals(fYuan)) {
								firWsParamVo.setAmountFlag("F");
							} else if (!"0000".equals(qYuan) && "0000".equals(fYuan)) {
								firWsParamVo.setAmountFlag("D");
							} else if (!"0000".equals(qYuan) && !"0000".equals(fYuan)) {
								firWsParamVo.setAmountFlag("A");
							}

							FirPremcalcTmp firPremcalcTmp = firVerifyDatasService.firAmountCal(firWsParamVo);
							if (firPremcalcTmp != null && "Y".equals(firPremcalcTmp.getReturnType())) {
								wkOid1 = firPremcalcTmp.getOid().toString();
								if ("Y".equals(tmpQcamtFlag) && !StringUtil.isSpace(oldAmtQ)) {
									wkQamt = firPremcalcTmp.getEqAmt();
								}
								if ("Y".equals(tmpFcamtFlag) && !StringUtil.isSpace(oldAmtF)) {
									wkFamt = firPremcalcTmp.getFsAmt();
								}
							} else {
								wkRemark.append("外銀件-保額計算WS無回應或執行失敗。");
								if (firPremcalcTmp != null && "N".equals(firPremcalcTmp.getReturnType())) {
									wkRemark.append(firPremcalcTmp.getReturnMsg());
								}
							}
						} catch (Exception e) {
							logger.error(e);
							wkRemark.append("外銀件-保額計算WS無回應或執行失敗。");
						}
					} else {
						if (null != rnData.getOldAmtQ()) {
							wkQamt = new BigDecimal(rnData.getOldAmtQ());
						}
						if (null != rnData.getOldAmtF1()) {
							wkFamt = new BigDecimal(rnData.getOldAmtF1());
						}
					}
					
					/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 start */
					if(rnData.getAmtF1()!=null && 
						("I99065".equals(rnData.getBusinessnature())||"I00107".equals(rnData.getBusinessnature())
								||"I99004".equals(rnData.getBusinessnature())||"I99080".equals(rnData.getBusinessnature())
								||"I00006".equals(rnData.getBusinessnature()))) {
						wkFamt = new BigDecimal(rnData.getAmtF1());
					}
					/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 end */

					// 保費重算
					try {
						FirPremWsParamVo firPremWsParamVo = new FirPremWsParamVo();
						firPremWsParamVo.setSourceType("FIR_RENEW_LIST");
						firPremWsParamVo.setSourceUser(userId);
						firPremWsParamVo.setCalcType("2");
						firPremWsParamVo.setCalcDate(sdf.format(rnData.getOldEnddate()));
						firPremWsParamVo.setChannelType("20");

						ArrayList<FirInsPremVo> firInsPremVoList = new ArrayList<>();
						// 地震險保費
						if (wkQamt.compareTo(new BigDecimal(0)) > 0) {
							FirInsPremVo firInsPremVoQ = new FirInsPremVo();
							firInsPremVoQ.setRiskcode("F02");
							firInsPremVoQ.setKindcode("FR2");
							firInsPremVoQ.setPara01(wkQamt.toString());
							firInsPremVoList.add(firInsPremVoQ);
						}

						// 火險保費(不動產)
						if (wkFamt.compareTo(new BigDecimal(0)) > 0) {
							FirInsPremVo firInsPremVoF = new FirInsPremVo();
							firInsPremVoF.setRiskcode("F02");
							firInsPremVoF.setKindcode("FR3");
							firInsPremVoF.setParaType("1");
							firInsPremVoF.setPara01(wkFamt.toString());
							firInsPremVoF.setPara02(rnData.getWallmaterial());
							firInsPremVoF.setPara03(rnData.getRoofmaterial());
							firInsPremVoF.setPara04(rnData.getSumfloors());
							firInsPremVoF.setPara05("N");
							firInsPremVoList.add(firInsPremVoF);
						}

						// 火險保費(動產)
						if (wkFamt2.compareTo(new BigDecimal(0)) > 0) {
							FirInsPremVo firInsPremVoF = new FirInsPremVo();
							firInsPremVoF.setRiskcode("F02");
							firInsPremVoF.setKindcode("FR3");
							firInsPremVoF.setParaType("1");
							firInsPremVoF.setPara01(wkFamt2.toString());
							firInsPremVoF.setPara02(rnData.getWallmaterial());
							firInsPremVoF.setPara03(rnData.getRoofmaterial());
							firInsPremVoF.setPara04(rnData.getSumfloors());
							firInsPremVoF.setPara05("N");
							firInsPremVoList.add(firInsPremVoF);
						}

						firPremWsParamVo.setInsPremList(firInsPremVoList);
						FirPremcalcTmp firPremcalcTmp = firVerifyDatasService.firPremCal(firPremWsParamVo);
						if (firPremcalcTmp != null && "Y".equals(firPremcalcTmp.getReturnType())) {
							wkOid2 = firPremcalcTmp.getOid().toString();
							ArrayList<FirPremcalcTmpdtl> premList = firPremcalcTmp.getFirPremcalcTmpdtlList();
							for (int i = 0; i < premList.size(); i++) {
								FirPremcalcTmpdtl firPremcalcTmpdtl = premList.get(i);
								if ("FR2".equals(firPremcalcTmpdtl.getKindcode())) {
									wkQprem = firPremcalcTmpdtl.getPremium();
								}
								if ("FR3".equals(firPremcalcTmpdtl.getKindcode())
										&& wkFamt.toString().equals(firPremcalcTmpdtl.getPara01())) {
									wkFprem = firPremcalcTmpdtl.getPremium();
								}
								if ("FR3".equals(firPremcalcTmpdtl.getKindcode())
										&& wkFamt2.toString().equals(firPremcalcTmpdtl.getPara01())) {
									wkFprem2 = firPremcalcTmpdtl.getPremium();
								}
							}
						} else {
							wkRemark.append("外銀件-保費計算WS無回應或執行失敗。");
							if (firPremcalcTmp != null && "N".equals(firPremcalcTmp.getReturnType())) {
								wkRemark.append(firPremcalcTmp.getReturnMsg());
							}
						}
					} catch (Exception e) {
						logger.error(e);
						wkRemark.append("外銀件-保費計算WS無回應或執行失敗。");
					}
					FirRenewListDtl dtl = new FirRenewListDtl();
					dtl.setOid(rnData.getOid());
					dtl.setOidFirPremcalcTmp(StringUtil.isSpace(wkOid1) ? null : Long.parseLong(wkOid1));
					dtl.setAmtF1(wkFamt);
					dtl.setAmtF2(wkFamt2);
					dtl.setAmtQ(wkQamt);
					dtl.setOidFirPremcalcTmp2(StringUtil.isSpace(wkOid2) ? null : Long.parseLong(wkOid2));
					dtl.setPremF1(wkFprem);
					dtl.setPremF2(wkFprem2);
					dtl.setPremQ(wkQprem);
					dtl.setDataStatus("".equals(wkRemark.toString()) ? "Y" : null);
					dtl.setRemark(wkRemark.toString());
					genRenewListService.updateFirRenewListDtl(dtl, userId);
				}
			}
			returnData.put("tmpStatus", "S");
			returnData.put("tmpMsg", "");
			returnData.put("tmpDataQty", tmpDataQty.toString());
			returnData.put("tmpQcamtFlag", tmpQcamtFlag);
			returnData.put("tmpFcamtFlag", tmpFcamtFlag);
		}
		return returnData;
	}

	private int callSp1(String batchNo, String userId, String rnYymm) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("inBatchNo", batchNo);
		params.put("inUser", userId);
		params.put("inYymm", rnYymm);
		params.put("outResult", null);

		return firSpService.runSpFirRenewList(params);
	}

	private int callSp2(String batchNo, String userId) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("inBatchNo", batchNo);
		params.put("inUser", userId);
		params.put("outResult", null);

		return firSpService.runSpFirRenewListUpdate(params);
	}

	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo, String dataQty, String fcamtFlag, String qcamtFlag, Date excuteTime,
			String tmpStatus, String errMsg, String programId) throws Exception {
		Mailer mailer = new Mailer();
		StringBuilder tmpMsg = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		params.put("prgId", programId);
		Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
		if (result.getResObject() == null) {
			return "無法取得FIR_BATCH_INFO資料，無法寄送MAIL";
		}
		FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String subject = firBatchInfo.getMailSubject() + "-" + sdf.format(new Date());
		String mailTo = firBatchInfo.getMailTo();
		String mailCc = firBatchInfo.getMailCc();

		StringBuilder sb = new StringBuilder();
		sb.append("<p>批次號碼：" + batchNo + "</p>");
		sb.append("<p>執行時間：" + sdf.format(excuteTime) + "</p>");
		if (tmpStatus.equals("S")) {
			//mantis：FIR0570_1，處理人員：DP0706，需求單編號：FIR0570_1 住火_APS每月應續件產生排程START
			sb.append("<p>執行狀態：完成</p>");
			sb.append("<p>總筆數：" + dataQty + "</p>");
			sb.append("<p>非核心住火保額重算：" + fcamtFlag + "</p>");
			sb.append("<p>非核心地震保額重算：" + qcamtFlag + "</p>");
			sb.append("<p>明細如下：</p>");
			params.clear();
			params.put("batchNo", batchNo);
			params.put("dataStatus", "N");
			result = firRenewListDtlService.findRenewListForMail(params);

			if (result.getResObject() == null) {
				sb.append("<p>異常清單：無</p>");
			} else {
				sb.append("<table border=1 style='border-collapse: collapse;'>");
				sb.append("<tr bgcolor='#70bbd9'>");
				sb.append("<td>類型</td>");
				sb.append("<td>處理狀態</td>");
				sb.append("<td>件數</td>");
				sb.append("</tr>");
				List<FirRenewListForPremVo> dtlList = (List<FirRenewListForPremVo>) result.getResObject();
				for (FirRenewListForPremVo dtl : dtlList) {
					sb.append("<tr>");
					sb.append("<td>" + dtl.getRnType() + "</td>");
					sb.append("<td>" + dtl.getDataStatus() + "</td>");
					sb.append("<td>" + dtl.getNrec() + "</td>");
					sb.append("</tr>");
				}
				sb.append("</table>");
			}
			//mantis：FIR0570_1，處理人員：DP0706，需求單編號：FIR0570_1 住火_APS每月應續件產生排程END
		} else if (tmpStatus.equals("N")) {
			sb.append("<p>執行狀態：無資料 </p>");
		} else {
			sb.append("<p>執行狀態：失敗</p>");
			sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
		}
		mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo, "",
				mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		return tmpMsg.toString();
	}

	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public FirSpService getFirSpService() {
		return firSpService;
	}

	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public FirRenewListService getFirRenewListService() {
		return firRenewListService;
	}

	public void setFirRenewListService(FirRenewListService firRenewListService) {
		this.firRenewListService = firRenewListService;
	}

	public GenRenewListService getGenRenewListService() {
		return genRenewListService;
	}

	public void setGenRenewListService(GenRenewListService genRenewListService) {
		this.genRenewListService = genRenewListService;
	}

	public FirRenewListDtlService getFirRenewListDtlService() {
		return firRenewListDtlService;
	}

	public void setFirRenewListDtlService(FirRenewListDtlService firRenewListDtlService) {
		this.firRenewListDtlService = firRenewListDtlService;
	}

	public PrpcpaymentService getPrpcpaymentService() {
		return prpcpaymentService;
	}

	public void setPrpcpaymentService(PrpcpaymentService prpcpaymentService) {
		this.prpcpaymentService = prpcpaymentService;
	}

	public FirVerifyDatasService getFirVerifyDatasService() {
		return firVerifyDatasService;
	}

	public void setFirVerifyDatasService(FirVerifyDatasService firVerifyDatasService) {
		this.firVerifyDatasService = firVerifyDatasService;
	}
}
