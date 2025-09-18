package com.tlg.aps.bs.firBotRenewalFileService;

import java.util.List;

import com.tlg.prpins.entity.FirAgtBotFd;
import com.tlg.util.Result;

/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
public interface BotReceiveFileService {
	
	public Result insertFirAgtBatchMain (String batchNo,String businessnature, String batchType,String filename,String fileStatus,String deleteFlag,String userId) throws Exception;
	
	public void insertBotFdAndUpdateFirAgtBatchMain(List<FirAgtBotFd> firAgtBotFdList,String batchNo,String userId ) throws Exception;

	public void updateFirAgtBatchMain(String batchNo, String userId, int fileQty,int filePqty,String fileStatus,String remark) throws Exception;
}
