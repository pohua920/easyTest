package com.tlg.aps.bs.firTiiDataServerce.impl;

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

import com.tlg.aps.bs.firTiiDataServerce.AS400ToTiiDataService;
import com.tlg.db2.entity.Com910wa;
import com.tlg.db2.entity.Com910wb;
import com.tlg.db2.service.Com910waService;
import com.tlg.db2.service.Com910wbService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchTiiAddr;
import com.tlg.prpins.entity.FirBatchTiiList;
import com.tlg.prpins.service.FirBatchTiiAddrService;
import com.tlg.prpins.service.FirBatchTiiListService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**
 * @author bi086
 *
 */
@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class AS400ToTiiDataServiceImpl implements AS400ToTiiDataService {
	
	private static final Logger logger = Logger.getLogger(AS400ToTiiDataServiceImpl.class);
	private ConfigUtil configUtil;
	private Com910waService com910waService;
	private Com910wbService com910wbService;
	private FirBatchTiiListService firBatchTiiListService;
	private FirBatchTiiAddrService firBatchTiiAddrService;
	
	@Override
	public int insertFirBatchTiiListFromAs400(String batchSerial,
			String exectutor) throws SystemException, Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer mailBody = new StringBuffer();
		int successCount = 0;
		int totalCount = 0;
		boolean sendData = true;
		
		try{
			while(sendData){
				
				ArrayList<Com910wa> com910waList = queryUnsend();
				if(com910waList == null){
					sendData = false;
					break;
				}
				totalCount = totalCount + com910waList.size();
				for(Com910wa com910wa : com910waList){
					
					Date executeDate = new Date();
					FirBatchTiiList otbl = new FirBatchTiiList();
					otbl.setProcType(com910wa.getWa02());
					otbl.setInscoCode(com910wa.getWa03());
					otbl.setBranchCode(com910wa.getWa04());
					otbl.setInscoCodeE(com910wa.getWa05());
					otbl.setBranchCodeE(com910wa.getWa06());
					otbl.setInsNo(com910wa.getWa07());
					otbl.setInsName(com910wa.getWa08());
					otbl.setPolicyno(com910wa.getWa09());
					otbl.setEndorseno(com910wa.getWa10());
					otbl.setOldpolicyno(com910wa.getWa11());
					otbl.setDataStatus(com910wa.getWa12());
					if(!StringUtil.isSpace(com910wa.getWa13()) && !"0".equals(com910wa.getWa13())){
						otbl.setUndate(formatter.parse(com910wa.getWa13()));
					}
					otbl.setInsuredIdOld(com910wa.getWa14());
					if(!StringUtil.isSpace(com910wa.getWa15()) && !"0".equals(com910wa.getWa15())){
						otbl.setStartdateOld(formatter.parse(com910wa.getWa15()));
					}
					if(!StringUtil.isSpace(com910wa.getWa16())){
						otbl.setSumamount(new Long(com910wa.getWa16()));
					}
					if(!StringUtil.isSpace(com910wa.getWa17())){
						otbl.setSumpremium(new Long(com910wa.getWa17()));
					}
					if(!StringUtil.isSpace(com910wa.getWa18()) && !"0".equals(com910wa.getWa18())){
						otbl.setStartdate(formatter.parse(com910wa.getWa18()));
					}
					if(!StringUtil.isSpace(com910wa.getWa19()) && !"0".equals(com910wa.getWa19())){
						otbl.setEnddate(formatter.parse(com910wa.getWa19()));
					}
					otbl.setPolicynoStatus(com910wa.getWa20());
					if(!StringUtil.isSpace(com910wa.getWa21())){
						otbl.setAppliCount(new Short(com910wa.getWa21()));
					}
					otbl.setApplicantId(com910wa.getWa22());
					otbl.setApplicantName(com910wa.getWa23());
					otbl.setApplicantAddr(com910wa.getWa24());
					if(!StringUtil.isSpace(com910wa.getWa25())){
						otbl.setInsuredCount(new Short(com910wa.getWa25()));
					}
					otbl.setInsuredId(com910wa.getWa26());
					otbl.setInsuredName(com910wa.getWa27());
					otbl.setInsuredAddr(com910wa.getWa30());
					if(!StringUtil.isSpace(com910wa.getWa28())){
						otbl.setBankCount(new Short(com910wa.getWa28()));
					}
					otbl.setBankData(com910wa.getWa29());
					otbl.setRemark(com910wa.getWa31());
					if(!StringUtil.isSpace(com910wa.getWa32())){
						otbl.setAddrCount(new Short(com910wa.getWa32()));
					}
					if(!StringUtil.isSpace(com910wa.getWa33()) && !"0".equals(com910wa.getWa33())){
						otbl.setStartdateE(formatter.parse(com910wa.getWa33()));
					}
					if(!StringUtil.isSpace(com910wa.getWa34()) && !"0".equals(com910wa.getWa34())){
						otbl.setEnddateE(formatter.parse(com910wa.getWa34()));
					}
					otbl.setEndorText(com910wa.getWa35());
					if(!StringUtil.isSpace(com910wa.getWa36()) && !"0".equals(com910wa.getWa36())){
						otbl.setProcDate(formatter.parse(com910wa.getWa36()));
					}
					otbl.setIcreate(exectutor);
					otbl.setDcreate(executeDate);
					otbl.setBatchNoSp(batchSerial);
					otbl.setDataSource("2");
					if(!StringUtil.isSpace(com910wa.getWa44())){
						otbl.setAs400Oid(new Long(com910wa.getWa44()));
					}
				
					try{
					
						Result result = firBatchTiiListService.insertFirBatchTiiList(otbl);
						if(result.getResObject() == null){
							String errStr = "新增失敗：" + com910wa.getWa44() + " - " + com910wa.getWa10() + "-" + com910wa.getWa11();
							logger.info(errStr);
							//新增失敗
							com910wa.setWa45(errStr);
							mailBody.append(errStr + "<br>");
							continue;
						}
						
						//判斷是否有明細OID
						if(StringUtil.isSpace(com910wa.getWa44())){
							String errStr = "新增失敗：欄位WA44為空值(AS400_OID 對應 WB11) - " + com910wa.getWa44() + " - " + com910wa.getWa10() + "-" + com910wa.getWa11();
							logger.info(errStr);
							//新增失敗
							com910wa.setWa45(errStr);
							mailBody.append(errStr + "<br>");
							continue;
						}
//						//判斷是否有主檔的OID
						otbl = (FirBatchTiiList)result.getResObject();
						if(otbl.getOid() == null){
							String errStr = "新增失敗：無法取得OID" + " - " + com910wa.getWa44() + " - " + com910wa.getWa10() + "-" + com910wa.getWa11();
							logger.info(errStr);
							//新增失敗
							com910wa.setWa45(errStr);
							mailBody.append(errStr + "<br>");
							continue;
						}
						//撈險種資料
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("wb23", com910wa.getWa44());
						result = com910wbService.findCom910wbByParams(params);
						if(result.getResObject() == null){
							String errStr = "新增失敗：無法取得 COM910WB資料 - " + com910wa.getWa44() + " - " + com910wa.getWa10() + "-" + com910wa.getWa11();
							logger.info(errStr);
							//新增失敗
							com910wa.setWa45(errStr);
							mailBody.append(errStr + "<br>");
							continue;
						}
						ArrayList<Com910wb> com910wbList = (ArrayList<Com910wb>)result.getResObject();
						for(Com910wb com910wb:com910wbList){
							FirBatchTiiAddr addr = new FirBatchTiiAddr();
							
							addr.setListOid(otbl.getOid());
							addr.setPolicyno(com910wb.getWb03());
							addr.setEndorseno(com910wb.getWb04());
							if(!StringUtil.isSpace(com910wb.getWb05())){
								addr.setAddressno(new Short(com910wb.getWb05()));
							}
							if(!StringUtil.isSpace(com910wb.getWb05())){
								addr.setBuildingno(com910wb.getWb06());
							}
							addr.setAddrDetail(com910wb.getWb07());
							addr.setWallNo(com910wb.getWb08());
							addr.setBuildyears(com910wb.getWb09());
							if(!StringUtil.isSpace(com910wb.getWb10())){
								addr.setBuildarea(new BigDecimal(com910wb.getWb10()));
							}
							if(!StringUtil.isSpace(com910wb.getWb11())){
								addr.setSumfloors(new Short(com910wb.getWb11()));
							}
							addr.setStructure(com910wb.getWb12());
							addr.setUsingnature(com910wb.getWb13());
							if(!StringUtil.isSpace(com910wb.getWb14())){
								addr.setAmountF1(new Long(com910wb.getWb14()));
							}
							if(!StringUtil.isSpace(com910wb.getWb15())){
								addr.setAmountF2(new Long(com910wb.getWb15()));
							}
							if(!StringUtil.isSpace(com910wb.getWb16())){
								addr.setAmountOth(new Long(com910wb.getWb16()));
							}
							if(!StringUtil.isSpace(com910wb.getWb17())){
								addr.setAmountQ(new Long(com910wb.getWb17()));
							}
							if(!StringUtil.isSpace(com910wb.getWb18())){
								addr.setPremF1(new Long(com910wb.getWb18()));
							}
							if(!StringUtil.isSpace(com910wb.getWb19())){
								addr.setPremOth(new Long(com910wb.getWb19()));
							}
							if(!StringUtil.isSpace(com910wb.getWb20())){
								addr.setPremQ(new Long(com910wb.getWb20()));
							}
							if(!StringUtil.isSpace(com910wb.getWb20())){
								addr.setPremQ(new Long(com910wb.getWb20()));
							}
							if(!StringUtil.isSpace(com910wb.getWb23())){
								addr.setAs400Oid(new Long(com910wb.getWb23()));
							}
							addr.setIcreate(exectutor);
							addr.setDcreate(executeDate);
							result = this.firBatchTiiAddrService.insertFirBatchTiiAddr(addr);
							if(result.getResObject() == null){
								String errStr = "新增明細失敗：" + com910wa.getWa44() + " - " + com910wa.getWa10() + "-" + com910wa.getWa11() + ",Wb23：" + com910wb.getWb23();
								logger.info(errStr);
								//新增失敗
								com910wa.setWa45(errStr);
								mailBody.append(errStr + "<br>");
								continue;
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
						String errStr = "新增異常：" + e.getMessage() + "：" + com910wa.getWa44() + " - " + com910wa.getWa10() + "-" + com910wa.getWa11();
						logger.info(errStr);
						com910wa.setWa45(errStr);
						mailBody.append(errStr + "<br>");
					}finally{

						com910wa.setWa41(formatter2.format(executeDate));
						Result result = com910waService.updateCom910waForBatch(com910wa);
						if(result.getResObject() == null){
							String errStr = "更新AS400 Com910wa發生異常：" + com910wa.getWa44() + " - " + com910wa.getWa10() + "-" + com910wa.getWa11();
							logger.info(errStr);
							mailBody.append(errStr + "<br>");
						}
						//新增無異常的才會+1
						if(StringUtil.isSpace(com910wa.getWa45())){
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
	
	private ArrayList<Com910wa> queryUnsend() throws SystemException, Exception{
		//目前是每500筆處理一次
		Result result = com910waService.findUnsendCom910waData();
		if(result.getResObject() != null){
			ArrayList<Com910wa> com910waList = (ArrayList<Com910wa>)result.getResObject();
			return com910waList;
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
		String to = "ag059@ctbcins.com";
		String cc = "";
		String bcc = "cf045@ctbcins.com";
		int failCount = totalCount - successCount;
		
		String errStr = "";
		if(errMailBody.length() > 0){
			errStr = "(請注意有異常)";
		}
		
		String subjectStr = env + " - 房貸火險傳送資料 -AS400轉中介(FirBatchTiiList)執行結果 " + errStr + " - 總筆數：" + totalCount + "，成功筆數：" +  successCount + "，失敗筆數：" + failCount;
		
		Mailer mailer = new Mailer();
		mailer.sendmail(smtpServer, contentType, subjectStr, from, "", to, "", cc, "", bcc, "", errMailBody.toString(), auth, userName, password);
	}

	public Com910waService getCom910waService() {
		return com910waService;
	}

	public void setCom910waService(Com910waService com910waService) {
		this.com910waService = com910waService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}


	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public Com910wbService getCom910wbService() {
		return com910wbService;
	}

	public void setCom910wbService(Com910wbService com910wbService) {
		this.com910wbService = com910wbService;
	}

	public FirBatchTiiListService getFirBatchTiiListService() {
		return firBatchTiiListService;
	}

	public void setFirBatchTiiListService(
			FirBatchTiiListService firBatchTiiListService) {
		this.firBatchTiiListService = firBatchTiiListService;
	}

	public FirBatchTiiAddrService getFirBatchTiiAddrService() {
		return firBatchTiiAddrService;
	}

	public void setFirBatchTiiAddrService(
			FirBatchTiiAddrService firBatchTiiAddrService) {
		this.firBatchTiiAddrService = firBatchTiiAddrService;
	}

	

}
