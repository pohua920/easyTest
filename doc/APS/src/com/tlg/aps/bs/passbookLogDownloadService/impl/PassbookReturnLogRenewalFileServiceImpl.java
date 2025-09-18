package com.tlg.aps.bs.passbookLogDownloadService.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.passbookLogDownloadService.PassbookReturnLogRenewalFileService;
import com.tlg.prpins.entity.OthBatchPassbookReturnLog;
import com.tlg.prpins.service.OthBatchPassbookReturnLogService;
import com.tlg.util.StringUtil;

/* mantis：OTH0138，處理人員：CC009，需求單編號：OTH0138 保發中心_保單存摺回饋檔回存資料庫規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class PassbookReturnLogRenewalFileServiceImpl implements PassbookReturnLogRenewalFileService{
	
	private static final Logger logger = Logger.getLogger(PassbookReturnLogRenewalFileServiceImpl.class);
	
	private OthBatchPassbookReturnLogService othBatchPassbookReturnLogService;

	@Override
	public void insertOthBatchPassbookReturnLogList(String fileName, String userId, List<String> fileDataList) throws Exception {
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
			
			OthBatchPassbookReturnLog entity = new OthBatchPassbookReturnLog();
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
			othBatchPassbookReturnLogService.insertOthBatchPassbookReturnLog(entity);
			count++;
		}
		
	}

	public OthBatchPassbookReturnLogService getOthBatchPassbookReturnLogService() {
		return othBatchPassbookReturnLogService;
	}

	public void setOthBatchPassbookReturnLogService(OthBatchPassbookReturnLogService othBatchPassbookReturnLogService) {
		this.othBatchPassbookReturnLogService = othBatchPassbookReturnLogService;
	}
	
}
