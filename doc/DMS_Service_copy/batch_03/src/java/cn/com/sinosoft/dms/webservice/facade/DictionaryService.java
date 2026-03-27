/*
 * @(#)DictionaryService.java
 *
 * Copyright 2009 sinosoft, Inc. All rights reserved.
 */
package cn.com.sinosoft.dms.webservice.facade;

import ins.framework.common.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.dms.model.PrpDagent;
import cn.com.sinosoft.dms.model.PrpDbank;
import cn.com.sinosoft.dms.model.PrpDclass;
import cn.com.sinosoft.dms.model.PrpDcode;
import cn.com.sinosoft.dms.model.PrpDcodeCom;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDcustomerIdv;
import cn.com.sinosoft.dms.model.PrpDdealer;
import cn.com.sinosoft.dms.model.PrpDdriver;
import cn.com.sinosoft.dms.model.PrpDexch;
import cn.com.sinosoft.dms.model.PrpDframe;
import cn.com.sinosoft.dms.model.PrpDitem;
import cn.com.sinosoft.dms.model.PrpDitemType;
import cn.com.sinosoft.dms.model.PrpDmaterialInfo;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDplane;
import cn.com.sinosoft.dms.model.PrpDport;
import cn.com.sinosoft.dms.model.PrpDration;
import cn.com.sinosoft.dms.model.PrpDrisk;
import cn.com.sinosoft.dms.model.PrpDriskClauseKind;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindId;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskEngageId;
import cn.com.sinosoft.dms.model.PrpDriskItem;
import cn.com.sinosoft.dms.model.PrpDriskItemId;
import cn.com.sinosoft.dms.model.PrpDriskLimit;
import cn.com.sinosoft.dms.model.PrpDriskLimitId;
import cn.com.sinosoft.dms.model.PrpDriskShortRate;
import cn.com.sinosoft.dms.model.PrpDriskShortRateId;
import cn.com.sinosoft.dms.model.PrpDship;
import cn.com.sinosoft.dms.model.PrpDstatistics;
import cn.com.sinosoft.dms.model.PrpDtype;
import cn.com.sinosoft.dms.vo.PrpDplan;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.xmlmsg.codetranslate.TranslateVO;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.RationObj;

/**
 * 
 * 
 * @author 姣嬮浄
 * @version 1.0 2009-7-9
 */
/**
 * @author hua
 * 
 */
public interface DictionaryService {

	/**
	 * 鍦≒rpDtype涓彇codeType瀵瑰簲鐨刢odeTypeDesc
	 * 
	 * @param systemCode
	 * @param codeType
	 * @param codeCode
	 * @param language
	 * @return
	 */
	public String codeTypeTranslate(String systemCode, String codeType);

	/**
	 * 鏀寔limitflag = '2'鐨勯檺棰濅唬鐮佺殑缈昏瘧
	 */
	public String translateLimit(String systemCode, String riskCode, String limitCode);
	/**
	 * 鍏戞崲閲戦
	 * */
	public double exchange(String systemCode, Date currDate, String baseCurrency, String exchCurrency, double amount);

	public List<PrpDcompany> getAllSubCompany(String systemCode, String comCode);

	public int getCount(String systemCode, String tableName, String condition);

	public List<Object> getListByCondition(String systemCode, String tableName, String condition);

	public PrpDagent getPrpDagent(String systemCode, String agentCode);

	public PrpDbank getPrpDbank(String systemCode, String bankCode);

	public PrpDnewCode getPrpDcode(String systemCode, String codeCode, String codeType);

	public PrpDcode getPrpDoldCode(String systemCode, Map values);

	public PrpDcompany getPrpDcompany(String systemCode, String comcode);

	public PrpDdealer getPrpDdealer(String systemCode, String dealerCode);

	public PrpDdriver getPrpDdriver(String systemCode, String drivingLicenseNo);

	public PrpDexch getPrpDexch(String systemCode, Date exchDate, String baseCurrency, String exchCurrency);

	public PrpDplane getPrpDplane(String systemCode, String licenceNo);

	public PrpDport getPrpDport(String systemCode, String portNo);

	public PrpDship getPrpDship(String systemCode, String shipCode);

	public PrpDtype getPrpDtype(String systemCode, String codeType);

	public List<PrpDnewCode> getSubCode(String systemCode, String codeType, String codeCode) throws Exception;

// public String getCodeLevel(String systemCode, String codeType,
// String codeCode);

	public List<PrpDcompany> getSubCompany(String systemCode, String comCode);

