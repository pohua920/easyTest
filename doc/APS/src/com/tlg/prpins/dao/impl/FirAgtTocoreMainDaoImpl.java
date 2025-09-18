package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.enums.EnumYCBFile;
import com.tlg.aps.vo.Aps055BotGenFileVo;
import com.tlg.aps.vo.Aps060YcbGenFileVo;
import com.tlg.aps.vo.Aps060YcbGenPolicyFileVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtTocoreMainDao;
import com.tlg.prpins.entity.FirAgtTocoreMain;

public class FirAgtTocoreMainDaoImpl extends IBatisBaseDaoImpl<FirAgtTocoreMain, BigDecimal> implements FirAgtTocoreMainDao {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	@Override
	public String getNameSpace() {
		return "FirAgtTocoreMain";
	}

	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public List<Aps055BotGenFileVo> selectBotReFileData(Map params) throws Exception {
		List<Aps055BotGenFileVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectBotReFileData",params);
		return queryForList;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public List<Aps055BotGenFileVo> selectBotEnFileData(Map params) throws Exception {
		List<Aps055BotGenFileVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectBotEnFileData",params);
		return queryForList;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public List<FirAgtTocoreMain> selectDistinctExtracomcodeByParams(Map params) throws Exception {
		List<FirAgtTocoreMain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectDistinctExtracomcodeByParams",params);
		return queryForList;
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public List<Aps060YcbGenFileVo> selectYcbRnFileData(Map params) throws Exception {
		List<Aps060YcbGenFileVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectYcbRnFileData",params);
		return queryForList;
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public List<Aps060YcbGenFileVo> selectYcbEnFileData(Map params) throws Exception {
		List<Aps060YcbGenFileVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectYcbEnFileData",params);
		return queryForList;
	} 
	
	/**
	 * FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔 
	 */
	@Override
	public List<Aps060YcbGenPolicyFileVo> selectYcbFileData(EnumYCBFile fileType, Map params) throws Exception {
		List<Aps060YcbGenPolicyFileVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace() + "." + fileType.getSelectId() ,params);
		return queryForList;
	}
	
	
	/** mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案*/
	public boolean updateByOid(FirAgtTocoreMain firAgtTocoreMain){
		String nameSpace = getNameSpace()+".updateByOid";
		int i = getSqlMapClientTemplate().update(nameSpace, firAgtTocoreMain);
		if(i >= 1) {
			return true;
		}
		return false;
	}
}