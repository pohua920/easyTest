package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps044DetailVo;
import com.tlg.aps.vo.FirRenewListForFileVo;
import com.tlg.aps.vo.FirRenewListForPremVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirRenewListDtlDao;
import com.tlg.prpins.entity.FirRenewListDtl;
import com.tlg.util.PageInfo;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public class FirRenewListDtlDaoImpl extends IBatisBaseDaoImpl<FirRenewListDtl, BigDecimal> implements FirRenewListDtlDao {
	
	@Override
	public String getNameSpace() {
		return "FirRenewListDtl";
	}
	
	@Override
	public List<FirRenewListForPremVo> selectRenewListForPrem(Map params) throws Exception {
		List<FirRenewListForPremVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectRenewListForPrem", params);
		return queryForList;
	}
	
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業 start*/
	@Override
	public List<Aps044DetailVo> selectForAps044Detail(PageInfo pageInfo) throws Exception {
		List<Aps044DetailVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps044Detail",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForAps044Detail(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps044Detail", params);
		return count;
	}
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業 end*/
	
	/**
	 * mantis：FIR0570_1，處理人員：DP0706，需求單編號：FIR0570_1 住火_APS每月應續件產生排程
	 */
	@Override
	public List<FirRenewListForPremVo> selectRenewListForMail(Map params) throws Exception {
		List<FirRenewListForPremVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectRenewListForMail", params);
		return queryForList;
	}
	
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
	@Override
	public List<FirRenewListForFileVo> selectRenewListForOtherFile(Map params) throws Exception {
		List<FirRenewListForFileVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectRenewListForOtherFile", params);
		return queryForList;
	}
	
	@Override
	public List<FirRenewListForFileVo> selectRenewListForCoreFile(Map params) throws Exception {
		List<FirRenewListForFileVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectRenewListForCoreFile", params);
		return queryForList;
	}
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end*/
}