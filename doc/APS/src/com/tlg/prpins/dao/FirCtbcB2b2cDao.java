package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.aps.vo.Aps039DetailVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirCtbcB2b2c;

/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 */
public interface FirCtbcB2b2cDao extends IBatisBaseDao<FirCtbcB2b2c, BigDecimal> {

	//mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業
	public Aps039DetailVo selectForAps039Detail(Map params) throws Exception;

}