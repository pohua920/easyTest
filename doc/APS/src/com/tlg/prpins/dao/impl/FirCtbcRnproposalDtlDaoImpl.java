package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps035DetailVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcRnproposalDtlDao;
import com.tlg.prpins.entity.FirCtbcRnproposalDtl;
import com.tlg.util.PageInfo;

/** mantis：FIR0459，處理人員：CC009，需求單編號：FIR0459 住火-APS中信續件要保書-排程查詢作業 
	mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
public class FirCtbcRnproposalDtlDaoImpl extends IBatisBaseDaoImpl<FirCtbcRnproposalDtl, BigDecimal> implements FirCtbcRnproposalDtlDao {
	@Override
	public String getNameSpace() {
		return "FirCtbcRnproposalDtl";
	}

	@Override
	public List<Aps035DetailVo> selectJoinPc(PageInfo pageInfo) throws Exception {
		List<Aps035DetailVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectJoinPc",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countJoinPc(Map<String, Object> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countJoinPc", params);
		return count;
	}

	
	

}