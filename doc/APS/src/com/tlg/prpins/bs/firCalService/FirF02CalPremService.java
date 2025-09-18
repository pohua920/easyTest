package com.tlg.prpins.bs.firCalService;

import java.util.Map;

import com.tlg.exception.SystemException;

public interface FirF02CalPremService {
	

	/**
	 * FR2-基本地震保險
	 * 
	 * @param calcDate 費率基準日，必填。	例如：20190601
	 * @param riskcode 險種代碼，必填。	F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	FR2
	 * @param para01 地震保額
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getFR2Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception;
	/**
	 * RFA-家庭財務損失保險-基本地震：計算方式請參閱「FR2-基本地震保險」，KINDCODE = RFA。
	 * 
	 * @param calcDate 費率基準日，必填。	例如：20190601
	 * @param riskcode 險種代碼，必填。	F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RFA
	 * @param para01 地震保額
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRFAPrem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception;
	
	/**
	 * FR3-住宅火災保險
	 * 
	 * @param calcDate 費率基準日，必填。	例如：20190601
	 * @param riskcode 險種代碼，必填。	F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	FR3
	 * @param paraType 參數類型，必填。	1 = 住宅、2 = 公共宿舍、3 = 連幢住宅
	 * @param para01 火險保額，例如：1000000
	 * @param para02 建築結構，例如03
	 * @param para03 屋頂別，例如50
	 * @param para04 總樓層，例如15
	 * @param para05 同棟樓是否有營業
	 * @param para06 如果KINDCODE = ‘FR3’，此欄位不處理。如果KINDCODE = ‘RFB’，此欄位必填。11=建築物、12-建築物內動產、13=建築物及其動產 (AS400同步新增)、14=個別約定之建築物 (AS400同步新增)、15=個別約定之動產
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getFR3Prem(String calcDate, String channelType, String riskcode, String kindcode, String paraType, String para01, String para02, String para03, String para04, String para05, String para06) throws SystemException, Exception;
	/**
	 * RFB-家庭財務損失保險-住宅火災
	 * 
	 * @param calcDate 費率基準日，必填。	例如：20190601
	 * @param riskcode 險種代碼，必填。	F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RFB
	 * @param paraType 參數類型，必填。	1 = 住宅、2 = 公共宿舍、3 = 連幢住宅
	 * @param para01 火險保額，例如：1000000
	 * @param para02 建築結構，例如03
	 * @param para03 屋頂別，例如50
	 * @param para04 總樓層，例如15
	 * @param para05 同棟樓是否有營業
	 * @param para06 如果KINDCODE = ‘FR3’，此欄位不處理。如果KINDCODE = ‘RFB’，此欄位必填。11=建築物、12-建築物內動產、13=建築物及其動產 (AS400同步新增)、14=個別約定之建築物 (AS400同步新增)、15=個別約定之動產
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRFBPrem(String calcDate, String channelType, String riskcode, String kindcode, String paraType, String para01, String para02, String para03, String para04, String para05, String para06) throws SystemException, Exception;
	
	/**
	 * RFC-家庭財務損失保險-機車火災
	 * 
	 * @param calcDate 費率基準日，必填。	例如：20190601
	 * @param riskcode 險種代碼，必填。	F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RFC
	 * @param para01 機車火災保額
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRFCPrem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception;
	
	/**
	 * RFD-家庭財務被竊損失保險
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RFD
	 * @param para01 每件特定物品保額，例如10000
	 * @param para02 特定物品保額，例如150000
	 * @param para03 財務被竊保額，例如300000
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRFDPrem(String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03) throws SystemException, Exception;
	
	/**
	 * RFE-家庭日常生活責任保險
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RFE
	 * @param para01 生活責任保額，例如500000。
	 * @param para02 自負額，例如0、2500。
	 * @param para03 慰問金住院保額，例如10000。
	 * @param para04 慰問金死亡保額，例如100000
	 * @param para05 慰問金保期內保額，例如200000
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRFEPrem(String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03, String para04, String para05) throws SystemException, Exception;
	/**
	 * RF01-擴大家庭災害費用補償
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RF01
	 * @param para01 基本保險金額，例如：50000。
	 * @param para02 地震保險金額，例如：50000。
	 * @param para03 颱風洪水保險金額，例如：50000。
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRF01Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03) throws SystemException, Exception;
	/**
	 * RF02-家事代勞費用保費
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RF02
	 * @param para01 家事代勞保險金額，例如：1000。
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRF02Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception;
	
	/**
	 * RF03-地震災害修復費用
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RF03
	 * @param para01 地震災害保險金額，例如：100000。
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRF03Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception;
	
	/**
	 * RF04-特定事故房屋跌價損失補償
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RF04
	 * @param para01 房屋跌價補償保險金額，例如：100000。
	 * @param para02 清理費用保險金額，例如：10000。
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRF04Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02) throws SystemException, Exception;
	
	/**
	 * RF05-特定事故房屋租金補償保險
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RF05
	 * @param para01 房屋租金補償保險金額，例如：100000。
	 * @param para02 清理費用保險金額，例如：10000。
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRF05Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02) throws SystemException, Exception;

	/**
	 * RF06-住宅鑰匙門鎖費用補償保險
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RF06
	 * @param para01 每一事故保額，例如：1000。
	 * @param para02 保險期間累計保額，例如3000
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRF06Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02) throws SystemException, Exception;

	/**
	 * RF07-寵物特定事故意外費用補償
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RF06
	 * @param para01 寵物意外費用保險金額，例如：10000。
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRF07Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception;

	/**
	 * RF08-住宅輕損地震損失
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RF06
	 * @param para01 輕損地震保險金額，例如：100000。
	 * @param para02 自負額比率，例如：0.05
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRF08Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02) throws SystemException, Exception;

	/**
	 * RF09-住宅火險附加家庭成員傷害保險
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RF09
	 * @param para01 身故或喪葬費用保險金額，例如：100000。
	 * @param para02 失能保險金額，例如100000。
	 * @param para03 住院保險金額，例如10000。
	 * @param para04 加護病房保險金額，例如10000。
	 * @param para05 住院慰問保險金額，例如10000。
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRF09Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01, 
			String para02, String para03, String para04, String para05) throws SystemException, Exception;

	/**
	 * RF10-住家綠能升級附加條款
	 * 
	 * @param calcDate 費率基準日，必填。例如：20190601
	 * @param riskcode 險種代碼，必填。F02
	 * @param channelType 通路別,例如：11
	 * @param kindcode 險別代碼，必填。	RF09
	 * @param para01 1 家庭綜合保險-甲式、2 家庭綜合保險-乙、丙、丁式、新家綜、3 住宅火災及地震基本保險
	 * @param fr3Map FR3住宅火險或RFB居綜住火
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getRF10Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01, Map fr3Map) throws SystemException, Exception;
}
