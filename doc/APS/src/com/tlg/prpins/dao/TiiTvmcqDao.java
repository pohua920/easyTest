package com.tlg.prpins.dao;

import java.math.BigDecimal;
//mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
import java.util.List;
//mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
import com.tlg.aps.vo.TiiTvmcqVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.TiiTvmcq;

/*mantis：CAR0427，處理人員：BJ085，需求單編號：CAR0427 機車強制車險重新投保發對接功能-第四次明信片*/
public interface TiiTvmcqDao extends IBatisBaseDao<TiiTvmcq, BigDecimal> {
	
	public int selectMaxID() throws Exception;

	// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
	public List<TiiTvmcqVo> selectTiiTvmcq2() throws Exception;
}