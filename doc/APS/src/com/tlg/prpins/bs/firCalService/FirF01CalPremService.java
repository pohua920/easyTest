package com.tlg.prpins.bs.firCalService;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremcalcTmp;

public interface FirF01CalPremService {
	

	/**
	 * Q_FB1鎮店保-商業火險(非正規計算公式，因而險別前方多一個Q)
	 * 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。 F01
	 * @param kindcode 險別代碼，必填。Q_FB1
	 * @param para01 保險金額
	 * @param para02 使用性質，如：C0301A1
	 * @param para03 自負額
	 * @param para04 樓層
	 * @param para05 坪數
	 * @param para06 郵遞區號
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getQfb1Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03, String para04, String para05, String para06) throws SystemException, Exception;
	
	/**
	 * Q_B1鎮店保-爆炸險(非正規計算公式，因而險別前方多一個Q)
	 * 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。 F01
	 * @param kindcode 險別代碼，必填。Q_FB1
	 * @param para01 保險金額，例如：10000000。
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getQb1Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception;

	/**
	 * Q_BB鎮店保-煙燻險(非正規計算公式，因而險別前方多一個Q)
	 * 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。 F01
	 * @param kindcode 險別代碼，必填。Q_FB1
	 * @param para01 保險金額，例如：10000000。
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getQbbPrem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception;

	/**
	 * Q_FB1鎮店保-商業火險(非正規計算公式，因而險別前方多一個Q)
	 * 
	 * @param firPremcalcTmp 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。 PB
	 * @param kindcode 險別代碼，必填。Q_FB1
	 * @param para01 使用性質，如：I
	 * @param para02 使坪數，如：25.34。
	 * @param para03 處所數，如：1
	 * @param para04 自負額，如：2500
	 * @param para05 AOP保額及AGG保額，使用分號間隔。如：2000000;24000000
	 * @param para06 AOA體傷保額及AOA財損保額，使用分號間隔。如：10000000;2000000
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getQpbPrem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03, String para04, String para05, String para06) throws SystemException, Exception;

	/**
	 * FB1商業火災保險
	 * 
	 * @param firPremcalcTmp 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。 F01
	 * @param kindcode 險別代碼，必填。FB1
	 * @param paraType 是否營業加費，如Y=是;N=否。
	 * @param para01 保險金額
	 * @param para02 使用性質，如：C0301A1
	 * @param para03 建物結構+屋頂別，如：0350
	 * @param para04 總樓層數
	 * @param para05 自負額賠償比例，如：10
	 * @param para06 自負額金額，如：MIN30000
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getFb1Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String paraType, String para01, String para02, String para03, String para04, String para05, String para06) throws SystemException, Exception;
	
	/**
	 * B1爆炸險
	 * 
	 * @param firPremcalcTmp 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。F01
	 * @param kindcode 險別代碼，必填。B1
	 * @param para01 保險金額
	 * @param para02 爆炸等級，如：A 
	 * @param para03 自負額賠償比例，如：10
	 * @param para04 自負額金額，如：MIN30000
	 * @param fb1UndwrtAry 營業狀況詢問表結果 0-主險 ,1-非天災附加險,2-天災附加險
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getB1Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03, String para04, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception;

	/**
	 * B4航空器墜落、機動車輛碰撞險
	 * 
	 * @param firPremcalcTmp 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。F01
	 * @param kindcode 險別代碼，必填。B4
	 * @param para01 保險金額
	 * @param para02 自負額賠償比例，如：10
	 * @param para03 自負額金額，如：MIN30000
	 * @param fb1UndwrtAry 營業狀況詢問表結果 0-主險 ,1-非天災附加險,2-天災附加險
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getB4Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception;
	
	/**
	 * B5罷工、暴動、民眾騷擾、惡意破壞行為險
	 * 
	 * @param firPremcalcTmp 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。F01
	 * @param kindcode 險別代碼，必填。B5
	 * @param para01 保險金額
	 * @param para02 自負額賠償比例，如：10
	 * @param para03 自負額金額，如：MIN30000
	 * @param fb1UndwrtAry 營業狀況詢問表結果 0-主險 ,1-非天災附加險,2-天災附加險
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getB5Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception;

	/**
	 * B6自動消防裝置滲漏險
	 * 
	 * @param firPremcalcTmp 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。 F01
	 * @param kindcode 險別代碼，必填。B6
	 * @param para01 建物保險金額
	 * @param para02 建物以外財物保險金額
	 * @param para03  自負額賠償比例，如：10
	 * @param para04  自負額金額，如：MIN30000
	 * @param fb1UndwrtAry 營業狀況詢問表結果 0-主險 ,1-非天災附加險,2-天災附加險
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getB6Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03, String para04, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception;
	
	/**
	 * B7竊盜險
	 * 
	 * @param firPremcalcTmp 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。 F01
	 * @param kindcode 險別代碼，必填。B7
	 * @param para01 保險金額
	 * @param para02 自負額賠償比例，如：10
	 * @param para03 自負額金額，如：MIN30000
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getB7Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03, String para04, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception;

	/**
	 * BB煙燻險
	 * 
	 * @param firPremcalcTmp 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。 F01
	 * @param kindcode 險別代碼，必填。BB
	 * @param para01 保險金額
	 * @param para02 自負額賠償比例，如：10
	 * @param para03 自負額金額，如：MIN30000
	 * @param fb1UndwrtAry 營業狀況詢問表結果 0-主險 ,1-非天災附加險,2-天災附加險
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getBBPrem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception;
	
	/**
	 * BD水漬險
	 * 
	 * @param firPremcalcTmp 
	 * @param calcDate 費率基準日，必填。 例如：20190601
	 * @param channelType 通路別,例如：11
	 * @param riskcode 險種代碼，必填。 F01
	 * @param kindcode 險別代碼，必填。BD
	 * @param para01 保險金額
	 * @param para02 使用性質，如：B0001A1
	 * @param para03 自負額賠償比例，如：10
	 * @param para04 自負額金額，如：MIN30000
	 * @param fb1UndwrtAry 營業狀況詢問表結果 0-主險 ,1-非天災附加險,2-天災附加險
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getBDPrem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03, String para04, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception;
}

