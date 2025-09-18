package com.tlg.prpins.dao;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.HasBatchMatchFet;

/** mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同*/
public interface HasBatchMatchFetDao extends IBatisBaseDao<HasBatchMatchFet, BigDecimal> {

}
