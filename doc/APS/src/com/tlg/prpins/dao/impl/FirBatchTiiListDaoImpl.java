package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.FirTiiDataVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchTiiListDao;
import com.tlg.prpins.entity.FirBatchTiiList;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
public class FirBatchTiiListDaoImpl extends IBatisBaseDaoImpl<FirBatchTiiList, BigDecimal> implements FirBatchTiiListDao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchTiiList";
	}
	
	@Override
	public List<FirTiiDataVo> selectForCountProcType(Map<String, String> params) throws Exception {
		List<FirTiiDataVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForCountProcType",params);
		return queryForList;
	}

}