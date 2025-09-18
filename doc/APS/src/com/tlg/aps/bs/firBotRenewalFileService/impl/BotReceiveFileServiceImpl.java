package com.tlg.aps.bs.firBotRenewalFileService.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firBotRenewalFileService.BotReceiveFileService;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirAgtBotFd;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirAgtBotFdService;
import com.tlg.util.Result;

/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class BotReceiveFileServiceImpl implements BotReceiveFileService {

	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtBotFdService firAgtBotFdService;

	@Override
	public Result insertFirAgtBatchMain(String batchNo,String businessnature, String batchType,String filename,String fileStatus,String deleteFlag,String userId) throws Exception{
		FirAgtBatchMain firAgtBatchMain = new FirAgtBatchMain();
		firAgtBatchMain.setBatchNo(batchNo);
		firAgtBatchMain.setBusinessnature(businessnature);
		firAgtBatchMain.setBatchType(batchType);
		firAgtBatchMain.setFileName(filename);
		firAgtBatchMain.setFileStatus(fileStatus);
		firAgtBatchMain.setDeleteFlag(deleteFlag);
		firAgtBatchMain.setIcreate(userId);
		firAgtBatchMain.setDcreate(new Date());
		Result result = firAgtBatchMainService.insertFirAgtBatchMain(firAgtBatchMain);
		result.setResObject(firAgtBatchMain);
		return result;
	}

	
	@Override
	public void updateFirAgtBatchMain(String batchNo, String userId, int fileQty,int filePqty,String fileStatus,String remark)
			throws Exception {
		FirAgtBatchMain firAgtBatchMain = new FirAgtBatchMain();
		firAgtBatchMain.setBatchNo(batchNo);
		firAgtBatchMain.setFileQty(fileQty);
		firAgtBatchMain.setFilePqty(filePqty);
		firAgtBatchMain.setFileStatus(fileStatus);
		firAgtBatchMain.setIupdate(userId);
		firAgtBatchMain.setDupdate(new Date());
		firAgtBatchMain.setRemark(remark);
		firAgtBatchMainService.updateFirAgtBatchMain(firAgtBatchMain);
	}
	
	@Override
	public void insertBotFdAndUpdateFirAgtBatchMain(List<FirAgtBotFd> firAgtBotFdList,String batchNo ,String userId) throws Exception{
		FirAgtBatchMain firAgtBatchMain = new FirAgtBatchMain();
		int filePqty=0;
		try{
			for(FirAgtBotFd firAgtBotFd :firAgtBotFdList){
				firAgtBotFdService.insertFirAgtBotFd(firAgtBotFd);
				filePqty++;
			}
			firAgtBatchMain.setFilePqty(filePqty);
			firAgtBatchMain.setFileStatus("Y");
		}catch(Exception e){
			firAgtBatchMain.setFileStatus("E");
			String remark=e.toString();
			if(remark.length()>1000){
				remark = remark.substring( remark.length()- 1000, remark.length());
			}
			firAgtBatchMain.setRemark(remark);
		}
		firAgtBatchMain.setFileQty(firAgtBotFdList.size());
		firAgtBatchMain.setBatchNo(batchNo);
		firAgtBatchMain.setIupdate(userId);
		firAgtBatchMain.setDupdate(new Date());
		try{
			firAgtBatchMainService.updateFirAgtBatchMain(firAgtBatchMain);
			
		}catch(Exception e){
			System.out.println( e.toString());
		}
	}


	public FirAgtBatchMainService getFirAgtBatchMainService() {
		return firAgtBatchMainService;
	}


	public void setFirAgtBatchMainService(FirAgtBatchMainService firAgtBatchMainService) {
		this.firAgtBatchMainService = firAgtBatchMainService;
	}


	public FirAgtBotFdService getFirAgtBotFdService() {
		return firAgtBotFdService;
	}


	public void setFirAgtBotFdService(FirAgtBotFdService firAgtBotFdService) {
		this.firAgtBotFdService = firAgtBotFdService;
	}

	
}