	public PrpDnewCode getUpperCode(String systemCode, String codeType, String codeCode);

	public PrpDcompany getUpperPrpDcompany(String systemCode, String comCode);

	/**
	 * 瀵归�鐢ㄤ唬鐮�PrpDcode 琛ㄤ腑鐨勪唬鐮佽繘琛屼唬鐮佺炕璇戯紝鎶婄紪鐮佺炕璇戞垚瀵瑰簲鐨勫悕绉�
	 * 
	 * @param systemCode
	 *            绯荤粺浠ｇ爜
	 * @param codeType
	 *            浠ｇ爜绫诲瀷
	 * @param codeCode
	 *            浠ｇ爜
	 * @param language
	 *            璇(C:涓枃/E:鑻辨枃)
	 * @return 浠ｇ爜鍚嶇О
	 */
	public String translateCode(String systemCode, String codeType, String codeCode, String codeFlag, String language);

	public List<PrpDnewCode> getPrpDcodeBytype(String systemCode, String codeType);

	public List<PrpDnewCode> findCodeByCondition(String systemCode, String condition);

	/**
	 * 閫氳繃涓婚敭鏌ヨprpDclass
	 * 
	 * @param systemcode
	 * @param classcode
	 * @return
	 */
	public List<PrpDclass> findPrpDclassById(String systemcode, String classcode);

	/**
	 * 閫氳繃涓婚敭鏌ヨprpDframe
	 * 
	 * @param systemcode
	 *            绯荤粺浠ｇ爜
	 * @param framecode
	 * @return
	 */
	public List<PrpDframe> findPrpDframeById(String systemcode, String framecode);

	/**
	 * 閫氳繃涓婚敭鏌ヨprpDitem
	 * 
	 * @param systemcode
	 * @param itemcode
	 * @return
	 */
	public List<PrpDitem> findPrpDitemById(String systemcode, String itemcode);

	/**
	 * 閫氳繃涓婚敭鏌ヨprpDrisk
	 * 
	 * @param systemcode
	 * @param riskcode
	 * @return
	 */
	public List<PrpDrisk> findPrpDriskById(String systemcode, String riskcode);

	/**
	 * 閫氳繃涓婚敭鏌ヨprpDplan
	 * 
	 * @param systemcode
	 * @param plancode
	 * @return
	 */
	public List<PrpDplan> findPrpDplanById(String systemcode, String plancode);

	/**
	 * 閫氳繃涓婚敭鏌ヨPrpDmaterialInfo
	 * 
	 * @param systemcode
	 * @param materialid
	 * @return
	 */
	public List<PrpDmaterialInfo> findPrpDmaterialInfoById(String systemcode, String materialid);

	/**
	 * 閫氳繃涓婚敭鏌ヨPrpDitemType
	 * 
	 * @param systemcode
	 * @param itemtype
	 * @return
	 */
	public List<PrpDitemType> findPrpDitemTypeById(String systemcode, String itemtype);

	/**
	 * 閫氳繃鏉′欢鏌ヨprpDcompnay
	 * 
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDcompany> findCompanyByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param prpDshortRateId
	 * @return
	 */

	/**
	 * @param systemcode
	 * @param prpDriskClauseKindId
	 * @return
	 */
	public List<PrpDriskClauseKind> findPrpDriskClauseKindById(String systemcode,
			PrpDriskClauseKindId prpDriskClauseKindId);

	/**
	 * @param systemcode
	 * @param prpDriskEngageId
	 * @return
	 */
	public List<PrpDriskEngage> findPrpDriskEngageById(String systemcode, PrpDriskEngageId prpDriskEngageId);

	/**
	 * @param systemcode
	 * @param prpDriskItemId
	 * @return
	 */
	public List<PrpDriskItem> findPrpDriskItemById(String systemcode, PrpDriskItemId prpDriskItemId);

	/**
	 * @param systemcode
	 * @param prpDriskLimitId
	 * @return
	 */
	public List<PrpDriskLimit> findPrpDriskLimitById(String systemcode, PrpDriskLimitId prpDriskLimitId);

	/**
	 * @param systemcode
	 * @param prpDriskShortRateId
	 * @return
	 */
	public List<PrpDriskShortRate> findPrpDriskShortRateById(String systemcode, PrpDriskShortRateId prpDriskShortRateId);

