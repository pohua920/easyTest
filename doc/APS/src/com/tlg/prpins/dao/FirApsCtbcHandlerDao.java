package com.tlg.prpins.dao;

import java.math.BigDecimal;

import com.tlg.prpins.entity.FirApsCtbcHandler;
import com.tlg.iBatis.IBatisBaseDao;

/* mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
 * 調整角色權限控管功能，後USER決定取消此功能*/
public interface FirApsCtbcHandlerDao extends IBatisBaseDao<FirApsCtbcHandler, BigDecimal> {
	
	public String selectByUpperComcode(String upperComcode)throws Exception;
	
}