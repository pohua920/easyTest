package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps032CompareVo;
import com.tlg.aps.vo.FirPahsinRenewalVo;
import com.tlg.aps.vo.WriteForBatchSendmailVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpcmainDao;
import com.tlg.prpins.entity.Prpcmain;

public class PrpcmainDaoImpl extends IBatisBaseDaoImpl<Prpcmain, BigDecimal> implements PrpcmainDao {
	
	@Override
	public String getNameSpace() {
		return "Prpcmain";
	}
	
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  START**/
	@Override
	public List<Prpcmain> findAS400PrefilEpolicy() {
		List<Prpcmain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectAS400PrefilEpolicy",null);
		return queryForList;
	}
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  END**/
	
	
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  START**/
	@Override
	public List<Prpcmain> findAS400HeccfilEpolicy() {
		List<Prpcmain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectHeccFilEpolicy",null);
		return queryForList;
	}
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  END**/
	
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  START**/
	@Override
	public List<Prpcmain> findAS400TolfilEpolicy() {
		List<Prpcmain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectTolFilEpolicy",null);
		return queryForList;
	}
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  END**/
	
	/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 start*/
	@Override
	public List<Aps032CompareVo> findToCompareTiiTvbcm(Map params) {
		List<Aps032CompareVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectToCompareTiiTvbcm",params);
		return queryForList;
	}
	
 	@Override
	public List<Prpcmain> findPolicynoToTvbcm(Map params) {
		List<Prpcmain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectPolicynoToTvbcm",params);
		return queryForList;
	}
 	/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 end*/
 	
 	/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險) start*/
 	public List<WriteForBatchSendmailVo> findForBatchSendmail(Map params) {
		List<WriteForBatchSendmailVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForBatchSendmail",params);
		return queryForList;
	}
 	
 	public List<WriteForBatchSendmailVo> findForBatchSendmailDtl(Map params) {
 		List<WriteForBatchSendmailVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForBatchSendmailDtl",params);
 		return queryForList;
 	}
 	/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險) end*/
 	
 	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 */
 	@Override
 	public FirPahsinRenewalVo findForPanhsinRenewalCoreData(Map params) {
 		FirPahsinRenewalVo queryForObject = (FirPahsinRenewalVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectForPanhsinRenewalCoreData",params);
 		return queryForObject;
 	}
 	
 	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單   START**/
	@Override
	public List<Prpcmain> findAS400TAPrefilEpolicy() {
		List<Prpcmain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectTaAS400PrefilEpolicy",null);
		return queryForList;
	}
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  END**/
	
	
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  START**/
	@Override
	public List<Prpcmain> findAS400TAHeccfilEpolicy() {
		List<Prpcmain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectTaHeccFilEpolicy",null);
		return queryForList;
	}
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  END**/
	
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  START**/
	@Override
	public List<Prpcmain> findAS400TATolfilEpolicy() {
		List<Prpcmain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectTaTolFilEpolicy",null);
		return queryForList;
	}
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  END**/	

	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  start**/
	@Override
	public List<Prpcmain> findForRerunFpolicy(Map params) {
		List<Prpcmain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForRerunFpolicy",params);
		return queryForList;
	}
	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  end**/	
}