	// **************************************************************************
	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDclass> findPrpDclassByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDframe> findPrpDframeByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDitem> findPrpDitemByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDrisk> findPrpDriskByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
//	public List<PrpDplan> findPrpDplanByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDmaterialInfo> findPrpDmaterialInfoByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDitemType> findPrpDitemTypeByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDriskClauseKind> findPrpDriskClauseKindByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDriskEngage> findPrpDriskEngageByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDriskItem> findPrpDriskItemByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDriskLimit> findPrpDriskLimitByCondition(String systemcode, String condition);

	/**
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDriskShortRate> findPrpDriskShortRateByCondition(String systemcode, String condition);

	/**
	 * 閫氳繃鏉′欢鑾峰緱prpDcodeCom
	 * 
	 * @param systemcode
	 * @param condition
	 * @return
	 */
	public List<PrpDcodeCom> findPrpDcodeComByCondition(String systemcode, String condition);

	/*********************************** 鍒嗛〉瀹炵幇锛屼娇鐢▁stream瑙ｆ瀽xml **************************************/
	/**
	 * 閫氳繃codeType鑾峰緱浠ｇ爜
	 * 
	 * @param systemCode
	 * @param codeType
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public DictPage getCode(String systemCode, String codeType, String codeFlag, int pageNo, int pageSize)
			throws Exception;

	/**
	 * 閫氳繃鏉′欢鑾峰緱company锛屽垎椤�
	 * 
	 * @param systemCode
	 * @param condition
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public DictPage getCompany(String systemCode, String condition, int pageNo, int pageSize) throws Exception;

	/**
	 * @param systemCode
	 * @param codeType
	 * @param codeCode
	 *            浠ｇ爜 妯＄硦鏌ヨ
	 * @param codeCName
	 *            浠ｇ爜涓枃鍚�妯＄硦鏌ヨ
	 * @param riskCode
	 * @param IgnoreCode 蹇界暐浠ｇ爜 鍦ㄩ�杈戜腑鎺掗櫎浼犲叆鐨勪唬鐮侊紝鍏ュ弬鍙紶鍏ュ涓唬鐮侊紝鐢�,"鍒嗛殧
	 * @param extraCodeCode 鏂版坊鍔爀xtraCodeCode鍙傛暟,鐢ㄦ潵鏌ヨ鍘熼�杈戞湁鏁堢殑鏁版嵁 + 鏌ヨextraCodeCode涓嶇鏄惁鏈夋晥鐨勬暟鎹�
	 * @param pageNO
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public DictPage getCodeWithRisk(String systemCode, String codeType, String codeCode, String codeCName,
			String withCode, String ignoreCode,String extraCodeCode,String upperCode,int pageNo, int pageSize);

	public DictPage getPrpDCodeWithRisk(String systemCode, String codeType, String codeCode, String codeCName,
			String withCode, String ignoreCode,String extraCodeCode,String upperCode,int pageNo, int pageSize);

	public DictPage getOldCodeWithRisk(String systemCode, String codeType, String codeCode, String codeCName,
			String withCode, int pageNo, int pageSize);

	public DictPage getCodeWithCom(String systemCode, String codeType, String codeCode, String codeCName,
			String withCode, int pageNo, int pageSize);

	public DictPage getTaxAuthorities(String systemCode, String userCode, String comCode, int pageNo, int pageSize)
			throws Exception;

	public DictPage getRiskEngage(String systemCode, String riskCode, String language, String clauseCode, String engageCode, String extraEngageCodes,int pageNo,
			int pageSize,String extraCondition,String initFlag) throws Exception;//add by guyanqing 2012-02-06 reason:澧炲姞鍙墿灞曟煡璇㈡潯浠�

	public DictPage getRisk(String systemCode, String classCode, String riskCode, String reverseType, int pageNo,
			int pageSize) throws Exception;

	public DictPage getClass(String systemCode, String classCode, int pageNo, int pageSize) throws Exception;

	public DictPage getPrpDtreatyReten(String systemCode, Map values) throws Exception;

	public String synShortRiskData(String systemCode, Object data);

	public String synFrameDataData(String systemCode, Object data);

	public String synPlanData(String systemCode, Object data);

	public String synClassData(String systemCode, Object data);

	public DictPage getPrpDriskByCondition(String systemCode, Map values) throws Exception;
	
	//閫氳繃serverCode鏈嶅姟浠ｇ爜鍜宔nvironmentCode鐜鍙橀噺鑾峰彇ipServiceConfig琛ㄧ殑IP鏈嶅姟淇℃伅
	public DictPage getServiceInfoByCode(String systemCode, Map values);
	
	//閫氳繃environmentCode鐜鍙橀噺鑾峰彇ipServiceConfig琛ㄨ鐜鍙橀噺涓嬬殑鎵�湁绯荤粺IP鏈嶅姟淇℃伅
	public DictPage getServiceInfoByEnvironmentCode(String systemCode,Map values);

	public DictPage getUrlByCode(String systemCode, Map values);

	public DictPage getAgent(String systemCode, Map values) throws Exception;
	
	public DictPage getAgent(String systemCode, String agentCode,int pageNo,int pageSize) throws Exception;

	public DictPage getRiskClause(String systemCode, Map values) throws Exception;

	public DictPage getAccountInfo(String systemCode, Map values) throws Exception;

	public DictPage getRiskClauseKind(String systemCode, Map values) throws Exception;
	
	public DictPage getPrpDclauseKind(String systemCode, Map values) throws Exception;//added by yuyiqiang 20130226

	public DictPage getPrpDkind(String systemCode, Map values) throws Exception;
	
	public DictPage savePrpDcustomerIdv(String systemCode, Map values) throws Exception;
	
	public DictPage getPrpDcustomerIdv(String systemCode, Map values) throws Exception;
	
	public DictPage getPrpDcustomerUnit(String systemCode, Map values) throws Exception;
	
	public DictPage getPrpDcustomerFXQ(String systemCode, Map values) throws Exception;
	
	public DictPage savePrpDcustomerUnit(String systemCode, Map values) throws Exception;

	public DictPage getPrpDlimit(String systemCode, Map values) throws Exception;

	public DictPage getReinsurer(String systemCode, Map values) throws Exception;

	public DictPage getCoins(String systemCode, Map values) throws Exception;
	
	/*
	 * 鏍囩殑浠ｇ爜缈昏瘧 by wanghaibo 2011-01-20
	 */
	public DictPage getRiskItem(String systemCode, Map values) throws Exception;
	/*
	 * 闄愰/鍏嶈禂浠ｇ爜缈昏瘧 by wanghaibo 2011-01-20
	 */
	public DictPage getRiskLimit(String systemCode,Map values) throws Exception; 
	
