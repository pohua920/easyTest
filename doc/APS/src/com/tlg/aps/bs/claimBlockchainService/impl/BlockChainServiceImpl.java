package com.tlg.aps.bs.claimBlockchainService.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.claimBlockchainService.BlockChainService;
import com.tlg.aps.bs.claimBlockchainService.ClaimCompulsoryJobNTService;
import com.tlg.aps.webService.claimBlockChainService.client.CompulsoryCaseCreate305;
import com.tlg.aps.webService.claimBlockChainService.client.CompulsoryCaseUpdate307;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryCreateApplicantVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryCreateApportionVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryCreateCaseVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryCreateChargesVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryCreateResultVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryCreateStatePriceVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryCreateVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryUpdateApplicantVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryUpdateApportionVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryUpdateCaseVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryUpdateChargesVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryUpdateResultVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryUpdateStatePriceVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryUpdateVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ClaimCompulsoryApplicant;
import com.tlg.prpins.entity.ClaimCompulsoryApportion;
import com.tlg.prpins.entity.ClaimCompulsoryCase;
import com.tlg.prpins.entity.ClaimCompulsoryCharges;
import com.tlg.prpins.entity.ClaimCompulsoryJob;
import com.tlg.prpins.entity.ClaimCompulsoryStatePrices;
import com.tlg.prpins.service.ClaimCompulsoryApplicantService;
import com.tlg.prpins.service.ClaimCompulsoryApportionService;
import com.tlg.prpins.service.ClaimCompulsoryCaseService;
import com.tlg.prpins.service.ClaimCompulsoryChargesService;
import com.tlg.prpins.service.ClaimCompulsoryJobService;
import com.tlg.prpins.service.ClaimCompulsoryStatePricesService;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class BlockChainServiceImpl implements BlockChainService {

	private ConfigUtil configUtil;
	private ClaimCompulsoryApplicantService claimCompulsoryApplicantService;
	private ClaimCompulsoryApportionService claimCompulsoryApportionService;
	private ClaimCompulsoryCaseService claimCompulsoryCaseService;
	private ClaimCompulsoryChargesService claimCompulsoryChargesService;
	private ClaimCompulsoryJobService claimCompulsoryJobService;
	private ClaimCompulsoryJobNTService claimCompulsoryJobNTService;
	private ClaimCompulsoryStatePricesService claimCompulsoryStatePricesService;
	
	
	private final Logger logger = Logger.getLogger(BlockChainServiceImpl.class);

	
	/* (non-Javadoc)
	 * @see com.tlg.aps.bs.claimBlockchainService.BlockChainService#claimCompulsoryCreate()
	 */
	@Override
	public Result claimCompulsoryCreateOrUpdate() throws SystemException, Exception {
		logger.info("claimCompulsoryCreate start - " + new Date());
		long startSmsTime = System.currentTimeMillis();
		System.out.println("startSmsTime = " + startSmsTime);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String executeDate = dateFormat.format(new Date());
		int sCount = 0;
		int fCount = 0;
		ArrayList<String> resultStrList = new ArrayList<String>();
		try{
			//executeTime為 null
			Map<String, String> params = new HashMap<String, String>();
			params.put("executeTimeNull", "Y");
			Result result = claimCompulsoryJobService.findClaimCompulsoryJobByParams(params);
			if(result.getResObject() == null){
				result = new Result();
				result.setResObject(Boolean.TRUE);
				
				Message message = new Message();
				message.setMessageString("目前無可新增資料");
				result.setMessage(message);
				
				return result;
			}
			ArrayList<ClaimCompulsoryJob> claimCompulsoryJobList = (ArrayList<ClaimCompulsoryJob>)result.getResObject();
			for(ClaimCompulsoryJob claimCompulsoryJob : claimCompulsoryJobList){
				logger.info("claimCompulsoryJob.getClaimNo() = " + claimCompulsoryJob.getClaimNo() + ", JOB_OID = " + claimCompulsoryJob.getOid());
				if("1".equals(claimCompulsoryJob.getType())){
					//區塊鍊新增
					CompulsoryCreateVo compulsoryCreateVo = new CompulsoryCreateVo();
					try{
						
						BigDecimal jobOid = claimCompulsoryJob.getOid();
						Map<String, BigDecimal> params1 = new HashMap<String, BigDecimal>();
						params1.put("jobOid", jobOid);
						result = claimCompulsoryApplicantService.findClaimCompulsoryApplicantByParams(params1);
						if(result.getResObject() != null){
							ArrayList<ClaimCompulsoryApplicant> claimCompulsoryApplicantList = (ArrayList<ClaimCompulsoryApplicant>)result.getResObject();
							ClaimCompulsoryApplicant claimCompulsoryApplicant = claimCompulsoryApplicantList.get(0);
							
							CompulsoryCreateApplicantVo compulsoryCreateApplicantVo = new CompulsoryCreateApplicantVo();
							BeanUtils.copyProperties(compulsoryCreateApplicantVo, claimCompulsoryApplicant);
							compulsoryCreateVo.setCreateApplicant(compulsoryCreateApplicantVo);
						}
						result = claimCompulsoryCaseService.findClaimCompulsoryCaseByParams(params1);
						if(result.getResObject() != null){
							ArrayList<ClaimCompulsoryCase> claimCompulsoryCaseList = (ArrayList<ClaimCompulsoryCase>)result.getResObject();
							ClaimCompulsoryCase claimCompulsoryCase = claimCompulsoryCaseList.get(0);
							
							CompulsoryCreateCaseVo compulsoryCreateCaseVo = new CompulsoryCreateCaseVo();
							BeanUtils.copyProperties(compulsoryCreateCaseVo, claimCompulsoryCase);
							compulsoryCreateVo.setCreateCase(compulsoryCreateCaseVo);
						}
						result = claimCompulsoryChargesService.findClaimCompulsoryChargesByParams(params1);
						if(result.getResObject() != null){
							ArrayList<CompulsoryCreateChargesVo> compulsoryCreateChargesVoList = new ArrayList<CompulsoryCreateChargesVo>();
							ArrayList<ClaimCompulsoryCharges> claimCompulsoryChargesList = (ArrayList<ClaimCompulsoryCharges>)result.getResObject();
							for(ClaimCompulsoryCharges claimCompulsoryCharges : claimCompulsoryChargesList){
								CompulsoryCreateChargesVo compulsoryCreateChargesVo = new CompulsoryCreateChargesVo();
								BeanUtils.copyProperties(compulsoryCreateChargesVo, claimCompulsoryCharges);
								compulsoryCreateChargesVoList.add(compulsoryCreateChargesVo);
							}
							if(compulsoryCreateChargesVoList.size() > 0){
								compulsoryCreateVo.setCreateCharges(compulsoryCreateChargesVoList);
							}
						}
						result = claimCompulsoryApportionService.findClaimCompulsoryApportionByParams(params1);
						if(result.getResObject() != null){
							ArrayList<ClaimCompulsoryApportion> claimCompulsoryApportionList = (ArrayList<ClaimCompulsoryApportion>)result.getResObject();
							ClaimCompulsoryApportion claimCompulsoryApportion = claimCompulsoryApportionList.get(0);
							//先設定主檔
							CompulsoryCreateApportionVo compulsoryCreateApportionVo = new CompulsoryCreateApportionVo();
							BeanUtils.copyProperties(compulsoryCreateApportionVo, claimCompulsoryApportion);
							//查是否有STATE_PRICES
							Map<String, BigDecimal> params2 = new HashMap<String, BigDecimal>();
							params2.put("oidClaimCompulsoryApportion", claimCompulsoryApportion.getOid());
							result = claimCompulsoryStatePricesService.findClaimCompulsoryStatePricesByParams(params2);
							if(result.getResObject() != null){
								ArrayList<CompulsoryCreateStatePriceVo> compulsoryCreateStatePriceVoList = new ArrayList<CompulsoryCreateStatePriceVo>();
								ArrayList<ClaimCompulsoryStatePrices> claimCompulsoryStatePricesList = (ArrayList<ClaimCompulsoryStatePrices>)result.getResObject();
								for(ClaimCompulsoryStatePrices claimCompulsoryStatePrices:claimCompulsoryStatePricesList){
									CompulsoryCreateStatePriceVo compulsoryCreateStatePriceVo = new CompulsoryCreateStatePriceVo();
									BeanUtils.copyProperties(compulsoryCreateStatePriceVo, claimCompulsoryStatePrices);
									compulsoryCreateStatePriceVoList.add(compulsoryCreateStatePriceVo);
								}
								if(compulsoryCreateStatePriceVoList != null && compulsoryCreateStatePriceVoList.size() > 0){
									compulsoryCreateApportionVo.setStatePriceVo(compulsoryCreateStatePriceVoList);
								}
							}
							compulsoryCreateVo.setCreateApportion(compulsoryCreateApportionVo);
						}
						
						CompulsoryCaseCreate305 compulsoryCaseCreate305 = new CompulsoryCaseCreate305();
						CompulsoryCreateResultVo resultVo = compulsoryCaseCreate305.compulsoryCreate(compulsoryCreateVo, claimCompulsoryJob.getIcreate());
						claimCompulsoryJob.setExecuteTime(new Date());
						claimCompulsoryJob.setCode(resultVo.getCode());
						claimCompulsoryJob.setMsg(resultVo.getMessage());
						claimCompulsoryJob.setIupdate("Schedule");
						claimCompulsoryJob.setDupdate(new Date());
						if(resultVo.get_case() != null){
							claimCompulsoryJob.setCaseId(resultVo.get_case().getId());
						}
						if(resultVo.getApportion() != null){
							claimCompulsoryJob.setApportionId(resultVo.getApportion().getId());
						}
						result = this.claimCompulsoryJobNTService.updateClaimCompulsoryJob(claimCompulsoryJob);
						String str = claimCompulsoryJob.getClaimNo() + "," + claimCompulsoryJob.getType() + "," + resultVo.getStatus() + "," + resultVo.getCode() + "," + resultVo.getMessage();
						if(resultVo.get_case() != null){
							str = str + "," + resultVo.get_case().getId();
						}else{
							str = str + ",";
						}
						if(resultVo.getApportion() != null){
							str = str + "," + resultVo.getApportion().getId();
						}else{
							str = str + ",";
						}
						resultStrList.add(str);
						if("Success".equals(resultVo.getStatus())){
							sCount++;
						}else{
							fCount++;
						}
					}catch(Exception e){
						e.printStackTrace();
						String str = claimCompulsoryJob.getClaimNo() + "," + claimCompulsoryJob.getType() + ",error," + e.getMessage() + ",發生異常,,";
						resultStrList.add(str);
						fCount++;
						continue;
					}
				}
				if("2".equals(claimCompulsoryJob.getType())){
					//區塊鍊修改
					CompulsoryUpdateVo compulsoryUpdateVo = new CompulsoryUpdateVo();
					BigDecimal jobOid = claimCompulsoryJob.getOid();
					String apportionId = claimCompulsoryJob.getApportionId();
					String caseId = claimCompulsoryJob.getCaseId();
					try{
						
						if(StringUtil.isSpace(apportionId)){
							throw new Exception("jobOid = " + jobOid + ",無法取得apportionId");
						}
						compulsoryUpdateVo.setCompulsoryApportionId(new BigDecimal(apportionId));
						compulsoryUpdateVo.setCaseId(caseId);
						Map<String, BigDecimal> params1 = new HashMap<String, BigDecimal>();
						params1.put("jobOid", jobOid);
						result = claimCompulsoryApplicantService.findClaimCompulsoryApplicantByParams(params1);
						if(result.getResObject() != null){
							ArrayList<ClaimCompulsoryApplicant> claimCompulsoryApplicantList = (ArrayList<ClaimCompulsoryApplicant>)result.getResObject();
							ClaimCompulsoryApplicant claimCompulsoryApplicant = claimCompulsoryApplicantList.get(0);
							
							CompulsoryUpdateApplicantVo compulsoryUpdateApplicantVo = new CompulsoryUpdateApplicantVo();
							BeanUtils.copyProperties(compulsoryUpdateApplicantVo, claimCompulsoryApplicant);
							compulsoryUpdateVo.setUpdateApplicant(compulsoryUpdateApplicantVo);
						}
						result = claimCompulsoryCaseService.findClaimCompulsoryCaseByParams(params1);
						if(result.getResObject() != null){
							ArrayList<ClaimCompulsoryCase> claimCompulsoryCaseList = (ArrayList<ClaimCompulsoryCase>)result.getResObject();
							ClaimCompulsoryCase claimCompulsoryCase = claimCompulsoryCaseList.get(0);
							
							CompulsoryUpdateCaseVo compulsoryUpdateCaseVo = new CompulsoryUpdateCaseVo();
							BeanUtils.copyProperties(compulsoryUpdateCaseVo, claimCompulsoryCase);
							compulsoryUpdateVo.setUpdateCase(compulsoryUpdateCaseVo);
						}
						result = claimCompulsoryChargesService.findClaimCompulsoryChargesByParams(params1);
						if(result.getResObject() != null){
							ArrayList<CompulsoryUpdateChargesVo> compulsoryUpdateChargesVoList = new ArrayList<CompulsoryUpdateChargesVo>();
							ArrayList<ClaimCompulsoryCharges> claimCompulsoryChargesList = (ArrayList<ClaimCompulsoryCharges>)result.getResObject();
							for(ClaimCompulsoryCharges claimCompulsoryCharges : claimCompulsoryChargesList){
								CompulsoryUpdateChargesVo compulsoryUpdateChargesVo = new CompulsoryUpdateChargesVo();
								BeanUtils.copyProperties(compulsoryUpdateChargesVo, claimCompulsoryCharges);
								compulsoryUpdateChargesVoList.add(compulsoryUpdateChargesVo);
							}
							if(compulsoryUpdateChargesVoList.size() > 0){
								compulsoryUpdateVo.setUpdateCharges(compulsoryUpdateChargesVoList);
							}
						}
						result = claimCompulsoryApportionService.findClaimCompulsoryApportionByParams(params1);
						if(result.getResObject() != null){
							ArrayList<ClaimCompulsoryApportion> claimCompulsoryApportionList = (ArrayList<ClaimCompulsoryApportion>)result.getResObject();
							ClaimCompulsoryApportion claimCompulsoryApportion = claimCompulsoryApportionList.get(0);
							//先設定主檔
							CompulsoryUpdateApportionVo compulsoryUpdateApportionVo = new CompulsoryUpdateApportionVo();
							BeanUtils.copyProperties(compulsoryUpdateApportionVo, claimCompulsoryApportion);
							//查是否有STATE_PRICES
							Map<String, BigDecimal> params2 = new HashMap<String, BigDecimal>();
							params2.put("oidClaimCompulsoryApportion", claimCompulsoryApportion.getOid());
							result = claimCompulsoryStatePricesService.findClaimCompulsoryStatePricesByParams(params2);
							if(result.getResObject() != null){
								ArrayList<CompulsoryUpdateStatePriceVo> compulsoryUpdateStatePriceVoList = new ArrayList<CompulsoryUpdateStatePriceVo>();
								ArrayList<ClaimCompulsoryStatePrices> claimCompulsoryStatePricesList = (ArrayList<ClaimCompulsoryStatePrices>)result.getResObject();
								for(ClaimCompulsoryStatePrices claimCompulsoryStatePrices:claimCompulsoryStatePricesList){
									CompulsoryUpdateStatePriceVo compulsoryUpdateStatePriceVo = new CompulsoryUpdateStatePriceVo();
									BeanUtils.copyProperties(compulsoryUpdateStatePriceVo, claimCompulsoryStatePrices);
									compulsoryUpdateStatePriceVoList.add(compulsoryUpdateStatePriceVo);
								}
								if(compulsoryUpdateStatePriceVoList != null && compulsoryUpdateStatePriceVoList.size() > 0){
									compulsoryUpdateApportionVo.setStatePriceVo(compulsoryUpdateStatePriceVoList);
								}
							}
							compulsoryUpdateVo.setUpdateApportion(compulsoryUpdateApportionVo);
						}
						
//						List<CompulsoryUpdateFilesVo> updateFilesList = new ArrayList<CompulsoryUpdateFilesVo>();
//						CompulsoryUpdateFilesVo fileVo = new CompulsoryUpdateFilesVo();
//						updateFilesList.add(fileVo);
//						compulsoryUpdateVo.setUpdateFiles(updateFilesList);
						
						CompulsoryCaseUpdate307 compulsoryCaseUpdate307 = new CompulsoryCaseUpdate307();
						CompulsoryUpdateResultVo resultVo = compulsoryCaseUpdate307.compulsoryUpdate(compulsoryUpdateVo, claimCompulsoryJob.getIcreate());
						claimCompulsoryJob.setExecuteTime(new Date());
						claimCompulsoryJob.setCode(resultVo.getCode());
						claimCompulsoryJob.setMsg(resultVo.getMessage());
						claimCompulsoryJob.setIupdate("Schedule");
						claimCompulsoryJob.setDupdate(new Date());
						result = this.claimCompulsoryJobNTService.updateClaimCompulsoryJob(claimCompulsoryJob);
						String str = claimCompulsoryJob.getClaimNo() + "," + claimCompulsoryJob.getType() + "," + resultVo.getStatus() + "," + resultVo.getCode() + "," + resultVo.getMessage() + "," + caseId + "," + apportionId;
						resultStrList.add(str);
						if("Success".equals(resultVo.getStatus())){
							sCount++;
						}else{
							fCount++;
						}
					}catch(Exception e){
						e.printStackTrace();
						String str = claimCompulsoryJob.getClaimNo() + "," + claimCompulsoryJob.getType() + ",error," + e.getMessage() + ",發生異常," + caseId + "," + apportionId;
						resultStrList.add(str);
						fCount++;
						continue;
					}

				}

			}
		}catch (SystemException se) {
			logger.error(se);
			se.printStackTrace();
			
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally{
			if(resultStrList != null && resultStrList.size() > 0){
				sendMail(resultStrList, executeDate, "理賠區塊鍊執行結果～", sCount, fCount);
			}
		}
		return null;
	}
	
	
	
	private void sendMail(ArrayList<String> msgList, String executeDate, String title, int successCount, int failCount) throws Exception{
		
		 ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
		 Mailer mailer = new Mailer();
		 String smtpServer = configUtil.getString("smtp_host");
		 String userName = configUtil.getString("smtp_username");
		 String password = configUtil.getString("smtp_pwd");
		 String contentType = "text/html; charset=utf-8";
		 String auth = "smtp";
		 String subject = title;
		 String from = configUtil.getString("mail_from_address");
		 String to = configUtil.getString("toClaimBlockChain_mail_to_address");
		 String cc = "";
		 StringBuffer mailBody = new StringBuffer();
		 mailBody.append("時間：" + executeDate + "<BR>");
		 mailBody.append("成功筆數：" + successCount + "，失敗筆數：" + failCount + "<BR>");
		 if(msgList != null && msgList.size() > 0){
			 mailBody.append("<table border='1' cellspacing='0'><tr><td></td><td>備案號</td><td>類型</td><td>STATUS</td><td>CODE</td><td>MESSAGE</td><td>CASE ID</td><td>APPORTION ID</td></tr>");
			 int count = 0;
			 for(String str:msgList){
				 String s1 = StringUtil.nullToSpace(str.split(",")[0]);
				 String s2 = StringUtil.nullToSpace(str.split(",")[1]);
				 if("1".equals(s2)){
					 s2 = "新增";
				 }
				 if("2".equals(s2)){
					 s2 = "修改";
				 }
				 String s3 = StringUtil.nullToSpace(str.split(",")[2]);
				 String s4 = StringUtil.nullToSpace(str.split(",")[3]);
				 String s5 = StringUtil.nullToSpace(str.split(",")[4]);
				 String s6 = StringUtil.nullToSpace(str.split(",")[5]);
				 String s7 = StringUtil.nullToSpace(str.split(",")[6]);
				 mailBody.append("<tr><td>" + (++count) + "</td><td>" + s1 + "</td><td>" + s2 + "</td><td>" + s3 + "</td><td>" + s4 + "</td><td>" + s5 + "</td><td>" + s6 + "</td><td>" + s7 + "</td></tr>");
			 }
			 mailBody.append("</table>");
		 }
		 mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
	}
	
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}


	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public ClaimCompulsoryApplicantService getClaimCompulsoryApplicantService() {
		return claimCompulsoryApplicantService;
	}

	public void setClaimCompulsoryApplicantService(
			ClaimCompulsoryApplicantService claimCompulsoryApplicantService) {
		this.claimCompulsoryApplicantService = claimCompulsoryApplicantService;
	}

	public ClaimCompulsoryApportionService getClaimCompulsoryApportionService() {
		return claimCompulsoryApportionService;
	}

	public void setClaimCompulsoryApportionService(
			ClaimCompulsoryApportionService claimCompulsoryApportionService) {
		this.claimCompulsoryApportionService = claimCompulsoryApportionService;
	}

	public ClaimCompulsoryCaseService getClaimCompulsoryCaseService() {
		return claimCompulsoryCaseService;
	}

	public void setClaimCompulsoryCaseService(ClaimCompulsoryCaseService claimCompulsoryCaseService) {
		this.claimCompulsoryCaseService = claimCompulsoryCaseService;
	}

	public ClaimCompulsoryChargesService getClaimCompulsoryChargesService() {
		return claimCompulsoryChargesService;
	}

	public void setClaimCompulsoryChargesService(
			ClaimCompulsoryChargesService claimCompulsoryChargesService) {
		this.claimCompulsoryChargesService = claimCompulsoryChargesService;
	}

	public ClaimCompulsoryJobService getClaimCompulsoryJobService() {
		return claimCompulsoryJobService;
	}

	public void setClaimCompulsoryJobService(
			ClaimCompulsoryJobService claimCompulsoryJobService) {
		this.claimCompulsoryJobService = claimCompulsoryJobService;
	}

	public ClaimCompulsoryStatePricesService getClaimCompulsoryStatePricesService() {
		return claimCompulsoryStatePricesService;
	}

	public void setClaimCompulsoryStatePricesService(
			ClaimCompulsoryStatePricesService claimCompulsoryStatePricesService) {
		this.claimCompulsoryStatePricesService = claimCompulsoryStatePricesService;
	}

	public ClaimCompulsoryJobNTService getClaimCompulsoryJobNTService() {
		return claimCompulsoryJobNTService;
	}

	public void setClaimCompulsoryJobNTService(
			ClaimCompulsoryJobNTService claimCompulsoryJobNTService) {
		this.claimCompulsoryJobNTService = claimCompulsoryJobNTService;
	}
}
