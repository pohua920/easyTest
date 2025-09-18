package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.prpins.entity.Prpcmain;
import com.tlg.aps.vo.Aps032CompareVo;
import com.tlg.aps.vo.FirPahsinRenewalVo;
import com.tlg.aps.vo.WriteForBatchSendmailVo;
import com.tlg.iBatis.IBatisBaseDao;

public interface PrpcmainDao extends IBatisBaseDao<Prpcmain, BigDecimal> {
	
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  **/
	public List<Prpcmain> findAS400PrefilEpolicy()throws Exception;
	
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  **/
	public List<Prpcmain> findAS400HeccfilEpolicy()throws Exception;
	
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  **/
	public List<Prpcmain> findAS400TolfilEpolicy()throws Exception;
	
	/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 start*/
	public List<Aps032CompareVo> findToCompareTiiTvbcm(Map params);
	
	public List<Prpcmain> findPolicynoToTvbcm(Map params);
	/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 end*/
	
	/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險) start*/
	public List<WriteForBatchSendmailVo> findForBatchSendmail(Map params);
	
	public List<WriteForBatchSendmailVo> findForBatchSendmailDtl(Map params);
	/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險) end*/
	
	//mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程
	public FirPahsinRenewalVo findForPanhsinRenewalCoreData(Map params);
	
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  **/
	public List<Prpcmain> findAS400TAPrefilEpolicy()throws Exception;
	
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  **/
	public List<Prpcmain> findAS400TAHeccfilEpolicy()throws Exception;
	
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  **/
	public List<Prpcmain> findAS400TATolfilEpolicy()throws Exception;

	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  **/
	public List<Prpcmain> findForRerunFpolicy(Map params)throws Exception;
}