	/*
	 * 鐗圭害浠ｇ爜缈昏瘧 by wanghaibo 2011-01-20
	 */
	public DictPage getReverseRiskEngage(String systemCode,Map values) throws Exception; 
	
	/*
	 * 闄愰鍏嶉
	 */
	public DictPage getPrpDriskLimit(String systemCode, Map values) throws Exception;

	/*
	 * 娓犻亾淇℃伅
	 */
	public String synPrpDAgentData(String systemCode, Object data) throws Exception;

	/*
	 * 鍥界灞�」鐩甈ICC鑱旂郴浜�
	 */
	public DictPage getPrpDsettlementLkr(String systemCode, Map values) throws Exception;

	/*
	 * 鍥界灞�」鐩竴绾ч绠楀崟浣�
	 */
	public DictPage getPrpDsettlementByr(String systemCode, Map values) throws Exception;

	/*
	 * 鏍规嵁makeCom妯＄硦鏌ヨPrpDstatistics琛ㄦ暟鎹�
	 */
	public DictPage getPrpDstatistics(String systemCode, String makeCom, int pageNo, int pageSize);

	/*
	 * 鏇存柊PrpDstatistics琛�鎻愪緵鐨勬暟鎹鏋滃瓨鍦ㄥ垯鏇存柊锛屼笉瀛樺湪鍒欎繚瀛�
	 */
	public void updatePrpDstatistics(String systemCode, PrpDstatistics prpDstatistics);

	/**
	 * prpDcode琛ㄤ唬鐮佺殑鏂版棫杞崲
	 * 
	 * @param systemCode
	 * @param codeType
	 * @param codeCode
	 * @param transType
	 *            1:new 鈫�old 锛宑odecode涓烘柊浠ｇ爜 2:old 鈫�new 锛宑odecode涓鸿�浠ｇ爜
	 * @return
	 */
	public List codeTransform(String systemCode, String codeType, String codeCode, String transType);

	/**
	 * 闄╃浠ｇ爜鏂拌�杞崲
	 * 
	 * @param systemCode
	 * @param riskCode
	 *            闈炵┖
	 * @param clauseCode
	 *            褰搑iskCode涓篋AA鏃讹紝涓嶈兘涓虹┖
	 * @param kindCode
	 *            褰搑iskCode涓篋AA鎴朌AB鏃讹紝涓嶈兘涓虹┖
	 * @param transtype
	 *            1: new 鈫�old 鍏ュ弬riskCode鏂颁唬鐮侊紝鍙﹀涓や釜鍏ュ弬娌＄敤 2锛�old 鈫�new 鍏ュ弬鐨嗕负鑰佷唬鐮侊紝
	 * @return 1锛�杩斿洖鍞竴鐨凱rpDrisk瀵硅薄 2: 杩斿洖鑰佷骇鍝佸搴旂殑鏁版嵁瀵硅薄
	 */
	public List riskTransform(String systemCode, String riskCode, String clauseCode, String kindCode, String transType)
			throws Exception;

