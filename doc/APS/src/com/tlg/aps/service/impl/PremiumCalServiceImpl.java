/**
 * 
 */
package com.tlg.aps.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tlg.aps.service.PremiumCalService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PrpdPropStruct;
import com.tlg.prpins.service.PrpdPropStructService;
import com.tlg.util.Result;
import com.tlg.xchg.entity.Rfrcode;



/**
 * @author bh061
 *
 */
public class PremiumCalServiceImpl implements PremiumCalService{
	private static final Logger logger = Logger.getLogger(PremiumCalService.class);
	private PrpdPropStructService prpdPropStructService;

	/**住火建議保額計算方法
	 * @param sumfloors
	 * @param areaCode
	 * @param structure
	 * @param wallMaterial
	 * @param buildArea
	 * @return
	 */
	public Map<String, BigDecimal> calSugAmt(String sumfloors, String postCode, String structure, String wallMaterial, String buildArea, String startDate,Map<String,Rfrcode> mapRfrCode) {
		logger.debug("sumfloors="+sumfloors);
		logger.debug("postCode="+postCode);
		logger.debug("structure="+structure);
		logger.debug("wallMaterial="+wallMaterial);
		logger.debug("buildArea="+buildArea);
		logger.debug("startDate="+startDate);
		Map<String, BigDecimal> sugAmtMap = new HashMap<String, BigDecimal>();
		BigDecimal sugAmt = new BigDecimal(0);//住火險建議保額
		BigDecimal sugAmt1 = new BigDecimal(0);//地震險建議保額
		BigDecimal sugAmt2 = new BigDecimal(0);//住火險建議保額(加計5萬裝潢費)
		
		if (!StringUtils.isBlank(sumfloors) && !StringUtils.isBlank(postCode) && !StringUtils.isBlank(structure) && !StringUtils.isBlank(wallMaterial) && !StringUtils.isBlank(buildArea)) {
			//取得保額計算相關參數
			

//			BigDecimal maxAmount = new BigDecimal(rfrCodeService.getRfrCode("CalParam", "MaxAmount", null).getCodeName());//地震險最高保額
//			BigDecimal woodPrice = new BigDecimal(rfrCodeService.getRfrCode("CalParam", "WoodPrice", null).getCodeName());//磚、木、石、及金屬造每坪造價
//			BigDecimal steelRate = new BigDecimal(rfrCodeService.getRfrCode("CalParam", "SteelRate", null).getCodeName());//鋼骨造加費率
//			BigDecimal minAmount = new BigDecimal(rfrCodeService.getRfrCode("CalParam", "MinAmount", null).getCodeName());//地震險最低保額
			
			Rfrcode rfrCodeMaxAmount = (Rfrcode) MapUtils.getObject(mapRfrCode, "CalParam,MaxAmount");
			//mantis：FIR0014，處理人員：DP0711，需求單編號：FIR0014 ，原因：配合7/1造價表及費率表調整相關需求，取依保期起日取最新造價 start
			//RfrCode rfrCodeWoodPrice = (RfrCode) MapUtils.getObject(mapRfrCode, "CalParam,WoodPrice");
			//mantis：FIR0014，處理人員：DP0711，需求單編號：FIR0014 ，原因：配合7/1造價表及費率表調整相關需求，取依保期起日取最新造價 end
			Rfrcode rfrCodeSteelRate = (Rfrcode) MapUtils.getObject(mapRfrCode, "CalParam,SteelRate");
			Rfrcode rfrCodeMinAmount = (Rfrcode) MapUtils.getObject(mapRfrCode, "CalParam,MinAmount");
			
			BigDecimal maxAmount = new BigDecimal(rfrCodeMaxAmount.getCodename());//地震險最高保額
			// mantis：FIR0014，處理人員：DP0711，需求單編號：FIR0014 ，原因：配合7/1造價表及費率表調整相關需求，取依保期起日取最新造價 start
			//BigDecimal woodPrice = new BigDecimal(rfrCodeWoodPrice.getCodeName());//磚、木、石、及金屬造每坪造價
			BigDecimal woodPrice = this.getPropStructPrice("CalParam", "WoodPrice", startDate); //造價表金額
			// mantis：FIR0014，處理人員：DP0711，需求單編號：FIR0014 ，原因：配合7/1造價表及費率表調整相關需求，取依保期起日取最新造價 end
			BigDecimal steelRate = new BigDecimal(rfrCodeSteelRate.getCodename());//鋼骨造加費率
			BigDecimal minAmount = new BigDecimal(rfrCodeMinAmount.getCodename());//地震險最低保額
			
			//若樓層數>21，則以21樓送進造價表查詢
			if(Integer.valueOf(sumfloors)>21){
				sumfloors = "21";
			}
			
			//取得造價表金額
//			String city = rfrCodeService.getRfrCode("PostCode", postCode, null).getCodeName().substring(0,3);//取出縣市別
			Rfrcode rfrCodePostCode = (Rfrcode) MapUtils.getObject(mapRfrCode, "PostCode,"+postCode);
			String city = rfrCodePostCode.getCodename().substring(0,3);//取出縣市別
			BigDecimal listedPrice = this.getPropStructPrice(sumfloors, city, startDate); //造價表金額
			
			//建築等級為二等、三等
			if ("5".equals(structure) || "6".equals(structure)) {
				listedPrice = woodPrice;
			} else {
				//鋼骨造另加16%
				if ("01".equals(wallMaterial) || "27".equals(wallMaterial) || "39".equals(wallMaterial)
					// mantis：FIR0014，處理人員：DP0711，需求單編號：FIR0014 ，原因：配合7/1造價表及費率表調整相關需求，增加鋼骨清單28,32 start
					|| "28".equals(wallMaterial) || "32".equals(wallMaterial)
					// mantis：FIR0014，處理人員：DP0711，需求單編號：FIR0014 ，原因：配合7/1造價表及費率表調整相關需求，增加鋼骨清單28,32 end 
				) {
					listedPrice = listedPrice.multiply(steelRate);
					//磚、木、石、及金屬造
				} else if ("04".equals(wallMaterial) || "29".equals(wallMaterial)) {
					listedPrice = woodPrice;
				} 
			}
			//建議保額計算 (造價金額*坪數)
			logger.debug("listedPrice="+listedPrice);
			sugAmt = listedPrice.multiply(new BigDecimal(buildArea));
			sugAmt1 = listedPrice.multiply(new BigDecimal(buildArea));
			
			// mantis：FIR0014，處理人員：DP0711，需求單編號：FIR0014 ，原因：配合7/1造價表及費率表調整相關需求,調整裝潢費6萬(2019/7/1後) start
			//sugAmt2 = listedPrice.add(new BigDecimal("50000")).multiply(new BigDecimal(buildArea));
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			try {
				Date date0701 = sf.parse("20200101");  // 寫死7/1 潢費6萬 生效
				if(date0701.after(sf.parse(startDate))){ //20190701 之前
					sugAmt2 = listedPrice.add(new BigDecimal("50000")).multiply(new BigDecimal(buildArea));
				}else{	//20190701 之後
					sugAmt2 = listedPrice.add(new BigDecimal("60000")).multiply(new BigDecimal(buildArea));
				}
			} catch (ParseException e) {
				sugAmt2 = listedPrice.add(new BigDecimal("50000")).multiply(new BigDecimal(buildArea));
			}
			// mantis：FIR0014，處理人員：DP0711，需求單編號：FIR0014 ，原因：配合7/1造價表及費率表調整相關需求,調整裝潢費6萬(2019/7/1後) end
			
			//地震險保額應在maxAmount及minAmount之間
			if(sugAmt1.compareTo(minAmount)==-1){
				sugAmt1 = minAmount;
			}
			if(sugAmt1.compareTo(maxAmount)==1){
				sugAmt1 = maxAmount;
			}
			sugAmtMap.put("suggestAmount", sugAmt);//住火
			sugAmtMap.put("suggestAmount1", sugAmt1);//地震險
			sugAmtMap.put("suggestAmount2", sugAmt2);//住火(含裝潢)
			sugAmtMap.put("listedPrice", listedPrice);//住火(含裝潢)
			
		} else {
			logger.error("sumfloors/areaCode/structure/wallMaterial is blank");
		}

		return sugAmtMap;
	}
	
