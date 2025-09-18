package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.Tmpfetclaimcomm;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public interface TmpfetclaimcommDao extends IBatisBaseDao<Tmpfetclaimcomm, BigDecimal> {
	
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 start */
	public boolean removeAll();
	public void processInBatch(final List<Tmpfetclaimcomm> listTmpfetclaimcomm);
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫  end*/
}