	/**
	 * 鎵归噺浠ｇ爜缈昏瘧
	 * 
	 * @param systemCode
	 * @param voList
	 *            瑕佺炕璇戠殑浠ｇ爜瀵硅薄
	 * @param transType
	 *            1锛�code 鈫�name 2锛�name 鈫�code
	 * @return
	 */
	public List codeTranslate(String systemCode, List<TranslateVO> voList, String transType);

	public DictPage getPrpDcurrency(String systemCode, String currencyCode, String currencyName, String validStatus,
			int pageNo, int pageSize) throws Exception;

	/**
	 * 宸ㄧ伨鏌ヨ鎺ュ彛
	 * 
	 * @param systemCode
	 * @param disasterCodeOrName
	 *            浼犲叆鐨勫弬鏁颁负浠ｇ爜鎴栨槸鍚嶅瓧
	 * @param validStatus
	 *            鏈夋晥鐘舵�
	 * @return
	 * @throws Exception
	 */
	public DictPage getPrpDdisaster(String systemCode, String disasterCodeOrName, String validStatus, Date damageDate,int pageNO,
			int pageSize) throws Exception;

	public DictPage getPrpDtype(String systemCode, String codeType, String codeTypeName, String validStatus,
			int pageNO, int pageSize) throws Exception;
	public DictPage getClass(String systemCode, String classCode, String reverseType, String validStatus, int pageNo,
			int pageSize) throws Exception;
	
	public RationObj getRationInfo(String systemCode, PrpDration prpDration) throws Exception;
	// @desc 鏂规鏂版柟娉曪紝澧炲姞prpdrationPeriodrate 淇濊垂/璐圭巼鍖洪棿琛�prpdChannelinfo --涓�淇℃伅琛�绛夎〃淇℃伅* @author wpf * @date 2012-3-29
	public RationObj getRationInfoNew(String systemCode,PrpDration prpDration ,String agentCode,String comCode,String startDate,String startHour,String endDate,String endHour,String policyWayCode)throws Exception;
	/** 2010-05-12 by wanghaibo*/
	public DictPage getPrpDcrossOrg(String systemCode,String orgcod,String comp_cod,String org_lvl,
			int pageNO, int pageSize)throws Exception;
	
	/** 2010-07-19 by wanghaibo*/
	public DictPage getContractManage(String systemCode,String contractObjectCode,String validStatus,
			int pageNO, int pageSize)throws Exception;
	
	/** 2010-07-21 by wanghaibo*/
	public DictPage getPlan(String systemCode, String planCode, String riskCode ,String[] comCodes,String rationType, int pageNo, int pageSize)throws Exception;
	
	/** 2010-07-22 by wanghaibo*/
	public DictPage getIdentity(String systemCode,String identifierCode,String identifierName,String portCode,String portName,String countryCode,String countryCName,String countryEName,String identifierType,int pageNO,int pageSize)throws Exception;
	
	/** 2010-09-15 by wanghaibo*/
	public DictPage getPrpDcurrencyAndExchRate(String systemCode,String currencyCode,String currencyName,String validStatus,int pageNO,int pageSize)throws Exception;
	
	/** 2010-09-28 by wanghaibo*/
	public String getPlanWhetherHasFixed(String systemCode,String riskCode);
	
	/** 2010-12-16 by wanghaibo*/
	public DictPage getSimpleTreaty(String systemCode,String classCode,String riskCode,String sectionNo,String startDate,String endDate,int pageNo,int pageSize)throws Exception;

	/** 2011-01-04 by wanghaibo*/
	public DictPage getTradeCodes(String systemCode,String upperCode,String riskCode,int pageNo,int pageSize) throws Exception;
	
	/** 2011-01-05 by wanghaibo*/
	public DictPage getShortRate(String systemCode, String riskCode, String clauseCode, String rateType, int newShortTerm, int oldShortTerm, int pageNo, int pageSize) throws Exception;

	/** 2011-01-20 by wanghaibo*/
	public List reverseCodeTyeAndCode(String systemCode, List codeVoList, String reverseType) throws Exception;

