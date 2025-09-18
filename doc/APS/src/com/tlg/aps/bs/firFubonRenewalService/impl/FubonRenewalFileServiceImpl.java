package com.tlg.aps.bs.firFubonRenewalService.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firFubonRenewalService.FubonRenewalFileService;
import com.tlg.aps.vo.Aps034FbDetailVo;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnAs400Data;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirAgtrnBatchFb;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.prpins.entity.FirAgtrnTmpFb;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAgtTocoreInsuredService;
import com.tlg.prpins.service.FirAgtTocoreMainService;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnBatchFbService;
import com.tlg.prpins.service.FirAgtrnBatchMainService;
import com.tlg.prpins.service.FirAgtrnTmpFbService;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程  
 *  mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業**/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class FubonRenewalFileServiceImpl implements FubonRenewalFileService {

	private FirBatchLogService firBatchLogService;
	private FirAgtrnBatchMainService firAgtrnBatchMainService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private FirAgtTocoreMainService firAgtTocoreMainService;
	private FirAgtTocoreInsuredService firAgtTocoreInsuredService;
	private FirAgtrnBatchFbService firAgtrnBatchFbService;
	private FirAgtrnTmpFbService firAgtrnTmpFbService;

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
	public void insertBatchMain(String batchNo, String filename, String userId) throws Exception {
		FirAgtrnBatchMain firAgtrnBatchMain = new FirAgtrnBatchMain();
		firAgtrnBatchMain.setBatchNo(batchNo);
		firAgtrnBatchMain.setFilename(filename);
		firAgtrnBatchMain.setBusinessnature("I00107");
		firAgtrnBatchMain.setFileStatus("S");
		firAgtrnBatchMain.setTransStatus("N");
		firAgtrnBatchMain.setDeleteFlag("N");
		firAgtrnBatchMain.setIcreate(userId);
		firAgtrnBatchMain.setDcreate(new Date());
		firAgtrnBatchMainService.insertFirAgtrnBatchMain(firAgtrnBatchMain);

		FirAgtrnBatchFb firAgtrnBatchFb = new FirAgtrnBatchFb();
		firAgtrnBatchFb.setBatchNo(batchNo);
		firAgtrnBatchFb.setIcreate(userId);
		firAgtrnBatchFb.setDcreate(new Date());
		firAgtrnBatchFbService.insertFirAgtrnBatchFb(firAgtrnBatchFb);
	}

	@Override
	public void insertFirAgtrnTmpFbList(String batchNo, String filename, String userId, List<String> fileDataList)
			throws Exception {
		for (int i = 0; i < fileDataList.size(); i++) {
			FirAgtrnTmpFb firAgtrnTmpFb = new FirAgtrnTmpFb();
			firAgtrnTmpFb.setBatchNo(batchNo);
			firAgtrnTmpFb.setBatchSeq(i + 1);
			firAgtrnTmpFb.setFilename(filename);
			firAgtrnTmpFb.setRawdata(fileDataList.get(i));
			firAgtrnTmpFb.setpStatus("N");
			firAgtrnTmpFb.setDiffFlag("N");
			firAgtrnTmpFb.setIcreate(userId);
			firAgtrnTmpFb.setDcreate(new Date());
			firAgtrnTmpFbService.insertFirAgtrnTmpFb(firAgtrnTmpFb);
		}
	}

	@Override
	public void updateFirAgtrnBatchMain(String batchNo, String userId, Map<String, String> params)
			throws Exception {
		FirAgtrnBatchMain firAgtrnBatchMain = new FirAgtrnBatchMain();
		firAgtrnBatchMain.setBatchNo(batchNo);
		firAgtrnBatchMain.setFileStatus(params.get("fileStatus"));
		firAgtrnBatchMain.setTransStatus(params.get("transStatus"));
		firAgtrnBatchMain.setDataqtyT(
				StringUtil.isSpace(params.get("dataqtyT")) ? null : Integer.parseInt(params.get("dataqtyT")));
		firAgtrnBatchMain.setDataqtyS(
				StringUtil.isSpace(params.get("dataqtyS")) ? null : Integer.parseInt(params.get("dataqtyS")));
		firAgtrnBatchMain.setDataqtyF(
				StringUtil.isSpace(params.get("dataqtyF")) ? null : Integer.parseInt(params.get("dataqtyF")));
		firAgtrnBatchMain.setIupdate(userId);
		firAgtrnBatchMain.setDupdate(new Date());
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
	public void insertFirAgtrnBatchDtl(String batchNo, Integer batchSeq, String userId) throws Exception {
		FirAgtrnBatchDtl firAgtrnBatchDtl = new FirAgtrnBatchDtl();
		firAgtrnBatchDtl.setBatchNo(batchNo);
		firAgtrnBatchDtl.setBatchSeq(batchSeq);
		firAgtrnBatchDtl.setDataStatus("0");
		firAgtrnBatchDtl.setDeleteFlag("N");
		firAgtrnBatchDtl.setSfFlag("N");
		firAgtrnBatchDtl.setIcreate(userId);
		firAgtrnBatchDtl.setDcreate(new Date());
		firAgtrnBatchDtlService.insertFirAgtrnBatchDtl(firAgtrnBatchDtl);
	}

	public void insertFirAgtTocore(String batchNo, int batchSeq, String userId, Map tmpdatas, String handler1code, String comcode, FirAgtrnAs400Data as400Data)
			throws Exception {
		FirAgtTocoreMain firAgtTocoreMain = new FirAgtTocoreMain();
		firAgtTocoreMain.setBatchNo(batchNo);
		firAgtTocoreMain.setBatchSeq(batchSeq);
		firAgtTocoreMain.setIsNew("N");
		firAgtTocoreMain.setRationcode("D");
		firAgtTocoreMain.setOldpolicyno((String) tmpdatas.get("oldpolicyno"));
		firAgtTocoreMain.setHandleridentifynumber((String) tmpdatas.get("handleridentifynumber"));
		firAgtTocoreMain.setHandler1code(handler1code);
		firAgtTocoreMain.setComcode(comcode);
		firAgtTocoreMain.setAgentcode("349347");
		firAgtTocoreMain.setExtracomcode("10" + tmpdatas.get("extracomcode"));
		firAgtTocoreMain.setBusinessnature("I00107");
		firAgtTocoreMain.setChanneltype("22");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		firAgtTocoreMain.setStartdate(sdf.parse((String) tmpdatas.get("startdate")));
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		String date = sdf2.format(new Date());
		firAgtTocoreMain.setMaildate(sdf2.parse(date));
		firAgtTocoreMain.setUpdatedate(sdf2.parse(date));
		firAgtTocoreMain.setSerialno1(1);
		firAgtTocoreMain.setMortgageepcode1("0120000");
		firAgtTocoreMain.setCollateralnumber1((String) tmpdatas.get("collateralnumber1"));
		firAgtTocoreMain.setAddressno(1);
		
		firAgtTocoreMain.setAddresscode((String) tmpdatas.get("addresscode"));
		firAgtTocoreMain.setAddressname((String) tmpdatas.get("addressname"));
		firAgtTocoreMain.setAddressdetailinfo((String) tmpdatas.get("address"));
		
		firAgtTocoreMain.setRepeatpolicyno((String) tmpdatas.get("dquakeNo"));
		firAgtTocoreMain.setPossessnaturecode("A0001A8");
		firAgtTocoreMain.setBuildingno(1);
		firAgtTocoreMain.setPropAddressno(1);

		firAgtTocoreMain.setWallmaterial((String) tmpdatas.get("wallno"));
		firAgtTocoreMain.setRoofmaterial((String) tmpdatas.get("roofno"));
		firAgtTocoreMain.setStructure((String) tmpdatas.get("structure"));
		firAgtTocoreMain.setStructureText((String) tmpdatas.get("structureText"));
		firAgtTocoreMain.setSumfloors((String) tmpdatas.get("sumfloors"));
		firAgtTocoreMain.setBuildarea((String) tmpdatas.get("area"));
		firAgtTocoreMain.setBuildyears((String) tmpdatas.get("buildyears"));
		firAgtTocoreMain.setKindcodeF("FR3");
		firAgtTocoreMain.setKindnameF("住宅火災保險");
		firAgtTocoreMain.setItemcodeF("11");
		firAgtTocoreMain.setItemnameF("建築物");
		firAgtTocoreMain.setItemnatureF("不動產");
		firAgtTocoreMain.setBuildingnoF(1);
		firAgtTocoreMain.setHighrisefee((BigDecimal) tmpdatas.get("highrisefee"));
		firAgtTocoreMain.setAmountF((String) tmpdatas.get("amountF"));
		firAgtTocoreMain.setPremiumF(tmpdatas.get("premiumF").toString());
		firAgtTocoreMain.setCommrateF(new BigDecimal((String) tmpdatas.get("commrateF")));
		firAgtTocoreMain.setKindcodeQ("FR2");
		firAgtTocoreMain.setKindnameQ("政策性地震險");
		firAgtTocoreMain.setItemcodeQ("11");
		firAgtTocoreMain.setItemnameQ("建築物");
		firAgtTocoreMain.setItemnatureQ("不動產");
		firAgtTocoreMain.setBuildingnoQ(1);
		firAgtTocoreMain.setAmountQ((String) tmpdatas.get("amountQ"));
		firAgtTocoreMain.setPremiumQ(tmpdatas.get("premiumQ").toString());
		firAgtTocoreMain.setCommrateQ(new BigDecimal((String) tmpdatas.get("commrateQ")));
		firAgtTocoreMain.setClauseSendtype("2");// 固定值2 預設紙本條款
		firAgtTocoreMain.setPremiumT(tmpdatas.get("premiumT").toString());
		firAgtTocoreMain.setAmountFLast((Long) tmpdatas.get("amtFLast"));
		firAgtTocoreMain.setAmountQLast((Long) tmpdatas.get("amtQLast"));
		firAgtTocoreMain.setPremiumFLast((Long) tmpdatas.get("premFLast"));
		firAgtTocoreMain.setPremiumQLast((Long) tmpdatas.get("premQLast"));
		//mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 
		firAgtTocoreMain.setAmountFAgt((String) tmpdatas.get("amountFAgt"));
		firAgtTocoreMain.setAmountQAgt((String) tmpdatas.get("amountQ"));
		firAgtTocoreMain.setIcreate(userId);
		firAgtTocoreMain.setDcreate(new Date());
		firAgtTocoreMainService.insertFirAgtTocoreMain(firAgtTocoreMain);

		FirAgtTocoreInsured firAgtTocoreInsured = new FirAgtTocoreInsured();
		firAgtTocoreInsured.setBatchNo(batchNo);
		firAgtTocoreInsured.setBatchSeq(batchSeq);
		firAgtTocoreInsured.setInsuredSeq(1);
		firAgtTocoreInsured.setDomicile("TW");
		firAgtTocoreInsured.setCountryename("TW");
		firAgtTocoreInsured.setIcreate(userId);
		firAgtTocoreInsured.setDcreate(new Date());
		firAgtTocoreInsured.setIshighdengeroccupation("0");// 高危職業 固定給0 高風險
		firAgtTocoreInsured.setBirthday(new SimpleDateFormat("yyyy/MM/dd").parse("1912/01/01"));// 生日 都給預設民國1年1月1日

		firAgtTocoreInsured.setInsuredflag("2");
		firAgtTocoreInsured.setInsurednature((String) tmpdatas.get("insuredNature2"));
		firAgtTocoreInsured.setInsuredname((String) tmpdatas.get("nameA"));
		firAgtTocoreInsured.setIdentifytype((String) tmpdatas.get("idtype2"));
		firAgtTocoreInsured.setIdentifynumber((String) tmpdatas.get("idA"));
		firAgtTocoreInsured.setPhonenumber((String) tmpdatas.get("phoneA"));
		firAgtTocoreInsured.setMobile((String) tmpdatas.get("mobileA"));
		firAgtTocoreInsured.setPostcode((String) tmpdatas.get("postcodeA"));
		firAgtTocoreInsured.setPostaddress((String) tmpdatas.get("addressA"));
		firAgtTocoreInsuredService.insertFirAgtTocoreInsured(firAgtTocoreInsured);

		firAgtTocoreInsured.setInsuredflag("1");
		firAgtTocoreInsured.setInsurednature((String) tmpdatas.get("insuredNature1"));
		firAgtTocoreInsured.setInsuredname((String) tmpdatas.get("nameI"));
		firAgtTocoreInsured.setIdentifytype((String) tmpdatas.get("idtype1"));
		firAgtTocoreInsured.setIdentifynumber((String) tmpdatas.get("idI"));
		firAgtTocoreInsured.setPhonenumber((String) tmpdatas.get("phoneI"));
		firAgtTocoreInsured.setMobile((String) tmpdatas.get("mobileI"));
		firAgtTocoreInsured.setPostcode((String) tmpdatas.get("postcodeI"));
		firAgtTocoreInsured.setPostaddress((String) tmpdatas.get("addressI"));
		firAgtTocoreInsuredService.insertFirAgtTocoreInsured(firAgtTocoreInsured);

		FirAgtrnTmpFb firAgtrnTmpFb = new FirAgtrnTmpFb();
		firAgtrnTmpFb.setBatchNo(batchNo);
		firAgtrnTmpFb.setBatchSeq(batchSeq);
		firAgtrnTmpFb.setpStatus("Y");
		firAgtrnTmpFb.setDiffFlag((String) tmpdatas.get("diffFlag"));
		firAgtrnTmpFb.setDiffReason((String) tmpdatas.get("diffReason"));
		firAgtrnTmpFb.setFbPolicynoF((String) tmpdatas.get("firPolicyno"));
		firAgtrnTmpFb.setFbPolicynoQ((String) tmpdatas.get("quakePolicyno"));
		firAgtrnTmpFb.setFbNameI((String) tmpdatas.get("fbNameI"));
		firAgtrnTmpFb.setFbIdI((String) tmpdatas.get("fbIdI"));
		firAgtrnTmpFb.setFbStartdate(sdf.parse((String) tmpdatas.get("fbStartdate")));
		firAgtrnTmpFb.setFbEnddate(sdf.parse((String) tmpdatas.get("fbEnddate")));
		firAgtrnTmpFb.setFbAddress((String) tmpdatas.get("address"));
		firAgtrnTmpFb.setFbMortgagee((String) tmpdatas.get("fbMortgagee"));
		firAgtrnTmpFb.setFbWallno((String) (tmpdatas.get("fbWallno")));
		firAgtrnTmpFb.setFbSumfloors((String) tmpdatas.get("sumfloors"));
		firAgtrnTmpFb.setFbBuildarea((String) tmpdatas.get("area"));
		firAgtrnTmpFb.setFbUseNature((String) tmpdatas.get("useNature"));
		firAgtrnTmpFb.setFbAmountF((String) tmpdatas.get("amountF"));
		firAgtrnTmpFb.setFbAmountQ((String) tmpdatas.get("amountQ"));
		firAgtrnTmpFb.setFbColInsurcNo((String)tmpdatas.get("collateralnumber1"));
		firAgtrnTmpFb.setFbIdA((String) tmpdatas.get("idA"));
		firAgtrnTmpFb.setFbNameA((String) tmpdatas.get("nameA"));
		firAgtrnTmpFb.setFbInsCom((String) tmpdatas.get("insCom"));
		firAgtrnTmpFb.setFbProcessCenter((String) tmpdatas.get("extracomcode"));
		firAgtrnTmpFb.setFbSalesName((String) tmpdatas.get("salesName"));
		firAgtrnTmpFb.setFbSalesId((String) tmpdatas.get("handleridentifynumber"));
		//AS400帶出的資料 start
		firAgtrnTmpFb.setNameI(as400Data.getNameI());
		firAgtrnTmpFb.setIdI(as400Data.getIdI());
		firAgtrnTmpFb.setStartdate(as400Data.getStartdate());
		firAgtrnTmpFb.setEnddate(as400Data.getEnddate());
		firAgtrnTmpFb.setAddressdetailinfo(as400Data.getAddressdetail());
		//mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程  
		firAgtrnTmpFb.setWallno(as400Data.getWallmaterial());
		firAgtrnTmpFb.setSumfloors(as400Data.getSumfloors());
		firAgtrnTmpFb.setBuildarea(as400Data.getBuildarea());
		firAgtrnTmpFb.setAmountF(as400Data.getAmountF());
		firAgtrnTmpFb.setAmountQ(as400Data.getAmountQ());
		firAgtrnTmpFb.setIdA(as400Data.getIdA());
		firAgtrnTmpFb.setNameA(as400Data.getNameA());
		//AS400帶出的資料 end
		firAgtrnTmpFb.setIupdate(userId);
		firAgtrnTmpFb.setDupdate(new Date());
		firAgtrnTmpFbService.updateFirAgtrnTmpFb(firAgtrnTmpFb);
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
	public void updateFirAgtTocoreAndAgtrnBatchDtl(Aps034FbDetailVo fbDetailVo, String userId)
			throws Exception {
		Date sysDate = new Date();
		
		FirAgtTocoreMain firAgtTocoreMain = new FirAgtTocoreMain();
		BeanUtils.copyProperties(fbDetailVo, firAgtTocoreMain);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		firAgtTocoreMain.setIntroducerid(fbDetailVo.getHandleridentifynumber());//介紹人
		firAgtTocoreMain.setBusinessnature("I00107");//業務來源富邦固定帶I00107
		firAgtTocoreMain.setStartdate(sdf.parse(fbDetailVo.getStartdate()));
		firAgtTocoreMain.setIupdate(userId);
		firAgtTocoreMain.setDupdate(sysDate);
		firAgtTocoreMain.setAmountF(fbDetailVo.getAmountF().replace(",", ""));
		firAgtTocoreMain.setAmountQ(fbDetailVo.getAmountQ().replace(",", ""));
		firAgtTocoreMain.setPremiumF(fbDetailVo.getPremiumF().replace(",", ""));
		firAgtTocoreMain.setPremiumQ(fbDetailVo.getPremiumQ().replace(",", ""));
		firAgtTocoreMain.setAmountFAgt(fbDetailVo.getAmountFAgt().replace(",", ""));
		firAgtTocoreMain.setAmountQAgt(fbDetailVo.getAmountQAgt().replace(",", ""));
		firAgtTocoreMain.setPremiumFAgt(fbDetailVo.getPremiumFAgt().replace(",", ""));
		firAgtTocoreMain.setPremiumQAgt(fbDetailVo.getPremiumQAgt().replace(",", ""));
		
		firAgtTocoreMainService.updateFirAgtTocoreMain(firAgtTocoreMain);

		List<FirAgtTocoreInsured> insuredList = fbDetailVo.getInsuredList();
		for(int i=0; i<insuredList.size(); i++) {
			FirAgtTocoreInsured firAgtTocoreInsured = insuredList.get(i);
			firAgtTocoreInsured.setBatchNo(fbDetailVo.getBatchNo());
			firAgtTocoreInsured.setBatchSeq(fbDetailVo.getBatchSeq());
			firAgtTocoreInsured.setBirthday(sdf.parse(DateUtils.transDate(firAgtTocoreInsured.getStrBirthday(),"/")));
			firAgtTocoreInsured.setIupdate(userId);
			firAgtTocoreInsured.setDupdate(sysDate);
			firAgtTocoreInsuredService.updateFirAgtTocoreInsured(firAgtTocoreInsured);
		}
		
		FirAgtrnBatchDtl firAgtrnBatchDtl = new FirAgtrnBatchDtl();
		BeanUtils.copyProperties(fbDetailVo, firAgtrnBatchDtl);
		firAgtrnBatchDtl.setBatchNo(fbDetailVo.getBatchNo());
		firAgtrnBatchDtl.setBatchSeq(fbDetailVo.getBatchSeq());
		firAgtrnBatchDtl.setIupdate(userId);
		firAgtrnBatchDtl.setDupdate(sysDate);
		if ("Y".equals(fbDetailVo.getSfFlag())) {
			firAgtrnBatchDtl.setDataStatus("A");//勾選為剔退件，不轉核心
			firAgtrnBatchDtl.setSfUser(userId);
			firAgtrnBatchDtl.setSfDate(sysDate);
		}else if("N".equals(fbDetailVo.getSfFlag()) && !StringUtil.isSpace(fbDetailVo.getSfUser()) ) {
			firAgtrnBatchDtl.setDataStatus("2");//當取消勾選為剔退件，將資料狀態改回暫存成功
			firAgtrnBatchDtl.setSfUser(userId);
			firAgtrnBatchDtl.setSfDate(sysDate);
		}
		
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

	public FirAgtrnBatchFbService getFirAgtrnBatchFbService() {
		return firAgtrnBatchFbService;
	}

	public void setFirAgtrnBatchFbService(FirAgtrnBatchFbService firAgtrnBatchFbService) {
		this.firAgtrnBatchFbService = firAgtrnBatchFbService;
	}

	public FirAgtrnTmpFbService getFirAgtrnTmpFbService() {
		return firAgtrnTmpFbService;
	}

	public void setFirAgtrnTmpFbService(FirAgtrnTmpFbService firAgtrnTmpFbService) {
		this.firAgtrnTmpFbService = firAgtrnTmpFbService;
	}

}
