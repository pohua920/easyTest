package com.tlg.aps.bs.firBotRenewalFileService.impl;

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

import com.tlg.aps.bs.firBotRenewalFileService.BotRenewalFileService;
import com.tlg.aps.vo.Aps055BotDetailVo;
import com.tlg.prpins.entity.FirAgtBotFd;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAgtBotFdService;
import com.tlg.prpins.service.FirAgtTocoreInsuredService;
import com.tlg.prpins.service.FirAgtTocoreMainService;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnBatchMainService;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class BotRenewalFileServiceImpl implements BotRenewalFileService {

	private FirBatchLogService firBatchLogService;
	private FirAgtrnBatchMainService firAgtrnBatchMainService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private FirAgtTocoreMainService firAgtTocoreMainService;
	private FirAgtTocoreInsuredService firAgtTocoreInsuredService;
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
	private FirAgtBotFdService firAgtBotFdService;
	

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
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業
	 *  台銀續件資料完成資料檢查後儲存轉核心要保資料主檔及要保資料關係人檔*/
	@Override
	public void updateFirAgtTocoreAndAgtrnBatchDtl(Aps055BotDetailVo botDetailVo, String userId) throws Exception {
		Date sysDate = new Date();
		
		FirAgtTocoreMain firAgtTocoreMain = new FirAgtTocoreMain();
		BeanUtils.copyProperties(botDetailVo, firAgtTocoreMain);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		firAgtTocoreMain.setIntroducerid(botDetailVo.getHandleridentifynumber());//介紹人
		firAgtTocoreMain.setStartdate(sdf.parse(botDetailVo.getStartdate()));
		firAgtTocoreMain.setIupdate(userId);
		firAgtTocoreMain.setDupdate(sysDate);
		firAgtTocoreMain.setAmountF(botDetailVo.getAmountF().replace(",", ""));
		firAgtTocoreMain.setAmountQ(botDetailVo.getAmountQ().replace(",", ""));
		/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 start
		 * 儲存時一併更新總保費欄位*/
		String premiumQ = botDetailVo.getPremiumQ().replace(",", "");
		String premiumF = botDetailVo.getPremiumF().replace(",", "");
		firAgtTocoreMain.setPremiumF(premiumF);
		firAgtTocoreMain.setPremiumQ(premiumQ);
		firAgtTocoreMain.setPremiumT(String.valueOf(Integer.parseInt(premiumQ) + Integer.parseInt(premiumF)));
		/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 end */
		firAgtTocoreMainService.updateFirAgtTocoreMain(firAgtTocoreMain);

		Map<String, Object> params = new HashMap<>();
		params.put("batchNo", botDetailVo.getBatchNo());
		params.put("batchSeq", botDetailVo.getBatchSeq());
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
		
		List<FirAgtTocoreInsured> insuredList = botDetailVo.getInsuredList();
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
		BeanUtils.copyProperties(botDetailVo, firAgtrnBatchDtl);
		firAgtrnBatchDtl.setBatchNo(botDetailVo.getBatchNo());
		firAgtrnBatchDtl.setBatchSeq(botDetailVo.getBatchSeq());
		firAgtrnBatchDtl.setIupdate(userId);
		firAgtrnBatchDtl.setDupdate(sysDate);
		if ("Y".equals(botDetailVo.getSfFlag())) {
			firAgtrnBatchDtl.setDataStatus("A");//勾選為剔退件，不轉核心
			firAgtrnBatchDtl.setSfUser(userId);
			firAgtrnBatchDtl.setSfDate(sysDate);
		}else if("N".equals(botDetailVo.getSfFlag()) && !StringUtil.isSpace(botDetailVo.getSfUser()) ) {
			firAgtrnBatchDtl.setDataStatus("2");//當取消勾選為剔退件，將資料狀態改回暫存成功
		/*mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 第二階段 start*/
			firAgtrnBatchDtl.setSfUser(null);
			firAgtrnBatchDtl.setSfDate(null);
		}else if("C".equals(botDetailVo.getDataStatus()) && !StringUtil.isSpace(botDetailVo.getFixUser())){
			firAgtrnBatchDtl.setDataStatus("2");//當調整簽署檔比對不一至資料，將資料狀態改回暫存成功，待轉核心
		}
		/*mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 第二階段 end*/
		
		firAgtrnBatchDtlService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl);
	}
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
	@Override
	public void updateAgtTocoreMainAndAgtrnBatchDtlAndAgtBotFd(FirAgtTocoreMain firAgtTocoreMain, FirAgtrnBatchDtl firAgtrnBatchDtl,FirAgtBotFd firAgtBotFd) throws Exception {
		firAgtTocoreMainService.updateFirAgtTocoreMain(firAgtTocoreMain);
		firAgtrnBatchDtlService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl);
		firAgtBotFdService.updateFirAgtBotFd(firAgtBotFd);
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
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 start **/
	public FirAgtBotFdService getFirAgtBotFdService() {
		return firAgtBotFdService;
	}

	public void setFirAgtBotFdService(FirAgtBotFdService firAgtBotFdService) {
		this.firAgtBotFdService = firAgtBotFdService;
	}
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程  end**/
}
