package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.CarReinsurancePlanDao;
import com.tlg.prpins.entity.CarReinsurancePlan;

/** mantis：CAR0553，處理人員：DP0706，需求單編號：CAR0553.APS-車險再保註記設定維護功能 */
public class CarReinsurancePlanDaoImpl extends IBatisBaseDaoImpl<CarReinsurancePlan, BigDecimal> implements CarReinsurancePlanDao {
	@Override
	public String getNameSpace() {
		return "CarReinsurancePlan";
	}
    
}