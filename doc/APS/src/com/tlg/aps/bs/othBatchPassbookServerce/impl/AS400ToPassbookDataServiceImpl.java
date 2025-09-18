package com.tlg.aps.bs.othBatchPassbookServerce.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.othBatchPassbookServerce.AS400ToPassbookDataService;
import com.tlg.aps.bs.othBatchPassbookServerce.OthBatchPassbookTranService;
import com.tlg.db2.entity.Com890wa;
import com.tlg.db2.entity.Com890wb;
import com.tlg.db2.service.Com890waService;
import com.tlg.db2.service.Com890wbService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.OthBatchPassbookKind;
import com.tlg.prpins.entity.OthBatchPassbookList;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 * @author bi086
 *
 */
@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class AS400ToPassbookDataServiceImpl implements AS400ToPassbookDataService {
	
	private static final Logger logger = Logger.getLogger(AS400ToPassbookDataServiceImpl.class);
	private ConfigUtil configUtil;
	private Com890waService com890waService;
	private Com890wbService com890wbService;
	private OthBatchPassbookTranService othBatchPassbookTranService;
	
	@Override
	public int insertOthBatchPassbookListFromAs400(String batchSerial,
			String exectutor) throws SystemException, Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer mailBody = new StringBuffer();
		int successCount = 0;
		int totalCount = 0;
		boolean sendData = true;
		
		try{
			while(sendData){
				
				ArrayList<Com890wa> com890waList = queryUnsend();
				if(com890waList == null){
					sendData = false;
					break;
				}
				totalCount = totalCount + com890waList.size();
				for(Com890wa com890wa : com890waList){
					
					Date executeDate = new Date();
					OthBatchPassbookList otbl = new OthBatchPassbookList();
					otbl.setProcType(com890wa.getWa02());
					otbl.setRiskcode(com890wa.getWa03());
					otbl.setPolicyno(com890wa.getWa04());
					otbl.setSendCode(com890wa.getWa05());
					otbl.setInscoCode(com890wa.getWa06());
					otbl.setDataStatus(com890wa.getWa07());
					otbl.setInsuredIdOld(com890wa.getWa08());
					if(!"A".equals(com890wa.getWa07())){
						otbl.setStartdateOld(formatter.parse(com890wa.getWa09()));
					}
					otbl.setApplicantId(com890wa.getWa10());
					otbl.setApplicantName(com890wa.getWa11());
					otbl.setInsuredId(com890wa.getWa12());
					otbl.setInsuredName(com890wa.getWa13());
					if(!"D".equals(com890wa.getWa07())){
						otbl.setStartdate(formatter.parse(com890wa.getWa14()));
						otbl.setEnddate(formatter.parse(com890wa.getWa15()));
					}
					otbl.setRiskcodeTii(com890wa.getWa16());
					otbl.setTarget(com890wa.getWa17());
					otbl.setUnderwriteenddate(formatter.parse(com890wa.getWa18()));
					otbl.setRemark(com890wa.getWa19());
					otbl.setTiiTmp(com890wa.getWa27());
					otbl.setEndorseno(com890wa.getWa28());
					otbl.setIcreate(exectutor);
					otbl.setDcreate(executeDate);
					otbl.setBatchNoNc(batchSerial);
					otbl.setDataSource("2");
					otbl.setEndortype(com890wa.getWa30());
					if(!StringUtil.isSpace(com890wa.getWa31()) && !"0".equals(com890wa.getWa31())){
						otbl.setValiddate(formatter.parse(com890wa.getWa31()));
					}
					if(!StringUtil.isSpace(com890wa.getWa32())){
						otbl.setKindCount(new BigDecimal(com890wa.getWa32()));
					}
					if(!StringUtil.isSpace(com890wa.getWa33())){
						otbl.setAs400Oid(new BigDecimal(com890wa.getWa33()));
					}
				
					try{
					
						Result result = othBatchPassbookTranService.insertOthBatchPassbookList(otbl);
						if(result.getResObject() == null){
							String errStr = "新增失敗：" + com890wa.getWa04() + " - " + com890wa.getWa28() + "-" + com890wa.getWa081();
							logger.info(errStr);
							//新增失敗
							com890wa.setWa29(errStr);
							mailBody.append(errStr + "<br>");
							continue;
						}
						
						//判斷是否有明細OID
						if(StringUtil.isSpace(com890wa.getWa33())){
							String errStr = "新增失敗：欄位WA33為空值(AS400_OID 對應 WB11) - " + com890wa.getWa04() + "-" + com890wa.getWa28() + "-" + com890wa.getWa081();
							logger.info(errStr);
							//新增失敗
							com890wa.setWa29(errStr);
							mailBody.append(errStr + "<br>");
							continue;
						}
//						//判斷是否有主檔的OID
						otbl = (OthBatchPassbookList)result.getResObject();
						if(otbl.getOid() == null){
							String errStr = "新增失敗：無法取得OID" + " - " + com890wa.getWa28() + "-" + com890wa.getWa081();
							logger.info(errStr);
							//新增失敗
							com890wa.setWa29(errStr);
							mailBody.append(errStr + "<br>");
							continue;
						}
						//撈險種資料
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("wb11", com890wa.getWa33());
						params.put("orderBySchdule", "Y");
						result = com890wbService.findCom890wbByParams(params);
						if(result.getResObject() == null){
							String errStr = "新增失敗：無法取得 COM890WB資料 - " + com890wa.getWa04() + "-" + com890wa.getWa28() + "-" + com890wa.getWa081();
							logger.info(errStr);
							//新增失敗
							com890wa.setWa29(errStr);
							mailBody.append(errStr + "<br>");
							continue;
						}
						ArrayList<Com890wb> com890wbList = (ArrayList<Com890wb>)result.getResObject();
						for(Com890wb com890wb:com890wbList){
							OthBatchPassbookKind kind = new OthBatchPassbookKind();
							
							kind.setListOid(otbl.getOid());
							kind.setProcType(com890wb.getWb03());
							kind.setRiskcode(com890wb.getWb04());
							kind.setPolicyno(com890wb.getWb05());
							kind.setEndorseno(com890wb.getWb06());
							kind.setKindsort(new BigDecimal(com890wb.getWb07()));
							kind.setKindcode(com890wb.getWb08());
							kind.setKindname(com890wb.getWb09());
							kind.setAmountText(com890wb.getWb10());
							kind.setAs400Oid(new BigDecimal(com890wb.getWb11()));
							result = this.othBatchPassbookTranService.insertOthBatchPassbookKind(kind);
							if(result.getResObject() == null){
								String errStr = "新增明細失敗：" + com890wa.getWa04() + " - " + com890wa.getWa28() + "-" + com890wa.getWa081() + ",Wb11：" + com890wb.getWb11();
								logger.info(errStr);
								//新增失敗
								com890wa.setWa29(errStr);
								mailBody.append(errStr + "<br>");
								continue;
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
						String errStr = "新增異常：" + e.getMessage() + "：" +com890wa.getWa04() + "-" + com890wa.getWa28() + "-" + com890wa.getWa081();
						logger.info(errStr);
						com890wa.setWa29(errStr);
						mailBody.append(errStr + "<br>");
					}finally{
						com890wa.setWa22(batchSerial);
						com890wa.setWa23(exectutor);
						com890wa.setWa24(formatter2.format(executeDate));
						Result result = com890waService.updateCom890waForBatch(com890wa);
						if(result.getResObject() == null){
							String errStr = "更新AS400 Com890wa發生異常：" + com890wa.getWa04() + "-" + com890wa.getWa28() + "-" + com890wa.getWa081();
							logger.info(errStr);
							mailBody.append(errStr + "<br>");
						}
						//新增無異常的才會+1
						if(StringUtil.isSpace(com890wa.getWa29())){
							successCount++;
						}
					}
				}
			}
		}catch (SystemException se) {
			logger.error(se);
//			se.printStackTrace();
			mailBody.append(se.getMessage());
			throw se;
		}catch (Exception e) {
			logger.error(e);
//			e.printStackTrace();
			mailBody.append(e.getMessage());
			throw e;
		}finally{
			sendMail(mailBody, totalCount, successCount);
		}
		return successCount;
	}
	
	private ArrayList<Com890wa> queryUnsend() throws SystemException, Exception{
		//目前是每500筆處理一次
		Result result = com890waService.findUnsendCom890waData();
		if(result.getResObject() != null){
			ArrayList<Com890wa> com890waList = (ArrayList<Com890wa>)result.getResObject();
			return com890waList;
		}
		return null;
	}
	
	private void sendMail(StringBuffer errMailBody, int totalCount, int successCount) throws AddressException, UnsupportedEncodingException, MessagingException{
		
		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String from = "";
		String to = "ag059@ctbcins.com;ag031@ctbcins.com";
		String cc = "";
		String bcc = "cf045@ctbcins.com";
		int failCount = totalCount - successCount;
		
		String errStr = "";
		if(errMailBody.length() > 0){
			errStr = "(請注意有異常)";
		}
		
		String subjectStr = env + " - 保單存摺 -AS400轉中介(OthBatchPassbookList)執行結果 " + errStr + " - 總筆數：" + totalCount + "，成功筆數：" +  successCount + "，失敗筆數：" + failCount;
		
		Mailer mailer = new Mailer();
		mailer.sendmail(smtpServer, contentType, subjectStr, from, "", to, "", cc, "", bcc, "", errMailBody.toString(), auth, userName, password);
	}

	public Com890waService getCom890waService() {
		return com890waService;
	}

	public void setCom890waService(Com890waService com890waService) {
		this.com890waService = com890waService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}


	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public Com890wbService getCom890wbService() {
		return com890wbService;
	}

	public void setCom890wbService(Com890wbService com890wbService) {
		this.com890wbService = com890wbService;
	}

	public OthBatchPassbookTranService getOthBatchPassbookTranService() {
		return othBatchPassbookTranService;
	}

	public void setOthBatchPassbookTranService(
			OthBatchPassbookTranService othBatchPassbookTranService) {
		this.othBatchPassbookTranService = othBatchPassbookTranService;
	}

}
