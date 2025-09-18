package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.TiiTvmetpDao;
import com.tlg.prpins.entity.TiiTvmetp;

/** mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業 */
public class TiiTvmetpDaoImpl extends IBatisBaseDaoImpl<TiiTvmetp, BigDecimal> implements TiiTvmetpDao {

	@Override
	public String getNameSpace() {
		return "TiiTvmetp";
	}
}