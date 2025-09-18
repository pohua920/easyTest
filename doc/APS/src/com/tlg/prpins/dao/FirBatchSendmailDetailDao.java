package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.aps.vo.WriteForBatchSendmailVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirBatchSendmailDetail;

public interface FirBatchSendmailDetailDao extends IBatisBaseDao<FirBatchSendmailDetail, BigDecimal> {

	//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
	public List<WriteForBatchSendmailVo> findRiskcodeForNoticeEmail();
}