	public String getBotWallmaterial(String wallmaterial){
		if ("01".equals(wallmaterial)){
			return "11";
		} else if ("02".equals(wallmaterial)){
			return "22";
		} else if ("03".equals(wallmaterial)){
			return "21";
		} else if ("04".equals(wallmaterial)){
			return "81";
		} else if ("05".equals(wallmaterial)){
			return "31";
		} else if ("06".equals(wallmaterial)){
			return "32";
		} else if ("09".equals(wallmaterial)){
			return "91";
		} else if ("11".equals(wallmaterial)){
			return "92";
		} else if ("12".equals(wallmaterial)){
			return "61";
		} else if ("13".equals(wallmaterial)){
			return "51";
		} else if ("28".equals(wallmaterial)){
			return "21";
		} else if ("29".equals(wallmaterial)){
			return "71";
		} else {
			return "";
		}
	}
	
	public String getBotRoofmaterial(String roofmaterial){
		if ("50".equals(roofmaterial)){
			return "01";
		} else if ("51".equals(roofmaterial)){
			return "02";
		} else if ("52".equals(roofmaterial)){
			return "03";
		} else if ("53".equals(roofmaterial)){
			return "04";
		} else{
			return "00";
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BigDecimal getPropStructPrice(String sumfloors, String city, String startDate){
		BigDecimal listedPrice = new BigDecimal(0);
		try {
			Map params = new HashMap();
			params.put("wallno", sumfloors);
			params.put("city", city);
			params.put("calcDate", startDate);
			Result result = this.prpdPropStructService.findPrpdPropStructByParams(params);
			if(result != null && result.getResObject() != null) {
				List<PrpdPropStruct> searchResult = (List<PrpdPropStruct>)result.getResObject();
				PrpdPropStruct prpdPropStruct = searchResult.get(0);
				String price =prpdPropStruct.getStructureno();
				listedPrice = new BigDecimal(price);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listedPrice;
	}

	public PrpdPropStructService getPrpdPropStructService() {
		return prpdPropStructService;
	}

	public void setPrpdPropStructService(PrpdPropStructService prpdPropStructService) {
		this.prpdPropStructService = prpdPropStructService;
	}

}
