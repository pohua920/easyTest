package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
import java.util.List;
import java.util.Map;
// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirRenewListDao;
import com.tlg.prpins.entity.FirRenewList;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public class FirRenewListDaoImpl extends IBatisBaseDaoImpl<FirRenewList, BigDecimal> implements FirRenewListDao {
	
	@Override
	public String getNameSpace() {
		return "FirRenewList";
	}

	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
	@Override
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectMainData(Map params) throws Exception {
		return  (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList(getNameSpace()+".selectMainData",params);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectInsuredData(Map params) throws Exception {
		return  (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList(getNameSpace()+".selectInsuredData",params);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectAddressData(Map params) throws Exception {
		return  (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList(getNameSpace()+".selectAddressData",params);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectPropData(Map params) throws Exception {
		return  (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList(getNameSpace()+".selectPropData",params);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectMortgageeData(Map params) throws Exception {
		return  (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList(getNameSpace()+".selectMortgageeData",params);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectItemkindData(Map params) throws Exception {
		return  (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList(getNameSpace()+".selectItemkindData",params);
	}
	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end
}