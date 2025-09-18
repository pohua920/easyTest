package com.tlg.aps.bs.miClaimDownloadService.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.miClaimDownloadService.MiClaimDownloadNewTransService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Tmpfetclaimcomm;
import com.tlg.prpins.entity.Tmpfetclaimkind;
import com.tlg.prpins.entity.Tmpfetclaimmain;
import com.tlg.prpins.entity.Tmpfetclaimpay;
import com.tlg.prpins.service.TmpfetclaimcommService;
import com.tlg.prpins.service.TmpfetclaimkindService;
import com.tlg.prpins.service.TmpfetclaimmainService;
import com.tlg.prpins.service.TmpfetclaimpayService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class MiClaimDownloadNewTransServiceImpl implements MiClaimDownloadNewTransService {
	
	private static final Logger logger = Logger.getLogger(MiClaimDownloadNewTransServiceImpl.class);
	private ConfigUtil configUtil;
	private TmpfetclaimmainService tmpfetclaimmainService;
	private TmpfetclaimkindService tmpfetclaimkindService;
	private TmpfetclaimpayService tmpfetclaimpayService;
	private TmpfetclaimcommService tmpfetclaimcommService;
	
	@Override
	public void deleteAllData() throws SystemException, Exception {
		Map params = new HashMap();
		int count = tmpfetclaimmainService.countTmpfetclaimmain(params);
		if(count > 0){
			tmpfetclaimmainService.removeTmpfetclaimmainAll();
		}
		
		count = tmpfetclaimpayService.countTmpfetclaimpay(params);
		if(count > 0){
			tmpfetclaimpayService.removeTmpfetclaimpayAll();
		}
		
		count = tmpfetclaimkindService.countTmpfetclaimkind(params);
		if(count > 0){
			tmpfetclaimkindService.removeTmpfetclaimkindAll();
		}
		
		count = tmpfetclaimcommService.countTmpfetclaimcomm(params);
		if(count > 0){
			tmpfetclaimcommService.removeTmpfetclaimcommAll();
		}
		
	}
	
	@Override
	public void batchInsertFtpData(
			ArrayList<Tmpfetclaimmain> listTmpfetclaimmain,
			ArrayList<Tmpfetclaimkind> listTmpfetclaimkind,
			ArrayList<Tmpfetclaimpay> listTmpfetclaimpay,
			ArrayList<Tmpfetclaimcomm> listTmpfetclaimcomm)
			throws SystemException, Exception {
		
		if((listTmpfetclaimmain == null || listTmpfetclaimmain.size() == 0) ||
				(listTmpfetclaimkind == null || listTmpfetclaimkind.size() == 0) || 
				(listTmpfetclaimpay == null || listTmpfetclaimpay.size() == 0) ||
				(listTmpfetclaimcomm == null || listTmpfetclaimcomm.size() == 0)){
			
			throw new SystemException("批次新增資料時發現資料不足");
		}
		
		this.tmpfetclaimmainService.batchInsertTmpfetclaimmain(listTmpfetclaimmain);
		this.tmpfetclaimkindService.batchInsertTmpfetclaimkind(listTmpfetclaimkind);
		this.tmpfetclaimpayService.batchInsertTmpfetclaimpay(listTmpfetclaimpay);
		this.tmpfetclaimcommService.batchInsertTmpfetclaimcomm(listTmpfetclaimcomm);
		
		Map<String, String> params = new HashMap<String, String>();
		int countMain = tmpfetclaimmainService.countTmpfetclaimmain(params);
		int countPay = tmpfetclaimpayService.countTmpfetclaimpay(params);
		int countKind = tmpfetclaimkindService.countTmpfetclaimkind(params);
		int countCom = tmpfetclaimcommService.countTmpfetclaimcomm(params);
		
		String error = "";
		if(listTmpfetclaimmain.size() != countMain){
			error = error + "Tmpfetclaimmain Excel筆數：" + listTmpfetclaimmain.size() + "，table筆數： " + countMain + "，筆數異常！\\r\\n"; 
		}
		if(listTmpfetclaimkind.size() != countKind){
			error = error + "Tmpfetclaimkind Excel筆數：" + listTmpfetclaimkind.size() + "，table筆數： " + countKind + "，筆數異常！\\r\\n"; 
		}
		if(listTmpfetclaimpay.size() != countPay){
			error = error + "Tmpfetclaimpay Excel筆數：" + listTmpfetclaimpay.size() + "，table筆數： " + countPay + "，筆數異常！\\r\\n"; 
		}		
		if(listTmpfetclaimcomm.size() != countCom){
			error = error + "Tmpfetclaimcomm Excel筆數：" + listTmpfetclaimcomm.size() + "，table筆數： " + countCom + "，筆數異常！\\r\\n"; 
		}
		
		if(!StringUtil.isSpace(error)){
			error = "資料匯入異常：\\r\\n" + error;
			throw new SystemException(error);
		}
	}

	
	
	public static void main(String args[]){

		String tempPath = System.getProperty("java.io.tmpdir");
		System.out.println("tempPath = " + tempPath);
		
		File f = new File("D:\\temp\\b2b2cDev.cer");
		System.out.println("getName = " + f.getName());
	}
	
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}
	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}



	public TmpfetclaimmainService getTmpfetclaimmainService() {
		return tmpfetclaimmainService;
	}



	public void setTmpfetclaimmainService(
			TmpfetclaimmainService tmpfetclaimmainService) {
		this.tmpfetclaimmainService = tmpfetclaimmainService;
	}



	public TmpfetclaimkindService getTmpfetclaimkindService() {
		return tmpfetclaimkindService;
	}



	public void setTmpfetclaimkindService(
			TmpfetclaimkindService tmpfetclaimkindService) {
		this.tmpfetclaimkindService = tmpfetclaimkindService;
	}



	public TmpfetclaimpayService getTmpfetclaimpayService() {
		return tmpfetclaimpayService;
	}



	public void setTmpfetclaimpayService(TmpfetclaimpayService tmpfetclaimpayService) {
		this.tmpfetclaimpayService = tmpfetclaimpayService;
	}



	public TmpfetclaimcommService getTmpfetclaimcommService() {
		return tmpfetclaimcommService;
	}



	public void setTmpfetclaimcommService(
			TmpfetclaimcommService tmpfetclaimcommService) {
		this.tmpfetclaimcommService = tmpfetclaimcommService;
	}
}
