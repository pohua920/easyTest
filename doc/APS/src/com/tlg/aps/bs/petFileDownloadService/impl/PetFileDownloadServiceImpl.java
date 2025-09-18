package com.tlg.aps.bs.petFileDownloadService.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tlg.aps.bs.petFileDownloadService.PetFileDownloadService;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.FtsUtil;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.UserInfo;
import com.tlg.xchg.entity.CtbcPetWs10704;
import com.tlg.xchg.service.CtbcPetWs10704Service;

public class PetFileDownloadServiceImpl implements PetFileDownloadService {
	
	private static final Logger logger = Logger.getLogger(PetFileDownloadServiceImpl.class);
	private ConfigUtil configUtil;
	private CtbcPetWs10704Service ctbcPetWs10704Service;
	private FirBatchInfoService firBatchInfoService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result petFileDownload(UserInfo userInfo) throws SystemException, Exception {
		Result result = new Result();
		try {
			//判斷排程是否要執行(可透過資料庫來人工介入中斷或執行排程)-----start
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("prgId", "PET01_STATUS");
			result = this.firBatchInfoService.findFirBatchInfoByUK(params);
			if(result != null && result.getResObject() != null) {
				FirBatchInfo firBatchInfo = (FirBatchInfo)result.getResObject();
				if("N".equals(firBatchInfo.getMailTo())) {
					logger.info("FIR_BATCH_INFO設定檔PET01_STATUS設定為排程暫停執行");
					Message message = new Message();
					message.setMessageString("FIR_BATCH_INFO設定檔PET01_STATUS設定為排程暫停執行");
					result.setMessage(message);
					return result;
				}
			}
			//判斷排程是否要執行(可透過資料庫來人工介入中斷或執行排程)-----end
			
			params = new HashMap();
			params.put("received", "N");
			params.put("sortBy", "policyno,seqno");
			Result searchResult = ctbcPetWs10704Service.findCtbcPetWs10704ByParams(params);
			List<CtbcPetWs10704> actualDownloadFileList = null;
			if(searchResult != null && searchResult.getResObject() != null) {
				List<CtbcPetWs10704> needDownloadFileList = (List<CtbcPetWs10704>)searchResult.getResObject();
				actualDownloadFileList = this.getZipFileFromSftp(needDownloadFileList);
			}
			
			if(actualDownloadFileList != null && actualDownloadFileList.size() > 0) {
				String localFilePath = configUtil.getString("localPetFilePath");
				String ftsUrl = configUtil.getString("ftsUrl");
				if(ftsUrl == null || ftsUrl.length() <= 0) {
					Message message = new Message();
					message.setMessageString("查無上傳檔案網址");
					result.setMessage(message);
					return result;
				}
				FtsUtil ftsUtil = new FtsUtil(ftsUrl);
				File localFile = null;
				String source = "寵物險";
				String riskCode = "C";
				String businessNo = "";
				FileUploadResponseVo fileUploadResponseVo = null;
				for(CtbcPetWs10704 entity : actualDownloadFileList) {
					localFile = new File(localFilePath + entity.getFilename());
					businessNo = entity.getPolicyno();
					if(localFile.exists()) {
						try {
							fileUploadResponseVo = ftsUtil.uploadFile(localFile.getAbsolutePath(), source, riskCode, businessNo);
							if(fileUploadResponseVo != null && "Y".equalsIgnoreCase(fileUploadResponseVo.getStatus())) {
								entity.setReceived("Y");
								entity.setModifiedBy("SFTP");
								entity.setModifiedTime(new Date());
								ctbcPetWs10704Service.updateCtbcPetWs10704(entity);
							}
						}catch(Exception e) {
							e.printStackTrace();
							logger.error("petFileDownload Exception : ", e);
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("petFileDownload Exception : ", e);
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<CtbcPetWs10704> getZipFileFromSftp(List<CtbcPetWs10704> needDownloadFileList) throws Exception{
		List<String> downloadFileList = new ArrayList<String>();
		List<CtbcPetWs10704> downloadFileEntityList = new ArrayList<CtbcPetWs10704>();
		if(needDownloadFileList == null || needDownloadFileList.size() <= 0) {
			return downloadFileEntityList;
		}
		String sftpHost = configUtil.getString("ctbcFTP");
		String sftpUser = configUtil.getString("ctbcFtpUserGet");
		String sftpPwd = configUtil.getString("ctbcFptPwdGet");
		int sftpPort = 22;
		String remoteDir = configUtil.getString("ctbcPetRemotePath");
		String workingDirPath = configUtil.getString("localPetFilePath");
		SftpUtil sftpUtil = new SftpUtil(sftpHost, sftpPort, sftpUser, sftpPwd);
		
		List<String> remoteFileList = sftpUtil.getFileListFromSftp(remoteDir);

		if(remoteFileList != null && remoteFileList.size() > 0) {
			/**
			 * needDownloadFileList有依保單號與保單附件序號排序
			 * 所以用needDownloadFileList來比對remoteFileList
			 * 這樣downloadFileEntityList才會有排序
			 * 而且上傳到FTS也會依保單號與保單附件序號順序上傳
			 * */
			for(CtbcPetWs10704 entity : needDownloadFileList) {
				for(String  fileName : remoteFileList) {
					if(fileName.equals(entity.getFilename())) {
						downloadFileList.add(fileName);
						downloadFileEntityList.add(entity);
					}
				}
			}
		}
		
		//下載檔案到指定目錄
		if(downloadFileList.size() > 0) {
			sftpUtil.getFileFromSftp(remoteDir, workingDirPath, downloadFileList);
		}
		return downloadFileEntityList;
	}

	public CtbcPetWs10704Service getCtbcPetWs10704Service() {
		return ctbcPetWs10704Service;
	}

	public void setCtbcPetWs10704Service(CtbcPetWs10704Service ctbcPetWs10704Service) {
		this.ctbcPetWs10704Service = ctbcPetWs10704Service;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}
	
	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public static void main(String args[]) throws Exception{
		//測試取得檔案list----start
		String riskCode = "C";
		String businessNo = "180620PEC000002";
		FtsUtil ftsUtil = new FtsUtil("http://192.168.112.122:8880/FTS/rf/fileHandler/");
		FileListResponseVo vo = ftsUtil.getFtsFileList(riskCode, businessNo);
        ArrayList<FileVo> list = vo.getFileList();
        for(FileVo fv : list) {
        	System.out.println("----------");
        	System.out.println("oid : " + fv.getOid());
        	System.out.println("name : " + fv.getName());
        	System.out.println("downloadPath : " + fv.getDownloadPath());
        	System.out.println("----------");
        	System.out.println("start to download file : " + fv.getName());
        	ftsUtil.downloadFile(businessNo, fv.getOid(), "D:/temp/testPet/", fv.getName());
        	System.out.println("download file : " + fv.getName() + " finished");
        }
		//測試取得檔案list----end
	}
}
