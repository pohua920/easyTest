package com.sinosoft.productconfig.intf.service.facade;

import java.util.List;

import com.sinosoft.common.schema.model.PrpCinsuredIdvListTemp;
import com.sinosoft.productconfig.common.schema.vo.ImportExcelInfoVo;
/**
 * ProductPolicyImportCheckService接口
 * @author Sinosoft
 */
public interface ProductPolicyImportCheckService {

	/**
	 * 校驗年齡大于70(EAK)
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAgeSeven(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 校驗出生日期正確性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkBirthday(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 校驗被保險人身份證有效性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkIdentify(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 校驗被保險人信息與導入清單中的重復性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkRedupByExcel(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 校驗被保險人信息與庫中數據的重復性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkRedupByDateBase(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 責任險導入數據只有出生日期情況處理，根據生日計算年齡暫存在生日字段中
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> setAegByBirthday(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 校驗投保人人身份證有效性，不根據證件類型
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAppliIdentifyNum(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 校驗受益人身份證有效性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkBenefitNumber1(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 校驗受益人02身份證有效性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkBenefitNumber2(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 校驗受益人03身份證有效性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkBenefitNumber3(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 校驗被保險人信息與導入清單中的重復性,增加name條件
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkRedupByNameExcel(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 每個被保險人的投保的最大保額不能超過400萬,ECR
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkTotal(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 責任險，校驗Excel數據與數據庫中數據重復性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkERedupByDateBase(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp, ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 責任險，校驗Excel人員重復性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkEIdentifyNo(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 年齡校驗
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAge(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 個單 印刷單號不能重復
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkRedupByPrintNo(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 身份證校驗無證件類型
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkIdentifyNoType(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 *  批改團單導入校驗
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkEImportRedupDBInsured(PrpCinsuredIdvListTemp prpCinsuredIdvListTempaa, ImportExcelInfoVo importExcelInfoVo);
	/**
	 *  批改團單導入校驗 prpPname
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkEImportRedupDBName(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl, ImportExcelInfoVo importExcelInfoVo);
	/**
	 *  農險個性(H、M類產品)清單總保險金額有效性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkSumAmount(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 *  農險個性(H、M類產品)清單總保險費有效性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkSumPremium(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 *  校驗職業類別有效性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkOccupation(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp,ImportExcelInfoVo importExcelInfoVo);
	/**
	 *  EAM年齡18-65
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAgeEAM(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo) ;
	/**
	 * 年齡個性校驗(ECN 年齡在0到75)
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAgeECN(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 年齡個性校驗(ECP 年齡在16到60)
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAgeECP(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo) ;
	/**
	 * 年齡個性校驗(EGA 年齡在20到40)
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAgeEGA(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 年齡個性校驗(EGD 年齡在18到60)
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAgeEGD(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 *  年齡個性校驗(EGH 年齡在18到70)
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAgeEGH(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 *  年齡個性校驗(EGJ 年齡在10到60)
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAgeEGJ(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 *  年齡個性校驗(EHA 年齡在5到55)
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAgeEHA(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 年齡個性校驗(EHB 年齡在0到60)
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkAgeEHB(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 年齡賦值(年齡都根據出生日期計算出來)
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> setAgeByBirthday(PrpCinsuredIdvListTemp prpCinsuredIdvListTemp1,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 校驗職業代碼有效性
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkOccupationCode(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 校驗出生日期
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkBirthdayMethod(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 清單人數應小于等于保障計劃人數
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkExcelQuantity(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 險別條款的年齡校驗
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkItemKindAge(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 添加職業代碼根據產品進行校驗的方法
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkRiskCodeOccupation(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl, ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 同一Excel中主被保險人被刪除，他的附屬被保險人就不能進行修改和刪除的操作
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkOtherIdvList(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);
	/**
	 * 救援卡號的校驗
	 * @param prpCinsuredIdvListTemp
	 * @param importExcelInfoVo 清單導入傳入參數
	 * @return 返回一個PrpCinsuredIdvListTemp對象集合
	 */
	public List<PrpCinsuredIdvListTemp> checkRescueNo(PrpCinsuredIdvListTemp prpCinsuredIdvListTempl,ImportExcelInfoVo importExcelInfoVo);	
}