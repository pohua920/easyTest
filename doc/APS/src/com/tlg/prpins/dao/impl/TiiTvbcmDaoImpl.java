package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
import java.util.List;
// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
import com.tlg.aps.vo.TiiTvbcmVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.TiiTvbcmDao;
import com.tlg.prpins.entity.TiiTvbcm;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 */
public class TiiTvbcmDaoImpl extends IBatisBaseDaoImpl<TiiTvbcm, BigDecimal> implements TiiTvbcmDao {
	
	@Override
	public String getNameSpace() {
		return "TiiTvbcm";
	}

	@Override
	public int selectMaxID() throws Exception {
		return (int) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectMaxID");
	}

	/**
	 * mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
	 */
	@Override
	public List<TiiTvbcmVo> selectTiiTvbcm2() throws Exception {
		List<TiiTvbcmVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectTiiTvbcm2");
		return queryForList;
	}
}