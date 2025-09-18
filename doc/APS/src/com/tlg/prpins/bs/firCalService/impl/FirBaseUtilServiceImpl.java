package com.tlg.prpins.bs.firCalService.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.bs.firCalService.FirBaseUtilService;
import com.tlg.prpins.entity.FirCalcamtWallno;
import com.tlg.prpins.entity.PrpdPropStruct;
import com.tlg.prpins.service.FirCalcamtWallnoService;
import com.tlg.prpins.service.PrpdPropStructService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBaseUtilServiceImpl implements FirBaseUtilService{

	private PrpdNewCodeService prpdNewCodeService;
	private PrpdPropStructService prpdPropStructService;
	private FirCalcamtWallnoService firCalcamtWallnoService;


	@SuppressWarnings("unchecked")
	@Override
	public boolean checkWallMaterial(String wallNo) throws SystemException,
			Exception {
		if(StringUtil.isSpace(wallNo)){
			return false;
		}
		Map params = new HashMap();
		params.put("codetype", "WallMaterial");
		params.put("validstatus", "1");
		params.put("codecode", wallNo);
		Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
		if(result.getResObject() != null){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkRoofMaterial(String roofNo) throws SystemException,
			Exception {
		if(StringUtil.isSpace(roofNo)){
			return false;
		}
		
		Map params = new HashMap();
		params.put("codetype", "RoofMaterial");
		params.put("validstatus", "1");
		params.put("codecode", roofNo);
		Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
		if(result.getResObject() != null){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getCityData(String postCode) throws SystemException,
			Exception {
		Map params = new HashMap();
		params.put("codetype", "PostAddress");
		params.put("validstatus", "1");
		params.put("codecode", postCode);
		Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
		if(result.getResObject() != null){
			ArrayList<PrpdNewCode> prpdNewCodeList = (ArrayList<PrpdNewCode>)result.getResObject();
			PrpdNewCode prpdNewCode = prpdNewCodeList.get(0);
			if(StringUtil.isSpace(prpdNewCode.getCodecname())){
				return "";
			}
			String city = prpdNewCode.getCodecname().substring(0, 3);
			if(StringUtil.isSpace(city)){
				return "";
			}
			return city;
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getPerUnitPrice(String sumFloors, String calcDate, String city) throws SystemException,
			Exception {
		
		if(StringUtil.isSpace(sumFloors) || StringUtil.isSpace(calcDate)){
			return null;
		}
		Map params = new HashMap();
		//若大於21層樓，則以21層樓查詢
		if(Integer.parseInt(sumFloors) > 21){
			sumFloors = "21";
		}
		params.put("wallno",sumFloors);
		params.put("calcDate", calcDate);
		params.put("city", city);
		Result result = prpdPropStructService.findPrpdPropStructByParams(params);
		if(result.getResObject() != null){
			ArrayList<PrpdPropStruct> prpdPropStructList = (ArrayList<PrpdPropStruct>)result.getResObject();
			PrpdPropStruct prpdPropStruct = prpdPropStructList.get(0);
			if(StringUtil.isSpace(prpdPropStruct.getStructureno())){
				return null;
			}
			return prpdPropStruct.getStructureno();
		}
		return null;
	}
	
	@Override
	public Map getFirAmountCalDetailData(String calcDate) throws SystemException,
			Exception {
		String selectTypeList[] = {"MaxDecorationFee", "SteelRate", "WoodPrice", "MaxAmount"};

		return getCalParamDataByAry(selectTypeList, calcDate);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getCalParamDataByAry(String selectTypeList[], String calcDate) throws SystemException, Exception {
		
		Map params = new HashMap();
		params.put("wallno", "CalParam");
		params.put("calcDate", calcDate);
		params.put("selectTypeList", selectTypeList);
		Result result = prpdPropStructService.findPrpdPropStructByParams(params);
		if(result.getResObject() != null){
			//params重複用
			params.clear();
			for(String str :selectTypeList){
				params.put(str, null);
			}
			ArrayList<PrpdPropStruct> prpdPropStructList = (ArrayList<PrpdPropStruct>)result.getResObject();
			for(PrpdPropStruct prpdPropStruct:prpdPropStructList){
				if(params.containsKey(prpdPropStruct.getRoofno())){
					if(StringUtil.isSpace(prpdPropStruct.getStructureno())){
						return null;
					}
					params.put(prpdPropStruct.getRoofno(), prpdPropStruct.getStructureno()); 
				}
			}
			//因為查n項，所以必須要有n筆資料，否則回傳null
			if(params.size() != selectTypeList.length){
				return null;
			}
			return params;
		}
		return null;
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public String getbuildingLevel(String wallNo, String roofNo, String calcDate)
			throws SystemException, Exception {
		Map params = new HashMap();
		params.put("wallno", wallNo);
		params.put("roofno", roofNo);
		params.put("calcDate", calcDate);
		Result result = prpdPropStructService.findPrpdPropStructByParams(params);
		if(result.getResObject() != null){
			ArrayList<PrpdPropStruct> prpdPropStructList = (ArrayList<PrpdPropStruct>)result.getResObject();
			PrpdPropStruct prpdPropStruct = prpdPropStructList.get(0);
			if(StringUtil.isSpace(prpdPropStruct.getStructureno())){
				return null;
			}
			return prpdPropStruct.getStructureno();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getWallNoStructureAddFee(String calcDate)
			throws SystemException, Exception {
		Map params = new HashMap();
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		Result result = this.firCalcamtWallnoService.findFirCalcamtWallnoByParams(params);
		if(result.getResObject() != null){
			String wallNoListStr = "";
			ArrayList<FirCalcamtWallno> firCalcamtWallnoList = (ArrayList<FirCalcamtWallno>)result.getResObject();
			for(FirCalcamtWallno firCalcamtWallno:firCalcamtWallnoList){
				wallNoListStr += firCalcamtWallno.getWallno() +  ","; 
			}
			
			return wallNoListStr;
		}
		return null;
	}
	
	public static void main(String[] args){
		String s = "彰化縣鹿港鎮";
		System.out.println(s.substring(0, 3));
	}

	public PrpdNewCodeService getPrpdNewCodeService() {
		return prpdNewCodeService;
	}

	public void setPrpdNewCodeService(PrpdNewCodeService prpdNewCodeService) {
		this.prpdNewCodeService = prpdNewCodeService;
	}

	public PrpdPropStructService getPrpdPropStructService() {
		return prpdPropStructService;
	}

	public void setPrpdPropStructService(PrpdPropStructService prpdPropStructService) {
		this.prpdPropStructService = prpdPropStructService;
	}

	public FirCalcamtWallnoService getFirCalcamtWallnoService() {
		return firCalcamtWallnoService;
	}

	public void setFirCalcamtWallnoService(
			FirCalcamtWallnoService firCalcamtWallnoService) {
		this.firCalcamtWallnoService = firCalcamtWallnoService;
	}
}
