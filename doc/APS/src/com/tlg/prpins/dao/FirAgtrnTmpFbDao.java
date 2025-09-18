package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAgtrnTmpFb;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業  **/
public interface FirAgtrnTmpFbDao extends IBatisBaseDao<FirAgtrnTmpFb, BigDecimal> {
	
	public List<FirAgtrnTmpFb> findForFbDiffFile(Map params) throws Exception;
	
	public List<FirAgtrnTmpFb> findForFbProcessCenter(Map params) throws Exception;
}