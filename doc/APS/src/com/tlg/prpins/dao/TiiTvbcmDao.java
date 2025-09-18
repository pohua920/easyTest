package com.tlg.prpins.dao;

import java.math.BigDecimal;
// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
import java.util.List;
//mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
import com.tlg.aps.vo.TiiTvbcmVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.TiiTvbcm;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能*/
public interface TiiTvbcmDao extends IBatisBaseDao<TiiTvbcm, BigDecimal> {
	
	public int selectMaxID() throws Exception;

	// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
	public List<TiiTvbcmVo> selectTiiTvbcm2() throws Exception;
}