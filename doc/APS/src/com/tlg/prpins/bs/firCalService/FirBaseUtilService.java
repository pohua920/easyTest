package com.tlg.prpins.bs.firCalService;

import java.util.Map;

import com.tlg.exception.SystemException;

public interface FirBaseUtilService {
	/**
	 * 建物結構檢核
	 * @param wallNo
	 * @return 查無資料則回傳false
	 * @throws SystemException
	 * @throws Exception
	 */
	public boolean checkWallMaterial(String wallNo) throws SystemException, Exception;

	/**
	 * 屋頂別檢核
	 * @param roofNo
	 * @return 查無資料則回傳false
	 * @throws SystemException
	 * @throws Exception
	 */
	public boolean checkRoofMaterial(String roofNo) throws SystemException, Exception;
	
	/**
	 * 取得縣市別POST
	 * @param postCode
	 * @return 回傳城市名稱
	 * @throws SystemException
	 * @throws Exception
	 */
	public String getCityData(String postCode) throws SystemException, Exception;
	
	/**
	 * 取得每坪造價資料
	 * @param sumFloors 
	 * @param calcDate
	 * @param city
	 * @return 回傳每坪造價資料
	 * @throws SystemException
	 * @throws Exception
	 */
	public String getPerUnitPrice(String sumFloors, String calcDate, String city) throws SystemException, Exception;
		
	/**
	 * 取得計算保額相關資料：每坪最高裝潢費、鋼骨加價、磚木石金屬每坪造價、地震保額上限OT
	 * @param calcDate 費率基準日
	 * @return 若查詢結果資料筆數筆數和陣列大小不同或OT.STRUCTURENO無值則回傳null<br>
	 * 		         否則 回傳Map - key → 'MaxDecorationFee', 'SteelRate', 'WoodPrice', 'MaxAmount'
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getFirAmountCalDetailData(String calcDate) throws SystemException, Exception;
	/**
	 * 查詢計算用的參數
	 * 
	 * @param selectTypeList 查詢內容，例如'MaxDecorationFee', 'SteelRate', 'WoodPrice', 'MaxAmount'
	 * @param calcDate 費率基準日
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getCalParamDataByAry(String[] selectTypeList, String calcDate)
			throws SystemException, Exception;
	
	/**
	 * 取得建物等級
	 * 
	 * @param wallNo
	 * @param roofNo
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public String getbuildingLevel(String wallNo, String roofNo, String calcDate) throws SystemException, Exception;
	
	/**
	 * 取得需計算鋼骨加價的建物結構
	 * 
	 * @param calcDate 費率基準日
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public String getWallNoStructureAddFee(String calcDate)throws SystemException, Exception;
	
}

