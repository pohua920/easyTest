package com.tlg.aps.bs.firBotRenewalFileService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps055BotDetailVo;
import com.tlg.prpins.entity.FirAgtBotFd;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.util.Result;

/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  **/
public interface BotRenewalFileService {
	
	public Result insertFirBatchLog (Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId,FirBatchLog firBatchLog) throws Exception;

	public void updateFirAgtrnBatchMain(String batchNo, String userId, Map<String,String> params) throws Exception;

	public void updateFirAgtrnBatchDtl(FirAgtrnBatchDtl firAgtrnBatchDtl, String batchNo, String userId, int batchSeq) throws Exception;

	public void updateFirAgtTocore(FirAgtTocoreMain firAgtTocoreMain,List<FirAgtTocoreInsured> firAgtTocoreInsuredToUpdateList) throws Exception;
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	public void updateFirAgtTocoreAndAgtrnBatchDtl(Aps055BotDetailVo botDetailVo, String userId) throws Exception;
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
	public void updateAgtTocoreMainAndAgtrnBatchDtlAndAgtBotFd(FirAgtTocoreMain firAgtTocoreMain, FirAgtrnBatchDtl firAgtrnBatchDtl,FirAgtBotFd firAgtBotFd) throws Exception;
}
