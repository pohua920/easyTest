package com.tlg.ajax.action;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.ReinsInwardPolicyInsVo;
import com.tlg.aps.vo.ReinsInwardPolicyMainVo;
import com.tlg.prpins.entity.Prpdcode;
import com.tlg.prpins.entity.ReinsInwardInsData;
import com.tlg.prpins.entity.ReinsInwardMainData;
import com.tlg.prpins.service.PrpdcodeService;
import com.tlg.prpins.service.ReinsInwardInsDataService;
import com.tlg.prpins.service.ReinsInwardMainDataService;
import com.tlg.util.BaseAction;
import com.tlg.util.GIGO;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class AJAX013Action extends BaseAction implements Serializable {
	
	private ReinsInwardMainDataService reinsInwardMainDataService;
	private ReinsInwardInsDataService reinsInwardInsDataService;
	private PrpdcodeService prpdcodeService;
	private String result;
	private String classCode;
	private String policyNo;
	private String endorseNo;
	
	public String findInsDataByParams() {
		try {
			Map<String,Object> map = new LinkedHashMap<>();
			
			if("".equals(StringUtil.nullToSpace(this.classCode))){
				map.put("isExist", Boolean.FALSE);
				return Action.SUCCESS;
			}
			
			String listStr = "";
			if("A".equals(this.classCode)){
				listStr = getA01();
			}
			if("B".equals(this.classCode)){
				listStr = getB01();
			}
			if("E".equals(this.classCode)){
				listStr = getE();
			}
			if("C".equals(this.classCode)){
				listStr = getC();
			}
			if("C1".equals(this.classCode)){
				listStr = getC1();
			}
			if("F01".equals(this.classCode)){
				listStr = getF01();
			}
			if("F02".equals(this.classCode)){
				listStr = getF02();
			}
			if("M".equals(this.classCode)){
				listStr = getM();
			}
			boolean isExist = false;
			if (!StringUtil.isSpace(listStr)) {
				String dataAry[] = listStr.split("\\^");
				if(dataAry != null && dataAry.length > 0){
					isExist = true;
					for(int i = 0 ; i < dataAry.length ; i++){
						String str[] = dataAry[i].split(",");
						
						String uwins = StringUtil.nullToSpace(str[0]); //大險種
						String poins = StringUtil.nullToSpace(str[1]); //出單險種
						String inscode = StringUtil.nullToSpace(str[2]); //30險種
						String actins = StringUtil.nullToSpace(str[3]); //財務險種
						String poname = StringUtil.nullToSpace(str[4]); //險種名稱
								
						map.put(uwins + "-" + poins + "-" + inscode + "-" + actins , poname);
					}
				}
			}
			map.put("isExist", isExist);
			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	
	public String findPolicyDataByParams() {
		Map<String,Object> map = new LinkedHashMap<>();
		try{
			if("".equals(StringUtil.nullToSpace(this.policyNo)) && "".equals(StringUtil.nullToSpace(this.endorseNo))){
				map.put("isExist", Boolean.FALSE);
				return Action.SUCCESS;
			}
			Map<String, String> params = new HashMap<String, String>();
			if(!StringUtil.isSpace(this.policyNo)){
				params.put("policyNo", this.policyNo);
			}
			
			if(StringUtil.isSpace(this.endorseNo)){
				params.put("nullEndorse", "Y");
			}else{
				params.put("endorseNo", this.endorseNo);
			}
			params.put("statusGtThan3", "Y");
			
			Result queryResult = reinsInwardMainDataService.findReinsInwardMainDataByParams(params);
			if(queryResult.getResObject() == null){
				map.put("isExist", Boolean.FALSE);
				return Action.SUCCESS;
			}
			ArrayList<ReinsInwardMainData> list = (ArrayList<ReinsInwardMainData>)queryResult.getResObject();
			ReinsInwardMainData mainData = list.get(0);
			
			ReinsInwardPolicyMainVo mainVo = new ReinsInwardPolicyMainVo();
			GIGO.fill(mainVo, mainData);
			ArrayList<ReinsInwardPolicyInsVo> insVoList = new ArrayList<ReinsInwardPolicyInsVo>();
			mainVo.setInsList(insVoList);
			//找明細資料
			params.clear();
			params.put("oidReinsInwardMainData", mainData.getOid().toString());
			
			queryResult = reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
			if(queryResult.getResObject() != null){
				DecimalFormat formatterDot = new DecimalFormat("#,###.##");
				ArrayList<ReinsInwardInsData> inslist = (ArrayList<ReinsInwardInsData>)queryResult.getResObject();
				for(ReinsInwardInsData insData : inslist){
					ReinsInwardPolicyInsVo insVo = new ReinsInwardPolicyInsVo();
					GIGO.fill(insVo, insData);
					if(!StringUtil.isSpace(insVo.getOriCurrAmount())){
						insVo.setOriCurrAmount(formatterDot.format(new BigDecimal(insVo.getOriCurrAmount())));
					}
					if(!StringUtil.isSpace(insVo.getOriCurrInwardAmount())){
						insVo.setOriCurrInwardAmount(formatterDot.format(new BigDecimal(insVo.getOriCurrInwardAmount())));
					}
					insVo.setUndertakingRate(mainData.getUndertakingRate().toString());
					insVoList.add(insVo);
				}
			}
			mainVo.setIsExist("true");
			result = JsonUtil.getJSONString(mainVo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String findDamageCode() throws Exception {
		try{
			Map<String,Object> map = new LinkedHashMap<>();
			
			if("".equals(StringUtil.nullToSpace(this.classCode))){
				map.put("isExist", Boolean.FALSE);
				return Action.SUCCESS;
			}
			boolean isExist = false;
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("codetype", "DamageCode");
			params.put("damageCode", "Y");
			params.put("classcode", this.classCode);
			Result queryResult = this.prpdcodeService.findPrpdcodeByParams(params);
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("", "");
			if(queryResult.getResObject() == null){
				map.put("isExist", Boolean.FALSE);
			}else{
				map.put("isExist", Boolean.TRUE);
				ArrayList<Prpdcode> list = (ArrayList<Prpdcode>)queryResult.getResObject();
				for(Prpdcode prpdcode:list) {
					map.put(prpdcode.getCodecode(), prpdcode.getCodecname());
				}		
			}
			
			result = JsonUtil.getJSONString(map);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
		
	private String getA01(){
		//險種,出單險種,30險種,財務險種,險種名稱
		return "A,A,10,,任意車險財損自用^A,A,10,A901,任意車險財損自用^A,A,11,A902,任意車險財損商業^A,A,12,A903,任意車險責任自用^A,A,13,A904,任意車險責任商業^A,A,24,A101,任意車險駕傷一年期^A,A,24,A102,任意車險駕傷二年期^A,A,28,AT,任意車險颱風洪水";
	}
	private String getB01(){
		//險種,出單險種,30險種,財務險種,險種名稱
		return "B,B,14,B901,強制車險汽車自用^B,B,15,B902,強制車險汽車商業^B,B,16,B101,強制車險機車一年期^B,B,16,B102,強制車險機車二年期^";
	}
	
	private String getC(){
		//險種,出單險種,30險種,財務險種,險種名稱
		return "C,BL,17,CGL,客運業旅客運送責任保險^C,CB,17,CGL,汽車車體保固契約責任險^C,CN,17,CGL,營繕承包人意外責任險^C,CY,17,CGL,安全與隱私保護保險" +
				"^C,DP,17,CGL,台壽保產物資安防護險^C,DR,17,CGL,台壽保產物無人機責任保險^C,EL,17,CGL,新雇主意外責任保險^C,EM,17,CGL,僱主意外責任保險^C,ER,17,CGL,台壽保產物雇主補償契約責任保險" +
				"^C,FT,17,CGL,機票代售業務契約^C,GC,17,CGL,高爾夫綜合險^C,GF,17,CGL,高爾夫球員責任保險^C,GL,17,CGL,商業綜合責任保險^C,HC,17,CGL,旅館綜合責任保險^C,LF,17,CGL,電梯意外責任保險" +
				"^C,PA,17,CGL,個人傷害保險^C,PB,17,CGL,公共意外責任保險^C,PC,17,CGL,毒性化學物質運作人責任保險^C,PG,17,CGL,產品責任、產品保證、財務損失及產品回收保險^C,PM,17,CGL,大眾捷運系統旅客運送責任保險" +
				"^C,PR,17,CGL,產品責任保險^C,PT,17,CGL,大眾捷運系統淡水線旅客運送責任保險^A,PX,17,CGL,產品修護契約責任保險^C,SB,17,CGL,金融業保管箱責任保險^C,SC,17,CGL,保全業責任保險" +
				"^C,ST,17,CBC,海外遊學業責任保險^C,TE,17,CBC,旅行業責任保險^C,TP,17,CGL,鐵路旅客運送責任保險^C,AB,18,CPL,保險代理人經紀人專業責任保險^C,AC,18,CPL,會計師責任保險" +
				"^C,AE,18,CPL,建築師工程師專業責任保險" +
				"^C,AT,18,CPL,建築師、技師及消防師(士)專業責任保險" +
				"^C,CL,18,CPL,民間公證人責任保險" +
				"^C,DI,18,CPL,董事及重要職員責任保險^C,DO,18,CPL,董事及重要職員責任保險（台灣人壽專用）^C,IA,18,CPL,保險公證人專業責任保險" +
				"^C,LL,18,CPL,律師責任保險^C,MA,18,CPL,醫師業務責任保險(甲)^C,MF,18,CPL,醫療機構綜合責任保險^C,MI,18,CPL,醫院綜合意外保險^C,MP,18,CPL,醫師業務責任保險^C,NR,18,CPL,護理人員專業責任保險" +
				"^C,PH,18,CPL,藥師與藥劑生專業責任保險^C,TL,18,CPL,教職員責任保險^C,AP,21,CBI,工程預付款保證保險單^C,BD,21,CBI,工程押標金保證保險^C,BN,21,CBI,經紀人保證保險^C,FD,21,CBI,員工誠實保證保險" +
				"^C,MT,21,CBI,保固保證金保證保險^C,PF,21,CBI,工程履約保證保險^C,SP,21,CBI,海外遊學業履約保證保險^C,TC,21,CBI,旅行業履約保證保險(甲式)^C,TD,21,CBI,旅行業履約保證保險(乙式)" +
				"^C,TF,21,CBI,汽車防盜器製造商(經銷商)履約保證保險^C,CR,22,CCI,消費者貸款信用保險^C,AR,23,COI,藝術品綜合保險^C,BB,23,COI,銀行業綜合保險^C,BR,23,COI,竊盜保險^C,GS,23,COI,玻璃保險" +
				"^C,JW,23,COI,銀樓珠寶業綜合保險^C,ME,23,COI,汽車保固費用保險^C,MN,23,COI,現金保險^C,VI,23,COI,疫苗保險(費用)^C,GZ,24,CGA,非執行職務團體傷害保險^C,CC,26,CPC,信用卡綜合保險" +
				"^C,FC,26,CTB,家庭綜合保險^C,PE,26,CPC,寵物綜合保險^C,DS,27,CBC,台壽保產物僱傭綜合保險^C,MV,27,CBC,中國信託產物影視製作綜合保險^C,TE,27,CBC,旅行業責任保險";
	}
	
	private String getC1(){
		//險種,出單險種,30險種,財務險種,險種名稱
		return "C,TA,23,COI,個人旅行綜合保險^C,TA,23,CTAA,旅平險一般責任泡泡險^C,GA,24,C,團體傷害保險^C,GA,24,CGA,團體傷害保險^C,GB,24,CGA,義消團傷險^C,HG,24,CGA,團體健康險" +
				"^C,PA,24,CPA,個人傷害保險^C,TA,24,TA,旅行平安險^C,TB,24,TA,個人旅行綜合保險^C,TR,24,TA,個人旅行綜合保險^C,TA,26,CTA,個人旅行綜合保險^C,TR,26,CTA,個人旅行綜合保險" +
				"^C,HG,30,QTH,團體健康險^Q,HP,30,QTH,個人健康險^Q,PA,30,QTH,個人健康險^Q,Q,30,QTH,海外突發疾病^Q,TA,30,QTH,海外突發疾病^C,TR,30,C,個人旅行綜合保險" +
				"^C,TR,30,QTH,個人旅行綜合保險^Q,VI,30,QTH,個人健康險";
	}
	
	private String getE(){
		//險種,出單險種,30險種,財務險種,險種名稱
		return "E,BP,19,E,鍋爐保險^E,CA,19,E,營造綜合保險^E,CE,19,E,完工土木工程保險^E,CP,19,E,營建機具綜合保險^E,EA,19,E,安裝工程綜合保險^E,EE,19,E,電子設備綜合保險^E,MB,19,E,機械保險";
	}
	
	private String getF01(){
		//險種,出單險種,30險種,財務險種,險種名稱
		return "F,F,3,FB1,火險一年期商業^F,F,4,FBL,火險長期商業^F,F,25,FE01,火險一年期商業地震保險" +
				"^F,F,27,FBC,商店綜合保險^F,F,28,FT,火險一年期商業颱風洪水";
	}
	
	private String getF02(){
		//險種,出單險種,30險種,財務險種,險種名稱
		return "F,F,1,FH1,火險一年期住宅^F,F,2,F,火險長期住宅^F,F,2,FHL,火險長期住宅^C,RC,26,CTB,居家綜合保險^F,F,26,FTB,居家綜合保險^F,F,29,FE02,火險一年期住宅政策地震";
	}
	
	private String getM(){
		//險種,出單險種,30險種,財務險種,險種名稱
		return "M,M,5,MF,內陸運輸保險^M,M,6,MC,貨物運輸保險^H,H,7,H,船體險^M,V,8,V,漁船險^R,AV,9,R,航空險";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}


	public ReinsInwardMainDataService getReinsInwardMainDataService() {
		return reinsInwardMainDataService;
	}


	public void setReinsInwardMainDataService(
			ReinsInwardMainDataService reinsInwardMainDataService) {
		this.reinsInwardMainDataService = reinsInwardMainDataService;
	}


	public ReinsInwardInsDataService getReinsInwardInsDataService() {
		return reinsInwardInsDataService;
	}


	public void setReinsInwardInsDataService(
			ReinsInwardInsDataService reinsInwardInsDataService) {
		this.reinsInwardInsDataService = reinsInwardInsDataService;
	}


	public String getEndorseNo() {
		return endorseNo;
	}


	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}


	public PrpdcodeService getPrpdcodeService() {
		return prpdcodeService;
	}


	public void setPrpdcodeService(PrpdcodeService prpdcodeService) {
		this.prpdcodeService = prpdcodeService;
	}

}