package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.sinosoft.common.schema.model.PrpCPmain;
import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpQmain;
import com.sinosoft.common.schema.model.PrpTexpense;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.common.schema.model.PrpTmainSub;
import com.sinosoft.prpall.dto.domain.PrpCPexpenseDto;
import com.sinosoft.prpall.dto.domain.PrpCPgradeDto;
import com.sinosoft.prpall.dto.domain.PrpCPmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpCexpenseDto;
import com.sinosoft.prpall.dto.domain.PrpCgradeDto;
import com.sinosoft.prpall.dto.domain.PrpCmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpLcompensateDto;
import com.sinosoft.prpall.dto.domain.PrpLprepayDto;
import com.sinosoft.prpall.dto.domain.PrpPheadCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpPmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpTgradeDto;
import com.sinosoft.reins.common.model.PrpCDangerUnit;
import com.sinosoft.reins.common.model.PrpPDangerUnit;
import com.sinosoft.reins.common.model.PrpTDangerUnit;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.vo.CommonAmountAndPremiumVo;

// TODO: Auto-generated Javadoc
/**
 * 核保系統查詢接口類.
 */
public interface PrpallService {

	/**
	 * 獲取要保書子訊息.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @param businessno
	 *            業務號
	 * @return 要保書子訊息
	 */
	public PrpTmainSub getPrpTmainSub(String riskCode, String businessno);

	/**
	 * 判斷某個單號是否在給定的單號數組裡面.
	 * 
	 * @param businessno
	 *            業務號
	 * @param businessnos
	 *            業務號數組
	 * @return 存在返回true,不存在返回false
	 */
	public boolean isInArray(String businessno, String businessnos[]);

	/**
	 * 判斷是否爲關聯單.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @param businessno
	 *            業務號
	 * @return 是關聯單返回true,不是返回false
	 */
	public boolean isAssociation(String riskCode, String businessno);

	/**
	 * 根據業務號返回定級信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 定級信息
	 * @throws Exception
	 *             異常
	 */
	public PrpTgradeDto getPrpTgrade(String businessNo, String businessType)
			throws Exception;

	/**
	 * 根據業務號獲取定級業務的關聯業務.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 定級業務的關聯業務訊息
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpGradeGroupDetailByBusinessNo(String businessNo)
			throws Exception;

	/**
	 * 根據不同業務號獲取要保書訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 要保書訊息
	 * @throws Exception
	 *             異常
	 */
	public PrpTexpense getPrpTexpense(String businessNo, String businessType)
			throws Exception;

	/**
	 * 獲取保單保險證明.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保單保險證明
	 * @throws Exception
	 *             異常
	 */
	public PrpCmainCovernoteDto getPrpCmainCovernote(String businessNo)
			throws Exception;

	/**
	 * 獲取報價單信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 報價單信息
	 * @throws Exception
	 *             異常
	 */
	public PrpQmain getPrpTmainQta(String businessNo)
			throws Exception;

	/**
	 * 根據不同業務號獲取要保書訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 要保書訊息
	 * @throws Exception
	 *             異常
	 */
	public PrpTmain getPrpTmain(String businessNo, String businessType)
			throws Exception;

	/**
	 * 查找保單主表信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保單主表信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCmain getPrpCmain(String businessNo) throws Exception;

	/**
	 * 根據業務號返回定級信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 定級信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCgradeDto getPrpCgrade(String businessNo) throws Exception;

	/**
	 * 根據不同業務號獲取保單信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 保單信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCexpenseDto getPrpCexpense(String businessNo, String businessType)
			throws Exception;

	/**
	 * 預約保險批單.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 預約保險批單訊息
	 * @throws Exception
	 *             異常
	 */
	public PrpPmainCovernoteDto getPrpPmainCovernote(String businessNo)
			throws Exception;

	/**
	 * 獲取預約保險主信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 預約保險主信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCPmainCovernoteDto getPrpCPmainCovernote(String businessNo)
			throws Exception;

	/**
	 * 根據業務號返回定級信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 定級信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCPgradeDto getPrpCPgrade(String businessNo) throws Exception;

	/**
	 * 獲取預約保險批改訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 預約保險批改訊息
	 * @throws Exception
	 *             異常
	 */
	public PrpPheadCovernoteDto getPrpPheadCovernote(String businessNo)
			throws Exception;

	/**
	 * 根據業務號返回批單訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 批單訊息
	 * @throws Exception
	 *             異常
	 */
	public PrpPmain getPrpPmain(String businessNo) throws Exception;

	/**
	 * 根據業務號返回保單訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保單訊息
	 * @throws Exception
	 *             異常
	 */
	public PrpCPmain getPrpCPmain(String businessNo) throws Exception;

	/**
	 * 根據不同業務號獲取批單費用信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 批單費用信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCPexpenseDto getPrpCPexpense(String businessNo,
			String businessType) throws Exception;

	/**
	 * 查找計算書主表信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 計算書主表信息
	 * @throws Exception
	 *             異常
	 */
	public PrpLcompensateDto getPrpLcompensate(String businessNo)
			throws Exception;

	/**
	 * 查找計算書主表信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 計算書主表信息
	 * @throws Exception
	 *             異常
	 */
	public PrpLprepayDto getPrpLprepay(String businessNo) throws Exception;

	/**
	 * 獲取要保書，保單的折幣總保額總保費(拆分危險單位時調用).
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 總保額總保費
	 * @throws Exception
	 *             異常
	 */
	public CommonAmountAndPremiumVo getAmountAndPremium(String businessNo,
			String businessType) throws Exception;

