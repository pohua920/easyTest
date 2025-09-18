package com.tlg.prpins.dao;

import java.math.BigDecimal;
// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
import java.util.List;
import java.util.Map;
// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirRenewList;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public interface FirRenewListDao extends IBatisBaseDao<FirRenewList, BigDecimal> {

	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectMainData(Map params) throws Exception;

	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectInsuredData(Map params) throws Exception;

	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectAddressData(Map params) throws Exception;

	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectPropData(Map params) throws Exception;

	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectMortgageeData(Map params) throws Exception;

	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectItemkindData(Map params) throws Exception;
	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end
}