package com.tlg.aps.bs.firYcbRenewalFileService.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firYcbRenewalFileService.YcbRenewalFileService;
import com.tlg.aps.vo.Aps060YcbDetailVo;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAgtTocoreInsuredService;
import com.tlg.prpins.service.FirAgtTocoreMainService;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnBatchMainService;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程  **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class YcbRenewalFileServiceImpl implements YcbRenewalFileService {

	private FirBatchLogService firBatchLogService;
	private FirAgtrnBatchMainService firAgtrnBatchMainService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private FirAgtTocoreMainService firAgtTocoreMainService;
	private FirAgtTocoreInsuredService firAgtTocoreInsuredService;
	

	@Override
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId, String status, String remark,
			String batchNo) throws Exception {
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
	public void updateFirAgtrnBatchMain(String batchNo, String userId, Map<String, String> params)
			throws Exception {
		FirAgtrnBatchMain firAgtrnBatchMain = new FirAgtrnBatchMain();
		firAgtrnBatchMain.setBatchNo(batchNo);
		firAgtrnBatchMain.setTransStatus(params.get("transStatus"));
		firAgtrnBatchMain.setDataqtyS(
				StringUtil.isSpace(params.get("dataqtyS")) ? null : Integer.parseInt(params.get("dataqtyS")));
		firAgtrnBatchMain.setDataqtyF(
				StringUtil.isSpace(params.get("dataqtyF")) ? null : Integer.parseInt(params.get("dataqtyF")));
		firAgtrnBatchMain.setIupdate(userId);
		firAgtrnBatchMain.setDupdate(new Date());
		firAgtrnBatchMain.setRemark(params.get("remark"));
		firAgtrnBatchMainService.updateFirAgtrnBatchMain(firAgtrnBatchMain);
	}

	@Override
	public void updateFirBatchLog(String status, String remark, String userId, FirBatchLog firBatchLog)
			throws Exception {
		firBatchLog.setStatus(status);
		if (!StringUtil.isSpace(remark)) {
			firBatchLog.setRemark(remark.length() > 300 ? remark.substring(0, 300) : remark);
		}
		firBatchLog.setIupdate(userId);
		firBatchLog.setDupdate(new Date());
		firBatchLogService.updateFirBatchLog(firBatchLog);
	}


	
	@Override
	public void updateFirAgtrnBatchDtl(FirAgtrnBatchDtl firAgtrnBatchDtl, String batchNo, String userId,
			int batchSeq) throws Exception {
		firAgtrnBatchDtl.setBatchNo(batchNo);
		firAgtrnBatchDtl.setBatchSeq(batchSeq);
		firAgtrnBatchDtl.setIupdate(userId);
		firAgtrnBatchDtl.setDupdate(new Date());
		firAgtrnBatchDtlService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl);
	}
	
	@Override
	public void updateFirAgtTocore(FirAgtTocoreMain firAgtTocoreMain,List<FirAgtTocoreInsured> firAgtTocoreInsuredToUpdateList) throws Exception {
		for(FirAgtTocoreInsured f :firAgtTocoreInsuredToUpdateList){
			firAgtTocoreInsuredService.updateFirAgtTocoreInsured(f);
		}
		firAgtTocoreMainService.updateFirAgtTocoreMain(firAgtTocoreMain);
		
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業
	 *  元大續件資料完成資料檢查後儲存轉核心要保資料主檔及要保資料關係人檔*/
	@Override
	public void updateFirAgtTocoreAndAgtrnBatchDtl(Aps060YcbDetailVo ycbDetailVo, String userId) throws Exception {
		Date sysDate = new Date();
		
		FirAgtTocoreMain firAgtTocoreMain = new FirAgtTocoreMain();
		BeanUtils.copyProperties(ycbDetailVo, firAgtTocoreMain);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		firAgtTocoreMain.setIntroducerid(ycbDetailVo.getHandleridentifynumber());//介紹人
		firAgtTocoreMain.setStartdate(sdf.parse(ycbDetailVo.getStartdate()));
		firAgtTocoreMain.setIupdate(userId);
		firAgtTocoreMain.setDupdate(sysDate);
		firAgtTocoreMain.setAmountF(ycbDetailVo.getAmountF().replace(",", ""));
		firAgtTocoreMain.setAmountQ(ycbDetailVo.getAmountQ().replace(",", ""));
		firAgtTocoreMain.setPremiumF(ycbDetailVo.getPremiumF().replace(",", ""));
		firAgtTocoreMain.setPremiumQ(ycbDetailVo.getPremiumQ().replace(",", ""));
		
		firAgtTocoreMainService.updateFirAgtTocoreMain(firAgtTocoreMain);

		Map<String, Object> params = new HashMap<>();
		params.put("batchNo", ycbDetailVo.getBatchNo());
		params.put("batchSeq", ycbDetailVo.getBatchSeq());
		Result result = firAgtTocoreInsuredService.findFirAgtTocoreInsuredByParams(params);
		List<BigDecimal> oriOidList = new ArrayList<>();
		if (result.getResObject() != null) {
			List<FirAgtTocoreInsured> oriInsuredList = (List<FirAgtTocoreInsured>) result.getResObject();
			for(FirAgtTocoreInsured oriInsured:oriInsuredList) {
				oriOidList.add(oriInsured.getOid());
			}
		}
		
		List<FirAgtTocoreInsured> updateList = new ArrayList<>();
		List<FirAgtTocoreInsured> insertList = new ArrayList<>();
		
		List<FirAgtTocoreInsured> insuredList = ycbDetailVo.getInsuredList();
		for (int i = 0; i < insuredList.size(); i++) {
			FirAgtTocoreInsured firAgtTocoreInsured = insuredList.get(i);
			firAgtTocoreInsured.setBirthday(sdf.parse(DateUtils.transDate(firAgtTocoreInsured.getStrBirthday(), "/")));
			firAgtTocoreInsured.setIupdate(userId);
			firAgtTocoreInsured.setDupdate(sysDate);
			
			if (firAgtTocoreInsured.getOid() != null) {
				updateList.add(firAgtTocoreInsured);
				oriOidList.remove(oriOidList.indexOf(firAgtTocoreInsured.getOid()));
			}else {
				firAgtTocoreInsured.setIcreate(userId);
				firAgtTocoreInsured.setDcreate(sysDate);
				insertList.add(firAgtTocoreInsured);
			}
		}
		
		//先執行刪除後再做資料更新及新增，以免有UK重複問題
		for(BigDecimal oriOid : oriOidList) {
			firAgtTocoreInsuredService.removeFirAgtTocoreInsured(oriOid);
		}
		if(!updateList.isEmpty()) {
			for(FirAgtTocoreInsured firAgtTocoreInsured:updateList) {
				firAgtTocoreInsuredService.updateFirAgtTocoreInsured(firAgtTocoreInsured);
			}
		}
		if(!insertList.isEmpty()) {
			for(FirAgtTocoreInsured firAgtTocoreInsured:insertList) {
				firAgtTocoreInsuredService.insertFirAgtTocoreInsured(firAgtTocoreInsured);
			}
		}
		
		FirAgtrnBatchDtl firAgtrnBatchDtl = new FirAgtrnBatchDtl();
		BeanUtils.copyProperties(ycbDetailVo, firAgtrnBatchDtl);
		firAgtrnBatchDtl.setBatchNo(ycbDetailVo.getBatchNo());
		firAgtrnBatchDtl.setBatchSeq(ycbDetailVo.getBatchSeq());
		firAgtrnBatchDtl.setIupdate(userId);
		firAgtrnBatchDtl.setDupdate(sysDate);
		if ("Y".equals(ycbDetailVo.getSfFlag())) {
			firAgtrnBatchDtl.setDataStatus("A");//勾選為剔退件，不轉核心
			firAgtrnBatchDtl.setSfUser(userId);
			firAgtrnBatchDtl.setSfDate(sysDate);
		}else if("N".equals(ycbDetailVo.getSfFlag()) && !StringUtil.isSpace(ycbDetailVo.getSfUser()) ) {
			firAgtrnBatchDtl.setDataStatus("2");//當取消勾選為剔退件，將資料狀態改回暫存成功
			firAgtrnBatchDtl.setSfUser(null);
			firAgtrnBatchDtl.setSfDate(null);
		}else if("C".equals(ycbDetailVo.getDataStatus()) && !StringUtil.isSpace(ycbDetailVo.getFixUser())){
			firAgtrnBatchDtl.setDataStatus("2");//當調整簽署檔比對不一至資料，將資料狀態改回暫存成功，待轉核心
		}
		/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start */
		if(ycbDetailVo.getLocking().equals("Y")) {
			firAgtrnBatchDtl.setFixUser(userId);
			firAgtrnBatchDtl.setFixDate(sysDate);
		}
		/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end */
		
		firAgtrnBatchDtlService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl);
	}

	
	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
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