	/**
	 * 根據不同業務號壹次獲取對應的所有危險單位主信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 所有危險單位主信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerDetailList(String businessNo, String businessType)
			throws Exception;

	/**
	 * 獲取標的信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @return 標的信息
	 * @throws Exception
	 *             異常
	 */
	public ArrayList getCustomItemKindList(String businessType,
			String businessNo, String riskCode) throws Exception;

	/**
	 * 自定義獲取標的信息.
	 * 
	 * @param proposalNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @return 標的信息
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getQtaCustomTitemKindList(String proposalNo,
			String riskCode) throws SQLException, Exception;

	/**
	 * 獲得保單號.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保單號
	 * @throws Exception
	 *             異常
	 */
	public String getMessageId(String businessNo) throws Exception;

	/**
	 * 獲取到指定危險單位序號的投保單(批單)的危險單位主信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @return 拆分危險單位程序
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerDetail(String businessType, String businessNo,
			String dangerNo) throws Exception;

	/**
	 * 獲取危險單位的所有子信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @param businessType
	 *            業務類型
	 * @return 子信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerItemList(String businessNo, String dangerNo,
			String businessType) throws Exception;

	/**
	 * 获取到收费计划中的币种类别.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 收费计划中的币种类别
	 * @throws Exception
	 *             異常
	 */
	public Collection getPlanCurrencyType(String businessNo, String businessType)
			throws Exception;

	/**
	 * 獲取指定業務號，危險單位序號的壹個危險單位主信息的風險等級，風險名稱，自留額，風險幣別信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @return 風險等級
	 * @throws Exception
	 *             異常
	 */
	public PrpTDangerUnit getDangerRiskLevel(String businessType,
			String businessNo, String dangerNo) throws Exception;

	/**
	 * 獲取指定業務號，危險單位序號的壹個危險單位主信息的風險等級，風險名稱，自留額，風險幣別信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @return 風險等級
	 * @throws Exception
	 *             異常
	 */
	public PrpCDangerUnit getCDangerRiskLevel(String businessType,
			String businessNo, String dangerNo) throws Exception;

	/**
	 * 保存投保單的所有危險單位主訊息.
	 * 
	 * @param dangerList
	 *            the danger list
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void savePrpTdangerUnit(ArrayList dangerList) throws SQLException,
			Exception;

	/**
	 * 保存保單的危險單位拆分信息.
	 * 
	 * @param dangerList
	 *            危險單位信息集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void savePrpCdangerUnit(ArrayList dangerList) throws SQLException,
			Exception;

	/**
	 * 保存批單的危險單位主信息.
	 * 
	 * @param dangerList
	 *            危險單位主信息
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void savePrpPdangerUnit(ArrayList dangerList) throws SQLException,
			Exception;

	/**
	 * 獲取指定業務號，危險單位序號的壹個危險單位主信息的風險等級，風險名稱，自留額，風險幣別信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @return 風險等級
	 * @throws Exception
	 *             異常
	 */
	public PrpPDangerUnit getPDangerRiskLevel(String businessType,
			String businessNo, String dangerNo) throws Exception;

	/**
	 * 獲取保單訊息.
	 * 
	 * @param sql
	 *            查詢條件
	 * @return 保單訊息集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public ArrayList getPrpCmainList(String sql) throws SQLException, Exception;

	/**
	 * 保存危險單位拆分主信息(不保存子信息).
	 * 
	 * @param dangerList
	 *            危險單位主信息集合
	 * @param businessType
	 *            業務類型
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void saveDangerUnit(ArrayList dangerList, String businessType)
			throws SQLException, Exception;

	/**
	 * 獲取到指定的收費計劃信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 指定的收費計劃信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpPlan(String businessNo, String businessType)
			throws Exception;

	/**
	 * 取當前數據庫聯共保信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 共保信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCoinsList(String businessNo, String businessType)
			throws Exception;

	/**
	 * 獲取業務的支付幣別相關信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @return 支付幣別相關信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getExchangeRate(String businessType, String businessNo)
			throws Exception;

	/**
	 * 獲取危險單位序號.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 危險單位序號
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerUnitSerialNoInfo(String businessNo,
			String businessType) throws Exception;

	/**
	 * 獲取投保標的信息.
	 * 
	 * @param proposalNo
	 *            業務號
	 * @return 投保標的信息類集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpTitemKindList(String proposalNo)
			throws SQLException, Exception;

	/**
	 * 獲取投保單特別約定信息.
	 * 
	 * @param proposalNo
	 *            業務號
	 * @return 投保單特別約定信息類集合.
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpTengageList(String proposalNo) throws SQLException,
			Exception;

	/**
	 * 取當前業務危險單位聯共保信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 危險單位聯共保信息類集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpDangerCoinsList(String businessNo,
			String businessType) throws Exception;

	/**
	 * 獲取要保書危險單位交費計畫訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 要保書危險單位交費計畫訊息
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpDangerPlanList(String businessNo,
			String businessType) throws Exception;

	/**
	 * 獲取保單標的信息.
	 * 
	 * @param policyNo
	 *            保單號
	 * @return 保單標的信息類集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCitemKindList(String policyNo) throws SQLException,
			Exception;

	/**
	 * 獲取批單標的信息.
	 * 
	 * @param endorseNo
	 *            批單號
	 * @return 批單標的信息類集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCPitemKindList(String endorseNo)
			throws SQLException, Exception;

	/**
	 * 獲取批單特別約定信息.
	 * 
	 * @param policyNo
	 *            保單號
	 * @return 批單特別約定信息類集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCPengageList(String policyNo) throws SQLException,
			Exception;
	public void updateTmain(PrpTmain prpTmain);

	public void updateCmain(PrpCmain prpCmain);
	
	public void updateQmain(PrpQmain prpQmain);
	
	public void RecoveryStatus(String policyNo);
	
	public void delete(Object obj);
	
	public void RecoveryStatusQta(String businessNo);

}