	public DictPage getBank(String systemCode, String bankCode, String bankName, int pageNo, int pageSize) throws Exception;
	
	
	/**
	 * 鑾峰彇浜у搧鐨勬爣鐨勪俊鎭�
	 * @param systemCode
	 * @param riskCode 
	 * 				浜у搧浠ｇ爜
	 * @param upperItemCode
	 * 				鏍囩殑涓婄骇浠ｇ爜
	 * @param itemCode
	 * 				鏍囩殑浠ｇ爜
	 * @param clauseCode
	 * 				鏉℃浠ｇ爜
	 * @param  extraItemCode
	 * 			    鏂版坊鍔爀xtraItemCode鍙傛暟,鐢ㄦ潵鏌ヨ鍘熼�杈戞湁鏁堢殑鏁版嵁 + 鏌ヨextraItemCode涓嶇鏄惁鏈夋晥鐨勬暟鎹�
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	public DictPage getPrpDriskItem(String systemCode, String riskCode,
			String itemCode, String upperItemCode, String clauseCode,String extraItemCode,int pageNo, int pageSize) throws Exception;
	/**
	 * 鑾峰彇娓彛鍒楄〃淇℃伅
	 * @param systemCode
	 * @param portCode
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public DictPage getPrpDports(String systemCode, String portCode, int pageNo, int pageSize) throws Exception;

	public DictPage getShortRate(String systemCode, String riskCode, String clauseCode, String rateType,int shortTerm,int pageNo,int pageSize) throws Exception;
	
	public DictPage getRationShortRate(String systemCode, String riskCode, String areaCode, String clauseCode, String rateType, String rationCode,Date startDate,int startHour, Date endDate, int endHour,int pageNo,int pageSize) throws Exception;
	public DictPage getShortRateNew(String systemCode, String riskCode,  String clauseCode, String rateType, Date startDate,int startHour, Date endDate, int endHour,int pageNo,int pageSize) throws Exception;// modify update by renshuo 2011-10-13 
	
	/**
	 * @param systemCode	绯荤粺浠ｇ爜
	 * @param comCodeOrName	鏈烘瀯浠ｇ爜鎴栧悕绉�
	 * @param upperComcode	涓婄骇鏈烘瀯浠ｇ爜锛堢簿纭尮閰嶏級
	 * @param flag			鏍囧織浣嶏紙绮剧‘鍖归厤锛�
	 * @param validStatus	鏈夋晥鐘舵�
	 * @return
	 */
	public DictPage getCompanys(String systemCode,String comCodeOrName,String upperComCode,String flag,String validStatus,int pageNo,int pageSize) throws Exception;
	
	public DictPage getProjects(String systemCode,String projectCode,String comCode,int pageNo,int pageSize) throws Exception;
	
	public DictPage getResource(String systemCode,String resourceCodeOrName,String projectCode,String agentCode,String comCode,int pageNo,int pageSize) throws Exception;
	
	/**
	 * @param riskCode	   绯荤粺浠ｇ爜
	 * @param riskCode 	   浜у搧浠ｇ爜
	 * @param codeType     浠ｇ爜绫诲瀷
	 * @param kindCode 	   璐ｄ换浠ｇ爜
	 * author wanghaibo 2011-03-11
	 */
	public DictPage getPrpDcodeKind(String systemCode, String riskCode,String codeType,String kindCode,int pageNo, int pageSize)throws Exception;
	
	/** 璁＄畻宸ヤ綔鏃ユ帴鍙�**/
	public Date countWorkDay(String systemCode,Date date, int n,String flag)throws Exception;
	
	/** 璁＄畻宸ヤ綔鏃ユ帴鍙�**/
	public DictPage getItem(String systemCode,String riskCode,int pageNo,int pageSize)throws Exception;
	
	/** 鑾峰彇娴峰浠ｇ悊浜鸿缁嗘弿杩颁俊鎭帴鍙�**/
	public DictPage getIdentityDesc(String systemCode,String identifierCode,int pageNo,int pageSize)throws Exception;
	/** MODIFY BEGIN-ADD-chenyi-20110513-reason:鏌ヨ绀句繚鍦版柟鏀跨瓥璧勬枡 */
	public DictPage getInfomation(String systemCode, Map values) throws Exception;
	/** MODIFY BEGIN-ADD-renshuo-20110513-reason:鏌ヨ浜岀骇璐ｄ换 */
	public DictPage getRiskClauseKindSub(String systemCode, Map values) throws Exception;
	public DictPage getRiskClauseKindRelation(String systemCode, Map values) throws Exception;//modify add by renshuo reason:澧炲姞鏉℃璐ｄ换浜掓枼鏉′欢鏌ヨ
	public DictPage getPrpDkindProduct(String systemCode, Map values);
	
