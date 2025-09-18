package com.tlg.aps.bs.firTiiDataServerce.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firTiiDataServerce.FirTiiReturnLogRenewalFileService;
import com.tlg.prpins.entity.FirBatchTiiReturnLog;
import com.tlg.prpins.service.FirBatchTiiReturnLogService;
import com.tlg.util.StringUtil;

/* mantis：FIR0580，處理人員：BJ085，需求單編號：FIR0580 保發中心-住火保批資料回饋檔回存資料庫 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class FirTiiReturnLogRenewalFileServiceImpl implements FirTiiReturnLogRenewalFileService{
	
	private static final Logger logger = Logger.getLogger(FirTiiReturnLogRenewalFileServiceImpl.class);
	
	private FirBatchTiiReturnLogService firBatchTiiReturnLogService;

	@Override
	public void insertReturnLogList(String fileName, String userId, List<String> fileDataList) throws Exception {
		int count = 1;
		Date now = new Date();
		fileName = fileName.replaceAll(".log", "");
		for(String s:fileDataList) {
			if(StringUtil.isSpace(s) || s.split("\\|\\|").length <=0) {
				continue;
			}
			String[] ary = s.split("\\|\\|");
			if(ary.length != 7) {
				logger.info("檔名:"+fileName+" 第"+count+"筆格式錯誤 :"+s);
				continue;
			}
			
			FirBatchTiiReturnLog entity = new FirBatchTiiReturnLog();
			String dataType = "DATA";
			if(ary[0].endsWith(".zip")) {
				dataType = "ZIP";
			} else if (ary[0].endsWith(".txt")) {
				dataType = "TXT";
			}
			entity.setFileName(fileName);
			entity.setDataType(dataType);
			entity.setIdNo(ary[0]);
			entity.setRemark(ary[1]);
			entity.setIdentifier(ary[2]);
			entity.setStatusCode(ary[3]);
			entity.setStatusMsg(ary[4]);
			entity.setErrorMsg(ary[5]);
			entity.setTimestamp(ary[6]);
			entity.setIcreate(userId);
			entity.setDcreate(now);
			firBatchTiiReturnLogService.insertFirBatchTiiReturnLog(entity);
			count++;
		}
	}

	public FirBatchTiiReturnLogService getFirBatchTiiReturnLogService() {
		return firBatchTiiReturnLogService;
	}

	public void setFirBatchTiiReturnLogService(FirBatchTiiReturnLogService firBatchTiiReturnLogService) {
		this.firBatchTiiReturnLogService = firBatchTiiReturnLogService;
	}

}
