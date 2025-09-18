package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtrnTmpFbDao;
import com.tlg.prpins.entity.FirAgtrnTmpFb;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業  **/
public class FirAgtrnTmpFbDaoImpl extends IBatisBaseDaoImpl<FirAgtrnTmpFb, BigDecimal> implements FirAgtrnTmpFbDao {
	@Override
	public String getNameSpace() {
		return "FirAgtrnTmpFb";
	}
	
	@Override
	public List<FirAgtrnTmpFb> findForFbDiffFile(Map params) throws Exception {
		List<FirAgtrnTmpFb> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectFbDiffFile",params);
		return queryForList;
	}
	
	@Override
	public List<FirAgtrnTmpFb> findForFbProcessCenter(Map params) throws Exception {
		List<FirAgtrnTmpFb> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectFbProcessCenter",params);
		return queryForList;
	}
}