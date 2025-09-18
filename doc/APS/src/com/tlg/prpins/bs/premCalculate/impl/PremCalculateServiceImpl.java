package com.tlg.prpins.bs.premCalculate.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.bs.firCalService.FirF01CalPremService;
import com.tlg.prpins.bs.firCalService.FirF02CalPremService;
import com.tlg.prpins.bs.pbCalService.PbCalPremService;
import com.tlg.prpins.bs.premCalculate.CalPremBaseService;
import com.tlg.prpins.bs.premCalculate.PremCalculateService;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;
import com.tlg.prpins.entity.PbPremcalcCklist;
import com.tlg.prpins.entity.PbPremcalcClause;
import com.tlg.prpins.entity.PbPremcalcTmp;
import com.tlg.prpins.entity.PbPremcalcTmpdtl;
import com.tlg.prpins.entity.PbQueryDetail;
import com.tlg.prpins.service.FirPremcalcTmpService;
import com.tlg.prpins.service.FirPremcalcTmpdtlService;
import com.tlg.prpins.service.PbPremcalcCklistService;
import com.tlg.prpins.service.PbPremcalcClauseService;
import com.tlg.prpins.service.PbPremcalcTmpService;
import com.tlg.prpins.service.PbPremcalcTmpdtlService;
import com.tlg.prpins.service.PbQueryDetailService;
import com.tlg.prpins.util.FirF01KindCodeUtil;
import com.tlg.prpins.util.FirF02KindCodeUtil;
import com.tlg.prpins.util.PbKindCodeUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PremCalculateServiceImpl implements PremCalculateService{

	private FirPremcalcTmpService firPremcalcTmpService;
	private FirPremcalcTmpdtlService firPremcalcTmpdtlService;
	private PbPremcalcCklistService pbPremcalcCklistService;
	private FirF02CalPremService firF02CalPremService;
	private FirF01CalPremService firF01CalPremService;
	private CalPremBaseService calPremBaseService;
	private PbPremcalcClauseService pbPremcalcClauseService;
	
	private PbPremcalcTmpService pbPremcalcTmpService;
	private PbPremcalcTmpdtlService pbPremcalcTmpdtlService;
	private PbQueryDetailService pbQueryDetailService;
	private PbCalPremService pbCalPremService;
	
	
	@Override
	public void insertFirCalData(FirPremcalcTmp firPremcalcTmp)
			throws SystemException,Exception {
		
		if(firPremcalcTmp == null ){
			throw new SystemException("新增試算暫存檔失敗");
		}
		if(firPremcalcTmp.getFirPremcalcTmpdtlList() == null){
			throw new SystemException("新增試算明細暫存檔失敗");
		}
		
		Result result = firPremcalcTmpService.insertFirPremcalcTmp(firPremcalcTmp);
		if(result.getResObject() == null){
			throw new SystemException("新增試算暫存檔失敗");
		}
		firPremcalcTmp = (FirPremcalcTmp)result.getResObject();
		for(FirPremcalcTmpdtl firPremcalcTmpdtl:firPremcalcTmp.getFirPremcalcTmpdtlList()){
			firPremcalcTmpdtl.setOidFirPremcalcTmp(firPremcalcTmp.getOid());
			result = firPremcalcTmpdtlService.insertFirPremcalcTmpdtl(firPremcalcTmpdtl);
			if(result.getResObject() == null){
				throw new SystemException("新增試算明細暫存檔失敗");
			}
		}
		
		if(firPremcalcTmp.getPbPremCklist() != null){
			String calcDate =  DateUtils.getDateDay(firPremcalcTmp.getCalcDate(), "", 1);
			//火險下的公共意外責任險問卷F01_PB
			BigDecimal maxLimit = calPremBaseService.getFirInsRate("F01_PB", "Q_PB", "4", calcDate, "CKLIST-MAX", "", "", "", "");
			if(maxLimit == null){	
				throw new SystemException("無法讀取核保最高加減費，無法計算保費。");
			}
			
			for(PbPremcalcCklist pbPremcalcCklist:firPremcalcTmp.getPbPremCklist()){
				pbPremcalcCklist.setOidFirPremcalcTmp(firPremcalcTmp.getOid());
				result = pbPremcalcCklistService.insertPbPremcalcCklist(pbPremcalcCklist);
				if(result.getResObject() == null){
					throw new SystemException("新增公共意外責任險試算詢問表失敗");
				}
			}
			
			Map params = new HashMap();
			params.put("oidFtrPremcalcTmp", firPremcalcTmp.getOid());
			params.put("calcDate", calcDate);
			result = pbPremcalcCklistService.findPbPremcalcCklistForScore(params);
			if(result.getResObject() != null){
				ArrayList<Map> list = (ArrayList<Map>)result.getResObject();
				for(Map map1:list){
					BigDecimal oid = (BigDecimal)map1.get("oid");
					BigDecimal cklistScore = (BigDecimal)map1.get("cklistScore");
					BigDecimal cklistY = (BigDecimal)map1.get("cklistY");
					BigDecimal cklistN = (BigDecimal)map1.get("cklistN");
					BigDecimal groupLimitUpper = (BigDecimal)map1.get("groupLimitUpper");
					BigDecimal groupLimitLower = (BigDecimal)map1.get("groupLimitLower");
					
					result = pbPremcalcCklistService.findPbPremcalcCklistByOid(oid);
					if(result.getResObject() == null){
						throw new SystemException("新增公共意外責任險試算詢問表失敗");
					}

					PbPremcalcCklist pbPremcalcCklist = (PbPremcalcCklist)result.getResObject();
					
					pbPremcalcCklist.setCklistScore(cklistScore);
					pbPremcalcCklist.setCklistY(cklistY);
					pbPremcalcCklist.setCklistN(cklistN);
					pbPremcalcCklist.setGroupLimitLower(groupLimitLower);
					pbPremcalcCklist.setGroupLimitUpper(groupLimitUpper);
					pbPremcalcCklist.setMaxLimit(maxLimit);
					result = pbPremcalcCklistService.updatePbPremcalcCklist(pbPremcalcCklist);
					if(result.getResObject() == null){
						throw new SystemException("更新公共意外責任險試算詢問表失敗");
					}
				}
			}
		}
		if(firPremcalcTmp.getPbPremcalcClauseList() != null){
			for(PbPremcalcClause pbPremcalcClause:firPremcalcTmp.getPbPremcalcClauseList()){
				pbPremcalcClause.setOidFirPremcalcTmp(firPremcalcTmp.getOid());
				result = pbPremcalcClauseService.insertPbPremcalcClause(pbPremcalcClause);
				if(result.getResObject() == null){
					throw new SystemException("新增試算明細暫存檔失敗");
				}
			}
		}
		
	}

	@Override
	public void updateFirCalData(FirPremcalcTmp firPremcalcTmp)
			throws SystemException,Exception {
		if(firPremcalcTmp == null ){
			throw new SystemException("更新試算暫存檔失敗");
		}
		if(firPremcalcTmp.getFirPremcalcTmpdtlList() == null){
			throw new SystemException("更新試算明細暫存檔失敗");
		}
		
		Result result = firPremcalcTmpService.updateFirPremcalcTmp(firPremcalcTmp);
		if(result.getResObject() == null){
			throw new SystemException("更新試算暫存檔失敗");
		}
		firPremcalcTmp = (FirPremcalcTmp)result.getResObject();
		for(FirPremcalcTmpdtl firPremcalcTmpdtl:firPremcalcTmp.getFirPremcalcTmpdtlList()){
			firPremcalcTmpdtl.setOidFirPremcalcTmp(firPremcalcTmp.getOid());
			result = firPremcalcTmpdtlService.updateFirPremcalcTmpdtl(firPremcalcTmpdtl);
			if(result.getResObject() == null){
				throw new SystemException("更新試算暫存檔失敗");
			}
		}
		if(firPremcalcTmp.getPbPremcalcClauseList() != null){
			for(PbPremcalcClause pbPremcalcClause:firPremcalcTmp.getPbPremcalcClauseList()){
				pbPremcalcClause.setOidFirPremcalcTmp(firPremcalcTmp.getOid());
				result = pbPremcalcClauseService.updatePbPremcalcClause(pbPremcalcClause);
				if(result.getResObject() == null){
					throw new SystemException("更新試算附約暫存檔-失敗");
				}
			}
		}
	}
	
	@Override
	public void insertPbCalData(PbPremcalcTmp pbPremcalcTmp)
			throws SystemException,Exception {
		
		if(pbPremcalcTmp == null ){
			throw new SystemException("新增試算暫存檔失敗");
		}
		if(pbPremcalcTmp.getPbPremcalcTmpdtlList() == null){
			throw new SystemException("新增試算明細暫存檔失敗");
		}
		
		Result result = pbPremcalcTmpService.insertPbPremcalcTmp(pbPremcalcTmp);
		if(result.getResObject() == null){
			throw new SystemException("新增試算暫存檔失敗");
		}
		pbPremcalcTmp = (PbPremcalcTmp)result.getResObject();
		for(PbPremcalcTmpdtl pbPremcalcTmpdtl:pbPremcalcTmp.getPbPremcalcTmpdtlList()){
			pbPremcalcTmpdtl.setOidPbPremcalcTmp(pbPremcalcTmp.getOid());
			result = pbPremcalcTmpdtlService.insertPbPremcalcTmpdtl(pbPremcalcTmpdtl);
			if(result.getResObject() == null){
				throw new SystemException("新增試算明細暫存檔失敗");
			}
		}
		
		if(pbPremcalcTmp.getPbQueryDetailList() != null){
			String calcDate =  DateUtils.getDateDay(pbPremcalcTmp.getCalcDate(), "", 1);
			//火險下的公共意外責任險問卷F01_PB
			Map valueMap = calPremBaseService.getPbInsRate("PB", "PB", "0", pbPremcalcTmp.getCalcType(), calcDate, "QUERY_MAX", "", "", "", "");
			if(valueMap == null || (valueMap != null && valueMap.size() == 0)){	
				throw new SystemException("無法讀取核保最高加減費，無法計算保費。");
			}
			BigDecimal maxLimitUpper = (BigDecimal)valueMap.get("PARA_VALUE");
			BigDecimal maxLimitLower = maxLimitUpper.multiply(new BigDecimal("-1"));
			for(PbQueryDetail pbQueryDetail:pbPremcalcTmp.getPbQueryDetailList()){
				pbQueryDetail.setOidPbPremcalcTmp(pbPremcalcTmp.getOid());
				result = pbQueryDetailService.insertPbQueryDetail(pbQueryDetail);
				if(result.getResObject() == null){
					throw new SystemException("新增公共意外責任險試算詢問表失敗");
				}
			}
			Map params = new HashMap();
			
			params.put("oidPbPremcalcTmp", pbPremcalcTmp.getOid());
			params.put("calcDate", calcDate);
			params.put("calcType", pbPremcalcTmp.getCalcType());
			result = pbQueryDetailService.findPbQueryDetailForPbScore(params);
			if(result.getResObject() != null){
				ArrayList<Map> list = (ArrayList<Map>)result.getResObject();
				for(Map map1:list){
					BigDecimal oid = (BigDecimal)map1.get("oid");
					BigDecimal cklistScore = (BigDecimal)map1.get("cklistScore");
					BigDecimal cklistY = (BigDecimal)map1.get("cklistY");
					BigDecimal cklistN = (BigDecimal)map1.get("cklistN");
					BigDecimal groupLimitUpper = (BigDecimal)map1.get("groupLimitUpper");
					BigDecimal groupLimitLower = (BigDecimal)map1.get("groupLimitLower");
					
					result = pbQueryDetailService.findPbQueryDetailByOid(oid);
					if(result.getResObject() == null){
						throw new SystemException("新增公共意外責任險試算詢問表失敗");
					}

					PbQueryDetail pbQueryDetail = (PbQueryDetail)result.getResObject();
					
					pbQueryDetail.setCklistScore(cklistScore);
					pbQueryDetail.setCklistY(cklistY);
					pbQueryDetail.setCklistN(cklistN);
					pbQueryDetail.setGroupLimitLower(groupLimitLower);
					pbQueryDetail.setGroupLimitUpper(groupLimitUpper);
					pbQueryDetail.setMaxLimitUpper(maxLimitUpper);
					pbQueryDetail.setMaxLimitLower(maxLimitLower);
					result = pbQueryDetailService.updatePbQueryDetail(pbQueryDetail);
					if(result.getResObject() == null){
						throw new SystemException("更新公共意外責任險試算詢問表失敗");
					}
				}
			}
		}
		if(pbPremcalcTmp.getPbPremcalcClauseList() != null){
			for(PbPremcalcClause pbPremcalcClause:pbPremcalcTmp.getPbPremcalcClauseList()){
				pbPremcalcClause.setOidFirPremcalcTmp(pbPremcalcTmp.getOid());
				result = pbPremcalcClauseService.insertPbPremcalcClause(pbPremcalcClause);
				if(result.getResObject() == null){
					throw new SystemException("新增試算明細暫存檔失敗");
				}
			}
		}
		
	}

	@Override
	public void updatePbCalData(PbPremcalcTmp pbPremcalcTmp)
			throws SystemException,Exception {
		if(pbPremcalcTmp == null ){
			throw new SystemException("更新試算暫存檔失敗");
		}
		if(pbPremcalcTmp.getPbPremcalcTmpdtlList() == null){
			throw new SystemException("更新試算明細暫存檔失敗");
		}
		
		Result result = pbPremcalcTmpService.updatePbPremcalcTmp(pbPremcalcTmp);
		if(result.getResObject() == null){
			throw new SystemException("更新試算暫存檔失敗");
		}
		pbPremcalcTmp = (PbPremcalcTmp)result.getResObject();
		for(PbPremcalcTmpdtl pbPremcalcTmpdtl:pbPremcalcTmp.getPbPremcalcTmpdtlList()){
			pbPremcalcTmpdtl.setOidPbPremcalcTmp(pbPremcalcTmp.getOid());
			result = pbPremcalcTmpdtlService.updatePbPremcalcTmpdtl(pbPremcalcTmpdtl);
			if(result.getResObject() == null){
				throw new SystemException("更新試算暫存檔失敗");
			}
		}
		if(pbPremcalcTmp.getPbPremcalcClauseList() != null){
			for(PbPremcalcClause pbPremcalcClause:pbPremcalcTmp.getPbPremcalcClauseList()){
				pbPremcalcClause.setOidFirPremcalcTmp(pbPremcalcTmp.getOid());
				result = pbPremcalcClauseService.updatePbPremcalcClause(pbPremcalcClause);
				if(result.getResObject() == null){
					throw new SystemException("更新試算附約暫存檔-失敗");
				}
			}
		}
	}


	@Override
	public void firCalPrem(FirPremcalcTmp firPremcalcTmp)
			throws SystemException, Exception {
		
		String calcDate =  DateUtils.getDateDay(firPremcalcTmp.getCalcDate(), "", 1);
		String channelType = firPremcalcTmp.getChannelType();
		ArrayList<FirPremcalcTmpdtl> firPremcalcTmpdtlList = firPremcalcTmp.getFirPremcalcTmpdtlList();
		
 
		if(firPremcalcTmpdtlList != null && firPremcalcTmpdtlList.size() > 0){
			//主險
			String mainIns = "FR2,FR3,RFA,RFB,RFC,RFD,RFE,Q_FB1,Q_PB,FB1";
			ArrayList<FirPremcalcTmpdtl> highPriorityList = new ArrayList<FirPremcalcTmpdtl>();
			ArrayList<FirPremcalcTmpdtl> lowPriorityList = new ArrayList<FirPremcalcTmpdtl>();
			//找出優先計算的，在將較低優先性的加入
			for(FirPremcalcTmpdtl firPremcalcTmpdtl:firPremcalcTmpdtlList){
				if(mainIns.indexOf(firPremcalcTmpdtl.getKindcode()) != -1){
					highPriorityList.add(firPremcalcTmpdtl);
				}else{
					lowPriorityList.add(firPremcalcTmpdtl);
				}
			}
			highPriorityList.addAll(lowPriorityList);
			//記錄FR3住宅火險或RFB居綜住火，RF10-住家綠能升級附加條款會用到
			Map fr3Map = new HashMap();
			//FB1商火會使用的核保參數
			BigDecimal[] fb1UndwrtAry = null;
			BigDecimal sumPremium = BigDecimal.ZERO;
			for(FirPremcalcTmpdtl firPremcalcTmpdtl:highPriorityList){
				Map map = new HashMap();
				String riskcode = StringUtil.nullToSpace(firPremcalcTmpdtl.getRiskcode());
				String kindcode = StringUtil.nullToSpace(firPremcalcTmpdtl.getKindcode());
				String paraType = StringUtil.nullToSpace(firPremcalcTmpdtl.getParaType());
				String para01 = StringUtil.nullToSpace(firPremcalcTmpdtl.getPara01());
				String para02 = StringUtil.nullToSpace(firPremcalcTmpdtl.getPara02());
				String para03 = StringUtil.nullToSpace(firPremcalcTmpdtl.getPara03());
				String para04 = StringUtil.nullToSpace(firPremcalcTmpdtl.getPara04());
				String para05 = StringUtil.nullToSpace(firPremcalcTmpdtl.getPara05());
				String para06 = StringUtil.nullToSpace(firPremcalcTmpdtl.getPara06());
				if("F01".equals(riskcode) || "F01_PB".equals(riskcode)){
					
					//Q_FB1	鎮店保-商業火險(非正規計算公式，因而險種前方多一個Q)
					if((FirF01KindCodeUtil.kindCode.Q_FB1.toString()).equals(kindcode)){
						map = firF01CalPremService.getQfb1Prem(calcDate, channelType, riskcode, kindcode, para01, para02, para03, para04, para05, para06);
					}
					//Q_B1	鎮店保-爆炸險(非正規計算公式，因而險種前方多一個Q)
					if((FirF01KindCodeUtil.kindCode.Q_B1.toString()).equals(kindcode)){
						map = firF01CalPremService.getQb1Prem(calcDate, channelType, riskcode, kindcode, para01);
					}
					//Q_BB	鎮店保-煙燻險(非正規計算公式，因而險別前方多一個Q)
					if((FirF01KindCodeUtil.kindCode.Q_BB.toString()).equals(kindcode)){
						map = firF01CalPremService.getQb1Prem(calcDate, channelType, riskcode, kindcode, para01);
					}
					//Q_PB鎮店保-公共意外責任險(此為簡化版公式，險別代號前方多一個Q)
					if((FirF01KindCodeUtil.kindCode.Q_PB.toString()).equals(kindcode)){
						map = firF01CalPremService.getQpbPrem(firPremcalcTmp, calcDate, channelType, riskcode, kindcode, para01, para02, para03, para04, para05, para06);
					}
					//FB1商業火災保險
					if((FirF01KindCodeUtil.kindCode.FB1.toString()).equals(kindcode)){
						map = firF01CalPremService.getFb1Prem(firPremcalcTmp, calcDate, channelType, riskcode, kindcode, paraType, para01, para02, para03, para04, para05, para06);
						firPremcalcTmp.setFireOperating((BigDecimal)map.get("businessFee")); //營業加費
						firPremcalcTmp.setFireStructure((String)map.get("structureno")); //建物等級
						firPremcalcTmp.setFireBaserate((BigDecimal)map.get("baseRate")); //火險基本費率
						firPremcalcTmp.setFireHigh((BigDecimal)map.get("highFee")); //火險基本費率
						
						fb1UndwrtAry = (BigDecimal[])map.get("fb1UndwrtAry");
					}
					//B1爆炸險
					if((FirF01KindCodeUtil.kindCode.B1.toString()).equals(kindcode)){
						map = firF01CalPremService.getB1Prem(firPremcalcTmp, calcDate, channelType, riskcode, kindcode, para01, para02, para03, para04, fb1UndwrtAry);
					}
					
					//B4航空器墜落、機動車輛碰撞險
					if((FirF01KindCodeUtil.kindCode.B4.toString()).equals(kindcode)){
						map = firF01CalPremService.getB4Prem(firPremcalcTmp, calcDate, channelType, riskcode, kindcode, para01, para02, para03, fb1UndwrtAry);
					}
					
					//B5罷工、暴動、民眾騷擾、惡意破壞行為險
					if((FirF01KindCodeUtil.kindCode.B5.toString()).equals(kindcode)){
						map = firF01CalPremService.getB5Prem(firPremcalcTmp, calcDate, channelType, riskcode, kindcode, para01, para02, para03, fb1UndwrtAry);
					}
					
					//B6自動消防裝置滲漏險
					if((FirF01KindCodeUtil.kindCode.B6.toString()).equals(kindcode)){
						map = firF01CalPremService.getB6Prem(firPremcalcTmp, calcDate, channelType, riskcode, kindcode, para01, para02, para03, para04, fb1UndwrtAry);
					}
					
					//B7竊盜險
					if((FirF01KindCodeUtil.kindCode.B7.toString()).equals(kindcode)){
						map = firF01CalPremService.getB7Prem(firPremcalcTmp, calcDate, channelType, riskcode, kindcode, para01, para02, para03, para04, fb1UndwrtAry);
					}
					
					//BB煙燻險
					if((FirF01KindCodeUtil.kindCode.BB.toString()).equals(kindcode)){
						map = firF01CalPremService.getBBPrem(firPremcalcTmp, calcDate, channelType, riskcode, kindcode, para01, para02, para03, fb1UndwrtAry);
					}
					
					//BD水漬險
					if((FirF01KindCodeUtil.kindCode.BD.toString()).equals(kindcode)){
						map = firF01CalPremService.getBDPrem(firPremcalcTmp, calcDate, channelType, riskcode, kindcode, para01, para02, para03, para04, fb1UndwrtAry);
					}

				}
				if("F02".equals(riskcode)){
					
					//FR2、RFA 基本地震保險
					if((FirF02KindCodeUtil.kindCode.FR2.toString()).equals(kindcode) || (FirF02KindCodeUtil.kindCode.RFA.toString()).equals(kindcode)){
						map = firF02CalPremService.getFR2Prem(calcDate, channelType, riskcode, kindcode, para01);
					}
					//FR3、RFB 住宅火災
					if((FirF02KindCodeUtil.kindCode.FR3.toString()).equals(kindcode) || (FirF02KindCodeUtil.kindCode.RFB.toString()).equals(kindcode)){
						map = firF02CalPremService.getFR3Prem(calcDate, channelType, riskcode, kindcode, paraType, para01, para02, para03, para04, para05, para06);
						BigDecimal firBaseRate = (BigDecimal)map.get("firBaseRate");
						BigDecimal firHigh = (BigDecimal)map.get("firHigh");
						BigDecimal firOperating = (BigDecimal)map.get("firOperating");
						String firStructure = (String)map.get("firStructure");
						//設定特殊值					
						firPremcalcTmp.setFireBaserate(firBaseRate);
						firPremcalcTmp.setFireHigh(firHigh);
						firPremcalcTmp.setFireOperating(firOperating);
						firPremcalcTmp.setFireStructure(firStructure);
						//將主險保費資料放入map暫存，供後續使用
						fr3Map.putAll(map);
					}
					//RFC 家庭財務損失保險-機車火災
					if((FirF02KindCodeUtil.kindCode.RFC.toString()).equals(kindcode)){
						map = firF02CalPremService.getRFCPrem(calcDate, channelType, riskcode, kindcode, para01);
					}
					//RFD 家庭財物被竊損失保險
					if((FirF02KindCodeUtil.kindCode.RFD.toString()).equals(kindcode)){
						map = firF02CalPremService.getRFDPrem(calcDate, channelType, riskcode, kindcode, para01, para02, para03);
					}
					//RFE 家庭日常生活責任保險
					if((FirF02KindCodeUtil.kindCode.RFE.toString()).equals(kindcode)){
						map = firF02CalPremService.getRFEPrem(calcDate, channelType, riskcode, kindcode, para01, para02, para03, para04, para05);
					}
					//RF01 擴大家庭災害費用補償
					if((FirF02KindCodeUtil.kindCode.RF01.toString()).equals(kindcode)){
						map = firF02CalPremService.getRF01Prem(calcDate, channelType, riskcode, kindcode, para01, para02, para03);
					}
					//RF02-家事代勞費用保費
					if((FirF02KindCodeUtil.kindCode.RF02.toString()).equals(kindcode)){
						map = firF02CalPremService.getRF02Prem(calcDate, channelType, riskcode, kindcode, para01);
					}
					//RF03-地震災害修復費用
					if((FirF02KindCodeUtil.kindCode.RF03.toString()).equals(kindcode)){
						map = firF02CalPremService.getRF03Prem(calcDate, channelType, riskcode, kindcode, para01);
					}
					//RF04-特定事故房屋跌價損失補償
					if((FirF02KindCodeUtil.kindCode.RF04.toString()).equals(kindcode)){
						map = firF02CalPremService.getRF04Prem(calcDate, channelType, riskcode, kindcode, para01, para02);
					}
					//RF05-特定事故房屋租金補償保險
					if((FirF02KindCodeUtil.kindCode.RF05.toString()).equals(kindcode)){
						map = firF02CalPremService.getRF05Prem(calcDate, channelType, riskcode, kindcode, para01, para02);
					}
					//RF06-住宅鑰匙門鎖費用補償保險
					if((FirF02KindCodeUtil.kindCode.RF06.toString()).equals(kindcode)){
						map = firF02CalPremService.getRF06Prem(calcDate, channelType, riskcode, kindcode, para01, para02);
					}
					//RF07-寵物特定事故意外費用補償
					if((FirF02KindCodeUtil.kindCode.RF07.toString()).equals(kindcode)){
						map = firF02CalPremService.getRF07Prem(calcDate, channelType, riskcode, kindcode, para01);
					}
					//RF08-住宅輕損地震損失
					if((FirF02KindCodeUtil.kindCode.RF08.toString()).equals(kindcode)){
						map = firF02CalPremService.getRF08Prem(calcDate, channelType, riskcode, kindcode, para01, para02);
					}
					//RF09-住宅火險附加家庭成員傷害保險
					if((FirF02KindCodeUtil.kindCode.RF09.toString()).equals(kindcode)){
						map = firF02CalPremService.getRF09Prem(calcDate, channelType, riskcode, kindcode, para01, para02, para03, para04, para05);
					}
					//RF10-住家綠能升級附加條款
					if((FirF02KindCodeUtil.kindCode.RF10.toString()).equals(kindcode)){
						map = firF02CalPremService.getRF10Prem(calcDate, channelType, riskcode, kindcode, para01, fr3Map);
					}
				}
				
				firPremcalcTmpdtl.setDangerGrade((BigDecimal)map.get("dangerGrade"));
				firPremcalcTmpdtl.setPremium((BigDecimal)map.get("premium"));
				firPremcalcTmpdtl.setPurePremium((BigDecimal)map.get("purePremium"));
				firPremcalcTmpdtl.setPremRate((BigDecimal)map.get("premRate"));
				firPremcalcTmpdtl.setPurePremRate((BigDecimal)map.get("purePremRate"));
				sumPremium = sumPremium.add((BigDecimal)map.get("premium"));
			}
			//最後沒問題～
			firPremcalcTmp.setReturnType("Y");
			firPremcalcTmp.setSumPremium(sumPremium);
		}
	}

	
	@Override
	public void pbCalPrem(PbPremcalcTmp pbPremcalcTmp) throws SystemException,
			Exception {
		String calcDate =  DateUtils.getDateDay(pbPremcalcTmp.getCalcDate(), "", 1);
		ArrayList<PbPremcalcTmpdtl> pbPremcalcTmpdtlList = pbPremcalcTmp.getPbPremcalcTmpdtlList();
		
 
		if(pbPremcalcTmpdtlList != null && pbPremcalcTmpdtlList.size() > 0){
			//主險
			String mainIns = "PB";
			ArrayList<PbPremcalcTmpdtl> highPriorityList = new ArrayList<PbPremcalcTmpdtl>();
			ArrayList<PbPremcalcTmpdtl> lowPriorityList = new ArrayList<PbPremcalcTmpdtl>();
			//找出優先計算的，在將較低優先性的加入
			for(PbPremcalcTmpdtl pbPremcalcTmpdtl:pbPremcalcTmpdtlList){
				if(mainIns.indexOf(pbPremcalcTmpdtl.getKindcode()) != -1){
					highPriorityList.add(pbPremcalcTmpdtl);
				}else{
					lowPriorityList.add(pbPremcalcTmpdtl);
				}
			}
			highPriorityList.addAll(lowPriorityList);
			//記錄主險
			Map pbMap = new HashMap();
			BigDecimal sumPremium = BigDecimal.ZERO;
			for(PbPremcalcTmpdtl pbPremcalcTmpdtl:highPriorityList){
				Map map = new HashMap();
				String riskcode = StringUtil.nullToSpace(pbPremcalcTmpdtl.getRiskcode());
				String kindcode = StringUtil.nullToSpace(pbPremcalcTmpdtl.getKindcode());
				if("PB".equals(riskcode)){
//					//Q_FB1	鎮店保-商業火險(非正規計算公式，因而險種前方多一個Q)
					if((PbKindCodeUtil.kindCode.PB.toString()).equals(kindcode)){
						map = pbCalPremService.getPbPlacePrem(pbPremcalcTmp, pbPremcalcTmpdtl);
					}
//					//Q_B1	鎮店保-爆炸險(非正規計算公式，因而險種前方多一個Q)
//					if((FirF01KindCodeUtil.kindCode.Q_B1.toString()).equals(kindcode)){
//						map = pbF01CalPremService.getQb1Prem(calcDate, channelType, riskcode, kindcode, para01);
//					}
//					//Q_BB	鎮店保-煙燻險(非正規計算公式，因而險別前方多一個Q)
//					if((FirF01KindCodeUtil.kindCode.Q_BB.toString()).equals(kindcode)){
//						map = pbF01CalPremService.getQb1Prem(calcDate, channelType, riskcode, kindcode, para01);
//					}
//					//Q_PB鎮店保-公共意外責任險(此為簡化版公式，險別代號前方多一個Q)
//					if((FirF01KindCodeUtil.kindCode.Q_PB.toString()).equals(kindcode)){
//						map = pbF01CalPremService.getQpbPrem(pbPremcalcTmp, calcDate, channelType, riskcode, kindcode, para01, para02, para03, para04, para05, para06);
//					}
				}
				BigDecimal premium = (BigDecimal)map.get("premium");
				pbPremcalcTmpdtl.setDangerGrade((BigDecimal)map.get("dangerGrade"));
				pbPremcalcTmpdtl.setPremium((BigDecimal)map.get("premium"));
				pbPremcalcTmpdtl.setPurePremium((BigDecimal)map.get("purePremium"));
				pbPremcalcTmpdtl.setAdjPremium((BigDecimal)map.get("adjPremium"));
				pbPremcalcTmpdtl.setAdjPurePremium((BigDecimal)map.get("adjPurePremium"));
//				pbPremcalcTmpdtl.setPremRate((BigDecimal)map.get("premRate"));
//				pbPremcalcTmpdtl.setPurePremRate((BigDecimal)map.get("purePremRate"));
				sumPremium = sumPremium.add(premium);
			}
			//最後沒問題～
			pbPremcalcTmp.setReturnType("Y");
			pbPremcalcTmp.setSumPremium(sumPremium);
		}
	}

	public FirPremcalcTmpService getFirPremcalcTmpService() {
		return firPremcalcTmpService;
	}

	public void setFirPremcalcTmpService(FirPremcalcTmpService firPremcalcTmpService) {
		this.firPremcalcTmpService = firPremcalcTmpService;
	}

	public FirPremcalcTmpdtlService getFirPremcalcTmpdtlService() {
		return firPremcalcTmpdtlService;
	}

	public void setFirPremcalcTmpdtlService(
			FirPremcalcTmpdtlService firPremcalcTmpdtlService) {
		this.firPremcalcTmpdtlService = firPremcalcTmpdtlService;
	}

	public PbPremcalcCklistService getPbPremcalcCklistService() {
		return pbPremcalcCklistService;
	}

	public void setPbPremcalcCklistService(
			PbPremcalcCklistService pbPremcalcCklistService) {
		this.pbPremcalcCklistService = pbPremcalcCklistService;
	}

	public FirF02CalPremService getFirF02CalPremService() {
		return firF02CalPremService;
	}

	public void setFirF02CalPremService(FirF02CalPremService firF02CalPremService) {
		this.firF02CalPremService = firF02CalPremService;
	}

	public FirF01CalPremService getFirF01CalPremService() {
		return firF01CalPremService;
	}

	public void setFirF01CalPremService(FirF01CalPremService firF01CalPremService) {
		this.firF01CalPremService = firF01CalPremService;
	}

	public CalPremBaseService getCalPremBaseService() {
		return calPremBaseService;
	}

	public void setCalPremBaseService(CalPremBaseService calPremBaseService) {
		this.calPremBaseService = calPremBaseService;
	}

	public PbPremcalcClauseService getPbPremcalcClauseService() {
		return pbPremcalcClauseService;
	}

	public void setPbPremcalcClauseService(
			PbPremcalcClauseService pbPremcalcClauseService) {
		this.pbPremcalcClauseService = pbPremcalcClauseService;
	}

	public PbPremcalcTmpService getPbPremcalcTmpService() {
		return pbPremcalcTmpService;
	}

	public void setPbPremcalcTmpService(PbPremcalcTmpService pbPremcalcTmpService) {
		this.pbPremcalcTmpService = pbPremcalcTmpService;
	}

	public PbPremcalcTmpdtlService getPbPremcalcTmpdtlService() {
		return pbPremcalcTmpdtlService;
	}

	public void setPbPremcalcTmpdtlService(
			PbPremcalcTmpdtlService pbPremcalcTmpdtlService) {
		this.pbPremcalcTmpdtlService = pbPremcalcTmpdtlService;
	}

	public PbQueryDetailService getPbQueryDetailService() {
		return pbQueryDetailService;
	}

	public void setPbQueryDetailService(PbQueryDetailService pbQueryDetailService) {
		this.pbQueryDetailService = pbQueryDetailService;
	}

	public PbCalPremService getPbCalPremService() {
		return pbCalPremService;
	}

	public void setPbCalPremService(PbCalPremService pbCalPremService) {
		this.pbCalPremService = pbCalPremService;
	}

}
