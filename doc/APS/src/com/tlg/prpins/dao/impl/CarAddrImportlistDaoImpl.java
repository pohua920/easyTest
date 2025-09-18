package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.CarAddrImportlistDao;
import com.tlg.prpins.entity.CarAddrImportlist;

/** mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業 */
public class CarAddrImportlistDaoImpl extends IBatisBaseDaoImpl<CarAddrImportlist, BigDecimal> implements CarAddrImportlistDao {
	@Override
	public String getNameSpace() {
		return "CarAddrImportlist";
	}
    
}