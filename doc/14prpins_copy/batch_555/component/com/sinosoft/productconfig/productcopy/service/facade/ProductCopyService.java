package com.sinosoft.productconfig.productcopy.service.facade;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.productconfig.common.schema.model.UtiBackRuleConfig;
import com.sinosoft.productconfig.common.schema.model.UtiJSFunc;
import com.sinosoft.productconfig.common.schema.model.UtiPrintConfig;
import com.sinosoft.productconfig.common.schema.productmanage.PrpDrisk;
import com.sinosoft.productconfig.common.schema.vo.PeripheralCodeVO;
import com.sinosoft.productconfig.common.schema.vo.PrpDriskBlockVO;
import com.sinosoft.productconfig.common.schema.vo.PrpDriskRelatedExtSetVO;
import com.sinosoft.productconfig.common.schema.vo.UtiFormulaInfoVO;


/**
 * 產品複製接口
 * @author Sinosoft
 */
public interface ProductCopyService{
	/**
	 * 查詢產品複製信息
	 * @param riskCodeConfigure 險種配置
	 * @param riskCodeCopy 險種複製
	 * @param referenceRiskFlag 險種標誌
	 * @param productCopyTagFlag 產品複製標誌
	 * @return list 產品複製list對象
	 * @throws Exception
	 */
	public List<Object> findProductCopyReSetMSG(String riskCodeConfigure,String riskCodeCopy,String referenceRiskFlag,String productCopyTagFlag) throws Exception;
	/**
	 * 按照機構刪除流程控制信息
	 * @param riskCode 險種代碼
	 * @param comCode 機構代碼
	 */
	public void deleteProcessConfigBycomCode(String riskCode,String comCode);
/*******************************************************************************************************************************************/
/*****************************************************產品配置組織頁面全部的對象，初始化的時候數據全部加載********************************************/
/*******************************************************************************************************************************************/
	/**
	 * 產品配置全部頁面數據的組織
	 * @param riskCodeConfigure 險種配置
	 * @param riskCodeCopy 險種複製
	 * @param referenceRiskFlag 險種標誌
	 * @return List 產品配置對象list
	 * @throws
	 */
	public List<Object> findRiskConfigAllDate(String riskCodeConfigure,String riskCodeCopy,String referenceRiskFlag) throws Exception;
	/********************產品前臺業務規則在復制的時候的保存的方法*****************/
	/**
	 * 前臺業務規則在復制的時候保存的方法
	 * @param utiJSFuncList 產品前臺業務規則UtiJSFunc對象list
	 * @param riskCode 險種代碼
	 * @param userCode 用戶代碼
	 */
	public void addProductFrontRuleConfig(List<UtiJSFunc> utiJSFuncList,String riskCode,String userCode);
	
	/**
	 * 前臺業務規則在復制的時候保存的方法
	 * @param utiJSFuncList 產品前臺業務規則UtiJSFunc對象list
	 * @param riskCode 險種代碼
	 * @param userCode 用戶代碼
	 */
	public void updateProductFrontRuleConfig(List<UtiJSFunc> utiJSFuncList,String riskCode,String userCode);
	/********************產品后臺業務規則在復制的時候的保存的方法*****************/
	/**
	 * 后臺業務規則在復制的時候保存的方法
	 * @param utiBackRuleConfigList 產品后臺業務規則UtiBackRuleConfig對象list
	 * @param riskCode 險種代碼
	 * @param userCode 用戶代碼
	 */
	public void addProductBackRuleConfig(List<UtiBackRuleConfig> utiBackRuleConfigList,String riskCode,String userCode);
	/**
	 * 后臺業務規則在復制的時候保存的方法
	 * @param utiBackRuleConfigList 產品后臺業務規則UtiBackRuleConfig對象list
	 * @param riskCode 險種代碼
	 * @param userCode 用戶代碼
	 */
	public void updateProductBackRuleConfig(List<UtiBackRuleConfig> utiBackRuleConfigList,String riskCode,String userCode);
	/********************產品打印參數配置規則在復制的時候的保存的方法*****************/
	/**
	 * 打印參數配置規則在復制的時候保存的方法
	 * @param utiBackRuleConfigList 打印參數配置規則UtiPrintConfig對象list
	 * @param riskCode 險種代碼
	 * @param classCode 險類代碼
	 * @param userCode 用戶代碼
	 */
	public void addProductPrintConfig(List<UtiPrintConfig> utiPrintConfigList,String riskCode,String classCode,String userCode);
	/**
	 * 打印參數配置規則在復制的時候保存的方法
	 * @param utiBackRuleConfigList 打印參數配置規則UtiPrintConfig對象list
	 * @param riskCode 險種代碼
	 * @param classCode 險類代碼
	 * @param userCode 用戶代碼
	 */
	public void updateProductPrintConfig(List<UtiPrintConfig> utiPrintConfigList,String riskCode,String classCode,String userCode);
	/**
	 * 產品復制進入計算配置頁面將頁面顯示的公式的因子保存成配置產品的因子
	 * @param riskCodeConfig 險種配置
	 * @param riskCodeCopy 險種複製
	 * @param referenceRiskFlag 險種標誌
	 * @throws Exception
	 */
	public void addProductCopyFormulaFactor(String riskCodeConfig,String riskCodeCopy,String referenceRiskFlag) throws Exception;
	/**
	 * 產品復制離開復制頁面計算配置沒有進行保存的情況下需要刪除原來保存的被配置產品的因子
	 * @param riskCodeConfig 險種配置
	 */
	public void deleteProductCopyFormulaFactor(String riskCodeConfig);
	/**
	 * 前臺業務規則事件的onchange方法
	 * @param  frontEventSet  事件的集合
	 * @return String
	 */
	public String productCopyFrontEventChange(String frontEventSet);
	/**
	 * 產品點擊完成時的操作
	 * @param  riskCodeConfig 險種配置
	 */
	public void productCopyFinishOperate(String riskCodeConfig);
	/**
	 * 產品復制進入流程控制頁面的時候，將復制產品的流程控制代碼復制一份，保存成配置產品的
	 * @param  riskCodeConfig 險種配置
	 * @param  riskCodeCopy 險種複製
	 * @throws Exception
	 */
	public void addProductCopyProcessConfig(String riskCodeConfig,String riskCodeCopy) throws Exception;
	/**
	 * 產品配置了一部分但是沒有配置完成的產品的標志位的修改
	 * @param  riskCodeConfig 險種配置
	 */
	public void updatePrpDriskProcessingFlag(String riskCodeConfig);
	/**
	 * 產品復制進入頁面配置的時候保存系數配置的信息
	 * @param riskCodeConfig 險種配置
	 * @param riskCodeCopy 險種複製
	 * @param referenceRiskFlag 險種標誌
	 * @throws Exception
	 */
	public void saveProductCopyCoeffConfig(String riskCodeConfig,String riskCodeCopy,String referenceRiskFlag) throws Exception;
	/**
	 * 產品復制在離開頁面的時候，如果沒有進行頁面配置的保存，則刪除配置產品的下的系數的信息
	 * @param riskCodeConfig 險種配置
	 */
	public void deleteProductCopyCoeffConfig(String riskCodeConfig);
	/**
	 * 產品配置復制的時候展現產品的基礎的信息和頁面的公用固化的信息
	 * @param riskCodeConfigure 險種配置
	 * @return List 產品的基礎的信息list對象
	 * @throws Exception
	 */
	public List<Object> findRiskConfigNotReferenceDate(String riskCodeConfigure) throws Exception;
}