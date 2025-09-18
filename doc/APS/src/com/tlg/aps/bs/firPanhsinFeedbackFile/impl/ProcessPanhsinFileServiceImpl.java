package com.tlg.aps.bs.firPanhsinFeedbackFile.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firPanhsinFeedbackFile.ProcessPanhsinFileService;
import com.tlg.aps.vo.Aps016DetailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchGenfile;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.prpins.entity.FirAgtrnTmpBop;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAgtBatchGenfileService;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirAgtTocoreInsuredService;
import com.tlg.prpins.service.FirAgtTocoreMainService;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnBatchMainService;
import com.tlg.prpins.service.FirAgtrnTmpBopService;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class ProcessPanhsinFileServiceImpl implements ProcessPanhsinFileService {
	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程
	   mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 
	   mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業
	   mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */

	private FirBatchLogService firBatchLogService;
	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtBatchGenfileService firAgtBatchGenfileService;
	private FirAgtrnBatchMainService firAgtrnBatchMainService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private FirAgtrnTmpBopService firAgtrnTmpBopService;
	private FirAgtTocoreMainService firAgtTocoreMainService;
	private FirAgtTocoreInsuredService firAgtTocoreInsuredService;

	@Override
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId,String status, String remark,String batchNo) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setStatus(status);
		firBatchLog.setBatchNo(batchNo);
		firBatchLog.setPrgId(programId);
		firBatchLog.setRemark(remark);
		firBatchLog.setIcreate(userId);
		firBatchLog.setDcreate(new Date());
		Result result = firBatchLogService.insertFirBatchLog(firBatchLog);
		result.setResObject(firBatchLog);
		
		return result;
	}
	
	@Override
	public void updateFirBatchLog(String status, String remark, String userId, FirBatchLog firBatchLog) throws Exception{
		firBatchLog.setStatus(status);
		if(!StringUtil.isSpace(remark)) {
			firBatchLog.setRemark(remark.length()>300?remark.substring(0, 300):remark);
		}
		firBatchLog.setIupdate(userId);
		firBatchLog.setDupdate(new Date());
		firBatchLogService.updateFirBatchLog(firBatchLog);
	}
	
	@Override
	public void insertBatchGenFileAndUpdateMain(String batchNo, String userId, String fileName) {
		try {
		FirAgtBatchGenfile firAgtBatchGenfile = new FirAgtBatchGenfile();
		firAgtBatchGenfile.setBatchNo(batchNo);
		firAgtBatchGenfile.setFileName(fileName);
		firAgtBatchGenfile.setIcreate(userId);
		firAgtBatchGenfile.setDcreate(new Date());		
		firAgtBatchGenfileService.insertFirAgtBatchGenfile(firAgtBatchGenfile);
		FirAgtBatchMain firAgtBatchMain = new FirAgtBatchMain();
		firAgtBatchMain.setFileStatus("Y");
		firAgtBatchMain.setFileName(fileName);
		firAgtBatchMain.setBatchNo(batchNo);
		firAgtBatchMainService.updateFirAgtBatchMain(firAgtBatchMain);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void insertFirAgtrnBatchMain(String batchNo,String userId,String filename) throws  SystemException, Exception {
		FirAgtrnBatchMain firAgtrnBatchMain = new FirAgtrnBatchMain();
		firAgtrnBatchMain.setBatchNo(batchNo);
		firAgtrnBatchMain.setFilename(filename);
		firAgtrnBatchMain.setBusinessnature("I99065");
		firAgtrnBatchMain.setFileStatus("S");
		firAgtrnBatchMain.setTransStatus("N");
		firAgtrnBatchMain.setDeleteFlag("N");
		firAgtrnBatchMain.setIcreate(userId);
		firAgtrnBatchMain.setDcreate(new Date());
		firAgtrnBatchMainService.insertFirAgtrnBatchMain(firAgtrnBatchMain);
	}
	
	@Override
	public void updateFirAgtrnBatchMain(String batchNo, String userId, Map<String,String> params ) throws  SystemException, Exception {
		FirAgtrnBatchMain firAgtrnBatchMain = new FirAgtrnBatchMain();
		firAgtrnBatchMain.setBatchNo(batchNo);
		firAgtrnBatchMain.setFileStatus(params.get("fileStatus"));
		firAgtrnBatchMain.setTransStatus(params.get("transStatus"));
		firAgtrnBatchMain.setDataqtyT(StringUtil.isSpace(params.get("dataqtyT"))?null:Integer.parseInt(params.get("dataqtyT")));
		firAgtrnBatchMain.setDataqtyS(StringUtil.isSpace(params.get("dataqtyS"))?null:Integer.parseInt(params.get("dataqtyS")));
		firAgtrnBatchMain.setDataqtyF(StringUtil.isSpace(params.get("dataqtyF"))?null:Integer.parseInt(params.get("dataqtyF")));
		firAgtrnBatchMain.setIupdate(userId);
		firAgtrnBatchMain.setDupdate(new Date());
		String remark = params.get("remark");
		if(!StringUtil.isSpace(remark) && remark.length()>300) {
			remark = remark.substring(0, 300);
		}
		firAgtrnBatchMain.setRemark(remark);
		firAgtrnBatchMainService.updateFirAgtrnBatchMain(firAgtrnBatchMain);
	}
	
	@Override
	public void insertFirAgtrnTmpBopList(String batchNo, String fileName, String userId, List<String> fileDataList) throws SystemException, Exception {
		for (int i = 0; i < fileDataList.size(); i++) {
			FirAgtrnTmpBop firAgtrnTmpBop = new FirAgtrnTmpBop();
			firAgtrnTmpBop.setBatchNo(batchNo);// BatchLog批次號
			firAgtrnTmpBop.setBatchSeq(i+1);
			firAgtrnTmpBop.setFilename(fileName);// 存取在FIR_AGTRN_BATCH_MAIN中
			firAgtrnTmpBop.setRawdata(fileDataList.get(i));
			firAgtrnTmpBop.setpStatus("N");// 固定值
			firAgtrnTmpBop.setIcreate(userId);
			firAgtrnTmpBop.setDcreate(new Date());
			firAgtrnTmpBopService.insertFirAgtrnTmpBop(firAgtrnTmpBop);
		}
	}
	
	/*mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業 start */
	@Override
	public void updateFirAgtTocoreAndAgtrnBatchDtl(Aps016DetailVo aps016DetailVo, String userId) throws SystemException, Exception {
		//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
		Date sysDate = new Date();
		
		FirAgtTocoreMain firAgtTocoreMain = new FirAgtTocoreMain();
		BeanUtils.copyProperties(aps016DetailVo,firAgtTocoreMain);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		firAgtTocoreMain.setIntroducerid(aps016DetailVo.getHandleridentifynumber());
		firAgtTocoreMain.setBusinessnature("I99065");
		String startdateCheck = aps016DetailVo.getStartdateCheck();
		firAgtTocoreMain.setStartdate(sdf.parse(startdateCheck));
		firAgtTocoreMain.setIupdate(userId);
		//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
		firAgtTocoreMain.setDupdate(sysDate);
		firAgtTocoreMain.setAmountF(aps016DetailVo.getAmountF().replace(",", ""));
		firAgtTocoreMain.setAmountQ(aps016DetailVo.getAmountQ().replace(",", ""));
		firAgtTocoreMain.setPremiumF(aps016DetailVo.getPremiumF().replace(",", ""));
		firAgtTocoreMain.setPremiumQ(aps016DetailVo.getPremiumQ().replace(",", ""));
		firAgtTocoreMain.setPremiumT(aps016DetailVo.getPremiumT().replace(",", ""));
		firAgtTocoreMainService.updateFirAgtTocoreMain(firAgtTocoreMain);
		
		FirAgtTocoreInsured firAgtTocoreInsured2 = new FirAgtTocoreInsured();
		firAgtTocoreInsured2.setInsurednature(aps016DetailVo.getInsurednature2());
		firAgtTocoreInsured2.setInsuredname(aps016DetailVo.getInsuredname2());
		firAgtTocoreInsured2.setIdentifytype(aps016DetailVo.getIdentifytype2());
		firAgtTocoreInsured2.setIdentifynumber(aps016DetailVo.getIdentifynumber2());
		firAgtTocoreInsured2.setPhonenumber(aps016DetailVo.getPhonenumber2());
		firAgtTocoreInsured2.setMobile(aps016DetailVo.getMobile2());
		firAgtTocoreInsured2.setPostcode(aps016DetailVo.getPostcode2());
		firAgtTocoreInsured2.setPostaddress(aps016DetailVo.getPostaddress2());
		firAgtTocoreInsured2.setIshighdengeroccupation(aps016DetailVo.getIshighdengeroccupation2());
		firAgtTocoreInsured2.setDomicile(aps016DetailVo.getDomicile2());
		firAgtTocoreInsured2.setCountryename(aps016DetailVo.getCountryename2());
		firAgtTocoreInsured2.setBirthday(sdf.parse(rocToAd(aps016DetailVo.getBirthday2Check(),"/")));
		firAgtTocoreInsured2.setListedcabinetcompany(aps016DetailVo.getListedcabinetcompany2());			
		firAgtTocoreInsured2.setHeadname(aps016DetailVo.getHeadname2());
		firAgtTocoreInsured2.setBatchNo(aps016DetailVo.getBatchNo());
		firAgtTocoreInsured2.setBatchSeq(aps016DetailVo.getBatchSeq());
		firAgtTocoreInsured2.setInsuredflag("2");
		firAgtTocoreInsured2.setInsuredSeq(1);
		firAgtTocoreInsured2.setIupdate(userId);
		//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
		firAgtTocoreInsured2.setDupdate(sysDate);
		firAgtTocoreInsuredService.updateFirAgtTocoreInsured(firAgtTocoreInsured2);
		
		FirAgtTocoreInsured firAgtTocoreInsured1 = new FirAgtTocoreInsured();
		firAgtTocoreInsured1.setInsurednature(aps016DetailVo.getInsurednature1());
		firAgtTocoreInsured1.setInsuredname(aps016DetailVo.getInsuredname1());
		firAgtTocoreInsured1.setIdentifytype(aps016DetailVo.getIdentifytype1());
		firAgtTocoreInsured1.setIdentifynumber(aps016DetailVo.getIdentifynumber1());
		firAgtTocoreInsured1.setPhonenumber(aps016DetailVo.getPhonenumber1());
		firAgtTocoreInsured1.setMobile(aps016DetailVo.getMobile1());
		firAgtTocoreInsured1.setPostcode(aps016DetailVo.getPostcode1());
		firAgtTocoreInsured1.setPostaddress(aps016DetailVo.getPostaddress1());
		firAgtTocoreInsured1.setIshighdengeroccupation(aps016DetailVo.getIshighdengeroccupation1());
		firAgtTocoreInsured1.setDomicile(aps016DetailVo.getDomicile1());
		firAgtTocoreInsured1.setCountryename(aps016DetailVo.getCountryename1());
		firAgtTocoreInsured1.setBirthday(sdf.parse(rocToAd(aps016DetailVo.getBirthday1Check(),"/")));
		firAgtTocoreInsured1.setListedcabinetcompany(aps016DetailVo.getListedcabinetcompany1());			
		firAgtTocoreInsured1.setHeadname(aps016DetailVo.getHeadname1());
		firAgtTocoreInsured1.setBatchNo(aps016DetailVo.getBatchNo());
		firAgtTocoreInsured1.setBatchSeq(aps016DetailVo.getBatchSeq());
		firAgtTocoreInsured1.setInsuredflag("1");
		firAgtTocoreInsured1.setInsuredSeq(1);
		firAgtTocoreInsured1.setIupdate(userId);
		//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
		firAgtTocoreInsured1.setDupdate(sysDate);
		firAgtTocoreInsuredService.updateFirAgtTocoreInsured(firAgtTocoreInsured1);
		
		FirAgtrnBatchDtl firAgtrnBatchDtl = new FirAgtrnBatchDtl();
		BeanUtils.copyProperties(aps016DetailVo,firAgtrnBatchDtl);
		firAgtrnBatchDtl.setIupdate(userId);
		/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
		firAgtrnBatchDtl.setDupdate(sysDate);
		if(aps016DetailVo.getLocking().equals("Y")) {
			firAgtrnBatchDtl.setFixUser(userId);
			firAgtrnBatchDtl.setFixDate(sysDate);
		}
		if ("Y".equals(aps016DetailVo.getSfFlag())) {
			firAgtrnBatchDtl.setDataStatus("A");//勾選為剔退件，不轉核心
			firAgtrnBatchDtl.setSfUser(userId);
			firAgtrnBatchDtl.setSfDate(sysDate);
		}else if("N".equals(aps016DetailVo.getSfFlag()) && !StringUtil.isSpace(aps016DetailVo.getSfUser()) ) {
			firAgtrnBatchDtl.setDataStatus("2");//當取消勾選為剔退件，將資料狀態改回暫存成功
			firAgtrnBatchDtl.setSfReason("");
			firAgtrnBatchDtl.setSfUser(userId);
			firAgtrnBatchDtl.setSfDate(sysDate);
		}
		/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
		firAgtrnBatchDtlService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl);
	}
	
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}
	
	/*mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業 end */
	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}

	public FirAgtBatchMainService getFirAgtBatchMainService() {
		return firAgtBatchMainService;
	}

	public void setFirAgtBatchMainService(FirAgtBatchMainService firAgtBatchMainService) {
		this.firAgtBatchMainService = firAgtBatchMainService;
	}

	public FirAgtBatchGenfileService getFirAgtBatchGenfileService() {
		return firAgtBatchGenfileService;
	}

	public void setFirAgtBatchGenfileService(FirAgtBatchGenfileService firAgtBatchGenfileService) {
		this.firAgtBatchGenfileService = firAgtBatchGenfileService;
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

	public FirAgtrnTmpBopService getFirAgtrnTmpBopService() {
		return firAgtrnTmpBopService;
	}

	public void setFirAgtrnTmpBopService(FirAgtrnTmpBopService firAgtrnTmpBopService) {
		this.firAgtrnTmpBopService = firAgtrnTmpBopService;
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

}
