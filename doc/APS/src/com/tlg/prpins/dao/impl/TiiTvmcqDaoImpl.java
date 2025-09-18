package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
//mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
import java.util.List;
//mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
import com.tlg.aps.vo.TiiTvmcqVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.TiiTvmcqDao;
import com.tlg.prpins.entity.TiiTvmcq;

/*mantis：CAR0427，處理人員：BJ085，需求單編號：CAR0427 機車強制車險重新投保發對接功能-第四次明信片*/
public class TiiTvmcqDaoImpl extends IBatisBaseDaoImpl<TiiTvmcq, BigDecimal> implements TiiTvmcqDao {
	
	@Override
	public String getNameSpace() {
		return "TiiTvmcq";
	}

	@Override
	public int selectMaxID() throws Exception {
		return (int) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectMaxID");
	}

	/**
	 * mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
	 */
	@Override
	public List<TiiTvmcqVo> selectTiiTvmcq2() throws Exception {
		List<TiiTvmcqVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectTiiTvmcq2");
		return queryForList;
	}
}