package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps035DetailVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirCtbcRnproposalDtl;
import com.tlg.util.PageInfo;

/** mantis：FIR0459，處理人員：CC009，需求單編號：FIR0459 住火-APS中信續件要保書-排程查詢作業 
	mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
public interface FirCtbcRnproposalDtlDao extends IBatisBaseDao<FirCtbcRnproposalDtl, BigDecimal> {
	
	public List<Aps035DetailVo> selectJoinPc(PageInfo pageInfo)throws Exception;
	public int countJoinPc(Map<String,Object> params)throws Exception;
}