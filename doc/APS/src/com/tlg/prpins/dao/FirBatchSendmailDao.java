package com.tlg.prpins.dao;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirBatchSendmail;

/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險)*/
public interface FirBatchSendmailDao extends IBatisBaseDao<FirBatchSendmail, BigDecimal> {

}