	public DictPage getAllowcarKind(String systemCode, Map values); //begin add by zhongjiang 用于获取险别关系信息 end
	/**
	 * 浜у搧鍒涙柊鍚屾浜у搧淇鏁版嵁
	 * guyanqing
	 * */
	public String synModiyRiskData(Object riskObj_dms) throws Exception;
	
	/**
	 * 浜у搧鍒涙柊鍚屾浜у搧搴熸
	 * guyanqing 2011-09-28
	 * */
	public String synReviseRiskData(Object riskObj_dms) throws Exception;
	
	/**
	 * 浜у搧鍒涙柊鍚屾鏉℃搴熸
	 * guyanqing 2011-09-28
	 * */
	public String synReviseClauseData(Object riskObj_dms) throws Exception;
	
	/**
	 * 鏂规璐圭巼鏌ヨ
	 * guyanqing 2011-10-27
	 * */
	public DictPage getRationRate(String systemCode, String riskCode, String areaCode, String clauseCode, String kindCode, String rationCode,Date startDate,int startHour , Date endDate, int endHour,int pageNo,int pageSize)throws Exception;

	//add by xuli 20130623	
	public DictPage getPrpDkindReport(String systemCode, Map values)throws Exception;
	
	//add by cuishang 20140213
	public String synClauseReportData(Object riskObj_dms) throws Exception;

	//ADD BY pengxiaohui 查询方案集合
    public DictPage getListByPlanNo(String systemCode, Map values) throws Exception;
   //add by fengyang 20140402
	public String synProductSetData(String systemCode, Object data);

	//查询套装商品和方案关联集合
    public DictPage getSeriesListBySeriesNo(String systemcode, Map<String, String> values) throws Exception;

    /**
     * 
     * getRationListByRationName:(根据方案名称查询方案集合). <br/>
     *
     * @author pengxiaohui
     * @param systemcode
     * @param values
     * @return
     * @throws Exception
     * @since JDK 1.7
     */
    public DictPage getRationListByRationName(String systemcode, Map<String, String> values) throws Exception;

    /**
     * 
     * getRationRalationListBySeriesCode:(根据套装商品代码查询方案集合). <br/>
     *
     * @author pengxiaohui
     * date: 2014-4-15 下午2:11:45 <br/>
     * @param systemcode
     * @param values
     * @return
     * @since JDK 1.7
     */
    public DictPage getRationRalationListBySeriesCode(String systemcode, Map<String, String> values) throws Exception;

    /**
     * 
     * getRationRalationListBySeriesCode:(根据套装商品代码查询方案集合). <br/>
     *
     * @author pengxiaohui
     * date: 2014-4-15 下午2:11:45 <br/>
     * @param systemcode
     * @param values
     * @return
     * @since JDK 1.7
     */
    public DictPage getStartPlaceInfo(String systemcode, Map<String, String> values) throws Exception;

    /**
     * 
     * getSetCodeDb:(套装商品双击域查询). <br/>
     *
     * @author pengxiaohui
     * date: 2014-4-16 上午10:54:08 <br/>
     * @param systemcode
     * @param values
     * @return
     * @throws Exception
     * @since JDK 1.7
     */
    public DictPage getSetCodeDb(String systemcode, Map<String, String> values) throws Exception;
    //add by fengyang 船舶校验信息
	public DictPage getPrDitemShip(String systemCode, Map values) throws Exception;
	//add by fengyang 保存新增船舶信息
	public DictPage savePrpDitemShip(String systemCode, Map values) throws Exception;
	//add by fengyang  20140524  飞机校验信息 
    public DictPage getPrDplane(String systemCode, Map values) throws Exception;
  //add by fengyang 20140526 保存新增飞机信息
  	public DictPage savePrpDplane(String systemCode, Map values) throws Exception;

  	/**
  	 * 
  	 * getUserCodeMCInfo:(查询法人信息). <br/>
  	 *
  	 * @author pengxiaohui
  	 * @date: 2014-6-3 下午7:47:16 <br/>
  	 * @param systemcode
  	 * @param values
  	 * @return
  	 * @throws Exception
  	 * @since JDK 1.7
  	 */
    public DictPage getUserCodeMCInfo(String systemcode, Map<String, String> values) throws Exception;

