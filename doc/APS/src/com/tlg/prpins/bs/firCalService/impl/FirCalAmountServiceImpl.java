package com.tlg.prpins.bs.firCalService.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.bs.firCalService.FirBaseUtilService;
import com.tlg.prpins.bs.firCalService.FirCalAmountService;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.util.DateUtils;
import com.tlg.util.StringUtil;


@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCalAmountServiceImpl implements FirCalAmountService {
	
	private FirBaseUtilService firBaseUtilService;

	@Override
	public void getFirAmountCal(FirPremcalcTmp firPremcalcTmp)
			throws SystemException, Exception {
		String wallno = firPremcalcTmp.getWallno();
		String roofno = firPremcalcTmp.getRoofno();
		String postcode = firPremcalcTmp.getPostcode();
		String sumfloors = firPremcalcTmp.getSumfloors();
		String calcDate =  DateUtils.getDateDay(firPremcalcTmp.getCalcDate(), "", 1);
		
		//A.	建物結構檢核
		boolean boo = firBaseUtilService.checkWallMaterial(wallno);
		if(!boo){
			firPremcalcTmp.setReturnType("N");
			firPremcalcTmp.setReturnMsg("查無符合建物結構，無法計算保額。");
			return;
		}
		
		//B.	屋頂別檢核
		boo = firBaseUtilService.checkRoofMaterial(roofno);
		if(!boo){
			firPremcalcTmp.setReturnType("N");
			firPremcalcTmp.setReturnMsg("查無符合屋頂別，無法計算保額。");
			return;
		}
		
		//C.	取得縣市別POST
		String city = firBaseUtilService.getCityData(postcode);
		if(StringUtil.isSpace(city)){
			firPremcalcTmp.setReturnType("N");
			firPremcalcTmp.setReturnMsg("查無郵遞區號，無法計算保額。");
			return;
		}
		//D.	取得每坪造價資料ST
		String perUniPrice = firBaseUtilService.getPerUnitPrice(sumfloors, calcDate, city);
		if(StringUtil.isSpace(perUniPrice)){
			firPremcalcTmp.setReturnType("N");
			firPremcalcTmp.setReturnMsg("查無造價表，無法計算保額。");
			return;
		}
		//E.	取得每坪最高裝潢費、鋼骨加價、磚木石金屬每坪造價、地震保額上限OT
		Map dataMap = firBaseUtilService.getFirAmountCalDetailData(calcDate);
		if(dataMap == null){
			firPremcalcTmp.setReturnType("N");
			firPremcalcTmp.setReturnMsg("查無造價表相關係數，無法計算保額。");
			return;
		}
		String maxDecorationFee = (String)dataMap.get("MaxDecorationFee"); 
		String steelRate = (String)dataMap.get("SteelRate");
		String woodPrice = (String)dataMap.get("WoodPrice");
		String maxAmount = (String)dataMap.get("MaxAmount");
		
		
		//F.	取得建物等級BL
		String buildingLv = firBaseUtilService.getbuildingLevel(wallno, roofno, calcDate);
		if(StringUtil.isSpace(buildingLv)){
			firPremcalcTmp.setReturnType("N");
			firPremcalcTmp.setReturnMsg("查無對應建物等級，無法計算保額。");
			return;
		}
		//G.	取得需計算鋼骨加價的建物結構(多筆)
		String structureAddFeeStr = StringUtil.nullToSpace(firBaseUtilService.getWallNoStructureAddFee(calcDate));
		
				
		/**
		 * 	If 接收參數.WALLNO為：待確認磚木石金屬的代號。
				TMP_每坪造價 = OT.STRUCTURENO (OT.ROOFNO = WoodPrice的那一筆資料)
			Else
  				TMP_每坪造價 = ST.STRUCTURENO
			End if
		 */
		BigDecimal perUnitPrice = null;
		//當「建物結構(WALLNO)為04磚造或29石造」或是「建物等級為5級、6級且建物結構(WALLNO)不為06加強牆造」時，使用設定檔的WoodPrice當做每坪造價。
		//If （接收參數.WALLNO為04, 29任一種（磚造）） OR (BL.STRUCTURENO建物等級 IN (‘5’,’6’) AND 接收參數.WALLNO <> ‘06’)
		if(("04".equals(firPremcalcTmp.getWallno()) || "29".equals(firPremcalcTmp.getWallno())) 
				|| (("5".equals(buildingLv) || "6".equals(buildingLv)) && (!"wallno".equals("06")))){
			perUnitPrice = new BigDecimal(woodPrice);
		}else{
			perUnitPrice = new BigDecimal(perUniPrice);
		}
		
		/**
		 * 	If 接收參數.WALLNO為：01, 39, 27, 28, 32任一種	//鋼骨 → 20191021修改為判斷structureAddFeeStr內的值
  				TMP_鋼骨加價 = OT.STRUCTURENO (OT.ROOFNO = SteelRate的那一筆資料)
  				WK_EQ_AMT = (TMP_每坪造價 * TMP_鋼骨加價) * 接收參數.BUILDAREA
  				WK_FS_AMT = (TMP_每坪造價 * TMP_鋼骨加價 + 接收參數.DECOR_FEE) * 接收參數.BUILDAREA
  				WK_FS_MAX_AMT = (TMP_每坪造價 * TMP_鋼骨加價 + TMP_每坪最高裝潢費) * 接收參數.BUILDAREA
			Else
  				WK_EQ_AMT = TMP_每坪造價 * 接收參數.BUILDAREA
  				WK_FS_AMT = (TMP_每坪造價 + 接收參數.DECOR_FEE) * 接收參數.BUILDAREA
  				WK_FS_MAX_AMT = (TMP_每坪造價 + TMP_每坪最高裝潢費) * 接收參數.BUILDAREA
			End if
		 */
		BigDecimal eqAmt = BigDecimal.ZERO;
		BigDecimal fsAmt = BigDecimal.ZERO;
		BigDecimal fsMaxAmt = BigDecimal.ZERO;
		if(structureAddFeeStr.indexOf(wallno) != -1){
			eqAmt = perUnitPrice.multiply(new BigDecimal(steelRate)).multiply(firPremcalcTmp.getBuildarea());
			fsAmt = (perUnitPrice.multiply(new BigDecimal(steelRate)).add(new BigDecimal(firPremcalcTmp.getDecorFee()))).multiply(firPremcalcTmp.getBuildarea());
			fsMaxAmt = (perUnitPrice.multiply(new BigDecimal(steelRate)).add(new BigDecimal(maxDecorationFee))).multiply(firPremcalcTmp.getBuildarea());
		}else{
			eqAmt = perUnitPrice.multiply(firPremcalcTmp.getBuildarea());
			fsAmt = (perUnitPrice.add(new BigDecimal(firPremcalcTmp.getDecorFee()))).multiply(firPremcalcTmp.getBuildarea());
			fsMaxAmt = (perUnitPrice.add(new BigDecimal(maxDecorationFee))).multiply(firPremcalcTmp.getBuildarea());
		}
		//若WK_EQ_AMT > TMP_地震保額上限，則WK_EQ_AMT = TMP_地震保額上限
		if(eqAmt.compareTo(new BigDecimal(maxAmount)) == 1){
			eqAmt = new BigDecimal(maxAmount);
		}
		eqAmt = eqAmt.divide(new BigDecimal(10000)).setScale(0, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(10000));
		fsAmt = fsAmt.divide(new BigDecimal(10000)).setScale(0, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(10000));
		fsMaxAmt = fsMaxAmt.divide(new BigDecimal(10000)).setScale(0, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(10000));
				
		firPremcalcTmp.setEqAmt(eqAmt);
		firPremcalcTmp.setFsAmt(fsAmt);
		firPremcalcTmp.setFsMaxAmt(fsMaxAmt);
		firPremcalcTmp.setReturnType("Y");
	}

	public FirBaseUtilService getFirBaseUtilService() {
		return firBaseUtilService;
	}

	public void setFirBaseUtilService(FirBaseUtilService firBaseUtilService) {
		this.firBaseUtilService = firBaseUtilService;
	}
	
    public static void main(String[] args) throws java.lang.Exception {
    	BigDecimal b = new BigDecimal("3311280");
    	b = b.divide(new BigDecimal(10000), BigDecimal.ROUND_CEILING).multiply(new BigDecimal(10000));
    	System.out.println(b.toString());
    }

}