    /**
     * 
     * saveUserCodeMCInfo:(最低保费维护保存). <br/>
     *
     * @author pengxiaohui
     * @date: 2014-6-4 下午6:23:42 <br/>
     * @param systemcode
     * @param values
     * @return
     * @since JDK 1.7
     */
    public DictPage saveUserCodeMCInfo(String systemcode, Map<String, String> values) throws Exception;

    public DictPage getReportNoByClauseCode(String systemcode, Map<String, String> values) throws Exception;

    /**
     * 
     * getRationPreiumListByCondition:(根据查询条件到dms中查询方案对应的保费). <br/>
     *
     * @author pengxiaohui
     * @date: 2014-7-25 下午3:01:07 <br/>
     * @param systemcode
     * @param values
     * @return
     * @throws Exception
     * @since JDK 1.7
     */
    public DictPage getRationPreiumListByCondition(String systemcode, Map<String, String> values) throws Exception;
    
	/**
	 *  add by  yjm  20150331 查詢特別約定
	 * @param systemCode
	 * @param values
	 * @return
	 */
	public DictPage getEngageMaintenance(String systemcode, Map<String, String> values);
	
	/**
	 *  add by  yjm  20150331 保存特別約定
	 * @param systemCode
	 * @param values
	 * @return
	 */

	public DictPage saveEngageMaintenance(String systemCode, Map values);
	
	/**
	 *  add by  yjm  20150331 查詢特別約定
	 * @param systemCode
	 * @param values
	 * @return
	 */
	public DictPage getClauseMaintenance(String systemcode, Map<String, String> values);
	
	/**
	 *  add by  yjm  20150331 保存條款
	 * @param systemCode
	 * @param values
	 * @return
	 */

	public DictPage saveClauseMaintenance(String systemCode, Map values);
	
    /**
 * add by  mjx  查询专案  20150216
 * @param systemcode
 * @param values
 * @return
 */
	public DictPage getCopyNumber(String systemcode, Map<String, String> values);
	/**
	 *  add by  mjx  20150225 保存文案号
	 * @param systemCode
	 * @param values
	 * @return
	 */

	public DictPage saveCopyNumber(String systemCode, Map values);
/**
 * add  by  mjx  20150226 獲取數據通過條款號碼
 * @param systemcode
 * @param values
 * @return
 */
	public DictPage getCopyNumberClauseCode(String systemcode,
			Map<String, String> values);
/**
 * add by  mjx  20150228 查询文案号信息
 * @param systemcode
 * @param values
 * @return
 */
public DictPage getOccupation(String systemcode, Map<String, String> values);

/**
 * add by  yjm  20150714 查询文案号信息
 * @param systemcode
 * @param values
 * @return
 */
public DictPage getOccupationById(String systemcode, Map<String, String> values);
/**
 * 保存或修改职业类别 add by mjx 20150302 
 * @param systemCode
 * @param values
 * @return
 */
public DictPage saveOrUpdateOccupation(String systemCode, Map values);

/**
 * add by yjm 伤害险险种详细信息查询（通报用） 20150729
 * @param prpCpolicyVo
 * @param pageNo
 * @param pageSize
 * @param userInfo
 * @return
 */
public DictPage getClauseInfo(String systemcode, Map<String, String> values);

//add by lekaifeng 验证業務來源与套装商品是否匹配  20160224
public DictPage cleckSavePolicy(Map<String, String> values);

/**
 * 查詢導入的年份，在年份下拉選中有沒有配置
 * @return
 */
public DictPage findYear(String systemCode,Map<String, String> values);

/**
 * 導入數據中的年份再下拉選中沒配置，則需在下拉選中新增一條年份
 */
public void insertNewYear(String systemCode,Map<String, String> values);

/**
 * 按條件查找港口代碼
 * prpDstartPlace
 * @return
 */
public DictPage findprpDstartPlaceByQuery(String systemCode,Map<String, String> values);

/**
 * 新增前校驗代碼是否重複
 * @return
 */
public DictPage insertCheck(String systemCode,Map<String, String> values);

/**
 * 新增港口代碼數據
 * add by liuyang 20160902
 */
public void insertPrpDstartPlace(String systemCode,Map<String, String> values);

/**
 * 刪除一條港口代碼
 * @param systemCode
 * @param values
 */
public void deletePrpDstartPlace(String systemCode,Map<String, String> values);

/**
 * 查詢一條港口代碼數據
 * @param systemCode
 * @param values
 * @return
 */
public DictPage searchStartPlace(String systemCode,Map<String, String> values);

/**
 * 保存港口代碼修改數據
 * @param systemCode
 * @param values
 */
public void saveStartPlace(String systemCode,Map<String, String> values);
}
